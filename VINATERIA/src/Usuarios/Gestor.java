package Usuarios;

import Interfaces.RF048;

public class Gestor extends Usuario implements RF048 {
    public Gestor(String apellido, String funcion, String ID, String nombre, String password, Boolean statu) {
        super(apellido, funcion, ID, nombre, password, statu);
    }

    @Override
    public void verificarRegistros() {

    }
}
