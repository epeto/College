/*
 * Código utilizado para el curso de Estructuras de Datos. Se permite
 * consultarlo para fines didácticos en forma personal, pero no está permitido
 * transferirlo tal cual a estudiantes actuales o potenciales pues se afectará
 * su realización de los ejercicios.
 */

package ed.estructuras;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementación burda e ineficiente de una estructura tipo conjunto,
 * utilizada sólo con fines de prueba para otra clase.
 *
 * @author blackzafiro
 * @author mindahrelfen
 */
public class Conjunto<E> extends ColeccionAbstracta<E> {

    /**
     * Arreglo donde se guardan los valores que este conjunto contiene.
     */
    private E[] buffer = (E[]) new Object[10];

    /**
     * Iterador que permite visitar todos los elementos del conjunto.
     */
    public class Iterador implements Iterator<E> {

        /**
         * Posicion del valor actual.
         */
        private int index = 0;

        /**
         * Bandera que dice si se puede borrar un elemento o no.
         */
        private boolean canRemove = false;

        @Override
        public boolean hasNext() {
            return index < size();
        }

        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException("No hay elemeto siguiente");
            final E temp = buffer[index++];
            canRemove = true;
            return temp;
        }

        @Override
        public void remove() {
            if (!canRemove) throw new IllegalStateException("next() no ha sido llamado y/o el conjunto está vacío.");
            // borra al que estaba en el valor anterior de index
            int shift = index - 1;
            while (shift < size() - 1) {
                // Pasa el elemento del final a esta posición
                buffer[shift] = buffer[shift + 1];
                shift++;
            }
            // Remueve el elemento del final
            buffer[--tam] = null;
            index--;
            canRemove = false;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterador();
    }

    /**
     * Agrega el elemento <code>e</code> únicamente si no se encuentra uno
     * igual, de acuerdo a la definición de método <code>equals</code> de
     * <code>E</code>.
     * 
     * @param e Elemento que se quiere agregar. Si permite valores nulos.
     * @return Si el elemento fue agregado devuelve <code>true</code>, si ya
     * estaba devuelve <code>false</code>.
     */
    @Override
    public boolean add(E e) {
        for (int i = 0; i < size(); i++) {
            if (buffer[i] == null) {
                if (e == null) return false;
            } else {
                if (buffer[i].equals(e)) return false; // No agrega
            }
        }
        if (buffer.length == size()) {
            buffer = Arrays.copyOf(buffer, buffer.length * 2);
        }
        buffer[tam++] = e;
        return true;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o == null){
            return false;
        }
        if(o instanceof Conjunto){
            Conjunto<E> c = (Conjunto<E>) o;
            if(this.containsAll(c) && c.containsAll(this)){
                return true;
            }
        }
        return false;
    }
}
