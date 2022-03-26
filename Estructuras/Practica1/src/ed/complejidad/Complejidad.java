
package ed.complejidad;

public class Complejidad implements IComplejidad{

    private long nop; //número de operaciones

    public long leeContador(){
        return nop;
    }

    public int tPascalRec(int ren, int col){
        if(ren < 0 || col < 0 || col>ren){
            throw new IndexOutOfBoundsException("Índices fuera de rango.");
        }
        nop = 0;
        return pascalAux(ren, col);
    }

    private int pascalAux(int ren, int col){
        nop++;
        if(col == 0 || ren == col){
            return 1;
        }else{
            return pascalAux(ren-1, col-1) + pascalAux(ren-1, col);
        }
    }

    public int tPascalIt(int ren, int col){
        if(ren < 0 || col < 0 || col>ren){
            throw new IndexOutOfBoundsException("Índices fuera de rango.");
        }
        int[] arr = new int[ren+1];
        for(int i=0; i<=ren; i++){
            arr[i] = 1;
        }
        for(int i=1; i<=col; i++){
            for(int j=1; j <= (ren-i); j++){
                arr[j] = arr[j] + arr[j-1];
            }
        }

        return arr[ren-col];
    }

    public int fibonacciRec(int n){
        if(n < 0){
            throw new IndexOutOfBoundsException("El índice no puede ser negativo.");
        }
        nop = 0;
        return fibonacciAux(n);
    }

    private int fibonacciAux(int n){
        nop++;
        if(n == 0 || n == 1){
            return n;
        }else{
            return fibonacciAux(n-1) + fibonacciAux(n-2);
        }
    }

    public int fibonacciIt(int n){
        if(n < 0){
            throw new IndexOutOfBoundsException("El índice no puede ser negativo.");
        }
        int a, b, c;
        a = 0;
        b = 0;
        c = 1;
        nop = 1;
        for(int i=0; i<n; i++){
            a = b;
            b = c;
            c = a + b;
            nop++;
        }
        return b;
    }
}
