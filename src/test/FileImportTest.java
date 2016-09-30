package test;

import java.util.List;

import app.DBExporter;
import app.FileImageDescriptorImporter;
import app.FileSystemUtility;
import app.ImageDescriptor;
import app.ImageDescriptorCreator;

public class FileImportTest {

	public static void main(String[] args) {

		FileImageDescriptorImporter fidi = new FileImageDescriptorImporter();
		//ImageDescriptor iD = fidi.importImageDescriptors("arbre1.jpg");

		//System.out.println(iD.toString());

		List<String> names = FileSystemUtility.getAllImagesNames();
		for (String name : names) {
			System.out.println(name);
		}

		//ImageDescriptorCreator.createImageDescriptorFile("arbre1.jpg", 30);

		//ImageDescriptorCreator.createAllImagesDescriptorFile(30);

		DBExporter.exportAllImages();
	}

}
