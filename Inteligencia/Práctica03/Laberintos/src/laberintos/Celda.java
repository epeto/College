/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laberintos;

public class Celda {
    	int celdaX,  celdaY;
	boolean estado;
        //nos dice si el agente ya visitó esta casilla.
        boolean visitoAgente;
	//Paredes de la celda:
	//pa: arriba,  pab: abajo,  pi: izquierda,  pd: derecha
	boolean pa, pab, pi, pd;

	/**
	 * Constructor de una celda. Por default
	 * todas sus paredes se dibujan.
	 * @param celdaX Coordenada en x
	 * @param celdaY Coordenada en y
	 * @param estado True para casilla que no ha sido visitada.
	*/
	Celda (int celdaX,  int celdaY,  boolean estado){
	    this.celdaX = celdaX;
	    this.celdaY = celdaY;
	    this.estado = estado;
            visitoAgente = false;
	    this.pa = true;
	    this.pab = true;
	    this.pi = true;
	    this.pd = true;
	}
        
        @Override
        public String toString(){
            return "("+celdaX+", "+celdaY+")";
        }
}
