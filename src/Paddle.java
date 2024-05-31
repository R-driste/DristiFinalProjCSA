import java.awt.*; //Rectangle functions
import java.awt.event.*; //Key events
/*
The paddle class extends Rectangle and
acts like a moving table hockey paddle by
sliding across screen. According to the team
the paddle belongs to, the class can respond to
different keys the user presses/releases to move.

@author Dristi Roy
 */
public class Paddle extends Rectangle {

    int id; //team
    int xVelocity; //xdirection movement, doesn't move y
    final int speed = 10; //how much to multiple xdir by

    //game dimensions import
    int gameW;
    int gameH;
    
    //reflected paddle
    SecondPaddle s;

    /* Constructor of the Paddle class. Initializes position of paddle,
    dimensions of paddle, and paddle team in id. Also creates reflective
    paddle with SecondPaddle class. Rectangle class fed dims to its own
    coordinates, width, and height

    @param x Initial x position of paddle
    @param y Initial y position of paddle
    @param pw Paddle Height px
    @param ph Paddle Width px
    @param id Team 1 or 2 (Blue or Red)
    @param gw Width of game screen w
    @param gh Height of game screen h
     */
    public Paddle(int x, int y, int pw, int ph, int id, int gw, int gh) {
        super(x, y, pw, ph);
        this.id = id;
        gameW = gw;
        gameH = gh;
        s = new SecondPaddle(gameW - x - pw, gameH - y - ph, width, height, id);
    }

    /* keyPressed method of Paddle class
    checks which team the paddle is on, and
    listens for corresponding key press events
    paddle 1 : press A is go left, press D is go right
    paddle 2 : press <- arrow is go left, -> press arrow is go right
    @param e KeyEvent that is fed from listener in game panel with information about what keys were pressed
     */
    public void keyPressed(KeyEvent e) {
        System.out.println("PRESSED");
        switch (id) {
            case 1:
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    setXDirection(-speed);
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    setXDirection(speed);
                }
                break;
            case 2:
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    setXDirection(-speed);
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    setXDirection(speed);
                }
                break;
        }
    }

    /* keyReleased method of Paddle class
    checks which team the paddle is on, and
    listens for corresponding key release events
    paddle 1 : release A is stop left, release D is stop right
    paddle 2 : release <- arrow is stop left, release -> arrow is stop right
    @param e KeyEvent that is fed from listener in game panel with information about what keys were released
     */
    public void keyReleased(KeyEvent e) {
        System.out.println("RELEASED");
        switch (id) {
            case 1:
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    setXDirection(0);
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    setXDirection(0);
                }
                break;
            case 2:
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    setXDirection(0);
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    setXDirection(0);
                }
                break;
        }
    }

    /*
    Setter function for x direction of paddle
    @param XDirection the px to move across x axis
     */
    public void setXDirection(int XDirection) {
        xVelocity = XDirection;
    }
    /* The Paddle move method updates the primary
    and secondary paddle positions by incrementing/
    decrementing by xVelocity
     */
    public void move() {
        x = x + xVelocity;
        s.x = s.x - xVelocity;
    }

    /* The Paddle draw method checks what team
    the paddle belongs to and fills the paddle as a
    rectangle with the correct color.

    @param g Graphics object which paint passes to save changes to
     */
    public void draw(Graphics g) {
        if (id == 1) {
            g.setColor(Color.blue);
        } else {
            g.setColor(Color.red);
        }
        g.fillRect(x, y, width, height);
        s.draw(g);
    }

    /* The SecondPaddle class is a rectangle that
    is inside the primary Paddle class. Its responsibility
    is to mirror the movements of the primary paddle on the
    opponents side of the screen at the top.

    @author Dristi Roy
     */
    public class SecondPaddle extends Rectangle {
        int team;

        public SecondPaddle(int x, int y, int w, int h, int i) {
            super(x, y, w, h);
            team = i;
        }

        /* The SecondPaddle draw method rewrites the
        Paddle draw method since second paddle doesn't
        extend the first

        @param g Graphics object which paint passes to save changes to
        */
        public void draw(Graphics g) {
            if (id == 1) {
                g.setColor(Color.blue);
            } else {
                g.setColor(Color.red);
            }
            g.fillRect(super.x, super.y, super.width,super.height);
        }
    }
}