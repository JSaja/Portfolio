#include <stdio.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>
#include <arpa/inet.h>
#include <sys/socket.h>
#include <netdb.h>
#include <unistd.h>
#include "dbStringProcess.h"

#define LINEMAX 100

struct msgStruct {
		int msgID;
		int payloadLen;
		char *payload;
	} msg;

void main(int argc, char** argv)
{
	int clientSocket;
	int portNumber = atoi(argv[2]);
	char message[100];
	char *server_reply[2000];
	char *ip = argv[1];
	char *city[15];
	char *resultFile = malloc(LINEMAX);
	struct sockaddr_in server;

	//Check that the program is being run correctly
	if (argc != 4) {
		printf("Usage: ./twitterTrend_client server_host_name server_port_number file_path\n");
		exit(1);
	}

	//Open clientX.in
	FILE *clientFile;
	clientFile = fopen(argv[3], "r");
	if (clientFile == NULL) {
		printf("The file : %s doesn't exist in working directory\n", argv[3]);
		exit(1);
	}

	//Create socket
	if ((clientSocket = socket(AF_INET, SOCK_STREAM, 0)) == -1) {
		perror("clientSocket");
		exit(1);
	}
	
	server.sin_addr.s_addr = inet_addr(ip);
	server.sin_family = AF_INET;
	server.sin_port = htons(portNumber);
	
	//Connect to server
	if (connect(clientSocket, (struct sockaddr *)&server, sizeof(server)) < 0) {
		printf("Connection error\n");
		exit(1);
	}

	printf("client connects\n");

	//Receive handshake from server
	read(clientSocket, server_reply, sizeof(server_reply));
	sscanf(server_reply, "%d %d %s", &msg.msgID, &msg.payloadLen, &msg.payload);

	//Check validity of handshake
	if ((msg.msgID != 100) && (msg.payloadLen != 0) && (strcmp(msg.payload, ""))) {
		printf("106 ERROR\n");
		exit(1);
	}

	bzero(server_reply, 256);
	
	//Update message to handshake response
	msg.msgID = 101;
	msg.payloadLen = 0;
	msg.payload = "";

	//Send handshake response to server
	sprintf(message, "%d %d %s", msg.msgID, msg.payloadLen, msg.payload);
	write(clientSocket, message, sizeof(message));
	printf("client sends handshake response:(%d,%d,%s)\n", msg.msgID, msg.payloadLen, msg.payload);

    //Create result file
    FILE *newFile;
	strcpy(resultFile, argv[3]);
	strcat(resultFile, ".result");
	newFile = fopen(resultFile, "w+");

	//Grab city name from clientX.in
    int i = 0;
    int ret;
	city[0] = malloc(15);

    while(fgets(city[i], 100, clientFile)) {
		//Update message to twitterTrend request
    	msg.msgID = 102;

		if (city[i][strlen(city[i])-1] == '\n') {
			city[i][strlen(city[i])-1] = '\0';
		} 
		ret = strcmp("\0", city[i]);   
		if (ret == 0) {
			continue;
		}
		
		city[i+1] = malloc(15);

		//Update payload and payload length
		msg.payloadLen = strlen(city[i]);
		msg.payload = city[i];

		//Send twitterTrend request to server
		sprintf(message, "%d %d %s", msg.msgID, msg.payloadLen, msg.payload);
		write(clientSocket, message, sizeof(message));
		printf("client sends twitterTrend request:(%d,%d,\"%s\")\n", msg.msgID, msg.payloadLen, msg.payload);
		
		//Receive twitterTrend response from server
		read(clientSocket, server_reply, sizeof(server_reply));
		sscanf(server_reply, "%d %d %s", &msg.msgID, &msg.payloadLen, &msg.payload);

		//Check validity of server's twitterTrend response
		if (msg.msgID != 103) {
			printf("106 ERROR\n");
			exit(1);
		}

		//Export twitterTrend response to clientX.in.result
		fprintf(newFile, "%s : %s\n", city[i], &msg.payload);
		bzero(server_reply, 256);

		//Receive end of response from server
		read(clientSocket, server_reply, sizeof(server_reply));
		sscanf(server_reply, "%d %d %s", &msg.msgID, &msg.payloadLen, &msg.payload);
		
		//Check validity of 'end of response' message from server
		if ((msg.msgID != 105) && (msg.payloadLen != 0) && (strcmp(msg.payload, "") < 0)) {
			printf("106 ERROR\n");
			exit(1);
		}

		i++;
	}

	//Update message to end of request
	msg.msgID = 104;
	msg.payloadLen = 0;
	msg.payload = "";

	//Send end of request to server
	sprintf(message, "%d %d %s", msg.msgID, msg.payloadLen, msg.payload);
	write(clientSocket, message, sizeof(message));
	printf("client sends end of request:(%d,%d,%s)\n", msg.msgID, msg.payloadLen, msg.payload);

	//Close connection with server
	close(clientSocket);
	printf("client closes connection\n");
}
