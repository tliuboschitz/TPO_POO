package TPO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class Audiencia extends Persona {
<<<<<<< HEAD
    private String email;
    private int edad;      
    private boolean esSocio; 
    private String tutorNombre; 
    public Audiencia(String nombre, String apellido, String dni, String email, int edad, boolean esSocio, String tutor) {
        super(nombre, apellido, dni);
        this.email = email;
        this.edad = edad;
        this.esSocio = esSocio;
        this.tutorNombre = (edad < 18) ? tutor : null; // Asigna tutor solo si es menor
=======
        private String email;
        private int edad;
        private boolean esSocio;
        private int dniTutor;
        public Audiencia(String nombre, String apellido, int dni, String email, int edad, boolean esSocio, int dniTutor) throws SQLException {
            super(nombre, apellido, dni);
            this.email = email;
            this.edad = edad;
            this.esSocio = esSocio;
            this.dniTutor = (edad < 18) ? dniTutor : null; // Asigna tutor solo si es menor
            addTablaA(dni);
        }

        // Getters
        public String getEmail() { return email; }
        public int getEdad() { return edad; }
        public boolean isEsSocio() { return esSocio; } // "is" es la convención para boolean

        // NOTA: Eliminé getDireccion() y getTelefono() porque no tenías esos atributos.

        protected void addTablaA(int dni) throws SQLException{
            Connection Conection;
            try {
                Conection = DriverManager.getConnection("jdbc:sqlite:TPO.db");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            String query = "INSERT INTO AUDIENCIA(dniA, email, edad, esSocio, dniTutor) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = Conection.prepareStatement(query)) {
                stmt.setInt(1, dni);
                stmt.setString(2, email);
                stmt.setInt(3, edad);
                stmt.setBoolean(4, esSocio);
                stmt.setInt(5, dniTutor);
                stmt.executeUpdate();
                stmt.close();
            }


        }
        public void comprarTicket(Partido partido) {
            // Lógica para comprar un ticket...
        }
>>>>>>> origin/main
    }

    // Getters
    public String getEmail() { return email; }
    public int getEdad() { return edad; }
    public boolean isEsSocio() { return esSocio; } // "is" es la convención para boolean
    
    // NOTA: Eliminé getDireccion() y getTelefono() porque no tenías esos atributos.

    public void comprarTicket(Partido partido) {
        // Lógica para comprar un ticket...
    }
}
