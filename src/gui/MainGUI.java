package gui;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import app.FileSystemUtility;

public class MainGUI extends JFrame {

	private ArrayList<Integer> a = new ArrayList<Integer>();
	final JFileChooser fc = new JFileChooser();
	private MainGUI instance = this;
	private MainPanel mPanel;
	private MainPanel mPanel2;
	// Menu
	private JMenuBar menuBar = new JMenuBar();
	private JMenu file = new JMenu("File");

	private JMenuItem load = new JMenuItem("Load File");
	private JMenuItem exit = new JMenuItem("Exit");

	public MainGUI(String name) throws HeadlessException {
		super(name);
		FileFilter type1 = new FileNameExtensionFilter("PGM File (.pgm)", "pgm");
		FileFilter type2 = new FileNameExtensionFilter("PPM File (.ppm)", "ppm");
		FileFilter type3 = new FileNameExtensionFilter("JPG File (.jpg)", "jpg");
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fc.setCurrentDirectory(new File(FileSystemUtility.imageDir));
		fc.setFileFilter(type1);
		fc.setFileFilter(type2);
		fc.setFileFilter(type3);
		fc.setAcceptAllFileFilterUsed(false);
		fc.setMultiSelectionEnabled(true);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// this.setSize(400, 100);
		this.setVisible(true);
		initMenu();
		this.pack();

		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int returnVal = fc.showOpenDialog(instance);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					try {
						String[] extension;
						FileNameExtensionFilter tmp = (FileNameExtensionFilter) fc
								.getFileFilter();
						extension = tmp.getExtensions();
						ArrayList<String> results = new ArrayList<String>();
						if (file.isDirectory()) {
							File[] listOfFiles = file.listFiles();
							results.clear();
							for (File filetmp : listOfFiles) {
								if (filetmp.isFile()
										&& filetmp.getAbsolutePath().endsWith(
												extension[0])) {
									results.add(filetmp.getAbsolutePath());
									mPanel.setResults(results);
									System.out.println("file "
											+ filetmp.getName()
											+ "was loaded !" + extension[0]);
									System.out.println(filetmp
											.getAbsolutePath());
								}
							}
						} else {
							results.clear();
							results.add(file.getAbsolutePath());
							mPanel.setResults(results);
							if (file.getAbsolutePath().endsWith(".pgm")) {
								System.out.println("PGM file was loaded !");
								System.out.println("PGM file was loaded !"
										+ extension[0]);
							} else if (file.getAbsolutePath().endsWith(".ppm")) {
								System.out.println("PPM file was loaded !");
							} else if (file.getAbsolutePath().endsWith(".jpg")) {
								System.out.println("JPG file was loaded !");
							}
							// System.out.println("Path: "+file.getAbsolutePath());
							repaint();
						}
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
				} else {
				}
			}
		});

		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					int rep = JOptionPane.showConfirmDialog(instance,
							"Do you really want to quit?", "Exit",
							JOptionPane.YES_NO_OPTION);

					if (rep == JOptionPane.YES_OPTION) {
						System.exit(0);
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		});
	}

	private void initMenu() {
		file.add(load);
		file.add(exit);
		menuBar.add(file);
		setJMenuBar(menuBar);
		mPanel = new MainPanel();
		mPanel2 = new MainPanel();
		// JSplitPane splitPane = new
		// JSplitPane(JSplitPane.VERTICAL_SPLIT,mPanel.getSplitPane(),mPanel2.getSplitPane());

		getContentPane().add(mPanel);
		pack();
		setVisible(true);
	}

	public static void main(String[] args) {

		MainGUI win = new MainGUI("test");
	}

}
