import javax.swing.*;
import java.io.*;
import java.applet.*;

/**
 * Driver for Space Invaders
 * @author Cal Trainor
 * @date March 1st 2019
 */
public class SpaceInvadersDriver extends Applet
{
    public static void main(String[] args) throws FileNotFoundException
    {
        JFrame frame = new JFrame();
        frame.setTitle("Ctrain Studios Presents : Space Invaders");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 700);
        frame.add(new LandScape());
        frame.setVisible(true);
    }
}
