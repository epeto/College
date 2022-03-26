Require Import Utf8.

Axiom classic : ∀ P: Prop, (P ∨ ¬P).

Theorem dist_not_exist : ∀(X:Type) (P:X → Prop), (∀ x, P x) → ¬(∃ x, ¬P x).
Proof.
intros.
unfold not.
intros.
destruct H0.
apply H0.
apply H.
Qed.

Theorem not_exists_dist: ∀ X P, ¬(∃x:X, ¬P x) → (∀x:X, P x).
Proof.
intros.
unfold not in H.
assert (P x ∨ ¬P x).
apply classic.
destruct H0.
- assumption.
- exfalso. apply H. exists x. apply H0.
Qed.

Example a: ∀P, ¬¬P → P.
Proof.
intros.
unfold not in H.
assert (P ∨ ¬P). apply classic.
destruct H0.
- assumption.
- exfalso. apply (H H0).
Qed.

Theorem peirce: ∀(P Q : Prop), ((P → Q) → P) → P.
Proof.
intros.
assert (P ∨ ¬P). apply classic.
destruct H0.
- assumption.
- apply H. intro. contradiction.
Qed.

Theorem de_morgan_not_and_not : ∀P Q:Prop, ¬(¬P ∧ ¬Q) → P ∨ Q.
Proof.
intros.
unfold not in H.
assert (P ∨ ¬P). apply classic.
destruct H0.
- left. assumption.
- right. assert (Q ∨ ¬Q). apply classic. destruct H1.
  + assumption.
  + exfalso. apply H. split; assumption.
Qed.

Theorem implies_to_or : ∀P Q:Prop, (P → Q) → (¬P ∨ Q).
Proof.
intros.
assert (P ∨ ¬P). apply classic.
destruct H0.
- right. apply (H H0).
- left. assumption.
Qed.
