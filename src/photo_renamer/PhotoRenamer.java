package photo_renamer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

public class PhotoRenamer extends JPanel implements ActionListener {
	
	JButton done;
	JButton openButton;
	JButton logButton;
	JFileChooser chooser;
	JTextArea log;
	PhotoRenamer singleton;
	protected static final String newLine = System.getProperty("line.separator");
    private static final PhotoRenamer instance = new PhotoRenamer();


	/**
	 * Creates a simply GUI allowing the user to either view log of PhotoRenamer or 
	 * choose an image to work with.
	 */
	private PhotoRenamer() { // SINGLETON PATTERN, every class will work 
							// with same instance PhotoRenamer
		super(new BorderLayout());

		log = new JTextArea(10, 30);
		log.setMargin(new Insets(5, 5, 5, 5));
		log.setEditable(false);
		JScrollPane logScrollPane = new JScrollPane(log);

		openButton = new JButton("Select an image");
		openButton.addActionListener(this);

		logButton = new JButton("View program log");
		logButton.addActionListener(this);
		
		done = new JButton("End program");
		done.addActionListener(this);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(openButton);
		buttonPanel.add(logButton);
		buttonPanel.add(done);

		add(buttonPanel, BorderLayout.SOUTH);
		add(logScrollPane, BorderLayout.CENTER);
		
		chooser = new JFileChooser();
		chooser.setPreferredSize(new Dimension(650, 450));
		chooser.addChoosableFileFilter(new ImageFilter());
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setApproveButtonText("Done");

		chooser.setFileView(new ImageFileView());
		
		JPanel imagePanel = new JPanel();
		imagePanel.setLayout(new BorderLayout());
		imagePanel.add(new ImagePreview(chooser), BorderLayout.CENTER);
		imagePanel.add(new ImageOptions(chooser), BorderLayout.SOUTH);
		
		chooser.setAccessory(imagePanel);

		log.append("Welcome to PhotoRenamer.\nPlease select an image to begin.\n\n");
	}
	
	public PhotoRenamer getInstance(){
		return instance;
	}

	/**
	 * Launches the GUI for PhotoRenamer.
	 */
	public static void createGUI() {
		JFrame frame = new JFrame("PhotoRenamer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new PhotoRenamer());
		frame.pack();
		frame.setVisible(true);

	}

	/**
	 * Processes the button clicks of the user. If open is clicked, displays a modified 
	 * JFileChooser, if log is clicked, shows log of the program, otherwise closes the program.
	 * 
	 * @param e the action of the user
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == openButton) {
			chooser.showOpenDialog(PhotoRenamer.this);
			
			chooser.setSelectedFile(null);
			chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		}
		if (e.getSource() == logButton) {
			ProgramLog logger = new ProgramLog();
			logger.readLog(log);
		}
		if (e.getSource() == done) {
			log.append("Thank you for using PhotoRenamer." + newLine);
			System.exit(0);
		}
	}

	/**
	 * Invokes the createGUI method.
	 * 
	 * @param args command-line arguments, not expected
	 * @return
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			/* (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			public void run() {
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				createGUI();
			}
		});
	}
}
