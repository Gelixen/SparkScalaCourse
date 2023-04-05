package com.sundogsoftware.spark.my

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types.{FloatType, IntegerType, StructType}

object TotalAmountSpentByCustomerDS {

  case class Order(customerId: Int, itemId: Int, price: Float)

  def main(args: Array[String]) {
    val sc = new SparkContext("local[*]", "TotalAmountSpentByCustomerDS")

    val spark = SparkSession
      .builder
      .appName("MinTemperatures")
      .master("local[*]")
      .getOrCreate()

    val orderSchema = new StructType()
      .add("customerId", IntegerType, nullable = true)
      .add("itemId", IntegerType, nullable = true)
      .add("price", FloatType, nullable = true)

    import spark.implicits._
    val ds = spark.read
      .schema(orderSchema)
      .csv("data/customer-orders.csv")
      .as[Order]

    val sortedDs = ds.groupBy("customerId")
      .agg(
        round(sum("price"), 2)
          .alias("totalSpent")
      )
      .withColumn("totalSpent", format_number($"totalSpent", 2))
      .sort("totalSpent")

    sortedDs.show(sortedDs.count().toInt)

  }
}
