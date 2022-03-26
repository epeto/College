#lang plai

;Este capítulo implementa el lenguaje del capítulo 6 pero usando evaluación perezosa.
;En esta versión no utilizamos "cache".

;;Definición del tipo CFAE/Lazy
(define-type CFAE/L
  [num (n number?)]
  [add (lhs CFAE/L?) (rhs CFAE/L?)]
  [id (name symbol?)]
  [fun (param symbol?) (body CFAE/L?)]
  [app (fun-expr CFAE/L?) (arg-expr CFAE/L?)])

;;Definición del valor de retorno: número, función o expresión.
(define-type CFAE/L-Value
  [numV (n number?)]
  [closureV (param symbol?)
            (body CFAE/L?)
            (env Env?)]
  [exprV (expr CFAE/L?)
         (env Env?)])

;;Definición del ambiente (Lista ligada con tipos: variable -> valor)
(define-type Env
  [mtSub]
  [aSub (name symbol?) (value CFAE/L-Value?) (env Env?)])

;; num+ : CFAE/L-Value CFAE/L-Value → numV
(define (num+ n1 n2)
  (numV (+ (numV-n (strict n1)) (numV-n (strict n2)))))

;Evaluación estricta de un exprV
;; strict : CFAE/L-Value → CFAE/L-Value [excluding exprV ]
(define (strict e)
  (type-case CFAE/L-Value e
    [exprV (expr env)
           (local ([define the-value (strict (interp expr env))]) ;the-value es el resultado de interpretar la expresión envuelta por exprV
             (begin
               (printf "Forcing exprV\n")
               the-value))]
    [else e]))

;; num-zero? : CFAE/L-Value → boolean
(define (num-zero? n)
  (zero? (numV-n (strict n))))

;; interp : CFAE/L Env → CFAE/L-Value
(define (interp expr env)
  (type-case CFAE/L expr
    [num (n) (numV n)]
    [add (l r) (num+ (interp l env) (interp r env))]
    [id (v) (lookup v env)]
    [fun (bound-id bound-body)
         (closureV bound-id bound-body env)]
    [app (fun-expr arg-expr)
         (local ([define fun-val (strict (interp fun-expr env))] ;Punto estricto
                 [define arg-val (exprV arg-expr env)])
           (interp (closureV-body fun-val)
                   (aSub (closureV-param fun-val)
                         arg-val
                         (closureV-env fun-val))))]))

;; lookup : symbol Env → FAE-Value
(define (lookup name ds)
  (type-case Env ds
    [mtSub () (error 'lookup "no binding for identifier")]
    [aSub (bound-name bound-value rest-ds)
          (if (symbol=? bound-name name)
              bound-value
              (lookup name rest-ds))]))

;;Parser
(define (parse sexp)
  (cond
    [(number? sexp) (num sexp)]
    [(symbol? sexp) (id sexp)]
    [(list? sexp)
     (case (first sexp)
       [(+) (add (parse (second sexp)) (parse (third sexp)))]
       [(with) (app (fun (first (second sexp)) (parse (third sexp))) (parse (second (second sexp))))]
       [(fun) (fun (first (second sexp)) (parse (third sexp)))]
       [else (app (parse (first sexp)) (parse (second sexp)))])]))