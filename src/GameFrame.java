import java.awt.*; //Color
import javax.swing.*; //JFrame functions

/*
GameFrame is an extension of JFrame with a custom constructor to
suit the game type, but utilizing the rest of the JFrame's
capabilities for rendering the game

@author Dristi Roy
 */
public class GameFrame extends JFrame{
    GamePanel panel; //We only need one panel inside our game frame.
    public GameFrame(){
        //instantiate a new GamePanel that is actually a JPanel
        panel = new GamePanel();

        //add it to the frame and customize
        this.add(panel);
        this.setTitle("Table Hockey"); //title
        this.setBackground(Color.lightGray); //bg color

        //our program will close when the user x-es out
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        //display panel
        this.setVisible(true);
        //center panel
        this.setLocationRelativeTo(null);
    }

}
