package TPO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.List;

public class SQLAudiencia {
    private SQLAudiencia() {
        try {
            Statement stmt = Sistema.Conection.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS AUDIENCIA(dniA INTEGER PRIMARY KEY, email TEXT, edad INTEGER, esSocio BOOLEAN, dniTutor INTEGER, FOREIGN KEY(dniA) REFERENCES PERSONA(dni), FOREIGN KEY(dniTutor) REFERENCES PERSONA(dni))");
            stmt.close();
        }catch(SQLException e){
            System.out.println(e);
        }
    }

    protected static void SQLProcessing(List<Audiencia> listaAudiencia) throws SQLException, ParseException {
        try {
            Statement stmt = Sistema.Conection.createStatement();
            ResultSet resultSet4 = stmt.executeQuery("SELECT nombre, apellido, dniA, email, edad, esSocio, dniTutor from AUDIENCIA inner join PERSONA P on P.dni = AUDIENCIA.dniA");
            while (resultSet4.next()) {
                String nombre = resultSet4.getString("nombre");
                String apellido = resultSet4.getString("apellido");
                int dniA = resultSet4.getInt("dniA");
                String email = resultSet4.getString("email");
                int edad = resultSet4.getInt("edad");
                boolean esSocio = resultSet4.getBoolean("esSocio");
                int dniTutor = resultSet4.getInt("dniTutor");
                Audiencia audiencia = new Audiencia(nombre, apellido, dniA, email, edad, esSocio, dniTutor);
                listaAudiencia.add(audiencia);
            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }

    }

    protected static void addTablaAu(int dni, String email, int edad, boolean esSocio, int dniTutor) throws SQLException{
        try {String query = "INSERT or IGNORE INTO AUDIENCIA(dniA, email, edad, esSocio, dniTutor) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = Sistema.Conection.prepareStatement(query);
            stmt.setInt(1, dni);
            stmt.setString(2, email);
            stmt.setInt(3, edad);
            stmt.setBoolean(4, esSocio);
            stmt.setInt(5, dniTutor);
            stmt.executeUpdate();
            stmt.close();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }



}
