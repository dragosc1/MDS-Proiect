package headers;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Screen extends JFrame {

    private Image image;
    private final List<TextPixel> textPixels = Collections.synchronizedList(new ArrayList<>());

    private Scene scene;

    public Screen(String imagePath) {
        this.image = new ImageIcon(imagePath).getImage();
        initUI();
    }

    public Screen() {
        initUI();
    }

    public void addTextAtPixel(String text, int x, int y, String color, float fontSize) {
        if (color == null || color.isEmpty()) {
            color = "WHITE"; // Set default color to "WHITE"
        }
        if (fontSize == 0f)
            fontSize = 30f;
        synchronized (textPixels) {
            textPixels.add(new TextPixel(text, x, y, color, fontSize));
        }
        repaint();
    }

    public void addTextAtPixel(String text, int x, int y, String color, float fontSize, String fontName) {
        if (color == null || color.isEmpty()) {
            color = "WHITE"; // Set default color to "WHITE"
        }
        if (fontSize == 0f)
            fontSize = 30f;
        synchronized (textPixels) {
            textPixels.add(new TextPixel(text, x, y, color, fontSize, fontName));
        }
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
                synchronized (textPixels) {
                    if (image != null) {
                        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
                    }
                    for (TextPixel textPixel : textPixels) {
                        drawText(g, textPixel);
                    }
                }
            }
        };

        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void drawText(Graphics g, TextPixel textPixel) {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/" + textPixel.fontName + ".TTF")).deriveFont(textPixel.fontSize);
            g.setFont(customFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        g.setColor(ColorConverter.getColorFromString(textPixel.color));
        g.setFont(g.getFont().deriveFont(textPixel.fontSize));
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
        listenToInput();
    }

    public void displayCurentScene() {
        this.scene.display();
    }

    public void listenToInput() {
        this.scene.listenToInput();
    }

    public void setBackground(String imagePath) {
        this.image = new ImageIcon(imagePath).getImage();
        initUI();
    }
}
