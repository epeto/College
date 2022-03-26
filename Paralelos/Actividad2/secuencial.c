
#include <stdio.h>
#include <stdlib.h>

int main(){
    int a[] = {9,1,4,6,8,0,6,10,7,2,13};
    int b[] = {15,14,12,11,10,9,8,7,6,5,3};
    int n = 11;
    int i;
    int pp = 0;
    for(i=0; i<n; i++){
        pp += a[i]*b[i];
    }
    printf("%d\n", pp); // Se espera un 552
}

