package TPO;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


 class Ticket {
        private int idTicket;
        private Partido partido;
        private Audiencia comprador;
        private double precioPagado;
        
        
        public Ticket(Partido partido, Audiencia comprador, double precioPagado) {

            this.idTicket = proximoId++; 

            this.idTicket = idTicket;
            this.partido = partido;
            this.comprador = comprador;
            this.precioPagado = precioPagado;
        }
       
    }
