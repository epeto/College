#División
.data
x: .word 47        #Colocar el valor decimal de 45 en x
y: .word 5         #Colocar el valor decimal de 5 en y
.text 
lw $t0, x          #Cargar en t0 el valor de x
lw $t1, y          #Cargar en t1 el valor de y
li $t2, 0          #Cargar 0 en t2
etiq:              #Etiqueta
addi $t2, $t2, 1   #Incrementa el valor de t2 en 1
mul $t3, $t2, $t1  #Coloca en t3 el resultado de multiplicar t2 con t1
bge $t0, $t3, etiq #Salta hacia etiq si t0 es mayor que t3
subi $t2, $t2, 1   #Decrementar el valor de t2 en 1
mul $t3, $t2, $t1  #Coloca en t3 el resultado de multiplicar t2 con t1
move $v0, $t2      #Coloca en v0 el resultado
sub $v1, $t0, $t3  #Coloca en v1 el residuo de la división