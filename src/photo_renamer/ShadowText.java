package photo_renamer;

import java.awt.event.*;

import javax.swing.*;

public class ShadowText extends JTextField implements FocusListener {
	/**
	 * This class is meant to be apart of an API but since we can not use API's from 
	 * other sources this class was mainly implemented using code posted on StackOverFlow.
	 * The purpose of this class is to display a "shadow text" in a JTextField so that 
	 * whenever the user clicks on the JTextField the "shadow text" disappears, 
	 * allowing the user to write their own text.
	 * http://stackoverflow.com/questions/1738966/java-jtextfield-with-input-hint.
	 */
	
	private final String shadow;
	protected boolean showing;

	/**
	 * Initializes a ShadowText instance with text in shadow parameter.
	 * 
	 * @param shadow the text to be displaed
	 */
	public ShadowText(String shadow) {
		super(shadow);
		this.shadow = shadow;
		this.showing = true;
		super.addFocusListener(this);
	}

	/**
	 * Changes the text in super class once focus is gained.
	 * 
	 * @param e the focus event
	 */
	@Override
	public void focusGained(FocusEvent e) {
		if (this.getText().isEmpty()) {
			super.setText("");
			showing = false;
		}

	}

	/**
	 * Shows text once focus is lost.
	 * 
	 * @param e the focus event
	 */
	@Override
	public void focusLost(FocusEvent e) {
		if (this.getText().isEmpty()) {
			super.setText(shadow);
			showing = true;
		}

	}

	/**
	 * Returns the text from super class.
	 */
	@Override
	public String getText() {
		return showing ? "" : super.getText();

	}

	/**
	 *  Resets the text.
	 */
	public void reset() {
		super.setText(shadow);
		showing = true;
	}

}
