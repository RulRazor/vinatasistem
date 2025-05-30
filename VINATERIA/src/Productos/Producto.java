package Productos;

import Sistema.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Producto {
    private SKU sku;
    private String nombreProducto;
    private double precio;
    private int stock;
    private String proveedor;
    private String descripcion;
    private LocalDate fehca_alta;

    public Producto(String descripcion, String nombreProducto, LocalDate fehca_alta, double precio, String proveedor, SKU sku, int stock) {
        this.descripcion = descripcion;
        this.nombreProducto = nombreProducto;
        this.fehca_alta = fehca_alta;
        this.precio = precio;
        this.proveedor = proveedor;
        this.sku = sku;
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFehca_alta() {
        return fehca_alta;
    }

    public void setFehca_alta(LocalDate fehca_alta) {
        this.fehca_alta = fehca_alta;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public SKU getSku() {
        return sku;
    }

    public void setSku(SKU sku) {
        this.sku = sku;
    }

    //Metodos
    private void registrarse(){

        try (Connection conexion = ConexionBD.obtenerConexion()) {

            // Verificar si ya existe un producto con el mismo SKU
            String consultaExistencia = "SELECT COUNT(*) FROM productos WHERE sku = ?";
            PreparedStatement checkStmt = conexion.prepareStatement(consultaExistencia);
            checkStmt.setString(1, this.sku.toString());
            ResultSet resultado = checkStmt.executeQuery();

            if (resultado.next() && resultado.getInt(1) > 0) {
                System.out.println("Error: Ya existe un producto con el SKU: " + this.sku);
                return;
            }

            // Insertar nuevo producto
            String sql = "INSERT INTO productos (sku, nombre_producto, precio, stock, proveedor, descripcion, fecha_alta) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, this.sku.toString());
            statement.setString(2, this.nombreProducto);
            statement.setDouble(3, this.precio);
            statement.setInt(4, this.stock);
            statement.setString(5, this.proveedor);
            statement.setString(6, this.descripcion);
            statement.setDate(7, java.sql.Date.valueOf(this.fehca_alta));

            int filasInsertadas = statement.executeUpdate();
            if (filasInsertadas > 0) {
                System.out.println("Producto registrado exitosamente.");
            } else {
                System.out.println("No se pudo registrar el producto.");
            }

        } catch (SQLException e) {
            System.err.println("Error al registrar el producto: " + e.getMessage());
        }

    }
}
