// Audiencia.java

import java.sql.SQLException;

/**
 * Audiencia: espectador que puede comprar tickets
 * Tiene email, edad, si es socio y tutor si menor
 */
public class Audiencia extends Persona {
    private String email;
    private int edad;
    private boolean esSocio;
    private int dniTutor;

    public Audiencia(String nombre, String apellido, int dni, String email, int edad, boolean esSocio, int dniTutor) throws SQLException {
        super(nombre, apellido, dni);
        this.email = email;
        this.edad = edad;
        this.esSocio = esSocio;
        this.dniTutor = (edad < 18) ? dniTutor : 0;
        SQLAudiencia.addTablaAu(dni, email, edad, esSocio, dniTutor);
    }

    public String getEmail() { return email; }
    public int getEdad() { return edad; }
    public boolean isEsSocio() { return esSocio; }
    public int getTutorNombre() { return dniTutor; }

    // ---- Setters agregados para permitir editar desde UI ----
    public void setEmail(String email) { this.email = email; }
    public void setEdad(int edad) { this.edad = edad; }
    public void setEsSocio(boolean esSocio) { this.esSocio = esSocio; }
    public void setTutorNombre(int dniTutor) { this.dniTutor = dniTutor; }

    @Override
    public String toString() {
        return super.toString() + " - Edad: " + edad + (esSocio ? " (Socio)" : "");
    }
}
