package app;

import java.util.List;

public class DBExporter {

	public static void exportOneImage(String imageFilename) {

		try {
			FileImageDescriptorImporter fidi = new FileImageDescriptorImporter();
			ImageDescriptor iD = fidi.importImageDescriptors(imageFilename);

			OJDBC db = new OJDBC();

			db.addImageData(iD.getFileName(), iD.getRatioR(), iD.getRatioG(),
					iD.getRatioB(), iD.getPixelNumber(), 1, iD.getHistogram()
							.getAll());
		} catch (NullPointerException e) {
			System.err.println(e.getMessage() + " for file " + imageFilename);
		}

	}

	public static void exportAllImages() {

		List<String> names = FileSystemUtility.getAllImagesNames();

		for (String name : names) {
			exportOneImage(name);
		}

	}

}
