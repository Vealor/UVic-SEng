/* File: Assignment 1 */
/* #### Final version #### */

/* Captain Jean Luc Picard V00123456 */

/*Read 2 dimensional matrices and their sizes until
	end of file. Compute the transpose. Test if a matrix
	is symmetrical, skew-symmetrical or orthogonal */
#include <stdio.h>
#define MAXSIZE 10

/* global variables and functions */
	/* Best to pass as input parameter and not used
		as global - here only for safeguard */
FILE *fpin;		/*pointers to input file*/

/*==== Function RdRowSize: reads the row size of a matrix from a file */
/*Input parameters:
	FILE *fp	pointer to input file
	int *Nrows	pointer to row size to be returned
  Output parameters:
  	1 if okay, -1 if end of file found*/
int RdRowSize(FILE *fp, int *Nrows) {
	int	nir;		/*number of integers read in*/
	nir = fscanf(fp,"%d",Nrows);  /* read size if there */
	if (nir == 1)
		return(1);
	else
		return(-1);		/*end of file found*/
}

/*==== Procedure RdMatrix: reads the elements of a matrix from a file */
/*Input parameters:
	FILE *fp	pointer to input file
	int Mat[MAXSIZE][MAXSIZE]	2D array for matrix
	int R,int C		row size and column size
  Output parameters:
  	None */
void RdMatrix(FILE *fp,int Mat[MAXSIZE][MAXSIZE],int R,int C) {
	int	i,j,temp;
	for (i=0;i<R;i++) {
		for (j=0;j<C;j++) {
			fscanf(fp,"%d",&temp);
			Mat[i][j]=temp;
		}
	}

}

/*===== Procedure PrMat: prints a 2-dimensional matrix of integers row by row*/
/*Input parameters:
	int Mat[MAXSIZE][MAXSIZE]	the matrix to be printed
	int R, int C		the row and column dimensions
	Ouput parameter: None	*/
void PrMat (int Mat[MAXSIZE][MAXSIZE],int R,int C) {
	int ii,jj;
	for (ii=0;ii<R;ii++) {
		fprintf(stdout,"     ");
		for (jj=0;jj<C;jj++) {
			fprintf(stdout,"%5d  ",Mat[ii][jj]);
		}
		fprintf(stdout,"\n");
	}
	fprintf(stdout,"\n");
}


/*===== Procedure Transpose: construct the transpose of a matrix*/
/*Input parameters:
	int Mat[MAXSIZE][MAXSIZE]	the original matrix
	int Transp[MAXSIZE][MAXSIZE]	the transpose to be built
	int RM,int CM		the original row and column dimensions,
	int *RT,int *CT		the transpose row and column dimensions
	Ouput parameter: None	*/
/*Given a matrix and its dimensions in RM and CM,
	construct its transpose in Transp with dimensions RT and CT as in:
	copy rows 0,1,...,RM-1 of Mat to cols 0,1,...,CT-1 of Transp */
void Transpose (int Mat[MAXSIZE][MAXSIZE],
				int Transp[MAXSIZE][MAXSIZE],
				int RM,int CM,int *RT,int *CT) {
	int i,j;
	*RT=CM;		/*set sizes*/
	*CT=RM;
	for (i=0;i<RM;i++) {
		for (j=0;j<CM;j++) {
			Transp[j][i]=Mat[i][j];
		}
	}
}

/*===== Function Symm: check for symmetric matrix*/
/*Input parameters:
	int Mat[MAXSIZE][MAXSIZE]		the  matrix
	int Transp[MAXSIZE][MAXSIZE]	its transpose
	int Size						dimensions,
	Output parameter:
	0 for yes or -1 for no */
/*Given a square matrix,check if it is symmetric
  by comparing if Mat = Transp.*/
 int Symm (int Mat[MAXSIZE][MAXSIZE],
			int Transp[MAXSIZE][MAXSIZE],int Size) {
	int i,j;
	for (i=0;i<Size;i++) {
		for (j=0;j<Size;j++) {
			if (Mat[i][j] != Transp[i][j])
				return(-1);
		}
	}
	return(0);
}

/*===== Function Symm2: check for symmetric matrix*/
/**** 2nd version more old style structure ***/
/*Input parameters:
	int Mat[MAXSIZE][MAXSIZE]		the  matrix
	int Transp[MAXSIZE][MAXSIZE]	its transpose
	int Size						dimensions,
	Output parameter:
	0 for yes or -1 for no */
/*Given a square matrix,check if it is symmetric
  by comparing if Mat = Transp.*/
 int Symm2 (int Mat[MAXSIZE][MAXSIZE],
			int Transp[MAXSIZE][MAXSIZE],int Size) {
	int i=0;
	int j=0;
	int flag = 1;
	while ((flag)&(i<Size)) {
		while ((flag == 0) & (j<Size)) {
			flag = (Mat[i][j] == Transp[i][j]);
			j++;
		}
		i++;
	}
	return(!flag);
}

/*===== Function SkewSymm: check for symmetric matrix*/
/*Input parameters:
	int Mat[MAXSIZE][MAXSIZE]		the  matrix
	int Transp[MAXSIZE][MAXSIZE]	its transpose
	int Size						dimensions
	Output parameter:
	0 for yes or -1 for no */
/*Given a square matrix,check if it is skew-symmetric
  by comparing if Mat = - Transp.*/
 int SkewSymm (int Mat[MAXSIZE][MAXSIZE],
			int Transp[MAXSIZE][MAXSIZE],int Size) {
	int i,j;
	for (i=0;i<Size;i++) {
		for (j=0;j<Size;j++) {
			if (Mat[i][j] != -Transp[i][j])
				return(-1);
		}
	}
	return(0);
}

/*===== Function MatMult: multiply 2 matrices - must check
		for compatibility of dimensions*/
/*Input parameters:
	int MatA[MAXSIZE][MAXSIZE]		matrix 1
	int MatB[MAXSIZE][MAXSIZE]		matrix 2
	int MatP[MAXSIZE][MAXSIZE]		resulting matrix
	int RowA,int ColA				size matrix 1
	int RowB,int ColB				size matrix 2
	int *RowP, int *ColP			size result
  Output parameter:
    0 if okay, or -1 if incompatible sizes*/
int MatMult (int MatA[MAXSIZE][MAXSIZE],
			int MatB[MAXSIZE][MAXSIZE],
			int MatP[MAXSIZE][MAXSIZE],
			int RowA,int ColA,
			int RowB, int ColB, int *RowP, int *ColP) {
	int i,j,k,sum;
	if (ColA != RowB)
	 return(-1);
	*RowP=RowA;
	*ColP=ColB;
	for (i=0;i<RowA;i++) {
		for (j=0;j<ColB;j++) {
			sum=0;
			for (k=0;k<ColA;k++) {
				sum = sum +MatA[i][k] * MatB[k][j];
			}
			MatP[i][j] = sum;
		}
	}
	return(0);
}

/*===== Function CheckId: check if square matrix is identity*/
/*Input parameters:
	int Mat[MAXSIZE][MAXSIZE]		matrix
	int Size						dimensions
  Output parameter:
    Return 0 for yes or -1 for no*/
 /*Given a square matrix, its dimensions in Size,
	compare it to Identity */
int CheckId (int Mat[MAXSIZE][MAXSIZE],int Size) {
	int i,j;
	for (i=0;i<Size;i++) {
		for (j=0;j<Size;j++) {
			if (i==j) {
				if (Mat[i][j] != 1) return (-1);
			}
			else {
				if (Mat[i][j] != 0) return (-1);
			}
		}
	}
	return(0);

}

/*===== Function Ortho: check for orthogonal matrix*/
/*Input parameters:
	int Mat[MAXSIZE][MAXSIZE]		matrix
	int Transp[MAXSIZE][MAXSIZE]	its transpose
	int Size						dimensions
  Output parameter:
    Return 0 for yes or -1 for no*/
 /*Given a square matrix, its dimensions in Size,
	and its transpose in Transp, check if Mat is
	orthogonal by comparing if Mat x Transp = Identity */
/*It also calls the function:
	int MatMult(Mat,Transp,Prod,Size,Size,Size,Size,&Size,&Size)
	to multiply the two matrices before comparing the result to I*/
int Ortho (int Mat[MAXSIZE][MAXSIZE],
			int Transp[MAXSIZE][MAXSIZE],int Size) {
	int MatProd[MAXSIZE][MAXSIZE];			/*product matrix*/
	int ok;
	/*Multiply matrix and its transpose*/
	ok = MatMult(Mat,Transp,MatProd,Size,Size,Size,Size,&Size,&Size);
	ok = CheckId(MatProd,Size);	/* check if product = Identity */
	return(ok);
}

/*===============================================*/
int main() {

    int MatMain[MAXSIZE][MAXSIZE];	/*the initial matrix*/
  	int RsizeM, CsizeM;				/*matrix row size and column size*/
 	int	nir; 						/*counters*/
 /*	int	nir,i,j,temp; */				/*counters*/
 	int MatTransp[MAXSIZE][MAXSIZE]; 		/*the transpose*/
 	int RsizeTr,CsizeTr;			/*transpose row size and column size*/
	int	MatNumber=0;			/*counter for which matrix*/
	int Check;					/*flag for testing*/

	fprintf(stdout, "Matrix testing program starts\n");	/*Headers*/
	fprintf(stdout, "Captain Picard V00123456\n");
	fprintf(stdout, "Summer 2012 - CSC 230 Assignment 1\n\n");
	/*open input file*/
	fpin = fopen("INA1.txt", "r");  /* open the file for reading */
	if (fpin == NULL) {
		fprintf(stdout, "Cannot open input file  - Bye\n");
		return(0); 					/*if problem, exit program*/
	}
	/*ASSUMPTION: the file is not empty to start with
		and the matrix will have at least 1 element*/

	/* read row size of a matrix */
	nir=RdRowSize(fpin,&RsizeM);
	while (nir == 1) {				/* while not end of file*/
		fscanf(fpin,"%d",&CsizeM);	/*read column size*/
		RdMatrix(fpin,MatMain,RsizeM,CsizeM);	/*read the matrix */
		MatNumber++;				/*matrix counter*/
		/*print the matrix and the sizes*/
		fprintf(stdout, "\n\n*** MATRIX %2d ***",MatNumber);
		fprintf(stdout, "  Size = %2d x %2d\n",RsizeM,CsizeM);
		PrMat(MatMain,RsizeM,CsizeM);

		/*construct the transpose*/
		Transpose(MatMain,MatTransp,RsizeM,CsizeM,&RsizeTr,&CsizeTr);
		/*print the transpose and the sizes*/
		fprintf(stdout, "    Transpose:");
		fprintf(stdout, "  Size = %2d x %2d\n",RsizeTr,CsizeTr);
		PrMat(MatTransp,RsizeTr,CsizeTr);

		/*If square matrix, check for symmetry,
			skew-symmetry, orthogonality */
		if (RsizeM == CsizeM) {
			Check = Symm(MatMain,MatTransp,RsizeM); /*symmetry*/
			if (Check == 0)
				fprintf(stdout, "    ==>Symmetric\n");
			else
				fprintf(stdout, "    ==>Not Symmetric\n");
			Check = SkewSymm(MatMain,MatTransp,RsizeM); /*skew-symmetry*/
			if (Check == 0)
				fprintf(stdout, "    ==>Skew-Symmetric\n");
			else
				fprintf(stdout, "    ==>Not Skew-Symmetric\n");
			Check = Ortho (MatMain,MatTransp,RsizeM); /*orthogonality*/
			if (Check == 0)
				fprintf(stdout, "    ==>Orthogonal\n");
			else
				fprintf(stdout, "    ==>Not Orthogonal\n");
		}
		else
			fprintf(stdout, "    ==>Not Square - no testing\n");

		nir=RdRowSize(fpin,&RsizeM); /*read next matrix*/
	}

	fclose(fpin);  /* close the file */
	fprintf(stdout, "\nAll done - Bye\n");

	return (0);
}
