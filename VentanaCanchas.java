// VentanaCanchas.java  (ya detallada antes — idéntica a la versión que tenías, pero ahora puede llamar sistema.eliminarCancha)
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaCanchas extends BaseUI {
    private Sistema sistema;
    private DefaultListModel<Cancha> listModel;
    private JList<Cancha> listCanchas;

    public VentanaCanchas(Sistema sistema) {
        super("Gestión de Canchas");
        this.sistema = sistema;
        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel main = new JPanel(new BorderLayout(10,10));
        main.setBackground(BG); main.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        add(main);
        JPanel card = new JPanel(new BorderLayout(8,8));
        card.setBackground(CARD); card.setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
        main.add(card, BorderLayout.CENTER);
        JLabel title = new JLabel("ABM - Canchas"); title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        card.add(title, BorderLayout.NORTH);
        JPanel content = new JPanel(new GridLayout(1,2,10,10)); content.setBackground(CARD); card.add(content, BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(0,2,8,8)); form.setBackground(CARD);
        JTextField txtNombre = new JTextField();
        JTextField txtTipo = new JTextField();
        JTextField txtPrecio = new JTextField();
        form.add(new JLabel("Nombre:")); form.add(txtNombre);
        form.add(new JLabel("Tipo (F11/F9/F5):")); form.add(txtTipo);
        form.add(new JLabel("Precio por hora:")); form.add(txtPrecio);
        JButton btnAlta = new JButton("Alta"); estilizarBoton(btnAlta, new Color(76,175,80));
        form.add(new JLabel("")); form.add(btnAlta);
        content.add(form);

        JPanel listPanel = new JPanel(new BorderLayout(6,6)); listPanel.setBackground(CARD);
        listModel = new DefaultListModel<>(); listCanchas = new JList<>(listModel); listCanchas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane sp = new JScrollPane(listCanchas); sp.setPreferredSize(new Dimension(300,0)); listPanel.add(sp, BorderLayout.CENTER);
        JPanel ops = new JPanel(new FlowLayout(FlowLayout.RIGHT)); ops.setBackground(CARD);
        JButton btnModificar = new JButton("Modificar (estado)");
        JButton btnEliminar = new JButton("Eliminar");
        estilizarBoton(btnModificar, new Color(33,150,243));
        estilizarBoton(btnEliminar, new Color(244,67,54));
        ops.add(btnModificar); ops.add(btnEliminar); listPanel.add(ops, BorderLayout.SOUTH);
        content.add(listPanel);

        btnAlta.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText().trim();
                String tipo = txtTipo.getText().trim();
                double precio = Double.parseDouble(txtPrecio.getText().trim());
                if (nombre.isEmpty() || tipo.isEmpty()) { showMsg(this, "Complete todos los campos."); return; }
                Cancha c = new Cancha(nombre, tipo, precio);
                sistema.registrarCancha(c);
                refreshLista();
                showMsg(this, "Cancha creada: " + c);
                txtNombre.setText(""); txtTipo.setText(""); txtPrecio.setText("");
            } catch (NumberFormatException ex) { showMsg(this, "Precio inválido."); }
        });

        btnModificar.addActionListener(e -> {
            Cancha sel = listCanchas.getSelectedValue(); if (sel == null) { showMsg(this, "Seleccione una cancha."); return; }
            String nuevoEstado = JOptionPane.showInputDialog(this, "Nuevo estado (Disponible/En Mantenimiento/Reservada):", sel.getEstado());
            if (nuevoEstado == null) return;
            sel.setEstado(nuevoEstado);
            refreshLista();
            showMsg(this, "Estado actualizado: " + sel);
        });

        btnEliminar.addActionListener(e -> {
            Cancha sel = listCanchas.getSelectedValue(); if (sel == null) { showMsg(this, "Seleccione una cancha."); return; }
            int opt = JOptionPane.showConfirmDialog(this, "Confirmar eliminación de " + sel + " ?", "Eliminar", JOptionPane.YES_NO_OPTION);
            if (opt != JOptionPane.YES_OPTION) return;
            boolean ok = sistema.eliminarCancha(sel.getIdCancha());
            if (ok) { showMsg(this, "Cancha eliminada."); refreshLista(); }
            else showMsg(this, "No se pudo eliminar la cancha.");
        });

        refreshLista();
    }

    private void refreshLista() {
        listModel.clear(); List<Cancha> canchas = sistema.getListaCanchas();
        for (Cancha c : canchas) listModel.addElement(c);
    }
}
