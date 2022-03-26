

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
-- Ésta es la resolución original
resOriginal :: Clausula -> [Clausula] -> [Clausula]
resOriginal _ [] = []
resOriginal c (x:xs) = let l = complClau c x
                in case l of
                Just lit -> (resBin c x lit):(resOriginal c xs)
                Nothing  -> resOriginal c xs

-- Toma una cláusula c, un conjunto de cláusulas cl y se realiza la resolución binaria unitaria.
-- Solo se realiza la resolución si alguna de las dos cláusulas es unitaria.
resUnitaria :: Clausula -> [Clausula] -> [Clausula]
resUnitaria _ [] = []
resUnitaria c (x:xs) = let l = complClau c x
                in case l of
                Just lit -> if (length c) == 1 || (length x) == 1
                            then (resBin c x lit):(resUnitaria c xs)
                            else resUnitaria c xs
                Nothing  -> resUnitaria c xs

-- Decide si una cláusula es positiva.
esClausulaPositiva :: Clausula -> Bool
esClausulaPositiva clau = all esLiteralPositiva clau
                          where esLiteralPositiva (Var x) = True
                                esLiteralPositiva _ = False

-- Aplica resolución binaria positiva de una cláusula c con el resto de cláusulas.
-- Solo se realiza resolución si alguna de las dos cláusulas es positiva.
resPositiva :: Clausula -> [Clausula] -> [Clausula]
resPositiva _ [] = []
resPositiva c (x:xs) = let l = complClau c x
                in case l of
                Just lit -> if (esClausulaPositiva c) || (esClausulaPositiva x)
                            then (resBin c x lit):(resPositiva c xs)
                            else resPositiva c xs
                Nothing  -> resPositiva c xs

-- Toma un conjunto de cláusulas y aplica resolución binaria de todos con todos.
-- Toma una función (fun) que representa un tipo de resolución.
res2 :: (Clausula -> [Clausula] -> [Clausula]) -> [Clausula] -> [Clausula]
res2 _ [] = []
res2 fun (x:xs) = (fun x xs)++(res2 fun xs)

-- Toma un conjunto de cláusulas, un número entero y aplica un tipo de resolución que depende del número.
-- n con n!=2 y n!=3 -> Resolución original
-- 2 -> Resolución positiva
-- 3 -> Resolución unitaria
res3 :: Int -> [Clausula] -> [Clausula]
res3 2 clau = res2 resPositiva clau
res3 3 clau = res2 resUnitaria clau
res3 _ clau = res2 resOriginal clau

-- Se calcula el resolvente de un conjunto de cláusulas.
resolucion :: Int -> [Clausula] -> [Clausula]
resolucion n clau = clau++(res3 n clau)


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


-- Se aplica el algoritmo de saturación con simplificación.
-- Recibe un número n que determina el tipo de resolución a aplicar.
-- n con n!=2 y n!=3 -> Resolución original
-- 2 -> Resolución positiva
-- 3 -> Resolución unitaria
saturaSimpl :: Int -> [Clausula] -> IO ()
saturaSimpl n clau = let resolvente = simplifica $ resolucion n clau
                   in
                      do
                      print clau;
                      if elem [] resolvente -- Cláusula vacía.
                      then do (print resolvente); (putStrLn "Insatisfacible");
                      else do
                           if resolvente == clau -- No  hubo cambios.
                           then do (print resolvente); (putStrLn "Satisfacible");
                           else do (saturaSimpl n resolvente)


-- Aplica el algoritmo de saturación con simplificación pero recibe una cadena de
-- caracteres que representa una fórmula.
aplicaSatura :: Int -> [String] -> IO()
aplicaSatura n [cadena] = let formula = parse cadena
                          in do
                           print formula;
                           saturaSimpl n (transforma formula);
aplicaSatura n (c:cs) = let formula = parse c
                        in do
                         print formula;
                         saturaSimpl n (transforma formula);
                         aplicaSatura n cs;


-- Función principal.
main = do 
   putStrLn "Elija una opción:\n1.-Resolución original\n2.-Resolución positiva\n3.-Resolución unitaria"
   opcion <- getLine -- Opción elegida por el usuario.
   entrada <- readFile "input" -- Lee el archivo "input" y se guarda en "entrada".
   let tipoRes = read opcion ::Int -- Se transforma la opción en un entero.
   (aplicaSatura tipoRes (lines entrada)) -- lines es para separar por \n.
          


