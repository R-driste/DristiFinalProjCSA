import java.util.*; //Random
import java.awt.*; //Rectangle functions
/* The puck class mimics the puck on an
ice hockey table by displaying and moving an oval
across the game screen which the users can
hit with their paddles.

@author Dristi Roy
 */
public class Puck extends Rectangle{
    Random random;
    int xVelocity;
    int yVelocity;
    int initialSpeed = 2;
    int gameW;
    int gameH;
    SecondPuck p;
    public Puck(int x, int y, int diameter, int w, int h){
        super(x,y,diameter,diameter);
        random = new Random();
        int randomXDirection = random.nextInt(2);
        int randomYDirection = random.nextInt(2);
        if (randomXDirection == 0) {
            randomXDirection--;
        }
        if (randomYDirection == 0) {
            randomYDirection--;
        }
        setXDirection(initialSpeed*randomXDirection);
        setYDirection(initialSpeed*randomYDirection);
        gameW = w;
        gameH = h;
        int sX = gameW - (x + diameter); // Corrected the x position
        int sY = gameH - (y + diameter); // Corrected the y position
        p = new SecondPuck(sX, sY, diameter, diameter);
    }

    /*
    Setter function for x direction of puck
    @param randomXDirection the px to move across x axis
     */
    public void setXDirection(int randomXDirection){
        xVelocity = randomXDirection;
    }

    /*
    Setter function for y direction of puck
    @param randomYDirection the px to move across x axis
     */
    public void setYDirection(int randomYDirection){
        yVelocity = randomYDirection;
    }

    /* The Puck move method updates the primary
    and secondary puck positions by incrementing/
    decrementing by xVelocity and yVelocity
     */
    public void move(){
        x+=xVelocity;
        y+=yVelocity;
        p.x -= xVelocity;
        p.y -= yVelocity;
    }

    /* The Puck draw method fills the rectangle
    as a white oval.

    @param g Graphics object which paint passes to save changes to
     */
    public void draw(Graphics g){
        g.setColor(Color.darkGray);
        g.fillOval(x,y,width,height);
        p.draw(g);
    }

    /* The SecondPuck class is a rectangle that
    is inside the primary Puck class. Its responsibility
    is to mirror the movements of the primary puck on the
    opponents side of the screen at the top.

    @author Dristi Roy
     */
    public class SecondPuck extends Rectangle{
        public SecondPuck(int x, int y, int w, int h){
            super(x,y,w,h);
        }
        /* The SecondPuck draw method rewrites the
        Puck draw method since second puck doesn't
        extend the first

        @param g Graphics object which paint passes to save changes to
        */
        public void draw(Graphics g) {
            g.setColor(Color.darkGray);
            g.fillOval(super.x, super.y, super.width,super.height);
        }

    }
}
