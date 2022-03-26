/*
 * Código utilizado para el curso de Estructuras de Datos.
 * Se permite consultarlo para fines didácticos en forma personal,
 * pero no está permitido transferirlo tal cual a estudiantes actuales o potenciales.
 */
package ed.estructuras.nolineales;

/**
 *
 * @author blackzafiro
 */
public interface NodoBinario<E> extends Nodo<E>{

    @Override
    NodoBinario<E> getPadre();
    NodoBinario<E> getHijoI();
    NodoBinario<E> getHijoD();
    
    void setHijoI(NodoBinario<E> izquierdo);
    void setHijoD(NodoBinario<E> derecho);
}
