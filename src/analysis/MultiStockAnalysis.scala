package analysis

import apiCall._
import utils.CSV
import java.util.Arrays
import scala.collection.mutable.HashMap

/**
 * @author Rodrigo Weffer
 */
object multiStockAnalysis {
  def main(args: Array[String]): Unit = {
    val stocks = Array[String]("TSLA", "AAPL", "TWTR", "NFLX", "IBM", "AMZN", "LNKD", "DDD", "HPQ", "QLIK", "FB")

    for (monthsAgo <- 4 to 8) {
    	val trendPercRise = new HashMap[String, Double]()
 			val stockPercRise = new HashMap[String, Double]()
      println(s"$monthsAgo months ago")
      for (stock <- stocks) {
        val data = new CSV().stringToData(YahooFinance.csvHistory(stock, monthsAgo), true)
        val sa = new StockAnalysis(data)
        stockPercRise(stock) = sa.percenturalRise()
        //      sa.print()
        val trendData = new CSV().stringToData(GoogleTrendsApi.csvHistory(stock, monthsAgo), false)
        val ta = new TrendsAnalysis(data)
        //      ta.print()
        trendPercRise(stock) = ta.percenturalRise()

        //      println("--------------------------------------------------------------------------")
      }
      println("Stocks: " + stockPercRise)
      println("Trends: " + trendPercRise)
    }
  }
}