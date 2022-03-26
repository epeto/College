/*
 * Código utilizado para el curso de Estructuras de Datos.
 * Se permite consultarlo para fines didácticos en forma personal,
 * pero no está permitido transferirlo tal cual a estudiantes actuales o potenciales.
 */
package ed.aplicaciones.calculadora;

import java.util.Stack;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Clase para evaluar expresiones en notaciones prefija y postfija.
 *
 * @author blackzafiro
 */
public class Fija {

	/**
	 * Evalúa la operación indicada por <code>operador</code>.
	 */
	private static double evalua(char operador, double operando1, double operando2) {
		double res = 0;
		switch(operador){
			case '+' : res = operando1 + operando2;
			break;
			case '-' : res = operando1 - operando2;
			break;
			case '*' : res = operando1 * operando2;
			break;
			case '/' : res = operando1 / operando2;
			break;
			case '%' : res = operando1 % operando2;
			break;
		}
		return res;
	}

	/**
	 * Verifica si un String es un double.
	 * @param expr cadena que posiblemente represente un número.
	 * @return true si expr es un double, falso en otro caso.
	 */
	public static boolean esDouble(String expr){
		int ndots=0; //cuenta el número de puntos en la expresión.
		int inicio = 0;
		if(expr.charAt(0) == '-' && expr.length()>1){
			inicio = 1;
		}
		for(int i=inicio; i<expr.length(); i++){
			char ca = expr.charAt(i); //caracter actual.
			if(Character.isDigit(ca) || ca=='.'){
				if(ca == '.'){
					ndots++;
				}
			}else{
				return false;
			}
		}

		if(ndots > 1){
			return false;
		}else{
			return true;
		}
	}

	/**
	 * Recibe la secuencia de símbolos de una expresión matemática en notación
	 * prefija y calcula el resultado de evaluarla.
	 *
	 * @param tokens Lista de símbolos: operadores y números.
	 * @return resultado de la operación.
	 */
	public static double evaluaPrefija(String[] tokens) {
		Stack<Double> pila = new Stack<>();
		for(int i=tokens.length-1; i>=0; i--){
			String simbolo = tokens[i];
			if(esDouble(simbolo)){
				pila.push(Double.parseDouble(simbolo));
			}else{
				double op1 = pila.pop();
				double op2 = pila.pop();
				pila.push(evalua(simbolo.charAt(0), op1, op2));
			}
		}
		return pila.pop();
	}

	/**
	 * Recibe la secuencia de símbolos de una expresión matemática en notación
	 * postfija y calcula el resultado de evaluarla.
	 *
	 * @param tokens Lista de símbolos: operadores y números.
	 * @return resultado de la operación.
	 */
	public static double evaluaPostfija(String[] tokens) {
		Stack<Double> pila = new Stack<>();
		for(int i=0; i<tokens.length; i++){
			String simbolo = tokens[i];
			if(esDouble(simbolo)){
				pila.push(Double.parseDouble(simbolo));
			}else{
				double op2 = pila.pop();
				double op1 = pila.pop();
				pila.push(evalua(simbolo.charAt(0), op1, op2));
			}
		}
		return pila.pop();
	}

	/**
	 * Interfaz de texto para la calculadora.
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String sentence = "comodin";
		String method = "prefija";
		String delims = "\\s+|(?<=\\()|(?=\\))";
		String[] tokens;
		while(!sentence.equals("exit")){
			sentence = scanner.nextLine();
			switch (sentence) {
				case "exit":
					return;
				case "prefija":
				case "postfija":
					System.out.println("Cambiando a notación " + sentence);
					method = sentence;
					continue;
				default:
					break;
			}
			tokens = sentence.split(delims);
			System.out.println(Arrays.toString(tokens));
			if (method.equals("postfija")) {
				System.out.println("= " + evaluaPostfija(tokens));
			} else {
				System.out.println("= " + evaluaPrefija(tokens));
			}
		}
		scanner.close();
	}
}
