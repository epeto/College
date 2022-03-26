; k-coloración en gráficas
; Dada una gráfica con vértices V y aristas E y un natural k
; colorear cada vértice de tal forma que dos vértices
; conectados no tengan el mismo color y se usen a lo más
; k colores distintos



; Ejemplo:
; G = A -- B -- C
; k = 2
; A -> rojo
; C -> rojo
; B -> azul
; la gráfica es 2-coloreable

; Formalización:
;
; Podemos formalizar el problema para esta gráfica de la siguiente manera

; Variables
; p_vc - el vértice v tiene color c. 
; Una para cada vértice para cada color

(declare-const p_Ar Bool)
(declare-const p_Aa Bool)

(declare-const p_Br Bool)
(declare-const p_Ba Bool)

(declare-const p_Cr Bool)
(declare-const p_Ca Bool)

; Fórmulas:
; Cada vértice tiene algún color

(assert (or p_Ar p_Aa))
(assert (or p_Br p_Ba))
(assert (or p_Cr p_Ca))

; Ningún vértice tiene dos colores
(assert (not (and p_Ar p_Aa)))
(assert (not (and p_Br p_Ba)))
(assert (not (and p_Cr p_Ca)))

; Si dos vértices comparten una arista, no tienen el mismo color
(assert (not (and p_Ar p_Br)))
(assert (not (and p_Aa p_Ba)))

(assert (not (and p_Br p_Cr)))
(assert (not (and p_Ba p_Ca)))

(check-sat)
(get-model)
