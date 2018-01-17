// Includes 
#include <unistd.h>     // Symbolic Constants 
#include <sys/types.h>  // Primitive System Data Types  
#include <errno.h>      // Errors 
#include <stdio.h>      // Input/Output 
#include <sys/wait.h>   // Wait for Process Termination 
#include <stdlib.h>     // General Utilities 
#include <time.h>

int main()
{
    pid_t childpid; // variable to store the child's pid 
    int retval;     // child process: user-provided return code 
    int status;     // parent process: child's exit status 
    int i=0;

    // now create new process 
    childpid = fork();
    if (childpid >= 0) // fork succeeded 
    {
        if (childpid == 0) // fork() returns 0 to the child process 
        {
            i = 2;
            printf("CHILD: I am the child process!\n");
            printf("CHILD: Here's my PID: %d\n", getpid());
            printf("CHILD: %d Sleeping for second...\n", getpid());
			sleep(1);
            printf("CHILD: %d Enter an exit value (0 to 255): ", getpid());
            scanf(" %d", &retval);
            printf("CHILD: %d Goodbye!\n", getpid());    
            printf("child's i: %d\n", i);
            exit(retval); // child exits with user-provided return code 
        }
        else // fork() returns new pid to the parent process 
        {
			printf("\nparent's i: %d\n", i);
            printf("PARENT: I am the parent process!\n");
			printf("PARENT: the pid of my copy of childpid is %d\n", childpid);
			printf("PARENT: I will wait for my child to exit\n");
			wait(&status);	//wait for child to exit and store its status
			printf("PARENT: child's exit code: %d\n", WEXITSTATUS(status));
			printf("PAREN: bye\n");
			exit(0);
        }
    }
    else // fork returns -1 on failure
    {
        perror("fork"); // display error message 
        exit(0); 
    }
}
