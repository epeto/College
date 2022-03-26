Rodríguez Hernández Alexis Arturo

Esta práctica fue mucho más díficil de lo que esperaba, hay varias situaciones
que el verificador no toma en cuenta y que valdria la pena resaltar:

-- No se le puede asignar un tipo a las funciones que no reciben parámetros
-- Dejará pasar por alto cuando se le pasan argumentos insuficientes a una
    función en una aplicación
-- El tipo de una lista vacia es distinto al tipo de cualquier otra lista
-- Cuando se infiere el tipo de un rec, no se revisa que la función declarada
   sea del tipo correcto pues para eso se debería crear algo como lo usado
   para el interp, un tipo de ambiente de tipos recursivo, entonces solo revisa
   que el cuerpo evalue al tipo correcto usando (o mejor dicho suponiendo) las
   declaraciones de tipos de sus bindings.
   
