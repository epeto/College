package ed.ordenamientos;

public class QuickSorter<C extends Comparable<C>> implements IOrdenador<C> {
    /**
     * Función que parte un arreglo
     * X[i]<=X[middle]<X[j]
     * left<=i<=middle<j<=right
     * @param x: Arreglo a partir.
     * @param left: índice izquierdo.
     * @param right: índice derecho.
     * @return middle: índice del pivote al final.
     */
    private int partition(C[] x, int left, int right){
        int l = left;
        int r = right;
        int middle = 0;
        C pivot = x[left];

        while(l<r){
            while(l <= right && x[l].compareTo(pivot) <= 0){ l++; }
            while(r >= left && x[r].compareTo(pivot) > 0){ r--; }

            if(l<r){
                swap(x,l,r);
            }
        }

        middle = r;
        swap(x, left, middle);

        return middle;
    }

    private void qs_aux(C[] x, int left, int right){
        if(left < right){
            int mid = partition(x, left, right);
            qs_aux(x, left, mid-1);
            qs_aux(x, mid+1, right);
        }
    }

    @Override
    public C[] ordena(C[] a) {
        qs_aux(a, 0, a.length-1);
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
