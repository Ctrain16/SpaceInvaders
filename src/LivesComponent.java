import java.awt.*;
import javax.swing.*;

/**
 * Responsible for lives displaying
 * @author Cal Trainor
 * @date March 2nd 2019
 */
public class LivesComponent extends JComponent
{
    private ImageIcon image;
    private final int LIVES_Y = 10;

    /**
     * Constructor
     */
    public LivesComponent()
    {
        //imports image and scales to wanted size
        image = new ImageIcon("heart.png");
        Image scaleImage = image.getImage();
        scaleImage = scaleImage.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        image = new ImageIcon(scaleImage);
    }

    /**
     * Draws heart
     * @param Graphics object
     */
    public void draw(Graphics g, int x)
    {
        int counter = 0;
        for(int i = 0; i < x; i++)
        {
            image.paintIcon(this, g, 770 - counter, LIVES_Y);

            counter += 20;
        }

    }
}