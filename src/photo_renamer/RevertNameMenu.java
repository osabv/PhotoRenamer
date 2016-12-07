package photo_renamer;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import javax.swing.*;

public class RevertNameMenu extends JPanel implements ActionListener, Menu {

	ArrayList<AbstractButton> oldNames;
	Set<String> names;
	JPanel options;
	JPanel buttons;
	JButton revert;
	JButton nevermind;
	File image;
	JFrame frame;
	ButtonGroup group;
	ImageHistory imageVersion;
	TagsManager taggy;

	/**
	 * Creates a mini pop-up asking user which name they would like to revert to.
	 * 
	 * @param f the file whose name is to be reverted
	 */
	public RevertNameMenu(File f) {

		super(new BorderLayout());
		this.names = new ImageHistory(f.getAbsolutePath()).names;
		oldNames = new ArrayList<>();
		fillArray(names);
		image = f;
		imageVersion = new ImageHistory(f.getAbsolutePath());
		taggy = new TagsManager("allTags");

		options = new JPanel();
		options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));

		JLabel label = new JLabel("Please select an image name to revert to:");
		options.add(label);

		Utils.fillPanel(options, oldNames);

		group = new ButtonGroup();
		groupButtons(oldNames);

		add(options, BorderLayout.NORTH);
	}

	/**
	 * Adds list of old names to the button group displayed to user
	 * 
	 * @param oldNames button to be added
	 */
	private void groupButtons(ArrayList<AbstractButton> oldNames) {
		for (AbstractButton name : oldNames) {
			group.add(name);
		}

	}

	/**
	 * Creates buttons for all names in the set of names.
	 * 
	 * @param names the set of names the image file has held
	 */
	public void fillArray(Set<String> names) {
		for (String n : names) {
			AbstractButton name = new JRadioButton(n);
			name.addActionListener(this);
			oldNames.add(name);
		}
	}
	
	public void writeVersionHistory(String oldName, String newName) {
		ArrayList<String> oldTags = null;
		ArrayList<String> newTags = null;
		String extension = "." + Utils.getExtension(oldName);
		if (oldName.contains("@")) {
			oldName = oldName.replace(extension, "");
			oldTags = new ArrayList<String>(
					Arrays.asList(oldName.substring(oldName.indexOf("@") + 1, oldName.length()).split("@")));
		}
		if (newName.contains("@")) {
			newName = newName.replace(extension, "");
			newTags = new ArrayList<String>(
					Arrays.asList(newName.substring(newName.indexOf("@") + 1, newName.length()).split("@")));
			System.out.println(newTags);
		}
		if (oldTags != null) {
			for (String tag : oldTags) {
				imageVersion.tags.remove(tag);
				taggy.removeTag(tag);
			}
		}
		if (newTags != null) {
			for (String tag : newTags) {
				imageVersion.tags.add(tag);
				taggy.removeTag(tag);
			}
		}
	}

	/** 
	 * Reverts the name of selected image to name that user clicked on.
	 * 
	 * @param e the action of the user
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		for (AbstractButton name : oldNames) {
			if (e.getSource() == name) {
				String oldName = image.getName();
				File f = Utils.renameImage(image, name.getText(), Edit.REVERT);
				String newName = f.getName();
				writeVersionHistory(oldName, newName);
			}
		}
	}

}
