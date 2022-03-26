#include <stdio.h>
#include <unistd.h>

int main(){
    printf("¿Cuántos segundos debo dormir?\n");
    unsigned int timeout;
    scanf("%u", &timeout);
    sleep(timeout);
    printf("Ya desperté\n");
    return 0;
}