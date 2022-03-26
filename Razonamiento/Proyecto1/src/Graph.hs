-- Este módulo será para el tipo de dato gráfica (graph).

module Graph where

import Data.List
import LogicaProp

type Vertice = Char  --Un vértice será un caracter.
type Arista = (Vertice, Vertice) --Una arista será un par de vértices.
type Grafica = ([Vertice], [Arista]) --Una gráfica es un conjunto de vértices y un conjunto de aristas.

--ejemplar1 = ("abcdefg", [('a','b'), ('c','d'), ('e','f'), ('a','g'), ('b','g'), ('c','g'), ('d','g'), ('e','g'), ('f','g')])

--ejemplar2 = ("abcdef", [('a','b'), ('a','c'), ('b','c'), ('b','d'), ('c','d'), ('c','e'), ('e','f')])

ejaris1 = [('a','b'), ('a','c'), ('a','d'), ('a','e'), ('b','c'), ('b','d'), ('b','e'), ('c','d'), ('c','e'), ('d','e')]

-- Toma un conjuno de aristas y devuelve el conjunto de vértices que las conforman.
vertsDeAri :: [Arista] -> [Vertice]
vertsDeAri ari = let par = unzip ari
                 in quitaRep ((fst par)++(snd par))

-- Dado un vértice v, devuelve el conjunto de aristas que son incidentes en v.
incideV :: Vertice -> [Arista] -> [Arista]
incideV _ [] = []
incideV v ((a,b):xs) = if (v==a || v==b)
                       then (a,b):(incideV v xs)
                       else incideV v xs

{- Toma una arista (a,b) y devuelve un par (izq, der)
   izq: aristas incidentes en a.
   der: aristas incidentes en b. -}
incideA :: Arista -> [Arista] -> ([Arista], [Arista])
incideA (a,b) ari = ((incideV a ari), (incideV b ari))

{- Toma un conjundo de aristas ari, un conjunto de vértices verts y deja en el conjunto de aristas
solo aquellas que tienen alguno de sus vértices en verts-}
dejaAri :: [Arista] -> [Vertice] -> [Arista]
dejaAri [] _ = []
dejaAri ((a,b):xs) verts = if (elem a verts) || (elem b verts)
                           then (a,b):(dejaAri xs verts)
                           else dejaAri xs verts

{- Realiza intersección entre dos conjuntos de aristas.
   En vez de comprobar si son iguales, se comprueba si comparten un vértice. -}
intersectaAri :: [Arista] -> [Arista] -> [Arista]
intersectaAri ari1 ari2 = (dejaAri ari1 (vertsDeAri ari2))++(dejaAri ari2 (vertsDeAri ari1))

-- Dada una arista a, encuentra el conjunto de aristas que forman un triángulo con a.
aristasDeTriangulo :: Arista -> [Arista] -> [Arista]
aristasDeTriangulo a lista = let par = incideA a lista
                             in intersectaAri (fst par) (snd par)

{- Dada una arista a y un conjunto de aristas aris, devuelve el primer vértice en
   aris que no está en a-}
findFirstDifVertex :: Arista -> [Arista] -> Maybe Vertice
findFirstDifVertex _ [] = Nothing
findFirstDifVertex (u,v) ((a,b):xs) = if (a/=u && a/=v)
                                      then Just a
                                      else if (b/=u && b/=v)
                                           then Just b
                                           else findFirstDifVertex (u,v) xs

{- Se forman varios triángulos cuando más de dos aristas inciden en una arista 'ari'
   donde 'ari' pertenece a los triángulos.-}
casoSuperior :: Arista -> [Arista] -> [[Arista]]
casoSuperior ari lista = let primerVertice = findFirstDifVertex ari lista
                         in case primerVertice of
                              Just v -> let part1 = incideV v lista
                                            part2 = lista \\ part1
                                        in  if (length part2) == 2
                                            then [(ari:part1),(ari:part2)]
                                            else (ari:part1):(casoSuperior ari part2)
                              Nothing -> []


{- Recibe una arista 'ari', un conjunto de aristas y devuelve triángulos formados
   con 'ari' (conjuntos de 3 aristas). Se pueden formar a lo sumo 2 triángulos.-}
formaTriangulos1 :: Arista -> [Arista] -> [[Arista]]
formaTriangulos1 ari lista = let arisTri = aristasDeTriangulo ari lista
                             in if (length arisTri) > 2
                                then casoSuperior ari arisTri
                                else if (length arisTri) == 2
                                     then [(ari:arisTri)]
                                     else []

{-Recibe 2 conjuntos de aristas aris1 y aris2, y forma triángulos usando como base
  una arista de aris1 y 2 aristas de aris2.-}
formaTriangulos2 :: [Arista] -> [Arista] -> [[Arista]]
formaTriangulos2 [] _ = []
formaTriangulos2 _ [] = []
formaTriangulos2 (x:xs) aris2 = let arisSinx = delete x aris2
                                in (formaTriangulos1 x arisSinx)++(formaTriangulos2 xs arisSinx)


-- Recibe un conjunto de aristas y devuelve los triángulos que se forman.
formaTriangulos3 :: [Arista] -> [[Arista]]
formaTriangulos3 aris = formaTriangulos2 aris aris

