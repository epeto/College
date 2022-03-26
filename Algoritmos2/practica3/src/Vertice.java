
//Clase vértice con pesos en los vecinos.


import java.util.LinkedList;
import java.util.HashMap;

public class Vertice{
    LinkedList<Vertice> ady; //Lista de adyacencias(vecinos)
    String id; //Identificador (o nombre) del vértice.
    HashMap<String,Integer> pesos; //Tabla que relaciona el id del vecino con el peso de la arista.
    HashMap<String,Integer> flujos; //El flujo enviado por un arco. Es menor o igual al peso.
    boolean visitado; //Falso si no ha sido visitado, verdadero en otro caso.
    Vertice p; //Predecesor o padre.
  
    /**
     * Constructor de la clase vértice.
     * @param ident: identificador del vértice, el cual es un número.
     */
    public Vertice(String ident){
        id = ident;
        ady = new LinkedList<Vertice>();
        pesos = new HashMap<String, Integer>();
        flujos = new HashMap<String, Integer>();
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

        pesos.put(vec.id, peso); //Coloca el peso en la tabla.
        flujos.put(vec.id, 0); //El flujo inicial es 0.
    }

    @Override
    public String toString(){
        return id;
    }
}




