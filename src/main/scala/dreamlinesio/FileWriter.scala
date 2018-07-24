package dreamlinesio

import java.io.{FileOutputStream, ObjectOutputStream}

import models.{CheapestSailsPerStartHarbour, CruiseIdMaxTimes, DataScienceTeamOutput, MostExpensiveSailsPerRoundTrip}

object FileWriter {

  val outputPath = "C:\\Users\\Asus\\Desktop\\dreamlines\\Programmatically\\OutputFiles"
  def timeInMills = System.currentTimeMillis().toString
  val separator = ","
  val newLine = "\n"

  def writeChepestSailsDataToFile(input : List[CheapestSailsPerStartHarbour]) = {
    val out = new ObjectOutputStream(new FileOutputStream(outputPath+"\\CheapestSails"+timeInMills+".txt"))
    input.map{
      elem =>
        out.writeObject(elem.startHarbour+separator+elem.sailId+separator+elem.minPriceSail)
        out.writeChars(newLine)
    }
    out.close()
  }

  def writeExpensiveSailsDataToFile(input : List[MostExpensiveSailsPerRoundTrip]) = {
    val out = new ObjectOutputStream(new FileOutputStream(outputPath+"\\ExpensiveSails"+timeInMills+".txt"))
    input.map{
      elem =>
        out.writeObject(elem.startHarbour+separator+elem.sailId+separator+elem.minPriceSail)
        out.writeChars(newLine)
    }
    out.close()
  }

def writeDataScienceTeamDataToFile(input : DataScienceTeamOutput) = {
  val out = new ObjectOutputStream(new FileOutputStream(outputPath+"\\DataScienceTeam"+timeInMills+".txt"))
  out.writeObject("Frequently appearing character","Average count")
  out.writeChars(newLine)
      out.writeObject(input.characterApperingMost+separator+input.averageCount)
  out.close()
}

  def cruiseIdMaxTimesWriter(input:CruiseIdMaxTimes) ={
    val out = new ObjectOutputStream(new FileOutputStream(outputPath+"\\CruiseIdMaxTimes"+timeInMills+".txt"))
    out.writeChars("Frequency,CruiseId,Variance")
    out.writeObject(input.frequency+separator+input.cruiseId+separator+input.variance)
    out.close()
  }
}
