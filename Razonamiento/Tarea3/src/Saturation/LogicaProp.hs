

module LogicaProp where

import Data.List

-- Definición de fórmula proposicional.
data Form = T | F | Var String | Neg Form | And Form Form | Or Form Form | Imp Form Form | Syss Form Form deriving Eq

type Literal = Form -- Una literal es una forma atómica o la negación de una forma atómica.
type Clausula = [Literal] -- Una cláusula es una lista de literales.

instance Show Form where
   --Atómicas
   show T = "T"
   show F = "F"
   --Variables
   show (Var x) = x
   --Negación
   show (Neg T) = "¬T"
   show (Neg F) = "¬F"
   show (Neg v@(Var x)) = "¬"++show v
   show (Neg f@(Neg p)) = "¬"++show f
   show (Neg p) = "¬("++show p++")"
   --Conjunción
   show (And v1@(Var p) v2@(Var q)) = show v1++" ∧ "++show v2
   show (And f1 v2@(Var q)) = case f1 of 
                               Neg f -> show f1++" ∧ "++show v2
                               _ -> "("++show f1++") ∧ "++show v2
   show (And v1@(Var p) f2) = case f2 of 
                               Neg f -> show v1++" ∧ "++show f2
                               _ -> show v1++" ∧ ("++show f2++")"
   show (And n1@(Neg f1) n2@(Neg f2)) = show n1++" ∧ "++show n2
   show (And n1@(Neg f1) f2) = show n1++" ∧ ("++show f2++")"
   show (And f1 n2@(Neg f2)) = "("++show f1++") ∧ "++show n2
   show (And f1 f2) = "("++show f1++") ∧ ("++show f2++")"
   --Disyunción
   show (Or v1@(Var p) v2@(Var q)) = show v1++" ∨ "++show v2
   show (Or f1 v2@(Var q)) = case f1 of 
                               Neg f -> show f1++" ∨ "++show v2
                               _ -> "("++show f1++") ∨ "++show v2
   show (Or v1@(Var p) f2) = case f2 of 
                               Neg f -> show v1++" ∨ "++show f2
                               _ -> show v1++" ∨ ("++show f2++")"
   show (Or n1@(Neg f1) n2@(Neg f2)) = show n1++" ∨ "++show n2
   show (Or n1@(Neg f1) f2) = show n1++" ∨ ("++show f2++")"
   show (Or f1 n2@(Neg f2)) = "("++show f1++") ∨ "++show n2
   show (Or f1 f2) = "("++show f1++") ∨ ("++show f2++")"
   --Implicación
   show (Imp v1@(Var p) v2@(Var q)) = show v1++" → "++show v2
   show (Imp f1 v2@(Var q)) = case f1 of 
                               Neg f -> show f1++" → "++show v2
                               _ -> "("++show f1++") → "++show v2
   show (Imp v1@(Var p) f2) = case f2 of 
                               Neg f -> show v1++" → "++show f2
                               _ -> show v1++" → ("++show f2++")"
   show (Imp n1@(Neg f1) n2@(Neg f2)) = show n1++" → "++show n2
   show (Imp n1@(Neg f1) f2) = show n1++" → ("++show f2++")"
   show (Imp f1 n2@(Neg f2)) = "("++show f1++") → "++show n2
   show (Imp f1 f2) = "("++show f1++") → ("++show f2++")"
   --Doble implicación
   show (Syss v1@(Var p) v2@(Var q)) = show v1++" ⇔ "++show v2
   show (Syss f1 v2@(Var q)) = case f1 of 
                               Neg f -> show f1++" ⇔ "++show v2
                               _ -> "("++show f1++") ⇔ "++show v2
   show (Syss v1@(Var p) f2) = case f2 of 
                               Neg f -> show v1++" ⇔ "++show f2
                               _ -> show v1++" ⇔ ("++show f2++")"
   show (Syss n1@(Neg f1) n2@(Neg f2)) = show n1++" ⇔ "++show n2
   show (Syss n1@(Neg f1) f2) = show n1++" ⇔ ("++show f2++")"
   show (Syss f1 n2@(Neg f2)) = "("++show f1++") ⇔ "++show n2
   show (Syss f1 f2) = "("++show f1++") ⇔ ("++show f2++")"  

-- Funciones ---------------------------------------------------------------------------------------------------------------------------

-- Elimina la equivalencia lógica (si y solo si) de una fórmula.
elimEquiv::Form->Form
elimEquiv F = F
elimEquiv T = T
elimEquiv (Var p) = Var p
elimEquiv (Neg f) = Neg (elimEquiv f)
elimEquiv (And f1 f2) = And (elimEquiv f1) (elimEquiv f2)
elimEquiv (Or f1 f2) = Or (elimEquiv f1) (elimEquiv f2)
elimEquiv (Imp f1 f2) = Imp (elimEquiv f1) (elimEquiv f2)
elimEquiv (Syss f1 f2) = And (Imp f1ee f2ee) (Imp f2ee f1ee)
                         where f1ee = elimEquiv f1
                               f2ee = elimEquiv f2


-- Recibe una fórmula sin equivalencia lógica y elimina la implicación.
elimImp::Form->Form
elimImp F = F
elimImp T = T
elimImp (Var p) = Var p
elimImp (Neg f) = Neg (elimImp f)
elimImp (And f1 f2) = And (elimImp f1) (elimImp f2)
elimImp (Or f1 f2) = Or (elimImp f1) (elimImp f2)
elimImp (Imp f1 f2) = Or (Neg (elimImp f1)) (elimImp f2)
elimImp f = f


-- Recibe una fórmula sin equivalencias y sin implicaciones, y lo transforma a forma normal negativa.
fnn::Form->Form
fnn F = F
fnn T = T
fnn (Var p) = Var p
fnn (Neg f) = case f of
              F -> T
              T -> F
              Var p -> Neg (Var p)
              Neg g -> fnn g
              And f1 f2 -> Or (fnn (Neg f1)) (fnn (Neg f2))
              Or f1 f2 -> And (fnn (Neg f1)) (fnn (Neg f2))
              g -> Neg g
fnn (And f1 f2) = And (fnn f1) (fnn f2)
fnn (Or f1 f2) = Or (fnn f1) (fnn f2)
fnn f = f


-- Recibe una fórmula en forma normal negativa y la transforma a forma normal conjuntiva.
fnc::Form->Form
fnc F = F
fnc T = T
fnc (Var p) = Var p
fnc (Neg p) = Neg p
fnc (And f1 f2) = And (fnc f1) (fnc f2)
fnc (Or f1 f2) = distr (fnc f1) (fnc f2)
fnc f = f


-- Función de distribución
distr :: Form -> Form -> Form
distr T T = T
distr T F = T
distr T (Var x) = T
distr T (Neg (Var x)) = T
distr F T = T
distr F F = F
distr F (Var x) = (Var x)
distr F (Neg (Var x)) = (Neg (Var x))
distr (Var x) T = T
distr (Var x) F = (Var x)
distr (Var x) (Var y) = if x==y then (Var x) else (Or (Var x) (Var y))
distr (Var x) (Neg (Var y)) = if x==y then T else (Or (Var x) (Neg (Var y)))
distr (Neg (Var x)) T = T
distr (Neg (Var x)) F = (Neg (Var x))
distr (Neg (Var x)) (Var y) = if x==y then T else (Or (Neg (Var x)) (Var y))
distr (Neg (Var x)) (Neg (Var y)) = if x==y then (Neg (Var x)) else (Or (Neg (Var x)) (Neg (Var y)))
distr (And f1 f2) f3 = (And (distr f1 f3) (distr f2 f3))
distr f3 (And f1 f2) = (And (distr f1 f3) (distr f2 f3))
distr f1 f2 = Or f1 f2

----Recibe una cláusula y devuelve una lista de las literales de dicha cláusula.
clConj :: Form -> Clausula
clConj T = [T]
clConj F = [F]
clConj (Var x) = [(Var x)]
clConj (Neg (Var x)) = [(Neg (Var x))]
clConj (Or f1 f2) = union (clConj f1) (clConj f2)
clConj f = [f]

-- Recibe una fórmula en fnc y devuelve su forma de conjunto.
fncConj :: Form -> [Clausula]
fncConj T = [[T]]
fncConj F = [[F]]
fncConj (Var x) = [[(Var x)]]
fncConj (Neg (Var x)) = [[(Neg (Var x))]]
fncConj (Or f1 f2) = [clConj (Or f1 f2)]
fncConj (And f1 f2) = union (fncConj f1) (fncConj f2)
fncConj f = [[f]]

-- Recibe cualquier fórmula lógica y la transforma a su FNC de conjuntos.
transforma :: Form -> [Clausula]
transforma f = fncConj $ fnc $ fnn $ elimImp $ elimEquiv f





