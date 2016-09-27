#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <unistd.h>
#include <string.h>
#include "contourDetection.h"
#include "computeRateRGB.h"
#include "histogram.h"

int main(int argc, char * argv[]){

	long nrh,nrl,nch,ncl;
	int ** moyenneur;
	int ** sobel_h;
	int ** sobel_v;

	int i,j;


	int verbose = 0;
	int silent = 0;
	int contourDetection = 0;
	int txColor = 0;
	int histogram = 0;
	int fileType = 0; // 1 for pgm, 2 for ppm
	char * filename;
    int opt;

    while ((opt = getopt(argc, argv, "vscth")) != -1) {
        switch (opt) {
	        case 'v': verbose = 1; break;
	        case 's': silent = 1; break;
	        case 'c': contourDetection = 1; break;
	        case 't': txColor = 1; break;
	        case 'h': histogram = 1; break;
	        default:
	            fprintf(stderr, "Usage: %s [-vscth] [file...]\n", argv[0]);
	            exit(EXIT_FAILURE);
        }
    }

    if(optind < argc) {
    	filename = argv[optind];
    } else {
    	puts("Too few arguments, please choose a filename");
    	fprintf(stderr, "Usage: %s [-vscth] [file...]\n", argv[0]);
    	exit(EXIT_FAILURE);
    }

    if (verbose) {
    	puts("Verbose mode used");
    	if (silent)
    		puts("Silent mode used, intermediate images will not be saved");
    	if (contourDetection)
    		puts("Contour detection requested");
    	if (txColor)
    		puts("Color tx requested");
    	if (histogram)
    		puts("Histogram requested");
    }

	if (verbose)
		printf("Analyzing file %s...\n", filename);
    if(strstr(filename, "pgm") != NULL) {
	    fileType = 1;
	    puts("File recognized as PGM");
	} else if(strstr(filename, "ppm") != NULL) {
	    fileType = 2;
	    puts("File recognized as PPM");
	} else {
    	fprintf(stderr, "%s can only support PGM or PPM files\n", argv[0]);
    	exit(EXIT_FAILURE);
	}

	if (verbose)
		printf("Opening file %s...\n", filename);
	if (fileType == 1) {

		byte **I;
		byte **R;
		I = LoadPGM_bmatrix(filename, &nrl, &nrh, &ncl, &nch);
		R = bmatrix(nrl, nrh, ncl, nch);

		// Gradient Horizontal
		sobel_h = imatrix(0, 3, 0, 3);
		sobel_h[0][0] = sobel_h[2][0] = -1;
		sobel_h[0][2] = sobel_h[2][2] = +1;
		sobel_h[0][1] = sobel_h[1][1] = sobel_h[2][1] = 0;
		sobel_h[1][0] = -2;
		sobel_h[1][2] = +2;
		convolution(I, nrh, nch, sobel_h, 3, R);
		SavePGM_bmatrix(R, nrl, nrh, ncl, nch, "rice_ix.pgm");

		// Gradient Vertical
		sobel_v = imatrix(0, 3, 0, 3);
		sobel_v[0][0] = sobel_v[0][2] = -1;
		sobel_v[2][0] = sobel_v[2][2] = +1;
		sobel_v[1][0] = sobel_v[1][1] = sobel_v[1][2] = 0;
		sobel_v[0][1] = -2;
		sobel_v[2][1] = +2;
		convolution(I, nrh, nch, sobel_v, 3, R);
		SavePGM_bmatrix(R, nrl, nrh, ncl, nch, "rice_iy.pgm");

		// Norme du gradient
		norme_gradient(filename);

		// Seuillage
		I = LoadPGM_bmatrix("rice_norme.pgm", &nrl, &nrh, &ncl, &nch);
		seuillage(I, nrh, nch, 9);
		SavePGM_bmatrix(I, nrl, nrh, ncl, nch, "rice_norme_seuille.pgm");

		free_bmatrix(I, nrl, nrh, ncl, nch);
		free_bmatrix(R, nrl, nrh, ncl, nch);
		free_imatrix(moyenneur, 0, 3, 0, 3);
		free_imatrix(sobel_h, 0, 3, 0, 3);
		free_imatrix(sobel_v, 0, 3, 0, 3);

	} else if (fileType == 2) {

		rgb8 ** I;
		I = LoadPPM_rgb8matrix(filename, &nrl, &nrh, &ncl, &nch);

	}



	return 1;

}










