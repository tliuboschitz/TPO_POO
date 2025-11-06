package TPO;

public class Alquilador extends Persona {
    // private int canchasAlquiladas; // <-- CAMBIO: Esto es mala idea.
    // Es mejor que Sistema calcule esto buscando en las reservas,
    // que tener un contador acá que puede desincronizarse.
    // Esta clase es simple, solo guarda datos del alquilador.

    public Alquilador(String nombre, String apellido, String dni) {
        super(nombre, apellido, dni);
    }
}
        