
package ed.estructuras.lineales;

import java.util.Iterator;
import java.util.Collection;
import java.util.NoSuchElementException;

public class PilaLigada<E> extends ColeccionAbstracta<E> implements IPila<E> {
    Nodo<E> cabeza; // Nodo que guarda al elemento al tope de la pila.

    public PilaLigada(){
        tam = 0;
        cabeza = null;
    }

    public E mira(){
        if(isEmpty()) return null;

        return cabeza.getDato();
    }

    public E expulsa(){
        if(isEmpty()) return null;

        E retval = mira();
        cabeza = cabeza.getSiguiente();
        tam--;
        return retval;
    }

    public void empuja(E elem){
        if(elem == null){
            throw new NullPointerException("El elemento a empujar es nulo.");
        }
        
        if(isEmpty()){
            cabeza = new Nodo<>(elem);
        }else{
            Nodo<E> nuevo = new Nodo<>(elem, cabeza);
            cabeza = nuevo;
        }
        tam++;
    }

    public boolean add(E elem){
        if(elem == null){
            return false;
        }

        empuja(elem);
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
        cabeza = null;
        tam = 0;
    }

    public Iterator<E> iterator(){
        return new Iterador();
    }

    private class Iterador implements Iterator<E>{
        Nodo<E> sig;

        public Iterador(){
            sig = cabeza;
        }

        public boolean hasNext(){
            return (sig != null);
        }

        public E next(){
            if(sig == null){
                throw new NoSuchElementException();
            }

            E retval = sig.getDato();
            sig = sig.getSiguiente();
            return retval;
        }

        public void remove(){
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public boolean equals(Object o){
        if(o == null) return false;
        if(this == o) return true;
        if(this.getClass() != o.getClass()) return false;
        PilaLigada<E> obj = (PilaLigada<E>) o;
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
}
