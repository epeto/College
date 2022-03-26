--Emmanuel Peto Gutiérrez
--414008117

{-
Lógica computacional 2017-1
         Noé Salomón Hernández Sánchez
         Albert M. Orozco Camacho
         C. Moisés Vázquez Reyes
         Diego Murillo
-}

import Data.List
import Data.Char

data Nat = Cero | Suc Nat deriving Show

miCuenta::Int
miCuenta = 414008117

--Devuelve la suma de 2 números naturales.
suma::Nat->Nat->Nat
suma Cero x = x
suma x Cero = x
suma (Suc x) (Suc y) = Suc (Suc (suma x y))

--Devuelve el producto de 2 números naturales.
prod::Nat->Nat->Nat
prod Cero x = Cero
prod x Cero = Cero
prod (Suc x) y = suma y (prod x y)

--Devuelve verdadero si y solo si el número de la izquierda es mayor que el de la derecha (>).
mayorQue::Nat->Nat->Bool
mayorQue Cero _ = False
mayorQue (Suc x) Cero = True
mayorQue (Suc x) (Suc y) = mayorQue x y

--Devuelve verdadero si y solo si los 2 números son iguales.
igual::Nat->Nat->Bool
igual Cero Cero = True
igual Cero (Suc x) = False
igual (Suc x) Cero = False
igual (Suc x) (Suc y) = igual x y

--Dados x, y enteros, la función debe calcular x^y.
power::Int->Int->Int
power 0 _ = 0
power _ 0 = 1
power x y = x*(power x (y-1))

--Devuelve n^k.
power2::Int->Int->Int
power2 n k = if (mod k 2) == 0
		then power (n*n) (div k 2)
		else n*(power n (k-1))

--Toma una lista y nos devuelve su reversa.
reversa::[a]->[a]
reversa [] = []
reversa [x] = [x]
reversa (x:xs) = (reversa xs) ++ [x]

--Toma una lista de números enteros y nos devuelve la suma de sus elementos.
sumal::[Int]->Int
sumal [] = 0
sumal [x] = x
sumal (x:xs) = x + (sumal xs)

--Toma los primeros n elementos de una lista.
toma::Int->[a]->[a]
toma 0 _ = []
toma n [] = error "Elementos insuficientes en la lista"
toma n (x:xs) = x:(toma (n-1) xs)

--Tira los primeros n elementos de una lista
tira::Int->[a]->[a]
tira 0 x = x
tira n [] = []
tira n (x:xs) = tira (n-1) xs

--Toma un elemento x y una lista l y nos dice cuántas veces aparece x en l
cuantas::Eq a=>a->[a]->Int
cuantas e [] = 0
cuantas e (x:xs) = if x == e
		then 1 + (cuantas e xs)
		else cuantas e xs

--Función que elimina todas las apariciones de un elemento e en una lista l.
elimAp::Eq a=>a->[a]->[a]
elimAp _ [] = []
elimAp e (x:xs) = if x==e
		then (elimAp e xs)
		else x:(elimAp e xs)

--Dada una lista L devuelve: {(x, y)|x ∈ L, y = el número de veces que aparece x en L}
frec::Eq a=>[a]->[(a, Int)]
frec [] = []
frec [x] = [(x,1)]
frec (x:xs) = (x,1+(cuantas x xs)):(frec (elimAp x xs))

--Dada una lista L de duplas (a,Int) nos devuelve la lista de los elementos a tales que su segunda entrada es 1.
unaVez'::[(a,Int)]->[a]
unaVez' [] = []
unaVez' ((y,z):xs) = if (z==1)
			then y:(unaVez' xs)
			else unaVez' xs

--Dada una lista L, nos devuelve a los elementos que aparecen sólo una vez en L.
unaVez::Eq a=>[a]->[a]
unaVez l = unaVez' (frec l)

--Recibe una cadena de caracteres y devuelve la lista de los caracteres que están después de un espacio.
compress1'::String->String
compress1' [] = []
compress1' [x] = []
compress1' (x:xs) = if x==' '
			then (head xs):(compress1' xs)
			else compress1' xs

--La entrada es una cadena que contiene palabras separadas por espacios, toma la primer letra de cada palabra y las junta en una sola cadena.
compress1::String->String
compress1 l = (head l):(compress1' l)

--Toma una cadena y devuelve los caracteres que son números.
obtNum::String->String
obtNum [] = []
obtNum [x] = if (isNumber x) then [x] else []
obtNum (x:xs) = if (isNumber x) then x:(obtNum xs) else []

--Toma una cadena y devuelve los caracteres que son letras.
obtChar::String->String
obtChar [] = []
obtChar [x] = if (isLetter x) then [x] else []
obtChar (x:xs) = if (isLetter x) then (x:xs) else (obtChar xs)

--Toma una lista de cadenas con números y caracteres y devuelve una lista de duplas con números y caracteres.
f::[String]->[(String,String)]
f [] = []
f (x:xs) = (f' x):(f xs) where
	f' l = (obtNum l,obtChar l)

--Toma una cadena que solo contiene caracteres numéricos y lo convierte a un entero.
stringToInt::String->Int
stringToInt [] = 0
stringToInt [x] = digitToInt x
stringToInt (x:xs) = (digitToInt x)*10^(length xs)+(stringToInt xs)

--Toma una lista de duplas ("numero",String) y devuelve una lista de triplas donde la primera es el número en tipo entero, la segunda entrada es la cadena y la tercera entrada es la longitud de la cadena.
g::[(String,String)]->[(Int,String,Int)]
g [] = []
g (x:xs) = (g' x):(g xs) where
	g' ([],[]) = (0,[],0)
	g' (a,b) = (stringToInt a,b,length b)

--Toma una lista de triplas del tipo (numero, cadena, longitud_de_cadena) y devuelve una lista de caracteres. Cada caracter está dentro de cada cadena y su posición está indicada por el número en la primera entrada; si la longitud de la cadena es menor al número devuelve un espacio en blanco.
h::[(Int,String,Int)]->String
h [] = []
h (x:xs) = (h' x):(h xs) where
	h' (a,b,c) = if a<c then (b !! a) else ' '

--La entrada es una cadena que contiene palabras separadas por espacios, cada palabra tiene un número al inicio. De cada palabra debes obtener el caracter que esté en la posición que el número al inicio indique y debes devolverlos en una sola cadena. Si el número excede la longitud de la palabra debes devolver un espacio en blanco.
compress2::String->String
compress2 l = h (g (f (words l)))

--Recibe un número entero a y devuelve una lista de duplas de todas las x,y tales que a=x+y. El segundo argumento es el comienzo.
suma2::Int->Int->[(Int,Int)]
suma2 n c = if (div n 2) <= c then [(c,n-c)] else (c,n-c):(suma2 n (c+1))

--Recibe un número entero y nos dice si es primo. El segundo argumento es el comienzo.
esPrimo::Int->Int->Bool
esPrimo 0 _ = False
esPrimo 1 _ = False
esPrimo n c = if (mod n c) == 0 && n>c then False else 
		if c >= n then True else esPrimo n (c+1)

--Recibe una lista de duplas de enteros y devuelve la lista de duplas tales que ambas entradas tengan números primos.
duplasPrimos::[(Int,Int)]->[(Int,Int)]
duplasPrimos [] = []
duplasPrimos ((a,b):xs) = if (esPrimo a 2) && (esPrimo b 2) then (a,b):(duplasPrimos xs) else duplasPrimos xs

--Recibe una lista l de duplas de enteros y devuelve una dupla (a,b) tal que (axb) <= (cxd) para toda (c,d) en l.
minDup::[(Int,Int)]->(Int,Int)
minDup [] = (0,0)
minDup [x] = x
minDup (x:xs) = minprod x (minDup xs) where
		minprod (a,b) (c,d) = if a*b < c*d then (a,b) else (c,d)

--Recibe una lista de duplas y elimina las que tienen 0 en la primera entrada o que tienen ambas entradas iguales.
depurar::[(Int,Int)]->[(Int,Int)]
depurar [] = []
depurar ((a,b):xs) = if a==0 || a==b then depurar xs else (a,b):(depurar xs)

--Recibe una dupla de enteros (a,b) y devuelve una lista con todos los enteros entre a y b.
rango::(Int,Int)->[Int]
rango (a,b) = if a<=b then a:(rango (a+1,b)) else []

--Recibe una lista de duplas (x,y) y devuelve el producto de todos los x*y.
multipDup::[(Int,Int)]->Int
multipDup [] = 0
multipDup [(a,b)] = a*b
multipDup ((a,b):xs) = a*b*(multipDup xs)

--Recibe una lista de duplas (a,b) y devuelve una dupla (x,y) donde x es el tamaño de la lista y y es el producto de todos los a*b módulo (10^9+7).
juego'::[(Int,Int)]->(Int,Int)
juego' l = (length l,mod (multipDup l) (10^9+7))

--Recibe una lista de enteros y devuelve una lista de duplas tal que a cada entero x le corresponde una dupla (y,z) tal que x=y+z.
entadup::[Int]->[(Int,Int)]
entadup [] = []
entadup (x:xs) = (minDup (duplasPrimos (suma2 x 1))):(entadup xs)

--El juego de Anita y Bufarreti
juego::(Int,Int)->(Int,Int)
juego x = juego' (depurar (entadup (rango x)))
