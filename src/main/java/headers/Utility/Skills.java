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

        skillName.add("Beginner Magic"); // 0
        skillName.add("Intermediary Magic"); // 1
        skillName.add("Advanced Magic"); // 2
        skillName.add("Explosion Magic"); // 3
        skillName.add("Scholar Ship"); // 4
        skillName.add("Armor Work"); // 5
        skillName.add("Healthy Physique"); // 6
        skillName.add("Deadly Precision"); // 7
        skillName.add("Stone Skin"); // 8
        skillName.add("Pure Muscles"); // 9
        skillName.add("Revitalize"); // 10
        skillName.add("Deadly Precision"); // 11
        skillName.add("After Shock"); // 12
        skillName.add("HealingMagic"); // 13

        skillDescription.add("Deals 75 Dmg no cooldown"); // 0
        skillDescription.add("Deals 150 Dmg cooldown 3 turns"); // 1
        skillDescription.add("Deals 250 Dmg to all enemy's cooldown 5 turns"); // 2
        skillDescription.add("Deals 400 Dmg to all enemy's cooldown 10 Turns"); // 3
        skillDescription.add("Gains 30 Ap (passive)"); // 4
        skillDescription.add("Gains 10 based Arm and Mr (passive)"); // 5
        skillDescription.add("Gains 50 based Hp (passive)"); // 6
        skillDescription.add("Gains 5% critical rate (passive)"); // 7
        skillDescription.add("Reduce all damage taken by 5% (passive)"); // 8
        skillDescription.add("Gain 30 Ad and 30% critical damage (passive)"); // 9
        skillDescription.add("Heals for 1% max Hp each combat turn (passive)."); // 10
        skillDescription.add("A precise strike that deals 300% Ad and can critical cooldown 5 turns"); // 11
        skillDescription.add("Devastating blow that deals 150% Ad to all, can critical, cooldown 4 turns"); // 12
        skillDescription.add("Heals for 50 Hp cooldown 4 turns"); // 13
    }

    public int index(String name) {
        return skillName.indexOf(name);
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
