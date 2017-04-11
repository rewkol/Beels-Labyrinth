/*
 * Mitchell Golding 3552573
 * CS1083 Assignment 6
 * 5 April 2017
 *
 * Purpose: run the Final Boss
 * Input: like a normal Turn-based RPG
 * Output: like a normal Turn-based RPG
 */
package cs1083_assign6;
import javax.swing.*; import java.awt.*; import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import javax.swing.Timer;
public class BossPanel extends JPanel{
    private ImageIcon intro1,intro2,intro3,intro4,intro5,outro1,outro2,outro3,outro4,outro5;
    private Timer introtime,gametime,gameovertime,victorytime;
    private int introcount,gameovercount,victorycount;
    private Player me;
    private boolean turn,attacked,attack;
    private int maxhp,hp,atk,def,spd,maxmp,mp,bmaxhp,bhp,batk,bdef,bspd;
    private double luck,bluck;
    private int select,beelstime,playertime,displaycount;
    private int playerfill, beelsfill;
    private String display,bdisplay;
    
    BossPanel(Player mew)
    {
        this.me = mew;
        //Me stats
        me.fillHp();
        maxhp = (int) me.getHp();
        hp = maxhp;
        atk = me.getAtk();
        def = me.getDef();
        spd = me.getSpd();
        luck = me.getLuck();
        mp = me.getLevel()*2;
        maxmp = mp;
        //His stats
        bmaxhp = 150;
        bhp = bmaxhp;
        batk = 15;
        bdef = 6;
        bspd = 9;
        bluck = 0.6;
        
        beelstime = 0; 
        playertime = 0;
        playerfill = 0;
        beelsfill = 0;
        displaycount = 45;
        
        display = "";
        bdisplay = "";
        
        select = 1;//I mean really this should be an int;

        
        intro1 = new ImageIcon("Beels1.jpg");//I didn't think this would accept a png, I was wrong and suffered for it
        intro2 = new ImageIcon("Beels2.jpg");
        intro3 = new ImageIcon("Beels3.jpg");
        intro4 = new ImageIcon("Beels4.jpg");
        intro5 = new ImageIcon("Beels5.jpg");
        
        outro1 = new ImageIcon("Beels6.png");//Can you tell this was made a day later
        outro2 = new ImageIcon("Beels7.png");
        outro3 = new ImageIcon("Beels8.png");//If you're wondering how I got it without losing quality
        outro4 = new ImageIcon("Beels9.png");//Luckily I accidentally saved Beels5 as a png, so I lost the layers and could not edit the face anymore
        outro5 = new ImageIcon("Beels10.png");//Which meant that I could not use it to salvage Beels1-4
                                                //But to just change the colour, that I can and did do!
        introcount = 1;
        
        introtime = new Timer(500,new Animations());
        gametime = new Timer(16,new BattleTimer());
        gameovertime = new Timer(16,new GameOver());
        victorytime = new Timer(16,new Victory());
        victorycount = 0;
        gameovercount = 0;
        introtime.start();
        gametime.start();
        
        this.setFocusable(true);
        this.addKeyListener(new KeyHandler());
    }
    
    @Override
    public void paintComponent(Graphics win)
    {
        super.paintComponent(win);
        
        if(victorycount<=0)
        {
            setBackground(new Color(101,83,59));//Stupid jpgs didn't save trans-freaking-parencies!
            if(introcount == 5||victorycount>0)
            {
                introtime.stop();
            }
            switch(introcount)
            {
                case 1:
                {
                    intro1.paintIcon(this,win,this.getWidth()/2-256,this.getHeight()/3-256);
                    break;
                }
                case 2:
                {
                    intro2.paintIcon(this,win,this.getWidth()/2-256,this.getHeight()/3-256);
                    break;
                }
                case 3:
                {
                    intro3.paintIcon(this,win,this.getWidth()/2-256,this.getHeight()/3-256);
                    break;
                }
                case 4:
                {
                    intro4.paintIcon(this,win,this.getWidth()/2-256,this.getHeight()/3-256);
                    break;
                }
                default:
                {
                    intro5.paintIcon(this,win,this.getWidth()/2-256,this.getHeight()/3-256);
                    break;
                } 
            }
            win.setColor(new Color(250,253,250));
            win.fillRect(0,2*this.getHeight()/3,this.getWidth(),15);
            win.fillRect(7*this.getWidth()/9,2*this.getHeight()/3,15,this.getHeight());
            win.setFont(new Font("Pristina",1,48));
            //Health stuff
            win.drawString("HP: ",15*this.getWidth()/18,5*this.getHeight()/6);
            win.drawString(hp+"/"+maxhp,8*this.getWidth()/9,5*this.getHeight()/6);
            win.drawString("MP: ",15*this.getWidth()/18,11*this.getHeight()/12);
            win.drawString(mp+"/"+maxmp,8*this.getWidth()/9,11*this.getHeight()/12);
            //Attack options
            win.drawString("Attack",this.getWidth()/3,5*this.getHeight()/6);
            win.drawString("Magic",this.getWidth()/3,11*this.getHeight()/12);
            switch(select)
            {
                case 1:
                {
                    win.drawRect(7*this.getWidth()/24,19*this.getHeight()/24,this.getWidth()/6,this.getHeight()/18);
                    break;
                }
                case 2:
                {
                    win.drawRect(7*this.getWidth()/24,21*this.getHeight()/24,this.getWidth()/6,this.getHeight()/18);
                    break;
                }
            }

            //Battle Timers
            win.drawRect(8*this.getWidth()/9,23*this.getHeight()/24,100,15);
            win.drawRect(this.getWidth()/3,9*this.getHeight()/12,200,15);
            win.fillRect(8*this.getWidth()/9,23*this.getHeight()/24,beelsfill,16);
            win.fillRect(this.getWidth()/3,9*this.getHeight()/12,playerfill,16);

            if(attack)
            {
                win.drawString(display,this.getWidth()/2,this.getHeight()/2);
            }
            if(attacked)
            {
                win.setColor(new Color(245,15,15));
                win.drawString(bdisplay,15*this.getWidth()/18,9*this.getHeight()/12);
            }

            //GameOver paints
            if(gameovercount>0)
            {
                win.setFont(new Font("Pristina",2,100));
                win.setColor(new Color(255,12,10));
                if(gameovercount<90)
                {
                    win.setFont(new Font("Pristina",2,100));
                    win.setColor(new Color(255,12,10));
                    win.drawString("GAME OVER",this.getWidth()/2-150,this.getHeight()/2);
                }
                else if(gameovercount>=90)
                {
                    win.setFont(new Font("Pristina",2,100-(gameovercount-90)));
                    win.setColor(new Color(255,12,10));
                    win.drawString("GAME OVER",this.getWidth()/2-150+(gameovercount-90)/2,this.getHeight()/2);
                }
                if(gameovercount>190)
                    System.exit(0);
            }
        }
        //Victory/Credits paints
        else if(victorycount>0&&victorycount<180)
        {
            int r = 101+victorycount;
            int g =83+victorycount;
            int b =59+victorycount;
            if(r>=255)
                r =255;
            if(g>=255)
                g = 255;
            if(b>=255)
                b = 255;
            setBackground(new Color(r,g,b));
            
            if(victorycount<36)
                outro1.paintIcon(this,win,this.getWidth()/2-256,this.getHeight()/3-256);
            else if(victorycount>=36&&victorycount<72)
                outro2.paintIcon(this,win,this.getWidth()/2-256,this.getHeight()/3-256);
            else if(victorycount>=72&&victorycount<108)
                outro3.paintIcon(this,win,this.getWidth()/2-256,this.getHeight()/3-256);
            else if(victorycount>=108&&victorycount<144)
                outro4.paintIcon(this,win,this.getWidth()/2-256,this.getHeight()/3-256);
            else if(victorycount>=144&&victorycount<18)
                outro5.paintIcon(this,win,this.getWidth()/2-256,this.getHeight()/3-256);
        }
        else if(victorycount==180)
        {
            setBackground(new Color(0,0,0));
            try
            {
                InputStream input = new FileInputStream(new File("Staff Roll - Super Mario 64.wav"));
                AudioStream asj = new AudioStream(input);
                AudioPlayer.player.start(asj);
            }
            catch(IOException doesnothing)
            {
                ;//This better gosh darn play! KOJI KONDO!
            }
        }
        
        //FULL BLOWN CREDITS SEQUENCE!
        //Could've been done with a couple JLabels with formatted text strings, but I couldn't get them to use coordinates properly
        //And seeing as I was already painting stuff anyway, I decided to make this monstrosity instead!
        else if(victorycount>180)
        {
            //TITLE
            if(victorycount>180&&victorycount<195)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,150));
                win.drawString("Beels\' Labyrinth",5*this.getWidth()/28,this.getHeight()/2);
            }
            if(victorycount>=195&&victorycount<495)
            {
                win.setColor(new Color(255,255,255));
                win.setFont(new Font("TimesNewRoman",0,150));
                win.drawString("Beels\' Labyrinth",5*this.getWidth()/28,this.getHeight()/2);
            }
            if(victorycount>=495&&victorycount<510)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,150));
                win.drawString("Beels\' Labyrinth",5*this.getWidth()/28,this.getHeight()/2);
            }
            
            //GAME DIRECTOR
            if(victorycount>570&&victorycount<585)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Game Director",this.getWidth()/3,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=585&&victorycount<885)
            {
                win.setColor(new Color(255,255,255));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Game Director",this.getWidth()/3,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=885&&victorycount<900)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Game Director",this.getWidth()/3,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            
            //PROGRAMMERS
            if(victorycount>1000&&victorycount<1015)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Programmer",17*this.getWidth()/48,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=1015&&victorycount<1315)
            {
                win.setColor(new Color(255,255,255));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Programmer",17*this.getWidth()/48,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=1315&&victorycount<1330)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Programmer",17*this.getWidth()/48,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            
            //SCENE PROGRAMMER
            if(victorycount>1430&&victorycount<1445)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Scene Programmer",9*this.getWidth()/32,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=1445&&victorycount<1745)
            {
                win.setColor(new Color(255,255,255));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Scene Programmer",9*this.getWidth()/32,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=1745&&victorycount<1760)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Scene Programmer",9*this.getWidth()/32,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            
            //DUNGEON DESIGN
            if(victorycount>1860&&victorycount<1875)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Dungeon Design",this.getWidth()/3,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=1875&&victorycount<2175)
            {
                win.setColor(new Color(255,255,255));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Dungeon Design",this.getWidth()/3,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=2175&&victorycount<2190)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Dungeon Design",this.getWidth()/3,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            
            //DUNGEON PROGRAMMER
            if(victorycount>2290&&victorycount<2305)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Dungeon Programmer",this.getWidth()/4,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=2305&&victorycount<2605)
            {
                win.setColor(new Color(255,255,255));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Dungeon Programmer",this.getWidth()/4,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=2605&&victorycount<2620)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Dungeon Programmer",this.getWidth()/4,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            
            //ENEMY DESIGN
            if(victorycount>2720&&victorycount<2735)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Enemy Design",this.getWidth()/3,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
                win.drawString("Fred Beels",35*this.getWidth()/96,this.getHeight()/2);
            }
            if(victorycount>=2735&&victorycount<3035)
            {
                win.setColor(new Color(255,255,255));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Enemy Design",this.getWidth()/3,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
                win.drawString("Fred Beels",35*this.getWidth()/96,this.getHeight()/2);
            }
            if(victorycount>=3035&&victorycount<3050)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Enemy Design",this.getWidth()/3,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
                win.drawString("Fred Beels",35*this.getWidth()/96,this.getHeight()/2);
            }
            
            //ENEMY PROGRAMMER
            if(victorycount>3150&&victorycount<3165)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Enemy Programmer",7*this.getWidth()/24,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=3165&&victorycount<3465)
            {
                win.setColor(new Color(255,255,255));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Enemy Programmer",7*this.getWidth()/24,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=3465&&victorycount<3480)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Enemy Programmer",7*this.getWidth()/24,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            
            //SOUND COMPOSER
            if(victorycount>3580&&victorycount<3595)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Sound Composer",5*this.getWidth()/16,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Koji Kondo",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=3595&&victorycount<3895)
            {
                win.setColor(new Color(255,255,255));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Sound Composer",5*this.getWidth()/16,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Koji Kondo",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=3895&&victorycount<3910)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Sound Composer",5*this.getWidth()/16,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Koji Kondo",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            
            //SOUND EFFECTS
            if(victorycount>4010&&victorycount<4025)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Sound Effects",this.getWidth()/3,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
                win.drawString("Unknown Artists",35*this.getWidth()/96,this.getHeight()/2);
            }
            if(victorycount>=4025&&victorycount<4325)
            {
                win.setColor(new Color(255,255,255));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Sound Effects",this.getWidth()/3,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
                win.drawString("Unknown Artists",35*this.getWidth()/96,this.getHeight()/2);
            }
            if(victorycount>=4325&&victorycount<4340)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Sound Effects",this.getWidth()/3,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
                win.drawString("Unknown Artists",35*this.getWidth()/96,this.getHeight()/2);
            }
            
            //SOUND PROGRAMMER
            if(victorycount>4440&&victorycount<4455)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Sound Programmer",9*this.getWidth()/32,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=4455&&victorycount<4755)
            {
                win.setColor(new Color(255,255,255));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Sound Programmer",9*this.getWidth()/32,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=4755&&victorycount<4770)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Sound Programmer",9*this.getWidth()/32,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            
            //ART DIRECTOR
            if(victorycount>4870&&victorycount<4885)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Art Director",15*this.getWidth()/40,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=4885&&victorycount<5185)
            {
                win.setColor(new Color(255,255,255));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Art Director",15*this.getWidth()/40,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=5185&&victorycount<5200)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Art Director",15*this.getWidth()/40,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            
            //SPRITE ARTIST
            if(victorycount>5300&&victorycount<5315)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Sprite Artist",23*this.getWidth()/64,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=5315&&victorycount<5615)
            {
                win.setColor(new Color(255,255,255));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Sprite Artist",23*this.getWidth()/64,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=5615&&victorycount<5630)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Sprite Artist",23*this.getWidth()/64,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            
            //ANIMATION
            if(victorycount>5730&&victorycount<5745)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Animation",9*this.getWidth()/24,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=5745&&victorycount<6045)
            {
                win.setColor(new Color(255,255,255));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Animation",9*this.getWidth()/24,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=6045&&victorycount<6060)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Animation",9*this.getWidth()/24,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            
            //SCREEN TEXT WRITER
            if(victorycount>6160&&victorycount<6175)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Screen Text Writer",5*this.getWidth()/16,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=6175&&victorycount<6475)
            {
                win.setColor(new Color(255,255,255));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Screen Text Writer",5*this.getWidth()/16,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=6475&&victorycount<6490)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Screen Text Writer",5*this.getWidth()/16,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            
            //PROGRESS MANAGEMENT
            if(victorycount>6590&&victorycount<6605)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Progress Management",this.getWidth()/4,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=6605&&victorycount<6905)
            {
                win.setColor(new Color(255,255,255));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Progress Management",this.getWidth()/4,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=6905&&victorycount<6920)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Progress Management",this.getWidth()/4,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            
            //QUALITY ASSURANCE
            if(victorycount>7020&&victorycount<7035)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Quality Assurance",5*this.getWidth()/16,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=7035&&victorycount<7335)
            {
                win.setColor(new Color(255,255,255));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Quality Assurance",5*this.getWidth()/16,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=7335&&victorycount<7350)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Quality Assurance",5*this.getWidth()/16,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            
            //DEBUG
            if(victorycount>7450&&victorycount<7465)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Debugging",9*this.getWidth()/24,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=7465&&victorycount<7765)
            {
                win.setColor(new Color(255,255,255));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Debugging",9*this.getWidth()/24,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=7765&&victorycount<7780)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Debugging",9*this.getWidth()/24,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            
            //Technical Support
            if(victorycount>7880&&victorycount<7895)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Technical Support",this.getWidth()/3,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Nicholas Balcomb",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=7895&&victorycount<8195)
            {
                win.setColor(new Color(255,255,255));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Technical Support",this.getWidth()/3,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Nicholas Balcomb",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=8195&&victorycount<8210)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Technical Support",this.getWidth()/3,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Nicholas Balcomb",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            
            //GAME IDEA
            if(victorycount>8310&&victorycount<8325)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Original Idea",this.getWidth()/3,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Fred Beels",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=8325&&victorycount<8625)
            {
                win.setColor(new Color(255,255,255));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Original Idea",this.getWidth()/3,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Fred Beels",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=8625&&victorycount<8640)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Original Idea",this.getWidth()/3,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Fred Beels",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            
            //SPECIAL THANKS I
            if(victorycount>8740&&victorycount<8755)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Special Thanks",this.getWidth()/3,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Nicholas Balcomb",35*this.getWidth()/96,this.getHeight()/2);
                win.drawString("Nicholas Ouellette",35*this.getWidth()/96,2*this.getHeight()/3);
                win.drawString("James Greening",35*this.getWidth()/96,5*this.getHeight()/6);
            }
            if(victorycount>=8755&&victorycount<9055)
            {
                win.setColor(new Color(255,255,255));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Special Thanks",this.getWidth()/3,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Nicholas Balcomb",35*this.getWidth()/96,this.getHeight()/2);
                win.drawString("Nicholas Ouellette",35*this.getWidth()/96,2*this.getHeight()/3);
                win.drawString("James Greening",35*this.getWidth()/96,5*this.getHeight()/6);
            }
            if(victorycount>=9055&&victorycount<9070)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Special Thanks",this.getWidth()/3,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Nicholas Balcomb",35*this.getWidth()/96,this.getHeight()/2);
                win.drawString("Nicholas Ouellette",35*this.getWidth()/96,2*this.getHeight()/3);
                win.drawString("James Greening",35*this.getWidth()/96,5*this.getHeight()/6);
            }
            
            //SPECIAL THANKS II
            if(victorycount>9170&&victorycount<9185)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Ethan McLellan",35*this.getWidth()/96,this.getHeight()/2);
                win.drawString("Austin Fraser",35*this.getWidth()/96,2*this.getHeight()/3);
                win.drawString("Sebastien Gauthier",35*this.getWidth()/96,5*this.getHeight()/6);
            }
            if(victorycount>=9185&&victorycount<9485)
            {
                win.setColor(new Color(255,255,255));
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Ethan McLellan",35*this.getWidth()/96,this.getHeight()/2);
                win.drawString("Austin Fraser",35*this.getWidth()/96,2*this.getHeight()/3);
                win.drawString("Sebastien Gauthier",35*this.getWidth()/96,5*this.getHeight()/6);
            }
            if(victorycount>=9485&&victorycount<9500)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Ethan McLellan",35*this.getWidth()/96,this.getHeight()/2);
                win.drawString("Austin Fraser",35*this.getWidth()/96,2*this.getHeight()/3);
                win.drawString("Sebastien Gauthier",35*this.getWidth()/96,5*this.getHeight()/6);
            }
            
            //SPECIAL THANKS III
            if(victorycount>9600&&victorycount<9615)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Fred Beels",35*this.getWidth()/96,this.getHeight()/2);
                win.drawString("Mohammadhossein",35*this.getWidth()/96,2*this.getHeight()/3);
                win.drawString(" Norouzimahalli",35*this.getWidth()/96,9*this.getHeight()/12);
            }
            if(victorycount>=9615&&victorycount<9915)
            {
                win.setColor(new Color(255,255,255));
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Fred Beels",35*this.getWidth()/96,this.getHeight()/2);
                win.drawString("Mohammadhossein",35*this.getWidth()/96,2*this.getHeight()/3);
                win.drawString(" Norouzimahalli",35*this.getWidth()/96,9*this.getHeight()/12);
            }
            if(victorycount>=9915&&victorycount<9930)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Fred Beels",35*this.getWidth()/96,this.getHeight()/2);
                win.drawString("Mohammadhossein",35*this.getWidth()/96,2*this.getHeight()/3);
                win.drawString(" Norouzimahalli",35*this.getWidth()/96,9*this.getHeight()/12);
            }
            
            //EXECUTIVE PRODUCER
            if(victorycount>10030&&victorycount<10045)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Executive Producer",7*this.getWidth()/24,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=10045&&victorycount<10405)
            {
                win.setColor(new Color(255,255,255));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Executive Producer",7*this.getWidth()/24,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            if(victorycount>=10405&&victorycount<10420)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,75));
                win.drawString("Executive Producer",7*this.getWidth()/24,this.getHeight()/3);
                win.setFont(new Font("TimesNewRoman",0,55));
                win.drawString("Mitchell Golding",35*this.getWidth()/96,2*this.getHeight()/3);
            }
            
            //LEGAL WARNINGS
            if(victorycount>10520&&victorycount<10535)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,35));
                win.drawString("The story, all names, characters, and incidents portrayed in this game are fictitious. ",this.getWidth()/12,3*this.getHeight()/15);
                win.drawString("No identification with actual persons (living or deceased), places, buildings, and ",this.getWidth()/12,4*this.getHeight()/15);
                win.drawString("products is intended or should be inferred.",this.getWidth()/12,5*this.getHeight()/15);
                win.drawString("No person or entity associated with this film received payment or anything of value.",this.getWidth()/12,7*this.getHeight()/15);
            }
            if(victorycount>=10535&&victorycount<11035)
            {
                win.setColor(new Color(255,255,255));
                win.setFont(new Font("TimesNewRoman",0,35));
                win.drawString("The story, all names, characters, and incidents portrayed in this game are fictitious. ",this.getWidth()/12,3*this.getHeight()/15);
                win.drawString("No identification with actual persons (living or deceased), places, buildings, and ",this.getWidth()/12,4*this.getHeight()/15);
                win.drawString("products is intended or should be inferred.",this.getWidth()/12,5*this.getHeight()/15);
                win.drawString("No person or entity associated with this film received payment or anything of value.",this.getWidth()/12,7*this.getHeight()/15);
            }
            if(victorycount>=11035&&victorycount<11050)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,35));
                win.drawString("The story, all names, characters, and incidents portrayed in this game are fictitious. ",this.getWidth()/12,3*this.getHeight()/15);
                win.drawString("No identification with actual persons (living or deceased), places, buildings, and ",this.getWidth()/12,4*this.getHeight()/15);
                win.drawString("products is intended or should be inferred.",this.getWidth()/12,5*this.getHeight()/15);
                win.drawString("No person or entity associated with this film received payment or anything of value.",this.getWidth()/12,7*this.getHeight()/15);
            }
            
            ///THANK YOU
            if(victorycount>11050&&victorycount<11075)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,150));
                win.drawString("Thank You",6*this.getWidth()/28,this.getHeight()/2);
                win.drawString("For Playing!",6*this.getWidth()/28,2*this.getHeight()/3);
            }
            if(victorycount>=11075&&victorycount<11975)
            {
                win.setColor(new Color(255,255,255));
                win.setFont(new Font("TimesNewRoman",0,150));
                win.drawString("Thank You",6*this.getWidth()/28,this.getHeight()/2);
                win.drawString("For Playing!",6*this.getWidth()/28,2*this.getHeight()/3);
            }
            if(victorycount>=11975&&victorycount<11990)
            {
                win.setColor(new Color(235,235,235));
                win.setFont(new Font("TimesNewRoman",0,150));
                win.drawString("Thank You",6*this.getWidth()/28,this.getHeight()/2);
                win.drawString("For Playing!",6*this.getWidth()/28,2*this.getHeight()/3);
            }
            if(victorycount>=11990&&victorycount<12005)
            {
                win.setColor(new Color(215,215,215));
                win.setFont(new Font("TimesNewRoman",0,150));
                win.drawString("Thank You",6*this.getWidth()/28,this.getHeight()/2);
                win.drawString("For Playing!",6*this.getWidth()/28,2*this.getHeight()/3);
            }
            if(victorycount>=12005&&victorycount<12020)
            {
                win.setColor(new Color(195,195,195));
                win.setFont(new Font("TimesNewRoman",0,150));
                win.drawString("Thank You",6*this.getWidth()/28,this.getHeight()/2);
                win.drawString("For Playing!",6*this.getWidth()/28,2*this.getHeight()/3);
            }
            if(victorycount>=12020&&victorycount<12035)
            {
                win.setColor(new Color(175,175,175));
                win.setFont(new Font("TimesNewRoman",0,150));
                win.drawString("Thank You",6*this.getWidth()/28,this.getHeight()/2);
                win.drawString("For Playing!",6*this.getWidth()/28,2*this.getHeight()/3);
            }
            if(victorycount>=12035&&victorycount<12050)
            {
                win.setColor(new Color(155,155,155));
                win.setFont(new Font("TimesNewRoman",0,150));
                win.drawString("Thank You",6*this.getWidth()/28,this.getHeight()/2);
                win.drawString("For Playing!",6*this.getWidth()/28,2*this.getHeight()/3);
            }
            if(victorycount>=12050&&victorycount<12065)
            {
                win.setColor(new Color(135,135,135));
                win.setFont(new Font("TimesNewRoman",0,150));
                win.drawString("Thank You",6*this.getWidth()/28,this.getHeight()/2);
                win.drawString("For Playing!",6*this.getWidth()/28,2*this.getHeight()/3);
            }
            if(victorycount>=12065&&victorycount<12080)
            {
                win.setColor(new Color(115,115,115));
                win.setFont(new Font("TimesNewRoman",0,150));
                win.drawString("Thank You",6*this.getWidth()/28,this.getHeight()/2);
                win.drawString("For Playing!",6*this.getWidth()/28,2*this.getHeight()/3);
            }
            if(victorycount>=12080&&victorycount<12095)
            {
                win.setColor(new Color(95,95,95));
                win.setFont(new Font("TimesNewRoman",0,150));
                win.drawString("Thank You",6*this.getWidth()/28,this.getHeight()/2);
                win.drawString("For Playing!",6*this.getWidth()/28,2*this.getHeight()/3);
            }
            if(victorycount>=12095&&victorycount<12110)
            {
                win.setColor(new Color(75,75,75));
                win.setFont(new Font("TimesNewRoman",0,150));
                win.drawString("Thank You",6*this.getWidth()/28,this.getHeight()/2);
                win.drawString("For Playing!",6*this.getWidth()/28,2*this.getHeight()/3);
            }
            if(victorycount>=12110&&victorycount<12125)
            {
                win.setColor(new Color(55,55,55));
                win.setFont(new Font("TimesNewRoman",0,150));
                win.drawString("Thank You",6*this.getWidth()/28,this.getHeight()/2);
                win.drawString("For Playing!",6*this.getWidth()/28,2*this.getHeight()/3);
            }
            if(victorycount>=12125&&victorycount<12140)
            {
                win.setColor(new Color(35,35,35));
                win.setFont(new Font("TimesNewRoman",0,150));
                win.drawString("Thank You",6*this.getWidth()/28,this.getHeight()/2);
                win.drawString("For Playing!",6*this.getWidth()/28,2*this.getHeight()/3);
            }
            if(victorycount>=12140&&victorycount<12155)
            {
                win.setColor(new Color(15,15,15));
                win.setFont(new Font("TimesNewRoman",0,150));
                win.drawString("Thank You",6*this.getWidth()/28,this.getHeight()/2);
                win.drawString("For Playing!",6*this.getWidth()/28,2*this.getHeight()/3);
            }
            if(victorycount>=12155&&victorycount<12170)
            {
                win.setColor(new Color(0,0,0));
                win.setFont(new Font("TimesNewRoman",0,150));
                win.drawString("Thank You",6*this.getWidth()/28,this.getHeight()/2);
                win.drawString("For Playing!",6*this.getWidth()/28,2*this.getHeight()/3);
            }
        }
    }
    private class Animations implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) 
        {
            introcount++;
            repaint();
        }
        
    }
    private class BattleTimer implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            playertime += 1+(spd/3);
            beelstime+=1+(bspd/2);
            playerfill = (int)(playertime/3);
            beelsfill = (int) (beelstime/7.5);
            if(attacked||attack)
            {
                displaycount--;
            }
            if(displaycount<=0)
            {
                attacked = false;
                attack = false;
            }
            if(playertime>=600)//Player turn
            {
                turn = true;
                playerfill = 200;
            }
            if(beelstime>=750&&turn)
            {
                beelsfill = 100;
            }
            else if(beelstime>=750&&!turn)
            {
                displaycount = 45;
                if(Math.random()>0.8)//Magic Attack(AKA More damage)
                {
                    bdisplay = batk*(1+bluck)-def+"";
                    attacked = true;
                    hp -= batk*(1+bluck)-def;
                    try
                    {
                        InputStream input = new FileInputStream(new File("bmagic.wav"));
                        AudioStream asj = new AudioStream(input);
                        AudioPlayer.player.start(asj);
                    }
                    catch(IOException doesnothing)
                    {
                        ;//Who cares if it doesn't play
                    }
                }
                else
                {
                    if(Math.random()<bluck)
                    {
                        bdisplay = batk*2-def+"";
                        attacked = true;
                        hp -= batk*2-def;
                        try
                        {
                            InputStream input = new FileInputStream(new File("bcrit.wav"));
                            AudioStream asj = new AudioStream(input);
                            AudioPlayer.player.start(asj);
                        }
                        catch(IOException doesnothing)
                        {
                            ;//Who cares if it doesn't play
                        }
                    }
                    else
                    {
                        bdisplay = batk-def+"";
                        attacked = true;
                        hp -= batk-def;
                        try
                        {
                            InputStream input = new FileInputStream(new File("bhit.wav"));
                            AudioStream asj = new AudioStream(input);
                            AudioPlayer.player.start(asj);
                        }
                        catch(IOException doesnothing)
                        {
                            ;//Who cares if it doesn't play
                        }
                    }
                }
                beelstime = 0;
            }
            if(hp<=0)//Start Gameover sequence
            {
                gametime.stop();
                gameovertime.start();
            }
            if(bhp<=0)//Start win sequence
            {
                gametime.stop();
                try
                {
                    InputStream input = new FileInputStream(new File("bdead.wav"));
                    AudioStream asj = new AudioStream(input);
                    AudioPlayer.player.start(asj);
                }
                catch(IOException doesnothing)
                {
                    ;//Let's pray it plays
                }
                displaycount = 0;
                victorytime.start();
            }
            repaint();
        }
    }
    private class GameOver implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            gameovercount++;
            repaint();
        }
    }
    private class Victory implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            victorycount++;
            repaint();
        }
    }
    private class KeyHandler implements KeyListener
    {
        @Override
        public void keyPressed(KeyEvent e) 
        {
            if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
            {
                System.exit(0);
            }
            if(e.getKeyCode()==KeyEvent.VK_DOWN||e.getKeyCode()==KeyEvent.VK_S)
            {
                select++;
                if(select>2)
                    select = 1;
                repaint();
            }
            if(e.getKeyCode()==KeyEvent.VK_UP||e.getKeyCode()==KeyEvent.VK_W)
            {
                select--;
                if(select<1)
                    select = 2;
                repaint();
            }
            if((e.getKeyCode()==KeyEvent.VK_SPACE||e.getKeyCode()==KeyEvent.VK_ENTER)&&turn)
            {
                displaycount = 45;
                switch(select)
                {
                    case 1:
                    {
                        if(Math.random()<luck)
                        {
                            display = atk*2-bdef+"";
                            attack = true;
                            bhp -= atk*2-bdef;
                            playertime = 0;
                            if(Math.random()<luck)//Double crit!!
                            {
                                bdef--;
                                try
                                {
                                    InputStream input = new FileInputStream(new File("DoubleCrit.wav"));
                                    AudioStream asj = new AudioStream(input);
                                    AudioPlayer.player.start(asj);
                                }
                                catch(IOException doesnothing)
                                {
                                    ;//Who cares if it doesn't play
                                }
                            }
                            else
                            {
                                try
                                {
                                InputStream input = new FileInputStream(new File("Crit.wav"));
                                AudioStream asj = new AudioStream(input);
                                AudioPlayer.player.start(asj);
                                }
                                catch(IOException doesnothing)
                                {
                                    ;//Who cares if it doesn't play
                                }
                            }
                        }
                        else
                        {
                            display = atk-bdef+"";
                            attack = true;
                            bhp-= atk-bdef;
                            playertime = 0;
                            try
                            {
                                InputStream input = new FileInputStream(new File("hit.wav"));
                                AudioStream asj = new AudioStream(input);
                                AudioPlayer.player.start(asj);
                            }
                            catch(IOException doesnothing)
                            {
                                ;//Who cares if it doesn't play
                            }
                        }
                        break;
                    }
                    case 2:
                    {
                        if(mp>0)
                        {
                            display = (int)(atk*(1+luck)-bdef)+"";
                            attack = true;
                            bhp -= atk*(1+luck)-bdef;
                            mp--;
                            playertime = 0;
                            try
                            {
                                InputStream input = new FileInputStream(new File("magic.wav"));
                                AudioStream asj = new AudioStream(input);
                                AudioPlayer.player.start(asj);
                            }
                            catch(IOException doesnothing)
                            {
                                ;//Who cares if it doesn't play
                            }
                        }
                        break;
                    }
                }
                turn = false;
            }
        }
        
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {}
        
    }
}
