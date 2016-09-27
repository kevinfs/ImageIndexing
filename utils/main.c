#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <unistd.h>
#include "contourDetection.h"

int main(int argc, char * argv[]){

	long nrh,nrl,nch,ncl;
	byte **I;
	byte **R;
	int ** moyenneur;
	int ** sobel_h;
	int ** sobel_v;

	int i,j;


	int verbose = 0;
	int silent = 0;
	int contourDetection = 0;
	int txColor = 0;
	int histogram = 0;
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

    printf("%d %d\n", optind, argc);

    if(optind < argc) {
    	filename = argv[optind];
    } else {
    	filename = NULL;
    }

    printf("ahah %s\n", filename);
exit(0);

	// I=LoadPGM_bmatrix("NRC/cubesx3.pgm",&nrl,&nrh,&ncl,&nch);
	I = LoadPGM_bmatrix("rice.pgm", &nrl, &nrh, &ncl, &nch);
	
	R = bmatrix(nrl, nrh, ncl, nch);


	// Filtre Moyenneur
	moyenneur = imatrix(0, 3, 0, 3);
	moyenneur[0][0] = moyenneur[0][1] = moyenneur[0][2]
		= moyenneur[1][0] = moyenneur[1][1] = moyenneur[1][2]
		= moyenneur[2][0] = moyenneur[2][1] = moyenneur[2][2]
		= 1;
	// convolution(I, nrh, nch, moyenneur, 3, R);
	// SavePGM_bmatrix(R,nrl,nrh,ncl,nch,"rice_moyenne.pgm");

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
	norme_gradient();

	// Seuillage
	I = LoadPGM_bmatrix("rice_norme.pgm", &nrl, &nrh, &ncl, &nch);
	seuillage(I, nrh, nch, 9);
	SavePGM_bmatrix(I, nrl, nrh, ncl, nch, "rice_norme_seuille.pgm");

	free_bmatrix(I, nrl, nrh, ncl, nch);
	free_bmatrix(R, nrl, nrh, ncl, nch);
	free_imatrix(moyenneur, 0, 3, 0, 3);
	free_imatrix(sobel_h, 0, 3, 0, 3);
	free_imatrix(sobel_v, 0, 3, 0, 3);


	return 1;

}






