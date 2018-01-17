/*****************************************************************************/
#include <stdio.h>
#include <pthread.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include <errno.h>
#include <time.h>

#define MAXLINE 100
#define MAXLOAD 100
/*****************************************************************************/
FILE *inFile;

/* TRAIN STRUCT */
typedef struct trainNode{
	pthread_mutex_t trainMutex;
	pthread_cond_t	activity;
	pthread_t tid;
	int dir;/* 1= West, 2= East */
	int trainNum;
	int priority;
	int loadTime;
	int trackTime;
	struct trainNode *next;
}train;

/* QUEUE STRUCT */
typedef struct Queue{
	train* head;
	train* tail;
	train* active;
	pthread_mutex_t Mutex;
	pthread_cond_t conV;
	int Length;
	int Use;//boolean
}tQueue;

/* Global Vars */
struct timespec mytimer;
tQueue* Equeue;
tQueue* Wqueue;
tQueue* equeue;
tQueue* wqueue;

pthread_mutex_t MT_Mutex;
pthread_cond_t MT_State;

int trackUse; //boolean
train loadStack[MAXLOAD];
train *Current;
int prevDir;
int trainFin;

/*** INIT PROTOTYPES ***/
// Did not include helpers:
//	mallocerr()
//	timeCheck()
void parseFile(const char* filename);
void* runTrain(void* inTrain);
void enqueue(train* t, tQueue* theQ);
void dequeue(tQueue* theQ);
void freeMem();
/*****************************************************************************/
