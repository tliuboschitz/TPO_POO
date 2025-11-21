import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SQLEmpleado {

    private SQLEmpleado() throws SQLException {
        Statement stmt = Sistema.Conection.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS EMPLEADO(dniE INTEGER PRIMARY KEY, rol TEXT, FOREIGN KEY(dniE) REFERENCES PERSONA(dni))");
        stmt.close();
    }

    protected static void SQLProcessing(List<Empleado> listaEmpleados) throws SQLException {
        Statement stmt = Sistema.Conection.createStatement();
        ResultSet resultSet2 = stmt.executeQuery("SELECT nombre, apellido, dniE, rol from EMPLEADO inner join PERSONA P on P.dni = EMPLEADO.dniE");
        while (resultSet2.next()) {
            String nombre = resultSet2.getString("nombre");
            String apellido = resultSet2.getString("apellido");
            int dniE =  resultSet2.getInt("dniE");
            String rol = resultSet2.getString("rol");
            Empleado empleado = new Empleado(nombre, apellido, dniE, rol);
            listaEmpleados.add(empleado);
        }
    }

    protected static void addTablaE(int dni, String rol) throws SQLException {
    try {
        String query = "INSERT or IGNORE INTO EMPLEADO(dniE, rol) VALUES (?, ?)";
        PreparedStatement stmt = Sistema.Conection.prepareStatement(query);
        stmt.setInt(1, dni);
        stmt.setString(2, rol);
        stmt.executeUpdate();
        stmt.close();
    }
    catch (SQLException ex) {
        throw new SQLException(ex.getMessage());
    }
}

}
