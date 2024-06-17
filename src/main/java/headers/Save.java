package headers;


import headers.MainLobby.GameLobby;
import headers.MainLobby.MainTransition;
import headers.Utility.Items;
import headers.Utility.Quests;
import headers.Utility.Skills;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Save implements Scene {
    private Screen window;
    private MainTransition mainTransition;
    private GameLobby lobby;
    private Menu menu;
    private KeyListener keyListener;
    private int where;

    public Save(Screen window, Menu menu) {
        this.window = window;
        this.window .setBackground("assets/mmu.png");
        this.menu = menu;
        this.where = 0;
    }

    public Save(Screen window, GameLobby gamelobby) {
        this.window = window;
        this.window .setBackground("assets/mmu.png");
        this.lobby = gamelobby;
        this.where = 1;
    }

    @Override
    public void display() {
        window.addTextAtPixel("> SaveFile", 160, 500, "WHITE", 25f);
    }

    void removeKeyAdaptor() {
        window.removeKeyListener(keyListener);
    }

    void EnterMenu() {
        removeKeyAdaptor();
        window.clearScreen();
        window.setCurentScene(menu);
    }

    void initMainTransition() {
        mainTransition = new MainTransition(window, 1);
    }

    private void startNewGame() {
        Skills.getInstance();
        Items.getInstance();
        removeKeyAdaptor();
        window.clearScreen();
        initMainTransition();
        window.setCurentScene(mainTransition);
    }

    private void enterLobby() {
        removeKeyAdaptor();
        window.clearScreen();
        window.setBackground("assets/Game Menu/GameMenuBackGround.png");
        window.setCurentScene(lobby);
    }


    @Override
    public void listenToInput() {
        keyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (where == 0) EnterMenu();
                    if (where == 1) enterLobby();
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (where == 0) {
                        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("saves/player.ser"))) {
                            Player player = (Player) ois.readObject();
                            Player.setInstance(player);
                            Player.getInstance().saveSettings();
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("saves/quest.ser"))) {
                            Quests quests = (Quests) ois.readObject();
                            Quests.setInstance(quests);
                        } catch (IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        startNewGame();
                    } else {
                        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("saves/player.ser"))) {
                            oos.writeObject(Player.getInstance());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("saves/quest.ser"))) {
                            oos.writeObject(Quests.getInstance());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        enterLobby();
                    }
                }
            }
        };

        window.addKeyListener(keyListener);
    }
}
