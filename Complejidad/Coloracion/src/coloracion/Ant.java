/*
 * Clase que representa las hormigas del algoritmo bui.
 */
package coloracion;

import java.util.LinkedList;
import java.util.Random;
/**
 *
 * @author emmanuel
 */
public class Ant {
    Vertice vp; //Representa el vértice sobre el que está parada la hormiga.
    LinkedList<Vertice> tabu; //Una lista de los vértices visitados recientemente por la hormiga.
    int R_SIZE_LIMIT;
    
    /**
     * Constructor del objeto hormiga
     * @param n : el número de movimientos que va a realizar la hormiga.
     */
    public Ant(int n){
        tabu = new LinkedList();
        R_SIZE_LIMIT = n/3;
    }
    
    /**
     * Le asigna un vértice a esta hormiga.
     * @param vert Vértice sobre el que se va a parar inicialmente.
     */
    public void setVertice(Vertice vert){
        vp = vert;
    }
    
    /**
     * Función que colorea al vértice sobre el que está la hormiga
     * @param ac: entero que representa el número de colores disponibles (availableColors)
     */
    public void colorea(int ac){
        if(vp.conflicto>0){
            for(int i=0;i<ac;i++){
                int ch = calcConflicto(i); //Conflicto hipotético
                if(ch<vp.conflicto){ //En esta parte nos aseguramos de asignarle a vp el color con el menor conflicto.
                    vp.color = i;
                    vp.conflicto = ch;
                }
            }
        }
        
        if(tabu.size()<R_SIZE_LIMIT){
            tabu.add(vp);
        }else{
            tabu.removeFirst();
            tabu.add(vp);
        }
    }
    
    /**
     * Función que mueve la hormiga 2 posiciones.
     */
    public void mueve(){
        Random rn = new Random();
        if(!vp.vecinos.isEmpty()){ //Si tiene vecinos se mueve.
            int sig = rn.nextInt(vp.vecinos.size());
            vp = vp.vecinos.get(sig); //Aquí ya cambiamos de posición.
            LinkedList<Vertice> vec = vp.getVecinos();
            int indice=0;
            Vertice vt = vec.get(0);
            for(Vertice ve:vec){
                //En esta parte asignamos a vt el vértice con el mayor conflicto.
                if(!tabu.contains(ve) && vt.conflicto<ve.conflicto){
                    vt = ve;
                }
            }
            vp = vt; //La hormiga se vuelve a mover.
        }
    }
    
    /**
     * Función que calcula el conflicto del vértice si se colorea del color que recibe.
     * @param col color que recibe.
     * @return 
     */
    public int calcConflicto(int col){
        int con=0;
        for(Vertice v:vp.getVecinos()){
            if(col==v.color){
                con++;
            }
        }
        return con;
    }
}
