/*
 * Código utilizado para el curso de Estructuras de Datos.
 * Se permite consultarlo para fines didácticos en forma personal.
 */
package ed.estructuras.nolineales;

import java.util.List;

/**
 * Nodo para un árbol cuyos subárboles tienen un órden.
 * @author veronica
 */
public interface Nodo<E> {
    E getElemento();
    void setElemento(E dato);
    Nodo<E> getPadre();
    void setPadre(Nodo<E> padre);
	
	/**
	 * Indica si este nodo no tiene hijos o
	 * todos sus hijos son árboles vacíos.
	 */
    boolean esHoja();
	
    /** Devuelve el altura del subárbol que tiene este nodo por raíz. */
    int getAltura();
    
    /**
     * Devuelve al hijo en la posición <code>índice</code>.
     * @param índice
     * @return 
     */
    Nodo<E> getHijo(int índice) throws IndexOutOfBoundsException;
    
	/**
	 * Devuelve el número de hijos que tiene este nodo.
	 * @return 
	 */
    int getGrado();
    
    /**
     * Devuelve una lista con todos los hijos de este nodo.
     * Cualquier alteración a la lista devuelta no debe reflejar una alteración
     * a los hijos del nodo.
     * @return hijos de este nodo.
     */
    List<Nodo<E>> getListaHijos();    
}
