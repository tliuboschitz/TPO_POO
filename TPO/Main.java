package TPO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
    import java.util.Scanner;


    // --- Clase Main para correr el sistema ---
 public class Main {

        static Connection Conection;
        static {
            try {
                Class.forName("org.sqlite.JDBC");

                Conection = DriverManager.getConnection("jdbc:sqlite:TPO.db");//Problemas con No suitable driver found for jdbc:sqlite:TPO.db
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        ;



        public static void main(String[] args) throws SQLException {

            try {
                Statement stmt = Conection.createStatement();
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS PERSONA(nombre TEXT, apellido TEXT, dni INTEGER PRIMARY KEY NOT NULL)");
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ALQUILADOR(dniAl INTEGER PRIMARY KEY, FOREIGN KEY(dniAl) REFERENCES PERSONA(dni))");
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS AUDIENCIA(dniA INTEGER PRIMARY KEY, email TEXT, edad INTEGER, esSocio BOOLEAN, dniTutor INTEGER, FOREIGN KEY(dniA) REFERENCES PERSONA(dni), FOREIGN KEY(dniTutor) REFERENCES PERSONA(dni))");
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS EMPLEADO(dniE INTEGER PRIMARY KEY, rol TEXT, FOREIGN KEY(dniE) REFERENCES PERSONA(dni))");
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS CANCHA(idCancha INTEGER PRIMARY KEY, nombreC TEXT, tipo TEXT, precioHora REAL, estado TEXT)");
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS RESERVA(idReserva INTEGER PRIMARY KEY, date REAL, hora TEXT, Alquilador INTEGER, Cancha INTEGER, monto REAL, estado TEXT, idComprobante INTEGER, FOREIGN KEY(Alquilador) REFERENCES PERSONA(dni), FOREIGN KEY(Cancha) REFERENCES CANCHA(idCancha))");
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS PARTIDO(idPartido INTEGER PRIMARY KEY, Reserve INTEGER, equipos TEXT, precioTicketBase REAL, FOREIGN KEY(Reserve) REFERENCES RESERVA(idReserva))");
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS TICKER(idTicket INTEGER PRIMARY KEY, Partido INTEGER, dniComprador INTEGER, precioPagado REAL, FOREIGN KEY(Partido) REFERENCES PARTIDO(idPartido), FOREIGN KEY(dniComprador) REFERENCES AUDIENCIA(dniA))");
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS MANTENIMIENTO(idMantenimiento INTEGER PRIMARY KEY, dni INTEGER, descripcion TEXT, CanchaId INTEGER, estado TEXT, FOREIGN KEY(dni) REFERENCES EMPLEADO(dniE), FOREIGN KEY(CanchaId) REFERENCES CANCHA(idCancha))");

                stmt.close();


        }
        catch (SQLException e){
            System.err.println("Error al conectar con la base de datos:");
            e.printStackTrace();
        }


        // --- 1. SETUP DEL MODELO ---
        Sistema miSistema = new Sistema();
        
        // datos de prueba para que el sistema no esté vacío
        // Se puede cambiar a que el alquilador se ingrese por consola.
        // Mas adelante sera reemplazado por una interfaz gráfica.
        Alquilador alquilador = new Alquilador("Juan", "Perez", "123");
        miSistema.registrarAlquilador(alquilador);
        
        Cancha cancha = miSistema.buscarCanchaPorId(3);

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

   
   
