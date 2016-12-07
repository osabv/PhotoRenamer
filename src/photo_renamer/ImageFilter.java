package photo_renamer;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * @author osama, ammar
 *
 */
public class ImageFilter extends FileFilter {

	/**
	 * Returns true if a file is acceptable; if it is a directory or a valid image file.
	 * 
	 * @param f the file to be checked
	 * 
	 */
	public boolean accept(File f) {
		if (f.isDirectory())
			return true;

		String extension = Utils.getExtension(f.getName());
		if (extension != null) {
			if (Utils.isValidExtension(extension))
				return true;
		}
		return false;
	}

	/**
	 * Returns description of an image.
	 * Sourced from official java docs.
	 */
	@Override
	public String getDescription() {
		return "Images";
	}
}
