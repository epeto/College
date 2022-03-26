#serie
.data 
m: .word 1000		#numero de iteraciones 
.text
li $t0 , 0		# contador empieza en n = 0
lw $t1 , m		# cargamos m en t1
li $t2 , 2		# cargamos t2 = 2
li $t3 , 1		# cargamos t3 = 1	
li  $t4 , 4		# cargamos t4 = 4
mtc1 $zero , $f0	# hacemos f0 = 0
mtc1 $t3 , $f1 		# hacemos que f1 = t3 = 1
mtc1 $t4 , $f7		# hacemos que f7 = t4 = 4
loop:	
bgt $t0 , $t1 , end	# si n > m salimos del loop
div $t0 , $t2		# dividimos n/2
mfhi $t5		# tomamos el residuo de la division anterior
if:
beqz $t5 , else 	# si el reiduo es cero saltamos al else 
li $t5 , -1		# si n es impar t5 = -1
j conti			# saltamos a conti para evitar el else
else:
li $t5 , 1		# si n es par t5 = 1
conti:
mult $t0 , $t2		# hacemos la operacion 2*n
mflo $t6		# tomamos el resultado de la operacion en t6
add $t6 , $t6 , $t3	# t6 = 2n+1
mtc1 $t6 , $f3		# hacemos f3 = t6 = 2n+1
div.s $f5 , $f1 , $f3	# f5 = ((-1)^n)/2n+1 
if1:
beq $t5 , -1 , else1	# si t5 = -1 saltamos a else1
add.s $f0 , $f0 , $f5	# hacemos f0 = f0 + f5
j salto			# saltamos para librarnos del else
else1:	
sub.s $f0 , $f0 , $f5	# hacemos f0 = f0 - f5 
salto:	
addi  $t0 , $t0 , 1	# incrementamos en 1 la n
j loop			# saltamos al incio del loop 
end:
div.s $f9 , $f7 , $f1	# hacemos f9 = f7 / f1
mul.s $f0 , $f0 , $f9	# multiplicamos por 4 lo que esta en f0
nop			# termina el programa
