package headers.Utility;

import javax.swing.*;
import java.util.ArrayList;

public class Skills {
    private static Skills single_instance = null;
    private ArrayList<ImageIcon> skills = new ArrayList<>();
    private ArrayList<String> skillName = new ArrayList<>();
    private ArrayList<String> skillDescription = new ArrayList<>();

    public Skills() {
        initAll();
    }

    private void initAll() {
        skills.add(new ImageIcon("assets/Skill Shop/BeginnerMagic.png"));
        skills.add(new ImageIcon("assets/Skill Shop/IntermediarMagic.png"));
        skills.add(new ImageIcon("assets/Skill Shop/AdvancedMagic.png"));
        skills.add(new ImageIcon("assets/Skill Shop/ExplosionMagic.png"));
        skills.add(new ImageIcon("assets/Skill Shop/ScholarShip.png"));
        skills.add(new ImageIcon("assets/Skill Shop/ArmorWork.png"));
        skills.add(new ImageIcon("assets/Skill Shop/HealthyPhysique.png"));
        skills.add(new ImageIcon("assets/Skill Shop/DeadlyPrecison.png"));
        skills.add(new ImageIcon("assets/Skill Shop/StoneSkin.png"));
        skills.add(new ImageIcon("assets/Skill Shop/PureMuscles.png"));
        skills.add(new ImageIcon("assets/Skill Shop/Revitalize.png"));
        skills.add(new ImageIcon("assets/Skill Shop/DeadlyPrecison.png"));
        skills.add(new ImageIcon("assets/Skill Shop/AfterShock.png"));
        skills.add(new ImageIcon("assets/Skill Shop/HealingMagic.png"));

        skillName.add("Beginner Magic");
        skillName.add("Intermediary Magic");
        skillName.add("Advanced Magic");
        skillName.add("Explosion Magic");
        skillName.add("Scholar Ship");
        skillName.add("Armor Work");
        skillName.add("Healthy Physique");
        skillName.add("Deadly Precision");
        skillName.add("Stone Skin");
        skillName.add("Pure Muscles");
        skillName.add("Revitalize");
        skillName.add("Deadly Precision");
        skillName.add("After Shock");
        skillName.add("HealingMagic");

        skillDescription.add("Deals base 75 DMG no CoolDown");
        skillDescription.add("Deals base`");
    }

    public static Skills getInstance() {
        if (single_instance == null) single_instance = new Skills();
        return single_instance;
    }
}
