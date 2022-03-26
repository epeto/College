
## @package lenstra
# Se implementan operaciones en campos finitos, además de
# el método de Lenstra para encontrar factores de un número.

import random
import lista_primos
import math

## Calcula el máximo común divisor de dos números.
# @param n Primer número.
# @param m Segundo número.
# @return máximo común divisor de n y m.
def mcd(n,m):
    if n<m:
        a = m
        b = n
    else:
        a = n
        b = m
    r=1
    while r > 0:
        r = a % b
        a = b
        b = r
    return a
    
## Calcula el inverso multiplicativo de i módulo n. Si no existe devuelve 0.
# Se utiliza el algoritmo de Euclides extendido.
# @param i El número al cual se le calcula el inverso multiplicativo.
# @param n El módulo.
# @return inverso multiplicativo de i.
def inversoMult(i,n):
    x0 = 1
    x1 = 0
    x2 = 0
    y0 = 0
    y1 = 1
    y2 = 0
    a = n
    b = i % n
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
        print("El "+str(i)+" no tiene inverso módulo "+str(n))
        return 0
#Fin inversoMult

## Calcula el inverso aditivo de i módulo n.
def inversoAdi(i, n):
    i = i % n
    return n - i

## Calcula el negativo de un punto.
def negPunto(p, n):
    p[1] = inversoAdi(p[1], n)
    return p

## Recibe dos puntos y calcula un tercer punto resultado de sumar en curvas elípticas.
# Si en algún punto no existe inverso multiplicativo se devuleve una lista de tamaño 3: [a, n, mcd(a,n)]
# Si p+q = O (punto al infinito) se devuelve lista vacía, la cual se tomará como el neutro aditivo.
# @param p Punto 1. Es una lista de tamaño 2.
# @param q Punto 2. Es una lista de tamaño 2.
# @param a Es un parámetro de la curva elíptica.
# @param n Módulo n.
# @return r=(x3, y3)
def sumaEliptica(p, q, a, n):
    if len(p) == 0:
        return q
    elif len(q) == 0:
        return p
    x1 = p[0]
    y1 = p[1]
    x2 = q[0]
    y2 = q[1]
    if x1 == x2:
        if y1 != y2:
            return []
        gcd = mcd(2*y1, n)
        if gcd != 1: # Significa que 2*y1 no tiene inverso multiplicativo.
            retVal = list()
            retVal.append(2*y1)
            retVal.append(n)
            retVal.append(gcd)
            return retVal
        frac = (3*(x1**2) + a)*inversoMult(2*y1, n)
        x3 = (frac**2) + inversoAdi(2*x1, n)
        x3 = x3 % n
        y3 = inversoAdi(y1, n) + frac*(x1 + inversoAdi(x3, n))
        y3 = y3 % n
    else:
        iy1 = inversoAdi(y1, n)
        ix1 = inversoAdi(x1, n)
        gcd = mcd(x2+ix1, n)
        if gcd != 1: # Significa que x2+ix1 no tiene inverso multiplicativo.
            retVal = list()
            retVal.append(x2+ix1)
            retVal.append(n)
            retVal.append(gcd)
            return retVal
        frac = (y2 + iy1)*inversoMult(x2 + ix1, n)
        x3 = (frac**2) + ix1 + inversoAdi(x2, n)
        x3 = x3 % n
        y3 = iy1 + frac*(x1 + inversoAdi(x3, n))
        y3 = y3 % n
    lista = list()
    lista.append(x3)
    lista.append(y3)
    return lista

## Se define la multiplicación por un escalar.
# @param c Escalar por el cual se multiplica un punto.
# @param p Punto a multiplicar por c.
# @param a Parámetro de la curva elíptica.
# @param n Módulo n.
# @return c*p
def multEscalar(c, p, a, n):
    retorno = []
    acum = []
    acum.append(p[0])
    acum.append(p[1])
    while c > 0:
        if (c%2) == 1:
            retorno = sumaEliptica(retorno, acum, a, n)
            if len(retorno) == 3:
                return retorno
        acum = sumaEliptica(acum, acum, a, n)
        c = c // 2
    return retorno

## Genera una curva elíptica aleatoria y un punto base.
# La curva está determinada por los valores 'a' y 'b', tal que y² = x³+ax+b
# La base está determinada por 'x' y 'y'.
# @param n El tamaño del campo.
# @return tupla (curva, base)
def generaCurva(n):
    disc = 0 # discriminante
    while disc == 0:
        a = random.randint(0,n)
        x = random.randint(0,n)
        y = random.randint(0,n)
        b = ((y**2) + inversoAdi((x**3)+a*x, n)) % n
        disc = (4*(a**3) + 27*(b**2)) % n
    curva = list()
    curva.append(a)
    curva.append(b)
    punto = list()
    punto.append(x)
    punto.append(y)
    return (curva, punto)

## Calcula un número k que es la multiplicación de potencias de primos
# donde el primo p < b y p^a < c.
# @param b cota para cada primo.
# @param c cota para el primo elevado a cierta potencia.
# @return k
def calcK(b,c):
    lista2 = list()
    i = 0
    while lista_primos.primos[i] < b:
        nump = lista_primos.primos[i]
        alfa = math.floor(math.log(c)/math.log(nump))
        lista2.append(nump**alfa)
        i = i+1
    k = 1
    for numero in lista2:
        k = k*numero
    return k

## Recibe un número y genera otro número aleatorio de la mitad de dígitos.
# @param n Número de referencia para generar otro.
# @return número de la mitad de dígitos de n.
def generaNumero(n):
    num_digitos = 0
    while n > 0:
        n = n // 10
        num_digitos = num_digitos + 1
    dig2 = num_digitos // 2
    retVal = 0
    j = 0
    for i in range(dig2-1):
        retVal = retVal + random.randint(0,10)*(10**i)
        j = i
    j = j+1
    retVal = retVal + random.randint(1,10)*(10**j)
    return retVal

## Encuentra un factor de un número n o devuelve 0 si fracasa.
# @param n El número del cual se intenta encontrar un divisor.
# @return divisor de n o 0.
def encuentraFactor(n):
    if n%2 == 0:
        return 2
    if n%3 == 0:
        return 3
    max_it = 200
    it = 0
    factor = 0
    while it < max_it:
        gcd = 0
        # Primero se busca una curva elíptica cuyo discriminante sea primo relativo con n.
        while gcd != 1:
            curva, punto = generaCurva(n)
            disc = (4*(curva[0]**3) + 27*(curva[1]**2)) % n # Discriminante
            gcd = mcd(disc,n)
            if gcd > 1 and gcd < n: # Se encontró un factor :)
                return gcd
        cota1 = random.randint(15,51)
        cota2 = generaNumero(n)
        k = calcK(cota1, cota2)
        puntoRes = multEscalar(k, punto, curva[0], n)
        if len(puntoRes) == 3:
            if puntoRes[2] < n:
                return puntoRes[2]
        it = it+1
    print("Falló después de "+str(max_it)+" intentos.")
    return 0

# Parte principal del programa
# Integrantes del equipo:
# Hernández Zacateco Aldo René
# Soto Astorga Enrique Francisco
# Peto Gutiérrez Emmanuel
print("Ingrese un número compuesto o deje en blanco para generar un número aleatorio.")
entrada = input()
if entrada != "":
    numero = int(entrada)
else:
    num1 = lista_primos.primos[random.randint(0, len(lista_primos.primos))]
    num2 = lista_primos.primos[random.randint(0, len(lista_primos.primos))]
    numero = num1*num2
factor = encuentraFactor(numero)
if factor != 0:
    otro_factor = numero // factor
    par = (factor,otro_factor)
    print("Número elegido:", numero)
    print("Dos factores del número:", par)

