#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <readline/readline.h>
#include <readline/history.h>
#include <regex.h>    
#include <sys/wait.h>


#define MAXLINE 100

typedef struct job{
    char * path;
    char * name;
    int  status; // 0 = Dead , 1 = running 
    int PID;
    struct job * nextNode;

}job;
// My linkedlist for bg jobs
job *Jobs = NULL;
job *Head = NULL;
// Jobs is a linkedlist
void addNode(char *name, int status , int pid ){
  int size = 100;
  char  buffer [size];
  getcwd( buffer, size );

  if (Jobs == NULL){
    //printf("trying this out\n");
      Jobs = (job *)malloc( MAXLINE * sizeof(job));
      if (Jobs == NULL){//malloc fail 
        exit(0);
        } 
      Jobs->name = (char * )malloc( MAXLINE * sizeof(name));
      Jobs->path = (char * )malloc( MAXLINE * sizeof(buffer));
      strcpy(Jobs->path, buffer);
      strcpy(Jobs->name, name);
      Jobs->status = status;
      Jobs->PID = pid;
      Jobs->nextNode = NULL;
      Head = Jobs;
      
    }else{
    job *newnode = (job *)malloc( MAXLINE * sizeof(job)); 
    if (newnode == NULL){//malloc fail 
        exit(0);     
     } 
    //printf("down here\n");
      newnode->name = (char * )malloc( MAXLINE * sizeof(name));
      newnode->path = (char * )malloc( MAXLINE * sizeof(buffer));
      strcpy(newnode->path, buffer);
      strcpy(newnode->name, name);
      newnode->status = status;
      newnode->PID = pid;
      Head->nextNode = newnode;
      Head = newnode;
    }
}

void listbg(){
   int count,size = 1000;
   char  buffer [size];
   if (Jobs != NULL){
       job *templist = Jobs;
       count = 1;
      while(templist->nextNode != NULL){
        strcpy(buffer , templist->path);
        strcat(buffer , "/");
        strcat(buffer , templist->name); 
        printf("%d:  %s\n",templist->PID , buffer);
        templist = templist->nextNode;
        count++; 
      }
        strcpy(buffer , templist->path);
        strcat(buffer , "/");
        strcat(buffer , templist->name); 
        printf("%d:  %s\n",templist->PID , buffer);
        printf("Total Background jobs: %d\n", count);

    }else{
      printf("No back ground jobs\n");
    }

}

void killPid(pid_t pid){
    job *finder = Head;
    job *prev = Head;
    int count = 0;
    int i = 0;
    if (finder->PID == pid){
      Head = Head->nextNode;
      free(finder);
    }
    while(finder->PID != pid){
      finder = finder->nextNode;
      count++;
    }
    if(finder->PID == pid){ 
      for (i; i < count-1; i++){
          prev = prev->nextNode;
      }
      if (finder->nextNode == NULL)
        { printf("\n BG Proccess: %s with PID: %d has finnished",finder->name , finder->PID);
          prev->nextNode = NULL;

        }else{ 
        printf("\n BG Proccess: %s with PID: %d has finnished",finder->name , finder->PID ); 
        prev->nextNode = prev->nextNode->nextNode; 
        finder->nextNode = NULL; 
      }
  }
}


void run( char *args[] ){
      pid_t pid ;
      // arg1 = (const char *)arg1;
      //args = (char *const)args;
       pid = fork();
        if( pid == 0){
            //*childprintf("child\n");
            if (execvp(args[0] , args ) < 0) {     
               printf("\n ERROR: exec failed\n");
               exit(1);
            }
        }else{ 
        //In Parent process
        wait(NULL);
        }
}

//*(const char *) arg1 , (char *const)arg1*/
void runBG( char *args[] , int arrayCount ){
      pid_t pid ,endPid;
      int pidStatus = 0;          
      // arg1 = (const char *)arg1;
      //args = (char *const)args;
      char *bgargs [1000];
      int i = 0;
      while(i < arrayCount){
           bgargs[i] = (char * ) malloc ( MAXLINE * sizeof(char));
           bgargs[i] = args[i + 1];
           i++;
         }   
        pid = fork();
        if (pid >= 0 ) //fork sucess
        {
            pidStatus = 1;
           addNode(bgargs[0] , pidStatus , pid);

          if( pid == 0){
              //in child
              
              if (execvp(bgargs[0] , bgargs ) < 0) {     
                 printf("\n ERROR: exec failed\n");
                 exit(1);
              }else{ 
                //printf("%d\n", pid);
                killPid(pid); 
                exit(0);
              }
          }else if(pid > 0){
              
             endPid = waitpid(pid,&pidStatus, WNOHANG); // WNOHANG
            // printf("%d\n", endPid);
              if (endPid == -1) { perror("waitpid"); exit(EXIT_FAILURE); }
                  if (WIFEXITED(pidStatus)) {
                   printf("Normal Exited, pidStatus=%d\n", WEXITSTATUS(pidStatus));
                  } 
                //printf ("Parent Starts to Sleep.\n");
                sleep(2);
                //printf("Press any key to exit:\n");
                getchar();
              } 

       }else{
       //killPid(pid); 
        //In Parent process
           do {
          endPid = waitpid(pid, &pidStatus, WUNTRACED | WCONTINUED);
              if (endPid == -1) {
                perror("waitpid");
                exit(EXIT_FAILURE);
              }
          }while (!WIFEXITED(pidStatus) && !WIFSIGNALED(pidStatus));
            exit(EXIT_SUCCESS);
      }
}

//This function reads in commands then sends them to Run.
void prompt(){
    int status = 1;
    char *args [1000];
    int size = 256;
    char *argReader;
      
    while(status != 0){
      char *Dir = malloc(256);
     getcwd( Dir, size );
      char header [150] = "RSI:";
      strcat(header , Dir);
      strcat(header , "> ");
      strcpy(Dir , header);
    int i = 0;
    char *reply = readline(Dir);
   
       argReader = strtok(reply," ");
      while(argReader != NULL){
          
          args[i] = (char * ) malloc ( MAXLINE * sizeof(char));
          strcpy(args[i],argReader);
          //printf("%s\n" , args[i]);
          argReader = strtok(NULL, " ");

           //printf( "%s\n",  args[i]);
           i++;
          } 
        if (i == 0) // if no args
          {
            continue;
          }  

       args[i] = (char * ) malloc ( MAXLINE * sizeof(char));
       args[i] = NULL; // adding Null to end of args
       
       
      //printf("woot\n");
       
        if (strcmp(args[0], "exit") == 0){
               
               status = 0;
               break;

        }else if (strcmp(args[0],"cd") == 0){
                // change dir             
                
                if ( strcmp(args[1], "~") == 0){// if ~
                  strcpy(args[1] ,"/home");
                }
                if (chdir(args[1])==0){  // Changing dir
                    free(Dir);
                    continue;
                }
                
                else{
                  printf("Could not change directory\n");
                  
                }
        
        }else if (strcmp(args[0],"bglist") == 0)
        {   listbg();
          
        }else if (strcmp(args[0],"bg") == 0)
            // Running in the back ground
        { runBG(args , i);       
        }else if(strcmp(args[0],"cd") != 0){
            // Normal execute
            run(args);
        }       
        

    }
    
   // free(reply);
    free(Jobs);
    free(Head);
   return;
}
int main(){
prompt();
return 1;

}
