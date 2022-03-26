/*
 * Código utilizado para el curso de Estructuras de Datos.
 * Se permite consultarlo para fines didácticos en forma personal.
 */
package ed.ordenamientos;

/**
 * Crea objetos que usan insertion sort.
 * @author blackzafiro
 */
public class InsertionSorterTest extends OrdenadorSupertest {
	
	@Override
	protected <C extends Comparable <C>> IOrdenador<C> creaOrdenador() {
		return new InsertionSorter<>();
	}
	
}
