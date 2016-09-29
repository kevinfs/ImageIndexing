package app;

/**
 * Our very own image descriptor
 *
 */
public class ImageDescriptor {

	private String fileName;
	private String colorType;
	private double gradientMean;
	private double ratioTexture;
	private double ratioR;
	private double ratioG;
	private double ratioB;
	private Histogram<Integer> histogram;

	public ImageDescriptor() {
		super();
	}

	public ImageDescriptor(String fileName, String colorType,
			double gradientMean, double ratioTexture, double ratioR,
			double ratioG, double ratioB, Histogram<Integer> histogram) {
		super();
		this.fileName = fileName;
		this.colorType = colorType;
		this.gradientMean = gradientMean;
		this.ratioTexture = ratioTexture;
		this.ratioR = ratioR;
		this.ratioG = ratioG;
		this.ratioB = ratioB;
		this.histogram = histogram;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the colorType
	 */
	public String getColorType() {
		return colorType;
	}

	/**
	 * @param colorType
	 *            the colorType to set
	 */
	public void setColorType(String colorType) {
		this.colorType = colorType;
	}

	/**
	 * @return the gradientMean
	 */
	public double getGradientMean() {
		return gradientMean;
	}

	/**
	 * @param gradientMean
	 *            the gradientMean to set
	 */
	public void setGradientMean(double gradientMean) {
		this.gradientMean = gradientMean;
	}

	/**
	 * @return the ratioTexture
	 */
	public double getRatioTexture() {
		return ratioTexture;
	}

	/**
	 * @param ratioTexture
	 *            the ratioTexture to set
	 */
	public void setRatioTexture(double ratioTexture) {
		this.ratioTexture = ratioTexture;
	}

	/**
	 * @return the ratioR
	 */
	public double getRatioR() {
		return ratioR;
	}

	/**
	 * @param ratioR
	 *            the ratioR to set
	 */
	public void setRatioR(double ratioR) {
		this.ratioR = ratioR;
	}

	/**
	 * @return the ratioG
	 */
	public double getRatioG() {
		return ratioG;
	}

	/**
	 * @param ratioG
	 *            the ratioG to set
	 */
	public void setRatioG(double ratioG) {
		this.ratioG = ratioG;
	}

	/**
	 * @return the ratioB
	 */
	public double getRatioB() {
		return ratioB;
	}

	/**
	 * @param ratioB
	 *            the ratioB to set
	 */
	public void setRatioB(double ratioB) {
		this.ratioB = ratioB;
	}

	/**
	 * @return the histogram
	 */
	public Histogram<Integer> getHistogram() {
		return histogram;
	}

	/**
	 * @param histogram
	 *            the histogram to set
	 */
	public void setHistogram(Histogram<Integer> histogram) {
		this.histogram = histogram;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ImageDescriptor [fileName=" + fileName + ", colorType="
				+ colorType + ", gradientMean=" + gradientMean
				+ ", ratioTexture=" + ratioTexture + ", ratioR=" + ratioR
				+ ", ratioG=" + ratioG + ", ratioB=" + ratioB + ", histogram="
				+ histogram + "]";
	}

}
