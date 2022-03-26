

package hamilton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author emmanuel
 */
public class Adivinar {
   
    /**
     * Dada una lista, devuelve uno de sus elementos al azar.
     * @param lista
     * @return 
     */
    public static int removeElemRandom(List<Integer> lista){
        int cota = lista.size();
        Random rn = new Random();
        if(cota>0)
            return lista.remove(rn.nextInt(cota));
        else
            return -1;
    }
    
    /**
     * Esta función recibe el orden de una gráfica (GrafoAbs) y devuelve
     * una permutación de vértices.
     * @param orden
     * @return
     */
    public static List<Integer> getCaminoAzar(int orden){
        ArrayList<Integer> listaRetorno = new ArrayList();
        //Esta lista contendrá inicialmente una secuencia de números ordenados de 0 a n-1.
        //donde n es el orden de la gráfica.
        ArrayList<Integer> listaOrdenada = new ArrayList();
        
        //Llenamos la lista ordenada
        for(int i=0;i<orden;i++){
            listaOrdenada.add(i);
        }
        
        //En este paso la vaciamos y pasamos los elementos a la lista de retorno.
        for(int i=0;i<orden;i++){
            int vertice = removeElemRandom(listaOrdenada);
            listaRetorno.add(vertice);
        }
        
        return listaRetorno;
    }
    
    /**
     * Función que recibe una gráfica, un camino y decide si el camino es
     * un ciclo hamiltoniano.
     * @param grafo
     * @param camino
     * @return 
     */
    public static boolean verificador(GrafoAbs grafo, List<Integer> camino){
        boolean verificar = true;
        
        //Primero verificamos que todos sean adyacentes, desde el primero al último
        for(int i=0;i<camino.size()-1;i++){
            if(!grafo.sonAdyacentes(camino.get(i), camino.get(i+1))){
                verificar = false; //Si alguno no es adyacente, cambiamos el estado de verificar.
            }
        }
        
        //Luego verificamos que el último elemento sea adyacente al primero.
        if(!grafo.sonAdyacentes(0, camino.size()-1)){
            verificar = false; //Si el último no es adyacente al primero, cambiamos el estado de verificar.
        }
        
        return verificar; //Si todos son adyacentes, entonces el estado de verificar no cambió.
    }
}