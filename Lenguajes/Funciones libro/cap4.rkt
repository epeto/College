#lang plai

;;Tipo de funciones de primer orden.
(define-type F1WAE
  [num (n number?)] ;número
  [add (lhs F1WAE?) (rhs F1WAE?)] ;suma
  [with (name symbol?) (named-expr F1WAE?) (body F1WAE?)] ;with (let en Scheme)
  [id (name symbol?)]  ;variable ligada o libre
  [app (fun-name symbol?) (arg F1WAE?)]) ;aplicación de la función cuyo nombre es fun-name con el argumento arg.

;;Definición de función.
(define-type FunDef
  [fundef (fun-name symbol?) ;nombre de la función
          (arg-name symbol?) ;nombre del parámetro formal
          (body F1WAE?)])    ;cuerpo de la función

;Substitución
;; subst : F1WAE symbol F1WAE → F1WAE
(define (subst expr sub-id val) ;Se substituye la variable (sub-id) por el valor (val) en la expresión (expr)
  (type-case F1WAE expr
    [num (n) expr]
    [add (l r) ( add (subst l sub-id val)
                     (subst r sub-id val))]
    [with (bound-id named-expr bound-body)
          (if (symbol=? bound-id sub-id)
              (with bound-id
                    (subst named-expr sub-id val)
                    bound-body)
              (with bound-id
                    (subst named-expr sub-id val)
                    (subst bound-body sub-id val)))]
    [id (v) (if (symbol=? v sub-id) val expr)]
    [app (fun-name arg-expr)
         (app fun-name (subst arg-expr sub-id val))]))

;; interp : F1WAE listof(fundef) → number
;; Evalúa F1WAE
(define (interp expr fun-defs)
(type-case F1WAE expr
  [num (n) n]
  [add (l r) (+ (interp l fun-defs) (interp r fun-defs))]
  [with (bound-id named-expr bound-body)
        (interp (subst bound-body bound-id (num (interp named-expr fun-defs))) fun-defs)] ;Interpreta el resultado de substituir en el cuerpo de with (bound-body) la variable (bound-id) por la interpretación de la asignación de la variable (named-expr).
  [id (v) (error 'interp "free identifier" )]
  [app (fun-name arg-expr) ;aplicación de función
       (local ([define the-fun-def (lookup-fundef fun-name fun-defs)]) ;the-fun-def = La definición(nombre, parámetro y cuerpo) de la función cuyo nombre es "fun-name" de la lista "fun-defs"
         (interp (subst (fundef-body the-fun-def ) ;Interpreta el resultado de: En el cuerpo de la función (body)
                        (fundef-arg-name the-fun-def ) ;substituye el parámetro formal (arg-name) por
                        (num (interp arg-expr fun-defs))) ;el resultado de interpretar el argumento que recibe la función (arg-expr).
                 fun-defs))]))


;; lookup-fundef : symbol listof(FunDef) -→ FunDef
(define (lookup-fundef fun-name fundefs) ;Busca en una lista(fundefs) el nombre de fun-name y devuelve su definición.
  (cond
    [(empty? fundefs) (error fun-name "function not found")]
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