package Sistema;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {


    public static Connection obtenerConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Reemplazar con el driver de tu base de datos
            Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/Vinateria", "root", "5575615058mau");//Datos dados por mauri
            return conexion;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    // Esta parte es para el archivo InicioSesion, pero se me hace muy larga
//    private static final String URL = "jdbc:mysql://localhost:3306/nombre_de_la_base_de_datos";
//    private static final String USUARIO = "nombre_de_usuario";
//    private static final String CONTRASENA = "contrasena";
//    private static Connection conexion;
//
//    public static Connection getConexion() {
//        try {
//            if (conexion == null || conexion.isClosed()) {
//                conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
//                System.out.println("Conexión establecida");
//            }
//        } catch (SQLException e) {
//            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
//        }
//        return conexion;
//    }
//
//    public static void cerrarConexion() {
//        try {
//            if (conexion != null && !conexion.isClosed()) {
//                conexion.close();
//                System.out.println("Conexión cerrada");
//            }
//        } catch (SQLException e) {
//            System.err.println("Error al cerrar la conexión: " + e.getMessage());
//        }
//    }
}
