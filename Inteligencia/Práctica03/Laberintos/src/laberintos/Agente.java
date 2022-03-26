/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laberintos;

import java.util.*;

/**
 * Agente que buscará el camino de la casilla de inicio a la casilla final.
 */
public class Agente {
    ModeloLaberinto lab; //Laberinto sobre el que se va a mover.
    int poX; //Posición en X en el tablero.
    int poY; //Posición en Y en el tablero.
    float corX; //Coordenada X en la ventana.
    float corY; //Coordenada Y en la ventana.
    float diam; //Diámetro del círculo.
    float tam2; //El tamaño en pixeles de cada casilla.
    Random rn;
    boolean enFin; //nos dice si el agente ya llegó a la casilla final.
    Stack<Celda> ruta; //La pila guardará la ruta desde la casilla inicial hasta la final.
    
    Agente(float fD, ModeloLaberinto modelo){
        poX = 0;
        poY = 0;
        lab = modelo;
        tam2 = lab.tam;
        corX = tam2/2;
        corY = tam2/2;
        diam = tam2*fD;
        rn = new Random();
        enFin = false;
        ruta = new Stack();
        lab.mundo[0][0].visitoAgente=true;
        ruta.push(lab.mundo[0][0]);
    }
    
    /**
     * A partir de la posición del agente verifica las casillas a las que se puede mover.
     * 0 = norte, 1 = sur, 2 = oeste, 3 = este.
     * @return 
     */
    public LinkedList<Integer> movimientosDisponibles(){
        LinkedList<Integer> lista = new LinkedList();
        if(!lab.mundo[poX][poY].pab && lab.vecinoDisponible2(poX, poY, 1)) lista.add(1);
        if(!lab.mundo[poX][poY].pd && lab.vecinoDisponible2(poX, poY, 3)) lista.add(3);
        if(!lab.mundo[poX][poY].pa && lab.vecinoDisponible2(poX, poY, 0)) lista.add(0);
        if(!lab.mundo[poX][poY].pi && lab.vecinoDisponible2(poX, poY, 2)) lista.add(2);
        
        return lista;
    }
    
    /**
     * Mueve al agente en la dirección seleccionada.
     * @param dir : dirección
     */
    public void mueve(int dir){
        switch(dir){
            case 0: if(poY>0){
                        poY--;
                        corY-=tam2;}
            break;
            case 1: if(poY<lab.alto){
                        poY++;
                        corY+=tam2;}
            break;
            case 2: if(poX>0){
                        poX--;
                        corX-=tam2;}
            break;
            case 3: if(poX<lab.ancho){
                        poX++;
                        corX+=tam2;}
            break;
            default : System.out.println("Movimiento no válido");
            break;
        }
    }
    
    public void mueveAzar(){
        LinkedList<Integer> l = movimientosDisponibles();
        if(!l.isEmpty()){
            int direccion = l.get(rn.nextInt(l.size()));
            mueve(direccion);
        }else{
            System.out.println("No se puede mover");
        }
    }
    
    /**
     * Algoritmo que busca la casilla final.
     */
    public void buscaFinal(){
        if(!enFin){
            LinkedList<Integer> ldisp = movimientosDisponibles();
            if(!ldisp.isEmpty()){
                int sig = ldisp.get(0); //Sacamos la primera casilla disponible.
                mueve(sig); //Nos movemos a esa casilla.
               
                if((poX == (lab.ancho-1)) && (poY == (lab.alto-1))){
                    enFin = true;
                }
               
                lab.mundo[poX][poY].visitoAgente = true; //Marcamos la casilla como visitada
                ruta.push(lab.mundo[poX][poY]); //Insertamos la casilla en la pila.
            }else{
                //Si no hay casillas disponibles, retrocedemos en el camino.
                Celda anterior = ruta.peek();
                poX = anterior.celdaX;
                poY = anterior.celdaY;
                corX = anterior.celdaX*tam2 + tam2/2;
                corY = anterior.celdaY*tam2 + tam2/2;
                if(movimientosDisponibles().isEmpty()){
                    ruta.pop();
                }
            }
        }
    }
}
