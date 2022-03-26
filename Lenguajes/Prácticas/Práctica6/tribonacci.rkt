#lang plai

;; Función que calcula el n-ésimo número de Tribonacci.
;; tribonacci: number -> number
(define (tribonacci n)
	(cond
		[(< n 2) 0]
		[(= n 2) 1]
		[else
			(+ (tribonacci (- n 1))
			   (tribonacci (- n 2))
			   (tribonacci (- n 3)))]))

;; Función que calcula el n-ésimo número de Tribonacci.
;; tribonacci: number -> number
(define (tribonacci-cola n)
	(tribo/cola n 0 1 1))

(define (tribo/cola n acc1 acc2 acc3)
  (cond
    [(< n 2) acc1]
    [(= n 2) acc2]
    [else (tribo/cola (- n 1) acc2 acc3 (+ (+ acc1 acc2) acc3))]))
         
;; Función que calcula el n-ésimo número de Tribonacci.
;; tribonacci: number -> number
(define (tribonacci-cps n)
	(tribo/k n (lambda(x)x)))

(define (tribo/k n k)
  (cond
    [(< n 2) (k 0)]
    [(= n 2) (k 1)]
    [else (tribo/k (- n 1) (lambda(v1)
                             (tribo/k (- n 2)
                                 (lambda(v2)
                                   (tribo/k (- n 3)
                                     (lambda(v3)
                                       (k (+ (+ v1 v2) v3))))))))]))

;; Función que calcula el n-ésimo número de Tribonacci.
;; tribonacci: number -> number
(define hash (make-hash (list
            (cons 0 0)
            (cons 1 0)
            (cons 2 1))))

(define (tribonacci-memo n)
	(let ([resultado (hash-ref hash n 'ninguno)])
          (cond
            [(equal? resultado 'ninguno)
               (define valor (+ (tribonacci (- n 1)) (+ (tribonacci (- n 2)) (tribonacci (- n 3)))))
                 (hash-ref hash n valor)
                 valor]
            [else resultado])))


