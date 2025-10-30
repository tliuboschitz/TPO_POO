    import java.util.ArrayList;
    import java.util.Date;
    import java.util.List;
    import java.util.UUID;
    import java.util.Scanner;

    // --- Clase Main para correr el sistema ---
 public class Main {

    public static void main(String[] args) {
        
        // --- 1. SETUP DEL MODELO ---
        Sistema miSistema = new Sistema();
        
        // datos de prueba para que el sistema no esté vacío
        // Se puede cambiar a que el alquilador se ingrese por consola.
        // Mas adelante sera reemplazado por una interfaz gráfica. Swing.
        Alquilador alquilador = new Alquilador("Juan", "Perez", "123");
        miSistema.registrarAlquilador(alquilador);
        
        try {
            // Creamos una reserva (que tendrá el ID 1)
            miSistema.crearReserva(alquilador, cancha, new Date(), "20:00");
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        // --- 2. SETUP DE LA VISTA (CONSOLA) ---
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== BIENVENIDO AL SISTEMA DE RESERVAS ===");
        System.out.println("Hay 1 reserva activa (ID 1)");
        System.out.print("Ingrese el ID de la reserva a CANCELAR: ");
        
        try {
            int idParaCancelar = scanner.nextInt();

            miSistema.cancelarReserva(idParaCancelar);
            
            System.out.println("ÉXITO: La reserva " + idParaCancelar + " fue cancelada.");

        } catch (java.util.InputMismatchException e) {
            System.err.println("Error: Debe ingresar un número entero.");
        } catch (IllegalStateException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
        
        scanner.close();
    }
}
    


    class Reserva {
        private int idReserva;
        private static int proximoId = 1;
        private Date fecha;
        private String hora;
        private Alquilador alquilador;
        private Cancha cancha;
        private double monto;
        private String estado; // "Pendiente", "Confirmada", "Cancelada"

        public Reserva(Date fecha, String hora, Alquilador alquilador, Cancha cancha) {
            this.idReserva = proximoId++;
            // Incrementa el ID para la próxima reserva, se reinicia cuando se abre el programa
            // pero por lo menos los IDs son únicos durante la ejecución y son simples y legibles.
            this.fecha = fecha;
            this.hora = hora;
            this.alquilador = alquilador;
            this.cancha = cancha;
            this.monto = cancha.getPrecioHora(); 
            this.estado = "Pendiente";
        }
        
        public int getIdReserva() {
        return idReserva;
        }

        public void confirmar() { this.estado = "Confirmada"; }
        public void cancelar() { this.estado = "Cancelada"; }
        
        // Getters
        public Cancha getCancha() { return cancha; }
        public Date getFecha() { return fecha; }
        public String getHora() { return hora; }
        public String getEstado() { return estado; }

        public Comprobante generarComprobante() {
            return new Comprobante(this);
        }
    }

    class Comprobante {
        private int idComprobante;
        private Reserva reserva;
        private Date fechaEmision;
        
        public Comprobante(Reserva reserva) {
            this.reserva = reserva;
            this.fechaEmision = new Date(); // Fecha de hoy
            // (generar un ID...)
        }
    }

    class Partido {
        private int idPartido;
        private Reserva reserva; // El partido "usa" una reserva
        private String equipos;
        private double precioTicketBase;
        
        public Partido(int idPartido, Reserva reserva, String equipos, double precioTicket) {
            this.idPartido = idPartido;
            this.reserva = reserva;
            this.equipos = equipos;
            this.precioTicketBase = precioTicket;
        }
    }

    class Ticket {
        private int idTicket;
        private Partido partido;
        private Audiencia comprador;
        private double precioPagado;
        
        public Ticket(int idTicket, Partido partido, Audiencia comprador, double precioPagado) {
            this.idTicket = idTicket;
            this.partido = partido;
            this.comprador = comprador;
            this.precioPagado = precioPagado;
        }
    }

    class Mantenimiento {
        private int idMantenimiento;
        private String descripcion;
        private Cancha canchaAfectada;
        private String estado; // "Pendiente", "Terminado"
        
        public Mantenimiento(int id, String desc, Cancha cancha) {
            this.idMantenimiento = id;
            this.descripcion = desc;
            this.canchaAfectada = cancha;
            this.estado = "Pendiente";
        }
    }