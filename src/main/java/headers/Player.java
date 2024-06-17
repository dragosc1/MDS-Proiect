package headers;

import headers.Utility.Items;
import headers.Utility.Skills;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.*;

public class Player implements Serializable {
    private static Player single_instance = null;
    private String name;
    private Trait trait;
    private ArrayList<Status> statuses;
    private int Gold, Supply;
    private int currTier;
    private int InventorySpace, PotionsSpace, SInventorySpace;
    private transient ArrayList<Image> inventory1 = new ArrayList<>(), inventory2 = new ArrayList<>(), inventory3 = new ArrayList<>();
    private ArrayList<String> nameinventory1 = new ArrayList<>(), nameinventory2 = new ArrayList<>(), nameinventory3 = new ArrayList<>();
    private ArrayList<Integer> price1 = new ArrayList<>(), price2 = new ArrayList<>();
    private ArrayList<Integer> dungeons = new ArrayList<>();
    private ArrayList<Integer> invtiers = new ArrayList<>();
    private ArrayList<Integer> cooldowns = new ArrayList<>();
    private transient Image heldWeapon, heldArmour;

    public Player(String name, Trait trait, ArrayList<Status> statuses) {
        this.name = name;
        this.trait = trait;
        this.statuses = statuses;
        this.Gold = 500;
        this.Supply = 100;
        this.currTier = 1;
        this.SInventorySpace = 3;
        heldWeapon = new ImageIcon("assets/Character/EmptyIcon.png").getImage();
        heldArmour = new ImageIcon("assets/Character/EmptyIcon.png").getImage();
        this.InventorySpace = this.PotionsSpace = 10;
        for (int i = 0; i < 5; ++i) {
            dungeons.add(0);
        }
    }

    public static void setInstance(Player player) {
        single_instance = player;
    }

    public void updateSInventorySpace() {
        ++this.SInventorySpace;
    }

    public int getSInventorySpace() {
        return this.SInventorySpace;
    }

    public int getCurrPlayerTier() {
        return this.currTier;
    }

    public void progressCurrPlayerTier() {
        ++currTier;
    }

    public String getName() {
        return name;
    }

    public void saveSettings() {
        int idx = 0;
        trait.setImageCorrect();
        inventory1 = new ArrayList<>();
        inventory2 = new ArrayList<>();
        inventory3 = new ArrayList<>();
        for (String name : nameinventory1) {
            String type = Items.getInstance().getTypeFromName(name);
            String what = Items.getInstance().getWhatFromName(name);
            int tier = invtiers.get(idx);
            Image x = Items.getInstance().getImageFromX(what, type, tier);
            inventory1.add(x);
            ++idx;
        }

        for (String name : nameinventory2) {
            int pos = Items.getInstance().getIndexPotion(name);
            inventory2.add(Items.getInstance().getPotion(pos));
        }

        for (String name : nameinventory3) {
            int pos = Skills.getInstance().index(name);
            inventory3.add(Skills.getInstance().getSkill(pos));
        }

        heldWeapon = new ImageIcon("assets/Character/EmptyIcon.png").getImage();
        heldArmour = new ImageIcon("assets/Character/EmptyIcon.png").getImage();
    }

    public Trait getTrait() {
        return trait;
    }

    public synchronized int getDungeon(int pos) {
        return dungeons.get(pos);
    }

    public void addDungeon(int pos) {
        int curr = dungeons.get(pos);
        dungeons.set(pos, curr + 1);
    }

    public int getInventorySpace() {
        return InventorySpace;
    }

    public int getPotionsSpace() {
        return PotionsSpace;
    }

    public synchronized int getGold() {
        return Gold;
    }

    public synchronized int getSupplies() {
        return Supply;
    }

    public synchronized void subtractFromGold(int cap) {
        Gold -= cap;
        Gold = max(0, Gold);
    }

    public synchronized void addSupplies(int cap) {
        Supply = min(100, Supply + cap);
    }

    public synchronized void subSupplies() {
        --Supply;
    }

    public void addItem1(Image item, String name, Integer price, int tier) {
        assert InventorySpace != 0;
        inventory1.add(item);
        nameinventory1.add(name);
        price1.add(price);
        invtiers.add(tier);
        --InventorySpace;
    }

    public void remItem1(int position) {
        inventory1.remove(position);
        nameinventory1.remove(position);
        price1.remove(position);
        invtiers.remove(position);
        ++InventorySpace;
        assert InventorySpace != 11;
    }

    public void addItem2(Image item, String name, Integer price) {
        assert PotionsSpace != 0;
        inventory2.add(item);
        nameinventory2.add(name);
        price2.add(price);
        --PotionsSpace;
    }

    public void addItem3(Image item, String name) {
        assert SInventorySpace != 0;
        inventory3.add(item);
        nameinventory3.add(name);
        cooldowns.add(0);
        --SInventorySpace;
    }

    public void setCooldown(int pos) {
        int idx = Skills.getInstance().index(nameinventory3.get(pos));
        if (idx == 1) cooldowns.set(pos, 3);
        if (idx == 2) cooldowns.set(pos, 5);
        if (idx == 3) cooldowns.set(pos, 10);
        if (idx == 11) cooldowns.set(pos, 5);
        if (idx == 12) cooldowns.set(pos, 4);
        if (idx == 13) cooldowns.set(pos, 5);
    }

    public Boolean checkCoolDownI(int pos) {
        int idx = Skills.getInstance().index(nameinventory3.get(pos));
        if (idx == 0) return true;

        if (idx == 1) {
            if (cooldowns.get(pos) != 0) {
                //cooldowns.set(pos, cooldowns.get(pos) - 1);
                return false;
            }
            return true;
        }

        if (idx == 2) {
            if (cooldowns.get(pos) != 0) {
                //cooldowns.set(pos, cooldowns.get(pos) - 1);
                return false;
            }
            return true;
        }

        if (idx == 3) {
            if (cooldowns.get(pos) != 0) {
                //cooldowns.set(pos, cooldowns.get(pos) - 1);
                return false;
            }
            return true;
        }

        if (idx <= 10) {
            return true;
        }

        if (idx == 11) {
            if (cooldowns.get(pos) != 0) {
                //cooldowns.set(pos, cooldowns.get(pos) - 1);
                return false;
            }
            return true;
        }

        if (idx == 12) {
            if (cooldowns.get(pos) != 0) {
                //cooldowns.set(pos, cooldowns.get(pos) - 1);
                return false;
            }
            return true;
        }

        if (cooldowns.get(pos) != 0) {
            //cooldowns.set(pos, cooldowns.get(pos) - 1);
            return false;
        }
        return true;
    }

    public void updateCoolDown(int pos) {
        int idx = Skills.getInstance().index(nameinventory3.get(pos));
        if (idx == 0)
            return;

        if (idx == 1) {
            if (cooldowns.get(pos) != 0) {
                cooldowns.set(pos, cooldowns.get(pos) - 1);
            }
        }

        if (idx == 2) {
            if (cooldowns.get(pos) != 0) {
                cooldowns.set(pos, cooldowns.get(pos) - 1);
            }
        }

        if (idx == 3) {
            if (cooldowns.get(pos) != 0) {
                cooldowns.set(pos, cooldowns.get(pos) - 1);
            }
            return;
        }

        if (idx <= 10)
            return;

        if (idx == 11) {
            if (cooldowns.get(pos) != 0) {
                cooldowns.set(pos, cooldowns.get(pos) - 1);
            }
            return;
        }

        if (idx == 12) {
            if (cooldowns.get(pos) != 0) {
                cooldowns.set(pos, cooldowns.get(pos) - 1);
            }
            return;
        }

        if (cooldowns.get(pos) != 0) {
            cooldowns.set(pos, cooldowns.get(pos) - 1);
        }
        return;
    }

    public void updateCoolDowns() {
        int idx = 0;
        for (String name : nameinventory3) {
            updateCoolDown(idx);
            ++idx;
        }
    }

    public Boolean checkItem3(String name) {
        return nameinventory3.contains(name);
    }

    public void remItem2(int position) {
        inventory2.remove(position);
        nameinventory2.remove(position);
        price2.remove(position);
        ++PotionsSpace;
        assert PotionsSpace != 11;
    }

    public Image getInventory1Img(int pos) {
        if (pos >= inventory1.size()) return new ImageIcon("assets/Character/EmptyIcon.png").getImage();
        return inventory1.get(pos);
    }

    public String getInventory1Str(int pos) {
        if (pos >= nameinventory1.size()) return "Empty";
        return nameinventory1.get(pos);
    }

    public Image getInventory2Img(int pos) {
        if (pos >= inventory2.size()) return new ImageIcon("assets/Character/EmptyIcon.png").getImage();
        return inventory2.get(pos);
    }

    public String getInventory2Str(int pos) {
        if (pos >= nameinventory2.size()) return "Empty";
        return nameinventory2.get(pos);
    }

    public Image getInventory3Img(int pos) {
        if (pos >= inventory3.size()) return new ImageIcon("assets/Character/EmptyIcon.png").getImage();
        return inventory3.get(pos);
    }

    public String getInventory3Str(int pos) {
        if (pos >= nameinventory3.size()) return "Empty";
        return nameinventory3.get(pos);
    }

    public void upgradeItem1(int pos, Image item, String name, Integer price, int tier) {
        assert pos < nameinventory1.size();
        inventory1.set(pos, item);
        nameinventory1.set(pos, name);
        price1.set(pos, price);
        invtiers.add(pos, tier);
        if (name.toLowerCase().contains("armour")) setHeldArmour(item);
        else setHeldWeapon(item);
    }

    public void upgradeStatus(int pos) {
        statuses.get(pos).addPoint();
    }

    public ArrayList<Status> getStatuses() {
        return statuses;
    }

    public Integer getStatus(int pos) {
        return statuses.get(pos).getPoints();
    }

    public String getItemName(int position) {
        return nameinventory1.get(position);
    }

    public String getItem2Name(int position) {
        return nameinventory2.get(position);
    }

    public Integer getItemPrice(int position) {
        return price1.get(position);
    }

    public Integer getItem2Price(int position) {
        return price2.get(position);
    }

    public Integer getItemTier(int pos) {
        if (pos >= invtiers.size()) return -1;
        return invtiers.get(pos);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTraits(Trait trait) {
        this.trait = trait;
    }

    public void setStatuses(ArrayList<Status> statuses) {
        this.statuses = statuses;
    }

    public synchronized int applyCharisma(int price) {
        double x = price;
        x *= (100.0 / (100.0 + 6.0 * (getStatus(3) - 1)));
        x = Math.ceil(x);
        return (int) x;
    }

    public synchronized int RunPassivesHp() {
        int value = 0;
        return value;
    }

    public synchronized String getHp() {
        double value = 100;
        if (nameinventory3.contains("Healthy Physique"))
            value += 50;

        int idx1 = inventory1.indexOf(getHeldArmour());
        if (idx1 != -1) {
            String name = nameinventory1.get(idx1);
            String type = Items.getInstance().getTypeFromName(name);
            int tier = invtiers.get(idx1);

            double mutiplier = 1;
            if (type.equalsIgnoreCase("common")) {
                if (tier == 0) {
                    value += 25;
                    mutiplier = (tier + 1) * 2;
                } else {
                    value += tier * 50;
                    mutiplier = tier * 2;
                }
            } else if (type.equalsIgnoreCase("rare")) {
                if (tier == 0) {
                    value += 50;
                    mutiplier = (tier + 1) * 4;
                } else {
                    value += tier * 75;
                    mutiplier = tier * 4;
                }
            } else if (type.equalsIgnoreCase("epic")) {
                if (tier == 0) {
                    value += 75;
                    mutiplier = (tier + 1) * 6;
                } else {
                    value += tier * 100;
                    mutiplier = tier * 6;
                }
            } else if (type.equalsIgnoreCase("legendary")) {
                if (tier == 0) {
                    value += 100;
                    mutiplier = (tier + 1) * 8;
                } else {
                    value += tier * 150;
                    mutiplier = tier * 8;
                }
            } else if (type.equalsIgnoreCase("mythical")) {
                if (tier == 0) {
                    value += 125;
                    mutiplier = (tier + 1) * 10;
                } else {
                    value += tier * 200;
                    mutiplier = tier * 10;
                }
            }
            value = (value * mutiplier);
        }

        int idx2 = inventory1.indexOf(getHeldWeapon());
        if (idx2 != -1) {
            String name2 = nameinventory1.get(idx2);
            String what = Items.getInstance().getWhatFromName(name2);
            String type = Items.getInstance().getTypeFromName(name2);
            int tier = invtiers.get(idx2);

            double mutiplier = 1;
            if (what.equalsIgnoreCase("sword")) {
                if (type.equalsIgnoreCase("common")) {
                    mutiplier = (tier == 0? 1 : tier) * 5;
                } else if (type.equalsIgnoreCase("rare")) {
                    mutiplier = (tier == 0? 1 : tier)  * 10;
                } else if (type.equalsIgnoreCase("epic")) {
                    mutiplier = (tier == 0? 1 : tier)  * 15;
                } else if (type.equalsIgnoreCase("legendary")) {
                    mutiplier = (tier == 0? 1 : tier)  * 25;
                } else if (type.equalsIgnoreCase("mythical")) {
                    mutiplier = (tier == 0? 1 : tier)  * 35;
                }
            }

            value = value * mutiplier;
        }

        int Hp = (int)Math.ceil(value);
        if (Hp < 1000) return String.valueOf(Hp);
        return Hp / 1000 + "." + (Hp / 100) % 10 + " K";
    }

    public synchronized Boolean heals() {
        return nameinventory3.contains("Revitalize");
    }

    public synchronized String getHp(int damage) {
        double value = 100;
        if (nameinventory3.contains("Healthy Physique"))
            value += 50;

        int idx1 = inventory1.indexOf(getHeldArmour());
        if (idx1 != -1) {
            String name = nameinventory1.get(idx1);
            String type = Items.getInstance().getTypeFromName(name);
            int tier = invtiers.get(idx1);

            double mutiplier = 1;
            if (type.equalsIgnoreCase("common")) {
                if (tier == 0) {
                    value += 25;
                    mutiplier = (tier + 1) * 2;
                } else {
                    value += tier * 50;
                    mutiplier = tier * 2;
                }
            } else if (type.equalsIgnoreCase("rare")) {
                if (tier == 0) {
                    value += 50;
                    mutiplier = (tier + 1) * 4;
                } else {
                    value += tier * 75;
                    mutiplier = tier * 4;
                }
            } else if (type.equalsIgnoreCase("epic")) {
                if (tier == 0) {
                    value += 75;
                    mutiplier = (tier + 1) * 6;
                } else {
                    value += tier * 100;
                    mutiplier = tier * 6;
                }
            } else if (type.equalsIgnoreCase("legendary")) {
                if (tier == 0) {
                    value += 100;
                    mutiplier = (tier + 1) * 8;
                } else {
                    value += tier * 150;
                    mutiplier = tier * 8;
                }
            } else if (type.equalsIgnoreCase("mythical")) {
                if (tier == 0) {
                    value += 125;
                    mutiplier = (tier + 1) * 10;
                } else {
                    value += tier * 200;
                    mutiplier = tier * 10;
                }
            }
            value = (value * mutiplier);
        }

        int idx2 = inventory1.indexOf(getHeldWeapon());
        if (idx2 != -1) {
            String name2 = nameinventory1.get(idx2);
            String what = Items.getInstance().getWhatFromName(name2);
            String type = Items.getInstance().getTypeFromName(name2);
            int tier = invtiers.get(idx2);

            double mutiplier = 1;
            if (what.equalsIgnoreCase("sword")) {
                if (type.equalsIgnoreCase("common")) {
                    mutiplier = (tier == 0? 1 : tier) * 5;
                } else if (type.equalsIgnoreCase("rare")) {
                    mutiplier = (tier == 0? 1 : tier)  * 10;
                } else if (type.equalsIgnoreCase("epic")) {
                    mutiplier = (tier == 0? 1 : tier)  * 15;
                } else if (type.equalsIgnoreCase("legendary")) {
                    mutiplier = (tier == 0? 1 : tier)  * 25;
                } else if (type.equalsIgnoreCase("mythical")) {
                    mutiplier = (tier == 0? 1 : tier)  * 35;
                }
            }

            value = value * mutiplier;
        }

        int Hp = (int)Math.ceil(value) - damage;
        if (Hp <= 0) Hp = 0;
        if (Hp < 1000) return String.valueOf(Hp);
        return Hp / 1000 + "." + (Hp / 100) % 10 + " K";
    }

    public synchronized Integer getHpI(int damage) {
        double value = 100;
        if (nameinventory3.contains("Healthy Physique"))
            value += 50;

        int idx1 = inventory1.indexOf(getHeldArmour());
        if (idx1 != -1) {
            String name = nameinventory1.get(idx1);
            String type = Items.getInstance().getTypeFromName(name);
            int tier = invtiers.get(idx1);

            double mutiplier = 1;
            if (type.equalsIgnoreCase("common")) {
                if (tier == 0) {
                    value += 25;
                    mutiplier = (tier + 1) * 2;
                } else {
                    value += tier * 50;
                    mutiplier = tier * 2;
                }
            } else if (type.equalsIgnoreCase("rare")) {
                if (tier == 0) {
                    value += 50;
                    mutiplier = (tier + 1) * 4;
                } else {
                    value += tier * 75;
                    mutiplier = tier * 4;
                }
            } else if (type.equalsIgnoreCase("epic")) {
                if (tier == 0) {
                    value += 75;
                    mutiplier = (tier + 1) * 6;
                } else {
                    value += tier * 100;
                    mutiplier = tier * 6;
                }
            } else if (type.equalsIgnoreCase("legendary")) {
                if (tier == 0) {
                    value += 100;
                    mutiplier = (tier + 1) * 8;
                } else {
                    value += tier * 150;
                    mutiplier = tier * 8;
                }
            } else if (type.equalsIgnoreCase("mythical")) {
                if (tier == 0) {
                    value += 125;
                    mutiplier = (tier + 1) * 10;
                } else {
                    value += tier * 200;
                    mutiplier = tier * 10;
                }
            }
            value = (value * mutiplier);
        }

        int idx2 = inventory1.indexOf(getHeldWeapon());
        if (idx2 != -1) {
            String name2 = nameinventory1.get(idx2);
            String what = Items.getInstance().getWhatFromName(name2);
            String type = Items.getInstance().getTypeFromName(name2);
            int tier = invtiers.get(idx2);

            double mutiplier = 1;
            if (what.equalsIgnoreCase("sword")) {
                if (type.equalsIgnoreCase("common")) {
                    mutiplier = (tier == 0? 1 : tier) * 5;
                } else if (type.equalsIgnoreCase("rare")) {
                    mutiplier = (tier == 0? 1 : tier)  * 10;
                } else if (type.equalsIgnoreCase("epic")) {
                    mutiplier = (tier == 0? 1 : tier)  * 15;
                } else if (type.equalsIgnoreCase("legendary")) {
                    mutiplier = (tier == 0? 1 : tier)  * 25;
                } else if (type.equalsIgnoreCase("mythical")) {
                    mutiplier = (tier == 0? 1 : tier)  * 35;
                }
            }

            value = value * mutiplier;
        }

        int Hp = (int)Math.ceil(value) - damage;
        if (Hp <= 0) Hp = 0;
        return Hp;
    }

    public synchronized String getAd() {
        int ad = getStatus(0) * 10;
        if (nameinventory3.contains("Pure Muscle"))
            ad += 30;

        if (getTrait().getName().contains("The Strong"))
            ad += (int)Math.ceil((double)ad * 0.10);


        int idx2 = inventory1.indexOf(getHeldWeapon());

        if (idx2 != -1) {
            String name2 = nameinventory1.get(idx2);
            String what = Items.getInstance().getWhatFromName(name2);
            String type = Items.getInstance().getTypeFromName(name2);
            int tier = invtiers.get(idx2);

            if (what.equalsIgnoreCase("sword")) {
                if (type.equalsIgnoreCase("common")) {
                    ad += (tier == 0 ? 1 : tier) * 10;
                } else if (type.equalsIgnoreCase("rare")) {
                    ad += (tier == 0 ? 1 : tier) * 15;
                } else if (type.equalsIgnoreCase("epic")) {
                    ad += (tier == 0 ? 1 : tier) * 20;
                } else if (type.equalsIgnoreCase("legendary")) {
                    ad += (tier == 0 ? 1 : tier) * 25;
                } else if (type.equalsIgnoreCase("mythical")) {
                    ad += (tier == 0 ? 1 : tier) * 30;
                }
            }

            if (what.equalsIgnoreCase("bow")) {
                if (type.equalsIgnoreCase("common")) {
                    ad += (tier == 0 ? 1 : tier) * 15;
                } else if (type.equalsIgnoreCase("rare")) {
                    ad += (tier == 0 ? 1 : tier) * 20;
                } else if (type.equalsIgnoreCase("epic")) {
                    ad += (tier == 0 ? 1 : tier) * 25;
                } else if (type.equalsIgnoreCase("legendary")) {
                    ad += (tier == 0 ? 1 : tier) * 35;
                } else if (type.equalsIgnoreCase("mythical")) {
                    ad += (tier == 0 ? 1 : tier) * 40;
                }
            }

            if (what.equalsIgnoreCase("staff")) {
                if (type.equalsIgnoreCase("common")) {
                    ad += (tier == 0 ? 1 : tier) * 2;
                } else if (type.equalsIgnoreCase("rare")) {
                    ad += (tier == 0 ? 1 : tier) * 4;
                } else if (type.equalsIgnoreCase("epic")) {
                    ad += (tier == 0 ? 1 : tier) * 6;
                } else if (type.equalsIgnoreCase("legendary")) {
                    ad += (tier == 0 ? 1 : tier) * 8;
                } else if (type.equalsIgnoreCase("mythical")) {
                    ad += (tier == 0 ? 1 : tier) * 10 ;
                }
            }
        }

        if (ad < 1000) return String.valueOf(ad);
        return ad / 1000 + "." + (ad / 100) % 10 + " K";
    }


    public synchronized Integer getAdI() {
        int ad = getStatus(0) * 10;
        if (getTrait().getName().contains("The Strong"))
            ad += (int)Math.ceil((double)ad * 0.10);

        int idx2 = inventory1.indexOf(getHeldWeapon());

        if (idx2 != -1) {
            String name2 = nameinventory1.get(idx2);
            String what = Items.getInstance().getWhatFromName(name2);
            String type = Items.getInstance().getTypeFromName(name2);
            int tier = invtiers.get(idx2);

            if (what.equalsIgnoreCase("sword")) {
                if (type.equalsIgnoreCase("common")) {
                    ad += (tier == 0 ? 1 : tier) * 10;
                } else if (type.equalsIgnoreCase("rare")) {
                    ad += (tier == 0 ? 1 : tier) * 15;
                } else if (type.equalsIgnoreCase("epic")) {
                    ad += (tier == 0 ? 1 : tier) * 20;
                } else if (type.equalsIgnoreCase("legendary")) {
                    ad += (tier == 0 ? 1 : tier) * 25;
                } else if (type.equalsIgnoreCase("mythical")) {
                    ad += (tier == 0 ? 1 : tier) * 30;
                }
            }

            if (what.equalsIgnoreCase("bow")) {
                if (type.equalsIgnoreCase("common")) {
                    ad += (tier == 0 ? 1 : tier) * 15;
                } else if (type.equalsIgnoreCase("rare")) {
                    ad += (tier == 0 ? 1 : tier) * 20;
                } else if (type.equalsIgnoreCase("epic")) {
                    ad += (tier == 0 ? 1 : tier) * 25;
                } else if (type.equalsIgnoreCase("legendary")) {
                    ad += (tier == 0 ? 1 : tier) * 35;
                } else if (type.equalsIgnoreCase("mythical")) {
                    ad += (tier == 0 ? 1 : tier) * 40;
                }
            }

            if (what.equalsIgnoreCase("staff")) {
                if (type.equalsIgnoreCase("common")) {
                    ad += (tier == 0 ? 1 : tier) * 2;
                } else if (type.equalsIgnoreCase("rare")) {
                    ad += (tier == 0 ? 1 : tier) * 4;
                } else if (type.equalsIgnoreCase("epic")) {
                    ad += (tier == 0 ? 1 : tier) * 6;
                } else if (type.equalsIgnoreCase("legendary")) {
                    ad += (tier == 0 ? 1 : tier) * 8;
                } else if (type.equalsIgnoreCase("mythical")) {
                    ad += (tier == 0 ? 1 : tier) * 10 ;
                }
            }
        }

        return ad;
    }

    public synchronized String getAp() {
        int ap = getStatus(2) * 15;

        if (nameinventory3.contains("Scholar Ship"))
            ap += 30;

        if (getTrait().getName().contains("The Wise"))
            ap += (int)Math.ceil((double)ap * 0.10);

        int idx2 = inventory1.indexOf(getHeldWeapon());

        if (idx2 != -1) {
            String name2 = nameinventory1.get(idx2);
            String what = Items.getInstance().getWhatFromName(name2);
            String type = Items.getInstance().getTypeFromName(name2);
            int tier = invtiers.get(idx2);

            if (what.equalsIgnoreCase("staff")) {
                if (type.equalsIgnoreCase("common")) {
                    ap += (tier == 0 ? 1 : tier) * 10;
                } else if (type.equalsIgnoreCase("rare")) {
                    ap += (tier == 0 ? 1 : tier) * 15;
                } else if (type.equalsIgnoreCase("epic")) {
                    ap += (tier == 0 ? 1 : tier) * 20;
                } else if (type.equalsIgnoreCase("legendary")) {
                    ap += (tier == 0 ? 1 : tier) * 25;
                } else if (type.equalsIgnoreCase("mythical")) {
                    ap += (tier == 0 ? 1 : tier) * 30;
                }
            }
        }

        if (ap < 1000) return String.valueOf(ap);
        return ap / 1000 + "." + (ap / 100) % 10 + " K";
    }

    public synchronized Integer getApI() {
        int ap = getStatus(2) * 15;

        if (nameinventory3.contains("Scholar Ship"))
            ap += 30;

        if (getTrait().getName().contains("The Wise"))
            ap += (int)Math.ceil((double)ap * 0.10);

        int idx2 = inventory1.indexOf(getHeldWeapon());

        if (idx2 != -1) {
            String name2 = nameinventory1.get(idx2);
            String what = Items.getInstance().getWhatFromName(name2);
            String type = Items.getInstance().getTypeFromName(name2);
            int tier = invtiers.get(idx2);

            if (what.equalsIgnoreCase("staff")) {
                if (type.equalsIgnoreCase("common")) {
                    ap += (tier == 0 ? 1 : tier) * 10;
                } else if (type.equalsIgnoreCase("rare")) {
                    ap += (tier == 0 ? 1 : tier) * 15;
                } else if (type.equalsIgnoreCase("epic")) {
                    ap += (tier == 0 ? 1 : tier) * 20;
                } else if (type.equalsIgnoreCase("legendary")) {
                    ap += (tier == 0 ? 1 : tier) * 25;
                } else if (type.equalsIgnoreCase("mythical")) {
                    ap += (tier == 0 ? 1 : tier) * 30;
                }
            }
        }

        return ap;
    }

    public synchronized String getArm() {
        double value = 0;
        if (nameinventory3.contains("Armor Work"))
            value += 10;
        if (nameinventory3.contains("Stone Skin"))
            value += 15;

        int idx1 = inventory1.indexOf(getHeldArmour());
        if (idx1 != -1) {
            String name = nameinventory1.get(idx1);
            String type = Items.getInstance().getTypeFromName(name);
            int tier = invtiers.get(idx1);

            double mutiplier = 1;
            if (type.equalsIgnoreCase("common")) {
                if (tier == 0) {
                    value += 2;
                    mutiplier = (tier + 1) * 2;
                } else {
                    value += tier * 2;
                    mutiplier = tier * 2;
                }
            } else if (type.equalsIgnoreCase("rare")) {
                if (tier == 0) {
                    value += 4;
                    mutiplier = (tier + 1) * 4;
                } else {
                    value += tier * 4;
                    mutiplier = tier * 4;
                }
            } else if (type.equalsIgnoreCase("epic")) {
                if (tier == 0) {
                    value += 6;
                    mutiplier = (tier + 1) * 6;
                } else {
                    value += tier * 6;
                    mutiplier = tier * 6;
                }
            } else if (type.equalsIgnoreCase("legendary")) {
                if (tier == 0) {
                    value += 8;
                    mutiplier = (tier + 1) * 8;
                } else {
                    value += tier * 8;
                    mutiplier = tier * 8;
                }
            } else if (type.equalsIgnoreCase("mythical")) {
                if (tier == 0) {
                    value += 10;
                    mutiplier = (tier + 1) * 10;
                } else {
                    value += tier * 10;
                    mutiplier = tier * 10;
                }
            }
            value = (value * mutiplier);
        }

        int idx2 = inventory1.indexOf(getHeldWeapon());
        if (idx2 != -1) {
            String name2 = nameinventory1.get(idx2);
            String what = Items.getInstance().getWhatFromName(name2);
            String type = Items.getInstance().getTypeFromName(name2);
            int tier = invtiers.get(idx2);

            double mutiplier = 1;
            if (what.equalsIgnoreCase("sword")) {
                if (type.equalsIgnoreCase("common")) {
                    mutiplier = (tier == 0? 1 : tier) * 5;
                } else if (type.equalsIgnoreCase("rare")) {
                    mutiplier = (tier == 0? 1 : tier)  * 10;
                } else if (type.equalsIgnoreCase("epic")) {
                    mutiplier = (tier == 0? 1 : tier)  * 15;
                } else if (type.equalsIgnoreCase("legendary")) {
                    mutiplier = (tier == 0? 1 : tier)  * 25;
                } else if (type.equalsIgnoreCase("mythical")) {
                    mutiplier = (tier == 0? 1 : tier)  * 35;
                }
            }

            value = value * mutiplier;
        }

        int Arm = (int)Math.ceil(value);
        if (Arm < 1000) return String.valueOf(Arm);
        return Arm / 1000 + "." + (Arm / 100) % 10 + " K";
    }

    public synchronized int getArmI() {
        double value = 20;

        int idx1 = inventory1.indexOf(getHeldArmour());
        if (idx1 != -1) {
            String name = nameinventory1.get(idx1);
            String type = Items.getInstance().getTypeFromName(name);
            int tier = invtiers.get(idx1);

            double mutiplier = 1;
            if (type.equalsIgnoreCase("common")) {
                if (tier == 0) {
                    value += 2;
                    mutiplier = (tier + 1) * 2;
                } else {
                    value += tier * 2;
                    mutiplier = tier * 2;
                }
            } else if (type.equalsIgnoreCase("rare")) {
                if (tier == 0) {
                    value += 4;
                    mutiplier = (tier + 1) * 4;
                } else {
                    value += tier * 4;
                    mutiplier = tier * 4;
                }
            } else if (type.equalsIgnoreCase("epic")) {
                if (tier == 0) {
                    value += 6;
                    mutiplier = (tier + 1) * 6;
                } else {
                    value += tier * 6;
                    mutiplier = tier * 6;
                }
            } else if (type.equalsIgnoreCase("legendary")) {
                if (tier == 0) {
                    value += 8;
                    mutiplier = (tier + 1) * 8;
                } else {
                    value += tier * 8;
                    mutiplier = tier * 8;
                }
            } else if (type.equalsIgnoreCase("mythical")) {
                if (tier == 0) {
                    value += 10;
                    mutiplier = (tier + 1) * 10;
                } else {
                    value += tier * 10;
                    mutiplier = tier * 10;
                }
            }
            value = (value * mutiplier);
        }

        int idx2 = inventory1.indexOf(getHeldWeapon());
        if (idx2 != -1) {
            String name2 = nameinventory1.get(idx2);
            String what = Items.getInstance().getWhatFromName(name2);
            String type = Items.getInstance().getTypeFromName(name2);
            int tier = invtiers.get(idx2);

            double mutiplier = 1;
            if (what.equalsIgnoreCase("sword")) {
                if (type.equalsIgnoreCase("common")) {
                    mutiplier = (tier == 0? 1 : tier) * 5;
                } else if (type.equalsIgnoreCase("rare")) {
                    mutiplier = (tier == 0? 1 : tier)  * 10;
                } else if (type.equalsIgnoreCase("epic")) {
                    mutiplier = (tier == 0? 1 : tier)  * 15;
                } else if (type.equalsIgnoreCase("legendary")) {
                    mutiplier = (tier == 0? 1 : tier)  * 25;
                } else if (type.equalsIgnoreCase("mythical")) {
                    mutiplier = (tier == 0? 1 : tier)  * 35;
                }
            }

            value = value * mutiplier;
        }

        int Arm = (int)Math.ceil(value);
        return Arm;
    }

    public synchronized double getCrit() {
        double value = 2 * (0.10 * getStatus(1));
        int idx2 = inventory1.indexOf(getHeldWeapon());

        if (nameinventory3.contains("Deadly Precision"))
            value = value * 0.05;

        if (nameinventory3.contains("Pure Muscles"))
            value = value * 0.3;

        if (getTrait().getName().contains("The Swift"))
            value += Math.ceil(value * 0.10);

        if (idx2 != -1) {
            String name2 = nameinventory1.get(idx2);
            String what = Items.getInstance().getWhatFromName(name2);
            String type = Items.getInstance().getTypeFromName(name2);
            int tier = invtiers.get(idx2);

            double mutiplier = (tier == 0? 1 : tier) * getStatus(1);
            if (getTrait().getName().contains("The Swift"))
                mutiplier *= 0.2;

            if (what.equalsIgnoreCase("sword")) {
                if (type.equalsIgnoreCase("common")) {
                    mutiplier = (tier == 0 ? 1 : tier);
                } else if (type.equalsIgnoreCase("rare")) {
                    mutiplier = (tier == 0? 1 : tier)  * 2;
                } else if (type.equalsIgnoreCase("epic")) {
                    mutiplier = (tier == 0? 1 : tier)  * 3;
                } else if (type.equalsIgnoreCase("legendary")) {
                    mutiplier = (tier == 0? 1 : tier)  * 4;
                } else if (type.equalsIgnoreCase("mythical")) {
                    mutiplier = (tier == 0? 1 : tier)  * 5;
                }
            }

            if (what.equalsIgnoreCase("bow")) {
                if (type.equalsIgnoreCase("common")) {
                    value = value * 0.10;
                    mutiplier = (tier == 0? 1 : tier) * 2;
                } else if (type.equalsIgnoreCase("rare")) {
                    value = value * 0.20;
                    mutiplier = (tier == 0? 1 : tier)  * 4;
                } else if (type.equalsIgnoreCase("epic")) {
                    value = value * 0.30;
                    mutiplier = (tier == 0? 1 : tier)  * 6;
                } else if (type.equalsIgnoreCase("legendary")) {
                    value = value * 0.40;
                    mutiplier = (tier == 0? 1 : tier)  * 8;
                } else if (type.equalsIgnoreCase("mythical")) {
                    value = value * 0.50;
                    mutiplier = (tier == 0? 1 : tier)  * 10;
                }
            }

            value = value * mutiplier;
        }

        return value;
    }

    public synchronized Boolean dealCrit() {
        Random rand = new Random();
        return rand.nextDouble() < 0.1;
    }

    public void setHeldWeapon(Image ig) {
        heldWeapon = ig;
    }

    public void setHeldArmour(Image ig) {
        heldArmour = ig;
    }

    public Image getHeldWeapon() {
        return heldWeapon;
    }

    public Image getHeldArmour() {
        return heldArmour;
    }

    public static synchronized Player getInstance(String name, Trait trait, ArrayList<Status> statuses) {
        if (single_instance == null)
            single_instance = new Player(name, trait, statuses);

        return single_instance;
    }

    public static synchronized Player getInstance() {
        assert single_instance != null;
        return single_instance;
    }
}