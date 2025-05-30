package Sistema;

import Productos.Producto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Venta {
    //Atributos
//    private Cliente cliente;
//    private Usuario usuario;
    private LocalTime horaVenta;
    private LocalDate fechaVenta;
    private int cantidad;
    private Producto producto;
    private double precioUnitario;
    private double precioPromocion;
    private double total;
    private String promoAplicada;
    private double iva;
    List skus = new ArrayList<>();
    List nombresProducs = new ArrayList<>();
    List precios = new ArrayList<>();

    public Venta(int cantidad,/* Cliente cliente,*/ LocalDate fechaVenta, LocalTime horaVenta, double iva, double precioPromocion, double precioUnitario, Producto producto, String promoAplicada, double total/*, Usuario usuario*/) {
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

    //Metodos

    public void buscarProducto(String nameProducto) throws SQLException {

        try {

            ConexionBD conexion = new ConexionBD();
            Connection con = conexion.obtenerConexion();//Crea la conexión con la DB

            Statement sentencia = con.createStatement();

            String sql = String.format("SELECT * FROM Productos WHERE Nombre = '%s'", nameProducto);
            ResultSet resultado = sentencia.executeQuery(sql);//Nombre es la columna donde esta el producto, todavia falta modificar "Valor a buscar"

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
        public void agregarProducto(String valor1, String valor2, Double valor3){
        skus.add(valor1);
        nombresProducs.add(valor2);
        precios.add(valor3);
    }

        public void generarComprobante(){
        horaVenta = LocalTime.now();
        fechaVenta = LocalDate.now();



        System.out.println("""
                ******************************* Nombre_de_la_vinata *******************************
               
               Hora:                Fecha:              Cliente:             Te Atendio:
               
               SKU      Producto          Cantidad    Precio Normal     Precio Promoción  Total
               
               
               
               
               
               Promoción Aplicada
               IVA 
               
               
                """);
    }
}


//
//    public void buscarComprobante(){
//
//    }
//

//
//    public void buscarCliente(){
//
//    }

