#include  "queue.h"
#include <stdio.h>


queue **separa(FILE *archivo);
queue * cambia_binario(queue** datos);
int stoi(char * str);
void escribe(FILE *archivo , queue * texto);
void free_datos(queue ** datos);

