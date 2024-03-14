package headers;

public class Game {
    private Screen window;
    private Menu menu;
    static public boolean running;
    private final int TICK_RATE = 60;
    private final long TICK_INTERVAL = 1000 / TICK_RATE;

    public Game() {
        running = true;
        initScreen();
        initMenu();
    }

    public void clearWindow() {
        // refresh screen
        window.clearScreen();
    }

    public void drawWindow() {
        window.displayCurentScene();
    }

    public void initMenu() {
        menu = new Menu(window);
        window.setCurentScene(menu);
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