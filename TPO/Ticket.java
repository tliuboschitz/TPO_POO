package TPO;

public class Ticket {
    private int idTicket;
    private Partido partido;
    private Audiencia comprador;
    private double precioPagado;
    private static int proximoId = 1; // Variable estática para el próximo ID
    
    
    public Ticket (Partido partido, Audiencia comprador, double precioPagado) {
        this.idTicket = proximoId++; 
        this.partido = partido;
        this.comprador = comprador;
        this.precioPagado = precioPagado;
    }
       
}
