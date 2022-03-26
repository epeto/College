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
-}

import Data.List

--El índice de una variable es un entero
type VarIndex = Integer


-- El nombre de una función o un predicado es una cadena.
-- Por convención utilizamos minúsculas para funciones y mayúsculas para predicados.
type Name = String


-- Tipo de dato para representar términos.
-- Un término es una variable o un símbolo de función aplicado a una lista de términos. 
data Term = X VarIndex | Fn Name [Term] deriving Eq


-- Las variables son términos.
type TVar = Term

{- Hacemos que sean instancia de Show para pintarlos bonito -}
instance Show Term where
      show t = case t of
                     X n -> "X"++show n
                     Fn a [] -> a
                     Fn f (t:ts) -> f++"("++show t++ponComas ts++")" where
                                 ponComas [] = []
                                 ponComas (t:ts) = ","++show t++ponComas ts 


-- Tipo de dato para representar fórmulas.
data Form  = Top | Bot | Pr Name [Term] | Eq Term Term | Neg Form | Or Form Form | And Form Form
            | Impl Form Form | Syss Form Form | All TVar Form | Ex TVar Form deriving Eq


{- Hacemos que sean instancia de Show para pintarlas bonito -}            
instance Show Form where 
      show f = case f of
                  --Atómicas
                  Top -> "⊤"               
                  Bot -> "⊥"
                  Pr p (t:ts) -> p++"("++show t++ponComas ts++")" where 
                                 ponComas [] = []
                                 ponComas (t:ts) = ","++show t++ponComas ts
                  --Igualdad               
                  Eq t1 t2 -> show t1++" = "++show t2 
                  --Negación
                  Neg Top -> "¬⊤"
                  Neg Bot -> "¬⊥"
                  Neg q@(Pr p ts) -> "¬"++show q
                  Neg f1@(Neg f2) -> "¬"++show f1
                  Neg g -> "¬("++show g++")"   
                  --Conjunción
                  And Top Top -> "⊤ ∧ ⊤"
                  And Top Bot -> "⊤ ∧ ⊥"
                  And Bot Top -> "⊥ ∧ ⊤"
                  And Bot Bot -> "⊥ ∧ ⊥"
                  And p1@(Pr p ts) p2@(Pr q ss) -> show p1++" ∧ "++show p2   
                  And f1 p2@(Pr q ss) -> case f1 of
                                           Neg g -> show f1++" ∧ "++show p2
                                           _ -> "("++show f1++") ∧ "++show p2
                  And p1@(Pr p ts) f2 -> case f2 of
                                           Neg g -> show p1++" ∧ "++show f2 
                                           _ -> show p1++" ∧ ("++show f2++")"    
                  And n1@(Neg f1) n2@(Neg f2) -> show n1++" ∧ "++show n2
                  And f1 n2@(Neg f2) -> "("++show f1++") ∧ "++show n2
                  And n1@(Neg f1) f2 -> show n1++" ∧ ("++show f2++")"                                              
                  And f1 f2 -> "("++show f1++") ∧ ("++show f2++")"
                  --Disyunción
                  Or Top Top -> "⊤ ∨ ⊤"
                  Or Top Bot -> "⊤ ∨ ⊥"
                  Or Bot Top -> "⊥ ∨ ⊤"
                  Or Bot Bot -> "⊥ ∨ ⊥"
                  Or p1@(Pr p ts) p2@(Pr q ss) -> show p1++" ∨ "++show p2   
                  Or f1 p2@(Pr q ss) -> case f1 of
                                           Neg g -> show f1++" ∨ "++show p2
                                           _ -> "("++show f1++") ∨ "++show p2
                  Or p1@(Pr p ts) f2 -> case f2 of
                                           Neg g -> show p1++" ∨ "++show f2 
                                           _ -> show p1++" ∨ ("++show f2++")"    
                  Or n1@(Neg f1) n2@(Neg f2) -> show n1++" ∨ "++show n2
                  Or f1 n2@(Neg f2) -> "("++show f1++") ∨ "++show n2
                  Or n1@(Neg f1) f2 -> show n1++" ∨ ("++show f2++")"                                              
                  Or f1 f2 -> "("++show f1++") ∨ ("++show f2++")"
                  --Implicación
                  Impl Top Top -> "⊤ ⟶ ⊤"
                  Impl Top Bot -> "⊤ ⟶ ⊥"
                  Impl Bot Top -> "⊥ ⟶ ⊤"
                  Impl Bot Bot -> "⊥ ⟶ ⊥"
                  Impl p1@(Pr p ts) p2@(Pr q ss) -> show p1++" ⟶ "++show p2   
                  Impl f1 p2@(Pr q ss) -> case f1 of
                                           Neg g -> show f1++" ⟶ "++show p2
                                           _ -> "("++show f1++") ⟶ "++show p2
                  Impl p1@(Pr p ts) f2 -> case f2 of
                                           Neg g -> show p1++" ⟶ "++show f2 
                                           _ -> show p1++" ⟶ ("++show f2++")"    
                  Impl n1@(Neg f1) n2@(Neg f2) -> show n1++" ⟶ "++show n2
                  Impl f1 n2@(Neg f2) -> "("++show f1++") ⟶ "++show n2
                  Impl n1@(Neg f1) f2 -> show n1++" ⟶ ("++show f2++")"                                              
                  Impl f1 f2 -> "("++show f1++") ⟶ ("++show f2++")"
                  --Doble implicación
                  Syss Top Top -> "⊤ ⟷ ⊤"
                  Syss Top Bot -> "⊤ ⟷ ⊥"
                  Syss Bot Top -> "⊥ ⟷ ⊤"
                  Syss Bot Bot -> "⊥ ⟷ ⊥"
                  Syss p1@(Pr p ts) p2@(Pr q ss) -> show p1++" ⟷ "++show p2   
                  Syss f1 p2@(Pr q ss) -> case f1 of
                                           Neg g -> show f1++" ⟷ "++show p2
                                           _ -> "("++show f1++") ⟷ "++show p2
                  Syss p1@(Pr p ts) f2 -> case f2 of
                                           Neg g -> show p1++" ⟷ "++show f2 
                                           _ -> show p1++" ⟷ ("++show f2++")"    
                  Syss n1@(Neg f1) n2@(Neg f2) -> show n1++" ⟷ "++show n2
                  Syss f1 n2@(Neg f2) -> "("++show f1++") ⟷ "++show n2
                  Syss n1@(Neg f1) f2 -> show n1++" ⟷ ("++show f2++")"                                              
                  Syss f1 f2 -> "("++show f1++") ⟷ ("++show f2++")"     
                  --Para todo...
                  All x f -> "∀"++show x++".["++show f++"]"   
                  --Para todo...
                  Ex x f -> "∃"++show x++".["++show f++"]"   
            
-- Una sustitución es una lista de pares cuya primer entrada es una variable 
-- y la segunda entrada es el término por el cual sustituimos la variable.
type Sust = [(TVar,Term)]


            {-DESDE AQUÍ COMIENZA LA PRÁCTICA-}

{-AUXILIARES:-}
--Nos da las variables de un término
varsT::Term->[VarIndex]
varsT term = case term of
            X n -> [n] 
            Fn _ [] -> []
            Fn _ ts -> concat [ varsT t | t<-ts]  
 
miCuenta::Int
miCuenta = 414008117

--Nos devuelve las variables libres de una fórmula.
fv::Form->[VarIndex]
fv form = case form of
            Top -> []
            Bot -> []
            Pr _ ts -> concat [ varsT t | t<-ts] 
            Eq t1 t2 -> varsT t1 ++ varsT t2
            Neg f -> fv f
            And f1 f2 -> fv f1 ++ fv f2
            Or f1 f2 -> fv f1 ++ fv f2
            Impl f1 f2 -> fv f1 ++ fv f2
            Syss f1 f2 -> fv f1 ++ fv f2
            All (X n) f -> filter (n/=) $ fv f
            Ex (X n) f -> filter (n/=) $ fv f
            
            
         
--Nos devuelve las variables ligadas de una fórmula.
bv::Form->[VarIndex]
bv Top = []
bv Bot = []
bv (Pr _ ts) = []
bv (Eq t1 t2) = []
bv (Neg f) = bv f
bv (And f1 f2) = union (bv f1) (bv f2)
bv (Or f1 f2) = union (bv f1) (bv f2)
bv (Impl f1 f2) = union (bv f1) (bv f2)
bv (Syss f1 f2) = union (bv f1) (bv f2)
bv (All (X n) f) = union [n] (bv f)
bv (Ex (X n) f) = union [n] (bv f)

--Devuelve todas las variables de una fórmula.
varsF form = case form of
            Top -> []
            Bot -> []
            Pr _ ts -> concat [ varsT t | t<-ts] 
            Eq t1 t2 -> varsT t1 ++ varsT t2
            Neg f -> varsF f
            And f1 f2 -> varsF f1 ++ varsF f2
            Or f1 f2 -> varsF f1 ++ varsF f2
            Impl f1 f2 -> varsF f1 ++ varsF f2
            Syss f1 f2 -> varsF f1 ++ varsF f2
            All (X n) f -> n:(varsF f)
            Ex (X n) f -> n:(varsF f)


--Aplica sustitución en términos
apsubsT::Term->Sust->Term
apsubsT term sust = case term of
                  X n -> case sust of 
                           [] -> X n
                           (X m,tm):s -> if n==m then tm 
                                                 else apsubsT term s  
                  Fn _ [] -> term 
                  Fn f ts -> Fn f [apsubsT t sust | t<-ts]                              


--Aplica sustitución en fórmulas 
apsubsF::Form->Sust->Form
apsubsF form sust = case form of 
                  Top -> Top
                  Bot -> Bot
                  Pr p ts -> Pr p [apsubsT t sust | t<-ts]    
                  Eq t1 t2-> Eq (apsubsT t1 sust) (apsubsT t2 sust)
		  Neg f -> Neg (apsubsF f sust)
		  Or f1 f2 -> Or (apsubsF f1 sust) (apsubsF f2 sust)
		  And f1 f2 -> And (apsubsF f1 sust) (apsubsF f2 sust)
		  Impl f1 f2 -> Impl (apsubsF f1 sust) (apsubsF f2 sust)
		  Syss f1 f2 -> Syss (apsubsF f1 sust) (apsubsF f2 sust)
                  All (X n) f -> if (elem n $ varsSust sust) then form 
                                                             else All (X n) $ apsubsF f sust
                  Ex (X n) f -> if (elem n $ varsSust sust) then form
                                                             else Ex (X n) $ apsubsF f sust

                  
--Nos devuelve las variables de una sustitución
varsSust::Sust->[VarIndex]
varsSust sust = concat [ [sacaN $ fst t]++(varsT $ snd t) | t<-sust ] where
                                       sacaN (X n) = n

                                    
{-Para rectificar una fórmula, renombra las variables ligadas de tal manera que 
las variables libres y ligadas sean ajenas y no haya cuantificadores de la misma 
variable con alcances ajenos-}
renVL::Form->[VarIndex]->Form
renVL f l = renVl'' (renVl' f l (varsF f)) (varsF (renVl' f l (varsF f)))

--Renombra todas las variables ligadas que aparezcan en la lista que se pasa como argumento.
renVl'::Form->[VarIndex]->[VarIndex]->Form
renVl' Top _ _ = Top
renVl' Bot _ _ = Bot
renVl' (Pr nom t) _ _ = Pr nom t
renVl' (Eq t1 t2) _ _ = Eq t1 t2
renVl' (Neg f) l1 l2 = Neg (renVl' f l1 l2)
renVl' (Or f1 f2) l1 l2 = Or (renVl' f1 l1 l2) (renVl' f2 l1 ((varsF (renVl' f1 l1 l2))++l2))
renVl' (And f1 f2) l1 l2 = And (renVl' f1 l1 l2) (renVl' f2 l1 ((varsF (renVl' f1 l1 l2))++l2))
renVl' (Impl f1 f2) l1 l2 = Impl (renVl' f1 l1 l2) (renVl' f2 l1 ((varsF (renVl' f1 l1 l2))++l2))
renVl' (Syss f1 f2) l1 l2 = Syss (renVl' f1 l1 l2) (renVl' f2 l1 ((varsF (renVl' f1 l1 l2))++l2))
renVl' (All (X n) f) l1 l2 = if elem n l1 then All (X ((maximum l2)+1)) (renVl' (apsubsF f [(X n, X ((maximum l2)+1))]) l1 (((maximum l2)+1):l2)) else All (X n) (renVl' f l1 l2)
renVl' (Ex (X n) f) l1 l2 = if elem n l1 then Ex (X ((maximum l2)+1)) (renVl' (apsubsF f [(X n, X ((maximum l2)+1))]) l1 (((maximum l2)+1):l2)) else Ex (X n) (renVl' f l1 l2)

--Renombra las variables ligadas x si x tiene alcances ajenos en la fórmula.
renVl''::Form->[VarIndex]->Form
renVl'' Top _ = Top
renVl'' Bot _ = Bot
renVl'' (Pr nom t) _ = Pr nom t
renVl'' (Eq t1 t2) _ = Eq t1 t2
renVl'' (Neg f) l = renVl'' f l
renVl'' (Or f1 f2) l = Or (renVl'' f1 l) (renVl'' (renVl' f2 (intersect (bv f1) (bv f2)) l) (varsF (renVl' f2 (intersect (bv f1) (bv f2)) l)))
renVl'' (And f1 f2) l = And (renVl'' f1 l) (renVl'' (renVl' f2 (intersect (bv f1) (bv f2)) l) (varsF (renVl' f2 (intersect (bv f1) (bv f2)) l)))
renVl'' (Impl f1 f2) l = Impl (renVl'' f1 l) (renVl'' (renVl' f2 (intersect (bv f1) (bv f2)) l) (varsF (renVl' f2 (intersect (bv f1) (bv f2)) l)))
renVl'' (Syss f1 f2) l = Syss (renVl'' f1 l) (renVl'' (renVl' f2 (intersect (bv f1) (bv f2)) l) (varsF (renVl' f2 (intersect (bv f1) (bv f2)) l)))
renVl'' (All x f) l = All x (renVl'' f l)
renVl'' (Ex x f) l = Ex x (renVl'' f l)

--Rectifica una fórmula, se da por hecho que no hay cuantificadores bobos :P
recF::Form->Form
recF f = renVL f (fv f)

--Elimina implicaciones
elimImp::Form->Form
elimImp Top = Top
elimImp Bot = Bot
elimImp (Pr n terms) = Pr n terms
elimImp (Eq t1 t2) = Eq t1 t2
elimImp (Neg f) = Neg (elimImp f)
elimImp (Or f1 f2) = Or (elimImp f1) (elimImp f2)
elimImp (And f1 f2) = And (elimImp f1) (elimImp f2)
elimImp (Impl f1 f2) = Or (Neg (elimImp f1)) (elimImp f2)
elimImp (Syss f1 f2) = Or (And (elimImp f1) (elimImp f2)) (And (Neg (elimImp f1)) (Neg (elimImp f2)))
elimImp (All x f) = All x (elimImp f)
elimImp (Ex x f) = Ex x (elimImp f)
                   
--Recibe una fórmula sin implicaciones y devuelve su forma normal negativa.
fnn'::Form->Form
fnn' Top = Top
fnn' Bot = Bot
fnn' (Pr n terms) = Pr n terms
fnn' (Eq t1 t2) = Eq t1 t2
fnn' (Or f1 f2) = Or (fnn' f1) (fnn' f2)
fnn' (And f1 f2) = And (fnn' f1) (fnn' f2)
fnn' (All x f) = All x (fnn' f)
fnn' (Ex x f) = Ex x (fnn' f)
fnn' (Neg Top) = Bot
fnn' (Neg Bot) = Top
fnn' (Neg (Pr n terms)) = Neg (Pr n terms)
fnn' (Neg (Eq t1 t2)) = Neg (Eq t1 t2)
fnn' (Neg (Neg f)) = fnn' f
fnn' (Neg (Or f1 f2)) = And (fnn' (Neg f1)) (fnn' (Neg f2))
fnn' (Neg (And f1 f2)) = Or (fnn' (Neg f1)) (fnn' (Neg f2))
fnn' (Neg (All x f)) = Ex x (fnn' (Neg f))
fnn' (Neg (Ex x f)) = All x (fnn' (Neg f))

--Recibe una fórmula y la cambia a forma normal negativa.
fnn::Form->Form
fnn f = fnn' (elimImp f)

--Recibe una fórmula y devuelve verdadero si y solo si no contiene cuantificadores.
sinCuant::Form->Bool
sinCuant Top = True
sinCuant Bot = True
sinCuant (Pr _ _) = True
sinCuant (Eq _ _) = True
sinCuant (Neg f) = sinCuant f
sinCuant (Or f1 f2) = (sinCuant f1) && (sinCuant f2)
sinCuant (And f1 f2) = (sinCuant f1) && (sinCuant f2)
sinCuant (Ex x f) = False
sinCuant (All x f) = False
sinCuant (Impl f1 f2) = (sinCuant f1) && (sinCuant f2)
sinCuant (Syss f1 f2) = (sinCuant f1) && (sinCuant f2)

--Devuelve la forma normal prenex, se da por hecho que antes se aplicó fnn
fnp::Form->Form
fnp Top = Top
fnp Bot = Bot
fnp (Pr n terms) = Pr n terms
fnp (Eq t1 t2) = Eq t1 t2
fnp (Neg at) = Neg at
fnp (And (Ex x f1) (Ex y f2)) = Ex x (Ex y (fnp (And (fnp f1) (fnp f2))))
fnp (And (All x f1) (All y f2)) = All x (All y (fnp (And (fnp f1) (fnp f2))))
fnp (And (Ex x f1) (All y f2)) = Ex x (All y (fnp (And (fnp f1) (fnp f2))))
fnp (And (All x f1) (Ex y f2)) = All x (Ex y (fnp (And (fnp f1) (fnp f2))))
fnp (And (Ex x f1) f2) = Ex x (fnp (And (fnp f1) (fnp f2)))
fnp (And f1 (Ex x f2)) = Ex x (fnp (And (fnp f1) (fnp f2)))
fnp (And (All x f1) f2) = All x (fnp (And (fnp f1) (fnp f2)))
fnp (And f1 (All x f2)) = All x (fnp (And (fnp f1) (fnp f2)))
fnp (And f1 f2) = if sinCuant (And f1 f2) then And f1 f2 else
			if sinCuant f1 then fnp (And f1 (fnp f2)) else
				if sinCuant f2 then fnp (And (fnp f1) f2) else fnp (And (fnp f1) (fnp f2))
fnp (Or (Ex x f1) (Ex y f2)) = Ex x (Ex y (fnp (Or (fnp f1) (fnp f2))))
fnp (Or (All x f1) (All y f2)) = All x (All y (fnp (Or (fnp f1) (fnp f2))))
fnp (Or (Ex x f1) (All y f2)) = Ex x (All y (fnp (Or (fnp f1) (fnp f2))))
fnp (Or (All x f1) (Ex y f2)) = All x (Ex y (fnp (Or (fnp f1) (fnp f2))))
fnp (Or (Ex x f1) f2) = Ex x (fnp (Or (fnp f1) (fnp f2)))
fnp (Or f1 (Ex x f2)) = Ex x (fnp (Or (fnp f1) (fnp f2)))
fnp (Or (All x f1) f2) = All x (fnp (Or (fnp f1) (fnp f2)))
fnp (Or f1 (All x f2)) = All x (fnp (Or (fnp f1) (fnp f2)))
fnp (Or f1 f2) = if sinCuant (Or f1 f2) then Or f1 f2 else
			if sinCuant f1 then fnp (Or f1 (fnp f2)) else
				if sinCuant f2 then fnp (Or (fnp f1) f2) else fnp (Or (fnp f1) (fnp f2))
fnp (All x f) = All x (fnp f)
fnp (Ex x f) = Ex x (fnp f)

--Recibe un número, una lista de términos y devuelve un símbolo de función con todos los términos de la lista.
creaFun::Int->[Term]->Term
creaFun n terms = Fn ("f" ++ (show n)) terms

--Recibe un número y devuelve una constante (o un símbolo de función sin entradas)
creaCon::Int->Term
creaCon n = Fn ("c"++(show n)) []

--Elimina los cuantificadores para la forma normal Skolem
elimCuant::Form->(Int,Int,[Term])->Form
elimCuant form (con,fun,ter) = case form of
	All x f -> elimCuant f (con,fun,ter++[x])
	Ex x f -> if null ter
		then elimCuant (apsubsF f [(x,creaCon con)]) (con+1,fun,ter)
		else elimCuant (apsubsF f [(x,creaFun fun ter)]) (con,fun+1,ter)
	_ -> form

--Devuelve la forma normal Skolem, se da por hecho que se aplicó fnp                    
fns::Form->Form
fns f = elimCuant f (0,0,[])

--Función que decide si una fórmula es literal.
esLit::Form->Bool
esLit Top = True
esLit Bot = True
esLit (Pr _ _) = True
esLit (Eq _ _) = True
esLit (Neg f) = esLit f
esLit _ = False

--Función de distribución.
distr :: Form -> Form -> Form
distr f1 f2 = if (esLit f1) && (esLit f2) then Or f1 f2
		else 
		if not (esLit f1) then case f1 of
		And g1 g2 -> And (distr g1 f2) (distr g2 f2)
		else	 case f2 of
		And h1 h2 -> And (distr f1 h1) (distr f1 h2)

--Función que transforma una fórmula a FNC.
fnc::Form->Form
fnc Top = Top
fnc Bot = Bot
fnc (Pr x t) = Pr x t
fnc (Eq t1 t2) = Eq t1 t2
fnc (Neg (Eq t1 t2)) = Neg (Eq t1 t2)
fnc (Neg (Pr x t)) = Neg (Pr x t)
fnc (Neg Top) = Bot
fnc (Neg Bot) = Top
fnc (And p1 p2) = (And (fnc p1) (fnc p2))
fnc (Or p1 p2) = distr (fnc p1) (fnc p2)

--Nos devuelve la forma clausular de una fórmula
formClaus::Form->Form
formClaus = fnc . fns . fnp . fnn . recF

--Para que veas que sí funciona, la formClaus de esta fórmula es (salvo alpha-equivalencias):
-- ¬R(X0,f0(X0),X3,f2(X0,X3)) ∨ P(f1(X0))
form_ejem = All (X 0) $ Ex (X 1) $ Impl (Ex (X 3) $ All (X 4) $ Pr "R" [X 0,X 1,X 3,X 4]) (Ex (X 0) $ Pr "P" [X 0])

form_ejem_1 = All (X 0) $ Impl (Pr "P"  [X 0]) (All (X 0) $ Pr "Q" [X 0])

form_ejem_2 = Or (All (X 0) $ Pr "P"  [X 0]) (All (X 0) $ Pr "Q" [X 0])

