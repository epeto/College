#lang plai

(require "streams.rkt")

;; Predicado para definir arreglos con elementos de cualquier tipo.
;; any?: any? -> boolean
(define (any? a) #t)

;; TDA para construir arreglos.
;; Se tienen dos constructores, uno para definir entradas vacías en el arreglo y otro para construir
;; arreglos con un tipo, dimensión y los elementos que son un stream (lista infinita o perezosa).
(define-type Arreglo
   [nulo]
   [arreglo (tipo any?) (dim integer?) (elementos Stream?)])

;; Procedimiento que genera una sucesión de nulos. 
;; genera-nulos: void -> Stream
(define (genera-nulo)
  (scons (nulo) (λ() (genera-nulo))))

;; Función que crea un arreglo vacío con un tipo y dimensión.
;; Regresa un arreglo cuyas entradas son (nulo).
;; crea: procedure integer -> Arreglo
(define (crea tipo dim)
      (arreglo tipo dim (crea-s dim (genera-nulo))))

;; Función que crea un stream de la longitud dada.
;; Regresa un stream cuyas entradas son (nulo).
;; crea-s: integer Stream -> Stream
(define (crea-s n s)
  (match n
    [0 (sempty)]
    ;[1 (cons (shead s) (sempty))]
    [else (scons (shead s) (λ() (crea-s (sub1 n) (stail s))))]))

;; Función que agrega un elemento en el índice indicado.
;; La función verifica que el tipo a agregar sea del tipo correspondiente y que el índice no esté 
;; fuera de rango.
;; Esta función no incrementa el stream asociado sino que reemplaza el valor actual del arreglo en
;; la posición indicada por el nuevo elemento.
;; agrega: Arreglo integer any -> Arreglo
(define (agrega arr ind elm)
   (if (and ((arreglo-tipo arr) elm) (> (arreglo-dim arr) ind))
       (arreglo (arreglo-tipo arr) (arreglo-dim arr) (agrega-s (arreglo-elementos arr) ind elm))
       (error 'agrega "Hubo un error. :c")))

;; Agrega un elemento dado en un stream en la posición indicada.
;; agrega-s Stream integer any -> Stream
(define (agrega-s s n e)
  (match s
    ;[(sempty) (scons e (thunk (sempty)))]
    [(scons h t) (match n
                   [0 (scons e t)]
                   [else (scons h (λ() (agrega-s (stail s) (sub1 n) e)))])]))
   
;; Función que obtiene el elemento del arreglo en la posición ind.
;; La función verifica que el índice no esté fuera de rango.
;; obten: Arreglo integer -> any
(define (obten arr ind)
   (if (> (arreglo-dim arr) ind)
       (obten-elem (arreglo-elementos arr) ind)
       (error 'obten "El índice está fuera de rango.")))

;; Procedimiento que obtiene el n-ésimo elemento de la sucesión stream.
;; obten-elem: Stream integer -> any
(define (obten-elem s n)
   (match n
       [0 (shead s)]
       [else (obten-elem (stail s) (sub1 n))]))

;; Función que imprime un arreglo en la consola.
;; Se imprimen con formato: [e1|e2|e3|...|en]. Si un elemento es nulo, deja la entrada vacía.
;; imprime: Arreglo -> void
(define (imprime arr)
   (display (string-append "[" (if (equal? arr (nulo))
                                   (error 'imprime "Arreglo nulo.")
                                   (imprimeme-esta (arreglo-dim arr) (arreglo-elementos arr))) "]")))

;; Función auxiliar que toma los números de un stream.
;; imprimeme-esta: integer Stream -> string
(define (imprimeme-esta n s)
  (match s
    [(sempty) ""]
    [(scons h t) (cond
                   [(= n 1) (if (number? h) (string-append (number->string h)) "")]
                   [(number? h) (string-append (number->string h) "| " (imprimeme-esta (sub1 n) (t)))]
                   [else (string-append " | " (imprimeme-esta (sub1 n) (t)))])]))