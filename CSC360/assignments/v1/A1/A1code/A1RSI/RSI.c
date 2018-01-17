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
/*===============================================*/
#include "RSI.h"
/*===============================================*/
#define PATH_MAX 4096
/*===============================================*/
typedef struct process_structure{
	char* pexec;
	pid_t PID;
	struct process_structure* Next;
}Process;
Process* headproc = NULL;
/*===============================================*/
char* doMalloc(int size){
	char* attempt = malloc(size);
	if(attempt == NULL){
		printf("<<ERROR>>: Not enough memory. Quitting.\n");
		exit(EXIT_FAILURE);
	}
	return attempt;
}
/*===============================================*/
char* printPrompt(){
	char* p1 = "RSI: ";
	char* p2 = doMalloc(PATH_MAX);
	char* p3 = " > ";
	/* try and get CWD, check size of prompt! */
	while(!getcwd(p2,PATH_MAX)){
		if(errno==ERANGE){
			if((p2 = realloc(p2, 2*PATH_MAX))==NULL){
				printf("<<ERROR>>: Not enough memory. Quitting.\n");
				exit(EXIT_FAILURE);
			}
		}else{
			perror("prompt print");
			exit(EXIT_FAILURE);
		}
	}
	char* theprompt = doMalloc(strlen(p1)+strlen(p2)+strlen(p3)+1);
	strcpy(theprompt, p1);
	strcat(theprompt, p2);
	strcat(theprompt, p3);
	free(p2);
	return(theprompt);
}
/*===============================================*/
int parser(char* input, char** tokline, int insize){
	int increment = 0; /* token increment */
	char* tokdelims = " \t\n";
	tokline[increment] = strtok(input,tokdelims);
	while(tokline[increment]){
		if(increment == (insize-1)){
			insize*=2;
			if((tokline = realloc(tokline, insize*sizeof(char*)))==NULL){
				printf("<<ERROR>>: Not enough memory. Quitting.\n");
				exit(EXIT_FAILURE);
			}
		}
		tokline[++increment] = strtok(NULL,tokdelims);
	}
	return increment;	
}
/*===============================================*/
void bgexecute(char** linetok, int linelen,char* input){
	pid_t child = fork();
	pid_t bgpid;
	int status=0;
	if(child==0){ 		/* in child */
		if(execvp(linetok[0],linetok)){
			perror("execvp FAILED! on background");
			exit(EXIT_FAILURE);
		}else{
			removeProcess(child);
		}
	}else if(child > 0){ 			/* in parent */	
		addProcess(input,child);
		printf("Child born=> PID:%d\tCMD:%s\n",child,input);	
		getchar();
	}
}
/*===============================================*/
void execute(char** linetok, int linelen,char* input){
	pid_t child = fork();
	pid_t bgpid;
	int status;
	if(child == 0){
		if(execvp(linetok[0],linetok)){
			perror("execvp FAILED!");
		}
		exit(EXIT_FAILURE);
	}else if(child >0){
		wait(&status);	
	}else{
		perror("fork() problem");
	}
}
/*===============================================*/
void chgDir(char** line, int items){
	if(items > 2){
		printf("usage:  cd [dir]\n");
	}else{
		if(items==1 || !strcmp(line[1],"~")){
			char* theHome = getenv("HOME");
			if(theHome==NULL){
				printf("<<ERROR>>: Can't get home directory.\n");
			}else{
				if(chdir(theHome)==-1){
					perror("chdir error");
				}
			}
		}else{
			if(chdir(line[1])==-1){
				perror("chdir error");
			}
		}
	}
}
/*===============================================*/
void addProcess(char* procname, pid_t PID){
	if(headproc==NULL){
		Process* newproc = (Process*)malloc(ARG_MAX*sizeof(Process*));
		if(newproc == NULL){
			printf("<<ERROR>>: Not enough memory. Quitting.\n");
			exit(EXIT_FAILURE);
		}
		headproc = newproc;
		newproc->pexec = doMalloc(ARG_MAX*sizeof(procname));
		strcpy(newproc->pexec,procname);
		newproc->PID = PID;
		newproc->Next = NULL;
	}else{
		Process* newproc = (Process*)malloc(ARG_MAX*sizeof(Process*));
		if(newproc == NULL){
			printf("<<ERROR>>: Not enough memory. Quitting.\n");
			exit(EXIT_FAILURE);
		}
		Process* temp = headproc;
		while(temp->Next != NULL){
			temp = temp->Next;
		}
		temp->Next = newproc;
		newproc->pexec = doMalloc(ARG_MAX*sizeof(procname));
		strcpy(newproc->pexec,procname);
		newproc->PID = PID;
		newproc->Next = NULL;
	}
}
/*===============================================*/
void removeProcess(pid_t PID){
	Process* toremove = headproc;
	Process* marker = headproc;
	if(toremove == NULL){
	}else if(headproc->PID==PID){
		toremove = headproc;
		headproc = headproc->Next;
		free(toremove);
	}else if(headproc->Next != NULL && headproc->Next->PID==PID){
		toremove=headproc->Next;
		headproc=headproc->Next->Next;
		free(toremove);
		
	}else{
		toremove=toremove->Next;
		while((toremove->Next != NULL) && (toremove->PID!=PID)){
			toremove=toremove->Next;
			marker=marker->Next;
		}
		if(toremove != NULL && toremove->PID==PID){
			marker->Next=toremove->Next;
			free(toremove);
		}
	}
}
/*===============================================*/
void bglist(){
	Process* list = headproc;
	printf("- PID -\t\t- Command -\n");
	printf("------------------------------------\n");
	if(list==NULL){
		printf("<<NO ACTIVE PROCESSES>>\n\n");
	}else{
		while(list!=NULL){
			printf("%d\t\t%s\n",list->PID,list->pexec);
			list=list->Next;
		}
	}
}
/*===============================================*/
void handler(){
	int status;
	pid_t child = waitpid(-1,&status,WNOHANG);
	if(child >0){
		if(WIFEXITED(status)||WIFSIGNALED(status)){
			removeProcess(child);
			printf("PID:%d has terminated\n",child);
		}
	}
}
/*===============================================*/
int main(){
	system("clear");
	/* for handler */
	struct sigaction watcher;
	watcher.sa_handler = handler;
	watcher.sa_flags = SA_NODEFER;
	sigemptyset(&watcher.sa_mask);
	if(sigaction(SIGCHLD,&watcher,NULL) == -1){
		perror("problem with sigaction handler");
	}
	//=================
	char* prompt = printPrompt();
	int quitcode = 0;
	while(!quitcode){
		char* input = readline(prompt);
		char incopy[ARG_MAX];
		strcpy(incopy,input);
		int linelen = ARG_MAX;
		char** linetok = (char**)doMalloc(linelen*sizeof(char*));
		linelen = parser(input,linetok,linelen);
		if(linelen > 0){
			if(!strcmp(linetok[0],"quit")){
				quitcode = 1;
				break;
			}else if(!strcmp(linetok[0],"cd")){
				chgDir(linetok,linelen);
				free(prompt);
				prompt = printPrompt();
			}else if(!strcmp(linetok[0],"bglist")){
				bglist();
			}else{
				if(!strcmp(linetok[linelen-1],"&")){
					linetok[--linelen]=NULL;
					bgexecute(linetok,linelen,incopy);
				}else{
					execute(linetok,linelen,incopy);
				}
			}
		}
		free(linetok[0]); free(linetok); setbuf(stdin,NULL);
	}	
	free(prompt);
	printf("-------- EXITING NORMALLY --------\n");
	return(0);	
}
/*===============================================*/
