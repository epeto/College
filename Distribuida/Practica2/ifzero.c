#include <stdio.h>

int main(){
    printf("Escribe un número\n");
    int n;
    scanf("%d",&n);
    int res = (n>10)?20:1;
    printf("%d\n",res);
}
