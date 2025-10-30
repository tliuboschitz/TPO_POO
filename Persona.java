 abstract class Persona {
        protected String nombre;
        protected String apellido;
        protected String dni;

        public Persona(String nombre, String apellido, String dni) {
            this.nombre = nombre;
            this.apellido = apellido;
            this.dni = dni;
        }
        // Getters
        public String getNombre() { return nombre; }
        public String getApellido() { return apellido; }
        public String getDni() { return dni; }

        // Método toString() es el estándar en Java para esto
        @Override
        public String toString() {
            return apellido + ", " + nombre + " (DNI: " + dni + ")";
        }
    }