package Usuarios;

import Interfaces.RF048;

public class Administrador extends Usuario implements RF048 {
    public Administrador(String apellido, String funcion, String ID, String nombre, String password, Boolean status) {
        super(apellido, funcion, ID, nombre, password, status);
    }


    //Metodos
    private void generarReporte(){

    }
    private void buscarTipoRegistro(){

    }
    private void mostrarTipoRegistro(){

    }
    private void altaProducto(){

    }
    private void modificarProducto(){

    }
    private void imprimirReporte(){

    }
//Interfaces
    @Override
    public void verificarRegistros() {

    }

}
