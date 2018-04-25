import numpy as np
import numpy.random as nprand
import random
import mnist_loader
import os.path
import pickle

class Network(object):
	def __init__(self, sizes):
		self.sizes = sizes
		self.num_layers = len(sizes)
		#wouldn't work if sizes have changed
		if not(os.path.exists('startingVals.pkl')):
			self.biases = [nprand.randn(y, 1) for y in sizes[1:]]
			self.weights = [nprand.randn(y, x) for x,y in zip(sizes[:-1], sizes[1:])]
		else:
			with open('startingVals.pkl', 'rb') as f:
				self.biases = pickle.load(f)
				self.weights = pickle.load(f)

	def feedforward(self, a):
		for w, b in zip(self.weights, self.biases):
			a = sigmoid(np.dot(w, a) + b)
		return a

	def SGD(self, trainingData, epochs, miniBatchSize, learningRate, testData=None):
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
				self.updateMiniBatch(miniBatch, learningRate)

			#If we pass in test data, evaluate it now
			if testData:
				evaluate = self.evaluate(testData)

				if evaluate > 9601:
					with open('startingVals.pkl', 'wb') as f:
						f.truncate()
						pickle.dump(self.biases, f)
						pickle.dump(self.weights, f)

				#Calculate rate of change of model accuracy
				percentage.append('%.2f'%((evaluate/nTest)*100))
				try:
					percentage[-2]
				except:
					roc = percentage[-1]
				else:
					roc = '%.2f'%(float(percentage[-1]) - float(percentage[-2]))
				print("Epoch {}: {}/{} : {}% ROC = {}%".format(j, evaluate, nTest, percentage[-1], roc))
			else:
				print("Epoch {} is complete".format(j))

	def updateMiniBatch(self, miniBatch, learningRate):
		nablaB = [np.zeros(b.shape) for b in self.biases]
		nablaW = [np.zeros(w.shape) for w in self.weights]
		for x,y in miniBatch:
			deltaNablaB, deltaNablaW = self.backprop(x, y)
			nablaB = [nb+dnb for nb,dnb in zip(nablaB, deltaNablaB)]
			nablaW = [nw+dnw for nw,dnw in zip(nablaW, deltaNablaW)]
		self.weights = [w-(learningRate/len(miniBatch)) * nw for w, nw in zip(self.weights, nablaW)]
		self.biases = [b-(learningRate/len(miniBatch)) * nb for b, nb in zip(self.biases, nablaB)]

	def backprop(self, x, y):
		nablaB = [np.zeros(b.shape) for b in self.biases]
		nablaW = [np.zeros(w.shape) for w in self.weights]

		#feedforward
		activation = x
		activations = [x]
		zs = []
		for w,b in zip(self.weights, self.biases):
			z = np.dot(w, activation)+b
			zs.append(z)
			activation = sigmoid(z)
			activations.append(activation)

		#backward pass, output layer
		delta = self.costDerivative(activations[-1], y) * sigmoidPrime(zs[-1])
		nablaB[-1] = delta
		nablaW[-1] = np.dot(delta, activations[-2].transpose())
	
		#go backwards from ouput layer -> input layer
		for layer in range(2, self.num_layers):
			z = zs[-layer]
			delta = np.dot(self.weights[-layer+1].transpose(), delta) * sigmoidPrime(z)
			nablaB[-layer] = delta
			nablaW[-layer] = np.dot(delta, activations[-layer-1].transpose())
		# for layer in range(self.num_layers-1, 2, -1):
		# 	z = zs[layer]
		# 	delta = np.dot(self.weights[layer+1].transpose(), delta) * sigmoidPrime(z)
		# 	nablaB[layer] = delta
		# 	nablaW[layer] = np.dot(delta, activations[layer-1].transpose())
		return (nablaB, nablaW)

	def evaluate(self, testData):
		testResults = [(np.argmax(self.feedforward(x)), np.argmax(y)) for (x, y) in testData]
		return sum(int(x == y) for (x, y) in testResults)

	def costDerivative(self, outputActivations, y):
		return (outputActivations - y)

def sigmoid(z):
	return 1.0/(1.0+np.exp(-z))

def sigmoidPrime(z):
	return (sigmoid(z) * (1-sigmoid(z)))


def main():
	trd, vd, td = mnist_loader.load_data_wrapper()
	net = Network([784,78,10])	
	net.SGD(trd, 20, 10, 2.0, testData=td)
	print("STOP")
	with open('vars.pkl', 'wb') as f:
		f.truncate()
		pickle.dump(net, f)
		pickle.dump(net.weights, f)
		pickle.dump(net.biases, f)
		pickle.dump(td, f)

if __name__ == "__main__":
	main()