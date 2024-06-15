package headers.MainLobby;

import headers.Player;
import headers.Scene;
import headers.Screen;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SkillShop implements Scene {
    private final Screen window;
    private final GameLobby lobby;
    private Integer ind0;
    private Boolean ind1;
    private KeyListener skillListener;
    private ImageIcon back, wood, bar, ic0;
    private ImageIcon holder, ic1, ic2, ic3;

    public SkillShop(Screen window, GameLobby _lobby) {
        ind0 = -1;
        this.window = window;
        this.lobby = _lobby;
        this.window.setBackground("WHITE");
        loadAssets();
    }

    void loadAssets() {
        back = new ImageIcon("assets/Skill Shop/SkillShop.png");
        wood = new ImageIcon("assets/Market/woodytexturebackground.jpg");
        bar = new ImageIcon("assets/Market/BrownBlackGround.png");
        ic0 = new ImageIcon("assets/Market/Cicon.png");
        holder = new ImageIcon("assets/Market/itemholder.png");
        ic1 = new ImageIcon("assets/Market/GoldIcon.png");
        ic2 = new ImageIcon("assets/Skill Shop/SkillShopIcon.png");
        ic3 = new ImageIcon("assets/Skill Shop/IntermediarMagic.png");
    }

    private void removeKeyAdaptor() {
        window.removeKeyListener(skillListener);
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
        window.addTextAtPixel("Skill Shop", 55, 30, "WHITE", 25f);

        window.addImageAtPixel(0, 400, 500, 400, wood.getImage());

        // first row
        int yValue = 400;
        window.addImageAtPixel(0, yValue, 245, 80, holder.getImage());
        window.addImageAtPixel(10, yValue + 20, 40, 40, ic1.getImage());
        window.addTextAtPixel("Gold: " + Player.getInstance().getGold(), 55, yValue + 45, "WHITE", 25f);

        window.addImageAtPixel(245, yValue, 240, 80, holder.getImage());
        window.addImageAtPixel(255, yValue + 20, 40, 40, ic2.getImage());
        window.addTextAtPixel("Skills", 310, yValue + 45, "WHITE", 25f);

        yValue += 120;
        window.addImageAtPixel(0, yValue, 490, 80, holder.getImage());
        window.addImageAtPixel(10, yValue + 20, 40, 40, ic3.getImage());
        window.addTextAtPixel(((ind0 == 1)? ">" : "") + "Space: " + Player.getInstance().getSInventorySpace(), 55, yValue + 45, "WHITE", 25f);


    }

    @Override
    public void display() {
        drawEverything();
    }

    @Override
    public void listenToInput() {
        skillListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    ind0 = -1;
                    enterLobby();
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_B) {
                    ind0 = 1;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    ind1 = true;
                    return;
                }
            }
        };
        window.addKeyListener(skillListener);
    }
}