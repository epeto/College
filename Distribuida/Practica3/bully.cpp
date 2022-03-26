//Algoritmo bully para elección distribuida.

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

    //Semilla de aleatoriedad
    srand(time(NULL)*(world_rank+1));

    //Definición de variables que se usarán durante el algoritmo:
    int activo = 1; //Determina si un nodo puede participar en la elección.
    int convocante = -1; //Nodo que convoca a elecciones la primera vez.
    char mensaje = '\0'; //Mensaje a enviar.
    int elegido = -1; //Cada nodo sabrá al final cuál fue el elegido.
    int depurador; //Para que el usuario elija si se mostrarán o no los mensajes.
    activo = (rand() % 100) > 25; //Un nodo tiene una probabilidad de 25% de estar inactivo.

    if(world_rank == world_size-1){
        activo = 0; //El nodo con el id más alto siempre está inactivo.
    }

    if(world_rank == 0){
        printf("Elija el modo depuración:\n0 -> Desactivado\n1 -> Activado\n");
        scanf("%d", &depurador);
        //Se elige un nodo que convocará a elecciones de manera aleatoria (no puede ser el último).
        convocante = rand() % (world_size-1);
    }
    MPI_Bcast(&depurador, 1, MPI_INT, 0, MPI_COMM_WORLD); //Se avisa a los demás si estará activo el modo depuración.
    MPI_Bcast(&convocante, 1, MPI_INT, 0, MPI_COMM_WORLD); //Se avisa a los demás quién será el convocante.

    if(world_rank == convocante)
        activo = 1; //El nodo convocante siempre debe estar activo
    
    MPI_Barrier(MPI_COMM_WORLD);

    if(activo){
        printf("Nodo %d activo\n", world_rank);
    }else{
        printf("Nodo %d inactivo\n", world_rank);
    }

	char* recv_data = new char[world_size]; //En este arreglo se reciben los mensajes.

    MPI_Request* send_request = new MPI_Request[world_size];
	MPI_Request* recv_request = new MPI_Request[world_size];
    int r;
    for(r=0; r<world_size; r++){
        recv_request[r] = MPI_REQUEST_NULL;
    }

    MPI_Barrier(MPI_COMM_WORLD);

/* A partir de aquí empieza el algoritmo **************************************/

    if(world_rank == convocante){ //Acciones del convocante.
        int i;
        //Convocar a los superiores a elecciones.
        mensaje = 'E'; //Elecciones
        if(depurador)
            printf("Nodo %d convoca a elecciones.\n", world_rank);
        
        for(i=world_rank+1; i<world_size; i++){
            MPI_Send(&mensaje, 1, MPI_CHAR, i, TAG_MESSAGE, MPI_COMM_WORLD);
        }

        //Espera a que sus superiores le respondan.
        int sup_activo = 0; //Para verificar si algún nodo superior está activo.
        for(i=world_rank+1; i<world_size; i++){
            MPI_Recv(&recv_data[i], 1, MPI_CHAR, i, TAG_MESSAGE, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
            if(recv_data[i] == 'R'){
                sup_activo = 1; //Significa que al menos un superior está activo.
                if(depurador)
                    printf("Nodo %d recibe respuesta %c de %d\n", world_rank, recv_data[i], i);
            }
        }

        if(sup_activo){
            //Espera el mensaje del id del coordinador.
            MPI_Recv(&elegido, 1, MPI_INT, MPI_ANY_SOURCE, TAG_MESSAGE, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        }else{
            //Se elige a sí mismo como coordinador y envía un mensaje a sus inferiores.
            if(depurador)
                printf("Nadie le contestó al nodo %d\n", world_rank);
            
            elegido = world_rank;
            for(i=0; i<world_rank; i++){
                MPI_Send(&world_rank, 1, MPI_INT, i, TAG_MESSAGE, MPI_COMM_WORLD);
            }
        }
    }else if(world_rank > convocante){ //Acciones de los superiores al convocante.
        int n_inf = world_rank - convocante -1; //Número de inferiores que participarán en la elección.
        if(n_inf < 0){n_inf = 0;}
        int n_sup = world_size -1 - world_rank; //Número de superiores que participarán en la elección.
        int i;
        //Espero el mensaje del convocante.
        MPI_Recv(&recv_data[convocante], 1, MPI_CHAR, convocante, TAG_MESSAGE, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        //Responderle al convocante.
        if(activo){
            mensaje = 'R'; //Recibido
            if(depurador)
                printf("Nodo %d recibe mensaje %c de %d\n", world_rank, recv_data[convocante], convocante);
        }else{
            mensaje = 'N'; //Null
        }
        
        MPI_Send(&mensaje, 1, MPI_CHAR, convocante, TAG_MESSAGE, MPI_COMM_WORLD);
        
        //Espero el mensaje del resto de los nodos inferiores y les envío un mensaje de recibido.
        for(i=convocante+1; i<world_rank; i++){
            MPI_Irecv(&recv_data[i], 1, MPI_CHAR, i, TAG_MESSAGE, MPI_COMM_WORLD, &recv_request[i]);
            MPI_Isend(&mensaje, 1, MPI_CHAR, i, TAG_MESSAGE, MPI_COMM_WORLD, &send_request[i]);
        }
        
        int recibidos = 0;
        while(recibidos < n_inf){
            int s;
		    MPI_Waitany(world_size, recv_request, &s, MPI_STATUS_IGNORE);
            if(depurador && activo && recv_data[s]=='E'){
                printf("Nodo %d recibe mensaje %c de %d\n", world_rank, recv_data[s], s);
            }
            recibidos++;
        }
        
        //Convocar a los superiores a elecciones y esperar respuesta.
        if(activo){
            mensaje = 'E'; //Elecciones
            if(depurador)
                printf("Nodo %d convoca a elecciones.\n", world_rank);
        }

        for(i=world_rank+1; i<world_size; i++){
            MPI_Isend(&mensaje, 1, MPI_CHAR, i, TAG_MESSAGE, MPI_COMM_WORLD, &send_request[i]);
            MPI_Irecv(&recv_data[i], 1, MPI_CHAR, i, TAG_MESSAGE, MPI_COMM_WORLD, &recv_request[i]);
        }
        
        int sup_activo = 0; //Para verificar si algún nodo superior está activo.
        recibidos = 0;
        while(recibidos < n_sup){
            int s;
		    MPI_Waitany(world_size, recv_request, &s, MPI_STATUS_IGNORE);
            recibidos++;
            if(recv_data[s] == 'R'){ //Nodo s está activo.
                sup_activo = 1;
                if(activo && depurador){
                    printf("Nodo %d recibe respuesta %c de %d\n", world_rank, recv_data[s], s);
                }
            }
        }

        if(activo){
            if(sup_activo){
                //Espera el mensaje del id del coordinador.
                MPI_Recv(&elegido, 1, MPI_INT, MPI_ANY_SOURCE, TAG_MESSAGE, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
            }else{
                //Se elige a sí mismo como coordinador y envía un mensaje a sus inferiores.
                if(depurador)
                    printf("Nadie le contestó al nodo %d\n", world_rank);

                elegido = world_rank;
                for(i=0; i<world_rank; i++){
                    MPI_Send(&world_rank, 1, MPI_INT, i, TAG_MESSAGE, MPI_COMM_WORLD);
                }
            }
        }
    } else { //Acciones de los inferiores al convocante.
        //Simplemente esperan el mensaje del id del coordinador.
        if(activo){
            MPI_Recv(&elegido, 1, MPI_INT, MPI_ANY_SOURCE, TAG_MESSAGE, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        }
    }

    MPI_Barrier(MPI_COMM_WORLD);
    //Finalmente el nodo elegido informa que será el nuevo coordinador.
    if(world_rank == elegido){
        printf("\nNodo %d es el nuevo coordinador\n",world_rank);
    }

    MPI_Finalize();
	return 0;
}

