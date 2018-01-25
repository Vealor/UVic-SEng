/* File: A1csc230.c */
/* Jakob Roberts V00484900 */
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
	/*Input parameters:
	  	FILE *fp pointer to input file
	  	int *Nrows pointer to row size to be returned
	  Output parameter:
	 	1 if okay, -1 if end of file found*/ 
int RdRowSize(FILE *fp, int *Nrows) {
	int nir;
	nir=fscanf(fp,"%d",Nrows); /* scans in the number  of rows */
	if(nir==1){
		return 1;
	}else{
		return -1;
	}
}

/*==== Procedure RdMatrix: reads the elements of a matrix from a file */
	/*Input parameters:
	  	FILE *fp pointer to input file
	  	int Mat[MAXSIZE][MAXSIZE] 2D array for matrix
	  	int R,int C number of rows and columns
	  Output parameters:
	  	None */ 
void RdMatrix(FILE *fp,int Mat[MAXSIZE][MAXSIZE],int R,int C) {
	int i,j;		/* counters */
	for (i=0;i<R;i++) {	/* read matrix */
		for (j=0;j<C;j++) {
			fscanf(fp,"%d",&Mat[i][j]);
		}
	}
	return;	
}

/*===== Procedure PrMat: prints a 2-dimensional matrix  row by row*/
	/*Input parameters:
		int Mat[MAXSIZE][MAXSIZE] the matrix to be printed
		int R, int C number of rows and columns
	  Output parameter: 
	  	None*/
void PrMat (int Mat[MAXSIZE][MAXSIZE],int R,int C) {
	int i,j;		/* counters */
	
	fprintf(stdout, "\n\n*** MATRIX ***");
	fprintf(stdout, "  Size = %2d x %2d\n",R,C);
	
	for (i=0;i<R;i++) { /* prints the matrix */
		fprintf(stdout,"     ");
		for (j=0;j<C;j++) {
			fprintf(stdout,"%5d  ",Mat[i][j]);
		}
		fprintf(stdout,"\n");
	}
	fprintf(stdout,"\n");
	
	return;
}

/*===== Procedure Transpose: construct the transpose of a matrix*/
	/*Input parameters:
		int Mat[MAXSIZE][MAXSIZE] the original matrix
		int Transp[MAXSIZE][MAXSIZE] the transpose to be built
		int RM,int CM  original number of rows and columns
		int *RT,int *CT  transpose number of rows and columns
	  Output parameter: 
	  	None*/
	/*Given a matrix Mat and its dimensions in RM and CM,
	  construct its transpose in Transp with dimensions RT and CT as in:
	  copy rows 0,1,...,CM-1 of Mat to cols 0,1,...,RT-1 of Transp */
void Transpose ( int Mat[MAXSIZE][MAXSIZE],
		 int Transp[MAXSIZE][MAXSIZE],
		 int RM,int CM,int *RT,int *CT) {
	int i,j;			/* counters */
	
	*RT=CM;
	*CT=RM;
	fprintf(stdout, "\n\n    Transpose:");
	fprintf(stdout, "  Size = %2d x %2d\n",*RT,*CT);
	
	
	for (i=0;i<RM;i++) { /* converts the matrix to transpose */
		for (j=0;j<CM;j++) {
			Transp[j][i] = Mat[i][j];
		}
	}	
		
	for (i=0;i<*RT;i++) { /* prints the transpose matrix */
		fprintf(stdout,"     ");
		for (j=0;j<*CT;j++) {
			fprintf(stdout,"%5d  ",Transp[i][j]);
		}
		fprintf(stdout,"\n");
	}
	fprintf(stdout,"\n");	
		
	return;
}

/*===== Function Symm: check for symmetric matrix*/
	/*Input parameters:
		int Mat[MAXSIZE][MAXSIZE] the matrix
		int Transp[MAXSIZE][MAXSIZE] its transpose
		int Size dimensions
	  Output parameter:
	  	0 for yes or -1 for no */
	/*Given a square matrix, check if it is symmetric
	  by comparing if Mat = Transp*/
int Symm (int Mat[MAXSIZE][MAXSIZE],int Transp[MAXSIZE][MAXSIZE],int Size) {
	int i,j;		/* counters */
	
	for (i=0;i<Size;i++) { 	/* checks symmetry */
		for (j=0;j<Size;j++) {
			if(Transp[i][j] != Mat[i][j]){
				return -1;
			}
		}
	}
	return 0; 		/* if no breaks, return 0 */
}

/*===== Function SkewSymm: check for symmetric matrix*/
	/*Input parameters:
		Page 11
		int Mat[MAXSIZE][MAXSIZE] the matrix
		int Transp[MAXSIZE][MAXSIZE] its transpose
		int Size dimensions
	  Output parameter:
	  	0 for yes or -1 for no */
	/*Given a square matrix, check if it is skew-symmetric
	  by comparing if Mat = - Transp*/
int SkewSymm (int Mat[MAXSIZE][MAXSIZE],int Transp[MAXSIZE][MAXSIZE],int Size) {
	int i,j;		/* counters */
	for (i=0;i<Size;i++) { 	/* checks symmetry */
		for (j=0;j<Size;j++) {
			if(-Transp[i][j] != Mat[i][j]){
				return -1;
			}
		}
	}
	return 0;		/* if no break, return 0 */
}

/*===== Function MatMult: multiply 2 matrices*/
	/*Input parameters:
		int MatA[MAXSIZE][MAXSIZE] matrix 1
		int MatB[MAXSIZE][MAXSIZE] matrix 2
		int MatP[MAXSIZE][MAXSIZE] resulting matrix
		int RowA,int ColA dimensions matrix 1
		int RowB,int ColB dimensions matrix 2
		int *RowP, int *ColP dimensions result
	  Output parameter:
	  	0 if okay, or -1 if incompatible sizes*/
int MatMult (int MatA[MAXSIZE][MAXSIZE],int MatB[MAXSIZE][MAXSIZE],
	     int MatP[MAXSIZE][MAXSIZE],int RowA,int ColA,
	     int RowB, int ColB, int *RowP, int *ColP) {
	int i, j, k;		/* counters */
	*RowP = RowA;
	*ColP = ColB;
	
	if(ColA == RowB){ /* check to see if compatible to be multiplied */
		for(i=0;i<RowA;i++){
			for(j=0;j<ColB;j++){
				MatP[i][j] = 0; /* starts entry at 0 */
				for(k=0;k<ColB;k++){ /*does multiplication */
					MatP[i][j] += (MatA[i][k] * MatB[k][j]);
				}
			}
		}
	}else{
		return -1;
	}
	return 0;		/* if no break, return 0 */
}

/*===== Function Ortho: check for orthogonal matrix*/
	/*Input parameters:
		int Mat[MAXSIZE][MAXSIZE] matrix
		int Transp[MAXSIZE][MAXSIZE] its transpose
		int Size dimensions
	  Output parameter:
	  	0 for yes or -1 for no*/
	/*Given a square matrix, its dimensions in Size,
	  and its transpose in Transp, check if Mat is
	  orthogonal by comparing if Mat x Transp = Identity */
	/*It also calls the function:
	  MatMult(Mat,Transp,Prod,MR,MC,TR,TC,&PR,&PC)
	  to multiply the two matrices before comparing the result to I*/
int Ortho (int Mat[MAXSIZE][MAXSIZE],int Transp[MAXSIZE][MAXSIZE],int Size) {
	int i,j;			/* counters */
	int MR = Size;
	int MC = Size;
	int TR = Size;
	int TC = Size;
	int PR, PC;
	int Prod[MAXSIZE][MAXSIZE];
					/* check if mult possible */
	int temp = MatMult(Mat,Transp,Prod,MR,MC,TR,TC,&PR,&PC);
	
	if (temp == 0 && PR==PC){	/* check for orthogonal */	 
		for(i=0;i<Size;i++){
			for(j=0;j<Size;j++){
				if(i==j && Prod[i][j]!=1){ /* check diagonal */
					return -1;
				}
					/* check upper and lower triangular */
				if(i!=j && Prod[i][j]!=0){
					return -1;
				}
			}
		}
	}else{
		return -1;
	}
	return 0;		/* if no breaks, return 0 */
}

/*===============================================*/
int main() {
/* ------------------------------------------------------------ */
	int MatMain[MAXSIZE][MAXSIZE];		/*the initial matrix*/
	int RsizeM, CsizeM;			/*matrix row size and column size*/
	int nir;				/*counters*/
	int MatTransp[MAXSIZE][MAXSIZE]; 	/*the transpose*/
	int RsizeTr,CsizeTr;			/*transpose row size and column size*/
/* ------------------------------------------------------------ */
	fprintf(stdout, "Matrix testing program starts\n");	/*Headers*/
	fprintf(stdout, "Jakob Roberts V00484900\n");
	fprintf(stdout, "CSC 230 Assignment 1\n\n");
/* ------------------------------------------------------------ */
	/*open input file - file name is hardcoded*/
	fpin = fopen("INA1.txt", "r");  /* open the file for reading */
	if (fpin == NULL) {
		fprintf(stdout, "Cannot open input file  - Bye\n");
		return(0); 	/* if problem, exit program*/
	}
	/*ASSUMPTIONS: the file is not empty and the matrix has at least 1 element*/
/* ------------------------------------------------------------ */
	nir=RdRowSize(fpin, &RsizeM);			/* read row size of a matrix */
	while (nir == 1) {				/* while not end of file*/
		nir=fscanf(fpin,"%d",&CsizeM);		/* read column size */
		RdMatrix(fpin,MatMain,RsizeM,CsizeM);	/* reads the matrix */
		PrMat(MatMain,RsizeM,CsizeM);		/* prints the matrix */
		
		/* creates a transpose matrix */
		Transpose(MatMain,MatTransp,RsizeM,CsizeM,&RsizeTr,&CsizeTr);
		
		/* starts to do checks on matrices */
		if(RsizeM!=CsizeM){
			fprintf(stdout,"==>Not Square - no testing\n");
		}else{
			/* checks symmetry */
			if(Symm(MatMain, MatTransp,RsizeM)==0){
				fprintf(stdout,"==>Symmetric\n");
			}else{
				fprintf(stdout, "==>Not Symmetric\n");
			}
			
			/* checks skew-symmetry */
			if(SkewSymm(MatMain, MatTransp,RsizeM)==0){
				fprintf(stdout,"==>Skew-Symmetric\n");
			}else{
				fprintf(stdout, "==>Not Skew-Symmetric\n");
			}
			
			/* checks orthogonality */
			if(Ortho(MatMain, MatTransp,RsizeM)==0){
				fprintf(stdout,"==>Orthogonal\n");
			}else{
				fprintf(stdout, "==>Not Orthogonal\n");
			}
			
		}
		
		/* checks for another matrix and repeats the process */
		nir=RdRowSize(fpin, &RsizeM);		/* read next row size */
	}

	fclose(fpin);  /* close the file */
	fprintf(stdout, "\nAll done - Bye\n");

	return (0);
}
