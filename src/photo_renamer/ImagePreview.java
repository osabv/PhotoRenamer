/**
 * 
 */
package photo_renamer;

import javax.swing.*;
import java.beans.*;
import java.awt.*;
import java.io.File;

public class ImagePreview extends JComponent implements PropertyChangeListener {
	ImageIcon thumbnail = null;
	File file = null;

	/**
	 * @param fc the FileChooser
	 */
	public ImagePreview(JFileChooser fc) {
		setPreferredSize(new Dimension(177, 132));
		fc.addPropertyChangeListener(this);
	}

	/**
	 *  Loads the image as an icon.
	 */
	public void loadImage() {
		if (file == null) {
			thumbnail = null;
			return;
		}

		ImageIcon icon = new ImageIcon(file.getPath());
		thumbnail = new ImageIcon(icon.getImage().getScaledInstance(177, 132, Image.SCALE_DEFAULT));

	}

	/**
	 * Updates the file chooser to display an icon of selected image and its new name after
	 * adding tags.
	 * 
	 * param e the property change event that is fired
	 */
	public void propertyChange(PropertyChangeEvent e) {
		boolean update = false;
		String prop = e.getPropertyName();

		if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(prop)) {
			file = (File) e.getNewValue();
			update = true;
		}

		if (update) {
			thumbnail = null;
			if (isShowing()) {
				loadImage();
				repaint();
			}
		}
	}

	/**
	 * Updates the thumbnail displayed on the file chooser.
	 * 
	 * param g the picture to be painted
	 */
	protected void paintComponent(Graphics g) {
		if (thumbnail == null) {
			loadImage();
		}
		if (thumbnail != null) {
			thumbnail.paintIcon(this, g, 0, 0);
		}
	}
}
