/**
 * Código utilizado para el curso de Estructuras de Datos.
 *
 * Se permite consultarlo para fines didácticos de forma personal, pero no está
 * permitido transferirlo tal cual a estudiantes actuales o potenciales pues se
 * afectará su realización de los ejercicios.
 */

package ed.estructuras;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;
import static org.junit.Assert.*;

import ed.Calificador;

/**
 * Clase que agrega pruebas unitarias para las clases hijas de la clase
 * Coleccion Abstracta.
 *
 * @author mindahrelfen
 */
public abstract class ColeccionAbstractaTestA extends Calificador {

    /**
     * Bandera que define si se permite invocar remove(Object).
     */
    protected boolean allowRemove;

    /**
     * Bandera que define si se permite invocar removeAll(Collection).
     */
    protected boolean allowRemoveAll;

    /**
     * Bandera que define si se permite invocar retainAll(Collection).
     */
    protected boolean allowRetainAll;

    /**
     * Bandera que define si se permite invocar remove() del iterador.
     */
    protected boolean allowIteratorRemove;

    /**
     * Bandera que define si el constructor recibe un arreglo del tipo usado
     * como parámetro.
     */
    protected boolean allowDifferentConstructor;

    @Override
    public void init() {
        allowRemove = allowRemoveAll = allowRetainAll = allowIteratorRemove = true;
        allowDifferentConstructor = false;
    }

    @Override
    protected void setCategories() {
        defineCategories(new String[] {
            "Insercion",
            "Borrado",
            "Busqueda",
            "*All",
            "Otros"
        }, new double[] {
            0.2,
            0.2,
            0.3,
            0.15,
            0.15,
        });
    }

    /**
     * Revisa la igualdad semántica entre dos arreglos de Object.
     *
     * @param array1 Object[] Primer arreglo a comparar.
     * @param array2 Object[] Segundo arreglo a comparar.
     *
     * @return boolean Devuelve true si son semánticamente iguales.
     */
    protected boolean equals(Object[] array1, Object[] array2) {
        if (array1 == array2) return true;
        if (array1 == null) return false;
        if (array2 == null) return false;
        if (array1.length != array2.length) return false;
        for (int i = 0; i < array1.length; i++) {
            if (array1[i] == null) {
                if (array2[i] != null) return false;
            } else {
                if (!array1[i].equals(array2[i])) return false;
            }
        }
        return true;
    }

    /**
     * Devuelve una Colección abstracta bien construida.
     *
     * @return Collection Una Colección abstracta de tipo String vacía.
     */
    protected abstract Collection<String> getColeccionAbstracta();

    /**
     * Devuelve una Colección abstracta bien construida.
     *
     * @param array String[] Arreglo de longitud cero del mismo tipo que el
     * generico instanciado.
     * @param range int Capacidad inicial deseada.
     *
     * @return Collection Una Colección abstracta de tipo String vacía.
     */
    protected Collection<String> getColeccionAbstracta(String[] array, int range) {
        throw new UnsupportedOperationException();
    }

    /**
     * Devuelve una Colección abstracta bien construida.
     *
     * @param array String[] Arreglo de longitud cero del mismo tipo que el
     * generico instanciado.
     *
     * @return Collection Una Colección abstracta de tipo String vacía.
     */
    protected Collection<String> getColeccionAbstracta(String[] array) {
        throw new UnsupportedOperationException();
    }

    //constructor

    @Test
    public void constructorIllegalWithSizeTest() {
        Collection<String> structure;

        if (allowDifferentConstructor) {
            startTest("Revisa que se lance IllegalArgumentException si el parámetro arreglo en el constructor tiene longitud distinta de cero", 1.0, "Otros");

            try {
                structure = getColeccionAbstracta(new String[range], range);
            } catch (IllegalArgumentException e) {
                addUp(1.0);
                passed();
            }
        }
    }

    @Test
    public void constructorIllegalNoSizeTest() {
        Collection<String> structure;

        if (allowDifferentConstructor) {
            startTest("Revisa que se lance IllegalArgumentException si el parámetro arreglo en el constructor tiene longitud distinta de cero", 1.0, "Otros");

            try {
                structure = getColeccionAbstracta(new String[range]);
            } catch (IllegalArgumentException e) {
                addUp(1.0);
                passed();
            }
        }
    }

    //add

    @Test
    public void addContainsTest() {
        Collection<String> structure;

        startTest("Revisa que la estructura contenga los elementos insertados con add(E)", 1.0, "Insercion");

        /**
         * Inserta elementos en la estructura.
         */
        structure = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            structure.add(rsgIt.next());
        }

        /**
         * Se asegura que los elementos insertados estén contenidos dentro de
         * la estructura.
         */
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            assertTrue(structure.contains(rsgIt.next()));
        }

        addUp(1.0);
        passed();
    }

    @Test
    public void addSizeTest() {
        int index;
        Collection<String> structure;

        startTest("Revisa que la estructura mantenga la cantidad correcta de elementos, ademas de no estar vacía tras insertar con add(E)", 1.0, "Insercion");

        /**
         * Inserta elementos en la estructura de uno en uno y revisa que cada
         * inserción mantenga el tamaño deseado. Además, revisa que ninguna
         * inserción vuelva vacía la estructura.
         */
        structure = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        index = 1;
        while (rsgIt.hasNext()) {
            assertTrue(structure.add(rsgIt.next()));
            assertEquals(structure.size(), index++);
            assertFalse(structure.isEmpty());
        }

        addUp(1.0);
        passed();
    }

    //addAll

    @Test
    public void addAllContainsTest() {
        Collection<String> structure, aux;

        startTest("Revisa que la estructura contenga todos los elementos insertados por addAll(Collection<?>)", 1.0, "*All");

        /**
         * Agrega elementos en una estructura auxiliar.
         */
        aux = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            aux.add(rsgIt.next());
        }

        /**
         * Usando addAll inserta todos los elementos de la estructura auxiliar
         * en la estructura principal.
         */
        structure = getColeccionAbstracta();
        assertTrue(structure.addAll(aux));

        /**
         * Revisa que la estructura principal contenga los elementos de la
         * estructura auxiliar.
         */
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            assertTrue(structure.contains(rsgIt.next()));
        }

        addUp(1.0);
        passed();
    }

    @Test
    public void addAllSizeTest() {
        Collection<String> structure, aux;

        startTest("Revisa que la estructura contenga la cantidad esperada de elementos insertados por addAll(Collection<?>)", 1.0, "*All");

        /**
         * Agrega elementos en una estructura auxiliar.
         */
        aux = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            aux.add(rsgIt.next());
        }

        /**
         * Usando addAll inserta todos los elementos de la estructura auxiliar
         * en la estructura principal.
         */
        structure = getColeccionAbstracta();
        assertTrue(structure.addAll(aux));

        /**
         * Revisa que el tamaño de ambas estructuras sea el mismo y que no este
         * vacía.
         */
        assertEquals(structure.size(), aux.size());
        assertFalse(structure.isEmpty());

        addUp(1.0);
        passed();
    }

    @Test
    public void addAllNullPointerTest() {
        Collection<String> structure;

        startTest("Revisa que se lance NullPointerException si el parámetro es null en addAll(Collection<?>)", 1.0, "*All");

        structure = getColeccionAbstracta();

        try {
            structure.addAll(null);
        } catch (NullPointerException e) {
            addUp(1.0);
            passed();
        }
    }

    @Test
    public void addAllIllegalArgumentTest() {
        Collection<String> structure;

        startTest("Revisa que se lance IllegalArgumentException si el parámetro es la misma colección en addAll(Collection<?>)", 1.0, "*All");

        structure = getColeccionAbstracta();

        try {
            structure.addAll(structure);
        } catch (IllegalArgumentException e) {
            addUp(1.0);
            passed();
        }
    }

    //clear

    @Test
    public void clearContainsTest() {
        Collection<String> structure;

        startTest("Revisa que la estructura no contenga ningún elemento borrado por clear()", 1.0, "Borrado");

        /**
         * Agrega elementos a la estructura.
         */
        structure = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            structure.add(rsgIt.next());
        }

        /**
         * Aplica clear.
         */
        structure.clear();

        /**
         * Revisa que la estructura no contenga ningun elemento borrado.
         */
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            assertFalse(structure.contains(rsgIt.next()));
        }

        addUp(1.0);
        passed();
    }

    @Test
    public void clearSizeTest() {
        Collection<String> structure;

        startTest("Revisa que la cantidad de elementos sea 0 y la estructura este vacía tras invocar clear()", 1.0, "Borrado");

        /**
         * Agrega elementos a la estructura.
         */
        structure = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            structure.add(rsgIt.next());
        }

        /**
         * Aplica clear.
         */
        structure.clear();

        /**
         * Revisa que la estructura este vacía y tenga tamaño cero.
         */
        assertTrue(structure.isEmpty());
        assertEquals(structure.size(), 0);

        addUp(1.0);
        passed();
    }

    //contains

    //containsAll

    @Test
    public void containsAllTest() {
        Collection<String> structure, aux;

        startTest("Revisa que dos estructuras con los mismos elementos contengan todos los elementos de la otra estructura con containsAll(Collection<?>)", 1.0, "*All");

        /**
         * Agrega elementos a una estructura.
         */
        structure = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            structure.add(rsgIt.next());
        }

        /**
         * Agrega elementos a la otra estructura.
         */
        aux = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            aux.add(rsgIt.next());
        }

        /**
         * Revisa la igualdad de ambas estructuras.
         */
        assertTrue(structure.containsAll(aux));
        assertTrue(aux.containsAll(structure));

        addUp(1.0);
        passed();
    }

    @Test
    public void containsAllItselfTest() {
        Collection<String> structure;

        startTest("Revisa que containsAll(Collection<?>) devuelva verdadero cuando el parámetro es la estructura misma", 1.0, "*All");

        /**
         * Agrega elementos a la estructura.
         */
        structure = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            structure.add(rsgIt.next());
        }

        /**
         * Revisa la igualdad con si misma.
         */
        assertTrue(structure.containsAll(structure));

        addUp(1.0);
        passed();
    }

    @Test
    public void containsAllInitTest() {
        Collection<String> structure, aux;

        startTest("Revisa que dos estructuras vacías tengan los mismos elementos con containsAll(Collection<?>)", 1.0, "*All");

        /**
         * Crea dos estructuras vacías.
         */
        structure = getColeccionAbstracta();
        aux = getColeccionAbstracta();

        /**
         * Revisa la igualdad de ambas estructuras.
         */
        assertTrue(structure.containsAll(aux));
        assertTrue(aux.containsAll(structure));

        addUp(1.0);
        passed();
    }

    //equals

    @Test
    public void equalsTest() {
        String str;
        Collection<String> structure, aux;

        startTest("Revisa la igualdad con equals(Object) para el orden y cantidad de elementos para dos estructuras no vacías equivalentes", 1.0, "Otros");

        /**
         * Agrega los mismos elementos a ambas estructuras.
         */
        structure = getColeccionAbstracta();
        aux = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            str = rsgIt.next();
            structure.add(str);
            aux.add(str);
        }

        /**
         * Revisa la igualdad de ambas estructuras.
         */
        assertTrue(structure.equals(aux));
        assertTrue(aux.equals(structure));

        addUp(1.0);
        passed();
    }

    @Test
    public void equalsItselfTest() {
        Collection<String> structure;

        startTest("Revisa la igualdad con equals(Object) de una estructura con si misma", 1.0, "Otros");

        structure = getColeccionAbstracta();
        assertTrue(structure.equals(structure));

        addUp(1.0);
        passed();
    }

    @Test
    public void equalsNullTest() {
        Collection<String> structure;

        startTest("Revisa la desigualdad con equals(Object) de una estructura con null", 1.0, "Otros");

        structure = getColeccionAbstracta();
        assertFalse(structure.equals(null));

        addUp(1.0);
        passed();
    }

@Test
    public void equalsObjectTest() {
        Collection<String> structure;

        startTest("Revisa la desigualdad con equals(Object) de una estructura con algo que no es una estructura", 1.0, "Otros");

        structure = getColeccionAbstracta();
        assertFalse(structure.equals(new Object()));

        addUp(1.0);
        passed();
    }

    //empty

    @Test
    public void emptyTest() {
        Collection<String> structure;

        startTest("Revisa que la estructura este vacía tras ser inicializada", 1.0, "Otros");

        /**
         * Crea una estructura vacía.
         */
        structure = getColeccionAbstracta();

        /**
         * Revisa que efectivamente este vacía y tenga tamaño cero.
         */
        assertTrue(structure.isEmpty());
        assertEquals(structure.size(), 0);

        addUp(1.0);
        passed();
    }

    //iterator

    @Test
    public void iteratorContainsTest() {
        String str;
        Collection<String> structure, aux;
        Iterator<String> it;

        startTest("Revisa que los elementos devueltos por el iterador sean los mismos que los insertados, no necesariamente en el mismo orden", 1.0, "Busqueda");

        /**
         * Agrega los mismos elementos a ambas estructuras.
         */
        structure = getColeccionAbstracta();
        aux = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            str = rsgIt.next();
            aux.add(str);
            structure.add(str);
        }

        /**
         * Revisa que la estructura auxiliar contenga los elementos de la
         * estructura principal.
         */
        it = structure.iterator();
        while (it.hasNext()) {
            assertTrue(aux.contains(it.next()));
        }

        addUp(1.0);
        passed();
    }

    @Test
    public void iteratorSizeTest() {
        int index;
        Collection<String> structure;
        Iterator<String> it;

        startTest("Revisa que el número de elementos devueltos por el iterador sea el mismo al número de elementos en la estructura", 1.0, "Busqueda");

        /**
         * Agrega elementos a la estructura.
         */
        structure = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            structure.add(rsgIt.next());
        }

        /**
         * Obtiene el número de elementos devueltos por el iterador.
         */
        index = 0;
        it = structure.iterator();
        while (it.hasNext()) {
            it.next();
            index++;
        }

        /**
         * Revisa que dicho número sea el esperado.
         */
        assertEquals(structure.size(), index);

        addUp(1.0);
        passed();
    }

    @Test
    public void iteratorInitTest() {
        Collection<String> structure;
        Iterator<String> it;

        startTest("Revisa que hasNext() del iterador devuelva falso cuando la estructura esta vacía", 1.0, "Busqueda");

        /**
         * Crea una estructura vacía y obtiene su iterador.
         */
        structure = getColeccionAbstracta();
        it = structure.iterator();

        /**
         * Revisa que hasNext devuelva falso.
         */
        assertFalse(it.hasNext());

        addUp(1.0);
        passed();
    }

    @Test
    public void iteratorNoElementExceptionTest() {
        Collection<String> structure;
        Iterator<String> it;

        startTest("Revisa que next() del iterador lance NoSuchElementException cuando la estructura no tiene elementos", 1.0, "Busqueda");

        structure = getColeccionAbstracta();
        it = structure.iterator();

        try {
            it.next();
        } catch (NoSuchElementException e) {
            addUp(1.0);
            passed();
        }
    }

    @Test
    public void iteratorRemoveNoNextTest() {
        boolean flag;
        Collection<String> structure;
        Iterator<String> it;

        if (allowIteratorRemove) {
            startTest("Revisa que en el iterador lance IllegalStateException si se intenta invocar remove() sin invocar next()", 1.0, "Busqueda");

            structure = getColeccionAbstracta();
            it = structure.iterator();
            flag = false;

            try {
                it.remove();
            } catch (IllegalStateException e) {
                addUp(0.5);
                flag = true;
            }


            structure.add(null);
            it = structure.iterator();
            try {
                it.next();
                it.remove();
                it.remove();
            } catch (IllegalStateException e) {
                addUp(0.5);
                if (flag) passed();
            }
        }
    }

    @Test
    public void iteratorRemoveUnsupportedTest() {
        Collection<String> structure;
        Iterator<String> it;

        if (!allowIteratorRemove) {
            startTest("Revisa que remove del iterador lance UnsupportedOperationException si remove() del iterador no se permite", 1.0, "Busqueda");

            structure = getColeccionAbstracta();
            structure.add("Cad");
            it = structure.iterator();

            try {
                it.next();
                it.remove();
            } catch (UnsupportedOperationException e) {
                addUp(1.0);
                passed();
            }
        }
    }

    //remove

    @Test
    public void removeContainsTest() {
        Collection<String> structure;

        if (allowRemove) {
            startTest("Prueba que la estructura no contenga elementos después de borrarlos con remove(E)", 1.0, "Borrado");

            /**
             * Inserta elementos en la estructura.
             */
            structure = getColeccionAbstracta();
            rsgIt = rsg.iterator();
            while (rsgIt.hasNext()) {
                structure.add(rsgIt.next());
            }

            /**
             * Luego los expulsa hasta que la estructura este vacía.
             */
            rsgIt = rsg.iterator();
            while (rsgIt.hasNext()) {
                assertTrue(structure.remove(rsgIt.next()));
            }

            /**
             * Revisa que ningún elemento insertado se encuentre en la
             * estructura.
             */
            rsgIt = rsg.iterator();
            while (rsgIt.hasNext()) {
                assertFalse(structure.contains(rsgIt.next()));
            }

            addUp(1.0);
            passed();
        }
    }

    @Test
    public void removeSizeTest() {
        int index;
        Collection<String> structure;

        if (allowRemove) {
            startTest("Revisa que la cantidad de elementos tras borrar sea consistente con remove(E)", 1.0, "Borrado");

            /**
             * Inserta elementos en la estructura.
             */
            structure = getColeccionAbstracta();
            rsgIt = rsg.iterator();
            while (rsgIt.hasNext()) {
                structure.add(rsgIt.next());
            }

            /**
             * Borra los elementos de la estructura de uno en uno y revisa que
             * cada borrado mantenga el tamaño deseado.
             */
            index = 1;
            rsgIt = rsg.iterator();
            while (index <= range) {
                structure.remove(rsgIt.next());
                assertEquals(structure.size(), range - index++);
            }

            /**
             * Revisa que la estructura este vacía al finalizar el borrado de
             * todos sus elementos.
             */
            assertTrue(structure.isEmpty());

            addUp(1.0);
            passed();
        }
    }

    @Test
    public void removeEmptyTest() {
        Collection<String> structure;

        if (allowRemove) {
            startTest("Revisa que se devuelva falso cuando se intenta borrar con remove(E) y no hay elementos", 1.0, "Borrado");

            /**
             * Si se intenta borrar sobre una estructura vacía debe devolver
             * null.
             */
            structure = getColeccionAbstracta();
            assertFalse(structure.remove(new Object()));

            addUp(1.0);
            passed();
        }
    }

    @Test
    public void removeUnsupportedTest() {
        Collection<String> structure;

        if (!allowRemove) {
            startTest("Revisa que remove(E) lance UnsupportedOperationException si remove(E) no se permite", 1.0, "Borrado");

            structure = getColeccionAbstracta();
            try {
                structure.remove(null);
            } catch (UnsupportedOperationException e) {
                addUp(1.0);
                passed();
            }
        }
    }

    //removeAll

    @Test
    public void removeAllContainsTest() {
        String str;
        Collection<String> structure, aux;

        if (allowRemoveAll) {
            startTest("Revisa que la estructura no contenga ningún elemento borrado por removeAll(Collection<E>)", 2.0, "*All");

            /**
             * Agrega los mismos elementos a ambas estructuras.
             */
            structure = getColeccionAbstracta();
            aux = getColeccionAbstracta();
            rsgIt = rsg.iterator();
            while (rsgIt.hasNext()) {
                str = rsgIt.next();
                structure.add(str);
                aux.add(str);
            }

            /**
             * Aplica removeAll.
             */
            assertTrue(structure.removeAll(aux));
            addUp(1.0);

            /**
             * Revisa que ningún elemento borrado este contenido en la
             * estructura.
             */
            rsgIt = rsg.iterator();
            while (rsgIt.hasNext()) {
                assertFalse(structure.contains(rsgIt.next()));
            }

            addUp(1.0);
            passed();
        }
    }

    @Test
    public void removeAllSizeTest() {
        String str;
        Collection<String> structure, aux;

        if (allowRemoveAll) {
            startTest("Revisa que la estructura este vacía y con cero elementos tras usar removeAll(Collection<E>) con una estructura equivalente", 1.0, "*All");

            /**
             * Agrega los mismos elementos a ambas estructuras.
             */
            structure = getColeccionAbstracta();
            aux = getColeccionAbstracta();
            rsgIt = rsg.iterator();
            while (rsgIt.hasNext()) {
                str = rsgIt.next();
                structure.add(str);
                aux.add(str);
            }

            /**
             * Aplica removeAll.
             */
            assertTrue(structure.removeAll(aux));

            /**
             * Revisa que la estructura original este vacía y con tamaño cero.
             */
            assertTrue(structure.isEmpty());
            assertEquals(structure.size(), 0);

            addUp(1.0);
            passed();
        }
    }

    @Test
    public void removeAllNullPointerTest() {
        Collection<String> structure;

        if (allowRemoveAll) {
            startTest("Revisa que removeAll(Collection<E>) lance NullPointerException si el parámetro es null", 1.0, "*All");

            structure = getColeccionAbstracta();

            try {
                structure.removeAll(null);
            } catch (NullPointerException e) {
                addUp(1.0);
                passed();
            }
        }
    }

    @Test
    public void removeAllItselfTest() {
        Collection<String> structure;

        if (allowRemoveAll) {
            startTest("Revisa que la estructura este vacía y con cero elementos tras usar removeAll(Collection<E>) con ella misma", 1.0, "*All");

            /**
             * Agrega elementos a la estructura.
             */
            structure = getColeccionAbstracta();
            rsgIt = rsg.iterator();
            while (rsgIt.hasNext()) {
                structure.add(rsgIt.next());
            }

            /**
             * Aplica removeAll sobre si misma.
             */
            assertTrue(structure.removeAll(structure));

            /**
             * Revisa que la estructura este vacía y con tamaño cero.
             */
            assertTrue(structure.isEmpty());
            assertEquals(structure.size(), 0);

            addUp(1.0);
            passed();
        }
    }

    @Test
    public void removeAllEmptyTest() {
        Collection<String> structure, aux;

        if (allowRemoveAll) {
            startTest("Revisa que removeAll(Collection<E>) devuelva falso si la estructura está vacía", 1.0, "*All");

            /**
             * Crea dos estructuras vacías.
             */
            structure = getColeccionAbstracta();
            aux = getColeccionAbstracta();

            /**
             * Revisa que la invocación de removeAll devuelva falso.
             */
            assertFalse(structure.removeAll(aux));

            addUp(1.0);
            passed();
        }
    }

    @Test
    public void removeAllUnsupportedTest() {
        Collection<String> structure;

        if (!allowRemoveAll) {
            startTest("Revisa que removeAll(Collection<E>) lance UnsupportedOperationException si removeAll(Collection<E>) no se permite", 1.0, "*All");

            structure = getColeccionAbstracta();

            try {
                structure.removeAll(null);
            } catch (UnsupportedOperationException e) {
                addUp(1.0);
                passed();
            }
        }
    }

    //retainAll

    @Test
    public void retainAllContainsTest() {
        String str;
        Collection<String> structure, aux;

        if (allowRetainAll) {
            startTest("Revisa que retainAll(Collection<E>) no haga cambios entre dos estructuras equivalentes", 1.0, "*All");

            /**
             * Agrega los mismos elementos a ambas estructuras.
             */
            structure = getColeccionAbstracta();
            aux = getColeccionAbstracta();
            rsgIt = rsg.iterator();
            while (rsgIt.hasNext()) {
                str = rsgIt.next();
                structure.add(str);
                aux.add(str);
            }

            /**
             * Invoca retainAll.
             */
            assertFalse(structure.retainAll(aux));
            assertFalse(aux.retainAll(structure));

            /**
             * Revisa que ambas estructuras mantengan su tamaño original y no estén vacías.
             */
            assertFalse(structure.isEmpty());
            assertEquals(structure.size(), range);
            assertFalse(aux.isEmpty());
            assertEquals(aux.size(), range);

            addUp(1.0);
            passed();
        }
    }

    @Test
    public void retainAllDontContainsTest() {
        int index;
        String str;
        Collection<String> structure, aux;

        if (allowRetainAll) {
            startTest("Revisa que retainAll(Collection<E>) borre todos los elementos entre dos estructuras diferentes", 1.0, "*All");

            /**
             * Agrega elementos a ambas estructuras, no tienen elementos en
             * común.
             */
            structure = getColeccionAbstracta();
            aux = getColeccionAbstracta();
            rsgIt = rsg.iterator();
            index = 0;
            while (rsgIt.hasNext()) {
                str = rsgIt.next();
                if (index++ % 2 == 0) {
                    structure.add(str);
                } else {
                    aux.add(str);
                }
            }

            /**
             * Invoca retainAll.
             */
            assertTrue(structure.retainAll(aux));
            assertTrue(aux.retainAll(structure));

            /**
             * Revisa que ambas estructuras estén vacías y con tamaño cero.
             */
            assertTrue(structure.isEmpty());
            assertEquals(structure.size(), 0);
            assertTrue(aux.isEmpty());
            assertEquals(aux.size(), 0);

            addUp(1.0);
            passed();
        }
    }

    @Test
    public void retainAllNullPointerTest() {
        Collection<String> structure;

        if (allowRetainAll) {
            startTest("Revisa que retainAll(Collection<E>) lance NullPointerException si el parámetro es null", 1.0, "*All");

            structure = getColeccionAbstracta();

            try {
                structure.retainAll(null);
            } catch (NullPointerException e) {
                addUp(1.0);
                passed();
            }
        }
    }

    @Test
    public void retainAllItselfTest() {
        Collection<String> structure;

        if (allowRetainAll) {
            startTest("Revisa que retainAll(Collection<E>) mantenga la cantidad de elementos si el parámetro es la misma estructura", 1.0, "*All");

            /**
             * Agrega elementos a la estructura.
             */
            structure = getColeccionAbstracta();
            rsgIt = rsg.iterator();
            while (rsgIt.hasNext()) {
                structure.add(rsgIt.next());
            }

            /**
             * Invoca retainAll sobre si misma.
             */
            assertFalse(structure.retainAll(structure));

            /**
             * Revisa que la estructura mantenga su tamaño y no este vacía.
             */
            assertFalse(structure.isEmpty());
            assertEquals(structure.size(), range);

            addUp(1.0);
            passed();
        }
    }

    @Test
    public void retainAllEmptyTest() {
        Collection<String> structure, aux;

        if (allowRetainAll) {
            startTest("Revisa que se borren todos los elementos si el parámetro de retainAll(Collection<E>) es una estructura vacía", 1.0, "*All");

            /**
             * Agrega elementos a la estructura.
             */
            structure = getColeccionAbstracta();
            rsgIt = rsg.iterator();
            while (rsgIt.hasNext()) {
                structure.add(rsgIt.next());
            }

            /**
             * Crea una estructura vacía e invoca retainAll.
             */
            aux = getColeccionAbstracta();
            assertTrue(structure.retainAll(aux));

            /**
             * Revisa que la estructura original este vacía y con tamaño cero.
             */
            assertTrue(structure.isEmpty());
            assertEquals(structure.size(), 0);

            addUp(1.0);
            passed();
        }
    }

    @Test
    public void retainAllUnsupportedTest() {
        Collection<String> structure;

        if (!allowRetainAll) {
            startTest("Revisa que retainAll(Collection<E>) lance UnsupportedOperationException si retainAll(Collection<E>) no se permite", 1.0, "*All");

            structure = getColeccionAbstracta();

            try {
                structure.retainAll(null);
            } catch (UnsupportedOperationException e) {
                addUp(1.0);
                passed();
            }
        }
    }

    //size

    @Test
    public void zeroSizeTest() {
        Collection<String> structure;

        startTest("Revisa que al inicializar la estructura la cantidad de elementos devuelta por size() sea cero", 1.0, "Otros");

        /**
         * Revisa que el tamaño de la estructura al ser creada sea cero.
         */
        structure = getColeccionAbstracta();
        assertEquals(structure.size(), 0);

        addUp(1.0);
        passed();
    }

    //toArray: Object[]

    @Test
    public void toObjectArrayContainsTest() {
        int index;
        Object array[];
        Collection<String> structure;
        Iterator<String> it;

        startTest("Revisa que el orden de los elementos devueltos por toArray() sea el mismo que el del iterador", 1.0, "Otros");

        /**
         * Agrega elementos a la estructura.
         */
        structure = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            structure.add(rsgIt.next());
        }

        /**
         * Obtiene el arreglo equivalente de la estructura.
         */
        array = structure.toArray();

        /**
         * Crea el iterador de la estructura y revisa que los elementos sean
         * equivalentes semánticamente y en el mismo orden.
         */
        it = structure.iterator();
        index = 0;
        while (it.hasNext()) {
            if (array[index] == null) {
                assertNull(it.next());
            } else {
                assertTrue(array[index].equals(it.next()));
            }
            index++;
        }

        addUp(1.0);
        passed();
    }

    @Test
    public void toObjectArraySizeTest() {
        int index;
        Object array[];
        Collection<String> structure;
        Iterator<String> it;

        startTest("Revisa que el número de elementos devueltos por toArray() sea el mismo que el que devuelve el iterador", 1.0, "Otros");

        /**
         * Agrega elementos a la estructura.
         */
        structure = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            structure.add(rsgIt.next());
        }

        /**
         * Obtiene el arreglo equivalente de la estructura.
         */
        array = structure.toArray();

        /**
         * Crea el iterador de la estructura y calcula el numero de elementos
         * obtenidos por dicho iterador.
         */
        it = structure.iterator();
        index = 0;
        while (it.hasNext()) {
            it.next();
            index++;
        }

        /**
         * Revisa que el tamaño sea correcto.
         */
        assertEquals(array.length, index);

        addUp(1.0);
        passed();
    }

    //toArray: E[]

    @Test
    public void toGenericArrayContainsTest() {
        int index;
        String array[];
        Collection<String> structure;
        Iterator<String> it;

        startTest("Revisa que el orden de los elementos devueltos por toArray(E[]) sea el mismo que el del iterador", 1.0, "Otros");

        /**
         * Agrega elementos a la estructura.
         */
        structure = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            structure.add(rsgIt.next());
        }

        /**
         * Crea un arreglo del tamaño esperado.
         */
        array = new String[structure.size()];

        /**
         * Obtiene el arreglo equivalente de la estructura.
         */
        array = structure.toArray(array);

        /**
         * Crea el iterador de la estructura y revisa que los elementos sean
         * equivalentes semánticamente y sean devueltos en el mismo orden.
         */
        it = structure.iterator();
        index = 0;
        while (it.hasNext()) {
            if (array[index] == null) {
                assertNull(it.next());
            } else {
                assertTrue(array[index].equals(it.next()));
            }
            index++;
        }

        addUp(1.0);
        passed();
    }

    @Test
    public void toGenericArraySizeTest() {
        int index;
        String array[];
        Collection<String> structure;
        Iterator<String> it;

        startTest("Revisa que el número de elementos devueltos por toArray(E[]) sea el mismo que el del iterador", 1.0, "Otros");

        /**
         * Agrega elementos a la estructura.
         */
        structure = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            structure.add(rsgIt.next());
        }

        /**
         * Crea un arreglo del tamaño esperado.
         */
        array = new String[structure.size()];

        /**
         * Obtiene el arreglo equivalente de la estructura.
         */
        array = structure.toArray(array);

        /**
         * Crea el iterador de la estructura y calcula el numero de elementos
         * obtenidos por dicho iterador.
         */
        it = structure.iterator();
        index = 0;
        while (it.hasNext()) {
            it.next();
            index++;
        }

        /**
         * Revisa que el tamaño sea correcto.
         */
        assertEquals(array.length, index);

        addUp(1.0);
        passed();
    }

    @Test
    public void toGenericArrayZeroSizeTest() {
        String array[];
        Collection<String> structure;

        startTest("Revisa que el número de elementos de toArray(E[]) sea correcto cuando el arreglo pasado es de menor longitud al tamaño de la estructura", 1.0, "Otros");

        /**
         * Agrega elementos a la estructura.
         */
        structure = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            structure.add(rsgIt.next());
        }

        /**
         * Crea un arreglo del tamaño menor al de la estructura.
         */
        array = new String[rdm.nextInt(structure.size())];

        /**
         * Obtiene el arreglo equivalente de la estructura.
         */
        array = structure.toArray(array);

        /**
         * Revisa que el tamaño sea correcto.
         */
        assertEquals(array.length, range);

        addUp(1.0);
        passed();
    }

    @Test
    public void toGenericArrayNullFillTest() {
        String array[];
        Collection<String> structure;

        startTest("Revisa que toArray(E[]) devuelva elementos null cuando la longitud del arreglo es mayor al número de elementos en la estructura", 1.0, "Otros");

        /**
         * Agrega elementos a la estructura.
         */
        structure = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            structure.add(rsgIt.next());
        }

        /**
         * Crea un arreglo del tamaño doble al numero de elementos insertados,
         * y llena el arreglo con elementos no nulos.
         */
        array = new String[range * 2];
        for (int i = range; i < array.length; i++) {
            array[i] = new String();
        }

        /**
         * Obtiene el arreglo equivalente de la estructura.
         */
        array = structure.toArray(array);

        /**
         * Revisa que el tamaño sea correcto.
         */
        assertEquals(array.length, range * 2);

        /**
         * Revisa que el arreglo contenga elementos null desde la posición
         * igual al tamaño de la estructura hasta el doble de dicho tamaño.
         */
        for (int i = structure.size(); i < range * 2; i++) {
            assertNull(array[i]);
        }

        addUp(1.0);
        passed();
    }

    @Test
    public void toGenericArrayNullPTest() {
        String array[];
        Collection<String> structure;

        startTest("Revisa que toArray(E[]) lance NullPointerException si el parámetro es null", 1.0, "Otros");

        structure = getColeccionAbstracta();

        try {
            array = null;
            array = structure.toArray(array);
        } catch (NullPointerException e) {
            addUp(1.0);
            passed();
        }
    }
}
