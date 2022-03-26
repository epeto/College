#lang plai
;; TAD WAE para representar el árbol de sintaxis abstracta.
;; El lenguaje WAE reconoce expresiones aritméticas, operaciones n-arias, asignaciones locales e
;; identificadores.
(define-type WAE
   [id (i symbol?)]
   [num (n number?)]
   [op (f procedure?) (args (listof WAE?))]
   [with (bindings (listof binding?)) (body WAE?)]
   [with* (bindings (listof binding?)) (body WAE?)])

;; TAD Bindig que nos permite representar a los identificadores con su valor.
(define-type Binding
   [binding (name symbol?) (value WAE?)])
