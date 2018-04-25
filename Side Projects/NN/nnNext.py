import mnist_loader
import pickle
from nn import *
import dataViz
import random
from scipy import misc
import numpy as np


with open('vars.pkl', 'rb') as f:
	weight = pickle.load(f)
	bias = pickle.load(f)
	td = pickle.load(f)

netOne = Network([784,30,10])
netOne.weights = weight
netOne.biases = bias

def randomWrong(testData):
	testResults = [(np.argmax(netOne.feedforward(x)), np.argmax(y)) for (x, y) in testData]
	incorrect, incorrectLabel, correct = [], [], []
	i = 0
	for x,y in testResults:
		if (x == y):
			correct.append(i)
		elif (x != y):
			incorrect.append(i)
			incorrectLabel.append(x)
		i += 1

	print("this one is incorrect")
	randomChoice = random.choice(incorrect)
	randomChoiceIdx = incorrect.index(randomChoice)
	dataViz.tryExample(randomChoice)
	print("Predicted Label: {}".format(incorrectLabel[randomChoiceIdx]))
	return correct, incorrect

cc, ii = randomWrong(td)
# #Sanity check
# print(len(cc))
# print(len(ii))
# print(len(cc) + len(ii))