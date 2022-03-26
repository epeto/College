/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peliculas;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javafx.util.Pair;
import java.util.Scanner;

/**
 *
 * @author emmanuel
 */
public class Principal {

    int idusuario;
    //calif representará las películas calificadas por el usuario.
    //nocalif representará las películas no calificadas por el usuario.
    ArrayList<Ratings> calif;
    ArrayList<Movies> nocalif;
    //Esta representará una tabla de calificaciones donde los usuarios representan
    //las columnas y las películas representan las filas.
    ArrayList<Par> tabla;
    
    
    public Principal(Connection conexion,int idus , int tam) throws SQLException{
        idusuario = idus;
        calif = Pearson.calificada(conexion, idus);
        nocalif = Movies.getRegistrosFiltro(conexion, idus);
        tabla = Pearson.matrizRatings(Ratings.getRegistros(conexion) , tam);
        
    }
    
    /**
     * Función que ordena de mayor a menor por pesos.
     */
    public void ordenaPorPesos(LinkedList<Pair<Integer, Double>> lista){
        Pair<Integer, Double> valorMax;
        int indiceMax;
        for(int i=0;i<lista.size();i++){
            valorMax = lista.get(i);
            indiceMax = i;
            for(int j=i+1;j<lista.size();j++){
                if(lista.get(j).getValue()>valorMax.getValue()){
                    valorMax = lista.get(j);
                    indiceMax = j;
                }
            }

            Pair<Integer, Double> temp = lista.get(i);
            lista.set(i,valorMax);
            lista.set(indiceMax, temp);
        }
    }
    
    /**
     * Función que estima la calificación de idusuario para la película representada por el siguiente id.
     * @param idpelicula
     * @return 
     */
    public double estima(int idpelicula){
        //Esta lista guardará los pesos, donde la primera entrada es movieId y la segunda el peso.
        LinkedList<Pair<Integer, Double>> pesos = new LinkedList();
        //Esta lista guardará los n elementos más similares a idpelicula.
        LinkedList<Pair<Integer, Double>> compara = new LinkedList();
        
        Par prin = Par.obtenPorId(tabla, idpelicula);
        Par p2;
        
        for(Ratings r:calif){
            p2 = Par.obtenPorId(tabla, r.movieId);
            Pair nuevo = new Pair(r.movieId, Pearson.correlacion(prin, p2));
            pesos.add(nuevo);
        }
        
        ordenaPorPesos(pesos);
        
        for(int i=0;i<5;i++){
            compara.add(pesos.get(i));
        }
        
        double valSup=0, valInf=0;
        
        for(Pair<Integer,Double> par:compara){
            valSup += par.getValue()*Ratings.getRatingByMovieId(par.getKey(), calif);
            valInf += Math.abs(par.getValue());
        }
        
        if(valInf!=0){
            return valSup/valInf;
        }else{
            return 0;
        }
    }
    
    public void quicksort(List<Pair<Movies,Double>> list, int from, int to) {
        if (from < to) {
            int pivot = from;
            int left = from + 1;
            int right = to;
            double pivotValue = list.get(pivot).getValue();
            while (left <= right) {

                while (left <= to && pivotValue <= list.get(left).getValue()) {
                    left++;
                }

                while (right > from && pivotValue > list.get(right).getValue()) {
                    right--;
                }
                if (left < right) {
                    Collections.swap(list, left, right);
                }
            }
            Collections.swap(list, pivot, left - 1);
            quicksort(list, from, right - 1);
            quicksort(list, right + 1, to);
        }
    }

    public void ordenaPorRating(List<Pair<Movies,Double>> lista){
        quicksort(lista,0,lista.size()-1);
    }
    
    /**
     * Esta función devuelve una lista de películas de tamaño "cantidad"
     * las cuales tienen el rating estimado más alto.
     * @return 
     */
    public List<Pair<Movies,Double>> recomienda(int cantidad){
        ArrayList<Pair<Movies,Double>> rec = new ArrayList();
        int i=0;
        //Meter todas las estimaciones a la lista rec
        for(Movies mov:nocalif){
            double esperanza = estima(mov.movieId);
            rec.add(new Pair(mov,esperanza));
        }
        
        //ordenar de mayor a menor
        ordenaPorRating(rec);
        return rec.subList(0, cantidad);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            //Obtener la conexión a la base de datos.
            Scanner sc = new Scanner(System.in);
            
            System.out.println("Escriba el usuario de mysql");
            String usuario = sc.nextLine();
            System.out.println("Escriba la url de mysql");
            String url = sc.nextLine();
            System.out.println("Escriba el contraseña de mysql");
            String password = sc.nextLine();
            
            Connection con1 = Conexion.obtener(url, usuario, password);
            
            int tam = Ratings.getNumeroUsuarios(Ratings.getRegistros(con1))+1;
            int num_usuario = -1;
            while(num_usuario < 0 || num_usuario > (tam-1)){
               System.out.println("Hola señor usuario.\n Elija a un usuario (que no es usted) de nuestra base de datos para"
                    + "hacerle una recomendacion personalizada\n"
                    + "(escriba un numero entre 1 y " + (tam-1));
                num_usuario = sc.nextInt();
                
            }
            int num_peliculas= -1;
            while(num_peliculas < 0){
                System.out.println("Escriba el numero de peliculas a recomendar");
                num_peliculas = sc.nextInt();
            }
            
            
           
            Principal prin = new Principal(con1,num_usuario , tam);
            List<Pair<Movies,Double>> l1 = prin.recomienda(num_peliculas);
            System.out.println("Recomendaciones para usuario " + num_usuario + ":");
            
            for(Pair<Movies,Double> x : l1){
                System.out.println(x);
            }           
            Conexion.cerrar();
        }catch(Exception e){
            System.out.println("Ocurrió un error");
            e.printStackTrace();
        }
    }
    
}
