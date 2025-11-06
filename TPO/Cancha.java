package TPO;
public class Cancha {
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