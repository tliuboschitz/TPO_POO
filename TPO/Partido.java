package TPO; /**
 * Partido: evento asociado a una reserva.
 * Ahora tiene ID autogenerado dentro del Sistema (se crea desde Sistema).
 */
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Partido {
    private int idPartido;
    private Reserva reserva; // El partido "usa" una reserva
    private String equipos;
    private double precioTicketBase;
    private static int proximoId = 1;
    private int capacidadMaximaTickets; // Ej: 200 entradas
    private List<Ticket> ticketsVendidos;   // Para llevar la cuenta

    public Partido(Reserva reserva, String equipos, double precioTicket, int capacidad) throws SQLException {
        this.idPartido = proximoId++;
        this.reserva = reserva;
        this.equipos = equipos;
        this.precioTicketBase = precioTicket;
        this.capacidadMaximaTickets = capacidad;
        this.ticketsVendidos = new ArrayList<>();
        SQLPartidos.addTablaPartido(this.idPartido, reserva.getIdReserva(), equipos, precioTicketBase);

    }
    public double getPrecioTicketBase() {
        return precioTicketBase;
    }

    public int getIdPartido() {
        return idPartido;
    }

    double calcularPrecioFinal(Audiencia audiencia) {
        double precioBase = this.precioTicketBase;
        double precioFinal;

        int edad = audiencia.getEdad();

        if (edad < 6) {
            // Menores a 6 años no pagan
            precioFinal = 0.0;
        } else if (edad > 6 && edad < 12) {
            // Mayores de 6 y menores de 12 pagan 50%
            precioFinal = precioBase * 0.5;
        } else if (edad >= 60) {
            // Mayores o iguales a 60 pagan con 70% de descuento -> pagan 30%
            precioFinal = precioBase * 0.3;
        } else {
            // Entre 12 (incl) y 59 pagan precio completo
            precioFinal = precioBase;
        }

        // Si es socio, aplica 50% adicional sobre el precio calculado
        if (audiencia.isEsSocio()) {
            precioFinal *= 0.5;
        }

        return precioFinal;
    }
    
    public String getEquipos() {
        return equipos;
    }
    public boolean estaLleno() {
        return this.ticketsVendidos.size() >= this.capacidadMaximaTickets;
    }
    //Agrega un ticket vendido a la lista interna del partido.
    
    void agregarTicket(Ticket ticket) {
        this.ticketsVendidos.add(ticket);
    }

    @Override
    public String toString() {
        return "Partido#" + idPartido + " [" + equipos + "] - Cancha: " + (reserva!=null?reserva.getCancha().getNombre():"N/A");
    }
}
