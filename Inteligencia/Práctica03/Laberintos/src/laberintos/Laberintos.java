/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laberintos;

import processing.core.PApplet;
import processing.core.PFont;

public class Laberintos extends PApplet{

    PFont fuente;  // Fuente para mostrar texto en pantalla
    
    // Propiedades del modelo de laberinto.
    int alto = 20;         // Altura  (en celdas) de la cuadricula.
    int ancho = 20;        // Anchura  (en celdas) de la cuadricula.
    int celda = 25;          // Tamaño de cada celda cuadrada  (en pixeles).
    ModeloLaberinto modelo; 
    Agente bolita; //Agente que va a recorrer el laberinto.
    
    @Override
    public void setup () {
        frameRate (15); //Define cuántos frames por segundo se van a ejecutar.
        background (115, 198, 182); //Define el color
        fuente = createFont ("Arial", 12, true);
        modelo = new ModeloLaberinto (ancho, alto, celda);
        bolita = new Agente(0.7f,modelo);
    }
    
    @Override
    public void settings(){
        size (ancho*celda,  alto*celda);
    }
    
    @Override
    public void draw () {
        background (115, 198, 182);
        strokeWeight(1);
        if(!modelo.terminado){ //Si no ha terminado dibuja el cuadrito rojo.
            fill (255,  0,  0);
            stroke(255, 0, 0);
            rect (modelo.t.posX*modelo.tam,  modelo.t.posY*modelo.tam,  modelo.tam,  modelo.tam); 
        }else{
            stroke(144, 12, 63);
            fill (144, 12, 63);
            rect (0, 0, modelo.tam, modelo.tam);
            rect ((ancho-1)*modelo.tam, (alto-1)*modelo.tam, modelo.tam, modelo.tam);
            fill (255, 255, 255);
            float tam2 = modelo.tam;
            textFont(fuente);
            text("Inicio", 1, tam2/2+1);
            text("Final", (ancho-1)*tam2+1, (alto-1)*tam2 + tam2/2+1);
            fill(211, 84, 0);
            ellipse(bolita.corX, bolita.corY, bolita.diam, bolita.diam);
        }
        
        stroke(0,0,0);
        strokeWeight(1.7f);
        for (int i = 0; i < ancho; i++)
	    for (int j = 0; j < alto; j++){
		if (modelo.mundo[i][j].pa) //Si la pared de arriba existe, dibujarla.
		    line (i*modelo.tam, j*modelo.tam,  (i+1)*modelo.tam, j*modelo.tam);
		if (modelo.mundo[i][j].pd) //Si la pared de la derecha existe, dibujarla.
		    line ( (i+1)*modelo.tam, j*modelo.tam,  (i+1)*modelo.tam,  (j+1)*modelo.tam);
		if (modelo.mundo[i][j].pab) //Si la pared de abajo existe, dibujarla.
		    line (i*modelo.tam,  (j+1)*modelo.tam,  (i+1)*modelo.tam,  (j+1)*modelo.tam);
		if (modelo.mundo[i][j].pi) //Si la pared de la izquierda existe, dibujarla.
		    line (i*modelo.tam, j*modelo.tam, i*modelo.tam,  (j+1)*modelo.tam);              
	    }
        
        if(!modelo.terminado){ 
            modelo.backTrack ();
        }else{
            bolita.buscaFinal();
        }
        
        //Si la bolita llegó al final, pintamos la ruta.
        if(bolita.enFin){
            float x1;
            float y1;
            float x2;
            float y2;
            stroke(169, 50, 38);
            strokeWeight(6);
            for(int i=0; i<bolita.ruta.size()-1;i++){
                x1 = bolita.ruta.get(i).celdaX*bolita.tam2+bolita.tam2/2;
                y1 = bolita.ruta.get(i).celdaY*bolita.tam2+bolita.tam2/2;
                x2 = bolita.ruta.get(i+1).celdaX*bolita.tam2+bolita.tam2/2;
                y2 = bolita.ruta.get(i+1).celdaY*bolita.tam2+bolita.tam2/2;
                line(x1,y1,x2,y2);
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       PApplet.main (new String[] { "laberintos.Laberintos" });
    }
    
}
