/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coloracion;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Random;
import processing.core.*;

/**
 *
 * @author emmanuel
 */
public class Coloracion extends PApplet {

    Grafica grafo;
    int tam=600; //Tamaño de un lado de la ventana.
    LinkedList<Color> colores; //Lista de colores en formato java.
    
    @Override
    public void settings(){
        size(tam,tam);
    }
    
    @Override
    public void setup(){
        frameRate(1); //cuadros por segundo.
        background(255,255,255);
        colores = new LinkedList();
        creaGrafAl(); //Creamos la gráfica.
        for(int i=0;i<grafo.getOrden();i++){
            colores.add(creaColorAl());
        }
    }
    
    @Override
    public void draw(){
        MXRLF.colorea(grafo); //Primero ejecutamos el algoritmo MXRLF
        Bui bui = new Bui(grafo);
        bui.abac(); //Ejecutamos la heuristica
        bui.repintaGraf(); //Pintamos la gráfica con la mejor coloración obtenida.
        dibujaGrafica();
    }
    
    /**
     * Crea un color aleatorio.
     * @return color
     */
    public Color creaColorAl(){
        Random rn = new Random();
        int r = rn.nextInt(256);
        int g = rn.nextInt(256);
        int b = rn.nextInt(256);
        
        return new Color(r,g,b);
    }
    
    /**
     * Función que crea una gráfica aleatoria.
     */
    public void creaGrafAl(){
        Random rn = new Random();
        int ord = rn.nextInt(41)+10; //El orden de la gráfica estará entre 10 y 50.
        int k = rn.nextInt(6)+5; //Será una gráfica k-partita con 5<=k<=10.
        grafo = new Grafica(ord,tam); //Aquí creamos la gráfica.
        
        //Esta parte es para asegurarnos de que cada partición tendrá al menos un vértice.
        for(int i=0;i<k;i++){
            grafo.verts.get(i).part = i;
        }
        
        //En esta parte distribuimos de manera aleatoria el resto de los vértices en las demás particiones.
        for(int i=k;i<grafo.verts.size();i++){
            int p = rn.nextInt(k); //p es la partición a la que se va a agregar el vértice i.
            grafo.verts.get(i).part = p;
        }
        
        //En esta parte agregamos las aristas con una probabilidad de un 1/6 y solo si los vértices no están en la misma partición.
        for(int i=0;i<grafo.verts.size();i++){
            for(int j=i;j<grafo.verts.size();j++){
                int prob = rn.nextInt(6);
                if(prob==0){
                    if(grafo.verts.get(i).part!=grafo.verts.get(j).part){
                        grafo.addArista(i,j); //Agregamos la arista.
                    }
                }
            }
        }
    }
    
    public void dibujaGrafica(){
         
        for(Vertice v:grafo.verts){
            for(Vertice vec:v.vecinos){
                line(v.cor.x,v.cor.y,vec.cor.x,vec.cor.y);
            }
        }
         
        for(Vertice v:grafo.verts){
            Color c = colores.get(v.color);
            fill(c.getRed(),c.getGreen(),c.getBlue());
            ellipse(v.cor.x,v.cor.y,30,20);
            fill(0,0,0);
            text(v.toString(),v.cor.x-10,v.cor.y+5);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       PApplet.main("coloracion.Coloracion");
    }
    
}
