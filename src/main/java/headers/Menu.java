package headers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Menu implements Scene {

    private List<Option> options;
    private int currentPosition;
    private Screen window;
    private NewGame gameScene;

    private KeyAdapter menuKeyAdapter;

    public Menu(Screen _window) {
        options = new ArrayList<>();
        currentPosition = -5;
        window = _window;
        addOption("New Game", 180, 460);
        addOption("Options", 180, 500);
        addOption("Exit", 180, 540);
    }

    public void addOption(String text, int x, int y) {
        options.add(new Option(text, x, y));
    }

    public Option getOptionAt(int index) {
        return options.get(index);
    }

    // Initialize the game scene
    private void initGameScene() {
        gameScene = new NewGame(this.window);
    }

    void removeKeyAdaptor() {
        window.removeKeyListener(menuKeyAdapter);
    }

    private void startNewGame() {
        initGameScene();
        removeKeyAdaptor();
        window.clearScreen();
        window.setCurentScene(gameScene);
    }

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
            window.addTextAtPixel(option.getText(), option.getX(), option.getY());
        }
    }

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
                    if (getCurrentPosition() == 0) {
                        // Start new game
                        startNewGame();
                    }
                }
            }
        };

        window.addKeyListener(menuKeyAdapter);
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void moveUp() {
        if (currentPosition == -5) currentPosition = 0;
        else currentPosition = (currentPosition - 1 + options.size()) % options.size();
    }

    public void moveDown() {
        if (currentPosition == -5) currentPosition = 0;
        else currentPosition = (currentPosition + 1) % options.size();
    }
}