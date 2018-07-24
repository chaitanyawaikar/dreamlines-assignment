package utils

object TaskHelper {

  def average(s: Seq[Int]): Double = s.foldLeft((0.0, 1))((acc, i) => ((acc._1 + (i - acc._1) / acc._2), acc._2 + 1))._1

  def mean(xs: Seq[Double]): Option[Double] = {
    if (xs.isEmpty) None
    else Some(xs.sum / xs.length)
  }

  def calculateVariance(xs: Seq[Double]) = {
    //Calculate the deviation of each element from the mean of the sequence:
    //    val a1 = mean(xs).map(m => xs.map(x => x-m))
    //    //calculate the squared deviation from the mean of the sequence:
    //    val a2 = mean(xs).map(m => xs.map(x => math.pow(x-m, 2)))
    //    //calculate the mean of that
    //    val a3 = mean(xs).map(m => mean(xs.map(x => math.pow(x-m, 2))))
    mean(xs).flatMap(m => mean(xs.map(x => math.pow(x - m, 2))))
  }

}
