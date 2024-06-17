package headers;

public class Game {
    private Screen window; // The game window
    private Menu menu; // The game menu
    static public boolean running; // Flag indicating whether the game is running
    private final int TICK_RATE = 60; // Desired tick rate per second
    private final long TICK_INTERVAL = 1000 / TICK_RATE; // Time interval between ticks

    public Game() {
        running = true; // Set the game as running
        initScreen(); // Initialize the game window
        initMenu(); // Initialize the game menu
    }

    public void clearWindow() {
        // refresh screen
        window.clearScreen();
    }

    // Method to draw the game window
    public void drawWindow() {
        window.displayCurentScene();
    }

    // Method to initialize the game menu
    public void initMenu() {
        menu = new Menu(window);
        window.setCurentScene(menu);
    }

    // Method to initialize the game window
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