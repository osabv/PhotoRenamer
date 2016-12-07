package photo_renamer;

import java.io.*;
import java.util.*;

public class TagsManager implements Serializable {

	protected HashMap<String, Integer> tags;
	protected String s;

	/**
	 * Initialize the tags manager by reading potential tags in .ser files.
	 */
	public TagsManager(String s) {
		this.s = s;
		readTags();
	}

	/**
	 * Add a new tag to master list.
	 * 
	 * @param tag the tag to be added
	 */
	public void addTag(String tag) {
		if (tags.containsKey(tag)) {
			tags.put(tag, tags.get(tag) + 1);
		} else {
			tags.put(tag, 1);
		}
		writeTags();
	}
	
	/**
	 * Remove a  tag from the master list.
	 * 
	 * @param tag the tag to be added
	 */
	public void removeTag(String tag) {
		Integer newVal = tags.get(tag) - 1;
		if (newVal == 0) {
			tags.remove(tag);
		} else {
			tags.put(tag, newVal);
		}
		writeTags();
	}

	/**
	 * Writes master list of tags, if the serialized file doesn't exist, creates a new one.
	 */
	public void writeTags() {
		try {
			FileOutputStream fos = new FileOutputStream(s);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(tags);
			os.flush();
			os.close();
			fos.close();
		} catch (FileNotFoundException e) {
			new File(s);
			writeTags();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads the master list of tags from a previous program version, if the seralized file doesn't exist, creates a new one.
	 * 
	 * @param tags
	 */
	@SuppressWarnings("unchecked")
	public void readTags() {
		try {
			FileInputStream fis = new FileInputStream(s);
			ObjectInputStream in = new ObjectInputStream(fis);
			tags = (HashMap<String, Integer>) in.readObject();
			in.close();
			fis.close();
		} catch (FileNotFoundException e) {
			tags = new HashMap<String, Integer>();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}
