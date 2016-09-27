CC=gcc

all : main

main : main.c NRC/nrio.o NRC/nralloc.o NRC/nrarith.o
	${CC} -o imageProcessing main.c NRC/nrio.o NRC/nralloc.o NRC/nrarith.o

NRC/nrio.o: NRC/nrio.c
	${CC} -c $< -o $@

NRC/nralloc.o: NRC/nralloc.c
	${CC} -c $< -o $@

NRC/nrarith.o: NRC/nrarith.c
	${CC} -c $< -o $@

#
# Clean
#

clean :
	rm imageProcessing NRC/*.o