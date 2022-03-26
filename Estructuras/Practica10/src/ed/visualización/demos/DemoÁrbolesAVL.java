/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.visualización.demos;

import ed.estructuras.nolineales.ÁrbolAVL;
import ed.estructuras.nolineales.ÁrbolBinarioOrdenado;
import ed.visualización.dibujantes.Dibujante;
import ed.visualización.dibujantes.DibujanteDeÁrbolBinario;

/**
 *
 * @author blackzafiro
 */
public class DemoÁrbolesAVL extends DemoÁrbolesBinariosOrdenados {
	
	@Override
	protected <C extends Comparable<C>> ÁrbolAVL<C> creaÁrbol() {
		return new ÁrbolAVL<>();
	}
    
    @MétodoDemo(nombre = "Desbalance LL", ejercicio = "[C, B, A]")
    public Dibujante dibujaLL() {
        ÁrbolBinarioOrdenado<String> árbol = creaÁrbol();
        árbol.add("C");
        árbol.add("B");
        árbol.add("A");
        return new DibujanteDeÁrbolBinario(árbol);
    }
    
    @MétodoDemo(nombre = "Desbalance RR", ejercicio = "[A, B, C]")
    public Dibujante dibujaRR() {
        ÁrbolBinarioOrdenado<String> árbol = creaÁrbol();
        árbol.add("A");
        árbol.add("B");
        árbol.add("C");
        return new DibujanteDeÁrbolBinario(árbol);
    }
    
    @MétodoDemo(nombre = "Desbalance LR", ejercicio = "[C, A, B]")
    public Dibujante dibujaLR() {
        ÁrbolBinarioOrdenado<String> árbol = creaÁrbol();
        árbol.add("C");
        árbol.add("A");
        árbol.add("B");
        return new DibujanteDeÁrbolBinario(árbol);
    }
    
    @MétodoDemo(nombre = "Desbalance RL", ejercicio = "[A, C, B]")
    public Dibujante dibujaRL() {
        ÁrbolBinarioOrdenado<String> árbol = creaÁrbol();
        árbol.add("A");
        árbol.add("C");
        árbol.add("B");
        return new DibujanteDeÁrbolBinario(árbol);
    }
    
    @MétodoDemo(nombre = "Todas las rotaciones",
			    ejercicio = "[65, 50, 23, 70, 82, 68, 39]")
    public Dibujante dibujaCuatro() {
        ÁrbolBinarioOrdenado<Integer> árbol = creaÁrbol();
        árbol.add(65);
        árbol.add(50);
        árbol.add(23);
        árbol.add(70);
        árbol.add(82);
        árbol.add(68);
        árbol.add(39);
        return new DibujanteDeÁrbolBinario(árbol);
    }
    
    @MétodoDemo(nombre = "Todas las rotaciones hasta raíz",
			    ejercicio = "[65, 50, 23, 70, 82, 68, 39, 10, 30, 55, 25]")
    public Dibujante dibujaCuatroExtra() {
        ÁrbolBinarioOrdenado<Integer> árbol = creaÁrbol();
        árbol.add(65);
        árbol.add(50);
        árbol.add(23);
        árbol.add(70);
        árbol.add(82);
        árbol.add(68);
        árbol.add(39);
        árbol.add(10);
        árbol.add(30);
        árbol.add(55);
        árbol.add(25);
        return new DibujanteDeÁrbolBinario(árbol);
    }
    
    @MétodoDemo(nombre = "Todas las rotaciones y remover 68",
			    ejercicio = "[65, 50, 23, 70, 82, 68, 39] - [68]")
    public Dibujante dibujaCuatroRemover1() {
        ÁrbolBinarioOrdenado<Integer> árbol = creaÁrbol();
        árbol.add(65);
        árbol.add(50);
        árbol.add(23);
        árbol.add(70);
        árbol.add(82);
        árbol.add(68);
        árbol.add(39);
        árbol.remove(68);
        return new DibujanteDeÁrbolBinario(árbol);
    }
    
    @MétodoDemo(nombre = "Todas las rotaciones y remover 82",
			    ejercicio = "[65, 50, 23, 70, 82, 68, 39] - [68, 82]")
    public Dibujante dibujaCuatroRemover2() {
        ÁrbolBinarioOrdenado<Integer> árbol = creaÁrbol();
        árbol.add(65);
        árbol.add(50);
        árbol.add(23);
        árbol.add(70);
        árbol.add(82);
        árbol.add(68);
        árbol.add(39);
        árbol.remove(68);
        árbol.remove(82);
        return new DibujanteDeÁrbolBinario(árbol);
    }
    
    @MétodoDemo(nombre = "Todas las rotaciones y remover 70",
			    ejercicio = "[65, 50, 23, 70, 82, 68, 39] - [68, 82, 70]")
    public Dibujante dibujaCuatroRemover3() {
        ÁrbolBinarioOrdenado<Integer> árbol = creaÁrbol();
        árbol.add(65);
        árbol.add(50);
        árbol.add(23);
        árbol.add(70);
        árbol.add(82);
        árbol.add(68);
        árbol.add(39);
        árbol.remove(68);
        árbol.remove(82);
        árbol.remove(70);
        return new DibujanteDeÁrbolBinario(árbol);
    }
}
