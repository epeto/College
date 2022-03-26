package ed.estructuras.lineales;

/**
 * Clase que inicia el uso de pruebas unitarias para la clase Pila Ligada.
 *
 * @author mindahrelfen
 */
public class PilaLigadaTest extends PilaTestA {

    @Override
    protected IPila <String> getPila() {
        return new PilaLigada<>();
    }
}