package headers.MainLobby; // Declares the package for the class
import headers.Player;
import headers.Scene;
import headers.Screen;
import headers.Utility.Quests;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;
import java.awt.image.*;

public class Guild implements Scene {
    private final Screen window; // The window where the scene is displayed
    private GameLobby lobby; // Reference to the GameLobby
    private TierUp qtierup; // Reference to the TierUp scene
    private KeyListener GuildListener; // Key listener for handling user input
    private ImageIcon back, wood, bar, ic0; // Image icons for various graphics
    private ImageIcon holder, ic1, ic2; // More image icons
    private Boolean checkA, checkB; // Flags for state management
    private Integer positionH, posCA, posCB; // Positions for selection and cursor

    public Guild(Screen window, GameLobby _lobby) {
        this.window = window;
        this.lobby = _lobby;
        this.window.setBackground("WHITE"); // Set the background color of the window
        positionH = posCA = -1; // Initialize positions
        checkA = checkB = false; // Initialize flags
        loadAssets(); // Load all required assets
    }

    // Method to modify colors of an ImageIcon
    public static ImageIcon modifyColors(ImageIcon icon) {
        Image image = icon.getImage();
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        // Modify colors pixel by pixel
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                int rgb = bufferedImage.getRGB(x, y);
                int newRGB = getNewRGB(rgb); // Get the new RGB value
                bufferedImage.setRGB(x, y, newRGB); // Set the new RGB value
            }
        }

        return new ImageIcon(bufferedImage); // Return the modified icon
    }

    // Helper method to get the new RGB value based on certain conditions
    private static int getNewRGB(int rgb) {
        int alpha = (rgb >> 24) & 0xFF;
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        // If the pixel is white, change it to black
        if (red == 255 && green == 255 && blue == 255) {
            red = 0;
            green = 0;
            blue = 0;
        }
        // If the pixel is black, change it to green
        else if (red == 0 && green == 0 && blue == 0) {
            green = 255;
        }

        int newRGB = (alpha << 24) | (red << 16) | (green << 8) | blue;
        return newRGB;
    }

    // Method to load all required assets
    void loadAssets() {
        back = new ImageIcon("assets/Guild/guild receptionist.png");
        wood = new ImageIcon("assets/Market/woodytexturebackground.jpg");
        bar  = new ImageIcon("assets/Market/BrownBlackGround.png");
        ic0 = new ImageIcon("assets/Guild/Cicon.png");
        ic1 = new ImageIcon("assets/Guild/questsicon.png");
        ic1 = modifyColors(ic1); // Modify the colors of the icon
        ic2 = new ImageIcon("assets/Guild/challangesicon.png");
        holder = new ImageIcon("assets/Market/itemholder.png");
    }

    // Method to remove the key listener
    private void removeKeyAdaptor() {
        window.removeKeyListener(GuildListener);
    }

    // Method to enter the lobby scene
    private void enterLobby() {
        removeKeyAdaptor();
        window.clearScreen();
        window.setBackground("assets/Game Menu/GameMenuBackGround.png");
        window.setCurentScene(lobby);
    }

    // Method to enter the TierUp scene
    private void enterTierUp() {
        removeKeyAdaptor();
        window.clearScreen();
        qtierup = new TierUp(window, lobby);
        window.setCurentScene(qtierup);
    }

    // Method to extract a number from a string
    private static int getNr(String str) {
        int number = 0;
        boolean foundNumber = false;

        for (char c : str.toCharArray()) {
            if (c >= '0' && c <= '9') {
                number = (number * 10) + (c - '0');
                foundNumber = true;
            } else {
                if (foundNumber) {
                    break;
                }
            }
        }

        return number; // Return the extracted number
    }

    // Method to draw all the elements on the screen
    void drawEverything() {
        window.addImageAtPixel(0, 40, 500, 400, back.getImage());
        window.addImageAtPixel(0, 0, 490, 40, bar.getImage());
        window.addImageAtPixel(5, 0, 40, 40, ic0.getImage());
        window.addTextAtPixel("Guild", 55, 30, "WHITE", 25f);
        window.addImageAtPixel(205, 0, 40, 40, ic1.getImage());
        window.addImageAtPixel(305, 0, 40, 40, ic2.getImage());
        window.addImageAtPixel(0, 400, 500, 400, wood.getImage());

        int yValue = 410;
        window.addImageAtPixel(10, yValue, 470, 80, holder.getImage());
        char l1 = Quests.getInstance().getQuest(0).charAt(0);
        window.addTextAtPixel((positionH == 0 ? ">" : "") + Quests.getInstance().getQuest(0), l1 == 'V' ? 140 : 180, yValue + 45, "WHITE", 25f);

        yValue += 100;
        window.addImageAtPixel(10, yValue, 470, 80, holder.getImage());
        char l2 = Quests.getInstance().getQuest(1).charAt(0);
        window.addTextAtPixel((positionH == 1 ? ">" : "") + Quests.getInstance().getQuest(1), l2 == 'V' ? 140 : 180, yValue + 45, "WHITE", 25f);

        if (checkA) {
            char lA = Quests.getInstance().getQuest(positionH).charAt(0);
            window.addPopUpAtPixel(0, 40, 490, 400, holder.getImage());
            window.addPopUpTextAtPixel("You have " + (lA != 'V' ? "killed" : "visited"), 150, 90, "WHITE", 25f);
            int nr1 = Quests.getInstance().getProgress(positionH);
            int nr2 = getNr(Quests.getInstance().getQuest(positionH));
            String color = "RED";
            if (nr1 >= nr2 / 2) color = "YELLOW";
            if (nr1 == nr2) color = "GREEN";
            window.addPopUpTextAtPixel(nr1 + "     out of     " + nr2, 150, 120, color, 25f);
            window.addPopUpTextAtPixel((posCA == 0 ? ">" : "") + "Discard", 200, 200, "RED", 25f);
            if (nr1 >= nr2) {
                window.addPopUpTextAtPixel((posCA == 1 ? ">" : "") + "Complete", 200, 250, "GREEN", 25f);
            }
        }

        if (checkB) {
            window.addCheckUpAtPixel(0, 40, 490, 400, holder.getImage());
            window.addCheckUpText("Discard Quest? ", 150, 90, "WHITE", 25f);
            window.addCheckUpText((posCB == 0 ? ">" : "") + "No", 20, 220, "RED", 25f);
            window.addCheckUpText((posCB == 1 ? ">" : "") + "Yes", 420, 220, "GREEN", 25f);
        }
    }

    @Override
    public void display() {
        drawEverything(); // Display all elements
    }

    @Override
    public void listenToInput() {
        GuildListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (checkB) {
                        checkB = false;
                    }
                    if (checkA) {
                        checkA = checkB = false;
                        posCA = -1;
                        return;
                    }

                    enterLobby();
                }

                if (e.getKeyCode() == KeyEvent.VK_2) {
                    enterTierUp();
                }

                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (checkB) {
                        return;
                    }

                    if (checkA) {
                        if (posCA == -1) posCA = 0;
                        else posCA = ((posCA - 1) % 2 + 2) % 2;
                        return;
                    }

                    if (positionH == -1) positionH = 0;
                    else positionH = ((positionH - 1) % 2 + 2) % 2;
                }

                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (checkB) {
                        return;
                    }

                    if (checkA) {
                        if (posCA == -1) posCA = 0;
                        else posCA = (posCA + 1) % 2;
                        return;
                    }

                    if (positionH == -1) positionH = 0;
                    else positionH = ((positionH + 1) % 2);
                }

                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (checkB) {
                        posCB = ((posCB - 1) % 2 + 2) % 2;
                        return;
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (checkB) {
                        posCB = (posCB + 1) % 2;
                        return;
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (!checkA && positionH != -1) {
                        checkA = true;
                        return;
                    }

                    if (checkA && !checkB && posCA == 0) {
                        checkB = true;
                        posCB = 0;
                        return;
                    }

                    if (checkA && !checkB && posCA == 1) {
                        int nr = Quests.getInstance().getPrize(positionH);
                        Player.getInstance().subtractFromGold(-nr);
                        Quests.getInstance().setRandomQuest(positionH);
                        checkB = checkA = false;
                        posCA = -1;
                        return;
                    }

                    if (checkB && posCB == 0) {
                        checkB = false;
                        return;
                    }

                    if (checkB && posCB == 1) {
                        checkB = checkA = false;
                        posCA = -1;
                        Quests.getInstance().setRandomQuest(positionH);
                    }
                }
            }
        };
        window.addKeyListener(GuildListener); // Add the key listener to the window
    }
}
