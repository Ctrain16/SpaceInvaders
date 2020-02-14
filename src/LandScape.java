import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

/**
 * Sets up components of game and runs it
 * @author Cal Trainor
 * @date March 1st 2019
 */
public class LandScape extends JPanel
{
    private final int UPDATE_DELAY = 15;
    private int counter, lifecount, score, highscore, shipX, spawnRate, totalEnemies, levelCounter, timeCounter;

    private LaserComponent laser;
    private EnemyComponent enemy;
    private SpaceShipComponent spaceShip;
    private LivesComponent life;

    private Font winningFont = new Font("Arial", Font.BOLD, 24);
    private Font defaultFont = new Font("Arial", Font.PLAIN, 16);

    private PrintWriter scoreWriter;
    private File scoreFile = new File("src/HighScoreKeeper.txt");

    private Timer startGame, gameTime;
    private boolean gameStarted,gameOver, mouseClicked, shoot, newHighScore, newLevel;

    private ArrayList<EnemyComponent> enemies = new ArrayList<EnemyComponent>();
    private ArrayList<LaserComponent> shots = new ArrayList<LaserComponent>();

    /**
     * Constructor
     */
    public LandScape() throws FileNotFoundException
    {
        Scanner scan = new Scanner(scoreFile);
        if(scan.hasNext())
        {
            highscore = Integer.parseInt(scan.next());
        }
        scoreWriter = new PrintWriter(scoreFile);

        setFocusable(true);

        KeyListener q = new QuitListener();
        addKeyListener(q);
        MouseListener click = new ClickListener();
        MouseMotionListener moveMouse = new ClickListener();
        addMouseListener(click);
        addMouseMotionListener(moveMouse);

        timeCounter = 0;
        levelCounter = 1;
        lifecount = 5;
        score = 0;
        shipX = 360;
        spawnRate = 200;
        totalEnemies = 0;

        life = new LivesComponent();
        enemy = new EnemyComponent();
        spaceShip = new SpaceShipComponent();
        enemies.add(enemy);

        ActionListener al = new StartGameListener();
        startGame = new Timer(UPDATE_DELAY, al);
        startGame.start();

        gameOver = false;
        gameStarted = false;
        mouseClicked = false;
    }

    /**
     * Sets game in motion
     */
    public void startGame()
    {
        gameStarted = true;

        lifecount = 5;
        shipX = 360;

        ActionListener game = new GameClock();
        gameTime = new Timer(UPDATE_DELAY, game);
        gameTime.start();
    }

    /**
     * Adds new enemy
     */
    public void addEnemy()
    {
        enemy = new EnemyComponent();
        enemies.add(enemy);
        totalEnemies++;
    }

    /**
     * Shoots laser
     */
    public void addLaser()
    {
        int x = spaceShip.getSpaceShipX() + 40;
        int y = spaceShip.getSpaceShipY();
        laser = new LaserComponent(x, y);
        shots.add(laser);
    }

    /**
     * Checks for collisions between lasers and enemies
     * Checks if any enemies got passed ship
     * Checks if any laser have gone off screen
     */
    public void checkForCollisions()
    {
        for(int i = 0; i < shots.size(); i++)
        {
            LaserComponent currentLaser = shots.get(i);
            for(int j = 0; j < enemies.size(); j++)
            {
                EnemyComponent e = enemies.get(j);
                int shotY = currentLaser.getLaserY();
                int shotX = currentLaser.getLaserX();
                int enemyY = e.getEnemyY() + 30;
                int enemyX = e.getEnemyX();

                if(shotY <= enemyY)
                {
                    if(shotX >= enemyX && shotX <= enemyX + 30)
                    {
                        enemies.remove(e);
                        shots.remove(currentLaser);
                        score++;
                        if(score > highscore)
                        {
                            highscore = score;
                            newHighScore = true;
                        }
                    }
                }
            }
        }

        for(int i = 0; i < enemies.size(); i++)
        {
            EnemyComponent e = enemies.get(i);
            if(e.getEnemyY() + 30 > 700)
            {
                lifecount--;
                enemies.remove(e);
            }
        }

        //checks for shots going off the top of the screen and removes them
        for(int i = 0; i < shots.size(); i++)
        {
            LaserComponent l = shots.get(i);
            if(l.getLaserY() < 0)
            {
                shots.remove(l);
            }
        }

        if(lifecount == 0)
        {
            endGame();
        }

        repaint();
    }

    /**
     * Increases enemy spawn rate
     */
    public void increaseDifficulty()
    {
        spawnRate = spawnRate - 20;
        if(spawnRate == 0)
        {
            spawnRate = 20;
        }
    }

    /**
     * Ends game
     */
    public void endGame()
    {
        shots.removeAll(shots);
        enemies.removeAll(enemies);

        totalEnemies = 0;
        spawnRate = 200;
        score = 0;

        gameStarted = false;
        gameOver = true;
        mouseClicked = false;

        gameTime.stop();
        startGame.start();
    }

    /**
     * Paints game
     * @param Graphics object
     */
    public void paintComponent(Graphics g)
    {
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());


        g.setColor(Color.white);
        g.drawString("Score: " + score,5, 20);


        //spaceShip.draw(g,spaceShip.getSpaceShipX());
        spaceShip.draw(g, shipX);
        life.draw(g, lifecount);

        for(EnemyComponent currentEnemy : enemies)
        {
            currentEnemy.draw(g);
        }

        for(LaserComponent currentLaser : shots)
        {
            currentLaser.draw(g);
        }

        if(newLevel)
        {
            g.setColor(Color.white);
            g.setFont(winningFont);
            g.drawString("Level " + levelCounter, 360, 230);

            timeCounter++;
            if(timeCounter % 30 == 0 && timeCounter != 0)
            {
                newLevel = false;
            }
        }
        if(gameOver)
        {
            g.setColor(Color.white);

            if(newHighScore)
            {
                g.setFont(winningFont);
                g.drawString("NEW HIGH SCORE!!!", 285, 230);
            }
            g.setFont(defaultFont);
            g.drawString("Game Over :(", 355, 270);
            g.drawString("Click to Play Again", 335, 300);
            g.drawString("Q to quit", 372, 330);
        }
        else if(!gameStarted)
        {
            g.setColor(Color.white);
            g.setFont(defaultFont);
            g.drawString("Click to Start", 360, 250);
            g.drawString("High Score: " + highscore, 358, 270);
            g.drawString("Controls", 375, 330);
            g.drawString("Use mouse to move", 340, 350);
            g.drawString("Click to shoot", 363, 370);
        }
    }

    private class QuitListener extends KeyAdapter
    {
        /**
         * Listens for user input to endgame
         * @param KeyEvent
         */
        public void keyPressed (KeyEvent event)
        {
            char key = event.getKeyChar();
            if(gameOver)
            {
                if(key == 'q')
                {
                    scoreWriter.println(highscore);
                    scoreWriter.close();
                    System.exit(0);
                }
            }

            repaint();
        }
    }

    private class GameClock implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            for(EnemyComponent currentEnemy : enemies)
            {
                currentEnemy.moveEnemyY();
            }

            for(LaserComponent currentLaser : shots)
            {
                currentLaser.moveLaser();
            }

            if(shoot)
            {
                addLaser();
                shoot = false;
            }

            counter++;
            if(counter % spawnRate == 0)
            {
                addEnemy();
            }

            checkForCollisions();

            if(totalEnemies % 10 == 0 && totalEnemies != 0)
            {
                increaseDifficulty();
                totalEnemies++;
                newLevel = true;
                levelCounter++;
            }

            repaint();
        }
    }

    private class StartGameListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            if(mouseClicked)
            {
                gameOver = false;
                startGame.stop();
                mouseClicked = false;
                startGame();
                newLevel = true;
            }
            repaint();
        }
    }

    private class ClickListener extends MouseAdapter
    {
        public void mousePressed(MouseEvent event)
        {
            mouseClicked = true;
            if(gameStarted)
            {
                shoot = true;
            }
        }

        /**
         * Moves ship
         * @param MouseEvent
         */
        public void mouseMoved(MouseEvent event)
        {
            if(gameStarted)
            {
                shipX = event.getX() - 40;
            }
        }
    }

}