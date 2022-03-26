/*
 * Código utilizado para el curso de Estructuras de Datos. Se permite
 * consultarlo para fines didácticos en forma personal, pero no está permitido
 * transferirlo tal cual a estudiantes actuales o potenciales pues se afectará
 * su realización de los ejercicios.
 */

package ed;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;

/**
 * Clase base que se encarga en crear la aplicación para el manejo y uso de
 * pruebas unitarias. Las pruebas se definen en clases hijas de esta clase, y
 * deben sobrescribir el método init. Para empezar cada prueba se debe utilizar
 * los métodos startTest y deben finalizar con el método passed, mientras que
 * el aumento de aciertos por test se hace con el método addUp.
 *
 * @author mindahrelfen
 */
public abstract class Calificador {

    /**
     * Rango para pruebas cortas.
     */
    public static final int SMALL_RANGE = 4;

    /**
     * Rango para pruebas intermedias.
     */
    public static final int MEDIUM_RANGE = 16;

    /**
     * Rango para pruebas extensivas.
     */
    public static final int LARGE_RANGE = 256;

    /**
     * Puntos acumulados por prueba.
     */
    protected static double points;

    /**
     * Puntos totales por prueba.
     */
    protected static double numberOfPoints;

    /**
     * Genera números enteros aleatorios en forma de String a través de su
     * iterador.
     */
    protected static RandomStringGenerator rsg;

    /**
     * Iterador de rsg.
     */
    protected static Iterator<String> rsgIt;

    /**
     * Cantidad de elementos que rsg tendrá.
     */
    protected static int range;

    /**
     * Generador de números aleatorios.
     */
    protected static Random rdm;

    /**
     * Bandera que dice si ya se imprimió el reporte por prueba.
     */
    protected static boolean isPrinted;

    /**
     * Define la categoría actual para la prueba en ejecución.
     */
    protected static String categoryName;

    /**
     * Definición de las categorías a calificar.
     */
    protected static Category categories;

    /**
     * Constructor por defecto. Define la cantidad de números posibles igual a
     * {@value #MEDIUM_RANGE}. Con una sola categoría a calificar con 1.0 de
     * 1.0 aciertos. Permite referencias nulas.
     */
    public Calificador() {
        set(MEDIUM_RANGE);
        init();
        setCategories();
        rdm = new Random();
    }

    /**
     * Define que al inicio de cada prueba se guarde el nombre de dicha prueba.
     */
    @Rule public TestName testName = new TestName();

    /**
     * Método que se implementa para mantener correcta la creación de las
     * clases que definen a las pruebas unitarias. Se espera que las clases
     * hijas sobrescriban este método, pues funge como constructor para todas
     * las clases en la jerarquía de herencia de esta clase.
     */
    protected void init() {}

    /**
     * Define las categorías a calificar. Por defecto solo se tiene una
     * categoría a calificar sin nombre y con 1.0 de 1.0 aciertos.
     */
    protected void setCategories() {
        defineCategories(new String[] {
            ""
        }, new double[] {
            1.0
        });
    }

    /**
     * Define el rango de valores con el cual el generador de números
     * aleatorios enteros va a trabajar.
     *
     * @param r int Recibe un número entero mayor a cero.
     */
    protected void set(int r) {
        if (r < 1) throw new IllegalArgumentException("Solo se permiten números positivos");
        range = r;
        rsg = new RandomStringGenerator(range);
    }

    /**
     * Define las categorías a calificar, recibe dos arreglos de misma longitud,
     * uno con los nombres de cada categoría y el otro con los porcentajes.
     *
     * @param categoryNames String[] Contiene los nombres de las categorías.
     * @param percentages double[] Contiene la ponderación de las categorías.
     */
    protected void defineCategories(String[] categoryNames, double[] percentages) {
        Category c;
        categoryName = "";
        c = new Category(categoryNames, percentages);
        if (!c.equals(categories)) { // evita reestablecer la calificación cuando
            categories = c; // se crea una nueva instancia de pruebas
        }
    }

    /**
     * Se ejecuta antes de iniciar la ejecución de todas las pruebas. Inicia
     * los puntos obtenidos y a calificar en cero e inicia las banderas en su
     * estado inicial.
     */
    @BeforeClass
    public static void setUpClass() {
        points = numberOfPoints = 0.0;
        isPrinted = true;
    }

    /**
     * Se ejecuta al finalizar la ejecución de todas las pruebas. Imprime el
     * ultimo reporte y el puntaje total obtenido.
     */
    @AfterClass
    public static void tearDownClass() {
        print(true, categories.getPuntaje());
    }


    /**
     * Define el inicio de una prueba en particular. Los parámetro definido se
     * utilizar para categorizar cada tipo de prueba, así como los aciertos de
     * la prueba.
     * 
     * @param s String Es el nombre de la prueba en particular.
     * @param p double Es el puntaje máximo que se obtiene por esta prueba.
     */
    protected final void startTest(String s, double p) {
        print(true, testName.getMethodName() + ": " + s);
        numberOfPoints = p;
        points = 0.0;
        isPrinted = false;
        categories.agregarMaximo(p, categoryName);
    }


    /**
     * Define el inicio de una prueba en particular. Los parámetro definido se
     * utilizar para categorizar cada tipo de prueba, así como los aciertos de
     * la prueba.
     * 
     * @param s String Es el nombre de la prueba en particular.
     * @param p double Es el puntaje máximo que se obtiene por esta prueba.
     * @param c String Es el nombre de la categoría a calificar.
     */
    protected final void startTest(String s, double p, String c) {
        categoryName = c;
        startTest(s, p);
    }

    /**
     * Se invoca para aumentar el puntaje obtenido para la prueba, el puntaje
     * se agrega a la categoría de la prueba.
     * 
     * @param d double Es el aumento en el puntaje obtenido para la prueba.
     */
    protected final void addUp(double d) {
        points += d;
        categories.agregarPuntaje(d, categoryName);
    }

    /**
     * Se invoca para avisar que la prueba fue pasada con éxito.
     */
    protected final void passed() {
        print(false, "\tPassed.");
    }

    /**
     * Imprime el resumen de la prueba, además imprime un mensaje en particular.
     * 
     * @param p boolean Bandera que indica si el resumen se imprime con una
     * vuelta de carro después de dicho resumen o no.
     * @param msg String Mensaje que se imprime después del resumen.
     */
    protected static final void print(boolean p, String msg) {
        String s;
        if (!isPrinted) {
            s = "[" + points + "/" + numberOfPoints + "]";
            if (p) {
                System.out.println(s);
            } else {
                System.out.print(s);
            }
        }
        isPrinted = true;
        System.out.println(msg);
    }

    /**
     * Define la clase que guarda las categorías a calificar, esta clase se
     * comporta como una lista simple, y guarda todas y cada una de las
     * categorías disponibles. Se utiliza una cadena String como identificador
     * para diferenciar cada categoría distinta. Cada categoría requiere de un
     * porcentaje entre cero y uno, pero a su vez, la suma de los porcentajes
     * de todas las categorías debe ser igual a uno. El resumen y resultado de
     * la calificación se calcula dentro de esta clase, esta requiere que se
     * invoque agregarMaximo para aumentar el número de aciertos de una
     * categoría en particular y luego se invoque agregarPuntaje para subir el
     * puntaje obtenido. Para obtener el puntaje total de las pruebas en todas
     * las categorías se debe invocar getPuntaje.
     */
    protected class Category {

        /**
         * Define el nombre de la categoría actual.
         */
        String name;

        /**
         * Define el porcentaje que esta categoría vale con respecto al total.
         */
        double percentage;

        /**
         * Define el número total de puntos en esta categoría.
         */
        double maxScore;

        /**
         * Define el número de aciertos con respecto al total en esta categoría.
         */
        double score;

        /**
         * Referencia a la siguiente categoría.
         */
        Category next;

        /**
         * Constructor que inicializa los valores particulares de cada
         * categoría: nombre y porcentaje. 
         *
         * @param names String[] Arreglo de contiene los nombres de las
         * categorías a calificar.
         * @param percentages double[] Arreglo que contiene la ponderación de
         * las categorías a calificar.
         */
        Category(String names[], double percentages[]) {
            this(names, percentages, 0);
        }

        /**
         * Constructor privado que inicializa los valores particulares de cada
         * categoría: nombre y porcentaje. El int indicado impone el índice
         * actual dentro de los arreglos
         *
         * @param names String[] Arreglo de contiene los nombres de las
         * categorías a calificar.
         * @param percentages double[] Arreglo que contiene la ponderación de
         * las categorías a calificar.
         * @param category int Índice en los arreglos de la categoría actual.
         */
        private Category(String names[], double percentages[], int category) {
            this.name = names[category];
            this.percentage = percentages[category];
            this.maxScore = 0.0;
            this.score = 0.0;
            if (names.length - 1 != category) {
                this.next = new Category(names, percentages, category + 1);
            } else {
                this.next = null;
            }
        }

        /**
         * Aumenta el puntaje máximo a una categoría en particular.
         *
         * @param p double Puntaje a agregar.
         * @param categoryName String Categoría a la cual agregar puntaje.
         */
        void agregarMaximo(double p, String categoryName) {
            if (this.name.equals(categoryName)) {
                this.maxScore += p;
            } else {
                next.agregarMaximo(p, categoryName);
            }
        }

        /**
         * Aumenta el puntaje obtenido a una categoría en particular.
         *
         * @param p double Puntaje a agregar.
         * @param categoryName String Categoría a la cual agregar puntaje.
         */
        void agregarPuntaje(double p, String categoryName) {
            if (this.name.equals(categoryName)) {
                this.score += p;
            } else {
                next.agregarPuntaje(p, categoryName);
            }
        }

        /**
         * Método que se encarga de calcular y devolver en forma de cadena los
         * puntajes parciales para cada categoría y el puntaje general de todas
         * las categorías.
         *
         * @return String Cadena que representa el resumen del total.
         */
        String getPuntaje() {
            return puntaje(new Printer(80), new StringBuilder(), 0.0);
        }

        /**
         * Método que se encarga de acarrear en forma de cadena y número los
         * puntajes parciales para cada categoría y el puntaje general de todas
         * las categorías.
         *
         * @param s String Cadena que acarrea el mensaje a imprimir.
         * @param d double Acarreo del puntaje total calculado.
         * @return String Cadena que representa el resumen del total.
         */
        private String puntaje(Printer p, StringBuilder sb, double d) {
            int len;
            double n;
            String str1, str2;
            n = (score / maxScore * percentage);
            d = (Double.isNaN(n) ? d : d + n);
            sb.append(p.divisor('='));
            str1 = "\n " + name + " (Aciertos: " + score + "/" + maxScore + ")";
            str2 = "Puntaje: " + (n * 100.0) + "/" + (percentage * 100.0);
            sb.append(p.line(str1, str2));
            if (next != null) {
                return next.puntaje(p, sb, d);
            } else {
                sb.append(p.divisor('='));
                str1 = "\n Puntaje Total:";
                str2 = (d * 100.0) + "/100.0";
                sb.append(p.line(str1, str2));
                sb.append(p.divisor('='));
                return sb.toString();
            }
        }

        @Override
        public boolean equals(Object obj) {
            Category c, t;
            if (obj == null) {
                return false;
            } else if (!(obj instanceof Category)) {
                return false;
            } else {
                c = (Category) obj;
                t = this;
                while (t != null && c != null) {
                    if (!t.name.equals(c.name)) {
                        return false;
                    }
                    if (Math.abs(t.percentage - c.percentage) > 0.1) {
                        return false;
                    }
                    c = c.next;
                    t = t.next;
                }
                return t == null && c == null;
            }
        }
    }

    /**
     * Clase que implementa la correcta impresión de el resumen de las pruebas
     * unitarias, implementa dos métodos que devuelven objetos que pueden ser
     * concatenados a un StringBuilder.
     */
    protected class Printer {

        /**
         * Sirve para crear cadenas de un solo símbolo de longitud dada.
         */
        char array[];

        /**
         * Constructor por defecto. El numero pasado como parámetro representa
         * la longitud de las líneas que se van a devolver.
         *
         * @param size int Representa la longitud de las líneas a devolver.
         */
        Printer(int size) {
            this.array = new char[size];
        }

        /**
         * Devuelve un arreglo con la longitud impuesta por el constructor que
         * solo contiene el símbolo pasado.
         *
         * @param symbol char Carácter con el que se va a rellenar el arreglo.
         */
        char[] divisor(char symbol) {
            Arrays.fill(array, symbol);
            return array;
        }

        /**
         * Devuelve una cadena que de longitud impuesta por el constructor la
         * cual del lado izquierdo contiene a la primera cadena pasada y del
         * lado derecho contiene a la segunda cadena pasada. En medio de ambas
         * cadena existe una cadena de espacios en blanco de longitud exacta
         * para alcanzar la longitud impuesta por el constructor.
         *
         * @param str1 String Cadena que va al inicio de la cadena respuesta.
         * @param str2 String Cadena que va al final de la cadena respuesta.
         */
        String line(String str1, String str2) {
            StringBuilder sb = new StringBuilder();
            Arrays.fill(array, ' ');
            sb.append(str1);
            sb.append(array, 0, array.length - str1.length() - str2.length());
            sb.append(str2 + "\n");
            return sb.toString();
        }
    }
}
