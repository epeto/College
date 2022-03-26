/*Práctica 1: Algoritmos Paralelos 2021-2
*	Muñiz Patiño, Andrea Fernanda
* 	UNAM, Facultad de Ciencias.
* 	Versión 1.0
*	-----------------------------------
*	Codigo auxiliar para la práctica 1 
* 	Laboratorio de Algoritmos paralelos
*/

#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>
#include <omp.h>

int main(int argc, char** argv){
    int numHilos, idHilo;
	
    omp_set_num_threads(100);
    //inicia seccion paralela
    #pragma omp parallel private(idHilo)
    {	
        while(1){
            idHilo = omp_get_thread_num();
            printf("¿Que hago? ¿Que estoy provocando? %i \n", idHilo);
        }
    }//fin de seccion paralela
}
