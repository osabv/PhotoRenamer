package photo_renamer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JTextArea;

class ProgramLog {
	Logger logger;

	/**
	 * Initializes a log for this program and set its level to ALL.
	 *
	 */
	public ProgramLog() {
		logger = Logger.getLogger(ProgramLog.class.getName());
		logger.setLevel(Level.ALL);
	}

	/**
	 * Updates the log every time the program changes the name of an image.
	 *
	 * @param oldName the old name of file
	 * @param newName the new name of file
	 */
	public void writeLog(String oldName, String newName) {
		FileHandler file;
		try {
			file = new FileHandler(
					Paths.get(System.getProperty("user.dir").toString(), "PhotoRenamerLogs.txt").toString(), true);
			file.setFormatter(new SimpleFormatter());
			logger.setUseParentHandlers(false);
			logger.addHandler(file);
			logger.info(String.format("Changed name from \"%s\" to \"%s\".%n", oldName, newName));
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Displays the log on the console screen for the user to view.
	 *
	 * @param texty the JCcomponnent to add text to
	 */
	public void readLog(JTextArea texty) {
		try {
			for (Object line : Files.lines(Paths.get(System.getProperty("user.dir").toString(), "PhotoRenamerLogs.txt"))
					.toArray()) {
				texty.append((String) line);
				texty.append(System.getProperty("line.separator"));
			}
		} catch (IOException e) {
			texty.append("Nothing has been logged yet.");
		}
	}
}
