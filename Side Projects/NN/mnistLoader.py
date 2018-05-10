"""
mnistLoader
"""
import _pickle
import gzip
import numpy as np

def loadData():
	f = gzip.open('mnist.pkl.gz', 'rb')
	trainingData, validationData, testData = _pickle.load(f, encoding="latin1")
	f.close()
	return (trainingData, validationData, testData)

def loadDataWrapper():
	trainingData, validationData, testData = load_data()
	#Go from flat row of values to column, as if we are looking at a NN map
	trainingInputs = [np.reshape(x, (784, 1)) for x in trainingData[0]]
	trainingResults = [vectorized_result(j) for j in trainingData[1]]
	trd = list(zip(trainingInputs, trainingResults))

	validationInputs = [np.reshape(x, (784, 1)) for x in validationData[0]]
	validationResults = [vectorized_result(j) for j in validationData[1]]
	vd = list(zip(validationInputs, validationResults))

	testInput = [np.reshape(x, (784, 1)) for x in testData[0]]
	testResults = [vectorized_result(j) for j in testData[1]]
	td = list(zip(testInput, testResults))

	return(trd, vd, td)

def vectorizedResult(j):
	#Return 10-dim unit vector
	outputCol = np.zeros((10,1))
	outputCol[j] = 1
	return outputCol