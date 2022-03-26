/*
 * Codigo utilizado para el curso de Estructuras de Datos.
 * Se permite consultarlo para fines didacticos en forma personal.
 */
package ed.ordenamientos;

/**
 * Interfaz que representa las acciones que debe realizar cualquier objeto
 * capaz de ordenar los elementos en un arreglo.
 * @author blackzafiro
 */
public interface IOrdenador<C extends Comparable<C>> {
	
	/**
	 * Crea un arreglo nuevo con los elementos ordenados.
	 * @param a El arreglo cuyos elementos se quieren ordenar.
	 * @return Un arreglo nuevo, del mismo tipo de <code>a</code> pero con los
	 *         elementos en el orden dictado por <code>compareTo</code>.
	 */
	C[] ordena(C[] a);
	
	/**
	 * Devuelve un arreglo de enteros de tal manera que, si es ordenado con
	 * un objeto de esta clase, será el peor caso para la complejidad en tiempo.
	 * @param tam La longitud del arreglo a generar.
	 * @return Arreglo con el peor caso para el algoritmo de ordenamiento
	 *         implementado.
	 */
	int[] peorCaso(int tam);
	
	/**
	 * Intercambia indicadas en el arreglo los elementos en las posiciones.
	 * @param a
	 * @param i
	 * @param j 
	 */
	default void swap(C[] a, int i, int j) {
		C temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
}

