package headers;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Screen extends JFrame {

    private Image image;
    private List<TextPixel> textPixels = new ArrayList<>();

    private Scene scene;

    public Screen(String imagePath) {
        this.image = new ImageIcon(imagePath).getImage();
        initUI();
    }

    public Screen() {
        initUI();
    }

    public void addTextAtPixel(String text, int x, int y) {
        textPixels.add(new TextPixel(text, x, y));
        repaint();
    }

    public void updateTextPosition(String text, int x, int y) {
        for (TextPixel textPixel : textPixels) {
            if (textPixel.text.equals(text)) {
                textPixel.x = x;
                textPixel.y = y;
                repaint();
                break;
            }
        }
    }

    private void initUI() {
        setSize(500, 700);
        setResizable(false);

        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (image != null) {
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
                }
                for (TextPixel textPixel : textPixels) {
                    drawText(g, textPixel);
                }
            }
        };

        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void drawText(Graphics g, TextPixel textPixel) {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/OLDENGL.TTF")).deriveFont(12f);
            g.setFont(customFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(30f)); // float value
        g.drawString(textPixel.text, textPixel.x, textPixel.y);
    }

    public void clearTextPixels() {
        textPixels.clear();
        repaint();
    }

    public void clearScreen() {
        this.image = null;
        clearTextPixels();
    }

    public void setCurentScene(Scene _scene) {
        this.scene = _scene;
    }

    public void displayCurentScene() {
        this.scene.display();
    }

    public void listenToInput() {
        this.scene.listenToInput();
    }

}
