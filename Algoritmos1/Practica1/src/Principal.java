

import java.awt.Color;
import processing.core.PApplet;
import java.util.Scanner;

/**
 *
 * @author emmanuel
 */
public class Principal extends PApplet{

    int tam=600; //Tamaño de un lado de la ventana.
    Rellena piso;
    public static int px = 1; //Posición en x de la casilla especial.
    public static int py = 2; //Posición en y de la casilla especial.
    public static int pot = 3;
    boolean inicio=false;
    
    public void setPotencia(int p){
        pot = p;
    }
    
    public void setCx(int cx){
        px = cx;
    }
    
    public void setCy(int cy){
        py = cy;
    }
    
    @Override
    public void settings(){
        size(tam,tam);
    }
    
    @Override
    public void setup(){
        frameRate(1); //cuadros por segundo.
        background (255, 255, 255); //Define el color de fondo
        piso = new Rellena(pot);
        piso.rellenaMatriz(px, py);
        piso.coloresAleatorios();
        piso.imprimeMatriz();
    }
    
    @Override
    public void draw(){
        colorea();
        raya();
    }
    
    //Dibuja el contorno de las casillas.
    public void raya(){
        float tamCasilla = tam/piso.terreno.length; //Tamaño de un lado de un cuadrito.
        float x1,y1,x2,y2;
        for(int i=0;i<piso.terreno.length;i++){
            for(int j=0;j<piso.terreno[0].length;j++){
                strokeWeight(2);
                
                if(i+1<piso.terreno.length && piso.terreno[i][j]!=piso.terreno[i+1][j]){
                    x1 = (i+1)*tamCasilla;
                    y1 = j*tamCasilla;
                    x2 = (i+1)*tamCasilla;
                    y2 = (j+1)*tamCasilla;
                    line(x1,y1,x2,y2);
                }
                
                if(j+1<piso.terreno[0].length && piso.terreno[i][j]!=piso.terreno[i][j+1]){
                    x1 = i*tamCasilla;
                    y1 = (j+1)*tamCasilla;
                    x2 = (i+1)*tamCasilla;
                    y2 = (j+1)*tamCasilla;
                    line(x1,y1,x2,y2);
                }
                
                if(piso.terreno[i][j]==0){
                    fill(0,0,0);
                    rect(i*tamCasilla,j*tamCasilla,tamCasilla,tamCasilla);
                }
            }
        }
    }
    
    //Pinta las casillas.
    public void colorea(){
        float tamCasilla = tam/piso.terreno.length; //Tamaño de un lado de un cuadrito.
        
        for(int i=0;i<piso.terreno.length;i++){
            for(int j=0;j<piso.terreno[0].length;j++){
                int indC = piso.terreno[i][j]; //Índice del color
                Color tempC = piso.colores[indC];
                strokeWeight(0);
                fill(tempC.getRed(),tempC.getGreen(),tempC.getBlue());
                
                rect(i*tamCasilla,j*tamCasilla,tamCasilla,tamCasilla);
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingresa la potencia del lado del piso");
        pot = sc.nextInt();
        System.out.println("Ingresa la coordenada x");
        px = sc.nextInt();
        System.out.println("Ingresa la coordenada y");
        py = sc.nextInt();        
        PApplet.main("Principal");
    }
    
}

