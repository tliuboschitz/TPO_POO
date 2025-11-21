// BaseUI.java
import javax.swing.*;
import java.awt.*;

public abstract class BaseUI extends JFrame {
    protected Color BG = new Color(245, 247, 252);
    protected Color CARD = Color.WHITE;

    public BaseUI(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
    }

    protected void estilizarBoton(JButton boton, Color colorFondo) {
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
            @Override public void mouseEntered(java.awt.event.MouseEvent e) { boton.setBackground(colorFondo.darker()); }
            @Override public void mouseExited(java.awt.event.MouseEvent e) { boton.setBackground(colorFondo); }
        });
    }

    protected void showMsg(JFrame parent, String msg) { JOptionPane.showMessageDialog(parent, msg); }
}
