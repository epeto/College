(* Razonamiento automatizado 2020-2
   Selene Linares *)

Require Import Classical.
Require Import Utf8.

Parameters (p q r s u v C:Prop)
           (A: Set)
           (a z:A)
           (P Q S: A -> Prop).


Check classic.

Check classic u.

Check classic (u\/v).

Check NNPP.

Check NNPP v.

Check NNPP (u -> v).

Theorem edn: ~~p <-> p.
Proof.
unfold iff.
split.
exact (NNPP p).
intro.
unfold not.
intro.
apply (H0 H).
Qed.

Theorem ContraPos: (p -> q) <-> (~q -> ~p).
Proof.
unfold iff.
split.
intros.
contradict H0.
apply (H H0).
intros.
assert (q \/ ~q).
exact (classic q).
destruct H1.
assumption.
absurd (p).
apply (H H1).
assumption.
Qed.



Theorem TEDebil: ((p -> q) -> q) \/ (p->q).
Proof. 
assert((p->q) \/ ~(p->q)).
apply classic.
destruct H.
right.
assumption.
left.
intro.
exfalso.
apply (H H0).
Qed.


Lemma NegImpC: ~(p->q) -> p /\ ~ q.
Proof.
intro.
cut(~~p /\ ~q).
intro.
split.
destruct H0.
apply NNPP.
assumption.
destruct H0.
assumption.
cut(~(~p \/ q)).
intro.
apply (not_or_and (~p) q).
exact H0.
contradict H.
intro.
destruct H.
exfalso.
apply (H H0).
assumption.
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

Theorem Baco: exists x:A, P x -> forall y:A, P y.
Proof.
Admitted.
