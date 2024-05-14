package headers.Utility;

import headers.ImageInfo;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Items {
    private static Items single_instance = null;
    private ArrayList<ImageIcon> swordsCommon, staffCommon, bowCommon, armourCommon;
    private static ArrayList<String> types = new ArrayList<>(Arrays.asList("Epic", "Legendary", "Mythical", "Rare", "Common"));
    private Items() {
        initCommon();
    }

    private void initCommon() {
        swordsCommon = new ArrayList<>();
        staffCommon = new ArrayList<>();
        bowCommon = new ArrayList<>();
        armourCommon = new ArrayList<>();

        for (String type : types) {
            if (Objects.equals(type, "Common")) {
                armourCommon.add(new ImageIcon("assets/weapon icons/amour/" + type + "Armour" + "NoTier.png"));
                bowCommon.add(new ImageIcon("assets/weapon icons/bows/" + type + "Bow" + "NoTier.png"));
                staffCommon.add(new ImageIcon("assets/weapon icons/staff/" + type + "Staff" + "NoTier.png"));
                swordsCommon.add(new ImageIcon("assets/weapon icons/swords/Sword" + type + "NoTier.png"));
                for (int i = 1; i < 11; ++i) {
                    armourCommon.add(new ImageIcon("assets/weapon icons/amour/" + type + "ArmourTier" + i + ".png"));
                    bowCommon.add(new ImageIcon("assets/weapon icons/bows/" + type + "BowTier" + i + ".png"));
                    staffCommon.add(new ImageIcon("assets/weapon icons/staff/" + type + "StaffTier" + i + ".png"));
                    swordsCommon.add(new ImageIcon("assets/weapon icons/swords/Sword" + type + "Tier" + i + ".png"));
                }
            }
        }
    }

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

    public static synchronized Items getInstance() {
        if (single_instance == null)
            single_instance = new Items();

        return single_instance;
    }
}
