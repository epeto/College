#lang plai

(require "grammars.rkt")
(require "parser.rkt")

;; Función encargada de interpretar el árbol de sintaxis abstracta generado por el parser. El
;; intérprete requiere un ambiente de evaluación en esta versión para buscar el valor de los 
;; identificadores.
;; interp: CFBAE/L Env -> CFBAE/L-Value
(define (interp exp env)
   (match exp
     [(id i)(lookup i env)]
     [(num n) (numV n)]
     [(bool b) (boolV b)]
     [(iF expr then-expr else-expr) (if (boolV-false? (strict (interp expr env)))
                                       (interp else-expr env)
                                       (interp then-expr env))]
     [(op f args) (local [(define op-val
                            (apply
                             f
                             (get-values
                              (map (lambda (x)  (strict(interp x env))) args))))]
                    (if (boolean? op-val)
                        (boolV op-val)
                        (numV op-val)))]
     [(fun params body) (closureV params body env)]
     [(app fun-expr args) (local ([define fun-val (strict(interp fun-expr env))]
                                  [define fun-args (map (lambda (x) (exprV x env)) args)])
                            (interp
                             (closureV-body fun-val)
                             (brand-new-env (closureV-params fun-val)
                                            fun-args
                                            (closureV-ds fun-val) ;esta línea es para estático
                                            ;env  esta línea es para dinámico                                            
                                            )))]))

;; Función que fuerza la evaluación de una cerradura de expresión

(define (strict e)
  (match e
    [(exprV expr env) (strict (interp expr env))]
    [else e]))

;; Función auxiliar que toma una lista de FBAE-Value que o bien son números
;; y regresa su representación en número, o bien son booleanos y regresa
;; su representación en booleanos

(define (get-values lst)
  (match lst
    ['() '()]
    [(cons (numV n) xs) (cons n (get-values xs))]
    [(cons (boolV b) xs) (cons b (get-values xs))]))

;; Función que busca en un ambiente una varable y regresa su valor

(define (lookup v env)
  (match env
    [(mtSub) (error 'interp (format  "~a : Variable libre >:|" v))]
    [(aSub name value ds)(if (symbol=? name v)
                             value
                             (lookup v ds))]))

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

(define (boolV-false? sexpr)
  (if (and (boolV? sexpr) (false? (boolV-b sexpr)))
      true
      false))