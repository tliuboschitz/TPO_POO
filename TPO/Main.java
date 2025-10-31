package TPO;
import java.util.ArrayList;
    import java.util.Date;
    import java.util.List;
    import java.util.UUID;
    import java.util.Scanner;


    // --- Clase Main para correr el sistema ---
 public class Main {

    public static void main(String[] args) {
        
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

   
   
