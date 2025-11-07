package TPO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


class Empleado extends Persona {
        private String rol;
        private List<Mantenimiento> tareasAsignadas;

        public Empleado(String nombre, String apellido, int dni, String rol) throws SQLException {
            super(nombre, apellido, dni);
            this.rol = rol;
            // ¡Inicializar la lista!
            this.tareasAsignadas = new ArrayList<>(); 
        }
        
        // Getters
        public String getRol() { return rol; }

        public void asignarTarea(Mantenimiento tarea) {
            if(tarea != null) {
                this.tareasAsignadas.add(tarea);
            }
        }
        
        
        public void gestionarPartido(Partido partido) {
            // Lógica para gestionar un partido...
        }

    }