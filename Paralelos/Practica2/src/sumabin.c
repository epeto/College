
// Se realiza la suma de números binarios en secuencial y en paralelo.
// un número binario estará representado por un arreglo.

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <omp.h>

/**
 * Calcula la potencia de un número entero.
 * @param b número base.
 * @param e exponente.
 * @return b^e
 */
int expo(int b, int e){
    if(e <= 0){
        return 1;
    }
    int res = b;
    for(int i=1; i<e; i++){
        res = res*b;
    }
    return res;
}

/**
 * Coloca 0's y 1's de forma aleatoria en el arreglo.
 * @param arr Arreglo donde se colocan los números.
 * @param n Tamaño del arreglo.
 * @param semilla Servirá para definir la semilla de aleatoriedad.
 */
void arregloRand(int* arr, int n, int semilla){
    srand(time(NULL)*semilla);
    int i;
    for(i=0; i<n; i++){
        arr[i] = rand()%2;
    }
}

/**
 * Imprime un arreglo de tamaño n. Lo imprime desde el final hacia el inicio.
 * @param arr Arreglo de 0's y 1's.
 * @param n Tamaño del arreglo.
 */
void imprimeArreglo(int* arr, int n){
    int i;
    for(i=n-1; i>=0; i--){
        printf("%d", arr[i]);
    }
    printf("\n");
}

/**
 * Suma dos números binarios de forma secuencial. Los números están representados por un arreglo de 0's y 1's.
 * @param num1 primer número
 * @param num2 segundo número
 * @param numres arreglo donde se guardará el resultado de num1 + num2.
 */
void sumaSec(int* num1, int* num2, int* numres, int n){
    int ac = 0; //Acarreo.
    int i;
    for(i=0; i<n; i++){
        int caso = ac + num1[i] + num2[i];
        switch(caso){
            case 0: ac = 0;
                    numres[i] = 0;
            break;
            case 1: ac = 0;
                    numres[i] = 1;
            break;
            case 2: ac = 1;
                    numres[i] = 0;
            break;
            case 3: ac = 1;
                    numres[i] = 1;
            break;
        }
    }
    numres[n] = ac;
}

/**
 * Suma paralela de números binarios.
 * @param num1 primer número
 * @param num2 segundo número
 * @param numres arreglo donde se guarda el resultado
 * @param izq índice izquierdo
 * @param der índice derecho
 * @param acIn acarreo de entrada
 * @return el acarreo de la suma (puede ser 0)
 */
int sumaPara(int* num1, int* num2, int* numres, int izq, int der, int acIn){
    if(izq == der){
        int caso = num1[izq] + num2[izq] + acIn;
        switch(caso){
            case 0: numres[0] = 0;
                    return 0;
            break;
            case 1: numres[0] = 1;
                    return 0;
            break;
            case 2: numres[0] = 0;
                    return 1;
            break;
            case 3: numres[0] = 1;
                    return 1;
            break;
        }
    }
    //Hay que partir cada entrada a la mitad.
    //Para guardar los resultados parciales se necesitan 3 nuevos arreglos.
    int resIzq[(der-izq+1)/2]; // izquierda de numres
    int resDer[(der-izq+1)/2]; // derecha de numres
    int resDerAC[(der-izq+1)/2]; // derecha de numres con acarreo
    int acIzq = 0; //Acarreo izquierdo
    int acDer = 0; //Acarreo derecho
    int acDer2 = 0; //Otro acarreo derecho
    int m = (izq + der)/2; //mitad

    //Ahora se calculan las sumas parciales de forma recursiva.
    #pragma omp parallel
    #pragma omp single nowait
    {
        #pragma omp task shared(acIzq) // Suma del lado izquierdo
        {
            acIzq = sumaPara(num1, num2, resIzq, izq, m, acIn);
        }
        #pragma omp task shared(acDer) // Suma del lado derecho
        {
            acDer = sumaPara(num1, num2, resDer, m+1, der, 0);
        }
        #pragma omp task shared(acDer2) // Suma del lado derecho con acarreo.
        {
            acDer2 = sumaPara(num1, num2, resDerAC, m+1, der, 1);
        }
        #pragma omp taskwait
    }

    //Finalmente se copia el resultado a numres.
    #pragma omp parallel for
    for(int i=0; i<(der-izq+1); i++){
        if(i < (der-izq+1)/2){
            numres[i] = resIzq[i];
        }else{
            if(acIzq == 1){ // Comprueba si hay acarreo del lado izquerdo
                numres[i] = resDerAC[i - (der-izq+1)/2];
            }else{
                numres[i] = resDer[i - (der-izq+1)/2];
            }
        }
    }

    if(acIzq == 1){
        return acDer2;
    }else{
        return acDer;
    }
}

int main(int argc, char** argv){
    int pot = atoi(argv[1]); //Se lee la potencia desde la terminal
    //Se crean dos arreglos de tamaño 2^pot.
    int n = expo(2, pot);
    int binario1[n];
    int binario2[n];
    int resultado[n];
    int resultado2[n+1];
    arregloRand(binario1, n, 13);
    arregloRand(binario2, n, 17);
    //Se realizan las sumas, tanto en paralelo como en secuencial.
    double inicioP = omp_get_wtime();
    int ac = sumaPara(binario1, binario2, resultado, 0, n-1, 0);
    double finP = omp_get_wtime();

    double inicioS = omp_get_wtime();
    sumaSec(binario1, binario2, resultado2, n);
    double finS = omp_get_wtime();
    //Luego se imprime el resultado.
    printf(" ");
    imprimeArreglo(binario1, n);
    printf("+");
    imprimeArreglo(binario2, n);
    int i;
    for(i=0; i<=n; i++){
        printf("=");
    }
    printf("\n%d", ac);
    imprimeArreglo(resultado, n);
    //Puedes descomentar la siguiente línea para comprobar que el resultado al hacerlo
    //secuencial es igual al resultado cuando se realiza en paralelo.
    //imprimeArreglo(resultado2, n+1);
    printf("Tiempo en paralelo: %f s\n", finP - inicioP);
    printf("Tiempo secuencial: %f s\n", finS - inicioS);
}
