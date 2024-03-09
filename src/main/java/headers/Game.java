package headers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Game {
    private Screen window;
    private Menu menu;
    private boolean running;
    private final int TICK_RATE = 60; // Desired tick rate in frames per second
    private final long TICK_INTERVAL = 1000 / TICK_RATE; // Interval between ticks in milliseconds

    public Game() {
        running = true;
        initScreen();
        initMenu();
    }

    public void listenToInput() {
        window.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    menu.moveUp();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    menu.moveDown();
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (menu.getCurrentPosition() == 2) {
                        window.dispose(); // Close the window
                        running = false;
                    }
                }
            }
        });
    }

    public void clearWindow() {
        // refresh screen
        window.clearTextPixels();
    }

    public void drawWindow() {
        menu.displayMenu();
        for (int i = 0; i < 3; i++) {
            Option option = menu.getOptionAt(i);
            window.addTextAtPixel(option.getText(), option.getX(), option.getY());
        }
    }

    public void initMenu() {
        menu = new Menu();
        menu.addOption("New Game", 180, 460);
        menu.addOption("Options", 180, 500);
        menu.addOption("Exit", 180, 540);
        menu.displayMenu();

        for (int i = 0; i < 3; i++) {
            Option option = menu.getOptionAt(i);
            window.addTextAtPixel(option.getText(), option.getX(), option.getY());
        }
    }

    public void initScreen() {
        window = new Screen("assets/mmu.png");
        setTheWindowVisible();
    }

    // check if the game is running
    public boolean isRunning() {
        return running;
    }

    // make the window visible
    public void setTheWindowVisible() {
        window.setVisible(true);
    }

    // Main game loop
    public void startGameLoop() {
        listenToInput();
        long lastTickTime = System.currentTimeMillis();
        while (isRunning()) {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - lastTickTime;

            if (elapsedTime >= TICK_INTERVAL) {
                clearWindow(); // Clear the window
                drawWindow(); // Redraw the window
                lastTickTime = currentTime; // Update last tick time
            }
        }
    }
}