#lang plai

;; TAD FWBAE para representar el árbol de sintaxis abstracta con azúcar sintáctica.
;; El lenguaje FWBAE reconoce expresiones aritméticas, opéraciones n-arias, asignaciones locales e
;; identificadores.
(define-type FWBAE
   [idS (i symbol?)]
   [numS (n number?)]
   [boolS (b boolean?)]
   [opS (f procedure?) (args (listof FWBAE?))]
   [withS (bindings (listof binding?)) (body FWBAE?)]
   [withS* (bindings (listof binding?)) (body FWBAE?)]
   [funS (params (listof symbol?)) (body FWBAE?)]
   [appS (fun-expr FWBAE?) (args (listof FWBAE?))])

;; TAD FBAE para representar el árbol de sintaxis abstracta.
;; El lenguaje FBAE reconoce expresiones aritméticas, operaciones n-arias e identificadores.
(define-type FBAE
   [id (i symbol?)]
   [num (n number?)]
   [bool (b boolean?)]
   [op (f procedure?) (args (listof FBAE?))]
   [fun (params (listof symbol?)) (body FBAE?)]
   [app (fun-expr FBAE?) (args (listof FBAE?))])

;; TAD Bindig que nos permite representar a los identificadores con su valor.
(define-type Binding
   [binding (name symbol?) (value FWBAE?)])

;; TAD para representar el ambiente de evaluación
(define-type Env
	[mtSub]
	[aSub (name symbol?) (value FBAE-Value?) (ds Env?)])

;; TAD para representar los resultados devueltos por el intérprete. El intérprete únicamente devuelve
;; números, booleanos y funciones (cerraduras).
(define-type FBAE-Value
	[numV (n number?)]
	[boolV (b boolean?)]
	[closureV (params (listof symbol?)) (body FBAE?) (ds Env?)])
