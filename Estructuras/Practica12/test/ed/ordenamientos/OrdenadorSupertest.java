/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed.ordenamientos;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

import ed.Calificador;
import java.util.Arrays;

/**
 *
 * @author blackzafiro
 */
public abstract class OrdenadorSupertest extends Calificador {
	
	/**
	 * Arreglo de arreglos para hacer las pruebas.
	 */
	protected Integer[][] arreglosInt = {
		{3,5,7,9,1,2,4,6},
		{20,45,37,19,1,25},
		{1, 2, 3, 4, 5},
		{5, 4, 3, 2, 1},
		{4, 3, 5, 2, 3, 4}
	};
	
	protected String[][] arreglosStr = {
		{"d","e","c","a","b"},
		{"juan","pedro","hugo","paco","luis"}
	};
	
	/**
	 * Crea el ordenador que será probado.
	 * @return 
	 */
	protected abstract <C extends Comparable <C>> IOrdenador<C> creaOrdenador();
	
	@Test
	public void pruebaOrdenarInt() {
		for(int i = 0; i < arreglosInt.length; i++) {
			System.out.println("Prueba ordenar arreglo " + Arrays.toString(arreglosInt[i]));
			testValue = 1.0f;
			totalPoints += testValue;
			
			IOrdenador<Integer> ordenador = creaOrdenador();
			Integer[] copia = Arrays.copyOf(arreglosInt[i], arreglosInt[i].length);
			Integer[] ordenado = ordenador.ordena(copia);
			
			if(ordenado.length != copia.length) {
				fail("los arreglos no tienen la misma capacidad");
			}
			comprobarSiEstaOrdenado(ordenado);
			
			points += testValue;
		}
	}
	
	@Test
	public void pruebaOrdenarString() {
		for(int i = 0; i < arreglosStr.length; i++) {
			System.out.println("Prueba ordenar arreglo " + Arrays.toString(arreglosStr[i]));
			testValue = 1.0f;
			totalPoints += testValue;
			
			IOrdenador<String> ordenador = creaOrdenador();
			String[] copia = Arrays.copyOf(arreglosStr[i], arreglosStr[i].length);
			String[] ordenado = ordenador.ordena(copia);
			
			if(ordenado.length != copia.length) {
				fail("los arreglos no tienen la misma capacidad");
			}
			comprobarSiEstaOrdenado(ordenado);
			
			points += testValue;
		}
	}
	
	/**
     * Comprueba que el arreglo esté ordenado
     * @param array el arreglo que se desea comprobar si esta ordenado
     */
    private void comprobarSiEstaOrdenado(Comparable[] array) {
		System.out.println("\tresultado >> " + Arrays.toString(array));
        int length = array.length - 1;
        for(int i = 0; i < length; i++){
			if (!(array[i].compareTo(array[i+1]) <= 0)) {
				System.out.println("\t Fallo, se obtuvo: " + Arrays.toString(array));
			}
            assertTrue(array[i].compareTo(array[i+1]) <= 0);
        }
    }
}
