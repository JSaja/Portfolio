#include <stdio.h>
#include <sys/types.h> 
#include <sys/socket.h>
#include <netinet/in.h>
#include <string.h>
#include <strings.h>
#include <stdlib.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <pthread.h>
#include <semaphore.h>
//User Defined
#include "queue.h"

#define LINEMAX 100
#define FILEMAX 5000

void* process(void* arg);

void error(char *msg) {
    perror(msg);
    exit(1);
}
pthread_mutex_t mut = PTHREAD_MUTEX_INITIALIZER;
sem_t sem;

struct threadNum_struct {
    int threadNum;
    char* twitterDB;
    char* cliaddr;
    int portno;
}args;

struct queue_struct {
	queue* Q;
} queueStruct;

struct msg_struct {
	int msgID;
	int payloadLen;
	char* payload;
}msg;

void checkValidity(int expID, int expLen, char* expLoad) {
		if (msg.msgID != expID) {
			printf("server received bad msgID:(106,0,)\n");
			exit(1);
		}
		if (msg.payloadLen != expLen) {
			printf("server received bad payloadLen:(106,0,)\n");
			exit(1);
		}
		if (strcmp(msg.payload, expLoad) < 0) {
			printf("server received bad payload:(106,0,)\n");
			exit(1);
		}
}	

void main(int argc, char *argv[]) {
 	sem_init(&sem,0,0);
	//Start by embedding twitterDB.txt into char* so we only need to open once
	FILE* dbFile;
	char* twitterDB = malloc(FILEMAX);
    dbFile = fopen("TwitterDB.txt", "r");
    if (dbFile == NULL) {
        printf("The file : '%s' doesn't exist in the working directory\n", "TwitterDB.txt");
        exit(1);
    }
    else {
		size_t itemsRead = fread(twitterDB, sizeof(char), FILEMAX, dbFile);
	    if (itemsRead < LINEMAX) {
			printf("Error reading from file\n");
			exit(1);
		}
	  	fclose(dbFile);
	} 
	
        
	
    int sockfd, newsockfd, portno, clilen;
    int numWrit = 0;
    char buffer[256];
     
    //Need to declare before binding
    struct sockaddr_in serv_addr, cli_addr;
     
    int n;
     
    if (argc != 3) {
        printf ("Usage: ./twitterTrend port_number num_threads\n");
        exit(1);
    }      
    
    //Creating a TCP socket.
    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    if (sockfd < 0) {
        error("ERROR opening socket");
	}
     
    //Need to clear sockaddr_in struct before bind
    bzero((char *) &serv_addr, sizeof(serv_addr));
     
    //Assign portNo based on cmd line input
    portno = atoi(argv[1]);

    //More stuff they didn't teach but is necessary
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = INADDR_ANY;
    serv_addr.sin_port = htons(portno);
     
    //Finally, we can attempt to bind
    if ( bind(sockfd, (struct sockaddr *) &serv_addr, sizeof(serv_addr)) < 0) {
         error("ERROR on binding");
	}
	 
	//Step 1: Listen for incoming connection at the specified port.
	int numThreads = atoi(argv[2]);
    listen(sockfd,numThreads);
    printf("server listens\n");

    //Create numThreads threads and an array of structs to deal with each thread
    
    pthread_t threads[numThreads];
    struct threadNum_struct currentNum[numThreads];

    //Initialize Queue
  	queue* q = malloc(sizeof(queue));
    init(q);
    queueStruct.Q = q; 
    
    
    clilen = sizeof(cli_addr);
    //Launch all threads
    int i = 0;
    for (i = 0; i < numThreads; i++) {
		currentNum[i].threadNum = i + 1;
		currentNum[i].portno = portno;
		currentNum[i].cliaddr = inet_ntoa(cli_addr.sin_addr);
		currentNum[i].twitterDB = twitterDB;
		int error = pthread_create(&threads[i], NULL, process, &currentNum[i]);
		if (error != 0) {
			printf("Unable to create thread #%d\n", i);
			exit(1);
		}
	}
   
   int clientsWaiting = 0;
   //indefinitely accept new connections
   while(newsockfd = accept(sockfd, (struct sockaddr *) &cli_addr, &clilen)) {		
		 if (newsockfd < 0) {
                 error("ERROR on accept");
		 }
		 printf("server accepts connection\n");
		 
		 clientsWaiting++;			
		 push(q, newsockfd);	
		 sem_post(&sem);
    }		
}


void* process(void* arg) {
	struct threadNum_struct* this = (struct threadNum_struct*) arg;
	sem_wait(&sem); // Block every thread initially

	//once threads get posted, pick client to handle
	while (1) {
		pthread_mutex_lock(&mut);
		//step 4 : pick to handle the head of queue. Pop atomically
		int theHead = pop(queueStruct.Q);
		if (theHead == -1) {
			//go back to top and keep checking if we can pop
			pthread_mutex_unlock(&mut);
			sem_wait(&sem);
			continue;
		}
		printf("Thread %d is handling client %s,%d\n", this->threadNum, this->cliaddr, this->portno);
		pthread_mutex_unlock(&mut);
		
		msg.msgID = 100;
		msg.payloadLen = 0;
		msg.payload = "";
		//make buffer to hold contents of messages
		char buf[100];
		char newBuf[100];
		sprintf(buf, "%d %d %s" , msg.msgID, msg.payloadLen, msg.payload);
		//step 5 : send handshake message to client
		write(theHead, buf, sizeof(buf));
		printf("server sends handshaking:(%d,%d,%s)\n", msg.msgID, msg.payloadLen, msg.payload);
		
		// wait for client response : (101,0,) . Read, parse,  and check for validity
		read(theHead, buf, sizeof(buf));
		sscanf(buf, "%d %d %s", &msg.msgID, &msg.payloadLen, &msg.payload);
		checkValidity(101,0,""); // if function doesn't fail, we're good

		//wait for city requests
		while (1) {
			bzero(newBuf, 100);
			read(theHead, buf, sizeof(buf));
			sscanf(buf, "%d %d %s", &msg.msgID, &msg.payloadLen, &msg.payload);
			if ((msg.msgID != 102) && (msg.msgID != 104)) {
				printf("server received bad msgID:(106,0,)\n");
				exit(1);
			}
			if (msg.msgID == 104) {
				break;
			}
	
			else if (msg.msgID == 102) {
				//Attempt to find substring which is the payload
				char* ret;
				if (strstr(this->twitterDB, &msg.payload) == NULL) {
					msg.msgID = 103;
					msg.payloadLen = 2;
					msg.payload = "NA";	
					sprintf(buf, "%d %d %s" , msg.msgID, msg.payloadLen, msg.payload); 
					write(theHead, buf, sizeof(buf));
					printf("server sends twitterTrend response:(%d,%d,\"%s\")\n", msg.msgID, msg.payloadLen, msg.payload);				

					//write end of req
				    msg.msgID = 105;
					msg.payloadLen = 0;
					msg.payload = "";
					sprintf(buf, "%d %d %s" , msg.msgID, msg.payloadLen, msg.payload);
					write(theHead, buf, sizeof(buf));
					printf("server sends end of response:(%d,%d,%s)\n", msg.msgID, msg.payloadLen, msg.payload);
				}
			    else {
				    ret = strstr(this->twitterDB, &msg.payload);

					int i= 0;
					int j= 0;

					while (ret[i] != '\n' || ret[i] == '\0') {
						if (i > msg.payloadLen) {
							newBuf[j] = ret[i];
							j++;
						}
						i++;
					}
										
					msg.msgID = 103;
					msg.payload = &newBuf;
					msg.payloadLen = strlen(msg.payload);
				    
				    //Send twitterTrend response to client
					sprintf(buf, "%d %d %s" , msg.msgID, msg.payloadLen, msg.payload);
					write(theHead, buf, sizeof(buf));
					printf("server sends twitterTrend response:(%d,%d,\"%s\")\n", msg.msgID, msg.payloadLen, msg.payload);

				    //write end of req
				    msg.msgID = 105;
					msg.payloadLen = 0;
					msg.payload = "";
					sprintf(buf, "%d %d %s" , msg.msgID, msg.payloadLen, msg.payload);
					write(theHead, buf, sizeof(buf));
					printf("server sends end of response:(%d,%d,%s)\n", msg.msgID, msg.payloadLen, msg.payload);
			}
			}
		 }
		
    close(theHead);
	printf("server closes the connection\n");
    printf("Thread %d finished handling client %s,%d\n", this->threadNum, this->cliaddr, this->portno);
	}
	
 	
      
     // close(sockfd);
}
