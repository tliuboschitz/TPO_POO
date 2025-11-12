package TPO;
<<<<<<< HEAD
class Encargado extends Empleado {
    public Encargado(String nombre, String apellido, String dni) {
        super(nombre, apellido, dni, "Encargado"); // Rol fijo
=======

import java.sql.SQLException;

class Encargado extends Empleado {
        public Encargado(String nombre, String apellido, int dni) throws SQLException {
            super(nombre, apellido, dni, "Encargado"); // Rol fijo
        }

        public void asignarTareaAEmpleado(Empleado emp, Mantenimiento tarea) {
            emp.asignarTarea(tarea);
        }
>>>>>>> origin/main
    }

    public void asignarTareaAEmpleado(Empleado emp, Mantenimiento tarea) {
        emp.asignarTarea(tarea);
    }
}