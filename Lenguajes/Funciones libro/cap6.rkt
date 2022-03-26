#lang plai

;Este capítulo habla de funciones de primera clase.

;;Definición del tipo de funciones de primera clase.
(define-type FAE
  [num (n number?)]
  [add (lhs FAE?) (rhs FAE?)]
  [id (name symbol?)]
  [fun (param symbol?) (body FAE?)]
  [app (fun-expr FAE?) (arg-expr FAE?)])

;;Valores de retorno del interp de FAE: número o función
(define-type FAE-Value
  [numV (n number?)]
  [closureV (param symbol?) ;Parámetro formal
            (body FAE?)     ;Cuerpo
            (ds Env?)])     ;Repositorio (ambiente)

;Definición del tipo ambiente
(define-type Env
  [mtSub]
  [aSub (name symbol?) (value FAE-Value?) (ds Env?)])

;; lookup : symbol Env → FAE-Value
(define (lookup name ds)
  (type-case Env ds
    [mtSub () (error 'lookup "no binding for identifier" )]
    [aSub (bound-name bound-value rest-ds)
          (if (symbol=? bound-name name)
              bound-value
              (lookup name rest-ds))]))

;; num+ : numV numV -→ numV
(define (num+ n1 n2)
  (numV (+ (numV-n n1) (numV-n n2))))

;; interp : FAE Env → FAE-Value
(define (interp expr ds)
  (type-case FAE expr
    [num (n) (numV n)]
    [add (l r) (num+ (interp l ds) (interp r ds))]
    [id (v) (lookup v ds)]
    [fun (bound-id bound-body)
          (closureV bound-id bound-body ds)]
    [app (fun-expr arg-expr) ;fun-expr es una definición de función o una variable.
          (local ([define fun-val (interp fun-expr ds)])
            (interp (closureV-body fun-val) ;Saca el cuerpo de fun-val
                    (aSub (closureV-param fun-val)
                           (interp arg-expr ds)
                           (closureV-ds fun-val))))]))

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
