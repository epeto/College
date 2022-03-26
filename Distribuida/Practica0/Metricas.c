#include <stdio.h>

void speedup(float t[], float s[], int n){
    float t1 = t[0];
    float res = 0;
    int i;
    for(i=0;i<n;i++){
        s[i] = t1/t[i]; //Se guarda el speedup en s.
    }
}

void eficiencia(float nnodos[], float s[], float e[], int n){
    int i;
    for(i=0;i<n;i++){
        e[i] = s[i]/nnodos[i]; //Se guarda la eficiencia en e
    }
}

void fraccion(float nnodos[], float s[], float fs[], int n){
    int i;
    for(i=0;i<n;i++){
        fs[i] = ((1/s[i])-(1/nnodos[i]))/(1-(1/nnodos[i]));
    }
}

int main(){
    float p[] = {1, 2, 3, 4, 6, 8, 10, 20};
    float t[] = {271472, 257654, 256413, 260133, 260528, 261617, 267476, 264899};
    float s[8];
    float e[8];
    float fs[8];

    speedup(t, s, 8);
    eficiencia(p, s, e, 8);
    fraccion(p, s, fs, 8);

    int i;
    printf("Número de nodos n:\n");
    for(i=0;i<8;i++){
        printf("%f\n", p[i]);
    }

    printf("Tiempo t(n):\n");
    for(i=0;i<8;i++){
        printf("%f\n", t[i]);
    }

    printf("Speedup s(n):\n");
    for(i=0;i<8;i++){
        printf("%f\n", s[i]);
    }

    printf("Eficiencia e(n):\n");
    for(i=0;i<8;i++){
        printf("%f\n", e[i]);
    }

    printf("Fracción serial f(n):\n");
    for(i=0;i<8;i++){
        printf("%f\n", fs[i]);
    }

    return 0;
}


