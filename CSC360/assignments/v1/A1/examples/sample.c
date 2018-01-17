#include <stdio.h>
#include <readline/readline.h>
#include <readline/history.h>

int
main()
{
	const char* prompt = "shell> ";

	int bailout = 0;
	while (!bailout) {

		char* reply = readline(prompt);
		/* Note that readline strips away the final \n */
		/* For Perl junkies, readline automatically chomps the line read */

		if (!strcmp(reply, "bye")) {
			bailout = 1;
		} else {
			printf("\nYou said: %s\n\n", reply);
		}
	
		free(reply);
	}
	printf("Bye Bye\n");
}
