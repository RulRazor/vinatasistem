package Usuarios.Clientes;

public class ClienteGenerico extends Cliente {
    public ClienteGenerico(String apellido, String funcion, String ID, String nombre, String password, Boolean status) {
        super(apellido, funcion, ID, nombre, password, status);
    }


    //Metodos
    @Override
    public void historialCompras() {

    }

    @Override
    public void generarComprobante() {

    }

    public static void generarCG(){
        ClienteGenerico CG = new ClienteGenerico("Anónimo", "Cliente Generico", "00000000", "Anónimo", "Anonimo0", false);
        guardarCliente(CG.getNombre()); //Obtiene el nombre anonimo y lo guarda
    }
}
