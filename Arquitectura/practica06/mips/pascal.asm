#Programa que calcula la identidad de Pascal

.data
n: .word 10
k: .word 5

.text
main:
    #Preambulo
    subi $sp, $sp, 24 # Reservar memoria para el marco
    sw $ra, 16($sp) # Guardar $ra
    sw $fp, 20($sp) # Guardar $fp
    addi $fp, $sp, 20 # Establecer $fp
    lw $s0, n
    lw $s1, k
    blt $s0, $s1, finmain # Si n < k se termina el programa
    move $a0, $s0 # Pasamos el parámetro 1
    move $a1, $s1 # Pasamos el parámetro 2
    #Invocación
    jal pascal
    # Conlcusion
    lw $ra, 16($sp) # Restaurar $ra
    lw $fp, 20($sp) # Restaurar $fp
    j finmain # Final del programa
    
pascal:
    #Preambulo
    subi $sp, $sp, 32 # Reservar memoria para el marco
    sw $ra, 16($sp) # Guardar $ra
    sw $fp, 20($sp) # Guardar $fp
    addi $fp, $sp, 28 # Establecer $fp
    #Tarea
    beqz $a1, caso_1 #Si k es 0 pasamos al caso base 1
    beqz $a0, caso_2 #Si n es 0 pasamos al caso base 2
    sw $a0, ($sp) #Guardamos el argumento 1 en la pila
    sw $a1, 4($sp) #Guardamos el argumento 2 en la pila
    #Invocacion
    subi $a0, $a0, 1
    subi $a1, $a1, 1
    jal pascal #Hacemos la primera llamada recursiva
    #Después de la llamada recursiva restablececmos los valores de a0 y a1
    lw $a0, ($sp)
    lw $a1, 4($sp)
    #Movemos el resultado de esa función a t0
    move $t0, $v0 #v0 contiene el valor de retorno
    sw $t0, 24($sp) #Guardamos el resultado en la pila
    #Segunda invocación
    subi $a0, $a0, 1
    jal pascal
    #Después de la llamada restablecemos t0
    lw $t0, 24($sp)
    add $t0, $t0, $v0 #Sumamos los resultados
    b end #Termina la función

caso_1:
    li $t0, 1 #Retorna 1
    b end
    
caso_2:
    li $t0, 0 #Retorna 0
    
end: #Final de la función
    move $v0, $t0	# Valores de retorno
    lw $ra, 16($sp)	# Restaurar $ra
    lw $fp, 20($sp)	# Restaurar $fp
    addi $sp, $sp, 32	# Eliminar el marco 
    jr $ra		# Regresa a donde fue llamado
    
finmain: nop #Termina el programa