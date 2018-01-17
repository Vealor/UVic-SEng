/* Jakob Roberts
 * CSC360
 * Assignment 1
 * 
 * Tasks:
 * 	1: Run Linux External Commands
 * 	2: Implement:
 *		CD & update prompt
 * 	3: maintain DS and implement:
 *		bg with &
 *		bglist
 * 		
 * 	Bonus: fg, bgkill, bgstop, bgstart
 */
//===================================
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <errno.h>
#include <readline/readline.h> 
#include <readline/history.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <signal.h>
#include <linux/limits.h>
//===================================

//===================================
//===================================

char* tryMalloc(int size){
	char* try = malloc(size);
	if(try == NULL){
		printf("Error: Insufficient memory. Exiting.\n");
		exit(EXIT_FAILURE);
	}
	return try;
}

char* getPrompt(){
	char* prompt1 = "RSI: ";
	char* prompt2 = tryMalloc(PATH_MAX);
	int i = 1;
	
	//try and get cwd
	while(!getcwd(prompt2, i*PATH_MAX)){
		/*is prompt2 too small?*/
		if(errno == ERANGE){
			i++;
			/*can reallocate enough memory?*/
			if((prompt2 = realloc(prompt2, i*PATH_MAX)) == NULL){
				printf("Error: Insufficient memory. Exiting.\n");
				exit(EXIT_FAILURE);
			}
		/*any other error is fatal*/
		}else{
			perror("RSI");
			exit(EXIT_FAILURE);
		}
	}
	char* prompt3 = " > ";
	char* prompt = tryMalloc(strlen(prompt1)+strlen(prompt2)+strlen(prompt3)+1);
	strcpy(prompt, prompt1);	
	strcat(prompt, prompt2);
	strcat(prompt, prompt3);
	free(prompt2);
	return(prompt);
}

int parse(char* reply, char** argv, int size){
	int i = 0;
	char* delim = " \t\n";
	/* while there are more tokens*/
	argv[i] = strtok(reply,delim);
	while(argv[i]){
		/*if we're at the end of allocated space for argv*/
		if(i == (size-1)){
			size*=2;
			/*try to reallocate enough space*/
			if((argv = realloc(argv, size*sizeof(char*))) == NULL){
				printf("Error: Insufficient memory. Exiting\n");
				exit(EXIT_FAILURE);
			}
			/*append to the next token*/
		}
		argv[++i] = strtok(NULL,delim);
	}
	return i;
}

void execute(char** argv, int args){
	/*attempt to create child process*/
	pid_t child = fork();
	int status;
	int bgflag = 0;
	/* if successful */
	if(child >= 0){
		/*if the child process is active*/
		if(child == 0){
			/*if background process remove & from argv*/
			if(!strcmp(argv[args-1],"&")){
				argv[args-1] = NULL;
				bgflag = 1;
			}
			/*try to replace self with code for command given in argv*/
			if(execvp(argv[0], argv)){
				perror("RSI execvp");
			}
			exit(EXIT_FAILURE);
			/*unsuccessful*/
		}else if(child > 0){
			/* if childis background process */ 
			if(!strcmp(argv[args-1],"&")){
				/*notify user of it's creation and pid */
				printf("Child created:    %d: %s\n",child,argv[0]);
			/*if child is foreground process*/
			}else{
				if(waitpid(child,&status,WNOHANG) == -1){
					perror("RSI waitpid");
				}
				/*while child has not been terminated, check status*/
				while(!WIFEXITED(status) && !WIFSIGNALED(status)){
					if(waitpid(child,&status,WNOHANG) == -1){
						perror("RSI waitpid");
					}
				}
			}
		}
	/* if unsuccessful, print error */
	}else{
		perror("RSI fork");
	}
	if(!bgflag){
		wait(&status);
	}	
}

void altDir(char** argv, int args){
	/* if there are too many args, give user usage info */
	if(args > 2){
		printf("cd:  usage:  cd [dir]\n");
	}else{
		/*if no args or arg is "~" attempt to retrieve home directory*/
		if(args==1 || !strcmp(argv[1],"~")){
			char* path = getenv("HOME");
			if(path == NULL){
				printf("Error: Could not retrieve home directory\n");
			}else{
				/* if successful, attempt to navigate to home dir*/
				if(chdir(path)==-1){
					perror("RSI chdir");
				}
			}
		/*otherwise, attept to go to directory given in argv */
		}else{
			if(chdir(argv[1]) == -1){
				perror("RSI chdir");
			}
		}
	}
}

void handler(){
	int status;
	/*retrieve child's pid and status*/
	pid_t child = waitpid(-1,&status, WNOHANG);
	/*if there isa  child status change*/
	if(child > 0){
		/*if the child has terminated normally or been killed then notify the user*/
		if(WIFEXITED(status) || WIFSIGNALED(status)){
			/*prinf is not async-signal safe has undefined behaviour*/
			printf("%d has terminated\n",child);
		}
	}
}


//===================================
int main(){
	/*clear screen*/
	system("clear");
	

	struct sigaction action;
	action.sa_handler = handler;
	action.sa_flags = SA_NODEFER;
	sigemptyset(&action.sa_mask);
	/*if signal handler fails*/
	if(sigaction(SIGCHLD,&action,NULL) == -1){
		perror("RSI sigaction");
	}
	
	
	char* prompt = getPrompt();
	int quitcode = 1;
	while(quitcode){
		char* reply = readline(prompt);
		/*if user quits, exit loop*/
		if(!strcmp(reply, "quit")){
			quitcode = 0;
		}else{
			int argCnt = ARG_MAX;
			char** argv = (char**)tryMalloc(argCnt*sizeof(char*));
			argCnt = parse(reply,argv,argCnt);
			/*if user has given a command*/
			if(argCnt > 0){
				/*if the command is cd*/
				if(!strcmp(argv[0],"cd")){
					altDir(argv, argCnt);
					free(prompt);
					prompt = getPrompt();
				/*otherwise attempt to fork and execute*/
				}else{
					execute(argv, argCnt);
				}
			}
			free(argv[0]);
			free(argv);
		}
		/*clear input buffer*/
		setbuf(stdin,NULL);
		
	}
	printf("RSI: Exiting normally.\n");
	free(prompt);
	return(0);
}






	/*int size = 100;
	char cur[size];
	getcwd(cur,size);
	
	//char* prompt = "shell> "; //to be cwd
	
	char* cwd = "";
	getcwd(prompt,50);
						//use dynamic memory
	int quitcode = 0;
	while(!quitcode){
		
		char* input = readline(cur); //note strips final \n
		
		if(!strcmp(prompt, "quit")){ //if input is exit, then quit
			quitcode = 1;
		}else{
			//do something with input text
			
			//token input text with strtok
			//use with an exec to get system command
			
			//fork and get PID and keep track
			//display active PID with PS command
			
			//have exit error message for fork if < 0
			//parent runs off > 0, child runs off 0
			
			
			
		}
		
		
		free(input);
	}
	
	// exec clear
	
	
	*/

