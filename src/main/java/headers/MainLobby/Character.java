package headers.MainLobby;

import headers.Scene;
import headers.Screen;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Character implements Scene {
    private final Screen window;
    private final GameLobby lobby;
    private KeyListener characterKeyListener;
    private ImageIcon back, wood, bar, ic0;
    private ImageIcon holder, ic1, ic2, ic3, ic4;
    private ImageIcon ic5, ic6, ic7, ic8, ic9;

    public Character(Screen window, GameLobby _lobby) {
        this.window = window;
        this.lobby = _lobby;
        this.window.setBackground("BLACK");
        loadAssets();
    }

    void loadAssets() {
        back = new ImageIcon("assets/Character/Chback.png");
        wood = new ImageIcon("assets/Market/woodytexturebackground.jpg");
        bar  = new ImageIcon("assets/Market/BrownBlackGround.png");
        ic0 = new ImageIcon("assets/Market/Cicon.png");
        holder = new ImageIcon("assets/Market/itemholder.png");
        ic1 = new ImageIcon("assets/Character/EmptyIcon.png");
        ic2 = new ImageIcon("assets/Character/HealthPointsIcon.png");
        ic3 = new ImageIcon("assets/Character/AttackDamageIcon.png");
        ic4 = new ImageIcon("assets/Character/AbilityPowerIcon.png");
        ic3 = new ImageIcon("assets/Character/AttackDamageIcon.png");
        ic4 = new ImageIcon("assets/Character/AbilityPowerIcon.png");
        ic5 = new ImageIcon("assets/Character/CritRateIcon.png");
        ic6 = new ImageIcon("assets/Character/CritDamageIcon.png");
        ic7 = new ImageIcon("assets/Market/inventoryicon.png");
        ic8 = new ImageIcon("assets/Market/GoldIcon.png");
        ic9 = new ImageIcon("assets/Game Menu/SuppliesIcon.png");
    }

    private void removeKeyAdaptor() {
        window.removeKeyListener(characterKeyListener);
    }

    private void enterLobby() {
        removeKeyAdaptor();
        window.clearScreen();
        window.setBackground("assets/Game Menu/GameMenuBackGround.png");
        window.setCurentScene(lobby);
    }

    void drawEverything() {
        window.addImageAtPixel(0, 0, 500, 400, back.getImage());

        window.addImageAtPixel(0, 0, 490, 40, bar.getImage());
        window.addImageAtPixel(5, 0, 40, 40, ic0.getImage());
        window.addTextAtPixel("Character", 55, 30, "WHITE", 25f);

        window.addImageAtPixel(0, 400, 500, 400, wood.getImage());

        int yValue = 50;
        window.addImageAtPixel(10, yValue, 40, 40, ic1.getImage());
        window.addTextAtPixel("Trait", 55, yValue + 30, "WHITE", 25f);

        window.addImageAtPixel(300, yValue, 40, 40, ic2.getImage());
        window.addTextAtPixel("Hp: ", 345, yValue + 30, "WHITE", 25f);

        yValue += 50;
        window.addImageAtPixel(10, yValue, 40, 40, ic3.getImage());
        window.addTextAtPixel("Ad: ", 55, yValue + 30, "WHITE", 25f);

        window.addImageAtPixel(300, yValue, 40, 40, ic4.getImage());
        window.addTextAtPixel("Ap: ", 345, yValue + 30, "WHITE", 25f);

        yValue += 50;
        window.addImageAtPixel(10, yValue, 40, 40, ic5.getImage());
        window.addTextAtPixel("Arm: ", 55, yValue + 30, "WHITE", 25f);

        window.addImageAtPixel(300, yValue, 40, 40, ic6.getImage());
        window.addTextAtPixel("Mr: ", 345, yValue + 30, "WHITE", 25f);

        yValue += 50;
        window.addImageAtPixel(10, yValue, 40, 40, ic1.getImage());
        window.addTextAtPixel("Weapon", 55, yValue + 30, "WHITE", 25f);

        window.addImageAtPixel(300, yValue, 40, 40, ic1.getImage());
        window.addTextAtPixel("Armour", 345, yValue + 30, "WHITE", 25f);

        yValue += 100;
        window.addTextAtPixel("Str: ", 55, yValue + 30, "WHITE", 25f);
        window.addTextAtPixel("Dex: ", 345, yValue + 30, "WHITE", 25f);
        yValue += 30;
        window.addTextAtPixel("Int: ", 55, yValue + 30, "WHITE", 25f);
        window.addTextAtPixel("Cha: ", 345, yValue + 30, "WHITE", 25f);

        yValue = 400;
        window.addImageAtPixel(0, yValue, 490, 80, holder.getImage());
        window.addImageAtPixel(10, yValue + 20, 40, 40, ic7.getImage());
        window.addTextAtPixel("10", 55, yValue + 45, "WHITE", 25f);
        window.addImageAtPixel(150, yValue + 20, 40, 40, ic8.getImage());
        window.addTextAtPixel("500", 200, yValue + 45, "WHITE", 25f);
        window.addImageAtPixel(300, yValue + 20, 40, 40, ic9.getImage());
        window.addTextAtPixel("100", 350, yValue + 45, "WHITE", 25f);

        yValue += 120;
        window.addImageAtPixel(0, yValue, 245, 80, holder.getImage());
        window.addTextAtPixel("Slot 1", 55, yValue + 45, "WHITE", 25f);

        window.addImageAtPixel(245, yValue, 240, 80, holder.getImage());
        window.addTextAtPixel("Supplies", 300, yValue + 45, "WHITE", 25f);
    }

    @Override
    public void display() {
        drawEverything();
    }

    @Override
    public void listenToInput() {
        characterKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    enterLobby();
                }

            }
        };
        window.addKeyListener(characterKeyListener);
    }
}
