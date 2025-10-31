import java.util.Date;

/**
 * Comprobante: generado al confirmar una reserva.
 */
public class Comprobante {
    private static int contador = 1;
    private int idComprobante;
    private Reserva reserva;
    private Date fechaEmision;
    private double monto;

    public Comprobante(Reserva reserva) {
        this.idComprobante = contador++;
        this.reserva = reserva;
        this.fechaEmision = new Date();
        this.monto = reserva != null ? reserva.getMonto() : 0.0;
    }

    public int getIdComprobante() { return idComprobante; }
    public Reserva getReserva() { return reserva; }
    public Date getFechaEmision() { return fechaEmision; }
    public double getMonto() { return monto; }

    @Override
    public String toString() {
        return "Comprobante#" + idComprobante + " - Reserva#" + (reserva!=null?reserva.getIdReserva():"N/A") + " - $" + monto;
    }
}
