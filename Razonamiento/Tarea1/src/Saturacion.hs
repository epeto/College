

import LogicaProp
import Parser
import Data.List

-- Función que recibe una literal y devuelve su literal complementaria.
litCompl :: Literal -> Literal
litCompl T = F
litCompl F = T
litCompl (Var x) = (Neg (Var x))
litCompl (Neg (Var x)) = (Var x)


-- Recibe un par de cláusulas (c1 y c2) y devuelve la primera literal en c1 que tenga un complemento en c2.
complClau :: Clausula -> Clausula -> Maybe Literal
complClau [] c = Nothing
complClau (x:xs) c = if (elem (litCompl x) c)
                     then Just x
                     else complClau xs c


-- Se realiza la resolución binaria de c1 y c2 con la literal l.
resBin :: Clausula -> Clausula -> Literal -> Clausula
resBin c1 c2 l = union (delete l c1) (delete (litCompl l) c2)


-- Toma una cláusula c, un conjunto de cláusulas cl y se realiza la resolución binaria de c con todas las de cl.
res1 :: Clausula -> [Clausula] -> [Clausula]
res1 _ [] = []
res1 c (x:xs) = let l = complClau c x
                in case l of
                Just lit -> (resBin c x lit):(res1 c xs)
                Nothing  -> res1 c xs


-- Toma un conjunto de cláusulas y aplica resolución binaria de todos con todos.
res2 :: [Clausula] -> [Clausula]
res2 [] = []
res2 (x:xs) = (res1 x xs)++(res2 xs)

-- Se aplica la resolución de un conjunto de cláusulas.
resolucion :: [Clausula] -> [Clausula]
resolucion clau = clau++(res2 clau)

-- Se aplica algoritmo de saturación sin simplificar.
saturacion :: [Clausula] -> ([Clausula],String)
saturacion clau = let resolvente = resolucion clau
                  in
                     if elem [] resolvente -- Cláusula vacía.
                     then (resolvente, "Insatisfacible")
                     else if resolvente == clau
                          then (resolvente, "Satisfacible")
                          else saturacion resolvente


-- Elimina las literales repetidas en las cláusulas por la propiedad de idempotencia.
idempotencia :: [Clausula] -> [Clausula]
idempotencia [] = []
idempotencia (x:xs) = (eiddc x):(idempotencia xs)
                      where
                      eiddc [] = [] --Elimina idempotencia de cláusula.
                      eiddc (l:ls) = if elem l ls
                                     then eiddc ls
                                     else l:(eiddc ls)


-- Recibe una cláusula y decide si es tautología.
esTautologia :: Clausula -> Bool
esTautologia clau = if (tlc clau) || (elem T clau)
                    then True
                    else False
                    where
                    tlc [] = False
                    tlc (x:xs) = if elem (litCompl x) xs
                                 then True
                                 else tlc xs


-- Recibe un conjunto de cláusulas y elimina las tautologías.
elimTautologia :: [Clausula] -> [Clausula]
elimTautologia claus = filter (\x -> not (esTautologia x)) claus

-- Comprueba si una lista es subconjunto de otra.
isSubsetOf :: Eq a => [a] -> [a] -> Bool
isSubsetOf c1 c2 = all (\x -> elem x c2) c1

-- Recibe 2 listas y decide si el de la izquierda es superconjunto de la de la derecha.
isSuperset :: Eq a => [a] -> [a] -> Bool
isSuperset l1 l2 = isSubsetOf l2 l1


-- Recibe una lista l, una lista  de listas ldl y decide si l es superconjunto de alguna lista en ldl.
hasSuperset :: Eq a => [a] -> [[a]] -> Bool
hasSuperset _ [] = False
hasSuperset l (x:xs) = if (isSuperset l x)
                       then True
                       else hasSuperset l xs

-- Recibe una lista de listas ldl y elimina los elementos que sean superconjuntos de alguna lista a la derecha.
quitaSuperset :: Eq a => [[a]] -> [[a]]
quitaSuperset [] = []
quitaSuperset (x:xs) = if (hasSuperset x xs)
                       then quitaSuperset xs
                       else x:(quitaSuperset xs)


-- Recibe un conjunto de cláusulas y elimina las que son subsumidas por otras dentro del mismo conjunto.
subsume :: [Clausula] -> [Clausula]
subsume clau = quitaSuperset $ reverse $ quitaSuperset $ reverse clau


-- Simplifica un conjunto de cláusulas. Es decir, quita literales repetidas dentro de la misma cláusula, elimina tautologías y quita cláusulas subsumidas.
simplifica :: [Clausula] -> [Clausula]
simplifica clau = subsume $ elimTautologia $ idempotencia clau


-- Se aplica el algoritmo de saturación con simplificación
saturaSimpl :: [Clausula] -> IO ()
saturaSimpl clau = let resolvente = simplifica $ resolucion clau
                   in
                      do
                      print clau;
                      if elem [] resolvente -- Cláusula vacía.
                      then do (print resolvente); (putStrLn "Insatisfacible");
                      else do
                           if resolvente == clau -- No  hubo cambios.
                           then do (print resolvente); (putStrLn "Satisfacible");
                           else do (saturaSimpl resolvente)


-- Aplica el algoritmo de saturación con simplificación pero recibe una cadena de caracteres que representa una fórmula.
aplicaSatura :: [String] -> IO()
aplicaSatura [cadena] = let formula = parse cadena
                        in do
                           print formula;
                           saturaSimpl $ transforma formula;
aplicaSatura (c:cs) = let formula = parse c
                      in do
                         print formula;
                         saturaSimpl $ transforma formula;
                         aplicaSatura cs;


-- Función principal.
main = do entrada <- readFile "input";
          (aplicaSatura $ lines entrada); -- lines es para separar por \n.
          

-- Ejemplares -------------------------------------------------------------------------------------------

clau1 = [[(Var "p"), (Var "q")], [(Neg (Var "p")), (Var "q")], [(Var "p"), (Neg (Var "q"))], [(Neg (Var "p")), (Neg (Var "q"))]]

clau2 = [[(Neg (Var "p")), (Neg (Var "q")), (Neg (Var "r")), (Var "s")], [(Neg (Var "t")), (Neg (Var "w")), (Var "r")], [(Var "q")], [(Neg (Var "v")), (Neg (Var "r")), (Var "p")], [(Var "t")], [(Var "v")], [(Neg (Var "v")), (Var "w")]]

form0 = Imp (Var "p") (Var "q")

form1 = And (And (Or (Var "p") (Var "q")) (Syss (Var "p") (Var "q"))) (Neg (And (Var "p") (Var "q")))

form2 = Syss (Var "p") (Var "q")

form3 = Or (And (Var "p") (Var "q")) (And (Var "r") (Var "s"))

form4 = Or (Neg $ Imp (Var "w") (Var "e")) (Neg $ Or (Syss (Neg $ Var "s") (Var "w")) (And (Var "e") (Var "s")))

