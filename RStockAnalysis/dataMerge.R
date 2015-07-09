
options("scipen"=100, "digits"=4)
ticket <- "TSLA"
stockData <- read.csv(paste("~/development/workspace/git/StockAnalysis/csv/stock-", ticket,".txt", sep = ''), header = FALSE)
trendsData <- read.csv(paste("~/development/workspace/git/StockAnalysis/csv/trends-", ticket,".txt", sep = ''), header = FALSE)

colnames(stockData) <- c("t","open","high","low","close","volume","adj")
colnames(trendsData) <- c("t","hits")


stockData[,1]
trendsData[,1]
