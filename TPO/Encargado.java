package TPO;

import java.sql.SQLException;

class Encargado extends Empleado {
    public Encargado(String nombre, String apellido, int dni) throws SQLException {
        super(nombre, apellido, dni); // Rol fijo
    }

    public void asignarTareaAEmpleado(Empleado emp, Mantenimiento tarea) {
        emp.asignarTarea(tarea);
    }
}