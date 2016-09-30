package app;

import java.util.List;

public class DBExporter {

	public static void exportOneImage(String imageFilename) {

		FileImageDescriptorImporter fidi = new FileImageDescriptorImporter();
		ImageDescriptor iD = fidi.importImageDescriptors(imageFilename);

		OJDBC db = new OJDBC();

		db.addImageData(iD.getFileName(), iD.getRatioR(), iD.getRatioG(), iD
				.getRatioB(), iD.getPixelNumber(), 1, iD.getHistogram()
				.getAll());

	}

	public static void exportAllImages() {

		List<String> names = FileSystemUtility.getAllImagesNames();

		for (String name : names) {
			exportOneImage(name);
		}

	}

}
