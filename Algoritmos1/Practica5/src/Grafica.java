
import java.util.LinkedList;
//import org.graphstream.graph.*;
//import org.graphstream.graph.implementations.SingleGraph;

public class Grafica {
    LinkedList<Vertice> vertices; //Lista de vértices.
    boolean dirigida; //Verdadero si y solo si la gráfica es dirigida.
  
    /**
     * Constructor de la clase gráfica.
     * @param dir: decide si la gráfica es dirigida o no.
     **/
    public Grafica(boolean dir){
        dirigida = dir;
        vertices = new LinkedList<Vertice>();
    }

    /**
    * Función que recibe un id y devuelve el vértice que tiene dicho id. Si no existe devuelve null.
    * @param ident: identificador del vértice.
    * @return vertice con ese id.
    */
    public Vertice getVerticeId(int ident){
        Vertice ret = null;
        for(Vertice v:vertices){
            if(ident == v.id){
                ret = v;
            }
        }
        return ret;
    }
 
    /**
    * Función que agrega una arista.
    * @param i id del vértice de origen de la arista.
    * @param j id del vértice de destino de la arista.
    */
    public void agregaArista(int i, int j){
        Vertice vert_i = getVerticeId(i);
        Vertice vert_j = getVerticeId(j);
        vert_i.agregaVecino(vert_j);
    
        if(!dirigida){
            vert_j.agregaVecino(vert_i);
        }
    }
  
    /**
    * Agrega un vértice a la gráfica
    * @param ident: identificador del vértice nuevo.
    */
    public void agregaVertice(int ident){
        Vertice nuevo = new Vertice(ident);
        vertices.add(nuevo);
    }

    /**
    * Obtiene el orden(número de vértices) de la gráfica.
    */
    public int getOrden(){
        return vertices.size();
    }

    @Override
    public String toString(){
        String salida = "";
        for(Vertice v:vertices){
            salida += v.toString()+"->"+v.vecinos.toString()+"\n";
        }
        return salida;
    }
}


