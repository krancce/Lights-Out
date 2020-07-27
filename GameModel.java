import java.util.Random;

public class GameModel {

    /**
     * The number of column of the game.
     */
    private  int widthOfGame;
 
    /**
     * The number of lines of the game.
     */
    private  int heightOfGame;
 
    /**
     * A 2 dimentionnal array of widthOfGame*heightOfGame 
     * recording the state of the game. FALSE is OFF.
     */
    private boolean[][] model;


   /**
     * The number of steps played since the last reset
     */
    private int numberOfSteps;
 
   /**
     * The number of dots currenly out (grey)
     */
    private int numberOut;


   /**
     * Random generator
     */
    private Random generator;

   /** 
     * Reference to a possible solution for the board
     */
    private Solution solution;

    /**
     * Constructor to initialize the model to a given size of board.
     * 
     * @param width
     *            the width of the board
     * 
     * @param height
     *            the height of the board
     * 
     */
    public GameModel(int width, int height) {
        
        widthOfGame = width;
        heightOfGame = height;
        // default boolean value = false == out
        model = new boolean[heightOfGame][widthOfGame]; 
        numberOut = widthOfGame*heightOfGame;
        numberOfSteps = 0;
        solution = null;
    }



    /**
     * Getter method for the height of the game
     * 
     * @return the value of the attribute heightOfGame
     */   
    public int getHeight(){
        return heightOfGame;
    }

    /**
     * Getter method for the width of the game
     * 
     * @return the value of the attribute widthOfGame
     */   
    public int getWidth(){
        return widthOfGame;
    }


  /**
     * returns true if the dot  at location (i,j) in the model is lit, false otherwise
     * 
     * @param i
     *            the row of the dot in the model
     * @param j
     *            the column of the dot in the model
     * @return the status of the dot at location (i,j)in the model
     */   
    public boolean isON(int i, int j){
        if(i < 0 || i >= heightOfGame) {
            throw new IllegalArgumentException("Error, wrong row: " + i);
        }
        if(j < 0 || j >= widthOfGame) {
            throw new IllegalArgumentException("Error, wrong column: " + j);
        } 
        return model[i][j];
    }

    /**
     * Resets the model to (re)start a game. The previous game (if there is one)
     * is cleared up . 
     */
    public void reset(){

        for(int i = 0; i < heightOfGame; i++){
            for(int j = 0; j < widthOfGame; j++){
                model[i][j] = false;
            }
        }
        numberOut = widthOfGame*heightOfGame;
        numberOfSteps = 0;
    }


  /**
     * Sets the location (i,j) to value
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @param value 
     *      the value of the dot at location (i,j)
     */   
    public void set(int i, int j, boolean value){
        if(i < 0 || i >= widthOfGame) {
            throw new IllegalArgumentException("Error, wrong width: " + i);
        }
        if(j < 0 || j >= heightOfGame) {
            throw new IllegalArgumentException("Error, wrong height: " + j);
        } 
        model[j][i] = value;
    }


   /**
     * Builds a String representation of the model
     *
     * @return String representation of the model
     */
    public String toString(){
        StringBuffer b = new StringBuffer();
        for(int j = 0; j < heightOfGame; j++){
            b.append("[");
            for(int i = 0; i < widthOfGame; i++){
                if (i>0) {
                    b.append(",\t");
                }
                b.append(model[j][i]);
            }
            b.append("]\n");
        }
        return b.toString();
    }

    /**
     * Restarts the game with a solveable random 
     * board instead of a clear board. 
     */
    public void randomize(){

      for(int i = 0; i < heightOfGame; i++){
            for(int j = 0; j < widthOfGame; j++){
                model[i][j] = true;
            }
        }

        numberOut = 0;

        if(generator == null) {
            generator = new Random();
        }

        for(int i = 0; i < heightOfGame*widthOfGame; i++){
            click(generator.nextInt(heightOfGame),
                  generator.nextInt(widthOfGame));
        }


        numberOfSteps = 0;

        // we don't want the "random" game to be
        // a blank one nor a solved one (unless
        // it is a 1x1 game)
        if((heightOfGame*widthOfGame>1) 
            && (numberOut==0 
                || numberOut == heightOfGame*widthOfGame)){
            randomize();
        }

    }

   /**
     * Sets the status of the dot at location (i,j) to clicked
     * and update the neighborhood accordingly
     * 
     * @param i
     *            the row of the dot in the model
     * @param j
     *            the column of the dot in the model
     */   
    public void click(int i, int j){
        
        
        toggle(i,j);
        if(i>0) {
            toggle(i-1,j);            
        }
        if(j>0) {
            toggle(i,j-1);            
        }
        if(i< heightOfGame -1) {
            toggle(i+1,j);            
        }
        if(j< widthOfGame -1) {
            toggle(i,j+1);            
        }

        numberOfSteps++;
    }

 

    private void toggle(int i, int j){        
        numberOut = (model[i][j])?(numberOut+1):(numberOut-1);
        model[i][j] = !model[i][j];
    }

    /**
     * Getter method for the current number of steps
     * 
     * @return the current number of steps
     */   
    public int getNumberOfSteps(){
        return numberOfSteps;
    }
  
   /**
     * The method <b>isFinished</b> returns true iff the game is finished, that
     * is, the board is all ON (the model is "true" everywhere)
     *
     * @return true if the game is finished, false otherwise
     */
    public boolean isFinished(){
        return numberOut == 0;
    }

   /**
    * computes a shortest solution for the current game
    * and records it in the variable solution
    */ 
    public void setSolution(){

        solution = LightsOut.solveShortest(this);
    }

   /**
     * Returns true if there is a current solution in the model
     * and that solution selects the dot at location (i,j) 
     * 
     * @param i
     *            the row of the dot in the model
     * @param j
     *            the column of the dot in the model
     * @return true if solution != null and solution.get(j,i)==true
     *
     */   
    public boolean solutionSelects(int i, int j){
        
        if(solution == null) {
            return false;
        } else {
            return solution.get(j,i);
        }
    }

}
