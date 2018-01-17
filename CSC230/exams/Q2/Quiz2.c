/* QUIZ 2 */
/*MY NAME = Jakob Roberts */
/*MY STUDENT NUMBER = v00484900*/

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
	/* Note: the size of the list is the first element of the list*/
void printlist(int List[MAXSIZE*2], int listnum) { 
	int i;
	printf("Size of List %d is %d\n",listnum,List[0]);  /*print the size*/
	printf("\t List %d elements are:\n \t",listnum);	/*print the list*/
	for (i=1;i<=List[0];i++) {
		printf(" %3d",List[i]);
		}
	printf("\n\n");
}
/*====================================*/

/* ==== Procedure Merge: Merges Two lists together */
	/*Input parameters:
		int List1[MAXSIZE]	array containing 1st list
		int List2[MAXSIZE]	array containing 2nd list
		int List3[MAXSIZE*2]	array containing the output list
	  Output parameters:    none*/
	/* Note: the size of the lists is the first element of the lists */
void Merge(int L1[MAXSIZE], int L2[MAXSIZE], int L3[MAXSIZE*2]){
	int i=1,j=1,k=1;
	int sizeL1=L1[0], sizeL2=L2[0];
	
	/* compares the front two items at the indexes in the list
	   then prints out the smallest of the two, if reaches the
	   end of either list, moves on
	 */
	while(i<=sizeL1 && j<=sizeL2){		
		if(L1[i] < L2[j]){		/* if element in list 1 is smaller than 2 */
			L3[k++]=L1[i++];
		}else{					/* if element in list 2 is smaller than 1 */
			L3[k++]=L2[j++];
		}
	}
	
	/* checks which list of the two reached the end and then
	   adds the rest of the remaining list to the end of the
	   new merged list
	 */
	if(i>sizeL1){		/* if list 1 finished */
		while(j<=sizeL2){
			L3[k++]=L2[j++];
		}
	}else{				/* if list 2 finished */
		while(i<=sizeL1){
			L3[k++]=L1[i++];
		}
	}
	
	L3[0]=k-1;		/* assigns the length of the merged list to index 0 */
}


/*====================================*/
int main()
{
	int List1[MAXSIZE],List2[MAXSIZE];
	int	List3[MAXSIZE*2];
	int okay;
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
		
		/*read the second list */
		okay=ReadList(fp,List2);		/*no need to check for EOF */
		
		printlist(List1,1);				/* prints list 1 */
		printlist(List2,2);				/* prints list 2 */
		Merge(List1,List2,List3);		/* merges list 1 and 2 */
		printlist(List3,3);				/* prints list 3 */

		okay=ReadList(fp,List1); /*read the next set of lists */
	}

	fclose(fp);  /* close the input file */
	fprintf(stdout, "\nAll done - Bye\n");

	return (0);
}




