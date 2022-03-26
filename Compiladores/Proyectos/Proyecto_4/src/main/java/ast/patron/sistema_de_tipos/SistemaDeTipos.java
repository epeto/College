/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.patron.sistema_de_tipos;

public class SistemaDeTipos {
    
    public static final int[][] SUMA = {{1,2,0,4},
                                        {2,2,0,4},
                                        {0,0,0,4},
                                        {4,4,4,4}};
    
    public static final int[] NOT = {0,0,3,0};
    
    /*Son todos los operadores que nos regresan un booleano
     OR AND MENOR_IGUAL MAYOR_IGUAL IGUAL_IGUAL DIFERENTE '<' '>'
    */
    public static final int[][] OPERACIONES_BOOLEANAS = {{3,3,0,0},
                                                         {3,3,0,0},
                                                         {0,0,3,0},
                                                         {0,0,0,0}};
    
    /* Son todas las operaciones aritmeticas 
      POW DIVISION_ENTERA '-' '*' '/' '%'
    */
    public static final int[][] OPERACIONES_ARITMETICAS = {{1,2,0,0},
                                                           {2,2,0,0},
                                                           {0,0,0,0},
                                                           {0,0,0,0}};
        
    public static String getTipo(int a){
        switch(a){
            case 1: return "Entero";
            case 2: return "Real";
            case 3: return "Booleano";
            case 4: return "Cadema";
            default: return "Tipo no encontrado";
        }
    }
}
