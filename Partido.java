/**
 * Partido: evento asociado a una reserva.
 *
 * ✔ Genera IDs automáticamente (proximoId++)
 * ✔ Tiene capacidad máxima para soportar una excepción de CapacidadAgotada (si se quisiera)
 * ✔ Guarda tickets vendidos → necesario para evitar TicketDuplicadoException
 */

import java.util.ArrayList;
import java.util.List;

public class Partido {

    // ID único generado automáticamente
    private int idPartido;

    // Cada partido pertenece a una reserva
    private Reserva reserva;

    // Nombre de los equipos
    private String equipos;

    // Precio base antes de aplicar descuentos
    private double precioTicketBase;

    // Generador de IDs autoincremental
    private static int proximoId = 1;

    // Capacidad máxima de entradas para este partido
    private int capacidadMaximaTickets;

    // Tickets vendidos → necesario para verificar duplicados
    private List<Ticket> ticketsVendidos;

    /**
     * Constructor del Partido
     * @param capacidad → límite de entradas
     */
    public Partido(Reserva reserva, String equipos, double precioTicket, int capacidad) {
        this.idPartido = proximoId++;  // autogenerado
        this.reserva = reserva;
        this.equipos = equipos;
        this.precioTicketBase = precioTicket;
        this.capacidadMaximaTickets = capacidad;
        this.ticketsVendidos = new ArrayList<>();
    }

    public double getPrecioTicketBase() { return precioTicketBase; }
    public int getIdPartido() { return idPartido; }
    public String getEquipos() { return equipos; }

    /**
     * Calcula el precio final aplicando reglas del sistema:
     * 🔹 < 6 años → GRATIS
     * 🔹 entre 6 y 12 → 50%
     * 🔹 ≥ 60 → 70% de descuento
     * 🔹 socio → 50% adicional
     */
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

        // Descuento adicional por ser socio
        if (audiencia.isEsSocio()) {
            precioFinal *= 0.5;
        }

        return precioFinal;
    }

    /**
     * Verifica si el partido alcanzó su capacidad máxima.
     * Necesario para CapacidadAgotadaException.
     */
    public boolean estaLleno() {
        return this.ticketsVendidos.size() >= this.capacidadMaximaTickets;
    }

    /**
     * Registra que un ticket fue vendido.
     * Más adelante, Sistema usará esta lista para evitar TicketDuplicadoException.
     */
    void agregarTicket(Ticket ticket) {
        this.ticketsVendidos.add(ticket);
    }

    @Override
    public String toString() {
        return "Partido#" + idPartido + " [" + equipos + "] - Cancha: "
                + (reserva != null ? reserva.getCancha().getNombre() : "N/A");
    }
}
