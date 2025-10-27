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
        
        public Partido crearPartido(Reserva reserva, String equipos, double precioTicket) {
            // Lógica para crear el partido...
            return null; // Placeholder
        }
        
        public Ticket venderTicket(Partido partido, Audiencia audiencia) {
            // Lógica para calcular precio (descuentos, etc) y crear Ticket
            return null; // Placeholder
        }

        // Método de soporte (Regla de Negocio)
        private boolean estaDisponible(Cancha cancha, Date fecha, String hora) {
            // Lógica de chequeo que vimos...
            return true; // Placeholder
        }

        // ... otros métodos ...
    }

    abstract class Persona {
        protected String nombre;
        protected String apellido;
        protected String dni;

        public Persona(String nombre, String apellido, String dni) {
            this.nombre = nombre;
            this.apellido = apellido;
            this.dni = dni;
        }
        // Getters
        public String getNombre() { return nombre; }
        public String getApellido() { return apellido; }
        public String getDni() { return dni; }

        // Método toString() es el estándar en Java para esto
        @Override
        public String toString() {
            return apellido + ", " + nombre + " (DNI: " + dni + ")";
        }
    }

    class Audiencia extends Persona {
        private String email;
        private int edad;      
        private boolean esSocio; 
        private String tutorNombre; 
        public Audiencia(String nombre, String apellido, String dni, String email, int edad, boolean esSocio, String tutor) {
            super(nombre, apellido, dni);
            this.email = email;
            this.edad = edad;
            this.esSocio = esSocio;
            this.tutorNombre = (edad < 18) ? tutor : null; // Asigna tutor solo si es menor
        }

        // Getters
        public String getEmail() { return email; }
        public int getEdad() { return edad; }
        public boolean isEsSocio() { return esSocio; } // "is" es la convención para boolean
        
        // NOTA: Eliminé getDireccion() y getTelefono() porque no tenías esos atributos.

        public void comprarTicket(Partido partido) {
            // Lógica para comprar un ticket...
        }
    }

    class Alquilador extends Persona {
        // private int canchasAlquiladas; // <-- CAMBIO: Esto es mala idea.
        // Es mejor que Sistema calcule esto buscando en las reservas,
        // que tener un contador acá que puede desincronizarse.
        // Esta clase es simple, solo guarda datos del alquilador.

        public Alquilador(String nombre, String apellido, String dni) {
            super(nombre, apellido, dni);
        }
    }
        

    class Empleado extends Persona {
        private String rol;
        private UUID iUuid;
        private List<Mantenimiento> tareasAsignadas;

        public Empleado(String nombre, String apellido, String dni, String rol) {
            super(nombre, apellido, dni);
            this.rol = rol;
            this.iUuid = UUID.randomUUID();
            // ¡Inicializar la lista!
            this.tareasAsignadas = new ArrayList<>(); 
        }
        
        // Getters
        public UUID getiUuid() { return iUuid; }
        public String getRol() { return rol; }

        public void asignarTarea(Mantenimiento tarea) {
            if(tarea != null) {
                this.tareasAsignadas.add(tarea);
            }
        }
        
        
        public void gestionarPartido(Partido partido) {
            // Lógica para gestionar un partido...
        }
    }


    class Encargado extends Empleado {
        public Encargado(String nombre, String apellido, String dni) {
            super(nombre, apellido, dni, "Encargado"); // Rol fijo
        }

        public void asignarTareaAEmpleado(Empleado emp, Mantenimiento tarea) {
            emp.asignarTarea(tarea);
        }
    }

    // --- CLASES DEL DOMINIO ---

    
class Cancha {
    // CONTADOR ESTÁTICO
    private static int proximoId = 1;

    private int idCancha;
    private String nombre;
    private String tipo; // "F11", "F9", "F5"
    private double precioHora;
    private String estado; 

    
    public Cancha(String nombre, String tipo, double precioHora) {
        
        
        this.idCancha = proximoId++; 
        
        this.nombre = nombre;
        this.tipo = tipo;
        this.precioHora = precioHora;
        this.estado = "Disponible";
    }
        // Getters y Setters
    public int getIdCancha() { 
        return idCancha;
    }
    
    public String getNombre() { 
        return nombre;
    }

    public String getTipo() { 
        return tipo;
    }

    public double getPrecioHora() { return precioHora; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
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