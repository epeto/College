(* Razonamiento Automatizado 2020-2
   Selene Linares
   Razonamiento sobre estructuras de datos: Árboles de búsqueda
   De: Software foundations - Verified Functional Algorithms *)

Require Import Utf8.
Require Import Nat.
Require Import List.
Require Import Omega.

Section TREES.
Variable V : Type.
Variable default : V.

(*Los identificadores de los nodos son de tipo nat*)
Definition key := nat.

(*Definición inductiva de árbol binario*)
Inductive tree : Type :=
 | E : tree
 | T : tree → key → V → tree → tree.

Definition empty_tree : tree := E.

(*Función que dada una llave y un árbol, regresa el valor asociado a esa llave*)
Fixpoint lookup (x:key) (t:tree) : V :=
  match t with
    | E => default
    | T ti k v td => if (x<?k) then lookup x ti
                     else if(k<?x) then lookup x td
                          else v
  end.

(*Función que inserta un nuevo nodo con llave x y valor v en árbol s*)
Fixpoint insert (x:key) (v:V) (s:tree) : tree :=
  match s with
    | E => T E x v E
    | T l y v' r => if (x<?y) then T (insert x v l) y v' r
                    else if (y<?x) then T l y v' (insert x v r)
                         else T l y v' r
  end.

(*Función que regresa una lista con todos los nodos (k,v) de un árbol*)
Fixpoint elements (s:tree) : list (key*V) :=
  match s with
    | E => nil
    | T l k v r => (elements l) ++ ((k,v)::(elements r))
  end.

(*########################################################################*)
(*Ejemplos*)
Section EXAMPLES.
Variables v1 v2 v3 v4 v5 v6 v7 v8 v9 v10 v11: V.
Eval compute in insert 5 v5 (insert 2 v2 (insert 4 v5 empty_tree)).
     (* T (T E 2 v2 E) 4 v5 (T E 5 v5 E) *)
Eval compute in lookup 5 (T (T E 2 v2 E) 4 v5 (T E 5 v5 E)).
     (* v5 *)
Eval compute in lookup 3 (T (T E 2 v2 E) 4 v5 (T E 5 v5 E)).
     (* default *)
Eval compute in elements (T (T E 2 v2 E) 4 v5 (T E 5 v5 E)).
     (* [(2,v2), (4,v5), (5,v5)] *)
End EXAMPLES.
(*#################################################################*)
(*Invariantes*)
Fixpoint forall_nodes (t:tree) (P: tree→key→V→tree→Prop) : Prop :=
  match t with
    | E => True
    | T l k v r => P l k v r ∧ forall_nodes l P ∧ forall_nodes r P
  end.

Definition SearchTreeX (t:tree) :=
  forall_nodes t
    (fun l k v r =>
      forall_nodes l (fun _ j _ _ => j<k) ∧
      forall_nodes r (fun _ j _ _ => j>k)).

Definition example_tree (v2 v4 v5 : V) :=
  T (T E 2 v2 E) 4 v4 (T E 5 v5 E).

Lemma example_SearchTree_good:
  ∀ v2 v4 v5, SearchTreeX (example_tree v2 v4 v5).
Proof.
intros.
unfold SearchTreeX.
simpl.
repeat split;auto.
Qed.

Lemma example_SearchTree_bad:
  ∀ v, ¬SearchTreeX (T (T E 3 v E) 2 v E).
Proof.
intro.
intro.
unfold SearchTreeX in H.
simpl in H.
do 3 destruct H.
omega.
Qed.

Theorem e_SearchTreeX : SearchTreeX empty_tree.
Proof.
unfold SearchTreeX.
simpl.
trivial.
Qed.

Theorem t_SearchTreeX : ∀ x v t, SearchTreeX t → SearchTreeX (insert x v t).
Proof.
intros.
induction t.
- unfold insert.
  unfold SearchTreeX.
  simpl.
  auto.
- simpl.
  case(x<?k).
  +
Admitted.

(*Segunda versión*)

Inductive SearchTree' : key → tree → key → Prop :=
| ST_E : ∀ lo hi, lo <= hi → SearchTree' lo E hi
| ST_T : ∀ lo l k v r hi,
    SearchTree' lo l k →
    SearchTree' (S k) r hi →
    SearchTree' lo (T l k v r) hi.

Inductive SearchTree: tree → Prop :=
| ST_intro: ∀ t hi, SearchTree' 0 t hi → SearchTree t.

Variables v1 v2 v3 v4 v5 v6 v7 v8 v9 v10 v11 : V.
Definition example_tree2 :=
  T (T (T E 1 v1 E) 3 v3 (T E 5 v5 E)) 6 v6 (T (T E 9 v9 E) 8 v8 (T E 10 v10 E)).

Lemma example_SearchTree_good2: SearchTree (example_tree2).
Proof.
apply ST_intro with 11.
apply ST_T.
- apply ST_T.
  + apply ST_T.
    * apply ST_E. omega.
    * apply ST_E. omega.
  + apply ST_T.
    * apply ST_E. omega.
    * apply ST_E. omega.
- apply ST_T.
  + apply ST_T.
    * apply ST_E. omega.
    * apply ST_E. (*falso*)
Admitted.

Theorem empty_tree_SearchTree: SearchTree empty_tree.
Proof.
apply ST_intro with 1.
apply ST_E. omega.
Qed.

Variable n : nat.

Theorem insert_SearchTree:
  ∀ k v t, SearchTree t → k<n → SearchTree (insert k v t).
Proof.
intros.
induction t.
- apply ST_intro with n.
  simpl.
  apply ST_T.
  + apply ST_E. omega.
  + apply ST_E. omega.
- simpl.
  case(k<?k0).
  + apply ST_intro with n.
    apply ST_T.
