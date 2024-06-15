package headers.Utility;

import javax.swing.*;
import java.awt.*;
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

        skillDescription.add("Deals 75 DMG no cooldown");
        skillDescription.add("Deals 150 DMG cooldown 3 turns");
        skillDescription.add("Deals 250 DMG to all enemy's CD 5 turns");
        skillDescription.add("Deals 400 DMG to all enemy's CD:10 Turns");
        skillDescription.add("Gains 30 AP (passive)");
        skillDescription.add("Gains 10 based ARM and MR (passive)");
        skillDescription.add("Gains 50 based HP (passive)");
        skillDescription.add("Gains 5% critical rate (passive)");
        skillDescription.add("Reduce all damage taken by 5% (passive)");
        skillDescription.add("Gain 30 AD and 30% critical damage (passive)");
        skillDescription.add("Heals for 1% max HP each combat turn (passive).");
        skillDescription.add("A precise strike that deals 300% AD and can critical cooldown 5 turns");
        skillDescription.add("Devastating blow that dela 150% AD to all names, can critical, cooldown 4 turns");
        skillDescription.add("Heals for 50 HP cooldown 4 turns");
    }

    public Image getSkill(int pos) {
        return skills.get(pos).getImage();
    }

    public String getSkillName(int pos) {
        return skillName.get(pos);
    }

    public String getSkillDescription(int pos) {
        return skillDescription.get(pos);
    }

    public static synchronized Skills getInstance() {
        if (single_instance == null) single_instance = new Skills();
        return single_instance;
    }
}
