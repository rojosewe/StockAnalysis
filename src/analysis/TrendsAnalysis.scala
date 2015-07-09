package analysis

import scala.collection.mutable.WrappedArray
import scala.annotation.tailrec
import scala.util.control.Breaks.break
import scala.collection.mutable.WrappedArrayBuilder
import apiCall.GoogleTrendsApi
import utils.CSV

/**
 * @author Rodrigo Weffer
 */
class TrendsAnalysis(data: Array[Array[Double]]) {

  val last = data.length - 1
  val size = data.length

  def rise(): Double = {
    data(last)(1) - data(0)(1)
  }

  def percenturalRise(): Double = {
    (data(last)(1) * 100 / data(0)(1)) - 100
  }
  
  def mean(): Double = deltaMean(0, (data: WrappedArray[Array[Double]]))

  @tailrec
  final def mean(sum: Double, data: Seq[Array[Double]]): Double = 
    data.tail match {
      case Nil => return (data.head(1) + sum) / data.length
      case _ => mean(sum + data.head(1), data.tail)
    }
  
  def deltaMean(): Double = deltaMean(0, (data: WrappedArray[Array[Double]]))

  @tailrec
  final def deltaMean(sum: Double, data: Seq[Array[Double]]): Double = 
    data.tail match {
      case Nil => return sum / data.length
      case _ => deltaMean(sum + data.tail.head(1) - data.head(1), data.tail)
    }
  
  def stdDev(mean:Double): Double = stdDev(0, (data: WrappedArray[Array[Double]]), mean)

  @tailrec
  final def stdDev(sum: Double, data: Seq[Array[Double]], mean :Double): Double = {
    data.tail match {
      case Nil => return (data.head(1) + sum) / data.length
      case _ => stdDev(sum + Math.pow(data.head(1) - mean, 2), data.tail, mean) 
    }
  }
  
  def deltaStdDev(mean:Double): Double = deltaStdDev(0, (data: WrappedArray[Array[Double]]), mean:Double)

  @tailrec
  final def deltaStdDev(sum: Double, data: Seq[Array[Double]], mean :Double): Double = 
    data.tail match {
      case Nil => return sum / data.length
      case _ => deltaStdDev(sum + Math.pow(data.tail.head(1) - data.head(1) - mean, 2), data.tail, mean)
    }

  def today() = { data(last)(1) }

  def monthsAgo() = { data(0)(1) }

  def max(): Double = {
    data.foldLeft(0.0) { (max, row) => if (max > row(1)) max else row(1) }
  }

  def min(): Double = {
    data.foldLeft(Double.MaxValue) { (min, row) => if (min < row(1)) min else row(1) }
  }
  
  def print() = {
    val m:Double = mean()
    val dm:Double = deltaMean()
    println(new StringBuilder()
    .append("Trends % Rise: " + percenturalRise() + "'\n")
    .append("Trends Rise: " + rise() + "'\n")
    .append("Trends Min: " + min() + "'\n")
    .append("Trends Max: " + max() + "'\n")
    .append(s"Trends months Ago : " + monthsAgo() + "'\n")
    .append("Trends Today: " + today() + "'\n")
    .append("Trends DeltaMean: " + dm + "'\n")
    .append("Trends DeltaStdDev: " + deltaStdDev(dm) + "'\n")
    .append("Trends Mean: " + m + "'\n")
    .append("Trends StdDev: " + stdDev(m) + "'\n")
    .toString())
  }
}
  
object TrendsAnalysisTest{
  def main(args: Array[String]): Unit = {
    val data  = new CSV().stringToData(GoogleTrendsApi.csvHistory("TSLA", 3), false)
    println(new TrendsAnalysis(data).deltaMean())
  }
}


