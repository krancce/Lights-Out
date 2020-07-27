import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * The class <b>GameView</b> provides the current view of the entire Game. It extends
 * <b>JFrame</b> and lays out a matrix of <b>GridButton</b> (the actual game) and 
 * two instances of JButton. The action listener for the buttons is the controller.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */

public class GameView extends JFrame {


    /**
     * The board is a two dimensionnal array of GridButton instances
     */
    private GridButton[][] board;

 
    /**
     * Reference to the model of the game
     */
    private GameModel  gameModel;
 

    /**
     * Reference to the JLabel label on which the current number of steps is written
     */
    private JLabel nbreOfStepsLabel;

    private JCheckBox buttonCBSolution;
    /**
     * Constructor used for initializing the Frame
     * 
     * @param gameModel
     *            the model of the game (already initialized)
     * @param gameController
     *            the controller
     */

    public GameView(GameModel gameModel, GameController gameController) {
        super("Lights Out -- the ITI 1121 version");

        this.gameModel = gameModel;
 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();        
       
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints pc = new GridBagConstraints();        
        
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        board = new GridButton[gameModel.getWidth()][gameModel.getHeight()];

        for (int j = 0; j < gameModel.getHeight(); j++) {
            for (int i = 0; i < gameModel.getWidth(); i++) {
                board[i][j] = new GridButton(i,j);
                board[i][j].addActionListener(gameController);
                pc.gridx = i;
                pc.gridy = j;
                panel.add(board[i][j],pc);
            }
        }
        c.gridx = 0;
        c.gridy = 0;

    	add(panel, c);

        JButton buttonReset = new JButton("Reset");
        buttonReset.setFocusPainted(false);
        buttonReset.addActionListener(gameController);

        JButton buttonRandom = new JButton("Random");
        buttonRandom.setFocusPainted(false);
        buttonRandom.addActionListener(gameController);

        buttonCBSolution = new JCheckBox("Solution");        
        buttonCBSolution.setSelected(false);
        buttonCBSolution.addItemListener(gameController);

        JButton buttonExit = new JButton("Quit");
        buttonExit.setFocusPainted(false);
        buttonExit.addActionListener(gameController);


  
        JPanel control = new JPanel();
        control.setBackground(Color.WHITE);
        control.setLayout(new GridBagLayout());
        GridBagConstraints cc = new GridBagConstraints();        


        cc.weightx=.5;
        cc.gridx = 0;
        cc.gridy = 0;
        control.add(buttonReset,cc);
        cc.weightx=.5;
        cc.gridx = 0;
        cc.gridy = 1;
        control.add(buttonRandom,cc);
        cc.weightx=.5;
        cc.gridx = 0;
        cc.gridy = 2;
        control.add(buttonCBSolution,cc);
        cc.weightx=.5;
        cc.gridx = 0;
        cc.gridy = 3;
        control.add(buttonExit,cc);
        c.gridx = 1;
        c.gridy = 0;
       
        add(control,c);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 10, 5));

        nbreOfStepsLabel = new JLabel();
        bottomPanel.add(nbreOfStepsLabel);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        add(bottomPanel, c);

        getContentPane().setBackground(Color.WHITE);

    	pack();
    	setVisible(true);

    }

    /**
     * updates the status of the board's GridButton instances based 
     * on the current game model, then redraws the view
     */

    public void update(){
        for(int i = 0; i < gameModel.getWidth(); i++){
            for(int j = 0; j < gameModel.getHeight(); j++){
                board[i][j].setState(gameModel.isON(j,i), solutionShown() ? gameModel.solutionSelects(j,i) : false);
            }
        }
        if(gameModel.getNumberOfSteps() > 1) {
            nbreOfStepsLabel.setText("Number of steps: " + gameModel.getNumberOfSteps());
        } else {
            nbreOfStepsLabel.setText("Number of step: " + gameModel.getNumberOfSteps());
        }
        repaint();
    }

    /**
     * returns true if the ``solution'' checkbox
     * is checked
     *
     * @return the status of the ``solution'' checkbox
     */

    public boolean solutionShown(){
        return buttonCBSolution.isSelected();
    }

}
