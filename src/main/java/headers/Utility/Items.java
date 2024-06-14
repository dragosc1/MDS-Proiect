package headers.Utility;

import headers.Player;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.File;

public class Items {
    private static Items single_instance = null;
    private final ArrayList<ImageIcon> swordsCommon = new ArrayList<>();
    private final ArrayList<ImageIcon> staffCommon = new ArrayList<>();
    private final ArrayList<ImageIcon> bowCommon = new ArrayList<>();
    private final ArrayList<ImageIcon> armourCommon = new ArrayList<>();
    private final ArrayList<ImageIcon> swordsRare = new ArrayList<>();
    private final ArrayList<ImageIcon> staffRare = new ArrayList<>();
    private final ArrayList<ImageIcon> bowRare = new ArrayList<>();
    private final ArrayList<ImageIcon> armourRare = new ArrayList<>();
    private final ArrayList<ImageIcon> swordsEpic = new ArrayList<>();
    private final ArrayList<ImageIcon> staffEpic = new ArrayList<>();
    private final ArrayList<ImageIcon> bowEpic = new ArrayList<>();
    private final ArrayList<ImageIcon> armourEpic = new ArrayList<>();
    private final ArrayList<ImageIcon> swordsLeg = new ArrayList<>();
    private final ArrayList<ImageIcon> staffLeg = new ArrayList<>();
    private final ArrayList<ImageIcon> bowLeg = new ArrayList<>();
    private final ArrayList<ImageIcon> armourLeg = new ArrayList<>();
    private final ArrayList<ImageIcon> swordsMyth = new ArrayList<>();
    private final ArrayList<ImageIcon> staffMyth = new ArrayList<>();
    private final ArrayList<ImageIcon> bowMyth = new ArrayList<>();
    private final ArrayList<ImageIcon> armourMyth = new ArrayList<>();
    private final static ArrayList<String> types = new ArrayList<>(Arrays.asList("Common", "Rare", "Epic", "Legendary", "Mythical"));
    private final ArrayList <Integer> pricesCommon = new ArrayList<>(), pricesCommonT = new ArrayList<>();
    private final ArrayList <Integer> pricesRare = new ArrayList<>(), pricesRareT = new ArrayList<>();
    private final ArrayList <Integer> pricesEpic = new ArrayList<>(), pricesEpicT = new ArrayList<>();
    private final ArrayList <Integer> pricesLeg = new ArrayList<>(), pricesLegT = new ArrayList<>();
    private final ArrayList <Integer> pricesMyth = new ArrayList<>(), pricesMythT = new ArrayList<>();
    private final ArrayList <Image> weaponsR = new ArrayList<>(), armourR = new ArrayList<>();
    private final ArrayList <String> nameweaponsR = new ArrayList<>(), namearmourR = new ArrayList<>();
    private final ArrayList <Integer> tierweaponR = new ArrayList<>(), tierarmourR = new ArrayList<>();
    private final ArrayList <ImageIcon> potions = new ArrayList<>();
    private final ArrayList <String> potionsName = new ArrayList<>(), potionDesciption = new ArrayList<>();
    private final ArrayList <Integer> potionsPrice = new ArrayList<>(), potionsProperties = new ArrayList<>();

    private Items() {
        initAll();
        initPotions();
        GenerateBlackSmithWeapons();
        GenerateBlackSmithArmour();
    }

    public static boolean isImagePathValid(String path) {
        File file = new File(path);
        return file.exists() && file.canRead();
    }

    private void initPotions() {
        for (int i = 1; i < 8; ++i) {
            assert isImagePathValid("assets/Potions/Potion" + i + ".png");
            potions.add(new ImageIcon("assets/Potions/Potion" + i + ".png"));
        }

        potionsName.add("Small health");
        potionsName.add("Big health");
        potionsName.add("Troll blood");
        potionsName.add("Bottle Of Madness");
        potionsName.add("Ichor");
        potionsName.add("Anconyte Toxin");
        potionsName.add("Liquid Fire");

        potionDesciption.add("Potion heals for");
        potionDesciption.add("Potion heals for");
        potionDesciption.add("Serum heals for");
        potionDesciption.add("Increase damage by 30% for the rest of round");
        potionDesciption.add("You take no damage for 5 turns ");
        potionDesciption.add("Damage for 4 turns to all enemies");
        potionDesciption.add("True damage to all enemies");

        potionsPrice.add(30);
        potionsPrice.add(50);
        potionsPrice.add(100);
        potionsPrice.add(500);
        potionsPrice.add(5000);
        potionsPrice.add(50);
        potionsPrice.add(100);

        potionsProperties.add(50);
        potionsProperties.add(100);
        potionsProperties.add(40);
        potionsProperties.add(-13000);
        potionsProperties.add(0);
        potionsProperties.add(-20);
        potionsProperties.add(-100);
    }

    private void initAll() {
        for (String type : types) {
            if (Objects.equals(type, "Common")) {
                for (String string : Arrays.asList("assets/weapon icons/amour/" + type + "Armour" + "NoTier.png", "assets/weapon icons/bows/" + type + "Bow" + "NoTier.png", "assets/weapon icons/staff/" + type + "Staff" + "NoTier.png", "assets/weapon icons/swords/Sword" + type + "NoTier.png")) {
                    assert isImagePathValid(string);
                }

                pricesCommon.add(250);
                pricesCommonT.add(250);
                armourCommon.add(new ImageIcon("assets/weapon icons/amour/" + type + "Armour" + "NoTier.png"));
                bowCommon.add(new ImageIcon("assets/weapon icons/bows/" + type + "Bow" + "NoTier.png"));
                staffCommon.add(new ImageIcon("assets/weapon icons/staff/" + type + "Staff" + "NoTier.png"));
                swordsCommon.add(new ImageIcon("assets/weapon icons/swords/Sword" + type + "NoTier.png"));


                for (int i = 1; i < 11; ++i) {
                    pricesCommon.add(i * 100);
                    pricesCommonT.add(pricesCommonT.get(i - 1) + i * 100);
                    for (String s : Arrays.asList("assets/weapon icons/amour/" + type + "ArmourTier" + i + ".png", "assets/weapon icons/bows/" + type + "BowTier" + i + ".png", "assets/weapon icons/staff/" + type + "StaffTier" + i + ".png", "assets/weapon icons/swords/Sword" + type + "Tier" + i + ".png")) {
                        assert isImagePathValid(s);
                    }

                    armourCommon.add(new ImageIcon("assets/weapon icons/amour/" + type + "ArmourTier" + i + ".png"));
                    bowCommon.add(new ImageIcon("assets/weapon icons/bows/" + type + "BowTier" + i + ".png"));
                    staffCommon.add(new ImageIcon("assets/weapon icons/staff/" + type + "StaffTier" + i + ".png"));
                    swordsCommon.add(new ImageIcon("assets/weapon icons/swords/Sword" + type + "Tier" + i + ".png"));
                }
            }

            if (Objects.equals(type, "Rare")) {
                assert isImagePathValid("assets/weapon icons/amour/" + type + "Armour" + "NoTier.png");
                assert isImagePathValid("assets/weapon icons/bows/" + type + "Bow" + "NoTier.png");
                assert isImagePathValid("assets/weapon icons/staff/" + type + "Staff" + "NoTier.png");
                assert isImagePathValid("assets/weapon icons/swords/" + type + "Sword" + "NoTier.png");

                pricesRare.add(400);
                pricesRareT.add(400);
                armourRare.add(new ImageIcon("assets/weapon icons/amour/" + type + "Armour" + "NoTier.png"));
                bowRare.add(new ImageIcon("assets/weapon icons/bows/" + type + "Bow" + "NoTier.png"));
                staffRare.add(new ImageIcon("assets/weapon icons/staff/" + type + "Staff" + "NoTier.png"));
                swordsRare.add(new ImageIcon("assets/weapon icons/swords/" + type + "Sword" + "NoTier.png"));

                for (int i = 1; i < 11; ++i) {
                    pricesRare.add(i * 250);
                    pricesRareT.add(pricesRareT.get(i - 1) + i * 250);
                    assert isImagePathValid("assets/weapon icons/amour/" + type + "ArmourTier" + i + ".png");
                    assert isImagePathValid("assets/weapon icons/bows/" + type + "BowTier" + i + ".png");
                    assert isImagePathValid("assets/weapon icons/staff/" + type + "StaffTier" + i + ".png");
                    assert isImagePathValid("assets/weapon icons/swords/" + type + "SwordTier" + i + ".png");

                    armourRare.add(new ImageIcon("assets/weapon icons/amour/" + type + "ArmourTier" + i + ".png"));
                    bowRare.add(new ImageIcon("assets/weapon icons/bows/" + type + "BowTier" + i + ".png"));
                    staffRare.add(new ImageIcon("assets/weapon icons/staff/" + type + "StaffTier" + i + ".png"));
                    swordsRare.add(new ImageIcon("assets/weapon icons/swords/" + type + "SwordTier" + i + ".png"));
                }
            }

            if (Objects.equals(type, "Epic")) {
                assert isImagePathValid("assets/weapon icons/amour/" + type + "Armour" + "NoTier.png");
                for (String string : Arrays.asList("assets/weapon icons/bows/" + type + "Bow" + "NoTier.png", "assets/weapon icons/staff/" + type + "Staff" + "NoTier.png", "assets/weapon icons/swords/" + type + "Sword" + "NoTier.png")) {
                    assert isImagePathValid(string);
                }

                pricesEpic.add(650);
                pricesEpicT.add(650);
                armourEpic.add(new ImageIcon("assets/weapon icons/amour/" + type + "Armour" + "NoTier.png"));
                bowEpic.add(new ImageIcon("assets/weapon icons/bows/" + type + "Bow" + "NoTier.png"));
                staffEpic.add(new ImageIcon("assets/weapon icons/staff/" + type + "Staff" + "NoTier.png"));
                swordsEpic.add(new ImageIcon("assets/weapon icons/swords/" + type + "Sword" + "NoTier.png"));

                for (int i = 1; i < 11; ++i) {
                    pricesEpic.add(i * 500);
                    pricesEpicT.add(pricesEpicT.get(i - 1) + i * 500);

                    for (String s : Arrays.asList("assets/weapon icons/amour/" + type + "ArmourTier" + i + ".png", "assets/weapon icons/bows/" + type + "BowTier" + i + ".png", "assets/weapon icons/staff/" + type + "StaffTier" + i + ".png", "assets/weapon icons/swords/" + type + "SwordTier" + i + ".png")) {
                        assert isImagePathValid(s);
                    }

                    armourEpic.add(new ImageIcon("assets/weapon icons/amour/" + type + "ArmourTier" + i + ".png"));
                    bowEpic.add(new ImageIcon("assets/weapon icons/bows/" + type + "BowTier" + i + ".png"));
                    staffEpic.add(new ImageIcon("assets/weapon icons/staff/" + type + "StaffTier" + i + ".png"));
                    swordsEpic.add(new ImageIcon("assets/weapon icons/swords/" + type + "SwordTier" + i + ".png"));
                }
            }

            if (Objects.equals(type, "Legendary")) {
                assert isImagePathValid("assets/weapon icons/amour/" + type + "Armour" + "NoTier.png");
                assert isImagePathValid("assets/weapon icons/bows/" + type + "Bow" + "NoTier.png");
                assert isImagePathValid("assets/weapon icons/staff/" + type + "Staff" + "NoTier.png");
                assert isImagePathValid("assets/weapon icons/swords/" + type + "Sword" + "NoTier.png");

                pricesLeg.add(1150);
                pricesLegT.add(1150);
                armourLeg.add(new ImageIcon("assets/weapon icons/amour/" + type + "Armour" + "NoTier.png"));
                bowLeg.add(new ImageIcon("assets/weapon icons/bows/" + type + "Bow" + "NoTier.png"));
                staffLeg.add(new ImageIcon("assets/weapon icons/staff/" + type + "Staff" + "NoTier.png"));
                swordsLeg.add(new ImageIcon("assets/weapon icons/swords/" + type + "Sword" + "NoTier.png"));

                for (int i = 1; i < 11; ++i) {
                    pricesLeg.add(i * 1000);
                    pricesLegT.add(pricesLegT.get(i - 1) + i * 1000);

                    for (String s : Arrays.asList("assets/weapon icons/amour/" + type + "ArmourTier" + i + ".png", "assets/weapon icons/bows/" + type + "BowTier" + i + ".png", "assets/weapon icons/staff/" + type + "StaffTier" + i + ".png", "assets/weapon icons/swords/" + type + "SwordTier" + i + ".png")) {
                        assert isImagePathValid(s);
                    }

                    armourLeg.add(new ImageIcon("assets/weapon icons/amour/" + type + "ArmourTier" + i + ".png"));
                    bowLeg.add(new ImageIcon("assets/weapon icons/bows/" + type + "BowTier" + i + ".png"));
                    staffLeg.add(new ImageIcon("assets/weapon icons/staff/" + type + "StaffTier" + i + ".png"));
                    swordsLeg.add(new ImageIcon("assets/weapon icons/swords/" + type + "SwordTier" + i + ".png"));
                }
            }

            if (Objects.equals(type, "Mythical")) {
                assert isImagePathValid("assets/weapon icons/amour/" + type + "Armour" + "NoTier.png");
                assert isImagePathValid("assets/weapon icons/bows/" + type + "Bow" + "NoTier.png");
                assert isImagePathValid("assets/weapon icons/staff/" + type + "Staff" + "NoTier.png");
                assert isImagePathValid("assets/weapon icons/swords/" + type + "Sword" + "NoTier.png");

                pricesMyth.add(2650);
                pricesMythT.add(2650);
                armourMyth.add(new ImageIcon("assets/weapon icons/amour/" + type + "Armour" + "NoTier.png"));
                bowMyth.add(new ImageIcon("assets/weapon icons/bows/" + type + "Bow" + "NoTier.png"));
                staffMyth.add(new ImageIcon("assets/weapon icons/staff/" + type + "Staff" + "NoTier.png"));
                swordsMyth.add(new ImageIcon("assets/weapon icons/swords/" + type + "Sword" + "NoTier.png"));

                for (int i = 1; i < 11; ++i) {
                    pricesMyth.add(i * 2500);
                    pricesMythT.add(pricesMythT.get(i - 1) + i * 2500);

                    for (String s : Arrays.asList("assets/weapon icons/amour/" + type + "ArmourTier" + i + ".png", "assets/weapon icons/bows/" + type + "BowTier" + i + ".png", "assets/weapon icons/staff/" + type + "StaffTier" + i + ".png", "assets/weapon icons/swords/" + type + "SwordTier" + i + ".png")) {
                        assert isImagePathValid(s);
                    }

                    armourMyth.add(new ImageIcon("assets/weapon icons/amour/" + type + "ArmourTier" + i + ".png"));
                    bowMyth.add(new ImageIcon("assets/weapon icons/bows/" + type + "BowTier" + i + ".png"));
                    staffMyth.add(new ImageIcon("assets/weapon icons/staff/" + type + "StaffTier" + i + ".png"));
                    swordsMyth.add(new ImageIcon("assets/weapon icons/swords/" + type + "SwordTier" + i + ".png"));
                }
            }
        }
    }

    // Potion
    public Image getPotion(int pos) {
        return potions.get(pos).getImage();
    }

    public String getNamePotion(int pos) {
        return potionsName.get(pos);
    }

    public String getDescPotion(int pos) {
        int tier = Player.getInstance().getCurrPlayerTier();
        String prefix = "", suffix = "";
        if (pos <= 2) suffix = " " + tier * potionsProperties.get(pos) + " health";
        else if (pos <= 4) prefix = "";
        else prefix = "Deals " + -1 * tier * potionsProperties.get(pos) + " ";
        return prefix + potionDesciption.get(pos) + suffix;
    }

    public Integer getPricePotions(int pos) {
        int tier = Player.getInstance().getCurrPlayerTier();
        tier = ((pos != 3 && pos != 4)? tier : 1);
        return tier * potionsPrice.get(pos);
    }

    // common
    public Image getSwordCommonTier(int tier) {
        return swordsCommon.get(tier).getImage();
    }

    public Image getStaffCommonTier(int tier) {
        return staffCommon.get(tier).getImage();
    }

    public Image getBowCommonTier(int tier) {
        return bowCommon.get(tier).getImage();
    }

    public Image getArmourCommonTier(int tier) {
        return armourCommon.get(tier).getImage();
    }

    public Integer getPricesCommonTier(int tier) {
        return pricesCommon.get(tier);
    }

    public Integer getPricesCommonTierT(int tier) {
        return pricesCommonT.get(tier);
    }

    // rare
    public Image getSwordRareTier(int tier) {
        return swordsRare.get(tier).getImage();
    }

    public Image getStaffRareTier(int tier) {
        return staffRare.get(tier).getImage();
    }

    public Image getBowRareTier(int tier) {
        return bowRare.get(tier).getImage();
    }

    public Image getArmourRareTier(int tier) {
        return armourRare.get(tier).getImage();
    }

    public Integer getPricesRareTier(int tier) {
        return pricesRare.get(tier);
    }

    public Integer getPricesRareTierT(int tier) {
        return pricesRareT.get(tier);
    }

    // epic
    public Image getSwordEpicTier(int tier) {
        return swordsEpic.get(tier).getImage();
    }

    public Image getStaffEpicTier(int tier) {
        return staffEpic.get(tier).getImage();
    }

    public Image getBowEpicTier(int tier) {
        return bowEpic.get(tier).getImage();
    }

    public Image getArmourEpicTier(int tier) {
        return armourEpic.get(tier).getImage();
    }

    public Integer getPricesEpicTier(int tier) {
        return pricesEpic.get(tier);
    }

    public Integer getPricesEpicTierT(int tier) {
        return pricesEpicT.get(tier);
    }

    // legendary
    public Image getSwordLegendaryTier(int tier) {
        return swordsLeg.get(tier).getImage();
    }

    public Image getStaffLegendaryTier(int tier) {
        return staffLeg.get(tier).getImage();
    }

    public Image getBowLegendaryTier(int tier) {
        return bowLeg.get(tier).getImage();
    }

    public Image getArmourLegendaryTier(int tier) {
        return armourLeg.get(tier).getImage();
    }

    public Integer getPricesLegendaryTier(int tier) {
        return pricesLeg.get(tier);
    }

    public Integer getPricesLegendaryTierT(int tier) {
        return pricesLegT.get(tier);
    }

    // mythical
    public Image getSwordMythicalTier(int tier) {
        return swordsMyth.get(tier).getImage();
    }

    public Image getStaffMythicalTier(int tier) {
        return staffMyth.get(tier).getImage();
    }

    public Image getBowMythicalTier(int tier) {
        return bowMyth.get(tier).getImage();
    }

    public Image getArmourMythicalTier(int tier) {
        return armourMyth.get(tier).getImage();
    }

    public Integer getPricesMythicalTier(int tier) {
        return pricesMyth.get(tier);
    }

    public Integer getPricesMythicalTierT(int tier) {
        return pricesMythT.get(tier);
    }

    public Image randomItem(String item, int typeQ) {
        Random random = new Random();
        double p = random.nextDouble();

        String type;

        if (p <= 0.5) type = types.get(0);
        else if (p <= 0.75) type = types.get(1);
        else if (p <= 0.85) type = types.get(2);
        else if (p <= 0.92) type = types.get(3);
        else type = types.get(4);

        int tier = random.nextInt(11);
        if (typeQ == 0) {
            System.out.println(type + " " + item + " " + tier);
            tierweaponR.add(tier);
            nameweaponsR.add(type + " " + item);
        } else {
            System.out.println(type + " " + item + " " + tier);
            tierarmourR.add(tier);
            namearmourR.add(type + " " + item);
        }
        return getImagefromtype(type, item, tier);
    }

    public void GenerateBlackSmithWeapons() {
        weaponsR.clear();
        Random random = new Random();
        ArrayList<String> weapons = new ArrayList<>(Arrays.asList("Bow", "Sword", "Staff"));
        for (int i = 0; i < 4; ++i) {
            String item = weapons.get(random.nextInt(weapons.size()));
            weaponsR.add(randomItem(item, 0));
        }
    }

    public void GenerateBlackSmithArmour() {
        armourR.clear();
        for (int i = 0; i < 4; ++i) {
            armourR.add(randomItem("Armour",1));
        }
    }

    public Image getBSW(int pos) {
        return weaponsR.get(pos);
    }

    public Image getBSA(int pos) {
        return armourR.get(pos);
    }

    public String getBSWN(int pos) {
        return nameweaponsR.get(pos);
    }

    public String getBSAN(int pos) {
        return namearmourR.get(pos);
    }

    public Integer getBSWT(int pos) {
        return tierweaponR.get(pos);
    }

    public Integer getBSAT(int pos) {
        return tierarmourR.get(pos);
    }

    public String TierRoman(int tier) {
        String tierRoman;
        switch (tier) {
            case 1:
                tierRoman = "i";
                break;
            case 2:
                tierRoman = "ii";
                break;
            case 3:
                tierRoman = "iii";
                break;
            case 4:
                tierRoman = "iv";
                break;
            case 5:
                tierRoman = "v";
                break;
            case 6:
                tierRoman = "vi";
                break;
            case 7:
                tierRoman = "vii";
                break;
            case 8:
                tierRoman = "viii";
                break;
            case 9:
                tierRoman = "ix";
                break;
            case 10:
                tierRoman = "x";
                break;
            default:
                tierRoman = "Invalid tier";
        }
        return tierRoman;
    }

    public String getTypeFromName(String name) {
        if (name.toLowerCase().contains("common"))
            return "Common";
        if (name.toLowerCase().contains("rare"))
            return "Rare";
        if (name.toLowerCase().contains("epic"))
            return "Epic";
        if (name.toLowerCase().contains("legendary"))
            return "Legendary";
        if (name.toLowerCase().contains("mythical"))
            return "Mythical";
        return "invalid";
    }

    public Integer getPrice(String type, int tier){
        if (type.toLowerCase().contains("common"))
            return getPricesCommonTier(tier);
        if (type.toLowerCase().contains("rare"))
            return getPricesRareTier(tier);
        if (type.toLowerCase().contains("epic"))
            return getPricesEpicTier(tier);
        if (type.toLowerCase().contains("legendary"))
            return getPricesLegendaryTier(tier);
        if (type.toLowerCase().contains("mythical"))
            return getPricesMythicalTier(tier);
        return -1;
    }

    public Integer getPriceT(String type, int tier){
        if (type.toLowerCase().contains("common"))
            return getPricesCommonTierT(tier) + (getPricesCommonTierT(tier) / 2);
        if (type.toLowerCase().contains("rare"))
            return getPricesRareTierT(tier) + (getPricesRareTierT(tier) / 2);
        if (type.toLowerCase().contains("epic"))
            return getPricesEpicTierT(tier) + (getPricesEpicTierT(tier) / 2);
        if (type.toLowerCase().contains("legendary"))
            return getPricesLegendaryTierT(tier) + (getPricesLegendaryTierT(tier) / 2);
        if (type.toLowerCase().contains("mythical"))
            return getPricesMythicalTier(tier) + (getPricesMythicalTierT(tier) / 2);
        return -1;
    }

    public Image getImagefromtype(String type, String item, int tier) {
        if (Objects.equals(type, "Common")) {
            if (item.toLowerCase().contains("armour"))
                return getArmourCommonTier(tier);
            if (item.toLowerCase().contains("staff"))
                return getStaffCommonTier(tier);
            if (item.toLowerCase().contains("bow"))
                return getBowCommonTier(tier);
            return getSwordCommonTier(tier);
        }

        if (Objects.equals(type, "Rare")) {
            if (item.toLowerCase().contains("armour"))
                return getArmourRareTier(tier);
            if (item.toLowerCase().contains("staff"))
                return getStaffRareTier(tier);
            if (item.toLowerCase().contains("bow"))
                return getBowRareTier(tier);
            return getSwordRareTier(tier);
        }

        if (Objects.equals(type, "Epic")) {
            if (item.toLowerCase().contains("armour"))
                return getArmourEpicTier(tier);
            if (item.toLowerCase().contains("staff"))
                return getStaffEpicTier(tier);
            if (item.toLowerCase().contains("bow"))
                return getBowEpicTier(tier);
            return getSwordEpicTier(tier);
        }

        if (Objects.equals(type, "Legendary")) {
            if (item.toLowerCase().contains("armour"))
                return getArmourLegendaryTier(tier);
            if (item.toLowerCase().contains("staff"))
                return getStaffLegendaryTier(tier);
            if (item.toLowerCase().contains("bow"))
                return getBowLegendaryTier(tier);
            return getSwordLegendaryTier(tier);
        }

        if (Objects.equals(type, "Mythical")) {
            if (item.toLowerCase().contains("armour"))
                return getArmourMythicalTier(tier);
            if (item.toLowerCase().contains("staff"))
                return getStaffMythicalTier(tier);
            if (item.toLowerCase().contains("bow"))
                return getBowMythicalTier(tier);
            return getSwordMythicalTier(tier);
        }

        assert false;
        return new ImageIcon("assets/Character/EmptyIcon.png").getImage();
    }

    public static synchronized Items getInstance() {
        if (single_instance == null)
            single_instance = new Items();

        return single_instance;
    }
}
