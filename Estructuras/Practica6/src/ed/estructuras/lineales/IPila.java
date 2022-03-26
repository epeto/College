/*
 * Código utilizado para el curso de Estructuras de Datos. Se permite
 * consultarlo para fines didácticos en forma personal, pero no está permitido
 * transferirlo tal cual a estudiantes actuales o potenciales pues se afectará
 * su realización de los ejercicios.
 */

package ed.estructuras.lineales;

import java.util.Collection;

/**
 * Estructura "Último en entrar, primero en salir".
 *
 * @author veronica
 */
public interface IPila<E> extends Collection<E> {

    /**
     * Muestra el elemento al tope de la pila.
     * Devuelve <code>null</code> si está vacía.
     *
     * @return Una referencia al elemento siguiente.
     */
    public E mira();

    /**
     * Devuelve el elemento al tope de la pila y lo elimina.
     * Devuelve <code>null</code> si está vacía.
     *
     * @return Una referencia al elemento siguiente.
     */
    public E expulsa();

    /**
     * Agrega un elemento al tope de la pila.
     *
     * @param e Referencia al elemento a agregar.
     *          Si <code>e</code> es <code>null</code> lanza
     *          <code>NullPointerException</code>.
     */
    public void empuja(E e);
}
