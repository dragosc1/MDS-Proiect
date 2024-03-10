package headers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class NewGame implements Scene {
    private Screen window;
    private KeyAdapter newGameKeyAdaptop;

    private ArrayList<Trait> traits;

    private KeyListener newGameKeyListener;

    private Menu menu;

    public NewGame(Screen _window, Menu menu) {
        window = _window;
        window.setBackground("assets/new_game.png");
        initTraits();
        this.menu = menu;
    }

    void initTraits() {
        traits = new ArrayList<Trait>();
        traits.add(new Trait("Trait 1"));
        traits.add(new Trait("Trait 2"));
        traits.add(new Trait("Trait 3"));
    }

    void drawTexts() {
        window.addTextAtPixel("Choose trait", 175, 90, "WHITE", 0f);
        window.addTextAtPixel("Name: ", 10, 250, "BLACK", 0f, "Arial");
        window.addTextAtPixel("Points: ", 165, 325, "WHITE", 0f);
        window.addTextAtPixel("Strength", 25, 415, "WHITE", 0f);
        window.addTextAtPixel("Dexterity", 25, 480, "WHITE", 0f);
        window.addTextAtPixel("Intellect", 25, 550, "WHITE", 0f);
        window.addTextAtPixel("Charisma", 25, 620, "WHITE", 0f);
    }

    @Override
    public void display() {
        drawTexts();

        // draw traits
        for (int i = 0; i < traits.size(); i++)
            window.addTextAtPixel(traits.get(i).name, 50 + i * traits.get(i).name.length() * 20, 150, "WHITE", 0f);


    }

    void removeKeyAdaptor() {
        window.removeKeyListener(newGameKeyListener);
    }

    private void goToMainMenu() {
        removeKeyAdaptor();
        window.clearScreen();
        window.setCurentScene(menu);
        window.setBackground("assets/mmu.png");
    }

    @Override
    public void listenToInput() {
        newGameKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    goToMainMenu();
                }
            }
        };

        window.addKeyListener(newGameKeyListener);
    }

}
