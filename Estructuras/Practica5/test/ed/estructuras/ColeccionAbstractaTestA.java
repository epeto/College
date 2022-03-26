/*
 * Código utilizado para el curso de Estructuras de Datos. Se permite
 * consultarlo para fines didácticos en forma personal, pero no está permitido
 * transferirlo tal cual a estudiantes actuales o potenciales pues se afectará
 * su realización de los ejercicios.
 */

package ed.estructuras;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
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
     * Bandera que define si el constructor recibe un arreglo del tipo usado como parámetro.
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
     * @return Collection Una Colección abstracta de tipo String vacía.
     */
    protected Collection<String> getColeccionAbstracta(String[] array) {
        throw new UnsupportedOperationException();
    }

    //constructor

    @Test(expected = IllegalArgumentException.class)
    public void constructorIllegalWithSizeTest() {
        Collection<String> ac;
        if (allowDifferentConstructor) {
            startTest("Revisa que se lance IllegalArgumentException si el parámetro arreglo en el constructor tiene longitud != 0", 1.0, "Otros");
            try {
                ac = getColeccionAbstracta(new String[range], range);
            } catch (IllegalArgumentException e) {
                addUp(1.0);
                passed();
                throw e;
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorIllegalNoSizeTest() {
        Collection<String> ac;
        if (allowDifferentConstructor) {
            startTest("Revisa que se lance IllegalArgumentException si el parámetro arreglo en el constructor tiene longitud != 0", 1.0, "Otros");
            try {
                ac = getColeccionAbstracta(new String[range]);
            } catch (IllegalArgumentException e) {
                addUp(1.0);
                passed();
                throw e;
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    //add

    @Test
    public void addContainsTest() {
        Collection<String> ac;
        startTest("Revisa si la colección contiene los elementos insertados por add", 2.0, "Insercion");
        ac = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            assertTrue(ac.add(rsgIt.next()));
        }
        addUp(1.0);
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            assertTrue(ac.contains(rsgIt.next()));
        }
        addUp(1.0);
        passed();
    }

    @Test
    public void addSizeTest() {
        int i;
        Collection<String> ac;
        startTest("Revisa que la colección mantenga la cantidad correcta y no vacía de elementos tras insertar con add", 1.0, "Insercion");
        ac = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        i = 1;
        while (rsgIt.hasNext()) {
            assertTrue(ac.add(rsgIt.next()));
            assertEquals(ac.size(), i++);
            assertFalse(ac.isEmpty());
        }
        addUp(1.0);
        passed();
    }

    //addAll

    @Test
    public void addAllContainsTest() {
        Collection<String> ac;
        LinkedList<String> l;
        startTest("Revisa que la colección contenga todos los elementos insertados por addAll", 2.0, "*All");
        ac = getColeccionAbstracta();
        l = new LinkedList<> ();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            l.add(rsgIt.next());
        }
        assertTrue(ac.addAll(l));
        addUp(1.0);
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            assertTrue(ac.contains(rsgIt.next()));
        }
        addUp(1.0);
        passed();
    }

    @Test
    public void addAllSizeTest() {
        Collection<String> ac;
        LinkedList<String> l;
        startTest("Revisa si la colección contiene la cantidad de elementos insertados por addAll", 1.0, "*All");
        ac = getColeccionAbstracta();
        l = new LinkedList<> ();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            l.add(rsgIt.next());
        }
        assertTrue(ac.addAll(l));
        assertEquals(ac.size(), range);
        assertFalse(ac.isEmpty());
        addUp(1.0);
        passed();
    }

    @Test(expected = NullPointerException.class)
    public void addAllNullPointerTest() {
        Collection<String> ac;
        startTest("Revisa que se lance NullPointerException si el parámetro es null en addAll", 1.0, "*All");
        ac = getColeccionAbstracta();
        try {
            ac.addAll(null);
        } catch (NullPointerException e) {
            addUp(1.0);
            passed();
            throw e;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void addAllIllegalArgumentTest() {
        Collection<String> ac;
        startTest("Revisa que se lance IllegalArgumentException si el parámetro es la misma colección en add", 1.0, "*All");
        ac = getColeccionAbstracta();
        try {
            ac.addAll(ac);
        } catch (IllegalArgumentException e) {
            addUp(1.0);
            passed();
            throw e;
        }
    }

    //clear

    @Test
    public void clearContainsTest() {
        Collection<String> ac;
        startTest("Revisa que la colección no contenga ningún elemento borrado por clear", 1.0, "Borrado");
        ac = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            ac.add(rsgIt.next());
        }
        ac.clear();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            assertFalse(ac.contains(rsgIt.next()));
        }
        addUp(1.0);
        passed();
    }

    @Test
    public void clearSizeTest() {
        Collection<String> ac;
        startTest("Revisa que la cantidad de elementos sea 0 y la colección este vacía tras invocar clear", 1.0, "Borrado");
        ac = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            ac.add(rsgIt.next());
        }
        ac.clear();
        assertTrue(ac.isEmpty());
        assertEquals(ac.size(), 0);
        addUp(1.0);
        passed();
    }

    @Test
    public void clearInitTest() {
        Collection<String> ac1, ac2;
        startTest("Revisa que una colección nueva sea igual a una colección que invoco clear", 1.0, "Borrado");
        ac1 = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            ac1.add(rsgIt.next());
        }
        ac1.clear();
        ac2 = getColeccionAbstracta();
        assertTrue(ac1.equals(ac2));
        assertTrue(ac2.equals(ac1));
        addUp(1.0);
        passed();
    }

    //contains

    //containsAll

    @Test
    public void containsAllTest() {
        Collection<String> ac1, ac2;
        startTest("Revisa que dos colecciones con los mismos elementos contengan todos los elementos de la otra colección con containsAll", 1.0, "*All");
        ac1 = getColeccionAbstracta();
        ac2 = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            ac1.add(rsgIt.next());
        }
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            ac2.add(rsgIt.next());
        }
        assertTrue(ac1.containsAll(ac2));
        assertTrue(ac2.containsAll(ac1));
        addUp(1.0);
        passed();
    }

    @Test
    public void containsAllButOneTest() {
        int i;
        String aux;
        Collection<String> ac1, ac2;
        startTest("Revisa la desigualdad a través de containsAll entre dos colección que difieren por un elemento", 1.0, "*All");
        ac1 = getColeccionAbstracta();
        ac2 = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        i = 0;
        while (rsgIt.hasNext()) {
            aux = rsgIt.next();
            if (i++ != 0) {
                ac1.add(aux);
            }
            ac2.add(aux);
        }
        assertFalse(ac1.containsAll(ac2));
        assertTrue(ac2.containsAll(ac1));
        addUp(1.0);
        passed();
    }

    @Test
    public void containsAllItselfTest() {
        Collection<String> ac;
        startTest("Revisa que containsAll devuelva verdadero cuando el parámetro es la colección misma", 1.0, "*All");
        ac = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            ac.add(rsgIt.next());
        }
        assertTrue(ac.containsAll(ac));
        addUp(1.0);
        passed();
    }
    @Test
    public void containsAllInitTest() {
        Collection<String> ac1, ac2;
        startTest("Revisa que dos colecciones vacías tengan los mismos elementos con containsAll", 1.0, "*All");
        ac1 = getColeccionAbstracta();
        ac2 = getColeccionAbstracta();
        assertTrue(ac1.containsAll(ac2));
        assertTrue(ac2.containsAll(ac1));
        addUp(1.0);
        passed();
    }

    //equals

    @Test
    public void equalsTest() {
        String aux;
        Collection<String> ac1, ac2;
        startTest("Revisa la igualdad con equals para el orden y cantidad de elementos para dos colecciones no vacías equivalentes", 1.0, "Otros");
        ac1 = getColeccionAbstracta();
        ac2 = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            aux = rsgIt.next();
            ac1.add(aux);
            ac2.add(aux);
        }
        assertTrue(ac1.equals(ac2));
        assertTrue(ac2.equals(ac1));
        addUp(1.0);
        passed();
    }

    @Test
    public void equalsItselfTest() {
        Collection<String> ac;
        startTest("Revisa la igualdad con equals de una colección con si misma", 1.0, "Otros");
        ac = getColeccionAbstracta();
        assertTrue(ac.equals(ac));
        addUp(1.0);
        passed();
    }

    @Test
    public void equalsNullTest() {
        Collection<String> ac;
        startTest("Revisa la desigualdad con equals de una colección con null", 1.0, "Otros");
        ac = getColeccionAbstracta();
        assertFalse(ac.equals(null));
        addUp(1.0);
        passed();
    }

    @Test
    public void equalsObjectTest() {
        Collection<String> ac;
        startTest("Revisa la desigualdad con equals de una colección con algo que no es una colección", 1.0, "Otros");
        ac = getColeccionAbstracta();
        assertFalse(ac.equals(new Object()));
        addUp(1.0);
        passed();
    }

    //empty

    @Test
    public void emptyTest() {
        Collection<String> ac;
        startTest("Revisa que la colección está vacía tras ser inicializada", 1.0, "Otros");
        ac = getColeccionAbstracta();
        assertTrue(ac.isEmpty());
        addUp(1.0);
        passed();
    }

    //iterator

    @Test
    public void iteratorContainsTest() {
        Iterator<String> it;
        String aux;
        Collection<String> ac;
        LinkedList<String> l;
        startTest("Revisa que los elementos devueltos por el iterador sean los mismos que los insertados, no necesariamente en el mismo orden", 1.0, "Busqueda");
        ac = getColeccionAbstracta();
        l = new LinkedList<> ();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            aux = rsgIt.next();
            l.add(aux);
            ac.add(aux);
        }
        it = ac.iterator();
        while (it.hasNext()) {
            assertTrue(l.contains(it.next()));
        }
        addUp(1.0);
        passed();
    }

    @Test
    public void iteratorSizeTest() {
        int aux;
        Iterator<String> it;
        Collection<String> ac;
        startTest("Revisa que el número de elementos devueltos por el iterador sea el mismo al número de elementos en la colección", 1.0, "Busqueda");
        ac = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            ac.add(rsgIt.next());
        }
        aux = 0;
        it = ac.iterator();
        while (it.hasNext()) {
            it.next();
            aux++;
        }
        assertEquals(ac.size(), aux);
        addUp(1.0);
        passed();
    }

    @Test
    public void iteratorInitTest() {
        Iterator<String> it;
        Collection<String> ac;
        startTest("Revisa que el iterador no devuelva ningún elemento en una colección vacía", 1.0, "Busqueda");
        ac = getColeccionAbstracta();
        it = ac.iterator();
        assertFalse(it.hasNext());
        addUp(1.0);
        passed();
    }

    @Test(expected = NoSuchElementException.class)
    public void iteratorNoElementExceptionTest() {
        Iterator<String> it;
        Collection<String> ac;
        startTest("Revisa que el iterador lance NoSuchElementException cuando no tiene elemento", 1.0, "Busqueda");
        ac = getColeccionAbstracta();
        it = ac.iterator();
        try {
            it.next();
        } catch (NoSuchElementException e) {
            addUp(1.0);
            passed();
            throw e;
        }
    }

    @Test
    public void iteratorRemoveNoNextTest() {
        Iterator<String> it;
        Collection<String> ac;
        if (allowIteratorRemove) {
            startTest("Revisa que el iterador lance IllegalStateException si se intenta invocar remove sin invocar next", 2.0, "Busqueda");
            ac = getColeccionAbstracta();
            it = ac.iterator();
            try {
                it.remove();
            } catch (IllegalStateException e) {
                addUp(1.0);
            }
            try {
                ac.add(null);
                it = ac.iterator();
                it.next();
                it.remove();
                it.remove();
            } catch (IllegalStateException e) {
                addUp(1.0);
            }
            passed();
        }
    }

    @Test(expected = UnsupportedOperationException.class)
    public void iteratorRemoveUnsupportedTest() {
        Collection<String> ac;
        Iterator<String> it;
        if (!allowIteratorRemove) {
            startTest("Revise que remove de iterator lance UnsupportedOperationException si remove de iterator no se permite", 1.0, "Busqueda");
            ac = getColeccionAbstracta();
            try {
                ac.add(null);
                it = ac.iterator();
                it.next();
                it.remove();
            } catch (UnsupportedOperationException e) {
                addUp(1.0);
                passed();
                throw e;
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    //remove

    @Test
    public void removeContainsTest() {
        Collection<String> ac;
        if (allowRemove) {
            startTest("Revisa que la colección no contenga ningún elemento borrado con remove", 2.0, "Borrado");
            ac = getColeccionAbstracta();
            rsgIt = rsg.iterator();
            while (rsgIt.hasNext()) {
                ac.add(rsgIt.next());
            }
            rsgIt = rsg.iterator();
            while (rsgIt.hasNext()) {
                assertTrue(ac.remove(rsgIt.next()));
            }
            addUp(1.0);
            rsgIt = rsg.iterator();
            while (rsgIt.hasNext()) {
                assertFalse(ac.contains(rsgIt.next()));
            }
            addUp(1.0);
            passed();
        }
    }

    @Test
    public void removeSizeTest() {
        int i;
        Collection<String> ac;
        if (allowRemove) {
            startTest("Revisa que la cantidad de elementos tras remove sea consistente", 1.0, "Borrado");
            ac = getColeccionAbstracta();
            rsgIt = rsg.iterator();
            while (rsgIt.hasNext()) {
                ac.add(rsgIt.next());
            }
            i = 1;
            rsgIt = rsg.iterator();
            while (rsgIt.hasNext()) {
                assertTrue(ac.remove(rsgIt.next()));
                assertEquals(ac.size(), range - i++);
            }
            assertTrue(ac.isEmpty());
            addUp(1.0);
            passed();
        }
    }

    @Test
    public void removeEmptyTest() {
        Collection<String> ac;
        if (allowRemove) {
            startTest("Revisa que remove devuelva falso para una colección vacía", 1.0, "Borrado");
            ac = getColeccionAbstracta();
            assertFalse(ac.remove(new Object()));
            addUp(1.0);
            passed();
        }
    }

    @Test(expected = UnsupportedOperationException.class)
    public void removeUnsupportedTest() {
        Collection<String> ac;
        if (!allowRemove) {
            startTest("Revise que remove lance UnsupportedOperationException si remove no se permite", 1.0, "Borrado");
            ac = getColeccionAbstracta();
            try {
                ac.remove(null);
            } catch (UnsupportedOperationException e) {
                addUp(1.0);
                passed();
                throw e;
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    //removeAll

    @Test
    public void removeAllContainsTest() {
        String aux;
        Collection<String> ac;
        LinkedList<String> l;
        if (allowRemoveAll) {
            startTest("Revisa que la colección no contenga ningún elemento borrado por removeAll", 2.0, "*All");
            ac = getColeccionAbstracta();
            l = new LinkedList<> ();
            rsgIt = rsg.iterator();
            while (rsgIt.hasNext()) {
                aux = rsgIt.next();
                l.add(aux);
                ac.add(aux);
            }
            assertTrue(ac.removeAll(l));
            addUp(1.0);
            rsgIt = rsg.iterator();
            while (rsgIt.hasNext()) {
                assertFalse(ac.contains(rsgIt.next()));
            }
            addUp(1.0);
            passed();
        }
    }

    @Test
    public void removeAllSizeTest() {
        String aux;
        Collection<String> ac;
        LinkedList<String> l;
        if (allowRemoveAll) {
            startTest("Revisa que la colección este vacía y con cero elementos tras usar removeAll con una colección igual", 1.0, "*All");
            ac = getColeccionAbstracta();
            l = new LinkedList<> ();
            rsgIt = rsg.iterator();
            while (rsgIt.hasNext()) {
                aux = rsgIt.next();
                l.add(aux);
                ac.add(aux);
            }
            assertTrue(ac.removeAll(l));
            assertTrue(ac.isEmpty());
            assertEquals(ac.size(), 0);
            addUp(1.0);
            passed();
        }
    }

    @Test(expected = NullPointerException.class)
    public void removeAllNullPointerTest() {
        Collection<String> ac;
        if (allowRemoveAll) {
            startTest("Revisa que removeAll lance NullPointerException si el parámetro es null", 1.0, "*All");
            ac = getColeccionAbstracta();
            try {
                ac.removeAll(null);
            } catch (NullPointerException e) {
                addUp(1.0);
                passed();
                throw e;
            }
        } else {
            throw new NullPointerException();
        }
    }

    @Test
    public void removeAllItselfTest() {
        Collection<String> ac;
        if (allowRemoveAll) {
            startTest("Revisa que la colección este vacía y con cero elementos tras usar removeAll con ella misma", 1.0, "*All");
            ac = getColeccionAbstracta();
            rsgIt = rsg.iterator();
            while (rsgIt.hasNext()) {
                ac.add(rsgIt.next());
            }
            assertTrue(ac.removeAll(ac));
            assertTrue(ac.isEmpty());
            assertEquals(ac.size(), 0);
            addUp(1.0);
            passed();
        }
    }

    @Test
    public void removeAllEmptyTest() {
        Collection<String> ac1, ac2;
        if (allowRemoveAll) {
            startTest("Revisa que removeAll devuelva falso si está vacía", 1.0, "*All");
            ac1 = getColeccionAbstracta();
            ac2 = getColeccionAbstracta();
            assertFalse(ac1.removeAll(ac2));
            addUp(1.0);
            passed();
        }
    }

    @Test(expected = UnsupportedOperationException.class)
    public void removeAllUnsupportedTest() {
        Collection<String> ac;
        if (!allowRemoveAll) {
            startTest("Revise que removeAll lance UnsupportedOperationException si removeAll no se permite", 1.0, "*All");
            ac = getColeccionAbstracta();
            try {
                ac.removeAll(null);
            } catch (UnsupportedOperationException e) {
                addUp(1.0);
                passed();
                throw e;
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    //retainAll

    @Test
    public void retainAllContainsTest() {
        String aux;
        Collection<String> ac1, ac2;
        if (allowRetainAll) {
            startTest("Revisa que retainAll no haga cambios entre dos colecciones equivalentes", 1.0, "*All");
            ac1 = getColeccionAbstracta();
            ac2 = getColeccionAbstracta();
            rsgIt = rsg.iterator();
            while (rsgIt.hasNext()) {
                aux = rsgIt.next();
                ac1.add(aux);
                ac2.add(aux);
            }
            assertFalse(ac1.retainAll(ac2));
            assertFalse(ac2.retainAll(ac1));
            assertFalse(ac1.isEmpty());
            assertEquals(ac1.size(), range);
            assertFalse(ac2.isEmpty());
            assertEquals(ac2.size(), range);
            addUp(1.0);
            passed();
        }
    }

    @Test
    public void retainAllDontContainsTest() {
        int i;
        String aux;
        Collection<String> ac1, ac2;
        if (allowRetainAll) {
            startTest("Revisa que retainAll borre todos los elementos entre dos colecciones diferentes", 1.0, "*All");
            ac1 = getColeccionAbstracta();
            ac2 = getColeccionAbstracta();
            rsgIt = rsg.iterator();
            i = 0;
            while (rsgIt.hasNext()) {
                aux = rsgIt.next();
                if (i++ % 2 == 0) {
                    ac1.add(aux);
                } else {
                    ac2.add(aux);
                }
            }
            assertTrue(ac1.retainAll(ac2));
            assertTrue(ac2.retainAll(ac1));
            assertTrue(ac1.isEmpty());
            assertEquals(ac1.size(), 0);
            assertTrue(ac2.isEmpty());
            assertEquals(ac2.size(), 0);
            addUp(1.0);
            passed();
        }
    }

    @Test(expected = NullPointerException.class)
    public void retainAllNullPointerTest() {
        Collection<String> ac;
        if (allowRetainAll) {
            startTest("Revisa que retainAll lance NullPointerException si el parámetro es null", 1.0, "*All");
            ac = getColeccionAbstracta();
            try {
                ac.retainAll(null);
            } catch (NullPointerException e) {
                addUp(1.0);
                passed();
                throw e;
            }
        } else {
            throw new NullPointerException();
        }
    }

    @Test
    public void retainAllItselfTest() {
        Collection<String> ac;
        if (allowRetainAll) {
            startTest("Revisa que retainAll mantenga la cantidad de elementos si el parámetro es la misma colección", 1.0, "*All");
            ac = getColeccionAbstracta();
            rsgIt = rsg.iterator();
            while (rsgIt.hasNext()) {
                ac.add(rsgIt.next());
            }
            assertFalse(ac.retainAll(ac));
            assertFalse(ac.isEmpty());
            assertEquals(ac.size(), range);
            addUp(1.0);
            passed();
        }
    }

    @Test
    public void retainAllEmptyTest() {
        Collection<String> ac1, ac2;
        if (allowRetainAll) {
            startTest("Revisa que se borren todos los elementos si el parámetro de retainAll es una colección vacía", 1.0, "*All");
            ac1 = getColeccionAbstracta();
            ac2 = getColeccionAbstracta();
            rsgIt = rsg.iterator();
            while (rsgIt.hasNext()) {
                ac1.add(rsgIt.next());
            }
            assertTrue(ac1.retainAll(ac2));
            assertTrue(ac1.isEmpty());
            assertEquals(ac1.size(), 0);
            addUp(1.0);
            passed();
        }
    }

    @Test(expected = UnsupportedOperationException.class)
    public void retainAllUnsupportedTest() {
        Collection<String> ac;
        if (!allowRetainAll) {
            startTest("Revise que retainAll lance UnsupportedOperationException si retainAll no se permite", 1.0, "*All");
            ac = getColeccionAbstracta();
            try {
                ac.retainAll(null);
            } catch (UnsupportedOperationException e) {
                addUp(1.0);
                passed();
                throw e;
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    //size

    @Test
    public void zeroSizeTest() {
        Collection<String> ac;
        startTest("Revisa que al inicializar la colección la cantidad de elementos es cero", 1.0, "Otros");
        ac = getColeccionAbstracta();
        assertEquals(ac.size(), 0);
        addUp(1.0);
        passed();
    }

    //toArray: Object[]

    @Test
    public void toObjectArrayContainsTest() {
        int i;
        Iterator<String> it;
        Object arr[];
        Collection<String> ac;
        startTest("Revisa que el orden de los elementos devueltos por toArray sea el mismo que el del iterador", 1.0, "Otros");
        ac = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            ac.add(rsgIt.next());
        }
        arr = ac.toArray();
        it = ac.iterator();
        i = 0;
        while (it.hasNext()) {
            if (arr[i] == null) {
                assertNull(it.next());
            } else {
                assertTrue(arr[i].equals(it.next()));
            }
            i++;
        }
        addUp(1.0);
        passed();
    }

    @Test
    public void toObjectArraySizeTest() {
        int i;
        Iterator<String> it;
        Object arr[];
        Collection<String> ac;
        startTest("Revisa que el número de elementos devueltos por toArray sea el mismo que el del iterador", 1.0, "Otros");
        ac = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            ac.add(rsgIt.next());
        }
        arr = ac.toArray();
        it = ac.iterator();
        i = 0;
        while (it.hasNext()) {
            it.next();
            i++;
        }
        assertEquals(arr.length, i);
        addUp(1.0);
        passed();
    }

    @Test
    public void toObjectArrayEmptyTest() {
        Iterator<String> it;
        Object arr[];
        Collection<String> ac;
        startTest("Revisa que el número de elementos de toArray sea cero si la colección es vacía", 1.0, "Otros");
        ac = getColeccionAbstracta();
        arr = ac.toArray();
        assertEquals(arr.length, 0);
        addUp(1.0);
        passed();
    }

    //toArray: E[]

    @Test
    public void toGenericArrayContainsTest() {
        int i;
        Iterator<String> it;
        String arr[];
        Collection<String> ac;
        startTest("Revisa que el orden de los elementos devueltos por toArray genérico sea el mismo que el del iterador", 1.0, "Otros");
        ac = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            ac.add(rsgIt.next());
        }
        arr = new String[range];
        arr = ac.toArray(arr);
        it = ac.iterator();
        i = 0;
        while (it.hasNext()) {
            if (arr[i] == null) {
                assertNull(it.next());
            } else {
                assertTrue(arr[i].equals(it.next()));
            }
            i++;
        }
        addUp(1.0);
        passed();
    }

    @Test
    public void toGenericArrayZeroSizeTest() {
        String arr[];
        Collection<String> ac;
        startTest("Revisa que el número de elementos de toArray genérico sea el mismo al de la colección cuando el arreglo pasado es de menor longitud", 1.0, "Otros");
        ac = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            ac.add(rsgIt.next());
        }
        arr = new String[rdm.nextInt(ac.size())];
        arr = ac.toArray(arr);
        assertEquals(arr.length, range);
        addUp(1.0);
        passed();
    }

    @Test
    public void toGenericArrayNullFillTest() {
        String arr[];
        Collection<String> ac;
        startTest("Revisa que toArray genérico devuelva elementos null cuando la longitud del arreglo es mayor al número de elementos en la colección", 1.0, "Otros");
        ac = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            ac.add(rsgIt.next());
        }
        arr = new String[range * 2];
        arr = ac.toArray(arr);
        for (int i = range; i < range * 2; i++) {
            assertNull(arr[i]);
        }
        addUp(1.0);
        passed();
    }

    @Test
    public void toGenericArraySizeTest() {
        int i;
        Iterator<String> it;
        String arr[];
        Collection<String> ac;
        startTest("Revisa que el número de elementos devueltos por toArray genérico sea el mismo que el del iterador", 1.0, "Otros");
        ac = getColeccionAbstracta();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            ac.add(rsgIt.next());
        }
        arr = new String[ac.size()];
        arr = ac.toArray(arr);
        it = ac.iterator();
        i = 0;
        while (it.hasNext()) {
            it.next();
            i++;
        }
        assertEquals(arr.length, i);
        addUp(1.0);
        passed();
    }

    @Test(expected = NullPointerException.class)
    public void toGenericArrayNullPTest() {
        String arr[];
        Collection<String> ac;
        startTest("Revisa que toArray genérico lance NullPointerException si el parámetro es null", 1.0, "Otros");
        ac = getColeccionAbstracta();
        String [] nullArray = null;
        try {
            arr = ac.toArray(nullArray);
        } catch (NullPointerException e) {
            addUp(1.0);
            passed();
            throw e;
        }
    }

    @Test
    public void toGenericArrayEmptyTest() {
        Iterator<String> it;
        String arr[];
        Collection<String> ac;
        startTest("Revisa que toArray genérico devuelva elementos null cuando la colección está vacía", 1.0, "Otros");
        ac = getColeccionAbstracta();
        arr = new String[range];
        arr = ac.toArray(arr);
        assertEquals(arr.length, range);
        for (int i = 0; i < arr.length; i++) {
            assertNull(arr[i]);
        }
        addUp(1.0);
        passed();
    }

}
