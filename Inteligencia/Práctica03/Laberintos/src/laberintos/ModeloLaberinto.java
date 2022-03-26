/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laberintos;

import java.util.*;


public class ModeloLaberinto {
    	int ancho,  alto;  // Cantidad de celdas a lo largo y ancho de la cuadrícula.
	int tam;  // Tamaño en pixeles de cada celda.
	Celda[][] mundo;  // Mundo de celdas.
	Stack<Celda> p; //pila de celdas para el back tracking.
	Laberinto t; //Representa al cuadro rojo que va a construir el laberinto.
	Random rnd = new Random ();  // Auxiliar para decisiones aleatorias.
        boolean terminado; //Verdadero si ya terminó de construir el laberinto.

	/**
	 * Constructor del modelo
	 * @param ancho Cantidad de celdas a lo ancho en la cuadricula.
	 * @param ancho Cantidad de celdas a lo largo en la cuadricula.
	 * @param tam Tamaño de cada celda que compone la cuadricula.
	 */ 
	ModeloLaberinto (int ancho,  int alto,  int tam){
	    //Inicializamos las dimensiones.
	    this.ancho = ancho;
	    this.alto = alto;
	    this.tam = tam;
	    //Creamos el grid
	    mundo = new Celda[ancho][alto];
	    //Agregamos las celdas
	    for (int i = 0; i < ancho; i++)
		for (int j = 0; j < alto; j++)
		    mundo[i][j] = new Celda (i, j, true);
	    t = new Laberinto (0, 0);
	    mundo[0][0].estado = false;
	    p = new Stack();
            p.push (mundo[0][0]);
            terminado = p.isEmpty();
	}

	/**
	 * Mueve a la celda señalada la direccion dada.
	 * Se asume que es posible moverse a la casilla en la dirección dada.
	 * @param direccion La direccion en la que se desea en el laberinto.
	 */
	void moverLaberinto (int direccion){
	    //Guardamos la celda actual
            p.push (mundo[t.posX][t.posY]);
	    switch (direccion) {
		//movimiento hacia arriba
	    case 0:  t.posY--;
		break;
		//movimiento hacia abajo
	    case 1:  t.posY++;
		break;
		//movimiento hacia la izquierda
	    case 2:  t.posX--;
		break;
		//movimiento hacia la derecha
	    case 3:  t.posX++;
	    }
	    //La nueva celda ya es visitada (no disponible)
	    mundo[t.posX][t.posY].estado = false;
	}
      
	/**
	 * Nos dice si la celda vecina a la posición actual
	 * indicada por la dirección dada está disponible.
	 * @param dir La dirección en la que queremos ver si nos podemos mover.
	 */
        boolean vecinoDisponible (int dir){
	    //Cuidamos los límites.
	    switch (dir){
	    case 0: if (t.posY-1 < 0) return false;
		return mundo[t.posX][t.posY-1].estado;
	    case 1: if (t.posY+1 >= alto) return false;
		return mundo[t.posX][t.posY+1].estado;
	    case 2: if (t.posX-1 < 0) return false;
		return mundo[t.posX-1][t.posY].estado;
	    case 3: if (t.posX+1 >= ancho) return false;
		return mundo[t.posX+1][t.posY].estado;
	    }

	    return false;
        }
        
        /**
         * Esto es para verificar si el agente puede avanzar hacia la siguiente casilla.
         * @param dir
         * @return 
         */
        boolean vecinoDisponible2 (int x, int y, int dir){
	    //Cuidamos los límites.
	    switch (dir){
	    case 0: if (y-1 < 0) return false;
		return !mundo[x][y-1].visitoAgente;
	    case 1: if (y+1 >= alto) return false;
		return !mundo[x][y+1].visitoAgente;
	    case 2: if (x-1 < 0) return false;
		return !mundo[x-1][y].visitoAgente;
	    case 3: if (x+1 >= ancho) return false;
		return !mundo[x+1][y].visitoAgente;
	    }

	    return false;
        }
      
	/**
	 * "Tumba" el muro de la celda de la que nos movimos en el
	 * laberinto dependiendo la dirección.
	 * @param cel La celda de la que nos movimos.
	 * @param direccion La dirección en la que nos movimos.
	 */
        public void tumbaMuro (Celda cel,  int direccion){
            switch (direccion){
	    case 0: cel.pa = false;
		break;
	    case 1: cel.pab = false;
		break;
	    case 2: cel.pi = false;
		break;
	    case 3: cel.pd = false;
            }            
        }

	/**
	 * "Tumba" los muros de celdas adyacentes
	 * pues tumbaMuro () solo elimina los muros de una
	 * celda. Se asume que es posible el movimiento
	 * de la celda.
	 * @param direccion La dirección en la que nos movimos.
	 */
        public void tumbaMuros (int direccion){
	    //Celda actual
	    tumbaMuro (mundo[t.posX][t.posY], direccion);
	    //Celda vecina
            switch (direccion){
	    case 0: tumbaMuro (mundo[t.posX][t.posY-1], 1);
		break;
	    case 1: tumbaMuro (mundo[t.posX][t.posY+1], 0);
		break;
	    case 2: tumbaMuro (mundo[t.posX-1][t.posY], 3);
		break;
	    case 3: tumbaMuro (mundo[t.posX+1][t.posY], 2);
            }
        }
        
	/**
	 * Realiza el recorrido con backtrack en el laberinto.
	 */
        public void backTrack (){
            LinkedList<Integer> v = new LinkedList<> (); //Lista de vecinos.
            boolean mover = false;
            int r = 0;
            while (!mover && v.size () < 4){
                r = rnd.nextInt (4);
		//Hay un vecino disponible.
		//Nos podemos mover.
                if (vecinoDisponible (r))
		    mover = true;
                if (!v.contains (r))
                    v.add (r);
	    }
	    if (mover){
		//Nos movemos a la siguiente dirección.
		tumbaMuros (r);
		moverLaberinto (r);
	    }else if (!p.empty ()){
		//Nos movemos a la celda previa
		//pues no hay vecnos a los que movernos
		Celda a = p.pop ();
		t.posY = a.celdaY;
		t.posX = a.celdaX;                
	    }
            
            terminado = p.isEmpty();
	}
}
