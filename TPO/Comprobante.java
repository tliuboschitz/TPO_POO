package TPO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

<<<<<<< HEAD
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
=======
 class Comprobante {
        private int idComprobante;
        private Reserva reserva;
        private Date fechaEmision;
        
        public Comprobante(Reserva reserva) {
            this.reserva = reserva;
            this.fechaEmision = new Date(); // Fecha de hoy
            // (generar un ID...)
            // cambiar el generado en idCom si esta
        }

    }
>>>>>>> origin/main
