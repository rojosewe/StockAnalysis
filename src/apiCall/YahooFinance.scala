package apiCall

/**
 * @author Rodrigo Weffer
 */
import java.util.{ GregorianCalendar, Calendar }
import utils.Http

object YahooFinance {

  def csvHistory(symbol: String, months: Int): String = {
    val content = Http("http://ichart.finance.yahoo.com/table.csv").params(getHistoricParams(symbol, months)).asString
    return content.body
  }

  def getHistoricParams(symbol: String, months: Int): Map[String, String] = {
    val today = new GregorianCalendar()
    val toMonth = today.get(Calendar.MONTH)
    val day = today.get(Calendar.DAY_OF_MONTH)
    val year = today.get(Calendar.YEAR)
    val fromMonth = toMonth - months
    val realMonth = fromMonth + 1
    println(s"Fetching $symbol since $day/$realMonth/$year until Today")
    return Map(("s", symbol), ("d", toMonth.toString()), ("e", day.toString()), ("f", year.toString()), ("g", "d"), ("a", fromMonth.toString()), ("b", day.toString()), ("c", year.toString()), ("ignore", ".csv"))
  }

  def main(args: Array[String]): Unit = {
    csvHistory("TSLA", 4)
  }

}