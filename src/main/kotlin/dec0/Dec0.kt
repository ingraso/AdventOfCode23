package dec0

import java.io.File

class Dec0 {
    fun getInput(): List<String> {
        return File("src/main/kotlin/dec0/input.txt").readLines()
    }

    fun partOne(inputs: List<String>): Int {
        return 1
    }

    fun partTwo(inputs: List<String>): Int {
        return 1
    }
}
fun main() {
    println("Today is the 0th of December!")
    val dec0 = Dec0()
    val inputs = dec0.getInput()
    println(dec0.partOne(inputs))
    println(dec0.partTwo(inputs))
}
