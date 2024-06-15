package headers;

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
    private final int currTier;
    private int InventorySpace, PotionsSpace, SInventorySpace;
    private ArrayList<Image> inventory1 = new ArrayList<>(), inventory2 = new ArrayList<>(), inventory3 = new ArrayList<>();
    private ArrayList<String> nameinventory1 = new ArrayList<>(), nameinventory2 = new ArrayList<>();
    private ArrayList<Integer> price1 = new ArrayList<>(), price2 = new ArrayList<>();
    private ArrayList<Integer> dungeons = new ArrayList<>();
    private ArrayList<Integer> invtiers = new ArrayList<>();

    public Player(String name, Trait trait, ArrayList<Status> statuses) {
        this.name = name;
        this.trait = trait;
        this.statuses = statuses;
        this.Gold = 10000;
        this.Supply = 100;
        this.currTier = 1;
        this.SInventorySpace = 3;
        this.InventorySpace = this.PotionsSpace = 10;
        for (int i = 0; i < 5; ++i) {
            dungeons.add(0);
        }
    }

    public void updateSInventorySpace() {
        this.SInventorySpace += 2;
    }

    public int getSInventorySpace() {
        return this.SInventorySpace;
    }

    public int getCurrPlayerTier() {
        return this.currTier;
    }

    public String getName() {
        return name;
    }

    public Trait getTrait() {
        return trait;
    }

    public int getDungeon(int pos) {
        return dungeons.get(pos);
    }

    public int getInventorySpace() {
        return InventorySpace;
    }

    public int getPotionsSpace() {
        return PotionsSpace;
    }

    public int getGold() {
        return Gold;
    }

    public int getSupplies() {
        return Supply;
    }

    public void subtractFromGold(int cap) {
        assert Gold >= cap;
        Gold -= cap;
    }

    public void addSupplies(int cap) {
        Supply = max(100, Supply + cap);
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

    public void upgradeItem1(int pos, Image item, String name, Integer price, int tier) {
        assert pos < nameinventory1.size();
        inventory1.set(pos, item);
        nameinventory1.set(pos, name);
        price1.set(pos, price);
        invtiers.add(pos, tier);
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