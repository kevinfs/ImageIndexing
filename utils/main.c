#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include "NRC/def.h"
#include "NRC/nrio.h"
#include "NRC/nrarith.h"
#include "NRC/nralloc.h"

void convolution(byte ** src, long height, long width, int ** filtre, int filter_size, byte ** dest);
void norme_gradient();
void seuillage(byte ** src, long height, long width, byte seuil);

int main(int argc, char * argv[]){

	long nrh,nrl,nch,ncl;
	byte **I;
	byte **R;
	int ** moyenneur;
	int ** sobel_h;
	int ** sobel_v;


	int i,j;

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
	printf("sum %li\n", sum);

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

void norme_gradient() {

	long nrh,nrl,nch,ncl;
	long r, c;
	byte **Ix;
	byte **Iy;
	byte **R;

	Ix = LoadPGM_bmatrix("rice_ix.pgm", &nrl, &nrh, &ncl, &nch);
	Iy = LoadPGM_bmatrix("rice_iy.pgm", &nrl, &nrh, &ncl, &nch);

	R = bmatrix(nrl, nrh, ncl, nch);

	for (r = nrl; r < nrh; ++r) {
		for (c = ncl; c < nch; ++c) {
			R[r][c] = sqrt(pow(Ix[r][c], 2) + pow(Iy[r][c], 2));
		}
	}

	SavePGM_bmatrix(R, nrl, nrh, ncl, nch, "rice_norme.pgm");

	free_bmatrix(Ix, nrl, nrh, ncl, nch);
	free_bmatrix(Iy, nrl, nrh, ncl, nch);
	free_bmatrix(R, nrl, nrh, ncl, nch);

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







