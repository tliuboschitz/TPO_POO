package TPO;

/**
 * Persona (abstracta): base para todos los roles humanos del sistema.
 * Contiene nombre, apellido y dni.
 */
public class Persona {
    protected String nombre;
    protected String apellido;
    protected int dni;

    public Persona(String nombre, String apellido, int dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }
    // Getters
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public int getDni() { return dni; }

    // Método toString() es el estándar en Java para esto
    @Override
    public String toString() {
        return apellido + ", " + nombre + " (DNI: " + dni + ")";
    }
}
