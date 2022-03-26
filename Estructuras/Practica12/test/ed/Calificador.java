/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ed;

import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 *
 * @author blackzafiro
 */
public class Calificador {
	protected static float points;
    protected static float totalPoints;
    protected float testValue;
	
	@BeforeClass
    public static void setUpClass() {
        totalPoints = points = 0;
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("==============================");
    	System.out.println("Calificacion automatica: " + points * 10 /totalPoints);
        System.out.println("==============================");
    }
}
