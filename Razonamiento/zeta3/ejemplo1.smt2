(set-option :produce-proofs true)

; =========================
; Ejemplo 1: satisfacible
; { (p v ¬q) }
(declare-const p Bool)
(declare-const q Bool)




(push)

(assert (or p (not q)))

(check-sat)
(get-model)

(pop)


; =========================
; Ejemplo 2: insastisfacible 
; { (p v q), (p v ¬q), (¬p v q), (¬p v ¬q), p, q }
(push)

(assert 
  (and 
    (or p q) 
    (or p (not q)) 
    (or (not p) q) 
    (or (not p) (not q)) 
    p 
    q))

(check-sat)
(get-proof)

(pop)

