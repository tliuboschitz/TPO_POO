package TPO;
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;

public class Partido {
    private int idPartido;
    private Reserva reserva; // El partido "usa" una reserva
    private String equipos;
    private double precioTicketBase;
    private static int proximoId = 1;
    private int capacidadMaximaTickets; // Ej: 200 entradas
    private List<Ticket> ticketsVendidos;   // Para llevar la cuenta

    public Partido(Reserva reserva, String equipos, double precioTicket, int capacidad) {
        this.idPartido = proximoId++;
        this.reserva = reserva;
        this.reserva = reserva;
        this.equipos = equipos;
        this.precioTicketBase = precioTicket;
        this.capacidadMaximaTickets = capacidad;
        this.ticketsVendidos = new ArrayList<>();
    }
    public double getPrecioTicketBase() {
        return precioTicketBase;
=======

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Partido {
        private int idPartido;
        private Reserva reserva; // El partido "usa" una reserva
        private String equipos;
        private double precioTicketBase;

        public Partido(int idPartido, Reserva reserva, String equipos, double precioTicket) throws SQLException {
            this.idPartido = idPartido;
            this.reserva = reserva;
            this.equipos = equipos;
            this.precioTicketBase = precioTicket;
            addTablaPartido(reserva.getIdReserva());
        }

    public int getIdPartido() {
        return idPartido;
    }

    protected void addTablaPartido(int idReserva) throws SQLException {
           Connection Conection;
           try {
               Conection = DriverManager.getConnection("jdbc:sqlite:TPO.db");

           } catch (SQLException e) {
               throw new RuntimeException(e);
           }

           String query = "INSERT INTO PARTIDO(idPartido, Reserve, equipos, precioTicketBase) VALUES (?, ?, ?, ?)";
           try (PreparedStatement stmt = Conection.prepareStatement(query)) {
               stmt.setInt(1, idPartido);
               stmt.setInt(2, idReserva);
               stmt.setString(3, equipos);
               stmt.setDouble(4, precioTicketBase);
               stmt.executeUpdate();
               stmt.close();
           }


        }
>>>>>>> origin/main
    }

    public int getIdPartido() {
        return idPartido;
    }

    public double calcularPrecioFinal(Partido partido, Audiencia audiencia) {
        double precioBase = this.getPrecioTicketBase();
        double precioFinal = precioBase;

        if(audiencia.getEdad() <= 6) {
            precioFinal = 0; 
        } else if (audiencia.getEdad() < 12) {
            precioFinal *= 0.5; 
        } else if (audiencia.getEdad() >= 60) {
            precioFinal *= 0.7; 
        }  

        return precioFinal;
    }

    public boolean estaLleno() {
        return this.ticketsVendidos.size() >= this.capacidadMaximaTickets;
    }
    //Agrega un ticket vendido a la lista interna del partido.
    
    public void agregarTicket(Ticket ticket) {
        this.ticketsVendidos.add(ticket);
    }
}

    