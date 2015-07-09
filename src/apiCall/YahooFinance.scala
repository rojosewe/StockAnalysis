package apiCall

/**
 * @author Rodrigo Weffer
 */
import java.util.{ GregorianCalendar, Calendar }
import utils.Http

object YahooFinance {

  def csvHistory(symbol: String, months: Int): String = {
    val url = "http://ichart.finance.yahoo.com/table.csv"+ getHistoricParams(symbol, months)
    val body = "Date:" + Http(url).asString.body.replace("\n", "\nDate:")
    return body.substring(0,body.length - 5)
  }

  def getHistoricParams(symbol: String, months: Int): String = {
    val today = new GregorianCalendar()
    val toMonth = today.get(Calendar.MONTH)
    val day = today.get(Calendar.DAY_OF_MONTH)
    val year = today.get(Calendar.YEAR)
    val fromMonth = toMonth - months
    val realMonth = fromMonth + 1
    return s"?s=$symbol&d=$toMonth&e=$day&f=$year&g=d&a=$fromMonth&b=$day&c=$year&ignore=.csv"
//    return Map(("s", symbol), ("d", toMonth.toString()), ("e", day.toString()), ("f", year.toString()), ("g", "d"), ("a", fromMonth.toString()), ("b", day.toString()), ("c", year.toString()), ("ignore", ".csv"))
  }

  def main(args: Array[String]): Unit = {
    println(csvHistory("TSLA", 1))
  }

}