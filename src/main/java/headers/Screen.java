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
    private final List<Square> squares = Collections.synchronizedList((new ArrayList<>()));
    private final List<Button> buttons = Collections.synchronizedList((new ArrayList<>()));
    private final List<ImageInfo> images = Collections.synchronizedList((new ArrayList<>()));

    private Scene scene;

    public Screen(String imagePath) {
        this.image = new ImageIcon(imagePath).getImage();
//        ImageIcon image = new ImageIcon("assets/Game Menu/BlackSmithIcon.png");
//        images.add(new ImageInfo(image.getImage(), 0, 0, 490, 40));
        ImageIcon image2 = new ImageIcon("assets/Game Menu/CasleIcon.png");
        images.add(new ImageInfo(image2.getImage(), 0, 0, 30, 30));
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

    public void addSquareAtPixel(int x, int y, String color, int dimX, int dimY) {
        squares.add(new Square(x, y, color, dimX, dimY));
        repaint();
    }

    public void addImageAtPixel(int x, int y, int dimX, int dimY, Image image) {
        images.add(new ImageInfo(image, x, y, dimX, dimY));
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
                    // draw Buttons
                    for (Button button : buttons)
                        drawButton(g, button);
                    for (TextPixel textPixel : textPixels) {
                        drawText(g, textPixel);
                    }
                    for (Square square : squares) {
                        drawSquare(g, square);
                    }
                    for (ImageInfo image : images) {
                        drawImageInfo(g, image);
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

    private void drawSquare(Graphics g, Square square) {
        g.setColor(ColorConverter.getColorFromString(square.getColor())); // Set the color
        g.fillRect(square.getX(), square.getY(), square.getDimX(), square.getDimY()); // Draw the filled square at the specified position and dimensions
    }

    void drawButton(Graphics g, Button button) {
        g.drawImage(new ImageIcon(button.getImage()).getImage(), button.getX(), button.getY(), button.getDimX(), button.getDimY(), null);
    }

    void drawImageInfo(Graphics g, ImageInfo image) {
        g.drawImage(image.getImage(), image.getX(), image.getY(), image.getDimX(), image.getDimY(), null);
    }

    public void addButton(int x, int y, int dimX, int dimY, boolean active) {
        buttons.add(new Button("", active, x, y, dimX, dimY));
        repaint();
    }

    public void clearButtons() {
        buttons.clear();
        repaint();
    }

    public void clearTextPixels() {
        textPixels.clear();
        repaint();
    }

    public void clearSquares() {
        squares.clear();
        repaint();
    }

    public void clearScreen() {
        clearTextPixels();
        clearSquares();
        clearButtons();
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

    public void setBackground(ImageIcon img) {
        this.image = img.getImage();
        initUI();
    }
}
