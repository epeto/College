/**
 * Código utilizado para el curso de Estructuras de Datos.
 *
 * Se permite consultarlo para fines didácticos de forma personal, pero no está
 * permitido transferirlo tal cual a estudiantes actuales o potenciales pues se
 * afectará su realización de los ejercicios.
 */

package ed.estructuras.lineales;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.junit.Test;
import static org.junit.Assert.*;

import ed.estructuras.ColeccionAbstractaTestA;

/**
 * Clase que agrega pruebas unitarias para las clases hijas de la clase List.
 *
 * @author mindahrelfen
 */
public abstract class ListaTestA extends ColeccionAbstractaTestA {

    @Override
    protected Collection<String> getColeccionAbstracta() {
        return getLista();
    }

    /**
     * Devuelve una lista bien construida.
     *
     * @return List Una lista de tipo String vacía.
     */
    protected abstract List<String> getLista();

    //add

    @Test
    public void listaddContainsTest() {
        List<String> structure;

        startTest("Revisa que la estructura contenga los elementos insertados con add(int, E)", 1.0, "Insercion");

        /**
         * Inserta elementos en la estructura.
         */
        structure = getLista();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            structure.add(rdm.nextInt(structure.size() + 1), rsgIt.next());
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
    public void listaddSizeTest() {
        int index;
        List<String> structure;

        startTest("Revisa que la estructura mantenga la cantidad correcta de elementos, ademas de no estar vacía tras insertar con add(int, E)", 1.0, "Insercion");

        /**
         * Inserta elementos en la estructura de uno en uno y revisa que cada
         * inserción mantenga el tamaño deseado. Además, revisa que ninguna
         * inserción vuelva vacía la estructura.
         */
        structure = getLista();
        rsgIt = rsg.iterator();
        index = 1;
        while (rsgIt.hasNext()) {
            structure.add(rdm.nextInt(structure.size() + 1), rsgIt.next());
            assertEquals(structure.size(), index++);
            assertFalse(structure.isEmpty());
        }

        addUp(1.0);
        passed();
    }

    @Test
    public void listaddIndexOutOfBoundsTest() {
        boolean flag;
        List<String> structure;

        startTest("Revisa que se lance IndexOutOfBoundsException si el parámetro int de add(int, E) esta fuera de rango", 1.0, "Insercion");

        structure = getLista();
        structure.add(0, new String());
        flag = false;

        try {
            structure.add(-1, new String());
        } catch (IndexOutOfBoundsException e) {
            addUp(0.5);
            flag = true;
        }

        try {
            structure.add(structure.size() + 1, new String());
        } catch (IndexOutOfBoundsException e) {
            addUp(0.5);
            if (flag) passed();
        }
    }

    //addAll

    @Test
    public void listaddAllContainsTest() {
        List<String> structure, aux;

        startTest("Revisa que la estructura contenga todos los elementos insertados por addAll(int, Collection<?>)", 1.0, "*All");

        /**
         * Agrega elementos en una estructura auxiliar.
         */
        aux = getLista();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            aux.add(rsgIt.next());
        }

        /**
         * Usando addAll inserta todos los elementos de la estructura auxiliar
         * en la estructura principal.
         */
        structure = getLista();
        assertTrue(structure.addAll(0, aux));

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
    public void listaddAllSizeTest() {
        List<String> structure, aux;

        startTest("Revisa que la estructura contenga la cantidad esperada de elementos insertados por addAll(int, Collection<?>)", 1.0, "*All");

        /**
         * Agrega elementos en una estructura auxiliar.
         */
        aux = getLista();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            aux.add(rsgIt.next());
        }

        /**
         * Usando addAll inserta todos los elementos de la estructura auxiliar
         * en la estructura principal.
         */
        structure = getLista();
        assertTrue(structure.addAll(0, aux));

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
    public void listaddAllNullPointerTest() {
        List<String> structure;

        startTest("Revisa que se lance NullPointerException si el parámetro Collection<?> en addAll(int, Collection<?>) es null", 1.0, "*All");

        structure = getLista();

        try {
            structure.addAll(0, null);
        } catch (NullPointerException e) {
            addUp(1.0);
            passed();
        }
    }

    @Test
    public void listaddAllIllegalArgumentTest() {
        List<String> structure;

        startTest("Revisa que se lance IllegalArgumentException si el parámetro Collection<?> en addAll(int, Collection<?>) es la misma colección", 1.0, "*All");

        structure = getLista();

        try {
            structure.addAll(0, structure);
        } catch (IllegalArgumentException e) {
            addUp(1.0);
            passed();
        }
    }

    @Test
    public void listaddAllIndexOutOfBoundsTest() {
        boolean flag;
        List<String> structure, aux;

        startTest("Revisa que se lance IndexOutOfBoundsException si el parámetro int en addAll(int, Collection<?>) esta fuera de rango", 1.0, "*All");

        structure = getLista();
        structure.add(new String());
        aux = getLista();
        aux.add(new String());
        flag = false;

        try {
            structure.addAll(-1, aux);
        } catch (IndexOutOfBoundsException e) {
            addUp(0.5);
            flag = true;
        }

        try {
            structure.addAll(structure.size() + 1, aux);
        } catch (IndexOutOfBoundsException e) {
            addUp(0.5);
            if (flag) passed();
        }
    }

    //get

    @Test
    public void getContainsTest() {
        String str;
        List<String> structure;

        startTest("Revisa que get(int) devuelva el elemento correcto con respecto a indexOf(E)", 1.0, "Busqueda");

        /**
         * Agrega elementos a la estructura.
         */
        structure = getLista();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            structure.add(rsgIt.next());
        }

        /**
         * Revisa que el elemento devuelto por get corresponda al índice de
         * indexof.
         */
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            str = rsgIt.next();
            assertEquals(str, structure.get(structure.indexOf(str)));
        }

        addUp(1.0);
        passed();
    }

    @Test
    public void getIndexOutOfBoundsTest() {
        boolean flag;
        List<String> structure;

        startTest("Revisa que se lance IndexOutOfBoundsException si el parámetro en get(int) esta fuera de rango", 1.0, "Busqueda");

        structure = getLista();
        structure.add(new String());
        flag = false;

        try {
            structure.get(-1);
        } catch (IndexOutOfBoundsException e) {
            addUp(0.5);
            flag = true;
        }

        try {
            structure.get(structure.size());
        } catch (IndexOutOfBoundsException e) {
            addUp(0.5);
            if (flag) passed();
        }
    }

    //indexOf

    @Test
    public void indexOfNoElementTest() {
        List<String> structure;

        startTest("Revisa que indexOf(E) devuelva -1 cuando el elemento dado no esta en la estructura", 1.0, "Busqueda");

        /**
         * Busca un elemento que no este en la estructura, pues la estructura
         * esta vacía, su índice debe ser -1.
         */
        structure = getLista();
        assertEquals(-1, structure.indexOf(new String()));

        /**
         * Agrega elementos a la estructura.
         */
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            structure.add(rsgIt.next());
        }

        /**
         * Busca un elemento que no este en la estructura.
         */
        assertEquals(-1, structure.indexOf(new String()));

        addUp(1.0);
        passed();
    }

    //lastIndexOf

    @Test
    public void lastIndexOfNoElementTest() {
        List<String> structure;

        startTest("Revisa que lastIndexOf(E) devuelva -1 cuando el elemento dado no esta en la estructura", 1.0, "Busqueda");

        /**
         * Busca un elemento que no este en la estructura, pues la estructura
         * esta vacía, su índice debe ser -1.
         */
        structure = getLista();
        assertEquals(-1, structure.lastIndexOf(new String()));

        /**
         * Agrega elementos a la estructura.
         */
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            structure.add(rsgIt.next());
        }

        /**
         * Busca un elemento que no este en la estructura.
         */
        assertEquals(-1, structure.lastIndexOf(new String()));

        addUp(1.0);
        passed();
    }

    //listIterator

    @Test
    public void listIteratorIndexConstructorTest() {
        int index;
        String str;
        List<String> structure;
        ListIterator<String> it;

        startTest("Revisa que listIterator(int) construya al iterador en la posición correcta", 1.0, "Busqueda");

        /**
         * Agrega elementos a la estructura.
         */
        structure = getLista();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            structure.add(rsgIt.next());
        }

        /**
         * Obtiene una posición aleatoria, luego con get obtiene el elemento
         * dentro de la estructura en dicha posición.
         */
        index = rdm.nextInt(structure.size());
        str = structure.get(index);

        /**
         * Crea un iterador en la misma posición, y el elemento devuelto por el
         * next debe de ser el mismo que el obtenido por el get.
         */
        it = structure.listIterator(index);
        assertEquals(str, it.next());

        addUp(1.0);
        passed();
    }

    @Test
    public void listIteratoraddContainsTest() {
        int index;
        List<String> structure;
        ListIterator<String> it;

        startTest("Revisa que la estructura contenga los elementos insertados con add() de List Iterator", 1.0, "Insercion");

        /**
         * Inserta elementos en la estructura.
         */
        structure = getLista();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            index = rdm.nextInt(structure.size() + 1);
            it = structure.listIterator(index);
            it.add(rsgIt.next());
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
    public void listiteratoraddSizeTest() {
        int index;
        List<String> structure;
        ListIterator<String> it;

        startTest("Revisa que la estructura mantenga la cantidad correcta de elementos, ademas de no estar vacía tras insertar con add() de List Iterator", 1.0, "Insercion");

        /**
         * Inserta elementos en la estructura de uno en uno y revisa que cada
         * inserción mantenga el tamaño deseado. Además, revisa que ninguna
         * inserción vuelva vacía la estructura.
         */
        structure = getLista();
        rsgIt = rsg.iterator();
        index = 1;
        while (rsgIt.hasNext()) {
            it = structure.listIterator(rdm.nextInt(structure.size() + 1));
            it.add(rsgIt.next());
            assertEquals(structure.size(), index++);
            assertFalse(structure.isEmpty());
        }

        addUp(1.0);
        passed();
    }

    @Test
    public void listIteratoraddOrderTest() {
        int index;
        String str;
        String[] array1, array2;
        List<String> structure;
        ListIterator<String> it;

        startTest("Revisa que la estructura mantenga el orden correcto de elementos tras insertar con add() de List Iterator", 1.0, "Insercion");

        /**
         * Inserta elementos en la estructura, también en un arreglo.
         */
        structure = getLista();
        array1 = new String[range];
        rsgIt = rsg.iterator();
        it = structure.listIterator();
        index = 0;
        while (rsgIt.hasNext()) {
            str = rsgIt.next();
            it.add(str);
            array1[index++] = str;
        }

        /**
         * Obtiene el arreglo equivalente a la estructura.
         */
        array2 = new String[structure.size()];
        it = structure.listIterator();
        index = 0;
        while (it.hasNext()) {
            array2[index++] = it.next();
        }

        /**
         * Revisa la igualdad entre arreglos.
         */
        assertTrue(equals(array1, array2));

        addUp(1.0);
        passed();
    }

    @Test
    public void listIteratorsetContainsTest() {
        int index;
        List<String> structure;
        ListIterator<String> it;

        startTest("Revisa que la estructura contenga los elementos insertados con set(E) de List Iterator", 1.0, "Busqueda");

        /**
         * Agrega elementos a la estructura.
         */
        structure = getLista();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            structure.add(rsgIt.next());
        }

        /**
         * Obtiene una posición aleatoria, después crea un iterador en esa
         * posición, y luego lo modifica a través de next y set.
         */
        index = rdm.nextInt(structure.size());
        it = structure.listIterator(index);
        it.next();
        it.set(new String());

        /**
         * Obtiene el elemento en la misma posición y lo compara, debe igual al
         * elemento insertado.
         */
        assertEquals(structure.get(index), new String());

        addUp(1.0);
        passed();
    }

    //listIterator(int)

    @Test
    public void listIteratorIndexOutOfBoundsTest() {
        boolean flag;
        List<String> structure;

        startTest("Revisa que se lance IndexOutOfBoundsException si el parámetro en listIterator(int) esta fuera de rango", 1.0, "Busqueda");

        structure = getLista();
        structure.add(new String());
        flag = false;

        try {
            structure.listIterator(-1);
        } catch (IndexOutOfBoundsException e) {
            addUp(0.5);
            flag = true;
        }

        try {
            structure.listIterator(structure.size() + 1);
        } catch (IndexOutOfBoundsException e) {
            addUp(0.5);
            if (flag) passed();
        }
    }

    @Test
    public void listIteratorNoElementsTest() {
        boolean flag;
        List<String> structure;
        ListIterator<String> it;

        startTest("Revisa que se lance NoSuchElementException en next() y previous() si no existe ese elemento", 1.0, "Busqueda");

        structure = getLista();
        it = structure.listIterator();
        flag = false;

        try {
            it.next();
        } catch (NoSuchElementException e) {
            addUp(0.25);
            flag = true;
        }

        try {
            it.previous();
            flag = false;
        } catch (NoSuchElementException e) {
            addUp(0.25);
        }

        structure.add(new String());

        it = structure.listIterator(0);
        try {
            it.previous();
            flag = false;
        } catch (NoSuchElementException e) {
            addUp(0.25);
        }

        it = structure.listIterator(structure.size());
        try {
            it.next();
        } catch (NoSuchElementException e) {
            addUp(0.25);
            if (flag) passed();
        }
    }

    @Test
    public void listIteratorIllegalStateTest() {
        boolean flag;
        List<String> structure;
        ListIterator<String> it;

        startTest("Revisa que se lance IllegalStateException cuando se invoca set(E) o remove de List Iterator si no se invoca next() o previous() primero", 1.0, "Busqueda");

        structure = getLista();
        it = structure.listIterator();
        flag = false;

        try {
            it.remove();
        } catch (IllegalStateException e) {
            addUp(0.5);
            flag = true;
        }

        try {
            it.set(null);
        } catch (IllegalStateException e) {
            addUp(0.5);
            if (flag) passed();
        }
    }

    @Test
    public void listIteratorIllegalStateAddRemoveTest() {
        boolean flag;
        List<String> structure;
        ListIterator<String> it;

        startTest("Revisa que se lance IllegalStateException cuando se invoca remove() de List Iterator si no se invoca next() o previous() tras invocar add(E) o remove() de List Iterator", 1.0, "Busqueda");

        structure = getLista();
        structure.add(new String());
        it = structure.listIterator(0);
        flag = false;

        try {
            it.next();
            it.remove();
            it.remove();
        } catch (IllegalStateException e) {
            addUp(0.5);
            flag = true;
        }

        try {
            it.add(new String());
            it.remove();
        } catch (IllegalStateException e) {
            addUp(0.5);
            if (flag) passed();
        }
    }

    @Test
    public void listIteratorIllegalStateSetRemoveTest() {
        boolean flag;
        List<String> structure;
        ListIterator<String> it;

        startTest("Revisa que se lance IllegalStateException cuando se invoca set(E) de List Iterator si no se invoca next() o previous() tras invocar add(E) o remove() de List Iterator", 1.0, "Busqueda");

        structure = getLista();
        structure.add(new String());
        it = structure.listIterator(0);
        flag = false;

        try {
            it.next();
            it.remove();
            it.set(null);
        } catch (IllegalStateException e) {
            addUp(0.5);
            flag = true;
        }

        try {
            System.out.println("Antes de agregar.");
            it.add(new String());
            System.out.println("A punto de lanzar excepción.");
            it.set(null);
            System.out.println("No se lanzó excepción.");
        } catch (IllegalStateException e) {
            System.out.println("Segunda prueba pasada");
            addUp(0.5);
            if (flag) passed();
        }
    }

    //remove(int)

    @Test
    public void listremoveContainsTest() {
        List<String> structure;

        if (allowRemove) {
            startTest("Prueba que la estructura no contenga elementos después de borrarlos con remove(int)", 1.0, "Borrado");

            /**
             * Inserta elementos en la estructura.
             */
            structure = getLista();
            rsgIt = rsg.iterator();
            while (rsgIt.hasNext()) {
                structure.add(rsgIt.next());
            }

            /**
             * Luego los expulsa hasta que la estructura este vacía.
             */
            rsgIt = rsg.iterator();
            while (!structure.isEmpty()) {
                structure.remove(rdm.nextInt(structure.size()));
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
    public void listremoveSizeTest() {
        int index;
        List<String> structure;

        if (allowRemove) {
            startTest("Revisa que la cantidad de elementos tras borrar sea consistente con remove(int)", 1.0, "Borrado");

            /**
             * Inserta elementos en la estructura.
             */
            structure = getLista();
            rsgIt = rsg.iterator();
            while (rsgIt.hasNext()) {
                structure.add(rsgIt.next());
            }

            /**
             * Borra los elementos de la estructura de uno en uno y revisa que
             * cada borrado mantenga el tamaño deseado.
             */
            index = 1;
            while (index <= range) {
                structure.remove(rdm.nextInt(structure.size()));
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
    public void removeIndexOutOfBoundsTest() {
        boolean flag;
        List<String> structure;

        if (allowRemove) {
            startTest("Revisa que se lance IndexOutOfBoundsException si el parámetro de remove(int) esta fuera de rango", 1.0, "Borrado");

            structure = getLista();
            structure.add(new String());
            flag = false;

            try {
                structure.remove(-1);
            } catch (IndexOutOfBoundsException e) {
                addUp(0.5);
                flag = true;
            }

            try {
                structure.remove(structure.size());
            } catch (IndexOutOfBoundsException e) {
                addUp(0.5);
                if (flag) passed();
            }
        }
    }

    //set

    @Test
    public void setIndexOutOfBoundsTest() {
        boolean flag;
        List<String> structure;

        startTest("Revisa que se lance IndexOutOfBoundsException si el parámetro int en set(int, E) esta fuera de rango", 1.0, "Busqueda");

        structure = getLista();
        flag = false;

        try {
            structure.set(-1, new String());
        } catch (IndexOutOfBoundsException e) {
            addUp(0.5);
            flag = true;
        }

        structure.add(new String());
        try {
            structure.set(structure.size(),new String());
        } catch (IndexOutOfBoundsException e) {
            addUp(0.5);
            if (flag) passed();
        }
    }

    @Test
    public void listsetContainsTest() {
        int index;
        List<String> structure;

        startTest("Revisa que la estructura contenga los elementos insertados con set(int, E)", 1.0, "Busqueda");

        /**
         * Agrega elementos a la estructura.
         */
        structure = getLista();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            structure.add(rsgIt.next());
        }

        /**
         * En un índice dado modifica a la estructura.
         */
        index = rdm.nextInt(structure.size());
        structure.set(index, new String());

        /**
         * Revisa que el elemento se haya modificado correctamente.
         */
        assertEquals(structure.get(index), new String());

        addUp(1.0);
        passed();
    }
}