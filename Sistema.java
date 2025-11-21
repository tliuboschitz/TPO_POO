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
        Cancha c1 = new Cancha("Cancha 1 (F11)", "F11", 10000.0);
        Cancha c2 = new Cancha("Cancha 2 (F9)", "F9", 8000.0);
        Cancha c3 = new Cancha("Cancha 3 (F5)", "F5", 5000.0);
        Cancha c4 = new Cancha("Cancha 4 (F5)", "F5", 5000.0);
        listaCanchas.add(c1);
        listaCanchas.add(c2);
        listaCanchas.add(c3);
        listaCanchas.add(c4);
    }

    // --- REGISTROS ---
    public void registrarCancha(Cancha c) { if (c!=null) listaCanchas.add(c); }
    public void registrarEmpleado(Empleado e) { if (e!=null) listaEmpleados.add(e); }
    public void registrarAlquilador(Alquilador a) { if (a!=null) listaAlquiladores.add(a); }
    public void registrarAudiencia(Audiencia a) { if (a!=null) listaAudiencias.add(a); }

    // --- RESERVAS ---
    public Reserva crearReserva(Alquilador alquilador, Cancha cancha, Date fecha, String hora)
            throws CanchaNoDisponibleException {

        if (alquilador == null) throw new IllegalArgumentException("Alquilador nulo.");
        if (cancha == null) throw new IllegalArgumentException("Cancha nula.");

        if (!estaDisponible(cancha, fecha, hora)) {
            throw new CanchaNoDisponibleException("La cancha no está disponible en ese horario.");
        }

        Reserva r = new Reserva(fecha, hora, alquilador, cancha);
        listaReservas.add(r);
        return r;
    }

    public void cancelarReserva(int idReserva) {
        Reserva r = buscarReservaPorId(idReserva);
        if (r.getEstado().equals("Cancelada"))
            throw new IllegalStateException("Reserva ya cancelada.");
        r.cancelar();
    }

    private boolean tieneReserva(Cancha cancha, Date fecha, String hora) {
        for (Reserva res : this.listaReservas) {
            if (res.getCancha().getIdCancha() == cancha.getIdCancha() &&
                    esMismoDia(res.getFecha(), fecha) &&
                    res.getHora().equals(hora)) {

                if (!res.getEstado().equals("Cancelada")) {
                    return true;
                }
            }
        }
        return false;
    }

    public Comprobante confirmarReserva(int idReserva) {
        Reserva r = buscarReservaPorId(idReserva);
        if (r.getEstado().equals("Confirmada"))
            throw new IllegalStateException("Reserva ya confirmada.");
        r.confirmar();
        return r.generarComprobante();
    }

    public Reserva buscarReservaPorId(int id) {
        for (Reserva r : listaReservas)
            if (r.getIdReserva() == id) return r;

        throw new IllegalStateException("Reserva no encontrada (id=" + id + ").");
    }

    public Cancha buscarCanchaPorId(int idCancha) {
        for (Cancha c : listaCanchas)
            if (c.getIdCancha() == idCancha) return c;
        throw new IllegalStateException("No se encontró cancha con ID " + idCancha);
    }

    public boolean estaDisponible(Cancha cancha, Date fecha, String hora) {

        if (this.estaEnMantenimiento(cancha, fecha)) return false;

        if (this.tieneReserva(cancha, fecha, hora)) return false;

        return true;
    }

    public Mantenimiento buscMantenimientoarPorId(int idMantenimiento) {
        for (Mantenimiento m : listaMantenimientos)
            if (m.getIdMantenimiento() == idMantenimiento) return m;

        throw new IllegalStateException("No se encontró mantenimiento con ID " + idMantenimiento);
    }

    public void asignarTareaMantenimiento(Empleado empleado, Mantenimiento tarea) {
        empleado.asignarTarea(tarea);
    }

    public Mantenimiento finalizarMantenimiento(int idMantenimiento) {
        Mantenimiento m = buscMantenimientoarPorId(idMantenimiento);
        m.finalizar();
        return m;
    }

    public Mantenimiento crearMantenimiento(String descripcion, Cancha cancha, Date fecha) {
        Mantenimiento nuevo = new Mantenimiento(descripcion, cancha, fecha);
        listaMantenimientos.add(nuevo);
        return nuevo;
    }

    // --- PARTIDOS ---
    public Partido crearPartido(Reserva reserva, String equipos, double precioTicketBase, int capacidadMaximaTickets) {
        if (reserva == null) throw new IllegalArgumentException("Reserva nula.");
        if (reserva.getEstado().equals("Pendiente")) reserva.confirmar();
        Partido p = new Partido(reserva, equipos, precioTicketBase, capacidadMaximaTickets);
        listaPartidos.add(p);
        return p;
    }

    public List<Partido> getListaPartidos() { return new ArrayList<>(listaPartidos); }

    public boolean esMismoDia(Date a, Date b) {
        if (a == null || b == null) return false;
        java.util.Calendar ca = java.util.Calendar.getInstance();
        java.util.Calendar cb = java.util.Calendar.getInstance();
        ca.setTime(a);
        cb.setTime(b);
        return ca.get(java.util.Calendar.YEAR) == cb.get(java.util.Calendar.YEAR)
                && ca.get(java.util.Calendar.DAY_OF_YEAR) == cb.get(java.util.Calendar.DAY_OF_YEAR);
    }

    private boolean estaEnMantenimiento(Cancha cancha, Date fecha) {
        for (Mantenimiento mant : listaMantenimientos) {
            if (mant.getCanchaAfectada().getIdCancha() == cancha.getIdCancha() &&
                    esMismoDia(mant.getFecha(), fecha)) {
                if (!mant.getEstado().equals("Finalizado")) return true;
            }
        }
        return false;
    }

    // --- TICKETS ---
    public Ticket venderTicket(Partido partido, Audiencia comprador)
            throws MenorSinTutorException, TicketDuplicadoException {

        if (partido == null || comprador == null)
            throw new IllegalArgumentException("Partido o comprador nulo.");

        // --- VALIDACIÓN 1: menor sin tutor ---
        if (comprador.getEdad() < 18 && (comprador.getTutorNombre() == null || comprador.getTutorNombre().isEmpty())) {
            throw new MenorSinTutorException("El comprador es menor y no tiene tutor registrado.");
        }

        // --- VALIDACIÓN 2: ticket duplicado ---
        for (Ticket t : listaTickets) {
            if (t.getPartido().getIdPartido() == partido.getIdPartido() &&
                    t.getComprador().getDni().equals(comprador.getDni())) {
                throw new TicketDuplicadoException("El comprador ya tiene un ticket para este partido.");
            }
        }

        if (partido.estaLleno()) {
            throw new IllegalStateException("El partido ya vendió todas sus entradas.");
        }

        double precioFinal = partido.calcularPrecioFinal(comprador);
        Ticket t = new Ticket(partido, comprador, precioFinal);
        listaTickets.add(t);
        return t;
    }

    public double generarReporteIngresos() {
        double total = 0.0;
        for (Ticket t : listaTickets) total += t.getPrecioPagado();
        return total;
    }

    public List<Cancha> getListaCanchas() { return new ArrayList<>(listaCanchas); }
    public List<Reserva> getListaReservas() { return new ArrayList<>(listaReservas); }
    public List<Ticket> getListaTickets() { return new ArrayList<>(listaTickets); }
    public List<Audiencia> getListaAudiencias() { return new ArrayList<>(listaAudiencias); }
}
