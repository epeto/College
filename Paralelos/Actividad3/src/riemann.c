// Suma de Riemann para calcular una integral definida.

#include <stdio.h>
#include <stdlib.h>
#include <omp.h>
#include <math.h>

// f(x) = 100 − (x−10)⁴ 4 + 50(x−10)² − 8x
double foo(double x){
    return (100.0 - pow(x-10.0, 4.0) + 50.0*pow(x-10.0, 2.0) - 8.0*x);
}

/**
 * Calcula una integral de foo.
 * @param a límite izquierdo.
 * @param b límite derecho.
 * @param n número de hilos.
 * @return área bajo la curva foo entre a y b.
 */
double riemann(double a, double b, int n){
    int id, i;
    double idouble;
    double base, altura; //servirán para las dimensiones de cada rectángulo
    double sumaP; //suma parcial que calcula cada hilo.
    double sumaT = 0.0; //suma total.
    double ndouble = (double) n;
    base = (b-a)/(5.0*ndouble); //Tamaño de la base de un rectángulo.
    omp_set_num_threads(n);
    #pragma omp parallel private(id, i, sumaP, altura, idouble) shared(sumaT, base) //Región paralela
    {
        id = omp_get_thread_num();
        sumaP = 0.0;
        for(i = id*5; i<(id+1)*5; i++){
            idouble = (double) i;
            altura = foo(idouble*base + a);
            sumaP = sumaP + base*altura;
        }
        #pragma omp critical
        {
            sumaT += sumaP;
        }
    }
    return sumaT;
}

// Función principal.
int main(int argc, char** argv){
    int nproc = atoi(argv[1]);
    double area = riemann(3.0, 17.0, nproc);
    printf("El área bajo la curva\nf(x) = 100 − (x−10)⁴ 4 + 50(x−10)² − 8x\nen el intervalo [3,17] es:\n%f\n", area);
}
