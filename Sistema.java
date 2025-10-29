package TPO.TPO_POO;
import java.util.*;

public class Sistema {
    private List<Cancha> listaCanchas;
    private List<Reserva> listaReservas;
    private List<Empleado> listaEmpleados;
    private List<Cliente> listaClientes;
    private List<Partido> listaPartidos;
    private List<Ticket> listaTickets;

    public Sistema() {
        listaCanchas = new ArrayList<>();
        listaReservas = new ArrayList<>();
        listaEmpleados = new ArrayList<>();
        listaClientes = new ArrayList<>();
        listaPartidos = new ArrayList<>();
        listaTickets = new ArrayList<>();
    }

    public void registrarCancha(Cancha cancha) {
        listaCanchas.add(cancha);
    }

    public void registrarEmpleado(Empleado empleado) {
        listaEmpleados.add(empleado);
    }

    public void registrarCliente(Cliente cliente) {
        listaClientes.add(cliente);
    }

    public Cancha buscarCanchaDisponible(Date fecha, String hora) {
        for (Cancha c : listaCanchas) {
            if (c.getEstado().equalsIgnoreCase("disponible")) return c;
        }
        return null;
    }

    public void mostrarPartidos() {
        for (Partido p : listaPartidos) System.out.println(p);
    }
}
