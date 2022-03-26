Require Import Utf8.

Variables
  (R: Set)
  (O: R)
  (U: R)
  (op1: R → R → R)
  (op2: R → R → R)
  (i: R → R).

Infix "⊓" := op1 (at level 40).
Infix "⊔" := op2 (at level 40).

Axiom or_neutro: ∀ x: R, x ⊔ O = x.
Axiom or_inv: ∀ x, x ⊔ (i x) = O.
Axiom or_comm: ∀ x y, x ⊔ y = y ⊔ x.
Axiom or_assoc: ∀ x y z, x ⊔ (y ⊔ z) = (x ⊔ y) ⊔ z.
Axiom and_neutro: ∀ x, x ⊓ U = x.
Axiom and_neutro_2: ∀ x, U ⊓ x = x.
Axiom and_assoc: ∀ x y z, x ⊓ (y ⊓ z) = (x ⊓ y) ⊓ z.
Axiom and_distr_or: ∀ x y z, x ⊓ (y ⊔ z) = (x ⊓ y) ⊔ (x ⊓ z).
Axiom and_distr_or_2: ∀ x y z, (x ⊔ y) ⊓ z = (x ⊓ z) ⊔ (y ⊓ z).

Lemma or_inv_2: ∀ x, (i x) ⊔ x = O.
Proof.
intro.
rewrite or_comm. rewrite or_inv.
reflexivity.
Qed.

Example uno: ∀ x, O ⊔ x = x.
Proof.
intro.
rewrite or_comm. rewrite or_neutro.
reflexivity.
Qed.

Lemma or_nothing: ∀ x y, x = x ⊔ (y ⊔ (i y)).
Proof.
intros.
rewrite or_inv. rewrite or_neutro.
reflexivity.
Qed.

Example dos: ∀ x y z, x ⊔ z = y ⊔ z → x = y.
Proof.
intros.
rewrite (or_nothing x z).
rewrite (or_nothing y z).
rewrite or_assoc.
rewrite or_assoc.
congruence.
Qed.

Example tres: ∀ x y z, z ⊔ x = z ⊔ y → x = y.
Proof.
intros.
rewrite (or_comm z x) in H.
rewrite (or_comm z y) in H.
apply (dos x y z).
assumption.
Qed.

Example cuatro: ∀ x, i (i x) = x.
Proof.
intros.
rewrite (or_nothing (i (i x)) x).
rewrite or_comm with x (i x).
rewrite or_assoc with (i (i x)) (i x) x.
rewrite or_inv_2. rewrite uno.
reflexivity.
Qed.

