
//Clase vértice con pesos en los vecinos.


import java.util.LinkedList;
import java.util.HashMap;

public class Vertice implements Comparable<Vertice> {
    LinkedList<Vertice> ady; //Lista de adyacencias(vecinos)
    int id; //Identificador.
    int d; //Atributo de distancia.
    Vertice p; //Padre de este vértice.
    HashMap<Integer,Integer> pesos; //Tabla que relaciona el id del vecino con el peso de la arista.
  
    /**
     * Constructor de la clase vértice.
     * @param ident: identificador del vértice, el cual es un número.
     */
    public Vertice(int ident){
        id = ident;
        ady = new LinkedList<Vertice>();
        pesos = new HashMap<Integer,Integer>();
    }
  
    //Comprueba si 2 vértices son iguales.
    @Override
    public boolean equals(Object o){
        boolean ret = false;
        if(o instanceof Vertice){
            Vertice comp = (Vertice)o;
            if(comp.id == this.id){
                ret = true;
            }
        }
        return ret;
    }
  
    /**
     * Agrega un vecino a este vértice
     * @param vec: nuevo vecino.
     * @param peso: peso de la arista entre este vértice y el vecino.
     */
    public void agregaVecino(Vertice vec, int peso){
        if(!ady.contains(vec)){
            ady.add(vec);
        }

        pesos.put(vec.id,peso); //Coloca el peso en la tabla.
    }

    @Override
    public String toString(){
        return "v"+id;
    }

    @Override
    public int compareTo(Vertice v) {
        return (this.d - v.d);
    }
    
    @Override
    public int hashCode(){
        return id;
    }
}




