/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peliculas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author emmanuel
 */
public class Ratings {
    int userId, movieId;
    double rating;
    String tiempo;
    
    public Ratings(){}
    
    public Ratings(int uid, int moid, double rat, String ti){
        userId = uid;
        movieId = moid;
        rating = rat;
        tiempo = ti;
    }
    
    public String toString(){
        return "("+userId+", "+movieId+", "+rating+", "+tiempo+")";
    }
    
    public static ArrayList<Ratings> getRegistros(Connection conexion) throws SQLException{
    ArrayList<Ratings> lista = new ArrayList<>();
    try{
    PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM RATINGS");
    ResultSet res = consulta.executeQuery();
    while(res.next()){
        lista.add(new Ratings(res.getInt("userId"),res.getInt("movieId"),res.getDouble("rating"),res.getString("tiempo")));
    }
    }catch(SQLException ex){
        throw new SQLException(ex);
    }
        return lista;
    }
    
    public static int getNumeroUsuarios(ArrayList<Ratings> lista){
        int resultado = 0;
        for(Ratings x : lista){
            if(x.userId > resultado){
                resultado = x.userId;
            }
        }
        return resultado;
    }
    
    public static double getRatingByMovieId(int idpelicula, ArrayList<Ratings> lista){
        for(Ratings r:lista){
            if(r.movieId==idpelicula){
                return r.rating;
            }
        }
        return 0;
    }
    
    public static void main(String[] args){
        try{
            ArrayList<Ratings> l1 = Ratings.getRegistros(Conexion.obtener("jdbc:mysql://localhost/DATASET", "root", "ZaPdOsReD7536589"));
            for(int i=0;i<l1.size();i++){
                System.out.println(l1.get(i));
            }
        Conexion.cerrar();
        }catch(Exception e){
            System.out.println("No se pudo hacer la conexión");
        }
    }
}
