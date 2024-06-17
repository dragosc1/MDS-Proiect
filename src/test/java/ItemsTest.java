import headers.Utility.Items;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.awt.*;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class ItemsTest {

    private Items items; // Declare Items instance for testing

    @Before
    // Method to set up Items instance before each test method
    public void setup() {
        items = Items.getInstance(); // Get instance of Items singleton
    }

    @Test
    // Test method to check potion properties retrieval
    public void testPotionProperties() {
        assertEquals(Integer.valueOf(50), items.getPotionProperties(0)); // Test potion property at index 0
        assertEquals(Integer.valueOf(100), items.getPotionProperties(1)); // Test potion property at index 1
    }

    @Test
    // Test method to check common sword retrieval
    public void testCommonSwordRetrieval() {
        Image swordImage = items.getSwordCommonTier(1); // Retrieve common sword image at tier 1
        assertNotNull(swordImage); // Assert that the image is not null
    }

    @Test
    // Test method to check rare staff retrieval
    public void testRareStaffRetrieval() {
        Image staffImage = items.getStaffRareTier(2); // Retrieve rare staff image at tier 2
        assertNotNull(staffImage); // Assert that the image is not null
    }

    @Test
    // Test method to check epic bow retrieval
    public void testEpicBowRetrieval() {
        Image bowImage = items.getBowEpicTier(3); // Retrieve epic bow image at tier 3
        assertNotNull(bowImage); // Assert that the image is not null
    }

    @Test
    // Test method to check legendary armour retrieval
    public void testLegendaryArmourRetrieval() {
        Image armourImage = items.getArmourLegendaryTier(4); // Retrieve legendary armour image at tier 4
        assertNotNull(armourImage); // Assert that the image is not null
    }

    @Test
    // Test method to check mythical item price retrieval
    public void testMythicalItemPrice() {
        Integer price = items.getPricesMythicalTier(5); // Retrieve price of mythical item at tier 5
        assertEquals(Integer.valueOf(12500), price); // Assert that the retrieved price matches the expected value
    }

    @Test
    // Test method to check random item generation
    public void testRandomItemGeneration() {
        Image randomWeapon = items.randomItem("Sword", 0); // Generate a random sword image
        assertNotNull(randomWeapon); // Assert that the generated image is not null
    }

    @Test
    // Test method to check getting image from type and tier
    public void testGetImageFromX() {
        Image image = items.getImageFromX("Bow", "Common", 1); // Retrieve bow image for common tier 1
        assertNotNull(image); // Assert that the image is not null
    }

    @Test
    // Test method to check blacksmith weapons generation
    public void testBlacksmithWeaponsGeneration() {
        items.GenerateBlackSmithWeapons(); // Generate blacksmith weapons
        for (int i = 0; i < 4; i++) {
            Image weapon = items.getBSW(i); // Retrieve blacksmith weapon at index i
            assertNotNull(weapon); // Assert that the weapon image is not null
        }
    }

    @Test
    // Test method to check blacksmith armour generation
    public void testBlacksmithArmourGeneration() {
        items.GenerateBlackSmithArmour(); // Generate blacksmith armour
        for (int i = 0; i < 4; i++) {
            Image armour = items.getBSA(i); // Retrieve blacksmith armour at index i
            assertNotNull(armour); // Assert that the armour image is not null
        }
    }

    @Test
    // Test method to check potion name retrieval
    public void testPotionName() {
        String name = items.getNamePotion(0); // Retrieve name of potion at index 0
        assertEquals("Small health", name); // Assert that the retrieved name matches the expected value
    }
}