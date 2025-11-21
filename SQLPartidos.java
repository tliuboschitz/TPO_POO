import java.sql.*;
import java.util.List;

public class SQLPartidos {

    protected void SQLPartidos() throws SQLException {
        try {
            Statement stmt = Sistema.Conection.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS TICKET(idTicket INTEGER PRIMARY KEY, Partido INTEGER, dniComprador INTEGER, precioPagado REAL, FOREIGN KEY(Partido) REFERENCES PARTIDO(idPartido), FOREIGN KEY(dniComprador) REFERENCES AUDIENCIA(dniA))");

            stmt.close();


        }
        catch (SQLException e){
            System.err.println("Error al conectar con la base de datos:");
            e.printStackTrace();
        }

    }

    protected static void SQLProcessing(List<Partido> listaPartidos) throws SQLException {
        Statement stmt = Sistema.Conection.createStatement();
        ResultSet resultSet6 = stmt.executeQuery("SELECT idPartido, Reserve, equipos, precioTicketBase from PARTIDO");
        while (resultSet6.next()) {
            int idPartido = resultSet6.getInt("idPartido");
            int ReserveId = resultSet6.getInt("Reserve");
            Reserva reserva = Sistema.buscarReservaPorId(ReserveId);
            String equipos = resultSet6.getString("equipos");
            double precioTicketBase = resultSet6.getDouble("precioTicketBase");
            Partido partido = new Partido(reserva, equipos, precioTicketBase, idPartido );
            listaPartidos.add(partido);
        }
        stmt.close();
    }

    protected static void addTablaPartido(int idPartido,int Reserve, String equipos, double precioTicketBase) throws SQLException {
        try {
            String query = "INSERT OR IGNORE INTO PARTIDO(idPartido, Reserve, equipos, precioTicketBase) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = Sistema.Conection.prepareStatement(query);
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
}
