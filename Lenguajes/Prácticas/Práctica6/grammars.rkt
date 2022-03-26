#lang plai

;; Gramática para el árbol de sintaxis abstraca.
;; Versión con azúcar sintáctica. Se incluyen:
;; · Identificadores
;; · Números
;; · Booleanos
;; · Listas
;; · Operaciones n-arias
;; · Condicionales: if y cond
;; · Asignaciones locales: with, with* y rec.
;; · Funciones y su aplicación.
(define-type RCFWBAEL
   [idS (i symbol?)]
   [numS (n number?)]
   [boolS (b boolean?)]
   [listS (l ListaS?)]
   [opS (f procedure?) (args (listof RCFWBAEL?))]
   [ifS (expr RCFWBAEL?) (then-expr RCFWBAEL?) (else-expr RCFWBAEL?)]
   [condS (cases (listof Condition?))]
   [withS (bindings (listof bindingS?)) (body RCFWBAEL?)]
   [withS* (bindings (listof bindingS?)) (body RCFWBAEL?)]
   [recS (bindings (listof bindingS?)) (body RCFWBAEL?)]
   [funS (params (listof symbol?)) (body RCFWBAEL?)]
   [appS (fun-expr RCFWBAEL?) (args (listof RCFWBAEL?))])

;; Versión desendulzada de la gramática RCFWBAEL
(define-type RCFBAEL
   [id (i symbol?)]
   [num (n number?)]
   [bool (b boolean?)]
   [lisT (l Lista?)]
   [op (f procedure?) (args (listof RCFBAEL?))]
   [iF (expr RCFBAEL?) (then-expr RCFBAEL?) (else-expr RCFBAEL?)]
   [fun (params (listof symbol?)) (body RCFBAEL?)]
   [rec (bindings (listof binding?)) (body RCFBAEL?)]
   [app (fun-expr RCFBAEL?) (args (listof RCFBAEL?))])

;; Tipo ListaS que representa listas endulzadas.
(define-type ListaS
   [emptyLS]
   [consLS (expr RCFWBAEL?) (xs RCFWBAEL?)])

;; Tipo Lista que representa listas desendulzadas.
(define-type Lista
   [emptyL]
   [consL (expr RCFBAEL?) (xs RCFBAEL?)])

;; Tipo bindingS que representa bindings endulzados.
;; Se agrega esto, pues rec no está endulzado.
(define-type BindingS
   [bindingS (name symbol?) (value RCFWBAEL?)])

;; Tipo bindings desendulzados.
(define-type Binding
   [binding (name symbol?) (value RCFBAEL?)])

;; Para las condiciones de cond.
(define-type Condition
	[condition (expr RCFWBAEL?) (then-expr RCFWBAEL?)]
	[else-cond (else-expr RCFWBAEL?)])

;; Ambientes recursivos.
(define-type Env
	[mtSub]
	[aSub (name symbol?) (value RCFBAEL-Value?) (ds Env?)]
   [aRecSub (name symbol?) (value boxed-RCFBAEL-Value?) (env Env?)])

;; Para trabajar con cajas.
(define (boxed-RCFBAEL-Value? v)
   (and (box? v) (RCFBAEL-Value? (unbox v))))

;; Los resultados incluyen:
;; · Números
;; · Booleanos
;; · Funciones (Cerraduras)
;; · Listas
(define-type RCFBAEL-Value
	[numV (n number?)]
	[boolV (b boolean?)]
	[closureV (params (listof symbol?)) (body RCFBAEL?) (ds Env?)]
   [listV (l ListaVal?)])

;; Para representar listas como valor
(define-type ListaVal
   [emptyV]
   [consV (expr RCFBAEL-Value?) (xs RCFBAEL-Value?)])
