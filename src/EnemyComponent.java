import javax.swing.*;
import java.awt.*;

/**
 * Responsible for enemy ships
 * @author Cal Trainor
 * @date March 1st 2019
 */
public class EnemyComponent extends JComponent
{
    private ImageIcon image;
    private int enemyY, enemyX;

    /**
     * Constructor
     */
    public EnemyComponent()
    {
        //imports image and scales to wanted size
        image = new ImageIcon("Enemy.png");
        Image scaleImage = image.getImage();
        scaleImage = scaleImage.getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        image = new ImageIcon(scaleImage);

        enemyY = (int)(Math.random() * -100) - 50;
        enemyX = (int)(Math.random() * 730) + 20;
    }

    /**
     * Draws enemy ship
     * @param Graphics object
     */
    public void draw(Graphics g)
    {
        image.paintIcon(this, g, enemyX, enemyY);
    }

    /**
     * Moves enemy
     */
    public void moveEnemyY()
    {
        enemyY += 2;
    }

    /**
     * Accessors for enemyY and enemyX
     */
    public int getEnemyY()
    {
        return enemyY;
    }
    public int getEnemyX()
    {
        return enemyX;
    }
}
