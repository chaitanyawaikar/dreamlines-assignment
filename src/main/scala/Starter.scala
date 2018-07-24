import dreamlinesio.FileDownloadTest
import fileProcessor.ProcessFiles
import tasks.{Task1, Task2}

object Starter extends App {

  val baseDir = "C:\\Users\\Asus\\Desktop\\dreamlines\\Programmatically"
  val url = "https://s3-eu-west-1.amazonaws.com/com.connectivity/code_task/prices.zip"
  val newUrl = "https://s3-eu-west-1.amazonaws.com/com.connectivity/code_task/updated.zip"
  val destinationForPrices = "C:\\Users\\Asus\\Desktop\\dreamlines\\Programmatically\\unzipped files\\prices files"
  val destinationForUpdatedPrices = "C:\\Users\\Asus\\Desktop\\dreamlines\\Programmatically\\unzipped files\\updated files"

  FileDownloadTest.downloadAndUnzipFile(baseDir,url,destinationForPrices)

  //Task 1
  val resp = ProcessFiles.processFiles(destinationForPrices)
  Task1.getCheapestSailsPerStartHarbour(resp)
  Task1.getExpensiveSailsForRoundTrips(resp)
  Task1.dataScienceTeam(resp)
  Task1.cruiseIdMaxTimes(resp)

//  Task 2
  FileDownloadTest.downloadAndUnzipFile(baseDir,newUrl,destinationForUpdatedPrices)
  val resp1 = ProcessFiles.processFiles(destinationForUpdatedPrices)
  val mergedRecords = Task2.mergeRecords(resp,resp1)
  Task1.getCheapestSailsPerStartHarbour(mergedRecords)
  Task1.getExpensiveSailsForRoundTrips(mergedRecords)
  Task1.dataScienceTeam(mergedRecords)
  Task1.cruiseIdMaxTimes(mergedRecords)

  Thread.sleep(5000)
}
