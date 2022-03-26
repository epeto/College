#lang plai

;Este capítulo habla de postergar la substitución al poner las variables que
;ya se definieron en un repositorio (que después llamaremos ambiente).

;;Definición de nuestro tipo F1WAE.
(define-type F1WAE
  [num (n number?)]
  [add (lhs F1WAE?) (rhs F1WAE?)]
  [with (name symbol?) (named-expr F1WAE?) (body F1WAE?)]
  [id (name symbol?)]
  [app (fun-name symbol?) (arg F1WAE?)])


;;Definición de función
(define-type FunDef
  [fundef (fun-name symbol?)
          (arg-name symbol?)
          (body F1WAE?)])


;Esta es la definición del repositorio que en realidad es una lista ligada de variables con su valor.
(define-type DefrdSub
	[mtSub] ;Lista vacía
	[aSub (name symbol?) (value number?) (ds DefrdSub?)]) ;Nombre de la variable seguida de su valor, seguida de una lista.


;Busca el nombre de la variable(name) en la lista ds y devuelve el valor de dicha variable.
(define (lookup name ds)
  (type-case DefrdSub ds
    [mtSub () (error 'lookup "no binding for identifier" )]
    [aSub (bound-name bound-value rest-ds) ;bound-name=nombre de la variable, bound-value=valor de la variable
          (if (symbol=? bound-name name)
              bound-value
              (lookup name rest-ds))]))


;; lookup-fundef : symbol listof(FunDef) -→ FunDef
;Hace un lookup pero en las definiciones de funciones
(define (lookup-fundef fun-name fundefs)
  (cond
    [(empty? fundefs) (error 'fun-name "function not found" )]
    [else (if (symbol=? fun-name (fundef-fun-name (first fundefs)))
              (first fundefs)
              (lookup-fundef fun-name (rest fundefs)))]))


;;Definición del parser (analizador sintáctico)
(define (parse sexp)
  (cond
    [(number? sexp) (num sexp)]
    [(symbol? sexp) (id sexp)]
    [(list? sexp)
     (case (first sexp)
       [(+) (add (parse (second sexp)) (parse (third sexp)))]
       [(with) (with (first (second sexp))
                     (parse (second (second sexp)))
                     (parse (third sexp)))]
       [else (app (first sexp) (parse (second sexp)))])]))


;;Intérprete
(define (interp expr fun-defs ds)
(type-case F1WAE expr
  [num (n) n]
  [add (l r) (+ (interp l fun-defs ds) (interp r fun-defs ds))]
  [with (bound-id named-expr bound-body)
        (interp bound-body
                fun-defs
                (aSub bound-id  ;Extendemos la lista de ds
                      (interp named-expr
                              fun-defs
                              ds)
                      ds))]
  [id (v) (lookup v ds)]
  [app (fun-name arg-expr)
       (local ([define the-fun-def (lookup-fundef fun-name fun-defs)])
         (interp (fundef-body the-fun-def )
                 fun-defs
                 (aSub (fundef-arg-name the-fun-def)
                       (interp arg-expr fun-defs ds)
                       (mtSub))))])) ;Solo le pasamos el valor del parámetro formal y nos olvidamos de ds.