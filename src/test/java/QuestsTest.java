import headers.Utility.Quests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class QuestsTest {

    private Quests quests; // Declare Quests instance for testing

    @BeforeEach
    // Method to set up Quests instance before each test method
    public void setUp() {
        quests = new Quests(); // Initialize a new Quests instance
    }

    @Test
    // Test method to check if the Singleton pattern is correctly implemented
    public void testSingletonInstance() {
        Quests instance1 = Quests.getInstance(); // Get first instance of Quests
        Quests instance2 = Quests.getInstance(); // Get second instance of Quests

        assertSame(instance1, instance2, "Instances should be the same"); // Ensure both instances are the same
    }

    @Test
    // Test method to validate setting and retrieving a random quest
    public void testSetRandomQuest() {
        quests.GenerateRandomQuests(); // Generate random quests
        quests.setRandomQuest(0); // Set a specific random quest

        String quest = quests.getQuest(0); // Retrieve the quest at index 0
        assertTrue(quest.startsWith("Kill") || quest.startsWith("Visit"), "Quest should start with 'Kill' or 'Visit'");
        // Assert that the quest description starts with "Kill" or "Visit"
    }

    @Test
    // Test method to verify the progress limit functionality
    public void testProgressLimit() {
        int initialLimit = quests.getLimit(); // Get initial progress limit
        quests.progressLimit(); // Increment the progress limit
        assertEquals(initialLimit + 1, quests.getLimit(), "Limit should be incremented");
        // Assert that the limit has been incremented by 1
    }

    @Test
    // Test method to validate modifying progress of a quest
    public void testModifyProgress() {
        quests.GenerateRandomQuests(); // Generate random quests
        quests.ModifyProgress(0); // Modify progress for the quest at index 0
        assertEquals(1, quests.getProgress(0), "Progress should be modified");
        // Assert that the progress of the quest at index 0 is now 1
    }

    @Test
    // Test method to check Singleton instance behavior after resetting the instance
    public void testSingletonInstanceAfterSetInstance() {
        Quests.setInstance(null); // Set the Singleton instance to null
        Quests instance1 = Quests.getInstance(); // Get first instance after reset
        Quests instance2 = Quests.getInstance(); // Get second instance after reset

        assertSame(instance1, instance2, "Instances should be the same after setting instance to null");
        // Assert that both instances retrieved after reset are the same
    }

    @Test
    // Test method to verify the behavior of getInstance() method
    public void testGetInstance() {
        Quests.setInstance(null); // Ensure instance is reset to null
        Quests instance1 = Quests.getInstance(); // First call to getInstance()
        assertNotNull(instance1, "Instance should not be null after first getInstance() call");
        // Assert that the instance retrieved is not null

        Quests instance2 = Quests.getInstance(); // Second call to getInstance()
        assertSame(instance1, instance2, "Subsequent getInstance() calls should return the same instance");
        // Assert that subsequent calls return the same instance as the first call
    }
}