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
    private ImageIcon back, wood, ;

    public Market(Screen window) {
        this.window = window;
        this.window.setBackground("WHITE");
        loadAssets();
    }

    private void loadAssets() {
        back = new ImageIcon("assets/Market/market.jpg");
        wood = new ImageIcon("assets/Market/woodytexturebackground.jpg");
    }

    void drawEverything() {
        this.window.addImageAtPixel(0, 0, 500, 400, back.getImage());
        this.window.addImageAtPixel(0, 400, 500, 400, wood.getImage());
    }

    @Override
    public void display() {
    }

    @Override
    public void listenToInput() {
        marketKeyListener = new KeyAdapter() {

        };

        window.addKeyListener(marketKeyListener);
    }

}
