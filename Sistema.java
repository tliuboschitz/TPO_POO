import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Sistema: controlador central
 * Contiene listas maestras y la logica de negocio principal.
 */
public class Sistema {

    private List<Cancha> listaCanchas;
    private List<Reserva> listaReservas;
    private List<Empleado> listaEmpleados;
    private List<Alquilador> listaAlquiladores;
    private List<Audiencia> listaAudiencias;
    private List<Partido> listaPartidos;
    private List<Ticket> listaTickets;
    private List<Mantenimiento> listaMantenimientos;

    private int proximoIdPartido = 1; // id secuencial para partidos

    public Sistema() {
        this.listaCanchas = new ArrayList<>();
        this.listaReservas = new ArrayList<>();
        this.listaEmpleados = new ArrayList<>();
        this.listaAlquiladores = new ArrayList<>();
        this.listaAudiencias = new ArrayList<>();
        this.listaPartidos = new ArrayList<>();
        this.listaTickets = new ArrayList<>();
        this.listaMantenimientos = new ArrayList<>();

        // Carga inicial de canchas de ejemplo
        listaCanchas.add(new Cancha("Cancha 1 (F11)", "F11", 10000.0));
        listaCanchas.add(new Cancha("Cancha 2 (F9)", "F9", 8000.0));
        listaCanchas.add(new Cancha("Cancha 3 (F5)", "F5", 5000.0));
    }

    // --- REGISTROS ---
    public void registrarCancha(Cancha c) { if (c!=null) listaCanchas.add(c); }
    public void registrarEmpleado(Empleado e) { if (e!=null) listaEmpleados.add(e); }
    public void registrarAlquilador(Alquilador a) { if (a!=null) listaAlquiladores.add(a); }
    public void registrarAudiencia(Audiencia a) { if (a!=null) listaAudiencias.add(a); }

    // --- RESERVAS ---
    public Reserva crearReserva(Alquilador alquilador, Cancha cancha, Date fecha, String hora) {
        if (alquilador == null) throw new IllegalArgumentException("Alquilador nulo.");
        if (cancha == null) throw new IllegalArgumentException("Cancha nula.");
        if (!estaDisponible(cancha, fecha, hora)) throw new IllegalStateException("Cancha ocupada en ese horario.");
        Reserva r = new Reserva(fecha, hora, alquilador, cancha);
        listaReservas.add(r);
        return r;
    }

    public void cancelarReserva(int idReserva) {
        Reserva r = buscarReservaPorId(idReserva);
        if (r.getEstado().equals("Cancelada")) throw new IllegalStateException("Reserva ya cancelada.");
        r.cancelar();
    }

    public Comprobante confirmarReserva(int idReserva) {
        Reserva r = buscarReservaPorId(idReserva);
        if (r.getEstado().equals("Confirmada")) throw new IllegalStateException("Reserva ya confirmada.");
        r.confirmar();
        Comprobante c = r.generarComprobante();
        return c;
    }

    // Busca reserva por id, lanza excepción si no existe
    public Reserva buscarReservaPorId(int id) {
        for (Reserva r : listaReservas) if (r.getIdReserva() == id) return r;
        throw new IllegalStateException("Reserva no encontrada (id=" + id + ").");
    }

    // Chequea disponibilidad simple (por cancha+fecha+hora)
    public boolean estaDisponible(Cancha cancha, Date fecha, String hora) {
        for (Reserva r : listaReservas) {
            if (r.getCancha() == cancha && r.getFecha().equals(fecha) && r.getHora().equals(hora) &&
                    !r.getEstado().equals("Cancelada")) {
                return false;
            }
        }
        return true;
    }

    // --- PARTIDOS ---
    public Partido crearPartido(Reserva reserva, String equipos, double precioTicketBase) {
        if (reserva == null) throw new IllegalArgumentException("Reserva nula.");
        // confirmar automáticamente la reserva si está pendiente
        if (reserva.getEstado().equals("Pendiente")) reserva.confirmar();
        Partido p = new Partido(proximoIdPartido++, reserva, equipos, precioTicketBase);
        listaPartidos.add(p);
        return p;
    }

    public List<Partido> getListaPartidos() { return new ArrayList<>(listaPartidos); }

    // --- TICKETS ---
    public Ticket venderTicket(Partido partido, Audiencia comprador) {
        if (partido == null || comprador == null) throw new IllegalArgumentException("Partido o comprador nulo.");
        double precio = partido.getPrecioTicketBase();
        Ticket t = new Ticket(partido, comprador, precio);
        listaTickets.add(t);
        return t;
    }

    // Reporte simple de ingresos (suma de todos los tickets vendidos)
    public double generarReporteIngresos() {
        double total = 0.0;
        for (Ticket t : listaTickets) total += t.getPrecioPagado();
        return total;
    }

    // Getters para UI y pruebas
    public List<Cancha> getListaCanchas() { return new ArrayList<>(listaCanchas); }
    public List<Reserva> getListaReservas() { return new ArrayList<>(listaReservas); }
    public List<Ticket> getListaTickets() { return new ArrayList<>(listaTickets); }
    public List<Audiencia> getListaAudiencias() { return new ArrayList<>(listaAudiencias); }
}
