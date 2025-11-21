package TPO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SQLPersona {
//  Si en caso de que no exista una tabla de Mantenimiento, se la creara
    private SQLPersona() throws SQLException {
        Statement stmt = Sistema.Conection.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS PERSONA(nombre TEXT, apellido TEXT, dni INTEGER PRIMARY KEY NOT NULL)");
        stmt.close();
    }
    // Eso permite conectar con las tablas del base de dato, asi adjuntando los datos en la lista de los objetos.
    protected void SQLProcessing(List<Persona> listaPersonas) throws SQLException {
        Statement stmt = Sistema.Conection.createStatement();
        ResultSet resultSet1 = stmt.executeQuery("SELECT nombre, apellido, dni FROM PERSONA");
        while (resultSet1.next()) {
            String nombre = resultSet1.getString("nombre");
            String apellido = resultSet1.getString("apellido");
            int dni =  resultSet1.getInt("dni");
            Persona persona = new Persona(nombre, apellido, dni);
            listaPersonas.add(persona);
        }
        stmt.close();
    }
    // Eso permite conectar con las tablas del base de dato, asi adjuntando los datos en la lista de los objetos.
    protected static void addTableP(String nombre, String apellido, int dni) throws SQLException {
        try {
            String query = "INSERT or IGNORE INTO PERSONA(nombre, apellido, dni) VALUES (?, ?, ?)";
            PreparedStatement stmt = Sistema.Conection.prepareStatement(query);
            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setInt(3, dni); // dni es int, no String
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }



}
