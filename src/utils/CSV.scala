package utils

import apiCall.YahooFinance
import java.text.SimpleDateFormat

/**
 * @author sensefields
 */
class CSV {

  def stringToData(strData: String, header: Boolean): Array[Array[Double]] = {
    processRows(strData.split("\n"), header)
  }

  def processRows(rows: Array[String], header: Boolean): Array[Array[Double]] = {
    if (header) read(rows.tail)
    else read(rows)
  }

  def read(rows: Array[String]): Array[Array[Double]] = {
    val data = Array.ofDim[Double](rows.length, rows(0).split(",").length)
    val df = new SimpleDateFormat("yyyy-mm-dd")
    for ((line, i) <- rows.zipWithIndex) {
      val cols = line.split(",")
      for (j <- 0 to (cols.length - 1)) {
        try {
          data(i)(j) = cols(j).trim().toDouble
        } catch {
          case e: Exception => data(i)(j) = df.parse(cols(j).trim()).getTime()
        }
      }
    }
    return data
  }
}

object CSVTest {

  def main(args: Array[String]): Unit = {
    println(YahooFinance.csvHistory("TSLA", 4).substring(0, 300))
    val csv = new CSV().stringToData(YahooFinance.csvHistory("TSLA", 4), true)
    csv.foreach { x => x.foreach { y => print(y + ",") }; println() }
  }

}