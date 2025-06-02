import Sistema.Acceso;
import Sistema.Venta;
import Usuarios.Clientes.ClienteGenerico;

import java.sql.SQLException;
import java.util.Scanner;

public static void main(String[] args) throws SQLException {
    Scanner barraBusqueda = new Scanner(System.in);//Esto sirve para añadir por consola, pero se puede cambiar.

    System.out.println("Ingrese Usuario y Contraseña\n");
    String usuario = barraBusqueda.nextLine();
    String contraseña = barraBusqueda.nextLine();

    Acceso.iniciarSesion(usuario, contraseña);

    System.out.println("""
            Lista de Opciones.
            1.- Buscar Producto.
            2.- Eliminar Producto.
            3.- Eliminar lista.
            4.- Agregar Cliente.
            5.- Validar Promociones.
            6.- Generar Comprobante
            """);

    int eleccion = barraBusqueda.nextInt();//Solo existe para las pruebas

    switch (eleccion){
        case 1: {
            System.out.println("\nIngrese el nombre del Producto\n");
            String nameProducto = barraBusqueda.nextLine();
            Venta.buscarProducto(nameProducto);
            break;
        }
        case 2:{
            //Pendiente
        }
        case 3:{
            Venta.eliminarLista();
            break;
        }
        case 4:{
            System.out.println("Desea agregar a un cliente? \n S = Si \n N = No\n");

            String resp = barraBusqueda.nextLine();//Solo existe para las pruebas

            if (resp.equals("S") || resp.equals("s")) {
                System.out.println("Agregue el nombre del cliente");
                String userCliente = barraBusqueda.nextLine();
                Venta.buscarCliente(userCliente);
            } else if (resp.equals("N") || resp.equals("n")) {
                ClienteGenerico.generarCG();
                //Se sigue
            } else {
                System.out.println("Dato invalido");
            }
            break;
        }
        case 5:{
            Venta.repasarLista();
            break;
        }
        case 6: {
            Venta.generarComprobante();
            break;
        }
        default:{
            System.out.println("Eleccion incorrecta");
        }
    }




}