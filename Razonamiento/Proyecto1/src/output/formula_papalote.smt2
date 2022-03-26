(declare-const ab1 Bool)
(declare-const ac1 Bool)
(declare-const bc1 Bool)
(declare-const bd1 Bool)
(declare-const cd1 Bool)
(declare-const de1 Bool)
(declare-const ef1 Bool)
(declare-const fg1 Bool)
(declare-const ab2 Bool)
(declare-const ac2 Bool)
(declare-const bc2 Bool)
(declare-const bd2 Bool)
(declare-const cd2 Bool)
(declare-const de2 Bool)
(declare-const ef2 Bool)
(declare-const fg2 Bool)
(push)
(assert
  (and
    (or ab1 ab2)
    (or ac1 ac2)
    (or bc1 bc2)
    (or bd1 bd2)
    (or cd1 cd2)
    (or de1 de2)
    (or ef1 ef2)
    (or fg1 fg2)
    (or (not ab1) (not ab2))
    (or (not ac1) (not ac2))
    (or (not bc1) (not bc2))
    (or (not bd1) (not bd2))
    (or (not cd1) (not cd2))
    (or (not de1) (not de2))
    (or (not ef1) (not ef2))
    (or (not fg1) (not fg2))
    (or (not ab1) (not ac1) (not bc1))
    (or (not ab2) (not ac2) (not bc2))
    (or (not bc1) (not bd1) (not cd1))
    (or (not bc2) (not bd2) (not cd2))))
(check-sat)
(get-model)
(pop)