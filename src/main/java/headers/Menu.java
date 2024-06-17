package headers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Menu implements Scene {

    private List<Option> options; // List of menu options
    private int currentPosition; // Current selected option position
    private Screen window; // Game window
    private NewGame gameScene; // Game scene
    private KeyAdapter menuKeyAdapter; // Key adapter for menu interaction

    public Menu(Screen _window) {
        options = new ArrayList<>(); // Initialize the list of options
        currentPosition = -5; // Initial position of the cursor
        window = _window; // Set the game window
        // Add default menu options
        addOption("New Game", 180, 460);
        addOption("Load Save", 180, 500);
        addOption("Exit", 180, 540);
    }

    // Method to add a new option to the menu
    public void addOption(String text, int x, int y) {
        options.add(new Option(text, x, y));
    }

    // Method to get the option at the specified index
    public Option getOptionAt(int index) {
        return options.get(index);
    }

    // Initialize the game scene
    private void initGameScene() {
        gameScene = new NewGame(this.window, this);
    }

    // Remove the key adapter from the window
    void removeKeyAdaptor() {
        window.removeKeyListener(menuKeyAdapter);
    }

    // Start a new game
    private void startNewGame() {
        removeKeyAdaptor();
        window.clearScreen();
        initGameScene();
        window.setCurentScene(gameScene);
    }

    // Display the menu options
    @Override
    public void display() {
        for (int i = 0; i < options.size(); i++) {
            String option = options.get(i).getText();
            if (option.startsWith("> ")) {
                option = option.substring(2);
            }
            if (i == currentPosition) {
                option = "> " + option;
            }
            options.get(i).setText(option); // Update the option in the list
        }
        for (int i = 0; i < options.size(); i++) {
            Option option = getOptionAt(i);
            window.addTextAtPixel(option.getText(), option.getX(), option.getY(), "WHITE", 0);
        }
    }

    // Load the save game
    void LoadSave() {
        removeKeyAdaptor();
        window.clearScreen();
        window.setCurentScene(new Save(window, this));
    }

    // Listen to user input
    @Override
    public void listenToInput() {
        menuKeyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    moveUp();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    moveDown();
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (getCurrentPosition() == 2) {
                        window.dispose(); // Close the window
                        Game.running = false;
                    }
                    if (getCurrentPosition() == 1) {
                        LoadSave();
                    }
                    if (getCurrentPosition() == 0) {
                        // Start new game
                        startNewGame();
                    }
                }
            }
        };

        window.addKeyListener(menuKeyAdapter);
    }

    // Get the current position of the cursor
    public int getCurrentPosition() {
        return currentPosition;
    }

    // Move the cursor up
    public void moveUp() {
        if (currentPosition == -5) currentPosition = 0;
        else currentPosition = (currentPosition - 1 + options.size()) % options.size();
    }

    // Move the cursor down
    public void moveDown() {
        if (currentPosition == -5) currentPosition = 0;
        else currentPosition = (currentPosition + 1) % options.size();
    }
}