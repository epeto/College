/*
 * Código utilizado para el curso de Estructuras de Datos. Se permite
 * consultarlo para fines didácticos en forma personal, pero no está permitido
 * transferirlo tal cual a estudiantes actuales o potenciales pues se afectará
 * su realización de los ejercicios.
 */
 
package ed.estructuras.lineales;

import java.util.Collection;

/**
 * Estructura "Primero en entrar, primero en salir".
 *
 * @author veronica
 */
public interface ICola<E> extends Collection<E> {

    /**
     * Muestra el elemento al inicio de la cola.
     * Devuelve <code>null</code> si está vacía.
     *
     * @return Una referencia al elemento siguiente.
     */
    public E mira();

    /**
     * Devuelve el elemento al inicio de la cola y lo elimina.
     * Devuelve <code>null</code> si está vacía.
     *
     * @return Una referencia al elemento siguiente.
     */
    public E atiende();

    /**
     * Agrega un elemento al final de la cola.
     *
     * @param e Referencia al elemento a agregar.
     */
    public void forma(E e);
}