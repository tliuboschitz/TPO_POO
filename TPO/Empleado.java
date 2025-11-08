package TPO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static TPO.Main.Conection;


class Empleado extends Persona {
        private String rol;
        private List<Mantenimiento> tareasAsignadas;

        public Empleado(String nombre, String apellido, int dni, String rol) throws SQLException {
            super(nombre, apellido, dni);
            this.rol = rol;
            // ¡Inicializar la lista!
            this.tareasAsignadas = new ArrayList<>();
            addTablaE(dni);
        }

        // Getters
        public String getRol() { return rol; }

        public void asignarTarea(Mantenimiento tarea) {
            if(tarea != null) {
                this.tareasAsignadas.add(tarea);
            }
        }

        protected void addTablaE(int dni) throws SQLException {
            try {
            String query = "INSERT or IGNORE INTO EMPLEADO(dniE, rol) VALUES (?, ?)";
            PreparedStatement stmt = Conection.prepareStatement(query);
                stmt.setInt(1, dni);
                stmt.setString(2, rol);
                stmt.executeUpdate();
                stmt.close();
            }
            catch (SQLException ex) {
                throw new SQLException(ex.getMessage());
            }
        }

    @Override
    public String toString() {
        return  super.toString() +
                ", Rol: " + rol ;
    }

    public void gestionarPartido(Partido partido) {
            // Lógica para gestionar un partido...
        }

    }