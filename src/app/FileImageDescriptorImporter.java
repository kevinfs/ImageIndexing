package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class FileImageDescriptorImporter extends ImageDescriptorImporter {

	@Override
	ImageDescriptor importImageDescriptors(String imageName) {

		// Does file exists ?
		
		ImageDescriptor imageDescriptor = new ImageDescriptor();
		
		BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader("C:\\testing.txt"));

			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return imageDescriptor;

	}

	@Override
	List<ImageDescriptor> importImagesDescriptors(List<String> imagesNames) {
		return null;
	}

}
