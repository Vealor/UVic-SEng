#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <errno.h>
int main()
{
	int size = 100;
	char cur[size];
	getcwd(cur,size);
	printf("\nThe current working directory of cur: %s\n", cur);

	char *change = "~";
	if (*change == '~')
		change = "/tmp";
	printf("\nNow, let's change the working directory.\n");
	if (chdir(change)==0){  // Success
		getcwd(cur,size);
		printf("\nSuccess...The current working directory of cur: %s\n", cur);

	}
	else{   //Failure
		getcwd(cur,size);
		printf("\nFailed! The current working directory of cur: %s\n", cur);

	}
	return 0;
}
