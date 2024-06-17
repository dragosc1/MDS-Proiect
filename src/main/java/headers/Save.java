package headers;

// Import necessary classes
import headers.MainLobby.GameLobby;
import headers.MainLobby.MainTransition;
import headers.Utility.Items;
import headers.Utility.Quests;
import headers.Utility.Skills;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Save implements Scene {
    // Instance variables
    private Screen window;                // Reference to the game's screen
    private MainTransition mainTransition; // Transition scene object
    private GameLobby lobby;              // Game lobby scene object
    private Menu menu;                    // Menu scene object
    private KeyListener keyListener;      // Key listener for input handling
    private int where;                    // Flag to distinguish between menu and lobby saves

    // Constructor for saving from the menu
    public Save(Screen window, Menu menu) {
        this.window = window;
        this.window.setBackground("assets/mmu.png");
        this.menu = menu;
        this.where = 0; // Set where to 0 (menu)
    }

    // Constructor for saving from the game lobby
    public Save(Screen window, GameLobby gamelobby) {
        this.window = window;
        this.window.setBackground("assets/mmu.png");
        this.lobby = gamelobby;
        this.where = 1; // Set where to 1 (lobby)
    }

    @Override
    public void display() {
        // Display the save option on the screen
        window.addTextAtPixel("> SaveFile", 160, 500, "WHITE", 25f);
    }

    // Method to remove the key adapter
    void removeKeyAdaptor() {
        window.removeKeyListener(keyListener);
    }

    // Method to return to the main menu
    void EnterMenu() {
        removeKeyAdaptor(); // Remove key listener
        window.clearScreen(); // Clear the screen
        window.setCurentScene(menu); // Set current scene to the menu
    }

    // Initialize the main transition scene
    void initMainTransition() {
        mainTransition = new MainTransition(window, 1);
    }

    // Method to start a new game
    private void startNewGame() {
        Skills.getInstance(); // Initialize skills
        Items.getInstance(); // Initialize items
        removeKeyAdaptor(); // Remove key listener
        window.clearScreen(); // Clear the screen
        initMainTransition(); // Initialize main transition
        window.setCurentScene(mainTransition); // Set current scene to main transition
    }

    // Method to enter the game lobby
    private void enterLobby() {
        removeKeyAdaptor(); // Remove key listener
        window.clearScreen(); // Clear the screen
        window.setBackground("assets/Game Menu/GameMenuBackGround.png"); // Set background
        window.setCurentScene(lobby); // Set current scene to the lobby
    }

    @Override
    public void listenToInput() {
        keyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // If Escape key is pressed
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (where == 0)
                        EnterMenu(); // If in menu, go back to menu
                    if (where == 1)
                        enterLobby(); // If in lobby, return to lobby
                }

                // If Enter key is pressed
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (where == 0) {
                        // Save player data and quests when in menu
                        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("saves/player.ser"))) {
                            Player player = (Player) ois.readObject(); // Read player object
                            Player.setInstance(player); // Set player instance
                            Player.getInstance().saveSettings(); // Save player settings
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }

                        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("saves/quest.ser"))) {
                            Quests quests = (Quests) ois.readObject(); // Read quests object
                            Quests.setInstance(quests); // Set quests instance
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        startNewGame(); // Start new game
                    } else {
                        // Save player data and quests when in lobby
                        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("saves/player.ser"))) {
                            oos.writeObject(Player.getInstance()); // Write player object
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("saves/quest.ser"))) {
                            oos.writeObject(Quests.getInstance()); // Write quests object
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        enterLobby(); // Return to lobby
                    }
                }
            }
        };

        window.addKeyListener(keyListener); // Add key listener to the window
    }
}
