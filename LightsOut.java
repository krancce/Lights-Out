import java.util.ArrayList;


/**
 * The class <b>LightsOut</b> launches the game
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */
public class LightsOut {


     /**
     * default width of the game.
     */
    public static final int DEFAULT_WIDTH = 10;
     /**
     * default height of the game.
     */
    public static final int DEFAULT_HEIGTH = 8;

    /**
     * The method <b>solve</b> finds all the 
     * solutions to the <b>Lights Out</b> game 
     * for an initially completely ``off'' board 
     * of size <b>widthxheight</b>, using a  
     * Breadth-First Search algorithm. 
     *
     * It returns an <b>ArrayList&lt;Solution&gt;</b> 
     * containing all the valid solutions to the 
     * problem.
     *
     * This version does not continue exploring a 
     * partial solution that is known to be
     * impossible. It will also attempt to complete
     * a solution as soon as possible
     *
     * During the computation of the solution, the 
     * method prints out a message each time a new 
     * solution  is found, along with the total time 
     * it took (in milliseconds) to find that solution.
     *
     * @param width
     *  the width of the board
     * @param height
     *  the height of the board
     * @return
     *  an instance of <b>ArrayList&lt;Solution&gt;</b>
     * containing all the solutions
     */
    public static ArrayList<Solution> solve(int width, int height){
        return solve(new GameModel(width,height));
    }


  /**
     * The method <b>solve</b> finds all the 
     * solutions to the <b>Lights Out</b> game 
     * for the game on the state specified by 
     * the instance of GameModel passed on as
     * parameter,  using a  
     * Breadth-First Search algorithm. 
     *
     * It returns an <b>ArrayList&lt;Solution&gt;</b> 
     * containing all the valid solutions to the 
     * problem.
     *
     * This version does not continue exploring a 
     * partial solution that is known to be
     * impossible. It will also attempt to complete
     * a solution as soon as possible
     *
     * During the computation of the solution, the 
     * method prints out a message each time a new 
     * solution  is found, along with the total time 
     * it took (in milliseconds) to find that solution.
     *
     * @param model
     *  reference to the GameModel instance
     * @return
     *  an instance of <b>ArrayList&lt;Solution&gt;</b>
     * containing all the solutions
     */
    public static ArrayList<Solution> solve(GameModel model){

 
        if(model == null) {
            throw new NullPointerException("model can't be null");
        }

        
        Queue<Solution>  q = new QueueImplementation<Solution>();
        ArrayList<Solution> solutions  = new ArrayList<Solution>();

        q.enqueue(new Solution(model.getWidth(),model.getHeight()));
        long start = System.currentTimeMillis();
        while(!q.isEmpty()){
            Solution s  = q.dequeue();
            if(s.isReady()){
                // by construction, it is successfull
                System.out.println("Solution found in " + (System.currentTimeMillis()-start) + " ms" );
                solutions.add(s);
            } else {
                boolean withTrue = s.stillPossible(true,model);
                boolean withFalse = s.stillPossible(false,model);
                if(withTrue && withFalse) {
                    Solution s2 = new Solution(s);
                    s.setNext(true);
                    q.enqueue(s);
                    s2.setNext(false);
                    q.enqueue(s2);
                } else if (withTrue) {
                    s.setNext(true);
                    if(s.finish(model)){
                        q.enqueue(s);
                    }                
                } else if (withFalse) {
                    s.setNext(false);
                    if( s.finish(model)){
                        q.enqueue(s); 
                    }               
                }
            }
        }
        return solutions;
    }


  /**
     * The method <b>solveShortest</b> finds 
     * amnd returns (one of) the shortest 
     * solutions to the <b>Lights Out</b> game 
     * for the game on the state specified by 
     * the instance of GameModel passed on as
     * parameter,  using a  
     * Breadth-First Search algorithm. 
     *
     * If no solution exist, it returns null.
     * If there are several solutions of the same
     * size, any one of them can be returned.
     *
     *
     * @param model
     *  reference to the GameModel instance
     * @return
     *  the Shortest solution, null if there is none
     */
    public static Solution solveShortest(GameModel model){

        if(model == null) {
            throw new NullPointerException("model can't be null");
        }

        ArrayList<Solution> results = solve(model);
        if(results.size() == 0){
            return null;
        }

        Solution res = results.get(0);
        for(int i =1; i < results.size(); i++){
            if(results.get(i).getSize() < res.getSize()){
                res = results.get(i);
            }
        }
        return res;
    }
   /**
     * <b>main</b> of the application. Creates the instance of  GameController 
     * and starts the game. If two parameters width and height
     * are passed, they are used. 
     * Otherwise, a default value is used. Defaults values are also
     * used if the paramters are too small (less than 1).
     * 
     * @param args
     *            command line parameters
     */
     public static void main(String[] args) {
        int width   = DEFAULT_WIDTH;
        int height  = DEFAULT_HEIGTH;
 
        StudentInfo.display();

        if (args.length == 2) {
            try{
                width = Integer.parseInt(args[0]);
                if(width<1){
                    System.out.println("Invalid argument, using default...");
                    width = DEFAULT_WIDTH;
                }
                height = Integer.parseInt(args[1]);
                if(height<1){
                    System.out.println("Invalid argument, using default...");
                    height = DEFAULT_HEIGTH;
                }
            } catch(NumberFormatException e){
                System.out.println("Invalid argument, using default...");
                width   = DEFAULT_WIDTH;
                height  = DEFAULT_HEIGTH;
            }
        }
        GameController game = new GameController(width, height);
    }


}
