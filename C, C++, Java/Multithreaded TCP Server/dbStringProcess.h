#ifndef DBSTRINGPROCESS_H
#define DBSTRINGPROCESS_H

char* getHaystack(char* needle, char* database, char* lineBuf);
char* makeOutputString(char* needle, char* haystack, char* partialHaystack, char* finalString);
char* dbProcess(char* needle);
char* getNeedle(FILE* clientFile, char* buffer);

#endif /* DBSTRINGPROCESS_H */
