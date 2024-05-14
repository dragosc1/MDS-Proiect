package headers.MainLobby;

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

    public RunesShop(Screen window, GameLobby _lobby) {
        this.window = window;
        this.lobby = _lobby;
        this.window.setBackground("WHITE");
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
        window.addTextAtPixel("500", 55, yValue + 45, "WHITE", 25f);

        yValue += 90;
        window.addImageAtPixel(0, yValue, 200, 80, holder.getImage());
        window.addImageAtPixel(10, yValue + 20, 40, 40, ic2.getImage());
        window.addTextAtPixel("Str: ", 55, yValue + 45, "WHITE", 25f);

        window.addImageAtPixel(285, yValue, 200, 80, holder.getImage());
        window.addImageAtPixel(295, yValue + 20, 40, 40, ic3.getImage());
        window.addTextAtPixel("Dex: ", 340, yValue + 45, "WHITE", 25f);

        yValue += 70;
        window.addImageAtPixel(0, yValue, 200, 80, holder.getImage());
        window.addImageAtPixel(10, yValue + 20, 40, 40, ic4.getImage());
        window.addTextAtPixel("Int: ", 55, yValue + 45, "WHITE", 25f);

        window.addImageAtPixel(285, yValue, 200, 80, holder.getImage());
        window.addImageAtPixel(295, yValue + 20, 40, 40, ic5.getImage());
        window.addTextAtPixel("Cha: ", 340, yValue + 45, "WHITE", 25f);
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
                    enterLobby();
                }
            }
        };
        window.addKeyListener(runesListener);
    }
}
