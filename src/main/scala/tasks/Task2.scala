package tasks

import models.FileDataModel

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object Task2 {

def mergeRecords(oldDataRecords: Future[List[FileDataModel]], newDataRecords: Future[List[FileDataModel]] ): Future[List[FileDataModel]] =
{
  oldDataRecords.flatMap{ oldData =>
    newDataRecords.map{
      newData =>
        val priceToBeSetToZero = oldData.toSet.diff(newData.toSet).map(x => x.copy(minPriceSail = "0"))
        val recordsToBeAdded = newData.toSet diff oldData.toSet
        recordsToBeAdded.toList ++ priceToBeSetToZero.toList
    }
  }
}



}
