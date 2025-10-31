import java.util.Date;

/**
 * Reserva: reserva de cancha por un alquilador en fecha/hora.
 * Estado: Pendiente/Confirmada/Cancelada.
 */
public class Reserva {
    private static int proximoId = 1;
    private int idReserva;
    private Date fecha;
    private String hora;
    private Alquilador alquilador;
    private Cancha cancha;
    private double monto;
    private String estado;

    public Reserva(Date fecha, String hora, Alquilador alquilador, Cancha cancha) {
        this.idReserva = proximoId++;
        this.fecha = fecha;
        this.hora = hora;
        this.alquilador = alquilador;
        this.cancha = cancha;
        this.monto = cancha != null ? cancha.getPrecioHora() : 0.0;
        this.estado = "Pendiente";
    }

    public int getIdReserva() { return idReserva; }
    public Date getFecha() { return fecha; }
    public String getHora() { return hora; }
    public Alquilador getAlquilador() { return alquilador; }
    public Cancha getCancha() { return cancha; }
    public double getMonto() { return monto; }
    public String getEstado() { return estado; }

    public void confirmar() { this.estado = "Confirmada"; }
    public void cancelar() { this.estado = "Cancelada"; }

    // Genera comprobante al confirmar
    public Comprobante generarComprobante() { return new Comprobante(this); }

    @Override
    public String toString() {
        return "Reserva#" + idReserva + " - Cancha: " + (cancha!=null?cancha.getNombre():"N/A")
                + " - Fecha: " + fecha + " - Hora: " + hora + " - Estado: " + estado;
    }
}
