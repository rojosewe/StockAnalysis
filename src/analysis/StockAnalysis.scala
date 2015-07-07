

import utils.CSV
import apiCall.YahooFinance
import apiCall.GoogleTrendsApi

/**
 * @author Rodrigo Weffer
 */
class StockAnalysis {

  val OPENING_INDEX = 1
  val HIGH_INDEX = 2
  val LOW_INDEX = 3
  val CLOSING_INDEX = 4
  val VOLUME_INDEX = 5

  def rise(data: Array[Array[Double]]): Double = {
    data(0)(CLOSING_INDEX) - data(data.length - 1)(CLOSING_INDEX)
  }

  def percenturalRise(data: Array[Array[Double]]): Double = {
    (data(0)(CLOSING_INDEX) * 100 / data(data.length - 1)(CLOSING_INDEX)) - 100
  }

  def deltaMean(data: Array[Array[Double]]): Double = {
    data.foldLeft(0.0) { (sum, x) => sum + Math.abs(x(CLOSING_INDEX) - x(OPENING_INDEX)) } / data.length
  }

  def deltaStdDev(data: Array[Array[Double]]): Double = {
    val mean = deltaMean(data)
    data.foldLeft(0.0) { (sum, x) => sum + Math.pow(x(CLOSING_INDEX) - x(OPENING_INDEX) - mean, 2) } / data.length
  }

  def deltaStdDev(data: Array[Array[Double]], mean: Double): Double = {
    data.foldLeft(0.0) { (sum, x) => sum + Math.pow(x(CLOSING_INDEX) - x(OPENING_INDEX) - mean, 2) } / data.length
  }

  def today(data: Array[Array[Double]]) = { data(0)(CLOSING_INDEX) }
  def monthsAgo(data: Array[Array[Double]]) = { data(data.length - 1)(CLOSING_INDEX) }

  def max(data: Array[Array[Double]]): Double = {
    data.foldLeft(0.0) { (max, row) => if (max > row(CLOSING_INDEX)) max else row(CLOSING_INDEX) }
  }

  def min(data: Array[Array[Double]]): Double = {
    data.foldLeft(Double.MaxValue) { (min, row) => if (min < row(CLOSING_INDEX)) min else row(CLOSING_INDEX) }
  }
}

object StockAnalysisTest {
	
	def main(args: Array[String]): Unit = {
			val stocks = Array[String]("TSLA", "AAPL", "TWTR", "NFLX", "IBM", "AMZN", "LNKD", "DDD", "HPQ", "QLIK", "FB")
					val monthsAgo = 6
					val sa: StockAnalysis = new StockAnalysis()
					for (stock <- stocks) {
						val data = new CSV().stringToData(YahooFinance.csvHistory(stock, monthsAgo), true)
								val trendData = new CSV().stringToData(GoogleTrendsApi.csvHistory(stock, monthsAgo), true)
								
								println(data(0)(0))
								println(trendData(0)(0))
								
								val mean = sa.deltaMean(data)
								println("% Rise: " + sa.percenturalRise(data))
								println("Rise: " + sa.rise(data))
								println("Min: " + sa.min(data))
								println("Max: " + sa.max(data))
								println(s"$monthsAgo months Ago : " + sa.monthsAgo(data))
								println("StdDev: " + sa.deltaStdDev(data, mean))
								println("Today: " + sa.today(data))
								println("Mean: " + mean)
								println("--------------------------------------------------------------------------")
					}
	}
}


