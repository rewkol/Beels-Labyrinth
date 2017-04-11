/*
 * Mitchell Golding 3552573
 * CS1083 Assignment 6
 * 5 April 2017
 *
 * Purpose: Run the game
 * Input: Mouse clicks
 * Output: Gameplay
 */
package cs1083_assign6;
import javax.swing.*; import java.awt.*; import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import javax.swing.border.TitledBorder;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
public class GamePanel extends JPanel{
    private JButton EXIT;
    private int[][][] map =   {{{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},//Floor 1
                                {1,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1},//LEGEND - - - - - 
                                {1,1,1,1,1,0,4,0,0,0,0,0,0,0,1,1,1,1,1,1},//X0=clear
                                {1,1,1,1,0,0,0,0,0,4,0,0,0,0,4,0,1,1,1,1},//X1=wall
                                {1,1,1,1,0,0,0,0,0,0,0,6,0,0,0,0,0,0,1,1},//X2=exit
                                {1,1,1,0,0,6,0,0,0,0,0,0,0,0,0,6,0,3,1,1},//X3=treasure
                                {1,1,0,0,0,0,0,0,0,6,0,0,0,0,1,1,1,1,1,1},//X4=trap
                                {1,1,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1},//X5=monster
                                {1,1,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,1,1},//X6=archer
                                {1,1,6,0,0,0,0,1,1,1,1,1,1,0,1,1,1,1,1,1},//X7=knight
                                {1,1,1,0,0,0,0,1,1,1,1,1,1,0,1,1,0,0,1,1},//X8=wizard
                                {1,1,1,0,0,0,0,0,1,1,1,1,1,0,1,1,1,0,1,1},//X9=BOSS
                                {1,1,1,1,0,0,4,0,0,0,1,1,1,0,0,0,0,0,1,1},//
                                {0,1,0,1,1,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1},//0X=undiscovered
                                {0,0,0,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1},//1X=discovered
                                {0,1,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1},//2X=Advanced(Enemies/Treasure only. Reserved for final boss/final treasure)
                                {0,1,0,1,0,0,0,0,0,6,0,0,0,0,0,0,1,1,1,1},//- - - - - - - - -
                                {1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1},
                                {1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}},//Floor 1
        
        {{1,2,1,4,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1},//Floor 2
        {1,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,1,1},
        {1,0,1,1,1,0,0,0,0,0,0,0,0,0,1,1,0,6,1,1},
        {1,0,1,1,1,1,0,0,0,1,0,0,0,1,1,1,0,0,1,1},
        {1,7,1,1,1,1,0,0,0,0,0,0,1,1,1,0,0,0,1,1},
        {1,0,1,1,1,1,1,1,0,4,1,1,1,1,0,0,0,1,1,1},
        {1,0,1,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,7,1},
        {1,0,1,1,1,1,1,1,1,1,0,0,0,1,1,1,0,0,0,1},
        {1,0,1,1,1,1,0,0,0,0,0,0,0,1,1,1,0,1,1,1},
        {1,6,1,1,1,0,0,0,0,0,0,0,1,1,1,4,0,0,0,1},
        {1,0,1,1,6,0,0,1,0,0,0,0,1,1,1,0,1,1,0,1},
        {1,0,1,1,1,1,0,0,0,6,0,0,1,4,0,0,3,1,0,1},
        {1,0,1,6,1,1,1,0,0,0,0,0,1,1,1,1,1,1,0,1},
        {1,0,1,0,0,1,1,1,1,0,0,0,0,1,7,0,0,0,0,1},
        {1,0,1,0,0,0,0,1,1,1,0,0,0,1,1,0,1,1,1,1},
        {1,0,1,0,1,1,0,1,1,1,1,0,0,0,0,0,0,1,1,1},
        {1,4,1,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1,1},
        {1,0,1,0,1,0,1,0,1,0,1,1,1,0,0,0,0,0,1,1},
        {1,0,1,0,1,0,1,7,1,0,0,0,1,1,0,0,6,0,0,1},
        {1,0,0,0,1,0,4,1,1,1,1,0,0,0,0,0,0,0,0,1}},//Floor 2
        
        {{0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0},//Floor 3
        {0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,7,0,0},
        {0,0,0,0,7,7,0,0,0,1,0,6,1,0,0,0,0,7,0,0},
        {0,0,0,0,7,0,0,0,0,1,1,1,1,0,0,0,0,7,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,6,0,6,0,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0},
        {1,1,0,1,1,1,6,0,0,0,0,0,6,1,1,1,0,1,1,1},
        {0,0,0,0,0,1,0,1,1,0,1,1,0,1,7,0,0,0,0,0},
        {0,1,1,1,0,1,0,1,0,7,0,1,0,1,0,1,1,1,1,0},
        {0,1,1,1,0,1,0,1,8,2,8,1,0,1,0,1,4,4,4,0},
        {0,1,1,1,0,1,0,1,0,7,0,1,0,1,0,1,1,1,1,0},
        {0,0,0,0,7,1,0,1,1,1,1,1,0,1,0,0,0,0,0,7},
        {1,1,0,1,1,1,4,0,0,0,0,0,4,1,1,1,0,1,1,1},
        {0,0,0,0,0,1,1,0,1,1,1,0,1,1,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1},
        {0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,1,0,0,7,0},
        {0,1,8,0,8,1,0,0,0,1,7,0,0,1,7,1,0,1,1,0},
        {0,1,0,0,0,1,0,0,0,1,0,0,0,1,0,1,0,1,1,0},
        {0,1,1,0,1,1,0,0,0,0,0,1,0,0,0,0,0,1,3,0},
        {0,0,0,0,0,0,0,0,0,0,0,1,7,0,0,1,7,1,1,1}},//Floor 3
        
        {{1,1,5,0,5,1,1,1,1,1,1,1,1,1,1,1,1,1,3,1},//Floor 4
        {1,1,0,2,0,0,0,0,0,0,0,0,0,0,5,0,0,1,0,1},
        {1,1,5,0,5,1,1,1,1,1,1,1,1,1,1,0,0,1,0,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,0,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,0,1},
        {1,1,1,1,0,0,0,4,0,0,0,4,0,0,1,0,0,1,0,1},
        {1,1,1,1,0,0,0,0,0,4,0,0,0,0,1,8,0,1,5,1},
        {1,1,1,1,0,0,0,1,1,1,1,1,0,0,1,0,0,1,0,1},
        {5,0,0,0,0,0,0,1,5,4,5,1,0,0,1,0,0,1,0,1},
        {0,0,0,0,0,4,0,1,8,0,8,1,0,0,1,0,0,1,0,1},
        {0,0,0,0,0,0,0,1,7,0,7,1,6,0,1,0,0,1,0,1},
        {0,0,0,1,1,1,1,1,0,0,0,1,0,0,0,0,0,1,0,1},
        {0,8,0,1,1,1,1,1,0,0,0,1,0,0,0,0,7,1,0,1},
        {0,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,0,1},
        {0,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,0,1},
        {0,0,7,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,5,1},
        {0,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1},
        {0,0,0,0,0,0,0,0,7,0,0,1,1,1,1,1,1,1,1,1},
        {0,0,0,0,6,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1},
        {7,0,0,0,0,0,0,0,0,0,8,1,1,1,1,1,1,1,1,1}},//Floor 4
        
        {{20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20},//Floor 5
        {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20},//All 20s because this is the special floor
        {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20},//High quality graphics
        {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20},
        {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20},
        {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20},
        {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20},
        {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20},
        {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20},
        {20,20,20,20,20,20,20,20,20,29,20,20,20,20,20,20,20,20,20,20},
        {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20},
        {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20},
        {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20},
        {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20},
        {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20},
        {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20},
        {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20},
        {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20},
        {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20},
        {20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20,20}}};//Floor 5
    
    private JButton[][] buttons = new JButton[20][20];
    private ImageIcon dark,ground,wall,player,nextl,nextu,nextr,nextd,treasure,exit,enemy,fancy,gilded,beels;
    private int curfloor = 0;
    private int playx = -1;
    private int playy = -1;
    private boolean compass,dmap, game, debug;//flags for treasures, fifth is end game
    private Player me;
    
    GamePanel()
    {
        setLayout(new GridLayout(20,20));
        me = new Player(100,6,2,5,0.1);//All stats come into play in final boss
        
        compass = false; //Shows treasure             
        dmap= false; //Shows walls
        game = true; //Game is still going
        
        //REMEMBER TO SET FALSE AFTER DEBUG!
        debug = false; //Shows all positions for debug purposes
        
        dark = new ImageIcon("Dark.jpg");
        ground = new ImageIcon("Ground.jpg");
        wall = new ImageIcon("Wall.jpg");
        player = new ImageIcon("Player.jpg");
        nextl = new ImageIcon("Nextl.jpg");
        nextr = new ImageIcon("Nextr.jpg");
        nextd = new ImageIcon("Nextd.jpg");
        nextu = new ImageIcon("Nextu.jpg");
        treasure = new ImageIcon("Treasure.jpg");
        exit = new ImageIcon("Exit.jpg");
        enemy = new ImageIcon("Enemy.jpg");
        fancy = new ImageIcon("Fancy Ground.jpg");
        gilded = new ImageIcon("Gilded.jpg");
        beels = new ImageIcon("Beels.jpg");
        
        playx = (int) (Math.random()*20);
        playy = 19;
        map[curfloor][playy][playx] = 10;
        
        JButton temp = new JButton();
        for(int j = 0;j<20;j++)
        {
            for(int k = 0; k<20;k++)
            {
                temp = new JButton();
                temp.setForeground(new Color(10,13,12));
                temp.addKeyListener(new KeyHandler());
                temp.addActionListener(new ButtonHandler());
                buttons[j][k] = temp;
            }
        }
        for(int i = 0;i<20;i++)
        {
            for(int j = 0; j<20;j++)
            {
                add(buttons[i][j]);
            }
        }
        
        //Instantiate the stuff
        updateIcons();
        
        this.setBorder(BorderFactory.createTitledBorder(null, "Beels' Labyrinth", TitledBorder.CENTER, TitledBorder.BELOW_TOP, new Font("Pristina",1,48)));
        
        setBackground(new Color(235,220,148));
        setPreferredSize(new Dimension(550,412));
        JOptionPane.showMessageDialog(null,"WELCOME TO BEELS\' LABYRINTH\n\n"+
                "You have awoken in a dark and mysterious dungeon. The serenading sounds of the city have been replaced by the malicious\n"+
                "monsters lurking deep in the dank cavern. The Archwizard Beels of Javalor has claimed this once Holy site as his own.\n"+
                "If you can survive long enough to reach him, and prevail in the final battle to the death, peace may once again return to\n"+
                "the United Nations of Beatum!\n\n"+
                "CONTROLS\n\nMovement - WASD/Arrow keys\nFast Travel - Click a cleared square to return to it instantly\n"+
                "Menu - E\nExit Game - ESC\n\n"+
                "As you battle monsters you will gain experience. Experience will allow you to level up and your stats will increase at random.\n"+
                "He is a strong man. Train hard, this will be your only preparation for the final battle.\n"+
                "Should you run low on health, you can regain it by either levelling up, or finding a hidden treasure on one of the dungeon levels.\n"+
                "Open the menu to check your health and other stats.");
    }
    
    private void updateIcons()//Updates icons after moving a space
    {
        //Make nearby walls visible!
        if(playy>0&&map[curfloor][playy-1][playx]==1)
            map[curfloor][playy-1][playx]=11;
        if(playy<19&&map[curfloor][playy+1][playx]==1)
            map[curfloor][playy+1][playx]=11;
        if(playx>0&&map[curfloor][playy][playx-1]==1)
            map[curfloor][playy][playx-1]=11;
        if(playx<19&&map[curfloor][playy][playx+1]==1)
            map[curfloor][playy][playx+1]=11;
        
        //Redo the board reflecting changes
        for(int i = 0;i<20;i++)
        {
            for(int j = 0;j<20;j++)
            {
                switch(map[curfloor][i][j])
                {
                    //Undiscovered tiles 0,4-9
                    default:
                    {
                        buttons[i][j].setIcon(dark);
                        break;
                    }
                    case 1:
                    {
                        if(dmap||debug)//If they have the map
                        {
                            buttons[i][j].setIcon(wall);
                        }
                        else
                        {
                            buttons[i][j].setIcon(dark); 
                        }
                        break;
                    }
                    case 2:
                    {
                        if(compass||debug)//if they have the compass
                        {
                            buttons[i][j].setIcon(exit);
                        }
                        else
                        {
                            buttons[i][j].setIcon(dark);
                        }
                        break;
                    }
                    case 3:
                    {
                        if(compass||debug)//if they have the compass
                        {
                            buttons[i][j].setIcon(treasure);
                        }
                        else
                        {
                            buttons[i][j].setIcon(dark);
                        }
                        break;
                    }
                    case 4://Trap
                    {
                        if(debug)
                        {
                            buttons[i][j].setIcon(enemy);
                        }
                        else
                        {
                            buttons[i][j].setIcon(dark);
                        }
                        break;
                    }
                    case 5://Monster
                    {
                        if(debug)
                        {
                            buttons[i][j].setIcon(enemy);
                        }
                        else
                        {
                            buttons[i][j].setIcon(dark);
                        }
                        break;
                    }
                    case 6://Archer
                    {
                        if(debug)
                        {
                            buttons[i][j].setIcon(enemy);
                        }
                        else
                        {
                            buttons[i][j].setIcon(dark);
                        }
                        break;
                    }
                    case 7://Knight
                    {
                        if(debug)
                        {
                            buttons[i][j].setIcon(enemy);
                        }
                        else
                        {
                            buttons[i][j].setIcon(dark);
                        }
                        break;
                    }
                    case 8://Wizard
                    {
                        if(debug)
                        {
                            buttons[i][j].setIcon(enemy);
                        }
                        else
                        {
                            buttons[i][j].setIcon(dark);
                        }
                        break;
                    }
                    
                    //Uncovered sections
                    case 10:
                    {
                        buttons[i][j].setIcon(ground);
                        break;
                    }
                    case 11:
                    {
                        buttons[i][j].setIcon(wall);
                        break;
                    }
                    //Bonus stuff
                    case 20:
                    {
                        buttons[i][j].setIcon(fancy);
                        break;
                    }
                    case 29:
                    {
                        buttons[i][j].setIcon(beels);
                        break;
                    }
                }
            }
        }
        
        
        //After resetting board for changes, reupdate with player travel info
        if(curfloor!=4)
            buttons[playy][playx].setIcon(player);
        else
            buttons[playy][playx].setIcon(gilded);
        if(playy>0&&map[curfloor][playy-1][playx]!=1&&map[curfloor][playy-1][playx]!=11&&map[curfloor][playy-1][playx]!=20&&map[curfloor][playy-1][playx]!=29)
            buttons[playy-1][playx].setIcon(nextu);
        if(playy<19&&map[curfloor][playy+1][playx]!=1&&map[curfloor][playy+1][playx]!=11&&map[curfloor][playy+1][playx]!=20&&map[curfloor][playy+1][playx]!=29)
            buttons[playy+1][playx].setIcon(nextd);
        if(playx>0&&map[curfloor][playy][playx-1]!=1&&map[curfloor][playy][playx-1]!=11&&map[curfloor][playy][playx-1]!=20&&map[curfloor][playy][playx-1]!=29)
            buttons[playy][playx-1].setIcon(nextl);
        if(playx<19&&map[curfloor][playy][playx+1]!=1&&map[curfloor][playy][playx+1]!=11&&map[curfloor][playy][playx+1]!=20&&map[curfloor][playy][playx+1]!=29)
            buttons[playy][playx+1].setIcon(nextr);
    }
    
    private boolean Battle(int j, int i)
    {//The damage doing method, also handles awarding treasure and moving to the next floor
        boolean win = false;
        Entity temp = new Entity(0);
        int Gain = 0;//Potential experience gain
        switch(map[curfloor][j][i])
        {
            case 2://Exit
            {
                switch(JOptionPane.showConfirmDialog(null,"AN EXIT HAS BEEN FOUND!\n\nContinue to next floor?"))
                {
                    case 0://yes
                    {
                        curfloor++;
                        me.advance();
                        if(curfloor==4)
                        {
                            try{
                                InputStream input = new FileInputStream(new File("Beels in Map.wav"));
                                AudioStream asj = new AudioStream(input);
                                AudioPlayer.player.start(asj);
                            }
                            catch(IOException doesnothing)
                            {
                                ;//Who cares if it doesn't play, it's literally one gong
                            }
                        }
                        break;
                    }
                    case 1://No
                    {
                        return false;
                    }
                    case 2://Cancel
                    {
                        return false;
                    }
                }
                break;
            }
            case 3://Treasure
            {
                switch(curfloor)
                {
                    case 0:
                    {
                        JOptionPane.showMessageDialog(null,"TREASURE!\n\nYou found the labyrinth compass.\nTreasure and exits are now visible.");
                        compass = true;
                        break;
                    }
                    case 1:
                    {
                        JOptionPane.showMessageDialog(null,"TREASURE!\n\nYou discovered the labyrinth map.\nWalls are now visible.");
                        dmap = true;
                        break;
                    }
                    case 2:
                    {
                        JOptionPane.showMessageDialog(null,"TREASURE!\n\nYou found the Armour of the Legendary Hero.\nDEF has been doubled.");
                        me.upDef();
                        break;
                    }
                    case 3:
                    {
                        JOptionPane.showMessageDialog(null,"TREASURE!\n\nYou found the Holy Sword \"Destruit Malum\"\nATK has been doubled");
                        me.upAtk();
                        break;
                    }
                }
                me.fillHp();
                break;
            }
            case 4://A trap
            {
                temp = new Trap(curfloor+1);//increasing it by every square traveled makes the monster overly OP overly fast!
                Gain = 10+(2*curfloor);
                if(temp.compareTo(me)<0)
                {
                    win = true;
                    JOptionPane.showMessageDialog(null, "A TRAP!\n\nYou disabled it before it could damage you.\n"+
                            Gain + " Experience gained!");
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "A TRAP!\n\nIt managed to go off before you could disarm it.\n"+
                            temp.getDamage()+ " damage taken!");
                }
                break;
            }
            case 5://Monster
            {
                temp = new Monster(curfloor+1);//increasing it by every square traveled makes the monster overly OP overly fast!
                Gain = 50+(2*curfloor);//These monsters are OP!
                if(temp.compareTo(me)<0)
                {
                    win = true;
                    JOptionPane.showMessageDialog(null, "A MONSTER ATTACKS!\n\nYou decapitate it before it's poison could pierce your armour.\n"+
                            Gain + " Experience gained!");
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "A MONSTER ATTACKS!\n\nIt managed to poison you before your blade could reach it.\n"+
                            temp.getDamage()+ " damage taken!");
                }
                break;
            }
            case 6://Archer
            {
                temp = new Archer(curfloor+1);//increasing it by every square traveled makes the monster overly OP overly fast!
                Gain = 20+(2*curfloor);
                if(temp.compareTo(me)<0)
                {
                    win = true;
                    JOptionPane.showMessageDialog(null, "AN ARCHER NOCKS AN ARROW!\n\nThe arrow missed its mark, your blade did not.\n"+
                            Gain + " Experience gained!");
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "AN ARCHER NOCKS AN ARROW!\n\nYour blade valiantly sliced through the nimble Archer, but it could not stop the arrow.\n"+
                            temp.getDamage()+ " damage taken!");
                }
                break;
            }
            case 7://Knight
            {
                temp = new Knight(curfloor+1);//increasing it by every square traveled makes the monster overly OP overly fast!
                Gain = 35+(2*curfloor);
                if(temp.compareTo(me)<0)
                {
                    win = true;
                    JOptionPane.showMessageDialog(null, "A KNIGHT ATTACKS!\n\nHis blade could not best yours.\n"+
                            Gain + " Experience gained!");
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "A KNIGHT ATTACKS!\n\nYou have been hurt, but 'tis but a flesh wound.\n"+
                            temp.getDamage()+ " damage taken!");
                }
                break;
            }
            case 8://Wizard
            {
                temp = new Wizard(curfloor+1);//increasing it by every square traveled makes the monster overly OP overly fast!
                Gain = 40+(2*curfloor);
                if(temp.compareTo(me)<0)
                {
                    win = true;
                    JOptionPane.showMessageDialog(null, "A WIZARD ATTACKS!\n\nIt's magicks have no effect on you.\n"+
                            Gain + " Experience gained!");
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "A WIZARD ATTACKS!\n\nThough your blade struck the Wizard, your armour could not withstand the powerful magic.\n"+
                            temp.getDamage()+ " damage taken!");
                }
                break;
            }
            case 29://Beels
            {
                BossBattle();
                break;
            }
        }
        if(!win)//If not a win, take damage and gain quarter exp
        {
            me.setHp(me.getHp()-temp.getDamage());
            me.setExp(me.getExp()+Gain/4);
        }
        else
            me.setExp(me.getExp()+Gain);
        if(me.getHp()<1)
        {
            GameOver();
        }
        if(map[curfloor][j][i]!=20)//If it isn't a special tile
        {
            map[curfloor][j][i]=10;//Set it as clear
        }
        return true;
    }
    private void GameOver()//The method for when you get a gameover!
    {
        JOptionPane.showMessageDialog(null,"GAME OVER!\n\nYou have fought valiantly to destroy the\n"+
                "legions of Archwizard Beels' malicious militia. May your soul\n"+
                "rest in peace and give safe passage to the next courageous soul\n"+
                "who attempts to slay the evil Archwizard.");
        System.exit(0);
    }
    public boolean getGame()//Returns game status
    {
        return game;
    }
    
    private void BossBattle()//Starts the final boss battle against Archwizard Beels
    {
        game = false;
    }
    public Player getPlayer()
    {
        return me;
    }
    
    private class ButtonHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            for(int i = 0;i<20;i++)
            {
                for(int j = 0;j<20;j++)
                {
                    if(e.getSource()==buttons[j][i])
                    {
                        if((Math.abs(playx-i)<=1&&playy-j==0)||(Math.abs(playy-j)<=1&&playx-i==0))//If it's within the one space diamond
                        {
                            if(map[curfloor][j][i]!=1&&map[curfloor][j][i]!=11)//If it's not a wall
                            {
                                if(Battle(j,i))
                                {
                                    playx = i;playy = j;//Move player after in case it has not been cleared
                                    updateIcons();
                                }
                            }
                        }
                        else if(map[curfloor][j][i]==10||map[curfloor][j][i]==20)
                        {
                            playx = i; playy = j;
                            updateIcons();
                        }
                    }
                }
            }
        }
    }
    private class KeyHandler implements KeyListener
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            if(e.getKeyCode()==KeyEvent.VK_ESCAPE)//How to quit game
            {
                System.exit(0);
            }
            if(e.getKeyCode()==KeyEvent.VK_W||e.getKeyCode()==KeyEvent.VK_UP)//Move up
            {
                if(playy>0)
                {
                    if(map[curfloor][playy-1][playx]!=1&&map[curfloor][playy-1][playx]!=11)
                    {
                        if(Battle(playy-1,playx))
                        {
                            playy--;
                            updateIcons(); 
                        }
                    }
                }
            }
            if(e.getKeyCode()==KeyEvent.VK_S||e.getKeyCode()==KeyEvent.VK_DOWN)//Move Down
            {
                if(playy<19)
                {
                    if(map[curfloor][playy+1][playx]!=1&&map[curfloor][playy+1][playx]!=11)
                    {
                        if(Battle(playy+1,playx))
                        {
                            playy++;
                            updateIcons();
                        }
                    }
                }
            }
            if(e.getKeyCode()==KeyEvent.VK_A||e.getKeyCode()==KeyEvent.VK_LEFT)//Move Left
            {
                if(playx>0)
                {
                    if(map[curfloor][playy][playx-1]!=1&&map[curfloor][playy][playx-1]!=11)
                    {
                        if(Battle(playy,playx-1))
                        {
                            playx--;
                            updateIcons();
                        }
                    }
                }
            }
            if(e.getKeyCode()==KeyEvent.VK_D||e.getKeyCode()==KeyEvent.VK_RIGHT)//Move Right
            {
                if(playx<19)
                {
                    if(map[curfloor][playy][playx+1]!=1&&map[curfloor][playy][playx+1]!=11)
                    {
                        if(Battle(playy,playx+1))
                        {
                            playx++;
                            updateIcons();
                        }
                    }
                }
            }
            if(e.getKeyCode()==KeyEvent.VK_E)//View current stats
            {//Only really relevant for final floor battle
                JOptionPane.showMessageDialog(null,"STATS\n\nHP: "+me.getHp()+"\nATK: "+me.getAtk()+
                        "\nDEF: "+me.getDef()+"\nSPD: "+me.getSpd()+"\nLUCK: "+me.getLuck()+"\nEXP: "+me.getExp()+"/100");
            }
        }
        @Override
        public void keyTyped(KeyEvent e){}
        @Override
        public void keyReleased(KeyEvent e){}
    }
}
