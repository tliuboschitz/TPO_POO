// VentanaEmpleados.java
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaEmpleados extends BaseUI {
    private Sistema sistema;
    private DefaultListModel<Empleado> listModel;
    private JList<Empleado> listEmpleados;

    public VentanaEmpleados(Sistema sistema) { super("Gestión de Empleados"); this.sistema = sistema; initUI(); setVisible(true); }

    private void initUI() {
        JPanel main = new JPanel(new BorderLayout(10,10)); main.setBackground(BG); main.setBorder(BorderFactory.createEmptyBorder(12,12,12,12)); add(main);
        JPanel card = new JPanel(new BorderLayout(8,8)); card.setBackground(CARD); card.setBorder(BorderFactory.createEmptyBorder(12,12,12,12)); main.add(card, BorderLayout.CENTER);
        JLabel title = new JLabel("ABM - Empleados"); title.setFont(new Font("Segoe UI", Font.BOLD, 16)); card.add(title, BorderLayout.NORTH);
        JPanel content = new JPanel(new GridLayout(1,2,10,10)); content.setBackground(CARD); card.add(content, BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(0,2,8,8)); form.setBackground(CARD);
        JTextField txtNombre = new JTextField(); JTextField txtApellido = new JTextField(); JTextField txtDni = new JTextField(); JTextField txtRol = new JTextField("Empleado");
        form.add(new JLabel("Nombre:")); form.add(txtNombre);
        form.add(new JLabel("Apellido:")); form.add(txtApellido);
        form.add(new JLabel("DNI:")); form.add(txtDni);
        form.add(new JLabel("Rol:")); form.add(txtRol);
        JButton btnAlta = new JButton("Alta Empleado"); estilizarBoton(btnAlta, new Color(76,175,80));
        form.add(new JLabel("")); form.add(btnAlta);
        content.add(form);

        JPanel listPanel = new JPanel(new BorderLayout()); listPanel.setBackground(CARD);
        listModel = new DefaultListModel<>(); listEmpleados = new JList<>(listModel); listEmpleados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listPanel.add(new JScrollPane(listEmpleados), BorderLayout.CENTER);
        JPanel ops = new JPanel(new FlowLayout(FlowLayout.RIGHT)); ops.setBackground(CARD);
        JButton btnAsignar = new JButton("Asignar Tarea"); JButton btnBaja = new JButton("Baja");
        estilizarBoton(btnAsignar, new Color(33,150,243)); estilizarBoton(btnBaja, new Color(244,67,54));
        ops.add(btnAsignar); ops.add(btnBaja); listPanel.add(ops, BorderLayout.SOUTH);
        content.add(listPanel);

        btnAlta.addActionListener(e -> {
            String nombre = txtNombre.getText().trim(); String apellido = txtApellido.getText().trim(); String dni = txtDni.getText().trim(); String rol = txtRol.getText().trim();
            if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty()) { showMsg(this, "Complete nombre, apellido y dni."); return; }
            Empleado emp = new Empleado(nombre, apellido, dni, rol); sistema.registrarEmpleado(emp); refreshLista(); showMsg(this, "Empleado registrado: " + emp);
            txtNombre.setText(""); txtApellido.setText(""); txtDni.setText("");
        });

        btnAsignar.addActionListener(e -> {
            Empleado sel = listEmpleados.getSelectedValue(); if (sel == null) { showMsg(this, "Seleccione un empleado."); return; }
            String descripcion = JOptionPane.showInputDialog(this, "Descripción de la tarea:");
            if (descripcion == null || descripcion.trim().isEmpty()) return;
            // Para persistir, conviene crear la tarea en Sistema (crearMantenimiento) y luego asignar.
            showMsg(this, "Nota: para persistir esta asignación, implementar crearMantenimiento(...) + asignarTareaMantenimiento(...) en Sistema.");
        });

        btnBaja.addActionListener(e -> {
            Empleado sel = listEmpleados.getSelectedValue(); if (sel == null) { showMsg(this, "Seleccione empleado."); return; }
            int opt = JOptionPane.showConfirmDialog(this, "Confirmar baja de " + sel + " ?", "Baja", JOptionPane.YES_NO_OPTION);
            if (opt != JOptionPane.YES_OPTION) return;
            boolean ok = sistema.eliminarEmpleado(sel.getIdEmpleado());
            if (ok) { showMsg(this, "Empleado dado de baja."); refreshLista(); } else showMsg(this, "No se pudo dar de baja.");
        });

        refreshLista();
    }

    private void refreshLista() {
        listModel.clear(); List<Empleado> es = sistema.getListaEmpleados(); for (Empleado e : es) listModel.addElement(e);
    }
}
