
module Parser where

import LogicaProp
import Data.List

-- Decide si un caracter es operador lógico.
esOpLog :: Char -> Bool
esOpLog c = case c of
            '¬' -> True
            '∧' -> True
            '∨' -> True
            '→' -> True
            '⇔' -> True
            otherwise -> False


-- Recibe una pila, una expresión y extrae elementos de la pila hasta que sea vacía o encuentre un paréntesis izquierdo.
extraePila :: String -> String -> (String,String)
extraePila [] expr = ([],expr)
extraePila (x:xs) expr = if (x == '(')
                         then ((x:xs),expr)
                         else extraePila xs (expr++[x])


-- Cambia una expresión lógica de forma infija a forma postfija.
-- El primer argumento es la expresión de entrada.
-- El segundo argumento es una pila, inicialmente vacía.
-- El tercer argumento es la expresión de salida que se está construyendo.
infApostCola :: String -> String -> String -> String
infApostCola [] [] exprSal = exprSal --Si la entrada y la pila están vacías, se devuelve la expresión construida.
infApostCola [] (x:xs) exprSal = infApostCola [] xs (exprSal++[x]) --Si la entrada está vacía pero la pila no, se mueve el tope de la pila a la  salida.
infApostCola ('(':xs) p exprSal = infApostCola xs ('(':p) exprSal
infApostCola ('¬':xs) p exprSal = infApostCola xs ('¬':p) exprSal
infApostCola (')':xs) p exprSal = let tupla = extraePila p exprSal
                                  in infApostCola xs (tail (fst tupla)) (snd tupla)
infApostCola (x:xs) p exprSal = if(esOpLog x)
                                then
                                    let tupla = extraePila p exprSal
                                    in infApostCola xs (x:(fst tupla)) (snd tupla)
                                else infApostCola xs p (exprSal++[x]) --Si llega a este caso es porque x es atómica.


-- Recibe una expresión lógica de forma infija y la transforma a postfija.
infApost :: String -> String
infApost expr = infApostCola expr [] []


-- Recibe un caracter que representa un operador lógico, un par de fórmulas y devuelve una fórmula nueva.
creaForm :: Char -> Form -> Form -> Form
creaForm op f1 f2 = case op of
                    '∧' -> And f1 f2
                    '∨' -> Or f1 f2
                    '→' -> Imp f1 f2
                    '⇔' -> Syss f1 f2


-- Recibe una fórmula lógica en forma postfija y la transforma al tipo de fórmula definido en LogicaProp.
-- Utiliza una pila para evaular.
parse' :: String -> [Form] -> Form
parse' [] p = head p
parse' (x:xs) p = if (esOpLog x)
                  then
                      if (x == '¬') -- operador unario
                      then let f = head p
                               p2 = tail p
                           in parse' xs ((Neg f):p2)
                      else let f1 = head (tail p) -- operador binario
                               f2 = head p
                               p2 = tail (tail p)
                           in parse' xs ((creaForm x f1 f2):p2)
                  else if x=='F'
                       then parse' xs (F:p)
                       else if x=='T'
                            then parse' xs (T:p)
                            else parse' xs ((Var [x]):p)


-- Recibe un String de una fórmula infija y la transforma a una fórmula de haskell.
parseForm :: String -> Form
parseForm expr = parse' (infApost expr) []

{- Separa un String en varias cadenas. 
   La separación la determina un caracter c.
   También ignora los espacios. -}
separaString :: Char -> String -> [String]
separaString _ [] = []
separaString c cadena = if (head cadena) == c || (head cadena) == ' '
                        then separaString c (tail cadena)
                        else let prefijo = takeWhile (/=c) cadena
                                 sufijo = dropWhile (/=c) cadena
                             in prefijo:(separaString c sufijo)

-- Recibe una conjunto de fórmulas en forma de String y las transforma a fórmulas de haskell.
parseConj :: String -> [Form]
parseConj conj = map parseForm (separaString ',' (init (tail conj))) -- El init.tail es para eliminar '[' y ']'

{- Recibe una fórmula o un conjunto de fórmulas en forma de String
   y los transforma a fórmulas de haskell.
   Si solo es una fórmula devuelve un conjunto con un elemento. -}
parseGeneral :: String -> [Form]
parseGeneral expr = if (head expr) == '['
                    then parseConj expr -- Es un conjunto de fórmulas.
                    else [parseForm expr] -- Es una sola fórmula.