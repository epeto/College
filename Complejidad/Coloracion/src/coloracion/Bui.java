/*
 * Esta clase implementará la heurística Bui.
 */
package coloracion;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author emmanuel
 */
public class Bui {
    LinkedList<Integer> currentColors; //La lista de colores actual
    LinkedList<Point> currentColoring; //La coloración actual donde la primera entrada es el vértice y la segunda el color.
    LinkedList<Point> bestColoring; //Mejor coloración hasta el momento
    int bestNumColors; //La menor cantidad de colores encontrada hasta el momento
    int availableColors; //Colores disponibles
    int totalConflict; //Esta variable representa cuántos vértices tienen conflicto, i.e., cuántos son adyacentes a un color igual.
    Grafica grafo;
    int k; //Representará el número de colores obtenidos por el algoritmo MXRLF
    int nAnts; //Representa el número de hormigas en la gráfica.
    LinkedList<Ant> hormigas; //Lista de hormigas.
    int nCycles;
    int nMoves;
    int nChangeCycle;
    int nJoltCycles;
    
    public Bui(Grafica g){
        grafo = g;
        nAnts = (int)(grafo.getOrden()*0.2) + 1; //El número de hormigas en la gráfica será el 20% del número de vértices.
        currentColoring = new LinkedList();
        currentColors = new LinkedList();
        bestColoring = new LinkedList();
        
        for(Vertice v:grafo.verts){
            currentColoring.add(new Point(v.nombre,v.color));
            if(v.color>k){
                k=v.color;
            }
        }
        k++; //Lo incrementamos en 1 porque la coloración empieza en 0.
        
        hormigas = new LinkedList();
        for(int i=0;i<nAnts;i++){
            hormigas.add(new Ant(grafo.getOrden()/4));
        }
        
        nMoves = grafo.getOrden()/4;
        nChangeCycle = 20;
        nJoltCycles = grafo.getOrden()/2;
        nCycles = grafo.getOrden()*6;
    }
    
    /**
     * Ant-based algorithm for coloring graphs.
     */
    public void abac(){
        int itcc = 0; //Iterador de nChangeCycle
        int itjc = 0; //Iterador de nJoltCycles
        
        Random rn = new Random();
        for(int i=0;i<k;i++){
            currentColors.add(i);
        }
        
        bestColoring.addAll(currentColoring);
        bestNumColors = k;
        availableColors = (int)(k*0.8)+1;
        
        int betak;
        if(k%2==0){
            betak = k/2;
        }else{
            betak = (k/2) + 1;
        }
        
        LinkedList<Integer> listabeta = new LinkedList();
        
        //Seleccionamos βk clases de colores al azar
        for(int i=0;i<betak;i++){
            int tcc = currentColors.size(); //Tamaño de current coloring
            listabeta.add(currentColors.remove(rn.nextInt(tcc)));
        }
        
        //Renombrar estos colores con elementos del conjunto {0,...,βk-1}
        for(Vertice v:grafo.verts){
            //Si el color de v está en la lista, lo recoloreamos con el índice.
            //Si el color de v no está en la lista lo despintamos, i.e., le asignamos -1
            v.color = listabeta.indexOf(v.color);
        }
        
        for(int i=0;i<listabeta.size();i++){
            listabeta.set(i, i); //Renombramos todos los elementos de la lista.
        }
        
        //Colorear los vértices no coloreados usando [ɣk] clases de colores de manera aleatoria.
        int gammak = (int)(0.7*k) + 1;
        
        for(Vertice v:MXRLF.noPintados(grafo)){ //Por cada vértice no pintado
            v.color = rn.nextInt(gammak); //Se pinta con un color aleatorio entre 0 y ɣk-1
        }
        
        LinkedList<Vertice> vertemp = new LinkedList(); //Esto servirá para distribuir los vértices.
        vertemp.addAll(grafo.verts);
        //Distribuir nAnts de manera aleatoria en los vértices de G.
        for(Ant a:hormigas){
            int pos = rn.nextInt(vertemp.size());
            a.setVertice(vertemp.remove(pos));
        }
        
        for(int c=0;c<nCycles;c++){
            for(Ant ant:hormigas){
                for(int m=0;m<nMoves;m++){
                    //La hormiga colorea su vértice actual
                    ant.colorea(availableColors);
                    //La hormiga se mueve
                    ant.mueve();
                }
            }
            updateTC(); //Actualizamos el conflicto total de la gráfica.
            if(totalConflict == 0 && bestNumColors>availableColors){
                actualizaCC(); //Actualizamos la coloración.
                bestColoring.clear();
                bestColoring.addAll(currentColoring);
                bestNumColors = availableColors;
                availableColors--;
                //Como ya mejoró availableColors entonces reiniciamos los iteradores.
                itcc = 0;
                itjc = 0;
            }
            
            if(itcc>=nChangeCycle){
                availableColors++;
                itcc = 0;
            }
            
            if(itjc>=nJoltCycles){
                jolt();
                itjc = 0;
            }
            
            itcc++;
            itjc++;
        }//Aquí termina nCycles
    }
    
    /**
     * Esta función recolorea toda la gráfica usando bestColoring.
     * Es decir, pinta la gráfica con la mejor coloración obtenida hasta el momento.
     */
    public void repintaGraf(){
        for(Point p:bestColoring){
            grafo.verts.get(p.x).color = p.y;
        }
    }
    
    /**
     * Esta función redistribuye a las hormigas en el tablero.
     */
    public void jolt(){
        LinkedList<Vertice> temp = new LinkedList();
        temp.addAll(grafo.verts);
        Random rn = new Random();
        
        for(Ant ant:hormigas){
            int lim = temp.size();
            int az = rn.nextInt(lim);
            ant.vp = temp.remove(az);
        }
    }
    
    /**
     * Función que actualiza la coloración actual, es decir currentColoring.
     */
    public void actualizaCC(){
        currentColoring.clear();
        
        for(Vertice v:grafo.verts){
            currentColoring.add(new Point(v.nombre,v.color));
        }
    }
    
    /**
     * Función que actualiza la variable "totalConflict"
     */
    public void updateTC(){
        totalConflict = 0;
        
        for(Vertice vertice:grafo.verts){
            vertice.conflicto=0;
            for(Vertice vecino:vertice.vecinos){
                if(vertice.color == vecino.color){ //Si el color del vértice es igual al color de su vecino se incrementa la variable.
                    totalConflict++;
                    vertice.conflicto++;
                }
            }
        }
    }
}
