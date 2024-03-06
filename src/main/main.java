import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // new window
        JFrame frame = new JFrame();
        frame.setSize(500, 700);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a custom JPanel
        JPanel panel = new JPanel() {
            // Override the paintComponent method to draw the background image
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("../../assets/mm.jpeg"); // Load the image
                Image image = imageIcon.getImage(); // Transform it 
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this); // Draw the image to fill the panel
            }
        };

        panel.setLayout(null);
        
        // Jpanel --> Jframe
        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}
