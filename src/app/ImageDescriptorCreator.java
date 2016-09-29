package app;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageDescriptorCreator {

	public static void createImageDescriptorFile(String imageFilename, int seuil) {

		final Path basedir = Paths
				.get(FileSystemUtility.imageProcessingDirectory);
		final Path stdout = Paths.get("somewheretooutputstdout");
		final Path stderr = Paths.get("somewheretooutputstderr");

		final ProcessBuilder builder = new ProcessBuilder(
				FileSystemUtility.imageProcessingExecutable, "-cxhm", "-t",
				String.valueOf(seuil), FileSystemUtility.imageDir + imageFilename);

		builder.directory(basedir.toFile());
		//builder.redirectOutput(stdout.toFile());
		//builder.redirectError(stderr.toFile());

		try {
			final Process process = builder.start();
			System.out.println("done");
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
		System.out.println("end");

	}

}
