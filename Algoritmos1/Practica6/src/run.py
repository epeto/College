
import os

print('Indique el nombre del archivo que contiene a la gráfica')
nombreArchivo = input()

print('Elija si la gráfica es dirigida.\n0 -> Dirigida\n1 -> No dirigida')
esDirigida = input()

cmdJava = 'java -cp gs-core-1.3.jar:. Principal ejemplares/'+nombreArchivo+' '+esDirigida
os.system(cmdJava)
