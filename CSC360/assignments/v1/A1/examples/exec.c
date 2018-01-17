/* exec.c */
#include <unistd.h>
#include <stdio.h>
#include <errno.h>
int main(void)
{
	char *envp[] = {"PATH=/tmp", "USER=sajjadi", "STATUS=testing", NULL};
	char *argv_execv[] = {"echo", "excuted by execv", NULL};
	char *argv_execvp[] = {"echo", "executed by execvp", NULL};
	char *argv_execve[] = {"env", NULL};
	if (fork() == 0)
		if (execl("/bin/echo", "echo", "executed by execl", NULL) < 0)
			perror("Err on execl");
	if (fork() == 0)
		if (execlp("echo", "echo", "executed by execlp", NULL) < 0)
			perror("Err on execlp");
	if (fork() == 0)
		if (execle("/usr/bin/env", "env", NULL, envp) < 0)
			perror("Err on execle");
	if (fork() == 0)
		if (execv("/bin/echo", argv_execv) < 0)
			perror("Err on execv");
	if (fork() == 0)
		if (execvp("echo", argv_execvp) < 0)
			perror("Err on execvp");
	if (fork() == 0)
		if (execve("/usr/bin/env", argv_execve, envp) < 0)
			perror("Err on execve");
	return 0;
}
