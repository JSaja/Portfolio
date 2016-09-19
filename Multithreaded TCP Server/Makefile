#!/bin/bash
all: twitterTrend_server twitterTrend_client 

twitterTrend_server: twitterTrend_server.o
	gcc -pthread -o twitterTrend_server twitterTrend_server.c dbStringProcess.c queue.c

twitterTrend_client: twitterTrend_client.o
	gcc -pthread -o twitterTrend_client twitterTrend_client.c dbStringProcess.c queue.c

twitterTrend_server.o: twitterTrend_server.c dbStringProcess.c queue.c
	gcc -g -O -w -c -pthread twitterTrend_server.c
	
twitterTrend_client.o: twitterTrend_client.c dbStringProcess.c queue.c
	gcc -g -O -w -c -pthread twitterTrend_client.c

clean:
	rm -rf *o twitterTrend_server
	rm -rf *o twitterTrend_client
	rm -rf *.result
