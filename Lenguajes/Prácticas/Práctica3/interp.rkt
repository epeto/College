#lang plai

(require "grammars.rkt")
(require "parser.rkt")
;; Función auxiliar que obtiene los id's de una lista de bindings

(define (list-id bindings)
  (match bindings
    ['() '()]
  [l (cons
    (binding-name (car bindings))
    (list-id (cdr bindings)))]))

;; Función auxiliar que obtiene los WAE's de una lista de bindings

(define (list-WAE bindings)
  (match bindings
    ['() '()]
  [l (cons
    (binding-value (car bindings))
    (list-WAE (cdr bindings)))]))

;; Función auxiliar que dice si un elemento está en una lista

(define (in? e lst)
  (match lst
    ['() #f]
    [l (if (symbol=? (car lst) e)
           #t
           (in? e (cdr lst)))]))

;; Función auxiliar que realiza una sustitución en
;; los respectivos cuerpos de una lista de bindings
;; new-bindings symbol -> WAE -> [Binding] -> [Binding]
(define (new-bindings sub-id val list)
  (match list
    ['() '()]
    [l (cons (binding (binding-name (car l))
                      (subst (binding-value (car l)) sub-id val))
             (new-bindings sub-id val (cdr l)))]))

;; Función que substituye cuando es necesario
;; subst: WAE -> symbol -> WAE -> WAE

(define (subst sexp sub-id val)
  (match sexp
    [(id i) (if (symbol=? i sub-id) val sexp)]
    [(num n) sexp]
;; Aquí se crea una lambda que se usa para reducir los parametros 
;; del subst y poder usar el map de forma sencilla
    [(op fun args) (op fun (map (lambda (exp)
                                  (subst exp sub-id val
                           )) args))]
    [(with bindings body-with)
     (if (in? sub-id (list-id bindings))
         (with
                (new-bindings sub-id val bindings)
                body-with)
         (with
                (new-bindings sub-id val bindings)
                (subst body-with sub-id val)))]
    [(with* bindings body-with)
     (if (in? sub-id (list-id bindings))
         (with*
                (new-bindings sub-id val bindings)
                body-with)
         (with*
                (new-bindings sub-id val bindings)
                (subst body-with sub-id val)))]))
;; Función encargada de interpretar el árbol de sintaxis abstracta generado por el parser.
;; interp: WAE -> any
(define (interp exp)
   (match exp
     [(id i) (error 'interp "Variable libre >:O")]
     [(num n) n]
     [(op f args) (apply f (map interp args))]
     [(with bindings body-with) (interp (foldl (lambda (bin exp)
                                         (subst exp
                                                (binding-name bin)
                                                (num (interp (binding-value bin)))))
                                       body-with
                                       bindings))]
     [(with* bindings body-with) (interp (foldr (lambda (bin exp)
                                         (subst exp
                                                (binding-name bin)
                                                (binding-value bin)))
                                       body-with
                                       bindings))]))
(define ejemplo
(foldr (lambda (bin exp)
         (subst exp
                (binding-name bin)
                (binding-value bin)))
       (op + (list (id 'a) (id 'b)))
       (list (binding 'a (num 1)) (binding 'b (op + (list (id 'a) (id 'a)))))))