package Usuarios.Clientes;

import Usuarios.Usuario;

public abstract class Cliente extends Usuario {
    public Cliente(String apellido, String funcion, String ID, String nombre, String password, Boolean status) {
        super(apellido, funcion, ID, nombre, password, status);
    }



    //Metodos
    public abstract void historialCompras();
    public abstract void generarComprobante();
}
