import java.awt.Point;
import java.util.ArrayList;

// Jihoon Jeon and Chris Jeng
public class Board {

    // Our representation of the board, where myBoard[0][0] represents
    // the bottom left dot.
    private Dot[][] myBoard;
    // Total number of moves allowed for a single game session.
    private static int movesAllowed = 5;

    // DO NOT MODIFY
    public static final int MINSIZE = 4;
    public static final int MAXSIZE = 10;

    // Our instance variables:
    private int movesUsed; // Keep track of how many moves used this game
    private int selectedColor; // is -1 if no color is selected
    private boolean isSelected[][];
    private boolean isRemoved[][];
    private int[][] orderCounter; // Keeps track of selection order
    private int orderCount;
    private int score;

    /**
     * Sets up the board's data and starts up the GUI. N is side length of the
     * board. (Number of dots per side) N will default to 0 if a non-integer is
     * entered as an argument. If there are no arguments, N will default to 10;
     */
    public static void main(String[] args) {
        int n = 0;
        try {
            n = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            n = 0;
        } catch (IndexOutOfBoundsException e) {
            // This line is run if no command line arguments are given.
            // If you wish to modify this line to test, remember to change it
            // back to n = 10;
            n = 10;
        }
        GUI.initGUI(n);
    }

    /**
     * When the New Game button is clicked, a new randomized board is
     * constructed. Sets up the board with input SIZE, such that the board's
     * side length is SIZE. Note: The Board is always a square, so SIZE is both
     * the length and the width. Generate a random board such that each entry in
     * board is a random color. (represented by an int). Should print and error
     * and System.exit if the size is not within the MINSIZE and MAXSIZE. This
     * constructor will only be called once per game session. Initialize any
     * variables if needed here.
     */
    public Board(int size) throws IllegalArgumentException {
        if (size < MINSIZE || size > MAXSIZE) {
            // We have an error!
            throw new IllegalArgumentException("Size " + size + " invalid! ");
        }
        this.initializeMe(size);
    }

    /**
     * This constructor takes in a 2D int array of colors and generates a preset
     * board with each dot matching the color of the corresponding entry in the
     * int[][] arguement. This constructor can be used for predetermined tests.
     * You may assume that the input is valid (between MINSIZE and MAXSIZE etc.)
     * since this is for your own testing.
     */
    public Board(int[][] preset) {
        this.initializeMe(preset.length);
        myBoard = new Dot[preset.length][preset.length];
        for (int i = 0; i < preset.length; i++)
            for (int j = 0; j < preset.length; j++)
                myBoard[i][j] = new Dot(preset[i][j]);
    }

    // Initializes our custom instance variables
    // Can also call this to reset the board
    private void initializeMe(int size) {
        isSelected = new boolean[size][size];
        isRemoved = new boolean[size][size];
        selectedColor = -1;
        movesUsed = 0;
        orderCounter = new int[size][size];
        orderCount = 0;
        score = 0;

        // Make the actual board:
        myBoard = new Dot[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                myBoard[i][j] = new Dot();
    }

//    public void printOrder(String s) {
//        System.out.println(s);
//        for (int j = orderCounter.length - 1; j >= 0; j--) {
//            for (int i = 0; i < orderCounter.length; i++)
//                System.out.print(orderCounter[i][j] + " ");
//            System.out.println();
//        }
//    }
//
//    public void printRemoval(String s) {
//        System.out.println(s);
//        for (int j = orderCounter.length - 1; j >= 0; j--) {
//            for (int i = 0; i < orderCounter.length; i++)
//                if (isRemoved[i][j])
//                    System.out.print("1 ");
//                else
//                    System.out.print("0 ");
//            System.out.println();
//        }
//    }

    /**
     * Returns the array representation of the board. (Data is used by GUI).
     */
    public Dot[][] getBoard() {
        return myBoard;
    }

    /**
     * Returns the number of moves allowed per game. This value should not
     * change during a game session.
     */
    public static int getMovesAllowed() {
        return movesAllowed;
    }

    /**
     * Change the number of moves allowed per game. This method can be used for
     * testing.
     */
    public static void setMovesAllowed(int n) {
        movesAllowed = n;
    }

    /** Returns the number of moves left. */
    public int getMovesLeft() {
        return movesAllowed - movesUsed;
    }

    /**
     * Return whether or not it is possible to make a Move. (ie, there exists
     * two adjacent dots of the same color.) If false, the GUI will report a
     * game over.
     */
    public boolean canMakeMove() {
        // Will first check outer rows except corners, then checks middle.
        // Declare first, and initialize as we need them.
        Dot myDot;
        Dot leftDot;
        Dot rightDot;
        Dot topDot;
        Dot botDot;

        // Check bottom rows, but not corners
        for (int i = 1; i < myBoard.length - 1; i++) {
            myDot = myBoard[i][0];
            rightDot = myBoard[i + 1][0];
            leftDot = myBoard[i - 1][0];
            if (myDot.isSameColor(rightDot) || myDot.isSameColor(leftDot))
                return true;
        }
        // Check top
        for (int i = 1; i < myBoard.length - 1; i++) {
            myDot = myBoard[i][myBoard.length - 1];
            rightDot = myBoard[i + 1][myBoard.length - 1];
            leftDot = myBoard[i - 1][myBoard.length - 1];
            if (myDot.isSameColor(rightDot) || myDot.isSameColor(leftDot))
                return true;
        }

        // Check left
        for (int i = 1; i < myBoard.length - 1; i++) {
            myDot = myBoard[0][i];
            topDot = myBoard[0][i + 1];
            botDot = myBoard[0][i - 1];
            if (myDot.isSameColor(topDot) || myDot.isSameColor(botDot))
                return true;
        }

        // Check right
        for (int i = 1; i < myBoard.length - 1; i++) {
            myDot = myBoard[myBoard.length - 1][i];
            topDot = myBoard[myBoard.length - 1][i + 1];
            botDot = myBoard[myBoard.length - 1][i - 1];
            if (myDot.isSameColor(topDot) || myDot.isSameColor(botDot))
                return true;
        }

        // Test the inner square (square minus perimeter) and
        // check each dot to see if they're next to a same color dot.
        // If any are, then the user can make at least one move.
        for (int i = 1; i < myBoard.length - 1; i++)
            for (int j = 1; j < myBoard.length - 1; j++) {
                myDot = myBoard[i][j];
                leftDot = myBoard[i - 1][j];
                rightDot = myBoard[i + 1][j];
                topDot = myBoard[i][j + 1];
                botDot = myBoard[i][j - 1];
                if (myDot.isSameColor(leftDot) || myDot.isSameColor(rightDot)
                        || myDot.isSameColor(topDot)
                        || myDot.isSameColor(botDot))
                    return true;
            }
        // If the code reaches here, the checks were unfruitful.
        return false;
    }

    /**
     * Returns if the board is in a state of game over. The game is over if
     * there are no possible moves left or if the player has used up the maximum
     * allowed moves.
     */
    public boolean isGameOver() {
        return !canMakeMove() || getMovesLeft() == 0;
    }

    /**
     * Returns whether or not you are allowed to select a dot at X, Y at the
     * moment. Remember, if the game is over, you cannot select any dots.
     */
    public boolean canSelect(int x, int y) throws IllegalArgumentException {
        // First check for valid (x,y) position
        if (x < 0 || y < 0 || x > myBoard.length || y > myBoard.length) {
            // We have an error!
            System.err.println("xy coordinate " + x + "," + y
                    + ") invalid! Threw exception! ");
            throw new IllegalArgumentException("XY pair not allowed.");
        }
        if (!isGameOver()) {
            // Only proceed if the game is not yet over
            if (isSelected[x][y]) {
                return false;
            } else if (selectedColor == -1)
                return true;
            else {
                // Check if it is touching a same color, selected dot.
                // The selected dot must be the last selected one.
                if (x > 0)
                    // Check left
                    if (myBoard[x][y].isSameColor(myBoard[x - 1][y])
                            && isSelected[x - 1][y]
                            && orderCounter[x - 1][y] == orderCount)
                        return true;
                if (x < myBoard.length - 1)
                    // Check right
                    if (myBoard[x][y].isSameColor(myBoard[x + 1][y])
                            && isSelected[x + 1][y]
                            && orderCounter[x + 1][y] == orderCount)
                        return true;
                if (y > 0)
                    // Check bottom
                    if (myBoard[x][y].isSameColor(myBoard[x][y - 1])
                            && isSelected[x][y - 1]
                            && orderCounter[x][y - 1] == orderCount)
                        return true;
                if (y < myBoard.length - 1)
                    // Check top
                    if (myBoard[x][y].isSameColor(myBoard[x][y + 1])
                            && isSelected[x][y + 1]
                            && orderCounter[x][y + 1] == orderCount)
                        return true;
                return false;
            }
        } else
            return false;
    }

    /**
     * Is called when a dot located at myBoard[X][Y] is selected on the GUI.
     */
    public void selectDot(int x, int y) {
        if (canSelect(x, y)) {
            selectedColor = myBoard[x][y].getColor();
            isSelected[x][y] = true;
            orderCounter[x][y] = ++orderCount;
        }
        else
            System.out.println("Cannot select (" + x + "," + y + "). ");
    }

    /**
     * Checks if you are allowed to deselect the chosen point. Assumes at least
     * one point has been selected already. You can only deselect the most
     * recent point you have selected. (You can select 3 dots and deselect them
     * in reverse order.)
     */
    public boolean canDeselect(int x, int y) {
        if (!isGameOver()) {
            boolean isSelect = isSelected[x][y];
            boolean isLastSelected = orderCounter[x][y] == orderCount;
            return isSelect && isLastSelected;
        }
        return false;
    }

    /** Is called when a dot located at myBoard[X][Y] is deselected on the GUI. */
    public void deselectDot(int x, int y) {
        if (canDeselect(x, y)) {
            isSelected[x][y] = false;
            if (this.numberSelected() == 0)
                selectedColor = -1;
            orderCounter[x][y] = 0;
            orderCount--;
        } else
            System.out.println("Cannot deselect (" + x + "," + y + "). ");
    }

    /** Returns the number of currently selected dots */
    public int numberSelected() {
        int count = 0;
        for (int i = 0; i < myBoard.length; i++)
            for (int j = 0; j < myBoard.length; j++)
                if (isSelected[i][j])
                    count++;
        return count;
    }

    /**
     * Is called when the "Remove" button is clicked. Puts all selected dots in
     * a "removed" state. If no dots should be removed, throw a
     * CantRemoveException. You must also create your own Exception Class named
     * CantRemoveException. If selected dots form a closed shape, remove all
     * dots on the board that have the same color as the selected dots.
     */
    public void removeSelectedDots() throws CantRemoveException {
        if (!isGameOver()) {
            if (numberSelected() < 2)
                throw new CantRemoveException("Not enough selected dots! ");
            for (int i = 0; i < myBoard.length; i++)
                for (int j = 0; j < myBoard.length; j++)
                    if (isSelected[i][j])
                        isRemoved[i][j] = true;
            if (isClosedShape())
                removeSameColor();
            dropRemainingDots();
            fillRemovedDots();
        }
    }

    /**
     * Puts the dot at X, Y in a removed state. Later all dots above a removed
     * dot will drop.
     */
//    public void removeSingleDot(int x, int y) {
//        // OPTIONAL
//        isRemoved[x][y] = true;
//        orderCounter[x][y] = 0;
//        orderCount--;
//    }

    /**
     * Return whether or not the selected dots form a closed shape. Refer to
     * diagram for what a closed shape looks like.
     */
    public boolean isClosedShape() {
        int high = 0;
        int x = -1;
        int y = -1;
        // Go through and find the highest one to find last selected dot
        for (int i = 0; i < orderCounter.length; i++)
            for (int j = 0; j < orderCounter.length; j++)
                if (orderCounter[i][j] > high) {
                    high = orderCounter[i][j];
                    x = i;
                    y = j;
                }
        if (high == 0)
            return false;
        else {
            int numTouching = 0;
            if (!isSelected[x][y])
                System.out
                        .println("Error in isClosedShape()! The last selected dot is not actually selected! "); // #Precaution
            if (x > 0)
                // Check left
                if (isSelected[x - 1][y])
                    numTouching++;
            if (x < myBoard.length - 1)
                // Check right
                if (isSelected[x + 1][y])
                    numTouching++;
            if (y > 0)
                // Check bottom
                if (isSelected[x][y - 1])
                    numTouching++;
            if (y < myBoard.length - 1)
                // Check top
                if (isSelected[x][y + 1])
                    numTouching++;
            return numTouching >= 2;
        }
    }

    /**
     * Removes all dots of the same color of the dots on the currently selected
     * dots. Assume it is confirmed that a closed shape has been formed from the
     * selected dots.
     */
    public void removeSameColor() {
        // OPTIONAL

        // Currently only marks to remove, doesn't actually execute remove
        for (int i = 0; i < orderCounter.length; i++)
            for (int j = 0; j < orderCounter.length; j++)
                if (myBoard[i][j].isColor(selectedColor)) {
                    isRemoved[i][j] = true;
                    // isSelected[i][j] = true;
                }
    }

    /**
     * Once the dots are removed. Rearrange the board to simulate the dropping
     * of all of the dots above the removed dots. Refer to diagram in the spec
     * for clarity. After dropping the dots, there should exist some "bad" dots
     * at the top. (These are the blank dots on the 4-stage diagram.)
     * fillRemovedDots will be called immediately after this by the GUI so that
     * random dots replace these bad dots with new ones that have a randomly
     * generated color.
     */
    public void dropRemainingDots() {
        // Actually executes the drop.
        // Drops and removes each dot, incrementing the score accordingly.
        int[] dropCounts = new int[myBoard.length];
        boolean didSomething = false;
        for (int j = 0; j < myBoard.length; j++) {
            boolean redoJ = false;
            for (int i = 0; i < myBoard.length; i++) {
                if (isRemoved[i][j]) {
                    score++;
                    dropCounts[i]++;
                    didSomething = true;
                    if (j == myBoard.length - 1) {
                        myBoard[i][j] = null;
                        isRemoved[i][j] = false;
                        redoJ = false;
                    } else {
                        for (int k = j; k < myBoard.length - 1; k++) {
                            myBoard[i][k] = myBoard[i][k + 1];
                            isRemoved[i][k] = isRemoved[i][k + 1];
                        }
                        // Fill the top or less than top row with null
                        int top = myBoard.length - dropCounts[i];
                        myBoard[i][top] = null;
                        isRemoved[i][top] = false;
                        if (isRemoved[i][j])
                            redoJ = true;

                    }

                }
            }
            if (redoJ)
                j--;
        }
        if (didSomething) {
            orderCount = 0;
            selectedColor = -1;
            movesUsed++;
            orderCounter = new int[myBoard.length][myBoard.length];
            isSelected = new boolean[myBoard.length][myBoard.length];
            fillRemovedDots();
        }
    }

    /**
     * After removing all dots that were meant to be removed, replace any
     * removed dot with a new dot of a random color.
     */
    public void fillRemovedDots() {
        for (int i = 0; i < myBoard.length; i++)
            for (int j = 0; j < myBoard.length; j++)
                if (myBoard[i][j] == null)
                    myBoard[i][j] = new Dot();
    }

    /**
     * Return the current score, which is called by the GUI when it needs to
     * update the display of the score. Remember to update the score in your
     * other methods.
     */
    public int getScore() {
        return score;
    }

    /**
     * Search the board for a sequence of 4 consecutive points which form a
     * square such that out of all possible 2x2 squares, selecting this one
     * yields the most points.
     */
    public ArrayList<Point> findBestSquare() {
        // Check for the bottom-left dot of a square set.
        // Only check the square minus the top and right row.
        // If statements are nested to improve efficiency
        ArrayList<Point> ans = new ArrayList<Point>();;
        int bestPoint = 0;
        for (int j = 0; j < myBoard.length - 1; j++)
            for (int i = 0; i < myBoard.length - 1; i++) {
                int currColor = myBoard[i][j].getColor();
                int rightColor = myBoard[i + 1][j].getColor();
                if (currColor == rightColor) {
                    int topColor = myBoard[i][j + 1].getColor();
                    if (topColor == currColor) {
                        int topRightColor = myBoard[i + 1][j + 1].getColor();
                        if (topRightColor == currColor) {   
                            int currPoint = 0;
                            for (int m = 0; m < myBoard.length; m++)
                                for (int n = 0; n < myBoard.length; n++)
                                    if (myBoard[m][n].getColor() == currColor)
                                        currPoint++;
                            if (currPoint > bestPoint) {
                                bestPoint = currPoint;
                                ans = new ArrayList<Point>();
                                Point p1 = new Point(i, j);
                                // selectDot(i, j);
                                Point p2 = new Point(i + 1, j);
                                // selectDot(i + 1, j);
                                Point p3 = new Point(i + 1, j + 1);
                                // selectDot(i, j + 1);
                                Point p4 = new Point(i, j + 1);
                                // selectDot(i + 1, j + 1);
                                ans.add(p1);
                                ans.add(p2);
                                ans.add(p3);
                                ans.add(p4);
                            }
                        }
                    }

                }

            }
        if (bestPoint == 0)
            return null;
        else
            return ans;
    }

    /** Prints the the board any way you like for testing purposes. */
    public void printBoard() {
        // OPTIONAL
        // Prints the board in an intuitive way, 
        // similar to the way the .pdf prints. 
        for (int j = orderCounter.length - 1; j >= 0; j--) {
            for (int i = 0; i < orderCounter.length; i++)
                System.out.print(myBoard[i][j].getColor() + " ");
            System.out.println();
        }
    }

    public void printBoard(String msg) {
        System.out.println(msg);
        printBoard();
    }

    @SuppressWarnings("serial")
    public static class CantRemoveException extends Exception {
        public CantRemoveException() {
            super();
        }

        public CantRemoveException(String s) {
            super(s);
        }
    }

}
