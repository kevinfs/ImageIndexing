#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#include "def.h" 
#include "nrio.h"
#include "nrarith.h"
#include "nralloc.h"

int* histogram1Channel(byte **image, long nrl, long nrh, long ncl, long nch);
int** histogram3Channel(rgb8 **imageRGB, long nrl, long nrh, long ncl, long nch);