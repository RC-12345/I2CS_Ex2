import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Intro2CS, 2026A, this is a very
 */
class MapTest {
    /**
     */
    private int[][] _map_3_3 = {{0,1,0}, {1,0,1}, {0,1,0}};
    private Map2D _m0, _m1, _m3_3;
    @BeforeEach
    public void setup() {
        _m3_3 = new Map(_map_3_3);
    }
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void init() {
        int[][] bigarr = new int [500][500];
        _m1.init(bigarr);
        assertEquals(bigarr.length, _m1.getWidth());
        assertEquals(bigarr[0].length, _m1.getHeight());
        Pixel2D p1 = new Index2D(3,2);
        _m1.fill(p1,1, true);
    }

    @Test
    void testInit() {
        _m0.init(_map_3_3);
        _m1.init(_map_3_3);
        assertEquals(_m0, _m1);
    }
    @Test
    void testEquals() {
        assertEquals(_m0,_m1);
        _m0.init(_map_3_3);
        _m1.init(_map_3_3);
        assertEquals(_m0,_m1);
    }
    @Test
    void testSquareInit() {
        /**
         * Tests whether the Map(int size) constructor works, through the checking of its size and checking whether its initial values are set to 0.
         */
        int size = 10;
        Map m = new Map(size);
        assertEquals(size, m.getWidth());
        assertEquals (size, m.getHeight());
        assertEquals(0, m.getPixel(0,0));
        assertEquals(0,m.getPixel(size-1,size-1));
    }
    @Test
    void testAnyInit() {
        /**
         * Tests whether init(int w, int h, int v) constructs the map correctly through its size and initial value (v)
         */
        Map m = new Map(5, 10, 1);
        assertEquals(5, m.getWidth());
        assertEquals(10,m.getHeight());
        int[][] values = m.getMap();
        for (int x = 0; x<m.getWidth();x++) {
            for (int y = 0;y<m.getHeight();y++) {
                assertEquals(1, values[x][y]);
            }
        }
    }
    @Test
    void testActionsOnPixels() {
        /**
         * Tests whether setPixel actually updates anything and whether isInside correctly realizes what the boundaries of the grid are.
         */
        Map m = new Map(10, 10, 0);
        Index2D pInside = new Index2D(5,5);
        Index2D pOutside = new Index2D(10,10);
        assertTrue(m.isInside(pInside));
        assertFalse(m.isInside(pOutside));
        m.setPixel(pInside, 7);
        assertEquals(7, m.getPixel(5,5));
    }
    @Test
    void testSameDimensions() {
        /**
         * Tests whether SameDimensions actually checks for identical sizes, irregardless of value.
         */
        Map m1 = new Map(10,20,0);
        Map m2 = new Map(10,20,5);
        Map m3 = new Map(20,10,0);
        assertTrue(m1.sameDimensions(m2));
        assertFalse(m1.sameDimensions(m3));
    }
    @Test
    void testAddMap2D() {
        /**
         * Tests whether the pixels in different maps when summed with AddMap2D are actually summed up properly.
         */
        Map m1 = new Map(2, 2, 10);
        Map m2 = new Map(2, 2, 5);
        m1.addMap2D(m2);
        assertEquals(15, m1.getPixel(0, 0));
        assertEquals(15, m1.getPixel(1, 1));
    }
    @Test
    void testMul() {
        /**
         * Tests whether the multiplication by the scalar actually brings about the appropriate result.
         */
        Map m = new Map(2, 2, 10);
        m.mul(0.5);
        assertEquals(5, m.getPixel(0, 0));
    }
    @Test
    void testRescale() {
        /**
         * Tests whether the same values exist in the predicted cells post rescale.
         */
        Map m = new Map(2, 2, 100); // Original: 2x2
        m.rescale(2.0, 3.0);
        assertEquals(4, m.getWidth());
        assertEquals(6, m.getHeight());
        assertEquals(100, m.getPixel(3, 5));
    }
    @Test
    void testDrawRect() {
        /**
         * Tests whether the rectangle drawn goes over the relevant points.
         */
        Map m = new Map(10, 10, 0);
        Pixel2D p1 = new Index2D(2, 2);
        Pixel2D p2 = new Index2D(4, 4);
        int color = 7;
        m.drawRect(p1, p2, color);
        assertEquals(7, m.getPixel(2, 2));
        assertEquals(7, m.getPixel(3, 3));
        assertEquals(7, m.getPixel(4, 4));
        assertEquals(0, m.getPixel(1, 1));
    }
    @Test
    void testDrawCircle() {
        /**
         * Tests whether the circle drawn goes over the relevant points.
         */
        Map m = new Map(10, 10, 0);
        Pixel2D center = new Index2D(5, 5);
        double radius = 2.0;
        int color = 9;
        m.drawCircle(center, radius, color);
        assertEquals(9, m.getPixel(5, 5));
        assertEquals(9, m.getPixel(5, 7));
        assertEquals(0, m.getPixel(5, 8));
    }
    @Test
    void testDrawLine() {
        /**
         * Tests whether the line drawn actually goes over the relevant points.
         */
        Map m = new Map(10, 10, 0);
        Pixel2D p1 = new Index2D(0, 0);
        Pixel2D p2 = new Index2D(5, 0);
        int color = 3;
        m.drawLine(p1, p2, color);
        assertEquals(3, m.getPixel(0, 0));
        assertEquals(3, m.getPixel(3, 0));
        assertEquals(3, m.getPixel(5, 0));
        assertEquals(0, m.getPixel(0, 1));
    }
    @Test
    void testAllDistance() {
        /**
         * Tests whether the function actually marks obstacle squares as -1.
         */
        Map m = new Map(_map_3_3);
        Map2D dists = m.allDistance(new Index2D(1, 1), 1, false);
        assertEquals(0, dists.getPixel(1, 1));
        assertEquals(-1, dists.getPixel(0, 1));
    }
    @Test
    void testShortestPath() {
        /**
         * Tests whether the shortest path is actually taken.
         */
        int[][] maze = {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
            Map m = new Map(maze);
            Pixel2D p1 = new Index2D(0, 0);
            Pixel2D p2 = new Index2D(2, 2);
            Pixel2D[] path = m.shortestPath(p1, p2, 1, false);
            assertNotNull(path);
            assertEquals(5, path.length);
            assertEquals(p1, path[0]);
            assertEquals(p2, path[path.length - 1]);
    }
}