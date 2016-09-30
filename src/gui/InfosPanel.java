package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
	private static JLabel oracleColor;
	private static JLabel oracleTexture;
	private static JLabel oracleShape;
	private static JLabel oracleLocation;
	private static JLabel oracleSeuil;
	private static JTextField oracleColorText;
	private static JTextField oracleTextureText;
	private static JTextField oracleShapeText;
	private static JTextField oracleLocationText;
	private static JTextField oracleSeuilText;
	private static JButton oracleButton;
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

		textureLabel = new JLabel();
		textureLabel.setText("mean");

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
		textInfosPanel.add(textureLabel);
		textInfosPanel.add(rLabel);
		textInfosPanel.add(gLabel);
		textInfosPanel.add(bLabel);
		textInfosPanel.add(picLabel);
		textInfosPanel.add(picLabelNorme);
		textInfosPanel.add(picLabelSeuille);
		scroller = new JScrollPane(textInfosPanel);
		scroller.getVerticalScrollBar().setUnitIncrement(16);

		batthaLabel = new JLabel("Selectionnez le seuil");
		batthaText = new JTextField("0.05", 5);
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

		oracleColor = new JLabel("Color");
		oracleColorText = new JTextField("0.5", 3);
		oracleTexture = new JLabel("Texture");
		oracleTextureText = new JTextField("0", 3);
		oracleShape = new JLabel("Shape");
		oracleShapeText = new JTextField("0", 3);
		oracleLocation = new JLabel("Location");
		oracleLocationText = new JTextField("0", 3);
		oracleSeuil = new JLabel("Seuil");
		oracleSeuilText = new JTextField("10", 3);
		oracleButton = new JButton("Signature Oracle");
		oracleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				oracle();
			}
		});

		oraclePanel = new JPanel(new FlowLayout());
		oraclePanel.add(oracleTexture);
		oraclePanel.add(oracleTextureText);
		oraclePanel.add(oracleShape);
		oraclePanel.add(oracleShapeText);
		oraclePanel.add(oracleLocation);
		oraclePanel.add(oracleLocationText);
		oraclePanel.add(oracleSeuil);
		oraclePanel.add(oracleSeuilText);
		oraclePanel.add(oracleButton);

		resultPanel = new JPanel(new GridLayout(0, 4));
		resultPanel.add(new JLabel("Images similaires :"));

		scroller2 = new JScrollPane(resultPanel);
		scroller2.getVerticalScrollBar().setUnitIncrement(16);

		formPanel = new JPanel(new BorderLayout());
		formPanel.add(batthaPanel, BorderLayout.NORTH);
		formPanel.add(oraclePanel, BorderLayout.CENTER);
		formPanel.add(scroller2, BorderLayout.SOUTH);
		formPanel.setMaximumSize(new Dimension(200, getHeight()));

		add(scroller, BorderLayout.CENTER);
		add(imageProcessingButton, BorderLayout.NORTH);
		add(formPanel, BorderLayout.LINE_END);

	}

	public static void updateFile(String filename) {

		resultPanel.removeAll();

		FileImageDescriptorImporter fidi = new FileImageDescriptorImporter();
		iD = fidi.importImageDescriptors(filename);

		fileNameLabel.setText(iD.getFileName());
		meanLabel.setText(String.valueOf(iD.getGradientMean()));
		textureLabel.setText(String.valueOf(iD.getRatioTexture()));
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

		resultPanel.removeAll();

		for (String filename : similar) {

			BufferedImage myPicture;
			try {
				myPicture = ImageIO.read(new File(FileSystemUtility.imageDir
						+ filename));
				JLabel img = new JLabel(new ImageIcon(myPicture));
				resultPanel.add(img);
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}

		resultPanel.updateUI();
		scroller2.updateUI();
		formPanel.updateUI();

	}

	public static void oracle() {

		final OJDBC dB = new OJDBC();
		ArrayList<String> similar = dB.signature(iD.getFileName(),
				Double.valueOf(oracleColorText.getText()),
				Double.valueOf(oracleTextureText.getText()),
				Double.valueOf(oracleShapeText.getText()),
				Double.valueOf(oracleLocationText.getText()),
				Double.valueOf(oracleSeuilText.getText()));

		resultPanel.removeAll();

		for (String filename : similar) {

			BufferedImage myPicture;
			try {
				myPicture = ImageIO.read(new File(FileSystemUtility.imageDir
						+ filename));
				JLabel img = new JLabel(new ImageIcon(myPicture));
				resultPanel.add(img);
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}

		resultPanel.updateUI();
		scroller2.updateUI();
		formPanel.updateUI();

	}

}
