
import LogicaProp
import Parser
import Data.List

data ArbolDPLL = Paloma
                 | Tacha [Clausula]
                 | RCU Literal [Clausula] ArbolDPLL
                 | RLP Literal [Clausula] ArbolDPLL 
                 | RD [Clausula] Literal [Clausula] ArbolDPLL [Clausula] ArbolDPLL

instance Show ArbolDPLL where
   show t = pinta t 0 where
              pinta t n = case t of
                 Paloma -> (replicate n ' ')++"✔️"
                 Tacha claus -> (replicate n ' ')++(show claus)++" ❌"
                 RCU l claus a -> (replicate n ' ')++(show claus)++" RCU "++(show l)++"\n"++(pinta a n)
                 RLP l claus a -> (replicate n ' ')++(show claus)++" RLP "++(show l)++"\n"++(pinta a n)
                 RD c0 l c1 a1 c2 a2 -> (replicate n ' ')++(show c0)++" RD "++(show l)++"\n"++
                                                                     (replicate n ' ')++(show c1)++ "\n" ++
                                                                     (pinta a1 (n+3))++ "\n" ++
                                                                     (replicate n ' ') ++ (show c2) ++ "\n" ++
                                                                     (pinta a2 (n+3))


-- Saca al elemento de Just x
sacaMaybe :: Maybe a -> a
sacaMaybe (Just x) = x
sacaMaybe Nothing = error "No contiene nada"

{- Encuentra una cláusula unitaria en un conjunto de cláusulas.
   Si el conjunto no tiene una cláusula unitaria devuelve Nothing. -}
findCU :: [Clausula] -> Maybe Clausula
findCU claus = find (\x -> (length x) == 1) claus

-- Aplica propagación unitaria a un conjunto de cláusulas usando la literal l.
reglaCU :: Literal -> [Clausula] -> [Clausula]
reglaCU l claus = let sinL = filter (\x -> not (elem l x)) claus -- Quita todas las cláusulas que contienen a l.
              in map (\x -> delete (litCompl l) x) sinL -- Quita todas las literales lc de las cláusulas.

-- Toma un conjunto de cláusulas y encuentra una literal pura.
-- Si no tiene una literal pura, devuelve Nothing.
findLP :: [Clausula] -> Maybe Literal
findLP claus = let literales = quitaRep $ concat claus -- Obtiene las lliterales de la cláusula.
                   positivas  = filter esLiteralPositiva literales -- Literales positivas de la cláusula.
                   negativas = filter (\x -> not (esLiteralPositiva x)) literales -- Literales negativas de la cláusula.
                   lit = find (\x -> not (elem (litCompl x) negativas)) positivas -- Literal positiva que no tiene complemento.
               in case lit of
                  Just l -> Just l
                  Nothing -> find (\x -> not (elem (litCompl x) positivas)) negativas -- Literal negativa que no tiene complemento.

-- Aplica la regla de literal pura con la literal l.
reglaLP :: Literal -> [Clausula] -> [Clausula]
reglaLP l claus = filter (\x -> not (elem l x)) claus

-- Cuenta el número de apariciones de un elemento e en una lista.
cuenta :: Eq a => a -> [a] -> Int
cuenta e lista = length (filter (==e) lista)

-- Elimina todas las apariciones de un elemento e en una lista.
multipleDelete :: Eq a => a -> [a] -> [a]
multipleDelete _ [] = []
multipleDelete e (x:xs) = if x==e
                          then multipleDelete e xs
                          else x:(multipleDelete e xs)

{- Recibe una lista de elementos y cuenta las repeticiones.
   Devuelve una lista de pares (elemento, repeticiones) -}
cuentaMultiple :: Eq a => [a] -> [(a, Int)]
cuentaMultiple [] = []
cuentaMultiple (x:xs) = (x, (1+(cuenta x xs))):(cuentaMultiple (multipleDelete x xs))

-- Recibe una lista de pares (a,b) y devuelve el par cuyo 'b' sea mayor. 
parMax :: Eq a => [(a,Int)] -> (a,Int)
parMax [tupla] = tupla
parMax ((x,i):xs) = mayor (x,i) (parMax xs)
                    where 
                    mayor (x1, i1) (x2, i2) = if (i1 > i2)
                    then (x1, i1)
                    else (x2, i2)

{- Recibe un conjunto de cláusulas y devuelve la literal con mayor frecuencia.
   Ésta es la heurística. -}
literalFrecuente :: [Clausula] -> Literal
literalFrecuente claus = fst $ parMax $ cuentaMultiple $ concat claus

-- Aplica la regla de descomposición con la literal l.
reglaD :: Literal -> [Clausula] -> ([Clausula], [Clausula])
reglaD l claus = let a = filter (\x -> elem l x) claus -- Todas las cláusulas que contienen a lit.
                     b = filter (\x -> elem (litCompl l) x) claus -- Todas las cláusulas que contienen al complemento de lit.
                     r = (claus \\ a) \\ b -- Las cláusulas menos 'a' menos 'b' (diferencia de conjuntos).
                     a' = map (\x -> delete l x) a
                     b' = map (\x -> delete (litCompl l) x) b
                 in ((union a' r), (union b' r))


{- Recibe un conjunto de cláusulas y crea un árbol DPLL. -}
dpll :: [Clausula] -> ArbolDPLL
dpll [] = Paloma -- Conjunto vacío -> Encontré modelo.
dpll claus = if (elem [] claus) -- Contiene cláusula vacía -> No encontré modelo.
         then Tacha claus
         else let clausula_unitaria = findCU claus -- Busca una cláusula unitaria.
              in case clausula_unitaria of
                 Just cu -> RCU (head cu) claus (dpll (reglaCU (head cu) claus))
                 Nothing -> let literal_pura = findLP claus
                            in case literal_pura of
                               Just lp -> RLP lp claus (dpll (reglaLP lp claus))
                               Nothing -> let literal_max = literalFrecuente claus
                                              par = reglaD literal_max claus
                                              s1 = fst par
                                              s2 = snd par
                                          in RD claus literal_max s1 (dpll s1) s2 (dpll s2)

aplicaDPLL :: [[Form]] -> IO()
aplicaDPLL [] = putStrLn "Terminado"
aplicaDPLL (x:xs) = let forma_clausular = simplifica' (formsAfnc x)
                    in do putStrLn "\nFórmulas:";
                          print x;
                          putStrLn "Forma clausular:";
                          print forma_clausular;
                          putStrLn "Árbol:";
                          print (dpll forma_clausular);
                          aplicaDPLL xs;

main = do entrada <- readFile "input";
          aplicaDPLL (map parseGeneral (lines entrada));

