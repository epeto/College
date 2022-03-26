#lang plai

;;Definición del tipo Arithmetic Expresion
(define-type AE
[num (n number?)]
[add (lhs AE?) (rhs AE?)]
[sub (lhs AE?) (rhs AE?)])

;;Definición del parser de AE
(define (parse sexp)
(cond
[(number? sexp) ( num sexp)]
[(list? sexp)
(case (first sexp)
	[(+) ( add (parse (second sexp)) (parse (third sexp)))]
	[(-) ( sub (parse (second sexp)) (parse (third sexp)))])]))

;;Definición del intérprete de AE
(define (calc an-ae)
(type-case AE an-ae
[ num (n) n]
[ add (l r) (+ (calc l) (calc r))]
[ sub (l r) (- (calc l) (calc r))]))