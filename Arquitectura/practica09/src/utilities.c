
#include "utilities.h"
#include <stdlib.h>
#include <string.h>
#include <math.h>

static char *TIPOI[] = {"lw" , "lh" , "lb" , "sw" , "sh" , "sb" , "addi" , "subi",
					   "andi" , "ori" , "beq" , "bgt" , "jr"}; //las intrucciones de tipo I
static char * TIPOR[] = {"add" , "sub" , "and" , "or"};// las intrucciones de Tipo R
/*
 * dado que dentro de las intrcciones que se dieron para realizar la practica nada mas
 * habia una intrccion de tipo j por lo que no se le reservo memoria solamente se
 * declaro la cadena en el metodo es intruccion
 */



char *strclone(char *str);
int equal(char *str1 , char* str2);
char *itos(int num , int num_digits);
char *itob(int number, int num_digits);
int * es_Instruccion(char * str);
char *cambia_memoria(char* str , queue** datos);
char *cambia_memoria_i(char* str , queue** datos);
char *registro_binario(char* str);
int es_etiqueta(char *str , queue* etiquetas);

/*
 * clona una cadena para que no apunten a l amisma direcion de memoria
 * str es la cadena a clonar
 */
char *strclone(char *str)
{
  int str_size = strlen(str);
  char *ptnew_str;
  ptnew_str =malloc(str_size+1);
  strcpy(ptnew_str, str);
  return ptnew_str;
}

/*
 * te dice si una cadena es igual a otra
 * str1 cadena 1
 * str2 cadena 2
 * regresa 0 si son diferentes 1 si son iguales
 */
int equal(char *str1 , char *str2)
{
	int i;
	for(i = 0 ; i < 7 ; i++){
		if (str1[i] != str2[i]){
			return 0;
		}else if(str1[i] == '\0' && str2[i] == '\0'){
				return 1;
		}
	}
	return 0;
}

/*
 * cambia un entero a una cadena
 * num el numero a cambiar
 * num_digits el numero de caracteres que se a va a querer en la cadena
 */

char *itos(int num , int num_digits)
{
	char* palabra;
	palabra  = malloc(sizeof(char) * (num_digits));
	int i;
	for(i = num_digits-1 ; i >= 0 ; i--){
		int caracter = num % 10;
		palabra[i] =(char) (caracter +48);
		num /= 10;
	}
	palabra[num_digits] = '\0';
	return palabra;
}

/*
 * dado un archivo saca los lexemas , las etiquetas , les asigan memoria
 * y te dice cual es el espacio de dexto y de datos y se regresa un arreglo de colas de longitud 4 donde
 * la primera tiene las etiquetas
 * la segunda los lexemas
 * la tercera la memoria
 * la cuarta el espacio de texto y el de datos en decimal
 * archivo es el archivo al cual se le quiere sacar lo anterior
 */
queue **separa(FILE *archivo)
{
	queue **arreglo =(queue **) malloc (4 * sizeof(queue*));
	int i , contador , j ,saltos , espacio_datos , espacio_texto , ref;
	char datos[40];
	char palabra[40];
	contador = 40;
	char indicador;
	saltos = 0;
	j = 0;
	queue *etiquetas = new_queue();
	queue *palabras = new_queue();
	queue *memoria = new_queue();
	queue *espacio = new_queue();
	ref = 0;
	espacio_texto = 0;
	espacio_datos = 0;
	rewind(archivo);
	while (contador == 40){
		contador = fread( datos , sizeof(char), 40 , archivo);
		for(i = 0 ; i < contador ; i++){

			if((datos[i] == ' ' || datos[i] == '\t' || datos[i] == '\n') && j == 0 ){
				;
			}else{
				palabra [j] = datos[i];
				j++;
				if(datos[i] == ' ' || datos[i] == '\n' || datos[i] == '\t'){
					indicador = palabra[j-2];
					palabra[j-1] = '\0';
					if(datos[i] == '\n'){
						if(!(equal(palabra , ".data")) && !(equal(palabra , ".text"))){
							saltos += 4;
							if(ref == 0){
								espacio_datos += 4;
							}else{
								espacio_texto += 4;
							}
						}else if(equal(palabra , ".text")){
							saltos  = 2048;
							ref = 1;
						}
					}
					if(indicador != ':'){
						enqueue( strclone(palabra) , palabras);
					}else{
						palabra[j-2] = '\0';
						enqueue( strclone(palabra) , etiquetas);
						enqueue( itos(saltos , 4) , memoria);

					}
					j = 0;
				}
			}

		}

	}
	palabra[j] = '\0';
	enqueue(strclone(palabra), palabras);
	enqueue(itos(espacio_datos , 10) , espacio);
	enqueue("\n" , espacio);
	enqueue(itos(espacio_texto , 10) , espacio);
	enqueue("\n" , espacio);
	arreglo[0] = etiquetas;
	arreglo[1] = palabras;
	arreglo[2] = memoria;
	arreglo[3] = espacio;
	return arreglo;
}

/*
 * cambia  un entero a binario
 * numberr numero a cambiar
 * num_digits el numero de caracteres de la carde resultante
 */
char *itob(int number, int num_digits)
{
  char *ptdigit;
  ptdigit = malloc(num_digits +1);
  *(ptdigit + num_digits +1) = '\0';
  while (number > 0 && num_digits >= 0) {
    *(ptdigit + num_digits--) = (number % 2) ? '1': '0'; // Línea muy abusiva de la sintaxis
    number /= 2;
  }
  while (num_digits >=0)
    *(ptdigit + num_digits--) = '0';
  return ptdigit;
}

/*
 * regresa un arreglo de 2 enteros el que te dice si es una intruccion o no lo es
 * Regresa [0 , 0] si no es una instruccion
 * Regresa [1 , 18] si es de tipo J
 * Regresa [2 , 1-13]  si es de tipo I
 * Regresa [3 , 1-4] si es de tipo R como el op code es  cero te dice la funcion que se va a hacer
 */

int * es_Instruccion(char * str)
{
	int * resultado = malloc(sizeof(int)*2);
	if(equal("j" , str)){
		resultado[0] = 1;
		resultado[1] = 18;
		return resultado;
	}
	int i ;
	resultado[0] = 2;
	for(i = 0 ; i < 13 ; i++){
		if(equal(TIPOI[i] , str)){
			resultado[1] = i+1;
			return resultado;
		}
	}
	resultado[0] = 3;
	for(i = 0 ; i < 4  ; i++){
		if(equal(TIPOR[i] , str)){
			resultado[1] = i+1;
			return resultado;
		}
	}
	resultado[0] = 0;
	resultado[1] = 0;
	return resultado;
}

/*
 * combierte una cadena de caracteres a numeros entero decimal
 */
int stoi(char * str)
{
	int i;
	int size = strlen(str);
	int actual;
	int resultado = 0;
	for(i = 0; i < size ; i++){
		actual = (int)str[i] - 48;
		actual *= pow(10 , size-i-1);
		resultado += actual;
	}
	return resultado;
}

/*
 * cambia una etiqueta por su respectivo numero de memoria y te lo regresa en binario a 26 bits que se definio antes en separa
 * str la etiquta a cambiar
 * datos los datos del texto
 */
char *cambia_memoria(char* str , queue** datos)
{
	char * actual ;
	int lugar = -1;
	int contador = 0;
	while(lugar == -1){
		actual = dequeue(datos[0]);
		enqueue(strclone(actual) , datos[0]);
		contador++;
		if(equal(actual , str)){
			lugar  = contador;
		}


	}
	while(contador ){
		actual = dequeue(datos[2]);
		contador--;
		enqueue(strclone(actual) , datos[2]);
	}
	char * resultado = itob(stoi(actual) , 26);
	return resultado;
}

/*
 * cambia una etiqueta por su respectivo numero de memoria y te lo regresa en binario a 16 bits que se definio antes en separa
 * str la etiquta a cambiar
 * datos los datos del texto
 */
char *cambia_memoria_i(char* str , queue** datos)
{
	char * actual ;
	int lugar = -1;
	int contador = 0;
	while(lugar == -1){
		actual = dequeue(datos[0]);
		enqueue(strclone(actual) , datos[0]);
		contador++;
		if(equal(actual , str)){
			lugar  = contador;
		}


	}
	while(contador ){
		actual = dequeue(datos[2]);
		contador--;
		enqueue(strclone(actual) , datos[2]);
	}
	char * resultado = itob(stoi(actual) , 16);
	return resultado;
}

/*
 * cambia un registro por su respectivo binario a 5 bits
 * str el registro a cambiar
 */
char *registro_binario(char* str){
	char * aux = malloc(sizeof(char) * 3);
	aux[0] = str[1];
	aux[1] = str[2];
	aux[2] = '\0';
	char * resultado = itob(stoi(aux) , 5);
	return resultado;
}

/*
 * te dice si una cadena es una etiqueta
 * regresa 0 si la cadena no es una etiqueta 1 si es una etiqueta
 * str la cadena que se sabra si esta en las etiquetas
 * etiquetas la cola con las etiquetas
 */
int es_etiqueta(char *str , queue* etiquetas){
	queue* aux = new_queue();
	char *actual;
	int resultado = 0;

	while(size(etiquetas) > 0){
		actual = dequeue(etiquetas);
		enqueue(strclone(actual) , aux);
		if(equal(actual, str)){
			resultado = 1;
		}
	}

	while((size(aux) > 0)){
		enqueue(dequeue(aux), etiquetas);
	}
	free_queue(aux);
	return resultado;
}
/*
 * cambia los datos del texto que salen de separa a su respectiva representacion en binario
 * en formato de un archivo objeto
 * regresa una cola con las lineas ya en binario de los datos
 * datos_texto los datos que se van acambiar a binario
 */
queue * cambia_binario(queue** datos_texto)
{
	queue *datos = new_queue();
	queue *texto = new_queue();
	while(size(datos_texto[3])>0){
		enqueue(dequeue(datos_texto[3]) , texto);
	}
	char *actual;
	int i;
	int j;
	int contador_bytes = 0;
	char *inmediato = malloc(sizeof(char)* 100);
	char * registro = malloc(sizeof(char) * 3);
	char referencia;
	actual = dequeue(datos_texto[1]);
	while(!equal(actual , ".text") && size(datos_texto[1]) > 0){
		if(equal(actual , ".word")){
			switch(contador_bytes){
			case 1:
				enqueue(itob(0,24) , datos);
				enqueue("\n" , datos);
				break;
			case 2:
				enqueue(itob(0,16) , datos);
				enqueue("\n" , datos);

				break;
			case 3:
				enqueue(itob(0,8) , datos);
				enqueue("\n" , datos);
				break;
			}
			actual = dequeue(datos_texto[1]);
			enqueue(itob(stoi(actual) , 32) , datos);
			enqueue("\n" , datos);

		}
		if(equal(actual , ".byte")){
			if(contador_bytes == 4){
				contador_bytes = 0;
				enqueue("\n" , datos);
			}
			actual = dequeue(datos_texto[1]);
			enqueue(itob(stoi(actual) , 8) , datos);
			contador_bytes++;

		}
		actual = dequeue(datos_texto[1]);
	}
	while(size(datos_texto[1]) > 0){
		actual = dequeue(datos_texto[1]);
		int * instruccion_actual = es_Instruccion(actual);
		switch(instruccion_actual[0]){
		case 1:
			enqueue(itob(instruccion_actual[1] , 6),texto);
			actual = dequeue(datos_texto[1]);
			if(equal(actual ,",")){
				actual =  dequeue(datos_texto[1]);
			}
			enqueue(cambia_memoria(actual , datos_texto),texto);
			enqueue("\n" , texto);
			break;

		case 2:
			enqueue(itob(instruccion_actual[1] , 6),texto);

			switch(instruccion_actual[1]){
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
				actual = dequeue(datos_texto[1]);
				if(equal(actual ,",")){
					actual =  dequeue(datos_texto[1]);
				}
				char *save = registro_binario(actual);
				actual = dequeue(datos_texto[1]);
				if(equal(actual ,",")){
					actual =  dequeue(datos_texto[1]);
				}
				if(es_etiqueta(actual , datos_texto[0])){
					enqueue(save , texto);
					enqueue(itob(0 ,5), texto);
					enqueue(cambia_memoria_i(actual , datos_texto), texto);
				}else{
					referencia = actual[0];
					i = 0;
					while(referencia != '('){
						inmediato[i] = referencia;
						i++;
						referencia = actual[i];
					}

					inmediato[i] = '\0';
					j = 0;
					i++;
					referencia = actual[i];
					while(referencia != ')'){
						registro[j] = referencia;
						i++;
						j++;
						referencia = actual[i];
					}
					registro[j] = '\0';
					enqueue(save , texto);
					enqueue(registro_binario(registro) , texto);
					enqueue(itob(stoi(inmediato) , 16) , texto);
				}
				enqueue("\n" , texto);
				break;
			case 7:
			case 8:
			case 9:
			case 10:
				i = 2;
				while(i > 0){
					actual = dequeue(datos_texto[1]);
					if(equal(actual ,",")){
						actual =  dequeue(datos_texto[1]);
					}
					enqueue(registro_binario(actual) , texto);
					i--;
				}
				actual = dequeue(datos_texto[1]);
				if(equal(actual ,",")){
						actual =  dequeue(datos_texto[1]);
				}
				enqueue(itob(stoi(actual), 16) , texto);
				enqueue("\n" , texto);
				break;
			case 11:
			case 12:
				i = 2;
				while(i > 0){
					actual = dequeue(datos_texto[1]);
					if(equal(actual ,",")){
						actual =  dequeue(datos_texto[1]);
					}
					enqueue(registro_binario(actual) , texto);
					i--;
				}
				actual = dequeue(datos_texto[1]);
				if(equal(actual ,",")){
						actual =  dequeue(datos_texto[1]);
				}
				enqueue(cambia_memoria_i(actual , datos_texto), texto);
				enqueue("\n" , texto);
				break;
			case 13:
				actual = dequeue(datos_texto[1]);
				if(equal(actual ,",")){
					actual =  dequeue(datos_texto[1]);
				}
				enqueue(registro_binario(actual) , texto);
				enqueue(itob(0,5) ,texto);
				enqueue(itob(0,16) ,texto);
				enqueue("\n" , texto);

			}

			break;
		case 3:
			enqueue(itob(0 , 6),texto);
			i = 3;
			while(i > 0){
				actual = dequeue(datos_texto[1]);
				if(equal(actual ,",")){
					actual =  dequeue(datos_texto[1]);
				}
				enqueue(registro_binario(actual) , texto);
				i--;
			}
			enqueue(itob(instruccion_actual[1] , 11) , texto);
			enqueue("\n" , texto);
			break;
		}

	}
	while(size(datos) > 0){
		enqueue(dequeue(datos) , texto);
	}
	free_queue(datos);
	return texto;

}

/*
 * escribe los satos que estan en texto en archivo en un formato de un archivo objeto donde cada linea
 * exeptuando las priemeras 2 y las ultimas son de 32 bits
 */
void escribe(FILE *archivo , queue * texto)
{
	char * palabra;
	int i  = 0;
	while(size(texto) > 0){
		palabra = dequeue(texto);
		if(!equal("\n" , palabra)){
			for(i = 0 ; i < strlen(palabra) ; i++){
				palabra[i] = palabra[i+1];
			}
			fwrite(palabra , sizeof(char) , strlen(palabra) , archivo);
		}else{
			fwrite("\n", sizeof(char) , 1 , archivo);
		}
	}
	free_queue(texto);
}

/*
 * libera la memoria de los datos
 */
void free_datos(queue ** datos)
{
	free_queue(datos[0]);
	free_queue(datos[2]);
	free_queue(datos[3]);
	free_queue(datos[1]);
}





