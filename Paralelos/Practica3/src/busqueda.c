// Se realiza búsqueda en secuencial y en paralelo.

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <omp.h>

/**
 * Realiza una búsqueda binaria.
 * @param arr arreglo donde se buscará al elemento
 * @param n tamaño del arreglo
 * @param x número a buscar
 * @return el índice de x en arr si lo encuentra, -1 en otro caso.
 */
int busquedaBin(int* arr, int n, int x){
    int indice = -1;
    int izq = 0;
    int der = n-1;
    int m;

    while(indice == -1 && izq < der){
        m = (izq + der)/2;
        if(arr[m] == x){
            indice = m;
        }else if(x < arr[m]){
            der = m-1;
        }else{
            izq = m+1;
        }
    }

    if(arr[izq] == x){
        indice = izq;
    }
    return indice;
}

/**
 * Realiza una búsqueda en paralelo en un arreglo.
 * @param arr arreglo donde se buscará al elemento
 * @param n tamaño del arreglo
 * @param x número a buscar
 * @return el índice de x en arr si lo encuentra, -1 en otro caso.
 */
int busquedaPara(int* arr, int n, int x){
    omp_set_num_threads(n);
    int id;
    int indice = -1;
    #pragma omp parallel private(id) shared(arr, indice, x) //Región paralela
    {
        id = omp_get_thread_num();
        if(x == arr[id]){
            #pragma omp critical
            {
                indice = id;
            }
        }
    }
    return indice;
}

/**
 * Rellena un arreglo con valores aleatorios, asegurando que esté ordenado.
 * @param arr Arreglo donde se colocan los números.
 * @param n Tamaño del arreglo.
 * @param semilla Servirá para definir la semilla de aleatoriedad.
 */
void arregloRand(int* arr, int n, int semilla){
    srand(time(NULL)*semilla);
    int i;
    arr[0] = rand() % 6;
    for(i=1; i<n; i++){
        arr[i] = arr[i-1] + (rand()%6);
    }
}

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

// Función principal
int main(int argc, char** argv){
    printf("Escriba el tamaño del arreglo:\n");
    int n;
    scanf("%d", &n);
    int nuevoArr[n];
    arregloRand(nuevoArr, n, 2);
    printf("Arreglo:\n");
    imprimeArreglo(nuevoArr, n);
    printf("Escriba el número a buscar:\n");
    int x;
    scanf("%d", &x);
    int i1 = busquedaBin(nuevoArr, n, x);
    int i2 = busquedaPara(nuevoArr, n, x);
    // Teóricamente, i1 debe ser igual a i2.
    printf("Índice en secuencial de %d: %d\n", x, i1);
    printf("Índice en paralelo de %d: %d\n", x, i2);
}
