
//Clase vértice

import java.util.LinkedList;
import java.util.ListIterator;

public class Vertice{
  LinkedList<Vertice> vecinos; //Lista de vecinos
  Vertice p; //Padre del vértice
  int id; //Identificador.
  String nombre; //Nombre del vértice.
  boolean visitado; //Comprueba si un vértice ya fue revisado.
  int dfi; //Índice resultado de aplicar DFS.
  int d; //Distancia resultante de aplicar BFS.
  ListIterator<Vertice> iterador; //Itera sobre la lista de vecinos. Se usará en DFS.
  
  /**
   * Constructor de la clase vértice.
   * @param ident: identificador del vértice, el cual es un número.
   */
  public Vertice(int ident){
    id = ident;
    vecinos = new LinkedList<Vertice>();
    nombre = "v"+ident;
    p = null;
    visitado = false;
    dfi = 0;
    d = Integer.MAX_VALUE;
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
   */
  public void agregaVecino(Vertice vec){
    if(!vecinos.contains(vec)){
      vecinos.add(vec);
    }
  }
  
  @Override
  public String toString(){
    return nombre;
  }
}
