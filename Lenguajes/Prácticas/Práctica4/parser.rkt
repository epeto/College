#lang plai

(require "grammars.rkt")

;; Analizador sintáctico para FBAE.
;; Dada una expresión en sintaxis concreta, regresa el árbol de sintaxis abstracta correspondiente, es
;; decir, construye expresiones del tipo de dato abstracto definido en el archivo grammars.rkt
;; parse: symbol -> FWBAE. (acá decía FBAE tal vez error de documentacion)
(define (parse sexp)
   (match sexp
     ['true (boolS #t)]
     ['false (boolS #f)]
     [(? symbol?) (idS sexp)]
     [(? number?) (numS sexp)]
     [(list 'with bindings body) (withS (map parse-bind bindings) (parse body))]
     [(list 'with* bindings body) (withS* (map parse-bind bindings) (parse body))]
     [(list 'fun params fun-body) (funS params (parse fun-body))]
     [(cons (? proc?) xs) (opS (select (car sexp)) (map parse (cdr sexp) ))]
     [(? list?) (appS (parse (car sexp)) (map parse (cdr sexp)))]))

;; Función auxiliar que convierte a tipo binding las expresiones del tipo {<id> <expr>}

(define (parse-bind list)
  (binding (car list) (parse (second list))))

;; Función que convierte símbolos de función en procedimientos 

(define (select op)
  (match op
    ['+ +]
    ['- -]
    ['* *]
    ['/ /]
    ['% modulo]
    ['min min]
    ['max max]
    ['pow dame-el-power]
    ['or nasty-or]
    ['and nasty-and]
    ['< <]
    ['<= <=]
    ['> >]
    ['>= >=]
    ['= =]
    ['!= nasty-not-equal]
    ['neg not]))

;; Función que dice si un símbolo es una operación soportada

(define (proc? s)
  (match s
    ['+ true]
    ['- true]
    ['* true]
    ['/ true]
    ['% true]
    ['min true]
    ['max true]
    ['pow true]
    ['or true]
    ['and true]
    ['< true]
    ['<= true]
    ['> true]
    ['>= true]
    ['= true]
    ['!= true]
    ['neg true]
    [else false]))

;; Función que recibe como parámetros dos o más números de la forma 
;; (dame-el-power n1 n2 n3...) y regresa (((n1^n2)^n3)...)

(define (dame-el-power . lst)
  (match lst
    [(list x) x]
    [l  (expt (apply dame-el-power (e-u l)) (last l))]))

;; Función auxiliar que elimina el último de una lista

(define (e-u l)
  (match l
    ['() '()]
    [(cons x xs) (if (null? xs)
                     '()
                     (cons x (e-u xs)))]))

;; Función auxiliar que toma una lista de booleanos y regresa su disyunción

(define (nasty-or . lst)
  (ormap (lambda (x) x) lst))

;; Función auxiliar que toma una lista de booleanos y regresa su conjunción

(define (nasty-and . lst)
  (andmap (lambda (x) x) lst))

;; Función auxiliar que toma una lista de números y dice si son diferentes

(define (nasty-not-equal . lst)
  (not (apply = lst)))

;; Función que quita el azúcar sintáctica de las expresiones en FWBAE. En pocas palabras, hace un 
;; mapeo entre FWBAE y FBAE. Las expresiones que tienen azúcar sintáctica son with y with*
;; desugar FWBAE -> FBAE.
(define (desugar sexps)
   (match sexps
     [(idS i) (id i)]
     [(numS n) (num n)]
     [(boolS b) (bool b)]
     [(opS f args) (op f (map desugar args))]
     [(funS params body) (fun params (desugar body))]
     [(appS fun-expr args) (app (desugar fun-expr) (map desugar args))]
     [(withS bindings body) (app
                             (fun
                              (bindings-ids bindings)
                              (desugar body))
                             (map desugar (bindings-values bindings)))]
     [(withS* bindings body) (desugar (with*-deconstruct sexps))]))

;; Función auxiliar que toma una lista de bindings y regresa sus id's

(define (bindings-ids bins)
  (match bins
    ['() '()]
    [(cons x xs) (cons (binding-name x) (bindings-ids xs))]))

;; Función auxiliar que toma una lista de bindings y regresa sus valores

(define (bindings-values bins)
  (match bins
    ['() '()]
    [(cons x xs) (cons (binding-value x) (bindings-values xs))]))

;; Función que hace el trabajo de quitar el azucar sintáctica del with*

(define (with*-deconstruct with*exp)
  (if (eq? 1 (length (withS*-bindings with*exp)))
      (withS (withS*-bindings with*exp) (withS*-body with*exp))
      (withS
       (list (car (withS*-bindings with*exp)))
       (with*-deconstruct (withS*
                           (cdr (withS*-bindings with*exp))
                           (withS*-body with*exp))))))