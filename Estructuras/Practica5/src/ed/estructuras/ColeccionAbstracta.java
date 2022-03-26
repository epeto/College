package ed.estructuras;

import java.util.Collection;
import java.util.Iterator;
import java.util.Arrays;

public abstract class ColeccionAbstracta<E> implements Collection<E>{
    protected int tam = 0; //Número de elementos en la colección.

    /**
     * Verifica si la colección está vacía.
     * @return true si es vacía, false en otro caso.
     */
    public boolean isEmpty(){
        return tam == 0;
    }
    
    /**
     * Verifica si la colección contiene al elemento o.
     * @param o elemento a comprobar si está en la colección.
     * @return true si y sólo si o está en la colección.
     */
    public boolean contains(Object o){
        if(isEmpty()){
            return false;
        }
        Iterator<E> iterador = iterator();
        while(iterador.hasNext()){
            E e = iterador.next();
            if(o == null){
                if(e == null){
                    return true;
                }
            }else{
                if(e != null && o.equals(e)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Devuelve un arreglo que contiene a todos los elementos en esta colección.
     * @return un arreglo que contiene a todos los elementos en esta colección.
     */
    public Object[] toArray(){
        Object[] arreglo = new Object[tam];
        Iterator<E> iterador = iterator();
        int i=0;
        while(iterador.hasNext()){
            arreglo[i] = iterador.next();
            i++;
        }
        return arreglo;
    }

    /**
     * Devuelve un arreglo que contiene a todos los elementos en esta colección; el tipo en
     * tiempo de ejecución del arreglo es el que está en el arreglo especificado.
     * Si la colección cabe en el arreglo especificado, se devuelve ahí.
     * En otro caso, se crea un nuevo arreglo con el tipo E y el tamaño de esta colección.
     * @param a El arreglo en el cual los elemento de esta colección serán almacenados, si
     * es lo suficientemente grande; en otro caso, se devuelve un nuevo arreglo de tipo E.
     * @return un arreglo que contiene a todos los elementos en esta colección.
     */
    public <T> T[] toArray(T[] a){
        if(a == null){
            throw new NullPointerException("El arreglo del parámetro es nulo");
        }

        if(a.length >= tam){
            Iterator<E> it = iterator();
            int i=0;
            while(it.hasNext()){
                a[i] = (T) it.next();
                i++;
            }
            while(i < a.length){
                a[i] = null;
                i++;
            }
            return a;
        }else{
            T[] niuArray = Arrays.copyOf(a, tam);
            Iterator<E> it = iterator();
            int i=0;
            while(it.hasNext()){
                niuArray[i] = (T) it.next();
                i++;
            }
            return niuArray;
        }
    }

    /**
     * Verifica si esta colección contiene todos los elementos de c.
     * @param c - colección a ser verificada por contenimiento en esta colección.
     * @return true si esta colección contiene a todos los elementos en c.
     */
    public boolean containsAll(Collection<?> c){
        if(c == null) throw new NullPointerException("La colección es nula.");
        Iterator<?> it = c.iterator();
        while(it.hasNext()){
            if(!contains(it.next())){
                return false;
            }
        }
        return true;
    }

    /**
     * Agrega todos los elementos de c en esta colección.
     * @param c - colección que contiene a los elementos a ser agregados en esta colección.
     * @return true si esta colección cambió como resultado de la llamada.
     */
    public boolean addAll(Collection<? extends E> c){
        if(c == null){
            throw new NullPointerException("El parámetro es nulo.");
        }
        
        if(this == c){
            throw new IllegalArgumentException();
        }

        boolean cambio = false;
        Iterator<? extends E> it = c.iterator();
        while(it.hasNext()){
            if(add(it.next())){
                cambio = true;
            }
        }
        return cambio;
    }

    /**
     * Elimina la primera aparición del elemento especificado en esta colección, si está presente.
     * @param o - elemento a ser eliminado de esta colección, si está presente.
     * @return true si un elemento fue eliminado como resultado de la llamada.
     */
    public boolean remove(Object o){
        if(contains(o)){
            Iterator<E> it = iterator();
            if(o == null){
                boolean eliminado = false;
                while(!eliminado && it.hasNext()){
                    if(it.next() == null){
                        it.remove();
                        eliminado = true;
                    }
                }
            }else{
                boolean eliminado = false;
                while(!eliminado && it.hasNext()){
                    E sig = it.next();
                    if(sig != null && o.equals(sig)){
                        it.remove();
                        eliminado = true;
                    }
                }
            }
            return true;
        }else{
            return false; // si no está, se devuelve false.
        }
    }

    /**
     * Elimina a todos los elementos de esta colección que también están contenidos en c.
     * Después de realizar la operación, esta colección no contendrá elementos en común con c.
     * @param c - colección que contiene a los elementos a ser eliminados de esta colección.
     * @return true si esta colección cambió como resultado de la llamada.
     */
    public boolean removeAll(Collection<?> c){
        if(c == null){
            throw new NullPointerException("El parámetro es nulo.");
        }
        boolean cambio = false;
        if(equals(c)){
            if(!isEmpty()){
                clear();
                cambio = true;
            }
        }else{
            Iterator<?> it = c.iterator();
            while(it.hasNext()){
                if(remove(it.next())){
                    cambio = true;
                }
            }
        }
        return cambio;
    }

    public boolean retainAll(Collection<?> c){
        if(c == null){
            throw new NullPointerException("El parámetro es nulo.");
        }
        if(equals(c)){
            return false;
        }
        Iterator<E> it = iterator();
        boolean cambio = false;
        while(it.hasNext()){
            if(!c.contains(it.next())){
                it.remove();
                cambio = true;
            }
        }
        return cambio;
    }

    public void clear(){
        Iterator<E> it = iterator();
        while(it.hasNext()){
            it.next();
            it.remove();
        }
        tam = 0;
    }
    
    public int size(){
        return tam;
    }

    @Override
    public String toString(){
        String retVal = "[";
        Iterator<E> it = iterator();
        while(it.hasNext()){
            E elem = it.next();
            if(elem != null){
                retVal = retVal + elem.toString()+", ";
            }else{
                retVal += "null, ";
            }
        }
        retVal = retVal.substring(0, retVal.length()-2);
        retVal += "]";
        return retVal;
    }

    @Override
    public int hashCode(){
        int suma = 0;
        Iterator<E> it = iterator();
        while(it.hasNext()){
            suma += it.next().hashCode();
        }
        return suma;
    }
}
