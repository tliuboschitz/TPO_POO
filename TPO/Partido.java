package TPO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    protected void addTablaPartido(int idReserva) throws SQLException {
           Connection Conection;
           try {
               Conection = DriverManager.getConnection("jdbc:sqlite:TPO.db");

           } catch (SQLException e) {
               throw new RuntimeException(e);
           }

           String query = "INSERT INTO PARTIDO(idPartido, Reserve, equipos, precioTicketBase) VALUES (?, ?, ?, ?)";
           try (PreparedStatement stmt = Conection.prepareStatement(query)) {
               stmt.setInt(1, idPartido);
               stmt.setInt(2, idReserva);
               stmt.setString(3, equipos);
               stmt.setDouble(4, precioTicketBase);
               stmt.executeUpdate();
               stmt.close();
           }


        }
    }

    