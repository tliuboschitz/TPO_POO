package TPO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static TPO.Main.Conection;


public abstract class Persona {
        private String nombre;
        private String apellido;
        private int dni;



        public Persona(String nombre, String apellido, int dni) throws SQLException {
            this.nombre = nombre;
            this.apellido = apellido;
            this.dni = dni;
            addTableP(Main.Conection);

        }

        // Getters
        public String getNombre() { return nombre; }
        public String getApellido() { return apellido; }
        public int getDni() { return dni; }

        private void addTableP(Connection conn) throws SQLException {
            String query = "INSERT INTO PERSONA (nombre, apellido, dni) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, nombre);
                stmt.setString(2, apellido);
                stmt.setInt(3, dni); // dni es int, no String
                stmt.executeUpdate();
            }
        }



    // Método toString() es el estándar en Java para esto
        @Override
        public String toString() {

            return apellido + ", " + nombre + " (DNI: " + dni + ")";
        }
    }