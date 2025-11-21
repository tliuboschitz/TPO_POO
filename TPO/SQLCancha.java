package TPO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SQLCancha {

    private SQLCancha() throws SQLException {
        Statement stmt = Sistema.Conection.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS CANCHA(idCancha INTEGER PRIMARY KEY, nombreC TEXT, tipo TEXT, precioHora REAL, estado TEXT)");
        stmt.close();
    }

    protected static void SQLProcessing(List<Cancha> listaCanchas) throws SQLException {
        try{
            Statement stmt = Sistema.Conection.createStatement();
            ResultSet resultSet = stmt.executeQuery("Select nombreC, tipo, precioHora from CANCHA where estado like 'Disponible'");
            while (resultSet.next()) {
                String c = resultSet.getString("nombreC");
                String tipo = resultSet.getString("tipo");
                double precioHora = resultSet.getDouble("precioHora");
                Cancha cancha = new Cancha(c, tipo, precioHora);
                listaCanchas.add(cancha);
            }
            stmt.close();
        }catch(SQLException e){
            throw e;
        }



    }

    protected static void addTablaC(String idCancha, String nombre, String tipo, double precioHora, String estado) {
        try {
            String query = "INSERT or IGNORE INTO CANCHA(idCancha, nombreC, tipo, precioHora, estado) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = Sistema.Conection.prepareStatement(query);
            stmt.setString(1, idCancha);
            stmt.setString(2, nombre);
            stmt.setString(3, tipo);
            stmt.setDouble(4, precioHora);
            stmt.setString(5, estado);
            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }





}
