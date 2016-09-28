package app;

import java.util.List;

public abstract class ImageDescriptorImporter {

	abstract ImageDescriptor importImageDescriptors(String imageName);

	abstract List<ImageDescriptor> importImagesDescriptors(List<String> imagesNames);

}
