package headers.MainLobby;

import headers.Player;
import headers.Scene;
import headers.Screen;

import java.awt.event.*;
import javax.swing.ImageIcon;
import java.util.ArrayList;

public class GameLobby implements Scene {
    private final Screen window;
    private KeyListener gameLobbyListener;
    private int width, height;
    private int lastw, lasth;
    private final ArrayList <ArrayList <ImageIcon>> matrix = new ArrayList<>();
    private final ArrayList <ArrayList <String>> matrix2 = new ArrayList<>();
    private final ArrayList <ImageIcon> assetsImg = new ArrayList<>();
    private Guild gameGuild;
    private Market gameMarket;
    private Blacksmith blackSmith;
    private MagicShop magicshop;
    private SkillShop skillShop;
    private RunesShop runesShop;
    private Character character;
    private WorldMap worldMap;

    public GameLobby(Screen window) {
        this.window = window;
        this.width = this.height = -5;
        window.setBackground("assets/Game Menu/GameMenuBackGround.png");
        addElements();
    }

    void addElements() {
        assetsImg.add(new ImageIcon("assets/Game Menu/BrownBlackGround.png"));
        assetsImg.add(new ImageIcon("assets/Game Menu/WhitePressedBackGround.png"));
        assetsImg.add(new ImageIcon("assets/Game Menu/Cicon.png"));
        assetsImg.add(new ImageIcon("assets/Game Menu/BlackSmithIcon.png"));
        assetsImg.add(new ImageIcon("assets/Game Menu/GoldIcon.png"));
        assetsImg.add(new ImageIcon("assets/Game Menu/SuppliesIcon.png"));
        assetsImg.add(new ImageIcon("assets/Game Menu/Gicon.png"));
        assetsImg.add(new ImageIcon("assets/Game Menu/MarketIcon.png"));
        assetsImg.add(new ImageIcon("assets/Game Menu/MagicShop.png"));
        assetsImg.add(new ImageIcon("assets/Game Menu/SkillShop.png"));
        assetsImg.add(new ImageIcon("assets/Game Menu/RuneStoneShop.png"));
        assetsImg.add(new ImageIcon("assets/Game Menu/CharacterIcon.png"));
        assetsImg.add(new ImageIcon("assets/Game Menu/WorldMap.png"));

        for (int i = 0; i < 4; i++) {
            ArrayList <ImageIcon> row = new ArrayList<>();
            ArrayList <String> rowaux = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                if (i == this.height && j == this.width) {
                    row.add(assetsImg.get(1));
                    rowaux.add("GREEN");
                } else {
                    row.add(assetsImg.get(0));
                    rowaux.add("WHITE");
                }
            }
            matrix.add(row);
            matrix2.add(rowaux);
        }
    }

    void updateAssets() {
        if (this.lasth != -5 && this.lastw != -5) {
            matrix.get(this.lasth).set(this.lastw, assetsImg.get(0));
            matrix2.get(this.lasth).set(this.lastw, "WHITE");
        }

        if (this.height != -5 && this.width != -5) {
            matrix.get(this.height).set(this.width, assetsImg.get(1));
            matrix2.get(this.height).set(this.width, "GREEN");
        }
    }

    public void drawEverything() {
        window.addImageAtPixel(0, 0, 490, 40, assetsImg.get(0).getImage());
        window.addImageAtPixel(5, 0, 40, 40, assetsImg.get(2).getImage());
        window.addTextAtPixel("Main Lobby", 55, 30, "WHITE", 25f);
        // first row
        int yValue = 260;
        window.addImageAtPixel(0, yValue, 245, 80, assetsImg.get(0).getImage());
        window.addImageAtPixel(10, yValue + 15, 40, 40, assetsImg.get(4).getImage());
        window.addTextAtPixel("Gold: " + Player.getInstance().getGold(), 55, yValue + 45, "WHITE", 25f);
        window.addImageAtPixel(245, yValue, 240, 80, assetsImg.get(0).getImage());
        window.addImageAtPixel(255, yValue + 15, 40, 40, assetsImg.get(5).getImage());
        window.addTextAtPixel("Supplies: " + Player.getInstance().getSupplies(), 300, yValue + 45, "WHITE", 25f);
        // second row
        yValue += 80;
        window.addImageAtPixel(0, yValue, 245, 80, matrix.get(0).get(0).getImage());
        window.addImageAtPixel(10, yValue + 15, 40, 40, assetsImg.get(6).getImage());
        window.addTextAtPixel("Guild", 55, yValue + 45, matrix2.get(0).get(0), 25f);
        window.addImageAtPixel(245, yValue, 240, 80, matrix.get(0).get(1).getImage());
        window.addImageAtPixel(255, yValue + 15, 40, 40, assetsImg.get(7).getImage());
        window.addTextAtPixel("Market", 300, yValue + 45, matrix2.get(0).get(1), 25f);
        //third row
        yValue += 80;
        window.addImageAtPixel(0, yValue, 245, 80, matrix.get(1).get(0).getImage());
        window.addImageAtPixel(10, yValue + 15, 40, 40, assetsImg.get(3).getImage());
        window.addTextAtPixel("Black Smith", 55, yValue + 45, matrix2.get(1).get(0), 25f);
        window.addImageAtPixel(245, yValue, 240, 80, matrix.get(1).get(1).getImage());
        window.addImageAtPixel(255, yValue + 15, 40, 40, assetsImg.get(8).getImage());
        window.addTextAtPixel("Magic Shop", 300, yValue + 45, matrix2.get(1).get(1), 25f);
        // forth row
        yValue += 80;
        window.addImageAtPixel(0, yValue, 245, 80, matrix.get(2).get(0).getImage());
        window.addImageAtPixel(10, yValue + 15, 40, 40, assetsImg.get(9).getImage());
        window.addTextAtPixel("Skill Shop", 55, yValue + 45, matrix2.get(2).get(0), 25f);
        window.addImageAtPixel(245, yValue, 240, 80, matrix.get(2).get(1).getImage());
        window.addImageAtPixel(255, yValue + 15, 40, 40, assetsImg.get(10).getImage());
        window.addTextAtPixel("Runes Shop", 300, yValue + 45, matrix2.get(2).get(1), 25f);
        // fifth row
        yValue += 80;
        window.addImageAtPixel(0, yValue, 245, 80, matrix.get(3).get(0).getImage());
        window.addImageAtPixel(10, yValue + 15, 40, 40, assetsImg.get(11).getImage());
        window.addTextAtPixel("Character", 55, yValue + 45, matrix2.get(3).get(0), 25f);
        window.addImageAtPixel(245, yValue, 240, 80, matrix.get(3).get(1).getImage());
        window.addImageAtPixel(255, yValue + 15, 40, 40, assetsImg.get(12).getImage());
        window.addTextAtPixel("World Map", 300, yValue + 45, matrix2.get(3).get(1), 25f);
    }

    private void removeKeyAdaptor() {
        window.removeKeyListener(gameLobbyListener);
    }

    void enterMarket() {
        removeKeyAdaptor();
        window.clearScreen();
        gameMarket = new Market(window, this);
        window.setCurentScene(gameMarket);
    }

    void enterGuild() {
        removeKeyAdaptor();
        window.clearScreen();
        gameGuild = new Guild(window, this);
        window.setCurentScene(gameGuild);
    }

    void enterBlackSmith() {
        removeKeyAdaptor();
        window.clearScreen();
        blackSmith = new Blacksmith(window, this);
        window.setCurentScene(blackSmith);
    }

    void enterMagicShop() {
        removeKeyAdaptor();
        window.clearScreen();
        magicshop = new MagicShop(window, this);
        window.setCurentScene(magicshop);
    }

    void enterSkillShop() {
        removeKeyAdaptor();
        window.clearScreen();
        skillShop = new SkillShop(window, this);
        window.setCurentScene(skillShop);
    }

    void enterRunesShop() {
        removeKeyAdaptor();
        window.clearScreen();
        runesShop = new RunesShop(window, this);
        window.setCurentScene(runesShop);
    }

    void enterCharacter() {
        removeKeyAdaptor();
        window.clearScreen();
        character = new Character(window, this);
        window.setCurentScene(character);
    }

    void enterWorldMap() {
        removeKeyAdaptor();
        window.clearScreen();
        worldMap = new WorldMap(window, this);
        window.setCurentScene(worldMap);
    }

    @Override
    public void display() {
        drawEverything();
    }

    @Override
    public void listenToInput() {
        gameLobbyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleArrowKeys(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (height == 0 && width == 0) {
                        enterGuild();
                    }

                    if (height == 0 && width == 1) {
                        enterMarket();
                    }

                    if (height == 1 && width == 0) {
                        enterBlackSmith();
                    }

                    if (height == 1 && width == 1) {
                        enterMagicShop();
                    }

                    if (height == 2 && width == 0) {
                        enterSkillShop();
                    }

                    if (height == 2 && width == 1) {
                        enterRunesShop();
                    }

                    if (height == 3 && width == 0) {
                        enterCharacter();
                    }

                    if (height == 3 && width == 1) {
                        enterWorldMap();
                    }
                }
            }
        };
        window.addKeyListener(gameLobbyListener);
    }

    private void handleArrowKeys(KeyEvent e) {
        lastw = width;
        lasth = height;
        if (width == -5 && height == -5) {
            width = height = 0;
        } else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    height = ((height - 1) % 4 + 4) % 4;
                    break;
                case KeyEvent.VK_DOWN:
                    height = ((height + 1) % 4);
                    break;
                case KeyEvent.VK_LEFT:
                    width = ((width - 1) % 2 + 2) % 2;
                    break;
                case KeyEvent.VK_RIGHT:
                    width = ((width + 1) % 2);
                    break;
                default:
                    break;
            }
        }
        updateAssets();
    }
}
