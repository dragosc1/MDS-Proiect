package headers.MainLobby;

import headers.Player;
import headers.Scene;
import headers.Screen;
import headers.Utility.Skills;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class SkillShop implements Scene {
    private final Screen window;
    private final GameLobby lobby;
    private Integer inventoryH, checkUpWidth;
    private Boolean ind1, ind2;
    private KeyListener skillListener;
    private ImageIcon back, wood, bar, ic0;
    private ImageIcon holder, ic1, ic2, ic3;

    public SkillShop(Screen window, GameLobby _lobby) {
        this.ind1 = this.ind2 = false;
        this.checkUpWidth = 0;
        this.inventoryH = 0;
        this.window = window;
        this.lobby = _lobby;
        this.window.setBackground("WHITE");
        loadAssets();
    }

    void loadAssets() {
        back = new ImageIcon("assets/Skill Shop/SkillShop.png");
        wood = new ImageIcon("assets/Market/woodytexturebackground.jpg");
        bar = new ImageIcon("assets/Market/BrownBlackGround.png");
        ic0 = new ImageIcon("assets/Market/Cicon.png");
        holder = new ImageIcon("assets/Market/itemholder.png");
        ic1 = new ImageIcon("assets/Market/GoldIcon.png");
        ic2 = new ImageIcon("assets/Skill Shop/SkillShopIcon.png");
        ic3 = new ImageIcon("assets/Skill Shop/IntermediarMagic.png");
    }

    private void removeKeyAdaptor() {
        window.removeKeyListener(skillListener);
    }

    private void enterLobby() {
        removeKeyAdaptor();
        window.clearScreen();
        window.setBackground("assets/Game Menu/GameMenuBackGround.png");
        window.setCurentScene(lobby);
    }

    private synchronized ArrayList <String> getSplit(String des) {
        String[] words = des.split(" ");

        // Use a StringBuilder to construct each line
        StringBuilder line = new StringBuilder();
        ArrayList<String> lines = new ArrayList<>();

        // Iterate through the words and add them to lines
        int count = 0;
        for (String word : words) {
            if (count < 5) {
                if (line.length() > 0) {
                    line.append(" ");
                }
                line.append(word);
                count++;
            } else {
                lines.add(line.toString());
                line = new StringBuilder(word);
                count = 1;
            }
        }

        // Add the last line if there are remaining words
        if (line.length() > 0) {
            lines.add(line.toString());
        }

        return lines;
    }

    void drawEverything() {
        window.addImageAtPixel(0, 0, 500, 400, back.getImage());

        window.addImageAtPixel(0, 0, 490, 40, bar.getImage());
        window.addImageAtPixel(5, 0, 40, 40, ic0.getImage());
        window.addTextAtPixel("Skill Shop", 55, 30, "WHITE", 25f);

        window.addImageAtPixel(0, 400, 500, 400, wood.getImage());

        int yValue = 400;
        window.addImageAtPixel(0, yValue, 245, 80, holder.getImage());
        window.addImageAtPixel(10, yValue + 20, 40, 40, ic1.getImage());
        window.addTextAtPixel("Gold: " + Player.getInstance().getGold(), 55, yValue + 45, "WHITE", 25f);

        window.addImageAtPixel(245, yValue, 240, 80, holder.getImage());
        window.addImageAtPixel(255, yValue + 20, 40, 40, ic2.getImage());
        window.addTextAtPixel("Skills", 310, yValue + 45, "WHITE", 25f);

        yValue += 120;
        window.addImageAtPixel(0, yValue, 490, 80, holder.getImage());
        window.addImageAtPixel(10, yValue + 20, 40, 40, ic3.getImage());
        window.addTextAtPixel((ind1? ">" : "") + "Space: " + Player.getInstance().getSInventorySpace(), 55, yValue + 45, "WHITE", 25f);

        if (ind1) {
            int popUpYvalue = 40;
            for (int i = -2; i < 3; ++i) {
                int curr = ((inventoryH + i) % 14 + 14) % 14;
                Image x = Skills.getInstance().getSkill(curr);
                String name = Skills.getInstance().getSkillName(curr);
                window.addPopUpAtPixel(5, popUpYvalue, 410, 50, holder.getImage());
                window.addPopUpAtPixel(25, popUpYvalue + 5, 40, 40, x);
                window.addPopUpTextAtPixel((i == 0? "> " : "") + name, 75, popUpYvalue + 35,  (i == 0? "GREEN" : "WHITE"), 25f);
                window.addPopUpTextAtPixel("Slot: " + (curr + 1), 300, popUpYvalue + 35,  "WHITE", 25f);
                popUpYvalue += 50;
            }
        }

        if (ind2) {
            String ItemBuy = Skills.getInstance().getSkillName(inventoryH);
            Integer price = Player.getInstance().applyCharisma(2000);

            if (Player.getInstance().checkItem3(ItemBuy)) {
                window.addCheckUpAtPixel(0, 40, 490, 400, holder.getImage());
                window.addCheckUpText("You already purchased this item!", 100, 90,  "WHITE", 25f);
                return;
            }

            window.addCheckUpAtPixel(0, 40, 490, 400, holder.getImage());
            window.addCheckUpText("Buy ", 150, 90,  "WHITE", 25f);
            window.addCheckUpText(ItemBuy, 210, 90,  "YELLOW", 25f);
            window.addCheckUpText("For ", 180, 120,  "WHITE", 25f);
            window.addCheckUpText(price + " Gold?", 240, 120,  "GREEN", 25f);

            String desc = Skills.getInstance().getSkillDescription(inventoryH);
            ArrayList <String> lines = getSplit(desc);

            int yPosition = 160;  // initial y position
            for (String l : lines) {
                window.addCheckUpText(l, 10, yPosition, "WHITE", 25f);
                yPosition += 30;  // adjust y position for the next line
            }

            window.addCheckUpText((checkUpWidth == 0? ">"  : "")  + "No", 20, 260,  "RED", 25f);
            if (Player.getInstance().getGold() >= Player.getInstance().applyCharisma(2000) && Player.getInstance().getSInventorySpace() > 0) window.addCheckUpText((checkUpWidth == 1? ">"  : "") + "Yes", 420, 260,  "GREEN", 25f);
        }
    }

    @Override
    public void display() {
        drawEverything();
    }

    @Override
    public void listenToInput() {
        skillListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (ind2) {
                        ind2 = false;
                        return;
                    }

                    if (ind1) {
                        ind1 = false;
                        return;
                    }

                    enterLobby();
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_B) {
                    ind1 = true;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && ind1 && ind2) {
                    if (checkUpWidth == 0) {
                        ind2 = false;
                        return;
                    }

                    if (Player.getInstance().getGold() < Player.getInstance().applyCharisma(2000) || Player.getInstance().getSInventorySpace() <= 0) {
                        return;
                    }

                    Player.getInstance().subtractFromGold(Player.getInstance().applyCharisma(2000));
                    Player.getInstance().addItem3(Skills.getInstance().getSkill(inventoryH), Skills.getInstance().getSkillName(inventoryH));
                    ind2 = false;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && ind1) {
                    ind2 = true;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    ind1 = true;
                    return;
                }

                if (ind2 && ind1 && e.getKeyCode() == KeyEvent.VK_LEFT) {
                    checkUpWidth = ((checkUpWidth - 1) % 2 + 2) % 2;
                    return;
                }

                if (ind2 && ind1 && e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    checkUpWidth = (checkUpWidth + 1) % 2;
                    return;
                }

                if (ind1 && e.getKeyCode() == KeyEvent.VK_UP) {
                    if (ind2) {
                        return;
                    }

                    inventoryH = ((inventoryH - 1) % 14 + 14) % 14;
                    return;
                }

                if (ind1 && e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (ind2) {
                        return;
                    }

                    inventoryH = (inventoryH + 1) % 14;
                    return;
                }
            }
        };
        window.addKeyListener(skillListener);
    }
}