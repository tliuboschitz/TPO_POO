package TPO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static TPO.Main.Conection;

public class Partido {
        private int idPartido;
        private Reserva reserva; // El partido "usa" una reserva
        private String equipos;
        private double precioTicketBase;

        public Partido(int idPartido, Reserva reserva, String equipos, double precioTicket) throws SQLException {
            this.idPartido = idPartido;
            this.reserva = reserva;
            this.equipos = equipos;
            this.precioTicketBase = precioTicket;
            addTablaPartido(reserva.getIdReserva());
        }

    public int getIdPartido() {
        return idPartido;
    }

    protected void addTablaPartido(int Reserve) throws SQLException {
           try {
           String query = "INSERT OR IGNORE INTO PARTIDO(idPartido, Reserve, equipos, precioTicketBase) VALUES (?, ?, ?, ?)";
           PreparedStatement stmt = Conection.prepareStatement(query);
               stmt.setInt(1, idPartido);
               stmt.setInt(2, Reserve);
               stmt.setString(3, equipos);
               stmt.setDouble(4, precioTicketBase);
               stmt.executeUpdate();
               stmt.close();
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }


    }

    @Override
    public String toString() {
        return "Partido:" +
                "\n PartidoID: " + idPartido +
                "\n ReservaID: " + reserva.getIdReserva() +
                "\n equipos:" + equipos +
                "\n precioTicketBase: " + precioTicketBase;
    }
}

    