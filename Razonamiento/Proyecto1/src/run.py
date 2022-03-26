
import os

print('Indique el nombre del archivo que contiene a la gráfica')
nombreArchivo = input()

''' A partir de la codificación de la gráfica se crea una fórmula
 reconocible por z3.'''
cmdHaskell = './mono_triangle '+nombreArchivo
os.system(cmdHaskell)

''' Cuando ya se tiene la fórmula en un archivo para z3, se ejecuta
 z3 para encontrar un modelo (si es satisfacible) y se guarda en
 la carpeta "modelos". '''
cmdZ3 = 'z3 output/formula_'+nombreArchivo+'.smt2 > modelos/modelo_'+nombreArchivo
os.system(cmdZ3)

''' Cuando ya se encontró el modelo, se muestra la partición resultante
 con ayuda de GraphStream en Java. Las aristas que pertenecen a una particion
 se pintan de rojo y las que pertenecen a la otra se pintan de azul. '''
cmdJava = 'java -cp gs-core-1.3.jar:. Visualiza input/'+nombreArchivo+' modelos/modelo_'+nombreArchivo
os.system(cmdJava)
