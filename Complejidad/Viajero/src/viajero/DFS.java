/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viajero;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author emmanuel
 */
public class DFS {
    
    /**
     * Algoritmo dfs para el árbol resultante del algoritmo de Prim.
     * @param graf gráfica a la que se le aplicará el algoritmo.
     * @param r raíz.
     * @return 
     */
    public static ArrayList<Vertice> dfs(GrafoAbs graf, int r){
        Stack<Vertice> pila = new Stack();
        ArrayList<Vertice> listaR = new ArrayList();
        
        pila.push(graf.vers.get(r));
        
        while(!pila.empty()){
            Vertice temp = pila.pop();
            listaR.add(temp);
            
            for(Vertice ve:temp.hijos){
                pila.push(ve);
            }
        }
        
        return listaR;
    }
}
