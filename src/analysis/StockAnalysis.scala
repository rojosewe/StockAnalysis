package analysis

/**
 * @author Rodrigo Weffer
 */
class StockAnalysis(data: Array[Array[Double]]) {

  val OPENING_INDEX = 1
  val HIGH_INDEX = 2
  val LOW_INDEX = 3
  val CLOSING_INDEX = 4
  val VOLUME_INDEX = 5

  val last = data.length - 1
  val size = data.length

  def rise(): Double = {
    data(0)(CLOSING_INDEX) - data(last)(CLOSING_INDEX)
  }

  def percenturalRise(): Double = {
    (data(0)(CLOSING_INDEX) * 100 / data(last)(CLOSING_INDEX)) - 100
  }

  def deltaMean(): Double = {
    data.foldLeft(0.0) { (sum, x) => sum + Math.abs(x(CLOSING_INDEX) - x(OPENING_INDEX)) } / size
  }

  def deltaStdDev(): Double = {
    val mean = deltaMean()
    data.foldLeft(0.0) { (sum, x) => sum + Math.pow(x(CLOSING_INDEX) - x(OPENING_INDEX) - mean, 2) } / size
  }
  
  def deltaStdDev(mean: Double): Double = {
		  data.foldLeft(0.0) { (sum, x) => sum + Math.pow(x(CLOSING_INDEX) - x(OPENING_INDEX) - mean, 2) } / size
  }

  def mean(): Double = {
    data.foldLeft(0.0) { (sum, x) => sum + Math.abs(x(CLOSING_INDEX)) } / size
  }

  def stdDev(): Double = {
    val mean = deltaMean()
    data.foldLeft(0.0) { (sum, x) => sum + Math.pow(x(CLOSING_INDEX) - mean, 2) } / size
  }

  def stdDev(mean: Double): Double = {
		  data.foldLeft(0.0) { (sum, x) => sum + Math.pow(x(CLOSING_INDEX) - mean, 2) } / size
  }
  

  def today() = { data(0)(CLOSING_INDEX) }

  def monthsAgo() = { data(last)(CLOSING_INDEX) }

  def max(): Double = {
    data.foldLeft(0.0) { (max, row) => if (max > row(CLOSING_INDEX)) max else row(CLOSING_INDEX) }
  }

  def min(): Double = {
    data.foldLeft(Double.MaxValue) { (min, row) => if (min < row(CLOSING_INDEX)) min else row(CLOSING_INDEX) }
  }
  
  def print() = {
    val m:Double = mean()
    val dm:Double = deltaMean()
    println(new StringBuilder()
    .append("Stock % Rise: " + percenturalRise() + "'\n")
    .append("Stock Rise: " + rise() + "'\n")
    .append("Stock Min: " + min() + "'\n")
    .append("Stock Max: " + max() + "'\n")
    .append(s"Stock months Ago : " + monthsAgo() + "'\n")
    .append("Stock Today: " + today() + "'\n")
    .append("Stock DeltaMean: " + dm + "'\n")
    .append("Stock DeltaStdDev: " + deltaStdDev(dm) + "'\n")
    .append("Stock Mean: " + m + "'\n")
    .append("Stock StdDev: " + stdDev(m) + "'\n")
    .toString())
  }
}


