package headers.MainLobby;

import headers.Scene;
import headers.Screen;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class MagicShop implements Scene {
    private final Screen window;
    private final GameLobby lobby;
    private KeyListener magicShopListener;
    private ImageIcon back, wood, bar, ic0;
    private ImageIcon holder, ic1, ic2, ic3;

    public MagicShop(Screen window, GameLobby _lobby) {
        this.window = window;
        this.lobby = _lobby;
        this.window.setBackground("WHITE");
        loadAssets();
    }

    void loadAssets()  {
        back = new ImageIcon("assets/Magic shop/MagicShop.jpg");
        wood = new ImageIcon("assets/Market/woodytexturebackground.jpg");
        bar  = new ImageIcon("assets/Market/BrownBlackGround.png");
        ic0 = new ImageIcon("assets/Market/Cicon.png");
        holder = new ImageIcon("assets/Market/itemholder.png");
        ic1 = new ImageIcon("assets/Market/inventoryicon.png");
        ic2 = new ImageIcon("assets/Market/GoldIcon.png");
        ic3 = new ImageIcon("assets/Magic shop/MagicShop.png");
    }


    void drawEverything() {
        window.addImageAtPixel(0, 0, 500, 400, back.getImage());

        window.addImageAtPixel(0, 0, 490, 40, bar.getImage());
        window.addImageAtPixel(5, 0, 40, 40, ic0.getImage());
        window.addTextAtPixel("Magic Shop", 55, 30, "WHITE", 25f);

        window.addImageAtPixel(0, 400, 500, 400, wood.getImage());

        // first row
        int yValue = 400;
        window.addImageAtPixel(0, yValue, 245, 80, holder.getImage());
        window.addImageAtPixel(10, yValue + 20, 40, 40, ic1.getImage());
        window.addTextAtPixel("10", 55, yValue + 45, "WHITE", 25f);
        window.addImageAtPixel(100, yValue + 20, 40, 40, ic2.getImage());
        window.addTextAtPixel("500", 150, yValue + 45, "WHITE", 25f);

        window.addImageAtPixel(245, yValue, 240, 80, holder.getImage());
        window.addImageAtPixel(255, yValue + 20, 40, 40, ic3.getImage());

        // second row
        yValue += 120;
        window.addImageAtPixel(0, yValue, 245, 80, holder.getImage());
        window.addTextAtPixel("Slot 1", 55, yValue + 45, "WHITE", 25f);

        window.addImageAtPixel(245, yValue, 240, 80, holder.getImage());
        window.addTextAtPixel("Supplies", 300, yValue + 45, "WHITE", 25f);

    }

    private void removeKeyAdaptor() {
        window.removeKeyListener(magicShopListener);
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
        magicShopListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    enterLobby();
                }
            }
        };
        window.addKeyListener(magicShopListener);
    }
}
