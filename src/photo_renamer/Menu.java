package photo_renamer;

import java.awt.event.ActionEvent;
import java.util.Set;

/**
 * This is our Servant pattern. The classes that implement this interface (CheckBoxMenu, 
 * RevertNameMenu, TagsCheckBoxMenu) solely do work for other classes.
 * @author osama
 *
 */

public interface Menu {
	
	/**
	 * Add series of options to GUI
	 * @param obj the objects to be added
	 */
	public void fillArray(Set<String> option);
	
	/**
	 * Process the even performed.
	 * 
	 * @param e the event
	 */
	public void actionPerformed(ActionEvent e);

}
