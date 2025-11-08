package TPO;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import static TPO.Main.Conection;


// --- CLASES DE PERSONAS REFACTORIZADAS ---
    public class Sistema {
        // --- Listas Maestras ---
        private List<Cancha> listaCanchas;
        private List<Reserva> listaReservas;
        private List<Empleado> listaEmpleados;
        private List<Alquilador> listaAlquiladores; 
        private List<Audiencia> listaAudiencia;
        private List<Ticket> listaTickets;
        private List<Persona> listaPersonas;
        private List<Partido> listaPartidos;

        // --- Constructor ---
        public Sistema() {
            this.listaPartidos = new ArrayList<>();
            this.listaPersonas = new ArrayList<>();
            this.listaCanchas = new ArrayList<>();
            this.listaReservas = new ArrayList<>();
            this.listaEmpleados = new ArrayList<>();
            this.listaAlquiladores = new ArrayList<>();
            this.listaAudiencia = new ArrayList<>();
            List<Partido> listaPartidos = new ArrayList<>();
            this.listaTickets = new ArrayList<>();
            List<Mantenimiento> listaMantenimientos = new ArrayList<>();

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

        public Empleado buscarEmpleadoPorId(int idEmpleado) {
            for (Empleado empleado : listaEmpleados) {
                if(empleado.getDni() == idEmpleado) {
                    return empleado;
                }
            }
            return null;
        }

        public Audiencia buscarAudienciaPorId(int idAudiencia) {
            for (Audiencia audiencia : listaAudiencia) {
                if(audiencia.getDni() == idAudiencia) {
                    return audiencia;
                }

            }
            return  null;
        }

        public Partido buscarPartidoPorId(int idPartido) {
            for (Partido partido : listaPartidos) {
                if (partido.getIdPartido() == idPartido) {
                    return partido;
                }
            }
            return  null;
        }


        public List<Cancha> buscarDisponibilidad(Date fecha, int hora, String tipoCancha) {
            // Lógica para buscar canchas...
            return new ArrayList<>(); // Devuelve lista vacía por ahora
        }
        
        public Reserva crearReserva(Alquilador alquilador, Cancha cancha, Date fecha, int hora) {

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
        private boolean estaDisponible(Cancha cancha, Date fecha, int hora) {
            // Lógica de chequeo que vimos...
            return true; // Placeholder
        }

        public Persona buscarPersonabyID(int idPersona) {
            for (Persona c : listaPersonas) {
                if (c.getDni() == idPersona) {
                    return c;
                }
            }
            return null;
        }

        public Alquilador buscarAlquiladorbyId(int idPersona) {
            for (Alquilador c : listaAlquiladores) {
                if (c.getDni() == idPersona) {
                    return c;
                }
            }
            return null;
        }




        //Solo mostrara listas de canchas solo disponibles para interfaz del usuario
        public void TestProcessing() throws SQLException, ParseException {
            Statement stmt = Conection.createStatement();
            ResultSet resultSet = stmt.executeQuery("Select nombreC, tipo, precioHora from CANCHA where estado like 'Disponible'");
            //Canchas
            while (resultSet.next()) {
                String c = resultSet.getString("nombreC");
                String tipo = resultSet.getString("tipo");
                double precioHora = resultSet.getDouble("precioHora");
                Cancha cancha = new Cancha(c, tipo, precioHora);
                listaCanchas.add(cancha);
            }
            ResultSet resultSet1 = stmt.executeQuery("SELECT nombre, apellido, dni FROM PERSONA");
            while (resultSet1.next()) {
                String nombre = resultSet1.getString("nombre");
                String apellido = resultSet1.getString("apellido");
                int dni =  resultSet1.getInt("dni");
                Persona persona = new Persona(nombre, apellido, dni);
                listaPersonas.add(persona);
            }
            ResultSet resultSet2 = stmt.executeQuery("SELECT nombre, apellido, dniE, rol from EMPLEADO inner join PERSONA P on P.dni = EMPLEADO.dniE");
            while (resultSet2.next()) {
                String nombre = resultSet2.getString("nombre");
                String apellido = resultSet2.getString("apellido");
                int dniE =  resultSet2.getInt("dniE");
                String rol = resultSet2.getString("rol");
                Empleado empleado = new Empleado(nombre, apellido, dniE, rol);
                listaEmpleados.add(empleado);
            }
            ResultSet resultSet3 = stmt.executeQuery("SELECT nombre, apellido, dniAl from ALQUILADOR inner join PERSONA P on P.dni = ALQUILADOR.dniAl");
            while (resultSet3.next()) {
                String nombre = resultSet3.getString("nombre");
                String apellido = resultSet3.getString("apellido");
                int dniAl =  resultSet3.getInt("dniAl");
                Alquilador alquilador = new Alquilador(nombre, apellido, dniAl);
                listaAlquiladores.add(alquilador);
            }
            ResultSet resultSet4 = stmt.executeQuery("SELECT nombre, apellido, dniA, email, edad, esSocio, dniTutor from AUDIENCIA inner join PERSONA P on P.dni = AUDIENCIA.dniA");
            while (resultSet4.next()) {
                String nombre = resultSet4.getString("nombre");
                String apellido = resultSet4.getString("apellido");
                int dniA =  resultSet4.getInt("dniA");
                String email = resultSet4.getString("email");
                int edad = resultSet4.getInt("edad");
                boolean esSocio = resultSet4.getBoolean("esSocio");
                int dniTutor =  resultSet4.getInt("dniTutor");
                Audiencia audiencia = new Audiencia(nombre, apellido,  dniA, email, edad, esSocio, dniTutor);
                listaAudiencia.add(audiencia);
            }

            ResultSet resultSet5 = stmt.executeQuery("select idReserva, date, hora, Alquilador, Cancha, monto, estado, idComprobante from RESERVA");
            while(resultSet5.next()) {
                int idReserva = resultSet5.getInt("idReserva");
                String dateInt = resultSet5.getString("date");
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                Date date = format.parse(dateInt);
                int hora = resultSet5.getInt("hora");
                int AlquiladorId = resultSet5.getInt("Alquilador");
                Alquilador alquilador = buscarAlquiladorbyId(AlquiladorId);
                int CanchaA = resultSet5.getInt("Cancha");
                Cancha  cancha = buscarCanchaPorId(CanchaA);
                int monto = resultSet5.getInt("monto");
                int estado = resultSet5.getInt("estado");
                int idComprobante = resultSet5.getInt("idComprobante");
                Reserva reserva = new Reserva(date, hora, alquilador, cancha);
                listaReservas.add(reserva);
            }

            ResultSet resultSet6 = stmt.executeQuery("SELECT idPartido, Reserve, equipos, precioTicketBase from PARTIDO");
            while (resultSet6.next()) {
                int idPartido = resultSet6.getInt("idPartido");
                int ReserveId = resultSet6.getInt("Reserve");
                Reserva reserva = buscarReservaPorId(ReserveId);
                String equipos = resultSet6.getString("equipos");
                double precioTicketBase = resultSet6.getDouble("precioTicketBase");
                Partido partido = new Partido(idPartido, reserva, equipos, precioTicketBase);
                listaPartidos.add(partido);

            }
            ResultSet resultSet7 = stmt.executeQuery("select idTicket, Partido, dniComprador, precioPagado from TICKET");
            while (resultSet7.next()) {
                int idTicket = resultSet7.getInt("idTicket");
                int PartidoId = resultSet7.getInt("Partido");
                Partido partido = buscarPartidoPorId(PartidoId);
                int dniComprador = resultSet7.getInt("dniComprador");
                Audiencia comprador= buscarAudienciaPorId(dniComprador);
                int precioPagado = resultSet7.getInt("precioPagado");
                Ticket ticket = new Ticket(idTicket, partido, comprador, precioPagado );
                listaTickets.add(ticket);

            }

            ResultSet resultSet8 = stmt.executeQuery("select idMantenimiento, dni, descripcion, CanchaId, estado from MANTENIMIENTO");

                while(resultSet8.next()) {
                    int idMantenimiento = resultSet8.getInt("idMantenimiento");
                    int dni = resultSet8.getInt("dni");
                    Empleado empleado = buscarEmpleadoPorId(dni);
                    int canchaid = resultSet8.getInt("CanchaId");
                    Cancha cancha = buscarCanchaPorId(canchaid);
                    String descripcionMantenimiento = resultSet8.getString("descripcionMantenimiento");
                    String estadoMantenimiento = resultSet8.getString("estadoMantenimiento");
                    Mantenimiento mantenimiento = new Mantenimiento(idMantenimiento, empleado, cancha, descripcionMantenimiento);
                    listaMantenimiento.add()

                }



            stmt.close();
        }



        public List<Cancha> getListaCanchas() {
            return new ArrayList<>(listaCanchas);
        }

        public List<Reserva> getListaReservas() { return new ArrayList<>(listaReservas); }
        public List<Ticket> getListaTickets() { return new ArrayList<>(listaTickets); }
        public List<Persona> getListaPersonas() { return new ArrayList<>(listaPersonas); }
        public List<Empleado> getListaEmpleados() {return new ArrayList<>(listaEmpleados);}

    public List<Alquilador> getListaAlquiladores() {return listaAlquiladores;}
    public List<Partido> getListaPartidos() {return listaPartidos;}

    public List<Audiencia> getListaAudiencias() {return new ArrayList<>(listaAudiencia);  }
}