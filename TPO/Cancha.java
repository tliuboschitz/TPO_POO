package TPO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static TPO.Main.Conection;

public class Cancha {
    // CONTADOR ESTÁTICO
    private static int proximoId;
    private int idCancha;
    private String nombre;
    private String tipo; // "F11", "F9", "F5"
    private double precioHora;
    private String estado; 

    
    public Cancha(String nombre, String tipo, double precioHora) {
        this.idCancha = proximoId++;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precioHora = precioHora;
        this.estado = "Disponible";
        addTablaC(idCancha, nombre, tipo, precioHora, estado);
    }
        // Getters y Setters
    public int getIdCancha() {
        return idCancha;
    }



    private void addTablaC(int idCancha, String nombre, String tipo, double precioHora, String estado) {
        try {
            String query = "INSERT or IGNORE INTO CANCHA(idCancha, nombreC, tipo, precioHora, estado) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = Conection.prepareStatement(query);
            stmt.setInt(1, idCancha);
            stmt.setString(2, nombre);
            stmt.setString(3, tipo);
            stmt.setDouble(4, precioHora);
            stmt.setString(5, estado);
            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public String getNombre() { 
        return nombre;
    }
    public String getTipo() { 
        return tipo;
    }
    public double getPrecioHora() {
        return precioHora; }
    public String getEstado() {
        return estado; }
    public void setEstado(String estado) {
        this.estado = estado;
    }



    @Override
    public String toString() {
        return nombre + "     $" + precioHora; //Para la interfaz
    }
}