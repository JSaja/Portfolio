"""
Create a custom 28x28 number and run a prediction on it based on your trained NN
Usage on command line/terminal: <customNumberExample.py number>
"""
import dataViz
import mnist_loader
from nn import *
import numpy as np
import pickle
import random
from scipy import misc
import sys

def getImage(filename, label):
	image = misc.imread(filename, flatten=True)
	label = label
	return(image, label)

def avg(pixel):
	return np.average(pixel)

def weightedAvg(pixel):
	return 0.299*pixel[0]+0.587*pixel[1]+0.114*pixel[2]

def formatArr(image):
	grey = np.zeros((image.shape[0], image.shape[1]))
	for row in range(len(image)):
		for col in range(len(image[row])):
			grey[row][col] = 1 - sigmoid(avg(image[row][col]))
			if grey[row][col] == 1.:
				grey[row][col] = 0

	inputs = np.reshape(grey, (784, 1))
	return inputs

def draw(inputs):
	outputString = ""
	for value, idx in zip(inputs, range(784)):
		if value > 0:
			outputString += "@"
		elif value == 0.:
			outputString += "."
		if idx % 28 == 0 and idx != 0:
			print(outputString)
			outputString = ""

def predict(network, inputs, label):
	result = [mnist_loader.vectorized_result(label)]
	inputs = [inputs]
	data = list(zip(inputs, result))
	prediction = [(np.argmax(network.feedforward(x)), np.argmax(y)) for (x, y) in data]
	for predicted, actual in prediction:
		if predicted == actual:
			print("Correct Prediction: {}".format(actual))
		elif predicted != actual:
			print("Incorrect Prediction: {}".format(predicted))

def init():
	with open('vars.pkl', 'rb') as f:
		network = pickle.load(f)
		weight = pickle.load(f)
		bias = pickle.load(f)
	return network, weight, bias

def main():
	label = int(sys.argv[1])
	netOne, netOne.weights, netOne.biases = init()

	image, label = getImage("customNumber.png", label)

	inputs = formatArr(image)

	draw(inputs)

	predict(netOne, inputs, label)

if __name__ == "__main__":
	main()