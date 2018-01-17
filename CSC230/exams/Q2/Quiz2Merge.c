/* QUIZ 2 */
/*MY NAME =  */
/*MY STUDENT NUMBER = */

/*Merging two ordered lists into a third ordered*/
/*list - no duplicates removed*/
/*Lists are input from file*/
/*No input validation is required*/

/*****THIS IS THE INITIAL FRAMEWORK FOR
	INPUT AND OUTPUT ONLY - MERGE STILL
	TO BE DONE ***/

#include "stdio.h"
#define	MAXSIZE	20	/*Maximum length for input lists*/

/*==== Function ReadList: reads a list of integers
	and its size from an opened input file */
/*Input parameters:
	FILE *fileptr		pointer to input file
	int List[MAXSIZE]	array to contain the list
  Output parameters:
  	1 if okay, -1 if end of file found*/
  /* Note: the size of the list is the first
  	element of the list*/
int ReadList(FILE *fileptr, int List[MAXSIZE]) {
	int i;
	int nir;
	nir = fscanf(fileptr,"%d",&List[0]); /*read size if there*/
		if (nir != 1)
			return(-1);		/*end of file found*/
		else {				/*read the elements of the list*/
			for (i=1;i<=List[0];i++) {
				nir = fscanf(fileptr,"%d",&List[i]);
			}
		}
	return(1);
}	/*end of ReadList*/

/* ==== Procedure printList: prints a list and
	its size */
	/*Input parameters:
		int List[MAXSIZE*2]	array containing the list
		int listnum			which list: 1, 2 or 3 as constants
	  Output parameters:    none*/
	/* Note: the size of the list is the first
  	element of the list*/
/* void printlist(int List[MAXSIZE*2], int listnum) { */
	/* MOVE THE CODE FROM MAIN TO HERE */
/* } */

/*====================================*/
int main()
{
	int  List1[MAXSIZE],List2[MAXSIZE];
	/*int	 List3[MAXSIZE*2];*/	/*REMOVE COMMENT*/
	int i,okay;
	FILE *fopen(),*fp;

	/*open input file*/
	fp=fopen("myQuizinputM.txt","r");
	if (fp == NULL) {
		printf ("Cannot read the file\n");
		return(1);
	}

	/*print greetings*/
	printf("Welcome to Quiz 2 \n\n");
	/*ASSUMPTION: the file is not empty to start with*/
	/*read the first list*/
	okay=ReadList(fp,List1);
	while (okay == 1) {				/* while not end of file*/
		printf("####################\n");
		printf("Size of List 1 is %d\n",List1[0]); /*print the size*/
		printf("\t List 1 elements are:\n \t");		/*print the list*/
		for (i=1;i<=List1[0];i++) {
			printf(" %3d",List1[i]);
		}
		printf("\n");
		/*read the second list and echo it out*/
		okay=ReadList(fp,List2);	/*no need to check for EOF */
		printf("Size of List 2 is %d\n",List2[0]); 	/*print the size*/
		printf("\t List 2 elements are:\n \t");	  	/*print the list*/
		for (i=1;i<=List2[0];i++) {
			printf(" %3d",List2[i]);
			}
		printf("\n\n");

		/*merge the two lists into List3*/
		/* YOUR NEW CODE HERE */

		/* print the merged list*/
		/*USE THE CODE BELOW WHILE DEVELOPING AND TESTING */
		/* WHEN MERGING IS DONE CORRECTLY, USE A CALL TO
			PRINTLIST AS ABOVE */
		/*printf("Size of List 3 is %d\n",List3[0]);*/ /*print the size*/
		/*printf("\t List 3 elements are:\n \t");*/	  	/*print the list*/
		/*for (i=1;i<=List3[0];i++) {
			printf(" %3d",List3[i]);
			}
		printf("\n\n");*/

		okay=ReadList(fp,List1); /*read the next set of lists */
	}

	fclose(fp);  /* close the input file */
	fprintf(stdout, "\nAll done - Bye\n");

	return (0);
}




