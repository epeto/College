/*
 * Código utilizado para el curso de Estructuras de Datos.
 * Se permite consultarlo para fines didácticos en forma personal.
 */
package ed.ordenamientos;

/**
 * Crea objetos que usan bubble sort.
 * @author blackzafiro
 */
public class BubbleSorterTest extends OrdenadorSupertest {

	@Override
	protected <C extends Comparable <C>> IOrdenador<C> creaOrdenador() {
		return new BubbleSorter<>();
	}
	
}
