Require Import Utf8.

Variables p q r s t u:Prop.
Variable U : Set.
Variable P Q: U -> Prop.
Variable R : U -> U -> Prop.


Theorem Example1: (p -> q \/ r)->(q -> r)-> (r -> s) -> (p -> s).
Proof.
intro.
intro.
intro.
intro.
(* intros. *)
apply H1.
assert (q \/ r).
apply H.
assumption.
destruct H3.
apply H0.
assumption.
assumption.
Qed.


Goal forall p q r: Prop, ((p\/q)/\(p\/r))-> (p \/ (q/\r)).
Proof.
intros.
destruct H.
destruct H.
left.
assumption.
destruct H0.
left.
assumption.
right.
split.
assumption.
assumption.
Qed.




Theorem Example3 : (forall v:U, P(v)->Q(v)) -> (forall x:U, (exists y:U, P(y) /\ (R x y)) -> (exists z:U, Q(z)/\ (R x z))).
Proof.
intro.
intro.
intro.
destruct H0.
exists x0.
destruct H0.
split.
apply H.
assumption.
assumption.
Qed.


Theorem pr0_083: (p -> q /\ r) -> (r \/ ~q -> s /\ t) -> (t <-> u) -> (p -> u).
Proof.
intros.
destruct H1.
assert (s /\ t).
apply H0.
left.
assert (q /\ r).
apply H.
trivial.
destruct H4.
assumption.
apply H1.
destruct H4.
assumption.
Qed.
