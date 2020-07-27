import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
//import javax.swing.event.ChangeEvent;
//import javax.swing.event.ChangeListener;

import javax.swing.*;


/**
 * The class <b>GameController</b> is the controller of the game. It is a listener
 * of the view, and has a method <b>play</b> which computes the next
 * step of the game, and  updates model and view.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */


public class GameController implements ActionListener, ItemListener {//ChangeListener, {

    /**
     * Reference to the view of the board
     */
    private GameView gameView;

    /**
     * Reference to the model of the game
     */
    private GameModel gameModel;
 

    /**
     * Constructor used for initializing the controller. It creates the game's view 
     * and the game's model instances
     * 
     * @param width
     *            the width of the board on which the game will be played
     * @param height
     *            the height of the board on which the game will be played
     */
    public GameController(int width, int height) {
        gameModel = new GameModel(width, height);
        gameView = new GameView(gameModel, this);
        update();
    }


    /**
     * Callback used when the user clicks a button (reset, 
     * random or quit)
     *
     * @param e
     *            the ActionEvent
     */

    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() instanceof GridButton) {
            int row = (((GridButton)(e.getSource())).getRow());
            int column = ((GridButton)(e.getSource())).getColumn();
            gameModel.click(row,column);
              
            update();

            if(gameModel.isFinished()) {
                Object[] options = {"Play Again",
                            "Quit"};
                int n = JOptionPane.showOptionDialog(gameView,
                        "Congratulations, you won in " + gameModel.getNumberOfSteps() 
                            +" steps!\n Would you like to play again?",
                        "Won",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);
                if(n == 0){
                    gameModel.reset();
                    update();
                } else{
                    System.exit(0);
                }             
            }   

        } else if (e.getSource() instanceof JButton) {
            JButton clicked = (JButton)(e.getSource());

            if (clicked.getText().equals("Quit")) {
                 System.exit(0);
             } else if (clicked.getText().equals("Reset")){
                gameModel.reset();
             } else if (clicked.getText().equals("Random")){
                gameModel.randomize();
             } 
             update();        
         } 
    }

    /**
     * Callback used when the user select/unselects
     * a checkbox
     *
     * @param e
     *            the ItemEvent
     */

    public void  itemStateChanged(ItemEvent e){
        update();
    }

    private void update(){
        if(gameView.solutionShown()) {
            gameModel.setSolution();
        } 
            
        gameView.update();

    }
}
