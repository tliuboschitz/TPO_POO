/**
 * Mantenimiento: tarea sobre una cancha (pendiente/terminado).
 * Se puede marcar como terminado y entonces la cancha vuelve a disponible.
 */
public class Mantenimiento {
    private static int contador = 1;
    private int idMantenimiento;
    private String descripcion;
    private Cancha canchaAfectada;
    private String estado;

    public Mantenimiento(String descripcion, Cancha cancha) {
        this.idMantenimiento = contador++;
        this.descripcion = descripcion;
        this.canchaAfectada = cancha;
        this.estado = "Pendiente";
    }

    public int getIdMantenimiento() { return idMantenimiento; }
    public String getDescripcion() { return descripcion; }
    public Cancha getCanchaAfectada() { return canchaAfectada; }
    public String getEstado() { return estado; }

    // Marca la tarea como terminada y vuelve a poner la cancha "Disponible"
    public void marcarComoTerminado() {
        this.estado = "Terminado";
        if (canchaAfectada != null) canchaAfectada.setEstado("Disponible");
    }

    @Override
    public String toString() {
        return "Mantenimiento#" + idMantenimiento + " - " + descripcion + " - " + estado;
    }
}
