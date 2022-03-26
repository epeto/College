
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <omp.h>

/**
 * Imprime un arreglo de tamaño n.
 * @param arr Arreglo donde se colocan los números.
 * @param n Tamaño del arreglo.
 */
void imprimeArreglo(int* arr, int n){
    printf("[");
    int i;
    for(i=0; i<n-1; i++){
        printf("%d, ", arr[i]);
    }
    printf("%d]\n", arr[n-1]);
}

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
 * Rellena un arreglo con valores aleatorios, asegurando que no se repitan.
 * @param arr Arreglo donde se colocan los números.
 * @param n Tamaño del arreglo.
 * @param semilla Servirá para definir la semilla de aleatoriedad.
 */
void arregloRand(int* arr, int n, int semilla){
    srand(time(NULL)*semilla);
    int i;
    int arr_aux[n]; //arreglo auxiliar que guardará elementos consecutivos.
    arr_aux[0] = (rand()%5)+1;
    for(i=1; i<n; i++){
        arr_aux[i] = arr_aux[i-1] + ((rand()%5)+1);
    }

    int indRand; //índice aleatorio.
    int j;
    for(i=n, j=0; i>0; i--, j++){
        indRand = rand()%i;
        arr[j] = arr_aux[indRand];
        for(int k=indRand; k<i-1; k++){
            arr_aux[k] = arr_aux[k+1];
        }
    }
}

/**
 * Encuentra al elemento más grande de un arreglo.
 * @param arr Arreglo de números.
 * @param n Tamaño del arreglo.
 */
void maxarr(int* arr, int n){
    int it = n/2;
    int vr = 2; //valor en la ronda r
    int vr_1 = 1; //valor en la ronda r-1
    int izq, der;
    int temp;

    omp_set_num_threads(n/2);
    while(it >= 1){
        #pragma omp parallel for shared(it, vr, vr_1, arr) private(izq, der, temp)
        for(int i=1; i<=it; i++){
            izq = vr*i - vr_1 - 1;
            der = vr*i - 1;
            if(arr[izq] > arr[der]){
                temp = arr[izq];
                arr[izq] = arr[der];
                arr[der] = temp;
            }
        }

        vr_1 = vr;
        vr *= 2;
        it /= 2;
    }
}

// Función principal
int main(int argc, char** argv){
    printf("Ingrese un número k para calcular 2^k\n");
    int k;
    scanf("%d", &k);
    int n = expo(2,k);
    int arr[n];
    arregloRand(arr, n, 5);
    printf("Arreglo inicial:\n");
    imprimeArreglo(arr, n);
    maxarr(arr, n);
    printf("Arreglo final:\n");
    imprimeArreglo(arr, n);
    printf("Máximo: %d\n", arr[n-1]);
}
