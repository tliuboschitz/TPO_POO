import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;

/**
 * Main con Swing: interfaz principal tipo formulario
 * con botones estilizados y distribución limpia tipo Google Forms.
 */
public class Main extends JFrame {

    private Sistema sistema;
    private JTextArea salida;
    private JComboBox<Cancha> comboCanchas;
    private JComboBox<Reserva> comboReservas;
    private JComboBox<Partido> comboPartidos;

    public Main() {
        sistema = new Sistema();
        setTitle("TP - Sistema de Reservas (Swing)");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
        setVisible(true);
    }

    // --- INTERFAZ GRÁFICA ---
    private void initUI() {

        // --- Panel principal ---
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(245, 247, 252));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(mainPanel);

        // --- Panel de formularios ---
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(new Color(245, 247, 252));

        // Secciones del formulario
        formPanel.add(crearSeccionReserva());
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(crearSeccionPartido());
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(crearSeccionVenta());

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // --- Panel lateral de registro ---
        salida = new JTextArea();
        salida.setEditable(false);
        salida.setFont(new Font("Consolas", Font.PLAIN, 13));
        salida.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scroll = new JScrollPane(salida);
        scroll.setPreferredSize(new Dimension(400, 0));
        scroll.setBorder(BorderFactory.createTitledBorder("Registro del Sistema"));
        mainPanel.add(scroll, BorderLayout.EAST);

        // --- Panel inferior con botones globales ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(245, 247, 252));

        JButton btnReporteIngresos = new JButton("Reporte de Ingresos");
        JButton btnRefrescar = new JButton("Refrescar Datos");

        estilizarBoton(btnReporteIngresos, new Color(156, 39, 176)); // violeta
        estilizarBoton(btnRefrescar, new Color(96, 125, 139));       // gris azulado

        bottomPanel.add(btnReporteIngresos);
        bottomPanel.add(btnRefrescar);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // --- Acciones de botones globales ---
        btnReporteIngresos.addActionListener(e -> {
            double total = sistema.generarReporteIngresos();
            showMsg("💰 Reporte de ingresos total: $" + total);
        });

        btnRefrescar.addActionListener(e -> {
            refreshCanchas();
            refreshReservas();
            refreshPartidos();
            showMsg("🔄 Listados actualizados.");
        });

        showMsg("✅ Sistema inicializado correctamente.");
    }

    // --- SECCIÓN 1: RESERVA ---
    private JPanel crearSeccionReserva() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Datos de Reserva"));
        panel.setBackground(Color.WHITE);

        comboCanchas = new JComboBox<>();
        for (Cancha c : sistema.getListaCanchas()) comboCanchas.addItem(c);

        JTextField txtHora = new JTextField("20:00", 6);
        JButton btnCrearReserva = new JButton("Crear Reserva");
        estilizarBoton(btnCrearReserva, new Color(76, 175, 80)); // verde

        panel.add(new JLabel("Cancha:"));
        panel.add(comboCanchas);
        panel.add(new JLabel("Hora:"));
        panel.add(txtHora);
        panel.add(new JLabel(""));
        panel.add(btnCrearReserva);

        btnCrearReserva.addActionListener(e -> {
            try {
                Cancha cancha = (Cancha) comboCanchas.getSelectedItem();
                if (cancha == null) {
                    showMsg("⚠️ Seleccione una cancha.");
                    return;
                }
                String hora = txtHora.getText().trim();
                if (hora.isEmpty()) {
                    showMsg("⚠️ Ingrese una hora válida.");
                    return;
                }
                Alquilador alquilador = new Alquilador("Demo", "Cliente", "000");
                sistema.registrarAlquilador(alquilador);
                Reserva r = sistema.crearReserva(alquilador, cancha, new Date(), hora);
                showMsg("📅 Reserva creada: " + r);
                refreshReservas();
            } catch (Exception ex) {
                showMsg("❌ Error creando reserva: " + ex.getMessage());
            }
        });

        return panel;
    }

    // --- SECCIÓN 2: PARTIDO ---
    private JPanel crearSeccionPartido() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Partido"));
        panel.setBackground(Color.WHITE);

        comboReservas = new JComboBox<>();
        refreshReservas();

        JTextField txtEquipos = new JTextField("EquipoA vs EquipoB", 12);
        JTextField txtPrecioTicket = new JTextField("1500", 6);
        JButton btnCrearPartido = new JButton("Crear Partido");
        estilizarBoton(btnCrearPartido, new Color(33, 150, 243)); // azul

        panel.add(new JLabel("Reserva:"));
        panel.add(comboReservas);
        panel.add(new JLabel("Equipos:"));
        panel.add(txtEquipos);
        panel.add(new JLabel("Precio Ticket:"));
        panel.add(txtPrecioTicket);
        panel.add(new JLabel(""));
        panel.add(btnCrearPartido);

        btnCrearPartido.addActionListener(e -> {
            try {
                Reserva reserva = (Reserva) comboReservas.getSelectedItem();
                if (reserva == null) {
                    showMsg("⚠️ Seleccione una reserva.");
                    return;
                }
                String equipos = txtEquipos.getText().trim();
                double precio = Double.parseDouble(txtPrecioTicket.getText().trim());
                Partido p = sistema.crearPartido(reserva, equipos, precio);
                showMsg("⚽ Partido creado: " + p);
                refreshPartidos();
            } catch (Exception ex) {
                showMsg("❌ Error creando partido: " + ex.getMessage());
            }
        });

        return panel;
    }

    // --- SECCIÓN 3: VENTA DE TICKET ---
    private JPanel crearSeccionVenta() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Venta de Ticket"));
        panel.setBackground(Color.WHITE);

        comboPartidos = new JComboBox<>();
        refreshPartidos();

        JTextField txtNombre = new JTextField();
        JTextField txtApellido = new JTextField();
        JTextField txtDni = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtEdad = new JTextField();
        JCheckBox chkSocio = new JCheckBox("Es socio");
        JTextField txtTutor = new JTextField();
        JButton btnVenderTicket = new JButton("Vender Ticket");
        estilizarBoton(btnVenderTicket, new Color(255, 152, 0)); // naranja

        panel.add(new JLabel("Partido:"));
        panel.add(comboPartidos);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Apellido:"));
        panel.add(txtApellido);
        panel.add(new JLabel("DNI:"));
        panel.add(txtDni);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel("Edad:"));
        panel.add(txtEdad);
        panel.add(chkSocio);
        panel.add(new JLabel("Tutor (si menor):"));
        panel.add(txtTutor);
        panel.add(new JLabel(""));
        panel.add(btnVenderTicket);

        btnVenderTicket.addActionListener(e -> {
            try {
                Partido partido = (Partido) comboPartidos.getSelectedItem();
                if (partido == null) {
                    showMsg("⚠️ Seleccione un partido.");
                    return;
                }
                String nombre = txtNombre.getText().trim();
                String apellido = txtApellido.getText().trim();
                String dni = txtDni.getText().trim();
                String email = txtEmail.getText().trim();
                int edad = Integer.parseInt(txtEdad.getText().trim());
                boolean esSocio = chkSocio.isSelected();
                String tutor = edad < 18 ? txtTutor.getText().trim() : null;

                Audiencia a = new Audiencia(nombre, apellido, dni, email, edad, esSocio, tutor);
                sistema.registrarAudiencia(a);
                Ticket t = sistema.venderTicket(partido, a);
                showMsg("🎟️ Ticket vendido: " + t);
            } catch (NumberFormatException nfe) {
                showMsg("⚠️ Edad inválida. Venta cancelada.");
            } catch (Exception ex) {
                showMsg("❌ Error en venta: " + ex.getMessage());
            }
        });

        return panel;
    }

    // --- MÉTODOS AUXILIARES ---
    private void showMsg(String msg) {
        salida.append(msg + "\n");
    }

    private void refreshCanchas() {
        comboCanchas.removeAllItems();
        for (Cancha c : sistema.getListaCanchas()) comboCanchas.addItem(c);
    }

    private void refreshReservas() {
        if (comboReservas != null) {
            comboReservas.removeAllItems();
            List<Reserva> rs = sistema.getListaReservas();
            for (Reserva r : rs) comboReservas.addItem(r);
        }
    }

    private void refreshPartidos() {
        if (comboPartidos != null) {
            comboPartidos.removeAllItems();
            List<Partido> ps = sistema.getListaPartidos();
            for (Partido p : ps) comboPartidos.addItem(p);
        }
    }

    // --- ESTILIZAR BOTONES ---
    private void estilizarBoton(JButton boton, Color colorFondo) {
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(colorFondo.darker(), 1, true),
                BorderFactory.createEmptyBorder(8, 18, 8, 18)
        ));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                boton.setBackground(colorFondo.darker());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                boton.setBackground(colorFondo);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
