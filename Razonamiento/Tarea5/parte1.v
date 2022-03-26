
Require Import Utf8.
Require Import Classical.

(* Teoremas auxiliares. *)

Theorem NNPP_inverso: ∀ p:Prop, p → ¬¬p.
unfold not.
intros.
apply (H0 H).
Qed.

Theorem deMorgan1 (A: Type) (P: A → Prop):
  ¬(∃ x: A, P x) → (∀ x: A, ¬(P x)).
Proof.
  intros.
  unfold not.
  intro.
  apply H.
  exists x.
  assumption.
Qed.

Theorem deMorgan2 (A: Type) (P: A→Prop):
  (∀ x: A, ¬(P x)) → ¬(∃ x: A, P x).
Proof.
  intros.
  unfold not.
  intro.
  destruct H0.
  apply H in H0.
  assumption.
Qed.

Theorem deMorgan3 (A: Type) (P: A→Prop):
  (∃ x: A, ¬(P x)) → ¬(∀ x: A, P x).
Proof.
  intros.
  unfold not.
  intro.
  destruct H.
  apply H.
  apply H0.
Qed.

Theorem deMorgan4 (A: Type) (P: A→Prop):
  ¬(∀ x: A, P x) → (∃ x: A, ¬(P x)).
Proof.
  intro.
  apply NNPP.
  intro.
  apply H.
  intro.
  apply NNPP.
  intro.
  apply H0.
  exists x.
  assumption.
Qed.

(* Fin de teoremas auxiliares. *)

Example uno: ∀ p q r s:Prop,
             (p → q) ∧ 
             (¬(p ∨ r) → s) ∧
             (p ∨ q → r) → 
             (¬s → r).
Proof.
intros.
destruct H. destruct H1.
assert (¬p ∨ q). apply imply_to_or. assumption.
assert (¬(¬(p ∨ r)) ∨ s). apply imply_to_or. assumption.
assert (¬(p ∨ q) ∨ r). apply imply_to_or. assumption.
destruct H5. assert (¬p ∧ ¬q). apply not_or_and. assumption.
- destruct H4.
  + assert (p ∨ r). apply NNPP. assumption. destruct H7.
    * destruct H6. contradiction.
    * assumption.
  + contradiction.
- assumption.
Qed.

Example dos: ∀ p q r s:Prop,
             (p ∧ ¬ q) ∧ 
             (p → ¬q) → 
             (¬(p → q) ∧ (q → ¬p)).
Proof.
intros.
destruct H.
split.
assert (¬(¬p ∨ q)).
apply and_not_or.
split.
destruct H.
apply NNPP_inverso.
assumption.
destruct H.
assumption.
- unfold not.
  intro.
  destruct H.
  apply (H0 H).
  apply (H2 H).
- intro.
  destruct H.
  contradiction.
Qed.
  
Example tres: ∀ (A : Type) (B P R : A → Prop) (a : A),
              (∀ x, P x → ¬ B x) → 
              (R a → (∀ x, R x → B x) → ¬ (P a)).
Proof.
intros.
unfold not.
intro.
assert (B a ∨ ¬ B a). apply classic.
destruct H3.
- apply (H a).
  + assumption.
  + assumption.
- unfold not in H3. apply H3. apply H1. assumption.
Qed.

Example cuatro: ∀ (A : Type) (P Q R S : A → Prop),
                ((∃x, P x ∧ ¬ Q x) → (∀ y, (P y → R y))) ∧ 
                (∃ x, P x ∧ S x) ∧
                (∀ x, P x → ¬ R x) →
                (∃ x, (S x ∧ Q x)).
Proof.
intros.
destruct H.
destruct H0.
destruct H0 as [a].
exists a.
split.
destruct H0. assumption.
assert (Q a ∨ ¬ Q a). apply classic.
destruct H2.
- assumption.
- assert (∀ x, ¬ P x ∨ ¬ R x). intro. apply imply_to_or. apply H1.
  assert (¬(∃ x, P x ∧ ¬ Q x) ∨ (∀ y, (P y → R y))). apply imply_to_or. assumption.
  destruct H0.
  destruct H4.
  + exfalso. apply H4. exists a. split.
    * assumption.
    * assumption.
  + exfalso. apply (H1 a).
    * assumption.
    * apply H4. assumption.
Qed.



 
