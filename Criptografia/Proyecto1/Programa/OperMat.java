
/**
 * @author Emmanuel Peto Gutiérrez
 * En esta clase se realizan operaciones con matrices cuadradas.
 * También se realizan funciones de teoría de números.
*/

public class OperMat{

    /**
     * Calcula i módulo n. Siempre devuelve un número positivo.
     * @param i número al cual se le va a calcular el residuo.
     * @param n módulo n.
     * @return i mod n positivo.
     */
    public static int modPositivo(int i, int n){
        int res = i%n;
        if(res < 0)
            res+=n;

        return res;
    }

    /**
     * Se calcula la transpuesta de una matriz.
     * @param matriz a la cual se le calcula la transpuesta
     * @return La transpuesta de 'matriz'
     */
    public static int[][] transpuesta(int[][] matriz){
        int[][] matrizT = new int[matriz.length][matriz.length];
        for(int i=0;i<matriz.length;i++){
            for(int j=0;j<matriz.length;j++){
                matrizT[j][i] = matriz[i][j];
            }
        }
        return matrizT;
    }

    /**
     * Devuelve una submatriz, eliminando la fila i y la columna j.
     * @param matriz original
     * @param i fila que se va a eliminar de la matriz original.
     * @param j columna que se va a eliminar de la matriz original.
     * @return
     */
    public static int[][] submatriz(int[][] matriz, int i, int j){
        int[][] sub = new int[matriz.length-1][matriz.length-1];
        int di = 0;
        for(int a=0;a<matriz.length-1;a++){
            if(a == i){di = 1;}
            int dj = 0;
            for(int b=0;b<matriz.length-1;b++){
                if(b == j){dj = 1;}

                sub[a][b] = matriz[a+di][b+dj];
            }
        }
        return sub;
    }

    /**
     * Calcula el cofactor del elemento matriz[i][j].
     * @param matriz a la cual se le calcula el cofactor
     * @param i Fila en la cual se encuentra el elemento Aij.
     * @param j Columna en la cual se encuentra el elemento Aij.
     * @return Entero que representa al cofactor de Aij.
     */
    public static int cofactor(int[][] matriz, int i, int j){
        return (1 - 2*((i+j)%2))*det(submatriz(matriz, i, j));
    }

    /**
     * Calcula la determinante de una matriz cuadrada.
     * @param matriz a la cual se le calcula el determinante.
     * @return determinante de matriz.
     */
    public static int det(int[][] matriz){
        if(matriz.length == 1){
            return matriz[0][0];
        }
        
        if(matriz.length == 2){
            return matriz[0][0]*matriz[1][1] - matriz[1][0]*matriz[0][1];
        }

        int suma = 0;
        //Se realiza la expansión sobre la fila 0.
        for(int i=0;i<matriz.length;i++){
            suma += matriz[0][i]*cofactor(matriz,0,i);
        }

        return suma;
    }

    /**
     * Calcula la cantidad de dígitos que hay en un entero.
     * @param n número entero.
     * @return dígitos de n.
     */
    public static int digitos(int n){
        int d = 0;
        if(n == 0){
            return 1;
        }
        //Se contará al signo '-' como un dígito.
        if(n < 0){
            d++;
            n = Math.abs(n);
        }

        while(n > 0){
            d++;
            n /= 10;
        }
        return d;
    }

    /**
     * Imprime una matriz cuadrada.
     * @param matriz a imprimir.
     */
    public static void imprimeMatriz(int[][] matriz){
        //El primer paso es calcular el número máximo de dígitos que hay en cualquier número de la matriz.
        int maxDigit = 0;
        for(int i=0;i<matriz.length;i++){
            for(int j=0;j<matriz.length;j++){
                int digAct = digitos(matriz[i][j]);
                if(digAct > maxDigit){
                    maxDigit = digAct;
                }
            }
        }

        //En esta parte ya imprime la matriz.
        for(int i=0;i<matriz.length;i++){
            System.out.print("|");
            for(int j=0;j<matriz.length;j++){
                //Decide cuántos espacios le va a agregar al principio.
                int numEsp = maxDigit - digitos(matriz[i][j]);
                String espacios = "";
                for(int k=0;k<numEsp;k++){
                    espacios += ' ';
                }
                System.out.print(espacios+matriz[i][j]);
                if(j<matriz.length-1){
                    System.out.print(' ');
                }
            }
            System.out.println("|");
        }
    }

    /**
     * Calcula el módulo n de todas las entradas de un vector.
     * @param vector al cual se le va a calcular el módulo.
     * @param n módulo n.
     */
    public static void vectorModulo(int[] vector, int n){
        for(int i=0;i<vector.length;i++){
            vector[i] = modPositivo(vector[i], n);
        }
    }

    /**
     * Calcula el módulo n de cada elemento de la matriz.
     * @param matriz A la cual se le calcula el módulo.
     * @param n módulo n.
     */
    public static void matrizModulo(int[][] matriz, int n){
        for(int i=0;i<matriz.length;i++){
            for(int j=0;j<matriz.length;j++){
                matriz[i][j] = modPositivo(matriz[i][j],n);
            }
        }
    }

    /**
     * Calcula el máximo común divisor de m y n.
     * @param m
     * @param n
     * @return mcd(m,n)
     */
    public static int mcd(int m, int n){
        m = Math.abs(m);
        n = Math.abs(n);
        int a,b;
        if(n<m){
            a = m;
            b = n;
        }else{
            a = n;
            b = m;
        }
    
        int r=1;
    
        while(r > 0){
            r = a % b;
            a = b;
            b = r;
        }
        return a;
    }

    /**
     * Calcula el inverso multiplicativo de i módulo n (si es que existe). Si no existe devuelve 0.
     * @param i número al cual se le calcula el inverso multiplicativo.
     * @param n módulo n.
     * @return i^{-1} mod n
     */
    public static int inversoMult(int i, int n){
        i = Math.abs(i);
        int a,b, x2, y2, q;
        int x0 = 1;
        int x1 = 0;
        int y0 = 0;
        int y1 = 1;

        i = i%n; //Por si i es más grande que n.
        a = n;
        b = i;

        int r=1;
    
        while(r > 0){
            r = a % b;
            q = a / b;
            x2 = x0 - x1*q;
            y2 = y0 - y1*q;
    
            //Actualización de variables para la siguiente iteración.
            a = b;
            b = r;
            x0 = x1;
            x1 = x2;
            y0 = y1;
            y1 = y2;
        }
    
        if(a == 1){
            //Si el mcd es 1 significa que son primos relativos y, por lo tanto, i tiene
            //inverso multiplicativo módulo n (que es y0).
            if(y0 < 0){
                y0 = y0 + n;
            }
            return y0;
        }else{
            return 0;
        }
    }

    /**
     * Imprime un vector de manera horizontal.
     * @param vector
     */
    public static void imprimeVectorHorizontal(int[] vector){
        System.out.print("[");
        for(int i=0;i<vector.length-1;i++){
            System.out.print(vector[i]+", ");
        }
        System.out.println(vector[vector.length-1]+"]");
    }

    /**
     * Imprime un vector de manera vertical.
     * @param vector vector a imprimir.
     */
    public static void imprimeVectorVertical(int[] vector){
        //Se calcula el número máximo de dígitos que existen en el vector.
        int maxDigit = 0;
        for(int i=0;i<vector.length;i++){
            int digAct = digitos(vector[i]);
            if(digAct > maxDigit){
                maxDigit = digAct;
            }
        }

        //Se imprime el vector.
        for(int i=0;i<vector.length;i++){
            int numEsp = maxDigit - digitos(vector[i]); //Decide cuántos espacios le va a agregar al principio.
            String espacios = "";
            for(int k=0;k<numEsp;k++){
                espacios += ' ';
            }
            System.out.println("|"+espacios+vector[i]+"|");
        }
    }

    /**
     * Multiplica una matriz por un escalar.
     * @param matriz a multiplicar por el escalar.
     * @param e escalar.
     */
    public static void multEscalarMat(int[][] matriz, int e){
        for(int i=0;i<matriz.length;i++){
            for(int j=0;j<matriz.length;j++){
                matriz[i][j] = e*matriz[i][j];
            }
        }
    }

    /**
     * Multiplica un vector por un escalar.
     * @param vector a multiplicar por el escalar.
     * @param e escalar.
     */
    public static void multEscalarVec(int[] vector, int e){
        for(int i=0;i<vector.length;i++){
            vector[i] = e*vector[i];
        }
    }

    /**
     * Multiplica por la izquierda una matriz A por un vector V (A*V).
     * @param matriz usada para multiplicar.
     * @param vector a multiplicar por la matriz.
     * @return vector resultante.
     */
    public static int[] matrizXvector(int[][] matriz, int[] vector){
        int[] res = new int[vector.length];
        for(int i=0;i<matriz.length;i++){
            res[i] = 0;
            for(int j=0;j<vector.length;j++){
                res[i] += matriz[i][j]*vector[j];
            }
        }
        return res;
    }

    /**
     * Calcula la adjunta clásica de una matriz.
     * @param matriz a la cual se le va a calcular la adjunta.
     * @return matriz adjunta.
     */
    public static int[][] adjuntaClasica(int[][] matriz){
        int[][] mat2 = new int[matriz.length][matriz.length];
        for(int i=0;i<matriz.length;i++){
            for(int j=0;j<matriz.length;j++){
                mat2[j][i] = cofactor(matriz,i,j);
            }
        }
        return mat2;
    }

    /**
     * Calcula la matriz inversa. La inversa es módulo n.
     * @param matriz a la cual se le calcula la inversa.
     * @param n módulo n.
     * @return matriz inversa módulo n.
     */
    public static int[][] matrizInversa(int[][] matriz, int n){
        int d = det(matriz); //Calcula el determinante de la matriz.
        d = modPositivo(d, n); //Se queda con el residuo.
        if(mcd(d,n) != 1){ //Si no son primos relativos no existe la inversa.
            return null;
        }

        int inverso = inversoMult(d,n); //Calcula el inverso multiplicativo del determinante.
        int[][] adjunta = adjuntaClasica(matriz); //Calculo la matriz adjunta.
        multEscalarMat(adjunta, inverso); //La multiplico por el inverso multiplicativo.
        matrizModulo(adjunta,n); //Calculo el módulo n de todos los elementos de la matriz.

        return adjunta;
    }

    public static void main(String[] args){
        int[][] llave1 = {{9,4},
                         {5,7}};

        int[][] llave2 = {{7,25},
                          {9,4}};

        int[][] llave3 = {{6,14,18},
                          {21,3,19},
                          {4,0,23}};

        int[][] llave4 = {{9,22,20,8},
                          {13,15,2,10},
                          {1,7,9,24},
                          {0,8,6,5}};

        System.out.println(modPositivo(det(llave1), 27));
        System.out.println(modPositivo(det(llave2), 27));
        System.out.println(modPositivo(det(llave3), 27));
        System.out.println(modPositivo(det(llave4), 27));
    }
}