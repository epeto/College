
package ed.aplicaciones.calculadora;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ed.aplicaciones.Calificador;

/**
 *
 * @author blackzafiro
 * @author mindahrelfen
 */
public class TestCalculadora extends Calificador{

    String evPref1;
    String evPref2;

    String evPost1;
    String evPost2;
    
    String infSuf1;
    String infSuf2;
    String infSuf3;

    @Override
    public void init(){
        evPref1 = "* 2 + 3 6"; // 18
        evPref2 = "+ 2 / * - 15 8 8 4"; // 16

        infSuf1 = "(2 * 5) + (32 / 4) - 8 + 3"; // 13
        infSuf2 = "(15 * 96) % 17"; // 12
        infSuf3 = "((3 + 5) * 10 - 14) * 2 - (18 - 3) / 5"; // 129

        evPost1 = "2 5 * 32 4 / + 8 - 3 +"; //13. Viene de infSuf1
        evPost2 = "3 5 + 10 * 14 - 2 * 18 3 - 5 / -"; // 129. Viene de infSuf3
    }

    @Test
    public void testPrefija() {
        startTest("Prefija", 2.0);

        double res1 = Fija.evaluaPrefija(evPref1.split(" "));
        assertEquals(res1, 18.0, 0.05);
        addUp(1.0);
        
        double res2 = Fija.evaluaPrefija(evPref2.split(" "));
        assertEquals(res2, 16.0, 0.05);
        addUp(1.0);

        passed();
    }

    @Test
    public void testPostfija() {
        startTest("Postfija", 2.0);

        double res1 = Fija.evaluaPostfija(evPost1.split(" "));
        assertEquals(res1, 13.0, 0.05);
        addUp(1.0);

        double res2 = Fija.evaluaPostfija(evPost2.split(" "));
        assertEquals(res2, 129.0, 0.05);
        addUp(1.0);

        passed();
    }

    @Test
    public void testInfijaAPostfija() {
        startTest("Infija a postfija", 3.0);

        String[] res1 = Infija.infijaASufija(infSuf1.split("\\s+|(?<=\\()|(?=\\))"));
        String[] expected1 = {"2", "5", "*", "32", "4", "/", "+", "8", "-", "3", "+"};
        assertArrayEquals(expected1, res1);
        addUp(1.0);

        String[] res2 = Infija.infijaASufija(infSuf2.split("\\s+|(?<=\\()|(?=\\))"));
        String[] expected2 = {"15", "96", "*", "17", "%"};
        assertArrayEquals(expected2, res2);
        addUp(1.0);

        String[] res3 = Infija.infijaASufija(infSuf3.split("\\s+|(?<=\\()|(?=\\))"));
        String[] expected3 = {"3", "5", "+", "10", "*", "14", "-", "2", "*", "18", "3", "-", "5", "/", "-"};
        assertArrayEquals(expected3, res3);
        addUp(1.0);

        passed();
    }

    @Test
    public void testInfija() {
        startTest("Infija", 3.0);

        double res1 = Infija.evaluaInfija(infSuf1.split("\\s+|(?<=\\()|(?=\\))"));
        assertEquals(13.0, res1, 0.05);
        addUp(1.0);

        double res2 = Infija.evaluaInfija(infSuf2.split("\\s+|(?<=\\()|(?=\\))"));
        assertEquals(12.0, res2, 0.05);
        addUp(1.0);

        double res3 = Infija.evaluaInfija(infSuf3.split("\\s+|(?<=\\()|(?=\\))"));
        assertEquals(129.0, res3, 0.05);
        addUp(1.0);

        passed();
    }
}
