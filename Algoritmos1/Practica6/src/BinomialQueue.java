// BinomialQueue class
//
// CONSTRUCTION: with no parameters or a single item
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// Comparable deleteMin( )--> Return and remove smallest item
// Comparable findMin( )  --> Return smallest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// vod merge( rhs )       --> Absord rhs into this heap
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

import java.util.HashMap;

/**
 * Implements a binomial queue.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public final class BinomialQueue<T extends Comparable<? super T>>
{
    private static final int DEFAULT_TREES = 1;
    private int currentSize;                // # items in priority queue
    private BinNode<T> [ ] theTrees;  // An array of tree roots
    private HashMap<T, BinNode<T>> tabla; //Tabla hash que guarda la posición de cada elemento de tipo T.

    /**
     * Construct the binomial queue.
     */
    public BinomialQueue( )
    {
        theTrees = new BinNode[ DEFAULT_TREES ];
        tabla = new HashMap<T, BinNode<T>>();
        makeEmpty( );
    }

    /**
     * Construct with a single item.
     */
    public BinomialQueue( T item ) 
    {
        currentSize = 1;
        theTrees = new BinNode[ 1 ];
        BinNode<T> nuevo = new BinNode<>(item);
        tabla = new HashMap<T, BinNode<T>>();
        tabla.put(item,nuevo);
        theTrees[ 0 ] = nuevo;
    }


    private void expandTheTrees( int newNumTrees )
    {
        BinNode<T> [ ] old = theTrees;
        int oldNumTrees = theTrees.length;

        theTrees = new BinNode[ newNumTrees ];
        for( int i = 0; i < Math.min( oldNumTrees, newNumTrees ); i++ )
            theTrees[ i ] = old[ i ];
        for( int i = oldNumTrees; i < newNumTrees; i++ )
            theTrees[ i ] = null;
    }
    
    /**
     * Merge rhs into the priority queue.
     * rhs becomes empty. rhs must be different from this.
     * @param rhs the other binomial queue.
     */
    public void merge( BinomialQueue<T> rhs )
    {
        if( this == rhs )    // Avoid aliasing problems
            return;

        currentSize += rhs.currentSize;
        tabla.putAll(rhs.tabla); // Copio los valores de la tabla de rhs a esta tabla.
        
        if( currentSize > capacity( ) )
        {
            int newNumTrees = Math.max( theTrees.length, rhs.theTrees.length ) + 1;
            expandTheTrees( newNumTrees );
        }

        BinNode<T> carry = null;
        for( int i = 0, j = 1; j <= currentSize; i++, j *= 2 )
        {
            BinNode<T> t1 = theTrees[ i ];
            BinNode<T> t2 = i < rhs.theTrees.length ? rhs.theTrees[ i ] : null;

            int whichCase = t1 == null ? 0 : 1;
            whichCase += t2 == null ? 0 : 2;
            whichCase += carry == null ? 0 : 4;

            switch( whichCase )
            {
              case 0: /* No trees */
              case 1: /* Only this */
                break;
              case 2: /* Only rhs */
                theTrees[ i ] = t2;
                rhs.theTrees[ i ] = null;
                break;
              case 4: /* Only carry */
                theTrees[ i ] = carry;
                carry = null;
                break;
              case 3: /* this and rhs */
                carry = combineTrees( t1, t2 );
                theTrees[ i ] = rhs.theTrees[ i ] = null;
                break;
              case 5: /* this and carry */
                carry = combineTrees( t1, carry );
                theTrees[ i ] = null;
                break;
              case 6: /* rhs and carry */
                carry = combineTrees( t2, carry );
                rhs.theTrees[ i ] = null;
                break;
              case 7: /* All three */
                theTrees[ i ] = carry;
                carry = combineTrees( t1, t2 );
                rhs.theTrees[ i ] = null;
                break;
            }
        }

        for( int k = 0; k < rhs.theTrees.length; k++ )
            rhs.theTrees[ k ] = null;
        rhs.currentSize = 0;
    }

    /**
     * Return the result of merging equal-sized t1 and t2.
     */
    private BinNode<T> combineTrees( BinNode<T> t1, BinNode<T> t2 )
    {
        if( t1.element.compareTo( t2.element ) > 0 )
            return combineTrees( t2, t1 );
        t2.parent = t1; // El padre de t2 es t1.
        t2.nextSibling = t1.leftChild; // El hermano de t2 es el primer hijo de t1.
        t1.leftChild = t2; // El primer hijo de t1 es t2.
        return t1;
    }

    /**
     * Insert into the priority queue, maintaining heap order.
     * @param x the item to insert.
     */
    public void insert( T x )
    {
        currentSize++;
        if(currentSize > capacity())
            expandTheTrees(theTrees.length+1);

        BinNode<T> t2 = new BinNode<>(x);
        tabla.put(x, t2);
        BinNode<T> carry = null;

        if(theTrees[0] == null){
            theTrees[0] = t2;
            return;
        }else{
            carry = combineTrees(theTrees[0], t2);
            theTrees[0] = null;
        }

        for( int i = 1; i<theTrees.length; i++)
        {
            BinNode<T> t1 = theTrees[ i ];

            if(t1 == null){
                theTrees[i] = carry;
                return;
            }else{
                carry = combineTrees(t1, carry);
                theTrees[i] = null;
            } 
        }
    }

    /**
     * Find the smallest item in the priority queue.
     * @return the smallest item, or throw UnderflowException if empty.
     */
    public T findMin( )
    {
        if( isEmpty( ) )
            throw new RuntimeException("Cola vacía");

        return theTrees[ findMinIndex( ) ].element;
    }


    /**
     * Find index of tree containing the smallest item in the priority queue.
     * The priority queue must not be empty.
     * @return the index of tree containing the smallest item.
     */
    private int findMinIndex( )
    {
        int i;
        int minIndex;

        for( i = 0; theTrees[ i ] == null; i++ ) // Se encuentra al primer elemento no nulo.
            ;

        for( minIndex = i; i < theTrees.length; i++ )
            if( theTrees[ i ] != null &&
                theTrees[ i ].element.compareTo( theTrees[ minIndex ].element ) < 0 )
                minIndex = i;

        return minIndex;
    }

    /**
     * Remove the smallest item from the priority queue.
     * @return the smallest item, or throw UnderflowException if empty.
     */
    public T deleteMin( )
    {
        if( isEmpty( ) )
            throw new RuntimeException("Cola vacía.");

        int minIndex = findMinIndex( );
        T minItem = theTrees[ minIndex ].element;
        tabla.remove(minItem);
        BinNode<T> deletedTree = theTrees[ minIndex ].leftChild;

        // Construct H''
        BinomialQueue<T> deletedQueue = new BinomialQueue<>( );
        deletedQueue.expandTheTrees( minIndex );
        
        deletedQueue.currentSize = ( 1 << minIndex ) - 1;
        for( int j = minIndex - 1; j >= 0; j-- )
        {
            deletedQueue.theTrees[ j ] = deletedTree;
            deletedTree = deletedTree.nextSibling;
            deletedQueue.theTrees[ j ].nextSibling = null;
            deletedQueue.theTrees[ j ].parent = null;
        }

        // Construct H'
        theTrees[ minIndex ] = null;
        currentSize -= deletedQueue.currentSize + 1;

        merge( deletedQueue );
        
        return minItem;
    }

    /**
     * Test if the priority queue is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return currentSize == 0;
    }

    /**
     * Make the priority queue logically empty.
     */
    public void makeEmpty( )
    {
        currentSize = 0;
        tabla.clear();
        for( int i = 0; i < theTrees.length; i++ )
            theTrees[ i ] = null;
    }

    /**
     * Realiza un filtrado hacia arriba a partir del nodo que recibe.
     * @param nodo nodo a partir del cual se realiza filtrado hacia arriba.
     */
    private void percolateUp(BinNode<T> nodo){
        if(nodo.parent != null &&
           nodo.element.compareTo(nodo.parent.element) < 0){
            T t = nodo.element;
            nodo.element = nodo.parent.element;
            nodo.parent.element = t;

            tabla.replace(nodo.element, nodo);
            tabla.replace(nodo.parent.element, nodo.parent);

            percolateUp(nodo.parent);
        }
    }

    /**
     * Se realiza un reacomodo de un elemento después de decrementar su llave.
     * @param elem elemento que decrementó su llave y se va a reacomodar.
     */
    public void decreaseKey(T elem){
        BinNode<T> nd = tabla.get(elem);
        if(nd != null)
            percolateUp(nd);
    }

    private static class BinNode<T>
    {
        T          element;     // The data in the node
        BinNode<T> parent;      // Padre del nodo.
        BinNode<T> leftChild;   // Primer hijo.
        BinNode<T> nextSibling; // Hermano derecho.

        // Constructors
        BinNode( T theElement )
        {
            this( theElement, null, null, null);
        }

        BinNode( T theElement, BinNode<T> p, BinNode<T> lt, BinNode<T> nt )
        {
            element     = theElement;
            parent      = p;
            leftChild   = lt;
            nextSibling = nt;
        }
    }

    /**
     * Return the capacity.
     */
    private int capacity( )
    {
        return ( 1 << theTrees.length ) - 1;
    }

    public void delete(T elem){
        decreaseKey(elem);
        deleteMin();
    }

    public static void main( String [ ] args )
    {
        int numItems = 10000;
        BinomialQueue<Integer> h  = new BinomialQueue<>( );
        BinomialQueue<Integer> h1 = new BinomialQueue<>( );
        int i = 37;

        System.out.println( "Starting check." );

        for( i = 37; i != 0; i = ( i + 37 ) % numItems )
            if( i % 2 == 0 )
                h1.insert( i );
            else
                h.insert( i );

        h.merge( h1 );
        for( i = 1; i < numItems; i++ )
            if( h.deleteMin( ) != i )
                System.out.println( "Oops! " + i );
 
        System.out.println( "Check done." );
    }
}
