import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class FrameViewer extends JFrame
{

	public static final String FRAME_TITLE = "Bouncing Cube";
	public static final int DEFAULT_WIDTH = 800;
	public static final int DEFAULT_HEIGHT = 600;
	public static final int TIMER_DELAY = 10;

	private CubeComponent cubeScene;
	private CubeOptionPanel cubeOptions;

	/**
	 * Constructs a new FrameViewer that generates a window with a moving cube
	 */
	public FrameViewer()
	{
		super(FRAME_TITLE);
	
		// Setting frame attributes
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		// Creating and adding the options panel
		cubeOptions = new CubeOptionPanel();
		add(cubeOptions, BorderLayout.NORTH);
		cubeOptions.addActionListener(new OptionListener());

		// Creating and adding the cube display component
		cubeScene = new CubeComponent(DEFAULT_WIDTH / 2, DEFAULT_HEIGHT / 2);
		add(cubeScene, BorderLayout.CENTER);

		// Adding mouse listeners to the cube display component
		cubeScene.addMouseListener(new CubeMouseListener());
		cubeScene.addMouseMotionListener(new CubeMouseMotionListener());

		// Creating and starting a timer for the frame
		Timer t = new Timer(TIMER_DELAY, new CubeTimer());
		t.start();	

		// Displaying frame
		setVisible(true);
	}

	class CubeTimer implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// Moves the cube if the program allows bouncing
			if (cubeOptions.shouldBounce())
				cubeScene.tick();

			// Draws the dotted translate line if the program allows translation
			if (cubeOptions.shouldTranslate())
				cubeScene.setDrawDottedLine(true);
			else
				cubeScene.setDrawDottedLine(false);

			// Re-draw the cube display
			cubeScene.repaint();
		}
	}

	/**
	 * Inner class used to process events from the options panel
	 */
	class OptionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// Updates whether the program allows bouncing and/or translation
			cubeOptions.updateState();
		}
	}

	/**
	 * Inner class used to process mouse buttons from the cube display component
	 */
	class CubeMouseListener implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e)
		{
			/*
			 * Sets the end point of the dotted translate line
			 * and determines which point is closest to the mouse
			 * when the click occurred.
			 */
			if (cubeOptions.shouldTranslate())
			{
				cubeScene.isPressed(e.getX(), e.getY());
				cubeScene.repaint();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
			// Moves the cube to the mouse's position
			if (cubeOptions.shouldTranslate())
			{
				cubeScene.moveCube(e.getX(), e.getY());
				cubeScene.isPressed(false);
				cubeScene.repaint();
			}
		}
	}
	
	/**
	 * Inner class to process mouse movement inside the cube display component
	 */
	class CubeMouseMotionListener implements MouseMotionListener
	{
		@Override
		public void mouseDragged(MouseEvent e)
		{
			// Sets the end point of the dotted translate line
			if (cubeOptions.shouldTranslate())
				cubeScene.setTarget(e.getX(), e.getY());
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {}
	}

	/**
	 * Tests the FrameViewer class
	 * @param args     not used
	 */
	public static void main(String[] args)
	{
		new FrameViewer();
	}

}
