package TPO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static TPO.Main.Conection;

public class Alquilador extends Persona {
    // private int canchasAlquiladas; // <-- CAMBIO: Esto es mala idea.
    // Es mejor que Sistema calcule esto buscando en las reservas,
    // que tener un contador acá que puede desincronizarse.
    // Esta clase es simple, solo guarda datos del alquilador.

    public Alquilador(String nombre, String apellido, int dni) throws SQLException {

        super(nombre, apellido, dni);
        addTableA(dni);
    }

    protected void addTableA(int dni) throws SQLException {
        try{
            String query = "INSERT or IGNORE INTO ALQUILADOR(dniAl) VALUES (?)";
            PreparedStatement stmt = Conection.prepareStatement(query);
                stmt.setInt(1, dni);  // dni is passed as an integer
                stmt.executeUpdate();
                stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
        