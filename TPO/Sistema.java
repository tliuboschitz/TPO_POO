package TPO;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Date;



// --- CLASES DE PERSONAS REFACTORIZADAS ---
public class Sistema {
    // --- Listas Maestras ---
    private List<Cancha> listaCanchas;
    private List<Reserva> listaReservas;
    private List<Empleado> listaEmpleados;
    private List<Alquilador> listaAlquiladores; 
    private List<Audiencia> listaAudiencia;   
    private List<Partido> listaPartidos;
    private List<Ticket> listaTickets;
    private List<Mantenimiento> listaMantenimientos; 

    // --- Constructor ---
    public Sistema() {
        this.listaCanchas = new ArrayList<>();
        this.listaReservas = new ArrayList<>();
        this.listaEmpleados = new ArrayList<>();
        this.listaAlquiladores = new ArrayList<>();
        this.listaAudiencia = new ArrayList<>();
        this.listaPartidos = new ArrayList<>();
        this.listaTickets = new ArrayList<>();
        this.listaMantenimientos = new ArrayList<>();
    
        Cancha c1 = new Cancha("Cancha 1 (F11)", "F11", 10000.0); // ID será 1
        Cancha c2 = new Cancha("Cancha 2 (F9)", "F9", 8000.0);   // ID será 2
        Cancha c3 = new Cancha("Cancha 3 (F5)", "F5", 5000.0);   // ID será 3
        Cancha c4 = new Cancha("Cancha 4 (F5)", "F5", 5000.0);   // ID será 4

        // Registro en la lista maestra
        this.listaCanchas.add(c1);
        this.listaCanchas.add(c2);
        this.listaCanchas.add(c3);
        this.listaCanchas.add(c4);
    }
    public void registrarCancha(Cancha cancha) {
        this.listaCanchas.add(cancha);
    }
    
    public void registrarEmpleado(Empleado empleado) {
        this.listaEmpleados.add(empleado);
    }
    
    public void registrarAlquilador(Alquilador alquilador) {
        this.listaAlquiladores.add(alquilador);
    }
    
    public void registrarAudiencia(Audiencia audiencia) {
        this.listaAudiencia.add(audiencia);
    }

    // Recorre la lista de reservas y devuelve la que coincide con el idReserva, si no coincide lanza excepción.
    private Reserva buscarReservaPorId(int idReserva) {
        for (Reserva res : listaReservas) {
            if (res.getIdReserva() == idReserva) {
                return res;
            }
        }
        throw new IllegalStateException("Error al buscar la reserva: Reserva no encontrada.");                      
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

    public List<Cancha> buscarDisponibilidad(Date fecha, String hora, String tipoCancha) {
        // Lógica para buscar canchas...
        return new ArrayList<>(); // Devuelve lista vacía por ahora
    }
    
    public Reserva crearReserva(Alquilador alquilador, Cancha cancha, Date fecha, String hora) {

    if (!this.estaDisponible(cancha, fecha, hora)) {  
        throw new IllegalStateException("Error al crear la reserva: La cancha " + 
        cancha.getNombre() + " ya está ocupada en ese horario.");
    }

    Reserva nuevaReserva = new Reserva(fecha, hora, alquilador, cancha);
        this.listaReservas.add(nuevaReserva);
        return nuevaReserva;
    }
    
    public void cancelarReserva(int idReserva) {
        // Lógica para buscar la reserva y setear estado "Cancelada"
    }
    
    public Comprobante confirmarReserva(int idReserva) {
        // Lógica para buscar reserva, setear estado "Confirmada"
        // y generar un Comprobante.
        return null; // Placeholder
    }
    
    public Partido crearPartido(int idReserva, String equipos, double precioTicketBase, int capacidadMaxima) {

        // Busca la Reserva
        Reserva reservaAsociada = this.buscarReservaPorId(idReserva);

        // buscarReservaPorId lanza una excepción si no la encuentra
        if (!reservaAsociada.getEstado().equals("Confirmada")) {
            throw new IllegalStateException("Error: Solo se pueden crear partidos sobre reservas 'Confirmadas'.");
        }

        // Instancia de Partido
        Partido nuevoPartido = new Partido(reservaAsociada, equipos, precioTicketBase, capacidadMaxima);

        // registro en lista maestra del Sistema
        this.listaPartidos.add(nuevoPartido);

        // Actualizar el estado de la reserva
        reservaAsociada.setEstado("Confirmada");

        System.out.println("Partido creado con éxito (ID: " + nuevoPartido.getIdPartido() + ")");
        return nuevoPartido;
    }
        
    public Ticket venderTicket(Partido partido, Audiencia audiencia) {
        if (partido.estaLleno()) {
            throw new IllegalStateException("Error al vender el ticket: El partido ya ha vendido todas las entradas disponibles.");
        }
        double precioFinal = partido.calcularPrecioFinal(partido, audiencia);
        Ticket nuevoTicket = new Ticket(partido, audiencia, precioFinal);
        partido.agregarTicket(nuevoTicket);
        this.listaTickets.add(nuevoTicket);
        return nuevoTicket;
    }

    // Método de soporte (Regla de Negocio)
    private boolean estaDisponible(Cancha cancha, Date fecha, String hora) {
        // Lógica de chequeo que vimos...
        return true; // Placeholder
    }
}