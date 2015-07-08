package analysis

import apiCall._
import utils.CSV
import java.util.Arrays

/**
 * @author Rodrigo Weffer
 */
object multiStockAnalysis {
  def main(args: Array[String]): Unit = {
    val stocks = Array[String]("TSLA", "AAPL", "TWTR", "NFLX", "IBM", "AMZN", "LNKD", "DDD", "HPQ", "QLIK", "FB")
    val monthsAgo = 3
    for (stock <- stocks) {
      val data = new CSV().stringToData(YahooFinance.csvHistory(stock, monthsAgo), true)

      new StockAnalysis(data).print()
      if (monthsAgo <= 3) {
        val trendData = new CSV().stringToData(GoogleTrendsApi.csvHistory(stock, monthsAgo), false)
        new TrendsAnalysis(data).print()
      }
      println("--------------------------------------------------------------------------")
    }
  }
}