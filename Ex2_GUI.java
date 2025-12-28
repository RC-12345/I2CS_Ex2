import java.awt.*;

/**
 * Intro2CS_2026A
 * This class represents a Graphical User Interface (GUI) for Map2D.
 * The class has save and load functions, and a GUI draw function.
 * You should implement this class, it is recommender to use the StdDraw class, as in:
 * https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html
 *
 *
 */
public class Ex2_GUI {
    public static void drawMap(Map2D map) {
        if (map == null) return;
        int w = map.getWidth();
        int h = map.getHeight();
        StdDraw.setCanvasSize(w * 5, h * 5);
        StdDraw.setXscale(-0.5, w - 0.5);
        StdDraw.setYscale(-0.5, h - 0.5);
        StdDraw.enableDoubleBuffering();
        for (int x = 0; x<w;x++) {
            for (int y = 0; y<h;y++) {
                int color = map.getPixel(x,y);
                    if (color ==0) {
                    StdDraw.setPenColor(Color.WHITE);
                    }
                        else if (color == 1) {
                    StdDraw.setPenColor(Color.WHITE);
                        }
                        else {
                    int shade = Math.max(0, 200 - (color * 10));
                    StdDraw.setPenColor(new Color(shade, shade, shade));
                        }
                        StdDraw.filledSquare(x,y,0.5);
                    }
                }
                StdDraw.show();
            }
        }

    /**
     * @param mapFileName
     * @return
     */
    public static Map2D loadMap(String mapFileName) {
        Map2D ans = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(mapFileName))) {
            ans = (Map2D) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading map: " + e.getMessage());
        }
        return ans;
    }

    /**
     *
     * @param map
     * @param mapFileName
     */
    public static void saveMap(Map2D map, String mapFileName) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(mapFileName))) {
            out.writeObject(map);
        } catch (IOException e) {
            System.err.println("Error saving map: " + e.getMessage());
        }
    }
    public static void main(String[] a) {
        String mapFile = "map.txt";
        Map m = new Map(100,100, 0);
        m.drawCircle(new Index2D(50,50),20,255);
        saveMap(m,mapFile);
        Map2D map = loadMap(mapFile);
        Ex2_GUI.drawMap(map);
    }
    /// ///////////// Private functions ///////////////

