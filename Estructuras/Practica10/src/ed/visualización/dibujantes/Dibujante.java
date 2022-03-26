/*
 * Código utilizado para el curso de Estructuras de Datos.
 * Se permite consultarlo para fines didácticos en forma personal,
 * pero no está permitido transferirlo tal cual a estudiantes actuales o potenciales.
 */
package ed.visualización.dibujantes;

import java.util.Collection;
import javafx.scene.Node;

/**
 * Clase encargada de dibujar una colección y resincronizar el dibujo si ésta
 * es modificada.
 * @author blackzafiro
 */
public abstract class Dibujante {
	
	/**
	 * Estructura de datos a dibujar.
	 */
	protected final Collection<?> estructura;
	
	/**
	 * Asigna la estructura a dibujar.
	 * @param c la estructura a dibujar.
	 */
	public Dibujante(Collection<?> c) {
		if (c == null) throw new IllegalArgumentException("La estructura no puede ser null");
		estructura = c;
	}
	
	/**
	 * Devuelve una referencia al nodo raíz de la gráfica dibujada.
	 * @return su nodo raíz.
	 */
	public abstract Node gráfica();
	
	/**
	 * Crea o sincroniza el dibujo con la estructura, en caso de que ésta
	 * haya sido modificada.
	 * @return su nodo raíz.
	 */
    public abstract Node sincronizaGráfica();
}
