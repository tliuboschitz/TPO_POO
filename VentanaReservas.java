// VentanaReservas.java
import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class VentanaReservas extends BaseUI {
    private Sistema sistema;
    private DefaultListModel<Reserva> listModel;
    private JList<Reserva> listReservas;

    public VentanaReservas(Sistema sistema) { super("Gestión de Reservas"); this.sistema = sistema; initUI(); setVisible(true); }

    private void initUI() {
        JPanel main = new JPanel(new BorderLayout(10,10)); main.setBackground(BG); main.setBorder(BorderFactory.createEmptyBorder(12,12,12,12)); add(main);
        JPanel card = new JPanel(new BorderLayout(8,8)); card.setBackground(CARD); card.setBorder(BorderFactory.createEmptyBorder(12,12,12,12)); main.add(card, BorderLayout.CENTER);
        JLabel title = new JLabel("ABM - Reservas"); title.setFont(new Font("Segoe UI", Font.BOLD, 16)); card.add(title, BorderLayout.NORTH);
        JPanel content = new JPanel(new GridLayout(1,2,10,10)); content.setBackground(CARD); card.add(content, BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(0,2,8,8)); form.setBackground(CARD);
        JComboBox<Cancha> comboCanchas = new JComboBox<>(); for (Cancha c : sistema.getListaCanchas()) comboCanchas.addItem(c);
        JTextField txtHora = new JTextField("20:00");
        JButton btnCrear = new JButton("Crear Reserva"); estilizarBoton(btnCrear, new Color(76,175,80));
        form.add(new JLabel("Cancha:")); form.add(comboCanchas);
        form.add(new JLabel("Hora:")); form.add(txtHora);
        form.add(new JLabel("Fecha:")); form.add(new JLabel("(hoy por defecto)"));
        form.add(new JLabel("")); form.add(btnCrear);
        content.add(form);

        JPanel listPanel = new JPanel(new BorderLayout(6,6)); listPanel.setBackground(CARD);
        listModel = new DefaultListModel<>(); listReservas = new JList<>(listModel); listReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listPanel.add(new JScrollPane(listReservas), BorderLayout.CENTER);
        JPanel ops = new JPanel(new FlowLayout(FlowLayout.RIGHT)); ops.setBackground(CARD);
        JButton btnCancelar = new JButton("Cancelar Reserva"); estilizarBoton(btnCancelar, new Color(244,67,54));
        ops.add(btnCancelar); listPanel.add(ops, BorderLayout.SOUTH);
        content.add(listPanel);

        btnCrear.addActionListener(e -> {
            try {
                Cancha cancha = (Cancha) comboCanchas.getSelectedItem();
                if (cancha == null) { showMsg(this, "Seleccione una cancha."); return; }
                String hora = txtHora.getText().trim();
                Date fecha = new Date();
                Alquilador demo = new Alquilador("Demo","Cliente",000);
                sistema.registrarAlquilador(demo);
                Reserva r = sistema.crearReserva(demo, cancha, fecha, hora);
                showMsg(this, "Reserva creada: " + r);
                refreshLista();
            } catch (CanchaNoDisponibleException ex) { showMsg(this, "⚠️ " + ex.getMessage()); }
              catch (Exception ex) { showMsg(this, "Error creando reserva: " + ex.getMessage()); }
        });

        btnCancelar.addActionListener(e -> {
            Reserva sel = listReservas.getSelectedValue(); if (sel == null) { showMsg(this, "Seleccione una reserva."); return; }
            try { sistema.cancelarReserva(sel.getIdReserva()); showMsg(this, "Reserva cancelada: " + sel.getIdReserva()); refreshLista(); }
            catch (Exception ex) { showMsg(this, "Error al cancelar: " + ex.getMessage()); }
        });

        refreshLista();
    }

    private void refreshLista() {
        listModel.clear(); List<Reserva> rs = sistema.getListaReservas(); for (Reserva r : rs) listModel.addElement(r);
    }
}
