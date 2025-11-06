package TPO;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


 class Ticket {
        private int idTicket;
        private Partido partido;
        private Audiencia comprador;
        private double precioPagado;
        private static int proximoId = 1; // Variable estática para el próximo ID
        
        
        public Ticket(Partido partido, Audiencia comprador, double precioPagado) {

            this.idTicket = proximoId++; 

            
            this.partido = partido;
            this.comprador = comprador;
            this.precioPagado = precioPagado;
        }
       
    }
