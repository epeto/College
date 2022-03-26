Require Import Utf8.

Variables p q r s t u v: Prop.
Variables U: Type.
Variables a b c d e f g : U.
Variables P Q R S: U → Prop.

Example proj1: p ∧ q → p.
Proof.
intro.
destruct H.
assumption.
Qed.

Example proj2: p ∧ q → q.
Proof.
intro.
destruct H.
assumption.
Qed.

Example and_comm: p ∧ q → q ∧ p.
Proof.
intro.
split.
- apply proj2. assumption.
- apply proj1. assumption.
Qed.

Example or_comm: p ∨ q → q ∨ p.
Proof.
intro.
destruct H.
- right. assumption.
- left. assumption.
Qed.

Example or_distr_and_1: p ∨ (q ∧ r) → (p ∨ q) ∧ (p ∨ r).
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

Example or_distr_and_2: (p ∨ q) ∧ (p ∨ r) → p ∨ (q ∧ r).
Proof.
intro.
destruct H.
destruct H.
- left. assumption.
- destruct H0.
  + left. assumption.
  + right. split; assumption.
Qed.

Example or_distr_iff: (p ∨ q) ∧ (p ∨ r) ↔ p ∨ (q ∧ r).
Proof.
split.
- apply or_distr_and_2.
- apply or_distr_and_1.
Qed.

Example not_true: ¬ False.
Proof.
unfold not.
intro. assumption.
Qed.

Example not_true_false: ¬(p ∧ ¬p).
Proof.
unfold not.
intro.
destruct H.
apply (H0 H).
Qed.

Example contrapositiva: (p → q) → (¬q → ¬p).
Proof.
unfold not.
intros.
apply (H0 (H H1)).
Qed.

Example ex_P: P a → (∃ x, P x).
Proof.
intro.
exists a.
assumption.
Qed.

Example not_ex_P: ¬(∃ x, P x) → ¬ P a.
Proof.
unfold not.
intros.
apply H.
exists a.
assumption.
Qed.

Example contrapositive_all: ∀ (P Q : Prop), (P → Q) → (¬Q → ¬P).
Proof.
unfold not.
intros.
apply H0.
apply H.
assumption.
Qed.

