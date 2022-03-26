Require Import Utf8.
Require Import List.

Module MyStack.

Variable V : Type.

Definition stack := list V.

Definition empty : stack := nil.

Definition is_empty (s : stack) : bool :=
  match s with
  | nil => true
  | _::_ => false
  end.

Definition push (x : V) (s : stack) : stack :=
  x::s.

Definition peek (s : stack) : option V :=
  match s with
  | nil => None
  | x::_ => Some x
  end.

Definition pop (s : stack) : option (stack) :=
  match s with
  | nil => None
  | _::xs => Some xs
  end.

Definition size (s : stack) : nat :=
  length s.

Theorem empty_is_empty : is_empty empty = true.
Proof.
simpl.
reflexivity.
Qed.

Theorem push_not_empty : ∀ (x:V) (s:stack), is_empty (push x s) = false.
Proof.
intros.
simpl.
reflexivity.
Qed.

Theorem peek_empty : peek empty = None.
Proof.
simpl.
reflexivity.
Qed.

Theorem peek_push : ∀ (x:V) (s:stack), peek (push x s) = Some x.
Proof.
intros.
simpl.
reflexivity.
Qed.

Theorem pop_empty : pop empty = None.
Proof.
simpl.
reflexivity.
Qed.

Theorem pop_push : ∀ (x:V) (s:stack), pop (push x s) = Some s.
Proof.
intros.
simpl.
reflexivity.
Qed.

Theorem size_empty : size empty = 0.
Proof.
simpl.
reflexivity.
Qed.

Theorem size_push : ∀ (x:V) (s:stack), size (push x s) = 1+(size s).
Proof.
intros.
simpl.
reflexivity.
Qed.

Definition ejemplo (v1 v2 v3 : V) := cons v1 (cons v2 (cons v3 nil)).

End MyStack.

Extraction Language Haskell.
Extraction "mystack" MyStack.