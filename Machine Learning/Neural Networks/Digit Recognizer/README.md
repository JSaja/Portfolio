To use the digit recognizer, run "nn.py".

This creates a network of 784 inputs, 10 outputs, and 65 hidden neurons (the only hidden layer).
The inputs are MNIST 28x28 pixels (784), each of which has an encoded grayscale value. The network will be trained using minibatch gradient
descent using the cross-entropy loss function. The network also incorporates L2 normalization for weights, and an optional momentum based
gradient descent. Other features such as learning schedules for the learning rate and momentum parameters are also included.

When training is done, we can visualize which test examples the trained network has gotten wrong by running "dataViz.py".

Dataviz.py can be called in one of two ways from the command-line:
  1 - "dataViz.py": This will output one randomly picked example that the network has failed to correctly classify.
  2 - "dataViz.py number": This will output the network's prediction for the given example number of the test set(between 0 and 10,000)
