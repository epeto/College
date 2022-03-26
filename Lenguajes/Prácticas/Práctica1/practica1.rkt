#lang plai

#| Lenguajes de Programación, 2017-2
   Práctica 1 - Fundamentos de Racket
   Profesora: Karla Ramírez Pulido
   Ayudante: José Ricardo Rodríguez Abreu
   Ayud.Lab: Manuel Soto Romero |#

;; Función que toma dos números enteros y los eleva a sí mismos para luego sumar
;; las potencias, es decir, debe regresar a^b + b^a.
;; pot-sum: number number -> number
(define (pot-sum a b)
   (+ (expt a b) (expt b a)))

;; Función que recibe un número y regresa su valor absoluto.
;; absoluto: number -> number
(define (absoluto n)
   (if (< n 0)
       (- n)
       n))

;; Predicado que recibe una cadena e indica si se encuentra en el lenguaje de 
;; todas las cadenas {a,b} tales que tienen dos letras a al inicio o al final.
;; pertenece?: string -> boolean
(define (pertenece? s)
  (define l (string-length s))
   (or (equal? "aa" (substring s 0 2))
       (equal? "aa" (substring s (- l 2) l))))

;; Función recursiva que calcula la división de dos números por restas 
;; sucesivas.
;; division: number number -> number
(define (division a b)
   (if (> b a)
       0
       (+ 1 (division (- a b) b))))

;; Dada una cadena, la “limpia”, es decir, si hay caracteres adyacentes repetidos, debe dejar una única aparición.
(define (limpia-cadena s)
  (define cabeza (substring s 0 1))
  (define l (string-length s))
  (define cola (substring s 1 l))
   (if (> l 1)
       (if (equal? cabeza (substring cola 0 1))
           (limpia-cadena cola)
           (string-append cabeza (limpia-cadena cola)))
       s))

;;Función que recibe una lista de caracteres y verifica si el último caracter es '0'
(define (pimp l)
  (match l
    [(list a) (if (equal? #\0 a)
                  "Par"
                  "Impar")]
    [else (pimp (rest l))]))

;; Función recursiva que dada una cadena que representa un número binario para 
;; que responde si el número representado por la cadena es par o impar.
;; par-impar: string -> string
(define (par-impar s)
  (pimp (string->list s)))

;;Función que recibe un número y lo convierte a su equivalente en binario en una cadena de caracteres
(define (numtobin n)
  (if (equal? 0 n)
      ""
      (string-append (numtobin (quotient n 2)) ((lambda (k)
                                                  (if (zero? k)
                                                      "0"
                                                      "1")) (modulo n 2)))))

;; Función que dada una lista de números, regresa otra con la representación 
;; binaria de cada uno de ellos.
;; binarios: (listof number) -> (listof string)
(define (binarios l)
   (map numtobin l))

;; Función que recibe una lista de cadenas y regresa una lista de pares de la 
;; forma (s,k) donde k es la longitud de cada cadena. La lista final no debe 
;; tener elementos repetidos.
;; longitudes: (listof string) -> (listof pair)
(define (longitudes l)
   (map (lambda (s)
     (cons s (string-length s))) l))

;; Función que calcula la reversa de una lista usando foldr.
;; reversar: list -> list
(define (reversar l)
  (foldr (lambda (v l)
           (append l (list v))) '() l))

;; Función que calcula la reversa de una lista usando foldl.
;; reversal: list -> list
(define (reversal l)
   (foldl cons '() l))

;; Función recursiva que agrega un elemento al final de la lista.
;; agrega-final: list any -> list
(define (agrega-final l e)
   (if (empty? l)
       (list e)
       (cons (car l) (agrega-final (cdr l) e))))

;; Función recursiva que calcula la suma de los elementos de una lista.
;;sumlis: list->number
(define (sumalis l)
  (if (empty? l)
      0
      [+ (car l) (sumalis (cdr l))]))

  ;; Función recursiva que calcula el promedio de una lista.
;; promedio: list -> number
(define (promedio l)
  (define tam (length l))
  (/ (sumalis l) tam))

;; Función recursiva que obtiene el i-ésimo elemento de una lista.
;; obten: number list -> any
(define (obten i l)
  (define tam (length l))
  (if (> i tam)
      (error "Excediste el tamaño de la lista.")
      (match i
        [0 (car l)]
        [else (obten (- i 1) (cdr l))])))

;;Función que devuelve el elemento más pequeño de una lista
;;emp: list->number
(define (emp l)
  (match l
    ['() "La lista está vacía"]
    [(list x) x]
    [(cons x xs) (if (< x (emp xs))
                     x
                     (emp xs))]))

;; Función que recibe un número, una lista y devuelve los primeros k elementos.
(define (kprim k l)
  (if (> k (length l))
     (error "El número es más grande que el tamaño de la lista")
     (match k
       [0 '()]
       [1 (cons (car l) empty)]
       [else (cons (car l) (kprim (- k 1) (cdr l)))])))

;; Función recursiva que selecciona los primeros n elementos de una lista,
;; donde n es el elemento más pequeño de la lista.
;; n-primeros: (listof number) -> number
(define (n-primeros l)
  (kprim (emp l) l))

;Función que borra todas las apariciones del elemento e en una lista l
;borrae: list -> list
(define (borrae e l)
  (if (empty? l)
      empty
      (if (equal? (first l) e)
          (borrae e (rest l))
          (cons (first l) (borrae e (rest l))))))

;; Función recursiva que toma una lista como parámetro y regresa otra lista con
;; los elementos que aparecen una única vez en la original
;; unica-vez: list -> list
(define (unica-vez l)
  (if (empty? l)
      empty
      (if (member (first l) (rest l))
          (unica-vez (borrae (first l) (rest l)))
          (cons (first l) (unica-vez (rest l))))))


;; AGREGA AQUÍ LA DEFINICIÓN DE LAS FUNCIONES EXPRESADAS COMO COMBINACIÓN DE
;; EXPRESIONES LET Y LAMBDAS

;Función que dice quién es mayor
(let ([a 1834] [b 1729])
  (if (> a b)
      a
      b))

;Función que calcula la suma del 1 al 100
(letrec ([sumgauss (lambda (n)
                    (if (equal? 0 n)
                        0
                        (+ n (sumgauss (- n 1)))))])
  (sumgauss 100))

