/**
 * Partido: evento deportivo asociado a una reserva.
 * Ahora tiene ID autogenerado dentro del Sistema (se crea desde Sistema).
 */
public class Partido {
    private int idPartido;
    private Reserva reserva;
    private String equipos;
    private double precioTicketBase;

    // Constructor con id provisto por Sistema
    public Partido(int idPartido, Reserva reserva, String equipos, double precioTicketBase) {
        this.idPartido = idPartido;
        this.reserva = reserva;
        this.equipos = equipos;
        this.precioTicketBase = precioTicketBase;
    }

    public int getIdPartido() { return idPartido; }
    public Reserva getReserva() { return reserva; }
    public String getEquipos() { return equipos; }
    public double getPrecioTicketBase() { return precioTicketBase; }

    @Override
    public String toString() {
        return "Partido#" + idPartido + " [" + equipos + "] - Cancha: " + (reserva!=null?reserva.getCancha().getNombre():"N/A");
    }
}
