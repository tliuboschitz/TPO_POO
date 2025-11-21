// Audiencia.java
public class Audiencia extends Persona {

    // Email del espectador
    private String email;

    // Edad del espectador → Se usa para aplicar descuentos y ver si requiere tutor
    private int edad;

    // Si es socio, tiene descuentos adicionales
    private boolean esSocio;

    // Nombre del tutor (SOLO para menores)
    // IMPORTANTE: este campo es necesario para MenorSinTutorException
    private String tutorNombre;

    /**
     * Constructor de Audiencia
     * @param tutor → SOLO se guarda si la persona es menor (edad < 18)
     */
    public Audiencia(String nombre, String apellido, String dni,
                     String email, int edad, boolean esSocio, String tutor) {

        super(nombre, apellido, dni);

        this.email = email;
        this.edad = edad;
        this.esSocio = esSocio;

        // 🔹 Reglas del sistema:
        //    - Si es menor de 18 → debe proporcionar tutor
        //    - Si NO es menor → tutorNombre queda en null
        this.tutorNombre = (edad < 18) ? tutor : null;
    }

    // Getters necesarios para validaciones del Sistema
    public String getEmail() { return email; }
    public int getEdad() { return edad; }
    public boolean isEsSocio() { return esSocio; }

    // Getter usado para MenorSinTutorException
    public String getTutorNombre() { return tutorNombre; }

    @Override
    public String toString() {
        return super.toString() + " - Edad: " + edad + (esSocio ? " (Socio)" : "");
    }
}
