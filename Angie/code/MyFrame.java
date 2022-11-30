
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.*;

public class MyFrame extends JFrame {
    public MyFrame() {
        this.setTitle("Mwanza Baraka Information System");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(750, 600);
        this.setResizable(false);

        ImageIcon icon = new ImageIcon("favicon.png");
        this.setIconImage(icon.getImage());
        this.getContentPane().setBackground(Color.GRAY);

        this.setLocationRelativeTo(null);

    }
}