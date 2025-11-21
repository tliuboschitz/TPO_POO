import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SQLAlquilador{
    private SQLAlquilador() throws ClassNotFoundException, SQLException{
        try {
            Statement stmt = Sistema.Conection.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ALQUILADOR(dniAl INTEGER PRIMARY KEY, FOREIGN KEY(dniAl) REFERENCES PERSONA(dni))");
            stmt.close();


        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos:");
            e.printStackTrace();
        }
    }

    protected static void addTableA(int dni) throws SQLException {
        try{
            String query = "INSERT or IGNORE INTO ALQUILADOR(dniAl) VALUES (?)";
            PreparedStatement stmt = Sistema.Conection.prepareStatement(query);
            stmt.setInt(1, dni);
            stmt.executeUpdate();
            stmt.close(); //Se asegura de cerrar la conexion
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected static void SQLProcessing(List<Alquilador> listaAlquiladores) throws SQLException {
        Statement stmt = Sistema.Conection.createStatement();
        ResultSet resultSet3 = stmt.executeQuery("SELECT nombre, apellido, dniAl from ALQUILADOR inner join PERSONA P on P.dni = ALQUILADOR.dniAl");
        while (resultSet3.next()) {
            String nombre = resultSet3.getString("nombre");
            String apellido = resultSet3.getString("apellido");
            int dniAl =  resultSet3.getInt("dniAl");
            Alquilador alquilador = new Alquilador(nombre, apellido, dniAl);
            listaAlquiladores.add(alquilador);
        }
    }



}
