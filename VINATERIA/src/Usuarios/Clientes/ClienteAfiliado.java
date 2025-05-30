package Usuarios.Clientes;

import Sistema.ConexionBD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClienteAfiliado extends Cliente {
    public ClienteAfiliado(String apellido, String funcion, String ID, String nombre, String password, Boolean status) {
        super(apellido, funcion, ID, nombre, password, status);
    }


    //Metodos
    @Override
    public void historialCompras() {

    }

    @Override
    public void generarComprobante() {

    }

    private boolean validarPromocion() throws SQLException {

        try (Connection conexion = ConexionBD.obtenerConexion()){
            Statement sentencia = conexion.createStatement();

            String sql = String.format("SELECT * FROM Productos WHERE Nombre = '%s'");
            ResultSet resultado = sentencia.executeQuery(sql);

            if (resultado.next()){

            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
