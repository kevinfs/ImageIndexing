package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import app.FileSystemUtility;

public class MainPanel extends JPanel implements ListSelectionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3967694204253567558L;
	private JLabel picture;
	private JList queryList;
	private JSplitPane splitPane;
	// private String[] imageNames = {
	// "C:\\Users\\Mehdi\\Documents\\DJMaxTrilogy\\DJMax@141122_203220.jpg" ,
	// "C:\\Users\\Mehdi\\Documents\\DJMaxTrilogy\\DJMax@141122_203223.jpg"};
	private static java.util.List<String> results = new ArrayList<String>();
	private ArrayList<String> query = new ArrayList<String>();
	private DefaultListModel<String> queryListModel = new DefaultListModel<String>();
	private DefaultListModel<String> imgListModel = new DefaultListModel<String>();
	private JScrollPane listScroller;
	private JScrollPane listScrollPane;
	private JPanel infosPanel;

	public void listUpdate() {
		queryListModel.clear();
		imgListModel.clear();
		for (String tmp : results) {
			queryListModel.addElement(tmp);
			// queryList.setSelectedValue(tmp, true);
		}
		queryList.setModel(queryListModel);
		/*
		 * queryList = new JList(results.toArray(new String[0]));
		 * queryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		 * queryList.setSelectedIndex(0);
		 * queryList.addListSelectionListener(this);
		 */
		// listScrollPane.setViewportView(new JList<String>(queryListModel));
		/*
		 * queryList.setSelectedIndex(0);
		 * queryList.addListSelectionListener(this);
		 */
	}

	public MainPanel() {
		// TODO Auto-generated constructor stub
		results = FileSystemUtility.getAllImagesNames();
		queryList = new JList(results.toArray());
		queryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		queryList.setSelectedIndex(0);
		queryList.addListSelectionListener(this);
		queryList.addMouseListener(mouseListener);

		listScrollPane = new JScrollPane(queryList);
		picture = new JLabel();
		picture.setFont(picture.getFont().deriveFont(Font.ITALIC));
		picture.setHorizontalAlignment(JLabel.CENTER);
		
		infosPanel = new InfosPanel();
		
		listScroller = new JScrollPane(picture);

		Dimension minimumSize = new Dimension(200, 150);

		// Create a split pane with the two scroll panes in it.
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScrollPane,
				infosPanel);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(150);

		// Provide minimum sizes for the two components in the split pane.
		listScrollPane.setMinimumSize(minimumSize);
		listScroller.setMinimumSize(minimumSize);

		// Provide a preferred size for the split pane.
		splitPane.setPreferredSize(new Dimension(800, 400));

		// updateLabel(imageNames[queryList.getSelectedIndex()]);
	}

	public java.util.List<String> getResults() {
		return results;
	}

	public void setResults(List<String> results2) {
		this.results = results2;
		listUpdate();
	}

	// public String[] getImageNames() {
	// return imageNames;
	// }
	//
	// public void setImageNames(String[] imageNames) {
	// this.imageNames = imageNames;
	// }

	public JList getImageList() {
		return queryList;
	}

	public JSplitPane getSplitPane() {
		return splitPane;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		JList list = (JList) e.getSource();
		// System.out.println(list.getSelectedIndex() + " " +results.size() );
		if (results.size() < list.getSelectedIndex()
				|| list.getSelectedIndex() < 0) {
			updateLabel(results.toArray(new String[0])[0]);
		} else
			updateLabel(results.toArray(new String[0])[list.getSelectedIndex()]);
	}

	protected void updateLabel(String path) {
		ImageIcon icon = new ImageIcon(path);
		System.out.println(path);
		picture.setIcon(icon);
	}

	MouseListener mouseListener = new MouseAdapter() {
		public void mouseClicked(MouseEvent mouseEvent) {
			JList theList = (JList) mouseEvent.getSource();
			if (mouseEvent.getClickCount() == 2) {
				int index = theList.locationToIndex(mouseEvent.getPoint());
				if (index >= 0) {
					Object o = theList.getModel().getElementAt(index);
					System.out.println("Double-clicked on: " + o.toString());
				}
			}
		}
	};

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("SplitPaneDemo");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				MainPanel splitPaneDemo = new MainPanel();
				// ArrayList<String> test = new ArrayList<String>();
				// test.add("C:\\Users\\Mehdi\\Documents\\DJMaxTrilogy\\DJMax@141122_203220.jpg");
				// test.add("C:\\Users\\Mehdi\\Documents\\DJMaxTrilogy\\DJMax@141122_203223.jpg");
				splitPaneDemo.setResults(results);
				frame.getContentPane().add(splitPaneDemo.getSplitPane());
				// Display the window.
				frame.pack();
				frame.setVisible(true);
			}
		});
	}

}
