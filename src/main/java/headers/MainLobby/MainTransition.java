package headers.MainLobby;

import headers.Scene;
import headers.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class MainTransition implements Scene {
    private final Screen window; // The window where the scene will be displayed
    private ImageIcon currentImage; // Current image to display with fading effect
    private Boolean buttonIsVisible; // Boolean to check if the button is visible
    private KeyListener MainTransitionKeyListener; // Key listener for handling user inputs
    private GameLobby gameLobby; // Reference to the game lobby
    private int FirstTime; // Flag to check if it's the first time the game is being played

    // Constructor to initialize the MainTransition scene
    public MainTransition(Screen window, int firstTime) {
        this.buttonIsVisible = false; // Button is not visible initially
        FirstTime = firstTime;// Set the first time flag
        this.window = window;// Set the window
        this.currentImage = makeDarkImage(0.0f); // Create the initial dark image
        this.window.setBackground(this.currentImage); // Set the background image

        int fadingDuration = 2000; // Duration of fading in milliseconds
        int fadingSteps = 100; // Number of steps for fading
        float startDarkness = 0.0f; // Initial darkness level
        float endDarkness = 1.0f; // Final darkness level
        int delay = fadingDuration / fadingSteps; // Delay between each fading step

        Timer timer = new Timer(delay, new ActionListener() {
            float darknessStep = (endDarkness - startDarkness) / fadingSteps; // Step size for darkness change
            float currentDarkness = startDarkness; // Current darkness level

            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if fading is complete
                if (currentDarkness >= endDarkness) {
                    buttonIsVisible = true; // Make the button visible
                    ((Timer) e.getSource()).stop(); // Stop the timer once fading is complete

                } else {
                    // Update the image with current darkness
                    currentImage = makeDarkImage(currentDarkness);
                    window.setBackground(currentImage); // Set the new background
                    currentDarkness += darknessStep; // Increase the darkness level
                }
            }
        });
        timer.start();
    }

    // Method to draw the enter button
    synchronized void drawEnterButton() {
        window.addTextAtPixel(" ", 100, 44, "WHITE", 0f); // Add a placeholder text
        window.addButton(125, 520, 0, 0, true); // Add the button
        // Add button text
        window.addTextAtPixel((FirstTime == 0? "Begin Journey" : "Continue"), (FirstTime == 0? 150 : 200), 568, "GREEN", 30.0f);
    }

    // Method to create a darkened image based on the darkness level
    public ImageIcon makeDarkImage(float darkness) {
        ImageIcon image = new ImageIcon("assets/Game Menu/GameMenuBackGround.png");  // Load the base image
        // Create a buffered image
        BufferedImage bufferedImage = new BufferedImage(image.getIconWidth(), image.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics(); // Get the graphics context
        image.paintIcon(null, g2d, 0, 0); // Paint the original image
        g2d.dispose(); // Dispose the graphics context

        // Iterate over each pixel to adjust the darkness
        for (int y = 0; y < bufferedImage.getHeight(); y++) {
            for (int x = 0; x < bufferedImage.getWidth(); x++) {
                int rgb = bufferedImage.getRGB(x, y); // Get the RGB value of the pixel
                Color c = new Color(rgb); // Create a Color object from the RGB value
                int r = (int) (c.getRed() * darkness); // Adjust the red component
                int g = (int) (c.getGreen() * darkness); // Adjust the green component
                int b = (int) (c.getBlue() * darkness); // Adjust the blue component
                r = Math.min(255, Math.max(0, r)); // Ensure values are within 0-255 range
                g = Math.min(255, Math.max(0, g));
                b = Math.min(255, Math.max(0, b));
                Color newColor = new Color(r, g, b); // Create a new Color object with adjusted values
                bufferedImage.setRGB(x, y, newColor.getRGB());
            }
        }
        return new ImageIcon(bufferedImage); // Return the darkened image
    }

    // Method to remove the key listener
    private void removeKeyAdaptor() {
        window.removeKeyListener(MainTransitionKeyListener);
    }

    // Method to initialize the game lobby
    private void initGameLobby() {
        gameLobby = new GameLobby(window);
    }

    // Method to begin the journey (start the game)
    private void beginJourney() {
        removeKeyAdaptor(); // Remove the key listener
        window.clearScreen(); // Clear the screen
        initGameLobby(); // Initialize the game lobby
        window.setCurentScene(gameLobby); // Set the current scene to the game lobby
    }

    // Method to display the scene
    @Override
    public void display() {
        if (this.buttonIsVisible)
            drawEnterButton(); // Draw the enter button if it is visible
    }

    // Method to listen to user inputs
    @Override
    public void listenToInput() {
        MainTransitionKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Check if enter key is pressed and button is visible
                if (buttonIsVisible && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    buttonIsVisible = false; // Hide the button
                    beginJourney(); // Begin the journey
                }
            }
        };

        // Check if enter key is pressed and button is visible
        window.addKeyListener(MainTransitionKeyListener);
    }
}
