/* File: sengfmt.c
 * Jakob Roberts
 * v00484900
 * SEng 265 - Assignemnt 1
 *
 * Decription:
 *      Takes an input file, implements formatting and then
 *	outputs the changes to another file.
*/
 
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define MAX_LINE_CHAR   133	/* defines the max chars per line +1 */
#define MAXLINES 	300	/* defines the max # of lines in the file */

/* =================== */
static int width  = -1;
static int width_save = -1;
static int margin =  0;
static int fmt    =  0;
/* =================== */

/* ==assign_parameters======================================================= */
/* checks every word if there is an associated parameter modifier */
int assign_parameters(char *ap_if, char *para){
	int w_out, m_out; /* temp assignments */
	char fmt_out[4];  /* holds fmt result */
	/* check width and assign */
	if(strcmp(para,"?width")==0){
		ap_if = strtok(NULL, " ");
		sscanf(ap_if, "%d", &w_out);
		width = w_out;
		width_save = width;
		fmt = 1;
		return 1;
	}
	/* check margin and assign */
	if(strcmp(para,"?mrgn")==0 && fmt==1){
		ap_if = strtok(NULL, " ");
		sscanf(ap_if, "%d", &m_out);
		margin = m_out;
		return 1;
		
	}
	/* check format (fmt) and assign */
	if(strcmp(para,"?fmt")==0){
		ap_if = strtok(NULL, " ");
		sscanf(ap_if, "%s", fmt_out);
		if(strcmp(fmt_out,"on")==0){
			fmt = 1;
			width = width_save;
		}
		if(strcmp(fmt_out,"off")==0){
			fmt = 0;
			width = MAX_LINE_CHAR;
		}
		return 1;
	}
	return 0;
}
/* ==add_margin============================================================== */
/* adds margins based on assigned parameters */
void add_margins(char *line){
	int i;
	/* if fmt on and beginning: add margins */
	if(fmt==1 && strlen(line)==0){
		for(i=0;i<margin;i++){
			strcat(line, " ");
		}
	}	
}

/* ==check_args============================================================== */
void check_args(int arg_num, char **argv){
	if(arg_num == 2){ 
 		fprintf(stderr, "usage: %s filename\n", argv[1]); 
 		exit(1); 
 	}
 	if(arg_num != 3 && arg_num == 1){ 
 		fprintf(stderr, "usage: ./sengfmt <input_file> <output_file>"); 
 		exit(1); 
 	}
}



/* ==main==================================================================== */
int main(int argc, char **argv){
	FILE *file_in;					/* input file */
	char line_in[MAX_LINE_CHAR] = "\0";		/* input line */
	char word_in[MAX_LINE_CHAR] = "\0";		/* input word */
	int current_word_len = 0;			/* input word length */
	int current_line_len = 0;			/* output line length */	
	char current_out_line[MAX_LINE_CHAR] = "\0";	/* output line */
	
/* =================== */

	check_args(argc,argv);   /* checks initial file input and output args */
 	file_in = fopen(argv[1], "r");			/* opens input file */

 	if(file_in == NULL){	 /* checks if input file can't be opened */
 		fprintf(stderr, "cannot open file @ %s", argv[1]);
 		exit(1);
 	}
 	
/* =================== */

	while(fgets(line_in, MAX_LINE_CHAR, file_in)!=NULL){
		char *t;	/* tokens */
		int check;	/* check for parameter use */
		char line_save[MAX_LINE_CHAR] = "\0"; /* use if fmt off */
		strcpy(line_save,line_in);	      /* build for each line */
		
		/* checks for a new paragraph and makes it if needed */
		if(strncmp(line_in, "\n",1)==0 && fmt==1){
			/* print current_out_line to finish leftover words */
			strcat(current_out_line, "\n");
			fprintf(stdout, "%s", current_out_line);
			/* initialize blank line & print it */
			strcpy(current_out_line, "\n\0");
			fprintf(stdout, "%s", current_out_line);
			/* NULL out current line */
			strcpy(current_out_line, "\0");
		}else{
			/* tokenize line_in */
			t=strtok(line_in, "\t ");	
			
			while(t){	/* while still can get next token */
				
				/*scan in removing whitespace */
				sscanf(t, "%s", word_in);
				
				/* get scanned in word length */
				current_word_len = strlen(word_in);	
				
				/* checks for base parameters (width margin) */
				if((check = assign_parameters(t, word_in))==0 
				   && fmt==1){
					add_margins(current_out_line);
						
					current_line_len = strlen(current_out_line);
					
					/* will it fit in width of line? */
					if(current_line_len+current_word_len <= width-1){
						/* then if not empty */
						if(margin!=current_line_len){ 
							strcat(current_out_line, " ");
						}
						/* add word to line */
						strcat(current_out_line, word_in);
					}else{ /* if no fit on line */
						/* print line with spacing */
						strcat(current_out_line, "\n");
						fprintf(stdout, "%s", current_out_line);
						/* initialize current line */
						strcpy(current_out_line, "\0");
						current_line_len = 0;
						/* add margins */
						add_margins(current_out_line);
						/* add leftover word */
						strcat(current_out_line, word_in);
					}
				}else if(fmt==0 && check == 0){
					/* if fmt off, print all as-is */
					fprintf(stdout, "%s", line_save);
					break;
				}
				/* next token */
				t = strtok(NULL, " ");
			}
		}
		
 	}
 	/* loop finished from EOF, print last remaining current_out_line */
 	fprintf(stdout, "%s", current_out_line);
 	
 	/* close opened file */
 	fclose(file_in);
 	return 0;
} 
