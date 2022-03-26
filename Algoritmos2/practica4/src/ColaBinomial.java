
// Clase de cola binomial
// El rango de un árbol binomial está dado de manera implícita por su posición en el arreglo.

import java.util.LinkedList;

public final class ColaBinomial<T extends Comparable<? super T>>
{
    public static final int DEFAULT = 1;
    public int size; // Número de elementos en la cola binomial.
    public NodoBin<T> [ ] arboles;  // Un arreglo de árboles binomiales.

    /**
     * Construye una cola binomial.
     */
    public ColaBinomial( )
    {
        arboles = new NodoBin[ DEFAULT ];
        makeEmpty( );
    }


    // Incrementa el número de árboles binomiales que caben en la cola.
    public void expandeArboles( int nuevoNA )
    {
        NodoBin<T> [ ] viejo = arboles;
        int viejoNA = arboles.length;

        arboles = new NodoBin[ nuevoNA ];
        for( int i = 0; i < Math.min( viejoNA, nuevoNA ); i++ )
            arboles[ i ] = viejo[ i ];
        for( int i = viejoNA; i < nuevoNA; i++ )
            arboles[ i ] = null;
    }
    
    /**
     * Fusiona rhs en esta cola binomial.
     * rhs se vuelve vacío. rhs debe ser diferente a esta cola.
     * @param rhs la otra cola binomial.
     */
    public void fundir( ColaBinomial<T> rhs )
    {
        if( this == rhs )    // Si son iguales no se fusionan.
            return;

        size += rhs.size;
        
        if( size > capacidad( ) )
        {
            int nuevoNA = Math.max( arboles.length, rhs.arboles.length ) + 1;
            expandeArboles( nuevoNA );
        }

        NodoBin<T> acarreo = null;
        for( int i = 0, j = 1; j <= size; i++, j *= 2 )
        {
            NodoBin<T> t1 = arboles[ i ];
            NodoBin<T> t2 = i < rhs.arboles.length ? rhs.arboles[ i ] : null;

            int whichCase = t1 == null ? 0 : 1;
            whichCase += t2 == null ? 0 : 2;
            whichCase += acarreo == null ? 0 : 4;

            switch( whichCase )
            {
              case 0: /* Sin árboles */
              case 1: /* Solo este */
                break;
              case 2: /* Solo rhs */
                arboles[ i ] = t2;
                rhs.arboles[ i ] = null;
                break;
              case 4: /* Solo acarreo */
                arboles[ i ] = acarreo;
                acarreo = null;
                break;
              case 3: /* this y rhs */
                acarreo = combinaArboles( t1, t2 );
                arboles[ i ] = rhs.arboles[ i ] = null;
                break;
              case 5: /* this y acarreo */
                acarreo = combinaArboles( t1, acarreo );
                arboles[ i ] = null;
                break;
              case 6: /* rhs y acarreo */
                acarreo = combinaArboles( t2, acarreo );
                rhs.arboles[ i ] = null;
                break;
              case 7: /* Los tres árboles */
                arboles[ i ] = acarreo;
                acarreo = combinaArboles( t1, t2 );
                rhs.arboles[ i ] = null;
                break;
            }
        }

        for( int k = 0; k < rhs.arboles.length; k++ )
            rhs.arboles[ k ] = null;
        rhs.size = 0;
    }

    /**
     * Devuelve el resultado de mezclar dos árboles del mismo tamaño t1 y t2.
     */
    public NodoBin<T> combinaArboles( NodoBin<T> t1, NodoBin<T> t2 )
    {
        if( t1.llave.compareTo( t2.llave ) > 0 )
            return combinaArboles( t2, t1 );
        t2.padre = t1; // El padre de t2 es t1.
        t2.hermanoDer = t1.primerHijo; // El hermano de t2 es el primer hijo de t1.
        t1.primerHijo = t2; // El primer hijo de t1 es t2.
        t1.rango++; //Se incrementa el rango del nodo raíz.
        return t1;
    }

    /**
     * Inserta un elemento en la cola manteniendo la propiedad de ser Heap-ordenado.
     * @param x elemento a insertar.
     */
    public void inserta( T x )
    {
        size++;
        if(size > capacidad())
            expandeArboles(arboles.length+1);

        NodoBin<T> t2 = new NodoBin<>(x);
        NodoBin<T> acarreo = null;

        if(arboles[0] == null){
            arboles[0] = t2;
            return;
        }else{
            acarreo = combinaArboles(arboles[0], t2);
            arboles[0] = null;
        }

        for( int i = 1; i<arboles.length; i++)
        {
            NodoBin<T> t1 = arboles[ i ];

            if(t1 == null){
                arboles[i] = acarreo;
                return;
            }else{
                acarreo = combinaArboles(t1, acarreo);
                arboles[i] = null;
            } 
        }
    }

    /**
     * Comprueba si la cola es vacía.
     * @return verdadero si es vacía, falso en otro caso.
     */
    public boolean isEmpty( )
    {
        return size == 0;
    }

    /**
     * Vacía la cola binomial.
     */
    public void makeEmpty( )
    {
        size = 0;
        for( int i = 0; i < arboles.length; i++ )
            arboles[ i ] = null;
    }

    /**
     * Devuelve una lista de matrices de sus árboles binomiales.
     * @return lista de matrices de los árboles binomiales.
     */
    public LinkedList<T[][]> toMatrices(){
        LinkedList<T[][]> matrices = new LinkedList<>();
        for(int i=0; i<arboles.length; i++){
            if(arboles[i] != null){
                matrices.add(arboles[i].toMatriz(i));
            }
        }

        return matrices;
    }

    //Clase para nodo binomial (o árbol binomial).
    public static class NodoBin<T>
    {
        T          llave;      // El dato en el nodo.
        NodoBin<T> padre;      // Padre del nodo.
        NodoBin<T> primerHijo; // Primer hijo.
        NodoBin<T> hermanoDer; // Hermano derecho.
        int rango = 0;

        //Estos atributos sólo servirán para dibujar un nodo.
        int fila;
        int columna;
        int x;
        int y;

        // Constructores
        NodoBin( T laLlave )
        {
            this( laLlave, null, null, null, 0);
        }

        NodoBin( T laLlave, NodoBin<T> p, NodoBin<T> lt, NodoBin<T> nt, int r)
        {
            llave = laLlave;
            padre = p;
            primerHijo = lt;
            hermanoDer = nt;
            rango = r;
        }

        /**
         * Coloca al nodo ph en la posición que le corresponde en la matriz.
         * @param matriz
         * @param nodo primer hijo de algún nodo.
         * @param filaPadre fila del padre de nodo.
         * @param columnaPadre columna del padre de nodo.
         */
        public void colocaHijo(T[][] matriz, NodoBin<T> nodo, int filaPadre, 
        int columnaPadre, int rangoPadre){
            int miFila = filaPadre+1;
            int miColumna = 0;
            int desplazaIzq = 0; //Cuántas posiciones va a estar a la izquierda de su padre.
            if(rangoPadre >= 2){
                desplazaIzq = 1 << (rangoPadre-2);
            }

            miColumna = columnaPadre - desplazaIzq;

            matriz[miFila][miColumna] = nodo.llave;
            nodo.fila = miFila;
            nodo.columna = miColumna;

            if(nodo.hermanoDer != null){
                colocaHermano(matriz, nodo.hermanoDer, miFila, miColumna, columnaPadre);
            }

            if(nodo.primerHijo != null){
                colocaHijo(matriz, nodo.primerHijo, miFila, miColumna, nodo.rango);
            }
        }

        /**
         * Coloca al hermano de un nodo en la posición correcta en la matriz.
         */
        public void colocaHermano(T[][] matriz, NodoBin<T> nodo, int filaHI, int columnaHI,
         int columnaPadre){
            int miFila = filaHI;
            int miColumna = 0;
            if(nodo.hermanoDer == null){
                miColumna = columnaPadre;
            }else{
                miColumna = (columnaHI + columnaPadre)/2;
            }

            matriz[miFila][miColumna] = nodo.llave;
            nodo.fila = miFila;
            nodo.columna = miColumna;

            if(nodo.hermanoDer != null){
                colocaHermano(matriz, nodo.hermanoDer, miFila, miColumna, columnaPadre);
            }

            if(nodo.primerHijo != null){
                colocaHijo(matriz, nodo.primerHijo, miFila, miColumna, nodo.rango);
            }
        }

        /**
         * Transforma un árbol binomial a una matriz.
         * @param rango El rango del árbol binomial.
         * @return representación de matriz del árbol binomial.
         */
        public T[][] toMatriz(int rango){
            int nf = rango+1; //Número de filas.
            int nc = 0; //Número de columnas.
            if(rango == 0){
                nc = 1;
            }else{
                nc = 1 << (rango-1);
            }

            T[][] matriz = (T[][]) new Comparable[nf][nc];

            for(int i=0;i<matriz.length;i++){
                for(int j=0;j<matriz[0].length;j++){
                    matriz[i][j] = null;
                }
            }

            matriz[0][nc-1] = llave;
            fila = 0;
            columna = nc-1;

            if(primerHijo != null){
                colocaHijo(matriz, primerHijo, 0, nc-1, rango);
            }

            return matriz;
        }

        public void imprimeArbol(){
            System.out.print(llave.toString()+" ");
            if(hermanoDer != null){
                hermanoDer.imprimeArbol();
            }

            if(primerHijo != null){
                primerHijo.imprimeArbol();
            }
        }
    }

    public void imprimeCola(){
        LinkedList<T[][]> lista = toMatrices();
        for(T[][] mat : lista){
            for(int i=0;i<mat.length;i++){
                for(int j=0;j<mat[0].length;j++){
                    if(mat[i][j] != null){
                        System.out.print(mat[i][j].toString()+" ");
                    }else{
                        System.out.print("  ");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    public void imprimeCola2(){
        for(int i=0; i<arboles.length; i++){
            if(arboles[i] != null){
                arboles[i].imprimeArbol();
                System.out.println();
            }
        }
    }

    /**
     * Devuelve la capacidad de la cola binomial.
     * Es el máximo número de nodos que caben en esta cola.
     */
    public int capacidad( )
    {
        return ( 1 << arboles.length ) - 1;
    }

    public static void main(String[] args){
        ColaBinomial<Character> queue = new ColaBinomial<>();
        queue.inserta('0');
        queue.inserta('1');
        queue.inserta('2');
        queue.inserta('3');
        queue.inserta('4');
        queue.inserta('5');
        queue.inserta('6');
        queue.inserta('7');
        queue.inserta('8');
        queue.inserta('9');
        queue.inserta('A');
        queue.inserta('B');
        queue.inserta('C');
        queue.inserta('D');
        queue.inserta('E');
        queue.inserta('F');
        queue.imprimeCola();
    }
}


