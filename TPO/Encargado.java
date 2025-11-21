package TPO;

import java.sql.SQLException;

/**
 * Encargado: tipo de empleado que puede asignar tareas a otros empleados.
 */
public class Encargado extends Empleado {
    public Encargado(String nombre, String apellido, int dni) throws SQLException {
        super(nombre, apellido, dni, "Encargado");
    }

    // Asigna la tarea dada a un empleado
    public void asignarTareaAEmpleado(Empleado emp, Mantenimiento tarea) {
        if (emp != null && tarea != null) {
            emp.asignarTarea(tarea);
        }
    }
}
