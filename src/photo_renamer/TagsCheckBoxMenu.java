package photo_renamer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.*;

public class TagsCheckBoxMenu extends JPanel implements ActionListener, Menu {

	ArrayList<AbstractButton> options;
	Set<String> tags;
	File image;
	JPanel optionsPanel;
	ImageHistory imageHistory;

	/**
	 * Shows a list of tags that file f has, and other tags that can be added to it.
	 * 
	 * @param f the image user is working with
	 */
	public TagsCheckBoxMenu(File f) {
		super(new BorderLayout());
		this.tags = new TagsManager("allTags").tags.keySet();
		imageHistory = new ImageHistory(f.getAbsolutePath());
		options = new ArrayList<>();
		fillArray(tags);
		image = f;

		optionsPanel = new JPanel();
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));

		JLabel label = new JLabel("Please select tags to add:");
		optionsPanel.add(label);

		Utils.fillPanel(optionsPanel, options);

		add(optionsPanel, BorderLayout.NORTH);
	}

	/**
	 * Adds an action listener to all tags' radiobuttons.
	 * 
	 * @param tags
	 */
	public void fillArray(Set<String> tags) {
		for (String t : tags) {
			AbstractButton tag = new JRadioButton(t);
			tag.addActionListener(this);
			options.add(tag);
		}
	}

	/**
	 * Updates the ImageHistory instance of this class, and renames the image up user's action to
	 * add tags.
	 * 
	 * @param e the user's action
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		for (AbstractButton tag : options) {
			if (e.getSource() == tag) {
				image = Utils.renameImage(image, tag.getText(), Edit.ADD);
				imageHistory.tags.add(tag.getText());
				imageHistory.names.add(image.getName());
			}
			imageHistory.writeVersionHistory();
		}
	}

}
