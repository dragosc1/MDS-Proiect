package headers.MainLobby;

import headers.Scene;
import headers.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class Blacksmith implements Scene {
    private final Screen window;
    private final GameLobby lobby;
    private KeyListener blackSmithListener;
    private ImageIcon back, wood, bar, ic0;
    private ImageIcon holder, ic1, ic2, ic3, ic4, ic5;
    private String currTxt;

    public Blacksmith(Screen window, GameLobby _lobby) {
        this.window = window;
        this.lobby = _lobby;
        this.window.setBackground("WHITE");
        loadAssets();
    }

    void loadAssets() {
        currTxt = "Weapons";
        back = new ImageIcon("assets/Black smith/blacksmith.png");
        wood = new ImageIcon("assets/Market/woodytexturebackground.jpg");
        bar  = new ImageIcon("assets/Market/BrownBlackGround.png");
        ic0 = new ImageIcon("assets/Guild/Cicon.png");
        ic1 = new ImageIcon("assets/Black smith/SwordCommonNoTier.png");
        ic1 = modifyColors(ic1);
        ic2 = new ImageIcon("assets/Black smith/armorIcon.png");
        ic3 = new ImageIcon("assets/Black smith/inventoryicon.png");
        ic4 = new ImageIcon("assets/Market/GoldIcon.png");
        ic5 = new ImageIcon("assets/Black smith/BlackSmithIcon.png");
        holder = new ImageIcon("assets/Market/itemholder.png");
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

                // Adjust thresholds for identifying light shades
                int blackThreshold = 30; // Increase threshold for identifying black
                int whiteThreshold = 220; // Decrease threshold for identifying white

                // Check if pixel is white
                if (red >= whiteThreshold && green >= whiteThreshold && blue >= whiteThreshold) {
                    red = 0; // Set red component to 0
                    green = 0; // Set green component to 0
                    blue = 0; // Set blue component to 0
                }
                // Check if pixel is black
                else if (red <= blackThreshold && green <= blackThreshold && blue <= blackThreshold) {
                    green = 255; // Set green component to max
                }

                int newRGB = (alpha << 24) | (red << 16) | (green << 8) | blue;
                bufferedImage.setRGB(x, y, newRGB);
            }
        }

        return new ImageIcon(bufferedImage);
    }

    void drawEverything() {
        window.addImageAtPixel(0, 0, 500, 400, back.getImage());

        window.addImageAtPixel(0, 0, 490, 40, bar.getImage());
        window.addImageAtPixel(5, 0, 40, 40, ic0.getImage());
        window.addTextAtPixel("BlackSmith", 55, 30, "WHITE", 25f);
        window.addImageAtPixel(305, 0, 40, 40, ic1.getImage());
        window.addImageAtPixel(355, 0, 40, 40, ic2.getImage());

        window.addImageAtPixel(0, 400, 500, 400, wood.getImage());

        // first row
        int yValue = 400;
        window.addImageAtPixel(0, yValue, 245, 80, holder.getImage());
        window.addImageAtPixel(10, yValue + 20, 40, 40, ic3.getImage());
        window.addTextAtPixel("10", 55, yValue + 45, "WHITE", 25f);
        window.addImageAtPixel(100, yValue + 20, 40, 40, ic4.getImage());
        window.addTextAtPixel("500", 150, yValue + 45, "WHITE", 25f);

        window.addImageAtPixel(245, yValue, 240, 80, holder.getImage());
        window.addImageAtPixel(255, yValue + 20, 40, 40, ic5.getImage());
        window.addTextAtPixel(currTxt, 300, yValue + 45, "WHITE", 25f);

        // second row
        yValue += 120;
        window.addImageAtPixel(0, yValue, 245, 80, holder.getImage());
        window.addTextAtPixel("Slot 1", 55, yValue + 45, "WHITE", 25f);

        window.addImageAtPixel(245, yValue, 240, 80, holder.getImage());
        window.addTextAtPixel("Supplies", 300, yValue + 45, "WHITE", 25f);
    }

    private void removeKeyAdaptor() {
        window.removeKeyListener(blackSmithListener);
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
        blackSmithListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    enterLobby();
                }

                if (e.getKeyCode() == KeyEvent.VK_1) {
                    currTxt = "Weapons";
                    ic1 = new ImageIcon("assets/Black smith/SwordCommonNoTier.png");
                    ic1 = modifyColors(ic1);
                    ic2 = new ImageIcon("assets/Black smith/armorIcon.png");
                }

                if (e.getKeyCode() == KeyEvent.VK_2) {
                    currTxt = "Armour";
                    ic1 = new ImageIcon("assets/Black smith/SwordCommonNoTier.png");
                    ic2 = new ImageIcon("assets/Black smith/armorIcon.png");
                    ic2 = modifyColors(ic2);
                }
            }
        };
        window.addKeyListener(blackSmithListener);
    }
}
