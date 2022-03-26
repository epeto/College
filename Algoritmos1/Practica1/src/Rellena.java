

import java.awt.Color;
import java.util.Random;

/**
 *
 * @author emmanuel
 */
public class Rellena {

    int[][] terreno;
    int lado; //Tamaño de un lado de la matriz.
    Color[] colores;
    
    //Recibe la potencia de un lado.
    public Rellena(int pot){
        lado = (int)Math.pow(2,pot);
        terreno = new int[lado][lado];
        colores = new Color[15];
    }
    
     /**
     * Esta funcion es para rellenar una L en la matriz terreno, o sea rellenar 3 cuadrantes.
     * @param cuad numero del cuadrante, que puede ser 1, 2, 3 o 4.
     * @param izq limite izquierdo
     * @param der limite derecho
     * @param arr limite superior
     * @param aba limite inferior
     */
    public void rellenarL(int cuad, int izq, int der, int arr, int aba){
        //Caso base
        if((der-izq)==3){
            switch(cuad){
                case 1: terreno[izq+2][arr+1]=2; terreno[izq+1][arr+2]=2; terreno[izq+2][arr+2]=2;
                        terreno[izq+2][arr]=3; terreno[der][arr]=3; terreno[der][arr+1]=3;
                        terreno[izq+2][aba]=4; terreno[der][aba]=4; terreno[der][arr+2]=4;
                        terreno[izq][arr+2]=5; terreno[izq][aba]=5; terreno[izq+1][aba]=5;
                break;
                case 2: terreno[izq+1][arr+1]=2; terreno[izq+1][arr+2]=2; terreno[izq+2][arr+2]=2;
                        terreno[der][arr+2]=6; terreno[der][aba]=6; terreno[izq+2][aba]=6;
                        terreno[izq][arr+2]=7; terreno[izq][aba]=7; terreno[izq+1][aba]=7;
                        terreno[izq+1][arr]=8; terreno[izq][arr]=8; terreno[izq][arr+1]=8;
                break;
                case 3: terreno[izq+1][arr+1]=2; terreno[izq+2][arr+1]=2; terreno[izq+2][arr+2]=2;
                        terreno[izq+2][arr]=9; terreno[der][arr]=9; terreno[der][arr+1]=9;
                        terreno[der][arr+2]=10; terreno[der][aba]=10; terreno[izq+2][aba]=10;
                        terreno[izq][arr+1]=11; terreno[izq][arr]=11; terreno[izq+1][arr]=11;
                break;
                case 4: terreno[izq+1][arr+2]=2; terreno[izq+1][arr+1]=2; terreno[izq+2][arr+1]=2;
                        terreno[izq][arr+1]=12; terreno[izq][arr]=12; terreno[izq+1][arr]=12;
                        terreno[izq+2][arr]=13; terreno[der][arr]=13; terreno[der][arr+1]=13;
                        terreno[izq][arr+2]=14; terreno[izq][aba]=14; terreno[izq+1][aba]=14;
                break;
            }
        }else{//Casos superiores
            int mitad = (der-izq+1)/2;
            switch(cuad){
                case 1: rellenarL(3,izq+mitad,der,arr,aba-mitad);
                        rellenarL(1,izq+(mitad/2),der-(mitad/2),arr+(mitad/2),aba-(mitad/2));
                        rellenarL(1,izq+mitad,der,arr+mitad,aba);
                        rellenarL(2,izq,der-mitad,arr+mitad,aba);
                break;
                case 2: rellenarL(4,izq,der-mitad,arr,aba-mitad);
                        rellenarL(2,izq+(mitad/2),der-(mitad/2),arr+(mitad/2),aba-(mitad/2));
                        rellenarL(2,izq,der-mitad,arr+mitad,aba);
                        rellenarL(1,izq+mitad,der,arr+mitad,aba);
                break;
                case 3: rellenarL(4,izq,der-mitad,arr,aba-mitad);
                        rellenarL(3,izq+mitad,der,arr,aba-mitad);
                        rellenarL(3,izq+(mitad/2),der-(mitad/2),arr+(mitad/2),aba-(mitad/2));
                        rellenarL(1,izq+mitad,der,arr+mitad,aba);
                break;
                case 4: rellenarL(4,izq,der-mitad,arr,aba-mitad);
                        rellenarL(3,izq+mitad,der,arr,aba-mitad);
                        rellenarL(4,izq+(mitad/2),der-(mitad/2),arr+(mitad/2),aba-(mitad/2));
                        rellenarL(2,izq,der-mitad,arr+mitad,aba);
                break;
            }
        }
    }
    
    /**
     * Esta función rellena un cuadro donde está la casilla especial.
     * @param x columna donde está la casilla especial.
     * @param y fila donde está la casilla especial.
     * @param izq límite izquierdo
     * @param der límite derecho
     * @param arr límite superior
     * @param aba límite inferior
     */
    public void rellenarCuadro(int x, int y,int izq, int der, int arr, int aba){
        //Casos base
        if((der-izq)==0){
            terreno[0][0]=0;
        }
        if((der-izq)==1){
            if(izq==x && arr==y){
                terreno[izq][arr]=0; terreno[der][arr]=1; terreno[izq][aba]=1; terreno[der][aba]=1;
            }
            if(der==x && arr==y){
                terreno[izq][arr]=1; terreno[der][arr]=0; terreno[izq][aba]=1; terreno[der][aba]=1;
            }
            if(izq==x && aba==y){
                terreno[izq][arr]=1; terreno[der][arr]=1; terreno[izq][aba]=0; terreno[der][aba]=1;
            }
            if(der==x && aba==y){
                terreno[izq][arr]=1; terreno[der][arr]=1; terreno[izq][aba]=1; terreno[der][aba]=0;
            }
        }
        if((der-izq)>1){
            int mitad = (der-izq+1)/2;
            //cuadrante 1
            if(x<(izq+mitad) && y<(arr+mitad)){
                rellenarCuadro(x,y,izq,der-mitad,arr,aba-mitad);
                rellenarL(1,izq,der,arr,aba);
            }
            //cuadrante 2
            if(x>=(izq+mitad) && y<(arr+mitad)){
                rellenarCuadro(x,y,izq+mitad,der,arr,aba-mitad);
                rellenarL(2,izq,der,arr,aba);
            }
            //Cuadrante 3
            if(x<(izq+mitad) && y>=(arr+mitad)){
                rellenarCuadro(x,y,izq,der-mitad,arr+mitad,aba);
                rellenarL(3,izq,der,arr,aba);
            }
            //cuadrante 4
            if(x>=(izq+mitad) && y>=(arr+mitad)){
                rellenarCuadro(x,y,izq+mitad,der,arr+mitad,aba);
                rellenarL(4,izq,der,arr,aba);
            }
        }
    }
    
    /**
     * Función que llama a rellenaCuadro.
     * @param x Posición en x de la casilla especial.
     * @param y Posición en y de la casilla especial.
     */
    public void rellenaMatriz(int x, int y){
        rellenarCuadro(x,y,0,lado-1,0,lado-1);
    }
    
    public void imprimeMatriz(){
        for(int i=0;i<terreno.length;i++){
            System.out.print("|");
            for(int j=0;j<terreno[0].length;j++){
                if(terreno[j][i]<10){
                    System.out.print(terreno[j][i]+"  ");
                }else{
                    System.out.print(terreno[j][i]+" ");
                }
            }
            System.out.println("|");
        }
    }
    
    /**
     * Función que genera colores aleatorios.
     */
    public void coloresAleatorios(){
        Random ran = new Random();
        int r,g,b;
        colores[0] = new Color(0,0,0);
        for(int i=1;i<15;i++){
            r = ran.nextInt(256);
            g = ran.nextInt(256);
            b = ran.nextInt(256);
            colores[i] = new Color(r,g,b);
        }
    }
    
    public static void main(String[] args){
        Rellena campo = new Rellena(3);
        
        campo.rellenaMatriz(4, 2);
        
        campo.imprimeMatriz();
    }
}
