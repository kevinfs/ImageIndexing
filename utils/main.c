#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <unistd.h>
#include <string.h>
#include "utils.h"
#include "contourDetection.h"
#include "computeRateRGB.h"
#include "histogram.h"

int main(int argc, char * argv[]){

	long nrh, nrl, nch, ncl;
	int ** moyenneur;
	int ** sobel_h;
	int ** sobel_v;

	int i,j;


	int verbose = 0;
	int silent = 0;
	int contourDetection = 0;
	int txColor = 0;
	int histogram = 0;
	int mean = 0;
	int threshold = 9;
	int fileType = 0; // 1 for pgm, 2 for ppm, 3 for jpg
	char * filename;
    int opt;

    while ((opt = getopt(argc, argv, "vscxhmt:")) != -1) {
        switch (opt) {
	        case 'v': verbose = 1; break;
	        case 's': silent = 1; break;
	        case 'c': contourDetection = 1; break;
	        case 'x': txColor = 1; break;
	        case 'h': histogram = 1; break;
	        case 'm': mean = 1; break;
	        case 't': threshold = atoi(optarg); break;
	        default:
	            fprintf(stderr, "Usage: %s [-vscxhm] [-t 20] [file...]\n", argv[0]);
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

    if(threshold > 255)
    	threshold = 255;
    else if(threshold < 0)
    	threshold = 0;

    if (verbose) {
    	puts("Verbose mode used");
    	if (silent)
    		puts("Silent mode used, intermediate images will not be saved");
    	if (contourDetection) {
    		printf("Contour detection requested using threshold of %d/255\n", threshold);
    	}
    	if (txColor)
    		puts("Color tx requested");
    	if (histogram)
    		puts("Histogram requested");
    }

	if (verbose)
		puts("Analyzing file...");
    if(strstr(filename, "pgm") != NULL) {
	    fileType = 1;
	    puts("File recognized as PGM");
	} else if(strstr(filename, "ppm") != NULL) {
	    fileType = 2;
	    puts("File recognized as PPM");
	} else if(strstr(filename, "jpg") != NULL) {
	    fileType = 3;
	    puts("File recognized as JPG");
	} else {
    	fprintf(stderr, "%s can only support PGM or PPM files\n", argv[0]);
    	exit(EXIT_FAILURE);
	}

	if (verbose)
		printf("Opening file %s...\n", filename);

	char * strippedFilename;
	strippedFilename = removeExtension(filename, '.', '/');

	char * colorType = "gray";
	double gradientMean = 0.0;
	double ratioTexture = 0.0;
	double ratioR = 0.0;
	double ratioG = 0.0;
	double ratioB = 0.0;
	int * histogramV = NULL;


	////////
	// PGM
	////////
	if (fileType == 1) {

		byte **I;
		byte **RH;
		byte **RV;
		byte **NORME;
		I = LoadPGM_bmatrix(filename, &nrl, &nrh, &ncl, &nch);

		if (contourDetection || mean) {

			if (verbose)
    			puts("Starting contour detection");

			RH = bmatrix(nrl, nrh, ncl, nch);
			RV = bmatrix(nrl, nrh, ncl, nch);
			NORME = bmatrix(nrl, nrh, ncl, nch);

			if (verbose)
    			puts("Horizontal gradient");

			// Gradient Horizontal
			sobel_h = imatrix(0, 3, 0, 3);
			sobel_h[0][0] = sobel_h[2][0] = -1;
			sobel_h[0][2] = sobel_h[2][2] = +1;
			sobel_h[0][1] = sobel_h[1][1] = sobel_h[2][1] = 0;
			sobel_h[1][0] = -2;
			sobel_h[1][2] = +2;
			convolution(I, nrh, nch, sobel_h, 3, RH);

			if (!silent) {
				if (verbose)
    				puts("Saving horizontal gradient to file");
				char * filenameH = (char *) malloc(sizeof(char) * strlen(strippedFilename) + strlen("-h.pgm"));
				strcpy(filenameH, strippedFilename);
				strcat(filenameH, "-h.pgm");
				SavePGM_bmatrix(RH, nrl, nrh, ncl, nch, filenameH);
				free(filenameH);
			}

			if (verbose)
    			puts("Vertical gradient");

			// Gradient Vertical
			sobel_v = imatrix(0, 3, 0, 3);
			sobel_v[0][0] = sobel_v[0][2] = -1;
			sobel_v[2][0] = sobel_v[2][2] = +1;
			sobel_v[1][0] = sobel_v[1][1] = sobel_v[1][2] = 0;
			sobel_v[0][1] = -2;
			sobel_v[2][1] = +2;
			convolution(I, nrh, nch, sobel_v, 3, RV);

			if (!silent) {
				if (verbose)
    				puts("Saving vertical gradient to file");
				char * filenameV = (char *) malloc(sizeof(char) * strlen(strippedFilename) + strlen("-v.pgm"));
				strcpy(filenameV, strippedFilename);
				strcat(filenameV, "-v.pgm");
				SavePGM_bmatrix(RV, nrl, nrh, ncl, nch, filenameV);
				free(filenameV);
			}

			if (verbose)
    			puts("Computing normalisation of gradient");

			// Norme du gradient
			norme_gradient(RH, RV, nrh, nch, NORME);

			if (!silent) {
				if (verbose)
    				puts("Saving normalised gradient to file");
				char * filenameN = (char *) malloc(sizeof(char) * strlen(strippedFilename) + strlen("-n.pgm"));
				strcpy(filenameN, strippedFilename);
				strcat(filenameN, "-n.pgm");
				SavePGM_bmatrix(NORME, nrl, nrh, ncl, nch, filenameN);
				free(filenameN);
			}

			if (mean) {
				if (verbose)
    				puts("Finding mean");

				gradientMean = getGradientMean(NORME, nrh, nch);
				printf("Mean of gradient accross image : %f\n", gradientMean);

			}

			if (verbose)
    			puts("Thresholding");

			// Seuillage
			seuillage(NORME, nrh, nch, threshold);

			if (!silent) {
				if (verbose)
    				puts("Saving thresholded values to file");
				char * filenameN = (char *) malloc(sizeof(char) * strlen(strippedFilename) + strlen("-ns.pgm"));
				strcpy(filenameN, strippedFilename);
				strcat(filenameN, "-ns.pgm");
				SavePGM_bmatrix(NORME, nrl, nrh, ncl, nch, filenameN);
				free(filenameN);
			}

			if (verbose) {
				long n = getNumberOfContourPixels(NORME, nrh, nch);
				printf("Number of pixels : %ld\n", n);
			}

			ratioTexture = getImageTexture(NORME, nrh, nch);
			printf("Texture : %f\n", ratioTexture);

			// Computing histogram
			if (verbose)
				puts("Computing histogram w/ one channel");

			histogramV = histogram1Channel(I, nrl, nrh, ncl, nch);

			if (verbose)
    			puts("Freeing image structures used");

			free_bmatrix(I, nrl, nrh, ncl, nch);
			free_bmatrix(RH, nrl, nrh, ncl, nch);
			free_bmatrix(RV, nrl, nrh, ncl, nch);
			free_bmatrix(NORME, nrl, nrh, ncl, nch);
			free_imatrix(sobel_h, 0, 3, 0, 3);
			free_imatrix(sobel_v, 0, 3, 0, 3);

			// Saving to file
			if (verbose)
				puts("Saving descriptors to file");

			saveDescriptorsToFile(filename, colorType, mean, ratioTexture, ratioR, ratioG, ratioB, histogramV);

		}

	}
	////////
	// PPM
	////////
	else if (fileType == 2) {

		rgb8 ** I;
		I = LoadPPM_rgb8matrix(filename, &nrl, &nrh, &ncl, &nch);

	}

	free(strippedFilename);

	return 1;

}










