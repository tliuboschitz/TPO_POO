package TPO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
@SuppressWarnings("unused")

class Mantenimiento {
    private int idMantenimiento;
    private String descripcion;
    private Cancha canchaAfectada;
    private String estado; // "Pendiente", "Terminado"
    
    public Mantenimiento(int id, String desc, Cancha cancha) {
        this.idMantenimiento = id;
        this.descripcion = desc;
        this.canchaAfectada = cancha;
        this.estado = "Pendiente";
    }
}
