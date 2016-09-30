package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import app.FileImageDescriptorImporter;
import app.FileSystemUtility;
import app.ImageDescriptor;
import app.OJDBC;

public class InfosPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6441487146102273234L;
	private static JPanel textInfosPanel;
	private static JButton imageProcessingButton;
	private static JLabel fileNameLabel;
	private static JLabel meanLabel;
	private static JLabel textureLabel;
	private static JLabel rLabel;
	private static JLabel gLabel;
	private static JLabel bLabel;
	private static JLabel picLabel;
	private static JLabel picLabelNorme;
	private static JLabel picLabelSeuille;
	private static JScrollPane scroller;
	private static JScrollPane scroller2;
	private static JPanel formPanel;
	private static JPanel batthaPanel;
	private static JPanel oraclePanel;
	private static JPanel resultPanel;
	private static JLabel batthaLabel;
	private static JTextField batthaText;
	private static JButton batthaButton;
	private static ImageDescriptor iD;

	public InfosPanel() {
		super(new BorderLayout());

		textInfosPanel = new JPanel();
		textInfosPanel.setLayout(new BoxLayout(textInfosPanel,
				BoxLayout.PAGE_AXIS));

		imageProcessingButton = new JButton();

		fileNameLabel = new JLabel();
		fileNameLabel.setText("file");

		meanLabel = new JLabel();
		meanLabel.setText("mean");

		rLabel = new JLabel();
		rLabel.setText("red");
		rLabel.setForeground(Color.red);

		gLabel = new JLabel();
		gLabel.setText("green");
		gLabel.setForeground(Color.green);

		bLabel = new JLabel();
		bLabel.setText("blue");
		bLabel.setForeground(Color.blue);

		picLabel = new JLabel();
		picLabelNorme = new JLabel();
		picLabelSeuille = new JLabel();

		textInfosPanel.add(fileNameLabel);
		textInfosPanel.add(meanLabel);
		textInfosPanel.add(rLabel);
		textInfosPanel.add(gLabel);
		textInfosPanel.add(bLabel);
		textInfosPanel.add(picLabel);
		textInfosPanel.add(picLabelNorme);
		textInfosPanel.add(picLabelSeuille);
		scroller = new JScrollPane(textInfosPanel);
		scroller.getVerticalScrollBar().setUnitIncrement(16);

		batthaLabel = new JLabel("SŽlectionnez le seuil");
		batthaText = new JTextField("0.0005", 5);
		batthaButton = new JButton("Distance de Bhattacharya");
		batthaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				battha();
			}
		});

		batthaPanel = new JPanel(new FlowLayout());
		batthaPanel.add(batthaLabel);
		batthaPanel.add(batthaText);
		batthaPanel.add(batthaButton);
		batthaPanel.setMaximumSize(new Dimension(200, getHeight()));

		oraclePanel = new JPanel(new FlowLayout());
		resultPanel = new JPanel(new FlowLayout());

		scroller2 = new JScrollPane(resultPanel);
		scroller2.getVerticalScrollBar().setUnitIncrement(16);

		formPanel = new JPanel();
		formPanel.add(batthaPanel, BorderLayout.NORTH);
		formPanel.add(oraclePanel, BorderLayout.CENTER);
		formPanel.add(scroller2, BorderLayout.SOUTH);
		formPanel.setMaximumSize(new Dimension(200, getHeight()));

		add(scroller, BorderLayout.CENTER);
		add(imageProcessingButton, BorderLayout.NORTH);
		add(formPanel, BorderLayout.LINE_END);

	}

	public static void updateFile(String filename) {

		FileImageDescriptorImporter fidi = new FileImageDescriptorImporter();
		iD = fidi.importImageDescriptors(filename);

		fileNameLabel.setText(iD.getFileName());
		meanLabel.setText(String.valueOf(iD.getGradientMean()));
		rLabel.setText(String.valueOf(iD.getRatioR()));
		gLabel.setText(String.valueOf(iD.getRatioG()));
		bLabel.setText(String.valueOf(iD.getRatioB()));

		BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(new File(FileSystemUtility.imageDir
					+ filename));
			picLabel.setIcon(new ImageIcon(myPicture));

			String imageFilenameRoot = filename.substring(0,
					filename.lastIndexOf('.'));

			PlanarImage pgmImage = JAI.create("fileload",
					FileSystemUtility.imageDir + imageFilenameRoot + "-n.pgm");
			picLabelNorme.setIcon(new ImageIcon(pgmImage.getAsBufferedImage()));

			pgmImage = JAI.create("fileload", FileSystemUtility.imageDir
					+ imageFilenameRoot + "-ns.pgm");
			picLabelSeuille
					.setIcon(new ImageIcon(pgmImage.getAsBufferedImage()));
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}

	public static void battha() {
		final OJDBC dB = new OJDBC();
		ArrayList<String> similar = dB.bhattacharyaHist(iD.getFileName(),
				Double.valueOf(batthaText.getText()));
		for (String filename : similar) {

			PlanarImage pgmImage = JAI.create("fileload",
					FileSystemUtility.imageDir + filename);
			JLabel img = new JLabel(
					new ImageIcon(pgmImage.getAsBufferedImage()));
			resultPanel.add(img);
		}
	}

}
