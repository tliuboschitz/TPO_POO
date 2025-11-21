import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SQLTicket {
    private void Ticket() throws SQLException {
        Statement stmt = Sistema.Conection.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS PARTIDO(idPartido INTEGER PRIMARY KEY, Reserve INTEGER, equipos TEXT, precioTicketBase REAL, FOREIGN KEY(Reserve) REFERENCES RESERVA(idReserva))");
        stmt.close();
    }
    protected static void addTablaT(int idTicket, int idPartido, int dniComprador, double precioPagado) throws SQLException {
        try {
            String query = "INSERT OR IGNORE INTO TICKET(idTicket, Partido, dniComprador, precioPagado) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = Sistema.Conection.prepareStatement(query);
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

    protected static void SQLProcessing(List<Ticket> listaTickets) throws SQLException {
        Statement stmt = Sistema.Conection.createStatement();
        ResultSet resultSet7 = stmt.executeQuery("select idTicket, Partido, dniComprador, precioPagado from TICKET");
        while (resultSet7.next()) {
            int idTicket = resultSet7.getInt("idTicket");
            int PartidoId = resultSet7.getInt("Partido");
            Partido partido = Sistema.buscarPartidoPorId(PartidoId);
            int dniComprador = resultSet7.getInt("dniComprador");
            Audiencia comprador= Sistema.buscarAudienciaPorId(dniComprador);
            int precioPagado = resultSet7.getInt("precioPagado");
            Ticket ticket = new Ticket(idTicket, partido, comprador, precioPagado );
            listaTickets.add(ticket);

        }
    }




}
