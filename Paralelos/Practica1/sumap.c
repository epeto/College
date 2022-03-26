
#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>
#include <omp.h>

int main(int argc, char** argv){
    // verifica el numero de hilos que se paso como parametro
    if (argc < 2) {
        printf("por favor especifique el numero de hilos \n");
        exit(1);
    }
    int numHilos, idHilo;
    omp_set_num_threads(100);

    sscanf(argv[1], "%i", &numHilos);
    if (numHilos < 1){
        printf("Numero de hilos no valido (%i)\n", numHilos);
        exit(1);
    }
    omp_set_num_threads(numHilos);

    long int sumaTotal=0;
    long int sumaParcial;
    long int a; // Límite izquierdo del intervalo.
    long int b; // Límite derecho del intervalo.
    long int limite = 1000000000;
    long int intervalo = limite/numHilos; // Tamaño de cada intervalo.
    long int i;

    struct timeval inicio, fin;//nos permiten medir el tiempo de ejecucion
    gettimeofday(&inicio, NULL);//guarda el tiempo al inicio del programa
    int tiempo;
    //inicia seccion paralela
    #pragma omp parallel private(idHilo, sumaParcial, a, b, i)
    {
        idHilo = omp_get_thread_num();
        a = idHilo*intervalo+1;
        b = (idHilo+1)*intervalo;
        if(idHilo == numHilos-1){
            b = limite;
        }
        //Se realiza la suma parcial en cada hilo.
        sumaParcial = 0;
        for(i=a; i<=b; i++){
            sumaParcial += i;
        }
		#pragma omp critical
		{
			sumaTotal+=sumaParcial;
		}
    }//fin de seccion paralela
    gettimeofday(&fin, NULL); //guarda el tiempo al final del programa
	tiempo = (fin.tv_sec - inicio.tv_sec)* 1000000 + (fin.tv_usec - inicio.tv_usec);
	printf("suma: %li  \ntiempo de ejecucion: %i microsegundos\n",sumaTotal, tiempo); //imprime resultados
    //Valor: 500000000500000000
}

