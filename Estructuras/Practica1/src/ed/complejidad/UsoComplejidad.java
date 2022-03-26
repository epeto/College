package ed.complejidad;

import java.util.Scanner;
import java.io.FileNotFoundException;

public class UsoComplejidad {

    public static void escribeFR(Complejidad comp, int n, String archivo) throws FileNotFoundException{
        for(int i=0; i<=n; i++){
            comp.fibonacciRec(i);
            IComplejidad.escribeOperaciones(archivo, i, comp.leeContador());
        }
    }

    public static void escribeFI(Complejidad comp, int n, String archivo) throws FileNotFoundException{
        for(int i=0; i<=n; i++){
            comp.fibonacciIt(i);
            IComplejidad.escribeOperaciones(archivo, i, comp.leeContador());
        }
    }

    public static void escribePR(Complejidad comp, int n, String archivo) throws FileNotFoundException{
        for(int i=0; i<=n; i++){
            for(int j=0; j<=i; j++){
                comp.tPascalRec(i, j);
                IComplejidad.escribeOperaciones(archivo, i, j, comp.leeContador());
            }
            IComplejidad.escribeLineaVacia(archivo);
        }
    }

    public static void escribePI(Complejidad comp, int n, String archivo) throws FileNotFoundException{
        for(int i=0; i<=n; i++){
            for(int j=0; j<=i; j++){
                comp.tPascalRec(i, j);
                IComplejidad.escribeOperaciones(archivo, i, j, comp.leeContador());
            }
            IComplejidad.escribeLineaVacia(archivo);
        }
    }

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Elija un método a ejecutar:\n1.-Fibonacci recursivo"
        +"\n2.-Fibonacci iterativo"
        +"\n3.-Pascal recursivo"
        +"\n4.-Pascal iterativo");
        String caso = sc.nextLine();
        System.out.println("Elija el valor de n.");
        String nStr = sc.nextLine();
        int n = Integer.parseInt(nStr);
        System.out.println("Elija el nombre del archivo de salida.");
        String nomAr = sc.nextLine();
        Complejidad comp = new Complejidad();
        sc.close();
        try{
            switch(caso){
                case "1" : escribeFR(comp, n, nomAr);
                break;
                case "2" : escribeFI(comp, n, nomAr);
                break;
                case "3" : escribePR(comp, n, nomAr);
                break;
                case "4" : escribePI(comp, n, nomAr);
                break;
                default: System.out.println("Caso no válido.");
            }
        }catch(Exception e){System.err.println("Ocurrió un error al escribir.");}
    }
}
