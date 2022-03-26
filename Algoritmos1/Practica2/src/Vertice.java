
import java.util.LinkedList;

public class Vertice{
  LinkedList<Vertice> vecinos;
  String label; //Etiqueta o nombre del vértice.

  //Constructor
  public Vertice(String l){
    label = l;
    vecinos = new LinkedList<Vertice>();
  }
  
  //Redefinir la función equals
  @Override
  public boolean equals(Object o){
    boolean ret = false;
    if(o instanceof Vertice){
      Vertice comp = (Vertice)o;
      if(comp.label.equals(this.label)){
        ret = true;
      }
    }
    return ret;
  }
  
  //Agrega un vecino a este vértice.
  public void agregaVecino(Vertice vec){
    if(!vecinos.contains(vec)){
      vecinos.add(vec);
    }
  }
  
  @Override
  public String toString(){
    return label;
  }
}





