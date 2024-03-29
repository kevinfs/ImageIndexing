#include "contourDetection.h"

/**
 * Only square filters
 */
void convolution(byte ** src, long height, long width, int ** filtre, int filter_size, byte ** dest) {
	long side = filter_size / 2;
	long max_height = height - side + 1;
	long max_width = width - side + 1;
	long r, c, rf, cf, sum = 0;
	long tmp = 0;
	long max_neg = 0, max_pos = 0;

	// find max and min values to counteract dynamic modifications
	for (rf = 0; rf < filter_size; ++rf) {
		for (cf = 0; cf < filter_size; ++cf) {
			if (filtre[rf][cf] > 0)
				max_pos += filtre[rf][cf];
			else
				max_neg += filtre[rf][cf];
		}
	}
	if(max_pos > max_neg)
		sum = max_pos;
	else
		sum = -1*max_neg;
	//printf("sum %li\n", sum);

	for (r = side; r < max_height; ++r) {
		for (c = side; c < max_width; ++c) {
			tmp = 0;
			for (rf = 0; rf < filter_size; ++rf) {
				for (cf = 0; cf < filter_size; ++cf) {
					tmp += (long) (src[r-1+rf][c-1+cf] * filtre[rf][cf]);
				}
			}
			if (sum > 0)
				dest[r][c] = (byte) (abs(tmp) / sum);
			else
				dest[r][c] = (byte) tmp;
		}
	}
}

void norme_gradient(byte ** srcH, byte ** srcV, long height, long width, byte ** dest) {

	long r, c;

	for (r = 0; r < height; ++r) {
		for (c = 0; c < width; ++c) {
			dest[r][c] = sqrt(pow(srcH[r][c], 2) + pow(srcV[r][c], 2));
		}
	}

}

double getGradientMean(byte ** src, long height, long width) {

	long r, c;
	double total = 0.0;

	for (r = 0; r < height; ++r) {
		for (c = 0; c < width; ++c) {
			total+= src[r][c];
		}
	}

	return total / (height * width);

}

void seuillage(byte ** src, long height, long width, byte seuil) {

	long r, c;

	for (r = 0; r < height; ++r) {
		for (c = 0; c < width; ++c) {
			if (src[r][c] >= seuil)
				src[r][c] = 255;
			else
				src[r][c] = 0;
		}
	}

}

long getNumberOfContourPixels(byte ** src, long height, long width) {

	long r, c, result = 0;

	for (r = 0; r < height; ++r) {
		for (c = 0; c < width; ++c) {
			if (src[r][c] > 0)
				result++;
		}
	}

	return result;

}

long getTotalNumberOfPixels(byte ** src, long height, long width) {

	return height * width;

}

double getImageTexture(byte ** src, long height, long width) {
	long p = getNumberOfContourPixels(src, height, width);
	long n = getTotalNumberOfPixels(src, height, width);

	return (double) p / (double) n;
}








