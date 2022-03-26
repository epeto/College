
Require Import Utf8.
Require Import List.
Require Import Nat.

Fixpoint sum_list (l : list nat) : nat :=
  match l with
  | nil => 0
  | x :: l' => x + sum_list l'
  end.

Example my_sum1 : sum_list (1::2::3::nil) = 6.
Proof.
  simpl. reflexivity.
Qed.

Example my_sum2 : sum_list nil = 0.
Proof.
  simpl. reflexivity.
Qed.

Example my_sum3 : sum_list (1::nil) = 1.
Proof.
  simpl. reflexivity.
Qed.

Theorem sum_list_nat_gt_0 : ∀ l : list nat, 0 ≤ sum_list l.
Proof.
intros l.
induction l.
- simpl. reflexivity.
- simpl. case a.
  + simpl. exact IHl.
  + intros n. rewrite IHl. exact (Plus.le_plus_r (S n) (sum_list l)).
Qed.

Extraction Language Haskell.
Recursive Extraction sum_list.
