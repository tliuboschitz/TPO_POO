import java.util.ArrayList;
import java.util.List;

/**
 * Partido: evento asociado a una reserva.
 *
 * Cambios:
 * - Se agregan setters para permitir modificaciones desde la UI:
 *   setEquipos, setPrecioTicketBase, setCapacidadMaximaTickets
 *
 * - Mantuve la lógica de precio y la lista interna de tickets vendids.
 */
public class Partido {

    private int idPartido;
    private Reserva reserva;
    private String equipos;
    private double precioTicketBase;
    private static int proximoId = 1;
    private int capacidadMaximaTickets;
    private List<Ticket> ticketsVendidos;

    public Partido(Reserva reserva, String equipos, double precioTicket, int capacidad) {
        this.idPartido = proximoId++;
        this.reserva = reserva;
        this.equipos = equipos;
        this.precioTicketBase = precioTicket;
        this.capacidadMaximaTickets = capacidad;
        this.ticketsVendidos = new ArrayList<>();
    }

    public double getPrecioTicketBase() { return precioTicketBase; }
    public int getIdPartido() { return idPartido; }
    public String getEquipos() { return equipos; }

    // --- SETTERS añadidos para permitir edición desde la UI (según lo solicitado) ---
    public void setEquipos(String equipos) { this.equipos = equipos; }
    public void setPrecioTicketBase(double precio) { this.precioTicketBase = precio; }
    public void setCapacidadMaximaTickets(int capacidad) { this.capacidadMaximaTickets = capacidad; }

    double calcularPrecioFinal(Audiencia audiencia) {
        double precioBase = this.precioTicketBase;
        double precioFinal;
        int edad = audiencia.getEdad();

        if (edad < 6) {
            precioFinal = 0.0;
        } else if (edad > 6 && edad < 12) {
            precioFinal = precioBase * 0.5;
        } else if (edad >= 60) {
            precioFinal = precioBase * 0.3;
        } else {
            precioFinal = precioBase;
        }

        if (audiencia.isEsSocio()) {
            precioFinal *= 0.5;
        }

        return precioFinal;
    }

    public boolean estaLleno() {
        return this.ticketsVendidos.size() >= this.capacidadMaximaTickets;
    }

    void agregarTicket(Ticket ticket) {
        this.ticketsVendidos.add(ticket);
    }

    @Override
    public String toString() {
        return "Partido#" + idPartido + " [" + equipos + "] - Cancha: "
                + (reserva != null ? reserva.getCancha().getNombre() : "N/A");
    }
}
