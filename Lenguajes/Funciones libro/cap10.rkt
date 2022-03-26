#lang plai

;Este capítulo implementa recursión usando ambientes cíclicos con cajas.

;Definición del tipo RCFAE
(define-type RCFAE
  [num (n number?)]
  [add (lhs RCFAE?) (rhs RCFAE?)] ;Suma
  [mult (lhs RCFAE?) (rhs RCFAE?)] ;Multiplicación
  [sub (lhs RCFAE?) (rhs RCFAE?)] ;Resta
  [div (lhs RCFAE?) (rhs RCFAE?)];Division
  [id (name symbol?)]
  [fun (param symbol?) (body RCFAE?)] ;definición de función
  [app (fun-expr RCFAE?) (arg-expr RCFAE?)] ;aplicación de función
  [if0 (test RCFAE?) (truth RCFAE?) (falsity RCFAE?)] ;condición
  [rec (name symbol?) (named-expr RCFAE?) (body RCFAE?)]) ;recursión

;Valor de retorno
(define-type RCFAE-Value
  [numV (n number?)]
  [closureV (param symbol?)
            (body RCFAE?)
            (env Env?)])

;Predicado para verificar si el valor de una caja pertenece a nuestro lenguaje.
(define (boxed-RCFAE-Value? v)
  (and (box? v)
       (RCFAE-Value? (unbox v))))

;Definición del ambiente
(define-type Env
  [mtSub] ;Vacío
  [aSub (name symbol?)
        (value RCFAE-Value?) ;Función o número
        (env Env?)]
  [aRecSub (name symbol?)
           (value boxed-RCFAE-Value?) ;Caja
           (env Env?)])

;; lookup : symbol env → RCFAE-Value
(define (lookup name env)
  (type-case Env env
    [mtSub () (error 'lookup "no binding for identifier" )]
    [aSub (bound-name bound-value rest-env)
          (if (symbol=? bound-name name)
              bound-value
              (lookup name rest-env))]
    [aRecSub (bound-name boxed-bound-value rest-env)
             (if (symbol=? bound-name name)
                 (unbox boxed-bound-value)
                 (lookup name rest-env))]))

;; cyclically-bind-and-interp : symbol RCFAE env → env
(define (cyclically-bind-and-interp bound-id named-expr env)
  (local ([define value-holder (box (numV 1729))]
          [define new-env (aRecSub bound-id value-holder env)]
          [define named-expr-val (interp named-expr new-env)])
    (begin
      (set-box! value-holder named-expr-val)
      new-env)))

;num+::numV,numV->numV
(define (num+ izq der)
  (numV (+ (numV-n izq) (numV-n der))))

;num*::numV,numV->numV
(define (num* izq der)
  (numV (* (numV-n izq) (numV-n der))))

;num-::numV,numV->numV
(define (num- izq der)
  (numV (- (numV-n izq) (numV-n der))))

;num/::numV,numV->numV
(define (num/ izq der)
  (numV (/ (numV-n izq) (numV-n der))))

;verifica si un número es 0
(define (num-zero? arg)
  (zero? (numV-n arg)))

;; interp : RCFAE env → RCFAE-Value
(define (interp expr env)
  (type-case RCFAE expr
    [num (n) (numV n)]
    [add (l r) (num+ (interp l env) (interp r env))]
    [mult (l r) (num* (interp l env) (interp r env))]
    [sub (l r) (num- (interp l env) (interp r env))]
    [div (l r) (num/ (interp l env) (interp r env))]
    [if0 (test truth falsity)
         (if (num-zero? (interp test env))
             (interp truth env)
             (interp falsity env))]
    [id (v) (lookup v env)]
    [fun (bound-id bound-body)
         (closureV bound-id bound-body env)]
    [app (fun-expr arg-expr)
         (local ([define fun-val (interp fun-expr env)])
           (interp (closureV-body fun-val)
                   (aSub (closureV-param fun-val)
                         (interp arg-expr env)
                         (closureV-env fun-val))))]
    [rec (bound-id named-expr bound-body)
      (interp bound-body
              (cyclically-bind-and-interp bound-id
                                          named-expr
                                          env))]))

;;Parser
(define (parse sexp)
  (cond
    [(number? sexp) (num sexp)]
    [(symbol? sexp) (id sexp)]
    [(list? sexp)
     (case (first sexp)
       [(+) (add (parse (second sexp)) (parse (third sexp)))]
       [(*) (mult (parse (second sexp)) (parse (third sexp)))]
       [(-) (sub (parse (second sexp)) (parse (third sexp)))]
       [(/) (div (parse (second sexp)) (parse (third sexp)))]
       [(with) (app (fun (first (second sexp)) (parse (third sexp))) (parse (second (second sexp))))]
       [(fun) (fun (first (second sexp)) (parse (third sexp)))]
       [(if0) (if0 (parse (second sexp)) (parse (third sexp)) (parse (fourth sexp)))]
       [(rec) (rec (first (second sexp)) (parse (second (second sexp))) (parse (third sexp)))]
       [else (app (parse (first sexp)) (parse (second sexp)))])]))