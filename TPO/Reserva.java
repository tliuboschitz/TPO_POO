package TPO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Reserva {
        private int idReserva;
        private static int proximoId = 1;
        private Date fecha;
        private String hora;
        private Alquilador alquilador;
        private Cancha cancha;
        private double monto;
        private String estado; // "Pendiente", "Confirmada", "Cancelada"

        public Reserva(java.util.Date fecha2, String hora, Alquilador alquilador, Cancha cancha) {
            this.idReserva = proximoId++;
            // Incrementa el ID para la próxima reserva, se reinicia cuando se abre el programa
            // pero por lo menos los IDs son únicos durante la ejecución y son simples y legibles.
            this.fecha = fecha2;
            this.hora = hora;
            this.alquilador = alquilador;
            this.cancha = cancha;
            this.monto = cancha.getPrecioHora(); 
            this.estado = "Pendiente";

        }
        
        public int getIdReserva() {
        return idReserva;
        }

        public void confirmar() { this.estado = "Confirmada"; }
        public void cancelar() { this.estado = "Cancelada"; }
        
        // Getters
        public Cancha getCancha() { return cancha; }
        public Date getFecha() { return fecha; }
        public String getHora() { return hora; }
        public String getEstado() { return estado; }

        public Comprobante generarComprobante() {
            return new Comprobante(this);
        }
    }
