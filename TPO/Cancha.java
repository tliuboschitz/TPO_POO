package TPO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Cancha {
    // CONTADOR ESTÁTICO
    private static int proximoId = 1;
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

    private void addTablaC(int idCancha, String nombre, String tipo, double precioHora, String estado){
        Connection Conection;
        try {
            Conection = DriverManager.getConnection("jdbc:sqlite:TPO.db");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String query = "INSERT INTO CANCHA(idCancha, nombreC, tipo, precioHora, estado) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = Conection.prepareStatement(query)) {
            stmt.setInt(1, idCancha);
            stmt.setString(2, nombre);
            stmt.setString(3, tipo);
            stmt.setDouble(4, precioHora);
            stmt.setString(5, estado);
            stmt.executeUpdate();
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

    public double getPrecioHora() { return precioHora; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}