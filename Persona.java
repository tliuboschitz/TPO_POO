// Persona.java
public class Persona {
    protected String nombre;
    protected String apellido;
    protected int dni;

    public Persona(String nombre, String apellido, int dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public int getDni() { return dni; }
    @Override
    public String toString() { return apellido + ", " + nombre + " (DNI: " + dni + ")"; }
    // Setters si se necesitan (no los agregamos por ahora para respetar modelo)
}
