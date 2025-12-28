import java.io.Serializable;
/**
 * This class represents a 2D map (int[w][h]) as a "screen" or a raster matrix or maze over integers.
 * This is the main class needed to be implemented.
 *
 * @author boaz.benmoshe
 *
 */
public class Map implements Map2D, Serializable{

    // edit this class below
	/**
	 * Constructs a w*h 2D raster map with an init value v.
	 * @param w Represents the columns.
	 * @param h Represents the rows.
	 * @param v Represent the initial value recieved by the rows & columns.
	 */
	public Map(int w, int h, int v) {init(w, h, v);}
	/**
	 * Constructs a square map (size*size).
	 * @param size
	 */
	public Map(int size) {this(size,size, 0);}
	
	/**
	 * Constructs a map from a given 2D array.
	 * @param data
	 */
	public Map(int[][] data) {
		init(data);
	}
	@Override
	public void init(int w, int h, int v) {
		/**
		 * This builds a map filled with its initial value, v.
		 * Does this by going over each index and inserting v into it.
		 */
		map = new int[w][h];
		for (int i = 0; i<w;i++) {
			for (int j = 0; j<h;j++) {
				map[i][j] = v;
			}
		}

	}
	@Override
	public void init(int[][] arr) {
		/**
		 * Constructs a map from a given array, functionally creating a deep copy.
		 * Does this by going over each value and inputting it into the new map.
		 */
		if (arr==null) {
			return;
		}
		int w = arr.length;
		int h = arr[0].length;
		map = new int[w][h];
		for (int i = 0; i<w;i++) {
			for (int j = 0; j<h;j++) {
				map[i][j]=arr[i][j];
			}
		}
	}
	@Override
	public int[][] getMap() {
		/**
		 * Procures the deep copy of a map.
		 * Does this by throwing the full values of a given map into a new map.
		 */
		int[][] ans = null;
		ans = map;
		return ans;
	}
	@Override
	public int getWidth() {
		/**
		 * Procures the amount of columns in a map.
		 * Does this by measuring its horizontal length.
		 */
        int ans = -1;
		ans = map.length;
        return ans;
    }
	@Override
	public int getHeight() {
		/**
		 * Procures the amount of rows in a map.
		 * Does this by measuring its vertical length.
		 */
        int ans = -1;
		ans = map[0].length;
        return ans;
    }
	@Override
	public int getPixel(int x, int y) {
		/**
		 * Procures the value of any given pixel in a map.
		 * Does this by pulling the value from a given index.
		 */
        int ans = -1;
		ans = map[x][y];
        return ans;
    }
	@Override
	public int getPixel(Pixel2D p) {
		/**
		 * Procures the value of any given pixel in a map.
		 * Does this by pulling the value from the pixel itself.
		 */
        int ans = -1;
		ans = getPixel(p.getX(), p.getY());
        return ans;
	}
	@Override
	public void setPixel(int x, int y, int v) {
		/**
		 * Changes the value of a pixel in a given coordinate.
		 * Does this by assigning a value to a given index.
		 */
		map[x][y]=v;
    }
	@Override
	public void setPixel(Pixel2D p, int v) {
		/**
		 * Changes the value of any given pixel.
		 * Does this by assigning a value to a pre-known pixel.
		 */
		setPixel(p.getX(),p.getY(), v);
	}

    @Override
    public boolean isInside(Pixel2D p) {
		/**
		 * Checks whether a pixel is in the map's range.
		 * Does this by checking whether its index exists between the limits of the map's range.
		 */
        boolean ans = true;
		int x = p.getX();
		int y = p.getY();
		ans = (x >= 0 && x < getWidth() && y >=0 && y < getHeight());
        return ans;
    }

    @Override
    public boolean sameDimensions(Map2D p) {
		/**
		 * Checks whether two maps have the same size.
		 * Compares the limits of their range to do so.
		 */
        boolean ans = false;
		ans = p!= null && this.getWidth() == p.getWidth() && this.getHeight() == p.getHeight();
        return ans;
    }

    @Override
    public void addMap2D(Map2D p) {
		/**
		 * Performs addition on two separate maps.
		 * Does this by adding the value of a map in a given index to the value of another map in that same index.
		 */
		if (!sameDimensions(p)) {return;}
		for (int x = 0;x<getWidth();x++) {
			for (int y = 0; y<getHeight();y++) {
				map[x][y]+=p.getPixel(x,y);
			}
		}
    }

    @Override
    public void mul(double scalar) {
		/**
		 * Performs multiplication with a scalar on a map.
		 * Multiplies each individual point within a map by the scalar to do so.
		 */
		for (int x = 0; x<getWidth();x++) {
			for (int y = 0;y<getHeight();y++) {
				map[x][y]= (int)(map[x][y]*scalar);
			}
		}
    }

    @Override
    public void rescale(double sx, double sy) {
		/**
		 * Changes the size of a map while retaining its values in proportional spots.
		 * Does this by enhancing the size of the map first, and then applying the proportion on a per-cell basis.
		 */
		int newW = (int)(getWidth()*sx);
		int newH = (int)(getHeight()*sy);
		int[][] rescale = new int [newW][newH];
		for (int x = 0; x<newW;x++) {
			for (int y = 0;y<newH;y++) {
				rescale[x][y]=map[(int)(x/sx)][(int)(y/sy)];
			}
		}
		map = rescale;
    }

    @Override
    public void drawCircle(Pixel2D center, double rad, int color) {
		/**
		 * Draws a circle given a central points and a radius.
		 * Does this by drawing along two points (the center and the point discovered at the end of the radious)
		 * The distance among those two points being the distance between two pixels.
		 */
		for (int x = 0;x<getWidth();x++){
			for (int y = 0;y<getHeight();y++){
				double distance = center.distance2D(new Index2D(x,y));
				if (distance<=rad) {
					setPixel(x,y,color);
				}
			}
		}
    }

    @Override
    public void drawLine(Pixel2D p1, Pixel2D p2, int color) {
		/**
		 * Draws a line along two given points.
		 * Does this by drawing along the distance between them.
		 */
		double distance = p1.distance2D(p2);
		for (double i =0;i<=1;i+=1.0/distance){
			int x = (int)(p1.getX()*(1-i)+p2.getX()*i);
			int y = (int)(p1.getY()*(1-i)+p2.getY()*i);
			setPixel(x,y,color);
		}
    }

    @Override
    public void drawRect(Pixel2D p1, Pixel2D p2, int color) {
		/**
		 * Draws a rectangle when given two points on the map.
		 * Does this by drawing from the earliest x point to the highest y point, until the latest x point.
		 */
		int x1=Math.min(p1.getX(),p2.getX());
		int x2=Math.max(p1.getX(),p2.getX());
		int y1=Math.min(p1.getY(),p2.getY());
		int y2=Math.max(p1.getY(),p2.getY());
		for (int x = x1;x<=x2;x++){
			for (int y = y1;y<=y2;y++){
				setPixel(x,y,color);
			}
		}
    }

    @Override
    public boolean equals(Object ob) {
		/**
		 * Checks whether a provided object is equal to the map.
		 */
        boolean ans = true;
		if (ob ==null || !(ob instanceof Map2D)) { //Checks whether the object is even of a relevant variable type.
			return false;
		}
		Map2D other = (Map2D) ob;
		if (!this.sameDimensions(other)) { //Checks whether the object has the same size as the map.
			return false;
		}
		for (int x = 0;x<getWidth();x++) {//Goes over each value of the objects and compares it to those of the map.
			for (int y = 0;y<getHeight();y++) {
				if (this.getPixel(x, y) != other.getPixel(x,y)) {
					return false;
				}
			}
		}
        return ans;
    }
	@Override
	/** 
	 * Fills this map with the new color (new_v) starting from p.
	 * https://en.wikipedia.org/wiki/Flood_fill
	 * Follows the flood-fill algorithm in the link provided, using a separate function for the recursion.
	 */
	public int fill(Pixel2D xy, int new_v,  boolean cyclic) {
		int ans = -1;
		int old_v=getPixel(xy);
		if (old_v==new_v) {
			return 0;
		}
		ans = fillRecursive(xy.getX(),xy.getY(),old_v,new_v,cyclic);
		return ans;
	}

	@Override
	/**
	 * BFS like shortest the computation based on iterative raster implementation of BFS, see:
	 * https://en.wikipedia.org/wiki/Breadth-first_search
	 * Checks each adjacent point to a given point until arriving at another given point.
	 * If the adjacent point is a -1, it will ignore it. If it has a value equal to the original point, it will count it.
	 */
	public Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor, boolean cyclic) {
		Pixel2D[] ans = null;
		Map2D distance = allDistance(p2,obsColor,cyclic);
		int steps = distance.getPixel(p1);
		if (steps==-1) {
			return ans;
		}
		Pixel2D[] path = new Pixel2D[steps+1];
		Pixel2D current = p1;
		path[0] = current;
		int[] dx = {1,-1,0,0};
		int[] dy = {0,0,1,-1};
		for (int k = 1; k<= steps;k++) {
			for (int i = 0; i<4; i++) {
				int nx = current.getX() + dx[i];
				int ny = current.getY()+dy[i];
				if (cyclic) {
					nx = (nx%getWidth()+getWidth())%getWidth();
					ny = (ny%getHeight()+getHeight())%getHeight();
				}
				Index2D next = new Index2D(nx, ny);
				if (isInside(next)&& distance.getPixel(next)==steps-k) {
					current = next;
					path[k] = current;
					break;
				}
			}
		}
		ans = path;
		return ans;
	}
    @Override
    public Map2D allDistance(Pixel2D start, int obsColor, boolean cyclic) {
		/**
		 * Moves over a whole map, from point to point, to denote which points along it are different to the original point provided.
		 */
        Map2D ans = null;  // the result.
		int w = getWidth();
		int h = getHeight();
		Map resolution = new Map(w, h, -1);
		if (!isInside(start) || getPixel(start) == obsColor) {
			ans = resolution;
		}
		Pixel2D[] queue = new Pixel2D[w*h];
		int head = 0;
		int tail = 0;
		queue[tail++]=start;
		resolution.setPixel(start,0);
		int[] dx = {0,0,1,-1};
		int[] dy = {1,-1,0,0};
		while (head<tail) {
			Pixel2D current = queue[head++];
			int currentDistance=resolution.getPixel(current);
			for (int i = 0;i<4;i++) {
				int nx = current.getX()+dx[i];
				int ny = current.getY()+dy[i];
				if (cyclic) {
					nx = (nx % w + w) %w;
					ny = (ny % h + h ) %h;
				}
				Index2D next = new Index2D(nx, ny);
				if (isInside(next) && getPixel(next) != obsColor && resolution.getPixel(next) == -1) {
					resolution.setPixel(next, currentDistance + 1);
					queue[tail++] = next;
				}
			}
		}
		ans = resolution;
        return ans;
    }
	////////////////////// Private Methods ///////////////////////
	private int[][] map;
	private int fillRecursive(int x, int y, int old_v, int new_v, boolean cyclic) {
		if (cyclic) {
			x = (x+getWidth())%getWidth();
			y = (y+getHeight())%getHeight();
		}
		else {
			if (x < 0 || x>= getWidth() || y < 0 || y>= getHeight()) { //Checks whether we're in-bounds
				return 0;
			}
		}
		if (map[x][y] != new_v) {
			return 0;
		}
		map[x][y] = new_v;
		int count = 1;
		count += fillRecursive(x+1, y, old_v, new_v, cyclic); //Goes east
		count += fillRecursive(x-1, y, old_v, new_v, cyclic); //Goes west
		count += fillRecursive(x,y+1, old_v, new_v, cyclic); //Goes north
		count += fillRecursive(x, y-1, old_v, new_v, cyclic); //Goes south
		return count;
	}
}
