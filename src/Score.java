import java.util.*; //ArrayList
import java.awt.*; //Rectangle functions

/* The Score class stores the number of matches a user
has won, as well as the points they currently have. It displays
these points on the screen.

@author Dristi Roy
 */
public class Score extends Rectangle{
    static ArrayList<String> history = new ArrayList<String>();
    static ArrayList<ArrayList<Integer>> scorehist = new ArrayList<>();
    static int p1wins;
    static int p2wins;
     static int gameW;
     static int gameH;
    int player1;
    int player2;

    boolean isPaused = false;
    int lastPoint = 0;
    public Score(int gw, int gh){
        gameW = gw;
        gameH = gh;
    }

    /*The draw method creates the outlines of the
    hockey table as well as displays the points earned
    as numbers at the top

    @param g Graphics object to save visual changes to
     */
    public void draw(Graphics g){
        g.setColor(Color.red);
        g.drawLine(0,gameH/2,gameW/2,gameH/2);
        g.drawLine(gameW,gameH/2,gameW/2,gameH/2);
        g.drawOval(gameW/6,gameH/2 - gameW/12, gameW/6, gameW/6);
        g.drawOval((gameW * 4)/6,gameH/2 - gameW/12, gameW/6, gameW/6);

        g.setColor(Color.white);
        g.setFont(new Font("Helvetica",Font.PLAIN,60));
        g.drawLine(gameW/2,0,gameW/2,gameH);
        g.drawString(String.valueOf(player1/10)+String.valueOf(player1%10),gameW/2-85,50);
        g.drawString(String.valueOf(player2/10)+String.valueOf(player2%10),gameW/2+20, 50);

        if (isPaused && lastPoint==1) {
            g.setColor(Color.black);
            g.setFont(new Font("Helvetica", Font.PLAIN, 60));
            String message = player1 >= 5 ? "Player 1 Won!" : "Player 2 Won!";
            g.drawString(message, gameW / 2 - 140, gameH / 2 - 20);
        } else if (isPaused && lastPoint==2) {
            g.setColor(Color.black);
            g.setFont(new Font("Helvetica", Font.PLAIN, 60));
            String message = player1 >= 5 ? "Player 2 Won!" : "Player 2 Won!";
            g.drawString(message, gameW / 2 - 140, gameH / 2 - 20);
        }
    }

    /* The checkWin method of the Score class will grant
    users a win once they score 5 points, as well as update
    the score history of the game.
    @return boolean that determines if win was made
     */
    public boolean checkWin(){
        if (player1 >= 5){
            p1wins++;
            scorehist.add(new ArrayList<Integer>(Arrays.asList(player1,player2)));
           isPaused = true;
           lastPoint = 1;
            history.add("Player 1 won!");
            try {
                Thread.sleep(3000); // Sleep for 3 milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace(); // Handle the exception
            }

            isPaused=false;
            newScores();
            return true;
        }
        if (player2 >= 5){
            p2wins++;
            scorehist.add(new ArrayList<Integer>(Arrays.asList(player1,player2)));
            isPaused = true;
            lastPoint = 2;
            history.add("Player 2 won!");
            try {
                Thread.sleep(3000); // Sleep for 3 milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace(); // Handle the exception
            }
            isPaused=false;
            newScores();
            return true;
        }
        return false;
    }

    /* newScores method resets scores to 0

     */
    public void newScores(){
        player1 = 0;
        player2 = 0;
    }
}
