
Para compilar y ejecutar el programa: ejecutar el comando "make"
Si ya está compilado y solo se quiere ejecutar: "mpirun -n <número de hilos> generales"

Sobre las elecciones de los nodos:
- 'A' significa atacar.
- 'R' significa retirarse.
- 'N' significa nada. Ésta la va a enviar un nodo cuando se "cayó"; ya que el MPI_Rcv se queda esperando un mensaje es mejor enviarle 'N' que no enviar nada.

Sobre los traidores:
- Cada nodo tiene una probabilidad de 25% de ser traidor y no siempre se obtendrá el mismo número de traidores.
- Si se elige el tipo 0 -> Todos los traidores serán por caída.
- Si se elige el tipo 1 -> Todos los traidores serán bizantinos.
- Si se elige cualquier número diferente a 1 y 0 -> Algunos serán por caída y otros serán bizantinos.
- El tipo 0 tendrá una probabilidad de 15% de caerse por cada mensaje enviado.

Sobre las matrices:
- En las filas se muestra el número del nodo que hizo una elección. En las columnas se muestra el que reportó la elección, y en la casilla correspondiente se muestra la elección. Por ejemplo, si en la fila f y la columna c está la 'A', significa que c reportó que f eligió 'A'.
- En la parte derecha de la matriz se muestra el elemento más frecuente entre 'A' y 'R' de cada fila (la 'N' no se cuenta).

Nota: En algunas ejecuciones se muestran las matrices "revueltas". Por ejemplo, se muestran filas de la matriz 1 intercaladas con filas de la matriz 5. Por eso se da la opción de mostrar o no las matrices.
