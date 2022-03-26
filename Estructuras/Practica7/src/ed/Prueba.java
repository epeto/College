
package ed;

import ed.estructuras.lineales.*;

public class Prueba{
    public static void main(String[] args){
        ColaArreglo<Integer> cola = new ColaArreglo<>(new Integer[0]);
        for(int i=-32; i<=32; i++){
            cola.forma(i);
        }
        System.out.println("Cola esperada con elementos de -32 a 32:\n"+cola.toString());
        for(int i=0; i<=32; i++){
            cola.atiende();
        }
        System.out.println("Cola esperada con elementos de 1 a 32:\n"+cola.toString());
        for(int i=0; i<19; i++){
            cola.atiende();
        }
        System.out.println("Cola esperada con elementos de 20 a 32:\n"+cola.toString());
        for(int i=33; i<=50; i++){
            cola.forma(i);
        }
        System.out.println("Cola esperada con elementos de 20 a 50:\n"+cola.toString());
        System.out.println("Elemento al inicio de la cola (se espera un 20):\n"+cola.mira().toString());
        System.out.println("Elementos eliminados uno por uno:");
        while(!cola.isEmpty()){
            System.out.print(cola.atiende().toString()+" ");
        }
        System.out.println();
        System.out.println("Cola actual (se espera vacía):\n"+cola.toString());
    }
}
