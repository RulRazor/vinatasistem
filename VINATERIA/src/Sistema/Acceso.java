package Sistema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class Acceso {
    //Atributos
    private String idUsuario;
    private String nombreUsuario;
    private String password;
    private LocalDate fecha;
    private LocalTime hora;
    private static boolean exito;
    private static int intentos;
    public static LocalDateTime horaBloqueo = null;
    private static String usuarioActual;

    public Acceso() {
        Acceso.exito = exito;
        this.fecha = fecha;
        this.hora = hora;
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        Acceso.intentos = intentos;
    }
    //Getters y Setters
    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //Metodos
    public static boolean iniciarSesion(String nombreUsuario, String password) {
        if(inicioBloqueado()) {System.out.println("No han pasado los 10 min");} //Llama a validar si esta bloqueado, si si acaba
        if (accesoDB("Gestor",nombreUsuario, password)){//Lo busca en tabla Gestor
            return true;
        }else if (accesoDB("Administrador", nombreUsuario, password)){//Lo busca en tabla Administrador
            return true;
        } else if (accesoDB("Vendedor", nombreUsuario, password)) {//Lo busca en tabla Vendedor
            return true;
        }
        intentosFallidos();//No lo encontro en ninguna tabla
        return false;
    }

    private static boolean accesoDB(String tabla, String nombreUsuario, String password) {
        try (Connection con = ConexionBD.obtenerConexion()){

            String sql = String.format("SELECT * FROM %s WHERE Usuario = ? AND Contraseña = ?", tabla);//Lo verifica en la DB con la tabla ingresada.

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombreUsuario);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                guardarSesion(rs.getString("Nombre"));//Guarda el usuario
                con.close();
                exito = true;
                reiniciarIntentos();
                return true; // Credenciales válidas
            } else {
                con.close();
                return false; // Credenciales inválidas
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar el Usuario en DB: " + e.getMessage());
            return false;
        }
    }
    //Conteo de los intentos fallidos
    public static boolean intentosFallidos(){
        intentos++;
        if(Acceso.intentos == 1){
            System.out.println("Usuario o Contraseña Incorrectos");
        } else if (Acceso.intentos == 2) {
            System.out.println("Solo le queda 1 intento");
        } else if (Acceso.intentos >= 3) {
            horaBloqueo = LocalDateTime.now();//Registra el momento en que se bloquea
            System.out.println("Usted ya no puede ingresar, espere 10 minutos");
        }
        return exito;
    }
    //Verificación de bloqueo
    public static boolean inicioBloqueado(){
            if (horaBloqueo == null) return false;     // Si nunca fue bloqueado, no está bloqueado
            return LocalDateTime.now().isBefore(horaBloqueo.plusMinutes(10)); // Bloqueado si han pasado menos de 10 min
    }
    //Reinicia intentos
    public static void reiniciarIntentos() {
        intentos = 0;
        horaBloqueo = null;
    }

    //Funciones que guardan al usuario que inicio sesion

    public static void guardarSesion(String usuario) {
        usuarioActual = usuario;
    }

    public static String getUsuarioActual() {
        return usuarioActual;
    }

    public static void cerrarSesion() {
        usuarioActual = null;
    }

}
