package Usuarios.Clientes;

import Usuarios.Usuario;

public abstract class Cliente extends Usuario {
    private static String clienteActual;
    public Cliente(String apellido, String funcion, String ID, String nombre, String password, Boolean status) {
        super(apellido, funcion, ID, nombre, password, status);
    }


    //Metodos hereditarios, para guardar el usuario

    public static void guardarCliente(String usuario) {
        clienteActual = usuario;
    }

    public static String getClienteActual() {
        return clienteActual;
    }

    public static void cerrarSesionCliente() {
        clienteActual = null;
    }

    //Metodos abstractos
    public abstract void historialCompras();
    public abstract void generarComprobante();
}
