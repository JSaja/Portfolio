#!/bin/bash

for i in $(seq 1 4) ; do
        echo "Creating client$i";
	./twitterTrend_client 128.101.38.39 1990 client$i.in > result$i.temp;
done	




