#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <omp.h>
#include <time.h>


int *  sumar_acarreo(int *res, int *acarreo, int longit){
  //guarda resultado de la suma entre el resultado anterior y el acarreo
  int * resultado = (int *)malloc((longit)*sizeof(int));
  int aux = 0;
  for(int i = 0; i < longit; i++){
    if(acarreo[i] == 1)
      aux = 1;
  }

  //aún tenemos que sumar el acarreo
  if (aux == 1){
    int nuevoAca[longit];

    for(int i = 0; i < longit; i++){
      resultado[i] = 0;
      nuevoAca[i] = 0;
    }

    //suma paralela del resultado anterior con el acarreo
    int idHilo;
    omp_set_num_threads(longit);
    #pragma omp parallel shared(idHilo)
    {
    idHilo = omp_get_thread_num();
      if ((res[idHilo] == 1) && (acarreo[idHilo] == 1)){
        resultado[idHilo] = 0;
        nuevoAca[idHilo-1] = 1;
      }
      else{
      	resultado[idHilo] = res[idHilo] + acarreo[idHilo];
      }
    }

    sumar_acarreo(resultado, nuevoAca, longit);
  }
  else{
    //ya no hay acarreo, ya terminamos
    return res;
  }
}


//saca la potencia, dada una base y un exponente
int pot( int base, int exp)
{
  if(exp == 0)
    {
      return 1;
    } else
    {
      return base*pot(base, exp-1);
    }
}

int main(int argc, char **argv){
  int potencia;
  if (argc < 2)
    {
      printf("por favor especifique el numero\n");
      exit(1);
    }

  sscanf(argv[1], "%i", &potencia);
  int poten = pot(2,potencia);

  //primera entrada
  int An[poten];
  //segunda entrada
  int Bn[poten];
  //Resultados
  int Cn[poten + 1];
  //Auxiliar
  int Dn[poten +1];

  //Rellenamos aleatoriamente los arreglos de entrada
  srand(time(NULL));
  for (int i=0 ;i < poten; i++)
  {
    An[i] = rand() % (2);
    Bn[i] = rand() % (2);
  }

  //Inicializamos los arreglos de resultados y de acarreo
  for (int i=0 ;i <=poten; i++)
  {
    Cn[i] = 0;
    Dn[i] = 0;
  }

  printf("El arreglo A: \n");
  for (int i=0 ;i < poten; i++)
  {
    printf("%i",An[i]);
  }
  printf("\n");

  printf("El arreglo B: \n");
  for (int i=0 ;i <poten; i++)
  {
    printf("%i",Bn[i]);
  }
  printf("\n");

  double start;
  double end;
  start = omp_get_wtime();

  //Calculamos la suma sin acarreo de las entradas, y calculamos el acarreo inicial
  int idHilo;
  omp_set_num_threads(poten);
  #pragma omp parallel shared(idHilo)
    {
      idHilo = omp_get_thread_num();
      if((An[idHilo] == 1)&&(Bn[idHilo]==1)){
        Cn[idHilo+1]=0;
        Dn[idHilo]=1;
      }else{
        Cn[idHilo+1] = An[idHilo] + Bn[idHilo];
      }
    }

  //Resultados finales
  int *resultado;
  resultado = sumar_acarreo(Cn, Dn, poten+1);

  end = omp_get_wtime();

  //Imprimiendo el acarreo
  printf("Acarreo: \n");
  for (int i=0 ;i <=poten; i++)
  {
    printf("%i",Dn[i]);
  }
  printf("\n");

  printf("Resultado: \n");
  for(int i = 0; i <= poten; i++){
   printf("%d", resultado[i]);
  }
  printf("\n");
  printf("Tiempo de ejecución %f \n", end - start);

  return 0;
}
