// Empleado.java
import java.util.ArrayList;
import java.util.List;

/**
 * Empleado: trabajador del complejo.
 */
public class Empleado extends Persona {
    private String rol;
    private int idEmpleado;
    private List<Mantenimiento> tareasAsignadas;
    private List<Partido> partidosACargo;
    private static int proximoId = 1;

    public Empleado(String nombre, String apellido, String dni, String rol) {
        super(nombre, apellido, dni);
        this.rol = rol;
        this.idEmpleado = proximoId++;
        this.tareasAsignadas = new ArrayList<>();
        this.partidosACargo = new ArrayList<>();
    }

    public int getIdEmpleado() { return idEmpleado; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public void asignarTarea(Mantenimiento tarea) { if (tarea != null) this.tareasAsignadas.add(tarea); }
    public void gestionarPartido(Partido partido) {
        if (partido != null && !partidosACargo.contains(partido)) partidosACargo.add(partido);
        System.out.println("Empleado " + super.toString() + " gestiona partido: " + (partido!=null?partido.getEquipos():"N/A"));
    }

    public List<Mantenimiento> getTareasAsignadas() { return new ArrayList<>(tareasAsignadas); }
    public List<Partido> getPartidosACargo() { return new ArrayList<>(partidosACargo); }

    @Override
    public String toString() {
        return super.toString() + " - Rol: " + rol + " (ID:" + idEmpleado + ")";
    }
}
