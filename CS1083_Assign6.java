/*
 * Mitchell Golding 3552573
 * CS1083 Assignment 6
 * 5 April 2017
 *
 * Purpose: Launch the game
 * Input: None
 * Output: None
 */
package cs1083_assign6;
import javax.swing.*; import java.awt.*;
public class CS1083_Assign6{

    public static void main(String[] args) {
        JFrame gameframe = new JFrame("Beels\' Labyrinth");
        gameframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon logo = new ImageIcon("Icon.png");
        gameframe.setIconImage(logo.getImage());

        GamePanel FinallyOver = new GamePanel();
        gameframe.getContentPane().add(FinallyOver);

        gameframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gameframe.setUndecorated(true);
        gameframe.setResizable(false);
        gameframe.pack();
        gameframe.setVisible(true);
        
        boolean bossdesuka = false;
        Player go = new Player(0,0,0,0,0);
        while(!bossdesuka)
        {
            if(!FinallyOver.getGame())
            {
                bossdesuka = true;
            }
            go = FinallyOver.getPlayer();
            System.out.print("");//WHY DO I REALLY NEED THIS HERE! It won't work otherwise which has me mad...
        }
        gameframe.removeAll();
        gameframe.setVisible(false);
        gameframe.setEnabled(false);

        ImageIcon loco = new ImageIcon("Icon.png");//It's the same thing, but twice for when I just want to test one part or the other without going through it all
        
        JFrame bossframe = new JFrame();
        bossframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bossframe.setIconImage(loco.getImage());

        BossPanel ShowtimeDa = new BossPanel(go);
        bossframe.getContentPane().add(ShowtimeDa);

        bossframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
        bossframe.setUndecorated(true);
        bossframe.setResizable(false);
        bossframe.pack();
        bossframe.setVisible(true);
        
        
        
    }
    
}
