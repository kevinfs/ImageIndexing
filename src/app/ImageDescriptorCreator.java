package app;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageDescriptorCreator {

	public static void createImageDescriptorFile(String imageFilename, int seuil) {

		final Path basedir = Paths
				.get(FileSystemUtility.imageProcessingDirectory);

		final ProcessBuilder builder = new ProcessBuilder(
				FileSystemUtility.imageProcessingExecutable, "-cxhm", "-t",
				String.valueOf(seuil), FileSystemUtility.imageDir
						+ imageFilename);

		builder.directory(basedir.toFile());

		try {
			final Process process = builder.start();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}

}
