/*
 * Código utilizado para el curso de Estructuras de Datos. Se permite
 * consultarlo para fines didácticos en forma personal, pero no está permitido
 * transferirlo tal cual a estudiantes actuales o potenciales pues se afectará
 * su realización de los ejercicios.
 */

package ed;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * Clase que se encarga de generar de manera aleatoria números enteros del cero
 * a un número dado. Dichos números serán devueltos como String, esto lo hará a
 * través de un iterador. Existe la opción de cambiar la representación del
 * número cero por null. Si se requiere reiniciar la generación de números
 * aleatorios es necesario volver a instanciar el iterador.
 *
 * @author mindahrelfen
 */
public class RandomStringGenerator implements Iterable<String> {

    /**
     * Cantidad de valores por defecto.
     */
    public static final int INIT_SIZE = 16;

    /**
     * Guarda los números que ya se devolvieron.
     */
    protected boolean array[];

    /**
     * Si este parámetro es true al número cero se le asignará null como valor.
     */
    protected boolean useNull;

    /**
     * Constructor por defecto, no permite el uso de null.
     */
    public RandomStringGenerator() {
        this(false);
    }

    /**
     * Constructor que define si se permite null.
     *
     * @param b boolean true si se permite null, false si no.
     */
    public RandomStringGenerator(boolean b) {
        this(INIT_SIZE, b);
    }

    /**
     * Constructor que define la cantidad total de números que puede devolver
     * aleatoriamente, permite el uso de null.
     *
     * @param size int Define la cantidad de números.
     */
    public RandomStringGenerator(int size) {
        this(size, false);
    }

    /**
     * Constructor que define la cantidad total de números que puede devolver
     * aleatoriamente y si se permite el uso de null o no.
     *
     * @param size int Define la cantidad de números.
     * @param b boolean Es true si se permite null y false si no.
     */
    public RandomStringGenerator(int size, boolean b) {
        array = new boolean[size];
        useNull = b;
    }

    @Override
    public Iterator<String> iterator() {
        return new RdnStringIterator();
    }

    @Override
    public String toString() {
        String e;
        StringBuilder sb;
        Iterator<String> it = iterator();
        if (!it.hasNext()) {
            return "[]";
        } else {
            sb = new StringBuilder();
            sb.append('[');
            while (it.hasNext()) {
                e = it.next();
                sb.append(e);
                if (!it.hasNext()) {
                    sb.append(']');
                } else {
                    sb.append(", ");
                }
            }
            return sb.toString();
        }
    }

    /**
     * Clase definida como un Iterador de String, devuelve el siguiente número
     * aleatorio.
     */
    protected class RdnStringIterator implements Iterator<String> {

        /**
         * Cantidad de valores ya devueltos.
         */
        protected int count;

        /**
         * Generador de números aleatorios.
         */
        protected Random rnd;

        /**
         * Constructor por defecto.
         */
        public RdnStringIterator() {
            rnd = new Random();
            count = 0;
            for (int i = 0; i < array.length; i++) {
                array[i] = false;
            }
        }

        /**
         * Obtiene el siguiente número aleatorio.
         *
         * @return int El siguiente número.
         */
        protected int getNextInt() {
            int i;
            do {
                i = rnd.nextInt(array.length);
            }
            while (array[i]);
            return i;
        }

        @Override
        public String next() {
            int i;
            String s;
            if (!hasNext()) throw new NoSuchElementException();
            i = getNextInt();
            if (i == 0 && useNull) {
                s = null;
            } else {
                s = "" + i;
            }
            array[i] = true;
            count++;
            return s;
        }

        @Override
        public boolean hasNext() {
            return count < array.length;
        }
    }
}