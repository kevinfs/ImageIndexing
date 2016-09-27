CC=gcc

all : main

main : main.c contourDetection.o computeRateRGB.o NRC/nrio.o NRC/nralloc.o NRC/nrarith.o
	${CC} -o imageProcessing main.c contourDetection.o computeRateRGB.o NRC/nrio.o NRC/nralloc.o NRC/nrarith.o

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
	rm imageProcessing *.o NRC/*.o