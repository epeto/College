#Programa calcula la potencia de y elevado a la x

        .data
x:      .word   5
y:      .word   4
        .text
main:   # Preambulo main
        subi $sp, $sp, 24    # 16 bytes para 4 argumentos + 8 para el ra y el fp
        sw $ra, 16($sp)	     # Guardar $ra
	sw $fp, 20($sp)	     # Guardar $fp
	addi $fp, $sp, 20    # Establecer $fp
        # Invocacion de mist_1
        lw $a0, x            # Se carga en a0 el valor de x
        lw $a1, y            # Se carga en a1 el valor de y
        jal mist_1
        # Retorno de mist_1
        # Conclusion main
        lw	$ra, 16($sp) # Restaurar $ra
	lw	$fp, 20($sp) # Restaurar $fp
        j finmain
# mist_1 recibe como argumentos $a0 y $a1
mist_1: # Preambulo mist_1
        subi $sp, $sp, 28    #Reservamos 16 bytes para 4 argumentos, 4 para fp, 4 para ra y 4 para s0
        sw $s0, 16($sp)      #Guardamos el valor de s0 en el stack
        sw $ra, 20($sp)      #Guardamos el valor de ra en el stack
        sw $fp, 24($sp)      #Guardamos el valor de fp en el stack
        addi $fp, $sp, 24    #Tamaño del marco
        move    $s0, $a0
        move    $t0, $a1
        li      $t1, 1
loop_1: beqz $s0, end_1
        # Invocación de mist_0
        move    $a0, $t0     # Se pasa el argumento $a0
        move    $a1, $t1     # Se pasa el argumento $a1
        jal mist_0
        # Retorno de mist_0
        move    $t1, $v0
        subi    $s0, $s0, 1
        j       loop_1
end_1:  # Conclusion mist_1
        move    $v0, $t1     # Se retorna el resultado en $v0
        lw $s0, 16($sp)      # Restablecer s0
        lw $ra, 20($sp)      # Restablecer ra
        lw $fp, 24($sp)      # Restablecer fp
        addi $sp, $sp, 28    # Restablecer sp
        jr $ra               #Para regresar a donde fue invocado mist_1
# mist_0 recibe como argumentos $a0 y $a1
mist_0: # Preambulo mist_0
        mult    $a0, $a1     #Multiplica a0 por a1 y guarda el resultado en el registro lo
        # Conclusion mist_0
        mflo    $v0          # Se retorna el resultado en $v0
	jr $ra               # Para regresar a donde fue invocado mist_0
finmain: nop