#include <stdio.h>
#include <stdlib.h>
#include <omp.h>

int main(int argc, char** argv){
    printf("Decida el número de hilos a ejecutar.\n");
    int num_hilos;
    scanf("%d",&num_hilos);
    int idHilo;
    omp_set_num_threads(num_hilos);
    #pragma omp parallel private(idHilo)
    {
        idHilo = omp_get_thread_num();
        printf("Se ejecutó el hilo %d\n", idHilo);
    }
}
