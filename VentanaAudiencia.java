// VentanaAudiencia.java
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaAudiencia extends BaseUI {
    private Sistema sistema;
    private DefaultListModel<Audiencia> listModel;
    private JList<Audiencia> listAud;

    public VentanaAudiencia(Sistema sistema) { super("Gestión de Audiencia"); this.sistema = sistema; initUI(); setVisible(true); }

    private void initUI() {
        JPanel main = new JPanel(new BorderLayout(10,10)); main.setBackground(BG); main.setBorder(BorderFactory.createEmptyBorder(12,12,12,12)); add(main);
        JPanel card = new JPanel(new BorderLayout(8,8)); card.setBackground(CARD); card.setBorder(BorderFactory.createEmptyBorder(12,12,12,12)); main.add(card, BorderLayout.CENTER);
        JLabel title = new JLabel("ABM - Audiencia / Espectadores"); title.setFont(new Font("Segoe UI", Font.BOLD, 16)); card.add(title, BorderLayout.NORTH);
        JPanel content = new JPanel(new GridLayout(1,2,10,10)); content.setBackground(CARD); card.add(content, BorderLayout.CENTER);
        JPanel form = new JPanel(new GridLayout(0,2,8,8)); form.setBackground(CARD);
        JTextField txtNombre = new JTextField(); JTextField txtApellido = new JTextField(); JTextField txtDni = new JTextField(); JTextField txtEmail = new JTextField(); JTextField txtEdad = new JTextField(); JCheckBox chkSocio = new JCheckBox("Es socio"); JTextField txtTutor = new JTextField();
        form.add(new JLabel("Nombre:")); form.add(txtNombre);
        form.add(new JLabel("Apellido:")); form.add(txtApellido);
        form.add(new JLabel("DNI:")); form.add(txtDni);
        form.add(new JLabel("Email:")); form.add(txtEmail);
        form.add(new JLabel("Edad:")); form.add(txtEdad);
        form.add(new JLabel("")); form.add(chkSocio);
        form.add(new JLabel("Tutor (si menor):")); form.add(txtTutor);
        JButton btnAlta = new JButton("Alta Audiencia"); estilizarBoton(btnAlta, new Color(76,175,80)); form.add(new JLabel("")); form.add(btnAlta);
        content.add(form);

        JPanel listPanel = new JPanel(new BorderLayout()); listPanel.setBackground(CARD);
        listModel = new DefaultListModel<>(); listAud = new JList<>(listModel); listAud.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listPanel.add(new JScrollPane(listAud), BorderLayout.CENTER);
        JPanel ops = new JPanel(new FlowLayout(FlowLayout.RIGHT)); ops.setBackground(CARD);
        JButton btnModificar = new JButton("Modificar"); JButton btnEliminar = new JButton("Eliminar");
        estilizarBoton(btnModificar, new Color(33,150,243)); estilizarBoton(btnEliminar, new Color(244,67,54)); ops.add(btnModificar); ops.add(btnEliminar); listPanel.add(ops, BorderLayout.SOUTH);
        content.add(listPanel);

        btnAlta.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText().trim(); String apellido = txtApellido.getText().trim();
                String dni = txtDni.getText().trim(); String email = txtEmail.getText().trim();
                int edad = Integer.parseInt(txtEdad.getText().trim()); boolean esSocio = chkSocio.isSelected();
                String tutor = edad < 18 ? txtTutor.getText().trim() : null;
                if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty()) { showMsg(this, "Complete nombre, apellido y dni."); return; }
                // Validación básica email -> simple regex
                if (!email.matches("^\\S+@\\S+\\.\\S+$")) { showMsg(this, "Email inválido."); return; }
                Audiencia a = new Audiencia(nombre, apellido, dni, email, edad, esSocio, tutor);
                sistema.registrarAudiencia(a);
                refreshLista();
                showMsg(this, "Audiencia registrada: " + a);
            } catch (NumberFormatException ex) { showMsg(this, "Edad inválida."); }
        });

        btnModificar.addActionListener(e -> {
            showMsg(this, "Modificar no implementado en backend. Añadir setters en Audiencia o método de actualización en Sistema.");
        });

        btnEliminar.addActionListener(e -> {
            Audiencia sel = listAud.getSelectedValue(); if (sel == null) { showMsg(this, "Seleccione audiencia."); return; }
            int opt = JOptionPane.showConfirmDialog(this, "Confirmar eliminación de " + sel + " ?", "Eliminar", JOptionPane.YES_NO_OPTION);
            if (opt != JOptionPane.YES_OPTION) return;
            boolean ok = sistema.eliminarAudienciaPorDni(sel.getDni());
            if (ok) { showMsg(this, "Audiencia eliminada."); refreshLista(); } else showMsg(this, "No se pudo eliminar.");
        });

        refreshLista();
    }

    private void refreshLista() {
        listModel.clear(); List<Audiencia> as = sistema.getListaAudiencias(); for (Audiencia a : as) listModel.addElement(a);
    }
}
