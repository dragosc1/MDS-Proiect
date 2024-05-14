package headers.MainLobby;

import headers.Scene;
import headers.Screen;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Dungeon implements Scene {
    private final Screen window;
    private final GameLobby lobby;
    private KeyListener dunegonKeyListener;
    private ImageIcon back, bar, ic0, blocked, wood, ic1, ic2, ic3, ic4;
    private Integer height, width;
    private char[][] maze;
    private boolean[][] visible;
    private static Integer sx, sy, ex, ey;

    private static final int SIZE = 20;
    private static final char FREE = '.';
    private static final char OBSTACLE = '#';
    private static final char START = 'S';
    private static final char EXIT = 'E';

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

    private static boolean isPathPossible(char[][] grid, int startX, int startY, int exitX, int exitY) {
        boolean[][] visited = new boolean[SIZE][SIZE];
        return dfs(grid, startX, startY, exitX, exitY, visited);
    }

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

    public static void printDungeon(char[][] grid) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public Dungeon(Screen window, GameLobby _lobby) {
        this.window = window;
        this.lobby = _lobby;
        this.window.setBackground("BLACK");
        height = width = 0;
        this.maze = generateDungeon();
        this.visible = new boolean[SIZE][SIZE];
        printDungeon(this.maze);
        height = sx;
        width = sy;
        visible[height][width] = true;
        loadAssets();
    }

    void loadAssets() {
        back = new ImageIcon("assets/Character/Chback.png");
        bar  = new ImageIcon("assets/Market/BrownBlackGround.png");
        ic0 = new ImageIcon("assets/Dungeon/CharacterIcon.png");
        blocked = new ImageIcon("assets/Game Menu/WhitePressedBackGround.png");
        wood = new ImageIcon("assets/Market/woodytexturebackground.jpg");
        ic1 = new ImageIcon("assets/Dungeon/DungeonEmptyTile.png");
        ic2 = new ImageIcon("assets/Dungeon/dugeonTiles.png");
        ic3 = new ImageIcon("assets/Dungeon/dungeonExitDoor.png");
        ic4 = new ImageIcon("assets/Dungeon/Untitled.png");
    }

    void drawEverything() {
        window.addImageAtPixel(0, 0, 490, 670, back.getImage());

        window.addImageAtPixel(0, 0, 490, 40, bar.getImage());
        window.addImageAtPixel(5, 0, 40, 40, ic0.getImage());
        window.addTextAtPixel("Dungeon", 55, 30, "WHITE", 25f);

        int cellSize = 20;
        int gridSize = 20;
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
                    if (maze[row][col] == EXIT) window.addImageAtPixel(x, y, cellSize, cellSize, ic3.getImage());
                }
            }
        }

        window.addImageAtPixel(0, 500, 500, 400, wood.getImage());
    }

    void verify(int a, int b) {
        visible[a][b] = true;
        if (this.maze[a][b]!= OBSTACLE) {
            height = a;
            width = b;
        }
    }

    private void removeKeyAdaptor() {
        window.removeKeyListener(dunegonKeyListener);
    }

    private void enterLobby() {
        removeKeyAdaptor();
        window.clearScreen();
        window.setBackground("assets/Game Menu/GameMenuBackGround.png");
        window.setCurentScene(lobby);
    }

    @Override
    public void display() {
        drawEverything();
    }

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
