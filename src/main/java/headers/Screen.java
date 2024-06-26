package headers;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Class representing the window and managing all that it is displayed
public class Screen extends JFrame {

    private Image image;
    private final List<TextPixel> textPixels = Collections.synchronizedList(new ArrayList<>());
    private final List<Square> squares = Collections.synchronizedList((new ArrayList<>()));
    private final List<Button> buttons = Collections.synchronizedList((new ArrayList<>()));
    private final List<ImageInfo> images = Collections.synchronizedList((new ArrayList<>()));
    private final List<ImageInfo> popups = Collections.synchronizedList((new ArrayList<>()));
    private final List<TextPixel> textPixelsPopUp = Collections.synchronizedList(new ArrayList<>());
    private final List<ImageInfo> checkups = Collections.synchronizedList((new ArrayList<>()));
    private final List<TextPixel> textPixelscheckups = Collections.synchronizedList(new ArrayList<>());

    // Current scene
    private Scene scene;

    // Constructor
    public Screen(String imagePath) {
        this.image = new ImageIcon(imagePath).getImage();
        initUI();
        setLocationRelativeTo(null);
    }

    // First initialize the ui
    public Screen() {
        initUI();
    }

    // Add text at some point with different variations (overloading)
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

    public void addPopUpTextAtPixel(String text, int x, int y, String color, float fontSize) {
        if (color == null || color.isEmpty()) {
            color = "WHITE"; // Set default color to "WHITE"
        }
        if (fontSize == 0f)
            fontSize = 30f;
        synchronized (textPixelsPopUp) {
            textPixelsPopUp.add(new TextPixel(text, x, y, color, fontSize));
        }
        repaint();
    }

    public void addCheckUpText(String text, int x, int y, String color, float fontSize) {
        if (color == null || color.isEmpty()) {
            color = "WHITE"; // Set default color to "WHITE"
        }
        if (fontSize == 0f)
            fontSize = 30f;
        synchronized (textPixelscheckups) {
            textPixelscheckups.add(new TextPixel(text, x, y, color, fontSize));
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

    // Adding a square at some position
    public void addSquareAtPixel(int x, int y, String color, int dimX, int dimY) {
        squares.add(new Square(x, y, color, dimX, dimY));
        repaint();
    }

    // Adding an image at some position
    public void addImageAtPixel(int x, int y, int dimX, int dimY, Image image) {
        images.add(new ImageInfo(image, x, y, dimX, dimY));
        repaint();
    }

    // Adding a popup at some position
    public void addPopUpAtPixel(int x, int y, int dimX, int dimY, Image image) {
        popups.add(new ImageInfo(image, x, y, dimX, dimY));
        repaint();
    }

    // Adding checkups at some position
    public void addCheckUpAtPixel(int x, int y, int dimX, int dimY, Image image) {
        checkups.add(new ImageInfo(image, x, y, dimX, dimY));
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

    // Initialize the ui and synchronize all arrays
    private void initUI() {
        setSize(500, 700);
        setResizable(false);
        setIconImage(new ImageIcon("assets/Market/Cicon.png").getImage());

        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                synchronized (textPixels) {
                    synchronized (squares) {
                        synchronized (buttons) {
                            synchronized (images) {
                                synchronized (popups) {
                                    synchronized (textPixelsPopUp) {
                                        synchronized (checkups) {
                                            synchronized (textPixelscheckups) {
                                                if (image != null) {
                                                    g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
                                                }

                                                // draw Buttons
                                                for (Button button : buttons)
                                                    drawButton(g, button);
                                                for (Square square : squares) {
                                                    drawSquare(g, square);
                                                }
                                                for (ImageInfo image : images) {
                                                    drawImageInfo(g, image);
                                                }
                                                for (TextPixel textPixel : textPixels) {
                                                    drawText(g, textPixel);
                                                }
                                                for (ImageInfo popup : popups) {
                                                    drawImageInfo(g, popup);
                                                }
                                                for (TextPixel textPixel : textPixelsPopUp) {
                                                    drawText(g, textPixel);
                                                }

                                                for (ImageInfo popup : checkups) {
                                                    drawImageInfo(g, popup);
                                                }
                                                for (TextPixel textPixel : textPixelscheckups) {
                                                    drawText(g, textPixel);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        };

        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // drawing the text
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

    // Drawing the rectangles
    private void drawSquare(Graphics g, Square square) {
        g.setColor(ColorConverter.getColorFromString(square.getColor())); // Set the color
        g.fillRect(square.getX(), square.getY(), square.getDimX(), square.getDimY()); // Draw the filled square at the specified position and dimensions
    }

    // Drawing the buttons
    void drawButton(Graphics g, Button button) {
        g.drawImage(new ImageIcon(button.getImage()).getImage(), button.getX(), button.getY(), button.getDimX(), button.getDimY(), null);
    }

    // Drawing the images
    void drawImageInfo(Graphics g, ImageInfo image) {
        g.drawImage(image.getImage(), image.getX(), image.getY(), image.getDimX(), image.getDimY(), null);
    }

    // Helper functions
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

    public void clearImageInfo() {
        images.clear();
        repaint();
    }

    void clearPopUps() {
        popups.clear();
        textPixelsPopUp.clear();
        repaint();
    }

    void clearCheckUps() {
        checkups.clear();
        textPixelscheckups.clear();
        repaint();
    }

    public void clearScreen() {
        clearTextPixels();
        clearSquares();
        clearButtons();
        clearImageInfo();
        clearPopUps();
        clearCheckUps();
    }

    // Setting the current scene
    public void setCurentScene(Scene _scene) {
        this.scene = _scene;
        listenToInput();
    }

    // Display teh current scene
    public void displayCurentScene() {
        this.scene.display();
    }

    // Listen to input
    public void listenToInput() {
        this.scene.listenToInput();
    }

    // Setting the background with different variations (overloading)
    public void setBackground(String imagePath) {
        this.image = new ImageIcon(imagePath).getImage();
        initUI();
    }

    public void setBackground(ImageIcon img) {
        this.image = img.getImage();
        initUI();
    }
}
