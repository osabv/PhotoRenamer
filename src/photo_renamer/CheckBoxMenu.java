package photo_renamer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.*;

public class CheckBoxMenu extends JPanel implements ActionListener, Menu {
	
	ArrayList<AbstractButton> options;
	Set<String> tags;
	File image;
	JPanel optionsPanel;
	ImageHistory imageHistory;
	
	/**
	 * Adds a check box menu to GUI for user to select tags from.
	 * 
	 * @param f the image 
	 */
	public CheckBoxMenu(File f) {
		super(new BorderLayout());
		imageHistory =  new ImageHistory(f.getAbsolutePath());
		this.tags = imageHistory.tags;
		options = new ArrayList<>();
		fillArray(tags);
		image = f;
		
		optionsPanel = new JPanel();
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
		
		JLabel label = new JLabel("Please select tags to remove:");
		optionsPanel.add(label);
		
		
		Utils.fillPanel(optionsPanel, options);
		
		add(optionsPanel, BorderLayout.NORTH);
	}
	
	/**
	 * Adds series of check boxes for each tag to the GUI.
	 * 
	 * @param tags the tags associated with this image
	 */
	public void fillArray(Set<String> tags) {
		for (String t : tags) {
			AbstractButton tag = new JRadioButton(t);
			tag.addActionListener(this);
			options.add(tag);
		}
	}

	/**
	 * Adds or removes user selected tags from the imageHistory class.
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		for (AbstractButton tag : options) {
			if (e.getSource() == tag) {
				image = Utils.renameImage(image, tag.getText(), Edit.REMOVE);
				imageHistory.tags.remove(tag.getText());
				imageHistory.names.add(image.getName());
			}
			imageHistory.writeVersionHistory();
		}
	}

}
