package TPO;
import java.util.Date;

class Comprobante {
    private int idComprobante;
    private int proximoId = 1;
    private Reserva reserva;
    private Date fechaEmision;
    
    public Comprobante(Reserva reserva) {
        this.reserva = reserva;
        this.idComprobante = proximoId++;
        this.fechaEmision = new Date(); // Fecha de hoy
        // (generar un ID...)
    }
}
