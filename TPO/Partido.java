import java.util.ArrayList;
import java.util.List;

public class Partido {
        private int idPartido;
        private Reserva reserva; // El partido "usa" una reserva
        private String equipos;
        private double precioTicketBase;
        private int capacidadMaximaTickets; // Ej: 200 entradas
        private List<Ticket> ticketsVendidos;   // Para llevar la cuenta
        
        public Partido(int idPartido, Reserva reserva, String equipos, double precioTicket, int capacidad) {
            this.idPartido = idPartido;
            this.reserva = reserva;
            this.equipos = equipos;
            this.precioTicketBase = precioTicket;
            this.capacidadMaximaTickets = capacidad;
            this.ticketsVendidos = new ArrayList<>();
        }
        public double getPrecioTicketBase() {
            return precioTicketBase;
        }

        public double calcularPrecioFinal(Partido partido, Audiencia audiencia) {
            double precioBase = this.getPrecioTicketBase();
            double precioFinal = precioBase;

        if(audiencia.getEdad() <= 6) {
            precioFinal = 0; 
        } else if (audiencia.getEdad() < 12) {
            precioFinal *= 0.5; 
        } else if (audiencia.getEdad() >= 60) {
            precioFinal *= 0.7; 
        }  

        return precioFinal;
        }

        public boolean estaLleno() {
            return this.ticketsVendidos.size() >= this.capacidadMaximaTickets;
        }
        //Agrega un ticket vendido a la lista interna del partido.
        
        public void agregarTicket(Ticket ticket) {
            this.ticketsVendidos.add(ticket);
        }
}

    