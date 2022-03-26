package ed.ordenamientos;

public class InsertionSorter<C extends Comparable<C>> implements IOrdenador<C> {

    @Override
    public C[] ordena(C[] a) {
        C temp;
        int j;

        for(int i=1;i<a.length;i++){
            j = i-1;
            temp = a[i];
            while(j>=0 && a[j].compareTo(temp) > 0){
                a[j+1] = a[j];
                j--;
            }
            a[j+1] = temp;
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
