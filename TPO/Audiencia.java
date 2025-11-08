package TPO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static TPO.Main.Conection;

class Audiencia extends Persona {
        private String email;
        private int edad;
        private boolean esSocio;
        private int dniTutor;
        public Audiencia(String nombre, String apellido, int dni, String email, int edad, boolean esSocio, int dniTutor) throws SQLException {
            super(nombre, apellido, dni);
            this.email = email;
            this.edad = edad;
            this.esSocio = esSocio;
            this.dniTutor = (edad < 18) ? dniTutor : -1; // Asigna tutor solo si es menor
            addTablaA(dni);
        }

        // Getters
        public String getEmail() { return email; }
        public int getEdad() { return edad; }
        public boolean isEsSocio() { return esSocio; } // "is" es la convención para boolean
        protected void addTablaA(int dni) throws SQLException{

            try {String query = "INSERT or IGNORE INTO AUDIENCIA(dniA, email, edad, esSocio, dniTutor) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = Conection.prepareStatement(query);
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
        public void comprarTicket(Partido partido) {
            // Lógica para comprar un ticket...
        }

    @Override
    public String toString() {
        return super.toString() +
                ", Edad: " + edad +
                ", esSocio: " + esSocio +
                ", dniTutor:" + dniTutor ;
    }
}
