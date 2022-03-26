.data

mtrue: .asciiz "True"
mfalse: .asciiz "False"
salto: .asciiz "\n"
cadena1: .asciiz "fizz"
cadena2: .asciiz "buzz"
indice: .word 0
otro: .word 0

.text

#Asignación
    li $v0, 1
    sw $v0, indice
while1:
#Menor
    lw $v0, indice
    move $a0, $v0
    subi $sp, $sp, 4
    sw $a0, ($sp)
    li $v0, 100
    move $a1, $v0
    lw $a0, ($sp)
    addi $sp, $sp, 4
    jal menor
    beqz $v0, fin_while1
do_while1:
#Asignación
    li $v0, 1
    sw $v0, otro
if1:
#Igualigual
#Modulo
    lw $v0, indice
    move $t1, $v0
    subi $sp, $sp, 4
    sw $t1, ($sp)
    li $v0, 3
    move $t2, $v0
    lw $t1, ($sp)
    addi $sp, $sp, 4
    rem $t0, $t1, $t2
    move $v0, $t0
    move $a0, $v0
    subi $sp, $sp, 4
    sw $a0, ($sp)
    li $v0, 0
    move $a1, $v0
    lw $a0, ($sp)
    addi $sp, $sp, 4
    jal igualigual
    bnez $v0, then1
    beqz $v0, else1
then1:
#Imprime
    la $v0, cadena1
    move $t3, $v0
    li $v0, 4
    move $a0, $t3
    syscall
    la $a0, salto
    li $v0, 4
    syscall
#Asignación
    li $v0, 0
    sw $v0, otro
else1:
if2:
#Igualigual
#Modulo
    lw $v0, indice
    move $t5, $v0
    subi $sp, $sp, 4
    sw $t5, ($sp)
    li $v0, 5
    move $t6, $v0
    lw $t5, ($sp)
    addi $sp, $sp, 4
    rem $t4, $t5, $t6
    move $v0, $t4
    move $a0, $v0
    subi $sp, $sp, 4
    sw $a0, ($sp)
    li $v0, 0
    move $a1, $v0
    lw $a0, ($sp)
    addi $sp, $sp, 4
    jal igualigual
    bnez $v0, then2
    beqz $v0, else2
then2:
#Imprime
    la $v0, cadena2
    move $t7, $v0
    li $v0, 4
    move $a0, $t7
    syscall
    la $a0, salto
    li $v0, 4
    syscall
#Asignación
    li $v0, 0
    sw $v0, otro
else2:
if3:
    lw $v0, otro
    bnez $v0, then3
    beqz $v0, else3
then3:
#Imprime
    lw $v0, indice
    move $t8, $v0
    li $v0, 1
    lw $a0, indice
    syscall
    la $a0, salto
    li $v0, 4
    syscall
else3:
#Asignación
#Suma
    lw $v0, indice
    move $s0, $v0
    subi $sp, $sp, 4
    sw $s0, ($sp)
    li $v0, 1
    move $s1, $v0
    lw $s0, ($sp)
    addi $sp, $sp, 4
    add $t9, $s0, $s1
    move $v0, $t9
    sw $v0, indice
    b while1
fin_while1:
    li $v0, 10
    syscall

# Subrutinas ############################################################################

igualigual:
    beq $a0, $a1, igualigual_true
    b igualigual_false
igualigual_true:
    li $v0, 1
    b fin_igualigual
igualigual_false:
    li $v0, 0
fin_igualigual:
    jr $ra

diferente:
    bne $a0, $a1, diferente_true
    b diferente_false
diferente_true:
    li $v0, 1
    b fin_diferente
diferente_false:
    li $v0, 0
fin_diferente:
    jr $ra

menor:
    blt $a0, $a1, menor_true
    b menor_false
menor_true:
    li $v0, 1
    b fin_menor
menor_false:
    li $v0, 0
fin_menor:
    jr $ra

mayor:
    bgt $a0, $a1, mayor_true
    b mayor_false
mayor_true:
    li $v0, 1
    b fin_mayor
mayor_false:
    li $v0, 0
fin_mayor:
    jr $ra

menorigual:
    ble $a0, $a1, menorigual_true
    b menorigual_false
menorigual_true:
    li $v0, 1
    b fin_menorigual
menorigual_false:
    li $v0, 0
fin_menorigual:
    jr $ra

mayorigual:
    bge $a0, $a1, mayorigual_true
    b mayorigual_false
mayorigual_true:
    li $v0, 1
    b fin_mayorigual
mayorigual_false:
    li $v0, 0
fin_mayorigual:
    jr $ra

negacion:
    beqz $a0, negacion_cero
    b negacion_uno
negacion_cero:
    li $v0, 1
    b fin_negacion
negacion_uno:
    li $v0, 0
fin_negacion:
    jr $ra

pow: #Función exponente $a0 elevado a la $a1
    subi $sp, $sp, 4
    sw $a1, ($sp)
    li $v0, 1
    beqz $a1, fin_pow #Si el exponente es 0, termina
loop_pow:
    mul $v0, $v0, $a0
    subi $a1, $a1, 1
    bgtz $a1, loop_pow
fin_pow:
    lw $a1, ($sp)
    addi $sp, $sp, 4
    jr $ra