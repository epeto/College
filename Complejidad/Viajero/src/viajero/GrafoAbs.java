/*
 * 
 */
package viajero;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author emmanuel
 */
public class GrafoAbs {
    double[][] matriz; //Representación de la gráfica con matriz de adyacencias.
    int orden; //El número de vértices de la gráfica.
    ArrayList<Vertice> vers; //Conjunto de vértices
    
    /**
     * Constructor que recibe el órden de la gráfica (número de vértices).
     * @param ord 
     */
    public GrafoAbs(int ord){
        matriz = new double[ord][ord];
        orden = ord;
        vers = new ArrayList();
    }
    
    /**
     * Constructor que recibe una gráfica y la copia.
     * @param grafica2
     */
    public GrafoAbs(GrafoAbs grafica2){
        matriz = new double[grafica2.orden][grafica2.orden];
        orden = grafica2.orden;
        
        for(int i=0;i<grafica2.orden;i++){
            for(int j=0;j<grafica2.orden;j++){
                matriz[i][j] = grafica2.matriz[i][j];
            }
        }
        
        vers.addAll(grafica2.vers);
    }
    
    /**
     * Crea puntos aleatorios en un plano y los agrega a la lista "coordenadas".
     * @param tam tamaño de la ventana.
     */
    public void llenarCoordenadasAzar(int tam){
        Random rn = new Random();
        
        for(int i=0;i<orden;i++){
            Point punto = new Point(rn.nextInt(tam),rn.nextInt(tam));
            vers.add(new Vertice(i,punto));
        }
    }
    
    /**
     * Llena la matriz de adyacencias con las distancias entre las coordenadas.
     * Primero debe tener inicializada la lista de coordenadas.
     */
    public void llenarMatriz(){
        double dist; //Variable que almacenará la distancia.
        for(int i=0;i<orden;i++){
            for(int j=i+1;j<orden;j++){
                dist = Math.sqrt((vers.get(i).x-vers.get(j).x)*(vers.get(i).x-vers.get(j).x)+(vers.get(i).y-vers.get(j).y)*(vers.get(i).y-vers.get(j).y));
                matriz[i][j]=dist;
                matriz[j][i]=dist;
            }
        }
    }
    
    public void imprimeMat(){
        for(int i=0;i<orden;i++){
            for(int j=0;j<orden;j++){
                System.out.print(matriz[i][j]+" ");
            }
            System.out.println();
        }
    }
    
    /**
     * Obtiene los vecinos del vértice v
     * @param v vértice al cual se le calculará la vecindad
     * @return lista de vecinos de v
     */
    public ArrayList<Vertice> getVecinos(Vertice v){
        ArrayList<Vertice> lista = new ArrayList();
        
        for(Vertice u:vers){
            if(matriz[u.name][v.name]!=0){
                lista.add(u);
            }
        }
        return lista;
    }
}
