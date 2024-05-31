import java.util.ArrayList; //ArrList
import java.awt.*; //Dimension and Graphics
import java.awt.event.*; //Key events
import javax.swing.*; //JFrame functions

/* The GamePanel class is an extension of the JPanel class which
will contain our custom functions for the game as well as the
game loop in the run method.

@author Dristi Roy
 */
public class GamePanel extends JPanel implements Runnable{
    //game dimensions
    final static int GAME_WIDTH = 800;
    final static int GAME_HEIGHT = (int) (GAME_WIDTH * (6.0/9.0));

    //object dimensions
    final static int PUCK_DIAMETER = 40;
    final static int PADDLE_WIDTH = 100;
    final static int PADDLE_HEIGHT = 30;

    Thread gameThread;
    Image image;
    Graphics graphics;
    Paddle paddle1;
    Paddle paddle2;
    Paddle opp1;
    Paddle opp2;
    Puck puck;
    Score score;

    /* Constructor of the GamePanel class. Creates a
    fresh set of paddles and puck, initializes a visual
    scoreboard, assigns panel key listeners and starts
    game thread.
     */
    public GamePanel(){
        newPaddles();
        newPuck();
        score = new Score(GAME_WIDTH,GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new PaddleListener());
        this.setPreferredSize(new Dimension(GAME_WIDTH,GAME_HEIGHT));
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    ////////////HELPER FUNCTIONS:
    /*
    newPuck function creates new puck by assigning puck variable
    of our panel a newly instantiated puck at the initial position
     */
    public void newPuck(){
        puck = new Puck((GAME_WIDTH/4)-(PUCK_DIAMETER/2),GAME_HEIGHT/2- (PUCK_DIAMETER/2),PUCK_DIAMETER, GAME_WIDTH,GAME_HEIGHT);
    }
    /*
    newPaddles function creates new paddles by paddle1 and paddle2
    variables of our panel to newly instantiated paddles at their
    initial positions
    */
    public void newPaddles(){
        paddle1 = new Paddle(GAME_WIDTH/4 - PADDLE_WIDTH/2,GAME_HEIGHT-PADDLE_HEIGHT, PADDLE_WIDTH,PADDLE_HEIGHT, 1,GAME_WIDTH,GAME_HEIGHT);
        paddle2 = new Paddle((GAME_WIDTH * 3)/4 - PADDLE_WIDTH/2,GAME_HEIGHT-PADDLE_HEIGHT, PADDLE_WIDTH,PADDLE_HEIGHT, 2,GAME_WIDTH,GAME_HEIGHT);

    }
    /*
    printProgress function grabs scoring history from
    score and prints them as necessary. Updates user on
    the last game won, leaderboards so far, and who is
    currently holding the most wins.
    */
    public void printProgress(){
        for (String s : Score.history){
            System.out.println(s);
        }
        for (ArrayList<Integer> irow : Score.scorehist){
            for (int i : irow){
                System.out.print(i + " ");
            }
            System.out.println();
        }
        if (Score.p1wins > Score.p2wins){
            System.out.println("Player 1 has the most wins :)");
        } else if (Score.p2wins > Score.p1wins){
            System.out.println("Player 2 has the most wins :)");
        } else {
            System.out.println("Player 1 and Player 2 have an equal number of wins :)");
        }
    }

    ////////////EVENT LISTENING AND EXECUTING:

    /*The PaddleListener class extends KeyAdapter which holds
    functions responsible for responding to different
    key events. Overrides them to use paddle specific key
    responses.
    
    @author Dristi Roy
     */
    public class PaddleListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }
        @Override
        public void keyReleased(KeyEvent e){
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }
    }

    /* The move method updates the locations of each paddle and
    the puck by changing their coordinates according to their
    x and y velocities.
     */
    public void move(){
        paddle1.move();
        paddle2.move();
        puck.move();
    }

    /*
    checkCollision method determines if multiple collisions occur:
    When puck bounces off of table wall
    When puck hits a paddle
    When puck hits a players goal
     */
    public void checkCollision(){
        if (paddle1.x <= 0){
            paddle1.x=0;
            paddle1.s.x = GAME_WIDTH - PADDLE_WIDTH;
            paddle1.setXDirection(0);
        }
        if (paddle1.x >= GAME_WIDTH/2 - PADDLE_WIDTH){
            paddle1.x = GAME_WIDTH/2 - PADDLE_WIDTH;
            paddle1.s.x = GAME_WIDTH/2;
            paddle1.setXDirection(0);
        }
        if (paddle2.x <= GAME_WIDTH/2){
            paddle2.x=GAME_WIDTH/2;
            paddle2.s.x = GAME_WIDTH/2 - PADDLE_WIDTH;
            paddle2.setXDirection(0);
        }
        if (paddle2.x >= GAME_WIDTH - PADDLE_WIDTH){
            paddle2.x = GAME_WIDTH - PADDLE_WIDTH;
            paddle2.s.x = 0;
            paddle2.setXDirection(0);
        }

        if (puck.x <= 0 || (puck.x >= GAME_WIDTH/2 - PUCK_DIAMETER)){
            puck.setXDirection(-puck.xVelocity);
        }

        if (puck.intersects(paddle1)){
            puck.setYDirection(-puck.yVelocity);
            puck.setXDirection(-puck.xVelocity);
            puck.xVelocity++;
        }
        if (puck.p.intersects(paddle2)){
            puck.setYDirection(-puck.yVelocity);
            puck.setXDirection(-puck.xVelocity);
            puck.xVelocity--;
        }
        if (puck.y<=0){
            score.player1++;
            newPaddles();
            newPuck();
        }
        if (puck.y>=GAME_HEIGHT - PUCK_DIAMETER){
            score.player2++;
            newPaddles();
            newPuck();
        }
    }

    /*
    run function contains the main game loop by
    repeatedly running helper functions and listeners
    in while loop
     */
    public void run(){
        long lastTime = System.nanoTime();
        double amountOfTicks = 60;
        double ns = 1000000000/amountOfTicks;
        double delta = 0;

        while (true){
            long now = System.nanoTime();
            delta += (now - lastTime)/ns;
            lastTime = now;
            if (delta >=1){
                move();
                checkCollision();
                repaint();
                delta--;
                boolean won = score.checkWin();
                if (won){
                    printProgress();
                }
            }
        }
    }

    ////////////GRAPHICS:
    
    /* The paint method overrides and customizes the initial paint
    method of the JPanel class. Every time we update the
    graphics of the game, we must repaint the screen. The
    old repaint method will refer to our new paint method

    @param g Graphics object which repaint passes to paint from JPanel
     */
    @Override
    public void paint(Graphics g){
        //receives graphics of image with w,h dim
        image = createImage(getWidth(),getHeight());
        graphics = image.getGraphics();
        draw(graphics); //send graphics to draw for custom use
        g.drawImage(image, 0, 0, this); //actually draws all draw from below
    }

    /* The draw method is one large method to draw each
    of the individual components of the game.

    @param g Graphics object which paint passes to save changes to
     */
    public void draw(Graphics g){
        paddle1.draw(g);
        paddle2.draw(g);
        puck.draw(g);
        score.draw(g);
    }
}
