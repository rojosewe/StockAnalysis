package apiCall


import utils.Http
import java.text.SimpleDateFormat

object GoogleTrendsApi {

  def csvHistory(symbol: String, months: Int): String = {
	  extractValues(jsonHistory(symbol, months))
    
  }
  
  def extractValues(json:String) : String = {
    val value : StringBuilder = new StringBuilder()
    val fPattern = """"f":".+?}""".r
    val df = new SimpleDateFormat("EEEEE, MMMMM dd, yyyy") 
    var isDate = true
    for(m <- fPattern.findAllIn(json)){
      if(isDate)
        value.append(df.parse(m.replace(""""f":"""", "").replace(""""}""", "")).getTime()).append(",")
      else
        value.append(m.replace(""""f":"""", "").replace(""""}""", "")).append("\n")
      isDate = !isDate
    } 
    value.toString()
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