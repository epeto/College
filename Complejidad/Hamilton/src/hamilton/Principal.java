
package hamilton;

import java.util.Random;
import processing.core.PApplet;
import java.awt.Point;
import java.util.List;
import java.util.Scanner;

/**
 * @author emmanuel
 */
public class Principal extends PApplet{

    GrafoAbs ejemplar;
    int tam=600; //Tamaño de un lado de la ventana.
    List<Integer> cam; //Camino a verificar.
    boolean esHamiltoniano; //Dice si el camino elegido hasta el momento es un ciclo hamiltoniano.
    boolean detenerse=false; //Esta variable decide en qué momento el programa debe detenerse.
    
    @Override
    public void settings(){
        size(tam,tam);
    }
    
    @Override
    public void setup(){
        frameRate(1); //cuadros por segundo.
        background (115, 198, 182); //Define el color
        ejAleatorio(); //Crea la gráfica aleatoria.
        
        int ord = ejemplar.orden;
        
        for(int i=0;i<ord;i++){
            float corx = (float)(tam/2+tam*0.4*Math.cos(2*Math.PI*i/ord));
            float cory = (float)(tam/2+tam*0.4*Math.sin(2*Math.PI*i/ord));
            ejemplar.coordenadas.add(new Point((int)corx,(int)cory));
            ejemplar.visitados.add(false);
        }
        
        cam = Adivinar.getCaminoAzar(ejemplar.orden);
        esHamiltoniano = Adivinar.verificador(ejemplar, cam);
    }
    
    @Override
    public void draw(){
        if(!detenerse){
        fill(255,255,255);
        rect(5,5,200,30);
        fill(0,0,0);
        text(cam.toString(),10,20); //Dibuja el camino en forma de lista.
        
        strokeWeight(2); //grosor de línea 2
        stroke(0,0,0); //Color de línea: negro
        //Dibuja aristas negras.
        for(int i=0;i<ejemplar.orden;i++){
            for(int j=0;j<=i;j++){
                if(ejemplar.matriz[i][j]==1){
                    int x1 = ejemplar.coordenadas.get(i).x;
                    int y1 = ejemplar.coordenadas.get(i).y;
                    int x2 = ejemplar.coordenadas.get(j).x;
                    int y2 = ejemplar.coordenadas.get(j).y;
                    line(x1,y1,x2,y2);
                }
            }
        }
        
        fill(255,255,255); //Color de relleno: blanco
        for(Point p: ejemplar.coordenadas){
            ellipse(p.x,p.y,20,20); //Dibuja los nodos
        }
        
        fill(0,0,0);
        for(int l=0;l<ejemplar.orden;l++){
            text("v"+l,ejemplar.coordenadas.get(l).x-5,ejemplar.coordenadas.get(l).y+5);
        }
        
        if(esHamiltoniano){
            fill(255,255,255);
            rect(440,10,150,30);
            fill(0,0,0);
            text("Es hamiltoniano",450,30);
            detenerse = true; //Si es hamiltoniano, se detiene
        }else{
            fill(255,255,255);
            rect(440,10,150,30);
            fill(0,0,0);
            text("No es hamiltoniano",450,30);
        }
        
        cam = Adivinar.getCaminoAzar(ejemplar.orden);
        esHamiltoniano = Adivinar.verificador(ejemplar, cam);
        }
    }//fin draw
    
    /**
     * Función que crea un ejemplar aleatorio.
     */
    public void ejAleatorio(){
        Random rn = new Random();
        int ord = rn.nextInt(6)+5;
        ejemplar = new GrafoAbs(ord);
        int dec;
        
        for(int i=0;i<ord;i++){
            for(int j=0;j<=i;j++){
                dec = rn.nextInt(3);
                if(dec == 0 && i!=j){
                    ejemplar.addArista(i, j);
                }
            }
        }
    }
    
    /**
     * Crea un ejemplar sencillo (un pentágono).
     */
    public void ejFijo(){
        ejemplar = new GrafoAbs(5);
        
        for(int a=0;a<4;a++){
            ejemplar.addArista(a, a+1);
        }
        ejemplar.addArista(4,0);
    }
    
    public static void main(String[] args) {
        System.out.println("Calculando ciclo...");
        Principal p = new Principal();
        p.main("hamilton.Principal");
    }
    
}