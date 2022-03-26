
package ed.estructuras.lineales;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ColaArreglo<E> extends ColeccionAbstracta<E> implements ICola<E> {

    private E[] buffer; //Aquí se almacenarán los datos de la cola.
    private int primero; //índice en el arreglo del primer elemento.
    private int ultimo; //índice en el arreglo donde se insertará el último elemento.
    private static final int DEFAULT_INITIAL_SIZE = 4; //tamaño inicial por defecto.
    
    /**
     * Constructor de la clase ColaArreglo.
     * @param a arreglo de tipo E que debe ser de tamaño 0.
     * @param tamInicial 
     */
    public ColaArreglo(E[] a, int tamInicial){
        if(a.length > 0){
            throw new IllegalArgumentException("El arreglo debe ser de tamaño 0");
        }
        buffer = Arrays.copyOf(a, tamInicial);
        tam = 0;
        primero = 0;
        ultimo = 0;
    }

    /**
     * Constructor de la clase ColaArreglo que inicia al buffer con un tamaño por defecto.
     * @param a arreglo de tipo E que debe ser de tamaño 0.
     */
    public ColaArreglo(E[] a){
        this(a, DEFAULT_INITIAL_SIZE);
    }

    /**
     * Crea un arreglo del doble de tamaño del actual y copia todos los elementos
     * de un arreglo al otro.
     */
    private void expande(){
        E[] temp = Arrays.copyOf(buffer, buffer.length*2);
        for(int i=0; i<temp.length; i++){
            temp[i] = null;
        }
        for(int i=primero, i2=0; i2 < tam; i = (i+1)%buffer.length, i2++){
            temp[i2] = buffer[i];
        }
        buffer = temp;
        primero = 0;
        ultimo = tam;
    }

    /**
     * Crea un arreglo de la mitad del tamaño del actual y copia todos
     * los elementos de uno al otro.
     */
    private void contrae(){
        E[] temp = Arrays.copyOf(buffer, buffer.length/2);
        for(int i=0; i<temp.length; i++){
            temp[i] = null;
        }
        for(int i=primero, i2=0; i2 < tam; i = (i+1)%buffer.length, i2++){
            temp[i2] = buffer[i];
        }
        buffer = temp;
        primero = 0;
        ultimo = tam;
    }

    public E mira(){
        if(isEmpty()){
            return null;
        }
        E retVal = buffer[primero];
        return retVal;
    }

    public E atiende(){
        if(isEmpty()){
            return null;
        }
        E retVal = buffer[primero];
        buffer[primero] = null;
        primero = (primero+1) % buffer.length;
        tam--;
        //Posible contracción.
        double tamDoub = (double)tam;
        double lengthDoub = (double)buffer.length;
        double alfa = tamDoub/lengthDoub;
        if((buffer.length > DEFAULT_INITIAL_SIZE) && (alfa <= 0.25)){
            contrae();
        }
        return retVal;
    }

    public void forma(E e){
        if(e == null){
            throw new NullPointerException("El elemento a formar no debe ser null.");
        }
        buffer[ultimo] = e;
        ultimo = (ultimo+1) % buffer.length;
        tam++;
        if(tam == buffer.length){
            expande();
        }
    }

    @Override
    public boolean add(E elem){
        if(elem == null){
            return false;
        }
        forma(elem);
        return true;
    }

    @Override
    public boolean remove(Object o){
        throw new UnsupportedOperationException("La pila no permite remove.");
    }

    @Override
    public boolean removeAll(Collection<?> c){
        throw new UnsupportedOperationException("La pila no permite removeAll.");
    }

    @Override
    public boolean retainAll(Collection<?> c){
        throw new UnsupportedOperationException("La pila no permite retainAll.");
    }

    @Override
    public void clear(){
        while(!isEmpty()){
            atiende();
        }
    }

    @Override
    public boolean equals(Object o){
        if(o == null) return false;
        if(this == o) return true;
        if(this.getClass() != o.getClass()) return false;
        ColaArreglo<E> obj = (ColaArreglo<E>) o;
        Iterator<E> it1 = iterator();
        Iterator<E> it2 = obj.iterator();

        while(it1.hasNext() && it2.hasNext()){
            if(!it1.next().equals(it2.next())){
                return false;
            }
        }
        if(it1.hasNext() || it2.hasNext()){
            return false;
        }
        return true;
    }

    public Iterator<E> iterator(){
        return new Iterador();
    }

    private class Iterador implements Iterator<E>{
        int indice; //Apuntador al índice siguiente.

        public Iterador(){
            indice = primero;
        }

        public boolean hasNext(){
            return (indice != ultimo);
        }

        public E next(){
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            E retVal = buffer[indice];
            indice = (indice+1) % buffer.length;
            return retVal;
        }

        public void remove(){
            throw new UnsupportedOperationException();
        }
    }
}
