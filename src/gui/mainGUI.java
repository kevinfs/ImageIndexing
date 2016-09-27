package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


public class mainGUI extends JFrame{
	
	private ArrayList<Integer> a=new ArrayList<Integer>();
	final JFileChooser fc = new JFileChooser();
	private mainGUI instance = this;
	JLabel picLabel;
	JPanel panImgReciev = new JPanel();
	// Menu	
	private JMenuBar menuBar = new JMenuBar();
	private JMenu file = new JMenu("File");
	
	private JMenuItem load = new JMenuItem("Load File");
	private JMenuItem exit = new JMenuItem("Exit");
	
	public mainGUI(String name) throws HeadlessException {
		super(name);
		FileFilter type1 = new FileNameExtensionFilter("PGM File (.pgm)", "pgm");
    	FileFilter type2 = new FileNameExtensionFilter("PPM File (.ppm)", "ppm");
    	FileFilter type3 = new FileNameExtensionFilter("JPG File (.jpg)", "jpg");
		fc.setFileFilter(type1);
		fc.setFileFilter(type2);
		fc.setFileFilter(type3);
		fc.setAcceptAllFileFilterUsed(false);
		fc.setMultiSelectionEnabled(false);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//this.setSize(400, 100);
		this.setLayout(new BorderLayout());
		this.getContentPane().add(panImgReciev,BorderLayout.CENTER);
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
						if(file.getAbsolutePath().endsWith(".pgm")){
							paintComponent(panImgReciev.getGraphics(), file.getAbsolutePath());
							System.out.println("PGM file was loaded !");
						}
						else if(file.getAbsolutePath().endsWith(".ppm")){
							paintComponent(panImgReciev.getGraphics(), file.getAbsolutePath());
							System.out.println("PPM file was loaded !");
						}
						else if(file.getAbsolutePath().endsWith(".jpg")){
							paintComponent(panImgReciev.getGraphics(), file.getAbsolutePath());
							System.out.println("JPG file was loaded !");
						}
						System.out.println("Path: "+file.getAbsolutePath());
						repaint();
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
				} else {}
			}
		});
		
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					int rep = JOptionPane.showConfirmDialog(instance,"Do you really want to quit?","Exit",JOptionPane.YES_NO_OPTION);
					 
					if (rep==JOptionPane.YES_OPTION){
						System.exit(0);
						}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		});
	}
	
	private void initMenu(){
		file.add(load);
		file.add(exit);
		menuBar.add(file);
		setJMenuBar(menuBar);
	}
	
	public void paintComponent(Graphics g, String path){
	    try {
	      Image img = ImageIO.read(new File(path));
	      g.drawImage(img, 0, 0, null);
	      picLabel = new JLabel(new ImageIcon(img));
	      panImgReciev.add(picLabel);
	      panImgReciev.repaint();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }                
	 }     
	
	public static void main(String[] args){
		
		mainGUI win = new mainGUI("test");
	}

}
