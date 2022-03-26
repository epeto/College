
package ed.ordenamientos;

public class BubbleSorter<C extends Comparable<C>> implements IOrdenador<C>{

    @Override
    public C[] ordena(C[] a) {
        for(int i=a.length-1; i>=0; i--){
            for(int j=0; j<i; j++){
                if(a[j].compareTo(a[j+1]) > 0){
                    swap(a, j, j+1);
                }
            }
        }
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