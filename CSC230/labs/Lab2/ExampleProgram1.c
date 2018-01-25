/* CSc230, Lab2 Example C program 1 */

#include <stdio.h>
#include <stdlib.h>

int main(){
	int mymatrix[3][3] = {{1,2,3},{4,5,6},{7,8,9}};
	int ii,jj;
	fprintf(stdout,"Hello friends, welcome \n");
	fprintf(stdout,"The matrix contains:\n");
	for(ii=0;ii<3;ii++){
		for(jj=0;jj<3;jj++){
			fprintf(stdout,"%d", mymatrix[ii][jj]);
		}
		fprintf(stdout,"\n");
	}
	printf("End of Execution, Bye\n");
	exit(0);
}
