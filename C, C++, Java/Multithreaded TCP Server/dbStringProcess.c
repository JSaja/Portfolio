//THIS FILE DOES CLIENT PROCESSING
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define LINEMAX 100


//PROTOTYPES
char* getHaystack(char* needle, char* database, char* lineBuf);
char* makeOutputString(char* needle, char* haystack, char* partialHaystack, char* finalString);


char* dbProcess(char* needle) {
	char* dbName = "TwitterDB.txt";
	char* lineBuf = (char*)malloc(LINEMAX);
	char* partialHaystack = (char*)malloc(LINEMAX);
	char* finalString = (char*)malloc(LINEMAX + 4);
	char* outputString = (char*)malloc(LINEMAX + 4);

	char* haystack = getHaystack(needle, dbName, lineBuf);
	outputString = makeOutputString(needle, haystack, partialHaystack, finalString);
	//FREE EVERYTHING EXCEPT THE OUTPUT STRING & FINAL STRING
	 free(lineBuf);
	 free(partialHaystack);

	return outputString;
}

char* getNeedle(FILE* clientFile, char* buffer) {
	int ret;
	//open up file
	//~ FILE *clientFile;
	//~ clientFile = fopen(clientName, "r");
	//~ if (clientFile == NULL) {
		//~ printf("The file : %s doesn't exist in working directory\n", clientName);
		//~ exit(1);
	//~ }

	//read through fgets
	fgets(buffer, LINEMAX, clientFile);

	if (buffer[strlen(buffer)-1] == '\n') {
		buffer[strlen(buffer)-1] = '\0';
	}

	return buffer;
}

char* getHaystack(char* needle, char* database, char* lineBuf) {
	int lineCount = 0;
	int ret;

	FILE* db;
	db = fopen(database, "r");
	if (db == NULL) {
		printf("The file : %s doesn't exist in working directory\n", database);
		exit(1);
	}


	while (fgets(lineBuf, LINEMAX, db)) {
		//Get rid of extra character bugs
		if (lineBuf[strlen(lineBuf)-1] == '\n') {
			lineBuf[strlen(lineBuf)-1] = '\0';
		}
		ret = strcmp("\0", lineBuf);
		if (ret == 0)
			continue;

	    //LineBuf now has potential haystack.... break out of while on success
        if (strstr(lineBuf, needle) !=NULL) {
			break;
		}
		lineCount++;
	}

	fclose(db);
	return lineBuf;
}


char* makeOutputString(char* needle, char* haystack, char* partialHaystack, char* finalString) {
	int needleLen = sizeof(needle);
	int haystackLen = 0;

	needleLen = strlen(needle);
	haystackLen = strlen(haystack);

	//get partial haystack
	strncpy(partialHaystack, haystack + needleLen + 1, haystackLen);

	strcat(finalString, needle);
	strcat(finalString, " : ");
	strcat(finalString, partialHaystack);
	strcat(finalString, "\n");

	return finalString;
}
