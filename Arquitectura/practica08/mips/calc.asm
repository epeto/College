
#Programa que evalúa expresiones aritméticas.
#s0 guarda el tamaño de la entrada
#s1 guarda la dirección del primer elemento de la entrada
#s2 guarda un apuntador a la entrada (el caracter que se está leyendo)
#s6 guarda la dirección de la base de la pila
#s7 guarda la dirección del tope de la pila

.data

prompt: .asciiz "\nEscriba una expresión aritmética.\n"
cexit:  .asciiz "exit"
coperador: .asciiz "Operador "
cnumero: .asciiz "Numero "
cdesb: .asciiz "Desbordamiento aritmético, el número es mayor a 255\n"

stack:  .space 1024 #Pila.
input:  .space 1024 #Entrada al programa.

.text

main:
    la $s6, stack #s6 guarda la base de la pila
    la $s7, stack #s7 guarda el tope de la pila
    
    li $v0, 4      #Código para imprimir un string
    la $a0, prompt #Cargo la dirección del prompt en a0
    syscall
    
    li $v0, 8      #Código para leer texto
    la $a0, input  #Cargo la dirección del input en a0
    li $a1, 1024
    syscall        #Al final input tendrá el texto ingresado por el usuario
    la $a0, input
    jal borraSalto #Borra el salto de línea de input y lo cambia por \0
    
    # A partir de aquí comparamos si el comando es igual a "exit".
    la $a0, input
    jal str_len           #Se calcula el tamaño de la entrada.
    move $s0, $v0         #Movemos el resultado a s0 (porque s0 no se modifica)
    bne $s0, 4, continue1 #Si no tienen el mismo tamaño se continua con la ejecución.
    la $a0, input         #Cargamos el input en a0
    la $a1, cexit         #Cargamos la dirección de cexit en a1
    jal comparaCad        #Si tienen el mismo tamaño, veremos si cexit y comando son iguales.
    beq $v1, 1, exit      #Si las cadenas son iguales, saltamos a exit.
continue1:
    
    la $s1, input
    la $s2, input
    subi $s2, $s2, 2
forinput: #En esta parte analizamos y evaluamos la entrada.
    addi $s2, $s2, 2 #s2 apunta al siguiente caracter de input
    lb $a0, ($s2) #Cargamos el caracter en a0
    jal esNumero #Verifica si es número
    beq $v0, 1, enmain #Si es un número, saltamos a enmain
    lb $a0, ($s2) #Cargamos el caracter en a0
    jal esOperador #Verifica si es operador
    beq $v0, 1, eomain #Si es operador, saltamos a eomain
    teq $v0, $zero #Si no es número ni operador, genera una trampa
    b main
enmain: #Es número
    subi $a0, $a0, 48 #Le resto 48 porque en el código ascii los números van del 48 al 57
    jal push #Inserto el número en a0 en la pila
    b finForInput
eomain: #Es operador
    jal opera
finForInput:
    sub $t1, $s2, $s0 #Le resto a s2 el tamaño de la cadena
    addi $t1, $t1, 1 #Le sumo 1 a t1
    blt $t1, $s1, forinput #Si t1 es menor a s1, regresa a forinput

#En este punto, el resultado de la expresión debe estar al tope de la pila
#Si hay más de un elemento en la pila, la expresión está mal formada
    sub $t2, $s7, $s6
    li $t3, 2
    tge $t2, $t3 #Trampa si hay más de 1 elemento en la pila
    jal peek #Sacamos el resultado
    move $a0, $v0 #Movemos el resultado a a0
    li $v0, 1
    syscall

b main #Salto incondicional a main.
#Fin del main

############Funciones auxiliares#####################################################################################################

borraSalto:                      #Función que borra un salto de línea al final de una cadena
    move $t0, $a0                #cargamos la dirección de la cadena en t0
    subi $t0, $t0, 1             #Le restamos 1 a t0
    forSalto:
    addi $t0, $t0, 1
    lb $t1, ($t0)
    beq $t1, '\0', borraSaltoFin #Si ya es 0 no cambiamos nada.
    bne $t1, '\n', forSalto
    sb $zero, ($t0)              #Cambiamos el salto por \0
borraSaltoFin: jr $ra            #Se regresa a donde fue llamada la función

exit: #Se termina el programa
    li $v0, 10 #Código para terminar una ejecución
    syscall
#Aquí termina exit

str_len:            #Función que calcula el tamaño de una cadena cargada en a0
    move $v0, $zero # Inicializar el contador en cero
loopsl:
    lb $t0, ($a0)        # Cargar caracter
    beq	$t0, '\0', endsl # Si es el caracter nulo, fin
    addi $v0, $v0, 1     # Aumentar contador 1
    addi $a0, $a0, 1     # Aumentar apuntador 1
    j loopsl             # Revisar siguiente carcater
endsl:	jr $ra           # Regresa a donde fue llamado.
#Aquí termina str_len

comparaCad:                     #Función que coloca 1 en v1 si 2 cadenas son iguales y 0 si son diferentes.
    move $v1, $zero             #Si las cadenas son diferentes v1 tendrá el valor de 0
    for:
    lb $t1, ($a0)               #t1 apuntará a la cadena de a0
    lb $t2, ($a1)               #t2 apuntará a la cadena a1
    bne $t1, $t2, comparaCadFin #Si son diferentes, termina la función.
    addi $a0, $a0, 1            #Incrementamos a0
    addi $a1, $a1, 1            #Incrementamos a1
    bne $t2, '\0', for          #Si aun no llega al final de la cadena se regresa el for
    li $v1, 1                   #Si las cadenas son iguales v1 valdrá 1
comparaCadFin: jr $ra           #se regresa a donde fue llamada comparaCad

#Aquí las funciones para operar sobre la pila    
isEmpty: #Función que verifica si la pila está vacía
    beq $s6, $s7, ietrue
    b iefalse
    ietrue:
    li $v0, 1 #Si es vacía, pone 1 en v0
    b finIsEmpty
    
    iefalse:
    li $v0, 0 #Si no es vacía pone 0 en v0
    b finIsEmpty
    
finIsEmpty:
    jr $ra #Regresa a donde fue llamada
    
push: #Inserta un número al tope de la pila pasado en a0
    addi $s7, $s7, 1
    sb $a0, ($s7)
    jr $ra
    
pop: #Saca un número de la pila y lo coloca en v0
    lb $v0, ($s7)
    subi $s7, $s7, 1
    jr $ra
    
peek: #Coloca el tope de la pila en v0 sin reducir el tamaño de la pila
    lb $v0, ($s7)
    jr $ra
    
#Aquí terminan las funciones para operar sobre la pila

esNumero: #Toma un byte cargado en a0 y devuelve 1 en v0 si es un número, 0 en otro caso
    beq $a0,'0', entrue
    beq $a0,'1', entrue
    beq $a0,'2', entrue
    beq $a0,'3', entrue
    beq $a0,'4', entrue
    beq $a0,'5', entrue
    beq $a0,'6', entrue
    beq $a0,'7', entrue
    beq $a0,'8', entrue
    beq $a0,'9', entrue
    
    li $v0, 0
    b finEsNumero
    
    entrue:
    li $v0, 1
finEsNumero:
    jr $ra
    
esOperador: #Toma un byte cargado en a0 y devuelve 1 en v0 si es un operador, 0 en otro caso
    beq $a0,'+', eotrue
    beq $a0,'-', eotrue
    beq $a0,'*', eotrue
    beq $a0,'/', eotrue
    
    li $v0, 0
    b finEsOperador
    
    eotrue:
    li $v0, 1
finEsOperador:
    jr $ra

opera: #Toma los 2 números al tope de la pila, el operador pasado en a0, realiza la operación y coloca el resultado en la pila
    subi $sp, $sp, 8 #Reservamos espacio en el stack
    sw $ra, ($sp) #Guardamos el ra en el stack
    sb $a0, 4($sp) #Guardamos el argumento en el stack
    jal isEmpty #Comprobamos si está vacía
    beq $v0, 1, ex_opera #Si la pila está vacía, genera una excepción
    jal pop
    move $t0, $v0 #Guardamos el primer operando en t0
    jal isEmpty #Comprobamos si está vacía
    beq $v0, 1, ex_opera #Si la pila está vacía, genera una excepción
    jal pop
    move $t1, $v0 #Guardamos el segundo operando en t1
    
    beq $a0,'+', suma
    beq $a0,'-', resta
    beq $a0,'*', multiplica
    beq $a0,'/', divide
suma:
    add $t2, $t1, $t0
    b finOpera
resta:
    sub $t2, $t1, $t0
    b finOpera
multiplica:
    mul $t2, $t1, $t0
    b finOpera
divide:
    div $t2, $t1, $t0
    b finOpera

finOpera:
    bge $t2, 256, desbordamiento #Si el resultado es mayor a 255 causa desbordamiento
    move $a0, $t2 #Movemos el resultado de la operación a a0
    jal push #Guardamos a0 en la pila
    lw $ra, ($sp) #Restaurar ra
    lb $a0, 4($sp) #Restaurar a0
    addi $sp, $sp, 8 #Restaurar el sp
    jr $ra #regresa
    
ex_opera:
    teq $zero, $zero #Genera una trampa
#Fin de opera

desbordamiento:
    la $a0, cdesb
    li $v0, 4
    syscall
    b main

#Aquí se define el manejo de errores ################################################################################################
.kdata

expr_inv: .asciiz "Expresión inválida\n"
div_zero: .asciiz "División entre 0\n"
desb: .asciiz "Desbordamiento aritmético\n"

.ktext 0x80000180

    mfc0 $k0, $13 # Obtener la causa de la excepción del registro del coproc 0
    andi $k0, $k0, 0x7C # Obtener solo la causa (sin excepciones pendientes) con la mascara 0x7C
    beq	$k0, 0x24, ex_z #División entre 0
    beq	$k0, 0x30, ex_d #Desbordamiento
    beq	$k0, 0x34, ex_t #Trampa
    
ex_z:
    la $a0, div_zero
    j ex_end

ex_d:
    la $a0, desb
    j ex_end

ex_t:
    la $a0, expr_inv

ex_end:
    li	$v0, 4			
    syscall
    la $k0, main #Cargamos la dirección del main
    mtc0 $k0, $14 # Guardar direccion en EPC
    eret # Fin del manejador de excepciones