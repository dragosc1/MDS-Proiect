package headers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Market implements Scene {
    private final Screen window;
    private KeyListener marketKeyListener;
    private ImageIcon back, wood, bar, ic0;
    private ImageIcon holder, ic1, ic2;

    public Market(Screen window) {
        this.window = window;
        this.window.setBackground("WHITE");
        loadAssets();
    }

    private void loadAssets() {
        back = new ImageIcon("assets/Market/market.jpg");
        wood = new ImageIcon("assets/Market/woodytexturebackground.jpg");
        bar  = new ImageIcon("assets/Market/BrownBlackGround.png");
        ic0 = new ImageIcon("assets/Market/Cicon.png");
        holder = new ImageIcon("assets/Market/itemholder.png");
        ic1 = new ImageIcon("assets/Market/inventoryicon.png");
        ic2 = new ImageIcon("assets/Market/GoldIcon.png");
    }

    void drawEverything() {
        window.addImageAtPixel(0, 0, 500, 400, back.getImage());

        window.addImageAtPixel(0, 0, 490, 40, bar.getImage());
        window.addImageAtPixel(5, 0, 40, 40, ic0.getImage());
        window.addTextAtPixel("Market", 55, 30, "WHITE", 25f);

        window.addImageAtPixel(0, 400, 500, 400, wood.getImage());

        // first row
        int yValue = 400;
        window.addImageAtPixel(0, yValue, 245, 80, holder.getImage());
        window.addImageAtPixel(10, yValue + 20, 40, 40, ic1.getImage());
        window.addTextAtPixel("Inventory", 55, yValue + 45, "WHITE", 25f);

        window.addImageAtPixel(245, yValue, 240, 80, holder.getImage());
        window.addImageAtPixel(255, yValue + 20, 40, 40, ic2.getImage());
        window.addTextAtPixel("Gold: 500", 300, yValue + 45, "WHITE", 25f);

        // second row
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
        marketKeyListener = new KeyAdapter() {

        };

        window.addKeyListener(marketKeyListener);
    }

}
