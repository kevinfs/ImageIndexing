package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class FileImageDescriptorImporter extends ImageDescriptorImporter {

	@Override
	public ImageDescriptor importImageDescriptors(String imageName) {

		// Does file exists ?

		ImageDescriptor imageDescriptor = new ImageDescriptor();

		BufferedReader br = null;

		try {

			String currentLine;

			String path = FileSystemUtility.imageDir + imageName;
			br = new BufferedReader(new FileReader(path));

			int lineNumber = 0;
			while ((currentLine = br.readLine()) != null) {
				System.out.println(currentLine);
				lineNumber++;
				switch (lineNumber) {
				case 1:
					imageDescriptor.setFileName(currentLine);
					break;

				case 3:
					imageDescriptor.setFileName(currentLine);
					break;

				case 4:
					imageDescriptor.setFileName(currentLine);
					break;

				case 5:
					imageDescriptor.setFileName(currentLine);
					break;

				case 6:
					imageDescriptor.setFileName(currentLine);
					break;

				case 7:
					imageDescriptor.setFileName(currentLine);
					break;

				case 8:
					imageDescriptor.setFileName(currentLine);
					break;

				case 9:
					imageDescriptor.setFileName(currentLine);
					break;

				default:
					break;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
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
