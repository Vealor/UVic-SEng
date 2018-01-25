CC=gcc
CFLAGS=-c -g -Wall -DDEBUG
LDFLAGS=

all: sengfmt3 driver

sengfmt3: sengfmt3.o formatter.o
	$(CC) $(LDFLAGS) sengfmt3.o formatter.o -o sengfmt3

driver: driver.o formatter.o
	$(CC) $(LDFLAGS) driver.o formatter.o -o driver

driver.o: driver.c formatter.h
	$(CC) $(CFLAGS) driver.c

sengfmt3.o: sengfmt3.c formatter.h
	$(CC) $(CFLAGS) sengfmt3.c

formatter.o: formatter.c formatter.h
	$(CC) $(CFLAGS) formatter.c

clean:
	/bin/rm -f sengfmt3 driver sengfmt3.o driver.o formatter.o
