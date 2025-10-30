  class Comprobante {
        private int idComprobante;
        private Reserva reserva;
        private Date fechaEmision;
        
        public Comprobante(Reserva reserva) {
            this.reserva = reserva;
            this.fechaEmision = new Date(); // Fecha de hoy
            // (generar un ID...)
        }
    }