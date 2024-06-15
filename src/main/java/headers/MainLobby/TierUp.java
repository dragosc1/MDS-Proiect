package headers.MainLobby;

import headers.Player;
import headers.Scene;
import headers.Screen;
import headers.Utility.Quests;

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
    private Boolean checkA;

    public TierUp(Screen window, GameLobby _lobby) {
        this.window = window;
        this.lobby = _lobby;
        this.window.setBackground("WHITE");
        checkA = false;
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

    private static int getNr(String str) {
        int number = 0;
        boolean foundNumber = false;

        for (char c : str.toCharArray()) {
            if (c >= '0' && c <= '9') {
                number = (number * 10) + (c - '0');
                foundNumber = true;
            } else {
                if (foundNumber) {
                    break;
                }
            }
        }

        return number;
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

    synchronized void drawEverything() {
        window.addImageAtPixel(0, 40, 500, 400, back.getImage());
        window.addImageAtPixel(0, 0, 490, 40, bar.getImage());
        window.addImageAtPixel(5, 0, 40, 40, ic0.getImage());
        window.addTextAtPixel("Tier Up", 55, 30, "WHITE", 25f);
        window.addImageAtPixel(205, 0, 40, 40, ic1.getImage());
        window.addImageAtPixel(305, 0, 40, 40, ic2.getImage());
        window.addImageAtPixel(0, 400, 500, 400, wood.getImage());

        int yValue = 510;
        window.addImageAtPixel(10, yValue, 470, 80, holder.getImage());
        window.addTextAtPixel(Quests.getInstance().getTierUp(), 80, yValue + 45,  "WHITE", 25f);

        if (checkA) {
            if (Player.getInstance().getCurrPlayerTier() == 10) return;
            window.addPopUpAtPixel(0, 40, 490, 400, holder.getImage());
            window.addPopUpTextAtPixel("You have completed", 150, 90, "WHITE", 25f);

            int poss = Player.getInstance().getCurrPlayerTier() - 1;
            int pos = Quests.getInstance().getWhatType(poss);
            int nr1 = Player.getInstance().getDungeon(pos);
            int nr2 = getNr(Quests.getInstance().getTierUp());
            String color = "RED";
            if (nr1 >= nr2 / 2) color = "YELLOW";
            if (nr1 == nr2) color = "GREEN";
            window.addPopUpTextAtPixel(nr1 + "     out of     " + nr2, 150, 120, color, 25f);
            if (nr1 >= nr2) {
                window.addPopUpTextAtPixel("Complete", 200, 250, "GREEN", 25f);
            }
        }
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
                    if (checkA) {
                        checkA = false;
                        return;
                    }

                    enterLobby();
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (!checkA) {
                        if (Player.getInstance().getCurrPlayerTier() == 10)
                            return;
                        checkA = true;
                        return;
                    }

                    if (checkA) {
                        if (Player.getInstance().getCurrPlayerTier() == 10)
                            return;

                        int poss = Player.getInstance().getCurrPlayerTier() - 1;
                        int pos = Quests.getInstance().getWhatType(poss);
                        int nr1 = Player.getInstance().getDungeon(pos);
                        int nr2 = getNr(Quests.getInstance().getTierUp());
                        if (nr1 >= nr2) {
                            Player.getInstance().progressCurrPlayerTier();
                            Quests.getInstance().progressTier();
                            if (Player.getInstance().getCurrPlayerTier() % 2 == 1) {
                                Player.getInstance().updateSInventorySpace();
                                Quests.getInstance().progressLimit();
                            }

                            checkA = false;
                            return;
                        }
                    }

                }

                if (e.getKeyCode() == KeyEvent.VK_1) {
                    enterGuild();
                }
            }
        };
        window.addKeyListener(tierUpListener);
    }
}
