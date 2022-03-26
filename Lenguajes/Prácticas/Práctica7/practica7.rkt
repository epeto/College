#lang plai

(require "grammars.rkt")
(require "parser.rkt")
(require "interp.rkt")
(require "verificador.rkt")

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


;; Función auxiliar para imprimir el valor de un tipo.
(define (imprime-tipo t)
	(match t
		[(a) "a"]
		[(tnumber) "num"]
		[(tboolean) "bool"]
		[(tlistof tipo) (string-append "listof " (imprime-tipo tipo))]
		[(tarrow arg result) (string-append (imprime-tipo arg) " -> " (imprime-tipo result))]))


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
               (let ([tipo (typeof (parse x))]
               	   [result (interp (desugar (parse x)) (mtSub))])
                  (cond
                    [(numV? result) (display (string-append (imprime-tipo tipo) ": "  (number->string (numV-n result))))]
                    [(boolV? result)
                      (if (boolV-b result)
                          (display (string-append (imprime-tipo tipo) ": true"))
                          (display (string-append (imprime-tipo tipo) ": false")))]
                    [(listV? result) 
                       (if (emptyV? (listV-l result))
                           (display (string-append (imprime-tipo tipo) ": []"))
                           (display (string-append (imprime-tipo tipo) ": [" (imprime-lista result) "]")))]
                    [else (display "#<function>")]))) 
            (display "\n")
            (ejecuta)))))

;; Llamada a la función
(display "Bienvenido a BRCFBWAEL v1.0.\n")
(ejecuta)
