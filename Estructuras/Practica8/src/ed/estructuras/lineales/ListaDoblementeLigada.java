package ed.estructuras.lineales;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.List;
import java.util.NoSuchElementException;

public class ListaDoblementeLigada<E> extends ColeccionAbstracta<E> implements List<E>{

    Nodo<E> centinela; //Nodo centinela de la lista ligada circular.

    /**
     * Constructor que crea una lista ligada vacía.
     */
    public ListaDoblementeLigada(){
        centinela = new Nodo<>(null);
        centinela.setSiguiente(centinela);
        centinela.setAnterior(centinela);
        tam = 0;
    }

    /**
     * Constructor que crea una lista ligada a partir de una colección.
     * @param c colección.
     */
    public ListaDoblementeLigada(Collection<? extends E> c){
        this();
        addAll(c);
    }

    /**
     * Agrega al elemento especificado al final de la lista.
     * @param e elemento a agregar a la lista.
     * @return true si se agrega a e, false en otro caso.
     */
    @Override
    public boolean add(E e) {
        add(size(), e);
        return true;
    }

    @Override
    public void add(int index, E element) {
        if(index < 0 || index > size()){
            throw new IndexOutOfBoundsException();
        }
        ListIterator<E> lit = listIterator(index);
        lit.add(element);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if(index < 0 || index > size()){
            throw new IndexOutOfBoundsException();
        }
        if(c == this){
            throw new IllegalArgumentException();
        }
        ListIterator<E> lit = listIterator(index);
        Iterator<? extends E> itc = c.iterator();
        boolean cambios = false;
        while(itc.hasNext()){
            lit.add(itc.next());
            cambios = true;
        }
        return cambios;
    }

    @Override
    public void clear(){
        centinela.setSiguiente(centinela);
        centinela.setAnterior(centinela);
        tam = 0;
    }

    @Override
    public E get(int index) {
        if(index < 0 || index >= size()){
            throw new IndexOutOfBoundsException();
        }
        ListIterator<E> lit = listIterator(index);
        return lit.next();
    }

    @Override
    public boolean equals(Object o){
        if(o == null) return false;
        if(this == o) return true;
        if(this.getClass() != o.getClass()) return false;
        Collection<?> obj = (Collection<?>) o;
        Iterator<E> it1 = iterator();
        Iterator<?> it2 = obj.iterator();

        while(it1.hasNext() && it2.hasNext()){
            E elemit1 = it1.next();
            if(elemit1 == null){
                if(it2.next() != null){
                    return false;
                }
            } else if(!elemit1.equals(it2.next())){
                return false;
            }
        }
        if(it1.hasNext() || it2.hasNext()){
            return false;
        }
        return true;
    }

    @Override
    public int indexOf(Object o) {
        ListIterator<E> lit = listIterator();
        if(o == null){
            while(lit.hasNext()){
                if(lit.next() == null){
                    return lit.previousIndex();
                }
            }
        }else{
            while(lit.hasNext()){
                if(o.equals(lit.next())){
                    return lit.previousIndex();
                }
            }
        }
        return -1;
    }

    @Override
    public Iterator<E> iterator() {
        return (Iterator<E>) listIterator();
    }

    @Override
    public int lastIndexOf(Object o) {
        ListIterator<E> lit = listIterator(size());
        if(o == null){
            while(lit.hasPrevious()){
                if(lit.previous() == null){
                    return lit.nextIndex();
                }
            }
        }else{
            while(lit.hasPrevious()){
                if(o.equals(lit.previous())){
                    return lit.nextIndex();
                }
            }
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new ListaIterador();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        if(index < 0 || index > size()){
            throw new IndexOutOfBoundsException();
        }
        ListaIterador lit;
        // este if garantiza que se realicen n/2 iteraciones, en el peor caso.
        if(index < size()/2){
            lit = new ListaIterador();
            while(lit.nextIndex() < index){
                lit.next();
            }
        }else{
            lit = new ListaIterador(centinela.getAnterior(), centinela, size());
            while(lit.nextIndex() > index){
                lit.previous();
            }
        }
        return lit;
    }

    @Override
    public E remove(int index) {
        if(index < 0 || index >= size()){
            throw new IndexOutOfBoundsException();
        }
        ListIterator<E> lit = listIterator(index);
        E retVal = lit.next();
        lit.remove();
        return retVal;
    }

    @Override
    public E set(int index, E element) {
        if(index < 0 || index >= size()){
            throw new IndexOutOfBoundsException();
        }
        ListIterator<E> lit = listIterator(index);
        E retVal = lit.next();
        lit.set(element);
        return retVal;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        if(fromIndex < 0 || toIndex > size() || fromIndex > toIndex){
            throw new IndexOutOfBoundsException();
        }
        ListaDoblementeLigada<E> listaNueva = new ListaDoblementeLigada<>();
        ListIterator<E> lit = listIterator(fromIndex);
        while(lit.nextIndex() < toIndex){
            listaNueva.add(lit.next());
        }
        return listaNueva;
    }

    private class ListaIterador implements ListIterator<E>{

        Nodo<E> prev; // Nodo previo.
        Nodo<E> nex; // Nodo siguiente.
        public Nodo<E> devuelto; // apuntador al último nodo devuelto.
        int indiceSig; // índice del siguiente elemento.
        boolean nextCalled; // verifica si se movió a la derecha (si se llamó un next).

        public ListaIterador(){
            prev = centinela;
            nex = centinela.getSiguiente();
            devuelto = null;
            indiceSig = 0;
        }

        // Constructor que coloca al iterador en una posición específica.
        // No necesariamente al inicio de la lista.
        private ListaIterador(Nodo<E> previo, Nodo<E> siguiente, int indice){
            prev = previo;
            nex = siguiente;
            devuelto = null;
            indiceSig = indice;
        }

        @Override
        public void add(E e) {
            Nodo<E> nuevo = new Nodo<>(e, prev, nex);
            prev.setSiguiente(nuevo);
            nex.setAnterior(nuevo);
            nex = nuevo;
            tam++;
            next();
            devuelto = null;
        }

        @Override
        public boolean hasNext() {
            return indiceSig < tam;
        }

        @Override
        public boolean hasPrevious() {
            return indiceSig > 0;
        }

        @Override
        public E next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            devuelto = nex;
            E retVal = devuelto.getDato();
            nex = nex.getSiguiente();
            prev = devuelto;
            indiceSig++;
            nextCalled = true;
            return retVal;
        }

        @Override
        public int nextIndex() {
            return indiceSig;
        }

        @Override
        public E previous() {
            if(!hasPrevious()){
                throw new NoSuchElementException();
            }
            devuelto = prev;
            E retVal = devuelto.getDato();
            prev = prev.getAnterior();
            nex = devuelto;
            indiceSig--;
            nextCalled = false;
            return retVal;
        }

        @Override
        public int previousIndex() {
            return indiceSig-1;
        }

        @Override
        public void remove() {
            if(devuelto == null){
                throw new IllegalStateException();
            }
            Nodo<E> antDev = devuelto.getAnterior(); //anterior del devuelto
            Nodo<E> sigDev = devuelto.getSiguiente(); //siguiente del devuelto
            antDev.setSiguiente(sigDev);
            sigDev.setAnterior(antDev);
            devuelto.setSiguiente(null);
            devuelto.setAnterior(null);
            devuelto = null;
            if(nextCalled){
                indiceSig--;
                prev = antDev;
            }else{
                nex = sigDev;
            }
            tam--;
        }

        @Override
        public void set(E e) {
            if(devuelto == null){
                throw new IllegalStateException();
            }
            devuelto.setDato(e);
        }
    }
}
