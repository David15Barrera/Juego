package PackTorres;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * @author dav
 */
public class Torre extends JPanel {

    /**
     * Constructor de la clase torre
     */
    public Torre() {
        this.setLayout(null);
    }

    /**
     * Metodo que dibuja la torre en el panel
     * @param g 
     */
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        this.setBackground(Color.YELLOW);

        g.setColor(Color.black);
        
        //base
        g.fillRect(10, 270, 200,5);
        
        //asta
        g.fillRect(110, 30, 5, 240);
        
    }
}
