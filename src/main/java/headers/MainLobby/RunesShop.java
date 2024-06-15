package headers.MainLobby;

import headers.Player;
import headers.Scene;
import headers.Screen;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class RunesShop implements Scene {
    private final Screen window;
    private final GameLobby lobby;
    private KeyListener runesListener;
    private ImageIcon back, wood, bar, ic0;
    private ImageIcon holder, ic1, ic2, ic3, ic4, ic5;
    private Integer x, y,checkUpWidth;
    private Boolean drawA;

    public RunesShop(Screen window, GameLobby _lobby) {
        this.window = window;
        this.lobby = _lobby;
        this.window.setBackground("WHITE");
        x = y = -1;
        drawA = false;
        checkUpWidth = 0;
        loadAssets();
    }

    void loadAssets() {
        back = new ImageIcon("assets/Rune Stone Shop/RuneSToneShop.png");
        wood = new ImageIcon("assets/Market/woodytexturebackground.jpg");
        bar  = new ImageIcon("assets/Market/BrownBlackGround.png");
        ic0 = new ImageIcon("assets/Market/Cicon.png");
        holder = new ImageIcon("assets/Market/itemholder.png");
        ic1 = new ImageIcon("assets/Market/GoldIcon.png");
        ic2 = new ImageIcon("assets/Rune Stone Shop/StrengthIcon.png");
        ic3 = new ImageIcon("assets/Rune Stone Shop/DexterityIcon.png");
        ic4= new ImageIcon("assets/Rune Stone Shop/IntelectIcon.png");
        ic5 = new ImageIcon("assets/Rune Stone Shop/CharismaIcon.png");
    }

    void drawEverything() {
        window.addImageAtPixel(0, 0, 500, 400, back.getImage());

        window.addImageAtPixel(0, 0, 490, 40, bar.getImage());
        window.addImageAtPixel(5, 0, 40, 40, ic0.getImage());
        window.addTextAtPixel("Runes Shop", 55, 30, "WHITE", 25f);

        window.addImageAtPixel(0, 400, 500, 400, wood.getImage());

        int yValue = 400;
        window.addImageAtPixel(0, yValue, 490, 80, holder.getImage());
        window.addImageAtPixel(10, yValue + 20, 40, 40, ic1.getImage());
        window.addTextAtPixel("Gold: "+ Player.getInstance().getGold(), 55, yValue + 45, "WHITE", 25f);

        yValue += 90;
        window.addImageAtPixel(0, yValue, 200, 80, holder.getImage());
        window.addImageAtPixel(10, yValue + 20, 40, 40, ic2.getImage());
        window.addTextAtPixel(((x == 0 && y == 0)? ">" : "") + "Str: +1", 55, yValue + 45, "WHITE", 25f);

        window.addImageAtPixel(285, yValue, 200, 80, holder.getImage());
        window.addImageAtPixel(295, yValue + 20, 40, 40, ic3.getImage());
        window.addTextAtPixel(((x == 0 && y == 1)? ">" : "") + "Dex: +1", 340, yValue + 45, "WHITE", 25f);

        yValue += 70;
        window.addImageAtPixel(0, yValue, 200, 80, holder.getImage());
        window.addImageAtPixel(10, yValue + 20, 40, 40, ic4.getImage());
        window.addTextAtPixel(((x == 1 && y == 0)? ">" : "") + "Int: +1", 55, yValue + 45, "WHITE", 25f);

        window.addImageAtPixel(285, yValue, 200, 80, holder.getImage());
        window.addImageAtPixel(295, yValue + 20, 40, 40, ic5.getImage());
        window.addTextAtPixel(((x == 1 && y == 1)? ">" : "") + "Cha: +1", 340, yValue + 45, "WHITE", 25f);

        if (drawA) {
            String type = "";
            int pos = 0;
            if (x == 0 && y == 0) {
                type = "Strength";
                pos = 0;
            } else if (x == 0 && y == 1) {
                type = "Dexterity";
                pos = 1;
            } else if (x == 1 && y == 0) {
                type = "Intelect";
                pos = 2;
            } else if (x == 1 && y == 1) {
                type = "Charisma";
                pos = 3;
            }

            int price = Player.getInstance().applyCharisma(10000);
            int playerGold = Player.getInstance().getGold();

            window.addPopUpAtPixel(0, 40, 490, 400, holder.getImage());
            window.addPopUpTextAtPixel("Buy", 120, 90,  "WHITE", 25f);
            window.addPopUpTextAtPixel(type + "  point", 180, 90,  "YELLOW", 25f);
            window.addPopUpTextAtPixel("For ", 150, 120, "WHITE", 25f);
            window.addCheckUpText(price + " Gold?", 200, 120, (playerGold >= price)? "GREEN" : "RED", 25f);

            window.addPopUpTextAtPixel("You currently have " + Player.getInstance().getStatus(pos) + " points", 10, 180,  "WHITE", 25f);



            window.addCheckUpText((checkUpWidth == 0? ">"  : "")  + "No", 20, 220,  "RED", 25f);
            if (playerGold >= price) window.addCheckUpText((checkUpWidth == 1? ">"  : "") + "Yes", 420, 220,  "GREEN", 25f);
        }
    }

    private void removeKeyAdaptor() {
        window.removeKeyListener(runesListener);
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
        runesListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (drawA) {
                        drawA = false;
                        return;
                    }

                    enterLobby();
                }

                if (e.getKeyCode() == KeyEvent.VK_B) {
                    x = y = 0;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (drawA) return;
                    x = ((x - 1) % 2 + 2) % 2;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (drawA) return;
                    x = (x + 1) % 2;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (drawA) {
                        checkUpWidth = ((checkUpWidth - 1) % 2 + 2) % 2;
                        return;
                    }
                    y  = ((y - 1) % 2 + 2) % 2;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (drawA) {
                        checkUpWidth = (checkUpWidth + 1) % 2;
                        return;
                    }
                    y = (y + 1) % 2;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && drawA && checkUpWidth == 0) {
                    drawA = false;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && drawA && checkUpWidth == 1) {
                    int playerGold = Player.getInstance().getGold();
                    if (playerGold < Player.getInstance().applyCharisma(10000)) {
                        return;
                    }

                    int pos = 0;
                    if (x == 0 && y == 0) pos = 0;
                    else if (x == 0 && y == 1) pos = 1;
                    else if (x == 1 && y == 0) pos = 2;
                    else if (x == 1 && y == 1) pos = 3;

                    Player.getInstance().subtractFromGold(Player.getInstance().applyCharisma(10000));
                    Player.getInstance().upgradeStatus(pos);
                    drawA = false;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && !drawA) {
                    drawA = true;
                    checkUpWidth = 0;
                    return;
                }
            }
        };
        window.addKeyListener(runesListener);
    }
}
