package headers.MainLobby;

import headers.Player;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Enemy {
    private ImageIcon icon;
    private int Hp, Dmg, Arm, Mr;
    private double Crit;
    private int type;
    private String typeEnemy;
    private Random rnd;
    private int maxHp;
    private double inc;
    private int potionRound1, potionRound2;

    public Enemy(int type) {
        inc = 1;
        this.type = type;
        this.potionRound1 =  this.potionRound2 = 0;
        rnd = new Random();
        if (rnd.nextBoolean()) {
            double level = Player.getInstance().getDungeon(type);
            this.typeEnemy = "Monster";
            icon = new ImageIcon("assets/Inamici Dungeon/Dungeon I/Monster.png");
            Hp = (int)Math.ceil((double)200 * min(5, 1 + level * 0.1));
            Dmg = (int)Math.ceil((double)10 * min(5, 1 + level * 0.1));
            Crit = 0.3 * max(5, 1 + level * 0.1);
            maxHp = Hp;
        } else {
            double level = Player.getInstance().getDungeon(type);
            this.typeEnemy = "Thief";
            icon = new ImageIcon("assets/Inamici Dungeon/Dungeon I/thief.png");
            Hp = (int)Math.ceil((double)150 * min(5, 1 + level * 0.1));
            Dmg = (int)Math.ceil((double)10 * min(5, 1 + level * 0.1));
            Arm = (int)Math.ceil((double)20 * min(5, 1 + level * 0.1));
            Mr = (int)Math.ceil((double)20 * min(5, 1 + level * 0.1));
            Crit = 0.3 * max(5, 1 + level * 0.1);
            maxHp = Hp;
        }
    }

    public void setInc(double inc) {
        this.inc = inc;
    }

    void setPotionRound1() {
        this.potionRound1 = 5;
    }

    void setPotionRound2() {
        this.potionRound2 = 4;
    }

    public void EndRound() {
        inc = 0;
        if (potionRound1 > 0) --potionRound1;
        if (potionRound2 > 0) {
            applyDealtDamage(20, false);
            --potionRound2;
        }
    }

    public Enemy(ImageIcon icon, String typeEnemy, int type) {
        this.icon = icon;
        this.typeEnemy = typeEnemy;
        this.type = type;
    }

    public synchronized void applyDealtDamage(int dmg, Boolean doCrit) {
        dmg += (int)Math.ceil((double)dmg * inc);
        if (doCrit) {
            boolean crit = rnd.nextDouble() < 0.05;
            if (crit) dmg = (int)Math.ceil((double)dmg * Player.getInstance().getCrit());
        }

        if (typeEnemy.equals("Thief"))  {
            double resist = Mr;
            dmg = (int)Math.ceil((dmg * (1.0 - resist / (resist + 100))));
        }

        Hp = max(0, Hp - dmg);
    }

    public synchronized void applyDealtDamageTrue(int dmg, Boolean doCrit) {
        dmg += (int)Math.ceil((double)dmg * inc);
        if (doCrit) {
            boolean crit = rnd.nextDouble() < 0.05;
            if (crit) dmg = (int)Math.ceil((double)dmg * Player.getInstance().getCrit());
        }

        Hp = max(0, Hp - dmg);
    }

    public synchronized int dealDamage() {
        if (currHealth() == 0)
            return 0;
        boolean crit = rnd.nextDouble() < 0.05;
        int dmg = Dmg;
        if (crit) dmg = (int)Math.ceil((double)dmg * 3);
        if (potionRound1 > 0) return 0;
        return dmg;
    }

    public synchronized int getX() {
        int startX = 200;
        int endX = 280;
        int maxBarLength = endX - startX;

        // Calculate the proportional position based on current HP
        int proportionalPosition = (int) (((double) Hp / maxHp) * maxBarLength);
        return proportionalPosition;
    }

    public synchronized int currHealth() {
        return Hp;
    }

    public synchronized String getTypeEnemy() {
        return this.typeEnemy;
    }

    public ImageIcon getIcon() {
        return icon;
    }
}
