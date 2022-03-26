/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viajero;

import java.util.ArrayList;
import java.util.Random;
import processing.core.PApplet;

/**
 *
 * @author emmanuel
 */
public class Principal extends PApplet {

    GrafoAbs ejemplar;
    int tam=600; //Tamaño de un lado de la ventana.
    ArrayList<Vertice> tour;
    
    @Override
    public void settings(){
        size(tam,tam);
    }
    
    @Override
    public void setup(){
        frameRate(1); //cuadros por segundo.
        background (229,152,102); //Define el color de fondo.
        Random rn = new Random();
        ejemplar = new GrafoAbs(rn.nextInt(6)+5);
        ejemplar.llenarCoordenadasAzar(tam);
        ejemplar.llenarMatriz();
        Prim.MST_Prim(ejemplar, 0); //Aplicamos el algoritmo de Prim a partir del vértice 0.
        tour = DFS.dfs(ejemplar, 0);
    }
    
    int it=0; //Variable que va a iterar sobre la lista de vértices para dibujar el árbol.
    int it2=0; //Variable que va a iterar sobre el tour.
    @Override
    public void draw(){
        strokeWeight(2); //grosor de línea 2
        stroke(0,0,0); //Color de línea: negro
        
        fill(255,255,255); //Color de relleno: blanco
        for(Vertice p: ejemplar.vers){
            fill(255,255,255); //Color de relleno: blanco
            ellipse(p.x,p.y,20,20); //Dibuja los nodos
            fill(0,0,0); //Color de relleno: negro
            text(p.etiqueta,p.x-7,p.y+5);
        }
        
        //Esta parte dibuja el árbol
        if(it<ejemplar.orden){ //Si it es menor que el orden y el vértice it tiene padre.
            int x1 = ejemplar.vers.get(it).x;
            int y1 = ejemplar.vers.get(it).y;
            
            if(ejemplar.vers.get(it).padre != null){
                int x2 = ejemplar.vers.get(it).padre.x;
                int y2 = ejemplar.vers.get(it).padre.y;
                line(x1,y1,x2,y2);
            }
            it++;
        }
        
        //Cuando termine de dibujar el árbol detendré la animación 5 segundos.
        //Posteriormente empezará a dibujar el tour.
        if(it2==1 && it==ejemplar.orden){
            delay(5000);
        }
        
        //Esta parte dibuja el tour
        if(it>=ejemplar.orden && it2<tour.size()){
            int x1 = tour.get(it2).x;
            int y1 = tour.get(it2).y;
            int x2 = tour.get((it2+1)%tour.size()).x;
            int y2 = tour.get((it2+1)%tour.size()).y;
            stroke( 169, 50, 38 ); //Color de línea: rojo
            line(x1,y1,x2,y2);
            
            int xm = (x1+x2)/2;
            int ym = (y1+y2)/2;
            float peso = (float)ejemplar.matriz[tour.get(it2).name][tour.get((it2+1)%tour.size()).name];
            text(peso,xm,ym);
            
            it2++;
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PApplet.main("viajero.Principal");
    }
    
}
