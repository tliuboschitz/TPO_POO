import javax.swing.*;
import java.awt.*;

/**
 * MenuPrincipal:
 * Ventana inicial con botones para abrir las 6 interfaces del TP.
 * - 4 ABM (Canchas, Reservas, Empleados, Audiencia)
 * - 2 funcionales (VentaTickets, ReporteIngresos)
 * + NUEVO: Gestión de Partidos (ABM de Partidos)
 *
 * Mantiene estilo uniforme con BaseUI.
 */
public class MenuPrincipal extends BaseUI {

    private Sistema sistema;

    public MenuPrincipal() {
        super("Menú Principal - TP Reservas");
        sistema = new Sistema(); // sistema compartido por todas las ventanas
        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel main = new JPanel(new GridLayout(3,3,15,15));
        main.setBackground(BG);
        main.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        add(main);

        // --------------------
        // BOTONES PRINCIPALES
        // --------------------
        JButton btnCanchas = new JButton("Gestión de Canchas");
        JButton btnReservas = new JButton("Gestión de Reservas");
        JButton btnEmpleados = new JButton("Gestión de Empleados");
        JButton btnAudiencia = new JButton("Gestión de Audiencia");

        // ⭐ NUEVO BOTÓN: PARTIDOS
        JButton btnPartidos = new JButton("Gestión de Partidos");

        JButton btnVenta = new JButton("Venta de Tickets");
        JButton btnReporte = new JButton("Reporte de Ingresos");

        // --------------------
        // ESTILO
        // --------------------
        estilizarBoton(btnCanchas, new Color(0,150,136));
        estilizarBoton(btnReservas, new Color(63,81,181));
        estilizarBoton(btnEmpleados, new Color(121,85,72));
        estilizarBoton(btnAudiencia, new Color(233,30,99));

        // ⭐ ESTILO PARA PARTIDOS
        estilizarBoton(btnPartidos, new Color(0,121,107)); // verde oscuro/azulado

        estilizarBoton(btnVenta, new Color(255,152,0));
        estilizarBoton(btnReporte, new Color(33,150,243));

        // --------------------
        // AGREGAR AL PANEL
        // --------------------
        main.add(btnCanchas);
        main.add(btnReservas);
        main.add(btnEmpleados);
        main.add(btnAudiencia);

        // ⭐ Añadimos el botón de Partidos
        main.add(btnPartidos);

        main.add(btnVenta);
        main.add(btnReporte);

        // --------------------
        // ACCIONES
        // --------------------
        btnCanchas.addActionListener(e -> new VentanaCanchas(sistema));
        btnReservas.addActionListener(e -> new VentanaReservas(sistema));
        btnEmpleados.addActionListener(e -> new VentanaEmpleados(sistema));
        btnAudiencia.addActionListener(e -> new VentanaAudiencia(sistema));
        btnPartidos.addActionListener(e -> new VentanaPartidos(sistema));

        btnVenta.addActionListener(e -> new VentanaVentaTickets(sistema));
        btnReporte.addActionListener(e -> new VentanaReporteIngresos(sistema));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuPrincipal::new);
    }
}
