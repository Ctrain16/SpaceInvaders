import javax.swing.*;
import java.awt.*;

/**
 * Responsible for lasers fired from ship
 * @author Cal Trainor
 * @date March 1st 2019
 */
public class LaserComponent extends JComponent
{
    private int x1, x2, y1, y2;

    /**
     * Constructor
     */
    public LaserComponent(int x, int y)
    {
        x1 = x;
        y1 = y;
        x2 = x1;
        y2 = y1 - 20;
    }

    /**
     * Draws Laser
     * @param Graphics object
     */
    public void draw(Graphics g)
    {
        g.setColor(Color.green);
        g.drawLine(x1, y1, x2, y2);
    }

    /**
     * Moves laser
     */
    public void moveLaser()
    {
        y1 = y1 - 3;
        y2 = y1 - 20;
    }

    /**
     * Accessors for y2 and x1
     */
    public int getLaserY()
    {
        return y2;
    }
    public int getLaserX()
    {
        return x1;
    }
}
