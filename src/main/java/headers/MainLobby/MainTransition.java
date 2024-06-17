package headers.MainLobby;

import headers.Scene;
import headers.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class MainTransition implements Scene {
    private final Screen window;
    private ImageIcon currentImage;
    private Boolean buttonIsVisible;
    private KeyListener MainTransitionKeyListener;
    private GameLobby gameLobby;
    private int FirstTime;

    public MainTransition(Screen window, int firstTime) {
        this.buttonIsVisible = false;
        FirstTime = firstTime;
        this.window = window;
        this.currentImage = makeDarkImage(0.0f);
        this.window.setBackground(this.currentImage);

        int fadingDuration = 2000; // Duration of fading in milliseconds
        int fadingSteps = 100; // Number of steps for fading
        float startDarkness = 0.0f;
        float endDarkness = 1.0f;
        int delay = fadingDuration / fadingSteps;

        Timer timer = new Timer(delay, new ActionListener() {
            float darknessStep = (endDarkness - startDarkness) / fadingSteps;
            float currentDarkness = startDarkness;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentDarkness >= endDarkness) {
                    buttonIsVisible = true;
                    ((Timer) e.getSource()).stop(); // Stop the timer once fading is complete

                } else {
                    currentImage = makeDarkImage(currentDarkness);
                    window.setBackground(currentImage);
                    currentDarkness += darknessStep;
                }
            }
        });
        timer.start();
    }

    synchronized void drawEnterButton() {
        window.addTextAtPixel(" ", 100, 44, "WHITE", 0f);
        window.addButton(125, 520, 0, 0, true);
        window.addTextAtPixel((FirstTime == 0? "Begin Journey" : "Continue"), (FirstTime == 0? 150 : 200), 568, "GREEN", 30.0f);
    }

    public ImageIcon makeDarkImage(float darkness) {
        ImageIcon image = new ImageIcon("assets/Game Menu/GameMenuBackGround.png");
        BufferedImage bufferedImage = new BufferedImage(image.getIconWidth(), image.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        image.paintIcon(null, g2d, 0, 0);
        g2d.dispose();

        for (int y = 0; y < bufferedImage.getHeight(); y++) {
            for (int x = 0; x < bufferedImage.getWidth(); x++) {
                int rgb = bufferedImage.getRGB(x, y);
                Color c = new Color(rgb);
                int r = (int) (c.getRed() * darkness);
                int g = (int) (c.getGreen() * darkness);
                int b = (int) (c.getBlue() * darkness);
                r = Math.min(255, Math.max(0, r)); // Ensure values are within 0-255 range
                g = Math.min(255, Math.max(0, g));
                b = Math.min(255, Math.max(0, b));
                Color newColor = new Color(r, g, b);
                bufferedImage.setRGB(x, y, newColor.getRGB());
            }
        }
        return new ImageIcon(bufferedImage);
    }

    private void removeKeyAdaptor() {
        window.removeKeyListener(MainTransitionKeyListener);
    }

    private void initGameLobby() {
        gameLobby = new GameLobby(window);
    }

    private void beginJourney() {
        removeKeyAdaptor();
        window.clearScreen();
        initGameLobby();
        window.setCurentScene(gameLobby);
    }

    @Override
    public void display() {
        if (this.buttonIsVisible)
            drawEnterButton();
    }


    @Override
    public void listenToInput() {
        MainTransitionKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (buttonIsVisible && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    buttonIsVisible = false;
                    beginJourney();
                }
            }
        };

        window.addKeyListener(MainTransitionKeyListener);
    }
}
