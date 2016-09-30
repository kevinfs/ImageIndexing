package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import app.FileImageDescriptorImporter;
import app.FileSystemUtility;
import app.ImageDescriptor;

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

		textInfosPanel.add(fileNameLabel);
		textInfosPanel.add(meanLabel);
		textInfosPanel.add(rLabel);
		textInfosPanel.add(gLabel);
		textInfosPanel.add(bLabel);
		textInfosPanel.add(picLabel);
		add(textInfosPanel, BorderLayout.CENTER);
		add(imageProcessingButton, BorderLayout.NORTH);

	}

	public static void updateFile(String filename) {

		FileImageDescriptorImporter fidi = new FileImageDescriptorImporter();
		ImageDescriptor iD = fidi.importImageDescriptors(filename);

		fileNameLabel.setText(iD.getFileName());
		meanLabel.setText(String.valueOf(iD.getGradientMean()));
		rLabel.setText(String.valueOf(iD.getRatioR()));
		gLabel.setText(String.valueOf(iD.getRatioG()));
		bLabel.setText(String.valueOf(iD.getRatioB()));

		BufferedImage myPicture;
		try {
			System.out.println(FileSystemUtility.imageDir + filename);
			myPicture = ImageIO.read(new File(FileSystemUtility.imageDir
					+ filename));
			picLabel.setIcon(new ImageIcon(myPicture));
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}

}
