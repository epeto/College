package ed.estructuras.lineales;

import java.util.Collection;

/**
 * Clase que inicia el uso de pruebas unitarias para la clase Cola Arreglo.
 *
 * @author mindahrelfen
 */
public class ColaArregloTest extends ColaTestA {

    @Override
    public void init() {
        allowRemove = allowRemoveAll = allowRetainAll = allowIteratorRemove = false;
        allowDifferentConstructor = true;
    }

    @Override
    protected ICola <String> getCola() {
        return new ColaArreglo<>(new String[0]);
    }

    @Override
    protected Collection <String> getColeccionAbstracta(String[] array){
        return new ColaArreglo<>(array);
    }

    @Override
    protected Collection <String> getColeccionAbstracta(String[] array, int range){
        return new ColaArreglo<>(array, range);
    }
}