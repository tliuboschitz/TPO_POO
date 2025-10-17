public class Sistema {

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

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getDni() {
        return dni;
    }
}

class Audiencia extends Persona {
    private String email;

    public Audiencia(String nombre, String apellido, String dni, String email) {
        super(nombre, apellido, dni);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void comprarTicket (Partido partido){
        // Lógica para comprar un ticket
    }
}

class Alquilador extends Persona {
    //Pensar si hacerlo array de alquiladores o no
    public int canchasAlquiladas;

    public Alquilador(String nombre, String apellido, String dni) {
        super(nombre, apellido, dni);
    }

    public void reservarCancha (Cancha cancha){
        // Lógica para reservar una cancha
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
    }
    public UUID getiUuid() {
        return iUuid;
    }
    public String getRol() {
        return rol;
    }
public void asignarTarea (Mantenimiento tarea){
        // Lógica para asignar una tarea de mantenimiento
    }
    public void gestionarEvento (Evento evento){
        // Lógica para gestionar un evento
    }
}

class Ninio extends Persona {
    private String tutor;
    private int edad;

    public Ninio(String nombre, String apellido, String dni, String tutor, int edad) {
        super(nombre, apellido, dni);
        this.tutor = tutor;
    }

    public String getTutor() {
        return tutor;
    }
}   

class Adulto extends Persona {
    private String email;
    private boolean  esSocio;

    public Adulto(String nombre, String apellido, String dni, String email, boolean esSocio) {
        super(nombre, apellido, dni);
        this.email = email;
        this.esSocio = esSocio;
    }
}

