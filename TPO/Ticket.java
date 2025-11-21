package TPO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@SuppressWarnings("unused")

class Ticket {
    private int idTicket;
    private Partido partido;
    private Audiencia comprador;
    private double precioPagado;
    private static int proximoId = 1; // Variable estática para el próximo ID
    
    
    public Ticket(Partido partido, Audiencia comprador, double precioPagado) {
        this.idTicket = proximoId++; 
        this.partido = partido;
        this.comprador = comprador;
        this.precioPagado = precioPagado;
    }
       
}
