--Emmanuel Peto Gutiérrez
--414008117
--Correo: empg014@gmail.com

{-
   Lógica computacional 2017-1
      Noé Salomón Hernández Sánchez
      Albert M. Orozco Camacho
      C. Moisés Vázquez Reyes
      Diego Murillo Albarrán
      José Roberto Piche Limeta

   Práctica 1
-}

--INTERPRETACIONES
import Data.List
data Form = T | F | Var String | Neg Form | Conj Form Form | Disy Form Form | Imp Form Form | DImp Form Form deriving Eq
type Estado = [String]

miCuenta::Int
miCuenta = 414008117

instance Show Form where
   --Atómicas
   show T = "⊤"
   show F = "⊥"
   --Variables
   show (Var x) = x
   --Negación
   show (Neg T) = "¬⊤"
   show (Neg F) = "¬⊥"
   show (Neg v@(Var x)) = "¬"++show v
   show (Neg f@(Neg p)) = "¬"++show f
   show (Neg p) = "¬("++show p++")"
   --Conjunción
   show (Conj v1@(Var p) v2@(Var q)) = show v1++" ∧ "++show v2
   show (Conj f1 v2@(Var q)) = case f1 of 
                               Neg f -> show f1++" ∧ "++show v2
                               _ -> "("++show f1++") ∧ "++show v2
   show (Conj v1@(Var p) f2) = case f2 of 
                               Neg f -> show v1++" ∧ "++show f2
                               _ -> show v1++" ∧ ("++show f2++")"
   show (Conj n1@(Neg f1) n2@(Neg f2)) = show n1++" ∧ "++show n2
   show (Conj n1@(Neg f1) f2) = show n1++" ∧ ("++show f2++")"
   show (Conj f1 n2@(Neg f2)) = "("++show f1++") ∧ "++show n2
   show (Conj f1 f2) = "("++show f1++") ∧ ("++show f2++")"
   --Disyunción
   show (Disy v1@(Var p) v2@(Var q)) = show v1++" ∨ "++show v2
   show (Disy f1 v2@(Var q)) = case f1 of 
                               Neg f -> show f1++" ∨ "++show v2
                               _ -> "("++show f1++") ∨ "++show v2
   show (Disy v1@(Var p) f2) = case f2 of 
                               Neg f -> show v1++" ∨ "++show f2
                               _ -> show v1++" ∨ ("++show f2++")"
   show (Disy n1@(Neg f1) n2@(Neg f2)) = show n1++" ∨ "++show n2
   show (Disy n1@(Neg f1) f2) = show n1++" ∨ ("++show f2++")"
   show (Disy f1 n2@(Neg f2)) = "("++show f1++") ∨ "++show n2
   show (Disy f1 f2) = "("++show f1++") ∨ ("++show f2++")"
   --Implicación
   show (Imp v1@(Var p) v2@(Var q)) = show v1++" ⟶ "++show v2
   show (Imp f1 v2@(Var q)) = case f1 of 
                               Neg f -> show f1++" ⟶ "++show v2
                               _ -> "("++show f1++") ⟶ "++show v2
   show (Imp v1@(Var p) f2) = case f2 of 
                               Neg f -> show v1++" ⟶ "++show f2
                               _ -> show v1++" ⟶ ("++show f2++")"
   show (Imp n1@(Neg f1) n2@(Neg f2)) = show n1++" ⟶ "++show n2
   show (Imp n1@(Neg f1) f2) = show n1++" ⟶ ("++show f2++")"
   show (Imp f1 n2@(Neg f2)) = "("++show f1++") ⟶ "++show n2
   show (Imp f1 f2) = "("++show f1++") ⟶ ("++show f2++")"
   --Doble implicación
   show (DImp v1@(Var p) v2@(Var q)) = show v1++" ⟷ "++show v2
   show (DImp f1 v2@(Var q)) = case f1 of 
                               Neg f -> show f1++" ⟷ "++show v2
                               _ -> "("++show f1++") ⟷ "++show v2
   show (DImp v1@(Var p) f2) = case f2 of 
                               Neg f -> show v1++" ⟷ "++show f2
                               _ -> show v1++" ⟷ ("++show f2++")"
   show (DImp n1@(Neg f1) n2@(Neg f2)) = show n1++" ⟷ "++show n2
   show (DImp n1@(Neg f1) f2) = show n1++" ⟷ ("++show f2++")"
   show (DImp f1 n2@(Neg f2)) = "("++show f1++") ⟷ "++show n2
   show (DImp f1 f2) = "("++show f1++") ⟷ ("++show f2++")"                                                  

--Función que toma un estado y evalúa una fórmula proposicional.
interp::Estado->Form->Bool 
interp _ T = True
interp _ F = False
interp e (Var p) = elem p e
interp e (Neg f) = not (interp e f)
interp e (Conj f1 f2) = (interp e f1) && (interp e f2)
interp e (Disy f1 f2) = (interp e f1) || (interp e f2)
interp e (Imp f1 f2) = (not (interp e f1)) || (interp e f2)
interp e (DImp f1 f2) = (interp e f1) == (interp e f2)

--Devuelve todas las variables de una fórmula proposicional sin repeticiones.
vars::Form->[String]
vars T = []
vars F = []
vars (Var p) = [p]
vars (Neg f) = vars f
vars (Conj f1 f2) = union (vars f1) (vars f2)
vars (Disy f1 f2) = union (vars f1) (vars f2)
vars (Imp f1 f2) = union (vars f1) (vars f2)
vars (DImp f1 f2) = union (vars f1) (vars f2)

--Función que recibe una lista y devuelve el conjunto potencia de esta.
potencia :: [a] -> [[a]]
potencia [] = [[]]
potencia (x:xs) = (potencia xs) ++ (agrEl x (potencia xs))
		where
		agrEl _ [] = []
		agrEl e (x:xs) = [e:x] ++ (agrEl e xs)

--Dada una fórmula proposicional, la función devuelve todos los estados con los que podemos evaluar la fórmula.
estados::Form->[Estado]
estados f = potencia (vars f)

--Nos dice si una fórmula es tautologı́a.
tautologia::Form->Bool
tautologia f = tauto' (estados f) f
		where
		tauto' [x] f = (interp x f)
		tauto' (x:xs) f = (interp x f) && tauto' xs f


--TABLEUAX SEMÁNTICOS

data Tableaux = Void | R1 [Form] Tableaux | R2 Tableaux Tableaux | Tache | Bolita deriving Show
   
--Esta función recibe una lista de fórmulas y comprueba que todas sean literales.
sonLit::[Form]->Bool
sonLit [] = True
sonLit (x:xs) = case x of
	T -> sonLit xs
	F -> sonLit xs
	Var p -> sonLit xs
	Neg T -> sonLit xs
	Neg F -> sonLit xs
	Neg (Var p) -> sonLit xs
	otherwise -> False

--Esta función recibe una lista de literales y comprueba si tiene 2 complementarias.
litComp::[Form]->Bool
litComp [] = False
litComp ((Neg x):xs) = if elem x xs then True else litComp xs
litComp (x:xs) = if elem (Neg x) xs then True else litComp xs

--Esta función decide si una fórmula es literal.
esLit::Form->Bool
esLit T = True
esLit F = True
esLit (Var p) = True
esLit (Neg T) = True
esLit (Neg F) = True
esLit (Neg (Var p)) = True
esLit _ = False

--Esta función inserta una fórmula en una lista de fórmulas antes de encontrar la primera literal.
insertaDisy::Form->[Form]->[Form]
insertaDisy f [] = [f]
insertaDisy f (x:xs) = if (esLit x) then f:(x:xs) else x:(insertaDisy f xs)

--Esta función inserta una fórmula en su orden correspondiente: primero las conjunciones, luego las disyunciones y al final las literales.
insertaord::Form->[Form]->[Form]
insertaord T l = l++[T]
insertaord F l = l++[F]
insertaord (Var p) l = l++[Var p]
insertaord (Neg T) l = l++[Neg T]
insertaord (Neg F) l = l++[Neg F]
insertaord (Neg (Var p)) l = l++[Neg (Var p)]
insertaord (Neg f) l = (Neg f):l
insertaord (Conj f1 f2) l = (Conj f1 f2):l
insertaord (Disy f1 f2) l = insertaDisy (Disy f1 f2) l
insertaord (Imp f1 f2) l = insertaDisy (Imp f1 f2) l
insertaord (DImp f1 f2) l = (DImp f1 f2):l

--Esta función expande un tableaux.
expande::Tableaux->Tableaux
expande (R1 (f:fs) Void) = if sonLit (f:fs) then 
			if litComp (f:fs) then R1 (f:fs) Tache else R1 (f:fs) Bolita
		       else
		case f of
		T -> expande (R1 (insertaord T fs) Void)
		F -> expande (R1 (insertaord F fs) Void)
		(Var p) -> expande (R1 (insertaord (Var p) fs) Void)
		(Neg T) -> expande (R1 (insertaord (Neg T) fs) Void)
		(Neg F) -> expande (R1 (insertaord (Neg F) fs) Void)
		(Neg (Var p)) -> expande (R1 (insertaord (Neg (Var p)) fs) Void)
		(Conj f1 f2) -> R1 (f:fs) (expande (R1 (insertaord f1 (insertaord f2 fs)) Void))
		(Disy f1 f2) -> R2 (expande (R1 (insertaord f1 fs) Void)) (expande (R1 (insertaord f2 fs) Void))
		(Imp f1 f2) -> R2 (expande (R1 (insertaord (Neg f1) fs) Void)) (expande (R1 (insertaord f2 fs) Void))
		(DImp f1 f2) -> R1 (f:fs) (expande (R1 (insertaord (Imp f1 f2) (insertaord (Imp f2 f1) fs)) Void))
		(Neg (Neg f1)) -> expande (R1 (insertaord f1 fs) Void)
		(Neg (Disy f1 f2)) -> R1 (f:fs) (expande (R1 (insertaord (Neg f1) (insertaord (Neg f2) fs)) Void))
		(Neg (Imp f1 f2))-> R1 (f:fs) (expande (R1 (insertaord f1 (insertaord (Neg f2) fs)) Void))
		(Neg (Conj f1 f2))-> R2 (expande (R1 (insertaord (Neg f1) fs) Void)) (expande (R1 (insertaord (Neg f2) fs) Void))
		(Neg (DImp f1 f2))-> R2 (expande (R1 (insertaord (Imp f1 f2) fs) Void)) (expande (R1 (insertaord (Imp f2 f1) fs) Void))

--Crea un tableaux a partir de una lista de fórmulas proposicionales.
creaTableaux::[Form]->Tableaux
creaTableaux l = expande (R1 l Void)

--Indica si un tableaux tiene tache en todas sus hojas.
cerrado::Tableaux->Bool
cerrado Void = False
cerrado Tache = True
cerrado Bolita = False
cerrado (R1 l t) = cerrado t
cerrado (R2 t1 t2) = (cerrado t1) && (cerrado t2)

--Indica si una fórmula es tautología usando un tableaux.
tautologia_tableaux::Form->Bool
tautologia_tableaux f = cerrado (creaTableaux [Neg f])

--Algunas fórmulas de prueba. 
--Puedes comprobar que f <--> f1 <--> f2
--Puedes comprobar que g <--> g1 <--> g2


f = Disy (Neg $ Imp (Var "w") (Var "e")) (Neg $ Disy (DImp (Neg $ Var "s") (Var "w")) (Conj (Var "e") (Var "s")))
f1 = Conj (Conj (Conj (Conj (Disy (Disy (Neg $ Var "e") (Var "s")) (Neg $ Var "w")) (Disy (Neg $ Var "s") (Var "w"))) (Disy (Disy (Neg $ Var "e") (Neg $ Var "s")) (Var "w"))) (Disy (Disy (Var "w") (Neg $ Var "e")) (Neg $ Var "s"))) (Disy (Neg $ Var "e") (Neg $ Var "s"))                                                             
f2 = Disy (Disy (Disy (Conj (Var "w") (Neg $ Var "e")) (Conj (Conj (Neg $ Var "w") (Neg $ Var "s")) (Neg $ Var "e"))) (Conj (Neg $ Var "w") (Neg $ Var "s"))) (Conj (Conj (Var "s") (Var "w")) (Neg $ Var "e"))

g = Disy (Neg $ Imp (Var "w") (Var "e")) (Neg $ Disy (DImp (Neg $ Var "s") (Var "w")) (Conj (Var "e") (Var "s")))
g1 = Conj (Conj (Conj (Conj (Disy (Var "w") (Neg $ Var "s")) (Disy (Disy (Neg $ Var "e") (Neg $ Var "s")) (Var "w"))) (Disy (Disy (Neg $ Var "e") (Neg $ Var "w")) (Var "s"))) (Disy (Disy (Var "w") (Neg $ Var "e")) (Neg $ Var "s"))) (Disy (Neg $ Var "e") (Neg $ Var "s"))
g2 = Disy (Disy (Disy (Conj (Var "w") (Neg $ Var "e")) (Conj (Conj (Neg $ Var "s") (Neg $ Var "w")) (Neg $ Var "e"))) (Conj (Neg $ Var "s") (Neg $ Var "w"))) (Conj (Conj (Var "w") (Var "s")) (Neg $ Var "e"))





