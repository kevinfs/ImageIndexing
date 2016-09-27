#include "computeRateRGB.h"

TauxRGB computeTauxRGB(rgb8 **imageRGB, long nrl, long nrh, long ncl, long nch) {

	int y, x, i, nbPixels;
	TauxRGB tauxRGB;
	double sumR = 0, sumG = 0, sumB = 0, moyR, moyG, moyB;

	for(y=nrl;y<=nrh;y++){
		for(x=ncl;x<=nch;x++){
			sumR += imageRGB[y][x].r;
			sumG += imageRGB[y][x].g;
			sumB += imageRGB[y][x].b;		
		}
	}

	nbPixels = x * y;
	
	moyR = sumR/nbPixels;
	moyG = sumG/nbPixels;
	moyB = sumB/nbPixels;
	
	return tauxRGB;
}