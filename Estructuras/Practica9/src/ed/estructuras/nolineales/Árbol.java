/*
 * Código utilizado para el curso de Estructuras de Datos.
 * Se permite consultarlo para fines didácticos en forma personal.
 */
package ed.estructuras.nolineales;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author veronica
 */
public interface Árbol<E> extends Collection<E> {
    
    Nodo<E> getRaíz();
    Iterator<E> getIteradorPreorden();
    Iterator<E> getIteradorPostorden();
    
    /**
     * Devuelve una lista con los datos en este árbol, según son visitados en un
     * recorrido postorden.
     * @return 
     */
    List<E> recorridoPostorden();
    
    /**
     * Devuelve el altura del árbol.  Si el árbol es vacío devuelve -1.
     * @return altura del árbol.
     */
    int getAltura();
}
