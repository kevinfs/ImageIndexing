#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#ifndef _UTILS_H_
#define _UTILS_H_

void saveDescriptorsToFile(char * filename, char * colorType, double gradientMean, int numberEdgePixel, double ratioTexture, double ratioR, double ratioG, double ratioB, int * histogram);
char * removeExtension (char * mystr, char dot, char sep);

#endif