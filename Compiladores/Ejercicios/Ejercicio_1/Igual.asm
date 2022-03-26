# Programa que verifica si 2 números son iguales.

.data
    mensaje1: .asciiz "Escribe un número\n"
    mensaje2: .asciiz "Escribe otro número\n"
    mensaje3: .asciiz "Los números no son iguales :(\n"
    mensaje4: .asciiz "Los números son iguales :)\n"

.text
main:
    #Pide el primer número
    la $a0, mensaje1
    li $v0, 4
    syscall
    
    #Lee el primer número y lo guarda en s0
    li $v0, 5
    syscall
    move $s0, $v0
    
    #Pide el segundo número
    la $a0, mensaje2
    li $v0, 4
    syscall
    
    #Lee el segundo número y lo guarda en s1
    li $v0, 5
    syscall
    move $s1, $v0
    
    move $a0, $s0
    move $a1, $s1
    
    jal iguales #Comprobamos si son iguales
    
    beqz $v0, no_iguales
    b si_iguales
    
no_iguales:
    la $a0, mensaje3
    li $v0, 4
    syscall
    b finmain
    
si_iguales:
    la $a0, mensaje4
    li $v0, 4
    syscall
    b finmain
    
finmain:
    li $v0, 10
    syscall
    
 
iguales: #Función que comprueba si 2 números son iguales (pasados en a0 y a1)
    beq $a0, $a1, true
    b false
true:
    li $v0, 1 #Si son iguales, v0 tendrá 1
    b fin_iguales
false:
    li $v0, 0 #Si son diferentes, v0 tendrá 0
    b fin_iguales
fin_iguales:
    jr $ra
# Aquí termina la función
