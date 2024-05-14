package headers.MainLobby;

import headers.Scene;
import headers.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class TierUp implements Scene {
    private final Screen window;
    private GameLobby lobby;
    private Guild qguild;
    private KeyListener tierUpListener;
    private ImageIcon back, wood, bar, ic0;
    private ImageIcon holder, ic1, ic2;

    public TierUp(Screen window, GameLobby _lobby) {
        this.window = window;
        this.lobby = _lobby;
        this.window.setBackground("WHITE");
        loadAssets();
    }

    public static ImageIcon modifyColors(ImageIcon icon) {
        Image image = icon.getImage();
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        // Modify colors
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                int rgb = bufferedImage.getRGB(x, y);
                int alpha = (rgb >> 24) & 0xFF;
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                // Check if pixel is white
                if (red == 255 && green == 255 && blue == 255) {
                    red = 0; // Set red component to 0
                    green = 0; // Set green component to 0
                    blue = 0; // Set blue component to 0
                }
                // Check if pixel is black
                else if (red == 0 && green == 0 && blue == 0) {
                    green = 255; // Set green component to max
                }

                int newRGB = (alpha << 24) | (red << 16) | (green << 8) | blue;
                bufferedImage.setRGB(x, y, newRGB);
            }
        }

        return new ImageIcon(bufferedImage);
    }

    void loadAssets() {
        back = new ImageIcon("assets/Guild/challangesbg.png");
        wood = new ImageIcon("assets/Market/woodytexturebackground.jpg");
        bar  = new ImageIcon("assets/Market/BrownBlackGround.png");
        ic0 = new ImageIcon("assets/Guild/Cicon.png");
        ic1 = new ImageIcon("assets/Guild/questsicon.png");
        ic2 = new ImageIcon("assets/Guild/challangesicon.png");
        ic2 = modifyColors(ic2);
        holder = new ImageIcon("assets/Market/itemholder.png");
    }

    private void removeKeyAdaptor() {
        window.removeKeyListener(tierUpListener);
    }

    private void enterLobby() {
        removeKeyAdaptor();
        window.clearScreen();
        window.setBackground("assets/Game Menu/GameMenuBackGround.png");
        window.setCurentScene(lobby);
    }

    void enterGuild() {
        removeKeyAdaptor();
        removeKeyAdaptor();
        window.clearScreen();
        qguild = new Guild(window, lobby);
        window.setCurentScene(qguild);
    }

    void drawEverything() {
        window.addImageAtPixel(0, 40, 500, 400, back.getImage());
        window.addImageAtPixel(0, 0, 490, 40, bar.getImage());
        window.addImageAtPixel(5, 0, 40, 40, ic0.getImage());
        window.addTextAtPixel("Tier Up", 55, 30, "WHITE", 25f);
        window.addImageAtPixel(205, 0, 40, 40, ic1.getImage());
        window.addImageAtPixel(305, 0, 40, 40, ic2.getImage());
        window.addImageAtPixel(0, 400, 500, 400, wood.getImage());

        int yValue = 510;
        window.addImageAtPixel(10, yValue, 470, 80, holder.getImage());
    }

    @Override
    public void display() {
        drawEverything();
    }

    @Override
    public void listenToInput() {
        tierUpListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    enterLobby();
                }

                if (e.getKeyCode() == KeyEvent.VK_1) {
                    enterGuild();
                }
            }
        };
        window.addKeyListener(tierUpListener);
    }
}
