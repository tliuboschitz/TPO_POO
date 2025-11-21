/**
 * Cancha: representa a la cancha física
 * Tiene ID autoincremental, nombre, tipo, precio por hora y estado.
 */
public class Cancha {
    private static int proximoId = 1;
    private int idCancha;
    private String nombre;
    private String tipo; // "F11", "F9", "F5"
    private double precioHora;
    private String estado;// "Disponible", "En Mantenimiento", "Reservada"

    public Cancha(String nombre, String tipo, double precioHora) {
        this.idCancha = proximoId++;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precioHora = precioHora;
        this.estado = "Disponible";
    }

    public int getIdCancha() { return idCancha; }
    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public double getPrecioHora() { return precioHora; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "[" + idCancha + "] " + nombre + " (" + tipo + ") - $" + precioHora + " [" + estado + "]";
    }
}
