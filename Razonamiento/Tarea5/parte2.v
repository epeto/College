
Require Import Utf8.
Require Import List.
Require Import Bool.
Require Import Classical.
Notation "[ x , .. , y ]" := (cons x .. (cons y nil) ..).

Parameters (A:Type)
           (equivalencia: (list A) → (list A) → Prop).

(* Definimos la función elem que recibe un elemento de tipo A y una lista
con elementos de tipo A.  Decide si el elemento es parte de la lista *)
Fixpoint elem (x:A) (l: list A)  :=
 match l with 
  | nil => False
  | (b::bs) => x = b ∨ elem x bs
 end.

Infix "≡" := equivalencia (at level 40).

Axiom conjunto: ∀ l1 l2 : list A, l1 ≡ l2 ↔ ∀ (x : A), (elem x l1 ↔ elem x l2).

Theorem reflexiva: ∀ l1 : list A,
                   l1 ≡ l1.
Proof.
intro.
rewrite conjunto.
intro.
reflexivity.
Qed.

Theorem simetrica: ∀ l1 l2 : list A,
                   (l1 ≡ l2) ↔ (l2 ≡ l1).
Proof.
intros.
unfold iff.
split.
- intro.
  rewrite conjunto.
  intro.
  rewrite conjunto in H.
  symmetry.
  apply H.
- intro.
  rewrite conjunto.
  intro.
  rewrite conjunto in H.
  symmetry.
  apply H.
Qed.

Theorem transitiva: ∀ l1 l2 l3 : list A,
                    ((l1 ≡ l2) ∧ (l2 ≡ l3)) → (l1 ≡ l3).
Proof.
intros.
destruct H.
rewrite conjunto in H.
rewrite conjunto in H0.
rewrite conjunto.
intro.
rewrite H.
apply H0.
Qed.

Example inciso_b: ∀ (l1 l2 : list A) (y : A),
                  (l1 ≡ l2) ∧ (elem y l1) → (elem y l2).
Proof.
intros.
destruct H.
rewrite conjunto in H.
apply H.
assumption.
Qed.

Example inciso_c: ∀ (l1 l2 : list A),
                  (l1 ≡ l2) → ∀ y, (cons y l1) ≡ (cons y l2).
Proof.
intros.
rewrite conjunto.
intro.
simpl.
rewrite conjunto in H.
rewrite H.
reflexivity.
Qed.

Example inciso_d: ∀ l2 : list A,
                  (nil ≡ l2) → (l2 = nil).
Proof.
intros.
rewrite conjunto in H.
simpl in H.
assert (∀ a:A, ¬ (In a nil)). apply in_nil.
unfold not in H0.
Admitted.
