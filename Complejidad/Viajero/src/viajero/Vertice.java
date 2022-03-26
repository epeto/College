/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viajero;

import java.awt.Point;
import java.util.ArrayList;
/**
 *
 * @author emmanuel
 */
public class Vertice implements Comparable{
    public String etiqueta;
    public Integer name;
    public int x=0,y=0;
    public Vertice padre; //El padre después de aplicar el algoritmo de Prim
    public ArrayList<Vertice> hijos; //Los hijos después de aplicar el algoritmo de Prim.
    public Double key;
     
    public Vertice(Integer nombre, Point p){
        etiqueta="V"+nombre.toString();
        name=nombre;
        this.x=(int)p.getX();
        this.y=(int)p.getY();
        key=Double.MAX_VALUE;
        hijos = new ArrayList();
    }
     
    @Override
    public boolean equals(Object o){
        if(o instanceof Vertice){
            Vertice ob = (Vertice)o;
            return this.name==ob.name;
        }
        return false;
    }
 
    @Override
    public int compareTo(Object v) {
        Vertice vComp;
        if(v instanceof Vertice)
            vComp = (Vertice)v;
        else
            throw new IllegalArgumentException("El agrumento no es un vértice");
         
        if(this.key < vComp.key)
            return -1;
        if(this.key == vComp.key)
            return 0;
        if(this.key > vComp.key)
            return 1;
        return 0;
    }
    
    @Override
    public String toString(){
        return etiqueta;
    }
}
