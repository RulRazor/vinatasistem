package Usuarios;

import Sistema.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
//NOTA: Los metodos llaman a la Tabla Usuario, pero no existe esa tabla como tal, hay que buscar una forma de reducir el codigo a que en la busqueda en la DB pase por las 4 tablas.
public class Usuario {
    //Atributos
    private String nombre;
    private String apellido;
    private String funcion;
    private String ID;
    private String password;
    private Boolean status;

    //Constructor
    public Usuario(String apellido, String funcion, String ID, String nombre, String password, Boolean status) {
        this.apellido = apellido;
        this.funcion = funcion;
        this.ID = ID;
        this.nombre = nombre;
        this.password = password;
        this.status = status;
    }

    //Metodos
    private void buscarUsuario(){
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            String sql = "SELECT * FROM usuarios WHERE ID = ?";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, this.ID);
            ResultSet resultado = statement.executeQuery();

            if (resultado.next()) {
                this.nombre = resultado.getString("nombre");
                this.apellido = resultado.getString("apellido");
                this.funcion = resultado.getString("funcion");
                this.password = resultado.getString("password");
                this.status = resultado.getBoolean("status");
                System.out.println("Usuario encontrado: " + this.nombre + " " + this.apellido);
            } else {
                System.out.println("Usuario no encontrado con ID: " + this.ID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void registrarUsuario(){
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            String sql = "INSERT INTO usuarios (ID, nombre, apellido, funcion, password, status) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, generarID()); // Asignación de ID único
            statement.setString(2, this.nombre);
            statement.setString(3, this.apellido);
            statement.setString(4, this.funcion);
            statement.setString(5, this.password);
            statement.setBoolean(6, this.status);

            int filasInsertadas = statement.executeUpdate();
            if (filasInsertadas > 0) {
                System.out.println("Usuario registrado exitosamente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private void modificarUsuario(){
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, funcion = ?, password = ?, status = ? WHERE ID = ?";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, this.nombre);
            statement.setString(2, this.apellido);
            statement.setString(3, this.funcion);
            statement.setString(4, this.password);
            statement.setBoolean(5, this.status);
            statement.setString(6, this.ID);

            int filasActualizadas = statement.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Usuario actualizado exitosamente.");
            } else {
                System.out.println("No se encontró el usuario con ID: " + this.ID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private String  generarID(){
        String nuevoID = UUID.randomUUID().toString();
        this.ID = nuevoID; // Asignación del nuevo ID al usuario actual
        return nuevoID;
    }

    //Getters y Setters
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
