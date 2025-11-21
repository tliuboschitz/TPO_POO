package TPO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@SuppressWarnings("unused")

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
