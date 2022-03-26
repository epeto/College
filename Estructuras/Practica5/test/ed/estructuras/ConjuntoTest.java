/*
 * Código utilizado para el curso de Estructuras de Datos. Se permite
 * consultarlo para fines didácticos en forma personal, pero no está permitido
 * transferirlo tal cual a estudiantes actuales o potenciales pues se afectará
 * su realización de los ejercicios.
 */

package ed.estructuras;

import java.util.Collection;

/**
 * Clase que inicia el uso de pruebas unitarias para la clase Conjunto.
 *
 * @author mindahrelfen
 */
public class ConjuntoTest extends ColeccionAbstractaTestA {

    @Override
    protected Collection<String> getColeccionAbstracta() {
        return new Conjunto<> ();
    }
}