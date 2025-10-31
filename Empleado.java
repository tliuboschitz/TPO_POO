import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Empleado: trabajador del complejo.
 * Tiene rol, UUID y listas de tareas/partidos.
 */
public class Empleado extends Persona {
    private String rol;
    private UUID idEmpleado;
    private List<Mantenimiento> tareasAsignadas;
    private List<Partido> partidosACargo;

    public Empleado(String nombre, String apellido, String dni, String rol) {
        super(nombre, apellido, dni);
        this.rol = rol;
        this.idEmpleado = UUID.randomUUID();
        this.tareasAsignadas = new ArrayList<>();
        this.partidosACargo = new ArrayList<>();
    }

    public String getRol() { return rol; }
    public UUID getIdEmpleado() { return idEmpleado; }

    // Añade una tarea de mantenimiento al empleado
    public void asignarTarea(Mantenimiento tarea) {
        if (tarea != null) tareasAsignadas.add(tarea);
    }

    // Simula que el empleado gestiona un partido
    public void gestionarPartido(Partido partido) {
        if (partido != null && !partidosACargo.contains(partido)) {
            partidosACargo.add(partido);
        }
        System.out.println("Empleado " + nombre + " gestiona partido: " + (partido!=null?partido.getEquipos():"N/A"));
    }

    public List<Mantenimiento> getTareasAsignadas() { return new ArrayList<>(tareasAsignadas); }
    public List<Partido> getPartidosACargo() { return new ArrayList<>(partidosACargo); }

    @Override
    public String toString() {
        return super.toString() + " - Rol: " + rol;
    }
}
