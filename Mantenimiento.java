// Mantenimiento.java
import java.sql.SQLException;
import java.util.Date;
public class Mantenimiento {
    private static int proximoId = 1;
    private int idMantenimiento;
    private String descripcion;
    private Cancha canchaAfectada;
    private Date fecha;
    private String estado;

    public Mantenimiento(String descripcion, Cancha cancha) {
        this.idMantenimiento = proximoId++;
        this.descripcion = descripcion;
        this.canchaAfectada = cancha;
        this.estado = "Pendiente";
    }

    public Mantenimiento(String descripcion, Cancha cancha, Date fecha) {
        this.idMantenimiento = proximoId++;
        this.descripcion = descripcion;
        this.canchaAfectada = cancha;
        this.fecha = fecha;
        this.estado = "Pendiente";
    }

    public Mantenimiento(String descripcion, Cancha cancha, int idMantenimiento) throws SQLException {
        this.idMantenimiento = idMantenimiento;
        this.descripcion = descripcion;
        this.canchaAfectada = cancha;
        this.estado = "Pendiente";
        if (idMantenimiento >= proximoId)
            proximoId = idMantenimiento + 1;
        SQLMantenimiento.addTablaM(idMantenimiento, descripcion, String.valueOf(cancha.getIdCancha()), estado);
    }

    public int getIdMantenimiento() { return idMantenimiento; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setEstado(String estado) { this.estado = estado; }
    public Cancha getCanchaAfectada() { return canchaAfectada; }
    public Date getFecha() { return fecha; }
    public String getEstado() { return estado; }

    public void marcarComoTerminado() {
        this.estado = "Terminado";
        if (canchaAfectada != null) canchaAfectada.setEstado("Disponible");
    }

    public void finalizar() { this.estado = "Finalizado"; }

    @Override
    public String toString() { return "Mantenimiento#" + idMantenimiento + " - " + descripcion + " - " + estado; }
}
