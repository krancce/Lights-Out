import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.border.Border;


public class GridButton extends JButton {

    /**
     * The row of the <b>Board</b> on which this cell is.
     */
    private int row;

    /**
     * The column of the <b>Board</b> on which this cell is.
     */
    private int column;


    /**
     * An array is used to cache all the images. Since the images are not
     * modified, all the cells that display the same image reuse the same
     * <b>ImageIcon</b> object. Notice the use of the keyword <b>static</b>.
     */

    private static final ImageIcon[] icons = new ImageIcon[4];
    
    private static final int LIT    = 0;
    private static final int DARK   = 1;
    private static final int LIT_SOL   = 2;
    private static final int DARK_SOL  = 3;

    private int type;

    /**
     * Constructor used for initializing a GridButton at a specific
     * Board location.
     * 
     * @param column
     *            the column of this Cell
     * @param row
     *            the row of this Cell
     */

    public GridButton(int column, int row) {

        this.row = row;
        this.column = column;
        type = DARK;
        setBackground(Color.WHITE);
        setIcon(getImageIcon());
        Border border = BorderFactory.createEmptyBorder(2, 2, 2, 2);
        setBorder(border);
        //setPreferredSize(new Dimension(30,30));
        setBorderPainted(false);
    }

    /**
     * Determine the image to use based on the cell type. Implements a caching mechanism.
     * 
     * @return the image to be displayed by the button
     */

    private ImageIcon getImageIcon() {
    
        if (icons[type] == null) {
            String strId = Integer.toString(type);
            icons[type] = new ImageIcon("Icons/Light-" + strId + ".png");
        }
        return icons[type];
    }

   /**
    * sets the icon of the button to reflect the
    * state specified by the parameters
    * @param isOn true if that location is ON
    * @param isClicked true if that location is
    * tapped in the model's current solution
    */ 
    public void setState(boolean isOn, boolean isClicked) {

        if(isOn) {
            type = (isClicked ? LIT_SOL : LIT);
        } else {
            type = (isClicked ? DARK_SOL : DARK);            
        }
        setIcon(getImageIcon());
    }

 

    /**
     * Getter method for the attribute row.
     * 
     * @return the value of the attribute row
     */

    public int getRow() {
	   return row;
    }

    /**
     * Getter method for the attribute column.
     * 
     * @return the value of the attribute column
     */

    public int getColumn() {
	   return column;
    }

}
