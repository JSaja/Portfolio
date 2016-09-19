/*
 ============================================================================
 Name        : twitterTrend.c 
 Author      : Harshada Chavan 
 Description : PA3 solution 
 ============================================================================
 */

#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<pthread.h>

pthread_mutex_t queue_mutex;

struct twitterDBEntry {
	char cityName[15];
	char keywords[200];
};

struct twitterDBEntry twitterDB[100];
int twitterDBCounter = 0;
char taskQueue[10][20];
int activeTaskCounter = 0;
int maxTasks = 0;
int currentTaskCounter = 0;
FILE *fp_queue = NULL;
int stop = 0;

void addToTwitterDB(struct twitterDBEntry entry)
{
	int i = 0;

	strncpy(twitterDB[twitterDBCounter].cityName, entry.cityName, strlen(entry.cityName));
	strncpy(twitterDB[twitterDBCounter++].keywords, entry.keywords, strlen(entry.keywords));
}

void lookupTwitterDB(char cityName[], char keywords[])
{
	int i = 0;
        for(i = 0; i < twitterDBCounter; i++) {
		if(!strncmp(twitterDB[i].cityName, cityName, strlen(cityName))) {
			strncpy(keywords, twitterDB[i].keywords,strlen(twitterDB[i].keywords));
			break;
		}
	}
}

void printTwitterDB()
{
	int i = 0;
	for(i = 0; i < twitterDBCounter; i++) {
		printf("CityName: %s, TrendingKeyWords: %s\n",twitterDB[i].cityName, twitterDB[i].keywords);
	}
}

void populateTaskQueue()
{
	char buffer[20];
        
        while ((activeTaskCounter < maxTasks) && ( fgets (buffer, sizeof(buffer), fp_queue)!=NULL)) {
                buffer[strlen(buffer)-1] = '\0';
                strncpy(taskQueue[activeTaskCounter++],buffer,strlen(buffer));
        }
	if(activeTaskCounter < maxTasks) {
		stop = 1;
	}
	else {
		printf("\nTask queue full.. waiting\n");
		stop = 0;
	}
}

void printTaskQueue()
{
	int i = 0;
	printf("\nActive Tasks: \n");
	for(i=0;i<activeTaskCounter;i++) {
		printf("%s\n", taskQueue[i]);
	}
}

/* 
* Given a fname, 
* 1. Open the file to get the city name
* 2. Look-up the city name in twitterDB and get corresponding trending keywords
* 3. Write keywords to fname_result
*/
 
void processTask(char *fname)
{
	char buffer[15];
	char keywords[200]="\0";
	char resultFileName[23]="\0";
	char toFile[200]="\0";
	FILE *fpWrite=NULL;

	FILE *fp = fopen(fname, "r");
        if(fp == NULL)
        {
                printf("\nError opening file: %s", fname);
                exit(1);
        }

        if (fgets (buffer, sizeof(buffer), fp)!=NULL) {
                buffer[strlen(buffer)-1] = '\0';
		lookupTwitterDB(buffer, keywords);
		keywords[strlen(keywords)]='\0';
		sprintf(toFile, "%s : %s\0", buffer, keywords);
		
		strncpy(resultFileName,fname,strlen(fname));
		strcat(resultFileName,".result");
		fpWrite = fopen(resultFileName, "w");
		if(fpWrite == NULL)
		{
			printf("\nError opening file: %s", resultFileName);
			exit(1);
		}
		fputs(toFile, fpWrite);
		fclose(fpWrite);
		fclose(fp);		
	}
	else {
		fclose(fp);
		printf("\nError reading from file: %s", fname);
		exit(1);
	}	
}

void *run(void *arg)
{	
	int counter = 0;
	int tid = *(int *)arg; 
	char fname[15];
	while(1) {
		pthread_mutex_lock(&queue_mutex);
		if (activeTaskCounter > currentTaskCounter) {
			counter = currentTaskCounter++;
			strcpy(fname,taskQueue[counter]);
			pthread_mutex_unlock(&queue_mutex);

			printf ("\nThread %d executing %s\n", tid, fname); 
			processTask(fname);
			printf ("Thread %d finished %s\n", tid, fname);
		
			pthread_mutex_lock(&queue_mutex);
			if(currentTaskCounter == activeTaskCounter) {
				activeTaskCounter = 0;
				currentTaskCounter = 0;
				if (fp_queue) {
					populateTaskQueue();
				}
			}
			pthread_mutex_unlock(&queue_mutex);
		}
		else if (stop == 1) {
			pthread_mutex_unlock(&queue_mutex);
			break;
		}
		pthread_mutex_unlock(&queue_mutex);
	}
	pthread_exit(NULL);
	return 0;
}	 	

int main(int argc, char *argv[])
{

	// read from Twitter trend, populate the TwitterDB
	FILE *fp = NULL;
	char buffer[120];
	struct twitterDBEntry entry;
	char *token;
	int i = 0;
	int numThreads = atoi(argv[2]);
	pthread_t tid[numThreads];
	int tids[numThreads];
	maxTasks = numThreads;

	// ./twitterTrend client_file num_threads
	fp = fopen("TwitterDB.txt", "r");
	if(fp == NULL)
	{
		printf("\nError opening file: TwitterDB.txt");
		exit(1);
	}

	while (fgets (buffer, sizeof(buffer), fp)!=NULL) { 
		token = strtok(buffer, ",");
		strncpy(entry.cityName, token, strlen(token));
		entry.cityName[strlen(token)]='\0';
		token = strtok(NULL, " ");
		strncpy(entry.keywords, token, strlen(token));
		entry.keywords[strlen(token)-1]='\0';
		addToTwitterDB(entry);
	}
	fclose(fp);

	// Spawn num_threads threads
	for(i = 0; i < numThreads; i++)
	{
		tids[i]=i+1;
		pthread_create(&tid[i], NULL, run, (void *) &tids[i]);
	}

	// open client file and populate task queue 
	fp_queue = fopen(argv[1],"r");
	if(fp_queue == NULL)
        {
                printf("\nError opening file: %s", argv[1]);
                exit(1);
        }

	pthread_mutex_lock(&queue_mutex);
	populateTaskQueue();
	pthread_mutex_unlock(&queue_mutex);

	// wait for threads before terminating
	for (i = 0; i < numThreads; i++) {
    		pthread_join(tid[i], NULL);
	}

	fclose(fp_queue);
	fp_queue = NULL;
	return 0;
}
