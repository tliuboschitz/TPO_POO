package TPO;
public class Partido {
        private int idPartido;
        private Reserva reserva; // El partido "usa" una reserva
        private String equipos;
        private double precioTicketBase;
        
        public Partido(int idPartido, Reserva reserva, String equipos, double precioTicket) {
            this.idPartido = idPartido;
            this.reserva = reserva;
            this.equipos = equipos;
            this.precioTicketBase = precioTicket;
        }
    }

    