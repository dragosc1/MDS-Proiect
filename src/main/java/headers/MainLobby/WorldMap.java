package headers.MainLobby;

import headers.Player;
import headers.Scene;
import headers.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class WorldMap implements Scene {
    private final Screen window;
    private final GameLobby lobby;
    private Dungeon dungeon;
    private KeyListener worldMapKeyListener;
    private ImageIcon back, bar, ic0, ic1, ic2, ic3, ic4, ic5, ic6;
    private Integer height, width, curr;

    public WorldMap(Screen window, GameLobby _lobby) {
        this.window = window;
        this.lobby = _lobby;
        this.window.setBackground("BLACK");
        height = width = 0;
        if (Player.getInstance().getCurrPlayerTier() == 1) curr = 2;
        else if (Player.getInstance().getCurrPlayerTier() == 2) curr = 3;
        else if (Player.getInstance().getCurrPlayerTier() == 3) curr = 4;
        else if (Player.getInstance().getCurrPlayerTier() == 4) curr = 5;
        else if (Player.getInstance().getCurrPlayerTier() == 5) curr = 6;
        else curr = 6;

        loadAssets();
    }

    void loadAssets() {
        back = new ImageIcon("assets/World Map/worldMapInitial size.png");
        bar  = new ImageIcon("assets/Market/BrownBlackGround.png");
        ic0 = new ImageIcon("assets/Market/Cicon.png");
        ic1 = new ImageIcon("assets/World Map/Dungeon1Icon.png");
        ic2 = new ImageIcon("assets/World Map/Dungeon1Icon.png");
        ic3 = new ImageIcon("assets/World Map/Dungeon1Icon.png");
        ic4 = new ImageIcon("assets/World Map/Dungeon1Icon.png");
        ic5 = new ImageIcon("assets/World Map/Dungeon1Icon.png");
    }

    public static ImageIcon modifyColors(ImageIcon icon) {
        Image image = icon.getImage();
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        // Adjust thresholds for light shades of black and white
        int blackThreshold = 30; // Increase threshold for identifying black
        int whiteThreshold = 220; // Decrease threshold for identifying white

        // Modify colors
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                int rgb = bufferedImage.getRGB(x, y);
                int alpha = (rgb >> 24) & 0xFF;
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                // Check if pixel is close to white
                if (red >= whiteThreshold && green >= whiteThreshold && blue >= whiteThreshold) {
                    red = 0; // Set red component to 0
                    green = 0; // Set green component to 0
                    blue = 0; // Set blue component to 0
                }
                // Check if pixel is close to black
                else if (red <= blackThreshold && green <= blackThreshold && blue <= blackThreshold) {
                    red = 0; // Set red component to 0
                    green = 255; // Set green component to max
                    blue = 0; // Set blue component to 0
                }

                int newRGB = (alpha << 24) | (red << 16) | (green << 8) | blue;
                bufferedImage.setRGB(x, y, newRGB);
            }
        }

        return new ImageIcon(bufferedImage);
    }

    synchronized void drawEverything() {
        window.addImageAtPixel(0, 0, 490, 670, back.getImage());

        window.addImageAtPixel(0, 0, 490, 40, bar.getImage());
        window.addImageAtPixel(5, 0, 40, 40, ic0.getImage());
        window.addTextAtPixel("World Map", 55, 30, "WHITE", 25f);


        ic0 = new ImageIcon("assets/Market/Cicon.png");
        ic1 = new ImageIcon("assets/World Map/Dungeon1Icon.png");
        ic2 = new ImageIcon("assets/World Map/Dungeon1Icon.png");
        ic3 = new ImageIcon("assets/World Map/Dungeon1Icon.png");
        ic4 = new ImageIcon("assets/World Map/Dungeon1Icon.png");
        ic5 = new ImageIcon("assets/World Map/Dungeon1Icon.png");

        if (width == 0) ic0 = modifyColors(ic0);
        else if (width == 1) ic1 = modifyColors(ic1);
        else if (width == 2) ic2 = modifyColors(ic2);
        else if (width == 3) ic3 = modifyColors(ic3);
        else if (width == 4) ic4 = modifyColors(ic4);
        else if (width == 5) ic5 = modifyColors(ic5);

        window.addImageAtPixel(250, 260, 40, 40, ic0.getImage());
        window.addImageAtPixel(100, 300, 40, 40, ic1.getImage());
        if (curr >= 3) window.addImageAtPixel(80, 360, 40, 40, ic2.getImage());
        if (curr >= 4) window.addImageAtPixel(150, 200, 40, 40, ic3.getImage());
        if (curr >= 5) window.addImageAtPixel(200, 250, 40, 40, ic4.getImage());
        if (curr >= 6) window.addImageAtPixel(300, 300, 40, 40, ic5.getImage());
    }

    private void removeKeyAdaptor() {
        window.removeKeyListener(worldMapKeyListener);
    }

    private void enterLobby() {
        removeKeyAdaptor();
        window.clearScreen();
        window.setBackground("assets/Game Menu/GameMenuBackGround.png");
        window.setCurentScene(lobby);
    }

    private void enterDungeon() {
        removeKeyAdaptor();
        window.clearScreen();
        dungeon = new Dungeon(window, lobby, width - 1);
        window.setCurentScene(dungeon);
    }

    @Override
    public void display() {
        drawEverything();
    }

    @Override
    public void listenToInput() {
        worldMapKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    enterLobby();
                }

                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    width = ((width - 1) % curr + curr) % curr;
                }

                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    width = ((width + 1) % curr);
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (width == 0) {
                        enterLobby();
                        return;
                    }

                   enterDungeon();
                   return;
                }
            }
        };
        window.addKeyListener(worldMapKeyListener);
    }
}
