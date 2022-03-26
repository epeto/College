
package ed.algebra;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PolinomioTest {
    @Test
	void sumaPolinomios1() {
		System.out.print("Suma 1:\n");
		Polinomio poli1 = new Polinomio(new double[]{1.0, 2.0, 3.0, 4.0},
                                        new int[]{0, 1, 2, 3});
        Polinomio poli2 = new Polinomio(new double[]{5.0, 6.0, 7.0, 8.0, 9.0},
                                        new int[]{2, 3, 4, 8, 11});
        Polinomio poli3 = new Polinomio(new double[]{1.0, 2.0, 8.0, 10.0, 7.0, 8.0, 9.0},
                                        new int[]{0, 1, 2, 3, 4, 8, 11});
        Polinomio resultado = poli1.más(poli2);
        System.out.println(poli1+"\n+");
        System.out.println(poli2+"\n=");
        System.out.println(resultado);
        assertEquals(resultado, poli3);
	}

    @Test
    void sumaPolinomios2(){
        System.out.print("Suma 2:\n");
        Polinomio poli1 = new Polinomio(new double[]{5.0, -1.0},
                                        new int[]{1, 2});
        Polinomio poli2 = new Polinomio(new double[]{-1.0, 1},
                                        new int[]{0, 1});
        Polinomio poli3 = new Polinomio(new double[]{-1.0, 6.0, -1.0},
                                        new int[]{0, 1, 2});
        Polinomio resultado = poli1.más(poli2);
        System.out.println(poli1+"\n+");
        System.out.println(poli2+"\n=");
        System.out.println(resultado);
        assertEquals(resultado, poli3);
    }

    @Test
    void multiplicaPolinomios1(){
        System.out.print("Multiplicación 1:\n");
		Polinomio poli1 = new Polinomio(new double[]{1.0, 2.0, 3.0, 4.0},
                                        new int[]{0, 1, 2, 3});
        Polinomio poli2 = new Polinomio(new double[]{5.0, 6.0, 7.0, 8.0, 9.0},
                                        new int[]{2, 3, 4, 8, 11});
        Polinomio poli3 = new Polinomio(new double[]{5.0, 16.0, 34.0, 52.0, 45.0, 28.0, 8.0, 16.0, 24.0, 41.0, 18.0, 27.0, 36.0},
                                        new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14});
        Polinomio resultado = poli1.por(poli2);
        System.out.println(poli1+"\n+");
        System.out.println(poli2+"\n=");
        System.out.println(resultado);
        assertEquals(resultado, poli3);
    }

    @Test
    void multiplicaPolinomios2(){
        System.out.print("Multiplicación 2:\n");
        Polinomio poli1 = new Polinomio(new double[]{5.0, -1.0},
                                        new int[]{1, 2});
        Polinomio poli2 = new Polinomio(new double[]{-1.0, 1},
                                        new int[]{0, 1});
        Polinomio poli3 = new Polinomio(new double[]{-5.0, 6.0, -1.0},
                                        new int[]{1, 2, 3});
        Polinomio resultado = poli1.por(poli2);
        System.out.println(poli1+"\n+");
        System.out.println(poli2+"\n=");
        System.out.println(resultado);
        assertEquals(resultado, poli3);
    }
}
