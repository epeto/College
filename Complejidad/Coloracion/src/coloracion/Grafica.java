/*
 * Esta clase representará una gráfica
 */
package coloracion;

import java.util.LinkedList;
import processing.core.PApplet;

/**
 *
 * @author emmanuel
 */
public class Grafica {
    LinkedList<Vertice> verts; //Conjunto de vértices. Vamos a suponer que vi está en la posición i de la lista.
    
    public Grafica(){
        verts = new LinkedList();
    }
    
    /**
     * Función que recibe un entero y crea una gráfica de ese orden.
     * @param orden : número de vértices en la gráfica.
     * @param tam: el tamaño de la ventana
     */
    public Grafica(int orden,int tam){
        verts = new LinkedList();
        for(int i=0;i<orden;i++){
            verts.add(new Vertice(i,tam));
        }
    }
    
    /**
     * Funcióń que agrega una arista a la gráfica del vértice 1 al vértice 2.
     * @param v1 vértice 1
     * @param v2 vértice 2
     */
    public void addArista(int v1, int v2){
        Vertice ver1 = verts.get(v1); //Asumimos que el vértice 1 está en la posición v1.
        Vertice ver2 = verts.get(v2); //Asumimos que el vértice 2 está en la posición v2.
        if(!ver1.vecinos.contains(ver2)){
            ver1.addVecino(ver2);
        }
        
        if(!ver2.vecinos.contains(ver1)){
            ver2.addVecino(ver1); //Esto debido a que es una gráfica no dirigida.
        }
    } 
    
    public int getOrden(){
        return verts.size();
    }
}
