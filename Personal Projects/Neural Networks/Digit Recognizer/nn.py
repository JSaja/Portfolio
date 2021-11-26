import _pickle
import math
import numpy as np
import numpy.random as nprand
import random
import mnistLoader
import time

class Network(object):
	def __init__(self, sizes, cost):
		self.costType = cost
		self.sizes = sizes
		self.numLayers = len(sizes)
		self.biases = [nprand.randn(y, 1) for y in sizes[1:]]
		self.weights = [nprand.randn(y, x) / math.sqrt(self.sizes[0]) for x,y in zip(sizes[:-1], sizes[1:])]
		self.velocities = [0 for w in self.weights]


	def cost(self, z, a, y):
		if self.costType == "crossEntropy":
			return a - y
		elif self.costType == "MSE":
			return (a - y) * sigmoidPrime(z)


	def feedforward(self, a):
		for w, b in zip(self.weights, self.biases):
			a = sigmoid(np.dot(w, a) + b)
		return a


	def SGD(self, trainingData, epochs, miniBatchSize, learningRate, lmbda, learningSchedule, momentum, testData=None, validationData=None):
		maxClassified, lastImprovement = 0, 0
		initLearningRate = learningRate
		if testData:
			nTest = len(testData)
		n = len(trainingData)
		percentage = []

		for j in range(epochs):		
			random.shuffle(trainingData)
			miniBatches = [trainingData[k:k+miniBatchSize] for k in range(0,n,miniBatchSize)]

			#Apply 1 step of sgd via updateMiniBatch - update each minibatch in order, updating weights after each call
			for miniBatch in miniBatches:
				self.updateMiniBatch(miniBatch, learningRate, lmbda, momentum, len(trainingData))			

			#If we pass in validation data, evaluate it now

			if validationData:
				evaluate = self.evaluate(validationData)

				#Calculate model accuracy
				percentage.append('%.2f'%((evaluate/nTest)*100))
				try:
					percentage[-2]
				except:
					roc = percentage[-1]
				else:
					roc = '%.2f'%(float(percentage[-1]) - float(percentage[-2]))
				print("Epoch {}: {}/{} : {}% ROC = {}%".format(j+1, evaluate, nTest, percentage[-1], roc, maxClassified))
				evaluate2 = self.evaluate(testData)
				print("TestData: {}/{}".format(evaluate2, nTest))			

				#Learning schedule to decrease learning rate after N epochs of no improvement
				if (evaluate > maxClassified):
					maxClassified = evaluate
					lastImprovement = 0
					with open("net.pkl", "wb") as f:
						f.truncate()
						_pickle.dump(self, f)

				elif (evaluate <= maxClassified) and (lastImprovement < learningSchedule-1):
					lastImprovement += 1
				elif (lastImprovement >= learningSchedule-1) and ((learningRate * 1024) > initLearningRate):					
					learningRate = learningRate / 2
					momentum = momentum / 2
					print("No classification improvement in {} epochs... Halving learningRate: ({}) & momentum: ({})".format(lastImprovement+1, learningRate, momentum))
					lastImprovement = 0
					#Implement "rolling" improvement
					maxClassified = 0
				else:
					print("learningRate halved 10 times... stopping SGD.")
					return
			else:
				print("Epoch {} is complete".format(j))

	def updateMiniBatch(self, miniBatch, learningRate, lmbda, momentum, n):
		inputVectors = np.asarray([x.ravel() for x, y in miniBatch]).transpose()
		outputVectors = np.asarray([y.ravel() for x, y in miniBatch]).transpose()

		nablaB, nablaW = self.backprop(inputVectors, outputVectors)

		self.velocities = [(momentum * velocity) - (learningRate/len(miniBatch)) * nw for w, nw, velocity in zip(self.weights, nablaW, self.velocities)]
		self.weights = [(1-lmbda*(learningRate/n))*w + velocity for w, nw, velocity in zip(self.weights, nablaW, self.velocities)]
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
		# outputSoftmax = np.zeros(zs[-1].shape)
		# for i, ele in enumerate(zs[-1]):
		# 	outputSoftmax[i] = self.softMax(ele)

		#backward pass, output layer, delta calculated using cost defined in net init.
		delta = self.cost(zs[-1], activations[-1], y)
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
	trd, vd, td = mnistLoader.loadDataWrapper()
	with open("data.pkl", "wb") as f:
		f.truncate()
		_pickle.dump(trd, f)
		_pickle.dump(td, f)
		_pickle.dump(vd, f)

	inputNeurons = 784
	hiddenNeurons1 = 65
	outputNeurons = 10

	startTime = time.clock()
	#If we already have a trained net, start with the trained net
	# try:
	# 	with open("net.pkl", 'rb') as f:
	# 		net = _pickle.load(f)
	# except:
	# 	print("could not open net")
	net = Network([inputNeurons, hiddenNeurons1, outputNeurons], cost="crossEntropy")


	net.SGD(trd, 1000, 10, 3.0, 0, learningSchedule=2, momentum=0, testData=td, validationData=vd)
	print("Total time elapsed: {} seconds.".format(time.clock()-startTime))	

if __name__ == "__main__":
	main()
