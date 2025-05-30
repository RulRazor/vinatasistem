import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//public class InicioSesion{
//
//public static boolean verificarLogin(String usuario, String contrasena) {
//    Connection conexion = Sistema.ConexionBD.getConexion();
//    if (conexion == null) {
//        return false; // No se pudo conectar a la base de datos
//    }
//
//    try {
//        String sql = "SELECT COUNT(*) FROM usuarios WHERE usuario = ? AND contrasena = ?";
//        PreparedStatement preparedStatement = conexion.prepareStatement(sql);
//        preparedStatement.setString(1, usuario);
//        preparedStatement.setString(2, contrasena);
//
//        ResultSet resultSet = preparedStatement.executeQuery();
//        resultSet.next();
//        int count = resultSet.getInt(1);
//
//        if (count > 0) {
//            return true; // Usuario y contraseña correctos
//        } else {
//            return false; // Usuario o contraseña incorrectos
//        }
//    } catch (SQLException e) {
//        System.err.println("Error al verificar el login: " + e.getMessage());
//        return false;
//    } finally {
//        Sistema.ConexionBD.cerrarConexion();
//    }
//}
//
//public static void main(String[] args) {
//    String usuario = "nombre_de_usuario"; // Reemplaza con el usuario ingresado
//    String contrasena = "contrasena"; // Reemplaza con la contraseña ingresada
//
//    if (verificarLogin(usuario, contrasena)) {
//        System.out.println("Inicio de sesión exitoso");
//    } else {
//        System.out.println("Inicio de sesión fallido");
//    }
//}
//}