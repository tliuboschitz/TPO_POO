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
        Cancha c1 = new Cancha("Cancha 1 (F11)", "F11", 10000.0); // ID será 1
        Cancha c2 = new Cancha("Cancha 2 (F9)", "F9", 8000.0);   // ID será 2
        Cancha c3 = new Cancha("Cancha 3 (F5)", "F5", 5000.0);   // ID será 3
        Cancha c4 = new Cancha("Cancha 4 (F5)", "F5", 5000.0);   // ID será 4
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
    public Reserva buscarReservaPorId(int id) {
        for (Reserva r : listaReservas) if (r.getIdReserva() == id) return r;
        throw new IllegalStateException("Reserva no encontrada (id=" + id + ").");
    }
    
    // Recorre la lista de canchas y devuelve la que coincide con el idCancha
    public Cancha buscarCanchaPorId(int idCancha) {
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
    public Partido crearPartido(Reserva reserva, String equipos, double precioTicketBase, int capacidadMaximaTickets) {
        if (reserva == null) throw new IllegalArgumentException("Reserva nula.");
        // confirmar automáticamente la reserva si está pendiente
        if (reserva.getEstado().equals("Pendiente")) reserva.confirmar();
        Partido p = new Partido(reserva, equipos, precioTicketBase, capacidadMaximaTickets);
        listaPartidos.add(p);
        return p;
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
    public Ticket venderTicket(Partido partido, Audiencia comprador) {
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
