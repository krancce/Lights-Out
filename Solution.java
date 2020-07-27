
/**
 * The class <b>Solution</b> is used
 * to store a (partial) solution to the game
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */
public class Solution {


    /**
     * our board. board[i][j] is true is in this
     * solution, the cell (j,i) is tapped
     */
    private boolean[][] board;

    /**
     *  width of the game
     */
    private int width;

    /**
     * height of the game
     */
    private int height;
    
    /**
     * how far along have we constructed that solution.
     * values range between 0 and height*width-1
     */
    private int currentIndex;

    /**
     * Number of selections on the solution
     */
    private int size;

    /**
     * Constructor. Creates an instance of Solution 
     * for a board of size <b>widthxheight</b>. That 
     * solution does not have any board position
     * value explicitly specified yet. 
     *
     * @param width
     *  the width of the board
     * @param height
     *  the height of the board
     */
    public Solution(int width, int height) {

        this.width = width;
        this.height = height;

        board = new boolean[height][width];
        currentIndex = 0;
        size = 0;
  
    }

   /**
     * Constructor. Creates an instance of Solution 
     * wich is a copy of the instance received
     * as parameter. The copy is a deep-copy, except
     * for the game model, which is shared between
     * the newly created instance and the instance
     * references as parameter
     *
     * @param other
     *  Instance of solution to copy
     */
    public Solution(Solution other) {

        this.width = other.width;
        this.height = other.height;
        this.currentIndex = other.currentIndex;

        board = new boolean[height][width];

        for(int i = 0; i < currentIndex; i++){
            board[i/width][i%width] = other.board[i/width][i%width];
        } 
        size = other.size;

    }


    /**
     * returns <b>true</b> if and only the parameter 
     * <b>other</b> is referencing an instance of a 
     * Solution which is the ``same'' as  this 
     * instance of Solution (its board as the same
     * values and it is completed to the same degree)
     *
     * @param other
     *  referenced object to compare
     */

    public boolean equals(Object other){

        if(other == null) {
            return false;
        }
        if(this.getClass() != other.getClass()) {
            return false;
        }

        Solution otherSolution = (Solution) other;

        if(width != otherSolution.width ||
            height != otherSolution.height ||
            currentIndex != otherSolution.currentIndex) {
            return false;
        }

        for(int i = 0; i < height ; i++){
            for(int j = 0; j < width; j++) {
                if(board[i][j] != otherSolution.board[i][j]){
                    return false;
                }
            }
        }

        return true;

    }


    /** 
    * returns <b>true</b> if the solution 
    * has been entirely specified
    *
    * @return
    * true if the solution is fully specified
    */
    public boolean isReady(){
        return currentIndex == width*height;
    }

    /** 
    * specifies the ``next'' value of the 
    * solution. 
    * The first call to setNext specifies 
    * the value of the board location (1,1), 
    * the second call specifies the value
    *  of the board location (1,2) etc. 
    *
    * If <b>setNext</b> is called more times 
    * than there are positions on the board, 
    * an error message is printed out and the 
    * call is ignored.
    *
    * @param nextValue
    *  the boolean value of the next position
    *  of the solution
    */
    public void setNext(boolean nextValue) {

        if(currentIndex >= width*height) {
            // note: we should really throw an exception here
            // but for backward compatibility with assignment 2
            // we just return false
            System.out.println("Board already full");
            return;
        }
        board[currentIndex/width][currentIndex%width] = nextValue;
        if(nextValue) {
            size++;
        }
        currentIndex++;
    }
    
    /**
    * returns <b>true</b> if the solution is completely 
    * specified and is indeed working, that is, if it 
    * will bring a board of the specified dimensions 
    * from being  entirely ``off'' to being  entirely 
    * ``on''.
    *
    * @return
    *  true if the solution is completely specified
    * and works
    */
    public boolean isSuccessful(){
        if(currentIndex < width*height) {
            System.out.println("Board not finished");
            return false;
        }
        return safeIsSuccessful(null);
    }

     /**
    * returns <b>true</b> if the solution is completely 
    * specified and is indeed working, that is, if it 
    * will bring a board of the specified dimensions 
    * from the state currently in game model to being  
    * entirely  ``on''.
    * @param model reference to the GameModel capturing
    *           the current state of the game
    * @return
    *  true if the solution is completely specified
    * and works
    */
    public boolean isSuccessful(GameModel model){
        if(currentIndex < width*height) {
            System.out.println("Board not finished");
            return false;
        }
        if(model == null){
            throw new NullPointerException("Model is null");
        } 
        if(width != model.getWidth() || height!= model.getHeight()){
            throw new IllegalArgumentException("model does not match board");
        }

        return safeIsSuccessful(model);
    }

    private boolean safeIsSuccessful(GameModel model){
        for(int i = 0; i < height ; i++){
            for(int j = 0; j < width; j++) {
                if(!neighborhoodOK(i,j,model)){
                    return false;
                }
            }
        }
        return true;
    }



   /**
    * this method ensure that adding <b>nextValue</b> at the
    * currentIndex does not make the current solution
    * impossible for an all OFF board. It assumes that 
    * the Solution was
    * built with a series of setNext on which 
    * stillPossible was always true.
    * @param nextValue
    *         The boolean value to add at currentIndex
    * @return true if the board is not known to be
    * impossible (which does not mean that the board
    * is possible!)
    */
    public boolean stillPossible(boolean nextValue) {
        if(currentIndex >= width*height) {
            throw new IllegalStateException("Board already full");
        }
        return safeStillPossible(nextValue, null);
    }

   /**
    * this method ensure that adding <b>nextValue</b> at the
    * currentIndex does not make the current solution
    * impossible for the game in the state passed at parameter. 
    * It assumes that the Solution was
    * built with a series of setNext on which 
    * stillPossible was always true for a game in that state.
    * @param nextValue
    *         The boolean value to add at currentIndex
    * @param model reference to the GameModel capturing
    *           the current state of the game
    * @return true if the board is not known to be
    * impossible (which does not mean that the board
    * is possible!)
    */

    public boolean stillPossible(boolean nextValue, GameModel model) {

        if(currentIndex >= width*height) {
            throw new IllegalStateException("Board already full");
        }
        if(model == null){
            throw new NullPointerException("Model is null");
        } 

        if(width != model.getWidth() || height!= model.getHeight()){

            throw new IllegalArgumentException("model does not match board");
        }

        return safeStillPossible(nextValue, model);

    }

    private boolean safeStillPossible(boolean nextValue, GameModel model) {

        int i = currentIndex/width;
        int j = currentIndex%width;
        boolean before = board[i][j];
        boolean possible = true;

        board[i][j] = nextValue;
        
        if((i > 0) && (!neighborhoodOK(i-1,j,model))){
            possible = false;
        }
        if(possible && (i == (height-1))) {
            if((j > 0) && (!neighborhoodOK(i,j-1,model))){
                possible = false;
            }
            if(possible && (j == (width-1))&& (!neighborhoodOK(i,j,model))){
                possible = false;            
            }
        }
        board[i][j] = before;
        return possible;
    }    
   
    /**
     * checks if board[i][j] will be "ON" given
     * its current state, the state of its neighborhood
     * and the current solution.
     * If the GameModel reference is null, then it is
     * assumed to be all OFF.
     */

    private boolean neighborhoodOK(int i, int j, GameModel model) {
        
        int total = 0;
        if(board[i][j]){
            total++;
        }
        if((i > 0) && (board[i-1][j])) {
            total++;
        }
        if((i < height -1 ) && (board[i+1][j])) {
            total++;
        }
        if((j > 0) && (board[i][j-1])) {
            total++;
        }
        if((j < (width - 1)) && (board[i][j+1])) {
            total++;
        }
        return (model == null || !model.isON(i,j)) ? (total%2)== 1 : (total%2) == 0;                
    }


    /**
    * this method attempts to finish the board for an all OFF board. 
    * It assumes that the Solution was
    * built with a series of setNext on which 
    * stillPossible was always true. It cannot
    * be called if the board can be extended 
    * with both true and false and still be 
    * possible.
    *
    * @return true if the all off board can be finished.
    * the Solution is also completed
    */
    public boolean finish(){
        return safeFinish(null);
    }

    /**
    * this method attempts to finish the board for 
    * for a game in the state passed as Game Model
    * It assumes that the Solution was
    * built with a series of setNext on which 
    * stillPossible was always true for that game. It cannot
    * be called if the board can be extended 
    * with both true and false and still be 
    * possible.
    *
    * @param model reference to the GameModel capturing
    *           the current state of the game
    *
    * @return true if the all off board can be finished.
    * the Solution is also completed
    */
    public boolean finish(GameModel model){

        if(model == null){
            throw new NullPointerException("Model is null");
        } 

        if(width != model.getWidth() || height!= model.getHeight()){

            throw new IllegalArgumentException("model does not match board");
        }
        return safeFinish(model);

    }

    private boolean safeFinish(GameModel model){

        int i = currentIndex/width;
        int j = currentIndex%width;
        
        while(currentIndex < height*width) {
            if(i < height - 1 ) {
                setNext(!neighborhoodOK(i-1,j,model));
                i = currentIndex/width;
                j = currentIndex%width;
            } else { //last row
                if(j == 0){
                    setNext(!neighborhoodOK(i-1,j,model));
                } else {
                   if((height > 1) && neighborhoodOK(i-1,j,model) != neighborhoodOK(i,j-1,model)){
                     return false;
                   }
                   setNext(!neighborhoodOK(i,j-1,model));
                } 
                i = currentIndex/width;
                j = currentIndex%width;
            }
        }
        if(!neighborhoodOK(height-1,width-1,model)){
            return false;
        }
        // here we should return true because we could
        // successfully finish the board. However, as a
        // precaution, if someone called the method on
        // a board that was unfinishable before calling
        // the method, we do a complete check
        
        if(!safeIsSuccessful(model)) {
            System.out.println("Warning, method called incorrectly");
            return false;
        }
       
        return true;

    }


   /**
     * returns the number of selections in the solution
     *
     * @return
     *      the number of selections
     */

    public int getSize() {
        return size;
    }    


   /**
     * Returns the value of position at column i
     * and row j in the solition
     * 
     * @param i
     *            the column of the dot in the model
     * @param j
     *            the row of the dot in the model
     * @return true position (i,j) is tapped in the solution,
     *          false otherwise
     */
    public boolean get(int i, int j){
        if(i < 0 || i >= width) {
            throw new IllegalArgumentException("Wrong parameter i " + i);
        }
        if(j < 0 || j >= height) {
            throw new IllegalArgumentException("Wrong parameter j " + j);
        }
        return board[j][i];
    }
 
   /**
     * returns a string representation of the solution
     *
     * @return
     *      the string representation
     */
    public String toString() {
        StringBuffer out = new StringBuffer();
        out.append("[");
        for(int i = 0; i < height; i++){
            out.append("[");
            for(int j = 0; j < width ; j++) {
                if (j>0) {
                    out.append(",");
                }
                out.append(board[i][j]);
            }
            out.append("]"+(i < height -1 ? ",\n" :""));
        }
        out.append("]");
        return out.toString();
    }

}
