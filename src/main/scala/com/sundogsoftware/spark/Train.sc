val number = 451

println(f"some $number%05d")

println(Math.PI)
val doubled = Math.PI * 2
println(f"${Math.PI * 2}%.3f")

var lastLastNumber = 0
var lastNumber = 1
for (i <- 1 to 10) {
  val fibonacci = i match {
    case 1 => lastLastNumber
    case 2 => lastNumber
    case _ => {
      val sum = lastNumber + lastLastNumber
      lastLastNumber = lastNumber
      lastNumber = sum
      sum
    }
  }
  println(fibonacci)
}

def square(x: Int): Int = {
  x * x
}

def square(x: Int) = x * x


val someList = List("first", "second", "third")

someList(0)
someList(1)
someList(2)

someList.head // gives first elment
someList.tail // gives all the reamining elements, after the first

for (el <- someList) { } // iteration

someList.map((word: String) => { word.reverse })
someList.map(_.reverse)
someList.reduce((acc: String, word: String) => acc + " " + word)

val numbers = Seq.range(1, 20)

numbers.filter(_ % 3 == 0)