package photo_renamer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

public class ImageOptions extends Box implements ActionListener {

	JFileChooser chooser;
	ShadowText getTag;
	JButton addTag;
	JButton removeTag;
	JButton listTags;
	JButton viewLog;
	JButton OS;

	/**
	 * Enhances the file chooser allowing it to add tags, and remove tags. Also allowing
	 * it to have text area for adding new tags or revert to old names.
	 * 
	 * @param chooser the chooser to add more components to
	 */
	public ImageOptions(JFileChooser chooser) {
		super(BoxLayout.Y_AXIS);

		this.chooser = chooser;

		getTag = new ShadowText("Enter Tag Here");

		addTag = new JButton("Add Tag");
		addTag.addActionListener(this);

		removeTag = new JButton("Remove Tag");
		removeTag.addActionListener(this);

		listTags = new JButton("Choose Tags");
		listTags.addActionListener(this);

		viewLog = new JButton("Revert");
		viewLog.addActionListener(this);

		OS = new JButton("View in OS");
		OS.addActionListener(this);

		add(getTag);
		add(addTag);
		add(removeTag);
		add(listTags);
		add(viewLog);
		add(OS);
	}

	/**
	 * Creates a GUI displaying old image names.
	 * 
	 * @param obj the component to add to revert frame
	 */
	public static void createGUI(JComponent obj) {
		JFrame frame = new JFrame("Revert");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(obj);
		frame.pack();
		frame.setVisible(true);
	}

	/** 
	 * Processes the users button requests, delivering correct behaviour on button presses
	 * for removeTags, addTags and viewLog.
	 * 
	 * @param e the event that was performed.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (chooser.getSelectedFile() != null) {
			if (e.getSource() == OS) {
				Utils.displayParentFolder(chooser.getSelectedFile());
			} else {
				ImageHistory image = new ImageHistory(chooser.getSelectedFile().getAbsolutePath());
				if (e.getSource() == addTag) {
					if (!getTag.showing) {
						File f = Utils.renameImage(chooser.getSelectedFile(), getTag.getText(), Edit.ADD);

						image.names.add(f.getName());
						image.tags.add(getTag.getText());
						image.writeVersionHistory();

						((ShadowText) getTag).reset();
						chooser.setSelectedFile(f);
					}
				}
				if (e.getSource() == removeTag) {
					CheckBoxMenu removeTags = new CheckBoxMenu(image);
					Utils.changeName(removeTags, "Remove Tags");
				}
				if (e.getSource() == listTags) {
					TagsCheckBoxMenu addTags = new TagsCheckBoxMenu(image);
					Utils.changeName(addTags, "Choose Tags");
				}
				if (e.getSource() == viewLog) {
					RevertNameMenu nameOptions = new RevertNameMenu(image);
					Utils.changeName(nameOptions, "Revert Name");
				}
				chooser.updateUI();
			}
		}
	}
}
