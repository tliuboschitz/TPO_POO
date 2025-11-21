import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * VentanaPartidos: interfaz ABM para Partidos
 * - Alta: crear partido a partir de una reserva existente
 * - Modificar: editar equipos, precio y capacidad (usa setters de Partido)
 * - Eliminar: llama a sistema.eliminarPartido(id)
 *
 * La ventana refresca la lista de partidos que usa la Venta de Tickets.
 */
public class VentanaPartidos extends BaseUI {

    private Sistema sistema;
    private DefaultListModel<Partido> listModel;
    private JList<Partido> listPartidos;

    public VentanaPartidos(Sistema sistema) {
        super("Gestión de Partidos");
        this.sistema = sistema;
        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel main = new JPanel(new BorderLayout(10,10));
        main.setBackground(BG);
        main.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        add(main);

        JPanel card = new JPanel(new BorderLayout(8,8));
        card.setBackground(CARD);
        card.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        main.add(card, BorderLayout.CENTER);

        JLabel title = new JLabel("ABM - Partidos");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        card.add(title, BorderLayout.NORTH);

        JPanel content = new JPanel(new GridLayout(1,2,10,10));
        content.setBackground(CARD);
        card.add(content, BorderLayout.CENTER);

        // --- Form creación ---
        JPanel form = new JPanel(new GridLayout(0,2,8,8));
        form.setBackground(CARD);

        JComboBox<Reserva> comboReservas = new JComboBox<>();
        for (Reserva r : sistema.getListaReservas()) comboReservas.addItem(r);

        JTextField txtEquipos = new JTextField("EquipoA vs EquipoB");
        JTextField txtPrecio = new JTextField("1500");
        JTextField txtCapacidad = new JTextField("200");

        form.add(new JLabel("Reserva:")); form.add(comboReservas);
        form.add(new JLabel("Equipos:")); form.add(txtEquipos);
        form.add(new JLabel("Precio base:")); form.add(txtPrecio);
        form.add(new JLabel("Capacidad máxima:")); form.add(txtCapacidad);

        JButton btnCrear = new JButton("Crear Partido");
        estilizarBoton(btnCrear, new Color(33,150,243));
        form.add(new JLabel("")); form.add(btnCrear);

        content.add(form);

        // --- Lista de partidos y acciones ---
        JPanel listPanel = new JPanel(new BorderLayout(6,6));
        listPanel.setBackground(CARD);
        listModel = new DefaultListModel<>();
        listPartidos = new JList<>(listModel);
        listPartidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listPanel.add(new JScrollPane(listPartidos), BorderLayout.CENTER);

        JPanel ops = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        ops.setBackground(CARD);
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        estilizarBoton(btnModificar, new Color(76,175,80)); // verde
        estilizarBoton(btnEliminar, new Color(244,67,54));  // rojo
        ops.add(btnModificar); ops.add(btnEliminar);
        listPanel.add(ops, BorderLayout.SOUTH);

        content.add(listPanel);

        // ACCIONES
        btnCrear.addActionListener(e -> {
            try {
                Reserva reserva = (Reserva) comboReservas.getSelectedItem();
                if (reserva == null) { showMsg(this, "Seleccione una reserva."); return; }
                String equipos = txtEquipos.getText().trim();
                double precio = Double.parseDouble(txtPrecio.getText().trim());
                int capacidad = Integer.parseInt(txtCapacidad.getText().trim());

                Partido p = sistema.crearPartido(reserva, equipos, precio, capacidad);
                showMsg(this, "Partido creado: " + p);
                refreshLista();
            } catch (NumberFormatException ex) {
                showMsg(this, "Valores numéricos inválidos.");
            } catch (Exception ex) {
                showMsg(this, "Error creando partido: " + ex.getMessage());
            }
        });

        btnModificar.addActionListener(e -> {
            Partido sel = listPartidos.getSelectedValue();
            if (sel == null) { showMsg(this, "Seleccione un partido."); return; }
            // pedir nuevos valores
            String nuevoEquipos = JOptionPane.showInputDialog(this, "Equipos:", sel.getEquipos());
            if (nuevoEquipos == null) return;
            String precioStr = JOptionPane.showInputDialog(this, "Precio base:", sel.getPrecioTicketBase());
            if (precioStr == null) return;
            String capStr = JOptionPane.showInputDialog(this, "Capacidad máxima:", "200");
            if (capStr == null) return;
            try {
                double nuevoPrecio = Double.parseDouble(precioStr);
                int nuevaCap = Integer.parseInt(capStr);
                sel.setEquipos(nuevoEquipos);
                sel.setPrecioTicketBase(nuevoPrecio);
                sel.setCapacidadMaximaTickets(nuevaCap);
                refreshLista();
                showMsg(this, "Partido modificado: " + sel);
            } catch (NumberFormatException ex) {
                showMsg(this, "Valores numéricos inválidos.");
            }
        });

        btnEliminar.addActionListener(e -> {
            Partido sel = listPartidos.getSelectedValue();
            if (sel == null) { showMsg(this, "Seleccione un partido."); return; }
            int opt = JOptionPane.showConfirmDialog(this, "Confirmar eliminación de " + sel + " ?", "Eliminar", JOptionPane.YES_NO_OPTION);
            if (opt != JOptionPane.YES_OPTION) return;
            boolean ok = sistema.eliminarPartido(sel.getIdPartido());
            if (ok) {
                showMsg(this, "Partido eliminado y tickets asociados removidos.");
            } else {
                showMsg(this, "No se pudo eliminar el partido (no encontrado).");
            }
            refreshLista();
        });

        refreshLista();
    }

    private void refreshLista() {
        listModel.clear();
        List<Partido> ps = sistema.getListaPartidos();
        for (Partido p : ps) listModel.addElement(p);
    }
}
