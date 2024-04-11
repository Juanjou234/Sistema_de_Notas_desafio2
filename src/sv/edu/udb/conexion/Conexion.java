package sv.edu.udb.conexion;

import java.sql.*;

/*
 *  Clase Conexion JDBC
 *  @author Rafael Torres
 * */

public class Conexion {
    // valores de conexion a mysql
    private static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    // el puerto es opcional
    private static String JDBC_URL = "jdbc:mysql://localhost:3306/poo_desafio2";
    private static String JDBC_USER = "root";
    private static String JDBC_PASS = "";
    private static Driver driver = null;

    // para que no hayan problemas al obtener la conexion de
    // manera concurrente, se usa la palabra synchronized
    public static synchronized Connection getConnection() throws SQLException {
        Connection con = null;
        if (driver == null) {
            try {
                // se registra el driver
                Class.forName(JDBC_DRIVER);
                //con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
            } catch (Exception e) {
                System.out.println("fallo al cargar el driver jdbc " + e);
                e.printStackTrace();
            }
        }
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
    }

    // cierre del resultSet, preparedStatement y Connetion
    public static void close(ResultSet rs){
        try {
            if (rs != null) rs.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public static void close(PreparedStatement stmnt) {
        try {
            if (stmnt != null) stmnt.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public static void close(Connection conn) {
        try {
            if (conn != null) conn.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

} // fin de la clase conexion
