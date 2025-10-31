package TPO;
    class Encargado extends Empleado {
        public Encargado(String nombre, String apellido, String dni) {
            super(nombre, apellido, dni, "Encargado"); // Rol fijo
        }

        public void asignarTareaAEmpleado(Empleado emp, Mantenimiento tarea) {
            emp.asignarTarea(tarea);
        }
    }


    