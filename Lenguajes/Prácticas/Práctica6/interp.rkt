#lang plai

(require "grammars.rkt")
(require "parser.rkt")

;; Función encargada de interpretar el árbol de sintaxis abstracta generado por el parser. El
;; intérprete requiere un ambiente de evaluación en esta versión para buscar el valor de los 
;; identificadores.
;; interp: RCFBAEL Env -> RCFBAEL-Value

(define (interp exp env)
   (match exp
     [(id i)(lookup i env)]
     [(num n) (numV n)]
     [(bool b) (boolV b)]
     [(lisT l) (listV (Lista->ListaVal l env))]
     [(op f args) (local [(define op-val
                            (apply
                             f
                             (get-values
                              (map (lambda (x) (interp x env)) args))))]
                    (cond
                      [(boolean? op-val) (boolV op-val)]
                      [(number? op-val) (numV op-val)]
                      [else op-val]))]
     [(iF expr then-expr else-expr) (if (boolV-false? (interp expr env))
                                        (interp else-expr env)
                                        (interp then-expr env))]
     [(fun params body) (closureV params body env)]
     [(app fun-expr args) (local [(define fun-val (interp fun-expr env))]
                            (interp
                             (closureV-body fun-val)
                             (brand-new-env (closureV-params fun-val)
                                            (map (lambda (x) (interp x env)) args)
                                            (closureV-ds fun-val) ;esta línea es para estático
                                            ;env  esta línea es para dinámico                                            
                                            )))]
     [(rec bindings body) (interp body
                                  (ciclically-bind-and-interp-list
                                   bindings env))]))

;; Función que recibe un binding junto con un ambiente y crea otro
;; ambiente que se usa para definir funciones recursivas

(define (ciclically-bind-and-interp binding env)
  (local ([define value-holder (box (numV 1729))]
          [define new-env (aRecSub (binding-name binding) value-holder env)]
          [define named-expr-val (interp (binding-value binding) new-env)])
    (begin
      (set-box! value-holder named-expr-val)
      new-env)))

;; Generalización de ciclically-bind-and-interp para una lista de bindings

(define (ciclically-bind-and-interp-list bindings env)
  (cond
    [(empty? bindings) env]
    [else (ciclically-bind-and-interp-list (cdr bindings)
                                           (ciclically-bind-and-interp (car bindings) env))]
    ))

;; Función auxiliar que toma una lista de FBAE-Value que o bien son números
;; y regresa su representación en número, o bien son booleanos y regresa
;; su representación en booleanos

(define (get-values lst)
  (match lst
    ['() '()]
    [(cons (numV n) xs) (cons n (get-values xs))]
    [(cons (boolV b) xs) (cons b (get-values xs))]
    [(cons (listV l) xs) (cons l (get-values xs))]))

;; Función que busca en un ambiente una varable y regresa su valor
(define (lookup v env)
  (match env
    [(mtSub) (error 'interp (format "Variable libre: ~a" v) )]
    [(aSub name value ds)(if (symbol=? name v)
                             value
                             (lookup v ds))]
    [(aRecSub name value env) (if (symbol=? name v)
                                  (unbox value)
                                  (lookup v env))]))

;; Función auxiliar que toma una lista de variables, una lista de valores
;; y un ambiente y construye otro ambiente que incluye las
;; asignaciones correspondientes

(define (brand-new-env idl vall env)
  (match idl
    ['() env]
    [(cons x xs) (aSub x
                       (if (null? vall)
                           (error 'brand-new-env "arity mismatch: too few arguments")
                           (car vall))
                       (brand-new-env xs (cdr vall) env))]))

;; Función que convierte una lista desendulzada a una lista
;; vista como un valor

(define (Lista->ListaVal lst env)
  (match lst
    [(emptyL) (emptyV)]
    [(consL expr xs) (consV (interp expr env)
                            (interp xs env))]))

;; Función auxiliar que dice si la expresión dada es de tipo
;; booleano y evalua a false

(define (boolV-false? sexpr)
  (if (and (boolV? sexpr) (false? (boolV-b sexpr)))
      true
      false))