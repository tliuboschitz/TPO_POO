class Empleado extends Persona {
        private String rol;
        private UUID iUuid;
        private List<Mantenimiento> tareasAsignadas;

        public Empleado(String nombre, String apellido, String dni, String rol) {
            super(nombre, apellido, dni);
            this.rol = rol;
            this.iUuid = UUID.randomUUID();
            // ¡Inicializar la lista!
            this.tareasAsignadas = new ArrayList<>(); 
        }
        
        // Getters
        public UUID getiUuid() { return iUuid; }
        public String getRol() { return rol; }

        public void asignarTarea(Mantenimiento tarea) {
            if(tarea != null) {
                this.tareasAsignadas.add(tarea);
            }
        }
        
        
        public void gestionarPartido(Partido partido) {
            // Lógica para gestionar un partido...
        }
    }public class Empleado {

}
