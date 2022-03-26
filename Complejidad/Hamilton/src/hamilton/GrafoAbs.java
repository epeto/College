/*
 * Esta clase representa a una gráfica abstracta.
 * Representada con una matriz de adyacencia.
 */
package hamilton;

import java.util.ArrayList;
import java.awt.Point;

/**
 *
 * @author emmanuel
 */
public class GrafoAbs {
    int[][] matriz;
    //Lista que almacena las coordenadas de cada vértice.
    ArrayList<Point> coordenadas;
    //Lista que verifica si un nodo ya ha sido visitado.
    ArrayList<Boolean> visitados;
    //Orden de la gráfica (número de vértices)
    int orden;
    
    /**
     * Constructor que recibe el órden de la gráfica (número de vértices).
     * @param ord 
     */
    public GrafoAbs(int ord){
        matriz = new int[ord][ord];
        coordenadas = new ArrayList();
        visitados = new ArrayList();
        orden = ord;
    }
    
    /**
     * Función que agrega una arista del vértice 'a' al vértice 'b'.
     * @param a
     * @param b 
     */
    public void addArista(int a, int b){
        //Debido a que es no dirigida, la matriz será simétrica.
        matriz[a][b] = 1;
        matriz[b][a] = 1;
    }
    
    /**
     * Función que recibe un vértice y devuelve una lista de sus vecinos.
     * @param vert
     * @return 
     */
    public ArrayList<Integer> getVecinos(int vert){
        ArrayList<Integer> lista = new ArrayList();
        for(int i=0;i<orden;i++){
            if(matriz[vert][i]==1){
                lista.add(i);
            }
        }
        return lista;
    }
    
    /**
     * Función que recibe 2 vértices y dice si son adyacentes.
     * @param v1 vértice 1
     * @param v2 vértice 2
     * @return verdadero si son adyacentes, falso si no.
     */
    public boolean sonAdyacentes(int v1, int v2){
        return matriz[v1][v2] == 1;
    }
    
    public void imprimeMat(){
        for(int i=0;i<orden;i++){
            for(int j=0;j<orden;j++){
                System.out.print(matriz[i][j]+" ");
            }
            System.out.println();
        }
    }
}