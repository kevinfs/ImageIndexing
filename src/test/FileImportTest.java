package test;

import java.util.List;

import app.FileImageDescriptorImporter;
import app.ImageDescriptor;

public class FileImportTest {

	public static void main(String[] args) {

		FileImageDescriptorImporter fidi = new FileImageDescriptorImporter();
		ImageDescriptor iD = fidi.importImageDescriptors("vache1-descriptors.txt");

	}

}
