El proyecto se abre con NetBeans.
Para compilarlo solo se presiona el botón de Build y esto creará las clases de java.
Para ejecutarlo se presiona el botón de Run y se muestra en pantalla el resultado de analizar el archivo test.txt.
Está predeterminado para que ocupe la gramática 1. Para cambiar eso, se va al archivo pom.xml y se modifica la línea 32.
Para que imprima toda la ejecución se modifica la línea 61 de cada archivo relacionado con la gramática (gramatica1.y, gramatica2.y).

El resultado de 3-2+8 en la gramática 2 es -7, lo cual es un error. Lo que hizo fue evaluar primero 2+8 y luego 3-(2+8)=3-10=-7.
En la gramática 1, el resultado es 9. En este caso sí hace la operación en el orden correcto (3-2)+8=1+8=9.

En la gramática 2, la reducción se hace de la siguiente manera:
expr -> term - expr -> term - (term + expr) -> term - (term + term) -> factor - (factor + factor) -> NUMBER - (NUMBER + NUMBER) -> 3-(2+8)

En la gramática 1 no se puede reducir como [expr -> expr - term], porque 2+8 no se puede derivar de <term>. Así que se hace de la siguiente manera:
expr -> expr + term -> (expr - term) + term -> (term - term) + term -> (factor - factor) + factor -> (NUMBER - NUMBER) + NUMBER -> (3-2)+8
