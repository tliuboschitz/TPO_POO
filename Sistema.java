import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

/**
 * Sistema: controlador central
 * Contiene listas maestras y la logica de negocio principal.
 */
public class Sistema {

    static Connection Conection;
    static {
        try {
            Class.forName("org.sqlite.JDBC");
            Conection = DriverManager.getConnection("jdbc:sqlite:TPO.db");//Problemas con No suitable driver found for jdbc:sqlite:TPO.db
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    };

    private static List<Cancha> listaCanchas;
    private static List<Reserva> listaReservas;
    private static List<Empleado> listaEmpleados;
    private static List<Alquilador> listaAlquiladores;
    private static List<Audiencia> listaAudiencias;
    private static List<Partido> listaPartidos;
    private List<Ticket> listaTickets;
    private List<Mantenimiento> listaMantenimientos;

    private int proximoIdPartido = 1; 

    public Sistema() throws SQLException, ParseException {
        this.listaCanchas = new ArrayList<>();
        this.listaReservas = new ArrayList<>();
        this.listaEmpleados = new ArrayList<>();
        this.listaAlquiladores = new ArrayList<>();
        this.listaAudiencias = new ArrayList<>();
        this.listaPartidos = new ArrayList<>();
        this.listaTickets = new ArrayList<>();
        this.listaMantenimientos = new ArrayList<>();

        SQLAlquilador.SQLProcessing(listaAlquiladores);
        SQLEmpleado.SQLProcessing(listaEmpleados);
        SQLAudiencia.SQLProcessing(listaAudiencias);
        SQLCancha.SQLProcessing(listaCanchas);
        SQLReserva.SQLProcessing(listaReservas);
        SQLPartidos.SQLProcessing(listaPartidos);
        SQLTicket.SQLProcessing(listaTickets);
        SQLMantenimiento.SQLProcessing(listaMantenimientos);

    }

    public static Audiencia buscarAudienciaPorId(int idAudiencia) {
        for (Audiencia audiencia : listaAudiencias) {
            if(audiencia.getDni() == idAudiencia) {
                return audiencia;
            }
        }
        return  null;
    }

    // --- REGISTROS ---
    public void registrarCancha(Cancha c) { if (c!=null) listaCanchas.add(c); }
    public void registrarEmpleado(Empleado e) { if (e!=null) listaEmpleados.add(e); }
    public void registrarAlquilador(Alquilador a) { if (a!=null) listaAlquiladores.add(a); }

    // --- MODIFICACIÓN SOLICITADA POR TU COMPAÑERO ---
    public void registrarAudiencia(Audiencia a) { 
        if (a == null) return;
        
        // Validación de edad (0 a 100 años)
        if (a.getEdad() < 0 || a.getEdad() > 100) {
            throw new IllegalArgumentException("Edad inválida: debe estar entre 0 y 100 años.");
        }
        
        listaAudiencias.add(a); 
    }
    // -----------------------------------------------

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

    public static Empleado buscarEmpleadoPorId(int idEmpleado) {
        for (Empleado empleado : listaEmpleados) {
            if(empleado.getDni() == idEmpleado) {
                return empleado;
            }
        }
        return null;
    }

    public Comprobante confirmarReserva(int idReserva) {
        Reserva r = buscarReservaPorId(idReserva);
        if (r.getEstado().equals("Confirmada"))
            throw new IllegalStateException("Reserva ya confirmada.");
        r.confirmar();
        return r.generarComprobante();
    }

    public static Alquilador buscarAlquiladorbyId(int idPersona) {
        for (Alquilador c : listaAlquiladores) {
            if (c.getDni() == idPersona) {
                return c;
            }
        }
        return null;
    }

    public static Reserva buscarReservaPorId(int id) {
        for (Reserva r : listaReservas)
            if (r.getIdReserva() == id) return r;

        throw new IllegalStateException("Reserva no encontrada (id=" + id + ").");
    }

    public static Cancha buscarCanchaPorId(int idCancha) {
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

    public static Partido buscarPartidoPorId(int idPartido) {
        for (Partido p : listaPartidos)
            if (p.getIdPartido() == idPartido) return p;
        throw new IllegalStateException("Partido no encontrado (id=" + idPartido + ").");
    }

    public boolean eliminarPartido(int idPartido) {
        // 1) Eliminar tickets asociados
        Iterator<Ticket> it = listaTickets.iterator();
        while (it.hasNext()) {
            Ticket t = it.next();
            if (t.getPartido().getIdPartido() == idPartido) {
                it.remove();
            }
        }
        // 2) Eliminar partido
        Iterator<Partido> itp = listaPartidos.iterator();
        while (itp.hasNext()) {
            Partido p = itp.next();
            if (p.getIdPartido() == idPartido) {
                itp.remove();
                return true;
            }
        }
        return false;
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
        if (comprador.getEdad() < 18 && (comprador.getTutorNombre() == -1 )) {
            throw new MenorSinTutorException("El comprador es menor y no tiene tutor registrado.");
        }

        // --- VALIDACIÓN 2: ticket duplicado (mismo partido + mismo DNI) ---
        for (Ticket t : listaTickets) {
            if (t.getPartido().getIdPartido() == partido.getIdPartido() &&
                    Objects.equals(t.getComprador().getDni(), comprador.getDni())) {
                throw new TicketDuplicadoException("El comprador ya tiene un ticket para este partido.");
            }
        }

        if (partido.estaLleno()) {
            throw new IllegalStateException("El partido ya vendió todas sus entradas.");
        }



        double precioFinal = partido.calcularPrecioFinal(comprador);
        Ticket t = new Ticket(partido, comprador, precioFinal);

        // Guardar en listas globales y en el propio Partido
        listaTickets.add(t);
        partido.agregarTicket(t);

        return t;
    }

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
    public List<Empleado> getListaEmpleados() { return new ArrayList<>(listaEmpleados); }

    // ============================================================
    //  MÉTODOS DE ELIMINACIÓN (Corregidos anteriormente)
    // ============================================================

    public boolean eliminarAudienciaPorDni(String dni) {
        Iterator<Audiencia> it = listaAudiencias.iterator();
        while (it.hasNext()) {
            Audiencia a = it.next();
            if (Objects.equals(a.getDni(), dni)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public boolean eliminarCancha(int idCancha) {
        Iterator<Cancha> it = listaCanchas.iterator();
        while (it.hasNext()) {
            Cancha c = it.next();
            if (c.getIdCancha() == idCancha) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public boolean eliminarEmpleado(int idEmpleado) {
        Iterator<Empleado> it = listaEmpleados.iterator();
        while (it.hasNext()) {
            Empleado e = it.next();
            if (e.getIdEmpleado() == idEmpleado) { 
                it.remove();
                return true;
            }
        }
        return false;
    }
}