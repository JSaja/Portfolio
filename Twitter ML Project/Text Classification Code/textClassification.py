#The feature is a random word for whichever
#category has the most words in common
from textblob.classifiers import NaiveBayesClassifier
from tweepy import Stream
from tweepy import OAuthHandler
from tweepy.streaming import StreamListener
import tweepy
import time
import pickle
import sys
reload(sys)
sys.setdefaultencoding('utf8')

#Twitter API credentials
consumer_key = "XXXX"
consumer_secret = "XXXX"
access_key = "XXXX"
access_secret = "XXXX"

#authorize twitter, initialize tweepy
auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_key, access_secret)
api = tweepy.API(auth)


trainingData = []
musicTweet = tweepy.Cursor(api.search, q='music', lang="en").items(750)

for tweet in musicTweet:
	tweetText = tweet.text.encode("utf-8")
	tweetText = unicode(tweetText, errors='ignore')
	x = (tweetText, 'music')
	trainingData.append(x)
		
foodTweet = tweepy.Cursor(api.search, q='food', lang="en").items(750)

for tweet in foodTweet:
	tweetText = tweet.text.encode("utf-8")
	tweetText = unicode(tweetText, errors='ignore')
	y = (tweetText, 'food')
	trainingData.append(y)
		
sportsTweet = tweepy.Cursor(api.search, q='sports', lang="en").items(750)

for tweet in sportsTweet:
	tweetText = tweet.text.encode("utf-8")
	tweetText = unicode(tweetText, errors='ignore')
	z = (tweetText, 'sport')
	trainingData.append(z)

	
cl = NaiveBayesClassifier(trainingData)

#Contain test set of tweets
musicTest = open('musicData.txt', 'r')
foodTest = open('foodData.txt', 'r')
sportTest = open('sportData.txt', 'r')

t = 0 # Number of lines in musicTest
u = 0 # Number of correct guesses for musicTest
for line in musicTest:
	a = line
	t += 1
	if cl.classify(line) == "music":
		u += 1
print "MUSIC TWEETS CORRECT: %d / %d" % (u, t)

t = 0 #reset vars
u = 0 #reset vars
for line in foodTest:
	b = line
	t += 1
	if cl.classify(line) == "food":
		u += 1
print "FOOD TWEETS CORRECT: %d / %d" % (u, t)

t = 0 #reset vars
u = 0 #reset vars	
for line in sportTest:
	c = line
	t += 1
	if cl.classify(line) == "sport":
		u += 1
print "SPORT TWEETS CORRECT: %d / %d" % (u, t)
	





