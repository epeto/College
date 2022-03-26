
#include <stdio.h>
#include <stdlib.h>
#include <omp.h>
#include <time.h>

/**
 * Coloca n números aleatorios en el arreglo arr.
 * @param arr Arreglo donde se colocan los números.
 * @param n Tamaño del arreglo.
 * @param semilla Servirá para definir la semilla de aleatoriedad.
 */
void arregloRand(int* arr, int n, int semilla){
    //Semilla de aleatoriedad
    srand(time(NULL)*semilla);
    int i;
    for(i=0; i<n; i++){
        arr[i] = (rand()%100) + 1;
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

/**
 * Realiza la suma de dos vectores.
 * @param a Vector 1.
 * @param b Vector 2.
 * @param c Vector donde se guarda el resultado de a+b.
 * @param n Tamaño de los vectores.
 */
void sumaVectores(int* a, int* b, int* c, int n){
    omp_set_num_threads(n);
    int i;
    #pragma omp parallel for shared(a,b,c,n) private(i)
    for(i=0; i<n; i++){
        c[i] = a[i] + b[i];
    }
}

/**
 * Realiza producto punto de dos vectores.
 * @param a Vector 1.
 * @param b Vector 2.
 * @param n Tamaño de los vectores.
 * @return a·b
 */
int productoPunto(int* a, int* b, int n){
    omp_set_num_threads(n);
    int id, x, y, r, cota;
    int c[n]; //Vector que servirá para el paso intermedio del producto punto.
    #pragma omp parallel private(cota, id, x, y, r) shared(a,b,c,n) //Región paralela
    {
        id = omp_get_thread_num();
        c[id] = a[id] * b[id];
        #pragma omp barrier // Debo esperar a que se calculen todos los valores de c antes de empezar a leerlo.
        cota = n;
        while(cota > 1){
            r = cota % 2;
            cota = (cota/2) + r;
            if(id < cota-r){
                x = c[2*id];
                y = c[2*id + 1];
                #pragma omp barrier // Debo esperar a que todos lean su valor de c antes de modificarlo.
                c[id] = x + y;
                #pragma omp barrier // Nuevamente, hay que esperar a que todos escriban en c.
            }else if(id == cota-r){
                if(r == 1){
                    x = c[2*id];
                    #pragma omp barrier
                    c[id] = x;
                    #pragma omp barrier
                }else{
                    #pragma omp barrier
                    #pragma omp barrier
                }
            }else{
                #pragma omp barrier
                #pragma omp barrier
            }
        }
    } //termina parte paralela
    return c[0];
}

/**
 * Realiza la resta de dos vectores.
 * @param a Vector 1.
 * @param b Vector 2.
 * @param c Vector donde se guarda el resultado de a-b.
 * @param n Tamaño de los vectores.
 */
void restaVectores(int* a, int* b, int* c, int n){
    omp_set_num_threads(n);
    int i;
    #pragma omp parallel for shared(a,b,c,n) private(i)
    for(i=0; i<n; i++){
        c[i] = a[i] - b[i];
    }
}

/**
 * Realiza la multiplicación de un vector por un escalar.
 * @param a Vector.
 * @param e Escalar.
 * @param n Tamaño del vector.
 */
void multEscalar(int* a, int e, int n){
    omp_set_num_threads(n);
    int i;
    #pragma omp parallel for shared(a,e,n) private(i)
    for(i=0; i<n; i++){
        a[i] = e*a[i];
    }
}

/**
 * Rellena una matriz de n*n con valores aleatorios.
 * @param mat Matriz.
 * @param n Número de columnas y filas en la matriz.
 * @param semilla Servirá para definir la semilla de aleatoriedad.
 */
void matrizRand(int** mat, int n, int semilla){
    srand(time(NULL)*semilla);
    int i,j;
    for(i=0; i<n; i++){
        for(j=0; j<n; j++){
            mat[i][j] = (rand()%100) + 1;
        }
    }
}

/**
 * Suma dos matrices de forma paralela.
 * @param a Matriz 1.
 * @param b Matriz 2.
 * @param c Matriz donde se guarda el resultado de a+b.
 * @param n Número de columnas y de filas en la matriz.
 */
void sumaMatrices(int** a, int** b, int** c, int n){
    omp_set_num_threads(n*n);
    int i, j, id;
    #pragma omp parallel shared(a,b,c,n) private(i,j,id)
    {
        id = omp_get_thread_num();
        i = id/n;
        j = id%n;
        c[i][j] = a[i][j] + b[i][j];
    }
}

/**
 * Calcula la cantidad de dígitos que hay en un entero.
 * @param n número entero.
 * @return dígitos de n.
 */
int digitos(int n){
    int d = 0;
    //Se cuenta al 0 como 1 dígito.
    if(n == 0){
        return 1;
    }
    //Se contará al signo '-' como un dígito.
    if(n < 0){
        d++;
        n = -n;
    }
    while(n > 0){
        d++;
        n /= 10;
    }
    return d;
}

/**
 * Imprime una matriz cuadrada.
 * @param matriz a imprimir.
 * @param n número de columnas y filas en la matriz.
 */
void imprimeMatriz(int** matriz, int n){
    //El primer paso es calcular el número máximo de dígitos que hay en cualquier número de la matriz.
    int maxDigit = 0;
    int i,j,k;
    for(i=0;i<n;i++){
        for(int j=0;j<n;j++){
            int digAct = digitos(matriz[i][j]);
            if(digAct > maxDigit){
                maxDigit = digAct;
            }
        }
    }
    //En esta parte ya imprime la matriz.
    for(i=0; i<n; i++){
        printf("|");
        for(j=0; j<n; j++){
            //Decide cuántos espacios le va a agregar al principio.
            int numEsp = maxDigit - digitos(matriz[i][j]);
            for(k=0;k<numEsp;k++){
                printf(" ");
            }
            printf("%d", matriz[i][j]);
            if(j < n-1){
                printf(" ");
            }
        }
        printf("|\n");
    }
}

// Función principal
int main(int argc, char** argv){
    int tam = atoi(argv[1]);
    int seed = atoi(argv[2]);

    printf("Elija un número para realizar una operación:\n");
    printf("1 -> Suma de vectores.\n");
    printf("2 -> Producto punto.\n");
    printf("3 -> Resta de vectores.\n");
    printf("4 -> Multiplicación de un vector por un escalar.\n");
    printf("5 -> Suma de matrices.\n");
    int opcion;
    scanf("%d", &opcion);

    int vector1[tam];
    int vector2[tam];
    int res[tam];
    int escalar, i, pp;
    int** matriz1 = (int **) malloc(tam*sizeof(int *));
    for(i=0;i<tam;i++){
        matriz1[i] = (int *) malloc(tam*sizeof(int));
    }
    int** matriz2 = (int **)malloc(tam*sizeof(int *));
    for(i=0;i<tam;i++){
        matriz2[i] = (int *) malloc(tam*sizeof(int));
    }
    int** resMat = (int **) malloc(tam*sizeof(int *));
    for(i=0;i<tam;i++){
        resMat[i] = (int *) malloc(tam*sizeof(int));
    }

    switch(opcion){
        case 1: arregloRand(vector1, tam, seed);
                arregloRand(vector2, tam, seed*2);
                printf("Vector 1:\n");
                imprimeArreglo(vector1, tam);
                printf("Vector 2:\n");
                imprimeArreglo(vector2, tam);
                sumaVectores(vector1, vector2, res, tam);
                printf("Resultado:\n");
                imprimeArreglo(res, tam);
        break;
        case 2: arregloRand(vector1, tam, seed);
                arregloRand(vector2, tam, seed*2);
                printf("Vector 1:\n");
                imprimeArreglo(vector1, tam);
                printf("Vector 2:\n");
                imprimeArreglo(vector2, tam);
                pp = productoPunto(vector1, vector2, tam);
                printf("Resultado: %d\n", pp);
        break;
        case 3: arregloRand(vector1, tam, seed);
                arregloRand(vector2, tam, seed*2);
                printf("Vector 1:\n");
                imprimeArreglo(vector1, tam);
                printf("Vector 2:\n");
                imprimeArreglo(vector2, tam);
                restaVectores(vector1, vector2, res, tam);
                printf("Resultado:\n");
                imprimeArreglo(res, tam);
        break;
        case 4: arregloRand(vector1, tam, seed);
                printf("Vector 1:\n");
                imprimeArreglo(vector1, tam);
                escalar = (rand()%100) + 1;
                printf("Escalar: %d\n", escalar);
                multEscalar(vector1, escalar, tam);
                printf("Resultado:\n");
                imprimeArreglo(vector1, tam);
        break;
        default: matrizRand(matriz1, tam, seed);
                 matrizRand(matriz2, tam, seed*2);
                 sumaMatrices(matriz1, matriz2, resMat, tam);
                 printf("Matriz 1:\n");
                 imprimeMatriz(matriz1, tam);
                 printf("Matriz 2:\n");
                 imprimeMatriz(matriz2, tam);
                 printf("Resultado:\n");
                 imprimeMatriz(resMat, tam);
    }
}

