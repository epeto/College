Nombre: Emmanuel Peto Gutiérrez
Número de cuenta: 414008117
Correo: empg014@gmail.com

En esta práctica se realizaron 2 métodos para comprobar si una fórmula es tautología. Una es comprobando que todas las interpretaciones den verdadero y otra es comprobando si el tableaux de la negación de la fórmula es cerrado.

1.-interp: En los casos base devuelvo True si es T, False si es F y si es una variable proposicional p devuelvo verdadero si p está en el conjunto de estados y Falso si no está. Para A ^ B devuelvo interp(A) && interp(B), para A v B devuelvo interp(A) || interp(B), para A -> B devuelvo ¬interp(A) v interp(B) y para A <-> B devuelvo interp(A)==interp(B).

2.-vars: En los casos T o F devuelvo lista vacía. Si es una variable proposicional p devuelvo la lista que contiene a p, es decir [p]. En el caso A * B devuelvo vars(A) U vars(B), es decir, la unión entre las variables de A y de B donde * es ^,v,->,<->. En el caso ¬A devuelvo vars(A).

3.-potencia: En el caso de recibir una lista vacía [] devuelvo la lista que contiene a la lista vacía [[]]. Si recibo una lista x:xs donde x es la cabeza y xs es la cola entonces devuelvo la potencia de xs unión con agregar x a cada elemento de la potencia de xs.

4.-estados: Para esta función solo hago la composición de vars con potencia. Es decir, si recibe una fórmula f devuelve potencia(vars(f)).

5.-tautologia: Para esta función solo compruebo que todos los estados de una fórmula f hagan verdadera a esta.

6.-creaTableaux: La idea para esta función fue usar una función auxiliar llamada expande que primero comprueba si el conjunto de fórmulas es solamente de literales, si lo es entonces ponía una tacha o bolita dependiento si tiene literales complementarias o no. En caso de que no sean todas literales, se toma la cabeza de la lista (f:fs) donde f es la cabeza y fs la cola. Si f es una literal entonces se manda al final de la lista y se vuelve a aplicar la función expande; si f se expande con reglas alfa entonces se crea una rama R1, se agregan las subfórmulas de f a fs y se vuelve a aplicar la función expande; si f se expande con reglas beta entonces se crean 2 ramas con R2 donde en una rama vamos a agregar una subfórmula de f a fs y en la otra rama la otra agregamos la otra subfórmula de f a fs.

7.-cerrado: En el caso base devolvemos True si es Tache y False si es Bolita. Si es una rama R1 t aplicamos la función cerrado a t. Si es una rama R2 t1 t2 comprobamos que t1 es cerrado y que t2 es cerrado para devolver True, si t1 no es cerrado o t2 no es cerrado devolvemos False.

8.-tautologia_tableaux: Dada una fórmula f solo comprobamos si el tableaux de [¬f] es cerrado para devolver True.
