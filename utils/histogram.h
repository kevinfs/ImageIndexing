#include "NRC/def.h" 
#include "NRC/nrio.h"
#include "NRC/nrarith.h"
#include "NRC/nralloc.h"

#ifndef _HISTOGRAM_H_
#define _HISTOGRAM_H_

int * histogram1Channel(byte **image, long nrl, long nrh, long ncl, long nch);
int ** histogram3Channel(rgb8 **imageRGB, long nrl, long nrh, long ncl, long nch);

#endif