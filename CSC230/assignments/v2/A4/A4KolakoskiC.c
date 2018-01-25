/* Jakob Roberts
 * V00484900
 * CSC230 - Assignment 4
 * Kolakoski's Sequence
 */

#include <stdlib.h>
#include <stdio.h>


#define MAXRUNS 50

int AppendKseq(int Kseq[],int startindex,int *Num1){
	int n,i;
	for(n=2;n<=MAXRUNS;n++){
			for(i=1;i<=Kseq[n];i++){
				Kseq[startindex] = (1+(n%2));
				if(Kseq[startindex] == 1){
					*Num1 += 1;
				}
				startindex++;
			}
	}
	return startindex-1;
}

void PrintKseq(int Kseq[],int index){
	int i,j=1;
	for(i=0;i<=(index/10);i++){
		fprintf(stdout,"  ");
		for(;j<=index;j++){
			if((j-1)/10 != i){
				break;
			}
			fprintf(stdout,"    %d",Kseq[j]);
		}
		fprintf(stdout,"\n");
	}
}

int main(int argc, char **argv){
	int Kseq[MAXRUNS*2];
	int index = 1;
	int Num1 = 0;
	fprintf(stdout,"Jakob Roberts V00484900\n");
	fprintf(stdout,"Kolakoski Program starts\n\n");

	if(MAXRUNS >0){
		Kseq[index] = 1;
		Num1++;
		index++;
	}else{exit(1);}
	if(MAXRUNS >1){
		Kseq[index] = 2;
		index++;
	}else{exit(1);}


	index = AppendKseq(Kseq, index, &Num1);

	fprintf(stdout,"Kolakoski sequence of length %d with %d Runs\n",(index),MAXRUNS);
	fprintf(stdout,"Number of 1's is = %d\n",Num1);
	fprintf(stdout,"Number of 2's is = %d\n",(index-Num1));


	PrintKseq(Kseq,index);
	fprintf(stdout,"Kolakoski Program ends\n");
	return 0;
}