public class Dot {

    // DO NOT MODIFY
    public static final int COLOR_BLUE = 1;
    public static final int COLOR_RED = 2;
    public static final int COLOR_GREEN = 3;
    public static final int COLOR_YELLOW = 4;
    public static final int COLOR_PURPLE = 5;
    public static final int NUM_COLORS = 5;

    private int myColor;

    /**
     * Generates a dot with a random color attribute. Note: There is a variable
     * defined as NUM_COLORS which should be used for generating random colors
     * (ints). You random number generator should return an integer from 1 to
     * NUM_COLORS inclusive, (not 1 - 5).
     */
    public Dot() {
        myColor = (int) (Math.floor(Math.random() * NUM_COLORS) + 1);
    }

    /** Generates a dot with an input color. */
    public Dot(int color) {
        if (color < 1 || color > NUM_COLORS) {
            // System.err.println("Invalid request color: " + color
            // + "! Creating blue color instead. ");
            throw new IllegalArgumentException("Input color " + color
                    + " invalid! ");
        } else
            myColor = color;
    }

    /** Returns the integer representation of a dot's color (myColor). */
    public int getColor() {
        return myColor;
    }

    /** Returns whether or not this dot is the same color as otherDot. */
    public boolean isSameColor(Dot otherDot) {
        if (otherDot == null)
            return false;
        else
            return this.myColor == otherDot.myColor;
    }

    /**
     * Returns whether or not this dot is the same color as the argument, which
     * is also an integer representation.
     */
    public boolean isColor(int color) {
        return this.myColor == color;
    }

    public static void main(String args[]) {
        System.out.println("No main method here! ");
    }

}
