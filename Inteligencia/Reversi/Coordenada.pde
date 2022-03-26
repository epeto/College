/*
  Clase que usaremos para guardar las coordenadas de las casillas que sean validas para el tiro
*/
public class Coordenada{
   int x; // coordenada que representa los renglones de  una matriz
   int y; // coordenada que representa las columnas de una matriz
   
   public Coordenada(int x , int y){
      this.x = x;
      this.y = y;
   }
   
   public int getX(){
     return x;
   }
   
   public int getY(){
    return y; 
   }
   
   @Override
   public String toString(){
     return "x: "+x+", "+"y: "+y;
   }
   @Override
   public boolean equals(Object obj){
     Coordenada cor = (Coordenada)obj;
     return this.getX() == cor.getX() && this.getY() == cor.getY();
   }
}