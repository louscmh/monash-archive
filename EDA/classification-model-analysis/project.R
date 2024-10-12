# Setup
setwd("~/FIT3152/a2")
rm(list = ls()) 
Phish <- read.csv("PhishingData.csv") 
set.seed(32937350) # Your Student ID is the random seed 
L <- as.data.frame(c(1:50)) 
L <- L[sample(nrow(L), 10, replace = FALSE),] 
Phish <- Phish[(Phish$A01 %in% L),] 
PD <- Phish[sample(nrow(Phish), 2000, replace = FALSE),] # sample of 2000 rows

# Load necessary libraries
library(e1071)
library(tree)
library(dplyr)
library(adabag)
library(rpart)
library(randomForest)
library(ROCR)
library(FSelector)
library(pROC)

# QUESTION 1 ################################################################################

# Exploratory Data Analysis
head(PD)
tail(PD)
dim(PD)
str(PD)

# Column names and summary statistics
colnames(PD)
summary(PD)
summary(PD[sapply(PD,is.numeric)])

# Count numeric and non-numeric columns
sum(sapply(PD,is.numeric))
sum(sapply(PD,function(x) !is.numeric(x)))

# Count missing values
colSums(is.na(PD))
sum(is.na(PD))

# Proportion of phishing sites to legitimate sites
sum(PD$Class==1)/sum(PD$Class==0)

# QUESTION 2 ################################################################################

# Impute missing values with the mean of each attribute
for (col in names (PD)) {
  PD[[col]] <- replace(PD[[col]], is.na(PD[[col]]), mean(PD[[col]], na.rm = TRUE))
}

# QUESTION 3 ################################################################################

# Split the data into training and testing sets
set.seed(32937350) # Use student ID as random seed
train.row = sample(1:nrow(PD), 0.7*nrow(PD)) 
PD.train = PD[train.row,] 
PD.train$Class = factor(PD.train$Class)
PD.test = PD[-train.row,]
PD.test$Class = factor(PD.test$Class)

# QUESTION 4 ################################################################################

# Train different models on the training data
# Decision Tree
PDfit.dt = tree(Class~., data=PD.train)

# Naive Bayes
PDfit.nb = naiveBayes(Class~., data=PD.train)

# Bagging
PDfit.bag = bagging(Class~., data=PD.train)

# Boosting
PDfit.boost = boosting(Class~., data=PD.train)

# Random Forest
PDfit.rf = randomForest(Class~., data=PD.train)

# Model summary
summary(PDfit.dt)
summary(PDfit.nb)
summary(PDfit.bag)
summary(PDfit.boost)
summary(PDfit.rf)

# QUESTION 5 ################################################################################

# Make predictions on the test data
predict.dt = predict(PDfit.dt, PD.test, type="class")
predict.nb = predict(PDfit.nb, PD.test)
predict.bag = predict.bagging(PDfit.bag, newdata=PD.test)
predict.boost = predict.boosting(PDfit.boost, newdata=PD.test)
predict.rf = predict(PDfit.rf, PD.test)

# Create confusion matrices
table.dt = table(observed = PD.test$Class, predicted=predict.dt)
table.nb = table(observed = PD.test$Class, predicted=predict.nb)
table.bag = table(observed = PD.test$Class, predicted=predict.bag$class)
table.boost = table(observed = PD.test$Class, predicted=predict.boost$class)
table.rf = table(observed = PD.test$Class, predicted=predict.rf)

# Calculate accuracy
acc.dt = (table.dt[2,2]+table.dt[1,1])/sum(table.dt)
acc.nb = (table.nb[2,2]+table.nb[1,1])/sum(table.nb)
acc.bag = (table.bag[2,2]+table.bag[1,1])/sum(table.bag)
acc.boost = (table.boost[2,2]+table.boost[1,1])/sum(table.boost)
acc.rf = (table.rf[2,2]+table.rf[1,1])/sum(table.rf)

# Print confusion matrices
table.dt
table.nb
table.bag
table.boost
table.rf

# Print accuracies
acc.dt
acc.nb
acc.bag
acc.boost
acc.rf

# QUESTION 6 ################################################################################

# Calculate ROC curves and AUC for each model
predict.dt.r = predict(PDfit.dt, PD.test, type="vector")
predict.nb.r = predict(PDfit.nb, PD.test, type="raw")
predict.bag.r = predict.bag$prob
predict.boost.r = predict.boost$prob
predict.rf.r = predict(PDfit.rf, PD.test, type="prob")

pred.dt = ROCR::prediction(predict.dt.r[,2], PD.test$Class)
pred.nb = ROCR::prediction(predict.nb.r[,2], PD.test$Class)
pred.bag = ROCR::prediction(predict.bag.r[,2], PD.test$Class)
pred.boost = ROCR::prediction(predict.boost.r[,2], PD.test$Class)
pred.rf = ROCR::prediction(predict.rf.r[,2], PD.test$Class)

perf.dt = performance(pred.dt, "tpr", "fpr")
perf.nb = performance(pred.nb, "tpr", "fpr")
perf.bag = performance(pred.bag, "tpr", "fpr")
perf.boost = performance(pred.boost, "tpr", "fpr")
perf.rf = performance(pred.rf, "tpr", "fpr")

# Plot ROC curves
plot(perf.dt, col="red", main="ROC Curves for Models")
plot(perf.nb, add=TRUE, col="blue")
plot(perf.bag, add=TRUE, col="green")
plot(perf.boost, add=TRUE, col="orange")
plot(perf.rf, add=TRUE, col="yellow")
abline(0, 1, lty=2)
legend("bottomright", legend = c("Decision Tree", "Naive Bayes", "Bagging", "Boosting", "Random Forest"),
       col = c("red", "blue", "green", "orange", "yellow"), lty = 1)

# Calculate AUC values
auc.dt = performance(pred.dt, "auc")
auc.nb = performance(pred.nb, "auc")
auc.bag = performance(pred.bag, "auc")
auc.boost = performance(pred.boost, "auc")
auc.rf = performance(pred.rf, "auc")

# Print AUC values
as.numeric(auc.dt@y.values)
as.numeric(auc.nb@y.values)
as.numeric(auc.bag@y.values)
as.numeric(auc.boost@y.values)
as.numeric(auc.rf@y.values)

# QUESTION 7 ################################################################################

# Summarize accuracy and AUC for each classifier
result = data.frame(
  Classifier = c("Decision Tree", "Naive Bayes", "Bagging", "Boosting", "Random Forest"),
  Accuracy = c(acc.dt, acc.nb, acc.bag, acc.boost, acc.rf),
  AUC = c(as.numeric(auc.dt@y.values), as.numeric(auc.nb@y.values), as.numeric(auc.bag@y.values), as.numeric(auc.boost@y.values), as.numeric(auc.rf@y.values))
)
result

# Decision Tree and Bagging are potential candidates to be the single "best" classifier

# QUESTION 8 ################################################################################

# Investigate attribute importance in different models, Naive Bayes is excluded as it has no indicator for importance
print(summary(PDfit.dt)) 
print(PDfit.bag$importance) 
print(PDfit.boost$importance) 
print(PDfit.rf$importance)

# QUESTION 9 ################################################################################

# Prune the decision tree to create a simpler model
PDfit.simple = prune.tree(PDfit.dt, best=3)
PDfit.simple

# Make predictions and calculate accuracy for the simplified model
predict.simple = predict(PDfit.simple, PD.test, type = "class")
table.simple = table(observed = PD.test$Class, predicted=predict.simple)

accuracy.simple = sum(predict.simple == PD.test$Class) / nrow(PD.test)

# Calculate ROC curve and AUC for the simplified model
predict.simple.r = predict(PDfit.simple, PD.test, type="vector")
pred.simple = ROCR::prediction(predict.simple.r[,2], PD.test$Class)
perf.simple = performance(pred.simple, "tpr", "fpr")

# Plot ROC curve for the simplified model
plot(perf.simple, col="red", main="ROC curve of the hand model")
abline(0, 1, lty=2)

auc.simple = performance(pred.simple, "auc")

# Display the pruned tree
plot(PDfit.simple)
text(PDfit.simple, pretty=0)

# Print accuracy and AUC for the simplified model
table.simple
accuracy.simple
as.numeric(auc.simple@y.values)

# Print results for all classifiers for comparison
result

# QUESTION 10 ################################################################################

# Tune hyperparameters for the decision tree model
hyperparameters = list(
  mincut = c(1, 5, 10, 15, 20, 25, 40),
  minsize = c(1, 5, 10, 15, 20, 25, 40)
)

best_model = list(mincut = 1, minsize = 1)
best_accuracy = 0

# Iterate over hyperparameters to find the best model
for (cut in hyperparameters$mincut) {
  for (size in hyperparameters$minsize) {
    if (cut <= size/2) {  # Ensure mincut is less than or equal to minsize/2
      model = tree(Class ~ ., data = PD.test, mincut = cut, minsize = size)
      test_predictions = predict(model, PD.test, type = "class")
      accuracy = sum(test_predictions == PD.test$Class) / nrow(PD.test)
      if (accuracy > best_accuracy) {
        best_model <- list(mincut = cut, minsize = size)
        best_accuracy = accuracy
      }
    }
  }
}

# Print the best hyperparameters and train the best model
best_model
PDfit.best = tree(Class ~ ., data = PD.test, mincut = best_model$mincut, minsize = best_model$minsize)

# Make predictions and calculate accuracy for the best model
predict.best = predict(PDfit.best, PD.test, type = "class")
accuracy.best = sum(predict.best == PD.test$Class) / nrow(PD.test)
table.best = table(observed = PD.test$Class, predicted=predict.best)

# Calculate ROC curve and AUC for the best model
predict.best.r = predict(PDfit.best, PD.test, type="vector")
pred.best = ROCR::prediction(predict.best.r[,2], PD.test$Class)
perf.best = performance(pred.best, "tpr", "fpr")

# Plot ROC curve for the best model
plot(perf.best, col="red", main="ROC curve of the Best Classifier")
abline(0, 1, lty=2)

auc.best = performance(pred.best, "auc")

# Print accuracy and AUC for the best model
table.best
accuracy.best
as.numeric(auc.best@y.values)

# Print results for all classifiers for comparison
result

# QUESTION 11 ################################################################################

# Train a neural network with selected attributes
library(neuralnet)
library(car)

# Convert class labels to numeric for neural network
PD.test.ANN = PD.test
PD.train.ANN = PD.train

PD.train.ANN$Class = as.numeric(PD.train.ANN$Class)
PD.test.ANN$Class = as.numeric(PD.test.ANN$Class)

options(digits=4)
set.seed(32937350) # Ensure reproducibility

# Train neural network with 3 hidden nodes
PD.nn = neuralnet(Class ~ A01 + A18 + A23, PD.train.ANN, hidden=3, stepmax=1e7)
PD.pred.nn = compute(PD.nn, PD.test.ANN[,c(1, 18, 23)])

# Predict probabilities and calculate ROC and AUC
PD.pred.prob = as.vector(PD.pred.nn$net.result)

roc.nn = roc(PD.test.ANN$Class, PD.pred.prob)
auc.nn = auc(roc.nn)

# Round predictions and calculate accuracy
PD.pred.nn = as.data.frame(round(PD.pred.nn$net.result, 0))
accuracy.nn = sum(PD.pred.nn$V1 == PD.test.ANN$Class) / nrow(PD.test.ANN)
table.nn = table(observed = PD.test.ANN$Class, predicted = PD.pred.nn$V1)

# Print results for neural network
table.nn
accuracy.nn
auc.nn

# Print results for all classifiers for comparison
result

# QUESTION 12 ################################################################################

# Train a Support Vector Machine (SVM) model
library(e1071)

# Train SVM with linear kernel
PDfit.svm = svm(formula=Class~., data=PD.train, type="C-classification", kernel="linear")
predict.svm = predict(PDfit.svm, PD.test)

# Create confusion matrix and calculate accuracy
table.svm = table(observed = PD.test$Class, predicted=predict.svm)
acc.svm = (table.svm[2,2]+table.svm[1,1])/sum(table.svm)
acc.svm

# Train SVM with probability estimation
PDfit.svm.p = svm(formula=Class~., data=PD.train, type="C-classification", kernel="linear", probability=TRUE)
predict.svm.p = predict(PDfit.svm.p, PD.test, probability=TRUE)

# Calculate ROC and AUC for SVM
pred.svm = ROCR::prediction(attr(predict.svm.p, "probabilities")[, "1"], PD.test$Class)
auc.svm <- performance(pred.svm, "auc")@y.values[[1]]

# Print results for SVM
table.svm
acc.svm
auc.svm

# Print results for all classifiers for comparison
result
