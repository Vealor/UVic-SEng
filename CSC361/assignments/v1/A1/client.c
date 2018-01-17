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

#define MAX_STR_LEN 120
#define MAX_RES_LEN 65535

/* --------- Main() routine ------------
 * three main task will be excuted:
 *  accept the input URI and parse it into fragments for further operation
 *  open socket connection with specified sockid ID
 *  use the socket id to connect sopecified server
 */
main(int argc, char **argv){
    char uri[MAX_STR_LEN];
    char hostname[MAX_STR_LEN];
    int port = 80;
    char identifier[MAX_STR_LEN];
    int sockfd;

    if(argc > 1){
      strncpy(uri,argv[1],MAX_STR_LEN-1);
    }else{
      printf("Open URI:  ");
      scanf("%s", uri);
    }
    if(strlen(uri)<2){
      printf("Error: Input requires a longer URI.");
      exit(1);
    }
    parse_URI(uri, hostname, &port, identifier);  //parse
    sockfd = open_connection(hostname, port);     //connect
    perform_http(sockfd, identifier);             //get data
    return 0;
}

/*------ Parse an "uri" into "hostname" and resource "identifier" --------*/
parse_URI(char *uri, char *hostname, int *port, char *identifier){

  strcpy(identifier,"/");

  const char* regtext = "^(http)?:?/?/?([a-zA-Z0-9]+[.]?[a-zA-Z0-9]*[.]?[a-zA-Z0-9]*[.]?[a-zA-Z0-9]*[.]?[a-zA-Z0-9]*):?([0-9]{1,5})?(.*)";
  regex_t re;
  int status;
  regmatch_t pmatch[5];

  if (regcomp(&re, regtext, REG_EXTENDED)) {
    printf("error with regex!!!!!!!!\n");
    exit(1);
  }

  regexec(&re, uri, 5, pmatch, 0);
  unsigned int g = 0;
  for (g = 0; g < 5; g++){
    char sourceCopy[strlen(uri) + 1];
    strcpy(sourceCopy, uri);
    sourceCopy[pmatch[g].rm_eo] = 0;
    if (pmatch[g].rm_so != (size_t)-1){
      if(g==2){
        strcpy(hostname, sourceCopy + pmatch[g].rm_so);
      }
      if(g==3){
        *port = atoi(sourceCopy + pmatch[g].rm_so);
      }
      if(g==4){
        if(strlen(sourceCopy + pmatch[g].rm_so)>1){
          strcpy(identifier,sourceCopy + pmatch[g].rm_so);
        }
      }
    }
  }
  regfree(&re);
  return 0;
}

/*---------------------------------------------------------------------------*
 * open_conn() routine. It connects to a remote server on a specified port.
 *---------------------------------------------------------------------------*/
int open_connection(char *hostname, int port){
  int sockfd;
  sockfd=socket(AF_INET,SOCK_STREAM,0);
  struct sockaddr_in servaddr;
  bzero(&servaddr,sizeof servaddr);
  servaddr.sin_family=AF_INET;
  servaddr.sin_port=htons(port);
  // Get host by name
  struct hostent *he;
  struct in_addr **addr_list;
  int i;
  char ip[100];
  if ( (he = gethostbyname( hostname ) ) == NULL){
      // get the host info
      herror("gethostbyname");
      exit(1);
  }
  addr_list = (struct in_addr **) he->h_addr_list;
  for(i = 0; addr_list[i] != NULL; i++){
      //Return the first one;
      strcpy(ip , inet_ntoa(*addr_list[i]) );
      break;
  }
  // finish connection
  inet_pton(AF_INET,ip,&servaddr.sin_addr);
  if(connect(sockfd,(struct sockaddr *)&servaddr,sizeof(servaddr)) == -1){
    printf("Error connecting!");
    exit(1);
  }
  return sockfd;
}

/*------------------------------------*
* connect to a HTTP server using hostname and port,
* and get the resource specified by identifier
*--------------------------------------*/
perform_http(int sockid, char *identifier){
  char recvline[MAX_RES_LEN];
  char sendline[MAX_STR_LEN] = "GET ";
  strcat(sendline, identifier);
  strcat(sendline," HTTP/1.0\r\n\r\n\0");
  write(sockid,sendline,strlen(sendline)+1);
  read(sockid,recvline,MAX_RES_LEN-1);
  printf("%s\n",recvline);
  close(sockid);
  return 0;
}
