// VentanaVentaTickets.java
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaVentaTickets extends BaseUI {
    private Sistema sistema;
    private JComboBox<Partido> comboPartidos;

    public VentanaVentaTickets(Sistema sistema) { super("Venta de Tickets"); this.sistema = sistema; initUI(); setVisible(true); }

    private void initUI() {
        JPanel main = new JPanel(new BorderLayout(10,10)); main.setBackground(BG); main.setBorder(BorderFactory.createEmptyBorder(12,12,12,12)); add(main);
        JPanel card = new JPanel(new BorderLayout(10,10)); card.setBackground(CARD); card.setBorder(BorderFactory.createEmptyBorder(12,12,12,12)); main.add(card);
        JLabel title = new JLabel("Venta de Tickets"); title.setFont(new Font("Segoe UI", Font.BOLD, 18)); card.add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(0,2,10,10)); center.setBackground(CARD); card.add(center, BorderLayout.CENTER);
        comboPartidos = new JComboBox<>(); refreshPartidos();
        JTextField txtNombre = new JTextField(); JTextField txtApellido = new JTextField(); JTextField txtDni = new JTextField(); JTextField txtEmail = new JTextField(); JTextField txtEdad = new JTextField(); JCheckBox chkSocio = new JCheckBox("Es socio"); JTextField txtTutor = new JTextField();

        center.add(new JLabel("Partido:")); center.add(comboPartidos);
        center.add(new JLabel("Nombre:")); center.add(txtNombre);
        center.add(new JLabel("Apellido:")); center.add(txtApellido);
        center.add(new JLabel("DNI:")); center.add(txtDni);
        center.add(new JLabel("Email:")); center.add(txtEmail);
        center.add(new JLabel("Edad:")); center.add(txtEdad);
        center.add(chkSocio); center.add(new JLabel(""));
        center.add(new JLabel("Tutor (si menor):")); center.add(txtTutor);

        JButton btnVender = new JButton("Vender Ticket"); estilizarBoton(btnVender, new Color(255,152,0)); card.add(btnVender, BorderLayout.SOUTH);

        btnVender.addActionListener(e -> {
            try {
                Partido partido = (Partido) comboPartidos.getSelectedItem();
                if (partido == null) { showMsg(this, "Seleccione un partido."); return; }
                String nombre = txtNombre.getText().trim(); String apellido = txtApellido.getText().trim(); String dni = txtDni.getText().trim();
                String email = txtEmail.getText().trim(); int edad = Integer.parseInt(txtEdad.getText().trim()); boolean esSocio = chkSocio.isSelected();
                String tutor = edad < 18 ? txtTutor.getText().trim() : null;
                // Validacion email simple
                if (!email.matches("^\\S+@\\S+\\.\\S+$")) { showMsg(this, "Email inválido."); return; }
                Audiencia a = new Audiencia(nombre, apellido, dni, email, edad, esSocio, tutor);
                sistema.registrarAudiencia(a);
                Ticket t = sistema.venderTicket(partido, a);
                showMsg(this, "Ticket vendido correctamente. Precio: $" + t.getPrecioPagado());
            } catch (NumberFormatException ex) { showMsg(this, "Edad inválida."); }
            catch (MenorSinTutorException | TicketDuplicadoException ex) { showMsg(this, "⚠️ " + ex.getMessage()); }
            catch (Exception ex) { showMsg(this, "Error al vender ticket: " + ex.getMessage()); }
        });
    }

    private void refreshPartidos() {
        comboPartidos.removeAllItems(); List<Partido> ps = sistema.getListaPartidos(); for (Partido p : ps) comboPartidos.addItem(p);
    }
}
