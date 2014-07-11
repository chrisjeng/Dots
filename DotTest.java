import junit.framework.TestCase;

public class DotTest extends TestCase {
    public void testConstructor() {
        Dot myDot = new Dot();
        assertNotNull(myDot);
        int dotColor = myDot.getColor();
        assertTrue(dotColor >= 1 && dotColor <= 5);
    }

    public void testConstructor2() {
        // Test all NUM_COLORS colors:
        for (int i = 1; i <= Dot.NUM_COLORS; i++) {
            Dot myDot = new Dot(i);
            assertTrue(myDot.getColor() == i);
        }

        // Try giving a bad dot color (i.e. negative color):
        System.out.println("Should print err msg below: ");
        try {
            Dot badDot = new Dot(-1);
            assertTrue(badDot.getColor() == Dot.COLOR_BLUE);
        } catch (Exception e) {
        }
    }

    // This method is the same as testConstructor2()
    // because both use getColor()
    public void testGetColor() {

        // Test all NUM_COLORS colors:
        for (int i = 1; i <= Dot.NUM_COLORS; i++) {
            Dot myDot = new Dot(i);
            assertTrue(myDot.getColor() == i);
        }

        // Try giving a bad dot color (i.e. negative color):
        System.out.println("Should print err msg below: ");
        try {
            Dot badDot = new Dot(-1);
            assertTrue(badDot.getColor() == Dot.COLOR_BLUE);
        } catch (Exception e) {
        }
    }

    // Will test every single possible case,
    // since there are only 5^2 = 25 cases.
    public void testIsSameColor() {
        for (int i = 1; i <= Dot.NUM_COLORS; i++) {
            for (int j = 1; j <= Dot.NUM_COLORS; j++) {
                Dot myDot = new Dot(i);
                Dot otherDot = new Dot(j);
                if (i == j)
                    assertTrue(myDot.isSameColor(otherDot));
                else
                    assertFalse(myDot.isSameColor(otherDot));
            }
        }
    }

    // Will test every single possible case,
    // since there are only 5^2 = 25 cases.
    public void testIsColor() {
        for (int i = 1; i <= Dot.NUM_COLORS; i++) {
            for (int j = 1; j <= Dot.NUM_COLORS; j++) {
                Dot myDot = new Dot(i);
                if (i == j)
                    assertTrue(myDot.isColor(j));
                else
                    assertFalse(myDot.isColor(j));
            }
        }
    }
}
