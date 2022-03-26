(** An attempt to formalize graphs. *)

Require Import Arith.
Require Import Utf8.

(** In order to avoid the intricacies of constructive mathematics,
    we consider finite simple graphs whose sets of vertices are
    natural numbers 0, 1, , ..., n-1 and the edges form a decidable
    relation.  *)

(** We shall work a lot with statements of the form
    [forall i : nat, i < V -> P i], so we introduce a
    notatation for them, and similarly for existentials. *)
Notation "'all' i : V , P" := (forall i : nat, i < V -> P) (at level 20, i at level 99).
Notation "'some' i : V , P" := (exists i : nat, i < V /\ P) (at level 20, i at level 99).

Structure Graph := {
  V :> nat ; (* The number of vertices. So the vertices are numbers 0, 1, ..., V-1. *)
  E :> nat -> nat -> Prop ; (* The edge relation *)
  E_decidable : forall x y : nat, ({E x y} + {~ E x y}) ;
  E_irreflexive : all x : V, ~ E x x ;
  E_symmetric : all x : V, all y : V, (E x y -> E y x)
}.

(** Let us define some graphs. *)

(* Empty graph on [n] vertices *)
Definition Empty (n : nat) : Graph.
Proof.
  refine {| V := n ;
            E := (fun x y => False)
         |} ; auto.
Defined.

(* Complete graph on [n] vertices *)
Definition K (n : nat) : Graph.
Proof.
  refine {| V := n ;
            E := (fun x y => x <> y)
         |}.
  - intros.
    destruct (Nat.eq_dec x y) ; tauto.
  - intros x H L.
    absurd (x = x) ; tauto.
  - intros ; now apply not_eq_sym.
Defined.

(** Path on [n] vertices. *)
Definition Path (n : nat) : Graph.
Proof.
  (* We will do this proof very slowly by hand to practice. *)
  refine {| V := n ;
            E := fun x y => S x = y \/ x = S y
         |}.
  - intros.
    destruct (eq_nat_dec (S x) y).
    + tauto.
    + destruct (eq_nat_dec x (S y)).
      * tauto.
      * { right.
          intros [ ? | ? ].
          - absurd (S x = y) ; assumption.
          - absurd (x = S y) ; assumption.
        }
  - (* This case we do by using a powerful tactic "firstorder".
       We tell it to use the statement [n_Sn] from the [Arith] library.
       To see what [n_Sn] is, run command [Check n_Sn].
       To find [n_Sn], we ran the command [Search (?m <> S ?m)]. *)
    firstorder using n_Sn.
  - intros ? ? ? ? [?|?].
    + right ; now symmetry.
    + left; now symmetry.
Defined.

(** [Cycle n] is the cycle on [n+3] vertices. We define it in a way
    which avoids modular arithmetic. *)
Definition Cycle (n : nat) : Graph.
Proof.
  refine {| V := 3 + n ; (* Do not forget: we have [3+n] vertices 0, 1, ..., n+2 *)
            E := fun x y =>
                 (S x = y \/ x = S y \/ (x = 0 /\ y = 2 + n) \/ (x = 2 + n /\ y = 0))
         |}.
  - intros.
    destruct (eq_nat_dec (S x) y) ;
    destruct (eq_nat_dec x (S y)) ;
    destruct (eq_nat_dec x 0) ;
    destruct (eq_nat_dec y 0) ;
    destruct (eq_nat_dec x (2 + n)) ;
    destruct (eq_nat_dec y (2 + n)) ;
    tauto.
  - intros ? ? H.
    destruct H as [?|[?|[[? ?]|[? ?]]]].
    + firstorder using Nat.neq_succ_diag_l.
    + firstorder using Nat.neq_succ_diag_r.
    + apply (Nat.neq_succ_0 (S n)) ; transitivity x.
      * now symmetry.
      * assumption.
    + apply (Nat.neq_succ_0 (S n)) ; transitivity x.
      * now symmetry.
      * assumption.
  - intros ? ? ? ? [?|[?|[[? ?]|[? ?]]]].
    + right ; left ; now symmetry.
    + left ; now symmetry.
    + tauto.
    + tauto.
Defined.

(* We work towards a general theorem: the number of vertices with odd degree is odd. *)

(** Given a decidable predicate [P] on [nat], we can count how many numbers up to [n] satisfy [P]. *)
Definition count:
  forall P : nat -> Prop,
    (forall x, {P x} + {~ P x}) -> nat -> nat.
Proof.
  intros P D n.
  induction n.
  - exact 0.
  - destruct (D n).
    + exact (1 + IHn).
    + exact IHn.
Defined.

(** Given a function [f : nat -> nat] and number [n], compute
    the sum of [f 0, f 1, ..., f (n-1)]. *)
Fixpoint sum (f : nat -> nat) (n : nat) :=
  match n with
  | 0 => 0
  | S m => f m + sum f m
  end.

(** The number of edges in a graph. *)
Definition edges (G : Graph) : nat :=
  sum (fun x => sum (fun y => if E_decidable G x y then 1 else 0) x) (V G).

(* An example: calculate how many edges are in various graphs. *)
Eval compute in edges (Cycle 2). (* NB: This is a cycle on 5 vertices. *)
Eval compute in edges (K 5).
Eval compute in edges (Empty 10).

(** The degree of a vertex. We define it so that it
    return 0 if we give it a number which is not
    a vertex. *)
Definition degree (G : Graph) (x : nat) : nat.
Proof.
  destruct (lt_dec x (V G)) as [ Hx | ? ].
  - { apply (count (fun y => y < G /\ G x y)).
      - intro z.
        destruct (lt_dec z G) as [Hz|?].
        + destruct (E_decidable G x z).
          * left ; tauto.
          * right ; tauto.
        + right ; tauto.
      - exact (V G). }
  - exact 0.
Defined.

(* Let us compute the degree of a vertex. *)
Eval compute in degree (K 6) 4.
Eval compute in degree (Cycle 4) 0.
Eval compute in degree (Cycle 4) 2.

(* If we sum up all the degress we get twice the edges. *)
Theorem sum_degrees (G : Graph) : sum (degree G) (V G) = 2 * (edges G).
Proof.
  (* We give up and go to lunch. *)
Admitted.

  