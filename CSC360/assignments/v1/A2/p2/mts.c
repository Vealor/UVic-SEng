/*###########################################################################*
 *# CSC 360 - Multi-Thread Scheduling Simulation Program		    #*
 *# Created by Jakob Roberts - v00484900				    #*
 *###########################################################################*/

/*****************************************************************************/
#include "mts.h"
/*****************************************************************************/
/* Function quits if malloc doesn't work
 */
void mallocerr(){
	fprintf(stderr,"ERROR allocating memory!\n");
	exit(1);
}
/*****************************************************************************/
struct timespec timeCheck(struct timespec mark){
	/* Used to time the program
	 * Taken Directly from 
	 * http://www.guyrutenberg.com/2007/09/22/profiling-code-using-clock_gettime/
	 */
	struct timespec temp;
	if ((mark.tv_nsec-mytimer.tv_nsec)<0) {
		temp.tv_sec = mark.tv_sec-mytimer.tv_sec-1;
		temp.tv_nsec = 1000000000+mark.tv_nsec-mytimer.tv_nsec;
	} else {
		temp.tv_sec = mark.tv_sec-mytimer.tv_sec;
		temp.tv_nsec = mark.tv_nsec-mytimer.tv_nsec;
	}
	return temp;
}
/*****************************************************************************/
/* Acts as main scheduler for program
 */
int main(int argc, const char* argv[]){
	/*** INPUT CHECK ***/
	if(argc != 3){
		printf("\nUsage: ./mts <input_file> <num_of_trains>\n\n");
		exit(1);
	}	
	/*** END INPUT CHECK ***/
	/*** GET INPUT PARAMETERS ***/
	const char* filename = argv[1];
	int qtytrains  = atoi(argv[2]);
	if(filename==NULL||qtytrains==0){
		printf("\nUsage: ./mts <input_file> <num_of_trains>\n\n");
		exit(1);
	}
	/*** END PARAMETER INPUT ***/
	/*** START INITS ***/
	trackUse=0;
	trainFin=0;
	prevDir=2;
	/* INIT East Queue */
	Equeue = (tQueue*)malloc(sizeof(tQueue));
	if(Equeue==NULL){mallocerr();}
	Equeue->head = NULL;
	Equeue->Length = 0;
	pthread_mutex_init(&Equeue->Mutex,NULL);
	pthread_cond_init(&Equeue->conV,NULL);
	/* INIT West Queue */
	Wqueue = (tQueue*)malloc(sizeof(tQueue));
	if(Wqueue==NULL){mallocerr();}
	Wqueue->head = NULL;
	Wqueue->Length = 0;
	pthread_mutex_init(&Wqueue->Mutex,NULL);
	pthread_cond_init(&Wqueue->conV,NULL);
	/* INIT east Queue */
	equeue = (tQueue*)malloc(sizeof(tQueue));
	if(equeue==NULL){mallocerr();}
	equeue->head = NULL;
	equeue->Length = 0;
	pthread_mutex_init(&equeue->Mutex,NULL);
	pthread_cond_init(&equeue->conV,NULL);
	/* INIT west Queue */
	wqueue = (tQueue*)malloc(sizeof(tQueue));
	if(wqueue==NULL){mallocerr();}	
	wqueue->head = NULL;
	wqueue->Length = 0;
	pthread_mutex_init(&wqueue->Mutex,NULL);
	pthread_cond_init(&wqueue->conV,NULL);
	/* INIT MAIN TRACK */
	pthread_mutex_init(&MT_Mutex,NULL);
	pthread_cond_init(&MT_State,NULL);	
	/*** INITS COMPLETE ***/
	/*** BEGIN PARSE & LOADING ***/
	
	parseFile(filename); // get inputs and put onto a loadStack
	int i=0; // counter for following For Loop
	for(Current=loadStack;i<qtytrains;Current++){ //creates all threads from loadStack
		pthread_mutex_init(&Current->trainMutex,NULL);
		pthread_cond_init (&Current->activity,NULL);
		pthread_create(&Current->tid,NULL,runTrain,(void*)Current);
		i++;
	}
	clock_gettime(CLOCK_MONOTONIC, &mytimer); // start program timer
	
	/* MAIN SCHEDULER */
	while(trainFin < qtytrains){
		if(trackUse){ // if main track in use, wait
			pthread_cond_wait   (&MT_State,&MT_Mutex);
			pthread_mutex_unlock(&MT_Mutex);
			if(trainFin==qtytrains){break;}
		}
		// once main track is free, grab next applicable train from a queue
		if(Wqueue->Length || Equeue->Length){
			//high priority
			if(Wqueue->Length && Equeue->Length){
				if(prevDir==2){ //west
					pthread_mutex_lock(&MT_Mutex);
					pthread_cond_signal(&Wqueue->active->activity);
				}else{ //east
					pthread_mutex_lock(&MT_Mutex);
					pthread_cond_signal(&Equeue->active->activity);
				}
			}else if(Wqueue->Length){ //west
				pthread_mutex_lock(&MT_Mutex);
				pthread_cond_signal(&Wqueue->active->activity);
			}else if(Equeue->Length){ //east
				pthread_mutex_lock(&MT_Mutex);
				pthread_cond_signal(&Equeue->active->activity);
			}
		}else if(wqueue->Length || equeue->Length){
			//low priority
			if(wqueue->Length && equeue->Length){
				if(prevDir==2){ //west
					pthread_mutex_lock(&MT_Mutex);
					pthread_cond_signal(&wqueue->active->activity);
				}else{ //east
					pthread_mutex_lock(&MT_Mutex);
					pthread_cond_signal(&equeue->active->activity);
				}
			}else if(wqueue->Length){ //west
				pthread_mutex_lock(&MT_Mutex);
				pthread_cond_signal(&wqueue->active->activity);
			}else if(equeue->Length){ //east
				pthread_mutex_lock(&MT_Mutex);
				pthread_cond_signal(&equeue->active->activity);
			}	
		}
		pthread_mutex_unlock(&MT_Mutex); //main track free
	}
	freeMem(); //clear used memory
	pthread_exit(NULL); //close main thread
}
/*****************************************************************************/
/* this funtion is the function to be used for each train thread created
 */
void* runTrain(void* inTrain){
	train* aTrain = (train*)inTrain;
	usleep(aTrain->loadTime*100000);
	char* traindir;
	struct timespec mark;
	if(aTrain->dir==1){
		traindir="West";
	}else if(aTrain->dir==2){
		traindir="East";
	}
	/* PUT ON QUEUE */
	if      (aTrain->dir==1 && aTrain->priority==1){
		enqueue(aTrain,Wqueue);
	}else if(aTrain->dir==2 && aTrain->priority==1){
		enqueue(aTrain,Equeue);
	}else if(aTrain->dir==1 && aTrain->priority==0){
		enqueue(aTrain,wqueue);
	}else if(aTrain->dir==2 && aTrain->priority==0){	
		enqueue(aTrain,equeue);
	}
	/* GET TIME */
	clock_gettime(CLOCK_MONOTONIC, &mark);
	mark = timeCheck(mark);
	printf("%02d:%02d:%1ld Train %2d is ready to go %4s\n",((int)mark.tv_sec/60)%60,(int)mark.tv_sec%60,mark.tv_nsec/100000000,aTrain->trainNum,traindir);
	/* LOCK AND WAIT */
	pthread_mutex_lock(&aTrain->trainMutex);
	pthread_cond_wait(&aTrain->activity,&aTrain->trainMutex);
	/* THIS TRAIN TURN */
	trackUse = 1;
	if      (aTrain->dir==1 && aTrain->priority==1){
		dequeue(Wqueue);
	}else if(aTrain->dir==2 && aTrain->priority==1){
		dequeue(Equeue);
	}else if(aTrain->dir==1 && aTrain->priority==0){
		dequeue(wqueue);
	}else if(aTrain->dir==2 && aTrain->priority==0){	
		dequeue(equeue);
	}
	prevDir=aTrain->dir;
	/* GET TIME */
	clock_gettime(CLOCK_MONOTONIC, &mark);
	mark = timeCheck(mark);	
	printf("%02d:%02d:%1ld Train %2d is ON the main track going %4s\n",((int)mark.tv_sec/60)%60,(int)mark.tv_sec%60,mark.tv_nsec/100000000,aTrain->trainNum,traindir);
	int Xtime=aTrain->trackTime*100000;
	usleep(Xtime); // time on main track
	/* GET TIME */
	clock_gettime(CLOCK_MONOTONIC, &mark);
	mark = timeCheck(mark);
	printf("%02d:%02d:%1ld Train %2d is OFF the main track after going %4s\n",((int)mark.tv_sec/60)%60,(int)mark.tv_sec%60,mark.tv_nsec/100000000,aTrain->trainNum,traindir);
	trackUse=0;
	trainFin++;
	usleep(100);
	pthread_cond_signal(&MT_State);
	pthread_mutex_unlock(&MT_Mutex);
	pthread_exit(NULL);
}
/*****************************************************************************/
/* add a train that is loaded to a designated queue
 */
void enqueue(train* t,tQueue* theQ){
	/* CHECK IF QUEUE IN USE */
	train* temp;
	if(theQ->Use){
		pthread_cond_wait (&theQ->conV,&theQ->Mutex);
		theQ->Use=1;
	}else{
		pthread_mutex_lock(&theQ->Mutex);
		theQ->Use=1;
	}
	/* ADD TO QUEUE */
	if(theQ->head==NULL){
		theQ->head=t;
		theQ->tail=t;
		theQ->Length++;
		theQ->active=t;
	}else{
		temp = theQ->head;
		while(temp->next!=NULL && t->loadTime > temp->next->loadTime){
			temp = temp->next;
		}
		while(temp->next!=NULL && temp->loadTime == temp->next->loadTime && t->trainNum > temp->next->trainNum){
			temp = temp->next;
		}
		if(temp->loadTime == t->loadTime && t->trainNum < temp->trainNum){
			t->next = temp;
			theQ->head = t;
			theQ->active = t;
		}else if(temp->next==NULL){
			theQ->tail->next=t;
			theQ->tail=t;
			t->next=NULL;
		}else{
			t->next=temp->next;
			temp->next = t;
		}
		theQ->Length++;
	}
	/* UNLOCK AND SIGNAL */
	theQ->Use=0;
	temp = theQ->head;
	pthread_cond_signal(&theQ->conV);
	pthread_mutex_unlock(&theQ->Mutex);
	return;
}
/*****************************************************************************/
/* takes a train off the designated queue
 */
void dequeue(tQueue* theQ){
	if(theQ->Use){
		pthread_cond_wait (&theQ->conV,&theQ->Mutex);
		theQ->Use=1;
	}else{
		pthread_mutex_lock(&theQ->Mutex);
		theQ->Use=1;
	}
	if(theQ->Length==0){
		printf("ERROR!: QUEUE EMPTY!");
		pthread_mutex_unlock(&theQ->Mutex);
		exit(1);
	}else{
		if(theQ->Length==1){
			theQ->Length--;
			theQ->active=theQ->head;
			theQ->head=NULL;
			theQ->tail=NULL;
		}else{
			theQ->active=theQ->head;
			theQ->head=theQ->head->next;
			theQ->Length--;
			theQ->active=theQ->head;
		}
		theQ->Use=0;
		pthread_cond_signal(&theQ->conV);
		pthread_mutex_unlock(&theQ->Mutex);
	}
	return;
}
/*****************************************************************************/
/* Read File Input and build loadStack to then create threads
 */
void parseFile(const char* filename){
	FILE* input=fopen(filename,"r");
	char* theline=(char*)malloc(sizeof(char)*MAXLINE);
	if(theline==NULL){mallocerr();}	
	int tCount = 0;
	Current = loadStack;
	while(fgets(theline,MAXLINE,input)!=NULL){
		Current->trainNum=tCount;
		char dir = theline[0];
		if      (dir=='W'){
			Current->dir=1;
			Current->priority=1;
		}else if(dir=='E'){
			Current->dir=2;
			Current->priority=1;
		}else if(dir=='w'){
			Current->dir=1;
			Current->priority=0;
		}else if(dir=='e'){
			Current->dir=2;
			Current->priority=0;
		}
		/* increment input line */
		theline = (theline+2);
		Current->loadTime  = atoi(strtok(theline,","));
		Current->trackTime = atoi(strtok(NULL,"\0"));
		Current++;
		tCount++;
	}
	fclose(input); // close open file
	return;
}
/*****************************************************************************/
/* this function frees used memory
 */
void freeMem(){
	pthread_mutex_destroy(&MT_Mutex);
	pthread_cond_destroy(&MT_State);
	free(Equeue);
	free(Wqueue);
	free(equeue);
	free(wqueue);
	return;
}
/*****************************************************************************/
