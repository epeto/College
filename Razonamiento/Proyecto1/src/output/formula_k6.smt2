(declare-const ab1 Bool)
(declare-const ac1 Bool)
(declare-const ad1 Bool)
(declare-const ae1 Bool)
(declare-const af1 Bool)
(declare-const bc1 Bool)
(declare-const bd1 Bool)
(declare-const be1 Bool)
(declare-const bf1 Bool)
(declare-const cd1 Bool)
(declare-const ce1 Bool)
(declare-const cf1 Bool)
(declare-const de1 Bool)
(declare-const df1 Bool)
(declare-const ef1 Bool)
(declare-const ab2 Bool)
(declare-const ac2 Bool)
(declare-const ad2 Bool)
(declare-const ae2 Bool)
(declare-const af2 Bool)
(declare-const bc2 Bool)
(declare-const bd2 Bool)
(declare-const be2 Bool)
(declare-const bf2 Bool)
(declare-const cd2 Bool)
(declare-const ce2 Bool)
(declare-const cf2 Bool)
(declare-const de2 Bool)
(declare-const df2 Bool)
(declare-const ef2 Bool)
(push)
(assert
  (and
    (or ab1 ab2)
    (or ac1 ac2)
    (or ad1 ad2)
    (or ae1 ae2)
    (or af1 af2)
    (or bc1 bc2)
    (or bd1 bd2)
    (or be1 be2)
    (or bf1 bf2)
    (or cd1 cd2)
    (or ce1 ce2)
    (or cf1 cf2)
    (or de1 de2)
    (or df1 df2)
    (or ef1 ef2)
    (or (not ab1) (not ab2))
    (or (not ac1) (not ac2))
    (or (not ad1) (not ad2))
    (or (not ae1) (not ae2))
    (or (not af1) (not af2))
    (or (not bc1) (not bc2))
    (or (not bd1) (not bd2))
    (or (not be1) (not be2))
    (or (not bf1) (not bf2))
    (or (not cd1) (not cd2))
    (or (not ce1) (not ce2))
    (or (not cf1) (not cf2))
    (or (not de1) (not de2))
    (or (not df1) (not df2))
    (or (not ef1) (not ef2))
    (or (not ab1) (not ac1) (not bc1))
    (or (not ab2) (not ac2) (not bc2))
    (or (not ab1) (not ad1) (not bd1))
    (or (not ab2) (not ad2) (not bd2))
    (or (not ab1) (not ae1) (not be1))
    (or (not ab2) (not ae2) (not be2))
    (or (not ab1) (not af1) (not bf1))
    (or (not ab2) (not af2) (not bf2))
    (or (not ac1) (not ad1) (not cd1))
    (or (not ac2) (not ad2) (not cd2))
    (or (not ac1) (not ae1) (not ce1))
    (or (not ac2) (not ae2) (not ce2))
    (or (not ac1) (not af1) (not cf1))
    (or (not ac2) (not af2) (not cf2))
    (or (not ad1) (not ae1) (not de1))
    (or (not ad2) (not ae2) (not de2))
    (or (not ad1) (not af1) (not df1))
    (or (not ad2) (not af2) (not df2))
    (or (not ae1) (not af1) (not ef1))
    (or (not ae2) (not af2) (not ef2))
    (or (not bc1) (not bd1) (not cd1))
    (or (not bc2) (not bd2) (not cd2))
    (or (not bc1) (not be1) (not ce1))
    (or (not bc2) (not be2) (not ce2))
    (or (not bc1) (not bf1) (not cf1))
    (or (not bc2) (not bf2) (not cf2))
    (or (not bd1) (not be1) (not de1))
    (or (not bd2) (not be2) (not de2))
    (or (not bd1) (not bf1) (not df1))
    (or (not bd2) (not bf2) (not df2))
    (or (not be1) (not bf1) (not ef1))
    (or (not be2) (not bf2) (not ef2))
    (or (not cd1) (not ce1) (not de1))
    (or (not cd2) (not ce2) (not de2))
    (or (not cd1) (not cf1) (not df1))
    (or (not cd2) (not cf2) (not df2))
    (or (not ce1) (not cf1) (not ef1))
    (or (not ce2) (not cf2) (not ef2))
    (or (not de1) (not df1) (not ef1))
    (or (not de2) (not df2) (not ef2))))
(check-sat)
(get-model)
(pop)