package TPO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
    import java.util.Date;
    import java.util.List;
    import java.util.UUID;
    import java.util.Scanner;


    // --- Clase Main para correr el sistema ---
 public class Main {
        static String Conection = "jdbc:mysql://localhost;" + "database=TPO;" + "user=root;" + "password=;" ;


        public static void main(String[] args) throws SQLException {

        try (Connection conn = DriverManager.getConnection(Conection);){
            String createP = "CREATE TABLE PERSONA(nombre TEXT , apellido TEXT, dni INTEGER PRIMARY KEY NOT NULL )";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(createP);

            String createA = "CREATE TABLE AUDIENCIA(FOREIGN KEY(dniA) REFERENCES PERSONA(dni), email TEXT, edad INTEGER, esSocio BOOLEAN, FOREIGN KEY(dniTutor) REFERENCES PERSONA(dni))";
            stmt.executeUpdate(createA);

            String createE = "CREATE TABLE EMPLEADO(FOREIGN KEY(dniE) references PERSONA(dni), rol TEXT)";
            stmt.executeUpdate(createE);

            String createC = "CREATE TABLE CANCHA(idCancha INTEGER PRIMARY KEY, nombreC TEXT, tipo TEXT, precioHora REAL, estado TEXT)";
            stmt.executeUpdate(createC);

            String Reserva = "CREATE TABLE RESERVA(idReserva INTEGER PRIMARY KEY, date REAL, hora REAL, FOREIGN KEY (Alquilador) REFERENCES PERSONA(dni),  FOREIGN KEY (Cancha) REFERENCES CANCHA(idCancha), monto REAL, estado STRING, idComprobante INTEGER NULL)";
            stmt.executeUpdate(Reserva);

            String createPa = "CREATE TABLE PARTIDO(idPartido INTEGER PRIMARY KEY, FOREIGN KEY (Reserve) REFERENCES RESERVA(idReserva), equipos TEXT, precioTicketBase REAL )";
            stmt.executeUpdate(createPa);

            String createT = "CREATE TABLE TICKER(idTicket INTEGER PRIMARY KEY, FOREIGN KEY(Partido) REFERENCES PARTIDO(idPartido), FOREIGN KEY (dniComprador) REFERENCES AUDIENCIA(dniA), FOREIGN KEY  (precioPagado) REFERENCES PARTIDO(precioTicketBase) )";
            stmt.executeUpdate(createT);

            String createM = "CREATE TABLE MANTENIMIENTO(FOREIGN KEY(dni) REFERENCES EMPLEADO(dniE), idMantenimiento INTEGER PRIMARY KEY, descripcion TEXT, FOREIGN KEY (CanchaId) REFERENCES CANCHA(idCancha), estado TEXT)";
            stmt.executeUpdate(createM);



        }
        catch (SQLException e){
            e.printStackTrace();
        }


        // --- 1. SETUP DEL MODELO ---
        Sistema miSistema = new Sistema();
        
        // datos de prueba para que el sistema no esté vacío
        // Se puede cambiar a que el alquilador se ingrese por consola.
        // Mas adelante sera reemplazado por una interfaz gráfica.
        Alquilador alquilador = new Alquilador("Juan", "Perez", 123);
        miSistema.registrarAlquilador(alquilador);
        
        Cancha cancha = miSistema.buscarCanchaPorId(3);
        try {
            // Example for MySQL; adjust URL, username, and password as needed
            String url = "jdbc:mysql://localhost:3306/yourDatabaseName";
            String user = "yourUsername";
            String password = "yourPassword";

            // Establish the connection
            DriverManager.getConnection(Conection);
        } catch (SQLException e) {
            e.printStackTrace();  // Print error if connection fails
        }

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

   
   
