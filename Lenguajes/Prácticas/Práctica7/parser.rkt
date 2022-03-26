#lang plai

(require "grammars.rkt")

;; Analizador sintáctico para FBAE.
;; Dada una expresión en sintaxis concreta, regresa el árbol de sintaxis abstracta correspondiente, 
;; es decir, construye expresiones del tipo de dato abstracto definido en el archivo grammars.rkt
;; parse: symbol -> RCFWBAEL.
(define (parse sexp)
  (match sexp
     ['true (boolS #t)]
     ['false (boolS #f)]
     ['empty (listS (emptyLS))]
     [(? symbol?) (idS sexp)]
     [(? number?) (numS sexp)]
     [(list 'cons exp xs) (listS (consLS (parse exp) (parse xs)))]
     [(list 'if cond then else) (ifS (parse cond) (parse then) (parse else))]
     [(cons 'cond xs) (condS (map parse-cond xs))]
     [(list 'with bindings body) (withS (map parse-bindS bindings) (parse body))]
     [(list 'with* bindings body) (withS* (map parse-bindS bindings) (parse body))]
     [(list 'rec bindings body) (recS (map parse-bindS bindings) (parse body))]
     [(list 'fun params ': tipo-regreso fun-body) (funS (map parseParam params) (parseT tipo-regreso) (parse fun-body))]
     [(cons (? proc?) xs) (opS (select (car sexp)) (map parse (cdr sexp) ))]
     [(? list?) (appS (parse (car sexp)) (map parse (cdr sexp)))]))

;; Función auxiliar que convierte a tipo Condition las expresiones
;; de la forma {<expr/else> <expr>} donde
;; <expr/else> ::= <expr> | else

(define (parse-cond cnd)
  (match (car cnd)
    ['else  (else-cond (parse (cadr cnd)))]
    [else (condition (parse (car cnd)) (parse (cadr cnd)))]))

;; Función auxiliar que convierte a tipo bindingS
;; las expresiones del tipo {<id> <expr>}

(define (parse-bindS list)
  (bindingS (car list) (parseT (caddr list)) (parse (cadddr list))))

;; Función que toma un símbolo y regresa el tipo correspondiente

(define (parseT type)
  (match type
    ['number (tnumber)]
    ['boolean (tboolean)]
    ['a (a)]
    [(list 'listof t) (tlistof (parseT t))]
    [(list t1 '-> t2) (tarrow (parseT t1) (parseT t2))]
    [else (error 'parseT (format "~a : not a valid type" type))]))

;; Función auxiliar que convierte a tipo Parameter
;; las expresiones del tipo {<id> : <type>}

(define (parseParam par)
  (param (car par) (parseT (caddr par))))

;; Función que convierte símbolos de función en procedimientos 

(define (select op)
  (match op
    ['+ +]
    ['- -]
    ['* *]
    ['/ /]
    ['% dame-el-modulo]
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
    ['neg not]
    ['head consV-expr]
    ['tail consV-xs]
    ['empty? emptyV?]
    ['zero? zero?]
    ))

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
    ['head true]
    ['tail true]
    ['empty? true]
    ['zero? true]
    [else false]))

;; Función que recibe como parámetros dos o más números de la forma 
;; (dame-el-modulo n1 n2 n3...) y regresa ((n1%(n2%n3))...)

(define (dame-el-modulo . lst)
  (cond
    [(empty? lst) 1]
    [(equal? 1 (length lst)) (car lst)]
    [else (modulo (car lst) (apply dame-el-modulo (cdr lst)))]))

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

;; El factorial de 5

(define f5 '{rec {{fac : (number -> number) {fun {{n : number}} : number {if {= n 0} 1 {* n {fac {- n 1}}}}}}} {fac 5}})

;; Función que quita el azúcar sintáctica de las expresiones en FWBAE. En pocas palabras, hace un 
;; mapeo entre RCFWBAEL y RCFBAE. Las expresiones que tienen azúcar sintáctica son with, with* y
;; cond.
;; desugar RCFWBAEL -> RCFWBAEL.
(define (desugar sexps)
   (match sexps
     [(idS i) (id i)]
     [(numS n) (num n)]
     [(boolS b) (bool b)]
     [(listS l) (lisT (list-deconstruct l))]
     [(ifS eval then else) (iF (desugar eval) (desugar then) (desugar else))]
     [(condS cases)  (desugar (cond-deconstruct cases))]
     [(opS f args) (op f (map desugar args))]
     [(funS params result body) (fun (params-ids params) (desugar body))]
     [(appS fun-expr args) (app (desugar fun-expr) (map desugar args))]
     [(withS bindings body) (app
                             (fun
                              (bindings-ids bindings)
                              (desugar body))
                             (map desugar (bindings-values bindings)))]
     [(withS* bindings body) (desugar (with*-deconstruct sexps))]
     [(recS bindings body) (rec (map desugar-binding bindings) (desugar body))]))

;; Función auxiliar que desendulza un binding dado

(define (desugar-binding sugared-binding)
  (binding (bindingS-name sugared-binding) (desugar (bindingS-value sugared-binding))))

;; Función auxiliar que toma una lista de parámetros y regresa sus id's

(define (params-ids params)
  (match params
    ['() '()]
    [(cons x xs) (cons (param-name x) (params-ids xs))]))

;; Función auxiliar que toma una lista de bindings y regresa sus id's

(define (bindings-ids bins)
  (match bins
    ['() '()]
    [(cons x xs) (cons (bindingS-name x) (bindings-ids xs))]))

;; Función auxiliar que toma una lista de bindings y regresa sus valores

(define (bindings-values bins)
  (match bins
    ['() '()]
    [(cons x xs) (cons (bindingS-value x) (bindings-values xs))]))

;; Función que hace el trabajo de quitar el azucar sintáctica del with*

(define (with*-deconstruct with*exp)
  (if (eq? 1 (length (withS*-bindings with*exp)))
      (withS (withS*-bindings with*exp) (withS*-body with*exp))
      (withS
       (list (car (withS*-bindings with*exp)))
       (with*-deconstruct (withS*
                           (cdr (withS*-bindings with*exp))
                           (withS*-body with*exp))))))

;; Función que hace el trabajo de quitar el azucar sintáctica del cond

(define (cond-deconstruct cases-list)
 (if (= 2 (length cases-list))
     (ifS (condition-expr (car cases-list))
         (condition-then-expr (car cases-list))
         (else-cond-else-expr (cadr cases-list)))
     (ifS (condition-expr (car cases-list))
         (condition-then-expr (car cases-list))
         (cond-deconstruct (cdr cases-list)))))

;; Función que hace el trabajo de quitar el azucar sintáctica de las listas

(define (list-deconstruct sugared-list)
  (cond
    [(emptyLS? sugared-list) (emptyL)]
    [else (consL (desugar (consLS-expr sugared-list)) (desugar (consLS-xs sugared-list)))]))