"""
This file is used to give intuiton about how MNIST data is represented.

PREREQUISITE:
	1) Run nn.py to generate the vars.pkl file, where the trained net is stored and will be retrieved

Usage on command line/terminal: 
	1) <dataViz.py number>: Shows prediction for the example number provided
	2) <dataViz.py>: Shows a randomly chosen wrong prediction on a test set example
"""
import _pickle
from nn import *
import numpy as np
import sys

def draw(example):
	outputString = ""
	#Replace grayscale values with symbols for viz purposes, and output to console
	for value, idx in zip(example, range(784)):
		if value > 0:
			outputString += "@"
		elif value == 0.:
			outputString += "."
		if idx % 28 == 0 and idx != 0:
			print(outputString)
			outputString = ""

def predict(example, net):
	example = np.reshape(example, (net.sizes[0],1))
	prediction = np.argmax(net.feedforward(example))
	return prediction

#Number is the training example between 0 and 9,999 that you would like to see.
def tryExample(testData, net):
	number = int(sys.argv[1])
	example = testData[number][0]
	exampleLabel = np.argmax(testData[number][1])
	
	draw(example)

	prediction = predict(example, net)
	if prediction == exampleLabel:
		print("Correctly predicted Label: {}".format(prediction))
	else:
		print("Incorrectly predicted {}. True label is {}".format(prediction, exampleLabel))

def randomWrong(testData, net):
	wrongList = []

	for i, (x,y) in enumerate(testData):
		prediction = predict(x, net)
		label = np.argmax(y)
		if prediction != label:
			wrongList.append(i)
	
	randomWrong = random.choice(wrongList)
	draw(testData[randomWrong][0])
	
	prediction = predict(testData[randomWrong][0], net)
	exampleLabel = np.argmax(testData[randomWrong][1])
	if prediction == exampleLabel:
		print("Correctly predicted Label: {}".format(prediction))
	else:
		print("Incorrectly predicted {}. True label is {}".format(prediction, exampleLabel))
	print(len(wrongList))


def main():
	with open('vars.pkl', 'rb') as f:
		net = _pickle.load(f)
		td = _pickle.load(f, encoding="latin1")

	if len(sys.argv) == 2:
		tryExample(td, net)
	elif len(sys.argv) == 1:
		randomWrong(td, net)
	else:
		print("You must set exactly 0 or 1 arguments")

if __name__ == "__main__":
	main()
