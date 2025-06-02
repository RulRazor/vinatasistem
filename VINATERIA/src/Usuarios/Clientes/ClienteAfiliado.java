package Usuarios.Clientes;

import Sistema.ConexionBD;

import java.sql.*;

import static Sistema.Venta.*;


public class ClienteAfiliado extends Cliente {
    public ClienteAfiliado(String apellido, String funcion, String ID, String nombre, String password, Boolean status) {
        super(apellido, funcion, ID, nombre, password, status);
    }
    //Mantiene la promocion
    public static int tipPromo;
    public static int cantidadDescuentos;
    public  static double nuevoPrecio;

    //Metodos
    @Override
    public void historialCompras() {

    }

    @Override
    public void generarComprobante() {

    }

    //Busca que el producto este en la DB Promocion
    public static boolean validarPromocion(String SKU) throws SQLException {

        try (Connection con = ConexionBD.obtenerConexion()){

            char ganador = SKU.charAt(0);//Obtiene la primera letra del SKU

            Statement sentencia = con.createStatement();
            String sql = String.format("SELECT LEFT(SKU, 1) AS sorteado FROM Promociones WHERE SKU = ?");//Selecciona de la cadena SKU la primera letra y ponlo el la columna temporal sorteado de la tabla Promocion donde encuetres el SKU

            ResultSet rs = sentencia.executeQuery(sql);

            if (rs.next()){
                String valor1 = rs.getString("sorteado");//Asigna a valo1 la letra que esta en la columna sorteado
                if (valor1.equals(ganador)){//Verifica si es igual a la letra que se obtubo del SKU ingresado
                       String tipodepromo = rs.getString("TIPO_PROMO");//Asigna a "tipodepromo" el tipo de promocion que tenga guardado en la columna "TIPO_PROMO"
                       sql = String.format("SELECT * FROM Producto WHERE SKU = '%s'", SKU);//Busca en la tabla Producto el mismo SKU
                       rs = sentencia.executeQuery(sql);
                    if (rs.next()) {
                        String precioAProcesar = rs.getString("Precio");//Si la encuentra obtiene el Precio

                        con.close();

                        //Cuestiona si se aplica la promocion
                        System.out.printf("El SKU %s tiene una promocion de %s Desea aplicarlo?\n S = Si\n N = No\n", SKU, tipodepromo);
                        String resp = barraBusqueda.nextLine();//Verificar para quitar
                        if (resp.equals("S") || resp.equals("s")) {
                            ClienteAfiliado.aplicarPromocion(tipodepromo, Double.valueOf(precioAProcesar));
                        } else if (resp.equals("N") || resp.equals("n")) {
                            //No se agrega la promocion
                            System.out.println("No se agragara la promocion a la compra\n");
                            posicionforporcent = Integer.parseInt(null);
                        } else {
                            System.out.println("Dato invalido");
                        }

                    }
                }

            }else {
                con.close();
                return false; //No hay promocion
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void aplicarPromocion(String promo, Double precio){
            Double desc;
            promosEnCompra.add(promo);
            if (promo.equals("2x1")){
                tipPromo = 2;
                revisarCantdePromos(tipPromo);
            } else if (promo.equals("3x2")) {
                tipPromo = 3;
                revisarCantdePromos(tipPromo);
            }else {
                desc = Double.parseDouble(promo);
                preciosConDesc.set(posicionforporcent,nuevoPrecio = (precio * (desc / 100)));//Cambia la posicion ya guardada el nuevoPrecio
            }
    }

    //Revisa cuantas veces se puede aplicar la promocion
    public static void revisarCantdePromos(int tipPromo){
        // Validamos que la posición sea válida
        if (posicionforporcent < 0 || posicionforporcent >= cantProducs.size()) {
            System.out.println("Posición fuera de los límites.");
            return;
        }
        // Obtenemos la cantidad de productos en la posición deseada
        int cantidadProductos = (int) cantProducs.get(posicionforporcent);

        // Ver cuántas veces cabe el tipPromo (división entera)
        cantidadDescuentos = cantidadProductos / tipPromo;
        preciosTotalesAPagar(cantidadDescuentos);
    }

    public static void preciosTotalesAPagar(int cantidadDescuento){
            double theDescuent = (double) preciosOriginales.get(posicionforporcent);
            double cantADesc = theDescuent * cantidadDescuento;
            preciosConDesc.set(posicionforporcent, cantADesc);

    }


}
