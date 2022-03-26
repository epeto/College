#lang plai

(require "grammars.rkt")
(require "parser.rkt")

;; Función encargada de interpretar el árbol de sintaxis abstracta generado por el parser. El
;; intérprete requiere un ambiente de evaluación en esta versión para buscar el valor de los 
;; identificadores.
;; interp: FBAE Env -> FBAE-Value
(define (interp exp env)
   (match exp
     [(id i)(lookup i env)]
     [(num n) (numV n)]
     [(bool b) (boolV b)]
     [(op f args) (local [(define op-val
                            (apply
                             f
                             (get-values
                              (map (lambda (x) (interp x env)) args))))]
                    (if (boolean? op-val)
                        (boolV op-val)
                        (numV op-val)))]
     [(fun params body) (closureV params body env)]
     [(app fun-expr args) (local [(define fun-val (interp fun-expr env))]
                            (interp
                             (closureV-body fun-val)
                             (brand-new-env (closureV-params fun-val)
                                            (map (lambda (x) (interp x env)) args)
                                            (closureV-ds fun-val) ;esta línea es para estático
                                            ;env  esta línea es para dinámico                                            
                                            )))]))
                                            

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
    [(mtSub) (error 'interp "Variable libre >:|")]
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
