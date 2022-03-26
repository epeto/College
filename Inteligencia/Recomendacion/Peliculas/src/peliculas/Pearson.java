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
import java.util.Arrays;
/**
 *
 * @author emmanuel
 */
public class Pearson {
    
    Connection conec;
    
    public Pearson(String url, String usuario, String contra) throws SQLException, ClassNotFoundException {
        conec = Conexion.obtener(url, usuario, contra);
    }
    
    /**
     * Método que devuelve una lista de ratings dados por el usuario "idusuario"
     * @param conexion
     * @param idusuario
     * @return
     * @throws SQLException 
     */
    public static ArrayList<Ratings> calificada(Connection conexion, int idusuario) throws SQLException {
        ArrayList<Ratings> lista = new ArrayList<>();
        try{
        PreparedStatement consulta = conexion.prepareStatement("SELECT * FROM RATINGS WHERE userId = "+idusuario);
        ResultSet res = consulta.executeQuery();
        while(res.next()){
            lista.add(new Ratings(res.getInt("userId"),res.getInt("movieId"),res.getDouble("rating"),res.getString("tiempo")));
        }
        }catch(SQLException ex){
            throw new SQLException(ex);
        }
            return lista;
    }
    
    /**
     * Este método recibe una lista de clase Ratings y lo transforma a algo similar a una matriz
     * donde las columnas son películas, las filas son usuarios y los elementos son calificaciones.
     * @param alr
     * @return 
     */
    public static ArrayList<Par> matrizRatings(ArrayList<Ratings> alr , int tam){
        ArrayList<Par> listaRetorno = new ArrayList();
        
        for(Ratings r:alr){
            Par.agregaPar(listaRetorno, r, tam);
        }
        
        return listaRetorno;
    }
    
    public static double productoPunto(double[] v1, double[] v2){
        double val=0;
        for(int i=0;i<v1.length;i++){
            val+=v1[i]*v2[i];
        }
        return val;
    }
    
    public static double magnitud(double[] vector){
        double val=0;
        for(int i=0;i<vector.length;i++){
            val+=Math.pow(vector[i],2);
        }
        return Math.sqrt(val);
    }
    
    public static double promedio(double[] vector){
        int cont=0;
        int acum=0;
        
        for(int i=0;i<vector.length;i++){
            if(vector[i]!=0){
                cont++;
                acum+=vector[i];
            }
        }
        
        if(cont!=0){
            return acum/cont;
        }else{
            return 0;
        }
    }
    
    /**
     * Función que calcula la correlación de pearson entre 2 vectores.
     * @param p1
     * @param p2
     * @return 
     */
    public static double correlacion(Par p1, Par p2){
        double[] v1,v2;
        
        v1 = new double[p1.listaRatings.length];
        v2 = new double[p2.listaRatings.length];
        double val=0,prom1,prom2;
        //Primero copiamos los arreglos a v1 y v2.
        for(int l=0;l<v1.length;l++){
            v1[l] = p1.listaRatings[l];
            v2[l] = p2.listaRatings[l];
        }
        
        //Después, a cada valor del arreglo que sea diferente de 0 le restaremos el promedio.
        prom1 = promedio(v1);
        prom2 = promedio(v2);
        
        for(int i=0;i<v1.length;i++){
            if(v1[i]!=0){
                v1[i]=v1[i]-prom1;
            }
        }
        
        for(int j=0;j<v2.length;j++){
            if(v2[j]!=0){
                v2[j]=v1[j]-prom2;
            }
        }
        
        //Finalmente, calculamos la correlación de pearson.
        if(magnitud(v1)*magnitud(v2)!=0){
            val = productoPunto(v1,v2)/(magnitud(v1)*magnitud(v2));
        }
        
        return val;
    }
}
