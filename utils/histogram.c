#include "histogram.h"

int * histogram1Channel(byte **image, long nrl, long nrh, long ncl, long nch){
	int hist[256] = { 0 };
	int i = 0;
	int x, y; 
	for (x = nrl; x <= nrh; x++){
		for (y = ncl; y <= nch; y++){
			hist[image[x][y]]++;
		}
	}
	return hist;
}

int ** histogram3Channel(rgb8 **imageRGB, long nrl, long nrh, long ncl, long nch){
	int hist[3][256] = { { 0 } };
	int x, y;
	int r = 0;
	int g = 1;
	int b = 2;
	for (x = nrl; x <= nrh; x++){
		for (y = ncl; y <= nch; y++){
			hist[r][imageRGB[x][y].r]++;
			hist[g][imageRGB[x][y].g]++;
			hist[b][imageRGB[x][y].b]++;
		}
	}
	return hist;
}
