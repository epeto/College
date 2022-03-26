//Algoritmo de consenso de los generalez bizantinos.

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <mpi.h>

/**
 * Devuelve el elemento más frecuente de un arreglo de A's y R's.
 * El arreglo puede tener elementos diferentes a 'A' y 'R' pero no se cuentan.
 */
char mayoria(char arr[], int tam){
    int cuenta_A = 0;
    int cuenta_R = 0;
    int i;

    for(i=0; i<tam; i++){
        if(arr[i] == 'A'){
            cuenta_A++;
        }else if(arr[i] == 'R'){
            cuenta_R++;
        }
    }

    if(cuenta_A > cuenta_R){
        return 'A';
    }else{
        return 'R';
    }
}

/**
 * Imprime una matriz de caracteres.
 * matriz: apuntador de arreglo bidimensional.
 * tam1: tamaño de una dimensión.
 * tam2: tamaño de la otra dimensión.
 * id: quién envía la matriz. */
void printMatChar(char** matriz, int tam1, int tam2, int id){
    int i,j;
    printf("Matriz de %d\n  ", id);
    for(i=0; i<tam1; i++){
        if(i<10)
            printf("%d ",i);
        else
            printf("%d",i);
    }

    printf("\n");
    for(i=0; i<tam1; i++){
        if(i<10)
            printf("%d ",i);
        else
            printf("%d",i);
        
        for(j=0; j<tam2; j++){
            printf("%c ",matriz[i][j]);
        }
        char may = mayoria(matriz[i],tam2);
        printf(" %c\n",may);
    }
    printf("\n");
}

/**
 * Llena una matriz con un solo caracter.
 * matriz: apuntador de arreglo bidimensional.
 * tam1: tamaño de una dimensión.
 * tam2: tamaño de la otra dimensión.
 * caracter: caracter a colocar en cada casilla de la matriz. */
void llenaMatChar(char** matriz, int tam1, int tam2, char caracter){
    int i,j;
    for(i=0; i<tam1; i++){
        for(j=0; j<tam2; j++){
            matriz[i][j] = caracter;
        }
    }
}

//Función principal.
int main(int argc, char** argv){
    // Inicializa el entorno de MPI
    MPI_Init(&argc, &argv);
    // Encuentra el rango y el tamaño.
    int world_rank;
    MPI_Comm_rank(MPI_COMM_WORLD, &world_rank);
    int world_size;
    MPI_Comm_size(MPI_COMM_WORLD, &world_size);

    //Semilla de aleatoriedad
    srand(time(NULL)*(world_rank+1));

    int tipot; //Representa al tipo de traidor.
    int muestra_matriz; //Decide si se muestra la matriz de cada nodo.
    if(world_rank == 0){
        printf("Elige el tipo de traidor\n0 -> Por caída\n1 -> Bizantino\n2 -> Aleatorio\n");
        scanf("%d",&tipot);
        printf("¿Mostrar matriz en segunda vuelta?\n0 -> No\n1 -> Sí\n");
        scanf("%d",&muestra_matriz);
    }
    MPI_Bcast(&tipot, 1, MPI_INT, 0, MPI_COMM_WORLD); //Se hace un broadcast del tipo de traidor.
    MPI_Bcast(&muestra_matriz, 1, MPI_INT, 0, MPI_COMM_WORLD); //Se hace un broadcast de muestra matriz.

    if(tipot!=0 && tipot!=1)
        tipot = rand()%2;
    
    int i,j; //Se usarán para los ciclos for.
    char mp = (rand() % 2)?'A':'R'; //Mi plan inicial.
    char plan_inicial[world_size+1]; //Arreglo donde se guardan los planes de todos.
    plan_inicial[world_rank] = mp;
    plan_inicial[world_size] = '\0';

    char** planes_reportados = (char **)malloc (world_size*sizeof(char *));
    for (i=0; i<world_size; i++)
        planes_reportados[i] = (char *) malloc (world_size*sizeof(char));
    
    llenaMatChar(planes_reportados, world_size, world_size, 'N');
    printf("%d elige plan %c\n", world_rank, mp);
    int soy_traidor = (rand() % 100) < 25; //Un nodo tiene una probabilidad de 25% de ser traidor.
    int caido = 0; //Variable lógica que determina si un nodo se cayó.
    MPI_Barrier(MPI_COMM_WORLD);
    if(soy_traidor)
        printf("Nodo %d es traidor de tipo %d\n", world_rank, tipot);

/* Primera ronda *****************************************************************
   Envío mi plan al resto de los nodos.*/

    //Se envía el plan inicial a todos los demás nodos.
    if(soy_traidor){
        if(tipot){ //Bizantino
            char mensaje;
            for(i=0; i<world_size; i++){
                if(i != world_rank){
                    mensaje = (rand() % 2)?'A':'R';
                    MPI_Send(&mensaje, 1, MPI_CHAR, i, 0, MPI_COMM_WORLD);
                }
            }
        }else{ //Por caída
            char nada = 'N';
            for(i=0; i<world_size; i++){
                if(i != world_rank){
                    if(!caido){ //Si no se ha caído, envía un mensaje normal.
                        MPI_Send(&mp, 1, MPI_CHAR, i, 0, MPI_COMM_WORLD);
                        caido = (rand() % 100) < 15; //Tiene un 15% de probabilidad de caerse.
                    }else{ //Si ya se cayó envía una 'N'
                        MPI_Send(&nada, 1, MPI_CHAR, i, 0, MPI_COMM_WORLD);
                    }
                }
            }
        }
    }else{ //Leal
        for(i=0; i<world_size; i++){
            if(i != world_rank){
                MPI_Send(&mp, 1, MPI_CHAR, i, 0, MPI_COMM_WORLD);
            }
        }
    }

    //Se recibe el plan de los demás
    for(i=0; i<world_size; i++){
        if(i != world_rank){
            MPI_Recv(&plan_inicial[i], 1, MPI_CHAR, i, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        }
    }
    MPI_Barrier(MPI_COMM_WORLD);
    printf("%d recibió planes: %s\n", world_rank, plan_inicial);

    /* Segunda ronda *****************************************************************
       Envío los planes que recibí a los demás nodos*/
    if(soy_traidor){
        if(tipot){ //Bizantino
            char mensaje;
            for(i=0; i<world_size; i++){
                for(j=0; j<world_size; j++){
                    if(i != world_rank){
                        mensaje = (rand() % 2)?'A':'R';
                        MPI_Send(&mensaje, 1, MPI_CHAR, j, 0, MPI_COMM_WORLD);
                    }
                }
            }
        }else{ //Por caída
            for(i=0; i<world_size; i++){
                if(i != world_rank){
                    if(!caido){ //Si no se ha caído, envía un mensaje normal.
                        for(i=0; i<world_size; i++){
                            for(j=0; j<world_size; j++){
                                if(j!=world_rank && i!=world_rank && i!=j){
                                    MPI_Send(&plan_inicial[i], 1, MPI_CHAR, j, 0, MPI_COMM_WORLD);
                                }
                            }
                            caido = (rand() % 100) < 15; //Tiene un 15% de probabilidad de caerse.
                        }
                    }else{ //Si ya se cayó envía una 'N'
                        char nada = 'N';
                        for(i=0; i<world_size; i++){
                            for(j=0; j<world_size; j++){
                                MPI_Send(&nada, 1, MPI_CHAR, j, 0, MPI_COMM_WORLD);
                            }
                        }
                    }
                }
            }
        }
    }else{ //Leal
        for(i=0; i<world_size; i++){
            for(j=0; j<world_size; j++){
                if(j!=world_rank && i!=world_rank && i!=j){
                    MPI_Send(&plan_inicial[i], 1, MPI_CHAR, j, 0, MPI_COMM_WORLD);
                }
            }
        }
    }

    //Se reciben los mensajes de los demás.
    for(i=0; i<world_size; i++){
        for(j=0; j<world_size; j++){
            if(j!=world_rank && i!=world_rank && i!=j){
                MPI_Recv(&planes_reportados[i][j], 1, MPI_CHAR, j, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
            }
        }
    }

    //Copio los planes iniciales a la matriz.
    for(i=0; i<world_size; i++){
        planes_reportados[i][i] = plan_inicial[i];
    }
    MPI_Barrier(MPI_COMM_WORLD);
    
    if(muestra_matriz){
        printMatChar(planes_reportados, world_size, world_size, world_rank);
    }

    char mpf[world_size]; //Mayoría por fila (de la matriz).

    for(i=0; i<world_size; i++){
        mpf[i] = mayoria(planes_reportados[i], world_size);
    }

    char may_abs = mayoria(mpf, world_size); //Mayoría absoluta

    MPI_Barrier(MPI_COMM_WORLD);
    if(may_abs == 'A'){
        printf("Decisión final de %d: Atacar\n",world_rank);
    }else{
        printf("Decisión final de %d: Retirarse\n",world_rank);
    }

    MPI_Finalize();
}

