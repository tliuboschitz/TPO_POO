package TPO;

<<<<<<< HEAD
public class Alquilador extends Persona {
    // private int canchasAlquiladas; // <-- CAMBIO: Esto es mala idea.
    // Es mejor que Sistema calcule esto buscando en las reservas,
    // que tener un contador acá que puede desincronizarse.
    // Esta clase es simple, solo guarda datos del alquilador.

    public Alquilador(String nombre, String apellido, String dni) {
        super(nombre, apellido, dni);
=======
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:TPO.db")) {
            String query = "INSERT INTO ALQUILADOR(dniAl) VALUES (?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, dni);  // dni is passed as an integer
                stmt.executeUpdate();
                stmt.close();
            }
        }
>>>>>>> origin/main
    }
}
        