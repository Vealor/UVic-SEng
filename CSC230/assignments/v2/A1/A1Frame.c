/* File: A1Frame.c */
/* Captain Jean Luc Picard V00123456 */
/* CSC 230 - Assignment 1 */
/*Read 2 dimensional matrices and their sizes until
	end of file. Compute the transpose. Test if a matrix
	is symmetrical, skew-symmetrical or orthogonal */

#include <stdio.h>
#define MAXSIZE 10		/*max number for rows and columns */

/* global variables and functions */
	/* Best to pass as input parameter and not used
		as global - here only for safeguard */
FILE *fpin;		/*pointer to input file*/

/**************************************************/

/*****THIS IS THE INITIAL FRAMEWORK DOING ONLY THE I/O IN MAIN ******/

/*==== Function RdRowSize: reads the row size of a matrix from a file */
		/* copy the interface as given and place your code here */

/*==== Procedure RdMatrix: reads the elements of a matrix from a file */
		/* copy the interface as given and place your code here */

/*===== Procedure PrMat: prints a 2-dimensional matrix  row by row*/
		/* copy the interface as given and place your code here */

/*===== Procedure Transpose: construct the transpose of a matrix*/
		/* copy the interface as given and place your code here */

/*===== Function Symm: check for symmetric matrix*/
		/* copy the interface as given and place your code here */

/*===== Function SkewSymm: check for symmetric matrix*/
		/* copy the interface as given and place your code here */

/*===== Function MatMult: multiply 2 matrices*/
		/* copy the interface as given and place your code here */

/*===== Function Ortho: check for orthogonal matrix*/
		/* copy the interface as given and place your code here */

/*===============================================*/
int main() {

    int MatMain[MAXSIZE][MAXSIZE];	/*the initial matrix*/
	int RsizeM, CsizeM;				/*matrix row size and column size*/
	int	nir,i,j,temp;				/*counters*/
/*	int MatTransp[MAXSIZE][MAXSIZE]; */		/*the transpose*/
/*	int RsizeTr,CsizeTr;*/			/*transpose row size and column size*/


	fprintf(stdout, "Matrix testing program starts\n");	/*Headers*/
	fprintf(stdout, "Captain Picard V00123456\n");
	fprintf(stdout, "CSC 230 Assignment 1\n\n");
	/*open input file - file name is hardcoded*/
	fpin = fopen("INA1.txt", "r");  /* open the file for reading */
	if (fpin == NULL) {
		fprintf(stdout, "Cannot open input file  - Bye\n");
		return(0); 					/* if problem, exit program*/
	}
	/*ASSUMPTIONS: the file is not empty
		and the matrix has at least 1 element*/

	nir=fscanf(fpin,"%d",&RsizeM);	/* read row size of a matrix */
							/* this needs to be encapsulated in RdRowSize*/
	while (nir == 1) {				/* while not end of file*/
		fscanf(fpin,"%d",&CsizeM);	/*read column size*/
		/*read matrix - needs to be encapsulated in RdMatrix*/
		for (i=0;i<RsizeM;i++) {		/*read matrix*/
			for (j=0;j<CsizeM;j++) {
				fscanf(fpin,"%d",&temp);
				MatMain[i][j]=temp;
			}
		}
		/*print the matrix and the sizes*/
		fprintf(stdout, "\n\n*** MATRIX ***");
		fprintf(stdout, "  Size = %2d x %2d\n",RsizeM,CsizeM);
		/*print matrix - needs to be encapsulated in PrMat*/
		for (i=0;i<RsizeM;i++) {
			fprintf(stdout,"     ");
			for (j=0;j<CsizeM;j++) {
				fprintf(stdout,"%5d  ",MatMain[i][j]);
			}
			fprintf(stdout,"\n");
		}
		fprintf(stdout,"\n");

		/* NEW CODE HERE FOR PROCESSING */
		/* call PrMat, Transpose, Symm, SkewSymm, MatMult, Ortho
			as required */

		nir=fscanf(fpin,"%d",&RsizeM); /*read next row size*/
	}

	fclose(fpin);  /* close the file */
	fprintf(stdout, "\nAll done - Bye\n");

	return (0);
}
