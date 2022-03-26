package ed.ordenamientos;

public class SelectionSorter<C extends Comparable<C>> implements IOrdenador<C> {

    @Override
    public C[] ordena(C[] a) {
        int indMin = 0; //Índice del elemento más pequeño.
        for(int i=0;i<a.length-1;i++){
            indMin = i;
            for(int j=i+1;j<a.length;j++){
                if(a[j].compareTo(a[indMin]) < 0){
                    indMin = j;
                }
            }
            swap(a,i,indMin);
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
