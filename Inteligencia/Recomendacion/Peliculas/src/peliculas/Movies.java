/*
 * Esta clase representa a la tabla de películas.
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
public class Movies {
    int movieId;
    String title, genres;
    
    public Movies(){}
    
    public Movies(int moid, String tit, String gen){
        movieId = moid;
        title = tit;
        genres = gen;
    }
    
    @Override
    public String toString(){
        return "("+movieId+", "+title+", "+genres+")";
    }
    
    /**
     * Función que devuelve todos los registros de la tabla MOVIES
     * @param conexion
     * @return
     * @throws SQLException 
     */
    public static ArrayList<Movies> getRegistros(Connection conexion) throws SQLException{
    ArrayList<Movies> lista = new ArrayList<>();
    try{
    PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM MOVIES");
    ResultSet res = consulta.executeQuery();
    while(res.next()){
        lista.add(new Movies(res.getInt("movieId"),res.getString("title"),res.getString("genres")));
    }
    }catch(SQLException ex){
        throw new SQLException(ex);
    }
        return lista;
    }
    
    /**
     * Función que devuelve las películas que no ha calificado el usuario idusuario.
     * @param conexion
     * @param idusuario
     * @return
     * @throws SQLException 
     */
    public static ArrayList<Movies> getRegistrosFiltro(Connection conexion, int idusuario) throws SQLException{
    ArrayList<Movies> lista = new ArrayList<>();
    try{
    PreparedStatement consulta = conexion.prepareStatement("select * from MOVIES \n" +
        "where movieId not in (select movieId from RATINGS where userId = "+idusuario+")\n" +
        "and movieId in (select distinct(movieId) from RATINGS)");
    ResultSet res = consulta.executeQuery();
    while(res.next()){
        lista.add(new Movies(res.getInt("movieId"),res.getString("title"),res.getString("genres")));
    }
    }catch(SQLException ex){
        throw new SQLException(ex);
    }
        return lista;
    }
    
    public static void main(String[] args){
        try{
            ArrayList<Movies> l1 = Movies.getRegistrosFiltro(Conexion.obtener("jdbc:mysql://localhost/DATASET", "root", "ZaPdOsReD7536589"),1);
            for(int i=0;i<l1.size();i++){
                System.out.println(l1.get(i));
            }
        Conexion.cerrar();
        }catch(Exception e){
            System.out.println("No se pudo hacer la conexión");
        }
    }
}
