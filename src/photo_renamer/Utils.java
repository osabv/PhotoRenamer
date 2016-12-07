package photo_renamer;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * This class follows the Utility pattern. Everything in this class is static
 * and never initialized. Other classes use utility methods available in this class.
 * @author osama
 *
 */
public class Utils {
	public final static ArrayList<String> extensions = new ArrayList<String>(
			Arrays.asList("gif", "jpeg", "png", "bmp", "tiff", "jpg"));
	protected static ProgramLog log = new ProgramLog();
	protected static TagsManager taggy = new TagsManager("allTags");

	/**
	 * Return the extension of the file, given its name.
	 * 
	 * @param filename name of the file.
	 * @return the extension of the file as a string
	 */
	public static String getExtension(String filename) {
		String extension = "";
		int i = filename.lastIndexOf('.');
		extension = filename.substring(i + 1);
		return extension;
	}

	/**
	 * Returns true if the extension is a valid image extension.
	 * 
	 * @param extension the extension of the image
	 * @return true if extension is contained in the list of extensions
	 */
	public static boolean isValidExtension(String extension) {
		return extensions.contains(extension.toLowerCase());
	}

	/**
	 * Renames an image.
	 * 
	 * @param f the file to renamed
	 * @param rename the new name
	 * @param i the type of edit to be made
	 * @return
	 */
	public static File renameImage(File f, String rename, Edit i) {

		File newFile = null;
		String oldName = f.getName();
		String newName;

		if (i == Edit.ADD) {
			String extension = "." + Utils.getExtension(f.getName());
			String path = f.getName().replace(extension, "") + " @" + rename + extension;
			newFile = new File(f.getParentFile().getAbsolutePath(), path);
			taggy.addTag(rename);
		}
		if (i == Edit.REMOVE) {
			String path = f.getName().replace(" @" + rename, "");
			newFile = new File(f.getParentFile().getAbsolutePath(), path);
			taggy.removeTag(rename);
			
		}
		if (i == Edit.REVERT) {
			newFile = new File(f.getParentFile().getAbsolutePath(), rename);
		}

		f.renameTo(newFile);

		newName = newFile.getName();
		log.writeLog(oldName, newName);

		return newFile;
	}

	/**
	 * Creates and returns an image icon.
	 * 
	 * @param path the path to an image
	 * @return an icon representation of image
	 */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = Utils.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			return null;
		}
	}

	/**
	 * Fills the panel with buttons.
	 * 
	 * @param options the panel to add things to
	 * @param list the list of things to on the panel
	 */
	protected static void fillPanel(JPanel options, ArrayList<AbstractButton> list) {
		for (AbstractButton item : list) {
			options.add(item);
		}

	}

	/**
	 * Changes the name of the panel.
	 * 
	 * @param panel the panel to change
	 * @param tittle the new title of it
	 */
	protected static void changeName(JPanel panel, String title) {
		JOptionPane.showMessageDialog(null, panel, title, JOptionPane.PLAIN_MESSAGE);
	}
	
	/**
	 * Displays the folder containing the image.
	 * 
	 * @param f the file to be shown
	 */
	public static void displayParentFolder(File f) {
		File file = new File(f.getParent());
		Desktop desktop = Desktop.getDesktop();
		try {
			desktop.open(file);
		} catch (Exception e) {
			System.err.format("IOException: %s%n", e);
		}
	}
}
