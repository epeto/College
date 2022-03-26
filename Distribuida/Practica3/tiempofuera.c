

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <mpi.h>
#include <unistd.h>
#define TAG_MESSAGE 1

//Función principal.
int main(int argc, char** argv){
    // Inicializa el entorno de MPI
    MPI_Init(&argc, &argv);
    // Encuentra el rango y el tamaño.
    int world_rank;
    MPI_Comm_rank(MPI_COMM_WORLD, &world_rank);
    int world_size;
    MPI_Comm_size(MPI_COMM_WORLD, &world_size);

    MPI_Request request;
    char mensaje = '\0';
    if(world_rank == 0){
        mensaje = 'e';
        MPI_Send(&mensaje, 1, MPI_CHAR, 1, TAG_MESSAGE, MPI_COMM_WORLD);
        //MPI_Isend(&mensaje, 1, MPI_CHAR, 1, TAG_MESSAGE, MPI_COMM_WORLD, &request);
        printf("El proceso 0 envió el mensaje %c a 1\n", mensaje);
    }else{
        MPI_Irecv(&mensaje, 1, MPI_CHAR, MPI_ANY_SOURCE, TAG_MESSAGE, MPI_COMM_WORLD, &request);
        int intentos, bandera = 0;
        for (intentos = 0; intentos < 3; ++intentos){
            MPI_Test(&request, &bandera, MPI_STATUS_IGNORE);//verifica si la peticion ya fue atendida
            if (bandera!=0)
                break;
            else
                sleep(1); //Se duerme un segundo.
        }

        if(bandera!=0){
            printf("Recibí mensaje %c de 0\n", mensaje);
        }else{
            printf("No recibí ningún mensaje de 0\n");
        }
    }

    MPI_Finalize();
	return 0;
}



