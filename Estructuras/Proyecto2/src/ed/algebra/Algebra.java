
package ed.algebra;

public class Algebra {
    public static void main(String[] args){
        Polinomio poli1 = new Polinomio(new double[]{1.0, 2.0, 3.0, 4.0},
                                        new int[]{0, 1, 2, 3});
        Polinomio poli2 = new Polinomio(new double[]{5.0, 6.0, 7.0, 8.0, 9.0},
                                        new int[]{2, 3, 4, 8, 11});
        System.out.println("P1: "+poli1.toString());
        System.out.println("P2: "+poli2.toString());
        System.out.println("P1 + P2:\n" + poli1.más(poli2).toString());
        System.out.println("P1 * P2:\n" + poli1.por(poli2).toString());
        Polinomio poli3 = new Polinomio(new double[]{5.0, -1.0},
                                        new int[]{1, 2});
        Polinomio poli4 = new Polinomio(new double[]{-1.0, 1},
                                        new int[]{0, 1});
        System.out.println("P3: "+poli3.toString());
        System.out.println("P4: "+poli4.toString());
        System.out.println("P3 + P4:\n" + poli3.más(poli4).toString());
        System.out.println("P3 * P4:\n" + poli3.por(poli4).toString());
    }
}
