#makefile for project 1 works both in linux and unix system now

.c.o:
	gcc -g -c $?

# compile client and server
all: client server

# compile client only
client:
	gcc -g client.c -o SimpClient -lnsl

#compile server only
server:
	gcc -g server.c -o SimpServer -lnsl
