package headers.MainLobby;

import headers.Player;
import headers.Scene;
import headers.Screen;
import headers.Utility.Items;
import headers.Utility.Quests;
import headers.Utility.Skills;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Encounter implements Scene {
    private KeyListener EncounterListener;
    private final Screen window;
    private Dungeon dungeon;
    private GameLobby lobby;
    private ImageIcon back, holder, wood, ic2, ic1, colorr, colorb;
    private Enemy m1, m2, m3;
    private Integer curr, width, howMany, widthC;;
    private Integer ani1, ani2, ani3, InventoryH, checkUpWidth;
    private long stTimer1, stTimer2, stTimer3;
    private int DamageDealtbyMob;
    private Boolean checkA, checkB;

    public Encounter(Screen window, Dungeon dungeon, GameLobby lobby, int howMany) {
        if (howMany >= 1) m1 = new Enemy(dungeon.getTdungeon());
        if (howMany >= 2) m2 = new Enemy(dungeon.getTdungeon());
        if (howMany >= 3) m3 = new Enemy(dungeon.getTdungeon());

        checkA = checkB = false;
        this.checkUpWidth = -1;
        widthC = -1;
        this.lobby = lobby;
        this.window = window;
        this.dungeon = dungeon;
        stTimer1 = stTimer2 = stTimer3 = 0;
        this.curr = -1;
        this.width = this.InventoryH = -1;
        this.ani1 = this.ani2 = this.ani3 = 0;
        this.howMany = howMany;
        DamageDealtbyMob = dungeon.getDamageDealt();
        loadAssets();
    }

    void applyPassive() {
        if (Player.getInstance().heals())
            DamageDealtbyMob -= (int)Math.ceil(0.1 * (double)Player.getInstance().getHpI(0));
    }

    void applyPotions() {
        String name = Player.getInstance().getItem2Name(InventoryH);
        int pos = Items.getInstance().getIndexPotion(name);

        if (pos <= 2) {
            DamageDealtbyMob -= Items.getInstance().getPotionProperties(pos);
        } else if (pos == 3) {
            if (howMany >= 1) m1.setInc(0.3);
            if (howMany >= 2) m2.setInc(0.3);
            if (howMany >= 3) m3.setInc(0.3);
        } else if (pos == 4) {
            if (howMany >= 1) m1.setPotionRound1();
            if (howMany >= 2) m2.setPotionRound1();
            if (howMany >= 3) m3.setPotionRound1();
        } else if (pos == 5) {
            if (howMany >= 1) m1.setPotionRound2();
            if (howMany >= 2) m2.setPotionRound2();
            if (howMany >= 3) m3.setPotionRound2();
        } else if (pos == 6) {
            if (howMany >= 1) m1.applyDealtDamageTrue(100, false);
            if (howMany >= 2) m2.applyDealtDamageTrue(100, false);
            if (howMany >= 3) m3.applyDealtDamageTrue(100, false);
        }
    }

    void check() {
        if ((howMany >= 1  && ani1 != 0) || (howMany >= 2 && ani2 != 0) || (howMany >= 3 && ani3 != 0)) {
            return;
        }

        if (Player.getInstance().getHp(DamageDealtbyMob).equals("0")) {
            if (howMany == 1) Player.getInstance().subtractFromGold(100 * Player.getInstance().getCurrPlayerTier());
            if (howMany == 2) Player.getInstance().subtractFromGold(200 * Player.getInstance().getCurrPlayerTier());
            if (howMany == 3) Player.getInstance().subtractFromGold(300 * Player.getInstance().getCurrPlayerTier());
            enterLobby();
        }

        int ok = (howMany >= 1 && m1.currHealth() == 0)? 1 : 0;
        ok += (howMany >= 2 && m2.currHealth() == 0)? 1 : 0;
        ok += (howMany >= 3 && m3.currHealth() == 0)? 1  : 0;
        if (ok == 1 && howMany == 1) {
            Quests.getInstance().updateKillQuest(m1.getTypeEnemy());
            Player.getInstance().subtractFromGold(-100 * Player.getInstance().getCurrPlayerTier());
            dungeon.setDamge(DamageDealtbyMob);
            enterDungeon();
        }
        if (ok == 2 && howMany == 2) {
            Quests.getInstance().updateKillQuest(m2.getTypeEnemy());
            Player.getInstance().subtractFromGold(-200 * Player.getInstance().getCurrPlayerTier());
            dungeon.setDamge(DamageDealtbyMob);
            enterDungeon();
        }
        if (ok == 3 && howMany == 3) {
            Quests.getInstance().updateKillQuest(m3.getTypeEnemy());
            Player.getInstance().subtractFromGold(-300 * Player.getInstance().getCurrPlayerTier());
            dungeon.setDamge(DamageDealtbyMob);
            enterDungeon();
        }
    }

    void loadAssets() {
        back = new ImageIcon("assets/Inamici Dungeon/Dungeon I/DungeonFightBackGround.png");
        wood = new ImageIcon("assets/Market/woodytexturebackground.jpg");
        holder = new ImageIcon("assets/Market/itemholder.png");
        ic2 = new ImageIcon("assets/Market/inventoryicon.png");
        ic1 = new ImageIcon("assets/Character/HealthPointsIcon.png");
        colorr = new ImageIcon("assets/Inamici Dungeon/Dungeon I/HPBarColor.png");
        colorb = new ImageIcon("assets/Inamici Dungeon/Dungeon I/blackBar.png");
    }

    synchronized void drawEverything() {
        check();
        window.addImageAtPixel(0, 0, 500, 400, back.getImage());

        if (howMany >= 1 && m1.currHealth() > 0) {
            if (ani1 == 0 || (ani1 == 2 && System.currentTimeMillis()- stTimer1 < 1500)) {
                window.addImageAtPixel(200, 150, 80, 80, m1.getIcon().getImage());

                window.addImageAtPixel(200, 235, m1.getX(), 10, colorr.getImage());
                window.addImageAtPixel(200 + m1.getX(), 235, 80 - m1.getX(), 10, colorb.getImage());

                window.addTextAtPixel(((width == 0 || width == 4) ? ">" : "") + m1.getTypeEnemy(), 210, 265, "WHITE", 20);
                window.addTextAtPixel(m1.currHealth() + " Hp", 210, 285, "WHITE", 20);
            }
        }

        if (howMany >= 2  && m2.currHealth() > 0) {
            if (ani2 == 0 || (ani2 == 2 && System.currentTimeMillis()- stTimer2 < 1500)) {
                window.addImageAtPixel(100, 150, 80, 80, m2.getIcon().getImage());

                window.addImageAtPixel(100, 235, m2.getX(), 10, colorr.getImage());
                window.addImageAtPixel(100 + m2.getX(), 235, 80 - m2.getX(), 10, colorb.getImage());

                window.addTextAtPixel(((width == 1 || width == 4) ? ">" : "") + m2.getTypeEnemy(), 110, 265, "WHITE", 20);
                window.addTextAtPixel(m2.currHealth() + " Hp", 110, 285, "WHITE", 20);
            }
        }

        if (howMany >= 3 && m3.currHealth() > 0) {
            if (ani3 == 0 || (ani3 == 2 && System.currentTimeMillis()- stTimer3 < 1500)) {
                window.addImageAtPixel(300, 150, 80, 80, m3.getIcon().getImage());

                window.addImageAtPixel(300, 235, m3.getX(), 10, colorr.getImage());
                window.addImageAtPixel(300 + m3.getX(), 235, 80 - m3.getX(), 10, colorb.getImage());

                window.addTextAtPixel(((width == 2 || width == 4) ? ">" : "") + m3.getTypeEnemy(), 310, 265, "WHITE", 20);
                window.addTextAtPixel(m3.currHealth() + " Hp", 310, 285, "WHITE", 20);
            }
        }

        window.addImageAtPixel(0, 400, 500, 400, wood.getImage());

        int yValue = 400;
        window.addImageAtPixel(0, yValue, 490, 80, holder.getImage());
        window.addTextAtPixel("You are being attacked!", 135, yValue + 45, "RED", 25f);

        yValue += 120;
        window.addImageAtPixel(0, yValue, 245, 80, holder.getImage());
        window.addImageAtPixel(10, yValue + 20, 40, 40, ic2.getImage());

        int space = 10;
        String what = "Poisions";
        if (curr == 1) {
            what = "Spells";
            space = Quests.getInstance().getLimit();
        }

        if (curr == 2) {
            window.addTextAtPixel("> Attack", 55, yValue + 45, "WHITE", 25f);
        } else {
            window.addTextAtPixel((curr != -1 ? ">" : "") + what + ": " + space, 55, yValue + 45, "WHITE", 25f);
        }

        window.addImageAtPixel(245, yValue, 240, 80, holder.getImage());
        window.addImageAtPixel(255, yValue + 20, 40, 40, ic1.getImage());
        window.addTextAtPixel("Health: " + Player.getInstance().getHp(DamageDealtbyMob), 300, yValue + 45, "WHITE", 25f);

        if (InventoryH != -1 && curr == 0) {
            int popUpYvalue = 40;
            for (int i = -2; i < 3; ++i) {
                int curr = ((InventoryH + i) % 10 + 10) % 10;
                Image x = Player.getInstance().getInventory2Img(curr);
                String name = Player.getInstance().getInventory2Str(curr);
                window.addPopUpAtPixel(5, popUpYvalue, 410, 50, holder.getImage());
                window.addPopUpAtPixel(25, popUpYvalue + 5, 40, 40, x);
                window.addPopUpTextAtPixel((i == 0? "> " : "") + name, 75, popUpYvalue + 35,  (i == 0? "GREEN" : "WHITE"), 25f);
                window.addPopUpTextAtPixel("Slot: " + (curr + 1), 280, popUpYvalue + 35,  "WHITE", 25f);
                popUpYvalue += 50;
            }
        }

        if (InventoryH != -1 && curr == 1) {
            int popUpYvalue = 40;
            for (int i = -2; i < 3; ++i) {
                int value = Quests.getInstance().getLimit();
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

        if (InventoryH != -1 && curr == 0 && checkA) {
            String name = Player.getInstance().getInventory2Str(InventoryH);
            int pos = Items.getInstance().getIndexPotion(name);
            String pname = Items.getInstance().getNamePotion(pos);

            window.addCheckUpAtPixel(0, 40, 490, 400, holder.getImage());
            window.addCheckUpText("Use ", 150, 90,  "WHITE", 25f);
            window.addCheckUpText(pname, 210, 90,  "RED", 25f);

            window.addCheckUpText(Items.getInstance().getDescPotion(pos), 10, 170,  "WHITE", 25f);

            window.addCheckUpText((checkUpWidth == 0? ">"  : "")  + "No", 20, 220,  "RED", 25f);
            window.addCheckUpText((checkUpWidth == 1? ">"  : "") + "Yes", 420, 220,  "GREEN", 25f);
        }

        if (InventoryH != -1 && curr == 1 && checkB) {
            String name = Player.getInstance().getInventory3Str(InventoryH);
            int pos = Skills.getInstance().index(name);
            if (pos != -1) {
                String ItemBuy = Skills.getInstance().getSkillName(pos);

                if (!Player.getInstance().checkCoolDownI(InventoryH)) {
                    window.addCheckUpAtPixel(0, 40, 490, 400, holder.getImage());
                    window.addCheckUpText("Spell is on cooldown!", 150, 90, "WHITE", 25f);
                    return;
                }

                window.addCheckUpAtPixel(0, 40, 490, 400, holder.getImage());
                window.addCheckUpText("Use ", 150, 90, "WHITE", 25f);
                window.addCheckUpText(ItemBuy, 210, 90, "YELLOW", 25f);

                String desc = Skills.getInstance().getSkillDescription(pos);
                ArrayList<String> lines = getSplit(desc);

                int yPosition = 160;  // initial y position
                for (String l : lines) {
                    window.addCheckUpText(l, 10, yPosition, "WHITE", 25f);
                    yPosition += 30;  // adjust y position for the next line
                }

                window.addCheckUpText((checkUpWidth == 0 ? ">" : "") + "No", 20, 260, "RED", 25f);
                window.addCheckUpText((checkUpWidth == 1 ? ">" : "") + "Yes", 420, 260, "GREEN", 25f);
            }
        }


        if (howMany >= 1 && ani1 == 1) {
            shake(m1.getIcon(), 200, 150);
            stTimer1 = stTimer2 = stTimer3 = System.currentTimeMillis();
            if (howMany >= 1 && m1.currHealth() > 0) ani1 = 2;
            else ani1 = 0;
            if (howMany >= 2 && m2.currHealth() > 0) ani2 = 2;
            else ani2 = 0;
            if (howMany >= 3 && m3.currHealth() > 0) ani3 = 2;
            else ani3 = 0;
            return;
        }

        if (howMany >= 2 && ani2 == 1) {
            shake(m2.getIcon(), 100, 150);
            stTimer1 = stTimer2 = stTimer3 = System.currentTimeMillis();
            if (howMany >= 1 && m1.currHealth() > 0) ani1 = 2;
            else ani1 = 0;
            if (howMany >= 2 && m2.currHealth() > 0) ani2 = 2;
            else ani2 = 0;
            if (howMany >= 3 && m3.currHealth() > 0) ani3 = 2;
            else ani3 = 0;
            return;
        }

        if (howMany >= 3 && ani3 == 1) {
            shake(m3.getIcon(), 300, 150);
            stTimer1 = stTimer2 = stTimer3 = System.currentTimeMillis();
            if (howMany >= 1 && m1.currHealth() > 0) ani1 = 2;
            else ani1 = 0;
            if (howMany >= 2 && m2.currHealth() > 0) ani2 = 2;
            else ani2 = 0;
            if (howMany >= 3 && m3.currHealth() > 0) ani3 = 2;
            else ani3 = 0;
            return;
        }

        if (howMany >= 1 && ani1 == 2 && System.currentTimeMillis()- stTimer1 >= 1500) {
            if (m1.currHealth() > 0) startMoveAnimation(m1.getIcon());
            stTimer1 = System.currentTimeMillis();
            ani1 = 3;
        }

        if (howMany >= 2 && ani2 == 2 && System.currentTimeMillis()- stTimer2 >= 1500) {
            if (m2.currHealth() > 0) startMoveAnimation(m2.getIcon());
            stTimer2 = System.currentTimeMillis();
            ani2 = 3;
        }

        if (howMany >= 3 && ani3 == 2 && System.currentTimeMillis()- stTimer3 >= 1500) {
            if (m3.currHealth() > 0) startMoveAnimation(m3.getIcon());
            stTimer3 = System.currentTimeMillis();
            ani3 = 3;
        }


        boolean ok = false;
        if (howMany >= 1 && ani1 == 3 && System.currentTimeMillis() - stTimer1 >= 1500) {
            double resist = Player.getInstance().getArmI();
            DamageDealtbyMob += (int) Math.ceil((m1.dealDamage() * (1.0 - resist / (resist + 100))));
            m1.EndRound();
            if (!ok) {
                applyPassive();
                ok = true;
            }
            ani1 = 0;
        }

        if (howMany >= 2 && ani2 == 3 && System.currentTimeMillis() - stTimer2 >= 1500) {
            double resist = Player.getInstance().getArmI();
            DamageDealtbyMob += (int) Math.ceil((m2.dealDamage() * (1.0 - resist / (resist + 100))));
            m2.EndRound();
            if (!ok) {
                applyPassive();
                ok = true;
            }
            ani2 = 0;
        }

        if (howMany >= 3 && ani3 == 3 && System.currentTimeMillis() - stTimer3 >= 1500) {
            double resist = Player.getInstance().getArmI();
            DamageDealtbyMob += (int) Math.ceil((m3.dealDamage() * (1.0 - resist / (resist + 100))));
            m3.EndRound();
            if (!ok) {
                applyPassive();
                ok = true;
            }
            ani3 = 0;
        }
    }

    private ArrayList<String> getSplit(String desc) {
        String[] words = desc.split(" ");

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

    synchronized void shake(ImageIcon img, int x, int y) {
        int shakeDuration = 300;  // Duration in milliseconds
        int shakeInterval = 50;        // Interval between shakes in milliseconds
        int shakeDistance = 15;         // Distance to shake

        Timer timer = new Timer(shakeInterval, null);
        timer.addActionListener(new ActionListener() {
            int elapsedTime = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                // Calculate new position for shaking
                int shakeX = x + (int) (Math.random() * shakeDistance * 2 - shakeDistance);
                int shakeY = y + (int) (Math.random() * shakeDistance * 2 - shakeDistance);

                // Update the health bar position
                window.addImageAtPixel(shakeX, shakeY, 80, 80, img.getImage());
                window.repaint();  // Refresh the window to show changes

                elapsedTime += shakeInterval;
                if (elapsedTime >= shakeDuration) {
                    // Stop shaking after the duration is reached
                    timer.stop();
                    // Reset the health bar to its original position
                    window.addImageAtPixel(x, y, 80, 80, img.getImage());
                    window.repaint();
                }
            }
        });
        timer.start();
    }

    private void startMoveAnimation(ImageIcon img) {
        int moveDistance = 100; // Distance to move downwards
        int moveInterval = 10;  // Interval between movement steps in milliseconds
        int moveDuration = 700; // Duration of the movement

        int x = 200, y = 150;
        int expandedWidth = 100, expandedHeight = 100; // Use the expanded size

        // Timer for the delay
        Timer delayTimer = new Timer(300, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Stop the delay timer
                ((Timer) e.getSource()).stop();

                // Timer for the movement
                Timer moveTimer = new Timer(moveInterval, null);
                moveTimer.addActionListener(new ActionListener() {
                    int elapsedTime = 0;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Calculate the new y position based on elapsed time
                        int newY = y + (int) ((moveDistance) * ((double) elapsedTime / moveDuration));

                        // Update the image position
                        window.addImageAtPixel(x, newY, expandedWidth, expandedHeight, img.getImage());
                        window.repaint();  // Refresh the window to show changes

                        elapsedTime += moveInterval;
                        if (elapsedTime >= moveDuration) {
                            // Stop the movement after the duration is reached
                            ((Timer) e.getSource()).stop();
                            // Reset the image to its original size and position
                            window.addImageAtPixel(x, y, 80, 80, img.getImage());
                            window.repaint();
                        }
                    }
                });

                moveTimer.start();
            }
        });

        delayTimer.setRepeats(false); // Only execute once
        delayTimer.start(); // Start the delay timer
    }


    @Override
    public void display() {
        drawEverything();
    }

    private void removeKeyAdaptor() {
        window.removeKeyListener(EncounterListener);
    }

    private void enterDungeon() {
        removeKeyAdaptor();
        window.clearScreen();
        window.setCurentScene(dungeon);
    }

    private void enterLobby() {
        removeKeyAdaptor();
        window.clearScreen();
        window.setBackground("assets/Game Menu/GameMenuBackGround.png");
        window.setCurentScene(lobby);
    }

    @Override
    public void listenToInput() {
        EncounterListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (widthC != -1) {
                        widthC = -1;
                        curr = 1;
                        checkB = true;
                        checkUpWidth = 1;
                        width = -1;
                        return;
                    }

                    if (checkB) {
                        checkB = false;
                        checkUpWidth = -1;
                        return;
                    }

                    if (checkA) {
                        checkA = false;
                        checkUpWidth = -1;
                        return;
                    }

                    if (InventoryH != -1) {
                        InventoryH = -1;
                        return;
                    }

                    if (width != -1) {
                        width = -1;
                        return;
                    }

                    if (curr != -1) {
                        curr = -1;
                        return;
                    }

                    enterDungeon();
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_I) {
                    curr = 0;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_RIGHT && checkB) {
                    checkUpWidth = (checkUpWidth + 1) % 2;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_RIGHT && checkA) {
                    checkUpWidth = (checkUpWidth + 1) % 2;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_RIGHT && curr == 2 && width != -1) {
                    if (InventoryH != -1) return;
                    int nr = howMany;
                    while (true) {
                        width = (width + 1) % nr;
                        if (nr >= 1 && width == 0 && m1.currHealth() != 0) break;
                        if (nr >= 2 && width == 1 && m2.currHealth() != 0) break;
                        if (nr >= 3 && width == 2 && m3.currHealth() != 0) break;
                    }
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_RIGHT && widthC == 0 && width != -1) {
                    int nr = howMany;
                    while (true && width != 4) {
                        width = (width + 1) % nr;
                        if (nr >= 1 && width == 0 && m1.currHealth() != 0) break;
                        if (nr >= 2 && width == 1 && m2.currHealth() != 0) break;
                        if (nr >= 3 && width == 2 && m3.currHealth() != 0) break;
                    }
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_LEFT && checkB) {
                    checkUpWidth = ((checkUpWidth - 1) % 2 + 2) % 2;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_LEFT && checkA) {
                    checkUpWidth = ((checkUpWidth - 1) % 2 + 2) % 2;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_LEFT && curr == 2 && width != -1) {
                    if (InventoryH != -1) return;
                    int nr = howMany;
                    while (true) {
                        width = ((width - 1) % nr + nr) % nr;
                        if (nr >= 1 && width == 0 && m1.currHealth() != 0) break;
                        if (nr >= 2 && width == 1 && m2.currHealth() != 0) break;
                        if (nr >= 3 && width == 2 && m3.currHealth() != 0) break;
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_LEFT && widthC == 0 && width != -1) {
                    int nr = howMany;
                    while (true && width != 4) {
                        width = ((width - 1) % nr + nr) % nr;
                        if (nr >= 1 && width == 0 && m1.currHealth() != 0) break;
                        if (nr >= 2 && width == 1 && m2.currHealth() != 0) break;
                        if (nr >= 3 && width == 2 && m3.currHealth() != 0) break;
                    }
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (width != -1) return;

                    if (InventoryH != -1) {
                        int val = 10;
                        if (curr == 1) val = Quests.getInstance().getLimit();
                        InventoryH = ((InventoryH - 1) % val + val) % val;
                        return;
                    }

                    if (curr != -1) curr = ((curr - 1) % 3 + 3) % 3;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (width != -1) return;

                    if (InventoryH != -1) {
                        int val = 10;
                        if (curr == 1) val = Quests.getInstance().getLimit();
                        InventoryH = ((InventoryH + 1) % val + val) % val;
                        return;
                    }

                    if (curr != -1) curr = (curr + 1) % 3;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && curr == 0 && InventoryH != -1 && checkA && checkUpWidth == 0) {
                    checkUpWidth = -1;
                    checkA = false;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && curr == 1 && InventoryH != -1 && checkB && checkUpWidth == 0) {
                    checkUpWidth = -1;
                    checkB = false;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && curr == 1 && InventoryH != -1 && checkB && checkUpWidth == 1) {
                    String name = Player.getInstance().getInventory3Str(InventoryH);
                    int idx = Skills.getInstance().index(name);

                    if (idx != -1 && Player.getInstance().checkCoolDownI(InventoryH)) {
                        checkUpWidth = -1;
                        checkB = false;
                        curr = -1;
                        widthC = 0;

                        if (idx == 0) width = 0;
                        if (idx == 1) width = 0;
                        if (idx == 2) width = 4;
                        if (idx == 3) width = 4;
                        if (idx == 11) width = 0;
                        if (idx == 12) width = 4;
                        if (idx == 13) width = 4;

                        if (width != 4 && width != -1) {
                            int nr = howMany;
                            while (true) {
                                width = ((width - 1) % nr + nr) % nr;
                                if (nr >= 1 && width == 0 && m1.currHealth() != 0) break;
                                if (nr >= 2 && width == 1 && m2.currHealth() != 0) break;
                                if (nr >= 3 && width == 2 && m3.currHealth() != 0) break;
                            }
                        }
                    } else {
                        checkUpWidth = -1;
                        checkB = false;
                        curr = 1;
                        widthC = -1;
                    }
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && curr == 0 && InventoryH != -1 && checkA && checkUpWidth == 1) {
                    if (Player.getInstance().getInventory2Str(InventoryH).equals("Empty"))
                        return;

                    applyPotions();
                    Player.getInstance().remItem2(InventoryH);
                    checkUpWidth = -1;
                    checkA = false;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && widthC == 0 && width != -1) {
                    if (width == 0 || width == 4) {
                        String name = Player.getInstance().getInventory3Str(InventoryH);
                        int pos = Skills.getInstance().index(name);

                        int dmg = Player.getInstance().getApI() / 10;
                        if (pos == 0) {
                            dmg *= 75;
                            Player.getInstance().setCooldown(InventoryH);
                        }
                        if (pos == 1) {
                            dmg *= 150;
                            Player.getInstance().setCooldown(InventoryH);
                        }
                        if (pos == 2) {
                            dmg *= 250;
                            Player.getInstance().setCooldown(InventoryH);
                        }
                        if (pos == 3) {
                            dmg *= 400;
                            Player.getInstance().setCooldown(InventoryH);
                        }
                        if (pos == 11) {
                            dmg *= 3;
                            dmg *= Player.getInstance().getAdI();
                        }

                        if (pos == 12) {
                            dmg *= (int) Math.ceil(1.5f * Player.getInstance().getAdI());
                        }

                        if (pos == 13) {
                            dmg = 0;
                        }

                        m1.applyDealtDamage(dmg, true);
                        if (width == 0) width = -1;
                        widthC = -1;
                        curr = -1;
                        if (pos != 13) ani1 = 1;
                        else ani1 = 2;
                    }

                    if (width == 1 || width == 4) {
                        String name = Player.getInstance().getInventory3Str(InventoryH);
                        int pos = Skills.getInstance().index(name);

                        int dmg = Player.getInstance().getApI() / 10;
                        if (pos == 0) {
                            dmg *= 75;
                            Player.getInstance().setCooldown(InventoryH);
                        }
                        if (pos == 1) {
                            dmg *= 150;
                            Player.getInstance().setCooldown(InventoryH);
                        }
                        if (pos == 2) {
                            dmg *= 250;
                            Player.getInstance().setCooldown(InventoryH);
                        }
                        if (pos == 3) {
                            dmg *= 400;
                            Player.getInstance().setCooldown(InventoryH);
                        }
                        if (pos == 11) {
                            dmg *= 3;
                            dmg *= Player.getInstance().getAdI();
                            Player.getInstance().setCooldown(InventoryH);
                        }

                        if (pos == 12) {
                            dmg *= (int) Math.ceil(1.5f * Player.getInstance().getAdI());
                            Player.getInstance().setCooldown(InventoryH);
                        }

                        if (pos == 13) {
                            dmg = 0;
                            Player.getInstance().setCooldown(InventoryH);
                        }

                        m2.applyDealtDamage(dmg, true);
                        if (width == 1) width = -1;
                        widthC = -1;
                        curr = -1;
                        if (pos != 13) ani2 = 1;
                        else ani2 = 2;
                    }

                    if (width == 2 || width == 4) {
                        String name = Player.getInstance().getInventory3Str(InventoryH);
                        int pos = Skills.getInstance().index(name);

                        int dmg = Player.getInstance().getApI() / 10;
                        if (pos == 0) {
                            dmg *= 75;
                            Player.getInstance().setCooldown(InventoryH);
                        }
                        if (pos == 1) {
                            dmg *= 150;
                            Player.getInstance().setCooldown(InventoryH);
                        }
                        if (pos == 2) {
                            dmg *= 250;
                            Player.getInstance().setCooldown(InventoryH);
                        }
                        if (pos == 3) {
                            dmg *= 400;
                            Player.getInstance().setCooldown(InventoryH);
                        }
                        if (pos == 11) {
                            dmg *= 3;
                            dmg *= Player.getInstance().getAdI();
                            Player.getInstance().setCooldown(InventoryH);
                        }

                        if (pos == 12) {
                            dmg *= (int) Math.ceil(1.5f * Player.getInstance().getAdI());
                            Player.getInstance().setCooldown(InventoryH);
                        }

                        if (pos == 13) {
                            DamageDealtbyMob -= 50 * dmg;
                            dmg = 0;
                            Player.getInstance().setCooldown(InventoryH);
                        }

                        m3.applyDealtDamage(dmg, true);
                        if (width == 2) width = -1;
                        widthC = -1;
                        curr = -1;
                        if (pos != 13) ani3 = 1;
                        else ani3 = 2;
                    }

                    Player.getInstance().updateCoolDowns();
                    if (width == 4) width = -1;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && curr == 0 && InventoryH != -1) {
                    if (Player.getInstance().getInventory2Str(InventoryH).equals("Empty"))
                        return;
                    checkA = true;
                    checkUpWidth = 0;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && curr == 1 && InventoryH != -1) {
                    if (Player.getInstance().getInventory3Str(InventoryH).equals("Empty"))
                        return;
                    checkB = true;
                    checkUpWidth = 0;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && (curr == 0 || curr == 1)) {
                    InventoryH = 0;
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && curr == 2 && width != -1) {
                    if (width == 0) {
                        int dmg = Player.getInstance().getAdI();
                        m1.applyDealtDamage(dmg, true);
                        width = -1;
                        curr = -1;
                        ani1 = 1;
                    }

                    if (width == 1) {
                        int dmg = Player.getInstance().getAdI();
                        m2.applyDealtDamage(dmg, true);
                        width = -1;
                        curr = -1;
                        ani2 = 1;
                    }

                    if (width == 2) {
                        int dmg = Player.getInstance().getAdI();
                        m3.applyDealtDamage(dmg, true);
                        width = -1;
                        curr = -1;
                        ani3 = 1;
                    }

                    Player.getInstance().updateCoolDowns();
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER && curr == 2) {
                    width = 0;
                    int nr = howMany;
                    while (true) {
                        width = ((width - 1) % nr + nr) % nr;
                        if (nr >= 1 && width == 0 && m1.currHealth() != 0) break;
                        if (nr >= 2 && width == 1 && m2.currHealth() != 0) break;
                        if (nr >= 3 && width == 2 && m3.currHealth() != 0) break;
                    }
                    return;
                }


            }
        };

        window.addKeyListener(EncounterListener);
    }
}
