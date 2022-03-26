
package ed.ordenamientos;

import java.util.Arrays;

public class MergeSorter<C extends Comparable<C>> implements IOrdenador<C> {

    /**
     * Algoritmo merge, usado en mergesort.
     * @param x arreglo que recibe
     * @param izq1 índice izquierdo del subarreglo izquierdo.
     * @param izq2 índice derecho del subarreglo izquierdo.
     * @param der1 índice izquierdo del subarreglo derecho.
     * @param der2 índice derecho del subarreglo derecho.
     */
    private void mezcla(C[] x, int izq1, int izq2, int der1, int der2){
        int i,j,k;
        C[] t = Arrays.copyOf(x, der2-izq1+1);

        i = izq1;
        j = der1;
        k = 0;

        //Mueve los elementos menores de x[izq1...izq2] y x[der1...der2] a t.
        while((i<=izq2) && (j<=der2)){
            if(x[i].compareTo(x[j]) <= 0){
                t[k] = x[i];
                i++;
                k++;
            }else{
                t[k] = x[j];
                j++;
                k++;
            }
        }

        //Mueve los elementos restantes de x[izq1...izq2] a t.
        while(i <= izq2){
            t[k] = x[i];
            i++;
            k++;
        }

        //Mueve los elementos restantes de x[der1...der2] a t.
        while(j <= der2){
            t[k] = x[j];
            j++;
            k++;
        }

        //Regresa los elementos ya mezclados al arreglo original.
        for(i=0;i<t.length;i++){
            x[i+izq1] = t[i];
        }
    }

    /**
     * Algoritmo de mergesort usando índices.
     * @param x arreglo que recibe
     * @param izq índice izquierdo del subarreglo a ordenar
     * @param der índice derecho del subarreglo a ordenar
     */
    private void divide_y_mezcla(C[] x, int izq, int der){
        int mitad;
        if(izq < der){
            mitad = (izq+der)/2;
            divide_y_mezcla(x,izq,mitad);
            divide_y_mezcla(x,mitad+1,der);
            mezcla(x,izq,mitad,mitad+1,der);
        }
    }

    @Override
    public C[] ordena(C[] a) {
        divide_y_mezcla(a,0,a.length-1);
        return a;
    }

    @Override
    public int[] peorCaso(int tam) {
        int[] arr = new int[tam];
        for(int i=0; i<tam; i++){
            arr[i] = tam-i;
        }
        return arr;
    }
    
}
