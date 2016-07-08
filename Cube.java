import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Polygon;

public class Cube
{
	
	public static final int DEFAULT_SIDE_LENGTH = 100;
	
	public static final double DEFAULT_OFFSET_SCALE = 0.33;
	
	public static final Color DEFAULT_COLOR = Color.BLACK;
	
	public static final int DEFAULT_SPEED = 1;
	
	public static final int START_XDIR = DEFAULT_SPEED * -1;
	
	public static final int START_YDIR = DEFAULT_SPEED * -1;
	
	public static final BasicStroke DEFAULT_STROKE = new BasicStroke(2f);

	private int sideLength;
	private int offset;
	private Color color;

	private Polygon cubePoly;

	private int deltaX;
	private int deltaY;

	/**
	 * Creates a cube with default values
	 */
	public Cube()
	{
		sideLength = DEFAULT_SIDE_LENGTH;
		offset = (int)(DEFAULT_SIDE_LENGTH * DEFAULT_OFFSET_SCALE);

		color = DEFAULT_COLOR;

		deltaX = START_XDIR;
		deltaY = START_YDIR;

		int[] xpoints = { offset,
						  offset + sideLength,
						  sideLength,
						  0,
						  offset,
						  offset,
						  offset + sideLength,
						  sideLength,
						  0,
						  offset,
						  offset + sideLength,
						  offset + sideLength,
						  sideLength,
						  sideLength,
						  0,
						  0 };

		int[] ypoints = { 0,
						  0,
						  offset,
						  offset,
						  0,
						  sideLength,
						  sideLength,
						  offset + sideLength,
						  offset + sideLength,
						  sideLength,
						  sideLength, 
						  0,
						  offset, 
						  offset + sideLength,
						  offset + sideLength,
						  offset };

		int npoints = xpoints.length;

		cubePoly = new Polygon(xpoints, ypoints, npoints);
	}

	/**
	 * Creates a cube with default values at the given position
	 * @param xpos the starting x position of the cube
	 * @param ypos the starting y position of the cube
	 */
	public Cube(int xpos, int ypos)
	{
		this();

		cubePoly.translate(xpos, ypos);
	}

	/**
	 * Returns the x position of the bounding box of the cube
	 * @return the x position of the bounding box of the cube
	 */
	public int getX() { return (int)cubePoly.getBounds().getX(); }
	/**
	 * Returns the y position of the bounding box of the cube
	 * @return the y position of the bounding box of the cube
	 */
	public int getY() { return (int)cubePoly.getBounds().getY(); }
	/**
	 * Returns the width of the bounding box of the cube
	 * @return the width of the bounding box of the cube
	 */
	public int getWidth() { return (int)cubePoly.getBounds().getWidth(); }
	/**
	 * Returns the height of the bounding box of the cube
	 * @return the height of the bounding box of the cube
	 */
	public int getHeight() { return (int)cubePoly.getBounds().getHeight(); }

	/**
	 * Sets the x point of the bounding box of the cube
	 * @param x the new x point of the bounding box of the cube
	 */
	public void setX(int x)
	{
		cubePoly.translate(x - (int)cubePoly.getBounds().getX(), 0);
	}
	/**
	 * Sets the y point of the bounding box of the cube
	 * @param y the new y point of the bounding box of the cube
	 */
	public void setY(int y)
	{
		cubePoly.translate(0, y - (int)cubePoly.getBounds().getY());
	}
	
	/**
	 * Sets the x direction of the cube rightward
	 */
	public void goRight() { deltaX = Math.abs(deltaX); }
	/**
	 * Sets the x direction of the cube leftward
	 */
	public void goLeft() { deltaX = -Math.abs(deltaX); }
	/**
	 * Sets the x direction of the cube upward
	 */
	public void goUp() { deltaY = -Math.abs(deltaY); }
	/**
	 * Sets the x direction of the cube downward
	 */
	public void goDown() { deltaY = Math.abs(deltaY); }

	/**
	 * Returns the point of the cube at the given index
	 * @param index the index of the point to be returned
	 * @return the point at the given index
	 */
	public int[] getPoint(int index)
	{
		int[] point = { cubePoly.xpoints[index], cubePoly.ypoints[index] };
		return point;
	}
	
	/**
	 * Returns the index of the point of the cube that is closest to the given point
	 * @param targetX the x position that will be used to find the closest point
	 * @param targetY the y position that will be used to find the closest point
	 * @return the index of the point that is closest the the target point
	 */
	public int getClosestPointIndex(int targetX, int targetY)
	{
		int minIndex = 0;
		double minLength = getHypotenuse(targetX - cubePoly.xpoints[0],
										 targetY - cubePoly.ypoints[0]);

		// Finds the point of the cube that is closest to the given point
		// using the length of the hypotenuse of the triangle formed using the
		// given point and each point of the cube
		for (int i = 1; i < cubePoly.npoints; i++)
		{
			if (getHypotenuse(targetX - cubePoly.xpoints[i],
							  targetY - cubePoly.ypoints[i]) < minLength)
			{
				minLength = getHypotenuse(targetX - cubePoly.xpoints[i],
							  			  targetY - cubePoly.ypoints[i]);	
				minIndex = i;
			}
				
		}

		return minIndex;
	}

	/**
	 * Translates the cube
	 * @param dx the change in the x coordinate
	 * @param dy the change in the y coordinate
	 */
	public void translate(int dx, int dy)
	{
		cubePoly.translate(dx, dy);
	}

	/**
	 * Moves the cube by its velocity
	 */
	public void move()
	{
		cubePoly.translate(deltaX, deltaY);
	}

	/**
	 * Draws the cube
	 * @param g2 used to draw the cube
	 */
	public void draw(Graphics2D g2)
	{
		g2.setStroke(DEFAULT_STROKE);
		g2.setColor(color);
		g2.drawPolygon(cubePoly);
	}

	/**
	 * Calculates the length of the hypotenuse
	 * @param base the base of the triangle
	 * @param height the height of the triangle
	 */
	private double getHypotenuse(int base, int height)
	{
		base = Math.abs(base);
		height = Math.abs(height);

		return Math.sqrt(Math.pow(base, 2) + Math.pow(height, 2));
	}
}
