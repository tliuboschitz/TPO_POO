import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

//  Si en caso de que no exista una tabla de Mantenimiento, se la creara
public class SQLMantenimiento {
    private SQLMantenimiento() throws SQLException {
        Statement stmt = Sistema.Conection.createStatement(); // Conection viene del TPO.db que esta dentro del carpeta.
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS MANTENIMIENTO(idMantenimiento INTEGER PRIMARY KEY, dni INTEGER, descripcion TEXT, CanchaId TEXT, estado TEXT, FOREIGN KEY(dni) REFERENCES EMPLEADO(dniE), FOREIGN KEY(CanchaId) REFERENCES CANCHA(idCancha))");
        stmt.close();
    }

    // Eso permite agregar los elementos en base de dato
    protected static void addTablaM(int idMantenimiento, String descripcion, String idCancha, String estado) throws SQLException {
        try {
            String query = "INSERT OR IGNORE INTO MANTENIMIENTO(idMantenimiento, dni, descripcion, CanchaId, estado) VALUES (?, ?, ?, ?, ?)"; //Si en caso de que haya duplicados, se ignorara
            PreparedStatement stmt = Sistema.Conection.prepareStatement(query);
            stmt.setInt(1, idMantenimiento);
            stmt.setInt(2,  -1); //Equivale a que no haya nadie asignado a la tarea
            stmt.setString(3, descripcion);
            stmt.setString(4, idCancha); // dni es int, no String
            stmt.setString(5, estado);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Eso permite conectar con las tablas del base de dato, asi adjuntando los datos en la lista de los objetos.
    protected static void SQLProcessing(List<Mantenimiento> listaMantenimiento) throws SQLException {
        Statement stmt = Sistema.Conection.createStatement();
        ResultSet resultSet8 = stmt.executeQuery("select idMantenimiento, dni, descripcion, CanchaId, estado from MANTENIMIENTO");

        while(resultSet8.next()) {
            int idMantenimiento = resultSet8.getInt("idMantenimiento");
            int dni = resultSet8.getInt("dni");
            Empleado empleado = Sistema.buscarEmpleadoPorId(dni);
            int canchaid = resultSet8.getInt("CanchaId");
            Cancha cancha = Sistema.buscarCanchaPorId(canchaid);
            String descripcion = resultSet8.getString("descripcion");
            String estadoMantenimiento = resultSet8.getString("estado");
            Mantenimiento mantenimiento = new Mantenimiento( descripcion, cancha, idMantenimiento);
            listaMantenimiento.add(mantenimiento);

        }
    }


}
