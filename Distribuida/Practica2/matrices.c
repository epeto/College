
#include <stdio.h>
#include <time.h>
#include <stdlib.h>

void printMatChar(char** matriz, int tam1, int tam2){
    int i,j;
    for(i=0; i<tam1; i++){
        for(j=0; j<tam2; j++){
            printf("%c",matriz[i][j]);
        }
        printf("\n");
    }
}

//Llena una matriz con un solo caracter.
void llenaMatChar(char** matriz, int tam1, int tam2, char caracter){
    int i,j;
    for(i=0; i<tam1; i++){
        for(j=0; j<tam2; j++){
            matriz[i][j] = caracter;
        }
    }
}

int main(int argc, char** argv){
    srand(time(NULL));
    int i,j;
    char** ejemplar;

    ejemplar = (char **)malloc (10*sizeof(char *));
    for (i=0;i<10;i++)
        ejemplar[i] = (char *) malloc (10*sizeof(char));

    llenaMatChar(ejemplar, 10, 10, 'N');

    printMatChar(ejemplar,10,10);
}