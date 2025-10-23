    import java.util.ArrayList;
    import java.util.Date;
    import java.util.List;
    import java.util.UUID;
    // (Importa las clases que vayas necesitando)

    import TPO_POO.Alquilador.Cancha;
    import TPO_POO.Alquilador.Comprobante;
    import TPO_POO.Alquilador.Empleado;
    import TPO_POO.Alquilador.Mantenimiento;
    import TPO_POO.Alquilador.Partido;
    import TPO_POO.Alquilador.Reserva;
    import TPO_POO.Alquilador.Ticket;

    // --- CLASES DE PERSONAS REFACTORIZADAS ---
    public class Sistema {
        // --- Listas Maestras ---
        private List<Cancha> listaCanchas;
        private List<Reserva> listaReservas;
        private List<Empleado> listaEmpleados;
        private List<Alquilador> listaAlquiladores; // <-- CAMBIO
        private List<Audiencia> listaAudiencia;    // <-- NUEVO
        private List<Partido> listaPartidos;
        private List<Ticket> listaTickets;
        private List<Mantenimiento> listaMantenimientos; // <-- NUEVO

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
        }

        // --- Métodos de Registro (ABM) ---
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

        // --- Métodos de Funcionalidad Principal (El Esqueleto) ---
        
        public List<Cancha> buscarDisponibilidad(Date fecha, String hora, String tipoCancha) {
            // Lógica para buscar canchas...
            return new ArrayList<>(); // Devuelve lista vacía por ahora
        }

        public Reserva crearReserva(Alquilador alquilador, Cancha cancha, Date fecha, String hora) {

        if (!this.estaDisponible(cancha, fecha, hora)) {
            
            // LANZAS UNA EXCEPCIÓN "UNCHECKED"
            throw new IllegalStateException("Error al crear la reserva: La cancha " + 
                                    cancha.getNombre() + " ya está ocupada en ese horario.");
        }

        
        int nuevoId = this.listaReservas.size() + 1; 
        Reserva nuevaReserva = new Reserva(nuevoId, fecha, hora, alquilador, cancha);
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
        private int idCancha;
        private String nombre;
        private String tipo;
        private double precioHora;
        private String estado; // "Disponible", "Mantenimiento"

        public Cancha(int idCancha, String nombre, String tipo, double precioHora) {
            this.idCancha = idCancha;
            this.nombre = nombre;
            this.tipo = tipo;
            this.precioHora = precioHora;
            this.estado = "Disponible";
        }
        
        public double getPrecioHora() { return precioHora; }
        public String getEstado() { return estado; }
        public void setEstado(String estado) { this.estado = estado; }
        
        // (Faltan los otros getters)
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
            proximoId++;    // Incrementa el ID para la próxima reserva, se reinicia cuando se abre el programa
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