package headers.MainLobby;

import headers.Player;
import headers.Scene;
import headers.Screen;
import headers.Utility.Items;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;


public class MagicShop implements Scene {
    private final Screen window; // The window where the scene will be displayed
    private final GameLobby lobby; // Reference to the game lobby for scene transitions
    private KeyListener magicShopListener; // Key listener for handling user inputs
    private ImageIcon back, wood, bar, ic0, holder, ic1, ic2, ic3; // Images used in the UI
    private Boolean drawA, drawB, checkB, checkA; // Booleans to manage different UI states
    private Integer magicWidth, inventoryH, checkUpWidth, popUpHeight; // Variables for managing UI navigation

    // Constructor to initialize the MagicShop scene
    public MagicShop(Screen window, GameLobby _lobby) {
        this.window = window;
        this.lobby = _lobby;
        this.window.setBackground("WHITE");
        drawA = drawB = checkB = checkA = false;
        magicWidth = -1;
        inventoryH = checkUpWidth = popUpHeight = 0;
        loadAssets();
    }

    // Method to load assets required for the scene
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


    // Method to draw all UI elements on the screen
    void drawEverything() {
        window.addImageAtPixel(0, 0, 500, 400, back.getImage());

        window.addImageAtPixel(0, 0, 490, 40, bar.getImage());
        window.addImageAtPixel(5, 0, 40, 40, ic0.getImage());
        window.addTextAtPixel("Magic Shop", 55, 30, "WHITE", 25f);

        window.addImageAtPixel(0, 400, 500, 400, wood.getImage());

        // first row
        int yValue = 400;
        window.addImageAtPixel(0, yValue, 245, 80, holder.getImage());
        window.addImageAtPixel(10, yValue + 20, 40, 40, ic2.getImage());
        window.addTextAtPixel("Gold: " + Player.getInstance().getGold(), 55, yValue + 45, "WHITE", 25f);

        window.addImageAtPixel(245, yValue, 240, 80, holder.getImage());
        window.addImageAtPixel(255, yValue + 20, 40, 40, ic3.getImage());
        window.addTextAtPixel("Potions", 300, yValue + 45, "WHITE", 25f);

        // second row
        yValue += 120;
        window.addImageAtPixel(0, yValue, 245, 80, holder.getImage());
        window.addImageAtPixel(10, yValue + 20, 40, 40, ic1.getImage());
        window.addTextAtPixel( (magicWidth == 0? ">" : "") + "Space: " + Player.getInstance().getPotionsSpace(), 55, yValue + 45, "WHITE", 25f);

        window.addImageAtPixel(245, yValue, 240, 80, holder.getImage());
        window.addTextAtPixel((magicWidth == 1? ">" : "") + "Supplies", 300, yValue + 45, "WHITE", 25f);

        // Drawing popup if drawA is true
        if (drawA) {
            Items x = Items.getInstance();
            int popUpYvalue = 40;
            window.addPopUpAtPixel(150, popUpYvalue, 335, 50, holder.getImage());
            window.addPopUpAtPixel(175, popUpYvalue + 5, 40, 40, x.getPotion(0));
            window.addPopUpTextAtPixel((popUpHeight == 0? "> " : "") + x.getNamePotion(0), 220, popUpYvalue + 35, (popUpHeight == 0? "GREEN" : "WHITE"), 25f);

            popUpYvalue += 50;
            window.addPopUpAtPixel(150, popUpYvalue, 335, 50, holder.getImage());
            window.addPopUpAtPixel(175, popUpYvalue + 5, 40, 40, x.getPotion(1));
            window.addPopUpTextAtPixel((popUpHeight == 1? "> " : "") + x.getNamePotion(1), 220, popUpYvalue + 35,  (popUpHeight == 1? "GREEN" : "WHITE"), 25f);

            popUpYvalue += 50;
            window.addPopUpAtPixel(150, popUpYvalue, 335, 50, holder.getImage());
            window.addPopUpAtPixel(175, popUpYvalue + 5, 40, 40, x.getPotion(2));
            window.addPopUpTextAtPixel((popUpHeight == 2? "> " : "") + x.getNamePotion(2), 220, popUpYvalue + 35,  (popUpHeight == 2? "GREEN" : "WHITE"), 25f);

            popUpYvalue += 50;
            window.addPopUpAtPixel(150, popUpYvalue, 335, 50, holder.getImage());
            window.addPopUpAtPixel(175, popUpYvalue + 5, 40, 40, x.getPotion(3));
            window.addPopUpTextAtPixel((popUpHeight == 3? "> " : "") + x.getNamePotion(3), 220, popUpYvalue + 35,  (popUpHeight == 3? "GREEN" : "WHITE"), 25f);

            popUpYvalue += 50;
            window.addPopUpAtPixel(150, popUpYvalue, 335, 50, holder.getImage());
            window.addPopUpAtPixel(175, popUpYvalue + 5, 40, 40, x.getPotion(4));
            window.addPopUpTextAtPixel((popUpHeight == 4? "> " : "") + x.getNamePotion(4), 220, popUpYvalue + 35,  (popUpHeight == 4? "GREEN" : "WHITE"), 25f);

            popUpYvalue += 50;
            window.addPopUpAtPixel(150, popUpYvalue, 335, 50, holder.getImage());
            window.addPopUpAtPixel(175, popUpYvalue + 5, 40, 40, x.getPotion(5));
            window.addPopUpTextAtPixel((popUpHeight == 5? "> " : "") + x.getNamePotion(5), 220, popUpYvalue + 35,  (popUpHeight == 5? "GREEN" : "WHITE"), 25f);

            popUpYvalue += 50;
            window.addPopUpAtPixel(150, popUpYvalue, 335, 50, holder.getImage());
            window.addPopUpAtPixel(175, popUpYvalue + 5, 40, 40, x.getPotion(6));
            window.addPopUpTextAtPixel((popUpHeight == 6? "> " : "") + x.getNamePotion(6), 220, popUpYvalue + 35,  (popUpHeight == 6? "GREEN" : "WHITE"), 25f);
        }

        // Drawing confirmation dialog if checkA is true
        if (checkA) {
            String pname = Items.getInstance().getNamePotion(popUpHeight);
            Integer price = Items.getInstance().getPricePotions(popUpHeight);
            Integer currGold = Player.getInstance().applyCharisma(Player.getInstance().getGold());

            window.addCheckUpAtPixel(0, 40, 490, 400, holder.getImage());
            window.addCheckUpText("Buy ", 150, 90,  "WHITE", 25f);
            window.addCheckUpText(pname, 210, 90,  "RED", 25f);
            window.addCheckUpText("For ", 180, 120,  "WHITE", 25f);
            window.addCheckUpText(price + " Gold?", 240, 120,  "GREEN", 25f);

            window.addCheckUpText(Items.getInstance().getDescPotion(popUpHeight), 10, 170,  "WHITE", 25f);


            window.addCheckUpText((checkUpWidth == 0? ">"  : "")  + "No", 20, 220,  "RED", 25f);
            if (price <= currGold && Player.getInstance().getPotionsSpace() > 0) window.addCheckUpText((checkUpWidth == 1? ">"  : "") + "Yes", 420, 220,  "GREEN", 25f);
        }

        // Drawing inventory if drawB is true
        if (drawB) {
            int popUpYvalue = 40;
            for (int i = -2; i < 3; ++i) {
                int curr = ((inventoryH + i) % 10 + 10) % 10;
                Image x = Player.getInstance().getInventory2Img(curr);
                String name = Player.getInstance().getInventory2Str(curr);
                window.addPopUpAtPixel(5, popUpYvalue, 410, 50, holder.getImage());
                window.addPopUpAtPixel(25, popUpYvalue + 5, 40, 40, x);
                window.addPopUpTextAtPixel((i == 0? "> " : "") + name, 75, popUpYvalue + 35,  (i == 0? "GREEN" : "WHITE"), 25f);
                window.addPopUpTextAtPixel("Slot: " + (curr + 1), 280, popUpYvalue + 35,  "WHITE", 25f);
                popUpYvalue += 50;
            }
        }

        // Drawing confirmation dialog if checkB is true
        if (checkB) {
            String ItemSell = Player.getInstance().getItem2Name(inventoryH);
            Integer price = Player.getInstance().getItem2Price(inventoryH);

            window.addCheckUpAtPixel(0, 40, 490, 400, holder.getImage());
            window.addCheckUpText("Sell ", 150, 90,  "WHITE", 25f);
            window.addCheckUpText(ItemSell, 210, 90,  "RED", 25f);
            window.addCheckUpText("For ", 180, 120,  "WHITE", 25f);
            window.addCheckUpText(price + " Gold?", 240, 120,  "GREEN", 25f);

            window.addCheckUpText((checkUpWidth == 0? ">"  : "")  + "No", 20, 220,  "RED", 25f);
            window.addCheckUpText((checkUpWidth == 1? ">"  : "") + "Yes", 420, 220,  "GREEN", 25f);
        }
    }

    // Method to remove the key listener
    private void removeKeyAdaptor() {
        window.removeKeyListener(magicShopListener);
    }

    // Method to transition back to the lobby
    private void enterLobby() {
        removeKeyAdaptor();
        window.clearScreen();
        window.setBackground("assets/Game Menu/GameMenuBackGround.png");
        window.setCurentScene(lobby);
    }

    // Method to display the scene
    @Override
    public void display() {
        drawEverything();
    }

    // Method to listen to user inputs
    @Override
    public void listenToInput() {
        magicShopListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (checkA) {
                        checkA = false;
                        drawA = true;
                        return;
                    }
                    if (checkB) {
                        checkB = false;
                        drawA = true;
                        return;
                    }

                    if (drawA) {
                        drawA = false;
                        magicWidth = -1;
                        return;
                    }

                    if (drawB) {
                        drawB = false;
                        magicWidth = -1;
                        return;
                    }

                    enterLobby();
                }

                if (e.getKeyCode()== KeyEvent.VK_B) {
                    drawA = true;
                    drawB = checkB = false;
                    magicWidth = 1;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_I) {
                    drawA = checkA = false;
                    drawB = true;
                    magicWidth = 0;
                }

                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (checkB || checkA) {
                        checkUpWidth = ((checkUpWidth - 1) % 2 + 2) % 2;
                        return;
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (checkB || checkA) {
                        checkUpWidth = (checkUpWidth + 1) % 2;
                        return;
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (checkA || checkB) {
                        return;
                    }

                    if (drawB) {
                        inventoryH = ((inventoryH - 1) % 10 + 10) % 10;
                        return;
                    }
                    if (drawA) {
                        popUpHeight = ((popUpHeight - 1) % 7 + 7) % 7;
                        return;
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (checkA || checkB) {
                        return;
                    }

                    if (drawB) {
                        inventoryH = (inventoryH + 1) % 10;
                        return;
                    }

                    if (drawA) {
                        popUpHeight = (popUpHeight + 1) % 7;
                        return;
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && drawA && checkA && checkUpWidth == 0) {
                    checkA = false;
                    drawA = true;
                    checkUpWidth = 0;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && drawA && checkA && checkUpWidth == 1) {
                    checkA = false;
                    drawA = true;
                    checkUpWidth = 0;
                    String pname = Items.getInstance().getNamePotion(popUpHeight);
                    int price = Player.getInstance().applyCharisma(Items.getInstance().getPricePotions(popUpHeight));
                    int currGold = Player.getInstance().getGold();
                    if (price > currGold || Player.getInstance().getPotionsSpace() <= 0) return;

                    Player.getInstance().addItem2(Items.getInstance().getPotion(popUpHeight), pname, price / 2);
                    Player.getInstance().subtractFromGold(price);
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && drawA && !checkA) {
                    checkA = true;
                    checkUpWidth = 0;
                    return;
                }

                if(e.getKeyCode() == KeyEvent.VK_ENTER && drawB && checkB && checkUpWidth == 0) {
                    checkB = false;
                    checkUpWidth = 0;
                    return;
                }

                if(e.getKeyCode() == KeyEvent.VK_ENTER && drawB && checkB && checkUpWidth == 1) {
                    Player.getInstance().subtractFromGold(-Player.getInstance().getItem2Price(inventoryH));
                    Player.getInstance().remItem2(inventoryH);
                    checkB = false;
                    checkUpWidth = 0;
                    return;
                }

                if(e.getKeyCode() == KeyEvent.VK_ENTER && drawB) {
                    checkB |= (!Objects.equals(Player.getInstance().getInventory2Str(inventoryH), "Empty"));
                    return;
                }

            }
        };
        window.addKeyListener(magicShopListener);
    }
}
