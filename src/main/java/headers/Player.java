package headers;


import headers.Utility.Items;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static java.lang.Math.max;

public class Player {
    private static Player single_instance = null;
    private String name;
    private Trait trait;
    private ArrayList<Status> statuses;
    private int Gold, Supply;
    private int InventorySpace;
    private ArrayList <Image> inventory1 = new ArrayList<>();
    private ArrayList <String> nameinventory1 = new ArrayList<>();

    public Player(String name, Trait trait, ArrayList<Status> statuses) {
        this.name = name;
        this.trait = trait;
        this.statuses = statuses;
        this.Gold = 500;
        this.Supply = 100;
        this.InventorySpace = 10;
    }

    public String getName() {
        return name;
    }

    public Trait getTrait() {
        return trait;
    }

    public int getInventorySpace() {return InventorySpace;}
    public int getGold() {return Gold;}
    public int getSupplies() {return Supply;}

    public void subtractFromGold(int cap) {
        assert Gold >= cap;
        Gold -= cap;
    }

    public void addSupplies(int cap) {
        Supply = max(100, Supply + cap);
    }

    public void addItem1(Image item, String name) {
        assert InventorySpace != 0;
        inventory1.add(item);
        nameinventory1.add(name);
        --InventorySpace;
    }

    public Image getInventory1Img(int pos) {
        if (pos >= inventory1.size()) return new ImageIcon("assets/Character/EmptyIcon.png").getImage();
        return inventory1.get(pos);
    }

    public String getInventory1Str(int pos) {
        if (pos >= nameinventory1.size()) return "Empty";
        return nameinventory1.get(pos);
    }

    public ArrayList<Status> getStatuses() {
        return statuses;
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

