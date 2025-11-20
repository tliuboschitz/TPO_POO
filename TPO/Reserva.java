package TPO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static TPO.Main.Conection;

public class Reserva {
        private int idReserva;
        private static int proximoId = 1;
        private Date fecha;
        private int hora;
        private Alquilador alquilador;
        private Cancha cancha;
        private double monto;
        private String estado; // "Pendiente", "Confirmada", "Cancelada"

        public Reserva(java.util.Date fecha, int hora, Alquilador alquilador, Cancha cancha) {
            this.idReserva = proximoId++;
            // Incrementa el ID para la próxima reserva, se reinicia cuando se abre el programa
            // pero por lo menos los IDs son únicos durante la ejecución y son simples y legibles.
            this.fecha = fecha;
            this.hora = hora;
            this.alquilador = alquilador;
            this.cancha = cancha;
            this.monto = cancha.getPrecioHora();
            this.estado = "Pendiente";
            addTablaR(alquilador.getDni(), cancha.getIdCancha());

        }

        public int getIdReserva() {
        return idReserva;
        }

        protected void addTablaR(int dniA, int canchaId) {
            try {
                String query = "INSERT OR IGNORE INTO RESERVA(idReserva, date, hora, Alquilador, Cancha, monto, estado, idComprobante) VALUES (?, ?, ?, ?, ? , ?, ?, ?)";
                PreparedStatement stmt = Conection.prepareStatement(query);
                stmt.setInt(1, idReserva);
                stmt.setString(2, fecha.toString());
                stmt.setInt(3, hora);
                stmt.setInt(4,dniA);
                stmt.setInt(5, canchaId);
                stmt.setDouble(6, monto);
                stmt.setString(7, estado);
                stmt.setInt(8, idReserva);
                stmt.executeUpdate();
                stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }



        public void confirmar() { this.estado = "Confirmada"; }
        public void cancelar() { this.estado = "Cancelada"; }

        // Getters
        public Cancha getCancha() { return cancha; }
        public Date getFecha() { return fecha; }
        public int getHora() { return hora; }
        public String getEstado() { return estado; }

        public Comprobante generarComprobante() {
            return new Comprobante(this);
        }

    @Override
    public String toString() {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        return "Reserva{" +
                "idReserva:" + idReserva +
                "\n fecha:" + f.format(fecha) +
                "\n hora:" + hora +
                "\n alquilador:" + alquilador +
                "\n cancha:" + cancha +
                "\n monto:" + monto +
                "\n estado:" + estado + "\n" ;
    }
}
