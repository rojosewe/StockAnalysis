package io

import apiCall.YahooFinance
import utils.CSV
import apiCall.GoogleTrendsApi

/**
 * @author sensefields
 */

object MultiStockDataToFiles {
  
  def main(args: Array[String]): Unit = {
    val stocks = Array[String]("TSLA", "AAPL", "TWTR", "NFLX", "IBM", "AMZN", "LNKD", "DDD", "HPQ", "QLIK", "FB")
//    val stocks = Array[String]("TSLA")
    val monthsAgo = 6
    for (stock <- stocks) {
      val csv = new CSV()
//      println("Stocks")
      csv.arraystoFile(s"csv/stock-$stock.txt", YahooFinance.csvHistory(stock, monthsAgo), true, true)
//      println("Trends")
      csv.arraystoFile(s"csv/trends-$stock.txt", GoogleTrendsApi.csvHistory(stock, monthsAgo), false)
    }
  }
}