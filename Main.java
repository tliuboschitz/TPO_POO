import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class Main extends JFrame {

    private Sistema sistema;
    private JTextArea salida;
    private JComboBox<Cancha> comboCanchas;
    private JComboBox<Reserva> comboReservas;
    private JComboBox<Partido> comboPartidos;

    public Main() {
        sistema = new Sistema(); // controlador central
        setTitle("TP - Sistema de Reservas (Swing)");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // ✅ ocupa toda la pantalla
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
        setVisible(true);
    }

    // --- INTERFAZ GRÁFICA ---
    private void initUI() {

        // --- Panel superior (zona de controles) ---
        JPanel top = new JPanel();
        top.setLayout(new GridLayout(3, 1, 5, 5)); // ✅ 3 filas, 1 columna (permite que los controles no se amontonen)
        top.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Fila 1: creación de reservas
        JPanel fila1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        comboCanchas = new JComboBox<>();
        for (Cancha c : sistema.getListaCanchas()) comboCanchas.addItem(c);
        fila1.add(new JLabel("Cancha:"));
        fila1.add(comboCanchas);

        JTextField txtHora = new JTextField("20:00", 6);
        fila1.add(new JLabel("Hora:"));
        fila1.add(txtHora);

        JButton btnCrearReserva = new JButton("Crear Reserva");
        fila1.add(btnCrearReserva);

        // Fila 2: creación de partidos
        JPanel fila2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        comboReservas = new JComboBox<>();
        refreshReservas();
        fila2.add(new JLabel("Reserva -> Partido:"));
        fila2.add(comboReservas);

        JTextField txtEquipos = new JTextField("EquipoA vs EquipoB", 12);
        fila2.add(new JLabel("Equipos:"));
        fila2.add(txtEquipos);

        JTextField txtPrecioTicket = new JTextField("1500", 6);
        fila2.add(new JLabel("Precio Ticket:"));
        fila2.add(txtPrecioTicket);

        JButton btnCrearPartido = new JButton("Crear Partido");
        fila2.add(btnCrearPartido);

        // Fila 3: tickets y reportes
        JPanel fila3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        comboPartidos = new JComboBox<>();
        refreshPartidos();
        fila3.add(new JLabel("Partido:"));
        fila3.add(comboPartidos);

        JButton btnVenderTicket = new JButton("Vender Ticket");
        fila3.add(btnVenderTicket);

        JButton btnReporteIngresos = new JButton("Reporte Ingresos");
        fila3.add(btnReporteIngresos);

        JButton btnRefrescar = new JButton("Refrescar Listados");
        fila3.add(btnRefrescar);

        // Añadir filas al panel principal superior
        top.add(fila1);
        top.add(fila2);
        top.add(fila3);
        add(top, BorderLayout.NORTH);

        // --- Área central ---
        salida = new JTextArea();
        salida.setEditable(false);
        salida.setFont(new Font("Monospaced", Font.PLAIN, 13));
        salida.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(salida);
        add(scroll, BorderLayout.CENTER);

        // --- Acciones ---
        btnCrearReserva.addActionListener(e -> {
            try {
                Cancha cancha = (Cancha) comboCanchas.getSelectedItem();
                if (cancha == null) { showMsg("Seleccione una cancha."); return; }
                String hora = txtHora.getText().trim();
                if (hora.isEmpty()) { showMsg("Ingrese hora válida."); return; }
                Alquilador alquilador = new Alquilador("Demo", "Cliente", "000");
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
                if (reserva == null) { showMsg("Seleccione una reserva."); return; }
                String equipos = txtEquipos.getText().trim();
                double precio = Double.parseDouble(txtPrecioTicket.getText().trim());
                Partido p = sistema.crearPartido(reserva, equipos, precio);
                showMsg("Partido creado: " + p);
                refreshPartidos();
            } catch (Exception ex) {
                showMsg("Error creando partido: " + ex.getMessage());
            }
        });

        btnVenderTicket.addActionListener(e -> {
    try {
        Partido partido = (Partido) comboPartidos.getSelectedItem();
        if (partido == null) { showMsg("Seleccione un partido."); return; }

        String nombre = JOptionPane.showInputDialog(this, "Nombre comprador:");
        if (nombre == null || nombre.trim().isEmpty()) {
            showMsg("Venta cancelada (nombre vacío).");
            return;
        }
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            showMsg("El nombre solo puede contener letras.");
            return;
        }

        String apellido = JOptionPane.showInputDialog(this, "Apellido comprador:");
        if (apellido == null || apellido.trim().isEmpty()) {
            showMsg("Venta cancelada (apellido vacío).");
            return;
        }
        if (!apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
            showMsg("El apellido solo puede contener letras.");
            return;
        }

        String dni = JOptionPane.showInputDialog(this, "DNI comprador:");
        if (dni == null || dni.trim().isEmpty()) {
            showMsg("Venta cancelada (DNI vacío).");
            return;
        }

        String email = JOptionPane.showInputDialog(this, "Email comprador:");
        if (email == null || email.trim().isEmpty()) {
            showMsg("Venta cancelada (email vacío).");
            return;
        }

        String edadStr = JOptionPane.showInputDialog(this, "Edad:");
        if (edadStr == null || edadStr.trim().isEmpty()) {
            showMsg("Venta cancelada (edad vacía).");
            return;
        }

        int edad = Integer.parseInt(edadStr);

        int esSocioOpt = JOptionPane.showConfirmDialog(this, "¿Es socio?", "Socio", JOptionPane.YES_NO_OPTION);
        boolean esSocio = (esSocioOpt == JOptionPane.YES_OPTION);

        String tutor = null;
        if (edad < 18) {
            tutor = JOptionPane.showInputDialog(this, "Nombre tutor (menor):");
            if (tutor == null || tutor.trim().isEmpty()) {
                showMsg("Venta cancelada (tutor vacío).");
                return;
            }
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

        refreshCanchas();
        refreshReservas();
        refreshPartidos();
        showMsg("Sistema inicializado.");
    }

    private void showMsg(String msg) {
        salida.append(msg + "\n");
    }

    private void refreshCanchas() {
        comboCanchas.removeAllItems();
        for (Cancha c : sistema.getListaCanchas()) comboCanchas.addItem(c);
    }

    private void refreshReservas() {
        comboReservas.removeAllItems();
        List<Reserva> rs = sistema.getListaReservas();
        for (Reserva r : rs) comboReservas.addItem(r);
    }

    private void refreshPartidos() {
        comboPartidos.removeAllItems();
        List<Partido> ps = sistema.getListaPartidos();
        for (Partido p : ps) comboPartidos.addItem(p);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}
