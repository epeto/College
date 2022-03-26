package ed.estructuras.lineales;

import java.util.Collection;
import java.util.LinkedList;

import org.junit.Test;
import static org.junit.Assert.*;

import ed.estructuras.ColeccionAbstractaTestA;

/**
 * Clase que agrega pruebas unitarias para las clases hijas de la clase ICola.
 * 
 * @author mindahrelfen
 */
public abstract class ColaTestA extends ColeccionAbstractaTestA {

    @Override
    public void init() {
        allowRemove = allowRemoveAll = allowRetainAll = allowIteratorRemove = false;
        allowDifferentConstructor = false;
    }

    @Override
    protected Collection <String> getColeccionAbstracta() {
        return getCola();
    }

    /**
     * Devuelve una Cola bien construida.
     * @return ICola Una cola de tipo String vacía.
     */
    protected abstract ICola <String> getCola();

    //atiende

    @Test
    public void atiendeContainsTest() {
        ICola <String> cola;
        startTest("Prueba que la cola no contenga elementos borrados con atiende", 1.0, "Borrado");
        cola = getCola();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            cola.forma(rsgIt.next());
        }
        while (!cola.isEmpty()) {
            cola.atiende();
        }
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            assertFalse(cola.contains(rsgIt.next()));
        }
        addUp(1.0);
        passed();
    }

    @Test
    public void atiendeMiraTest() {
        String s;
        ICola <String> cola;
        startTest("Prueba que mira y atiende devuelvan el mismo valor", 1.0, "Borrado");
        cola = getCola();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            cola.forma(rsgIt.next());
        }
        while (!cola.isEmpty()) {
            s = cola.mira();
            if (s == null) {
                assertNull(cola.atiende());
            } else {
                assertTrue(s.equals(cola.atiende()));
            }
        }
        addUp(1.0);
        passed();
    }

    @Test
    public void atiendeSizeTest() {
        int i;
        ICola <String> cola;
        startTest("Revisa que la cantidad de elementos tras invocar atiende sean consistente", 1.0, "Borrado");
        cola = getCola();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            cola.forma(rsgIt.next());
        }
        i = 1;
        while (i <= range) {
            cola.atiende();
            assertEquals(cola.size(), range - i++);
        }
        assertTrue(cola.isEmpty());
        addUp(1.0);
        passed();
    }

    @Test
    public void atiendeEmptyTest() {
        ICola <String> cola;
        startTest("Revisa que se devuelva null cuando se invoca atiende y no hay elementos", 1.0, "Borrado");
        cola = getCola();
        assertNull(cola.atiende());
        addUp(1.0);
        passed();
    }

    //forma

    @Test
    public void formaContainsTest() {
        ICola <String> cola;
        startTest("Revisa si la cola contiene los elementos insertados por forma", 1.0, "Insercion");
        cola = getCola();
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            cola.forma(rsgIt.next());
        }
        rsgIt = rsg.iterator();
        while (rsgIt.hasNext()) {
            assertTrue(cola.contains(rsgIt.next()));
        }
        addUp(1.0);
        passed();
    }

    @Test
    public void formaSizeTest() {
        int i;
        ICola <String> cola;
        startTest("Revisa que la cola mantenga la cantidad correcta y no vacía de elementos tras insertar con forma", 1.0, "Insercion");
        cola = getCola();
        rsgIt = rsg.iterator();
        i = 1;
        while (rsgIt.hasNext()) {
            cola.forma(rsgIt.next());
            assertEquals(cola.size(), i++);
            assertFalse(cola.isEmpty());
        }
        addUp(1.0);
        passed();
    }

    //FIFO

    @Test
    public void FIFOTest() {
        int i;
        String s;
        ICola <String> cola;
        LinkedList <String> l;
        startTest("Revisa que la cola sea una estructura FIFO", 1.0, "Otros");
        cola = getCola();
        l = new LinkedList<>();
        rsgIt = rsg.iterator();
        i = 1;
        while (rsgIt.hasNext()) {
            s = rsgIt.next();
            cola.forma(s);
            l.addFirst(s);
        }
        while (!cola.isEmpty()) {
            s = cola.atiende();
            if (s == null) {
                assertNull(l.removeLast());
            } else {
                assertTrue(s.equals(l.removeLast()));
            }
        }
        addUp(1.0);
        passed();
    }
}