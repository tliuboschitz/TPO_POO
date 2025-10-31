/**
 * Audiencia: espectador que puede comprar tickets
 * Tiene email, edad, si es socio y tutor si menor
 */
public class Audiencia extends Persona {
    private String email;
    private int edad;
    private boolean esSocio;
    private String tutorNombre;

    public Audiencia(String nombre, String apellido, String dni, String email, int edad, boolean esSocio, String tutor) {
        super(nombre, apellido, dni);
        this.email = email;
        this.edad = edad;
        this.esSocio = esSocio;
        this.tutorNombre = (edad < 18) ? tutor : null;
    }

    public String getEmail() { return email; }
    public int getEdad() { return edad; }
    public boolean isSocio() { return esSocio; }

    @Override
    public String toString() {
        return super.toString() + " - Edad: " + edad + (esSocio ? " (Socio)" : "");
    }
}
