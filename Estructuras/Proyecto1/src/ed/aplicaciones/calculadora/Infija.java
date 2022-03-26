/*
 * Código utilizado para el curso de Estructuras de Datos.
 * Se permite consultarlo para fines didácticos en forma personal,
 * pero no está permitido transferirlo tal cual a estudiantes actuales o potenciales.
 */
package ed.aplicaciones.calculadora;

import java.util.Stack;
import java.util.Arrays;
import java.util.Scanner;
import java.util.LinkedList;

/**
 * Clase para evaluar expresiones en notación infija.
 *
 * @author blackzafiro
 */
public class Infija {

	/**
	 * Devuelve la precedencia de cada operador. Entre mayor es la precedencia,
	 * más pronto deverá ejecutarse la operación.
	 *
	 * @operador Símbolo que representa a las operaciones: +,-,*,/ y '('.
	 * @throws UnsupportedOperationException para cualquier otro símbolo.
	 */
	private static int precedencia(char operador) {
		int prec = -1;
		switch(operador){
			case '(' : prec = 0;
			break;
			case '+' :
			case '-' : prec = 1;
			break;
			case '*' :
			case '/' :
			case '%' : prec = 2;
			break;
			default: throw new UnsupportedOperationException("El símbolo "+operador+" no es operador.");
		}
		return prec;
	}

	/**
	 * Pasa las operaciones indicadas en notación infija a notación sufija o
	 * postfija.
	 *
	 * @param tokens Arreglo con símbolos de operaciones (incluyendo paréntesis)
	 * y números (según la definición aceptada por
	 * <code>Double.parseDouble(token)</code> en orden infijo.
	 * @return Arreglo con símbolos de operaciones (sin incluir paréntesis) y
	 * números en orden postfijo.
	 */
	public static String[] infijaASufija(String[] tokens) {
		LinkedList<String> cola = new LinkedList<>();
		Stack<String> pila = new Stack<>();
		for(String simbolo : tokens){
			if(!simbolo.equals("")){
				if(Fija.esDouble(simbolo)){
					cola.offer(simbolo);
				}else if(simbolo.equals("(")){
					pila.push(simbolo);
				}else if(simbolo.equals(")")){
					String tope = pila.pop();
					while(!tope.equals("(")){
						cola.offer(tope);
						tope = pila.pop();
					}
				}else{
					int prec = precedencia(simbolo.charAt(0));
					while(!pila.isEmpty() && precedencia(pila.peek().charAt(0)) >= prec){
						cola.offer(pila.pop());
					}
					pila.push(simbolo);
				}
			}
		}

		while(!pila.isEmpty()){
			cola.offer(pila.pop());
		}
		String[] retVal = new String[cola.size()];
		return cola.toArray(retVal);
	}

	/**
	 * Recibe la secuencia de símbolos de una expresión matemática en notación
	 * infija y calcula el resultado de evaluarla.
	 *
	 * @param tokens Lista de símbolos: operadores, paréntesis y números.
	 * @return resultado de la operación.
	 */
	public static double evaluaInfija(String[] tokens) {
		String[] suf = infijaASufija(tokens);
		System.out.println("Sufija: " + Arrays.toString(suf));
		return Fija.evaluaPostfija(suf);
	}

	/**
	 * Interfaz de texto para la calculadora.
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String sentence = "comodin";
		String method = "infija";
		String delims = "\\s+|(?<=\\()|(?=\\))";
		String[] tokens;

		System.out.println("Calculadora en modo notación " + method);
		while (!sentence.equals("exit")) {
			System.out.println("Escriba una operación en notación "+method+", o en su defecto"
			+" escriba\ninfija - para cambiar a notación infija.\n"
			+"prefija - para cambiar a notación prefija.\n"
			+"postfija - para cambiar a notación postfija\n"
			+"exit - para terminar la ejecución del programa.");
			sentence = scanner.nextLine();
			switch (sentence) {
				case "exit":
					return;
				case "infija":
				case "prefija":
				case "postfija":
					System.out.println("Cambiando a notación " + sentence);
					method = sentence;
					continue;
				default:
					break;
			}
			tokens = sentence.split(delims);
			System.out.println("Tokens: " + Arrays.toString(tokens));
			double resultado;
			switch (method) {
				case "infija":
					resultado = evaluaInfija(tokens);
					break;
				case "prefija":
					resultado = Fija.evaluaPrefija(tokens);
					break;
				case "postfija":
					resultado = Fija.evaluaPostfija(tokens);
					break;
				default:
					System.out.println("Método inválido <" + method
							+ "> seleccione alguno de:\n"
							+ "\tinfija\n"
							+ "\tprefija\n"
							+ "\tpostfija\n");
					continue;
			}
			System.out.println("= " + resultado);
		}
		scanner.close();
	}
}

