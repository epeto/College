#lang plai

(require "grammars.rkt")
(require "parser.rkt")
(require "interp.rkt")

(print-only-errors)

(define expr1
	'{rec {
		   {fac ; Función que calcula el factorial de un número
		      {fun {n}
		         {if {zero? n}
		             1
		             {* n {fac {- n 1}}}}}}

         ; Definición del número
		   {x 5}}

		; Usamos el número en el cuerpo del rec
		{fac x}})

(define expr2
	'{rec {

		   {longitud ; Función que calcula la longitud de una lista
      		{fun {l} 
      		   {if {empty? l} 
      		       0 
      		       {+ 1 {longitud {tail l}}}}}}
	       
	      {suma ; Función que suma los elementos de una lista
	       	{fun {l}
	       		{if {empty? l}
	       	       0
	       	       {+ {head l} {suma {tail l}}}}}}

         ; Definición de la lista
	      {lista {cons 1 {cons 2 empty}}}}
	    
	   ; Usamos la lista en el cuerpo del rec 
	   {+ {longitud lista} {suma lista}}})


(define expr3
	'{rec {

		   {concatena ; Función que concatena dos listas
		      {fun {l1 l2}
		         {if {empty? l1}
		             l2
		             {cons {head l1} {concatena {tail l1} l2}}}}}

		   {reversa ; Función que encuentra la reversa
		      {fun {l}
		         {if {empty? l}
		             l
		             {concatena {reversa {tail l}} {cons {head l} empty}}}}}

         ; Lista [1,2,3,4,5]
		   {lista {cons 1 {cons 2 {cons 3 {cons 4 {cons 5 empty}}}}}}}

      ; Usamos la lista en el cuerpo del rec. Reversa usa concatena.
		{reversa lista}})

(test (interp (desugar (parse expr1)) (mtSub)) (numV 120))
(test (interp (desugar (parse expr2)) (mtSub)) (numV 5))
(test (interp (desugar (parse expr3)) (mtSub)) (listV (consV (numV 5)
	                                           (listV (consV (numV 4)
	                                           (listV (consV (numV 3)
	                                           (listV (consV (numV 2)
	                                           (listV (consV (numV 1)
	                                           (listV (emptyV)))))))))))))

