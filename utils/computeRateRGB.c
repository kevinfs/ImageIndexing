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
	
	tauxRGB.tauxR = moyR;
	tauxRGB.tauxG = moyG;
	tauxRGB.tauxB = moyB;

	return tauxRGB;
}

TauxRGB computeRGBLevel(rgb8 **imageRGB, long nrl, long nrh, long ncl, long nch){
	
	int x, y;
	double sumR = 0.0, sumG = 0.0, sumB = 0.0;
	double sum = 0.0;
	TauxRGB tRGB;
	int borneInf = 150, borneSup = 150;
	for (y = nrl; y <= nrh; y++){
		for (x = ncl; x <= nch; x++){
			if ((imageRGB[y][x].r > borneSup) && (imageRGB[y][x].r > 1.2 * imageRGB[y][x].g) && (imageRGB[y][x].r > 1.2 * imageRGB[y][x].b) && (borneInf > imageRGB[y][x].g) && (borneInf > imageRGB[y][x].b))
				sumR++;
			if ((imageRGB[y][x].g > borneSup) && (imageRGB[y][x].g > 1.2 * imageRGB[y][x].r) && (imageRGB[y][x].g > 1.2 * imageRGB[y][x].b) && (borneInf > imageRGB[y][x].r) && (borneInf > imageRGB[y][x].b))
				sumG++;
			if ((imageRGB[y][x].b > borneSup) && (imageRGB[y][x].b > 1.2 * imageRGB[y][x].g) && (imageRGB[y][x].b > 1.2 * imageRGB[y][x].r) && (borneInf > imageRGB[y][x].r) && (borneInf > imageRGB[y][x].g))
				sumB++;
			//else
			sum++;
		}
	}
	//sum += sumR + sumG + sumB;
	tRGB.tauxR = sumR / sum;
	tRGB.tauxG = sumG / sum;
	tRGB.tauxB = sumB / sum;
	return tRGB;
}
