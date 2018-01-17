/* Jakob Roberts
 * CSC360
 * Assignment 1
 * 
 * Header file for RSI.c
 */
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
#include <time.h>

char* doMalloc(int size);
char* printPrompt();
int parser(char* input, char** tokline, int insize);
void bgexecute(char** linetok, int linelen,char* input);
void execute(char** linetok, int linelen,char* input);
void chgDir(char** line, int items);
void addProcess(char* procname, pid_t PID);
void removeProcess(pid_t PID);
void bglist();
void handler();

