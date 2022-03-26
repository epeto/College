#lang plai

;; TAD CFWBAE/L para representar el árbol de sintaxis abstracta con azúcar sintáctica.
;; El lenguaje CFWBAE/L reconoce expresiones aritméticas, opéraciones n-arias, asignaciones locales e
;; identificadores.
(define-type CFWBAE/L
   [idS (i symbol?)]
   [numS (n number?)]
   [boolS (b boolean?)]
   [opS (f procedure?) (args (listof CFWBAE/L?))]
   [ifS (expr CFWBAE/L?) (then-expr CFWBAE/L?) (else-expr CFWBAE/L?)]
   [condS (cases (listof Condition?))]
   [withS (bindings (listof binding?)) (body CFWBAE/L?)]
   [withS* (bindings (listof binding?)) (body CFWBAE/L?)]
   [funS (params (listof symbol?)) (body CFWBAE/L?)]
   [appS (fun-expr CFWBAE/L?) (args (listof CFWBAE/L?))])

;; TAD CFBAE/L para representar el árbol de sintaxis abstracta.
;; El lenguaje CFBAE/L reconoce expresiones aritméticas, operaciones n-arias e identificadores.
(define-type CFBAE/L
   [id (i symbol?)]
   [num (n number?)]
   [bool (b boolean?)]
   [op (f procedure?) (args (listof CFBAE/L?))]
   [iF (expr CFBAE/L?) (then-expr CFBAE/L?) (else-expr CFBAE/L?)]
   [fun (params (listof symbol?)) (body CFBAE/L?)]
   [app (fun-expr CFBAE/L?) (args (listof CFBAE/L?))])

;; TAD Bindig que nos permite representar a los identificadores con su valor.
(define-type Binding
   [binding (name symbol?) (value CFWBAE/L?)])

;; TAD Condition que permite representar una condición.
(define-type Condition
	[condition (expr CFWBAE/L?) (then-expr CFWBAE/L?)]
	[else-cond (else-expr CFWBAE/L?)])

;; TAD para representar el ambiente de evaluación
(define-type Env
	[mtSub]
	[aSub (name symbol?) (value CFBAE/L-Value?) (ds Env?)])

;; TAD para representar los resultados devueltos por el intérprete. El intérprete únicamente devuelve
;; números, booleanos y funciones (cerraduras).
(define-type CFBAE/L-Value
	[numV (n number?)]
	[boolV (b boolean?)]
	[closureV (params (listof symbol?)) (body CFBAE/L?) (ds Env?)]
	[exprV (expr CFBAE/L?) (env Env?)])
