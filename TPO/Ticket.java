package TPO;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


 class Ticket {
        private int idTicket;
        private Partido partido;
        private Audiencia comprador;
        private double precioPagado;
        
        
        public Ticket(int idTicket, Partido partido, Audiencia comprador, double precioPagado) {

            this.idTicket = proximoId++; 

            this.idTicket = idTicket;
            this.partido = partido;
            this.comprador = comprador;
            this.precioPagado = precioPagado;
        }
        public calcularPrecioFinal(Partido partido, Audiencia audiencia) {
            double precioBase = partido.getPrecioTicketBase();
            double precioFinal = precioBase;

            if (audiencia.getEdad() < 12) {
                precioFinal *= 0.5; 
            } else if (audiencia.getEdad() >= 60) {
                precioFinal *= 0.7; 
            } else if (audiencia.getEdad() > 6) {
                precioFinal *= 0;

            return precioFinal;
        }
    }
