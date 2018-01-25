/*
 * UVic SENG 265, Summer 2014, A#4
 *
 * Jakob Roberts
 * v00484900
 * Assignemnt 4
 *
 * This will contain a solution to sengfmt3. In order to complete the
 * task of formatting a file, it must open the file and pass the result
 * to a routine in formatter.c.
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "formatter.h"
/*---------------------------------------------------------------------------*/
/*===========================================================================*/


/*===CHECK_ARGS==============================================================*/
void check_args(int argnum, char** input){
	if(argnum !=2){
		fprintf(stderr,"Invalid inital parameters\n");
		exit(1);
	}
}
/*===MAIN====================================================================*/
int main(int argc, char *argv[]) {
	/*===DECLARE INITIAL STRINGS=====*/
	char *line_in[512];
	char **lines;
	char **formatted;
	FILE *file_in;	
	if(argc==1){ /* read from stdin */
		fgets(*line_in, 200, stdin);
		formatted = format_lines(line_in, 1);
	}else{
		check_args(argc,argv); /* CHECK ARG INPUT */
		/*===OPEN INPUT FILE=============*/
		file_in = fopen(argv[1],"r");
		if(file_in == NULL){
			fprintf(stderr,"Cannot open file @ %s\n", argv[1]);
			exit(1);
		}
		/*===FORMAT INPUT LINES FROM FILE*/
		formatted = format_file(file_in);
		if(formatted == NULL){
			fprintf(stderr,"====ERROR: FILE \"%s\" RETURNED NO LINES====\n",argv[1]);
			exit(1);
		}
	}
	/*===PRINT FORMATTED RESULT======*/
	for(lines = formatted; *lines != NULL; lines++){
		fprintf(stdout,"%s",*lines);
	}
	fclose(file_in);
	exit(0);
}
