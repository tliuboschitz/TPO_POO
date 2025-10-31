import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.List;

/**
 * Main cdon swing: interfaz principal que permite:
 * - crear reservas, listar canchas y reservas, crear partidos a partir de reserva, vender tickets y generar reporte de ingresos.
 */
public class Main extends JFrame {

    private Sistema sistema;
    private JTextArea salida;
    private JComboBox<Cancha> comboCanchas;
    private JComboBox<Reserva> comboReservas;
    private JComboBox<Partido> comboPartidos;

    public Main() {
        sistema = new Sistema(); // controlador central
        setTitle("TP - Sistema de Reservas (Swing)");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
        setVisible(true);
    }

    // Inicializa componentes y acciones
    private void initUI() {
        // Panel superior con controles
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        comboCanchas = new JComboBox<>();
        for (Cancha c : sistema.getListaCanchas()) comboCanchas.addItem(c);
        top.add(new JLabel("Cancha:"));
        top.add(comboCanchas);

        JTextField txtHora = new JTextField("20:00", 6);
        top.add(new JLabel("Hora:"));
        top.add(txtHora);

        JButton btnCrearReserva = new JButton("Crear Reserva");
        top.add(btnCrearReserva);

        // Combo reservas para crear partido
        comboReservas = new JComboBox<>();
        refreshReservas();
        top.add(new JLabel("Reserva -> Partido:"));
        top.add(comboReservas);

        JTextField txtEquipos = new JTextField("EquipoA vs EquipoB", 12);
        top.add(new JLabel("Equipos:"));
        top.add(txtEquipos);

        JTextField txtPrecioTicket = new JTextField("1500", 6);
        top.add(new JLabel("Precio Ticket:"));
        top.add(txtPrecioTicket);

        JButton btnCrearPartido = new JButton("Crear Partido");
        top.add(btnCrearPartido);

        // Combo partidos para vender ticket
        comboPartidos = new JComboBox<>();
        refreshPartidos();
        top.add(new JLabel("Partido:"));
        top.add(comboPartidos);

        JButton btnVenderTicket = new JButton("Vender Ticket");
        top.add(btnVenderTicket);

        JButton btnReporteIngresos = new JButton("Reporte Ingresos");
        top.add(btnReporteIngresos);

        JButton btnRefrescar = new JButton("Refrescar Listados");
        top.add(btnRefrescar);

        add(top, BorderLayout.NORTH);

        // Área central de texto
        salida = new JTextArea();
        salida.setEditable(false);
        salida.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(salida), BorderLayout.CENTER);

        // Acciones binding (de selección e ingreso)
        btnCrearReserva.addActionListener(e -> {
            try {
                Cancha cancha = (Cancha) comboCanchas.getSelectedItem();
                if (cancha == null) { showMsg("Seleccione una cancha."); return; }
                String hora = txtHora.getText().trim();
                if (hora.isEmpty()) { showMsg("Ingrese hora válida."); return; }
                Alquilador alquilador = new Alquilador("Demo", "Cliente", "000"); // demo
                sistema.registrarAlquilador(alquilador);
                Reserva r = sistema.crearReserva(alquilador, cancha, new Date(), hora);
                showMsg("Reserva creada: " + r);
                refreshReservas();
            } catch (Exception ex) {
                showMsg("Error creando reserva: " + ex.getMessage());
            }
        });

        btnCrearPartido.addActionListener(e -> {
            try {
                Reserva reserva = (Reserva) comboReservas.getSelectedItem();
                if (reserva == null) { showMsg("Seleccione una reserva para crear el partido."); return; }
                String equipos = txtEquipos.getText().trim();
                double precio = Double.parseDouble(txtPrecioTicket.getText().trim());
                Partido p = sistema.crearPartido(reserva, equipos, precio);
                showMsg("Partido creado: " + p);
                refreshPartidos();
            } catch (NumberFormatException nfe) {
                showMsg("Precio inválido.");
            } catch (Exception ex) {
                showMsg("Error creando partido: " + ex.getMessage());
            }
        });

        btnVenderTicket.addActionListener(e -> {
            try {
                Partido partido = (Partido) comboPartidos.getSelectedItem();
                if (partido == null) { showMsg("Seleccione un partido."); return; }
                // Aca se piden los datos de la persona que va a comprar (simplificado en dialogs)
                String nombre = JOptionPane.showInputDialog(this, "Nombre comprador:");
                if (nombre == null || nombre.trim().isEmpty()) { showMsg("Venta cancelada."); return; }
                String apellido = JOptionPane.showInputDialog(this, "Apellido comprador:");
                String dni = JOptionPane.showInputDialog(this, "DNI comprador:");
                String email = JOptionPane.showInputDialog(this, "Email comprador:");
                String edadStr = JOptionPane.showInputDialog(this, "Edad:");
                int edad = Integer.parseInt(edadStr);
                int esSocioOpt = JOptionPane.showConfirmDialog(this, "¿Es socio?", "Socio", JOptionPane.YES_NO_OPTION);
                boolean esSocio = (esSocioOpt == JOptionPane.YES_OPTION);
                String tutor = null;
                if (edad < 18) {
                    tutor = JOptionPane.showInputDialog(this, "Nombre tutor (menor):");
                }
                Audiencia a = new Audiencia(nombre, apellido, dni, email, edad, esSocio, tutor);
                sistema.registrarAudiencia(a);
                Ticket t = sistema.venderTicket(partido, a);
                showMsg("Ticket vendido: " + t);
            } catch (NumberFormatException nfe) {
                showMsg("Edad inválida. Venta cancelada.");
            } catch (Exception ex) {
                showMsg("Error en venta: " + ex.getMessage());
            }
        });

        btnReporteIngresos.addActionListener(e -> {
            double total = sistema.generarReporteIngresos();
            showMsg("Reporte de ingresos: $ " + total);
        });

        btnRefrescar.addActionListener(e -> {
            refreshCanchas();
            refreshReservas();
            refreshPartidos();
            showMsg("Listados actualizados.");
        });

        // Inicializa vistas
        refreshCanchas();
        refreshReservas();
        refreshPartidos();
        showMsg("Sistema inicializado.");
    }

    // Muestra mensaje en la área de salida
    private void showMsg(String msg) {
        salida.append(msg + "\n");
    }

    // Refresca combobox canchas
    private void refreshCanchas() {
        comboCanchas.removeAllItems();
        for (Cancha c : sistema.getListaCanchas()) comboCanchas.addItem(c);
    }

    // Refresca combobox reservas
    private void refreshReservas() {
        comboReservas.removeAllItems();
        List<Reserva> rs = sistema.getListaReservas();
        for (Reserva r : rs) comboReservas.addItem(r);
    }

    // Refresca combobox partidos
    private void refreshPartidos() {
        comboPartidos.removeAllItems();
        List<Partido> ps = sistema.getListaPartidos();
        for (Partido p : ps) comboPartidos.addItem(p);
    }

    // MAIN
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}
