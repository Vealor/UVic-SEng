/*------------------------------
* Jakob Roberts - V00484900
* client.c
* Description: HTTP client program
* CSC 361
-------------------------------*/
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <regex.h>
#include <signal.h>
#include <sys/wait.h>
#include <time.h>
#include <errno.h>
#include <regex.h>

#define MAX_DIR_LEN	300
#define MAX_IN_LEN 	300
#define MAX_OUT_LEN	1000000
#define BACKLOG 3

/*---------------------main() routine--------------------------*
 * tasks for main
 * generate socket and get socket id,
 * max number of connection is 3 (maximum length the queue of pending connections may grow to)
 * Accept request from client and generate new socket
 * Communicate with client and close new socket after done
 *---------------------------------------------------------------------------*/
void sigchld_handler(){while(wait(NULL)>0);} // SIGACTION HELPER

int main(int argc, char **argv){
  int newsockid; /* return value of the accept() call */
	int port;
	char* directory;

	char instring[MAX_IN_LEN];
	char outstring[MAX_OUT_LEN];

  int listen_fd, comm_fd; //listen on listen, connect on comm
  struct sockaddr_in servaddr; //server info
  struct sockaddr_in commaddr; //connection info
  int comm_size;

  struct sigaction sa;

  char request[MAX_IN_LEN];
  char location[MAX_IN_LEN];
  char type[MAX_IN_LEN];
  char version[MAX_IN_LEN];

  const char* regtext = "^(.+) (.*) (.+)/(.+)\r\n\r\n";
  regex_t re;
  int status;
  regmatch_t pmatch[5];
  unsigned int g;

  FILE *file_req;

  //START CODE
	if(argc !=3){
		printf("Usage: ./SimpServer <port> <directory>\n");
		exit(1);
	}
	port = atoi(argv[1]);				//INIT USE PORT
	directory = malloc(MAX_DIR_LEN*sizeof(char));
	if(directory==NULL){
		printf("Directory Malloc Error!\n");
		exit(1);
	}
	strcpy(directory,argv[2]);	// INIT FOLDER LOCATION
	printf("Directory: %s\n",directory);
	if ((listen_fd = socket(AF_INET, SOCK_STREAM, 0)) == -1){
		perror("ERROR: Can't init SOCKET.\n");
		exit(1);
	}
	//init server information
  bzero( &servaddr, sizeof(servaddr));
  servaddr.sin_family = AF_INET;
  servaddr.sin_port = htons(port);
  servaddr.sin_addr.s_addr = htons(INADDR_ANY);
  if (bind(listen_fd, (struct sockaddr *)&servaddr, sizeof(struct sockaddr)) == -1){
		perror("ERROR: Could not BIND socket.\n");
		exit(1);
	}
	//attempt to start listening
	if (listen(listen_fd, BACKLOG) == -1){
		perror("ERROR: Could not LISTEN on socket.\n");
		exit(1);
	}else{
		printf("Listening on %d\n",port);
	}
	//handle children
	sa.sa_handler = sigchld_handler;
	sigemptyset(&sa.sa_mask);
	sa.sa_flags = SA_RESTART;
	if (sigaction(SIGCHLD, &sa, NULL) == -1){
		perror("ERROR: Sigaction could not run.\n");
		exit(1);
	}
	//listen and return communications
  while (1){
  	comm_size = sizeof(struct sockaddr_in);
  	if((comm_fd = accept(listen_fd, (struct sockaddr *)&commaddr,&comm_size)) == -1){
  		perror("ERROR: Could not accept connection.\n");
  		continue;
  	}
  	printf("CONNECTION REQUEST >> %s:%d\n",inet_ntoa(commaddr.sin_addr),port);

  	// FORKS PROCESS
  	if(!fork()){ //CHILD
  		close(listen_fd); //close listening socket for cloned child
  		bzero( instring, MAX_IN_LEN);
    	read(comm_fd,instring,MAX_IN_LEN);

    	//parse instring with regex
			if (regcomp(&re, regtext, REG_EXTENDED)) {
		    printf("ERROR: Regex did not compile.	\n");
		  }
		  if(regexec(&re, instring, 5, pmatch, 0)){
		  	printf("\tSENT 417 Expectation Failed >> %s\n\n",instring);
		  	write(comm_fd, "417 Expectation Failed\r\n\r\n", strlen("417 Expectation Failed\r\n\r\n")+1);
		  }
		  g = 0;
		  for (g = 0; g < 5; g++){
		    char sourceCopy[strlen(instring) + 1];
		    strcpy(sourceCopy, instring);
		    sourceCopy[pmatch[g].rm_eo] = 0;
		    if (pmatch[g].rm_so != (size_t)-1){
		      if(g==1){
		        strcpy(request, sourceCopy + pmatch[g].rm_so);
		      }
		      if(g==2){
		        strcpy(location,sourceCopy + pmatch[g].rm_so);
		      }
		      if(g==3){
		        strcpy(type,sourceCopy + pmatch[g].rm_so);
		      }
		      if(g==4){
		      	strcpy(version,sourceCopy + pmatch[g].rm_so);
		      }
		    }
		  }
		  regfree(&re);

		  //create full directory location and attempt file open
		  strcat(directory,location);
		  file_req = fopen(directory,"r");

		  //Server Verboseness
		  printf("\t%s:%d  >>  %s %s %s/%s\n",inet_ntoa(commaddr.sin_addr),port,request,location,type,version);
		  bzero( outstring, MAX_OUT_LEN);
		  if(strcmp(request,"GET")||strcmp(type,"HTTP")||strcmp(version,"1.0")){
		  	//IF NOT RIGHT VERSION OR HEADER NOT GOOD
		  	strcat(outstring,type);
		  	strcat(outstring,"/");
		  	strcat(outstring,version);
		  	strcat(outstring," 501 Not Implemented\r\n\r\n");
		  	printf("SENT 501 Not Implemented >> %s %s %s/%s\n\n",request,location,type,version);
		  	write(comm_fd, outstring, strlen(outstring)+1);
		  }else if((file_req == NULL)){
		  	// IF FILE CAN'T BE OPENED
		  	strcat(outstring,type);
		  	strcat(outstring,"/");
		  	strcat(outstring,version);
		  	strcat(outstring," 404 Not Found\r\n\r\n");
		  	printf("SENT 404 Not Found >> %s %s %s/%s\n\n",request,location,type,version);
		  	write(comm_fd, outstring, strlen(outstring)+1);
		  }else{ // FILE OPENED AND TIME TO READ
		  	strcat(outstring,type);
		  	strcat(outstring,"/");
		  	strcat(outstring,version);
		  	strcat(outstring," 200 OK\r\n\r\n");
		  	printf("SENT 200 OK >> %s %s %s/%s\n\n",request,location,type,version);

		  	char sym;
		    while((sym = getc(file_req)) != EOF){
		    	strcat(outstring, &sym); //READ CHAR BY CHAR
		    }
		    //WRITE AND CLOSE
		    write(comm_fd, outstring, strlen(outstring)+1);
    		fclose(file_req);
		  }
    	close(comm_fd); //Child done with socket
    	free(directory);
    	exit(0); //KILL CHILD
  	}
  	//PARENT
    close(comm_fd); // Parent does not require open
  }
  return 0;
}
