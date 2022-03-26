
{- Éste es un intento de hacer algoritmos sobre gráficas usando Haskell
   pero no es parte del proyecto. -}

import Data.List

-- El primer Int es el id del vértice y la lista de enteros son los id's de sus vecinos.
data Vertice = V Int [Int]

instance Show Vertice where
    show (V id ady) = "v"++(show id) ++ " → [" ++ (pintaV ady) ++ "]" where
                      pintaV lista = case lista of
                         []  -> ""
                         [v] -> "v"++(show v)
                         (x:xs) -> "v"++(show x)++", "++(pintaV xs)

-- Una gráfica es una lista de vértices.
data Grafica = G [Vertice]

instance Show Grafica where
    show (G vertices) = pintaG vertices where
                        pintaG lista = case lista of
                            [] -> ""
                            (x:xs) -> (show x)++"\n"++(pintaG xs)

-- Recibe un vértice y devuelve su id.
getId :: Vertice -> Int
getId (V id _) = id

-- Recibe una gráfica, un id de vértice y devuelve el vértice.
getVerticePorId :: Grafica -> Int -> Vertice
getVerticePorId (G lista) = gvpid lista

gvpid :: [Vertice] -> Int -> Vertice
gvpid [] _ = error "No existe un vértice con ese id"
gvpid (v:vs) id = if id == (getId v)
                  then v
                  else gvpid vs id

-- Recibe un vértice y devuelve sus vecinos.
getVecinos :: Vertice -> [Int]
getVecinos (V _ vecinos) = vecinos

-- Aplica DFS a una gráfica y devuelve los id's de los vértices en el orden en el que fueron visitados.
dfs :: Grafica -> [Int]
dfs (G vertices) = dfs2 vertices (map getId vertices) []

{- Algoritmo DFS usando recursión de cola.
   La primera lista son todos los vértices de la gráfica.
   La segunda lista son los vértices a visitar (pila).
   La tercera lista son los vértices visitados. -}
dfs2 :: [Vertice] -> [Int] -> [Int] -> [Int]
dfs2 _ [] visitados = visitados
dfs2 totales (v:vs) visitados = if (length visitados) == (length totales) -- Si todos los vértices ya fueron visitados se devuelve la lista.
                                then visitados
                                else if elem v visitados -- Si v ya fue visitado lo ignoro.
                                     then dfs2 totales vs visitados
                                     else let vNuevos = getVecinos (gvpid totales v)
                                          in dfs2 totales (vNuevos ++ vs) (visitados++[v])

-- Aplica BFS a una gráfica y devuelve los id's de los vértices en el orden en el que fueron visitados.
-- Recibe un vértice raíz a partir de el cual se aplica el algoritmo.
bfs :: Grafica -> Int -> [Int]
bfs (G vertices) raiz = bfs2 vertices [raiz] []

-- La lista de enmedio es la cola.
bfs2 :: [Vertice] -> [Int] -> [Int] -> [Int]
bfs2 _ [] visitados = visitados
bfs2 totales (v:vs) visitados = if elem v visitados -- Si el vértice ya fue visitado lo ignoro.
                                then bfs2 totales vs visitados
                                else let vNuevos = getVecinos (gvpid totales v) -- vértices nuevos.
                                     in bfs2 totales (vs ++ vNuevos) (visitados++[v])

-- Recibe una lista de enteros, un número n y devuelve una lista de pares de tipo (_,n).
creaPar :: [Int] -> Int -> [(Int, Int)]
creaPar lista n = map (\x -> (x,n)) lista

-- Estrategia de BFS para calcular distancia desde la raíz al resto de los vértices.
distancia :: Grafica -> Int -> [(Int,Int)]
distancia (G vertices) raiz = distancia2 vertices [(raiz, 0)] []

distancia2 :: [Vertice] -> [(Int, Int)] -> [(Int, Int)] -> [(Int, Int)]
distancia2 _ [] visitados = visitados
distancia2 totales (v:vs) visitados = if elem (fst v) (map fst visitados)
                                      then distancia2 totales vs visitados
                                      else let vNuevos = getVecinos (gvpid totales (fst v)) -- vértices nuevos
                                               dNueva = (snd v) + 1 -- distancia nueva
                                               paresNuevos = creaPar vNuevos dNueva
                                           in  distancia2 totales (vs ++ paresNuevos) (visitados++[v])

ejemplar1 = G [V 0 [1,2],
               V 1 [0,3],
               V 2 [0,3],
               V 3 [1,2,4],
               V 4 [3,5,6],
               V 5 [4,6],
               V 6 [4,5]]

