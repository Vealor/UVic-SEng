/*
 * UVic SENG 265, Summer 2014,  A#4
 * 
 * Jakob Roberts
 * v00484900
 * Assignment 4
 *
 * This will contain the bulk of the work for the fourth assignment. It
 * provide similar functionality to the class written in Python for
 * assignment #3.
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "formatter.h"
int fromfile = 0;
int isoff = 0;
/*---------------------------------------------------------------------------*/
void mallocerr(){
	fprintf(stderr,"ERROR allocating memory!\n");
	exit(1);
}
/*---------------------------------------------------------------------------*/
char **format_file(FILE *infile) {
	char *line = NULL;
	int numlines = 0;

	size_t initlen = 0;
	ssize_t read;

	char **output = calloc(1,sizeof(char*));
	if(output == NULL){mallocerr();}
	char **result = NULL;
	while((read = getline(&line, &initlen, infile)) != -1){
		output = (char**)realloc(output,(numlines+1) * sizeof(char*));
		if(output == NULL){mallocerr();}
		output[numlines] = calloc(read+1,sizeof(char));
		if(output[numlines] == NULL){mallocerr();}
		strcpy(output[numlines], line);
		numlines++;
	}
	fromfile = 1;
	result = format_lines(output,numlines);
	return result;
}
/*---------------------------------------------------------------------------*/
int assign_parameters(char *line_in, int *width, int *margin, int *fmt){
	char *line = (char*)calloc(strlen(line_in)+1,sizeof(char));
	if(line == NULL){mallocerr();}
	strncpy(line,line_in,strlen(line_in)+1);
	char *tok = strtok(line,"\t ");
	char fmt_out[4];  /* holds fmt result */
	/* check width and assign */
	if(!strcmp(tok,"?width")){
		tok = strtok(NULL, " ");	/* get parameter */
		*width = atoi(tok);
		*fmt = 1;
		return 1;
	}
	/* check margin and assign */
	else if(!strcmp(tok,"?mrgn") && *fmt==1){
		tok = strtok(NULL, " ");	/* get parameter */
		if(!strncmp(tok,"+",1)) {
			*margin += atoi(++tok);
		}else if(!strncmp(tok,"-",1)) {
			*margin -= atoi(++tok);
		}else{
			*margin = atoi(tok);
		}
		if (*margin >= *width - 20) {
			*margin = *width - 20;
		}
		return 1;
	}
	/* check format (fmt) and assign */
	else if(!strcmp(tok,"?fmt")){
		tok = strtok(NULL, " ");	/* get parameter */
		sscanf(tok, "%s", fmt_out);
		if(strcmp(fmt_out,"on")==0){
			*fmt = 1;
			isoff++;
		}
		if(strcmp(fmt_out,"off")==0){
			*fmt = 0;
			isoff++;
		}
		return 1;
	}
	return 0;
}
/*---------------------------------------------------------------------------*/
char **format_lines(char **lines, int num_lines) {
	/*===Formatting Variables========*/
	static int width      = 0;
	static int margin     = 0;
	static int fmt 	      = 0;
	/*===============================*/
	char **result = (char**)calloc(1,sizeof(char*));
	if(result == NULL){
		mallocerr();
	}
	int curr_line = 0;
	int curr_word_len=0;
	int curr_line_len=0;
	int i;
	int mar;
	for(i = 0; i<num_lines; i++){
		char *t;
		if(!strncmp(lines[i],"\n",1) && fmt==1){
			curr_line++;
			result = (char**)realloc(result,(curr_line+1) * sizeof(char*));
			if(result == NULL){mallocerr();}
			result[curr_line] = (char*)calloc(2,sizeof(char));
			if(result[curr_line] == NULL){mallocerr();}
			strcpy(result[curr_line],"\n");
			curr_line_len = width;
			continue;
		}
		if(assign_parameters(lines[i], &width, &margin, &fmt)){
			if(isoff==1){
				result = (char**)realloc(result,(curr_line+1) * sizeof(char*));
				if(result == NULL){mallocerr();}
			
				result[curr_line] = (char*)calloc(strlen(lines[i])+1,sizeof(char));
				if(result[curr_line] == NULL){mallocerr();}
				if(fmt==0){
					strcat(result[curr_line],"\n");
					strcat(result[curr_line],"\n");
					curr_line++;
					result = (char**)realloc(result,(curr_line+1) * sizeof(char*));
					if(result == NULL){mallocerr();}
			
					result[curr_line] = (char*)calloc(strlen(lines[i])+1,sizeof(char));
					if(result[curr_line] == NULL){mallocerr();}
				}
				isoff=0;
			}
			continue;
		}else if(fmt){
			isoff = 0;
			char *lines_tok = (char*)calloc(strlen(lines[i])+1, sizeof(char));
			if(lines_tok == NULL){mallocerr();}
			if(fromfile){
				strncpy(lines_tok,lines[i], strlen(lines[i])-1);
			}else{
				strncpy(lines_tok,lines[i], strlen(lines[i]));
				lines_tok[strlen(lines[i])] = '\0';
			}
			strcat(lines_tok,"\0");
			t=strtok(lines_tok, "\t \n");
			while(t){
				curr_word_len = strlen(t);
				if(width < curr_word_len + margin){
					fprintf(stderr,"ERROR word too big!");
					exit(1);
				}
				if(curr_line_len + curr_word_len + 1 > width){
					if(fromfile){
						strncat(result[curr_line],"\n",2);
					}
					curr_line++;
					result = (char**)realloc(result,(curr_line+1)*sizeof(char*));
					if(result == NULL){mallocerr();}
					curr_line_len = 0;
				}else if(curr_line_len !=0){
					strncat(result[curr_line]," ",1);
					curr_line_len++;
				}
				if(curr_line_len==0){
					result[curr_line] =(char*)calloc(width+2, sizeof(char));
					if(result[curr_line] == NULL){mallocerr();}
					for(mar=0;mar<margin;mar++){
						strcat(result[curr_line], " ");
						curr_line_len++;
					}
				}
				strncat(result[curr_line], t,curr_word_len);
				curr_line_len += curr_word_len;
				t = strtok(NULL, " ");
			}
		}else{
			isoff=0;
			if(curr_line_len < width){
				strncat(result[curr_line],"\n",2);
				curr_line++;
			}
			
			result = (char**)realloc(result,(curr_line+1) * sizeof(char*));
			if(result == NULL){mallocerr();}
			
			result[curr_line] = (char*)calloc(width+2,sizeof(char));
			if(result[curr_line] == NULL){mallocerr();}
			
			strncpy(result[curr_line], lines[i],strlen(lines[i])+1);
			curr_line++;
			curr_line_len = width+1;
			continue;
		}
	}
	if(fmt){
		curr_line++;
		if(fromfile){
			result = (char**)realloc(result,(curr_line+1) * sizeof(char*));
			if(result == NULL){mallocerr();}
			result[curr_line] = (char*)calloc(2,sizeof(char));
			if(result[curr_line] == NULL){mallocerr();}
			strncpy(result[curr_line],"\n",1);
			curr_line++;
			result[curr_line] = NULL;
		}
	}
	result[curr_line] = NULL;
	return result;
}

