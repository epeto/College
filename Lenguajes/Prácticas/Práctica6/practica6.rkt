#lang plai

(require "grammars.rkt")
(require "parser.rkt")
(require "interp.rkt")

;; Función auxiliar para imprimir los elementos de una lista.
;; Sólo se muestran listas de números.
;; imprime-lista: (listof listV) -> string
(define (imprime-lista l)
   (match (listV-l l)
      [(emptyV) ""]
      [(consV x xs)
         (if (emptyV? (listV-l xs))
             (number->string (numV-n x))
             (string-append (number->string (numV-n x)) "," (imprime-lista xs)))]))

;; Función encargada de ejecutar el intérprete para que el usuario interactúe con el lenguaje. Para
;; diferenciar el prompt de Racket del nuestro, usamos "(λ)". Aprovechamos los aspectos imperativos
;; del lenguaje para esta función.
;; ejecuta: void
(define (ejecuta)
   (begin
      (display "(λ) ")
      (define x (read))
      (if (equal? x '{exit})
          (display "")
          (begin 
            (with-handlers ([exn:fail? (lambda (exn) (display "Ocurrió un error al interpretar"))])
               (let ([result (interp (desugar (parse x)) (mtSub))])
                  (cond
                    [(numV? result) (display (numV-n result))]
                    [(boolV? result)
                      (if (boolV-b result)
                          (display "true")
                          (display "false"))]
                    [(listV? result) 
                       (if (emptyV? (listV-l result))
                           (display "[]")
                           (display (string-append "[" (imprime-lista result) "]")))]
                    [else (display "#<function>")]))) 
            (display "\n")
            (ejecuta)))))

;; Llamada a la función
(display "Bienvenido a RCFBWAEL v1.0.\n")
(ejecuta)
