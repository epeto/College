-- Este programa resuelve el problema del triángulo monocromático.

import Data.List
import Graph
import ParserGraph
import LogicaProp
import System.Environment

--ejemplar1 = ("abcdefg", [('a','b'), ('c','d'), ('e','f'), ('a','g'), ('b','g'), ('c','g'), ('d','g'), ('e','g'), ('f','g')])

--ejemplar2 = ("abcdef", [('a','b'), ('a','c'), ('b','c'), ('b','d'), ('c','d'), ('c','e'), ('e','f')])

-- Recibe un conjunto de aristas y devuelve el conjunto de variables proposicionales que las representan.
obtenVariables :: [Arista] -> [(Form,Form)]
obtenVariables [] = []
obtenVariables ((u,v):xs) = ((Var [u,v,'1']), (Var [u,v,'2'])):(obtenVariables xs)

{- Recibe una lista de pares de variables y devuelve un conjunto de cláusulas.
   Cada cláusula significa que al menos una de las 2 variables debe ser verdadera.-}
minUno :: [(Form, Form)] -> [Clausula]
minUno pares = map (\x -> [fst x, snd x]) pares

{- Recibe una lista de pares de variables y devuelve un conjunto de cláusulas.
   Cada cláusula significa que solo una de las 2 variables puede ser verdadera.-}
maxUno :: [(Form, Form)] -> [Clausula]
maxUno pares = map (\x -> [Neg (fst x), Neg (snd x)]) pares

{- Recibe un conjunto de triángulos (listas de 3 aristas) y devuelve un conjunto de cláusulas.
   Cada cláusula significa que no puede haber un triángulo en una partición.-}
sinTriangulo :: [[Arista]] -> [Clausula]
sinTriangulo [] = []
sinTriangulo (x:xs) = (trianAclaus x)++(sinTriangulo xs)
                      where trianAclaus [(a1,b1),(a2,b2),(a3,b3)] = [[(Neg (Var [a1,b1,'1'])), (Neg (Var [a2,b2,'1'])), (Neg (Var [a3,b3,'1']))], [(Neg (Var [a1,b1,'2'])), (Neg (Var [a2,b2,'2'])), (Neg (Var [a3,b3,'2']))]]
                            trianAclaus _ = error "El triángulo debe tener 3 aristas"

{- Dada una gráfica, crea una fórmula en forma clausular que es satisfacible si y solo si
   existe una partición del problema "triángulo monocromático" para ésta gráfica.-}
creaFormula :: Grafica -> [Clausula]
creaFormula grafica = let aristas = snd grafica
                          triangulos = formaTriangulos3 aristas
                          varProp = obtenVariables aristas
                      in (minUno varProp) ++ (maxUno varProp) ++ (sinTriangulo triangulos)

-- Recibe una literal y devuelve su formato en Lisp.
litAlisp :: Literal -> String
litAlisp (Var x) = x
litAlisp (Neg (Var x)) = "(not "++ x ++")"

-- Recibe una cláusula y devuelve una fórmula en formato de Lisp.
clausAlisp :: Clausula -> String
clausAlisp clau = "(or "++(ddlit clau)++")"
                  where ddlit [l] = litAlisp l
                        ddlit (l:ls) = (litAlisp l)++" "++(ddlit ls)

-- Recibe una fórmula en forma clausular y devuelve su formato en Lisp.
fncAlisp :: [Clausula] -> String
fncAlisp claus = "(and"++('\n':(ddclau claus))++")"
                 where ddclau [c] = "    "++(clausAlisp c)
                       ddclau (c:cs) = "    "++(clausAlisp c)++('\n':(ddclau cs))

-- Recibe un conjunto de variables proposicionales y devuelve una declaración en formato de Lisp.
varsAlisp :: [Literal] -> String
varsAlisp [] = []
varsAlisp ((Var x):ls) = "(declare-const "++x++" Bool)\n"++(varsAlisp ls)

{- Recibe un conjunto de variables proposicionales, un conjunto de cláusulas
   y devuelve un String para formato en Z3.-}
formatoZ3 :: [Literal] -> [Clausula] -> String
formatoZ3 lits claus = (varsAlisp lits)++
                       "(push)\n(assert\n  "++
                       (fncAlisp claus)++
                       ")\n(check-sat)\n(get-model)\n(pop)"

-- Función principal
main = do
    args <- getArgs
    let ruta = "input/"++(head args) --Toma el primer argumento que es el nombre del archivo.
    entrada <- readFile ruta --Lee el archivo.
    let grafica = parseGrafica entrada --Transforma el archivo en una gráfica.
        varsPar = unzip (obtenVariables (snd grafica)) --Obtiene las variables a partir de la gráfica.
        varsTotal = (fst varsPar)++(snd varsPar)
        forma_clausular = (creaFormula grafica) --Obtiene la fórmula en forma clausular.
    print forma_clausular
    writeFile ("output/formula_"++(head args)++".smt2") (formatoZ3 varsTotal forma_clausular) --Escribe el archivo de salida para z3.


