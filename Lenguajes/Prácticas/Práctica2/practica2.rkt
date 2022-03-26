#lang plai

;; A partir de aquí empieza Figuras

;Figuras tridimensionales
(define-type Figura
  [tetraedro (a number?)]
  [prisma (a number?) (P number?) (B number?) (h number?)]
  [paralelepipedo (a number?) (b number?) (c number?)]
  [piramide (P number?) (a number?) (B number?) (h number?)]
  [cilindro (g number?) (C number?) (B number?) (h number?)]
  [esfera (r number?)]
  [cono (g number?) (h number?) (r number?)]
  )

;Funcion area lateral.
(define (area-lateral fig)
  (type-case Figura fig
    [tetraedro (a) (error "No se puede calcular")]
    [prisma (a P B h) (* (P) (a))]
    [paralelepipedo (a b c) (* (* 2 (+ a b)) c)]
    [piramide (P a B h) (* (/ 1 2) (* P a))]
    [cilindro (g C B h) (* C g)]
    [esfera (r) (error "No se puede calcular")]
    [cono (g h r) (* pi (* r g))]
    )
  )

;Funcion area total.
(define (area-total fig)
  (type-case Figura fig
    [tetraedro (a) (* 1.7321 (expt a 2))]
    [prisma (a P B h) (+ (* P a) (* 2 B))]
    [paralelepipedo (a b c) (+ (* (* 2 (+ a b)) c) (* 2 (* a b)))]
    [piramide (P a B h) (+ (* (/ 1 2) (* P a)) B)]
    [cilindro (g C B h) (+ (* C g) (* 2 B))]
    [esfera (r) (* 4 (* pi (expt r 2)))]
    [cono (g h r) (+ (* pi (* r g)) (* pi (expt r 2)))]
    )
  )

;Funcion volumen.
(define (volumen fig)
  (type-case Figura fig
    [tetraedro (a) (* 0.1178 (expt a 3))]
    [prisma (a P B h) (* B h)]
    [paralelepipedo (a b c) (* a (* b c))]
    [piramide (P a B h) (* (/ 1 3) (* B h))]
    [cilindro (g C B h) (* B h)]
    [esfera (r) (* (/ 4 3) (* pi (expt r 3)))]
    [cono (g h r) (* (/ 1 3) (* pi (* (expt r 2) h)))]
    )
  )


;;A partir de aquí empieza árboles binarios ordenados.

(define (any? a) #t)

(define-type ABO
  [vacio]
  [nodo (a any?)
        (t1 ABO?)
        (t2 ABO?)
        (c procedure?)])

(define tree (nodo 1
                   (nodo -2 (nodo -4 (vacio) (vacio) <) (nodo 9 (nodo 3 (vacio) (vacio)<) (nodo 10 (vacio) (vacio) <) < ) < ) (nodo 7 (vacio) (vacio) <) <))


(define (agrega-abo a e)
  (match a
    [(vacio) (nodo e (vacio) (vacio) eq?)]
    [(nodo elm (vacio) (vacio) c) (if (c elm e)
                                      (nodo elm (vacio) (nodo e (vacio) (vacio) c) c)
                                      (nodo elm (nodo e (vacio) (vacio) c) (vacio) c))]
    [(nodo elm li (vacio) c) (if (c elm e)
                                      (nodo elm li (nodo e (vacio) (vacio) c) c)
                                      (nodo elm (agrega-abo li e) (vacio) c))]
    [(nodo elm (vacio) ld c) (if (c elm e)
                                      (nodo elm (vacio) (agrega-abo ld e) c)
                                      (nodo elm (nodo e (vacio) (vacio) c) ld c))]
    [(nodo elm li ld c) (if (c elm e)
                            (nodo elm li (agrega-abo ld e) c)
                            (nodo elm (agrega-abo li e) li c)
                            )]))

(define (numero-elms a)
  (match a
    [(vacio) 0]
    [(nodo elm li ld c) (+ 1 (numero-elms li) (numero-elms ld))]
   ))

(define (altura a)
  (match a
    [(vacio) -1]
    [(nodo elm li ld c) (+ 1 (max (altura li) (altura ld)))]
   ))
(define (inorder a)
(match a
  [(vacio) empty]
  [(nodo elm li ld c) (append (inorder li) (list elm) (inorder ld))]
 ))

(define (preorder a)
(match a
  [(vacio) empty]
  [(nodo elm li ld c) (append (list elm) (preorder li) (preorder ld))]
 ))

(define (postorder a)
(match a
  [(vacio) empty]
  [(nodo elm li ld c) (append (postorder li) (postorder ld)  (list elm))]
 ))


;;A partir de aquí empieza árboles binarios

;; TAD AB para construir árboles.
(define-type AB
   [hoja (a any?)]
   [mkt (t1 AB?) (t2 AB?)])

;; Predicado para agregar un elemento al árbol dado.
(define (agrega-ab a e)
  (match a
      [(hoja i) (mkt (hoja e) a)]
      [(mkt t1 t2) (mkt (hoja e) (mkt t1 t2))])) 

;; Predicado que regresa el número de hojas de un árbol.
(define (numero-hojas a)
  (match a
    [(hoja a) 1]
    [(mkt t1 t2) (+ (numero-hojas t1) (numero-hojas t2))]))

;; Predicado que regresa el número de nodos internos de un árbol.
(define (nodos-internos a)
  (match a
    [(hoja a) 0]
    [(mkt t1 t2) (add1 (nodos-internos t1) (nodos-internos t2))]))

;; Predicado que regresa una lista con la información de cada hoja del árbol en inorder.
(define (hojas a)
  (match a
    [(hoja a) (list a)]
    [(mkt t1 t2) (append (hojas t1) (hojas t2))]))

;; Función que aplica la función f a cada elemento de un árbol.
(define (map-a f a)
  (match a
    [(hoja a) (hoja (f a))]
    [(mkt t1 t2) (mkt (map-a f t1) (map-a f t2))]))

;; Función que regresa la profundidad de un árbol.
(define (profundidad a)
  (match a
    [(hoja a) 0]
    [(mkt t1 t2) (add1 (max (profundidad t1) (profundidad t2)))]))


;;A partir de aquí empieza arreglo

;;Tipo arreglo
(define-type Arreglo
	[arreglo (tipo procedure?) (dim number?) (elems list?)]
        [agrega-a (e any?) (a Arreglo?) (i number?)]
        [obten-a (a Arreglo?) (i number?)])

;; Verifica que todos los elementos de una lista sean de tipo ti
(define (revisa ti lista)
	(if (empty? lista)
		#t
		(and (ti (car lista)) (revisa ti (cdr lista)))))

;; Agrega un elemento e a una lista l en la posición i
(define (agr-lista e l i)
	(if (or (> i (length l)) (< i 0))
    (error "Índice inválido")
	(if (zero? i)
	   (cons e l)
	   (cons (car l) (agr-lista e (cdr l) (- i 1))))))

;;Verifica si dim es menor o igual a la longitud de la lista.
(define (long-cor dim lista)
	(<= dim (length lista)))

;;Verifica que un arreglo esté bien definido.
(define (bd t d e)
  (cond
      [(not (revisa t e)) (error "Los elementos no son del tipo especificado")]
      [(not (equal? d (length e))) (error "Dimensión inválida")]
      [else #t]))

;;Evalúa un tipo arreglo
(define (calc-a arr)
  (match arr
    [(arreglo t d e) (if (bd t d e)
                         (arreglo t d e)
                         (error "El arreglo no esa bien definido"))]
    [(agrega-a e (arreglo tipo dim elems) i) (arreglo tipo dim (agr-lista e elems i))]
    [(obten-a (arreglo tipo dim elems) i) (if (or (< i 0) (>= i dim))
                                             (error "Índice inválido")
                                             (list-ref elems i))]))