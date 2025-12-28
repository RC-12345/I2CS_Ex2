import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class Index2DTest {
    @Test
    void testConstructorAndGetters() {
        Index2D p = new Index2D(5, 10);
        assertEquals(5, p.getX());
        assertEquals (10, p.getY());
    }
    @Test
    void testCopyConstructor() {
        Index2D original = new Index2D(3, 4);
        Index2D copy = new Index2D(original);
        assertEquals(original.getX(), copy.getX());
        assertEquals(original.getY(), copy.getY());
        assertNotSame(original, copy);
    }
    @Test
    void testDistance2D() {
        Index2D p1 = new Index2D(0, 0);
        Index2D p2 = new Index2D(3, 4);
        assertEquals(5.0, p1.distance2D(p2), 0.001);
        assertEquals(5.0, p2.distance2D(p1), 0.001);
    }
    @Test
    void testEquals() {
        Index2D p1 = new Index2D(10,20);
        Index2D p2 = new Index2D(10,20);
        Index2D p3 = new Index2D(5,5);
        assertEquals(p1, p2);
        assertNotEquals(p1,p3);
        assertNotEquals(null,p1);
    }
    @Test
    void testToString() {
        Index2D p = new Index2D(7, 8);
        String result = p.toString();
        assertTrue(result.contains("7"));
        assertTrue(result.contains("8"));
    }
}
