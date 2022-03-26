(declare-const ab1 Bool)
(declare-const ac1 Bool)
(declare-const bc1 Bool)
(declare-const bd1 Bool)
(declare-const be1 Bool)
(declare-const de1 Bool)
(declare-const ce1 Bool)
(declare-const cf1 Bool)
(declare-const ef1 Bool)
(declare-const ab2 Bool)
(declare-const ac2 Bool)
(declare-const bc2 Bool)
(declare-const bd2 Bool)
(declare-const be2 Bool)
(declare-const de2 Bool)
(declare-const ce2 Bool)
(declare-const cf2 Bool)
(declare-const ef2 Bool)
(push)
(assert
  (and
    (or ab1 ab2)
    (or ac1 ac2)
    (or bc1 bc2)
    (or bd1 bd2)
    (or be1 be2)
    (or de1 de2)
    (or ce1 ce2)
    (or cf1 cf2)
    (or ef1 ef2)
    (or (not ab1) (not ab2))
    (or (not ac1) (not ac2))
    (or (not bc1) (not bc2))
    (or (not bd1) (not bd2))
    (or (not be1) (not be2))
    (or (not de1) (not de2))
    (or (not ce1) (not ce2))
    (or (not cf1) (not cf2))
    (or (not ef1) (not ef2))
    (or (not ab1) (not ac1) (not bc1))
    (or (not ab2) (not ac2) (not bc2))
    (or (not bc1) (not be1) (not ce1))
    (or (not bc2) (not be2) (not ce2))
    (or (not bd1) (not be1) (not de1))
    (or (not bd2) (not be2) (not de2))
    (or (not ce1) (not cf1) (not ef1))
    (or (not ce2) (not cf2) (not ef2))))
(check-sat)
(get-model)
(pop)