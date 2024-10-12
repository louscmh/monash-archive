# Setup
setwd("~/FIT3152/a1")
rm(list = ls())
library(tidyr)
library(ggplot2)
library(dplyr)

set.seed(32937350) # 32937350 = your student ID
cvbase = read.csv("PsyCoronaBaselineExtract.csv")
cvbase <- cvbase[sample(nrow(cvbase), 40000), ] # 40000 rows


# 1 ######################################################

# (a) ##################

head(cvbase)
tail(cvbase)
dim(cvbase)
str(cvbase)

summary(cvbase)
summary(cvbase[sapply(cvbase,is.numeric)])

sum(sapply(cvbase,is.numeric))
sum(sapply(cvbase,function(x) !is.numeric(x)))

colSums(is.na(cvbase))
sum(is.na(cvbase))

lapply(cvbase[,1:52],unique)

# (b) ##################

# Numerical Data ###########
# Employment Status
cvbase[,1:10] = lapply(cvbase[,1:10],function(x) replace(x,is.na(x),0))
# Isolation Offline
cvbase = cvbase[complete.cases(cvbase[,11:12]),]
# Isolation Online
cvbase = cvbase[complete.cases(cvbase[,13:14]),]
# Loneliness
cvbase = cvbase[complete.cases(cvbase[,15:17]),]
# Life Satisfactio
cvbase = cvbase[complete.cases(cvbase[,18:19]),]
cvbase[is.na(cvbase$MLQ),20] = 0
# Boredom
cvbase = cvbase[complete.cases(cvbase[,21:23]),]
# Conspiracy
cvbase[,24:26] = lapply(cvbase[,24:26],function(x) replace(x,is.na(x),5))
# Corona Personal Behavior
cvbase[,33:35] = lapply(cvbase[,33:35],function(x) replace(x,is.na(x),0))
# Corona RadicalAction
cvbase[,36:38] = lapply(cvbase[,36:38],function(x) replace(x,is.na(x),0))
# Corona Proximity
cvbase[,39:44] = lapply(cvbase[,39:44],function(x) replace(x,is.na(x),0))
# Gender, Age, Education
cvbase = cvbase[complete.cases(cvbase[,45:47]),]
# Corona ProSocial Behavior (Predictor)
cvbase[,49:52] = lapply(cvbase[,49:52],function(x) replace(x,is.na(x),0))

# Categorical Data ###########
# Rank Order Life
cvbase = cvbase[complete.cases(cvbase[,27:32]),]

dim(cvbase)

# 2 ######################################################

# (a) ##################

# Extraction of "focus" country and "others"
# Focus country: "United States of America"
cvbase.focus = cvbase
cvbase.focus$coded_country = ifelse(cvbase.focus$coded_country == "United States of America","United States of America","Others")

# Employment
employment.focus = cvbase.focus %>% group_by(coded_country) %>% summarize(employstatus_1 = sum(employstatus_1),
                                                                          employstatus_2 = sum(employstatus_2),
                                                                          employstatus_3 = sum(employstatus_3),
                                                                          employstatus_4 = sum(employstatus_4),
                                                                          employstatus_5 = sum(employstatus_5),
                                                                          employstatus_6 = sum(employstatus_6),
                                                                          employstatus_7 = sum(employstatus_7),
                                                                          employstatus_8 = sum(employstatus_8),
                                                                          employstatus_9 = sum(employstatus_9),
                                                                          employstatus_10 = sum(employstatus_10))

employment.focus = pivot_longer(employment.focus, cols=2:11,names_to = "employStatus",values_to = "surveyCount")
employmentPlot = ggplot(data=employment.focus, aes(x=reorder(employStatus,surveyCount),y=surveyCount,fill=coded_country)) + 
  geom_col(position="dodge") + 
  labs(x="Employment Type",y="Survey Count",title="Employment Distribution for USA vs Other Countries") +
  scale_fill_discrete(name="Country") +
  theme(axis.text.x = element_text(angle = 90))

# Isolation
isolation.focus = cvbase.focus %>% group_by(coded_country) %>% 
  summarize(inPersonFriendsMean = mean(isoFriends_inPerson),
            inPersonOthersMean = mean(isoOthPpl_inPerson),
            onlineFriendsMean = mean(isoFriends_online),
            onlineOthersMean = mean(isoOthPpl_online))
isolation.focus

# Loneliness
lone.focus = cvbase.focus %>% group_by(coded_country) %>% 
  summarize(lone01Mean = mean(lone01),
            lone02Mean = mean(lone02),
            lone03Mean = mean(lone03))
lone.focus

# Life Satisfaction
life.focus = cvbase.focus %>% group_by(coded_country) %>% 
  summarize(happyMean = mean(happy),
            lifeSatMean = mean(lifeSat),
            MLQ = mean(MLQ))
life.focus

# Boredom
bor.focus = cvbase.focus %>% group_by(coded_country) %>% 
  summarize(bor01Mean = mean(bor01),
            bor02Mean = mean(bor02),
            bor03Mean = mean(bor03))
bor.focus

# Conspiracy
consp.focus = cvbase.focus %>% group_by(coded_country) %>% 
  summarize(consp01Mean = mean(consp01),
            consp02Mean = mean(consp02),
            consp03Mean = mean(consp03))
consp.focus

# Corona Personal Behavior
c19perBeh.focus = cvbase.focus %>% group_by(coded_country) %>% 
  summarize(beh01Mean = mean(c19perBeh01),
            beh02Mean = mean(c19perBeh02),
            beh03Mean = mean(c19perBeh03))
c19perBeh.focus

# Corona RadicalAction
c19RCA.focus = cvbase.focus %>% group_by(coded_country) %>% 
  summarize(c19RCA01Mean = mean(c19RCA01),
            c19RCA02Mean = mean(c19RCA02),
            c19RCA03Mean = mean(c19RCA03))
c19RCA.focus

# Corona Proximity
coronaClose.focus = cvbase.focus %>% group_by(coded_country) %>% 
  summarize(coronaClose_1 = sum(coronaClose_1),
            coronaClose_2 = sum(coronaClose_2),
            coronaClose_3 = sum(coronaClose_3),
            coronaClose_4 = sum(coronaClose_4),
            coronaClose_5 = sum(coronaClose_5),
            coronaClose_6 = sum(coronaClose_6))
coronaClose.focus

coronaClose.focus = pivot_longer(coronaClose.focus, cols=2:7,names_to = "coronaClose",values_to = "surveyCount")
coronaProximityPlot = ggplot(data=coronaClose.focus, aes(x=reorder(coronaClose,surveyCount),y=surveyCount,fill=coded_country)) + 
  geom_col(position="dodge") + 
  labs(x="Proximity Type",y="Survey Count",title="Corona Proximity Distribution for USA vs Other Countries") +
  scale_fill_discrete(name="Country") +
  theme(axis.text.x = element_text(angle = 90))

# Gender + Age
age.focus = coronaClose.focus = cvbase.focus %>% group_by(coded_country,gender,age) %>% summarize(count=n())
age.focus$gender[age.focus$gender == 1] = "female"
age.focus$gender[age.focus$gender == 2] = "male"
age.focus$gender[age.focus$gender == 3] = "others"

ageGenderPlot = ggplot(data=age.focus, aes(x=age,y=count,fill=gender)) +
  geom_col(position="dodge") + facet_grid(cols=vars(coded_country))

# Corona ProSocial Behavior
c19ProSo01.focus =  cvbase.focus %>% group_by(coded_country) %>% 
  summarize(c19ProSo01 = mean(c19ProSo01),
            c19ProSo02 = mean(c19ProSo02),
            c19ProSo03 = mean(c19ProSo03),
            c19ProSo04 = mean(c19ProSo04))

# combining all mean tables together
result = merge(isolation.focus,lone.focus,by="coded_country")
result = merge(result,life.focus,by="coded_country")
result = merge(result,bor.focus,by="coded_country")
result = merge(result,consp.focus,by="coded_country")
result = merge(result,c19perBeh.focus,by="coded_country")
result = merge(result,c19RCA.focus,by="coded_country")
result = merge(result,c19ProSo01.focus,by="coded_country")
result = t(result)
colnames(result) = c("Others","United States")
result = result[-1,]

# Result of all calculations
result
employmentPlot
coronaProximityPlot
ageGenderPlot

# (b) ##################

# a new dataset is created that contains only USA entries.
cvbase.focus = cvbase[which(cvbase$coded_country == "United States of America"), ]
unique(cvbase.focus$coded_country)
# Removing coded_country attribute as it is not needed for regression
cvbase.focus$coded_country = NULL
# Attaching the dataset to check for contrasts in the categorical variables to prove that they are in different classes
attach(cvbase.focus)

rankOrdLife_1 = factor(rankOrdLife_1)
rankOrdLife_2 = factor(rankOrdLife_2)
rankOrdLife_3 = factor(rankOrdLife_3)
rankOrdLife_4 = factor(rankOrdLife_4)
rankOrdLife_5 = factor(rankOrdLife_5)
rankOrdLife_6 = factor(rankOrdLife_6)

contrasts(rankOrdLife_1) = contr.treatment(6)
contrasts(rankOrdLife_2) = contr.treatment(6)
contrasts(rankOrdLife_3) = contr.treatment(6)
contrasts(rankOrdLife_4) = contr.treatment(6)
contrasts(rankOrdLife_5) = contr.treatment(6)
contrasts(rankOrdLife_6) = contr.treatment(6)

contrasts(rankOrdLife_1)
contrasts(rankOrdLife_2)
contrasts(rankOrdLife_3)
contrasts(rankOrdLife_4)
contrasts(rankOrdLife_5)
contrasts(rankOrdLife_6)

# Fitting the model to predict each pro-so attribute individually
focus.fit.So01 = lm(c19ProSo01 ~ . - c19ProSo02 - c19ProSo03 - c19ProSo04, data=cvbase.focus)
focus.fit.So02 = lm(c19ProSo02 ~ . - c19ProSo01 - c19ProSo03 - c19ProSo04, data=cvbase.focus)
focus.fit.So03 = lm(c19ProSo03 ~ . - c19ProSo01 - c19ProSo02 - c19ProSo04, data=cvbase.focus)
focus.fit.So04 = lm(c19ProSo04 ~ . - c19ProSo01 - c19ProSo02 - c19ProSo03, data=cvbase.focus)

# Summarizing all models
summary(focus.fit.So01)
summary(focus.fit.So02)
summary(focus.fit.So03)
summary(focus.fit.So04)

# Plotting the models
par(mfrow = c(2, 2))
plot(focus.fit.So01)
plot(focus.fit.So02)
plot(focus.fit.So03)
plot(focus.fit.So04)
par(mfrow = c(1, 1))

# (c) ##################

# a new dataset is created that contains everything but USA entries.
cvbase.other = cvbase[which(cvbase$coded_country != "United States of America"), ]
unique(cvbase.other$coded_country)
# Removing coded_country attribute as it is not needed for regression
cvbase.other$coded_country = NULL
# Attaching the dataset to check for contrasts in the categorical variables to prove that they are in different classes
attach(cvbase.other)

contrasts(rankOrdLife_1) = contr.treatment(6)
contrasts(rankOrdLife_2) = contr.treatment(6)
contrasts(rankOrdLife_3) = contr.treatment(6)
contrasts(rankOrdLife_4) = contr.treatment(6)
contrasts(rankOrdLife_5) = contr.treatment(6)
contrasts(rankOrdLife_6) = contr.treatment(6)

contrasts(rankOrdLife_1)
contrasts(rankOrdLife_2)
contrasts(rankOrdLife_3)
contrasts(rankOrdLife_4)
contrasts(rankOrdLife_5)
contrasts(rankOrdLife_6)

# Fitting the model to predict each pro-so attribute individually
other.fit.So01 = lm(c19ProSo01 ~ . - c19ProSo02 - c19ProSo03 - c19ProSo04, data=cvbase.other)
other.fit.So02 = lm(c19ProSo02 ~ . - c19ProSo01 - c19ProSo03 - c19ProSo04, data=cvbase.other)
other.fit.So03 = lm(c19ProSo03 ~ . - c19ProSo01 - c19ProSo02 - c19ProSo04, data=cvbase.other)
other.fit.So04 = lm(c19ProSo04 ~ . - c19ProSo01 - c19ProSo02 - c19ProSo03, data=cvbase.other)

# Summarizing all models
summary(other.fit.So01)
summary(other.fit.So02)
summary(other.fit.So03)
summary(other.fit.So04)

# Plotting the models
par(mfrow = c(2, 2))
plot(other.fit.So01)
plot(other.fit.So02)
plot(other.fit.So03)
plot(other.fit.So04)
par(mfrow = c(1, 1))

# 3 ######################################################

# (a) ##################

# Extracting the two external datasets I've chosen and cleaning them, removing any attributes I don't need and omitting
# NA values.
economy = read.csv("economy.csv")
health = read.csv("health.csv")
economy = economy[complete.cases(economy[,2:3]),]
table = merge(economy,health, by="location_key")
table$hospital_beds_per_1000 = NULL
table$pollution_mortality_rate = NULL
table$comorbidity_mortality_rate = NULL
table$diabetes_prevalence = NULL
table$pollution_nurses_per_1000 = NULL
table$human_capital_index = NULL
table$out_of_pocket_health_expenditure_usd = NULL
table$smoking_prevalence = NULL
table$life_expectancy = NULL
table = na.omit(table)
# Exporting the data as a csv, this csv file is the table used for clustering (and is shown in the report appendix)
write.csv(table, "3a.csv", row.names = FALSE)
table = read.csv("3a.csv")

# Normalizing the data
table[,2:9] = scale(table[,2:9]) 

# Performing Kmeans Clustering
fit_kmeans = kmeans(table[,2:9],3,nstart=20)
fit_kmeans
table$cluster.kmeans = fit_kmeans$cluster

# Performing Hiearchial Clustering
fit_hierarchy = hclust(dist(table[,2:9]),"ward.D")
print(fit_hierarchy)
cut.fit_hierarchy = cutree(fit_hierarchy,k=3)
table$cluster.hiearchy = cut.fit_hierarchy

# Result of both clusters is taken and the countries that is present in both clusters is identified.
intersect(table[table$cluster.kmeans == table$cluster.kmeans[table$location_key == "US"],1],
          table[table$cluster.hiearchy == table$cluster.hiearchy[table$location_key == "US"],1])

# (b) ##################

# a new dataset is created that contains all the similar countries extracted from above
similar_countries = c("Australia","Belgium","Canada","Switzerland","Germany","Denmark","Finland","France",
                      "United Kingdom","Ireland","Iceland","Japan","Luxembourg","Netherlands","Norway","Sweden")

cvbase.cluster = cvbase[which(cvbase$coded_country %in% similar_countries), ]
unique(cvbase.cluster$coded_country)
# Removing coded_country attribute as it is not needed for regression
cvbase.cluster$coded_country = NULL

# Attaching the dataset to check for contrasts in the categorical variables to prove that they are in different classes
attach(cvbase.cluster)

contrasts(rankOrdLife_1) = contr.treatment(6)
contrasts(rankOrdLife_2) = contr.treatment(6)
contrasts(rankOrdLife_3) = contr.treatment(6)
contrasts(rankOrdLife_4) = contr.treatment(6)
contrasts(rankOrdLife_5) = contr.treatment(6)
contrasts(rankOrdLife_6) = contr.treatment(6)

contrasts(rankOrdLife_1)
contrasts(rankOrdLife_2)
contrasts(rankOrdLife_3)
contrasts(rankOrdLife_4)
contrasts(rankOrdLife_5)
contrasts(rankOrdLife_6)

# Fitting the model to predict each pro-so attribute individually
cluster.fit.So01 = lm(c19ProSo01 ~ . - c19ProSo02 - c19ProSo03 - c19ProSo04, data=cvbase.cluster)
cluster.fit.So02 = lm(c19ProSo02 ~ . - c19ProSo01 - c19ProSo03 - c19ProSo04, data=cvbase.cluster)
cluster.fit.So03 = lm(c19ProSo03 ~ . - c19ProSo01 - c19ProSo02 - c19ProSo04, data=cvbase.cluster)
cluster.fit.So04 = lm(c19ProSo04 ~ . - c19ProSo01 - c19ProSo02 - c19ProSo03, data=cvbase.cluster)

# Summarizing all models
summary(cluster.fit.So01)
summary(cluster.fit.So02)
summary(cluster.fit.So03)
summary(cluster.fit.So04)

# Plotting the models
par(mfrow = c(2, 2))
plot(cluster.fit.So01)
plot(cluster.fit.So02)
plot(cluster.fit.So03)
plot(cluster.fit.So04)
par(mfrow = c(1, 1))