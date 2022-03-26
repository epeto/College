(* Razonamiento automatizado 2020-2
   Selene Linares Arévalo
   Tarea 4, Tácticas básicas y negación clásica*)

Require Import Utf8.
Require Import Classical.

Variables p q r s t x l m:Prop.
Variables U:Type.
Variables P Q: U → Prop.
Variables R : U → U → Prop.
Variables a b : U.


Section LPyLPO.

Theorem DilemaC : (p → q) → (r → s) → p ∨ r → q ∨ s. 
Proof.
intros.
destruct H1.
- left. apply (H H1).
- right. apply (H0 H1).
Qed.

Theorem Distrib : p ∨ (q ∧ r) → (p ∨ q) ∧ (p ∨ r).
Proof.
intro.
split.
- destruct H.
  + left. assumption.
  + destruct H. right. assumption.
- destruct H.
  + left. assumption.
  + destruct H. right. assumption.
Qed.


Theorem Argumento1: ((x ∨ p) ∧ q → l) ∧
                    (m ∨ q → s ∧ t) ∧
                    ((s ∧ t) ∧ l → x) ∧
                    (p → q) →
                    (m ∧ p → x).
Proof.
intro.
destruct H.
destruct H0.
destruct H1.
intro.
apply H1.
destruct H3.
split.
- apply H0. left. assumption.
- apply H. split.
  + right. assumption.
  + apply H2. assumption.
Qed.


Theorem Socrates: (∀ x:U, P x → Q x) ∧ P a → Q a.
Proof.
intro.
destruct H.
apply H.
assumption.
Qed.


Theorem DistrExistsConj: (∃ x:U, P x ∧ Q x) → (∃ x:U, P x) ∧ (∃ x:U, Q x).
Proof.
intro.
destruct H.
destruct H.
split.
- exists x0. assumption.
- exists x0. assumption.
Qed.


Theorem Argumento2: (∀ y:U, P y → Q y) → (∀ x:U, (∃ y:U, P y ∧ R x y) → ∃ z:U, Q z ∧ R x z).
Proof.
intros.
destruct H0.
destruct H0.
exists x1.
split.
- apply H. assumption.
- assumption.
Qed.
End LPyLPO.


Section LogConst.

Theorem NegImp: (p → q) → (p → ¬q) → ¬p.
Proof.
unfold not.
intros.
apply H0.
- assumption.
- apply H. assumption.
Qed.

(* Este teorema me servirá para los siguientes ejercicios.*)
Theorem NNPP_inverso: ∀ p:Prop, p → ¬¬p.
unfold not.
intros.
apply (H0 H).
Qed.
(*Hasta aquí mi teorema*)

Theorem nonoTExc: ¬¬(p ∨ ¬p).
Proof.
apply NNPP_inverso.
exact (classic p).
Qed.

Theorem dmorganO : ¬ ( p ∨ q ) ↔ ¬p ∧ ¬q.
Proof.
unfold iff.
unfold not.
split.
- intro. split.
  + intro. apply H. left. assumption.
  + intro. apply H. right. assumption.
- intro. destruct H. intro. destruct H1.
  + apply H. assumption.
  + apply H0. assumption.
Qed.


Theorem Argumento3: (∀ x:U, P x → Q x) ∧
              (¬ ∃ x:U, P x ∧ Q x) →
              ¬ ∃ x:U, P x.
Proof.
unfold not.
intros.
destruct H.
apply H1.
destruct H0.
exists x0.
split.
- assumption.
- apply H. assumption.
Qed.

End LogConst.
