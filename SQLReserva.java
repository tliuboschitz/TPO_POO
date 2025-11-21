
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class SQLReserva {
    private SQLReserva() throws SQLException {
        Statement stmt = Sistema.Conection.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS RESERVA(idReserva INTEGER PRIMARY KEY, date TEXT, hora TEXT, Alquilador INTEGER, Cancha INTEGER, monto REAL, estado TEXT, idComprobante INTEGER, FOREIGN KEY(Alquilador) REFERENCES PERSONA(dni), FOREIGN KEY(Cancha) REFERENCES CANCHA(idCancha))");
        stmt.close();
    }

    protected static void addTablaR(int idReserva, int fecha, String hora, int dniA, int canchaId, double monto, String estado, Object idComprobante) throws SQLException {
        try {
            String query = "INSERT OR IGNORE INTO RESERVA(idReserva, date, hora, Alquilador, Cancha, monto, estado, idComprobante) VALUES (?, ?, ?, ?, ? , ?, ?, ?)";
            PreparedStatement stmt = Sistema.Conection.prepareStatement(query);
            stmt.setInt(1, idReserva);
            stmt.setString(2, String.valueOf(fecha));
            stmt.setString(3, hora);
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

    protected static void SQLProcessing(List<Reserva> listaReservas) throws SQLException, ParseException {
        Statement stmt = Sistema.Conection.createStatement();
        ResultSet resultSet5 = stmt.executeQuery("select idReserva, date, hora, Alquilador, Cancha, monto, estado, idComprobante from RESERVA");
        while(resultSet5.next()) {
            int idReserva = resultSet5.getInt("idReserva");
            String dateInt = resultSet5.getString("date");
            SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
            f.setLenient(false);
            java.util.Date date = f.parse(dateInt);

            String hora = resultSet5.getString("hora");
            int AlquiladorId = resultSet5.getInt("Alquilador");
            Alquilador alquilador = Sistema.buscarAlquiladorbyId(AlquiladorId);
            int CanchaA = resultSet5.getInt("Cancha");
            Cancha  cancha = Sistema.buscarCanchaPorId(CanchaA);
            int monto = resultSet5.getInt("monto");
            int estado = resultSet5.getInt("estado");
            int idComprobante = resultSet5.getInt("idComprobante");
            Reserva reserva = new Reserva(date, hora, alquilador, cancha);
            listaReservas.add(reserva);
        }
    }


}
