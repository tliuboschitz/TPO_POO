class Audiencia extends Persona {
        private String email;
        private int edad;      
        private boolean esSocio; 
        private String tutorNombre; 
        public Audiencia(String nombre, String apellido, String dni, String email, int edad, boolean esSocio, String tutor) {
            super(nombre, apellido, dni);
            this.email = email;
            this.edad = edad;
            this.esSocio = esSocio;
            this.tutorNombre = (edad < 18) ? tutor : null; // Asigna tutor solo si es menor
        }

        // Getters
        public String getEmail() { return email; }
        public int getEdad() { return edad; }
        public boolean isEsSocio() { return esSocio; } // "is" es la convención para boolean
        
        // NOTA: Eliminé getDireccion() y getTelefono() porque no tenías esos atributos.

        public void comprarTicket(Partido partido) {
            // Lógica para comprar un ticket...
        }
    }
