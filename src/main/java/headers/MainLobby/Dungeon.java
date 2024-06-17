package headers.MainLobby;

import headers.Player;
import headers.Scene;
import headers.Screen;
import headers.Utility.Items;
import headers.Utility.Quests;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Dungeon implements Scene {
    // Represents the screen where the game is displayed.
    private final Screen window;

    // Represents the lobby where the game is hosted.
    private final GameLobby lobby;

    // Represents the key listener for dungeon interaction.
    private KeyListener dunegonKeyListener;

    // Image icons used in the game.
    private ImageIcon back, bar, ic0, blocked, wood, ic1, ic2, ic3, ic4, ic9, holder;

    // Height and width of the game window.
    private Integer height, width;

    // Represents the maze layout.
    private char[][] maze;

    // Represents the visibility of each cell in the maze.
    private boolean[][] visible;

    // Starting and ending positions in the maze.
    private static Integer sx, sy, ex, ey;

    // Size of each cell in the maze.
    private static final int SIZE = 20;

    // Characters representing different elements in the maze.
    private static final char FREE = '.';
    private static final char OBSTACLE = '#';
    private static final char START = 'S';
    private static final char EXIT = 'E';

    // Time limit for completing the dungeon.
    private int Tdungeon;

    // Damage dealt during the game.
    private int DamageDealt;

    // Indicates if it's the first move in the game.
    private Boolean firstMove;

    // Starting time of the game.
    private static long stTime;

    public synchronized void setDamge(int dmg) {
        DamageDealt = dmg;
    }

    public synchronized int getDamageDealt() {
        return DamageDealt;
    }

    public static char[][] generateDungeon() {
        char[][] grid = new char[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            Arrays.fill(grid[i], FREE);
        }

        // Randomly place obstacles
        Random rand = new Random();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (rand.nextDouble() < 0.3) { // 30% chance to be an obstacle
                    grid[i][j] = OBSTACLE;
                }
            }
        }

        // Ensure start and exit positions
        int startX = rand.nextInt(SIZE);
        int startY = rand.nextInt(SIZE);
        grid[startX][startY] = START;

        sx = startX;
        sy = startY;

        int exitX;
        int exitY;
        do {
            exitX = rand.nextInt(SIZE);
            exitY = rand.nextInt(SIZE);
        } while (exitX == startX && exitY == startY);
        grid[exitX][exitY] = EXIT;

        ex = exitX; ey = exitY;

        // Ensure there's a path from start to exit
        while (!isPathPossible(grid, startX, startY, exitX, exitY)) {
            // Re-generate obstacles if no path is found
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (grid[i][j] != START && grid[i][j] != EXIT) {
                        grid[i][j] = (rand.nextDouble() < 0.3) ? OBSTACLE : FREE;
                    }
                }
            }
        }

        return grid;
    }

    // Methode to check if there is a path to complete the dungeon level
    private static boolean isPathPossible(char[][] grid, int startX, int startY, int exitX, int exitY) {
        boolean[][] visited = new boolean[SIZE][SIZE];
        return dfs(grid, startX, startY, exitX, exitY, visited);
    }

    // DFS to check the existence of the path
    private static boolean dfs(char[][] grid, int x, int y, int exitX, int exitY, boolean[][] visited) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE || grid[x][y] == OBSTACLE || visited[x][y]) {
            return false;
        }
        if (x == exitX && y == exitY) {
            return true;
        }
        visited[x][y] = true;

        // Explore neighbors (up, down, left, right)
        return dfs(grid, x + 1, y, exitX, exitY, visited) ||
                dfs(grid, x - 1, y, exitX, exitY, visited) ||
                dfs(grid, x, y + 1, exitX, exitY, visited) ||
                dfs(grid, x, y - 1, exitX, exitY, visited);
    }

    // Methode to output the dungeon maze
    public static void printDungeon(char[][] grid) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public Dungeon(Screen window, GameLobby _lobby, int typeDungeon) {
        Tdungeon = typeDungeon; // Assign the type of dungeon.
        // Initialize the screen and lobby.
        this.window = window;
        this.lobby = _lobby;
        this.firstMove = false; // Set the first move to false.
        this.window.setBackground("BLACK"); // Set the background of the window to black.
        height = width = 0; // Set height and width to 0.
        stTime = -1; // Set the starting time to -1.
        this.maze = generateDungeon(); // Generate the dungeon.
        this.visible = new boolean[SIZE][SIZE]; // Initialize visibility array.
        printDungeon(this.maze); // Print the generated dungeon.

        // Set the initial position.
        height = sx;
        width = sy;
        visible[height][width] = true;
        DamageDealt = 0; // Set initial damage dealt to 0.
        loadAssets(); // Load assets for the game.
    }

    public int getTdungeon() {
        return this.Tdungeon;
    }

    // Method to load assets required for the scene
    void loadAssets() {
        holder = new ImageIcon("assets/Market/itemholder.png");
        back = new ImageIcon("assets/Character/Chback.png");
        bar  = new ImageIcon("assets/Market/BrownBlackGround.png");
        ic0 = new ImageIcon("assets/Dungeon/CharacterIcon.png");
        blocked = new ImageIcon("assets/Game Menu/WhitePressedBackGround.png");
        wood = new ImageIcon("assets/Market/woodytexturebackground.jpg");
        ic1 = new ImageIcon("assets/Dungeon/DungeonEmptyTile.png");
        ic2 = new ImageIcon("assets/Dungeon/dugeonTiles.png");
        ic3 = new ImageIcon("assets/Dungeon/dungeonExitDoor.png");
        ic4 = new ImageIcon("assets/Dungeon/Untitled.png");
        ic9 = new ImageIcon("assets/Game Menu/SuppliesIcon.png");
    }

    synchronized void drawEverything() {
        // Get the current time.
        long endTime = System.currentTimeMillis();

        // Check if the time limit for the dungeon has passed.
        if (stTime != -1 && endTime - stTime >= 1000) {
            // Subtract supplies from the player.
            Player.getInstance().subSupplies();
            stTime = System.currentTimeMillis();
            if (Player.getInstance().getSupplies() == 0) {
                Player.getInstance().subtractFromGold(100);
                enterLobby();
            }
        }

        // Draw the background.
        window.addImageAtPixel(0, 0, 490, 670, back.getImage());

        // Draw the top bar.
        window.addImageAtPixel(0, 0, 490, 40, bar.getImage());
        window.addImageAtPixel(5, 0, 40, 40, ic0.getImage());
        window.addTextAtPixel("Dungeon", 55, 30, "WHITE", 25f);

        // Draw the maze.
        int cellSize = 20;
        int gridSize = SIZE;
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                int x = 40 + col * cellSize;
                int y = 60 + row * cellSize;
                if (height == row && width == col) window.addImageAtPixel(x, y, cellSize, cellSize, ic0.getImage());
                else if (!visible[row][col]) window.addImageAtPixel(x, y, cellSize, cellSize, blocked.getImage());
                else {
                    if (maze[row][col] == OBSTACLE) window.addImageAtPixel(x, y, cellSize, cellSize, ic2.getImage());
                    if (maze[row][col] == FREE) window.addImageAtPixel(x, y, cellSize, cellSize, ic1.getImage());
                    if (maze[row][col] == START) window.addImageAtPixel(x, y, cellSize, cellSize, ic4.getImage());
                    if (maze[row][col] == EXIT) {
                        window.addImageAtPixel(x, y, cellSize, cellSize, ic3.getImage());
                    }
                }
            }
        }

        // Draw the inventory.
        window.addImageAtPixel(0, 500, 500, 400, wood.getImage());

        // Draw the supplies information.
        window.addImageAtPixel(150, 530, 245, 80, holder.getImage());
        window.addImageAtPixel(160, 550, 40, 40, ic9.getImage());
        window.addTextAtPixel("Supplies: " + Player.getInstance().getSupplies(), 210, 575, "WHITE", 25f);

    }

    void verify(int a, int b) {
        // If it's the first move, set the start time and update firstMove flag
        if (!firstMove) {
            stTime = System.currentTimeMillis();
            firstMove = true;
        }

        // Update the tile quest if the tile is not visible
        if (!visible[a][b])
            Quests.getInstance().updateTileQuest();

        // If the tile is not an obstacle, update the position.
        if (this.maze[a][b] != OBSTACLE) {
            height = a;
            width = b;

            // If the tile is not visible, check for encounter with a certain probability
            if (!visible[a][b]) {
                Random rand = new Random();
                if (rand.nextDouble() < 0.15)
                    enterEncounter();
            }
        }

        // Mark the tile as visible.
        visible[a][b] = true;
    }

    // Method to remove the key listener
    private void removeKeyAdaptor() {
        window.removeKeyListener(dunegonKeyListener);
    }

    // Method to transition back to the lobby
    private void enterLobby() {
        removeKeyAdaptor();
        window.clearScreen();
        window.setBackground("assets/Game Menu/GameMenuBackGround.png");
        window.setCurentScene(lobby);
    }

    // Method to transition to the Encounter scene
    private void enterEncounter() {
        removeKeyAdaptor();
        window.clearScreen();
        Encounter enc = new Encounter(window, this, lobby, min(3, Tdungeon + 1));
        window.setCurentScene(enc);
    }

    // Method to display the scene
    @Override
    public void display() {
        drawEverything();
    }

    // Method to listen to user inputs
    @Override
    public void listenToInput() {
        dunegonKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    int nwidth = max(0, width - 1);
                    verify(height, nwidth);

                }

                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    int nwidth = min(SIZE - 1, width + 1);
                    verify(height, nwidth);

                }

                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    int nheight = max(0, height - 1);
                    verify(nheight, width);
                }

                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    int nheight = min(SIZE - 1, height + 1);
                    verify(nheight, width);
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                   if (Objects.equals(height, ex) && Objects.equals(width, ey)) {
                       if (Tdungeon < 5) Player.getInstance().addDungeon(Tdungeon);
                       Items.getInstance().GenerateBlackSmithArmour();
                       Items.getInstance().GenerateBlackSmithWeapons();
                       enterLobby();
                   }
                   if (Objects.equals(height, sx) && Objects.equals(width, sy)) {
                       enterLobby();
                   }
                }
            }
        };
        window.addKeyListener(dunegonKeyListener);
    }
}
