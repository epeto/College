/**
 * Código utilizado para el curso de Estructuras de Datos.
 *
 * Se permite consultarlo para fines didácticos de forma personal, pero no está
 * permitido transferirlo tal cual a estudiantes actuales o potenciales pues se
 * afectará su realización de los ejercicios.
 */

package ed.estructuras.lineales;

import java.util.List;

/**
 * Clase que inicia el uso de pruebas unitarias para la clase Lista Doblemente
 * Ligada.
 *
 * @author mindahrelfen
 */
public class ListaDoblementeLigadaTest extends ListaTestA {

    @Override
    protected List<String> getLista() {
        return new ListaDoblementeLigada<>();
    }
}