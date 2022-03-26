//En este archivo se tienen las funciones para ejecutar merge sort de forma secuencial.

#include <stdio.h>

void merge(int arr1[], int tam1, int arr2[], int tam2, int res[]);
void merge_sort(int x[], int tam);
void split(int x[], int arr1[], int tam1, int arr2[], int tam2);

/**
 * Parte un arreglo en dos.
 * El arreglo x es el que recibe.
 * arr1 y arr2 contienen las dos partes de x.
 */
void split(int x[], int arr1[], int tam1, int arr2[], int tam2){

    int i = 0;
    for(i=0; i<tam1; i++){
        arr1[i] = x[i];
    }

    for(i=0; i<tam2; i++){
        arr2[i] = x[i+tam1];
    }
}

/**
 * Algoritmo merge usado en mergesort.
 * Se mezcla arr1 y arr2 en res.
 */
void merge(int arr1[], int tam1, int arr2[], int tam2, int res[]){
    int i, j, k;

    i = 0; //Itera sobre el primer arreglo.
    j = 0; //Itera sobre el segundo arreglo.
    k = 0; //Itera sobre el arreglo resultante.

    //Mueve los elementos menores de arr1[] y arr2[] a res[].
    while((i<tam1) && (j<tam2)){
        if(arr1[i] <= arr2[j]){
            res[k] = arr1[i];
            i++;
        }else{
            res[k] = arr2[j];
            j++;
        }
        k++;
    }

    //Mueve los elementos restantes de arr1 a res.
    while(i < tam1){
        res[k] = arr1[i];
        i++;
        k++;
    }

    //Mueve los elementos restantes de arr2 a res.
    while(j < tam2){
        res[k] = arr2[j];
        j++;
        k++;
    }
}

/**
 * Algoritmo mergesort.
 * Ordena el arreglo x que es de tamaño tam.
 */
void merge_sort(int x[], int tam){
    if(tam > 1){
        int tamDer = tam/2;
        int tamIzq = tam - tamDer;
        //El izquierdo es mayor o igual al derecho.
        int arr_izq[tamIzq];
        int arr_der[tamDer];

        split(x, arr_izq, tamIzq, arr_der, tamDer); //Parte x en arr_izq y arr_der.

        merge_sort(arr_izq, tamIzq); //Ordena arreglo izquierdo.
        merge_sort(arr_der, tamDer); //Ordena arreglo derecho.
        merge(arr_izq, tamIzq, arr_der, tamDer, x); //Mezcla los dos en x.
    }
}


