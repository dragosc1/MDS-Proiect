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
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Blacksmith implements Scene {
    private final Screen window;
    private final GameLobby lobby;
    private KeyListener blackSmithListener;
    private ImageIcon back, wood, bar, ic0;
    private ImageIcon holder, ic1, ic2, ic3, ic4, ic5;
    private String currTxt;
    private Integer InventoryH, checkUpWidth, popUpHeight;
    private Boolean checkB, drawB;
    private Boolean checkA, drawA, checkkA, checkkkA;
    private int wblacksmith;

    public Blacksmith(Screen window, GameLobby _lobby) {
        this.window = window;
        this.lobby = _lobby;
        this.window.setBackground("WHITE");
        popUpHeight = 0;
        wblacksmith = -1;
        drawB = checkB = drawA = checkA = checkkA = checkkkA = false;
        InventoryH = checkUpWidth = 0;
        currTxt = "Weapons";
        loadAssets();
    }

    void loadAssets() {
        currTxt = "Weapons";
        back = new ImageIcon("assets/BlackSmith/blacksmith.png");
        wood = new ImageIcon("assets/Market/woodytexturebackground.jpg");
        bar  = new ImageIcon("assets/Market/BrownBlackGround.png");
        ic0 = new ImageIcon("assets/Guild/Cicon.png");
        ic1 = new ImageIcon("assets/BlackSmith/SwordCommonNoTier.png");
        ic1 = modifyColors(ic1);
        ic2 = new ImageIcon("assets/BlackSmith/armorIcon.png");
        ic3 = new ImageIcon("assets/BlackSmith/inventoryicon.png");
        ic4 = new ImageIcon("assets/Market/GoldIcon.png");
        ic5 = new ImageIcon("assets/BlackSmith/BlackSmithIcon.png");
        holder = new ImageIcon("assets/Market/itemholder.png");
    }

    public static ImageIcon modifyColors(ImageIcon icon) {
        Image image = icon.getImage();
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        // Modify colors
        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                int rgb = bufferedImage.getRGB(x, y);
                int alpha = (rgb >> 24) & 0xFF;
                int newRGB = getNewRGB(rgb, alpha);
                bufferedImage.setRGB(x, y, newRGB);
            }
        }

        return new ImageIcon(bufferedImage);
    }

    private static int getNewRGB(int rgb, int alpha) {
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        // Adjust thresholds for identifying light shades
        int blackThreshold = 30; // Increase threshold for identifying black
        int whiteThreshold = 220; // Decrease threshold for identifying white

        // Check if pixel is white
        if (red >= whiteThreshold && green >= whiteThreshold && blue >= whiteThreshold) {
            red = 0; // Set red component to 0
            green = 0; // Set green component to 0
            blue = 0; // Set blue component to 0
        }
        // Check if pixel is black
        else if (red <= blackThreshold && green <= blackThreshold && blue <= blackThreshold) {
            green = 255; // Set green component to max
        }

        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

    synchronized void drawEverything() {
        window.addImageAtPixel(0, 0, 500, 400, back.getImage());

        window.addImageAtPixel(0, 0, 490, 40, bar.getImage());
        window.addImageAtPixel(5, 0, 40, 40, ic0.getImage());
        window.addTextAtPixel("BlackSmith", 55, 30, "WHITE", 25f);
        window.addImageAtPixel(305, 0, 40, 40, ic1.getImage());
        window.addImageAtPixel(355, 0, 40, 40, ic2.getImage());

        window.addImageAtPixel(0, 400, 500, 400, wood.getImage());

        // first row
        int yValue = 400;
        window.addImageAtPixel(0, yValue, 245, 80, holder.getImage());
        window.addImageAtPixel(10, yValue + 20, 40, 40, ic4.getImage());
        window.addTextAtPixel("Gold: " + Player.getInstance().getGold(), 55, yValue + 45, "WHITE", 25f);

        window.addImageAtPixel(245, yValue, 240, 80, holder.getImage());
        window.addImageAtPixel(255, yValue + 20, 40, 40, ic5.getImage());
        window.addTextAtPixel(currTxt, 300, yValue + 45, "WHITE", 25f);

        // second row
        yValue += 120;
        window.addImageAtPixel(0, yValue, 245, 80, holder.getImage());
        window.addImageAtPixel(10, yValue + 20, 40, 40, ic3.getImage());
        window.addTextAtPixel((wblacksmith == 0? ">" : "") + "Space: " + Player.getInstance().getInventorySpace(), 55, yValue + 45, "WHITE", 25f);

        window.addImageAtPixel(245, yValue, 240, 80, holder.getImage());
        window.addTextAtPixel((wblacksmith == 1? ">" : "") + "Supplies", 300, yValue + 45, "WHITE", 25f);

        if (drawA) {
            int popUpYvalue = 40;
            ImageIcon ic1 = new ImageIcon("assets/Character/EmptyIcon.png");
            window.addPopUpAtPixel(150, popUpYvalue, 335, 50, holder.getImage());
            window.addPopUpAtPixel(175, popUpYvalue + 5, 40, 40, ic1.getImage());
            window.addPopUpTextAtPixel((popUpHeight == 0? "> " : "") + "Upgrade " + currTxt, 220, popUpYvalue + 35, (popUpHeight == 0? "GREEN" : "WHITE"), 25f);

            popUpYvalue += 50;
            Items x = Items.getInstance();
            String name = Objects.equals(currTxt, "Weapons")? x.getBSWN(0) : x.getBSAN(0);
            Integer tier = Objects.equals(currTxt, "Weapons")? x.getBSWT(0) : x.getBSAT(0);
            String type = x.getTypeFromName(name);

            int price = Player.getInstance().applyCharisma(x.getPriceT(type, tier));

            if (name.toLowerCase().contains("armour")) name = "Armour";
            if (name.toLowerCase().contains("bow")) name = "Bow";
            if (name.toLowerCase().contains("sword")) name = "Sword";
            if (name.toLowerCase().contains("staff")) name = "Staff";

            window.addPopUpAtPixel(150, popUpYvalue, 335, 50, holder.getImage());
            window.addPopUpAtPixel(175, popUpYvalue + 5, 40, 40, Objects.equals(currTxt, "Weapons")? x.getBSW(0) : x.getBSA(0));
            window.addPopUpTextAtPixel((popUpHeight == 1? "> " : "") + name + "  Price: " + price, 220, popUpYvalue + 35,  (popUpHeight == 1? "GREEN" : "WHITE"), 25f);

            popUpYvalue += 50;
            name = Objects.equals(currTxt, "Weapons")? x.getBSWN(1) : x.getBSAN(1);
            tier = Objects.equals(currTxt, "Weapons")? x.getBSWT(1) : x.getBSAT(1);
            type = x.getTypeFromName(name);

            price = Player.getInstance().applyCharisma(x.getPriceT(type, tier));

            if (name.toLowerCase().contains("armour")) name = "Armour";
            if (name.toLowerCase().contains("bow")) name = "Bow";
            if (name.toLowerCase().contains("sword")) name = "Sword";
            if (name.toLowerCase().contains("staff")) name = "Staff";

            window.addPopUpAtPixel(150, popUpYvalue, 335, 50, holder.getImage());
            window.addPopUpAtPixel(175, popUpYvalue + 5, 40, 40, Objects.equals(currTxt, "Weapons")? x.getBSW(1) : x.getBSA(1));
            window.addPopUpTextAtPixel((popUpHeight == 2? "> " : "") + name + "  Price: " + price, 220, popUpYvalue + 35,  (popUpHeight == 2? "GREEN" : "WHITE"), 25f);

            popUpYvalue += 50;
            name = Objects.equals(currTxt, "Weapons")? x.getBSWN(2) : x.getBSAN(2);
            tier = Objects.equals(currTxt, "Weapons")? x.getBSWT(2) : x.getBSAT(2);
            type = x.getTypeFromName(name);

            price = Player.getInstance().applyCharisma(x.getPriceT(type, tier));

            if (name.toLowerCase().contains("armour")) name = "Armour";
            if (name.toLowerCase().contains("bow")) name = "Bow";
            if (name.toLowerCase().contains("sword")) name = "Sword";
            if (name.toLowerCase().contains("staff")) name = "Staff";

            window.addPopUpAtPixel(150, popUpYvalue, 335, 50, holder.getImage());
            window.addPopUpAtPixel(175, popUpYvalue + 5, 40, 40, Objects.equals(currTxt, "Weapons")? x.getBSW(2) : x.getBSA(2));
            window.addPopUpTextAtPixel((popUpHeight == 3? "> " : "") + name + "  Price: " + price, 220, popUpYvalue + 35,  (popUpHeight == 3? "GREEN" : "WHITE"), 25f);

            popUpYvalue += 50;
            name = Objects.equals(currTxt, "Weapons")? x.getBSWN(3) : x.getBSAN(3);
            tier = Objects.equals(currTxt, "Weapons")? x.getBSWT(3) : x.getBSAT(3);
            type = x.getTypeFromName(name);

            price = Player.getInstance().applyCharisma(x.getPriceT(type, tier));

            if (name.toLowerCase().contains("armour")) name = "Armour";
            if (name.toLowerCase().contains("bow")) name = "Bow";
            if (name.toLowerCase().contains("sword")) name = "Sword";
            if (name.toLowerCase().contains("staff")) name = "Staff";

            window.addPopUpAtPixel(150, popUpYvalue, 335, 50, holder.getImage());
            window.addPopUpAtPixel(175, popUpYvalue + 5, 40, 40, Objects.equals(currTxt, "Weapons")? x.getBSW(3) : x.getBSA(3));
            window.addPopUpTextAtPixel((popUpHeight == 4? "> " : "") + name + "  Price: " + price, 220, popUpYvalue + 35,  (popUpHeight == 4? "GREEN" : "WHITE"), 25f);
        }

        if (checkA) {
            window.addCheckUpAtPixel(0, 40, 490, 400, holder.getImage());
            int popUpYvalue = 100;
            for (int i = -2; i < 3; ++i) {
                int curr = ((InventoryH + i) % 10 + 10) % 10;
                Image x = Player.getInstance().getInventory1Img(curr);
                String name = Player.getInstance().getInventory1Str(curr);
                window.addCheckUpAtPixel(50, popUpYvalue, 410, 50, holder.getImage());
                window.addCheckUpAtPixel(70, popUpYvalue + 5, 40, 40, x);
                window.addCheckUpText((i == 0? "> " : "") + name, 115, popUpYvalue + 35,  (i == 0? "GREEN" : "WHITE"), 25f);
                window.addCheckUpText("Slot: " + (curr + 1), 350, popUpYvalue + 35,  "WHITE", 25f);
                popUpYvalue += 50;
            }
        }

        if (checkkA) {
            String ItemSell = Player.getInstance().getItemName(InventoryH);
            Integer tier = Player.getInstance().getItemTier(InventoryH);
            ++tier;
            int playerGold = Player.getInstance().getGold();
            String type = Items.getInstance().getTypeFromName(ItemSell);
            int price = Player.getInstance().applyCharisma(Items.getInstance().getPrice(type, tier));

            window.addCheckUpAtPixel(0, 40, 490, 400, holder.getImage());
            window.addCheckUpText("Upgrade", 120, 90,  "WHITE", 25f);
            window.addCheckUpText(ItemSell, 230, 90,  "YELLOW", 25f);
            window.addCheckUpText("To tier  " + tier, 210, 120, (playerGold >= price)? "GREEN" : "RED", 25f);
            window.addCheckUpText("For ", 180, 150, "WHITE", 25f);
            window.addCheckUpText(price + " Gold?", 240, 150, (playerGold >= price)? "GREEN" : "RED", 25f);

            window.addCheckUpText((checkUpWidth == 0? ">"  : "")  + "No", 20, 220,  "RED", 25f);
            if (playerGold >= price) window.addCheckUpText((checkUpWidth == 1? ">"  : "") + "Yes", 420, 220,  "GREEN", 25f);
        }

        if (checkkkA) {
            Items x = Items.getInstance();
            String ItemSell = Objects.equals(currTxt, "Weapons")? x.getBSWN(popUpHeight - 1) : x.getBSAN(popUpHeight - 1);
            Integer tier = Objects.equals(currTxt, "Weapons")? x.getBSWT(popUpHeight - 1) : x.getBSAT(popUpHeight - 1);
            int playerGold = Player.getInstance().getGold();
            String type = Items.getInstance().getTypeFromName(ItemSell);
            int price = Player.getInstance().applyCharisma(Items.getInstance().getPriceT(type, tier));

            window.addCheckUpAtPixel(0, 40, 490, 400, holder.getImage());
            window.addCheckUpText("Buy", 120, 90,  "WHITE", 25f);
            window.addCheckUpText(ItemSell, 180, 90,  "YELLOW", 25f);
            window.addCheckUpText("tier  " + tier, 180, 120, (playerGold >= price)? "GREEN" : "RED", 25f);
            window.addCheckUpText("For ", 150, 150, "WHITE", 25f);
            window.addCheckUpText(price + " Gold?", 200, 150, (playerGold >= price)? "GREEN" : "RED", 25f);

            window.addCheckUpText((checkUpWidth == 0? ">"  : "")  + "No", 20, 220,  "RED", 25f);
            if (playerGold >= price && Player.getInstance().getInventorySpace() > 0) window.addCheckUpText((checkUpWidth == 1? ">"  : "") + "Yes", 420, 220,  "GREEN", 25f);
        }

        if (drawB) {
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

        if (checkB) {
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

    private void removeKeyAdaptor() {
        window.removeKeyListener(blackSmithListener);
    }

    private void enterLobby() {
        removeKeyAdaptor();
        window.clearScreen();
        window.setBackground("assets/Game Menu/GameMenuBackGround.png");
        window.setCurentScene(lobby);
    }

    @Override
    public void display() {
        drawEverything();
    }

    @Override
    public void listenToInput() {
        blackSmithListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (checkkkA) {
                        checkkkA = false;
                        checkA = false;
                        drawA = true;
                    }

                    if (checkkA) {
                        checkkA = false;
                        checkA = true;
                        return;
                    }

                    if (checkB) {
                        checkB = false;
                        return;
                    }

                    if (drawB) {
                        drawB = checkB = false;
                        checkUpWidth = 0;
                        wblacksmith = -1;
                        return;
                    }

                    if (checkA) {
                        checkA = false;
                        return;
                    }

                    if (drawA) {
                        drawA = checkA = false;
                        popUpHeight = 0;
                        wblacksmith = -1;
                        return;
                    }

                    enterLobby();
                }

                if (e.getKeyCode() == KeyEvent.VK_1) {
                    currTxt = "Weapons";
                    ic1 = new ImageIcon("assets/BlackSmith/SwordCommonNoTier.png");
                    ic1 = modifyColors(ic1);
                    ic2 = new ImageIcon("assets/BlackSmith/armorIcon.png");
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_2) {
                    currTxt = "Armour";
                    ic1 = new ImageIcon("assets/BlackSmith/SwordCommonNoTier.png");
                    ic2 = new ImageIcon("assets/BlackSmith/armorIcon.png");
                    ic2 = modifyColors(ic2);
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (checkkkA) {
                        return;
                    }

                    if (drawB || checkA) {
                        InventoryH = ((InventoryH - 1) % 10 + 10) % 10;
                        return;
                    }

                    if (drawA) {
                        popUpHeight = ((popUpHeight - 1) % 5 + 5) % 5;
                        return;
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (checkkkA) {
                        return;
                    }

                    if (drawB || checkA) {
                        InventoryH = (InventoryH + 1) % 10;
                        return;
                    }


                    if (drawA) {
                        popUpHeight = (popUpHeight + 1) % 5;
                        return;
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (checkB || checkkA || checkkkA) {
                        checkUpWidth = ((checkUpWidth - 1) % 2 + 2) % 2;
                        return;
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (checkB || checkkA || checkkkA) {
                        checkUpWidth = (checkUpWidth + 1) % 2;
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_I) {
                    wblacksmith = 0;
                    drawB = true;
                    checkA = drawA = checkkA = checkkkA = false;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_B) {
                    drawA = true;
                    wblacksmith = 1;
                    drawB = checkB = false;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && drawA && !checkkA && !checkkkA && popUpHeight > 0) {
                    checkkkA = true;
                    checkA = false;
                    checkUpWidth = 0;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && drawA && !checkA && checkkkA && checkUpWidth == 0) {
                    checkkkA = false;
                    checkA = false;
                    checkUpWidth = 0;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && drawA && !checkA && checkkkA && checkUpWidth == 1) {
                    Items x = Items.getInstance();
                    String ItemSell = Objects.equals(currTxt, "Weapons")? x.getBSWN(popUpHeight - 1) : x.getBSAN(popUpHeight - 1);
                    Integer tier = Objects.equals(currTxt, "Weapons")? x.getBSWT(popUpHeight - 1) : x.getBSAT(popUpHeight - 1);
                    int playerGold = Player.getInstance().getGold();
                    String type = Items.getInstance().getTypeFromName(ItemSell);
                    int price = Player.getInstance().applyCharisma(Items.getInstance().getPriceT(type, tier));
                    if (playerGold < price || Player.getInstance().getInventorySpace() <= 0) {
                        return;
                    }

                    Image upItem = Items.getInstance().getImagefromtype(type, ItemSell, tier);
                    Player.getInstance().subtractFromGold(price);
                    Player.getInstance().addItem1(upItem, ItemSell, price / 2, tier);
                    checkkkA = checkkA = false;
                    checkA = false;
                    drawA = true;
                    checkUpWidth = 0;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && drawA && !checkA && checkkA && checkUpWidth == 0) {
                    checkkA = false;
                    checkA = true;
                    checkUpWidth = 0;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && drawA && !checkA && checkkA && checkUpWidth == 1) {
                    String ItemSell = Player.getInstance().getItemName(InventoryH);
                    Integer tier = Player.getInstance().getItemTier(InventoryH);
                    ++tier;
                    int playerGold = Player.getInstance().getGold();
                    String type = Items.getInstance().getTypeFromName(ItemSell);
                    int price = Player.getInstance().applyCharisma(Items.getInstance().getPrice(type, tier));
                    if (playerGold < price) {
                        return;
                    }

                    Image upItem = Items.getInstance().getImagefromtype(type, ItemSell, tier);
                    Integer currPrice = Player.getInstance().getItemPrice(InventoryH);

                    Player.getInstance().subtractFromGold(price);
                    Player.getInstance().upgradeItem1(InventoryH, upItem, ItemSell, currPrice + price / 2, tier);
                    checkkA = false;
                    checkA = true;
                    checkUpWidth = 0;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && drawA && popUpHeight == 0 && !checkA) {
                    checkA = true;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && drawA && checkA) {
                    if (Objects.equals(Player.getInstance().getInventory1Str(InventoryH), "Empty")) {
                        return;
                    }

                    checkkA |= (Objects.equals(currTxt, "Armour") && Player.getInstance().getInventory1Str(InventoryH).toLowerCase().contains("armour") && Player.getInstance().getItemTier(InventoryH) != 10);
                    checkkA |= (!Objects.equals(currTxt, "Armour") && !Player.getInstance().getInventory1Str(InventoryH).toLowerCase().contains("armour") && Player.getInstance().getItemTier(InventoryH) != 10);
                    if (checkkA) checkA = false;
                    checkUpWidth = 0;
                    return;
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

        window.addKeyListener(blackSmithListener);
    }
}
