
package ed;

import ed.estructuras.lineales.*;

public class Prueba{
    public static void main(String[] args){
        PilaLigada<String> pila = new PilaLigada<>();
        pila.empuja("Javier");
        pila.empuja("llamo");
        pila.empuja("me");
        pila.empuja("no");
        pila.empuja("Yo");
        System.out.println("Elemento al tope: "+pila.mira());
        System.out.println("Pila actual:\n"+pila.toString());
        System.out.println("Elementos eliminados uno por uno:");
        while(!pila.isEmpty()){
            System.out.print(pila.expulsa() + " ");
        }
    }
}
