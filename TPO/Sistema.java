package TPO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private int proximoIdPartido = 1; // id secuencial para partidos

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

    public static Empleado buscarEmpleadoPorId(int idEmpleado) {
        for (Empleado empleado : listaEmpleados) {
            if(empleado.getDni() == idEmpleado) {
                return empleado;
            }
        }
        return null;
    }


    // --- REGISTROS ---
    public void registrarCancha(Cancha c) { if (c!=null) listaCanchas.add(c); }
    public void registrarEmpleado(Empleado e) { if (e!=null) listaEmpleados.add(e); }
    public void registrarAlquilador(Alquilador a) { if (a!=null) listaAlquiladores.add(a); }
    public void registrarAudiencia(Audiencia a) { if (a!=null) listaAudiencias.add(a); }

    // --- RESERVAS ---
    public Reserva crearReserva(Alquilador alquilador, Cancha cancha, Date fecha, String hora) throws SQLException {
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
    private boolean tieneReserva(Cancha cancha, Date fecha, String hora) {
        for (Reserva res : this.listaReservas) {
            // Coincide Cancha, Fecha y Hora
            if (res.getCancha().getIdCancha() == cancha.getIdCancha() &&
                esMismoDia(res.getFecha(), fecha) &&
                res.getHora().equals(hora)) {
                
                // Si la reserva NO está cancelada, ocupa lugar
                if (!res.getEstado().equals("Cancelada")) {
                    return true; // SÍ, tiene reserva
                }
            }
        }
        return false; // No tiene reserva
    }  
    public Comprobante confirmarReserva(int idReserva) {
        Reserva r = buscarReservaPorId(idReserva);
        if (r.getEstado().equals("Confirmada")) throw new IllegalStateException("Reserva ya confirmada.");
        r.confirmar();
        Comprobante c = r.generarComprobante();
        return c;
    }
    
    // Busca reserva por id, lanza excepción si no existe
    public static Reserva buscarReservaPorId(int id) {
        for (Reserva r : listaReservas) if (r.getIdReserva() == id) return r;
        throw new IllegalStateException("Reserva no encontrada (id=" + id + ").");
    }
    
    // Recorre la lista de canchas y devuelve la que coincide con el idCancha
    public static Cancha buscarCanchaPorId(int idCancha) {
        for (Cancha c : listaCanchas) {
            if (c.getIdCancha() == idCancha) {
                return c;
            }
        }
        throw new IllegalStateException("Error: No se encontró ninguna cancha con el ID " + idCancha);
    }

    public boolean estaDisponible(Cancha cancha, Date fecha, String hora) {
    
        // 1. Chequeamos Mantenimiento
        if (this.estaEnMantenimiento(cancha, fecha)) {
            System.out.println("Disponibilidad: NO. La cancha está en mantenimiento.");
            return false;
        }

        // 2. Chequeamos Reservas
        if (this.tieneReserva(cancha, fecha, hora)) {
            System.out.println("Disponibilidad: NO. La cancha ya está reservada.");
            return false;
        }

        // 3. Si pasó ambos filtros
        System.out.println("Disponibilidad: SÍ. Puede reservar.");
        return true;
    }

    public static Audiencia buscarAudienciaPorId(int idAudiencia) {
        for (Audiencia audiencia : listaAudiencias) {
            if(audiencia.getDni() == idAudiencia) {
                return audiencia;
            }

        }
        return  null;
    }

    public static Alquilador buscarAlquiladorbyId(int idPersona) {
        for (Alquilador c : listaAlquiladores) {
            if (c.getDni() == idPersona) {
                return c;
            }
        }
        return null;
    }


    public Mantenimiento buscMantenimientoarPorId(int idMantenimiento) {
        for (Mantenimiento m : listaMantenimientos) {
            if (m.getIdMantenimiento() == idMantenimiento) {
                return m;
            }
        }
        throw new IllegalStateException("Error: No se encontró ningún mantenimiento con el ID " + idMantenimiento);
    }
    public void asignarTareaMantenimiento(Empleado empleado, Mantenimiento tarea) {
        empleado.asignarTarea(tarea);
    }
    public Mantenimiento finalizarMantenimiento(int idMantenimiento) {
        Mantenimiento mantenimiento = this.buscMantenimientoarPorId(idMantenimiento);
        mantenimiento.finalizar();
        return mantenimiento;
    }
    public Mantenimiento crearMantenimiento(String descripcion, Cancha cancha, Date fecha) {
        Mantenimiento nuevoMantenimiento = new Mantenimiento(descripcion, cancha, fecha);
        this.listaMantenimientos.add(nuevoMantenimiento);
        return nuevoMantenimiento;
    }
    
    
    // --- PARTIDOS ---
    public Partido crearPartido(Reserva reserva, String equipos, double precioTicketBase, int capacidadMaximaTickets) throws SQLException {
        if (reserva == null) throw new IllegalArgumentException("Reserva nula.");
        // confirmar automáticamente la reserva si está pendiente
        if (reserva.getEstado().equals("Pendiente")) reserva.confirmar();
        Partido p = new Partido(reserva, equipos, precioTicketBase, capacidadMaximaTickets);
        listaPartidos.add(p);
        SQLPartidos.addTablaPartido(p.getIdPartido(), reserva.getIdReserva(), equipos, precioTicketBase);
        return p;
    }

    public static Partido buscarPartidoPorId(int idPartido) {
        for (Partido partido : listaPartidos) {
            if (partido.getIdPartido() == idPartido) {
                return partido;
            }
        }
        return  null;
    }


    public List<Partido> getListaPartidos() { return new ArrayList<>(listaPartidos); }

    public boolean esMismoDia(Date a, Date b) {
        if (a == null || b == null) return false;
        java.util.Calendar ca = java.util.Calendar.getInstance();
        ca.setTime(a);
        java.util.Calendar cb = java.util.Calendar.getInstance();
        cb.setTime(b);
        return ca.get(java.util.Calendar.YEAR) == cb.get(java.util.Calendar.YEAR)
            && ca.get(java.util.Calendar.DAY_OF_YEAR) == cb.get(java.util.Calendar.DAY_OF_YEAR);
    }

    private boolean estaEnMantenimiento(Cancha cancha, Date fecha) {
        for (Mantenimiento mant : this.listaMantenimientos) {
            // Coincide Cancha Y Coincide Fecha
            if (mant.getCanchaAfectada().getIdCancha() == cancha.getIdCancha() &&
                esMismoDia(mant.getFecha(), fecha)) {
                
                // Si NO está finalizado, entonces está en mantenimiento
                if (!mant.getEstado().equals("Finalizado")) {
                    return true; // SÍ, está en mantenimiento
                }
            }
        }
        return false; // No encontramos nada, no está en mantenimiento
    }

    // --- TICKETS ---
    public Ticket venderTicket(Partido partido, Audiencia comprador) throws SQLException {
        if (partido == null || comprador == null) throw new IllegalArgumentException("Partido o comprador nulo.");
        if (partido.estaLleno()) {
            throw new IllegalStateException("Error al vender el ticket: El partido ya ha vendido todas las entradas disponibles.");
        }
        double precioFinal = partido.calcularPrecioFinal(comprador);
        Ticket t = new Ticket(partido, comprador, precioFinal);
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
