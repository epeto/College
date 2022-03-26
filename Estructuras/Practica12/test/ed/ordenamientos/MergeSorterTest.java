/*
 * Código utilizado para el curso de Estructuras de Datos.
 * Se permite consultarlo para fines didácticos en forma personal.
 */
package ed.ordenamientos;

/**
 * Crea objetos que usan merge sort.
 * @author blackzafiro
 */
public class MergeSorterTest extends OrdenadorSupertest {
	
	@Override
	protected <C extends Comparable <C>> IOrdenador<C> creaOrdenador() {
		return new MergeSorter<>();
	}
	
}
