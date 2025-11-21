import java.util.Date;
/**
 * Mantenimiento: tarea sobre una cancha (pendiente/terminado).
 * Se puede marcar como terminado y entonces la cancha vuelve a disponible.
 */
public class Mantenimiento {
    private static int proximoId = 1;
    private int idMantenimiento;
    private String descripcion;
    private Cancha canchaAfectada;
    private Date fecha; // Fecha del bloqueo
    private String estado; // "Pendiente", "En Curso", "Finalizado"

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

    // Getters y Setters
    public int getIdMantenimiento() { return idMantenimiento; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setEstado(String estado) { this.estado = estado; }
    public Cancha getCanchaAfectada() { return canchaAfectada; }
    public Date getFecha() { return fecha; }
    public String getEstado() { return estado; }

    // Marca la tarea como terminada y vuelve a poner la cancha "Disponible"
    public void marcarComoTerminado() {
        this.estado = "Terminado";
        if (canchaAfectada != null) canchaAfectada.setEstado("Disponible");
    }

    // Método para cerrar el mantenimiento (manual)
    public void finalizar() {
        this.estado = "Finalizado";
    }
    @Override
    public String toString() {
        return "Mantenimiento#" + idMantenimiento + " - " + descripcion + " - " + estado;
    }
}
