
# En este archivo se definen diferentes funciones matemáticas que se usarán para el cifrado RSA.

import sympy

alfabeto = 'ABCDEFGHIJKLMNÑOPQRSTUVWXYZ'

# Convierte un número decimal a binario
def decToBin(n):
    binario = []
    while n>0:
        binario.insert(0,(n%2))
        n = n//2        
    return binario
# Fin decToBin

# Calcula el exponente b^pot mod n en tiempo log(pot). (Página 283, Stallings).
def modPot(b,pot,n):
    f = 1
    binPot = decToBin(pot) #Representación binaria de pot.
    for bit in binPot:
        f = (f*f) % n
        if bit == 1:
            f = (f*b) % n
    return f
# Fin modPot

# Suponiendo que una cadena de caracteres representa un número en base 27,
# este método transforma la cadena a decimal. (Página 224, Galaviz).
def stringToNum27(cadena):
    total = 0
    for i in range(0,len(cadena)):
        indice = len(cadena)-1 - i
        total = total + alfabeto.index(cadena[indice])*(27 ** i)
    return total
# Fin stringToNum27


# Dado un número en notación decimal, lo convierte a base 27 (con letras).
def numToString27(numero):
    cadena = ''
    while numero > 0 :
        cadena = alfabeto[numero % 27] + cadena
        numero = numero // 27
    return cadena
# Fin numToString27


# Calcula el inverso multiplicativo de i módulo n. Si no existe devuelve 0.
# Se utiliza el algoritmo de Euclides extendido.
def inversoMult(i,n):
    x0 = 1
    x1 = 0
    x2 = 0
    y0 = 0
    y1 = 1
    y2 = 0

    a = n
    b = i
    r = 1
    q = 0

    while r > 0 :
        r = a % b
        q = a // b
        x2 = x0 - x1*q
        y2 = y0 - y1*q

        # Actualización de variables para la siguiente iteración.
        a = b
        b = r
        x0 = x1
        x1 = x2
        y0 = y1
        y1 = y2

    if a==1 :
        if(y0 < 0):
            y0 = y0 + n
        return y0
    else:
        return 0
#Fin inversoMult


# Toma cualquier cadena de caracteres y la limpia
# dejando solamente caracteres alfabéticos y en mayúsculas.
def limpiaTexto(texto):
    texto = texto.upper()
    resultado = ''
    for c in texto:
        if c in alfabeto:
            resultado = resultado + c

    return resultado
# Fin limpiaTexto


# Parte una cadena de texto en cadenas más pequeñas de tamaño tam y lo devuelve como una lista.
def parteCadena(cadena, tam):
    cadenitas = []
    while len(cadena) > 0:
        cadenitas.append(cadena[:tam])
        cadena = cadena[tam:]
    return cadenitas
# Fin parteCadena


# Devuelve una lista de 4 números: p, q, n, phi.
# Donde p y q son primos, n=p*q y phi = (p-1)*(q-1)
def multPrimos():
    #Obtiene números primos aleatorios de entre 50 y 60 dígitos.
    p = sympy.randprime(
    10000000000000000000000000000000000000000000000000,
    100000000000000000000000000000000000000000000000000000000000)

    q = sympy.randprime(
    10000000000000000000000000000000000000000000000000,
    100000000000000000000000000000000000000000000000000000000000)

    n = p*q
    phi = (p-1)*(q-1)

    return [p, q, n, phi]
# Fin multPrimos

