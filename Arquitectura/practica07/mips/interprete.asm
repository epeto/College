
#Intérprete de comandos
#s0 guardará el número de letras en el comando.
#s1 guardará el número de palabras ingresadas.

.data

prompt:     .asciiz "\nEscriba un comando\n"
err_com:    .asciiz "No existe ese comando\n"
opciones:   .asciiz "Comandos disponibles:\nrev\ncat\nexit\ncmp\n"
rev_prompt: .asciiz "Escriba una cadena de caracteres\n"
err_archivo:.asciiz "No existe el archivo\n"
iguales:    .asciiz "Los archivos son iguales\n"
diferentes: .asciiz "Los archivos son diferentes en el byte "

cexit: .asciiz "exit"
chelp: .asciiz "help"
crev:  .asciiz "rev"
ccat:  .asciiz "cat"
ccmp:  .asciiz "cmp"

descrev:  .asciiz "Imprime la reversa de una cadena.\nSi recibe el nombre de un archivo aplica la función sobre el contenido de este\nSi se ejecuta sin argumentos se aplica sobre la siguiente línea\n"
desccat:  .asciiz "Concatena los archivos que recibe como argumentos\n"
descexit: .asciiz "Sale del programa\n"
desccmp:  .asciiz "Compara 2 archivos y muestra el primer byte en el que difieren.\n"

comando: .space 100  #Primera palabra
arg0:    .space 100  #Segunda palabra
arg1:    .space 100  #Tercera palabra
input:   .space 150  #Espacio donde se va a guardar el input
archivo1:.space 1024 #Espacio donde se guardará el contenido del archivo1.
archivo2:.space 1024 #Espacio donde se guardará el contenido del archivo2.

input_rev: .space 1024 #Espacio para guardar el input de la reversa.
res_rev:   .space 1024 #Espacio para guardar el resultado de la reversa.

.text
main: #Función principal

    li $v0, 4      #Código para imprimir un string
    la $a0, prompt #Cargo la dirección del prompt en a0
    syscall

    li $v0, 8      #Código para leer texto
    la $a0, input  #Cargo la dirección del input en a0
    li $a1, 100
    syscall        #Al final input tendrá el texto ingresado por el usuario
    la $a0, input
    jal borraSalto #Borra el salto de línea de input y lo cambia por \0
    jal split      #Separa el input por espacios y guarda el resultado en: comando, arg0, arg1
    move $s1, $v1  #v1 tendrá el número de palabras del input. Lo copiamos a s1 porque este registro no cambia.
    
# A partir de aquí comparamos si el comando es igual a "exit".
    la $a0, comando
    jal str_len           #Se calcula el tamaño del comando
    move $s0, $v0         #Movemos el resultado a s0 (porque s0 no se modifica)
    bne $s0, 4, continue1 #Si no tienen el mismo tamaño prueba con el siguiente comando.
    la $a0, comando       #Cargamos el comando en a0
    la $a1, cexit         #Cargamos la dirección de cexit en a1
    jal comparaCad        #Si tienen el mismo tamaño, veremos si cexit y comando son iguales.
    beq $v1, 1, exit      #Si las cadenas son iguales, saltamos a exit.
continue1:

# A partir de aquí comparamos si comando es igual a "help"
    bne $s0, 4, continue2     #Si no tienen el mismo tamaño continúa la ejecución.
    la $a0, comando           #Cargamos el comando en a0
    la $a1, chelp             #Cargamos la dirección de chelp en a1
    jal comparaCad            #Si tienen el mismo tamaño veremos si son iguales
    beq $v1, $zero, continue2 #Si las cadenas son diferentes comparamos con el siguiente comando
    jal help                  #Si las cadenas son iguales ejecutamos help
    b main                    #Salto incondicional a main
continue2:

# A partir de aquí comparamos si comando es igual a "rev"
    bne $s0, 3, continue3     #Si no tienen el mismo tamaño continúa la ejecución.
    la $a0, comando           #Cargamos el comando en a0
    la $a1, crev              #Cargamos la dirección de crev en a1
    jal comparaCad            #Si tienen el mismo tamaño veremos si son iguales
    beq $v1, $zero, continue3 #Si las cadenas son diferentes comparamos con el siguiente comando
    jal rev                   #Si las cadenas son iguales ejecutamos rev
    b main                    #Salto incondicional a main
continue3:

# A partir de aquí comparamos si comando es igual a "cat"
    bne $s0, 3, continue4     #Si no tienen el mismo tamaño continúa la ejecución.
    la $a0, comando           #Cargamos el comando en a0
    la $a1, ccat              #Cargamos la dirección de ccat en a1
    jal comparaCad            #Si tienen el mismo tamaño veremos si son iguales
    beq $v1, $zero, continue4 #Si las cadenas son diferentes comparamos con el siguiente comando
    jal cat                   #Si las cadenas son iguales ejecutamos cat
    b main                    #Salto incondicional a main
continue4:
    
# A partir de aquí comparamos si comando es igual a "cmp"
    bne $s0, 3, continue5     #Si no tienen el mismo tamaño continúa la ejecución.
    la $a0, comando           #Cargamos el comando en a0
    la $a1, ccmp              #Cargamos la dirección de ccat en a1
    jal comparaCad            #Si tienen el mismo tamaño veremos si son iguales
    beq $v1, $zero, continue5 #Si las cadenas son diferentes comparamos con el siguiente comando
    jal cmp                   #Si las cadenas son iguales ejecutamos cmp
    b main                    #Salto incondicional a main
continue5:
    jal error_comando        #Si ningun comando hizo match, entonces es un error.
    b main                   #Salto incondicional a main

# Aquí termina la función main ###############################################################################################
    
exit:          #Se termina el programa
    li $v0, 10 #Código para terminar una ejecución
    syscall
#Aquí termina exit

help:
    beq $s1, 1, help1 #Si solo es 1 palabra, salta a help1.
    b help2           #Si es más de 1 palabra, salta a help2.

rev:
    beq $s1, 1, rev1 #Si es solo 1 palabra, salta a rev1.
    b rev2           #Si es más de 1 palabra, salta a rev2.
    
cat:
    subi $sp, $sp, 12 #Reservamos 12 espacios en el stack
    sw $ra, ($sp)     #Guardo ra en el stack
    sw $s0, 4($sp)    #Guardo s0 en el stack
    sw $s1, 8($sp)    #Guardo s1 en el stack
    
    #Abrir archivo
    la $a0, arg0               # Cargar direccion de cadena con nombre del archivo
    li $a1, 0                  # Cargar bandera 0, solo lectura
    li $v0, 13                 # Cargar codigo de llamada para abrir archivo
    syscall                    # Abre
    beq	$v0, -1, error_archivo # No existe el archivo
    move $s0, $v0              #Movemos el descriptor de archivo al registro fijo s0
    
    #Leer archivo
    li $v0, 14       #Comando para leer archivo
    move $a0, $s0    #pasamos el descriptor de archivo
    la $a1, archivo1 #Donde voy a guardar el contenido del archivo.
    li $a2, 1024     #Máximo número de caracteres que se pueden leer.
    syscall          #Lee
    move $t0, $v0    #Movemos el número de caracteres leidos a t0
    move $s1, $t0    #s1 tendrá el número de caracteres leidos
    la $t1, archivo1
    add $t2, $t1, $t0
    sb $zero, ($t2)  #Colocamos caracter nulo al final
    
    #Cierra el archivo
    move $a0, $s0 # Cargar descriptor
    li $v0, 16	  # Cargar codigo de llamada para cerrar archivo
    syscall       #Cierra
    
    #Abrir archivo
    la $a0, arg1               # Cargar direccion de cadena con nombre del archivo
    li $a1, 0                  # Cargar bandera 0, solo lectura
    li $v0, 13                 # Cargar codigo de llamada para abrir archivo
    syscall                    # Abre
    beq	$v0, -1, error_archivo # No existe el archivo
    move $s0, $v0              #Movemos el descriptor de archivo al registro fijo s0
    
    #Leer archivo
    li $v0, 14       #Comando para leer archivo
    move $a0, $s0    #pasamos el descriptor de archivo
    la $a1, archivo2 #Donde voy a guardar el contenido del archivo.
    li $a2, 1024     #Máximo número de caracteres que se pueden leer.
    syscall          #Lee
    move $t0, $v0    #Movemos el número de caracteres leidos a t0
    move $s1, $t0    #s1 tendrá el número de caracteres leidos
    la $t1, archivo2
    add $t2, $t1, $t0
    sb $zero, ($t2)  #Colocamos caracter nulo al final
    
    #Cierra el archivo
    move $a0, $s0 # Cargar descriptor
    li $v0, 16	  # Cargar codigo de llamada para cerrar archivo
    syscall       #Cierra
    
    #Imprimimos el contenido de los archivos.
    la $a0, archivo1
    jal imprimeCadena
    la $a0, archivo2
    jal imprimeCadena
    
    #Restauramos las variables usadas
    lw $ra, ($sp)
    lw $s0, 4($sp)
    lw $s1, 8($sp)
    addi $sp, $sp, 12
    jr $ra #Termina la función

cmp:
    subi $sp, $sp, 20 #Reservamos 20 espacios en el stack
    sw $ra, ($sp)     #Guardo ra en el stack
    sw $s0, 4($sp)    #Guardo s0 en el stack
    sw $s1, 8($sp)    #Guardo s1 en el stack
    sw $s2, 12($sp)   #Guardo s2 en el stack
    sw $s3, 16($sp)   #Guardo s3 en el stack
    
    #Abrir archivo
    la $a0, arg0               # Cargar direccion de cadena con nombre del archivo
    li $a1, 0                  # Cargar bandera 0, solo lectura
    li $v0, 13                 # Cargar codigo de llamada para abrir archivo
    syscall                    # Abre
    beq	$v0, -1, error_archivo # No existe el archivo
    move $s0, $v0              #Movemos el descriptor de archivo al registro fijo s0
    
    #Leer archivo
    li $v0, 14       #Comando para leer archivo
    move $a0, $s0    #pasamos el descriptor de archivo
    la $a1, archivo1 #Donde voy a guardar el contenido del archivo.
    li $a2, 1024     #Máximo número de caracteres que se pueden leer.
    syscall          #Lee
    move $t0, $v0    #Movemos el número de caracteres leidos a t0
    move $s1, $t0    #s1 tendrá el número de caracteres leidos
    la $t1, archivo1
    add $t2, $t1, $t0
    sb $zero, ($t2)  #Colocamos caracter nulo al final
    
    #Cierra el archivo
    move $a0, $s0 # Cargar descriptor
    li $v0, 16	  # Cargar codigo de llamada para cerrar archivo
    syscall       #Cierra
    
    #Abrir archivo
    la $a0, arg1               # Cargar direccion de cadena con nombre del archivo
    li $a1, 0                  # Cargar bandera 0, solo lectura
    li $v0, 13                 # Cargar codigo de llamada para abrir archivo
    syscall                    # Abre
    beq	$v0, -1, error_archivo # No existe el archivo
    move $s0, $v0              #Movemos el descriptor de archivo al registro fijo s0
    
    #Leer archivo
    li $v0, 14       #Comando para leer archivo
    move $a0, $s0    #pasamos el descriptor de archivo
    la $a1, archivo2 #Donde voy a guardar el contenido del archivo.
    li $a2, 1024     #Máximo número de caracteres que se pueden leer.
    syscall          #Lee
    move $t0, $v0    #Movemos el número de caracteres leidos a t0
    move $s1, $t0    #s1 tendrá el número de caracteres leidos
    la $t1, archivo2
    add $t2, $t1, $t0
    sb $zero, ($t2)  #Colocamos caracter nulo al final
    
    #Cierra el archivo
    move $a0, $s0 # Cargar descriptor
    li $v0, 16	  # Cargar codigo de llamada para cerrar archivo
    syscall       #Cierra
    
    #Calculamos el tamaño de las cadenas.
    la $a0, archivo1
    move $s2, $a0 #s2 guardará la dirección de archivo1
    jal str_len
    move $s0, $v0 #s0 guardará el tamaño del archivo1
    la $a0, archivo2
    move $s3, $a0 #s3 guardará la dirección del archivo2
    jal str_len
    move $s1, $v0 #s1 guardará el tamaño del archivo2
    
    bne $s0, $s1, contcmp   #Si tienen el mismo tamaño, comprobaremos si son iguales.
    la $a0, archivo1
    la $a1, archivo2
    jal comparaCad          #Comparamos los archivos.
    beq $v1, 1, cmp_iguales #Si son iguales, saltamos a cmp_iguales
    
contcmp:
    li $t0, 0 #Iniciamos el contador en 0
    subi $s2, $s2, 1
    subi $s3, $s3, 1
loopcmp:
    addi $s2, $s2, 1
    addi $s3, $s3, 1
    addi $t0, $t0, 1
    lb $t1, ($s2)
    lb $t2, ($s3)
    beq $t1, $t2, loopcmp #Si los caracteres son iguales, hacemos loop
    
    la $a0, diferentes
    jal imprimeCadena
    
    move $a0, $t0
    li $v0, 1
    syscall #Imprimimos el número que estaba en t0
    
    lw $ra, ($sp)     #Restaurar ra
    lw $s0, 4($sp)    #Restaurar s0
    lw $s1, 8($sp)    #Restaurar s1
    lw $s2, 12($sp)   #Restaurar s2
    lw $s3, 16($sp)   #Restaurar s3
    addi $sp, $sp, 20 #Restaurar sp
    jr $ra            #Termina
    
error_comando:       #Se ejecuta cuando ningun comando hace match.
    subi $sp, $sp, 8 #Reservamos 8 espacios en el stack
    sw $v0, ($sp)    #Guardo v0 en el stack
    sw $a0, 4($sp)   #Guardo a0 en el stack
    li $v0, 4        #Código para imprimir un string
    la $a0, err_com  #Cargo la dirección del prompt en a0
    syscall
    lw $v0, ($sp)    #Restauramos v0
    lw $a0, 4($sp)   #Restauramos a0
    addi $sp, $sp, 8 #Restauramos sp
    jr $ra           #Regresamos
    
# Funciones auxuliares #######################################################################################################

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

split: #Función que separa el input por espacios.
    move $v1, $zero           #Al final, s1 tendrá el número de parámetros pasados.
    la $t0, input             #t0 tendrá el apuntador al input
    la $t1, comando           #Apuntador a comando
    la $t2, arg0              #Apuntador a arg0
    la $t3, arg1              #Apuntador a arg1
    subi $t0, $t0, 1
    subi $t1, $t1, 1
    addi $v1, $v1, 1          #incrementa en 1 el contador de parámetros.
loopsplit1:                   #Pasamos la primera palabra de input a comando
    addi $t0, $t0, 1
    addi $t1, $t1, 1
    lb $t4, ($t0)
    sb $t4, ($t1)             #Copiamos t0 a t1
    beq $t4, '\0', finsplit   #Si es 0 terminamos el split.
    bne $t4, ' ' , loopsplit1 #Mientras no sea espacio, regresamos.
    li $t4, '\0'
    sb $t4, ($t1)             #Cambiamos el espacio por \0
    
    addi $v1, $v1, 1          #incrementa en 1 el contador de parámetros.
    subi $t2, $t2, 1
loopsplit2:                   #Pasamos la segunda palabra de input a arg0
    addi $t0, $t0, 1
    addi $t2, $t2, 1
    lb $t4, ($t0)
    sb $t4, ($t2)             #Copiamos t0 a t2
    beq $t4, '\0', finsplit   #Si es 0 terminamos el split.
    bne $t4, ' ' , loopsplit2 #Mientras no sea espacio, regresamos.
    li $t4, '\0'
    sb $t4, ($t2)             #Cambiamos el espacio por \0
    
    addi $v1, $v1, 1          #incrementa en 1 el contador de parámetros.
    subi $t3, $t3, 1
loopsplit3:                   #Pasamos la tercera palabra de input a arg1
    addi $t0, $t0, 1
    addi $t3, $t3, 1
    lb $t4, ($t0)
    sb $t4, ($t3)             #Copiamos t0 a t2
    beq $t4, '\0', finsplit   #Si es 0 terminamos el split.
    bne $t4, ' ' , loopsplit3 #Mientras no sea espacio, regresamos.
    li $t4, '\0'
    sb $t4, ($t3)             #Cambiamos el espacio por \0
    
finsplit: jr $ra

imprimeCadena: #Imprime la cadena que se pase en el parámetro a0
    li $v0, 4  #Código para imprimir un string
    syscall
    jr $ra

help1: #Se ejecuta si help se llama sin argumentos.
    li $v0, 4        #Código para imprimir un string
    la $a0, opciones #Para imprimir las opciones.
    syscall
    jr $ra           #Regresamos a donde fue llamado help.
    
help2: #Se ejecuta si help se llama con argumentos.
    subi $sp, $sp, 8       #Reservamos espacio en el stack.
    sw $ra, ($sp)          #Guardamos el ra en el stack.
    sw $s0, 4($sp)         #Guardamos s0 en el stack.
    la $a0, arg0
    jal str_len            #Se calcula el tamaño del comando
    move $s0, $v0          #El tamaño de arg0 se guardará en s0
    bne $s0, 4, helpc1     #Si no tienen el mismo tamaño continúa la ejecución.
    la $a0, arg0           #Cargamos el comando en a0
    la $a1, cexit          #Cargamos la dirección de cexit en a1
    jal comparaCad         #Si tienen el mismo tamaño veremos si son iguales
    beq $v1, $zero, helpc1 #Si las cadenas son diferentes comparamos con el siguiente comando
    la $a0, descexit       #Cargamos la descripción de exit en a0
    jal imprimeCadena      #Imprime la descripción de exit
    b finhelp2
helpc1:
    bne $s0, 3, helpc2     #Si no tienen el mismo tamaño continúa la ejecución.
    la $a0, arg0           #Cargamos el comando en a0
    la $a1, crev           #Cargamos la dirección de crev en a1
    jal comparaCad         #Si tienen el mismo tamaño veremos si son iguales
    beq $v1, $zero, helpc2 #Si las cadenas son diferentes comparamos con el siguiente comando
    la $a0, descrev        #Cargamos la descripción de rev en a0
    jal imprimeCadena      #Imprime la descripción de rev
    b finhelp2
helpc2:
    bne $s0, 3, helpc3     #Si no tienen el mismo tamaño continúa la ejecución.
    la $a0, arg0           #Cargamos el comando en a0
    la $a1, ccat           #Cargamos la dirección de ccat en a1
    jal comparaCad         #Si tienen el mismo tamaño veremos si son iguales
    beq $v1, $zero, helpc3 #Si las cadenas son diferentes comparamos con el siguiente comando
    la $a0, desccat        #Cargamos la descripción de cat en a0
    jal imprimeCadena      #Imprime la descripción de cat
    b finhelp2
helpc3:
    bne $s0, 3, helpc4     #Si no tienen el mismo tamaño continúa la ejecución.
    la $a0, arg0           #Cargamos el comando en a0
    la $a1, ccmp           #Cargamos la dirección de ccmp en a1
    jal comparaCad         #Si tienen el mismo tamaño veremos si son iguales
    beq $v1, $zero, helpc4 #Si las cadenas son diferentes comparamos con el siguiente comando
    la $a0, desccmp        #Cargamos la descripción de cmp en a0
    jal imprimeCadena      #Imprime la descripción de cmp
    b finhelp2
helpc4:
    jal error_comando
finhelp2:
    lw $ra, ($sp)          #Restauramos ra.
    lw $s0, 4($sp)         #Restauramos s0.
    addi $sp, $sp, 8       #Restauramos el sp.
    jr $ra                 #Regresamos
#Aquí termina help2

reversa: #Funcion que calcula la reversa de una cadena pasada en a0.
    la $t0, res_rev       #Cargamos la dirección de res_rev en t0
loopreversa:
    subi $a1, $a1, 1      #Le restamos 1 a a1
    add $t1, $a0, $a1
    lb $t2, ($t1)
    sb $t2, ($t0)
    addi $t0, $t0, 1      #Le sumamos 1 a t0
    bnez $a1, loopreversa #Si a1 no es 0, regresamos.
    li $t3, '\0'
    sb $t3, ($t0)
    jr $ra                #Termina la función
    
rev1: #Se llama si rev se ejecuta sin argumentos.
    subi $sp, $sp, 4   #Reserva espacio en el stack.
    sw $ra, ($sp)      #Guarda el ra en el stack.
    la $a0, rev_prompt #Cargamos el prompt de rev en a0
    jal imprimeCadena  #Imprimimos el prompt de rev.
    li $v0, 8          #Código para leer texto
    la $a0, input_rev  #Cargo la dirección del input en a0
    li $a1, 1024       #Tamaño máximo del input
    syscall            #Al final input_rev tendrá el texto ingresado por el usuario
    la $a0, input_rev  #cargo el argumento "input_rev" en a0
    jal str_len        #Calculamos el tamaño de input_rev
    move $a1, $v0      #Pasamos el tamaño a a1
    la $a0, input_rev  #Cargamos el argumento
    jal reversa        #Calcula la reversa de input_rev y la guarda en res_rev.
    la $a0, res_rev    #Cargamos el argumento res_rev en a0
    jal imprimeCadena  #Imprime el resultado de la reversa.
    lw $ra, ($sp)      #Restauramos ra
    addi $sp, $sp, 4   #Restauramos el stack pointer.
    jr $ra             #Regresamos
    
rev2: #Se llama si rev se ejecuta con argumentos.
    subi $sp, $sp, 12 #Reservamos espacio en el stack
    sw $ra, ($sp)     #Guardamos el ra
    sw $s0, 4($sp)    #Guardamos s0
    sw $s1, 8($sp)    #Guardamos s1
    
    #Abrir archivo
    la $a0, arg0               # Cargar direccion de cadena con nombre del archivo
    li $a1, 0                  # Cargar bandera 0, solo lectura
    li $v0, 13                 # Cargar codigo de llamada para abrir archivo
    syscall                    # Abre
    beq	$v0, -1, error_archivo # No existe el archivo
    move $s0, $v0              #Movemos el descriptor de archivo al registro fijo s0
    
    #Leer archivo
    li $v0, 14        #Comando para leer archivo
    move $a0, $s0     #pasamos el descriptor de archivo
    la $a1, input_rev #Donde voy a guardar el contenido del archivo.
    li $a2, 1024      #Máximo número de caracteres que se pueden leer.
    syscall           #Lee
    move $t0, $v0     #Movemos el número de caracteres leidos a t0
    move $s1, $t0     #s1 tendrá el número de caracteres leidos
    la $t1, input_rev
    add $t2, $t1, $t0
    sb $zero, ($t2)   #Colocamos caracter nulo al final
    
    #Invertimos el contenido
    la $a0, input_rev #Argumento 1
    move $a1, $s1     #Argumento 2
    jal reversa       #Calcula la reversa de input_rev y la guarda en res_rev.
    
    #Imprimimos el resultado
    la $a0, res_rev
    jal imprimeCadena
    
    #Cierra el archivo
    move $a0, $s0 # Cargar descriptor
    li $v0, 16	  # Cargar codigo de llamada para cerrar archivo
    syscall       #Cierra
    
    #Restauramos las variables usadas
    lw $ra, ($sp)
    lw $s0, 4($sp)
    lw $s1, 8($sp)
    addi $sp, $sp, 12
    jr $ra #Termina la función
    
error_archivo:
    li $v0, 4
    la $a0, err_archivo
    syscall
    b main

cmp_iguales:
    la $a0, iguales
    jal imprimeCadena
    lw $ra, ($sp)     #Restaurar ra
    lw $s0, 4($sp)    #Restaurar s0
    lw $s1, 8($sp)    #Restaurar s1
    lw $s2, 12($sp)   #Restaurar s2
    lw $s3, 16($sp)   #Restaurar s3
    addi $sp, $sp, 20 #Restaurar sp
    jr $ra            #Termina
