/*
 * Esta clase representará un par donde un elemento será un id de película y
 * el otro elemento será una lista de ratings.
 */
package peliculas;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author emmanuel
 */
public class Par {
    int movieId;
    double[] listaRatings;
    
    public Par(int mid, int tamanio){
        movieId = mid;
        listaRatings = new double[tamanio];
    }
    
    @Override
    public String toString(){
        String str="";
        str+="movieId: ["+movieId+"] Ratings:";
        for(int i=0;i<listaRatings.length;i++){
            str+=" "+listaRatings[i];
        }
        return str;
    }
    
    /**
     * Función que agrega una calificación a la lista.
     * @param idusuario el usuario que la agregó, en este caso, la posición.
     * @param rating 
     */
    public void agregaRating(int idusuario, double rating){
        listaRatings[idusuario] = rating;
    }
    
    /**
     * Función que agrega un par a una lista de pares.
     * @param lista
     * @param ra
     * @param tamanio :tamaño asociado al par, en este caso, la cantidad de usuarios.
     */
    public static void agregaPar(List<Par> lista, Ratings ra, int tamanio){
        //Nos dice si el movieId ya existe en la lista de pares.
        boolean existe=false;
        for(Par p:lista){
            if(ra.movieId == p.movieId){
                p.agregaRating(ra.userId, ra.rating);
                existe=true;
            }
        }
        
        //Si ese movieId no existia, lo agregamos a la lista con un nuevo par.
        if(!existe){
            Par nuevo = new Par(ra.movieId, tamanio);
            lista.add(nuevo);
        }
    }
    
    public static Par obtenPorId(List<Par> lista, int idpelicula){
        Par retVal=null;
        for(Par p:lista){
            if(idpelicula==p.movieId){
                retVal = p;
            }
        }
        return retVal;
    }
}
