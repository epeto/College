Instrucciones para ejecutar el programa:

Primero tenemos que crear y poblar una base de datos en MySQL. Esto se hace cargando los scripts que aparecen en esta carpeta en este orden:
-DDL.sql
-dataset_movies.sql
-dataset_links.sql
-dataset_tags.sql
-dataset_ratings.sql

Nota: los datos los sacamos de https://grouplens.org/datasets/movielens/

Después ejecutamos el archivo Peliculas.jar que se encuentra en
Peliculas/dist.

Se pedirá el usuario de la base de datos (en mi caso es root).

Luego se pedirá la URL de la base de datos (en mi caso es jdbc:mysql://localhost/DATASET)

Finalmente se pedirá la contraseña para ingresar a la base y después de esto se hará la conexión.

Después se pedirá que ingrese un número de usuario (que es uno que ya está registrado en la base de datos)

Después se pedirá la cantidad de películas a recomendar.

El resultado de esto será una lista de películas seguido de su calificación estimada.
