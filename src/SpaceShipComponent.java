import javax.swing.*;
import java.awt.*;

/**
 * Responsible for users spaceship
 * @author Cal Trainor
 * @date March 1st 2019
 */
public class SpaceShipComponent extends JComponent
{
    private final int SPACESHIP_Y = 600;
    private int spaceShipX = 360;

    private ImageIcon image;

    /**
     * Constructor
     */
    public SpaceShipComponent()
    {
        //imports image and scales to wanted size
        image = new ImageIcon("SpaceShip.jpeg");
        Image scaleImage = image.getImage();
        scaleImage = scaleImage.getScaledInstance(80, 60, Image.SCALE_DEFAULT);
        image = new ImageIcon(scaleImage);
    }

    /**
     * Draws user spaceship
     * @param Graphics object
     */
    public void draw(Graphics g, int x)
    {
        spaceShipX = x;
        if(spaceShipX > 720)
        {
            spaceShipX = 720;
        }
        image.paintIcon(this, g, spaceShipX, SPACESHIP_Y);
    }

    /**
     * Accessor for spaceShipX
     * @return spaceShipX
     */
    public int getSpaceShipX()
    {
        return spaceShipX;
    }

    /**
     * Accessor for SPACESHIP_Y
     * @return SPACESHIP_Y
     */
    public int getSpaceShipY()
    {
        return SPACESHIP_Y;
    }
}
