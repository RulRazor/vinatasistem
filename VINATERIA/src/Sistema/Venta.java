package Sistema;

import Productos.Producto;
import Usuarios.Clientes.ClienteAfiliado;
import Usuarios.Clientes.ClienteGenerico;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Venta {
    //Atributos
//    private Cliente cliente;
//    private Usuario usuario;
    private static LocalTime horaVenta;
    private static LocalDate fechaVenta;
    public static int cantidad;
    private Producto producto;
    private double precioUnitario;
    private double precioPromocion;
    public static double total;
    private String promoAplicada;
    private double iva;
    public static List skus = new ArrayList<>();
    public static List nombresProducs = new ArrayList<>();
    public static List preciosOriginales = new ArrayList<>();
    public static List preciosConDesc = new ArrayList<>();
    public static List cantProducs = new ArrayList();
    public static List promosEnCompra = new ArrayList();
    public static int posicionforporcent;
    public static int posicionProductRepet;

    //Constructor
    public Venta(/* Cliente cliente,*/        /*, Usuario usuario*/) {
        this.cantidad = cantidad;
        //this.cliente = cliente;
        this.fechaVenta = fechaVenta;
        this.horaVenta = horaVenta;
        this.iva = iva;
        this.precioPromocion = precioPromocion;
        this.precioUnitario = precioUnitario;
        this.producto = producto;
        this.promoAplicada = promoAplicada;
        this.total = total;
        //this.usuario = usuario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

//    public Cliente getCliente() {
//        return cliente;
//    }
//
//    public void setCliente(Cliente cliente) {
//        this.cliente = cliente;
//    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public LocalTime getHoraVenta() {
        return horaVenta;
    }

    public void setHoraVenta(LocalTime horaVenta) {
        this.horaVenta = horaVenta;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getPrecioPromocion() {
        return precioPromocion;
    }

    public void setPrecioPromocion(double precioPromocion) {
        this.precioPromocion = precioPromocion;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getPromoAplicada() {
        return promoAplicada;
    }

    public void setPromoAplicada(String promoAplicada) {
        this.promoAplicada = promoAplicada;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

//    public Usuario getUsuario() {
//        return usuario;
//    }
//
//    public void setUsuario(Usuario usuario) {
//        this.usuario = usuario;
//    }

    public static Scanner barraBusqueda = new Scanner(System.in);//Esto sirve para añadir por consola, pero se puede cambiar, SOLO ESTA AQUI PARA QUE JALE
    //Metodos
    public static void buscarProducto(String nameProducto) throws SQLException {

        try (Connection con = ConexionBD.obtenerConexion()){//Conecta con la DB

            Statement sentencia = con.createStatement();

            String sql = String.format("SELECT * FROM Productos WHERE Nombre = '%s'", nameProducto);
            ResultSet resultado = sentencia.executeQuery(sql);

            while (resultado.next()) {
                // Procesa los resultados
                String valor1 = resultado.getString("SKU");
                String valor2 = resultado.getString("Nombre");
                Double valor3 = resultado.getDouble("Precio");
                // ...

                System.out.println("SKU: " + valor1 + ", Nombre: " + valor2 + "Precio: " + valor3);

                agregarProducto(valor1, valor2, valor3);//Aquí falta añadir algo que cuestione si se añade o no

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        public static void agregarProducto(String valor1, String valor2, Double valor3){

        System.out.println("Agregar Productos?\n S = Si N = n\n");
        String resp = barraBusqueda.nextLine();//Verificar para quitar
            if (resp.equals("S") || resp.equals("s")) {
                if (skus.contains(valor1)){
                    posicionProductRepet = skus.indexOf(valor1);
                    cantidad = (int) cantProducs.get(posicionProductRepet);
                    cantProducs.set(posicionProductRepet, cantidad + 1);
                }else {
                    skus.add(valor1);
                    nombresProducs.add(valor2);
                    preciosOriginales.add(valor3);
                    preciosConDesc.add(valor3);
                    cantProducs.add(1);
                }
            } else if (resp.equals("N") || resp.equals("n")) {
                //No se agrega a la lista
                System.out.println("No se agragara a la lista\n");
            } else {
                System.out.println("Dato invalido");
            }
    }

        public static void repasarLista() throws SQLException {
        if (skus.isEmpty()){
            //No hace nada
            System.out.println("La lista esta vacia\n");
        }else {
            int maxList = skus.size();
                for (int i = 0; i < maxList; i++ ){
                    posicionforporcent = i;// Se guarda la posicion del sku para modificar el precio si es un descuento porcentual
                    ClienteAfiliado.validarPromocion(String.valueOf(skus.get(i)));//Envia los SKU para validar la promo
                }
        }
    }

        public static void buscarCliente(String userCliente)  throws SQLException{
            if (userCliente == null){
                ClienteGenerico.generarCG();//Si es nulo el cliente ingresado asigna un CG.
            }else {
                try (Connection con = ConexionBD.obtenerConexion()){
                    String sql = String.format("SELECT * FROM Cliente_Afiliado WHERE Nombre = '%s'", userCliente); //Verifica si esta el cliente afiliado

                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.setString(1, userCliente);

                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        ClienteAfiliado.guardarCliente(rs.getString("Nombre"));//Guarda el cliente
                        con.close();
                        //return true; // Se encontro CA
                    } else {
                        System.out.println("Cliente no se encuentra o esta mal escrito");
                        con.close();
                        //return false; // No se encontro CA.
                    }

                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    }

        public static void generarComprobante(){

        horaVenta = LocalTime.now();
        fechaVenta = LocalDate.now();
        String horaImpresa = horaVenta.toString();
        String fechaImpresa = fechaVenta.toString();
        String atendido = ClienteAfiliado.getClienteActual();
        String atendio = Acceso.getUsuarioActual();
        total = 0;
        String iva;

            // Cabecera
            System.out.println("******************************* Penta Copas *******************************");
            System.out.printf("Hora: %s\tFecha: %s\tCliente: %s\tTe Atendió: %s\n\n", horaImpresa, fechaImpresa, atendido, atendio);
            System.out.println("SKU       Producto        Cantidad   Precio Normal   Precio Promoción   Total");
            System.out.println("-------------------------------------------------------------------------------");
            for (int i=0; i < skus.size(); i++){
                String sku = (String) skus.get(i);
                String productos = (String) nombresProducs.get(i);
                int cantidad = (int) cantProducs.get(i);
                double precioUnit = (double) preciosOriginales.get(i);
                String precioProm = "";
                Double totalProd = (double) cantProducs.get(i) * precioUnit;

                if (preciosOriginales.get(i).equals(preciosConDesc.get(i))){
                    //No hace nada si son iguales, solo se imprime
                }else {
                    precioProm = (String) preciosConDesc.get(i);
                    totalProd -= Double.parseDouble(precioProm);
                }
                total += totalProd;
                System.out.printf("%-10s %-15s %-10d %-15.2f %-18.2f %.2f\n",
                        sku, productos, cantidad, precioUnit, precioProm, totalProd);
            }
            System.out.println("\nPromoción Aplicada: " + promosEnCompra);
            //System.out.println("IVA: " + iva + "%");
            System.out.printf("TOTAL FINAL: %.2f\n", total);

    }

        public static void eliminarLista(){
        skus.clear();
        nombresProducs.clear();
        preciosOriginales.clear();
        preciosConDesc.clear();
        cantProducs.clear();
    }
}


//
//    public void buscarComprobante(){
//
//    }
//


