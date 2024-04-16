package headers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class GameLobby implements  Scene {
    private Screen window;

    public GameLobby(Screen window) {
        this.window = window;
        window.setBackground("assets/Game Menu/GameMenuBackGround.png");
    }

    public void drawEverything() {
        ImageIcon image = new ImageIcon("assets/Game Menu/BrownBlackGround.png");
        window.addImageAtPixel(0, 0, 490, 40, image.getImage());
        image = new ImageIcon("assets/Game Menu/CasleIcon.png");
        window.addImageAtPixel(0, 0, 30, 30, image.getImage());
    }

    @Override
    public void display() {
        drawEverything();
    }

    @Override
    public void listenToInput() {

    }
}
