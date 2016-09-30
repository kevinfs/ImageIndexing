#include "utils.h"

void saveDescriptorsToFile(char * filename, char * colorType, double gradientMean, int numberEdgePixel, double ratioTexture, double ratioR, double ratioG, double ratioB, int * histogram) {

    FILE *fp;
    char * strippedFilename;
    char * descriptorsFile;
    char * fileID;
    int i;

    // Find picture name
    strippedFilename = removeExtension(filename, '.', '/');
    fileID = strrchr(filename, '/') + 1;


    // Make correct path for descriptors file
    descriptorsFile = (char *) malloc(sizeof(char) * strlen(strippedFilename) + strlen("-descriptors.txt"));
    strcpy(descriptorsFile, strippedFilename);
    strcat(descriptorsFile, "-descriptors.txt");

    // Open/create descriptors file
    fp = fopen(descriptorsFile, "w+");
    if (fp == NULL)
        puts("Unable to write to descriptor file, aborting...");

    // Write global descriptors
    fprintf(fp, "%s\n%s\n%f\n%d\n%f\n%f\n%f\n%f\n", fileID, colorType, gradientMean, numberEdgePixel, ratioTexture, ratioR, ratioG, ratioB);

    // Write histogram
    if (histogram != NULL)
        for (i = 0; i < 256; ++i)
            fprintf(fp, "%d ", histogram[i]);

    fclose(fp);

}

char *removeExtension (char* mystr, char dot, char sep) {
    char *retstr, *lastdot, *lastsep;

    // Error checks and allocate string.

    if (mystr == NULL)
        return NULL;
    if ((retstr = malloc (strlen (mystr) + 1)) == NULL)
        return NULL;

    // Make a copy and find the relevant characters.

    strcpy (retstr, mystr);
    lastdot = strrchr (retstr, dot);
    lastsep = (sep == 0) ? NULL : strrchr (retstr, sep);

    // If it has an extension separator.

    if (lastdot != NULL) {
        // and it's before the extenstion separator.

        if (lastsep != NULL) {
            if (lastsep < lastdot) {
                // then remove it.

                *lastdot = '\0';
            }
        } else {
            // Has extension separator with no path separator.

            *lastdot = '\0';
        }
    }

    // Return the modified string.

    return retstr;
}