package TPO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class Mantenimiento {
<<<<<<< HEAD
    private int idMantenimiento;
    private String descripcion;
    private Cancha canchaAfectada;
    private String estado; // "Pendiente", "Terminado"
    
    public Mantenimiento(int id, String desc, Cancha cancha) {
        this.idMantenimiento = id;
        this.descripcion = desc;
        this.canchaAfectada = cancha;
        this.estado = "Pendiente";
    }
}
=======
        private int idMantenimiento;
        private String descripcion;
        private Cancha canchaAfectada;
        private String estado; // "Pendiente", "Terminado"

        public Mantenimiento(int id, String desc, Cancha cancha) throws SQLException {
            this.idMantenimiento = id;
            this.descripcion = desc;
            this.canchaAfectada = cancha;
            this.estado = "Pendiente";
            addTablaM(cancha.getIdCancha()  );
        }


        protected void addTablaM(int idCancha) throws SQLException {
            Connection Conection;
            try {
                Conection = DriverManager.getConnection("jdbc:sqlite:TPO.db");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            String query = "INSERT INTO MANTENIMIENTO(idMantenimiento, dni, descripcion, CanchaId, estado) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = Conection.prepareStatement(query)) {
                stmt.setInt(1, idMantenimiento);
                stmt.setInt(2,  0); //Equivale a que no haya nadie asignado a la tarea
                stmt.setString(3, descripcion);
                stmt.setInt(4, idCancha); // dni es int, no String
                stmt.setString(5, estado);
                stmt.executeUpdate();
                stmt.close();
            }
        }
    }
>>>>>>> origin/main
