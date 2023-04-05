package com.sundogsoftware.spark.my

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object FriendsByAgeDS {

  case class FakeFriends(id: Int, name: String, age: Int, friends: Int)

  def main(args: Array[String]) {
    val spark = SparkSession
      .builder
      .appName("FriendsByAge")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._
    val schemaPeople = spark.read
      .option("header", "true")
      .option("inferSchema", "true")
      .csv("data/fakefriends.csv")
      .as[FakeFriends]

    schemaPeople
      .select("age", "friends")
      .groupBy("age")
      .agg(
        round(
          avg("friends"), 2
        ).alias("friends_avg"))
      .sort("age")
      .show()
  }
}
