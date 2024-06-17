package headers.MainLobby;

import headers.Player;
import headers.Scene;
import headers.Screen;
import headers.Utility.Quests;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;

public class Character implements Scene {
    private final Screen window;
    private final GameLobby lobby;
    private KeyListener characterKeyListener;
    private ImageIcon back, wood, bar, ic0;
    private ImageIcon holder, ic1, ic2, ic3, ic4;
    private ImageIcon ic5, ic6, ic7, ic8, ic9;
    private ImageIcon ic10, ic11, ic12, ic13;
    private Integer Inv;
    private Integer InventoryH, checkUpWidth;
    private boolean checkup;

    public Character(Screen window, GameLobby _lobby) {
        Inv = InventoryH = -1;
        checkUpWidth = -1;
        checkup = false;
        this.window = window;
        this.lobby = _lobby;
        this.window.setBackground("BLACK");
        loadAssets();
    }

    void loadAssets() {
        back = new ImageIcon("assets/Character/Chback.png");
        wood = new ImageIcon("assets/Market/woodytexturebackground.jpg");
        bar  = new ImageIcon("assets/Market/BrownBlackGround.png");
        ic0 = new ImageIcon("assets/Market/Cicon.png");
        holder = new ImageIcon("assets/Market/itemholder.png");
        ic1 = new ImageIcon("assets/Character/EmptyIcon.png");
        ic2 = new ImageIcon("assets/Character/HealthPointsIcon.png");
        ic3 = new ImageIcon("assets/Character/AttackDamageIcon.png");
        ic4 = new ImageIcon("assets/Character/AbilityPowerIcon.png");
        ic3 = new ImageIcon("assets/Character/AttackDamageIcon.png");
        ic4 = new ImageIcon("assets/Character/AbilityPowerIcon.png");
        ic5 = new ImageIcon("assets/Character/CritRateIcon.png");
        ic6 = new ImageIcon("assets/Character/CritDamageIcon.png");
        ic7 = new ImageIcon("assets/Market/inventoryicon.png");
        ic8 = new ImageIcon("assets/Market/GoldIcon.png");
        ic9 = new ImageIcon("assets/Game Menu/SuppliesIcon.png");
        ic10 = new ImageIcon("assets/Rune Stone Shop/StrengthIcon.png");
        ic11 = new ImageIcon("assets/Rune Stone Shop/DexterityIcon.png");
        ic12 = new ImageIcon("assets/Rune Stone Shop/IntelectIcon.png");
        ic13 = new ImageIcon("assets/Rune Stone Shop/CharismaIcon.png");
    }

    private void removeKeyAdaptor() {
        window.removeKeyListener(characterKeyListener);
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
        window.addTextAtPixel("Character", 55, 30, "WHITE", 25f);

        window.addImageAtPixel(0, 400, 500, 400, wood.getImage());

        int yValue = 50;
        window.addImageAtPixel(10, yValue, 40, 40, Player.getInstance().getTrait().getImg());
        window.addTextAtPixel("Trait: " +  Player.getInstance().getTrait().getName(), 55, yValue + 30, "WHITE", 25f);

        window.addImageAtPixel(300, yValue, 40, 40, ic2.getImage());
        window.addTextAtPixel("Hp: " + Player.getInstance().getHp(), 345, yValue + 30, "WHITE", 25f);

        yValue += 50;
        window.addImageAtPixel(10, yValue, 40, 40, ic3.getImage());
        window.addTextAtPixel("Ad: " + Player.getInstance().getAd(), 55, yValue + 30, "WHITE", 25f);

        window.addImageAtPixel(300, yValue, 40, 40, ic4.getImage());
        window.addTextAtPixel("Ap: " + Player.getInstance().getAp(), 345, yValue + 30, "WHITE", 25f);

        yValue += 50;
        window.addImageAtPixel(10, yValue, 40, 40, ic5.getImage());
        window.addTextAtPixel("Arm: " + Player.getInstance().getArm(), 55, yValue + 30, "WHITE", 25f);

        window.addImageAtPixel(300, yValue, 40, 40, ic6.getImage());
        window.addTextAtPixel("Mr: " + Player.getInstance().getArm(), 345, yValue + 30, "WHITE", 25f);

        yValue += 50;
        window.addImageAtPixel(10, yValue, 40, 40, Player.getInstance().getHeldWeapon());
        window.addTextAtPixel("Weapon", 55, yValue + 30, "WHITE", 25f);

        window.addImageAtPixel(300, yValue, 40, 40, Player.getInstance().getHeldArmour());
        window.addTextAtPixel("Armour", 345, yValue + 30, "WHITE", 25f);

        yValue += 100;
        window.addImageAtPixel(20, yValue - 15, 30, 30, ic10.getImage());
        window.addTextAtPixel("Str: " + Player.getInstance().getStatus(0), 60, yValue + 10, "WHITE", 25f);
        window.addImageAtPixel(305, yValue - 15, 30, 30, ic11.getImage());
        window.addTextAtPixel("Dex: " + Player.getInstance().getStatus(1), 345, yValue + 10, "WHITE", 25f);
        yValue += 30;
        window.addImageAtPixel(20, yValue - 15, 30, 30, ic12.getImage());
        window.addTextAtPixel("Int: " + Player.getInstance().getStatus(2), 60, yValue + 10, "WHITE", 25f);
        window.addImageAtPixel(305, yValue - 15, 30, 30, ic13.getImage());
        window.addTextAtPixel("Cha: " + Player.getInstance().getStatus(3), 345, yValue + 10, "WHITE", 25f);

        yValue = 400;
        window.addImageAtPixel(0, yValue, 490, 80, holder.getImage());
        window.addImageAtPixel(10, yValue + 20, 40, 40, ic7.getImage());
        window.addTextAtPixel("Gold: " + Player.getInstance().getGold(), 55, yValue + 45, "WHITE", 25f);

        yValue += 120;
        int value = 10;
        if (Inv == 2) value = Quests.getInstance().getLimit();

        window.addImageAtPixel(0, yValue, 245, 80, holder.getImage());
        window.addImageAtPixel(10, yValue + 20, 40, 40, ic7.getImage());
        window.addTextAtPixel((Inv != -1? ">" : "") + "Inventory " + (Inv == -1? 1 : Inv + 1) + ": " + value, 55, yValue + 45, "WHITE", 25f);

        window.addImageAtPixel(245, yValue, 240, 80, holder.getImage());
        window.addImageAtPixel(255, yValue + 20, 40, 40, ic9.getImage());
        window.addTextAtPixel("Supplies: " + Player.getInstance().getSupplies(), 300, yValue + 45, "WHITE", 25f);

        if (InventoryH != -1 && Inv == 0) {
            int popUpYvalue = 40;
            for (int i = -2; i < 3; ++i) {
                int curr = ((InventoryH + i) % value + value) % value;
                Image x = Player.getInstance().getInventory1Img(curr);
                String name = Player.getInstance().getInventory1Str(curr);
                window.addPopUpAtPixel(5, popUpYvalue, 410, 50, holder.getImage());
                window.addPopUpAtPixel(25, popUpYvalue + 5, 40, 40, x);
                window.addPopUpTextAtPixel((i == 0? "> " : "") + name, 75, popUpYvalue + 35,  (i == 0? "GREEN" : "WHITE"), 25f);
                window.addPopUpTextAtPixel("Slot: " + (curr + 1), 280, popUpYvalue + 35,  "WHITE", 25f);
                popUpYvalue += 50;
            }
        }

        if (InventoryH != -1 && Inv == 1) {
            int popUpYvalue = 40;
            for (int i = -2; i < 3; ++i) {
                int curr = ((InventoryH + i) % value + value) % value;
                Image x = Player.getInstance().getInventory2Img(curr);
                String name = Player.getInstance().getInventory2Str(curr);
                window.addPopUpAtPixel(5, popUpYvalue, 410, 50, holder.getImage());
                window.addPopUpAtPixel(25, popUpYvalue + 5, 40, 40, x);
                window.addPopUpTextAtPixel((i == 0? "> " : "") + name, 75, popUpYvalue + 35,  (i == 0? "GREEN" : "WHITE"), 25f);
                window.addPopUpTextAtPixel("Slot: " + (curr + 1), 280, popUpYvalue + 35,  "WHITE", 25f);
                popUpYvalue += 50;
            }
        }

        if (InventoryH != -1 && Inv == 2) {
            int popUpYvalue = 40;
            for (int i = -2; i < 3; ++i) {
                int curr = ((InventoryH + i) % value + value) % value;
                Image x = Player.getInstance().getInventory3Img(curr);
                String name = Player.getInstance().getInventory3Str(curr);
                window.addPopUpAtPixel(5, popUpYvalue, 410, 50, holder.getImage());
                window.addPopUpAtPixel(25, popUpYvalue + 5, 40, 40, x);
                window.addPopUpTextAtPixel((i == 0? "> " : "") + name, 75, popUpYvalue + 35,  (i == 0? "GREEN" : "WHITE"), 25f);
                window.addPopUpTextAtPixel("Slot: " + (curr + 1), 300, popUpYvalue + 35,  "WHITE", 25f);
                popUpYvalue += 50;
            }
        }

        if (checkup) {
            String Item = Player.getInstance().getItemName(InventoryH);

            window.addCheckUpAtPixel(0, 40, 490, 400, holder.getImage());
            window.addCheckUpText("Equip ", 150, 90,  "WHITE", 25f);
            window.addCheckUpText(Item, 230, 90,  "GREEN", 25f);

            window.addCheckUpText((checkUpWidth == 0? ">"  : "")  + "No", 20, 220,  "RED", 25f);
            window.addCheckUpText((checkUpWidth == 1? ">"  : "") + "Yes", 420, 220,  "GREEN", 25f);
        }
    }

    @Override
    public void display() {
        drawEverything();
    }

    @Override
    public void listenToInput() {
        characterKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (checkup) {
                        checkup = false;
                        checkUpWidth = -1;
                        return;
                    }

                    if (Inv != -1 && InventoryH != -1) {
                        InventoryH = -1;
                        return;
                    }

                    if (Inv != -1) {
                        Inv = -1;
                        return;
                    }

                    enterLobby();
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (checkup) {
                        Image img = Player.getInstance().getInventory1Img(InventoryH);
                        String name = Player.getInstance().getInventory1Str(InventoryH);
                        name = name.toLowerCase();
                        if (name.contains("armour")) {
                            Player.getInstance().setHeldArmour(img);
                        } else {
                            Player.getInstance().setHeldWeapon(img);
                        }

                        checkup = false;
                        checkUpWidth = -1;
                        return;
                    }

                    if (Inv == 0 && InventoryH != -1 && !Objects.equals(Player.getInstance().getInventory1Str(InventoryH), "Empty")) {
                        checkup = true;
                        checkUpWidth = 0;
                        return;
                    }

                    if (Inv != -1) {
                        InventoryH = 0;
                        return;
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_I) {
                    Inv = 0;
                    return;
                }

                if (checkup && e.getKeyCode() == KeyEvent.VK_LEFT) {
                    checkUpWidth = ((checkUpWidth - 1) % 2 + 2) % 2;
                    return;
                }

                if (checkup && e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    checkUpWidth = (checkUpWidth + 1) % 2;
                    return;
                }

                if (Inv != -1 && e.getKeyCode() == KeyEvent.VK_UP) {
                    if (checkup)
                        return;

                    if (InventoryH != -1) {
                        int value = 10;
                        if (Inv == 2) value = Quests.getInstance().getLimit();
                        InventoryH = ((InventoryH - 1) + value) % value;
                        return;
                    }

                    Inv = ((Inv - 1) % 3 + 3) % 3;
                    return;
                }

                if (Inv != -1 && e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (checkup)
                        return;

                    if (InventoryH != -1) {
                        int value = 10;
                        if (Inv == 2) value = Quests.getInstance().getLimit();
                        InventoryH = (InventoryH + 1) % value;
                        return;
                    }

                    Inv = (Inv + 1) % 3;
                    return;
                }
            }
        };
        window.addKeyListener(characterKeyListener);
    }
}
