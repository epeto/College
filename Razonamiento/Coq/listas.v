(* Razonamiento Automatizado 2020-2
   Selene Linares
   Razonamiento sobre estructuras de datos: listas *)


Require Import List.
Require Import Bool.
Notation "[ x , .. , y ]" := (cons x .. (cons y nil) ..).

(*Definición de listas en Datatypes.v*)

Parameters (A:Type)
           (a b z:A)
           (l l1 l2 : list A).

(* Definimos la función elem que recibe un elemento de tipo A y una lista
con elementos de tipo A.  Decide si el elemento es parte de la lista *)
Fixpoint elem (x:A) (l: list A)  :=
 match l with 
  | nil => False
  | (b::bs) => x = b \/ elem x bs
 end.

(** Ejercicio 1 **)
Theorem elem_eq : elem a (a :: l).
Proof.
simpl.
left.
reflexivity.
Qed.

(** Ejercicio 2 **)
Theorem elem_nil : forall a:A, ~ elem a nil.
Proof.
intro.
intro.
simpl in H.
assumption.
Qed.


(** Ejercicio 3 **)
Theorem elem_conc : forall (a : A) (la lb : list A), elem a (la++lb) -> elem a la \/ elem a lb.
Proof.
intros.
induction la.
- right.
  simpl in H.
  assumption.
-  simpl.
   simpl in H.
  destruct H.
  + left.
    left.
    assumption.
  + rewrite or_assoc.
    right.
    apply (IHla H).
Qed.


(** Definimos la función longitud que dada una lista regresa el
número de elementos que almacena **)
Fixpoint longitud (l : list A) : nat :=
 match l with
   | nil => 0
   | (_::ls) => 1+ longitud (ls)
 end.

(** Definimos la función rev que dada una lista regresa otra lista
con los elementos de la primera pero en orden inverso *)
Fixpoint rev (l:list A): list A :=
 match l with
  | nil => nil
  | (x::xs) => rev xs ++ [x]
 end.

Lemma longCon: forall (l1 l2: list A), longitud (l1++l2) = longitud l1 + longitud l2.
Proof.
intros.
induction l3.
- simpl.
  reflexivity.
- simpl.
  rewrite <- IHl3.
  reflexivity.
Qed.

Lemma aux: forall (m : nat), m+1 = S m.
Proof.
intros.
induction m.
reflexivity.
simpl.
rewrite IHm.
reflexivity.
Qed.

(** Ejercicio 4 **)
Theorem long_rev : forall (la : list A), longitud(rev la) = longitud la.
Proof.
intros.
induction la.
- simpl.
  reflexivity.
- simpl.
  rewrite longCon.
  rewrite IHla.
  simpl.
  rewrite aux.
  reflexivity.
Qed.


