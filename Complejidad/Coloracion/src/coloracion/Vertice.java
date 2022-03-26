/*
 * Clase que represeta un vértice.
 */
package coloracion;

import java.util.LinkedList;
import java.awt.Point;
import java.util.Random;

/**
 *
 * @author emmanuel
 */
public class Vertice {
    int color; //El color va a estar representado por un número.
    int nombre; //El nombre del vértice será representado como un número.
    LinkedList<Vertice> vecinos; //Vecinos de este vértice.
    int part; //Esto solo se usará para decidir en qué partición va a estar este vértice.
    Point cor;
    int conflicto; //representa el conflicto local.
    
    /**
     * Constructor de la clase vértice
     * @param nom el nombre del vértice (un número).
     * @param tam el tamaño de la ventana donde se va a colocar.
     */
    public Vertice(int nom, int tam){
        vecinos = new LinkedList();
        nombre = nom;
        Random rn = new Random();
        int cx = rn.nextInt(tam);
        int cy = rn.nextInt(tam);
        cor = new Point(cx,cy); //Por el momento agregaremos los vértices en una posición aleatoria.
        color = -1; //La coloración empezará a partir del 0.
    }
    
    /**
     * Función que le agrega un vecino a este vértice.
     * @param v: vecino a agregar
     */
    public void addVecino(Vertice v){
        vecinos.add(v);
    }
    
    /**
     * Función que devuelve los vecinos de este vértice
     * @return vecinos
     */
    public LinkedList<Vertice> getVecinos(){
        return vecinos;
    }
    
    /**
     * Obtiene el grado de este vértice.
     * @return 
     */
    public int getGrado(){
        return vecinos.size();
    }
    
    @Override
    public String toString(){
        return "v"+nombre;
    }
}
