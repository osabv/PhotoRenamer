package photo_renamer;

import java.io.File;

import javax.swing.Icon;
import javax.swing.filechooser.FileView;

public class ImageFileView extends FileView {

	/**
	 * Auto-generated function.
	 * 
	 * @see javax.swing.filechooser.FileView#getName(java.io.File)
	 */
	public String getName(File f) {
		return null;
	}

	/**
	 * Auto-generated function.
	 * 
	 * @see javax.swing.filechooser.FileView#getDescription(java.io.File)
	 */
	public String getDescription(File f) {
		return null;
	}

	/** 
	 * Auto-generated function.
	 * 
	 * @see javax.swing.filechooser.FileView#isTraversable(java.io.File)
	 */
	public Boolean isTraversable(File f) {
		return null;
	}

	/**
	 * Returns the type image that file f is.
	 * 
	 * @param f the image to get type of
	 * @see javax.swing.filechooser.FileView#getTypeDescription(java.io.File)
	 */
	public String getTypeDescription(File f) {
		String extension = Utils.getExtension(f.getName());
		String type = null;

		if (extension != null) {
			if (extension.toLowerCase() == "jpeg" || extension.toLowerCase() == "jpg")
				type = "JPEG Image";
		} else {
			type = String.format("%s Image", extension);
		}

		return type;
	}

	/** 
	 * Return an icon of file f.
	 * 
	 * @param f the file representing the image
	 * @see javax.swing.filechooser.FileView#getIcon(java.io.File)
	 */
	public Icon getIcon(File f) {
		Icon icon = Utils.createImageIcon(f.getPath());
		return icon;
	}
}
