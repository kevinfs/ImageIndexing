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

				case 2:
					imageDescriptor.setColorType(currentLine);
					break;

				case 3:
					imageDescriptor.setGradientMean(Double
							.parseDouble(currentLine));
					break;

				case 4:
					imageDescriptor.setRatioTexture(Double
							.parseDouble(currentLine));
					break;

				case 5:
					imageDescriptor.setRatioR(Double.parseDouble(currentLine));
					break;

				case 6:
					imageDescriptor.setRatioG(Double.parseDouble(currentLine));
					break;

				case 7:
					imageDescriptor.setRatioB(Double.parseDouble(currentLine));
					break;

				case 8:
					imageDescriptor.setHistogram(extractHistogram(currentLine));
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

	Histogram<Integer> extractHistogram(String histogramLine) {
		String valuesS[] = histogramLine.split(" ");
		Integer values[] = new Integer[valuesS.length];

		int i = 0;
		for (String string : valuesS) {
			values[i] = Integer.valueOf(string);
			i++;
		}

		Histogram<Integer> histogram = new Histogram<Integer>(values.length,
				values);
		return histogram;
	}

}
