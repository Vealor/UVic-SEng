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
