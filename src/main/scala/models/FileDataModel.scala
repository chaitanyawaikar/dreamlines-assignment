package models

case class FileDataModel(companyId: String, companyTitle: String, cruiseNid: String, cruiseTitle: String, area: String, startHarbour: String, endHarbour: String, originalImage: String, shipTitle: String, nights: String, includesFlight: String, stars: Option[String], shipSize: String, shipNid: String, sailId: String, departure: String, arrival: String, minPriceSail: String
                        )

case class CheapestSailsPerStartHarbour( startHarbour: String, sailId: String,minPriceSail: Int)

case class MostExpensiveSailsPerRoundTrip( startHarbour: String, sailId: String, minPriceSail: Int)

case class DataScienceTeamOutput(averageCount:Double,characterApperingMost:Character)

case class CruiseIdMaxTimes(cruiseId:String,frequency : Int,variance : Option[Double])