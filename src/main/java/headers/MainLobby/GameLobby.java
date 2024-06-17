package headers.MainLobby;

import headers.Player;
import headers.Save;
import headers.Scene;
import headers.Screen;

import java.awt.event.*;
import javax.swing.ImageIcon;
import java.util.ArrayList;

public class GameLobby implements Scene {
    private final Screen window;  // The game window where the lobby is displayed
    private KeyListener gameLobbyListener; // Listener for keyboard events
    private int width, height;    // Current width and height positions for navigation
    private int lastw, lasth;     // Last width and height positions for navigation
    private final ArrayList<ArrayList<ImageIcon>> matrix = new ArrayList<>(); // Matrix for background icons
    private final ArrayList<ArrayList<String>> matrix2 = new ArrayList<>();   // Matrix for text colors
    private final ArrayList<ImageIcon> assetsImg = new ArrayList<>(); // List of image assets
    private Guild gameGuild;      // Guild scene
    private Market gameMarket;    // Market scene
    private Blacksmith blackSmith;// Blacksmith scene
    private MagicShop magicshop;  // Magic shop scene
    private SkillShop skillShop;  // Skill shop scene
    private RunesShop runesShop;  // Runes shop scene
    private Character character;  // Character scene
    private WorldMap worldMap;    // World map scene

    // Constructor initializing the GameLobby
    public GameLobby(Screen window) {
        this.window = window;
        this.width = this.height = -5; // Default invalid positions
        window.setBackground("assets/Game Menu/GameMenuBackGround.png"); // Set background image
        addElements(); // Add elements to the lobby
    }

    // Method to add elements to the lobby
    void addElements() {
        // Adding image assets to the list
        assetsImg.add(new ImageIcon("assets/Game Menu/BrownBlackGround.png"));
        assetsImg.add(new ImageIcon("assets/Game Menu/WhitePressedBackGround.png"));
        assetsImg.add(new ImageIcon("assets/Game Menu/Cicon.png"));
        assetsImg.add(new ImageIcon("assets/Game Menu/BlackSmithIcon.png"));
        assetsImg.add(new ImageIcon("assets/Game Menu/GoldIcon.png"));
        assetsImg.add(new ImageIcon("assets/Game Menu/SuppliesIcon.png"));
        assetsImg.add(new ImageIcon("assets/Game Menu/Gicon.png"));
        assetsImg.add(new ImageIcon("assets/Game Menu/MarketIcon.png"));
        assetsImg.add(new ImageIcon("assets/Game Menu/MagicShop.png"));
        assetsImg.add(new ImageIcon("assets/Game Menu/SkillShop.png"));
        assetsImg.add(new ImageIcon("assets/Game Menu/RuneStoneShop.png"));
        assetsImg.add(new ImageIcon("assets/Game Menu/CharacterIcon.png"));
        assetsImg.add(new ImageIcon("assets/Game Menu/WorldMap.png"));

        // Initialize the matrices for the lobby layout
        for (int i = 0; i < 4; i++) {
            ArrayList<ImageIcon> row = new ArrayList<>();
            ArrayList<String> rowaux = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                if (i == this.height && j == this.width) {
                    row.add(assetsImg.get(1)); // Highlighted background
                    rowaux.add("GREEN");       // Highlighted text
                } else {
                    row.add(assetsImg.get(0)); // Default background
                    rowaux.add("WHITE");       // Default text
                }
            }
            matrix.add(row);
            matrix2.add(rowaux);
        }
    }

    // Method to update assets based on navigation
    void updateAssets() {
        // Reset the last position to default background and text
        if (this.lasth != -5 && this.lastw != -5) {
            matrix.get(this.lasth).set(this.lastw, assetsImg.get(0));
            matrix2.get(this.lasth).set(this.lastw, "WHITE");
        }

        // Highlight the current position
        if (this.height != -5 && this.width != -5) {
            matrix.get(this.height).set(this.width, assetsImg.get(1));
            matrix2.get(this.height).set(this.width, "GREEN");
        }
    }

    // Method to draw all elements in the lobby
    public void drawEverything() {
        window.addImageAtPixel(0, 0, 490, 40, assetsImg.get(0).getImage());
        window.addImageAtPixel(5, 0, 40, 40, assetsImg.get(2).getImage());
        window.addTextAtPixel("Main Lobby", 55, 30, "WHITE", 25f);

        // First row
        int yValue = 260;
        window.addImageAtPixel(0, yValue, 245, 80, assetsImg.get(0).getImage());
        window.addImageAtPixel(10, yValue + 15, 40, 40, assetsImg.get(4).getImage());
        window.addTextAtPixel("Gold: " + Player.getInstance().getGold(), 55, yValue + 45, "WHITE", 25f);
        window.addImageAtPixel(245, yValue, 240, 80, assetsImg.get(0).getImage());
        window.addImageAtPixel(255, yValue + 15, 40, 40, assetsImg.get(5).getImage());
        window.addTextAtPixel("Supplies: " + Player.getInstance().getSupplies(), 300, yValue + 45, "WHITE", 25f);

        // Second row
        yValue += 80;
        window.addImageAtPixel(0, yValue, 245, 80, matrix.get(0).get(0).getImage());
        window.addImageAtPixel(10, yValue + 15, 40, 40, assetsImg.get(6).getImage());
        window.addTextAtPixel("Guild", 55, yValue + 45, matrix2.get(0).get(0), 25f);
        window.addImageAtPixel(245, yValue, 240, 80, matrix.get(0).get(1).getImage());
        window.addImageAtPixel(255, yValue + 15, 40, 40, assetsImg.get(7).getImage());
        window.addTextAtPixel("Market", 300, yValue + 45, matrix2.get(0).get(1), 25f);

        // Third row
        yValue += 80;
        window.addImageAtPixel(0, yValue, 245, 80, matrix.get(1).get(0).getImage());
        window.addImageAtPixel(10, yValue + 15, 40, 40, assetsImg.get(3).getImage());
        window.addTextAtPixel("Black Smith", 55, yValue + 45, matrix2.get(1).get(0), 25f);
        window.addImageAtPixel(245, yValue, 240, 80, matrix.get(1).get(1).getImage());
        window.addImageAtPixel(255, yValue + 15, 40, 40, assetsImg.get(8).getImage());
        window.addTextAtPixel("Magic Shop", 300, yValue + 45, matrix2.get(1).get(1), 25f);

        // Fourth row
        yValue += 80;
        window.addImageAtPixel(0, yValue, 245, 80, matrix.get(2).get(0).getImage());
        window.addImageAtPixel(10, yValue + 15, 40, 40, assetsImg.get(9).getImage());
        window.addTextAtPixel("Skill Shop", 55, yValue + 45, matrix2.get(2).get(0), 25f);
        window.addImageAtPixel(245, yValue, 240, 80, matrix.get(2).get(1).getImage());
        window.addImageAtPixel(255, yValue + 15, 40, 40, assetsImg.get(10).getImage());
        window.addTextAtPixel("Runes Shop", 300, yValue + 45, matrix2.get(2).get(1), 25f);

        // Fifth row
        yValue += 80;
        window.addImageAtPixel(0, yValue, 245, 80, matrix.get(3).get(0).getImage());
        window.addImageAtPixel(10, yValue + 15, 40, 40, assetsImg.get(11).getImage());
        window.addTextAtPixel("Character", 55, yValue + 45, matrix2.get(3).get(0), 25f);
        window.addImageAtPixel(245, yValue, 240, 80, matrix.get(3).get(1).getImage());
        window.addImageAtPixel(255, yValue + 15, 40, 40, assetsImg.get(12).getImage());
        window.addTextAtPixel("World Map", 300, yValue + 45, matrix2.get(3).get(1), 25f);
    }

    // Method to remove the key listener
    private void removeKeyAdaptor() {
        window.removeKeyListener(gameLobbyListener);
    }

    // Method to enter the Market scene
    void enterMarket() {
        removeKeyAdaptor();
        window.clearScreen();
        gameMarket = new Market(window, this);
        window.setCurentScene(gameMarket);
    }

    // Method to enter the Guild scene
    void enterGuild() {
        removeKeyAdaptor();
        window.clearScreen();
        gameGuild = new Guild(window, this);
        window.setCurentScene(gameGuild);
    }

    // Method to enter the Blacksmith scene
    void enterBlackSmith() {
        removeKeyAdaptor();
        window.clearScreen();
        blackSmith = new Blacksmith(window, this);
        window.setCurentScene(blackSmith);
    }

    // Method to enter the Magic Shop scene
    void enterMagicShop() {
        removeKeyAdaptor();
        window.clearScreen();
        magicshop = new MagicShop(window, this);
        window.setCurentScene(magicshop);
    }

    // Method to enter the Skill Shop scene
    void enterSkillShop() {
        removeKeyAdaptor();
        window.clearScreen();
        skillShop = new SkillShop(window, this);
        window.setCurentScene(skillShop);
    }

    // Method to enter the Runes Shop scene
    void enterRunesShop() {
        removeKeyAdaptor();
        window.clearScreen();
        runesShop = new RunesShop(window, this);
        window.setCurentScene(runesShop);
    }

    // Method to enter the Character scene
    void enterCharacter() {
        removeKeyAdaptor();
        window.clearScreen();
        character = new Character(window, this);
        window.setCurentScene(character);
    }

    // Method to enter the World Map scene
    void enterWorldMap() {
        removeKeyAdaptor();
        window.clearScreen();
        worldMap = new WorldMap(window, this);
        window.setCurentScene(worldMap);
    }

    // Method to enter the Save scene
    void enterSave() {
        removeKeyAdaptor();
        window.clearScreen();
        window.setCurentScene(new Save(window, this));
    }

    // Display method from the Scene interface
    @Override
    public void display() {
        drawEverything(); // Draw all elements on the screen
    }

    // Listen to input method from the Scene interface
    @Override
    public void listenToInput() {
        gameLobbyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleArrowKeys(e); // Handle arrow key navigation

                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    enterSave(); // Enter save scene if ESC is pressed
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // Enter different scenes based on the current position
                    if (height == 0 && width == 0) {
                        enterGuild();
                    }

                    if (height == 0 && width == 1) {
                        enterMarket();
                    }

                    if (height == 1 && width == 0) {
                        enterBlackSmith();
                    }

                    if (height == 1 && width == 1) {
                        enterMagicShop();
                    }

                    if (height == 2 && width == 0) {
                        enterSkillShop();
                    }

                    if (height == 2 && width == 1) {
                        enterRunesShop();
                    }

                    if (height == 3 && width == 0) {
                        enterCharacter();
                    }

                    if (height == 3 && width == 1) {
                        enterWorldMap();
                    }
                }
            }
        };
        window.addKeyListener(gameLobbyListener); // Add the key listener to the window
    }

    // Method to handle arrow key navigation
    private void handleArrowKeys(KeyEvent e) {
        lastw = width;
        lasth = height;
        if (width == -5 && height == -5) {
            width = height = 0; // Initialize to the first position
        } else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    height = ((height - 1) % 4 + 4) % 4; // Navigate up
                    break;
                case KeyEvent.VK_DOWN:
                    height = ((height + 1) % 4); // Navigate down
                    break;
                case KeyEvent.VK_LEFT:
                    width = ((width - 1) % 2 + 2) % 2; // Navigate left
                    break;
                case KeyEvent.VK_RIGHT:
                    width = ((width + 1) % 2); // Navigate right
                    break;
                default:
                    break;
            }
        }
        updateAssets(); // Update the assets based on the new position
    }
}
