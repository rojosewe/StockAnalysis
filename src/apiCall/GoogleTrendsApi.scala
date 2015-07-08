package apiCall

import utils.Http
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Arrays

object GoogleTrendsApi {

  def csvHistory(symbol: String, months: Int): String = {
    extractValues(jsonHistory(symbol, months))

  }

  def extractValues(json: String): String = {
    val value: StringBuilder = new StringBuilder()
    val fPattern = """"v":.+?,"""".r
    var isDate = true
    try {
      for (m <- fPattern.findAllIn(json)) {
        if (isDate) {
          val values = m.replace(""""v":new Date(""", "").replace("""),"""", "").split(",")
          val cal = Calendar.getInstance()
          cal.set(Integer.valueOf(values(0)), Integer.valueOf(values(1)), Integer.valueOf(values(2)))
          value.append(cal.getTime.getTime).append(",")
        } else {
          println(m.replace(""""v":""", "").replace(""","""", ""))
          value.append(m.replace(""""v":""", "").replace(""","""", "").toDouble).append("\n")
        }
        isDate = !isDate
      }
    } catch {
      case t: Throwable => if (!isDate) value.setLength(value.length - 14)
    }
    return value.toString()
  }

  def jsonHistory(symbol: String, months: Int): String = {
    val content = Http(s"http://www.google.com/trends/fetchComponent?hl=en-US&cmpt=q&tz=Etc/GMT-2&export=3&cid=TIMESERIES_GRAPH_0&q=$symbol&date=today+$months-m&content=1").asString
    return content.body
  }

  def getHistoricParams(symbol: String, months: Int): Map[String, String] = {
    return Map(("hl", "en-US"), ("q", symbol), ("date", s"today+$months-m"), ("cmpt", "q"), ("tz", "Etc/GMT-2"), ("tz", "Etc/GMT-2"), ("content", "1"), ("cid", "TIMESERIES_GRAPH_0"), ("export", "3"))
  }

  def main(args: Array[String]): Unit = {
    println(csvHistory("TSLA", 3))
  }
}