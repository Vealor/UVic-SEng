.phony all:
all: sample inf args

sample: sample.c
	gcc sample.c -lreadline -lhistory -ltermcap -o sample

inf: inf.c
	gcc inf.c -o inf

args: args.c
	gcc args.c -o args

.PHONY clean:
clean:
	-rm -rf *.o *.exe
