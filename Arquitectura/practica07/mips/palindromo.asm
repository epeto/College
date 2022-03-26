
#Función que dice si una cadena es palíndromo o no.
.data

input:  .space 1024 #Espacio para guardar el input de la reversa.
res:    .space 1024 #Espacio para guardar el resultado de la reversa.
prompt: .asciiz "Escriba una cadena de caracteres\n"
true:   .asciiz "\nEs palíndromo"
false:  .asciiz "\nNo es palíndromo"

.text
main:
    li $v0, 4      #Código para imprimir un string
    la $a0, prompt #Cargo la dirección del prompt en a0
    syscall
    
    li $v0, 8      #Código para leer texto
    la $a0, input  #Cargo la dirección del input en a0
    li $a1, 1024
    syscall        #Al final input tendrá el texto ingresado por el usuario
    
    la $a0, input
    jal borraSalto #Borra el salto de línea de input y lo cambia por \0
    
    la $a0, input
    jal str_len
    move $s0, $v0
    
    la $a0, input
    move $a1, $s0
    jal reversa #Calculamos la reversa de input
    
    #Borrar desde aquí
    la $a0, res
    li $v0, 4
    syscall
    #Hasta aquí
    
    la $a0, input
    la $a1, res
    jal comparaCad #Comparamos si input y res son iguales
    
    beq $v1, 1, imptrue
    beq $v1, 0, impfalse
    
imptrue:
    la $a0, true
    li $v0, 4
    syscall
    b finmain
    
impfalse:
    la $a0, false
    li $v0, 4
    syscall
    b finmain
    
finmain:
    li $v0, 10
    syscall
    
reversa: #Funcion que calcula la reversa de una cadena pasada en a0.
    la $t0, res           #Cargamos la dirección de res en t0
loopreversa:
    subi $a1, $a1, 1      #Le restamos 1 a a1
    add $t1, $a0, $a1
    lb $t2, ($t1)
    sb $t2, ($t0)
    addi $t0, $t0, 1      #Le sumamos 1 a t0
    bnez $a1, loopreversa #Si a1 no es 0, regresamos.
    sb $zero, ($t0)
    jr $ra                #Termina la función
    
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