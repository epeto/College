
#include <stdio.h>
#include <string.h>
#include "utilities.h"

/*
 * Programa ejecutable es el que se llama para que se escriba el archivo con los nmumeros en binario el archivo resultante se llama resultado.txt
 */
int main(){
	char nombre_archivo[40] ="";

	printf("Escriba a continuacion el nombre del archivo: \n");
	scanf("%s", nombre_archivo);
	FILE *fichero;
	fichero = fopen(nombre_archivo , "r");
	if( fichero )
	{
		queue **elementos_texto = separa(fichero);
		queue * texto = cambia_binario(elementos_texto);

		FILE* fichero_r;
		fichero_r = fopen("resultado.txt" , "w+");
		if( !fichero_r ){
			 printf( "Error archivo salida (NO CREADO)\n" );
			 return 1;
		}else{
			escribe(fichero_r , texto);
		}
		if( fclose(fichero) ){
			printf( "Error: fichero NO CERRADO\n" );
		}
		free_datos(elementos_texto);

		if(fclose(fichero_r)){
			printf( "Error: fichero de resultado NO CERRADO\n" );
		}
	}else
	 {
		printf( "Error (NO ABIERTO)\n" );
		return 1;
	 }
}

