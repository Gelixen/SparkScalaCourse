package com.sundogsoftware.spark.my

import org.apache.spark.SparkContext

object TotalAmountSpentByCustomer {

  def parseLine(line: String) = {
    val fields = line.split(",")
    val customerId = fields(0).toInt
    val price = fields(2).toFloat
    (customerId, price)
  }

  def main(args: Array[String]) {
    val sc = new SparkContext("local[*]", "TotalSpentByCustomer")

    val input = sc.textFile("data/customer-orders.csv")

    val purchases = input.map(parseLine)
    println("------------")
//    println(purchases.collect().mkString("Array(", ", ", ")"))
//    println(purchases.countByKey())
    println("------------")

    val pricePerCustomer = purchases.reduceByKey((x, y) => x + y)

    val results = pricePerCustomer.map(t => (t._2, t._1))
      .sortByKey()
      .collect() // without collecting, on RDD, everything gets parallelized again, losing sort
      .foreach(s => println(f"Customer ${s._2} spent total ${s._1}%.2f"))

//    for (result <- results.sortBy(_._2)) {
//      val customer = result._1
//      val total = result._2
//      val formattedTotal = f"$total%.2f"
//      println(s"Customer $customer spent: $formattedTotal")
//    }
  }
}
