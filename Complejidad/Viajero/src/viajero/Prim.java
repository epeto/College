/*
 * Clase para implementar el algoritmo de Prim.
 */
package viajero;

import java.util.PriorityQueue;
import java.util.Random;

/**
 *
 * @author emmanuel
 */
public class Prim {
    
    /**
     * Algoritmo de Prim.
     * @param grafica: Gráfica a la que se le va a aplicar el algoritmo.
     * @param r: raíz
     */
    public static void MST_Prim(GrafoAbs grafica, int r){
        grafica.vers.get(r).key = 0.0;
        PriorityQueue<Vertice> pq = new PriorityQueue();
        //En esta parte agregamos todos los vértices a la cola de prioridades.
        for(Vertice ver:grafica.vers){
            pq.add(ver);
        }
        
        while(!pq.isEmpty()){
            Vertice u = pq.poll();
            for(Vertice v:grafica.getVecinos(u)){ //Para cada vértice adyacente a u.
                if(pq.contains(v) && (grafica.matriz[u.name][v.name]<v.key)){
                    if(v.padre!=null){
                        v.padre.hijos.remove(v);
                    }
                    
                    v.padre = u;
                    u.hijos.add(v);
                    v.key = grafica.matriz[u.name][v.name];
                }
            }
        }
    }
}
