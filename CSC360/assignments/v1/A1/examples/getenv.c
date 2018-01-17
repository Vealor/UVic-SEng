/*
This is not a correct program!!!
$PATH is changed by calling strtok over char *path.
To make it right, save/copy the content of path to another array.
And perform strtok over the new array.
*/
#include<stdio.h>
#include<stdlib.h>
#include<string.h>

int main()
{
	char *path, *cpath;
	char *strArray[150]; 
	path = getenv("PATH");
	strcpy(cpath, path);
	printf("\nFirst Path: %s\n\n", path);
	char seps[] = ":";
	char *token;
	int j=0;

	token = strtok(cpath, seps);
	strArray[j]=token;

	while( token != NULL )
	{
	    token = strtok(NULL, seps);
	    strArray[j]=token;
	    j++;
	}

	path =getenv("PATH");
	printf("Again Path: %s\n\n", path);
	return 0;
}
