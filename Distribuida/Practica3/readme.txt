
Comentarios sobre el código:

- Los tipos de mensaje que se envían son tipo char y son 3: 'E': convocar a una elección, 'N': null y 'R': nodo responde que recibió la convocatoria a elecciones.
- Para simular un "timeout" solo se envía el caracter 'N' si el nodo está inactivo.
- Durante la ejecución se manda llamar muchas veces "printf", mostrando cada mensaje recibido. Por eso, al iniciar el programa se pregunta si desea activar o no el modo "depuración" (le puse así porque lo usé para encontrar errores). Si no está activado solo se muestra al principio cuáles son los nodos que activos y al final se muestra el resultado, es decir, quién es el nodo elegido.

- Para compilar y ejecutar solo hay que poner el comando "make". Si ya está compilado y se desea ejecutar se debe usar el comando:
mpirun -n <número_de_hilos> bully

