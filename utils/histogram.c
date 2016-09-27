#include "histogram.h"

int* histogram1Channel(byte **image, long nrl, long nrh, long ncl, long nch){
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

int** histogram3Channel(rgb8 **imageRGB, long nrl, long nrh, long ncl, long nch){
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

double getBhattacharyyaDistance(int *h1, int *h2, int nbPixels){

	double sumh1 = 0, sumh2 = 0, multsumh1h2, BC = 0.0, result;
	int i;
	
	for(i = 0; i < 256; i++){
		sumh1 += ((double)h1[i]/nbPixels);
		sumh2 += ((double)h2[i]/nbPixels);
	}

	multsumh1h2 = sumh1*sumh2;
	
	for(i = 0; i < 256; i++){
		BC += sqrt( ( ((double)(h1[i])/nbPixels)*((double)(h2[i])/nbPixels) )/multsumh1h2 );
	}
	printf("bc : %f\n", BC);
	printf("%f\n", log(1));
	result = -log(BC);
	printf("result = %f\n", result);

return result;
}

