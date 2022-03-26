
import java.util.LinkedList;

public class Grafica{
  LinkedList<Vertice> vertices;
  int orden; //Número de vértices.
  boolean dirigida;
  
  public Grafica(boolean dir){
    orden = 0;
    dirigida = dir;
    vertices = new LinkedList<Vertice>();
  }

  /**
  * Función que recibe una etiqueta y devuelve el vértice que tiene dicho label. Si no existe devuelve null.
  * @param l: nombre del vértice.
  * @return vertice con ese nombre.
  */
  public Vertice getVerticeLabel(String l){
    Vertice ret = null;
    for(Vertice v:vertices){
      if(l.equals(v.label)){
        ret = v;
      }
    }
    return ret;
  }
 
  /**
  * Función que agrega una arista.
  * @param i Vértice de origen de la arista.
  * @param j Vértice de destino de la arista.
  */
  public void agregaArista(String i, String j){
    Vertice vert_i = getVerticeLabel(i);
    Vertice vert_j = getVerticeLabel(j);
    vert_i.agregaVecino(vert_j);
    
    if(!dirigida){
      vert_j.agregaVecino(vert_i);
    }
  }
  
  //Agrega un vértice a la gráfica.
  public void agregaVertice(String l){
    Vertice nuevo = new Vertice(l);
    vertices.add(nuevo);
    orden = vertices.size();
  }
}





