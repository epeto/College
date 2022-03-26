
import java.io.*;
import java.util.LinkedList;
import java.util.Random;

public class Busquedas{

    /**
     * Algoritmo de búsqueda secuencial
     * @param elem : elemento a buscar
     * @param arreglo : secuencia donde se va a buscar a elem
     * @return i : índice donde se encuentra el elemento, o -1 si no lo encuentra
     */
    public int secuencial(int elem, int[] arreglo){
        for(int i=0;i<arreglo.length;i++){
            if(arreglo[i] == elem){
                return i;
            }
        }

        return -1;
    }

    /**
     * Algoritmo de búsqueda binaria acotado por los índices "izq" y "der"
     * @param izq :índice izquierdo
     * @param der :índice derecho
     * @param elem :elemento a buscar
     * @param arreglo :secuencia ordenada donde se va a buscar al elemento
     * @return índice donde se encuentra el elemento
     */
    public int binaria_aux(int izq, int der, int elem, int[] arreglo){
        if(izq == der){
            return izq;
        }

        int m = (izq+der)/2;

        if(arreglo[m] == elem){
            return m;
        }

        if(elem < arreglo[m]){
            return binaria_aux(izq, m-1, elem, arreglo);
        }else{
            return binaria_aux(m+1, der, elem, arreglo);
        }
    }

    //Búsqueda binaria sin cotas.
    public int binaria(int elem, int[] arreglo){
        return binaria_aux(0, arreglo.length-1, elem, arreglo);
    }

    /**
     * Función de búsqueda exponencial.
     * @param elem : Elemento a buscar
     * @param arreglo : Arreglo donde se va a buscar
     * @return índice del número buscado
     */
    public int exponencial(int elem, int[] arreglo){
        int ci = 0; //Acota inferiormente el rango de búsqueda.
        int cs = 1; //Acota superiormente el rango de búsqueda.
        boolean re = false; //Determina si un rango ya ha sido encontrado.
        if(elem == arreglo[0] || arreglo.length<=1)
            return 0;
    
        //Se encuentra el rango en el que se va a aplicar BB
        while(!re){
            if(elem <= arreglo[cs]){
                re = true;
            }else{
                ci = cs;
                cs = cs*2;
            }
        }

        //Para asegurarse de que la cota superior no se pasó del tamaño.
        if(cs >= arreglo.length){
            cs = arreglo.length-1;
        }

        return binaria_aux(ci,cs,elem,arreglo);
    }

    public int interpolacion(int elem, int[] arreglo){
        int izq = 0;
        int der = arreglo.length-1;
        int pos = 0;
        int funcion = 0;

        while((arreglo[izq] < elem) && (elem <= arreglo[der])){
            funcion = izq + (((elem-arreglo[izq])*(der-izq))/(arreglo[der]-arreglo[izq])); //Función de interpolación.
            if(funcion == pos){ //Si el valor no cambió en una iteración, se incrementa pos.
                pos++;
            }else{
                pos = funcion;
            }

            if(arreglo[pos] <= elem){
                izq = pos;
            }else{
                der = pos-1;
            }
        }

        if(arreglo[izq] == elem){
            return izq; //Se encontró al elemento.
        }
            return -1; //No se encontró :'v
    }

    //Función principal
    public static void main(String[] args){
        GenLec gl = new GenLec();
        Busquedas bu = new Busquedas();
        int[] arreglo = gl.lector(args[0]); //Se leen los números.
        int eab = Integer.parseInt(args[1]); //Elemento a buscar.
        int res = -1; //Resultado

        switch(args[2]){
            case "secuencial" : res = bu.secuencial(eab, arreglo);
            break;
            case "binaria" : res = bu.binaria(eab, arreglo);
            break;
            case "exponencial" : res = bu.exponencial(eab, arreglo);
            break;
            case "interpolacion" : res = bu.interpolacion(eab, arreglo);
            break;
            default: System.err.println("Error en el nombre del algoritmo");
        }

        if(res == -1){
            System.out.println("Elemento no encontrado");
        }else{
            System.out.println("Índice de "+eab+": "+res);
        }
    }
}



