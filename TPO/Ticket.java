package TPO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


 class Ticket {
        private int idTicket;
        private Partido partido;
        private Audiencia comprador;
        private double precioPagado;
        
        public Ticket(int idTicket, Partido partido, Audiencia comprador, double precioPagado) {
            this.idTicket = idTicket;
            this.partido = partido;
            this.comprador = comprador;
            this.precioPagado = precioPagado;
            addTablaT(partido.getIdPartido(), comprador.getDni());
        }

        protected void addTablaT(int idPartido, int dniComprador) {
            Connection Conection;
            try {
                Conection = DriverManager.getConnection("jdbc:sqlite:TPO.db");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            String query = "INSERT INTO TICKET(idTicket, Partido, dniComprador, precioPagado) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = Conection.prepareStatement(query)) {
                stmt.setInt(1, idTicket);
                stmt.setInt(2, idPartido);
                stmt.setInt(3, dniComprador);
                stmt.setDouble(4, precioPagado);// dni es int, no String
                stmt.executeUpdate();
                stmt.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }