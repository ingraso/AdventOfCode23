package dec4

import java.io.File

class Dec4 {
    fun getInput(): List<String> {
        return File("src/main/kotlin/dec4/input.txt").readLines()
    }

    fun partOne(inputs: List<String>): Int {
        return 1
    }

    fun partTwo(inputs: List<String>): Int {
        return 1
    }
}
fun main() {
    println("Today is the 4th of December!")
    val dec4 = Dec4()
    val inputs = dec4.getInput()
    println(dec4.partOne(inputs))
    println(dec4.partTwo(inputs))
}
