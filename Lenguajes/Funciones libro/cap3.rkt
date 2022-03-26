#lang plai

;;Definición del tipo With Arithmetic Expresion
(define-type WAE
[num (n number?)]
[add (lhs WAE?) (rhs WAE?)]
[sub (lhs WAE?) (rhs WAE?)]
[with (name symbol?) (named-expr WAE?) (body WAE?)]
[id (name symbol?)])

;;Definición del parser
(define (parse sexp)
(cond
[(number? sexp) (num sexp)]
[(symbol? sexp) (id sexp)]
[(list? sexp)
(case (first sexp)
	[(+) (add (parse (second sexp)) (parse (third sexp)))]
	[(-) (sub (parse (second sexp)) (parse (third sexp)))]
	[(with) (with (first (second sexp))
                      (parse (second (second sexp)))
                      (parse (third sexp)))])]))

;;Definición del subst
(define (subst expr sub-id val)
(type-case WAE expr
[num (n) expr]
[add (l r) ( add (subst l sub-id val) (subst r sub-id val))]
[sub (l r) ( sub (subst l sub-id val) (subst r sub-id val))]
[with (bound-id named-expr bound-body)
      (if (symbol=? bound-id sub-id)
          (with bound-id (subst named-expr sub-id val) bound-body)
          (with bound-id (subst named-expr sub-id val) (subst bound-body sub-id val)))]
[id (v) (if (symbol=? v sub-id) val expr)]))

;;Definición del interp
(define (calc expr)
(type-case WAE expr
[num (n) n]
[add (l r) (+ (calc l) (calc r))]
[sub (l r) (- (calc l) (calc r))]
[with (bound-id named-expr bound-body) (calc (subst bound-body bound-id (num (calc named-expr))))]
[id (v) (error 'calc "free identifier" )]))