package TPO;

class Mantenimiento {
        private int idMantenimiento;
        private String descripcion;
        private Cancha canchaAfectada;
        private String estado; // "Pendiente", "Terminado"
        
        public Mantenimiento(int id, String desc, Cancha cancha) {
            this.idMantenimiento = id;
            this.descripcion = desc;
            this.canchaAfectada = cancha;
            this.estado = "Pendiente";
        }
    }