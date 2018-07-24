package tasks

import dreamlinesio.FileWriter
import models._
import utils.TaskHelper

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


object Task1 {

  //a) What are the cheapest sails for each of the starting harbours
  def getCheapestSailsPerStartHarbour(inputData : Future[List[FileDataModel]] ) ={
    inputData.map{
      list =>
        val result = list.groupBy(_.startHarbour).map{
          harbourDetails =>
            //Fetches the sail id along with min price for that sail.
            val cheapestSail: (String, Int) = harbourDetails._2.map(x => (x.sailId,x.minPriceSail.toInt)).sortBy(_._2).head
            CheapestSailsPerStartHarbour(harbourDetails._1,cheapestSail._1,cheapestSail._2)
        }.toList
        FileWriter.writeChepestSailsDataToFile(result)
    }
  }

  //b) What are the most expensive sails for each of the round trip cruises in the feed
  def getExpensiveSailsForRoundTrips(inputData : Future[List[FileDataModel]] )={
    inputData.map{
      list =>
        val result = list.filter(x => x.startHarbour.equalsIgnoreCase(x.endHarbour)).groupBy(_.startHarbour).map{
          harbourDetails =>
            //Fetches the sail id along with max price for that sail.
            val mostExpensiveSail: (String, Int) = harbourDetails._2.map(x => (x.sailId,x.minPriceSail.toInt)).sortBy(_._2).last
            MostExpensiveSailsPerRoundTrip(harbourDetails._1,mostExpensiveSail._1,mostExpensiveSail._2)
        }.toList
        FileWriter.writeExpensiveSailsDataToFile(result)
    }
  }

  //c) Our Data science team thinks that it is useful to know what is the average count of numerical characters per line and what is the numerical character that appears the most in the feed
  def dataScienceTeam(inputData : Future[List[FileDataModel]] )=
  {
   inputData.map{
     list =>
       list.map{
         feedDetails =>
           val charactersPerLine = feedDetails.toString.filter(Character.isDigit(_))
           val characterFrequency = charactersPerLine.groupBy(identity).collect { case (k, v) => (k -> v.size) }
           (charactersPerLine.size,characterFrequency)
       }
   }.map{
     characterFeed =>
       val averageCharacterCountPerLine = TaskHelper.average(characterFeed.map(_._1).toSeq)
         val completeFileCharacterFrequency: Map[Char, List[Int]] = characterFeed.map(_._2).map(_.toList).flatten.foldLeft(List[(Char,Int)]()){ (a, b) => b :: a }.groupBy(_._1).mapValues(_.map(_._2).toList)
       val highestFreqChar = completeFileCharacterFrequency.collect{case (k,v) => (k->v.size)}.maxBy(_._2)
       val result = DataScienceTeamOutput(averageCharacterCountPerLine,highestFreqChar._1)
       FileWriter.writeDataScienceTeamDataToFile(result)
   }
  }

  //d) We want to know what is the cruise id that appears the most in the feed, how many times it appears, and what is the variance of its prices
  def cruiseIdMaxTimes(inputData : Future[List[FileDataModel]] )={
    inputData.map{
      list =>
        val groupByCruiseId = list.groupBy(_.cruiseNid)
        val cruiseIdAppearingMaxTimes: (String, Int) = groupByCruiseId.collect{case (k,v) => (k,v.size)}.maxBy(_._2)
        val variance = TaskHelper.calculateVariance(groupByCruiseId.get(cruiseIdAppearingMaxTimes._1).get.map(_.minPriceSail.toDouble))
        val result = CruiseIdMaxTimes(cruiseIdAppearingMaxTimes._1,cruiseIdAppearingMaxTimes._2,variance)
        FileWriter.cruiseIdMaxTimesWriter(result)
    }
  }
}
