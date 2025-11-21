// VentanaReporteIngresos.java
import javax.swing.*;
import java.awt.*;

public class VentanaReporteIngresos extends BaseUI {
    private Sistema sistema;
    private JLabel lblResultado;

    public VentanaReporteIngresos(Sistema sistema) { super("Reporte de Ingresos"); this.sistema = sistema; initUI(); setVisible(true); }

    private void initUI() {
        JPanel main = new JPanel(new BorderLayout(10,10)); main.setBackground(BG); main.setBorder(BorderFactory.createEmptyBorder(12,12,12,12)); add(main);
        JPanel card = new JPanel(new BorderLayout(10,10)); card.setBackground(CARD); card.setBorder(BorderFactory.createEmptyBorder(12,12,12,12)); main.add(card);
        JLabel title = new JLabel("Reporte de Ingresos Totales"); title.setFont(new Font("Segoe UI", Font.BOLD, 18)); card.add(title, BorderLayout.NORTH);
        lblResultado = new JLabel("Presione 'Actualizar'", SwingConstants.CENTER); lblResultado.setFont(new Font("Segoe UI", Font.PLAIN, 16)); card.add(lblResultado, BorderLayout.CENTER);
        JButton btnActualizar = new JButton("Actualizar"); estilizarBoton(btnActualizar, new Color(33,150,243)); card.add(btnActualizar, BorderLayout.SOUTH);
        btnActualizar.addActionListener(e -> { double total = sistema.generarReporteIngresos(); lblResultado.setText("Total recaudado: $" + total); });
    }
}
