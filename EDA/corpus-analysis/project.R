# Setup
setwd("~/FIT3152/a3")  # Set working directory
rm(list = ls())  # Clear the workspace
set.seed(32937350)  # Set seed for reproducibility

# Libraries
library(slam) 
library(tm) 
library(SnowballC)
library(igraph)
library(igraphdata)
library(scales)

# QUESTION 2 ####################################
# Load the corpus of reviews
cname = file.path(".", "Corpus-Reviews")
print(file(cname))
reviews = Corpus(DirSource(cname))
print(summary(reviews))

# QUESTION 3 ####################################
# Preprocess the reviews
reviews = tm_map(reviews, removeNumbers)
reviews = tm_map(reviews, removePunctuation)
reviews = tm_map(reviews, content_transformer(tolower))
reviews = tm_map(reviews, removeWords, stopwords("english"))
reviews = tm_map(reviews, stripWhitespace)
reviews = tm_map(reviews, stemDocument, language = "english")

# Create Document-Term Matrix (DTM)
dtm = DocumentTermMatrix(reviews)
dim(dtm)  # Dimensions of the DTM

# Remove sparse terms
dtm.postSparse = removeSparseTerms(dtm, 0.27)
dim(dtm.postSparse)  # Dimensions after removing sparse terms
inspect(dtm.postSparse)  # Inspect the sparse DTM
findFreqTerms(dtm.postSparse, lowfreq = 10)  # Find frequent terms

# Convert DTM to a data frame
dtm.postSparse.df = as.data.frame(as.matrix(dtm.postSparse))
dtm.postSparse.df

# Remove specific terms
remove = c("get", "one", "also", "even")
dtm.postSparse = dtm.postSparse[, !(colnames(dtm.postSparse) %in% remove)]
dtm.postSparse
dim(dtm.postSparse)  # Dimensions after removing specific terms
inspect(dtm.postSparse)  # Inspect the modified DTM
findFreqTerms(dtm.postSparse, lowfreq = 10)  # Find frequent terms after removal

# Update DTM data frame after term removal
dtm.postSparse.df = as.data.frame(as.matrix(dtm.postSparse))
dtm.postSparse.df

# The DTM is written into a csv to be exported into the appendix of the report
write.csv(dtm.postSparse.df, "dtm_final.csv")

# QUESTION 4 ####################################
# Hierarchical clustering
dtm.postSparse.m = as.matrix(dtm.postSparse)
distmatrix = proxy::dist(dtm.postSparse.m, method = "cosine")
fit = hclust(distmatrix, method = "ward.D")
plot(fit, hang = -1, main = "REVIEW CLUSTERING RESULT")

# Cut tree into 3 clusters
groups = cutree(fit, k = 3)
rect.hclust(fit, k = 3, border = 2:5)

# Create a confusion matrix for clusters
topics = c("anime", "anime", "anime", "anime", "anime", "book", "book", "book", "book", "book", "tv", "tv", "tv", "tv", "tv")
CM = table(GroupNames = topics, Clusters = groups)
CM = CM[, c(1, 3, 2)]  # Reorder columns
TA = as.data.frame.matrix(CM)
TA

# Calculate clustering accuracy
accuracy = sum(diag(CM)) / sum(CM)
print(paste("Accuracy:", accuracy))

# Summary of hierarchical clustering
summary(fit)
fit

# QUESTION 5 ####################################
# Single Mode Network (Document Similarity)
dtm.postSparse.m
class(dtm.postSparse.m)
dtm.postSparse.binary = as.matrix((dtm.postSparse.m > 0) + 0)
dtm.postSparse.binary
dtm.postSparse.bam = dtm.postSparse.binary %*% t(dtm.postSparse.binary)
dtm.postSparse.bam
diag(dtm.postSparse.bam) = 0  # Remove self-loops
dtm.postSparse.bam

# Create graph from adjacency matrix
set.seed(32937350)
ByAbs = graph_from_adjacency_matrix(dtm.postSparse.bam, mode = "undirected", weighted = TRUE)
plot(ByAbs, main = "Single Mode Network (Abstract)")

# Calculate and format closeness centrality
format(closeness(ByAbs), digits = 2)
closeness = format(closeness(ByAbs), digits = 2)

# Rescale betweenness centrality
betweenness = rescale(betweenness(ByAbs), to = c(5, 15))

# Layout for the graph
kk_layout = layout_with_kk(ByAbs)
set.seed(32937350)
fr_layout = layout_with_fr(ByAbs, coords = kk_layout, niter = 1000)

# Assign colors to vertices based on clusters
cluster_colors = c("#ffb4a4", "#ffdea4", "#ffffa4")
vertex_colors = cluster_colors[groups]

# Plot the graph with improved visualization
plot(ByAbs, layout = fr_layout,
     vertex.size = closeness(ByAbs) * 5000,
     vertex.color = vertex_colors,
     edge.width = E(ByAbs)$weight / max(E(ByAbs)$weight) * 4,
     main = "Abstract Network Plot (Improved)"
)

# QUESTION 6 ####################################
# Single Mode Network (Token Similarity)
dtm.postSparse.btm = t(dtm.postSparse.binary) %*% dtm.postSparse.binary
diag(dtm.postSparse.btm) = 0  # Remove self-loops
dtm.postSparse.btm

# Create graph from adjacency matrix
set.seed(32937350)
ByToken = graph_from_adjacency_matrix(dtm.postSparse.btm, mode = "undirected", weighted = TRUE)
plot(ByToken, main = "Single Mode Network (Token)")

# Layout for the token graph
kk_layout.token = layout_with_kk(ByToken)
set.seed(32937350)
fr_layout.token = layout_with_fr(ByToken, coords = kk_layout.token, niter = 1000)

# Calculate and format eigen centrality
format(eigen_centrality(ByToken)$vector, digits = 2)
scaled_centrality = rescale(eigen_centrality(ByToken)$vector, to = c(5, 15))

# Plot the token graph with improved visualization
plot(ByToken, layout = fr_layout.token,
     vertex.size = scaled_centrality,
     edge.width = (E(ByToken)$weight / max(E(ByToken)$weight) * 3),
     main = "Token Network Plot (Improved)"
)

# QUESTION 7 ####################################
# Two-Mode Network (Documents and Tokens)
dtm.postSparse.m
dtmsa = as.data.frame(dtm.postSparse.m)
dtmsa$ABS = rownames(dtmsa)
dtmsb = data.frame()

# Create edge list for two-mode network
for (i in 1:nrow(dtmsa)) {
  for (j in 1:(ncol(dtmsa) - 1)) {
    touse = cbind(dtmsa[i, j], dtmsa[i, ncol(dtmsa)], colnames(dtmsa[j]))
    dtmsb = rbind(dtmsb, touse)
  }
}
colnames(dtmsb) = c("weight", "abs", "token")
dtmsc = dtmsb[dtmsb$weight != 0, ]
dtmsc = dtmsc[, c(2, 3, 1)]
dtmsc

# Create bipartite graph
set.seed(32937350)
g = graph.data.frame(dtmsc, directed = FALSE)
bipartite.mapping(g)
V(g)$type = bipartite.mapping(g)$type
V(g)$color = ifelse(V(g)$type, "lightgreen", "pink")
V(g)$shape = ifelse(V(g)$type, "circle", "square")
E(g)$color = "darkgrey"
plot(g, main = "Two-Mode Network Plot")

# Calculate degree centrality
degree(g)

# Cluster detection using fast greedy algorithm
communities = cluster_fast_greedy(g)
double.group = split(V(g), communities$membership)

# Scale node sizes based on degree centrality
degree_centrality = degree(g)
V(g)$size = 5 + (degree_centrality - min(degree_centrality)) * 10 / (max(degree_centrality) - min(degree_centrality))

# Plot the graph with communities highlighted
set.seed(32937350)
plot(g, mark.groups = double.group, main = "Two-Mode Network Plot (Improved)")
