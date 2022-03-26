
import aritmetica
import random

# Genera las llaves pública y privada.
def generaLlaves():
    primos = aritmetica.multPrimos() # Se generan los números primos de manera aleatoria.
    random.seed()
    phi = primos[3]
    n = primos[2]
    e = 0
    d = 0 # Va a ser el inverso multiplicativo de d.
    while d == 0:
        e = random.randint(10000000000000000000, phi) # Un número menor que phi(n) de al menos 20 dígitos
        d = aritmetica.inversoMult(e, phi)

    #e es la llave pública y d la privada. Ahora se van a guardar en un archivo de texto.
    f1 = open ('llave_publica','w')
    f1.write(str(n))
    f1.write('\n')
    f1.write(str(e))
    f1.close()

    f2 = open ('llave_privada','w')
    f2.write(str(n))
    f2.write('\n')
    f2.write(str(d))
    f2.close()
# Fin generaLlaves


# Realiza el cifrado RSA. Recibe el nombre del archivo que contiene el texto original.
def cifra(archivo):
    f1 = open('original/'+archivo, 'r')
    texto = aritmetica.limpiaTexto(f1.read())
    f1.close()

    #Parte el texto total en bloques de 50 caracteres.
    fragmentos = aritmetica.parteCadena(texto, 50)
    #Transforma los fragmentos de texto a números.
    fragNum = []
    for bloque in fragmentos:
        fragNum.append(aritmetica.stringToNum27(bloque))

    #Se lee la llave pública
    f2 = open('llave_publica', 'r')
    n = int(f2.readline())
    key = int(f2.readline())
    f2.close()

    #Se escribe el texto cifrado en un archivo de texto en la carpeta 'cifrado'.
    f3 = open('cifrado/'+archivo, 'w')
    for bloque in fragNum:
        cifrado = aritmetica.modPot(bloque, key, n)
        f3.write(str(cifrado))
        f3.write('\n')
    f3.close()
# Fin cifra


# Realiza el descifrado de RSA. Recibe el nombre del archivo que contiene al mensaje cifrado.
def descifra(archivo):
    f1 = open('cifrado/'+archivo, 'r')
    bloques = f1.read().split('\n') #Lee todas las líneas y las divide en bloques
    f1.close()
    bloques.pop() # Elimina el último elemento que es una línea vacía

    bloquesNum = []
    # Transforma todas las líneas de texto a números.
    for b in bloques:
        bloquesNum.append(int(b))

    # Lee la llave privada.
    f2 = open('llave_privada', 'r')
    n = int(f2.readline())
    key = int(f2.readline())
    f2.close()

    #Se escribe el texto descifrado en un archivo en la carpeta 'descifrado'
    f3 = open('descifrado/'+archivo, 'w')
    for bloque in bloquesNum:
        descifrado = aritmetica.modPot(bloque, key, n)
        f3.write(aritmetica.numToString27(descifrado))
        f3.write('\n')
    f3.close()
# Fin descifra

#Main()
print('Elija una opción:\n1.-Generar llaves.\n2.-Cifrar mensaje.\n3.-Descifrar mensaje.')
opcion = input()

if opcion == '1':
    generaLlaves()
    print('Se han generado llaves aleatorias en los archivos \'llave_privada\' y \'llave_publica\' ')
else:
    if opcion == '2':
        print('Escriba el nombre del archivo que contiene el texto original.')
        nombreArchivo = input()
        cifra(nombreArchivo)
        print('Puede ver el criptotexto en la carpeta \'cifrado\'')
    else:
        print('Escriba el nombre del archivo que contiene el criptograma.')
        nombreArchivo = input()
        descifra(nombreArchivo)
        print('Puede ver el texto descifrado en la carpeta \'descifrado\'')

