CC=gcc

all : main

main : main.c utils.o contourDetection.o computeRateRGB.o histogram.o NRC/nrio.o NRC/nralloc.o NRC/nrarith.o
	${CC} -o imageProcessing main.c utils.o contourDetection.o computeRateRGB.o histogram.o NRC/nrio.o NRC/nralloc.o NRC/nrarith.o

utils.o: utils.c utils.h
	${CC} -c $< -o $@

contourDetection.o: contourDetection.c contourDetection.h
	${CC} -c $< -o $@

computeRateRGB.o: computeRateRGB.c computeRateRGB.h
	${CC} -c $< -o $@

histogram.o: histogram.c histogram.h
	${CC} -c $< -o $@

NRC/nrio.o: NRC/nrio.c NRC/nrio.h
	${CC} -c $< -o $@

NRC/nralloc.o: NRC/nralloc.c NRC/nralloc.h
	${CC} -c $< -o $@

NRC/nrarith.o: NRC/nrarith.c NRC/nrarith.h
	${CC} -c $< -o $@

#
# Clean
#

clean :
	rm imageProcessing *.o NRC/*.o images/*-h.pgm images/*-v.pgm images/*-n.pgm images/*-ns.pgm images/*-descriptors.txt