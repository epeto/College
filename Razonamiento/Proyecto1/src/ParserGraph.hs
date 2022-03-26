-- Este módulo sirve para traducir un archivo de texto a una gráfica.

module ParserGraph where

import Data.List
import Graph

-- Recibe un conjunto de aristas como listas de tamaño 2 de vértices. Devuelve el conjunto de tipo Arista.
parseAristas :: [String] -> [Arista]
parseAristas cadenas = map (\x -> (head x, head (tail x))) cadenas

-- Función que elimina las aristas que no tienen un vértice en la gráfica.
aristaSinVertice :: [Arista] -> [Vertice] -> [Arista]
aristaSinVertice [] _ = []
aristaSinVertice ((a,b):xs) verts = if (elem a verts) && (elem b verts)
                                    then (a,b):(aristaSinVertice xs verts)
                                    else aristaSinVertice xs verts

-- Función que elimina las aristas repetidas.
quitaAR :: [Arista] -> [Arista]
quitaAR [] = []
quitaAR ((a,b):xs) = if (elem (a,b) xs) || (elem (b,a) xs)
                     then quitaAR xs
                     else (a,b):(quitaAR xs)

--Función que elimina de una gráfica las aristas que no necesita.
limpiaGrafica :: Grafica -> Grafica
limpiaGrafica (verts,aris) = let new_aris = quitaAR (aristaSinVertice aris verts)
                             in (verts, new_aris)

{- Recibe una codificación de gráfica y lo transforma a un tipo gráfica.
   La primera línea es el conjunto de vértices, mientras que el resto de
   las líneas son las aristas.-}
parseGrafica :: String -> Grafica
parseGrafica codificado = let lineas = lines codificado -- Separa la entrada por saltos de línea
                          in limpiaGrafica (head lineas, parseAristas (tail lineas))


