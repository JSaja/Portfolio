This readme will serve as a high-level guide through the projects contained in this file structure, so that the reader may be able to obtain quick details about each project, which they may choose to delve more deeply in afterward.

**#Classification & Regression:**

   **1. Churn classification using dimension reduction**
   
       - Various cell-phone customer data features from an AWS dataset were used to determine whether or not those customers would churn.
  
   **2. Facebook network graphs**
   
      - Social network graphs for 10 Facebook users were built using NetworkX. Basic graph statistics such as clustering coefficient, degrees of centrality were computed.

   **3. KNN classification**
   
      - KNN was used as a technique to classify different iris species, from the sklearn "iris" dataset.

   **4. Lasso Regression**
   
      - A laymans definition of Lasso regression is presented, and lasso is performed on the kaggle "Bike sharing" dataset to determine which variables were irrelevant in predicting bike rentals.

   **5. Facial recognition using linear SVC**
   
      - The AutoML TPOT tool was used to determine that linear SVC was the best performing algorithm to classify faces from the Olivetti faces dataset.

   **6. Weight training exercise classification**
   
      - Gradient boosting classifier & Random forest classifier were used to determine what form was used to perform a bicep curl exercise (i.e correct form, incorrect form #1, incorrect form #2, etc.). The data is sensor data from a participant's arm.

  
**#Neural Networks**

   **1. Handwritten digit recognizer**
   
      - Written from scratch in Python & help from the book "Neural Networks and Deep Learning" by Michael Nielsen, the program performs minibatch gradient descemt using a cross-entropy cost function, along with backpropagation to classify MNIST digits.

**#Sentiment Analysis**

   **1. Text classification and sentiment analysis of Twitter user's profiles:**
   
      - Determines whether users have positive or negative attitudes, and text classification denotes a category for a given tweet.
