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

    private Player player; // Declare Player instance for testing

    @Before
    // Method to set up Player instance before each test method
    public void setUp() {
        // Create a Trait instance with a name and an ImageIcon
        Trait trait = new Trait("some trait", new ImageIcon("assets/Traits/TheStrong.png"));

        // Create a Status instance with a name and an initial value
        Status status = new Status("Strength", 1);

        // Create an ArrayList to hold Status instances and add the created status
        ArrayList<Status> statusList = new ArrayList<>();
        statusList.add(status);

        // Create a Player instance with a name, trait, and status list
        player = new Player("Player", trait, statusList);
    }

    @Test
    // Test method to check the getName() method of Player
    public void testGetName() {
        assertEquals("Player", player.getName());
    }

    @Test
    // Test method to check the getTrait() method of Player
    public void testGetTrait() {
        assertEquals("some trait", player.getTrait().getName());
    }

    @Test
    // Test method to check the getCurrPlayerTier() method of Player
    public void testGetCurrPlayerTier() {
        assertEquals(1, player.getCurrPlayerTier());
    }

    @Test
    // Test method to check the getGold() method of Player
    public void testGetGold() {
        assertEquals(500, player.getGold());
    }

    @Test
    // Test method to check the subtractFromGold() method of Player
    public void testSubtractFromGold() {
        player.subtractFromGold(500);
        assertEquals(0, player.getGold());
    }

    @Test()
    // Test method to check the subtractFromGold() method when gold is insufficient
    public void testSubtractFromGoldInsufficient() {
        player.subtractFromGold(11000);
        assertEquals(0, player.getGold());
    }

    @Test
    // Test method to check the addSupplies() method of Player
    public void testAddSupplies() {
        player.addSupplies(-50);
        assertEquals(50, player.getSupplies());
    }

    @Test
    // Test method to check the addItem1() method of Player
    public void testAddItem1() {
        // Create an Image instance for testing
        Image item = new ImageIcon("assets/Character/TestItem.png").getImage();
        player.addItem1(item, "Test Item", 100, 1);
        assertEquals(9, player.getInventorySpace());
        assertEquals("Test Item", player.getInventory1Str(0));
    }

    @Test
    // Test method to check the remItem1() method of Player
    public void testRemItem1() {
        // Create an Image instance for testing
        Image item = new ImageIcon("assets/Character/TestItem.png").getImage();
        player.addItem1(item, "Test Item", 100, 1);
        player.remItem1(0);
        assertEquals(10, player.getInventorySpace());
        assertEquals("Empty", player.getInventory1Str(0));
    }

    @Test
    // Test method to check the addItem2() method of Player
    public void testAddItem2() {
        // Create an Image instance for testing
        Image item = new ImageIcon("assets/Character/TestPotion.png").getImage();
        player.addItem2(item, "Test Potion", 50);
        assertEquals(9, player.getPotionsSpace());
        assertEquals("Test Potion", player.getInventory2Str(0));
    }

    @Test
    // Test method to check the remItem2() method of Player
    public void testRemItem2() {
        // Create an Image instance for testing
        Image item = new ImageIcon("assets/Character/TestPotion.png").getImage();
        player.addItem2(item, "Test Potion", 50);
        player.remItem2(0);
        assertEquals(10, player.getPotionsSpace());
        assertEquals("Empty", player.getInventory2Str(0));
    }

    @Test
    // Test method to check the upgradeStatus() method of Player
    public void testUpgradeStatus() {
        player.upgradeStatus(0);
        assertEquals((Integer) 2, player.getStatus(0));
    }

    @Test
    // Test method to check the getInstance() method of Player
    public void testGetInstance() {
        // Create a new Player instance using getInstance() method
        Player instance = Player.getInstance("Player2", new Trait("trait2", new ImageIcon("assets/Traits/TheStrong.png")), new ArrayList<>());

        // Assert that the instance is not null and has the expected name
        assertNotNull(instance);
        assertEquals("Player2", instance.getName());
    }
}