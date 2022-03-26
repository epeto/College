
(*Definición de gráficas en Coq usando listas de adyacencias.*)

Require Import Utf8 List Nat Bool Omega.
Require Import Coq.Logic.Classical_Pred_Type.

Notation "[ x , .. , y ]" := (cons x .. (cons y nil) ..).

Module graficas.

Inductive Tpar : Set :=
  par : nat -> nat -> Tpar.
Notation "( x , y )" := (par x y).

Inductive Vertice : Set :=
  V : nat -> list nat -> Vertice.

Definition Grafica := list Vertice.

Fixpoint elem (n : nat) (l : list nat) : bool :=
match l with
  | nil => false
  | x::xs => (x =? n) || elem n xs
end.

Definition fst (p : Tpar) :=
match p with
  par e1 _ => e1
end.

Definition snd (p : Tpar) :=
match p with
  par _ e2 => e2
end.

(* Aquí se definen algunas funciones para el tipo Grafica ******************)

(*Recibe un vértice y devuelve su id.*)
Definition getId (v : Vertice) : nat :=
match v with
  V id _ => id
end.

(*Recibe un vértice y obtiene sus vecinos.*)
Definition getVecinos (v : Vertice) : list nat :=
match v with
  V _ vecinos => vecinos
end.

(*Recibe una gráfica, un id y devuelve el vértice asociado a ese id.*)
Fixpoint getVpid (g : Grafica) (id : nat) : Vertice :=
match g with
  | nil => V 0 nil
  | x::xs => if ((getId x) =? id)
             then x
             else getVpid xs id
end.

(*Recibe una lista de enteros, un número 'n' y devuelve una lista de pares: (_,n).*)
Fixpoint creaPar (lista : list nat) (n : nat) : list Tpar :=
match lista with
  | nil => nil
  | x::xs => (x,n)::(creaPar xs n)
end.

(*Decide el vértice u es adyacente a v*)
Definition esAdy (u v : Vertice) : Prop :=
In (getId u) (getVecinos v).

(*Define una gráfica simple (o no dirigida)*)
Definition esSimple (g : Grafica) : Prop :=
∀ (x y : Vertice), (In x g) ∧ (In y g) ∧ ¬(getId x = getId y) ∧ (esAdy x y) → (esAdy y x).

(*Obtiene el orden de una gráfica*)
Definition getOrden (g : Grafica) : nat := length g.

(*Obtiene el tamaño de una gráfica.
  En realidad el número es 2|E| si la gráfica es no dirigida.*)
Fixpoint getTamano (g : Grafica) : nat :=
match g with
  | nil => 0
  | x::xs => (length (getVecinos x)) + (getTamano xs)
end.

(*Obtiene el grado de un vértice.*)
Definition grado (v : Vertice) : nat :=
match v with
  V _ vecinos => length vecinos
end.

(*Decide si una secuencia de vértices es un camino*)
Fixpoint esCamino (c : list Vertice) : Prop :=
match c with
  | nil => True
  | (x::nil) => True
  | (x::xs) => (esAdy x (hd (V 0 nil) xs)) ∧ (esCamino xs)
end.

(*Decide si una secuencia de vértices es un camino
  de u a v en una gráfica g*)
Definition esCaminoDeUaV (c : list Vertice) (u v : Vertice) (g : Grafica) : Prop :=
(esCamino c) ∧
 (Forall (fun e => In e g) c) ∧
 (u = (hd (V 0 nil) c)) ∧
 (v = (last c (V 0 nil))).

(*Decide si un número d es la distancia de u a v en una gráfica g.
  La definición de distancia es el menor número de aristas que
  conectan a u con v.*)
Definition esDistancia (d : nat) (u v : Vertice) (g : Grafica) : Prop :=
∀ (cam : list Vertice), (esCaminoDeUaV cam u v g) → 
(d <=? ((length cam)-1)) = true.

(*Recibe una lista de pares, un número n y devuelve el par donde n
  coincida con la primera entrada.*)
Fixpoint getParFst (lp : list Tpar) (n : nat) : Tpar :=
match lp with
  | nil => (0,0)
  | x::xs => if n =? (fst x)
             then x
             else getParFst xs n
end.

(*Aquí se definen dos funciones para explorar gráficas:
  Búsqueda en profundidad, DFS
  Búsqueda en amplitud, BFS*)

(*Recibe una gráfica, una pila de vértices por visitar, una lista
  de vértices visitados y devuelve otra lista de vértices visitados.*)
Fixpoint dfs' (cs : nat) (g : Grafica) (pila : list nat) (visitados : list nat) : list nat :=
match cs with
  | 0 => visitados
  | S n => if ((length pila) =? 0)
           then visitados
           else if (elem (hd 0 pila) visitados)
                then dfs' n g (tl pila) visitados
                else dfs' n g ((getVecinos (getVpid g (hd 0 pila))) ++ (tl pila)) (visitados ++ ((hd 0 pila)::nil))
end.

(*Función que aplica búsqueda en profundidad a una gráfica y devuelve una lista 
 id's de vértices en el orden en el que fueron visitados.*)
Definition dfs (g : Grafica) : list nat := 
dfs' ((getTamano g)+(getOrden g)) g (map getId g) nil.

Fixpoint bfs' (cs : nat) (g : Grafica) (cola : list nat) (visitados : list nat) : list nat :=
match cs with
  | 0 => visitados
  | S n => if ((length cola) =? 0)
           then visitados
           else if (elem (hd 0 cola) visitados)
                then bfs' n g (tl cola) visitados
                else bfs' n g ((tl cola) ++ (getVecinos (getVpid g (hd 0 cola)))) (visitados ++ ((hd 0 cola)::nil))
end.

(*Función que aplica búsqueda en por amplitud a una gráfica y devuelve una lista 
 id's de vértices en el orden en el que fueron visitados.*)
Definition bfs (g : Grafica) (raiz : nat) : list nat :=
bfs' ((getTamano g)+(getOrden g)) g (raiz::nil) nil.

Fixpoint bfs2' (cs : nat) (g : Grafica) (cola : list Tpar) (visitados : list Tpar) : list Tpar :=
match cs with
  | 0 => visitados
  | S n => if ((length cola) =? 0)
           then visitados
           else if (elem (fst (hd (0,0) cola)) (map fst visitados))
                then bfs2' n g (tl cola) visitados
                else bfs2' n g ((tl cola) ++ (creaPar (getVecinos (getVpid g (fst (hd (0,0) cola)))) (S (snd (hd (0,0) cola))))) (visitados ++ ((hd (0,0) cola)::nil))
end.

(*Aplica búsqueda por amplitud a una gráfica pero calculando
  la distancia del vértice raíz a todos los demás.*)
Definition bfs2 (g : Grafica) (raiz : nat) : list Tpar :=
bfs2' ((getTamano g)+(getOrden g)) g ((raiz,0)::nil) nil.


(*Aplicación de las funciones a ejemplos*********************************)

(*2 aristas y 3 vertices*)
Definition ejemplo1 :=
  [V 1 [2],
   V 2 [1,3],
   V 3 [2]].

(*Un cuadrado y un triángulo unidos por un puente*)
Definition ejemplo2 :=
  [V 0 [1,2],
   V 1 [0,3],
   V 2 [0,3],
   V 3 [1,2,4],
   V 4 [3,5,6],
   V 5 [4,6],
   V 6 [4,5]].

(*Pentágono*)
Definition ejemplo3 :=
  [V 1 [2,3],
   V 2 [1,4],
   V 3 [1,5],
   V 4 [2,5],
   V 5 [3,4]].

Definition ejemplo4 :=
  [V 1 [2],
   V 2 nil].

Eval compute in dfs ejemplo1.
Eval compute in dfs ejemplo2.
Eval compute in dfs ejemplo3.

Eval compute in bfs ejemplo1 1.
Eval compute in bfs ejemplo2 0.
Eval compute in bfs ejemplo3 1.

Eval compute in bfs2 ejemplo1 2.
Eval compute in bfs2 ejemplo2 3.
Eval compute in bfs2 ejemplo3 4.

(*Demostración de que el ejemplo 1 es una gráfica simple*)
Lemma ej1_esSimple : esSimple ejemplo1.
Proof.
unfold esSimple.
intros.
destruct H.
destruct H0.
destruct H1.
simpl in H.
simpl in H0.
destruct H.

destruct H0.
rewrite <- H in H1.
rewrite <- H0 in H1.
simpl in H1.
contradiction.

destruct H0.
rewrite <- H.
rewrite <- H0.
unfold esAdy.
simpl.
left.
reflexivity.

destruct H0.
rewrite <- H in H2.
rewrite <- H0 in H2.
unfold esAdy in H2.
simpl in H2.
omega.

contradiction.

destruct H.

destruct H0.
rewrite <- H.
rewrite <- H0.
unfold esAdy.
simpl.
left.
reflexivity.

destruct H0.
rewrite <- H in H1.
rewrite <- H0 in H1.
simpl in H1.
contradiction.

destruct H0.
rewrite <- H.
rewrite <- H0.
unfold esAdy.
simpl.
right.
left.
reflexivity.

contradiction.

destruct H.

destruct H0.
rewrite <- H in H2.
rewrite <- H0 in H2.
unfold esAdy in H2.
simpl in H2.
omega.

destruct H0.
rewrite <- H.
rewrite <- H0.
unfold esAdy.
simpl.
left.
reflexivity.

destruct H0.
rewrite <- H in H1.
rewrite <- H0 in H1.
simpl in H1.
contradiction.

contradiction.

contradiction.
Qed.

(*El algoritmo bfs2 calcula de forma correcta la distancia de u a v.*)
Theorem bfs2Gdistancia : ∀ (u v : Vertice) (g : Grafica) (d : nat),
d = snd (getParFst (bfs2 g (getId u)) (getId v)) →
esDistancia d u v g.
Proof.
intros.
unfold snd in H.
unfold getParFst in H.
unfold bfs2 in H.
unfold bfs2' in H.
unfold esDistancia.
unfold esCaminoDeUaV.
unfold esCamino.
Admitted.
      
End graficas.

Extraction Language Haskell.
Extraction "graficas_la" graficas.

