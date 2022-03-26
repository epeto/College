/*
 * Código utilizado para el curso de Estructuras de Datos.
 * Se permite consultarlo para fines didácticos, pero no está permitido
 * entregarlo tal cual a los estudiantes.
 */
package ed.estructuras.nolineales;

import java.util.Iterator;

/**
 *
 * @author blackzafiro
 */
public interface ÁrbolBinario<E> extends Árbol<E> {
    @Override
    NodoBinario<E> getRaíz();
    Iterator<E> getIteradorInorden();
}
