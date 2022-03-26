
import LogicaProp
import Parser
import Data.List


{- Encuentra una cláusula unitaria en un conjunto de cláusulas.
   Si el conjunto no tiene una cláusula unitaria devuelve Nothing. -}
findCU :: [Clausula] -> Maybe Clausula
findCU claus = find (\x -> (length x) == 1) claus

-- Aplica propagación unitaria a un conjunto de cláusulas usando la literal l.
reglaCU :: Literal -> [Clausula] -> [Clausula]
reglaCU l claus = let sinL = filter (\x -> not (elem l x)) claus -- Quita todas las cláusulas que contienen a l.
              in map (\x -> delete (litCompl l) x) sinL -- Quita todas las literales lc de las cláusulas.

-------------------------------------------------------------------------------
ejemplar1 = [[(Var "p")], [(Var "q"), (Var "p")], [(Var "r"), (Neg (Var "p"))]]
test1 = let res = findCU ejemplar1
        in case res of
            Just cu -> reglaCU (head cu) ejemplar1
            Nothing -> ejemplar1
-------------------------------------------------------------------------------

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

----------------------------------------------------------------------------
ejemplar2 = [[(Var "p"), (Neg (Var "r"))], [(Var "q"), (Var "p")],
             [(Neg (Var "r")), (Neg (Var "p"))], [(Neg (Var "q"))]]

test2 = let res = findLP ejemplar2
        in case res of
            Just lit -> reglaLP lit ejemplar2
            Nothing -> ejemplar2
----------------------------------------------------------------------------

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

--------------------------------------------------------------
ejemplar3 = [[(Var "r"), (Neg (Var "q")), (Neg (Var "p"))],
             [(Neg (Var "r")), (Var "q"), (Neg (Var "p"))],
             [(Var "p"), (Neg (Var "q")), (Var "r")],
             [(Var "s"), (Var "t")],
             [(Neg (Var "s")), (Neg (Var "t"))]]

test3 = let lit = literalFrecuente ejemplar3
        in reglaD lit ejemplar3
--------------------------------------------------------------

{- Recibe un conjunto de cláusulas y encuentra todas las literales que formarán el modelo para este conjunto.
   Ejecuta el algoritmo dpll. Carga un conjunto de literales como segundo argumento. -}
dpllCola :: [Clausula] -> [Literal] -> [[Literal]]
dpllCola [] cola = [cola] -- Conjunto vacío -> Encontré modelo.
dpllCola claus cola = if (elem [] claus) -- Contiene cláusula vacía -> No encontré modelo.
         then []
         else let clausula_unitaria = findCU claus -- Busca una cláusula unitaria.
              in case clausula_unitaria of
                 Just cu -> dpllCola (reglaCU (head cu) claus) ((head cu):cola) -- Aplica la regla de cláusula unitaria.
                 Nothing -> let literal_pura = findLP claus -- Busca una literal pura.
                            in case literal_pura of
                               Just lp -> dpllCola (reglaLP lp claus) (lp:cola) -- Aplica la regla de literal pura.
                               Nothing -> let literal_max = literalFrecuente claus -- Encuentra la literal más frecuente.
                                              par = reglaD literal_max claus -- Aplica la regla de descomposición.
                                              s1 = fst par
                                              s2 = snd par
                                          in (dpllCola s1 ((litCompl literal_max):cola))++(dpllCola s2 (literal_max:cola))


{- Recibe un conjunto de cláusulas y encuentra todos los modelos para este, usando DPLL.
   En este caso un estado está representado por un conjunto de variables:
   Si la variable está en el conjunto significa que su valor en una fórmula es 1.
   Si la variable no está en el conjunto significa que su valor en una fórmula es 0.
   Un conjunto vacío implica una fórmula insatisfacible.
   Un modelo vacío implica que todas las variables están negadas. -} 
dpllModelo :: [Clausula] -> [[Literal]]
dpllModelo claus = map (\x -> filter esLiteralPositiva x) (dpllCola claus [])

----------------------------------------------------------------------
ejemplar4 = [[(Var "p"), (Var "q"), (Var "r")],
             [(Neg (Var "p")), (Var "q"), (Var "r")],
             [(Var "p"), (Neg (Var "q"))],
             [(Var "p"), (Var "r")],
             [(Neg (Var "p")), (Neg (Var "q")), (Var "r")],
             [(Neg (Var "p")), (Var "q"), (Neg (Var "r"))],
             [(Neg (Var "p")), (Neg (Var "q")), (Neg (Var "r"))]]

test4 = dpllModelo ejemplar4
----------------------------------------------------------------------

-- Recibe un conjunto de conjuntos de fórmulas y aplica el algoritmo DPLL a cada conjunto.
aplicaDPLL :: [[Form]] -> IO()
aplicaDPLL [formulas] = let forma_clausular = simplifica' (formsAfnc formulas)
                        in do putStrLn "\nFórmulas:";
                              print formulas;
                              putStrLn "Forma clausular:";
                              print forma_clausular;
                              putStrLn "Modelos:";
                              print (dpllModelo forma_clausular);
aplicaDPLL (x:xs) = let forma_clausular = simplifica' (formsAfnc x)
                    in do putStrLn "\nFórmulas:";
                          print x;
                          putStrLn "Forma clausular:";
                          print forma_clausular;
                          putStrLn "Modelos:";
                          print (dpllModelo forma_clausular);
                          aplicaDPLL xs;


-- Función principal
main = do entrada <- readFile "input";
          aplicaDPLL (map parseGeneral (lines entrada));
