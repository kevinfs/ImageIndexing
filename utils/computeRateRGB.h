#include "NRC/def.h"
#include "NRC/nrio.h"
#include "NRC/nrarith.h"
#include "NRC/nralloc.h"

typedef struct {
	double tauxR;
	double tauxG;
	double tauxB;
} TauxRGB;

TauxRGB computeTauxRGB(rgb8 **imageRGB, long nrl,long nrh,long ncl,long nch);