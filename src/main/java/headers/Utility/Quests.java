package headers.Utility;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.*;

public class Quests implements Serializable {
    private static Quests single_instance = null;

    private ArrayList <String> Quests = new ArrayList<>();
    private ArrayList <Integer> Progress = new ArrayList<>();
    private final ArrayList <String> TierUp = new ArrayList<>();
    private ArrayList <Integer> Type = new ArrayList<>();
    private ArrayList <Integer> Prize = new ArrayList<>();
    private Integer progressT, limit;

    Random random;

    public Quests(){
        random = new Random();
        limit = 3;
        GenerateRandomQuests();
        GenerateRandomTier();
    }

    public Quests(ArrayList <String> arr) {
        random = new Random();
        Quests = arr;
    }

    public static void setInstance(Quests quests) {
        single_instance = quests;
    }

    public void GenerateRandomQuests() {
        Quests.clear(); // Clear previous quests if any

        for (int i = 0; i < 2; i++) {
            int questType = random.nextInt(2); // Generate a random number between 0 and 2

            switch (questType) {
                case 0:
                    // Generate kill enemies quest
                    String enemyType = random.nextBoolean() ? "Monster" : "Thief";
                    int enemyCount = 4 + random.nextInt(7); // Random number between 4 and 10 (inclusive)
                    Quests.add("Kill " + enemyCount + " " + enemyType + "s");
                    Progress.add(0);
                    Prize.add((25 + random.nextInt(26)) * enemyCount);
                    break;
                case 1:
                    // Generate visit dungeon tiles quest
                    int tileCount = 5 + random.nextInt(36); // Random number between 5 and 40 (inclusive)
                    Quests.add("Visit " + tileCount + " dungeon tiles");
                    Progress.add(0);
                    Prize.add((10 + random.nextInt(6)) * tileCount);
                    break;
            }
        }
    }


    public void setRandomQuest(int pos) {
        int questType = random.nextInt(2); // Generate a random number between 0 and 2

        switch (questType) {
            case 0:
                // Generate kill enemies quest
                String enemyType = random.nextBoolean() ? "Monster" : "Thief";
                int enemyCount = 4 + random.nextInt(7); // Random number between 4 and 10 (inclusive)
                Quests.set(pos, "Kill " + enemyCount + " " + enemyType + "s");
                Progress.set(pos, 0);
                Prize.set(pos, (25 + random.nextInt(26)) * enemyCount);
                break;
            case 1:
                // Generate visit dungeon tiles quest
                int tileCount = 5 + random.nextInt(36); // Random number between 5 and 40 (inclusive)
                Quests.set(pos, "Visit " + tileCount + " dungeon tiles");
                Progress.set(pos, 0);
                Prize.set(pos, (10 + random.nextInt(6)) * tileCount);
                break;
        }
    }

    public void GenerateRandomTier() {
        progressT = 0;
        TierUp.add("Reach level 25 in the first dungeon");
        TierUp.add("Reach level 25 in the second dungeon");
        TierUp.add("Reach level 25 in the third dungeon");
        TierUp.add("Reach level 25 in the fourth dungeon");
        TierUp.add("Reach level 25 in the fifth dungeon");
        TierUp.add("Reach level 50 in the first dungeon");
        TierUp.add("Reach level 50 in the second dungeon");
        TierUp.add("Reach level 50 in the third dungeon");
        TierUp.add("Reach level 50 in the fourth dungeon");
        TierUp.add("Maximum Tier Reached");
        Type.add(0);
        Type.add(1);
        Type.add(2);
        Type.add(3);
        Type.add(4);
        Type.add(0);
        Type.add(1);
        Type.add(2);
        Type.add(3);
        Type.add(5);
    }

    public synchronized void updateTileQuest() {
        int idx = 0;
        for (String x : Quests) {
            if (x.charAt(0) == 'V') {
                int curr = Progress.get(idx);
                Progress.set(idx, curr + 1);
            }
            ++idx;
        }
    }

    public synchronized void updateKillQuest(String Type) {
        int idx = 0;
        for (String x : Quests) {
            if (x.charAt(0) == 'K' && x.toLowerCase().contains(Type.toLowerCase())) {
                int curr = Progress.get(idx);
                Progress.set(idx, curr + 1);
            }
            ++idx;
        }
    }

    public Integer getWhatType(int pos) {
        return Type.get(pos);
    }

    public void progressLimit() {
        ++limit;
    }

    public int getLimit() {
        return limit;
    }

    public void progressTier() {
        ++progressT;
    }

    public String getQuest(int pos) {
        return Quests.get(pos);
    }

    public Integer getProgress(int pos) {
        return Progress.get(pos);
    }

    public Integer getPrize(int pos) {
        return Prize.get(pos);
    }

    public String getTierUp() {
        return TierUp.get(progressT);
    }

    public void ModifyProgress(int pos) {
        Progress.set(pos, Progress.get(pos) + 1);
    }

    public static synchronized Quests getInstance() {
        if (single_instance == null) {
            single_instance = new Quests();
        }

        return single_instance;
    }

}
