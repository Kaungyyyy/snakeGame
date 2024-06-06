import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int screenWidth = 600;
    static final int screenHeight = 600;
    static final int unitSize = 25;
    static final int gameUnits = (screenWidth * screenHeight) / unitSize;
    static final int delay = 75; //determines how slow the game will run
    final int[] x = new int[gameUnits]; // holding the x coordinates for the body parts of the snake
    final int[] y = new int[gameUnits]; // holding the y coordinates for the body parts of the snake
    int bodyParts = 6;
    int applesEaten = 0;
    int appleX; // x coordinate of apple's location
    int appleY;// y coordinate of apple's location
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;


    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new myKeyAdapter());
        startGame();
    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            g.setColor(Color.red);
            g.fillRect(appleX, appleY, unitSize, unitSize);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.blue);
                    g.fillRect(x[i], y[i], unitSize, unitSize);
                } else {
                    g.setColor(Color.green);
                    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i], unitSize, unitSize);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Kai", Font.BOLD, 50));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten,(screenWidth-metrics.stringWidth("Score: "+applesEaten)) / 2, g.getFont().getSize()); // shows score
        }
        else {
            gameOver(g);
        }
    }

    public void newApple() {
        appleX = random.nextInt((int) (screenWidth / unitSize)) * unitSize;
        appleY = random.nextInt((int) (screenHeight / unitSize)) * unitSize;

    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) { // shifting body parts of snake
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U': //upwards
                y[0] = y[0] - unitSize;
                break;
            case 'D': // downwards
                y[0] = y[0] + unitSize;
                break;
            case 'L':
                x[0] = x[0] - unitSize;
                break;
            case 'R':
                x[0] = x[0] + unitSize;


        }

    }

    public void checkApple() {
        if((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }

    }

    public void checkCollisions() {
        //checks if head collided with body
        for (int i = bodyParts; i > 0; i--) {
            if ((x[0] == x[i] && y[0] == y[i])) {
                running = false;
            }
            //checks if head touches left border
            if (x[0] < 0) {
                running = false;
            }
            //checks if head touches right border
            if (x[0] > screenWidth) {
                running = false;
            }
            // checks if head touches top border
            if (y[0] < 0) {
                running = false;
            }
            //checks if head touches bottom border
            if (y[0] > screenHeight) {
                running = false;
            }
            if (!running) {
                timer.stop();
            }
        }
    }

    public void gameOver(Graphics g) {
    //Game Over text
        g.setColor(Color.red);
        g.setFont(new Font("Kai", Font.BOLD, 90));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over",(screenWidth-metrics.stringWidth("Game Over")) / 2, screenHeight / 2); // "game over at centre of screen"

        g.setColor(Color.red);
        g.setFont(new Font("Kai", Font.BOLD, 50));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten,(screenWidth-metrics1.stringWidth("Score: "+applesEaten)) / 2, g.getFont().getSize());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();


    }

    public class myKeyAdapter extends KeyAdapter { // inner class
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }

    }
}