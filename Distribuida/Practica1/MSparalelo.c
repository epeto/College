//Este programa ejecuta Merge sort en paralelo con ayuda de Open MPI


#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <mpi.h>
#include "mssec.h"

/**
 * Función que inicializa la semilla aleatoia.
 */
void inicializaAleatoriedad(){
    srand(time(NULL));
}

/**
 * Imprime un arreglo (arr) de tamaño tam.
 */
void imprimeArreglo(int arr[], int tam){
    printf("[");
    int i;

    for(i=0; i<tam-1; i++){
        printf("%d, ", arr[i]);
    }
    printf("%d]\n", arr[tam-1]);
}

/**
 * Llena el arreglo arr con números aleatorios entre 0 y 500.
 */
void arregloAleatorio(int arr[], int tam){
    int i;
    for(i=0; i<tam; i++){
        arr[i] = rand()%500;
    }
}

int main(int argc, char** argv){
    inicializaAleatoriedad();
    // Inicializa el entorno de MPI
    MPI_Init(&argc, &argv);
    // Encuentra el rango y el tamaño.
    int world_rank;
    MPI_Comm_rank(MPI_COMM_WORLD, &world_rank);
    int world_size;
    MPI_Comm_size(MPI_COMM_WORLD, &world_size);

    int hizq = world_rank*2 + 1; //Hijo izquierdo.
    int hder = world_rank*2 + 2; //Hijo derecho.
    int padre = (world_rank-1)/2; //Padre.
    int tipo; //Especifica el tipo de nodo.

    //En esta parte se determina el tipo de nodo.
    if(world_rank == 0){
        tipo = 0; //Tipo raíz
    }else if(hder<world_size){
        tipo = 1; //Tipo interno (tiene hijo izquierdo y derecho)
    }else if(hizq<world_size){
        tipo = 2; //Tipo semi-interno (tiene hijo izquierdo pero no derecho)
    }else{
        tipo = 3; //Tipo hoja (no tiene hijo izquierdo ni derecho)
    }

    int tam; //Tamaño del arreglo local.
    int* alocal; //Arreglo local.
    int* mensaje1; //Arreglo a enviar al hijo izquierdo.
    int* mensaje2; //Arreglo a enviar al hijo derecho.
    MPI_Status status; //Se usará para conocer el tamaño del mensaje recibido

    switch(tipo){
        //Raíz
        case 0: printf("Indique el tamaño del arreglo a ordenar:\n");
                scanf("%d", &tam);
                if(tam < 1){
                    fprintf(stderr, "El tamaño debe ser un número positivo\n");
                    MPI_Abort(MPI_COMM_WORLD, 1);
                    return 0;
                }else if(tam == 1){
                    int numAl = rand()%500;
                    printf("%d\n", numAl);
                    MPI_Abort(MPI_COMM_WORLD, 1);
                    return 0;
                }

                alocal = (int*)malloc(sizeof(int) * tam);
                arregloAleatorio(alocal, tam); //Rellena el arreglo local con números aleatorios.
                imprimeArreglo(alocal, tam);
                int tamM2 = tam/2;
                int tamM1 = tam - tamM2;
                mensaje1 = (int*)malloc(sizeof(int) * tamM1);
                mensaje2 = (int*)malloc(sizeof(int) * tamM2);
                split(alocal, mensaje1, tamM1, mensaje2, tamM2); //Parte 'alocal' en 'mensaje1' y 'mensaje2'.
                //Se envían los mensajes.
                if(world_size > 1){
                    MPI_Send(mensaje1, tamM1, MPI_INT, hizq, 0, MPI_COMM_WORLD); //Envía el primer trozo al hijo izquierdo.
                    if(world_size > 2){
                        MPI_Send(mensaje2, tamM2, MPI_INT, hder, 0, MPI_COMM_WORLD); //Envía el segundo trozo al hijo derecho.
                    }else{ //Solo 2 hilos.
                        merge_sort(mensaje2, tamM2); //Si solo son dos hilos ordena la parte derecha secuencialmente.
                    }
                }else{ //Solo 1 hilo.
                    merge_sort(alocal, tam); //Si solo es un hilo se ordena de forma secuencial.
                }

                //Se reciben los mensajes.
                if(world_size > 1){
                    MPI_Recv(mensaje1, tamM1, MPI_INT, hizq, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
                    if(world_size > 2){
                        MPI_Recv(mensaje2, tamM2, MPI_INT, hder, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
                    }
                    merge(mensaje1, tamM1, mensaje2, tamM2, alocal); //Mezcla los dos en 'alocal'
                }

                //En este punto el arreglo ya debe estar ordenado.
                printf("\nOrdenado:\n");
                imprimeArreglo(alocal, tam);
        break;
        //Interno
        case 1: //Se recibe el mensaje del padre y se guarda el 'alocal'
                MPI_Probe(padre, 0, MPI_COMM_WORLD, &status);
                MPI_Get_count(&status, MPI_INT, &tam);
                alocal = (int*)malloc(sizeof(int) * tam);
                MPI_Recv(alocal, tam, MPI_INT, padre, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
                //Se envían los mensajes a sus hijos.
                if(tam > 1){
                    int tamM2 = tam/2;
                    int tamM1 = tam - tamM2;
                    mensaje1 = (int*)malloc(sizeof(int) * tamM1);
                    mensaje2 = (int*)malloc(sizeof(int) * tamM2);
                    split(alocal, mensaje1, tamM1, mensaje2, tamM2); //Parte 'alocal' en 'mensaje1' y 'mensaje2'.
                    //Se envían los mensajes a los hijos
                    MPI_Send(mensaje1, tamM1, MPI_INT, hizq, 0, MPI_COMM_WORLD);
                    MPI_Send(mensaje2, tamM2, MPI_INT, hder, 0, MPI_COMM_WORLD);
                    //Se espera el retorno de los mensajes de los hijos
                    MPI_Recv(mensaje1, tamM1, MPI_INT, hizq, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
                    MPI_Recv(mensaje2, tamM2, MPI_INT, hder, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
                    merge(mensaje1, tamM1, mensaje2, tamM2, alocal); //Se mezclan en alocal
                }else{ //Tamaño = 1
                    //En este caso solo se manda un número a los hijos para
                    //que no se queden esperando el Recv.
                    MPI_Send(alocal, 1, MPI_INT, hizq, 0, MPI_COMM_WORLD);
                    MPI_Send(alocal, 1, MPI_INT, hizq, 0, MPI_COMM_WORLD);
                }
                MPI_Send(alocal, tam, MPI_INT, padre, 0, MPI_COMM_WORLD); //Se envía el resultado al padre
        break;
        //Semi-interno
        case 2: //Se recibe el mensaje del padre y se guarda el 'alocal'
                MPI_Probe(padre, 0, MPI_COMM_WORLD, &status);
                MPI_Get_count(&status, MPI_INT, &tam);
                alocal = (int*)malloc(sizeof(int) * tam);
                MPI_Recv(alocal, tam, MPI_INT, padre, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
                //Se envía 'mensaje1' a su hijo izquierdo mientras ordena 'mensaje2'
                if(tam > 1){
                    int tamM2 = tam/2;
                    int tamM1 = tam - tamM2;
                    mensaje1 = (int*)malloc(sizeof(int) * tamM1);
                    mensaje2 = (int*)malloc(sizeof(int) * tamM2);
                    split(alocal, mensaje1, tamM1, mensaje2, tamM2); //Parte 'alocal' en 'mensaje1' y 'mensaje2'.
                    MPI_Send(mensaje1, tamM1, MPI_INT, hizq, 0, MPI_COMM_WORLD); //Se envía el mensaje1 al hijo izquierdo
                    MPI_Recv(mensaje1, tamM1, MPI_INT, hizq, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE); //Se espera el retorno del mensaje
                    merge_sort(mensaje2, tamM2); //Se ordena de forma secuencial el lado derecho
                    merge(mensaje1, tamM1, mensaje2, tamM2, alocal); //Se mezclan en alocal
                }else{ //Tamaño = 1
                    //En este caso solo se manda un número a los hijos para
                    //que no se queden esperando el Recv.
                    MPI_Send(alocal, 1, MPI_INT, hizq, 0, MPI_COMM_WORLD);
                    MPI_Send(alocal, 1, MPI_INT, hizq, 0, MPI_COMM_WORLD);
                }
                MPI_Send(alocal, tam, MPI_INT, padre, 0, MPI_COMM_WORLD); //Se envía el resultado al padre
        break;
        //Hoja
        case 3: //Se recibe el mensaje del padre y se guarda en 'alocal'
                MPI_Probe(padre, 0, MPI_COMM_WORLD, &status);
                MPI_Get_count(&status, MPI_INT, &tam);
                alocal = (int*)malloc(sizeof(int) * tam);
                MPI_Recv(alocal, tam, MPI_INT, padre, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
                //Se ordena de forma secuencial el arreglo recibido
                merge_sort(alocal, tam);
                //Se devuelve el resultado al padre
                MPI_Send(alocal, tam, MPI_INT, padre, 0, MPI_COMM_WORLD);
        break;
    }

    MPI_Finalize();
}
