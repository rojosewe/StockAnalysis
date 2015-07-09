package utils

import apiCall.YahooFinance
import java.text.SimpleDateFormat
import java.util.Arrays
import java.nio.file.{ Paths, Files }
import java.nio.charset.StandardCharsets
import java.nio.file.Paths
import java.nio.file.Files
import java.nio.charset.StandardCharsets
import apiCall.GoogleTrendsApi

/**
 * @author sensefields
 */
class CSV {

  def stringToData(strData: String, header: Boolean): Array[Array[Double]] = {
    processRows(strData.split("\n"), header)
  }

  def arraystoFile(filePath: String, strData: String, header: Boolean, reverse: Boolean = false) = stringToFile(filePath, arraysToString(strData, header, reverse))

  def arraysToString(strData: String, header: Boolean, reverse: Boolean = false): String = {
    val sb = new StringBuilder()
    processRows(strData.split("\n"), header, reverse).foreach { r =>
      sb.append(Arrays.toString(r).replace("[", "").replace("]", "")).append("\n")
    }
    sb.toString()
  }

  def stringToFile(filePath: String, content: String) =
    Files.write(Paths.get(filePath), content.getBytes(StandardCharsets.UTF_8))

  def processRows(rows: Array[String], header: Boolean, reverse: Boolean = false): Array[Array[Double]] = {
    if (header) read(rows.tail, reverse)
    else read(rows, reverse)
  }

  def read(rowsOrig: Array[String], reverse: Boolean = false): Array[Array[Double]] = {
    var rows: Array[String] = null
    if (reverse)
      rows = rowsOrig.reverse
    else
      rows = rowsOrig
    val df = new SimpleDateFormat("yyyy-MM-dd")
    val data = Array.ofDim[Double](rows.length, rows(0).split(",").length)
    for ((line, i) <- rows.zipWithIndex) {
      val cols = line.split(",")
      for (j <- 0 to (cols.length - 1)) {
        if (cols(j).startsWith("Date:")) {
//          println(cols(j).trim().replace("Date:", ""), "->", df.parse(cols(j).trim().replace("Date:", "")), "->", df.parse(cols(j).trim().replace("Date:", "")).getTime())
          data(i)(j) = df.parse(cols(j).trim().replace("Date:", "")).getTime()/1000
        }else if (cols(j).isEmpty()) {
          data(i)(j) = 0.0
        } else {
          data(i)(j) = cols(j).trim().toDouble
        }
      }
    }
    return data
  }
}

object CSVTest {

  def main(args: Array[String]): Unit = {
//    println(GoogleTrendsApi.csvHistory("TSLA", 4).substring(0, 300))
    val csv = new CSV().stringToData(GoogleTrendsApi.csvHistory("TSLA", 4), true)
    csv.foreach { x => x.foreach { y => print(y + ",") }; println() }
  }

}