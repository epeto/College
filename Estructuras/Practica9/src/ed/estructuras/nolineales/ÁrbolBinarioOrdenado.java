/*
 * Código utilizado para el curso de Estructuras de Datos.
 * Se permite consultarlo para fines didácticos, pero no está permitido
 * entregarlo tal cual a los estudiantes.
 */
package ed.estructuras.nolineales;

/**
 * @author blackzafiro
 * @param <C> Tipo de los datos almacenado en este árbol, debe poderse definir
 * una relación de orden entre ellos.
 */
public interface ÁrbolBinarioOrdenado<C extends Comparable<C>> extends ÁrbolBinario<C>{
    /** Sobreescribir los iteradores para que implementen remove. */
    
    /**
     * Indica si el objeto comparable <code>o</code> se encuentra en este árbol.
     * La complejidad de este método es log(n) en promedio.
     * @param o
     * @return si el objeto se encuentra en el árbol.
     * @throws NullPointerException si <code>o</code> es <code>null</code>.
     */
    public boolean contains(C o) throws NullPointerException;
    
    /**
     * Remueve el objeto comparable <code>o</code>.
     * La complejidad de este método es log(n) en promedio.
     * @param o el objeto a remover
     * @return si el objeto estuvo presente y lo removió
     * @throws NullPointerException si <code>o</code> es <code>null</code>.
     */
    public boolean remove(C o) throws NullPointerException;
}
