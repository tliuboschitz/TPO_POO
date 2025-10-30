 class Ticket {
        private int idTicket;
        private Partido partido;
        private Audiencia comprador;
        private double precioPagado;
        
        public Ticket(int idTicket, Partido partido, Audiencia comprador, double precioPagado) {
            this.idTicket = idTicket;
            this.partido = partido;
            this.comprador = comprador;
            this.precioPagado = precioPagado;
        }
    }