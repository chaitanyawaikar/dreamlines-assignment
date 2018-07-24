package fileProcessor

import java.io.File
import models.FileDataModel
import scala.io.Source
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global


object ProcessFiles {

  def getListOfFiles(dir: String):List[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).toList
    } else {
      List[File]()
    }
  }

  def convertFileDataToCaseClass(fileName: File) ={
    Source.fromFile(fileName).getLines.toList.map{
      line =>
        val splitter = line.split("\\|")
        FileDataModel(splitter(0),splitter(1),splitter(2),splitter(3),splitter(4),splitter(5),splitter(6),splitter(7),splitter(8),splitter(9),splitter(10),Option(splitter(11)),splitter(12),splitter(13),splitter(14),splitter(15),splitter(16),splitter(17))
    }
  }

  def processFiles(dir: String): Future[List[FileDataModel]] ={
    val listOfFiles = getListOfFiles(dir)
    println("listOfFiles "+listOfFiles )
    Future.sequence(listOfFiles.map{
      filePath =>
    Future{convertFileDataToCaseClass(filePath)}
    }).map{
      x=> x.flatten
    }
  }

}
