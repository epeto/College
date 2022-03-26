/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peliculas;

import java.sql.*;
/**
 *
 * @author emmanuel
 */
public class Conexion {
    private static Connection cnx = null;
    
    /**
     * Método para hacer la conexión con la base de datos.
     * @param contra contraseña para ingresar a la base de datos.
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static Connection obtener(String url, String usuario, String contra) throws SQLException, ClassNotFoundException {
      if (cnx == null) {
         try {
            Class.forName("com.mysql.jdbc.Driver");
            cnx = DriverManager.getConnection("jdbc:mysql://localhost/DATASET", "root", contra);
         } catch (SQLException ex) {
            throw new SQLException(ex);
         } catch (ClassNotFoundException ex) {
            throw new ClassCastException(ex.getMessage());
         }
      }
      return cnx;
   }
    
    /**
     * Método para obtener la conexión a una base de datos local sin contraseña.
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static Connection obtener() throws SQLException, ClassNotFoundException{
        if (cnx == null) {
         try {
            Class.forName("com.mysql.jdbc.Driver");
            cnx = DriverManager.getConnection("jdbc:mysql://localhost/DATASET");
         } catch (SQLException ex) {
            throw new SQLException(ex);
         } catch (ClassNotFoundException ex) {
            throw new ClassCastException(ex.getMessage());
         }
      }
      return cnx;
    }
    
   public static void cerrar() throws SQLException {
      if (cnx != null) {
         cnx.close();
      }
   }
}
