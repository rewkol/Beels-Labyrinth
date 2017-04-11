/*
 * Mitchell Golding 3552573
 * CS1083 Assignment 6
 * 5 April 2017
 *
 * Purpose: Stores player data
 * Input: Nothing yet, maybe stats
 * Output: nothing
 */
package cs1083_assign6;

import javax.swing.JOptionPane;

public class Player extends Entity {
    private double hp;//Player's stats
    private int atk;
    private int def;
    private int spd;
    private double luck;
    private int exp;
    private int curfloor;
    private int level;
    private int maxhp;
    private boolean up1, up2;
    
    Player(double hp, int atk, int def, int spd, double luck)
    {
        super((int)(Math.random()*10)+0.1);//+0.1 because 1*0.1 = 1 and we always start on floor 1
        this.hp=hp;
        this.atk=atk;
        this.def=def;
        this.spd=spd;
        this.luck=luck;
        up1 = false;//upgrades
        up2 = false;
        exp = 0;
        curfloor = 1;//Because 0 wouldn't help the math
        level = 1;
        maxhp=(int)hp;
    }

    public double getHp() 
    {
        return hp;
    }

    public void setHp(double hp) 
    {
        this.hp = hp;
        super.setDamage((int)(Math.random()*10)+curfloor*(0.1)+level-1);//Roll new die for every loss
    }

    public int getAtk() 
    {
        if(up2)
            return 2*atk;
        else
            return atk;
    }

    public void setAtk(int atk) 
    {
        this.atk = atk;
    }

    public int getDef() 
    {
        if(up1)
            return 2*def;
        else
            return def;
    }

    public void setDef(int def) 
    {
        this.def = def;
    }

    public int getSpd() 
    {
        return spd;
    }

    public void setSpd(int spd) 
    {
        this.spd = spd;
    }

    public double getLuck() 
    {
        return luck;
    }

    public void setLuck(double luck) 
    {
        this.luck = luck;
    }

    public int getExp() 
    {
        return exp;
    }

    public void setExp(int exp) 
    {
        this.exp = exp;
        while(this.exp>=100)
        {//LEVEL UP!
            this.exp -= 100;
            level++;//Should help make fighting easier later
            int hgain; int dgain; int again; int sgain; double lgain;
            hgain = (int)(Math.random()*4);//0-3
            again = (int)(Math.random()*3);//0-2
            dgain = (int)(Math.random()*2);//0-1
            sgain = (int)(Math.random()*3);//0-3
            lgain = (double)((int)(Math.random()*3))/10;
            JOptionPane.showMessageDialog(null,"LEVEL UP!\nLevel: "+level+"\n\n"+
                    "HP + "+hgain+"\nATK + "+again+"\nDEF + "+dgain+"\nSPD + "+sgain+"\nLUCK + "+lgain);
            maxhp+=hgain;atk+=again;def+=dgain;spd+=sgain;luck+=lgain;
            hp = maxhp;
        }
        super.setDamage((int)(Math.random()*10)+curfloor*(0.1)+level-1);//Change dice roll after every battle win or lose
    }
    public void advance()
    {//Let's us change damage
        curfloor++;
        super.setDamage((int)(Math.random()*10)+curfloor*(0.1)+level-1);
    }
    public void fillHp()
    {
        hp = maxhp;
    }

    public void upDef()
    {
        up1 = true;
    }
    public void upAtk()
    {
        up2 = true;
    }
    public int getLevel()
    {
        return level;
    }
}
