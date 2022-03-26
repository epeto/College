package ast.patron.registros;

import java.util.Arrays;

/**
 * Clase para facilitar la asignación de registros, que abstrae el manejo de 
 * los registros.
 */
public class Registros {

    int objetivoEntero;

    // Todos los registros enteros disponibles.
    String[] E_registros = {"$t0", "$t1","$t2", "$t3", "$t4", "$t5", "$t6", "$t7", "$t8", "$t9", "$s0", "$s1","$s2","$s3","$s4","$s5","$s6","$s7"};
    /**
     * 
     * @param o
     */
    public void setObjetivo(int o) {
        objetivoEntero = o % E_registros.length;
    }

    /**
     * 
     * @param o
     */
    public void setObjetivo(String o) {
        int nvo_objetivo = Arrays.asList(E_registros).indexOf(o);
        setObjetivo(nvo_objetivo);
    }

    /**
     * Recupera el número de registro en el que se espera que sea
     * guardado el resultado de la operación principal.
     * @return objetivoEntero
     */
    public int getObjetivoInt() {
        return objetivoEntero;
    }
    
    public String getObjetivoString(){
        return E_registros[objetivoEntero];
    }
    
    //Aumenta en 1 el contador de objetivoEntero
    public void siguiente(){
        objetivoEntero = (objetivoEntero + 1) % E_registros.length;
    }

    /**
     * Recupera los n registros siguientes que pueden ser usados de manera 
     * auxiliar para llevar a cabo los cálculos, es decir "disponibles".
     * @param n
     * @return siguientes
     */
    public String[] getNSiguientes(int n) {
        String[] siguientes = new String[n];
        for(int i = 1; i <= n; i++) {
            siguientes[i-1] = E_registros[(objetivoEntero + i) % E_registros.length];
        }
        return siguientes;
    }
}