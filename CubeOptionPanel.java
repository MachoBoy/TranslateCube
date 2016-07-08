import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.Dimension;

public class CubeOptionPanel extends JPanel 
{
	private JLabel optionLabel;

	private JPanel optionsPanel;
	private JRadioButton bounceButton;
	private JRadioButton translateButton;
	private JRadioButton bothButton;
	private ButtonGroup optionsGroup;

	private boolean shouldBounce;
	private boolean shouldTranslate;

	/**
	 * Creates a new panel with a label and buttons specific to cube movement
	 */
	public CubeOptionPanel()
	{
		setLayout(new BorderLayout());
		
		// Creating radio buttons
		bounceButton = new JRadioButton("Bounce", true);
		translateButton = new JRadioButton("Translate");
		bothButton = new JRadioButton("Bounce + Translate");

		// Adding radio buttons to a ButtonGroup
		optionsGroup = new ButtonGroup();
		optionsGroup.add(bounceButton);
		optionsGroup.add(translateButton);
		optionsGroup.add(bothButton);

		// Adding radio buttons to the panel
		optionsPanel = new JPanel(new FlowLayout());
		optionsPanel.add(bounceButton);
		optionsPanel.add(translateButton);
		optionsPanel.add(bothButton);
		add(optionsPanel, BorderLayout.CENTER);

		// Creating and adding a label to the panel
		optionLabel = new JLabel("Mode Selection", JLabel.CENTER);
		optionLabel.setFont(new Font("Arial", Font.BOLD, 20));
		add(optionLabel, BorderLayout.NORTH);

		// Update the cube movement state variables based on the radio buttons
		updateState();
	}

	/**
	 * Returns whether the options allow the cube to bounce
	 * @return whether the options allow the cube to bounce
	 */
	public boolean shouldBounce() { return shouldBounce; }
	/**
	 * Returns whether the options allow the cube to be translated
	 * @return whether the options allow the cube to be translated
	 */
	public boolean shouldTranslate() { return shouldTranslate; }

	/**
	 * Updates the cube movement state variables based on the radio buttons
	 */
	public void updateState()
	{
		if (bounceButton.isSelected() || bothButton.isSelected())
			shouldBounce = true;
		else
			shouldBounce = false;

		if (translateButton.isSelected() || bothButton.isSelected())
			shouldTranslate = true;
		else
			shouldTranslate = false;
	}

	/**
	 * Adds an ActionListener to the radio buttons
	 * @param listener the ActionListener to be added the to radio buttons
	 */
	public void addActionListener(ActionListener listener)
	{
		bounceButton.addActionListener(listener);
		translateButton.addActionListener(listener);
		bothButton.addActionListener(listener);
	}

}
