#include <readline/readline.h>
#include <readline/history.h>
#include <string.h>
#include <stdio.h>
#include <stdlib.h>

char string[] = "This is - CSC360 Tutorial - example";
char seps[] = "-";
char *token;
int main( void )
{
	char *line;
	printf("Enter your input string (2 words or more):");
	line = readline(NULL);	//reading user input
	token = strtok(line, " ");
	while(token != NULL)
	{
		//printf("[%s]\n", token);
		printf("%s\n", token);
		token = strtok(NULL, " ");
	}

	return 0;
}
