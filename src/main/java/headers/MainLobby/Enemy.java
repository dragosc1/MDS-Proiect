package headers.MainLobby;

import headers.Player;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Enemy {
    private ImageIcon icon;       // Icon representing the enemy
    private int Hp, Dmg, Arm, Mr; // Enemy stats: Health points, Damage, Armor, Magic resistance
    private double Crit;          // Critical hit chance
    private int type;             // Type of the enemy
    private String typeEnemy;     // String representation of enemy type (e.g., "Monster", "Thief")
    private Random rnd;           // Random object for generating random values
    private int maxHp;            // Maximum health points
    private double inc;           // Incremental damage factor
    private int potionRound1, potionRound2; // Potion effect counters

    // Constructor to initialize an enemy of a given type
    public Enemy(int type) {
        inc = 1;
        this.type = type;
        this.potionRound1 = this.potionRound2 = 0;
        rnd = new Random();
        if (rnd.nextBoolean()) { // 50% chance to create a Monster type enemy
            double level = Player.getInstance().getDungeon(type);
            this.typeEnemy = "Monster";
            icon = new ImageIcon("assets/Inamici Dungeon/Dungeon I/Monster.png");
            //Setting statuses based on level
            Hp = (int) Math.ceil((double) 200 * min(5, 1 + level * 0.1));
            Dmg = (int) Math.ceil((double) 10 * min(5, 1 + level * 0.1));
            Crit = 0.3 * max(5, 1 + level * 0.1);
            maxHp = Hp;
        } else { // 50% chance to create a Thief type enemy
            double level = Player.getInstance().getDungeon(type);
            this.typeEnemy = "Thief";
            icon = new ImageIcon("assets/Inamici Dungeon/Dungeon I/thief.png");
            //Setting statuses based on level
            Hp = (int) Math.ceil((double) 150 * min(5, 1 + level * 0.1));
            Dmg = (int) Math.ceil((double) 10 * min(5, 1 + level * 0.1));
            Arm = (int) Math.ceil((double) 20 * min(5, 1 + level * 0.1));
            Mr = (int) Math.ceil((double) 20 * min(5, 1 + level * 0.1));
            Crit = 0.3 * max(5, 1 + level * 0.1);
            maxHp = Hp;
        }
    }

    // Set the incremental damage factor
    public void setInc(double inc) {
        this.inc = inc;
    }

    // Set the potion effect counter for the first potion type
    void setPotionRound1() {
        this.potionRound1 = 5;
    }

    // Set the potion effect counter for the second potion type
    void setPotionRound2() {
        this.potionRound2 = 4;
    }

    // Method to handle the end of a round
    public void EndRound() {
        inc = 0;
        if (potionRound1 > 0) --potionRound1;
        if (potionRound2 > 0) {
            applyDealtDamage(20, false);
            --potionRound2;
        }
    }

    // Alternative constructor to initialize an enemy with specific icon, type, and typeEnemy
    public Enemy(ImageIcon icon, String typeEnemy, int type) {
        this.icon = icon;
        this.typeEnemy = typeEnemy;
        this.type = type;
    }

    // Apply damage to the enemy, considering critical hits and resistance
    public synchronized void applyDealtDamage(int dmg, Boolean doCrit) {
        dmg += (int) Math.ceil((double) dmg * inc);
        if (doCrit) {
            boolean crit = rnd.nextDouble() < 0.05;
            if (crit) dmg = (int) Math.ceil((double) dmg * Player.getInstance().getCrit());
        }

        if (typeEnemy.equals("Thief")) {
            double resist = Mr;
            dmg = (int) Math.ceil((dmg * (1.0 - resist / (resist + 100))));
        }

        Hp = max(0, Hp - dmg);
    }

    // Apply true damage to the enemy, ignoring resistances
    public synchronized void applyDealtDamageTrue(int dmg, Boolean doCrit) {
        dmg += (int) Math.ceil((double) dmg * inc);
        if (doCrit) {
            boolean crit = rnd.nextDouble() < 0.05;
            if (crit) dmg = (int) Math.ceil((double) dmg * Player.getInstance().getCrit());
        }

        Hp = max(0, Hp - dmg);
    }

    // Calculate and return the damage dealt by the enemy
    public synchronized int dealDamage() {
        if (currHealth() == 0)
            return 0;
        boolean crit = rnd.nextDouble() < 0.05;
        int dmg = Dmg;
        if (crit) dmg = (int) Math.ceil((double) dmg * 3);
        if (potionRound1 > 0) return 0;
        return dmg;
    }

    // Calculate and return the X position of the health bar
    public synchronized int getX() {
        int startX = 200;
        int endX = 280;
        int maxBarLength = endX - startX;

        // Calculate the proportional position based on current HP
        int proportionalPosition = (int) (((double) Hp / maxHp) * maxBarLength);
        return proportionalPosition;
    }

    // Return the current health points of the enemy
    public synchronized int currHealth() {
        return Hp;
    }

    // Return the type of the enemy
    public synchronized String getTypeEnemy() {
        return this.typeEnemy;
    }

    // Return the icon of the enemy
    public ImageIcon getIcon() {
        return icon;
    }
}
