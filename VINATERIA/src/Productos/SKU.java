package Productos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;
import Sistema.ConexionBD;

public class SKU {
    private String codigo;

    //Getter
    public String getCodigo() {
        return codigo;
    }


    public SKU(String codigo) {
        if (validarSKU(codigo) && !existeEnBD(codigo)) {
            this.codigo = codigo;
        } else {
            throw new IllegalArgumentException("SKU inválido o ya existente");
        }
    }

    public void setCodigo(String codigo) {
        if (validarSKU(codigo) && !existeEnBD(codigo)) {
            this.codigo = codigo;
        } else {
            throw new IllegalArgumentException("SKU inválido o ya existente");
        }
    }

    //Crea el SKU y verifica si ya estaba o cumple con el requisito
    public void generarSKU(char tipo, int marcaId, int modeloId, int versionId) {
        String nuevoSKU = String.format("%s%02d%02d%03d",
                String.valueOf(tipo).toUpperCase(),
                marcaId,
                modeloId,
                versionId);

        if (validarSKU(nuevoSKU) && !existeEnBD(nuevoSKU)) {
            this.codigo = nuevoSKU;
        } else {
            throw new IllegalArgumentException("El SKU generado ya existe en la base de datos o es inválido.");
        }
    }

    //Valida que cumpla con la escritura
    public boolean validarSKU(String sku) {
        return sku != null && Pattern.matches("^[LBWCSRPKM][0-9]{7}$", sku);
    }

    //Lo busca en la DB
    private boolean existeEnBD(String sku) {
        boolean existe = false;
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            String query = "SELECT COUNT(*) FROM Productos WHERE SKU = ?";
            PreparedStatement stmt = conexion.prepareStatement(query);
            stmt.setString(1, sku);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                existe = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar existencia del SKU: " + e.getMessage());
        }
        return existe;
    }

    @Override
    public String toString() {
        return codigo;
    }
}
