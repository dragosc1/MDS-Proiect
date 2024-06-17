package headers.MainLobby;

import headers.Player;
import headers.Scene;
import headers.Screen;
import headers.Utility.Quests;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class TierUp implements Scene {
    private final Screen window; // Reference to the game window/screen where the Tier Up scene is displayed

    private GameLobby lobby; // Reference to the game lobby scene, used for transitioning back to the lobby
    private Guild qguild; // Reference to the guild scene for transitioning to the guild

    private KeyListener tierUpListener; // Key listener to capture user input for the Tier Up scene

    private ImageIcon back, wood, bar, ic0; // Icons for various visual elements
    private ImageIcon holder, ic1, ic2; // Icons for various visual elements

    private Boolean checkA; // Indicator for whether to display the completion popup

    // Constructor initializes the Tier Up scene with required assets
    public TierUp(Screen window, GameLobby _lobby) {
        this.window = window;
        this.lobby = _lobby;
        this.window.setBackground("WHITE");
        checkA = false;
        loadAssets();
    }

    // Method to modify colors of an ImageIcon
    public static ImageIcon modifyColors(ImageIcon icon) {
        Image image = icon.getImage();
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        // Modify colors
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                int rgb = bufferedImage.getRGB(x, y);
                int alpha = (rgb >> 24) & 0xFF;
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                // Check if pixel is white
                if (red == 255 && green == 255 && blue == 255) {
                    red = 0; // Set red component to 0
                    green = 0; // Set green component to 0
                    blue = 0; // Set blue component to 0
                }
                // Check if pixel is black
                else if (red == 0 && green == 0 && blue == 0) {
                    green = 255; // Set green component to max
                }

                int newRGB = (alpha << 24) | (red << 16) | (green << 8) | blue;
                bufferedImage.setRGB(x, y, newRGB);
            }
        }

        return new ImageIcon(bufferedImage);
    }

    // Load images and icons required for the Tier Up scene
    void loadAssets() {
        back = new ImageIcon("assets/Guild/challangesbg.png");
        wood = new ImageIcon("assets/Market/woodytexturebackground.jpg");
        bar  = new ImageIcon("assets/Market/BrownBlackGround.png");
        ic0 = new ImageIcon("assets/Guild/Cicon.png");
        ic1 = new ImageIcon("assets/Guild/questsicon.png");
        ic2 = new ImageIcon("assets/Guild/challangesicon.png");
        ic2 = modifyColors(ic2);
        holder = new ImageIcon("assets/Market/itemholder.png");
    }

    // Extract number from a string
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

        return number;
    }

    // Remove the KeyAdapter from the window
    private void removeKeyAdaptor() {
        window.removeKeyListener(tierUpListener);
    }

    // Method to transition back to the lobby
    private void enterLobby() {
        removeKeyAdaptor();
        window.clearScreen();
        window.setBackground("assets/Game Menu/GameMenuBackGround.png");
        window.setCurentScene(lobby);
    }

    // Transition to the guild scene
    void enterGuild() {
        removeKeyAdaptor();
        removeKeyAdaptor();
        window.clearScreen();
        qguild = new Guild(window, lobby);
        window.setCurentScene(qguild);
    }

    // Draw all elements of the Tier Up scene on the window
    synchronized void drawEverything() {
        // Draw background images and icons
        window.addImageAtPixel(0, 40, 500, 400, back.getImage());
        window.addImageAtPixel(0, 0, 490, 40, bar.getImage());
        window.addImageAtPixel(5, 0, 40, 40, ic0.getImage());
        window.addTextAtPixel("Tier Up", 55, 30, "WHITE", 25f);
        window.addImageAtPixel(205, 0, 40, 40, ic1.getImage());
        window.addImageAtPixel(305, 0, 40, 40, ic2.getImage());
        window.addImageAtPixel(0, 400, 500, 400, wood.getImage());

        // Display current tier progress
        int yValue = 510;
        window.addImageAtPixel(10, yValue, 470, 80, holder.getImage());
        window.addTextAtPixel(Quests.getInstance().getTierUp(), 80, yValue + 45,  "WHITE", 25f);

        if (checkA) {
            // Display completion popup if applicable
            if (Player.getInstance().getCurrPlayerTier() == 10) return;
            window.addPopUpAtPixel(0, 40, 490, 400, holder.getImage());
            window.addPopUpTextAtPixel("You have completed", 150, 90, "WHITE", 25f);

            int poss = Player.getInstance().getCurrPlayerTier() - 1;
            int pos = Quests.getInstance().getWhatType(poss);
            int nr1 = Player.getInstance().getDungeon(pos);
            int nr2 = getNr(Quests.getInstance().getTierUp());
            String color = "RED";
            if (nr1 >= nr2 / 2) color = "YELLOW";
            if (nr1 == nr2) color = "GREEN";
            window.addPopUpTextAtPixel(nr1 + "     out of     " + nr2, 150, 120, color, 25f);
            if (nr1 >= nr2) {
                window.addPopUpTextAtPixel("Complete", 200, 250, "GREEN", 25f);
            }
        }
    }

    // Display the Tier Up scene
    @Override
    public void display() {
        drawEverything();
    }

    // Listen for user input
    @Override
    public void listenToInput() {
        tierUpListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Handle Escape key press to return to the lobby or exit popups
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (checkA) {
                        checkA = false;
                        return;
                    }

                    enterLobby();
                }

                // Handle Enter key press to confirm actions
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (!checkA) {
                        if (Player.getInstance().getCurrPlayerTier() == 10)
                            return;
                        checkA = true;
                        return;
                    }

                    if (checkA) {
                        // Handle completion and progression when the confirmation popup is displayed
                        if (Player.getInstance().getCurrPlayerTier() == 10)
                            return;

                        int poss = Player.getInstance().getCurrPlayerTier() - 1;
                        int pos = Quests.getInstance().getWhatType(poss);
                        int nr1 = Player.getInstance().getDungeon(pos);
                        int nr2 = getNr(Quests.getInstance().getTierUp());
                        if (nr1 >= nr2) {
                            Player.getInstance().progressCurrPlayerTier();
                            Quests.getInstance().progressTier();
                            if (Player.getInstance().getCurrPlayerTier() % 2 == 1) {
                                Player.getInstance().updateSInventorySpace();
                                Quests.getInstance().progressLimit();
                            }

                            checkA = false;
                            return;
                        }
                    }

                }

                // Handle '1' key press to enter the guild
                if (e.getKeyCode() == KeyEvent.VK_1) {
                    enterGuild();
                }
            }
        };
        window.addKeyListener(tierUpListener);
    }
}
