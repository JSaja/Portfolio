"""
This file is used to give intuiton about how MNIST data is represented.
Usage on command line/terminal: <dataViz.py number>
"""
import _pickle
import gzip
import numpy as np
from PIL import Image
import sys

def load_data():
	f = gzip.open('mnist.pkl.gz', 'rb')
	trainingData, validationData, testData = _pickle.load(f, encoding="latin1")
	f.close()
	return (trainingData, validationData, testData)

#Number is the training example between 0 and 49,999 that you would like to see.
def tryExample(number):
	trainingData, validationData, testData = load_data()
	example = trainingData[0][number]
	exampleLabel = trainingData[1][number]
	#Replace grayscale values with symbols for viz purposes, and output to console
	outputString = ""
	for value, idx in zip(example, range(784)):
		if value > 0:
			outputString += "@"
		elif value == 0.:
			outputString += "."
		if idx % 28 == 0 and idx != 0:
			print(outputString)
			outputString = ""

	print("Correct Label: {}".format(exampleLabel))

def main():
	number = int(sys.argv[1])
	tryExample(number)

if __name__ == "__main__":
	main()