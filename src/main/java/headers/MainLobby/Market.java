package headers.MainLobby;

import headers.Player;
import headers.Scene;
import headers.Screen;
import headers.Utility.Items;

import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.ImageIcon;

public class Market implements Scene {
    private final Screen window;         // The screen on which the market interface is displayed
    private final GameLobby lobby;       // Reference to the game lobby
    private KeyListener marketKeyListener;  // Listener for keyboard input in the market scene
    private ImageIcon back, wood, bar, ic0;  // Image icons for various background elements
    private ImageIcon holder, ic1, ic2, ic3; // Image icons for UI elements
    private Boolean drawA, drawB;          // Booleans to control drawing different parts of the market interface
    private Boolean checkA, checkB;        // Booleans to control checking different actions in the market
    private Integer popUpHeight, checkUpWidth;  // Heights and widths of various pop-up interfaces
    private Integer InventoryH;            // Current inventory selection index

    // Constructor
    public Market(Screen window, GameLobby lobby) {
        this.lobby = lobby;
        this.window = window;
        // Initialize booleans
        drawA = checkA = drawB = checkB = false;
        // Initialize integers
        popUpHeight = checkUpWidth = InventoryH = 0;
        // Load image assets
        loadAssets();
    }

    // Loading assets
    private void loadAssets() {
        back = new ImageIcon("assets/Market/market.jpg");
        wood = new ImageIcon("assets/Market/woodytexturebackground.jpg");
        bar  = new ImageIcon("assets/Market/BrownBlackGround.png");
        ic0 = new ImageIcon("assets/Market/Cicon.png");
        holder = new ImageIcon("assets/Market/itemholder.png");
        ic1 = new ImageIcon("assets/Market/inventoryicon.png");
        ic2 = new ImageIcon("assets/Market/GoldIcon.png");
        ic3 = new ImageIcon("assets/Market/25SuppliesIcon.png");
    }

    // Draw everything on the screen
    void drawEverything() {
        // Background images
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
        window.addTextAtPixel("Gold: " + Player.getInstance().getGold(), 300, yValue + 45, "WHITE", 25f);

        // second row
        yValue += 120;
        window.addImageAtPixel(0, yValue, 245, 80, holder.getImage());
        window.addTextAtPixel((drawB? "> " : "") + "Space: " + Player.getInstance().getInventorySpace(), 55, yValue + 45, "WHITE", 25f);

        window.addImageAtPixel(245, yValue, 240, 80, holder.getImage());
        window.addTextAtPixel((drawA? "> " : "")+ "Supplies", 300, yValue + 45, "WHITE", 25f);

        if (drawA) {
            // Draw additional items
            Items x = Items.getInstance();
            int popUpYvalue = 40;
            int priceX = Player.getInstance().applyCharisma(250);
            int priceY = Player.getInstance().applyCharisma(25);

            // Add item pop-ups
            window.addPopUpAtPixel(150, popUpYvalue, 335, 50, holder.getImage());
            window.addPopUpAtPixel(175, popUpYvalue + 5, 40, 40, x.getSwordCommonTier(0));
            window.addPopUpTextAtPixel((popUpHeight == 0? "> " : "") + "Sword      Price: " + priceX, 220, popUpYvalue + 35, (popUpHeight == 0? "GREEN" : "WHITE"), 25f);

            popUpYvalue += 50;
            window.addPopUpAtPixel(150, popUpYvalue, 335, 50, holder.getImage());
            window.addPopUpAtPixel(175, popUpYvalue + 5, 40, 40, x.getStaffCommonTier(0));
            window.addPopUpTextAtPixel((popUpHeight == 1? "> " : "") + "Staff        Price: " + priceX, 220, popUpYvalue + 35,  (popUpHeight == 1? "GREEN" : "WHITE"), 25f);

            popUpYvalue += 50;
            window.addPopUpAtPixel(150, popUpYvalue, 335, 50, holder.getImage());
            window.addPopUpAtPixel(175, popUpYvalue + 5, 40, 40, x.getBowCommonTier(0));
            window.addPopUpTextAtPixel((popUpHeight == 2? "> " : "") + "Bow         Price: " + priceX, 220, popUpYvalue + 35,  (popUpHeight == 2? "GREEN" : "WHITE"), 25f);

            popUpYvalue += 50;
            window.addPopUpAtPixel(150, popUpYvalue, 335, 50, holder.getImage());
            window.addPopUpAtPixel(175, popUpYvalue + 5, 40, 40, x.getArmourCommonTier(0));
            window.addPopUpTextAtPixel((popUpHeight == 3? "> " : "") + "Armour     Price: " + priceX, 220, popUpYvalue + 35,  (popUpHeight == 3? "GREEN" : "WHITE"), 25f);

            popUpYvalue += 50;
            window.addPopUpAtPixel(150, popUpYvalue, 335, 50, holder.getImage());
            window.addPopUpAtPixel(175, popUpYvalue + 5, 40, 40, ic3.getImage());
            window.addPopUpTextAtPixel((popUpHeight == 4? "> " : "") + "Supplies   Price: " + priceY, 220, popUpYvalue + 35,  (popUpHeight == 4? "GREEN" : "WHITE"), 25f);
        }

        if (drawB) {
            // Draw inventory items
            int popUpYvalue = 40;
            for (int i = -2; i < 3; ++i) {
                int curr = ((InventoryH + i) % 10 + 10) % 10;
                Image x = Player.getInstance().getInventory1Img(curr);
                String name = Player.getInstance().getInventory1Str(curr);
                window.addPopUpAtPixel(5, popUpYvalue, 410, 50, holder.getImage());
                window.addPopUpAtPixel(25, popUpYvalue + 5, 40, 40, x);
                window.addPopUpTextAtPixel((i == 0? "> " : "") + name, 75, popUpYvalue + 35,  (i == 0? "GREEN" : "WHITE"), 25f);
                window.addPopUpTextAtPixel("Slot: " + (curr + 1), 280, popUpYvalue + 35,  "WHITE", 25f);
                popUpYvalue += 50;
            }
        }

        if (checkA) {
            // Draw confirmation popup for checkA
            String itemBuy = "";
            int price = 0;
            switch (popUpHeight) {
                case 0:
                    itemBuy = "Common Sword";
                    price = Player.getInstance().applyCharisma(250);
                    break;
                case 1:
                    itemBuy = "Common Staff";
                    price = Player.getInstance().applyCharisma(250);
                    break;
                case 2:
                    itemBuy = "Common Bow";
                    price = Player.getInstance().applyCharisma(250);
                    break;
                case 3:
                    itemBuy = "Common Armour";
                    price = Player.getInstance().applyCharisma(250);
                    break;
                case 4:
                    itemBuy = "25 Supplies";
                    price = Player.getInstance().applyCharisma(25);
                    break;
            }

            window.addCheckUpAtPixel(0, 40, 490, 400, holder.getImage());
            window.addCheckUpText("Buy ", 150, 90,  "WHITE", 25f);
            window.addCheckUpText(itemBuy, 200, 90,  "YELLOW", 25f);
            window.addCheckUpText("For ", 180, 120,  "WHITE", 25f);
            window.addCheckUpText(price + " Gold?", 240, 120,  "GREEN", 25f);

            window.addCheckUpText((checkUpWidth == 0? ">"  : "") + "No", 20, 220,  "RED", 25f);
            window.addCheckUpText((checkUpWidth == 1? ">"  : "") + "Yes", 420, 220,  "GREEN", 25f);
        }

        if (checkB) {
            // Draw confirmation popup for checkB
            String ItemSell = Player.getInstance().getItemName(InventoryH);
            Integer price = Player.getInstance().getItemPrice(InventoryH);

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
        window.removeKeyListener(marketKeyListener);
    }

    // Method to enter the lobby
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
        marketKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (checkB) {
                        checkB = false;
                        return;
                    }

                    if (drawB) {
                        drawB = false;
                        return;
                    }

                    if (checkA) {
                        checkA = false;
                        checkUpWidth = 0;
                        return;
                    }

                    if (drawA) {
                        drawA = checkA = false;
                        checkUpWidth = 0;
                        return;
                    }

                    enterLobby();
                }

                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (drawB) {
                        InventoryH = ((InventoryH - 1) % 10 + 10) % 10;
                        return;
                    }

                    if (checkA) {
                        return;
                    }

                    if (drawA) {
                        popUpHeight = ((popUpHeight - 1) % 5 + 5) % 5;
                        return;
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (drawB) {
                        InventoryH = (InventoryH + 1) % 10;
                        return;
                    }

                    if (checkA) {
                        return;
                    }

                    if (drawA) {
                        popUpHeight = (popUpHeight + 1) % 5;
                        return;
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (checkA) {
                        checkUpWidth = ((checkUpWidth - 1) % 2 + 2) % 2;
                        return;
                    }

                    if (checkB) {
                        checkUpWidth = ((checkUpWidth - 1) % 2 + 2) % 2;
                        return;
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (checkA) {
                        checkUpWidth = (checkUpWidth + 1) % 2;
                        return;
                    }

                    if (checkB) {
                        checkUpWidth = (checkUpWidth + 1) % 2;
                        return;
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_B) {
                    drawA = true;
                    drawB = checkB = false;
                    checkUpWidth = 0;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_I) {
                    drawA = checkA = false;
                    drawB = true;
                    checkUpWidth = 0;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && drawA && !checkA) {
                    checkA = (popUpHeight == 0 && Player.getInstance().getGold() >= 250 && Player.getInstance().getInventorySpace() != 0);
                    checkA |= (popUpHeight == 1 && Player.getInstance().getGold() >= 250 && Player.getInstance().getInventorySpace() != 0);
                    checkA |= (popUpHeight == 2 && Player.getInstance().getGold() >= 250 && Player.getInstance().getInventorySpace() != 0);
                    checkA |= (popUpHeight == 3 && Player.getInstance().getGold() >= 250 && Player.getInstance().getInventorySpace() != 0);
                    checkA |= (popUpHeight == 4 && Player.getInstance().getGold() >= 25 && Player.getInstance().getSupplies() != 100);
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && drawA) {
                    if (popUpHeight == 0) {
                        checkA = false;
                        if (checkUpWidth == 1) {
                            Player.getInstance().addItem1(Items.getInstance().getSwordCommonTier(0), "Common Sword", 125, 0);
                            Player.getInstance().subtractFromGold(Player.getInstance().applyCharisma(250));
                        }
                        checkUpWidth = 0;
                    }

                    if (popUpHeight == 1) {
                        checkA = false;
                        if (checkUpWidth == 1) {
                            Player.getInstance().addItem1(Items.getInstance().getStaffCommonTier(0), "Common Staff", 125, 0);
                            Player.getInstance().subtractFromGold(Player.getInstance().applyCharisma(250));
                        }
                        checkUpWidth = 0;
                    }

                    if (popUpHeight == 2) {
                        checkA = false;
                        if (checkUpWidth == 1) {
                            Player.getInstance().addItem1(Items.getInstance().getBowCommonTier(0), "Common Bow", 125,0);
                            Player.getInstance().subtractFromGold(Player.getInstance().applyCharisma(250));
                        }
                        checkUpWidth = 0;
                    }

                    if (popUpHeight == 3) {
                        checkA = false;
                        if (checkUpWidth == 1) {
                            Player.getInstance().addItem1(Items.getInstance().getArmourCommonTier(0), "Common Armour", 125, 0);
                            Player.getInstance().subtractFromGold(Player.getInstance().applyCharisma(250));
                        }
                        checkUpWidth = 0;
                    }

                    if (popUpHeight == 4) {
                        checkA = false;
                        if (checkUpWidth == 1) {
                            Player.getInstance().addSupplies(25);
                            Player.getInstance().subtractFromGold(Player.getInstance().applyCharisma(25));
                        }
                        checkUpWidth = 0;
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && drawB && !checkB) {
                    checkB = (!Objects.equals(Player.getInstance().getInventory1Str(InventoryH), "Empty"));
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && drawB && checkUpWidth == 0) {
                    checkB = false;
                    checkUpWidth = 0;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && drawB && checkUpWidth == 1) {
                    Player.getInstance().subtractFromGold(-Player.getInstance().getItemPrice(InventoryH));
                    Player.getInstance().remItem1(InventoryH);
                    checkB = false;
                    checkUpWidth = 0;
                }
            }
        };

        window.addKeyListener(marketKeyListener);
    }
}
