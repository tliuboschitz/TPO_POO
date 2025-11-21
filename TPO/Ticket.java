package TPO;

import java.sql.SQLException;

public class Ticket {
    private static int contador = 1;
    private int idTicket;
    private Partido partido;
    private Audiencia comprador;
    private double precioPagado;

    public Ticket(Partido partido, Audiencia comprador, double precioPagado) throws SQLException {
        this.idTicket = contador++;
        this.partido = partido;
        this.comprador = comprador;
        this.precioPagado = precioPagado;
        SQLTicket.addTablaT(this.idTicket, partido.getIdPartido(), comprador.getDni(),precioPagado);
    }

    public Ticket(int idTicket, Partido partido, Audiencia comprador, double precioPagado) {
        this.idTicket = idTicket;
        this.partido = partido;
        this.comprador = comprador;
        this.precioPagado = precioPagado;

        if (idTicket >= contador)
            contador = idTicket + 1;   // keep counter correct
    }


    public int getIdTicket() { return idTicket; }
    public Partido getPartido() { return partido; }
    public Audiencia getComprador() { return comprador; }
    public double getPrecioPagado() { return precioPagado; }

    @Override
    public String toString() {
        return "Ticket#" + idTicket + " - Partido: " + (partido!=null?partido.getEquipos():"N/A")
                + " - Comprador: " + (comprador!=null?comprador.getNombre():"N/A") + " - $" + precioPagado;
    }
}
