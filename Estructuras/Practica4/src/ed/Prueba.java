
package ed;

import java.util.Scanner;
import ed.lineales.Arreglo;

public class Prueba{
    public static void main(String[] args){
        System.out.println("Escriba los tamaños de cada dimensión, separados por espacios.");
        Scanner sc = new Scanner(System.in);
        String[] dimensStr = sc.nextLine().split(" ");
        int[] dimens = new int[dimensStr.length];
        for(int i=0; i<dimens.length; i++){
            dimens[i] = Integer.parseInt(dimensStr[i]);
        }
        Arreglo ejemplar = new Arreglo(dimens);
        int count = 1;
        for(int i=0;i<4;i++){
            for (int j=0;j<5;j++){
                ejemplar.almacenarElemento(new int [] {i,j},count);
                count++;
            }
        }
        String decision = "0";
        while(!decision.equals("3")){
            System.out.println("Elija la operación a realizar con el arreglo:");
            System.out.println("1.-Almacenar un elemento.");
            System.out.println("2.-Recuperar un elemento.");
            System.out.println("3.-Salir del programa.");
            decision = sc.nextLine();
            switch(decision){
                case "1":
                System.out.println("Escriba los índices separados por espacio y el elemento al final.");
                String[] indElem = sc.nextLine().split(" ");
                int[] arrInd = new int[dimens.length];
                for(int i=0; i<arrInd.length; i++){
                    arrInd[i] = Integer.parseInt(indElem[i]);
                }
                int elem = Integer.parseInt(indElem[arrInd.length]);
                ejemplar.almacenarElemento(arrInd, elem);
                break;
                case "2":
                System.out.println("Escriba los índices separados por espacio.");
                String[] indElem2 = sc.nextLine().split(" ");
                int[] arrInd2 = new int[dimens.length];
                for(int i=0; i<arrInd2.length; i++){
                    arrInd2[i] = Integer.parseInt(indElem2[i]);
                }
                int elem2 = ejemplar.obtenerElemento(arrInd2);
                System.out.println("Elemento en esa posición: "+elem2);
                break;
                case "3": break;
                default:
                System.out.println("Caso no válido.");
            }
        }
        sc.close();
    }
}