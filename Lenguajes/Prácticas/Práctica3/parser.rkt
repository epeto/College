#lang plai

(require "grammars.rkt")

;; Analizador sintáctico para WAE.
;; Dada una expresión en sintaxis concreta, regresa el árbol de sintaxis abstracta correspondiente, es
;; decir, construye expresiones del tipo de dato abstracto definido en el archivo grammars.rkt
;; parse: symbol -> WAE.
(define (parse sexp)
   (match sexp
     [(? symbol?) (id sexp)]
     [(? number?) (num sexp)]
     [(list 'with bindings body) (with (map parse-bind bindings) (parse body))]
     [(list 'with* bindings body) (with* (map parse-bind bindings) (parse body))]
     [(? list?) (op (select (car sexp)) (map parse (cdr sexp) ))]
     ))

;; Función auxiliar que convierte a tipo binding una parte del with

(define (parse-bind list)
  (binding (car list) (parse (second list)))
  )

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
    ))

(define (dame-el-power . lst)
  (match lst
    [(list x) x]
    [l  (expt (apply dame-el-power (e-u l)) (last l))]))
(define (e-u l)
  (match l
    ['() '()]
    [(cons x xs) (if (null? xs)
                     '()
                     (cons x (e-u xs)))]))