package photo_renamer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class ImageHistory extends File implements Serializable {

	protected List<Set<String>> versionHistory;
	protected Set<String> names;
	protected Set<String> tags;
	protected String path;
	protected String firstName;
	private String name;

	/**
	 * Constructs a new instance for tracking an individual image file.
	 * 
	 * @param path the path to the image file
	 */
	public ImageHistory(String path) {
		super(path);
		this.path = path;
		this.name = this.getName();
		firstName = getFirstName(name);
		readVersionHistory();
		names = versionHistory.get(0);
		tags = versionHistory.get(1);
	}

	/**
	 * Reads old version history of the file, if it exists. Otherwise creates one.
	 */
	@SuppressWarnings("unchecked")
	public void readVersionHistory() {
		try {
			FileInputStream fis = new FileInputStream(this.getParentFile().getAbsolutePath() + firstName);
			ObjectInputStream in = new ObjectInputStream(fis);
			versionHistory = (List<Set<String>>) in.readObject();
			in.close();
			fis.close();
		} catch (FileNotFoundException e) {
			versionHistory = new ArrayList<>();
			names = new TreeSet<>();
			names.add(firstName + "." + Utils.getExtension(name));
			versionHistory.add(names);
			tags = new HashSet<>();
			versionHistory.add(tags);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Writes version history of the image this instance of ImageHistory belongs to.
	 */
	public void writeVersionHistory() {
		try {
			FileOutputStream fos = new FileOutputStream(this.getParentFile().getAbsolutePath() + firstName);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(versionHistory);
			os.flush();
			os.close();
			fos.close();
		} catch (FileNotFoundException e) {
			new File(this.getParentFile().getAbsolutePath() + firstName);
			writeVersionHistory();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the original name of the image before tags were added.
	 * 
	 * @param name the current name
	 * @return the original name
	 */
	private String getFirstName(String name) {
		if (!name.contains("@")) {
			String extension = "." + Utils.getExtension(name);
			return name.replace(extension, "");
		} else {
			return name.substring(0, name.indexOf("@") - 1);
		}
	}

}
