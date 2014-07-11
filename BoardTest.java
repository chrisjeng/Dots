//import Board.CantRemoveException;
import junit.framework.TestCase;

import java.awt.Point;
import java.util.ArrayList;

public class BoardTest extends TestCase {

    Dot blue = new Dot(1);
    Dot red = new Dot(2);
    Dot green = new Dot(3);
    Dot yellow = new Dot(4);
    Dot purple = new Dot(5);

    // These will be used for quicker writing of test code
    int[][] sameRows = { { 1, 1, 1, 1 }, { 2, 2, 2, 2 }, { 3, 3, 3, 3 },
            { 4, 4, 4, 4 } };

    int[][] diffRows = { { 1, 2, 1, 2 }, { 1, 3, 1, 3 }, { 1, 4, 1, 4 },
            { 2, 3, 4, 5 } };

    int[][] noMoves = { { 1, 2, 3, 4 }, { 2, 3, 4, 5 }, { 3, 4, 5, 1 },
            { 4, 5, 1, 2 } };

    int[][] canClose = { { 3, 3, 4, 2 }, { 1, 4, 4, 5 }, { 2, 4, 4, 1 },
            { 3, 3, 5, 2 } };

    public void testConstructor1() {
        Board three = new Board(4);
        assertNotNull(three);
        try {
            Board badBoard = new Board(3);
            assertNotNull(badBoard);
        } catch (IllegalArgumentException e) {
            System.out.println("Small board exception successfully caught! ");
        }
        try {
            Board badBoard = new Board(11);
            assertNotNull(badBoard);
        } catch (IllegalArgumentException e) {
            System.out.println("Large board exception successfully caught! ");
        }

        // Make sure the dots are random
        Board rand1 = new Board(10);
        Board rand2 = new Board(10);
        boolean allAreSame = true;
        for (int i = 0; i < 10; i++)
            if (allAreSame)
                for (int j = 0; j < 10; j++) {
                    int col1 = rand1.getBoard()[i][j].getColor();
                    int col2 = rand2.getBoard()[i][j].getColor();
                    if (col1 != col2) {
                        allAreSame = false;
                        break;
                    }
                }
        assertFalse(allAreSame);
    }

    public void testConstructor2() {
        int[][] sampleArray = new int[5][5];
        for (int i = 0; i < sampleArray.length; i++)
            for (int j = 0; j < sampleArray.length; j++)
                sampleArray[i][j] = i + 1;

        Board three = new Board(sampleArray);
        assertNotNull(three);
    }

    public void testGetMovesLeft() {
        Board one = new Board(5);
        // Check start
        assertTrue(one.getMovesLeft() == 5);

        Board two = new Board(sameRows);
        two.selectDot(0, 0);
        two.selectDot(0, 1);
        two.selectDot(0, 2);
        try {
            two.removeSelectedDots();
        } catch (Board.CantRemoveException e) {
            System.out.println(e);
        }
        assertTrue(two.getMovesLeft() == 4);

        // Check for removes all the way to zero
        try {
            two.selectDot(1, 0);
            two.selectDot(1, 1);
            two.removeSelectedDots();
            assertTrue(two.getMovesLeft() == 3);
            two.selectDot(2, 0);
            two.selectDot(2, 1);
            two.removeSelectedDots();
            assertTrue(two.getMovesLeft() == 2);
            two.selectDot(1, 0);
            two.selectDot(1, 1);
            two.removeSelectedDots();
            assertTrue(two.getMovesLeft() == 1);
            two.selectDot(3, 0);
            two.selectDot(3, 1);
            two.removeSelectedDots();
        } catch (Board.CantRemoveException e) {
            e.printStackTrace();
        }
        assertTrue(two.getMovesLeft() == 0);
    }

    public void testGetScore() {
        // Test score starts at zero
        Board hello = new Board(5);
        assertTrue(hello.getScore() == 0);

        // Test score increments correctly
        Board two = new Board(sameRows);
        two.selectDot(0, 0);
        two.selectDot(0, 1);
        two.selectDot(0, 2);
        try {
            two.removeSelectedDots();
            two.selectDot(2, 0);
            two.selectDot(2, 1);
            two.removeSelectedDots();
        } catch (Board.CantRemoveException e) {
            System.out.println(e);
        }
        assertTrue(two.getScore() == 5);

        // Test square score increments correctly
        Board bigPoints = new Board(canClose);
        bigPoints.selectDot(1, 1);
        bigPoints.selectDot(1, 2);
        bigPoints.selectDot(2, 2);
        bigPoints.selectDot(2, 1);
        try {
            two.removeSelectedDots();
        } catch (Board.CantRemoveException e) {
            System.out.println(e);
        }
        assertTrue(two.getScore() == 5);
    }

    public void testGetBoard() {
        Board one = new Board(sameRows);
        assertEquals(one.getBoard()[0][0].getColor(), blue.getColor());

        // Test all colors are consistent
        int[][] sampleArray = new int[5][5];
        for (int i = 0; i < sampleArray.length; i++)
            for (int j = 0; j < sampleArray.length; j++)
                sampleArray[i][j] = i + 1;
        Board myBo = new Board(sampleArray);
        Dot[][] checkMe = myBo.getBoard();
        for (int i = 0; i < sampleArray.length; i++)
            for (int j = 0; j < sampleArray.length; j++)
                assertTrue(checkMe[i][j].getColor() == i + 1);
    }

    public void testIsGameOver() {
        Board one = new Board(5);
        Board.setMovesAllowed(0);
        assertTrue(one.isGameOver());

        // Test to see if reaching zero naturally gives game over
        Board.setMovesAllowed(2);
        Board two = new Board(sameRows);
        two.selectDot(0, 0);
        two.selectDot(0, 1);
        two.selectDot(0, 2);
        try {
            two.removeSelectedDots();
            two.selectDot(2, 0);
            two.selectDot(2, 1);
            two.removeSelectedDots();
        } catch (Board.CantRemoveException e) {
            System.out.println(e);
        }
        assertTrue(two.isGameOver());

        Board three = new Board(noMoves);
        assertTrue(three.isGameOver());
        Board.setMovesAllowed(5);
    }

    public void testCanDeselect() {
        Board one = new Board(sameRows);
        // Test deselect when nothing is selected
        assertFalse(one.canDeselect(0, 0));
        one.selectDot(0, 0);
        // Test deselect when one is selected
        assertTrue(one.canDeselect(0, 0));

        Board two = new Board(sameRows);
        two.selectDot(0, 0);
        two.selectDot(0, 1);
        // Test deselect on non-last of multiple selected dots
        assertFalse(two.canDeselect(0, 0));
        // Test deselect on unrelated dot
        assertFalse(two.canDeselect(0, 2));
        // Test deselect on last of multiple selected dots
        assertTrue(two.canDeselect(0, 1));
    }

    public void testDeselectDot() {
        Board one = new Board(sameRows);
        one.selectDot(0, 0);
        one.deselectDot(0, 0);
        assertTrue(one.numberSelected() == 0);

        Board two = new Board(sameRows);
        two.selectDot(0, 0);
        two.selectDot(0, 1);
        System.out.println("The following two lines indicate bad deselect: ");
        two.deselectDot(0, 0);
        two.deselectDot(0, 2);
        System.out.println(two.numberSelected());
        assertTrue(two.numberSelected() == 2);
        two.deselectDot(0, 1);
        assertTrue(two.numberSelected() == 1);
        two.deselectDot(0, 0);
        assertTrue(two.numberSelected() == 0);
    }
    
    // #ChrisDone

    public void testCanMakeMove() {
        Board one = new Board(sameRows);
        assertTrue(one.canMakeMove());

        Board two = new Board(noMoves);
        assertTrue(!two.canMakeMove());
    }

    public void testCanSelect() {
        Board one = new Board(sameRows);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertTrue(one.canSelect(i, j));
            }
        }

        Board two = new Board(sameRows);
        two.selectDot(0, 0);
        assertTrue(two.canSelect(0, 1));
        assertTrue(!two.canSelect(1, 0));
    }

    public void testSelectDot() {
        Board one = new Board(sameRows);
        one.selectDot(0, 0);

    }

    public void testRemoveSelectedDots() {
        // Should not cause error.
        Board one = new Board(sameRows);
        one.selectDot(0, 0);
        one.selectDot(0, 1);
        one.selectDot(0, 2);
        try {
            one.removeSelectedDots();
        } catch (Board.CantRemoveException e) {
            System.out.println(e);
        }

        // Should not cause error.
        Board two = new Board(sameRows);
        two.selectDot(0, 0);
        two.selectDot(0, 1);
        try {
            two.removeSelectedDots();
        } catch (Board.CantRemoveException e) {
            System.out.println(e);
        }

        // Should remove all same colors.
        Board three = new Board(canClose);
        ArrayList<Point> squareArray = three.findBestSquare();
        for (int i = 0; i < squareArray.size(); i++) {
            Point currPoint = squareArray.get(i);
            int x = currPoint.x;
            int y = currPoint.y;
            three.selectDot(x, y);
        }
        try {
            three.removeSelectedDots();
        } catch (Board.CantRemoveException e) {
            System.out.println(e);
        }
        assertEquals(three.getBoard()[0][2].getColor(), red.getColor());
        assertEquals(three.getBoard()[1][1].getColor(), purple.getColor());
        assertEquals(three.getBoard()[2][1].getColor(), blue.getColor());
    }

    public void testDropRemainingDots() {
        Board one = new Board(diffRows);
        one.selectDot(0, 0);
        one.selectDot(1, 0);
        one.selectDot(2, 0);
        try {
            one.removeSelectedDots();
        } catch (Board.CantRemoveException e) {
            System.out.println(e);
        }
        assertEquals(one.getBoard()[0][0].getColor(), red.getColor());
        assertEquals(one.getBoard()[1][0].getColor(), green.getColor());
        assertEquals(one.getBoard()[2][0].getColor(), yellow.getColor());
        assertEquals(one.getBoard()[0][1].getColor(), blue.getColor());
        assertEquals(one.getBoard()[1][1].getColor(), blue.getColor());
        assertEquals(one.getBoard()[2][1].getColor(), blue.getColor());
        assertEquals(one.getBoard()[3][0].getColor(), red.getColor());

        Board two = new Board(sameRows);
        two.selectDot(0, 0);
        two.selectDot(0, 1);
        two.selectDot(0, 2);
        try {
            two.removeSelectedDots();
        } catch (Board.CantRemoveException e) {
            System.out.println(e);
        }
        assertEquals(two.getBoard()[0][0].getColor(), blue.getColor());
        assertEquals(two.getBoard()[1][2].getColor(), red.getColor());
        assertEquals(two.getBoard()[1][3].getColor(), red.getColor());
        assertEquals(two.getBoard()[2][2].getColor(), green.getColor());
        assertEquals(two.getBoard()[2][3].getColor(), green.getColor());
        assertEquals(two.getBoard()[3][2].getColor(), yellow.getColor());
        assertEquals(two.getBoard()[3][3].getColor(), yellow.getColor());

        Board three = new Board(canClose);
        three.selectDot(1, 1);
        three.selectDot(2, 1);
        three.selectDot(2, 2);
        three.selectDot(1, 2);
        three.selectDot(0, 2);
        try {
            three.removeSelectedDots();
        } catch (Board.CantRemoveException e) {
            System.out.println(e);
        }
        assertEquals(three.getBoard()[1][1].getColor(), purple.getColor());
        assertEquals(three.getBoard()[2][1].getColor(), blue.getColor());
        assertEquals(three.getBoard()[0][2].getColor(), red.getColor());
    }

    public void testFillRemovedDots() {
        Board one = new Board(diffRows);
        one.selectDot(0, 0);
        one.selectDot(1, 0);
        one.selectDot(2, 0);
        try {
            one.removeSelectedDots();
        } catch (Board.CantRemoveException e) {
            System.out.println(e);
        }
        assertNotNull(one.getBoard()[0][3]);
        assertNotNull(one.getBoard()[1][3]);
        assertNotNull(one.getBoard()[2][3]);
    }

    public void testFindBestSquare() {
        Board one = new Board(canClose);
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 1);
        Point p3 = new Point(2, 2);
        Point p4 = new Point(1, 2);
        assertEquals(one.findBestSquare().get(0), p1);
        assertEquals(one.findBestSquare().get(1), p2);
        assertEquals(one.findBestSquare().get(2), p3);
        assertEquals(one.findBestSquare().get(3), p4);
    }

    public void testNumberSelected() {
        Board one = new Board(sameRows);
        one.selectDot(0, 0);
        one.selectDot(0, 1);
        assertTrue(one.numberSelected() == 2);

        Board two = new Board(noMoves);
        assertTrue(two.numberSelected() == 0);
    }

    public void testIsClosedShape() {
        Board one = new Board(diffRows);
        one.selectDot(0, 0);
        one.selectDot(1, 0);
        one.selectDot(2, 0);
        assertTrue(!one.isClosedShape());

        Board two = new Board(canClose);
        two.selectDot(1, 1);
        two.selectDot(2, 1);
        two.selectDot(2, 2);
        two.selectDot(1, 2);
        two.selectDot(0, 2);
        assertTrue(!two.isClosedShape());

        Board three = new Board(canClose);
        three.selectDot(0, 2);
        three.selectDot(1, 2);
        three.selectDot(2, 2);
        three.selectDot(2, 1);
        three.selectDot(1, 1);
        assertTrue(three.isClosedShape());
    }
}
