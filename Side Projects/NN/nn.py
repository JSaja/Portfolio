import _pickle
import math
import numpy as np
import numpy.random as nprand
import random
import mnist_loader
import time

class Network(object):
	def __init__(self, sizes):
		self.sizes = sizes
		self.numLayers = len(sizes)		
		self.biases = [nprand.randn(y, 1) for y in sizes[1:]]
		self.weights = [nprand.randn(y, x) / math.sqrt(self.sizes[0]) for x,y in zip(sizes[:-1], sizes[1:])]


	def feedforward(self, a):
		for w, b in zip(self.weights, self.biases):
			a = sigmoid(np.dot(w, a) + b)
		return a


	def SGD(self, trainingData, epochs, miniBatchSize, learningRate, lmbda, testData=None, validationData=None):
		if testData:
			nTest = len(testData)
		n = len(trainingData)
		percentage = []

		for j in range(epochs):
			# learningRate = float(input("Enter learningRate: "))
			random.shuffle(trainingData)
			miniBatches = [trainingData[k:k+miniBatchSize] for k in range(0,n,miniBatchSize)]

			#Apply 1 step of sgd via updateMiniBatch - update each minibatch in order, updating weights after each call
			for miniBatch in miniBatches:
				self.updateMiniBatch(miniBatch, learningRate, lmbda, len(trainingData))

			#If we pass in validation data, evaluate it now

			if validationData:
				evaluate = self.evaluate(validationData)

				#Calculate rate of change of model accuracy
				percentage.append('%.2f'%((evaluate/nTest)*100))
				try:
					percentage[-2]
				except:
					roc = percentage[-1]
				else:
					roc = '%.2f'%(float(percentage[-1]) - float(percentage[-2]))
				print("Epoch {}: {}/{} : {}% ROC = {}%".format(j+1, evaluate, nTest, percentage[-1], roc))
				evaluate2 = self.evaluate(testData)
				print("TestData: {}/{}".format(evaluate2, nTest))
			else:
				print("Epoch {} is complete".format(j))


	def updateMiniBatch(self, miniBatch, learningRate, lmbda, n):
		inputVectors = np.asarray([x.ravel() for x, y in miniBatch]).transpose()
		outputVectors = np.asarray([y.ravel() for x, y in miniBatch]).transpose()

		nablaB, nablaW = self.backprop(inputVectors, outputVectors)

		self.weights = [(1-lmbda*(learningRate/n)*w)-(learningRate/len(miniBatch)) * nw for w, nw in zip(self.weights, nablaW)]
		self.biases = [b-(learningRate/len(miniBatch)) * nb for b, nb in zip(self.biases, nablaB)]


	def backprop(self, x, y):
		nablaB = [0 for b in self.biases]
		nablaW = [0 for w in self.weights]

		#feedforward
		activationMatrix = x
		activations = [x]
		zs = []
		for w,b in zip(self.weights, self.biases):
			z = np.dot(w, activationMatrix) + b
			zs.append(z)
			activationMatrix = sigmoid(z)
			activations.append(activationMatrix)

		#Apply softMax to each weighted input in the last layer
		outputSoftmax = np.zeros(zs[-1].shape)
		for i, ele in enumerate(zs[-1]):
			outputSoftmax[i] = self.softMax(ele)

		#backward pass, output layer
		delta = (activations[-1] - y) * sigmoidPrime(zs[-1])
		nablaB[-1] = np.array([[sum(ele)] for ele in delta])
		nablaW[-1] = np.dot(delta, activations[-2].transpose())

		#go 'backwards' from input layer -> output layer-1
		for layer in range(2, self.numLayers):
			z = zs[-layer]
			delta = np.dot(self.weights[-layer+1].transpose(), delta) * sigmoidPrime(z)
			nablaB[-layer] = np.array([[sum(ele)] for ele in delta])
			nablaW[-layer] = np.dot(delta, activations[-layer-1].transpose())
		
		return (nablaB, nablaW)


	def evaluate(self, data):
		testResults = [(np.argmax(self.feedforward(x)), np.argmax(y)) for (x, y) in data]
		return sum(int(x == y) for (x, y) in testResults)


	def softMax(self, a):
		eList = [np.exp(val) for val in a]
		probability = np.asarray([np.exp(val)/sum(eList) for val in a])
		return probability


def sigmoid(z):
	return 1.0/(1.0+np.exp(-z))


def sigmoidPrime(z):
	return (sigmoid(z) * (1-sigmoid(z)))


def main():
	trd, vd, td = mnist_loader.load_data_wrapper()
	inputNeurons = 784
	hiddenNeurons1 = 78
	outputNeurons = 10

	startTime = time.clock()
	#If we already have a trained net, start with the trained net
	try:
		with open("vars.pkl", 'rb') as f:
			net = _pickle.load(f)
	except:				
		net = Network([inputNeurons, hiddenNeurons1, outputNeurons])

	net.SGD(trd, 20, 10, 1.0, 0.1, testData=td, validationData=vd)
	print("Total time elapsed: {} seconds.".format(time.clock()-startTime))

	#Save the completely trained net
	with open('vars.pkl', 'wb') as f:
		f.truncate()
		_pickle.dump(net, f)
		_pickle.dump(td, f)


if __name__ == "__main__":
	main()
