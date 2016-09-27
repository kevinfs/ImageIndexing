#include "NRC/def.h"
#include "NRC/nrio.h"
#include "NRC/nrarith.h"
#include "NRC/nralloc.h"

#ifndef _DETECTIONCONTOURS_H_
#define _DETECTIONCONTOURS_H_

void convolution(byte ** src, long height, long width, int ** filtre, int filter_size, byte ** dest);
void norme_gradient();
void seuillage(byte ** src, long height, long width, byte seuil);

#endif