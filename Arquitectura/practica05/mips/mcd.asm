#Máximo común divisor
.data
x: .word 60        #Definir x
y: .word 45        #Definir y
.text
lw $t0, x          #Cargar t0 con x
lw $t1, y          #Cargar t1 con y
rem $t2, $t0, $t1  #Colocar en t2 el residuo de t0 con t1
move $t3, $t1      #t3=t1
etiq:              #etiqueta
move $t4, $t2      #t4=t2
rem $t2, $t3, $t4  #colocar en t2 el residuo de t3 con t4
move $t3, $t4      #t3 = t4
bgtz $t2, etiq     #si t2>0 saltar a etiq
move $v0, $t3      #colocar resultado en v0