/*
 * Código utilizado para el curso de Estructuras de Datos.
 * Se permite consultarlo para fines didácticos en forma personal,
 * pero no está permitido transferirlo tal cual a estudiantes actuales o potenciales.
 */
package ed.visualización.demos;

import ed.estructuras.nolineales.ÁrbolBOLigado;
import ed.estructuras.nolineales.ÁrbolBinarioOrdenado;
import ed.visualización.dibujantes.Dibujante;
import ed.visualización.dibujantes.DibujanteDeÁrbolBinario;

/**
 *
 * @author blackzafiro
 */
public class DemoÁrbolesBinariosOrdenados extends Demo {
	
	private DibujanteDeÁrbolBinario dibujante;
	
	private <C extends Comparable<C>> ÁrbolBinarioOrdenado<C> creaÁrbol() {
		return new ÁrbolBOLigado<>();
	}
	
	
	@MétodoDemo(nombre = "Árbol vacío", ejercicio = "[]")
	public Dibujante dibujaÁrbol0() {
        ÁrbolBinarioOrdenado<String> árbol = creaÁrbol();
		return new DibujanteDeÁrbolBinario(árbol);
    }
	
	@MétodoDemo(nombre = "Un elemento", ejercicio = "[A]")
    public Dibujante dibujaÁrbol1() {
        ÁrbolBinarioOrdenado<String> árbol = creaÁrbol();
		árbol.add("A");
        return new DibujanteDeÁrbolBinario(árbol);
    }
	
	@MétodoDemo(nombre = "Números", ejercicio = "[50, 20, 10, 5, 60]")
    public Dibujante dibujaÁrbolNúmeros() {
        ÁrbolBinarioOrdenado<Integer> árbol = creaÁrbol();
        árbol.add(50);
        árbol.add(20);
        árbol.add(10);
        árbol.add(5);
		árbol.add(60);
        return new DibujanteDeÁrbolBinario(árbol);
    }
	
	@MétodoDemo(nombre = "Árbol 1", ejercicio = "[B, A, D, C]")
    public Dibujante dibujaÁrbol2() {
        ÁrbolBinarioOrdenado<String> árbol = creaÁrbol();
        árbol.add("B");
        árbol.add("A");
        árbol.add("D");
        árbol.add("C");
        return new DibujanteDeÁrbolBinario(árbol);
    }
	
	@MétodoDemo(nombre = "Árbol 2", ejercicio = "[E, A, D, C, H, B, F, G]")
    public Dibujante dibujaÁrbol3() {
        ÁrbolBinarioOrdenado<String> árbol = creaÁrbol();
        árbol.add("E");
        árbol.add("A");
        árbol.add("D");
        árbol.add("C");
        árbol.add("H");
        árbol.add("B");
        árbol.add("F");
        árbol.add("G");
        return new DibujanteDeÁrbolBinario(árbol);
    }
	
	@MétodoDemo(nombre = "Remueve A de Árbol 2", ejercicio = "[E, A, D, C, H, B, F, G] - [A]")
    public Dibujante dibujaÁrbol4() {
        ÁrbolBinarioOrdenado<String> árbol = creaÁrbol();
        árbol.add("E");
        árbol.add("A");
        árbol.add("D");
        árbol.add("C");
        árbol.add("H");
        árbol.add("B");
        árbol.add("F");
        árbol.add("G");
        árbol.remove("A");
        return new DibujanteDeÁrbolBinario(árbol);
    }
	
	@MétodoDemo(nombre = "Remueve A y E de Árbol 2", ejercicio = "[E, A, D, C, H, B, F, G] - [A, E]")
    public Dibujante dibujaÁrbol5() {
        ÁrbolBinarioOrdenado<String> árbol = creaÁrbol();
        árbol.add("E");
        árbol.add("A");
        árbol.add("D");
        árbol.add("C");
        árbol.add("H");
        árbol.add("B");
        árbol.add("F");
        árbol.add("G");
        árbol.remove("A");
        árbol.remove("E");
        return new DibujanteDeÁrbolBinario(árbol);
    }
    
    @MétodoDemo(nombre = "Remueve G de Árbol 2", ejercicio = "[E, A, D, C, H, B, F, G] - [G]")
    public Dibujante dibujaÁrbol6() {
        ÁrbolBinarioOrdenado<String> árbol = creaÁrbol();
        árbol.add("E");
        árbol.add("A");
        árbol.add("D");
        árbol.add("C");
        árbol.add("H");
        árbol.add("B");
        árbol.add("F");
        árbol.add("G");
        árbol.remove("G");
        return new DibujanteDeÁrbolBinario(árbol);
    }
}
