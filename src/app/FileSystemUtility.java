package app;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class FileSystemUtility {

	static public String basename = System.getProperty("user.dir");
	static public String imageDir = basename + "/utils/images/";
	static public String imageProcessingDirectory = basename + "/utils/";
	static public String imageProcessingExecutable = basename + "/utils/imageProcessing";

	public static List<String> getAllImagesNames() {
		List<String> result = new ArrayList<String>();

		File dir = new File(imageDir);
		File[] files = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".jpg");
			}
		});

		for (File file : files) {
			result.add(file.getName());
		}

		return result;
	}
}