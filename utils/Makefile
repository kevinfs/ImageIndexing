CC=gcc

all : main lib

exemple : exemple.c lib
	${CC} -o exemple exemple.c nrio.o nralloc.o nrarith.o

main : main.c lib
	${CC} -o imageProcessing main.c nrio.o nralloc.o nrarith.o

lib : NRC/nrio.c NRC/nralloc.c NRC/nrarith.c
	${CC} -c NRC/nrio.c NRC/nralloc.c NRC/nrarith.c

#
# Clean
#

clean :
	rm imageProcessing *.o