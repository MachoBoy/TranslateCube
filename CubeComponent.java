import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Rectangle;
import java.awt.Polygon;

public class CubeComponent extends JComponent
{
	public static Color BACKGROUND_COLOR = Color.WHITE;
	public static Color TRANSLATE_LINE_COLOR = Color.RED;
	public static BasicStroke TRANSLATE_LINE = new BasicStroke(2);
	
	private Cube mainCube;
	private boolean shouldDrawDottedLine;
	private boolean isPressed;
	private int moveLineX;
	private int moveLineY;
	private int closestPointIndex;

	/**
	 * Initializes instance variables
	 */
	public CubeComponent()
	{
		mainCube = null;

		shouldDrawDottedLine = false;
		isPressed = false;
		moveLineX = 0;
		moveLineY = 0;
		closestPointIndex = 0;
	}

	/**
	 * Creates a new CubeComponent placing the cube at the given point
	 * @param x the cube's starting x point
	 * @param y the cube's starting y point
	 */
	public CubeComponent(int x, int y)
	{
		this();
		
		mainCube = new Cube(x, y);
	}

	/**
	 * Processes a single move of the cube
	 */
	public void tick()
	{
		checkCubeBounds();
		
		// Move the cube
		mainCube.move();
	}
	
	private void checkCubeBounds()
	{
		// Checks the left bound of the component
		if (mainCube.getX() <= 0)
		{
			mainCube.goRight();
			
			if (mainCube.getX() < 0)
				mainCube.setX(0);
		}
		// Checks the right bound of the component
		else if (mainCube.getX() + mainCube.getWidth() >= getWidth())
		{
			mainCube.goLeft();
			
			if (mainCube.getX() + mainCube.getWidth() > getWidth())
				mainCube.setX(getWidth() - mainCube.getWidth());
		}

		// Checks the upper bound of the component
		if (mainCube.getY() <= 0)
		{
			mainCube.goDown();
			
			if (mainCube.getY() < 0)
				mainCube.setY(0);
		}
		// Checks the lower bound of the component
		else if (mainCube.getY() + mainCube.getHeight() >= getHeight())
		{
			mainCube.goUp();
			
			if (mainCube.getY() + mainCube.getHeight() > getHeight())
				mainCube.setY(getHeight() - mainCube.getHeight());
		}
	}

	/**
	 * Moves the cube to the given point
	 * @param targetX the x position the cube will be moved to
	 * @param targetY the y position the cube will be moved to
	 */
	public void moveCube(int targetX, int targetY)
	{
		int[] closestPoint = mainCube.getPoint(closestPointIndex);
		mainCube.translate(targetX - closestPoint[0], targetY - closestPoint[1]);
	}

	/**
	 * Finds the point of the cube closest to the given point
	 * @param targetX the x position that will be used to find the closest point
	 * @param targetY the y position that will be used to find the closest point
	 */
	public void isPressed(int targetX, int targetY)
	{
		isPressed = true;

		setTarget(targetX, targetY);
		
		closestPointIndex = mainCube.getClosestPointIndex(targetX, targetY);
	}

	/**
	 * Sets whether the component has been pressed
	 * @param isPressed new value for isPressed
	 */
	public void isPressed(boolean isPressed)
	{
		this.isPressed = isPressed;
		checkCubeBounds();
	}

	/**
	 * Sets whether the dotted translate line should be drawn
	 * @param shouldDrawDottedLine whether the dotted translate line should be drawn
	 */
	public void setDrawDottedLine(boolean shouldDrawDottedLine)
	{
		this.shouldDrawDottedLine = shouldDrawDottedLine;
	}
	
	/**
	 * Set the target point of the cube translation
	 * @param targetX x point of the target location
	 * @param targetY y point of the target location
	 */
	public void setTarget(int targetX, int targetY)
	{
		moveLineX = targetX;
		moveLineY = targetY;
	}

	/**
	 * Draws elements of the scene
	 * @param g used to paint objects to the screen
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;

		// Filling the background
		g2.setColor(BACKGROUND_COLOR);
		g2.fill(new Rectangle(0, 0, getWidth(), getHeight()));

		// Drawing the cube
		mainCube.draw(g2);
		
		// Drawing the dotted translate line
		if (isPressed && shouldDrawDottedLine)
			drawDottedLine(g2);
	}

	/**
	 * Draws the dotted translate line
	 * @param g2 used to draw the line
	 */
	private void drawDottedLine(Graphics2D g2)
	{
		g2.setColor(TRANSLATE_LINE_COLOR);
		g2.setStroke(TRANSLATE_LINE);
					 
		int[] closestPoint = mainCube.getPoint(closestPointIndex);
		g2.drawLine(closestPoint[0], closestPoint[1], moveLineX, moveLineY);
	}

}
