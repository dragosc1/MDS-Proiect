import headers.Player;
import headers.Status;
import headers.Trait;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerTest {
    private Player player;

    @Before
    public void setUp() {
        Trait trait = new Trait("some trait", new ImageIcon("assets/Traits/TheStrong.png"));
        Status status = new Status("Strength", 1);
        ArrayList<Status> statusList = new ArrayList<>();
        statusList.add(status);
        player = new Player("Player", trait, statusList);
    }

    @Test
    public void testGetName() {
        assertEquals("Player", player.getName());
    }

    @Test
    public void testGetTrait() {
        assertEquals("some trait", player.getTrait().getName());
    }

    @Test
    public void testGetCurrPlayerTier() {
        assertEquals(1, player.getCurrPlayerTier());
    }

    @Test
    public void testGetGold() {
        assertEquals(500, player.getGold());
    }

    @Test
    public void testSubtractFromGold() {
        player.subtractFromGold(500);
        assertEquals(0, player.getGold());
    }

    @Test()
    public void testSubtractFromGoldInsufficient() {
        player.subtractFromGold(11000);
        assertEquals(0, player.getGold());
    }

    @Test
    public void testAddSupplies() {
        player.addSupplies(-50);
        assertEquals(50, player.getSupplies());
    }

    @Test
    public void testAddItem1() {
        Image item = new ImageIcon("assets/Character/TestItem.png").getImage();
        player.addItem1(item, "Test Item", 100, 1);
        assertEquals(9, player.getInventorySpace());
        assertEquals("Test Item", player.getInventory1Str(0));
    }

    @Test
    public void testRemItem1() {
        Image item = new ImageIcon("assets/Character/TestItem.png").getImage();
        player.addItem1(item, "Test Item", 100, 1);
        player.remItem1(0);
        assertEquals(10, player.getInventorySpace());
        assertEquals("Empty", player.getInventory1Str(0));
    }

    @Test
    public void testAddItem2() {
        Image item = new ImageIcon("assets/Character/TestPotion.png").getImage();
        player.addItem2(item, "Test Potion", 50);
        assertEquals(9, player.getPotionsSpace());
        assertEquals("Test Potion", player.getInventory2Str(0));
    }

    @Test
    public void testRemItem2() {
        Image item = new ImageIcon("assets/Character/TestPotion.png").getImage();
        player.addItem2(item, "Test Potion", 50);
        player.remItem2(0);
        assertEquals(10, player.getPotionsSpace());
        assertEquals("Empty", player.getInventory2Str(0));
    }

    @Test
    public void testUpgradeStatus() {
        player.upgradeStatus(0);
        assertEquals((Integer) 2, player.getStatus(0));
    }

    @Test
    public void testGetInstance() {
        Player instance = Player.getInstance("Player2", new Trait("trait2", new ImageIcon("assets/Traits/TheStrong.png")), new ArrayList<>());
        assertNotNull(instance);
        assertEquals("Player2", instance.getName());
    }
}