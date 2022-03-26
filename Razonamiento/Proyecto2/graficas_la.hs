module Graficas_la where

import qualified Prelude

data Bool =
   True
 | False

orb :: Bool -> Bool -> Bool
orb b1 b2 =
  case b1 of {
   True -> True;
   False -> b2}

data Nat =
   O
 | S Nat

data List a =
   Nil
 | Cons a (List a)

length :: (List a1) -> Nat
length l =
  case l of {
   Nil -> O;
   Cons _ l' -> S (length l')}

app :: (List a1) -> (List a1) -> List a1
app l m =
  case l of {
   Nil -> m;
   Cons a l1 -> Cons a (app l1 m)}

add :: Nat -> Nat -> Nat
add n m =
  case n of {
   O -> m;
   S p -> S (add p m)}

eqb :: Nat -> Nat -> Bool
eqb n m =
  case n of {
   O ->
    case m of {
     O -> True;
     S _ -> False};
   S n' ->
    case m of {
     O -> False;
     S m' -> eqb n' m'}}

hd :: a1 -> (List a1) -> a1
hd default0 l =
  case l of {
   Nil -> default0;
   Cons x _ -> x}

tl :: (List a1) -> List a1
tl l =
  case l of {
   Nil -> Nil;
   Cons _ m -> m}

map :: (a1 -> a2) -> (List a1) -> List a2
map f l =
  case l of {
   Nil -> Nil;
   Cons a t -> Cons (f a) (map f t)}

data Tpar =
   Par Nat Nat

tpar_rect :: (Nat -> Nat -> a1) -> Tpar -> a1
tpar_rect f t =
  case t of {
   Par x x0 -> f x x0}

tpar_rec :: (Nat -> Nat -> a1) -> Tpar -> a1
tpar_rec =
  tpar_rect

data Vertice =
   V Nat (List Nat)

vertice_rect :: (Nat -> (List Nat) -> a1) -> Vertice -> a1
vertice_rect f v =
  case v of {
   V x x0 -> f x x0}

vertice_rec :: (Nat -> (List Nat) -> a1) -> Vertice -> a1
vertice_rec =
  vertice_rect

type Grafica = List Vertice

elem :: Nat -> (List Nat) -> Bool
elem n l =
  case l of {
   Nil -> False;
   Cons x xs -> orb (eqb x n) (elem n xs)}

fst :: Tpar -> Nat
fst p =
  case p of {
   Par e1 _ -> e1}

snd :: Tpar -> Nat
snd p =
  case p of {
   Par _ e2 -> e2}

getId :: Vertice -> Nat
getId v =
  case v of {
   V id _ -> id}

getVecinos :: Vertice -> List Nat
getVecinos v =
  case v of {
   V _ vecinos -> vecinos}

getVpid :: Grafica -> Nat -> Vertice
getVpid g id =
  case g of {
   Nil -> V O Nil;
   Cons x xs ->
    case eqb (getId x) id of {
     True -> x;
     False -> getVpid xs id}}

creaPar :: (List Nat) -> Nat -> List Tpar
creaPar lista n =
  case lista of {
   Nil -> Nil;
   Cons x xs -> Cons (Par x n) (creaPar xs n)}

getOrden :: Grafica -> Nat
getOrden g =
  length g

getTamano :: Grafica -> Nat
getTamano g =
  case g of {
   Nil -> O;
   Cons x xs -> add (length (getVecinos x)) (getTamano xs)}

grado :: Vertice -> Nat
grado v =
  case v of {
   V _ vecinos -> length vecinos}

getParFst :: (List Tpar) -> Nat -> Tpar
getParFst lp n =
  case lp of {
   Nil -> Par O O;
   Cons x xs ->
    case eqb n (fst x) of {
     True -> x;
     False -> getParFst xs n}}

dfs' :: Nat -> Grafica -> (List Nat) -> (List Nat) -> List Nat
dfs' cs g pila visitados =
  case cs of {
   O -> visitados;
   S n ->
    case eqb (length pila) O of {
     True -> visitados;
     False ->
      case elem (hd O pila) visitados of {
       True -> dfs' n g (tl pila) visitados;
       False ->
        dfs' n g (app (getVecinos (getVpid g (hd O pila))) (tl pila))
          (app visitados (Cons (hd O pila) Nil))}}}

dfs :: Grafica -> List Nat
dfs g =
  dfs' (add (getTamano g) (getOrden g)) g (map getId g) Nil

bfs' :: Nat -> Grafica -> (List Nat) -> (List Nat) -> List Nat
bfs' cs g cola visitados =
  case cs of {
   O -> visitados;
   S n ->
    case eqb (length cola) O of {
     True -> visitados;
     False ->
      case elem (hd O cola) visitados of {
       True -> bfs' n g (tl cola) visitados;
       False ->
        bfs' n g (app (tl cola) (getVecinos (getVpid g (hd O cola))))
          (app visitados (Cons (hd O cola) Nil))}}}

bfs :: Grafica -> Nat -> List Nat
bfs g raiz =
  bfs' (add (getTamano g) (getOrden g)) g (Cons raiz Nil) Nil

bfs2' :: Nat -> Grafica -> (List Tpar) -> (List Tpar) -> List Tpar
bfs2' cs g cola visitados =
  case cs of {
   O -> visitados;
   S n ->
    case eqb (length cola) O of {
     True -> visitados;
     False ->
      case elem (fst (hd (Par O O) cola)) (map fst visitados) of {
       True -> bfs2' n g (tl cola) visitados;
       False ->
        bfs2' n g
          (app (tl cola)
            (creaPar (getVecinos (getVpid g (fst (hd (Par O O) cola)))) (S
              (snd (hd (Par O O) cola))))) (app visitados (Cons (hd (Par O O) cola) Nil))}}}

bfs2 :: Grafica -> Nat -> List Tpar
bfs2 g raiz =
  bfs2' (add (getTamano g) (getOrden g)) g (Cons (Par raiz O) Nil) Nil

ejemplo1 :: List Vertice
ejemplo1 =
  Cons (V (S O) (Cons (S (S O)) Nil)) (Cons (V (S (S O)) (Cons (S O) (Cons (S (S (S O))) Nil)))
    (Cons (V (S (S (S O))) (Cons (S (S O)) Nil)) Nil))

ejemplo2 :: List Vertice
ejemplo2 =
  Cons (V O (Cons (S O) (Cons (S (S O)) Nil))) (Cons (V (S O) (Cons O (Cons (S (S (S O)))
    Nil))) (Cons (V (S (S O)) (Cons O (Cons (S (S (S O))) Nil))) (Cons (V (S (S (S O))) (Cons
    (S O) (Cons (S (S O)) (Cons (S (S (S (S O)))) Nil)))) (Cons (V (S (S (S (S O)))) (Cons (S
    (S (S O))) (Cons (S (S (S (S (S O))))) (Cons (S (S (S (S (S (S O)))))) Nil)))) (Cons (V (S
    (S (S (S (S O))))) (Cons (S (S (S (S O)))) (Cons (S (S (S (S (S (S O)))))) Nil))) (Cons (V
    (S (S (S (S (S (S O)))))) (Cons (S (S (S (S O)))) (Cons (S (S (S (S (S O))))) Nil)))
    Nil))))))

ejemplo3 :: List Vertice
ejemplo3 =
  Cons (V (S O) (Cons (S (S O)) (Cons (S (S (S O))) Nil))) (Cons (V (S (S O)) (Cons (S O) (Cons
    (S (S (S (S O)))) Nil))) (Cons (V (S (S (S O))) (Cons (S O) (Cons (S (S (S (S (S O)))))
    Nil))) (Cons (V (S (S (S (S O)))) (Cons (S (S O)) (Cons (S (S (S (S (S O))))) Nil))) (Cons
    (V (S (S (S (S (S O))))) (Cons (S (S (S O))) (Cons (S (S (S (S O)))) Nil))) Nil))))

ejemplo4 :: List Vertice
ejemplo4 =
  Cons (V (S O) (Cons (S (S O)) Nil)) (Cons (V (S (S O)) Nil) Nil)

