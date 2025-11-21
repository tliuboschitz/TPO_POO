package TPO;

import java.sql.SQLException;

/**
 * Alquilador: cliente que reserva canchas.
 * Hereda de Persona.
 */
public class Alquilador extends Persona {

    public Alquilador(String nombre, String apellido, int dni) throws SQLException {
        super(nombre, apellido, dni);
        SQLAlquilador.addTableA(dni);
    }

    @Override
    public int getDni() {
        return super.getDni();
    }
}
